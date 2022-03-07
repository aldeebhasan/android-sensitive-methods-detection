package java.util.logging;

import java.util.concurrent.*;
import java.lang.ref.*;
import sun.reflect.*;
import java.util.function.*;
import java.security.*;
import java.util.*;

public class Logger
{
    private static final Handler[] emptyHandlers;
    private static final int offValue;
    static final String SYSTEM_LOGGER_RB_NAME = "sun.util.logging.resources.logging";
    private static final LoggerBundle SYSTEM_BUNDLE;
    private static final LoggerBundle NO_RESOURCE_BUNDLE;
    private volatile LogManager manager;
    private String name;
    private final CopyOnWriteArrayList<Handler> handlers;
    private volatile LoggerBundle loggerBundle;
    private volatile boolean useParentHandlers;
    private volatile Filter filter;
    private boolean anonymous;
    private ResourceBundle catalog;
    private String catalogName;
    private Locale catalogLocale;
    private static final Object treeLock;
    private volatile Logger parent;
    private ArrayList<LogManager.LoggerWeakRef> kids;
    private volatile Level levelObject;
    private volatile int levelValue;
    private WeakReference<ClassLoader> callersClassLoaderRef;
    private final boolean isSystemLogger;
    public static final String GLOBAL_LOGGER_NAME = "global";
    @Deprecated
    public static final Logger global;
    
    public static final Logger getGlobal() {
        LogManager.getLogManager();
        return Logger.global;
    }
    
    protected Logger(final String s, final String s2) {
        this(s, s2, null, LogManager.getLogManager(), false);
    }
    
    Logger(final String name, final String s, final Class<?> clazz, final LogManager manager, final boolean isSystemLogger) {
        this.handlers = new CopyOnWriteArrayList<Handler>();
        this.loggerBundle = Logger.NO_RESOURCE_BUNDLE;
        this.useParentHandlers = true;
        this.manager = manager;
        this.isSystemLogger = isSystemLogger;
        this.setupResourceInfo(s, clazz);
        this.name = name;
        this.levelValue = Level.INFO.intValue();
    }
    
    private void setCallersClassLoaderRef(final Class<?> clazz) {
        final ClassLoader classLoader = (clazz != null) ? clazz.getClassLoader() : null;
        if (classLoader != null) {
            this.callersClassLoaderRef = new WeakReference<ClassLoader>(classLoader);
        }
    }
    
    private ClassLoader getCallersClassLoader() {
        return (this.callersClassLoaderRef != null) ? this.callersClassLoaderRef.get() : null;
    }
    
    private Logger(final String name) {
        this.handlers = new CopyOnWriteArrayList<Handler>();
        this.loggerBundle = Logger.NO_RESOURCE_BUNDLE;
        this.useParentHandlers = true;
        this.name = name;
        this.isSystemLogger = true;
        this.levelValue = Level.INFO.intValue();
    }
    
    void setLogManager(final LogManager manager) {
        this.manager = manager;
    }
    
    private void checkPermission() throws SecurityException {
        if (!this.anonymous) {
            if (this.manager == null) {
                this.manager = LogManager.getLogManager();
            }
            this.manager.checkPermission();
        }
    }
    
    private static Logger demandLogger(final String s, final String s2, final Class<?> clazz) {
        final LogManager logManager = LogManager.getLogManager();
        if (System.getSecurityManager() != null && !SystemLoggerHelper.disableCallerCheck && clazz.getClassLoader() == null) {
            return logManager.demandSystemLogger(s, s2);
        }
        return logManager.demandLogger(s, s2, clazz);
    }
    
    @CallerSensitive
    public static Logger getLogger(final String s) {
        return demandLogger(s, null, Reflection.getCallerClass());
    }
    
    @CallerSensitive
    public static Logger getLogger(final String s, final String s2) {
        final Class<?> callerClass = Reflection.getCallerClass();
        final Logger demandLogger = demandLogger(s, s2, callerClass);
        demandLogger.setupResourceInfo(s2, callerClass);
        return demandLogger;
    }
    
    static Logger getPlatformLogger(final String s) {
        return LogManager.getLogManager().demandSystemLogger(s, "sun.util.logging.resources.logging");
    }
    
    public static Logger getAnonymousLogger() {
        return getAnonymousLogger(null);
    }
    
    @CallerSensitive
    public static Logger getAnonymousLogger(final String s) {
        final LogManager logManager = LogManager.getLogManager();
        logManager.drainLoggerRefQueueBounded();
        final Logger logger = new Logger(null, s, Reflection.getCallerClass(), logManager, false);
        logger.anonymous = true;
        logger.doSetParent(logManager.getLogger(""));
        return logger;
    }
    
    public ResourceBundle getResourceBundle() {
        return this.findResourceBundle(this.getResourceBundleName(), true);
    }
    
    public String getResourceBundleName() {
        return this.loggerBundle.resourceBundleName;
    }
    
    public void setFilter(final Filter filter) throws SecurityException {
        this.checkPermission();
        this.filter = filter;
    }
    
    public Filter getFilter() {
        return this.filter;
    }
    
    public void log(final LogRecord logRecord) {
        if (!this.isLoggable(logRecord.getLevel())) {
            return;
        }
        final Filter filter = this.filter;
        if (filter != null && !filter.isLoggable(logRecord)) {
            return;
        }
        for (Logger logger = this; logger != null; logger = (this.isSystemLogger ? logger.parent : logger.getParent())) {
            final Handler[] array = this.isSystemLogger ? logger.accessCheckedHandlers() : logger.getHandlers();
            for (int length = array.length, i = 0; i < length; ++i) {
                array[i].publish(logRecord);
            }
            if (!(this.isSystemLogger ? logger.useParentHandlers : logger.getUseParentHandlers())) {
                break;
            }
        }
    }
    
    private void doLog(final LogRecord logRecord) {
        logRecord.setLoggerName(this.name);
        final LoggerBundle effectiveLoggerBundle = this.getEffectiveLoggerBundle();
        final ResourceBundle userBundle = effectiveLoggerBundle.userBundle;
        final String resourceBundleName = effectiveLoggerBundle.resourceBundleName;
        if (resourceBundleName != null && userBundle != null) {
            logRecord.setResourceBundleName(resourceBundleName);
            logRecord.setResourceBundle(userBundle);
        }
        this.log(logRecord);
    }
    
    public void log(final Level level, final String s) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(new LogRecord(level, s));
    }
    
    public void log(final Level level, final Supplier<String> supplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(new LogRecord(level, supplier.get()));
    }
    
    public void log(final Level level, final String s, final Object o) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setParameters(new Object[] { o });
        this.doLog(logRecord);
    }
    
    public void log(final Level level, final String s, final Object[] parameters) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setParameters(parameters);
        this.doLog(logRecord);
    }
    
    public void log(final Level level, final String s, final Throwable thrown) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setThrown(thrown);
        this.doLog(logRecord);
    }
    
    public void log(final Level level, final Throwable thrown, final Supplier<String> supplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, supplier.get());
        logRecord.setThrown(thrown);
        this.doLog(logRecord);
    }
    
    public void logp(final Level level, final String sourceClassName, final String sourceMethodName, final String s) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        this.doLog(logRecord);
    }
    
    public void logp(final Level level, final String sourceClassName, final String sourceMethodName, final Supplier<String> supplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, supplier.get());
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        this.doLog(logRecord);
    }
    
    public void logp(final Level level, final String sourceClassName, final String sourceMethodName, final String s, final Object o) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setParameters(new Object[] { o });
        this.doLog(logRecord);
    }
    
    public void logp(final Level level, final String sourceClassName, final String sourceMethodName, final String s, final Object[] parameters) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setParameters(parameters);
        this.doLog(logRecord);
    }
    
    public void logp(final Level level, final String sourceClassName, final String sourceMethodName, final String s, final Throwable thrown) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setThrown(thrown);
        this.doLog(logRecord);
    }
    
    public void logp(final Level level, final String sourceClassName, final String sourceMethodName, final Throwable thrown, final Supplier<String> supplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, supplier.get());
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setThrown(thrown);
        this.doLog(logRecord);
    }
    
    private void doLog(final LogRecord logRecord, final String resourceBundleName) {
        logRecord.setLoggerName(this.name);
        if (resourceBundleName != null) {
            logRecord.setResourceBundleName(resourceBundleName);
            logRecord.setResourceBundle(this.findResourceBundle(resourceBundleName, false));
        }
        this.log(logRecord);
    }
    
    private void doLog(final LogRecord logRecord, final ResourceBundle resourceBundle) {
        logRecord.setLoggerName(this.name);
        if (resourceBundle != null) {
            logRecord.setResourceBundleName(resourceBundle.getBaseBundleName());
            logRecord.setResourceBundle(resourceBundle);
        }
        this.log(logRecord);
    }
    
    @Deprecated
    public void logrb(final Level level, final String sourceClassName, final String sourceMethodName, final String s, final String s2) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s2);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        this.doLog(logRecord, s);
    }
    
    @Deprecated
    public void logrb(final Level level, final String sourceClassName, final String sourceMethodName, final String s, final String s2, final Object o) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s2);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setParameters(new Object[] { o });
        this.doLog(logRecord, s);
    }
    
    @Deprecated
    public void logrb(final Level level, final String sourceClassName, final String sourceMethodName, final String s, final String s2, final Object[] parameters) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s2);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setParameters(parameters);
        this.doLog(logRecord, s);
    }
    
    public void logrb(final Level level, final String sourceClassName, final String sourceMethodName, final ResourceBundle resourceBundle, final String s, final Object... parameters) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        if (parameters != null && parameters.length != 0) {
            logRecord.setParameters(parameters);
        }
        this.doLog(logRecord, resourceBundle);
    }
    
    @Deprecated
    public void logrb(final Level level, final String sourceClassName, final String sourceMethodName, final String s, final String s2, final Throwable thrown) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s2);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setThrown(thrown);
        this.doLog(logRecord, s);
    }
    
    public void logrb(final Level level, final String sourceClassName, final String sourceMethodName, final ResourceBundle resourceBundle, final String s, final Throwable thrown) {
        if (!this.isLoggable(level)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(level, s);
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setThrown(thrown);
        this.doLog(logRecord, resourceBundle);
    }
    
    public void entering(final String s, final String s2) {
        this.logp(Level.FINER, s, s2, "ENTRY");
    }
    
    public void entering(final String s, final String s2, final Object o) {
        this.logp(Level.FINER, s, s2, "ENTRY {0}", o);
    }
    
    public void entering(final String s, final String s2, final Object[] array) {
        String string = "ENTRY";
        if (array == null) {
            this.logp(Level.FINER, s, s2, string);
            return;
        }
        if (!this.isLoggable(Level.FINER)) {
            return;
        }
        for (int i = 0; i < array.length; ++i) {
            string = string + " {" + i + "}";
        }
        this.logp(Level.FINER, s, s2, string, array);
    }
    
    public void exiting(final String s, final String s2) {
        this.logp(Level.FINER, s, s2, "RETURN");
    }
    
    public void exiting(final String s, final String s2, final Object o) {
        this.logp(Level.FINER, s, s2, "RETURN {0}", o);
    }
    
    public void throwing(final String sourceClassName, final String sourceMethodName, final Throwable thrown) {
        if (!this.isLoggable(Level.FINER)) {
            return;
        }
        final LogRecord logRecord = new LogRecord(Level.FINER, "THROW");
        logRecord.setSourceClassName(sourceClassName);
        logRecord.setSourceMethodName(sourceMethodName);
        logRecord.setThrown(thrown);
        this.doLog(logRecord);
    }
    
    public void severe(final String s) {
        this.log(Level.SEVERE, s);
    }
    
    public void warning(final String s) {
        this.log(Level.WARNING, s);
    }
    
    public void info(final String s) {
        this.log(Level.INFO, s);
    }
    
    public void config(final String s) {
        this.log(Level.CONFIG, s);
    }
    
    public void fine(final String s) {
        this.log(Level.FINE, s);
    }
    
    public void finer(final String s) {
        this.log(Level.FINER, s);
    }
    
    public void finest(final String s) {
        this.log(Level.FINEST, s);
    }
    
    public void severe(final Supplier<String> supplier) {
        this.log(Level.SEVERE, supplier);
    }
    
    public void warning(final Supplier<String> supplier) {
        this.log(Level.WARNING, supplier);
    }
    
    public void info(final Supplier<String> supplier) {
        this.log(Level.INFO, supplier);
    }
    
    public void config(final Supplier<String> supplier) {
        this.log(Level.CONFIG, supplier);
    }
    
    public void fine(final Supplier<String> supplier) {
        this.log(Level.FINE, supplier);
    }
    
    public void finer(final Supplier<String> supplier) {
        this.log(Level.FINER, supplier);
    }
    
    public void finest(final Supplier<String> supplier) {
        this.log(Level.FINEST, supplier);
    }
    
    public void setLevel(final Level levelObject) throws SecurityException {
        this.checkPermission();
        synchronized (Logger.treeLock) {
            this.levelObject = levelObject;
            this.updateEffectiveLevel();
        }
    }
    
    final boolean isLevelInitialized() {
        return this.levelObject != null;
    }
    
    public Level getLevel() {
        return this.levelObject;
    }
    
    public boolean isLoggable(final Level level) {
        return level.intValue() >= this.levelValue && this.levelValue != Logger.offValue;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addHandler(final Handler handler) throws SecurityException {
        handler.getClass();
        this.checkPermission();
        this.handlers.add(handler);
    }
    
    public void removeHandler(final Handler handler) throws SecurityException {
        this.checkPermission();
        if (handler == null) {
            return;
        }
        this.handlers.remove(handler);
    }
    
    public Handler[] getHandlers() {
        return this.accessCheckedHandlers();
    }
    
    Handler[] accessCheckedHandlers() {
        return this.handlers.toArray(Logger.emptyHandlers);
    }
    
    public void setUseParentHandlers(final boolean useParentHandlers) {
        this.checkPermission();
        this.useParentHandlers = useParentHandlers;
    }
    
    public boolean getUseParentHandlers() {
        return this.useParentHandlers;
    }
    
    private static ResourceBundle findSystemResourceBundle(final Locale locale) {
        return AccessController.doPrivileged((PrivilegedAction<ResourceBundle>)new PrivilegedAction<ResourceBundle>() {
            @Override
            public ResourceBundle run() {
                try {
                    return ResourceBundle.getBundle("sun.util.logging.resources.logging", locale);
                }
                catch (MissingResourceException ex) {
                    throw new InternalError(ex.toString());
                }
            }
        });
    }
    
    private synchronized ResourceBundle findResourceBundle(final String catalogName, final boolean b) {
        if (catalogName == null) {
            return null;
        }
        final Locale default1 = Locale.getDefault();
        final LoggerBundle loggerBundle = this.loggerBundle;
        if (loggerBundle.userBundle != null && catalogName.equals(loggerBundle.resourceBundleName)) {
            return loggerBundle.userBundle;
        }
        if (this.catalog != null && default1.equals(this.catalogLocale) && catalogName.equals(this.catalogName)) {
            return this.catalog;
        }
        if (catalogName.equals("sun.util.logging.resources.logging")) {
            this.catalog = findSystemResourceBundle(default1);
            this.catalogName = catalogName;
            this.catalogLocale = default1;
            return this.catalog;
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        try {
            this.catalog = ResourceBundle.getBundle(catalogName, default1, classLoader);
            this.catalogName = catalogName;
            this.catalogLocale = default1;
            return this.catalog;
        }
        catch (MissingResourceException ex) {
            if (b) {
                final ClassLoader callersClassLoader = this.getCallersClassLoader();
                if (callersClassLoader == null || callersClassLoader == classLoader) {
                    return null;
                }
                try {
                    this.catalog = ResourceBundle.getBundle(catalogName, default1, callersClassLoader);
                    this.catalogName = catalogName;
                    this.catalogLocale = default1;
                    return this.catalog;
                }
                catch (MissingResourceException ex2) {
                    return null;
                }
            }
            return null;
        }
    }
    
    private synchronized void setupResourceInfo(final String s, final Class<?> callersClassLoaderRef) {
        final LoggerBundle loggerBundle = this.loggerBundle;
        if (loggerBundle.resourceBundleName != null) {
            if (loggerBundle.resourceBundleName.equals(s)) {
                return;
            }
            throw new IllegalArgumentException(loggerBundle.resourceBundleName + " != " + s);
        }
        else {
            if (s == null) {
                return;
            }
            this.setCallersClassLoaderRef(callersClassLoaderRef);
            if (this.isSystemLogger && this.getCallersClassLoader() != null) {
                this.checkPermission();
            }
            if (this.findResourceBundle(s, true) == null) {
                this.callersClassLoaderRef = null;
                throw new MissingResourceException("Can't find " + s + " bundle", s, "");
            }
            assert loggerBundle.userBundle == null;
            this.loggerBundle = LoggerBundle.get(s, null);
        }
    }
    
    public void setResourceBundle(final ResourceBundle resourceBundle) {
        this.checkPermission();
        final String baseBundleName = resourceBundle.getBaseBundleName();
        if (baseBundleName == null || baseBundleName.isEmpty()) {
            throw new IllegalArgumentException("resource bundle must have a name");
        }
        synchronized (this) {
            final LoggerBundle loggerBundle = this.loggerBundle;
            if (loggerBundle.resourceBundleName != null && !loggerBundle.resourceBundleName.equals(baseBundleName)) {
                throw new IllegalArgumentException("can't replace resource bundle");
            }
            this.loggerBundle = LoggerBundle.get(baseBundleName, resourceBundle);
        }
    }
    
    public Logger getParent() {
        return this.parent;
    }
    
    public void setParent(final Logger logger) {
        if (logger == null) {
            throw new NullPointerException();
        }
        if (this.manager == null) {
            this.manager = LogManager.getLogManager();
        }
        this.manager.checkPermission();
        this.doSetParent(logger);
    }
    
    private void doSetParent(final Logger parent) {
        synchronized (Logger.treeLock) {
            LogManager.LoggerWeakRef loggerWeakRef = null;
            if (this.parent != null) {
                final Iterator<LogManager.LoggerWeakRef> iterator = this.parent.kids.iterator();
                while (iterator.hasNext()) {
                    loggerWeakRef = iterator.next();
                    if (loggerWeakRef.get() == this) {
                        iterator.remove();
                        break;
                    }
                    loggerWeakRef = null;
                }
            }
            this.parent = parent;
            if (this.parent.kids == null) {
                this.parent.kids = new ArrayList<LogManager.LoggerWeakRef>(2);
            }
            if (loggerWeakRef == null) {
                final LogManager manager = this.manager;
                manager.getClass();
                loggerWeakRef = manager.new LoggerWeakRef(this);
            }
            loggerWeakRef.setParentRef(new WeakReference<Logger>(this.parent));
            this.parent.kids.add(loggerWeakRef);
            this.updateEffectiveLevel();
        }
    }
    
    final void removeChildLogger(final LogManager.LoggerWeakRef loggerWeakRef) {
        synchronized (Logger.treeLock) {
            final Iterator<LogManager.LoggerWeakRef> iterator = this.kids.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() == loggerWeakRef) {
                    iterator.remove();
                }
            }
        }
    }
    
    private void updateEffectiveLevel() {
        int levelValue;
        if (this.levelObject != null) {
            levelValue = this.levelObject.intValue();
        }
        else if (this.parent != null) {
            levelValue = this.parent.levelValue;
        }
        else {
            levelValue = Level.INFO.intValue();
        }
        if (this.levelValue == levelValue) {
            return;
        }
        this.levelValue = levelValue;
        if (this.kids != null) {
            for (int i = 0; i < this.kids.size(); ++i) {
                final Logger logger = this.kids.get(i).get();
                if (logger != null) {
                    logger.updateEffectiveLevel();
                }
            }
        }
    }
    
    private LoggerBundle getEffectiveLoggerBundle() {
        final LoggerBundle loggerBundle = this.loggerBundle;
        if (loggerBundle.isSystemBundle()) {
            return Logger.SYSTEM_BUNDLE;
        }
        final ResourceBundle resourceBundle = this.getResourceBundle();
        if (resourceBundle != null && resourceBundle == loggerBundle.userBundle) {
            return loggerBundle;
        }
        if (resourceBundle != null) {
            return LoggerBundle.get(this.getResourceBundleName(), resourceBundle);
        }
        for (Logger parent = this.parent; parent != null; parent = (this.isSystemLogger ? parent.parent : parent.getParent())) {
            final LoggerBundle loggerBundle2 = parent.loggerBundle;
            if (loggerBundle2.isSystemBundle()) {
                return Logger.SYSTEM_BUNDLE;
            }
            if (loggerBundle2.userBundle != null) {
                return loggerBundle2;
            }
            final String s = this.isSystemLogger ? (parent.isSystemLogger ? loggerBundle2.resourceBundleName : null) : parent.getResourceBundleName();
            if (s != null) {
                return LoggerBundle.get(s, this.findResourceBundle(s, true));
            }
        }
        return Logger.NO_RESOURCE_BUNDLE;
    }
    
    static {
        emptyHandlers = new Handler[0];
        offValue = Level.OFF.intValue();
        SYSTEM_BUNDLE = new LoggerBundle("sun.util.logging.resources.logging", (ResourceBundle)null);
        NO_RESOURCE_BUNDLE = new LoggerBundle((String)null, (ResourceBundle)null);
        treeLock = new Object();
        global = new Logger("global");
    }
    
    private static final class LoggerBundle
    {
        final String resourceBundleName;
        final ResourceBundle userBundle;
        
        private LoggerBundle(final String resourceBundleName, final ResourceBundle userBundle) {
            this.resourceBundleName = resourceBundleName;
            this.userBundle = userBundle;
        }
        
        boolean isSystemBundle() {
            return "sun.util.logging.resources.logging".equals(this.resourceBundleName);
        }
        
        static LoggerBundle get(final String s, final ResourceBundle resourceBundle) {
            if (s == null && resourceBundle == null) {
                return Logger.NO_RESOURCE_BUNDLE;
            }
            if ("sun.util.logging.resources.logging".equals(s) && resourceBundle == null) {
                return Logger.SYSTEM_BUNDLE;
            }
            return new LoggerBundle(s, resourceBundle);
        }
    }
    
    private static class SystemLoggerHelper
    {
        static boolean disableCallerCheck;
        
        private static boolean getBooleanProperty(final String s) {
            return Boolean.valueOf(AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
                @Override
                public String run() {
                    return System.getProperty(s);
                }
            }));
        }
        
        static {
            SystemLoggerHelper.disableCallerCheck = getBooleanProperty("sun.util.logging.disableCallerCheck");
        }
    }
}
