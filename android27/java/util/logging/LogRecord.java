package java.util.logging;

import java.util.concurrent.atomic.*;
import java.io.*;
import java.util.*;
import sun.misc.*;

public class LogRecord implements Serializable
{
    private static final AtomicLong globalSequenceNumber;
    private static final int MIN_SEQUENTIAL_THREAD_ID = 1073741823;
    private static final AtomicInteger nextThreadId;
    private static final ThreadLocal<Integer> threadIds;
    private Level level;
    private long sequenceNumber;
    private String sourceClassName;
    private String sourceMethodName;
    private String message;
    private int threadID;
    private long millis;
    private Throwable thrown;
    private String loggerName;
    private String resourceBundleName;
    private transient boolean needToInferCaller;
    private transient Object[] parameters;
    private transient ResourceBundle resourceBundle;
    private static final long serialVersionUID = 5372048053134512534L;
    
    private int defaultThreadID() {
        final long id = Thread.currentThread().getId();
        if (id < 1073741823L) {
            return (int)id;
        }
        Integer value = LogRecord.threadIds.get();
        if (value == null) {
            value = LogRecord.nextThreadId.getAndIncrement();
            LogRecord.threadIds.set(value);
        }
        return value;
    }
    
    public LogRecord(final Level level, final String message) {
        level.getClass();
        this.level = level;
        this.message = message;
        this.sequenceNumber = LogRecord.globalSequenceNumber.getAndIncrement();
        this.threadID = this.defaultThreadID();
        this.millis = System.currentTimeMillis();
        this.needToInferCaller = true;
    }
    
    public String getLoggerName() {
        return this.loggerName;
    }
    
    public void setLoggerName(final String loggerName) {
        this.loggerName = loggerName;
    }
    
    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }
    
    public void setResourceBundle(final ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
    
    public String getResourceBundleName() {
        return this.resourceBundleName;
    }
    
    public void setResourceBundleName(final String resourceBundleName) {
        this.resourceBundleName = resourceBundleName;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public void setLevel(final Level level) {
        if (level == null) {
            throw new NullPointerException();
        }
        this.level = level;
    }
    
    public long getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(final long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    public String getSourceClassName() {
        if (this.needToInferCaller) {
            this.inferCaller();
        }
        return this.sourceClassName;
    }
    
    public void setSourceClassName(final String sourceClassName) {
        this.sourceClassName = sourceClassName;
        this.needToInferCaller = false;
    }
    
    public String getSourceMethodName() {
        if (this.needToInferCaller) {
            this.inferCaller();
        }
        return this.sourceMethodName;
    }
    
    public void setSourceMethodName(final String sourceMethodName) {
        this.sourceMethodName = sourceMethodName;
        this.needToInferCaller = false;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public Object[] getParameters() {
        return this.parameters;
    }
    
    public void setParameters(final Object[] parameters) {
        this.parameters = parameters;
    }
    
    public int getThreadID() {
        return this.threadID;
    }
    
    public void setThreadID(final int threadID) {
        this.threadID = threadID;
    }
    
    public long getMillis() {
        return this.millis;
    }
    
    public void setMillis(final long millis) {
        this.millis = millis;
    }
    
    public Throwable getThrown() {
        return this.thrown;
    }
    
    public void setThrown(final Throwable thrown) {
        this.thrown = thrown;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeByte(1);
        objectOutputStream.writeByte(0);
        if (this.parameters == null) {
            objectOutputStream.writeInt(-1);
            return;
        }
        objectOutputStream.writeInt(this.parameters.length);
        for (int i = 0; i < this.parameters.length; ++i) {
            if (this.parameters[i] == null) {
                objectOutputStream.writeObject(null);
            }
            else {
                objectOutputStream.writeObject(this.parameters[i].toString());
            }
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final byte byte1 = objectInputStream.readByte();
        final byte byte2 = objectInputStream.readByte();
        if (byte1 != 1) {
            throw new IOException("LogRecord: bad version: " + byte1 + "." + byte2);
        }
        final int int1 = objectInputStream.readInt();
        if (int1 < -1) {
            throw new NegativeArraySizeException();
        }
        if (int1 == -1) {
            this.parameters = null;
        }
        else if (int1 < 255) {
            this.parameters = new Object[int1];
            for (int i = 0; i < this.parameters.length; ++i) {
                this.parameters[i] = objectInputStream.readObject();
            }
        }
        else {
            final ArrayList list = new ArrayList<Object>(Math.min(int1, 1024));
            for (int j = 0; j < int1; ++j) {
                list.add(objectInputStream.readObject());
            }
            this.parameters = list.toArray(new Object[list.size()]);
        }
        if (this.resourceBundleName != null) {
            try {
                this.resourceBundle = ResourceBundle.getBundle(this.resourceBundleName, Locale.getDefault(), ClassLoader.getSystemClassLoader());
            }
            catch (MissingResourceException ex) {
                this.resourceBundle = null;
            }
        }
        this.needToInferCaller = false;
    }
    
    private void inferCaller() {
        this.needToInferCaller = false;
        final JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
        final Throwable t = new Throwable();
        final int stackTraceDepth = javaLangAccess.getStackTraceDepth(t);
        int n = 1;
        for (int i = 0; i < stackTraceDepth; ++i) {
            final StackTraceElement stackTraceElement = javaLangAccess.getStackTraceElement(t, i);
            final String className = stackTraceElement.getClassName();
            final boolean loggerImplFrame = this.isLoggerImplFrame(className);
            if (n != 0) {
                if (loggerImplFrame) {
                    n = 0;
                }
            }
            else if (!loggerImplFrame && !className.startsWith("java.lang.reflect.") && !className.startsWith("sun.reflect.")) {
                this.setSourceClassName(className);
                this.setSourceMethodName(stackTraceElement.getMethodName());
                return;
            }
        }
    }
    
    private boolean isLoggerImplFrame(final String s) {
        return s.equals("java.util.logging.Logger") || s.startsWith("java.util.logging.LoggingProxyImpl") || s.startsWith("sun.util.logging.");
    }
    
    static {
        globalSequenceNumber = new AtomicLong(0L);
        nextThreadId = new AtomicInteger(1073741823);
        threadIds = new ThreadLocal<Integer>();
    }
}
