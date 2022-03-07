package java.util.logging;

import java.security.*;
import sun.util.logging.*;
import java.beans.*;
import sun.misc.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.lang.ref.*;

public class LogManager
{
    private static final LogManager manager;
    private volatile Properties props;
    private static final Level defaultLevel;
    private final Map<Object, Integer> listenerMap;
    private final LoggerContext systemContext;
    private final LoggerContext userContext;
    private volatile Logger rootLogger;
    private volatile boolean readPrimordialConfiguration;
    private boolean initializedGlobalHandlers;
    private boolean deathImminent;
    private boolean initializedCalled;
    private volatile boolean initializationDone;
    private WeakHashMap<Object, LoggerContext> contextsMap;
    private final ReferenceQueue<Logger> loggerRefQueue;
    private static final int MAX_ITERATIONS = 400;
    private final Permission controlPermission;
    private static LoggingMXBean loggingMXBean;
    public static final String LOGGING_MXBEAN_NAME = "java.util.logging:type=Logging";
    static final /* synthetic */ boolean $assertionsDisabled;
    
    protected LogManager() {
        this(checkSubclassPermissions());
    }
    
    private LogManager(final Void void1) {
        this.props = new Properties();
        this.listenerMap = new HashMap<Object, Integer>();
        this.systemContext = new SystemLoggerContext();
        this.userContext = new LoggerContext();
        this.initializedGlobalHandlers = true;
        this.initializedCalled = false;
        this.initializationDone = false;
        this.contextsMap = null;
        this.loggerRefQueue = new ReferenceQueue<Logger>();
        this.controlPermission = new LoggingPermission("control", null);
        try {
            Runtime.getRuntime().addShutdownHook(new Cleaner());
        }
        catch (IllegalStateException ex) {}
    }
    
    private static Void checkSubclassPermissions() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("shutdownHooks"));
            securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
        }
        return null;
    }
    
    final void ensureLogManagerInitialized() {
        if (this.initializationDone || this != LogManager.manager) {
            return;
        }
        synchronized (this) {
            final boolean b = this.initializedCalled;
            if (!LogManager.$assertionsDisabled && !this.initializedCalled && this.initializationDone) {
                throw new AssertionError((Object)"Initialization can't be done if initialized has not been called!");
            }
            if (b || this.initializationDone) {
                return;
            }
            this.initializedCalled = true;
            try {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        assert LogManager.this.rootLogger == null;
                        assert LogManager.this.initializedCalled && !LogManager.this.initializationDone;
                        LogManager.this.readPrimordialConfiguration();
                        LogManager.this.rootLogger = new RootLogger();
                        LogManager.this.addLogger(LogManager.this.rootLogger);
                        if (!LogManager.this.rootLogger.isLevelInitialized()) {
                            LogManager.this.rootLogger.setLevel(LogManager.defaultLevel);
                        }
                        LogManager.this.addLogger(Logger.global);
                        return null;
                    }
                });
            }
            finally {
                this.initializationDone = true;
            }
        }
    }
    
    public static LogManager getLogManager() {
        if (LogManager.manager != null) {
            LogManager.manager.ensureLogManagerInitialized();
        }
        return LogManager.manager;
    }
    
    private void readPrimordialConfiguration() {
        if (!this.readPrimordialConfiguration) {
            synchronized (this) {
                if (!this.readPrimordialConfiguration) {
                    if (System.out == null) {
                        return;
                    }
                    this.readPrimordialConfiguration = true;
                    try {
                        AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction<Void>() {
                            @Override
                            public Void run() throws Exception {
                                LogManager.this.readConfiguration();
                                PlatformLogger.redirectPlatformLoggers();
                                return null;
                            }
                        });
                    }
                    catch (Exception ex) {
                        assert false : "Exception raised while reading logging configuration: " + ex;
                    }
                }
            }
        }
    }
    
    @Deprecated
    public void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener) throws SecurityException {
        final PropertyChangeListener propertyChangeListener2 = Objects.requireNonNull(propertyChangeListener);
        this.checkPermission();
        synchronized (this.listenerMap) {
            final Integer n = this.listenerMap.get(propertyChangeListener2);
            this.listenerMap.put(propertyChangeListener2, (n == null) ? 1 : (n + 1));
        }
    }
    
    @Deprecated
    public void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener) throws SecurityException {
        this.checkPermission();
        if (propertyChangeListener != null) {
            synchronized (this.listenerMap) {
                final Integer n = this.listenerMap.get(propertyChangeListener);
                if (n != null) {
                    final int intValue = n;
                    if (intValue == 1) {
                        this.listenerMap.remove(propertyChangeListener);
                    }
                    else {
                        assert intValue > 1;
                        this.listenerMap.put(propertyChangeListener, intValue - 1);
                    }
                }
            }
        }
    }
    
    private LoggerContext getUserContext() {
        LoggerContext loggerContext = null;
        final SecurityManager securityManager = System.getSecurityManager();
        final JavaAWTAccess javaAWTAccess = SharedSecrets.getJavaAWTAccess();
        if (securityManager != null && javaAWTAccess != null) {
            final Object appletContext = javaAWTAccess.getAppletContext();
            if (appletContext != null) {
                synchronized (javaAWTAccess) {
                    if (this.contextsMap == null) {
                        this.contextsMap = new WeakHashMap<Object, LoggerContext>();
                    }
                    loggerContext = this.contextsMap.get(appletContext);
                    if (loggerContext == null) {
                        loggerContext = new LoggerContext();
                        this.contextsMap.put(appletContext, loggerContext);
                    }
                }
            }
        }
        return (loggerContext != null) ? loggerContext : this.userContext;
    }
    
    final LoggerContext getSystemContext() {
        return this.systemContext;
    }
    
    private List<LoggerContext> contexts() {
        final ArrayList<LoggerContext> list = new ArrayList<LoggerContext>();
        list.add(this.getSystemContext());
        list.add(this.getUserContext());
        return list;
    }
    
    Logger demandLogger(final String s, final String s2, final Class<?> clazz) {
        Logger logger = this.getLogger(s);
        if (logger == null) {
            final Logger logger2 = new Logger(s, s2, clazz, this, false);
            while (!this.addLogger(logger2)) {
                logger = this.getLogger(s);
                if (logger != null) {
                    return logger;
                }
            }
            return logger2;
        }
        return logger;
    }
    
    Logger demandSystemLogger(final String s, final String s2) {
        final Logger demandLogger = this.getSystemContext().demandLogger(s, s2);
        Logger logger;
        do {
            if (this.addLogger(demandLogger)) {
                logger = demandLogger;
            }
            else {
                logger = this.getLogger(s);
            }
        } while (logger == null);
        if (logger != demandLogger && demandLogger.accessCheckedHandlers().length == 0) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    final Handler[] accessCheckedHandlers = logger.accessCheckedHandlers();
                    for (int length = accessCheckedHandlers.length, i = 0; i < length; ++i) {
                        demandLogger.addHandler(accessCheckedHandlers[i]);
                    }
                    return null;
                }
            });
        }
        return demandLogger;
    }
    
    private void loadLoggerHandlers(final Logger logger, final String s, final String s2) {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                final String[] access$1300 = LogManager.this.parseClassNames(s2);
                for (int i = 0; i < access$1300.length; ++i) {
                    final String s = access$1300[i];
                    try {
                        final Handler handler = (Handler)ClassLoader.getSystemClassLoader().loadClass(s).newInstance();
                        final String property = LogManager.this.getProperty(s + ".level");
                        if (property != null) {
                            final Level level = Level.findLevel(property);
                            if (level != null) {
                                handler.setLevel(level);
                            }
                            else {
                                System.err.println("Can't set level for " + s);
                            }
                        }
                        logger.addHandler(handler);
                    }
                    catch (Exception ex) {
                        System.err.println("Can't load log handler \"" + s + "\"");
                        System.err.println("" + ex);
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        });
    }
    
    final void drainLoggerRefQueueBounded() {
        for (int i = 0; i < 400; ++i) {
            if (this.loggerRefQueue == null) {
                break;
            }
            final LoggerWeakRef loggerWeakRef = (LoggerWeakRef)this.loggerRefQueue.poll();
            if (loggerWeakRef == null) {
                break;
            }
            loggerWeakRef.dispose();
        }
    }
    
    public boolean addLogger(final Logger logger) {
        final String name = logger.getName();
        if (name == null) {
            throw new NullPointerException();
        }
        this.drainLoggerRefQueueBounded();
        if (this.getUserContext().addLocalLogger(logger)) {
            this.loadLoggerHandlers(logger, name, name + ".handlers");
            return true;
        }
        return false;
    }
    
    private static void doSetLevel(final Logger logger, final Level level) {
        if (System.getSecurityManager() == null) {
            logger.setLevel(level);
            return;
        }
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                logger.setLevel(level);
                return null;
            }
        });
    }
    
    private static void doSetParent(final Logger logger, final Logger parent) {
        if (System.getSecurityManager() == null) {
            logger.setParent(parent);
            return;
        }
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                logger.setParent(parent);
                return null;
            }
        });
    }
    
    public Logger getLogger(final String s) {
        return this.getUserContext().findLogger(s);
    }
    
    public Enumeration<String> getLoggerNames() {
        return this.getUserContext().getLoggerNames();
    }
    
    public void readConfiguration() throws IOException, SecurityException {
        this.checkPermission();
        final String property = System.getProperty("java.util.logging.config.class");
        if (property != null) {
            try {
                try {
                    ClassLoader.getSystemClassLoader().loadClass(property).newInstance();
                    return;
                }
                catch (ClassNotFoundException ex2) {
                    Thread.currentThread().getContextClassLoader().loadClass(property).newInstance();
                    return;
                }
            }
            catch (Exception ex) {
                System.err.println("Logging configuration class \"" + property + "\" failed");
                System.err.println("" + ex);
            }
        }
        String s = System.getProperty("java.util.logging.config.file");
        if (s == null) {
            final String property2 = System.getProperty("java.home");
            if (property2 == null) {
                throw new Error("Can't find java.home ??");
            }
            s = new File(new File(property2, "lib"), "logging.properties").getCanonicalPath();
        }
        try (final FileInputStream fileInputStream = new FileInputStream(s)) {
            this.readConfiguration(new BufferedInputStream(fileInputStream));
        }
    }
    
    public void reset() throws SecurityException {
        this.checkPermission();
        synchronized (this) {
            this.props = new Properties();
            this.initializedGlobalHandlers = true;
        }
        for (final LoggerContext loggerContext : this.contexts()) {
            final Enumeration<String> loggerNames = loggerContext.getLoggerNames();
            while (loggerNames.hasMoreElements()) {
                final Logger logger = loggerContext.findLogger(loggerNames.nextElement());
                if (logger != null) {
                    this.resetLogger(logger);
                }
            }
        }
    }
    
    private void resetLogger(final Logger logger) {
        final Handler[] handlers = logger.getHandlers();
        for (int i = 0; i < handlers.length; ++i) {
            final Handler handler = handlers[i];
            logger.removeHandler(handler);
            try {
                handler.close();
            }
            catch (Exception ex) {}
        }
        final String name = logger.getName();
        if (name != null && name.equals("")) {
            logger.setLevel(LogManager.defaultLevel);
        }
        else {
            logger.setLevel(null);
        }
    }
    
    private String[] parseClassNames(final String s) {
        final String property = this.getProperty(s);
        if (property == null) {
            return new String[0];
        }
        final String trim = property.trim();
        int i = 0;
        final ArrayList<String> list = new ArrayList<String>();
        while (i < trim.length()) {
            int j;
            for (j = i; j < trim.length(); ++j) {
                if (Character.isWhitespace(trim.charAt(j))) {
                    break;
                }
                if (trim.charAt(j) == ',') {
                    break;
                }
            }
            final String substring = trim.substring(i, j);
            i = j + 1;
            final String trim2 = substring.trim();
            if (trim2.length() == 0) {
                continue;
            }
            list.add(trim2);
        }
        return list.toArray(new String[list.size()]);
    }
    
    public void readConfiguration(final InputStream inputStream) throws IOException, SecurityException {
        this.checkPermission();
        this.reset();
        this.props.load(inputStream);
        final String[] classNames = this.parseClassNames("config");
        for (int i = 0; i < classNames.length; ++i) {
            final String s = classNames[i];
            try {
                ClassLoader.getSystemClassLoader().loadClass(s).newInstance();
            }
            catch (Exception ex) {
                System.err.println("Can't load config class \"" + s + "\"");
                System.err.println("" + ex);
            }
        }
        this.setLevelsOnExistingLoggers();
        Object o = null;
        synchronized (this.listenerMap) {
            if (!this.listenerMap.isEmpty()) {
                o = new HashMap<Object, Object>(this.listenerMap);
            }
        }
        if (o != null) {
            assert Beans.isBeansPresent();
            final Object propertyChangeEvent = Beans.newPropertyChangeEvent(LogManager.class, null, null, null);
            for (final Map.Entry<Object, V> entry : ((Map<Object, V>)o).entrySet()) {
                final Object key = entry.getKey();
                for (int intValue = (int)entry.getValue(), j = 0; j < intValue; ++j) {
                    Beans.invokePropertyChange(key, propertyChangeEvent);
                }
            }
        }
        synchronized (this) {
            this.initializedGlobalHandlers = false;
        }
    }
    
    public String getProperty(final String s) {
        return this.props.getProperty(s);
    }
    
    String getStringProperty(final String s, final String s2) {
        final String property = this.getProperty(s);
        if (property == null) {
            return s2;
        }
        return property.trim();
    }
    
    int getIntProperty(final String s, final int n) {
        final String property = this.getProperty(s);
        if (property == null) {
            return n;
        }
        try {
            return Integer.parseInt(property.trim());
        }
        catch (Exception ex) {
            return n;
        }
    }
    
    boolean getBooleanProperty(final String s, final boolean b) {
        final String property = this.getProperty(s);
        if (property == null) {
            return b;
        }
        final String lowerCase = property.toLowerCase();
        return lowerCase.equals("true") || lowerCase.equals("1") || (!lowerCase.equals("false") && !lowerCase.equals("0") && b);
    }
    
    Level getLevelProperty(final String s, final Level level) {
        final String property = this.getProperty(s);
        if (property == null) {
            return level;
        }
        final Level level2 = Level.findLevel(property.trim());
        return (level2 != null) ? level2 : level;
    }
    
    Filter getFilterProperty(final String s, final Filter filter) {
        final String property = this.getProperty(s);
        try {
            if (property != null) {
                return (Filter)ClassLoader.getSystemClassLoader().loadClass(property).newInstance();
            }
        }
        catch (Exception ex) {}
        return filter;
    }
    
    Formatter getFormatterProperty(final String s, final Formatter formatter) {
        final String property = this.getProperty(s);
        try {
            if (property != null) {
                return (Formatter)ClassLoader.getSystemClassLoader().loadClass(property).newInstance();
            }
        }
        catch (Exception ex) {}
        return formatter;
    }
    
    private synchronized void initializeGlobalHandlers() {
        if (this.initializedGlobalHandlers) {
            return;
        }
        this.initializedGlobalHandlers = true;
        if (this.deathImminent) {
            return;
        }
        this.loadLoggerHandlers(this.rootLogger, null, "handlers");
    }
    
    void checkPermission() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(this.controlPermission);
        }
    }
    
    public void checkAccess() throws SecurityException {
        this.checkPermission();
    }
    
    private synchronized void setLevelsOnExistingLoggers() {
        final Enumeration<?> propertyNames = this.props.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final String s = (String)propertyNames.nextElement();
            if (!s.endsWith(".level")) {
                continue;
            }
            final String substring = s.substring(0, s.length() - 6);
            final Level levelProperty = this.getLevelProperty(s, null);
            if (levelProperty == null) {
                System.err.println("Bad level value for property: " + s);
            }
            else {
                final Iterator<LoggerContext> iterator = this.contexts().iterator();
                while (iterator.hasNext()) {
                    final Logger logger = iterator.next().findLogger(substring);
                    if (logger == null) {
                        continue;
                    }
                    logger.setLevel(levelProperty);
                }
            }
        }
    }
    
    public static synchronized LoggingMXBean getLoggingMXBean() {
        if (LogManager.loggingMXBean == null) {
            LogManager.loggingMXBean = new Logging();
        }
        return LogManager.loggingMXBean;
    }
    
    static {
        defaultLevel = Level.INFO;
        manager = AccessController.doPrivileged((PrivilegedAction<LogManager>)new PrivilegedAction<LogManager>() {
            @Override
            public LogManager run() {
                LogManager logManager = null;
                String property = null;
                try {
                    property = System.getProperty("java.util.logging.manager");
                    if (property != null) {
                        try {
                            logManager = (LogManager)ClassLoader.getSystemClassLoader().loadClass(property).newInstance();
                        }
                        catch (ClassNotFoundException ex2) {
                            logManager = (LogManager)Thread.currentThread().getContextClassLoader().loadClass(property).newInstance();
                        }
                    }
                }
                catch (Exception ex) {
                    System.err.println("Could not load Logmanager \"" + property + "\"");
                    ex.printStackTrace();
                }
                if (logManager == null) {
                    logManager = new LogManager();
                }
                return logManager;
            }
        });
        LogManager.loggingMXBean = null;
    }
    
    private static class Beans
    {
        private static final Class<?> propertyChangeListenerClass;
        private static final Class<?> propertyChangeEventClass;
        private static final Method propertyChangeMethod;
        private static final Constructor<?> propertyEventCtor;
        
        private static Class<?> getClass(final String s) {
            try {
                return Class.forName(s, true, Beans.class.getClassLoader());
            }
            catch (ClassNotFoundException ex) {
                return null;
            }
        }
        
        private static Constructor<?> getConstructor(final Class<?> clazz, final Class<?>... array) {
            try {
                return (clazz == null) ? null : clazz.getDeclaredConstructor(array);
            }
            catch (NoSuchMethodException ex) {
                throw new AssertionError((Object)ex);
            }
        }
        
        private static Method getMethod(final Class<?> clazz, final String s, final Class<?>... array) {
            try {
                return (clazz == null) ? null : clazz.getMethod(s, array);
            }
            catch (NoSuchMethodException ex) {
                throw new AssertionError((Object)ex);
            }
        }
        
        static boolean isBeansPresent() {
            return Beans.propertyChangeListenerClass != null && Beans.propertyChangeEventClass != null;
        }
        
        static Object newPropertyChangeEvent(final Object o, final String s, final Object o2, final Object o3) {
            try {
                return Beans.propertyEventCtor.newInstance(o, s, o2, o3);
            }
            catch (InstantiationException | IllegalAccessException ex2) {
                final Object o4;
                throw new AssertionError(o4);
            }
            catch (InvocationTargetException ex) {
                final Throwable cause = ex.getCause();
                if (cause instanceof Error) {
                    throw (Error)cause;
                }
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                }
                throw new AssertionError((Object)ex);
            }
        }
        
        static void invokePropertyChange(final Object o, final Object o2) {
            try {
                Beans.propertyChangeMethod.invoke(o, o2);
            }
            catch (IllegalAccessException ex) {
                throw new AssertionError((Object)ex);
            }
            catch (InvocationTargetException ex2) {
                final Throwable cause = ex2.getCause();
                if (cause instanceof Error) {
                    throw (Error)cause;
                }
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                }
                throw new AssertionError((Object)ex2);
            }
        }
        
        static {
            propertyChangeListenerClass = getClass("java.beans.PropertyChangeListener");
            propertyChangeEventClass = getClass("java.beans.PropertyChangeEvent");
            propertyChangeMethod = getMethod(Beans.propertyChangeListenerClass, "propertyChange", Beans.propertyChangeEventClass);
            propertyEventCtor = getConstructor(Beans.propertyChangeEventClass, Object.class, String.class, Object.class, Object.class);
        }
    }
    
    private class Cleaner extends Thread
    {
        private Cleaner() {
            this.setContextClassLoader(null);
        }
        
        @Override
        public void run() {
            LogManager.manager;
            synchronized (LogManager.this) {
                LogManager.this.deathImminent = true;
                LogManager.this.initializedGlobalHandlers = true;
            }
            LogManager.this.reset();
        }
    }
    
    private static class LogNode
    {
        HashMap<String, LogNode> children;
        LoggerWeakRef loggerRef;
        LogNode parent;
        final LoggerContext context;
        
        LogNode(final LogNode parent, final LoggerContext context) {
            this.parent = parent;
            this.context = context;
        }
        
        void walkAndSetParent(final Logger logger) {
            if (this.children == null) {
                return;
            }
            for (final LogNode logNode : this.children.values()) {
                final LoggerWeakRef loggerRef = logNode.loggerRef;
                final Logger logger2 = (loggerRef == null) ? null : loggerRef.get();
                if (logger2 == null) {
                    logNode.walkAndSetParent(logger);
                }
                else {
                    doSetParent(logger2, logger);
                }
            }
        }
    }
    
    final class LoggerWeakRef extends WeakReference<Logger>
    {
        private String name;
        private LogNode node;
        private WeakReference<Logger> parentRef;
        private boolean disposed;
        
        LoggerWeakRef(final Logger logger) {
            super(logger, LogManager.this.loggerRefQueue);
            this.disposed = false;
            this.name = logger.getName();
        }
        
        void dispose() {
            synchronized (this) {
                if (this.disposed) {
                    return;
                }
                this.disposed = true;
            }
            final LogNode node = this.node;
            if (node != null) {
                synchronized (node.context) {
                    node.context.removeLoggerRef(this.name, this);
                    this.name = null;
                    if (node.loggerRef == this) {
                        node.loggerRef = null;
                    }
                    this.node = null;
                }
            }
            if (this.parentRef != null) {
                final Logger logger = this.parentRef.get();
                if (logger != null) {
                    logger.removeChildLogger(this);
                }
                this.parentRef = null;
            }
        }
        
        void setNode(final LogNode node) {
            this.node = node;
        }
        
        void setParentRef(final WeakReference<Logger> parentRef) {
            this.parentRef = parentRef;
        }
    }
    
    class LoggerContext
    {
        private final Hashtable<String, LoggerWeakRef> namedLoggers;
        private final LogNode root;
        
        private LoggerContext() {
            this.namedLoggers = new Hashtable<String, LoggerWeakRef>();
            this.root = new LogNode(null, this);
        }
        
        final boolean requiresDefaultLoggers() {
            final boolean b = this.getOwner() == LogManager.manager;
            if (b) {
                this.getOwner().ensureLogManagerInitialized();
            }
            return b;
        }
        
        final LogManager getOwner() {
            return LogManager.this;
        }
        
        final Logger getRootLogger() {
            return this.getOwner().rootLogger;
        }
        
        final Logger getGlobalLogger() {
            return Logger.global;
        }
        
        Logger demandLogger(final String s, final String s2) {
            return this.getOwner().demandLogger(s, s2, null);
        }
        
        private void ensureInitialized() {
            if (this.requiresDefaultLoggers()) {
                this.ensureDefaultLogger(this.getRootLogger());
                this.ensureDefaultLogger(this.getGlobalLogger());
            }
        }
        
        synchronized Logger findLogger(final String s) {
            this.ensureInitialized();
            final LoggerWeakRef loggerWeakRef = this.namedLoggers.get(s);
            if (loggerWeakRef == null) {
                return null;
            }
            final Logger logger = loggerWeakRef.get();
            if (logger == null) {
                loggerWeakRef.dispose();
            }
            return logger;
        }
        
        private void ensureAllDefaultLoggers(final Logger logger) {
            if (this.requiresDefaultLoggers()) {
                final String name = logger.getName();
                if (!name.isEmpty()) {
                    this.ensureDefaultLogger(this.getRootLogger());
                    if (!"global".equals(name)) {
                        this.ensureDefaultLogger(this.getGlobalLogger());
                    }
                }
            }
        }
        
        private void ensureDefaultLogger(final Logger logger) {
            if (this.requiresDefaultLoggers() && logger != null && (logger == Logger.global || logger == LogManager.this.rootLogger)) {
                if (!this.namedLoggers.containsKey(logger.getName())) {
                    this.addLocalLogger(logger, false);
                }
                return;
            }
            assert logger == null;
        }
        
        boolean addLocalLogger(final Logger logger) {
            return this.addLocalLogger(logger, this.requiresDefaultLoggers());
        }
        
        synchronized boolean addLocalLogger(final Logger logger, final boolean b) {
            if (b) {
                this.ensureAllDefaultLoggers(logger);
            }
            final String name = logger.getName();
            if (name == null) {
                throw new NullPointerException();
            }
            final LoggerWeakRef loggerWeakRef = this.namedLoggers.get(name);
            if (loggerWeakRef != null) {
                if (loggerWeakRef.get() != null) {
                    return false;
                }
                loggerWeakRef.dispose();
            }
            final LogManager owner = this.getOwner();
            logger.setLogManager(owner);
            final LoggerWeakRef loggerRef = owner.new LoggerWeakRef(logger);
            this.namedLoggers.put(name, loggerRef);
            final Level levelProperty = owner.getLevelProperty(name + ".level", null);
            if (levelProperty != null && !logger.isLevelInitialized()) {
                doSetLevel(logger, levelProperty);
            }
            this.processParentHandlers(logger, name);
            final LogNode node = this.getNode(name);
            node.loggerRef = loggerRef;
            Logger logger2 = null;
            for (LogNode logNode = node.parent; logNode != null; logNode = logNode.parent) {
                final LoggerWeakRef loggerRef2 = logNode.loggerRef;
                if (loggerRef2 != null) {
                    logger2 = ((Reference<__Null>)loggerRef2).get();
                    if (logger2 != null) {
                        break;
                    }
                }
            }
            if (logger2 != null) {
                doSetParent(logger, logger2);
            }
            node.walkAndSetParent(logger);
            loggerRef.setNode(node);
            return true;
        }
        
        synchronized void removeLoggerRef(final String s, final LoggerWeakRef loggerWeakRef) {
            this.namedLoggers.remove(s, loggerWeakRef);
        }
        
        synchronized Enumeration<String> getLoggerNames() {
            this.ensureInitialized();
            return this.namedLoggers.keys();
        }
        
        private void processParentHandlers(final Logger logger, final String s) {
            final LogManager owner = this.getOwner();
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    if (logger != owner.rootLogger && !owner.getBooleanProperty(s + ".useParentHandlers", true)) {
                        logger.setUseParentHandlers(false);
                    }
                    return null;
                }
            });
            int n = 1;
            while (true) {
                final int index = s.indexOf(".", n);
                if (index < 0) {
                    break;
                }
                final String substring = s.substring(0, index);
                if (owner.getProperty(substring + ".level") != null || owner.getProperty(substring + ".handlers") != null) {
                    this.demandLogger(substring, null);
                }
                n = index + 1;
            }
        }
        
        LogNode getNode(String substring) {
            if (substring == null || substring.equals("")) {
                return this.root;
            }
            LogNode root = this.root;
            while (substring.length() > 0) {
                final int index = substring.indexOf(".");
                String substring2;
                if (index > 0) {
                    substring2 = substring.substring(0, index);
                    substring = substring.substring(index + 1);
                }
                else {
                    substring2 = substring;
                    substring = "";
                }
                if (root.children == null) {
                    root.children = new HashMap<String, LogNode>();
                }
                LogNode logNode = root.children.get(substring2);
                if (logNode == null) {
                    logNode = new LogNode(root, this);
                    root.children.put(substring2, logNode);
                }
                root = logNode;
            }
            return root;
        }
    }
    
    private final class RootLogger extends Logger
    {
        private RootLogger() {
            super("", null, null, LogManager.this, true);
        }
        
        @Override
        public void log(final LogRecord logRecord) {
            LogManager.this.initializeGlobalHandlers();
            super.log(logRecord);
        }
        
        @Override
        public void addHandler(final Handler handler) {
            LogManager.this.initializeGlobalHandlers();
            super.addHandler(handler);
        }
        
        @Override
        public void removeHandler(final Handler handler) {
            LogManager.this.initializeGlobalHandlers();
            super.removeHandler(handler);
        }
        
        @Override
        Handler[] accessCheckedHandlers() {
            LogManager.this.initializeGlobalHandlers();
            return super.accessCheckedHandlers();
        }
    }
    
    final class SystemLoggerContext extends LoggerContext
    {
        @Override
        Logger demandLogger(final String s, final String s2) {
            Logger logger = this.findLogger(s);
            if (logger == null) {
                final Logger logger2 = new Logger(s, s2, null, this.getOwner(), true);
                do {
                    if (this.addLocalLogger(logger2)) {
                        logger = logger2;
                    }
                    else {
                        logger = this.findLogger(s);
                    }
                } while (logger == null);
            }
            return logger;
        }
    }
}
