package java.util.logging;

public class MemoryHandler extends Handler
{
    private static final int DEFAULT_SIZE = 1000;
    private volatile Level pushLevel;
    private int size;
    private Handler target;
    private LogRecord[] buffer;
    int start;
    int count;
    
    private void configure() {
        final LogManager logManager = LogManager.getLogManager();
        final String name = this.getClass().getName();
        this.pushLevel = logManager.getLevelProperty(name + ".push", Level.SEVERE);
        this.size = logManager.getIntProperty(name + ".size", 1000);
        if (this.size <= 0) {
            this.size = 1000;
        }
        this.setLevel(logManager.getLevelProperty(name + ".level", Level.ALL));
        this.setFilter(logManager.getFilterProperty(name + ".filter", null));
        this.setFormatter(logManager.getFormatterProperty(name + ".formatter", new SimpleFormatter()));
    }
    
    public MemoryHandler() {
        this.sealed = false;
        this.configure();
        this.sealed = true;
        final LogManager logManager = LogManager.getLogManager();
        final String name = this.getClass().getName();
        final String property = logManager.getProperty(name + ".target");
        if (property == null) {
            throw new RuntimeException("The handler " + name + " does not specify a target");
        }
        try {
            this.target = (Handler)ClassLoader.getSystemClassLoader().loadClass(property).newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            final Throwable t;
            throw new RuntimeException("MemoryHandler can't load handler target \"" + property + "\"", t);
        }
        this.init();
    }
    
    private void init() {
        this.buffer = new LogRecord[this.size];
        this.start = 0;
        this.count = 0;
    }
    
    public MemoryHandler(final Handler target, final int size, final Level pushLevel) {
        if (target == null || pushLevel == null) {
            throw new NullPointerException();
        }
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
        this.sealed = false;
        this.configure();
        this.sealed = true;
        this.target = target;
        this.pushLevel = pushLevel;
        this.size = size;
        this.init();
    }
    
    @Override
    public synchronized void publish(final LogRecord logRecord) {
        if (!this.isLoggable(logRecord)) {
            return;
        }
        this.buffer[(this.start + this.count) % this.buffer.length] = logRecord;
        if (this.count < this.buffer.length) {
            ++this.count;
        }
        else {
            ++this.start;
            this.start %= this.buffer.length;
        }
        if (logRecord.getLevel().intValue() >= this.pushLevel.intValue()) {
            this.push();
        }
    }
    
    public synchronized void push() {
        for (int i = 0; i < this.count; ++i) {
            this.target.publish(this.buffer[(this.start + i) % this.buffer.length]);
        }
        this.start = 0;
        this.count = 0;
    }
    
    @Override
    public void flush() {
        this.target.flush();
    }
    
    @Override
    public void close() throws SecurityException {
        this.target.close();
        this.setLevel(Level.OFF);
    }
    
    public synchronized void setPushLevel(final Level pushLevel) throws SecurityException {
        if (pushLevel == null) {
            throw new NullPointerException();
        }
        this.checkPermission();
        this.pushLevel = pushLevel;
    }
    
    public Level getPushLevel() {
        return this.pushLevel;
    }
    
    @Override
    public boolean isLoggable(final LogRecord logRecord) {
        return super.isLoggable(logRecord);
    }
}
