package java.util.logging;

import java.io.*;
import java.nio.charset.*;

public abstract class Handler
{
    private static final int offValue;
    private final LogManager manager;
    private volatile Filter filter;
    private volatile Formatter formatter;
    private volatile Level logLevel;
    private volatile ErrorManager errorManager;
    private volatile String encoding;
    boolean sealed;
    
    protected Handler() {
        this.manager = LogManager.getLogManager();
        this.logLevel = Level.ALL;
        this.errorManager = new ErrorManager();
        this.sealed = true;
    }
    
    public abstract void publish(final LogRecord p0);
    
    public abstract void flush();
    
    public abstract void close() throws SecurityException;
    
    public synchronized void setFormatter(final Formatter formatter) throws SecurityException {
        this.checkPermission();
        formatter.getClass();
        this.formatter = formatter;
    }
    
    public Formatter getFormatter() {
        return this.formatter;
    }
    
    public synchronized void setEncoding(final String encoding) throws SecurityException, UnsupportedEncodingException {
        this.checkPermission();
        if (encoding != null) {
            try {
                if (!Charset.isSupported(encoding)) {
                    throw new UnsupportedEncodingException(encoding);
                }
            }
            catch (IllegalCharsetNameException ex) {
                throw new UnsupportedEncodingException(encoding);
            }
        }
        this.encoding = encoding;
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public synchronized void setFilter(final Filter filter) throws SecurityException {
        this.checkPermission();
        this.filter = filter;
    }
    
    public Filter getFilter() {
        return this.filter;
    }
    
    public synchronized void setErrorManager(final ErrorManager errorManager) {
        this.checkPermission();
        if (errorManager == null) {
            throw new NullPointerException();
        }
        this.errorManager = errorManager;
    }
    
    public ErrorManager getErrorManager() {
        this.checkPermission();
        return this.errorManager;
    }
    
    protected void reportError(final String s, final Exception ex, final int n) {
        try {
            this.errorManager.error(s, ex, n);
        }
        catch (Exception ex2) {
            System.err.println("Handler.reportError caught:");
            ex2.printStackTrace();
        }
    }
    
    public synchronized void setLevel(final Level logLevel) throws SecurityException {
        if (logLevel == null) {
            throw new NullPointerException();
        }
        this.checkPermission();
        this.logLevel = logLevel;
    }
    
    public Level getLevel() {
        return this.logLevel;
    }
    
    public boolean isLoggable(final LogRecord logRecord) {
        final int intValue = this.getLevel().intValue();
        if (logRecord.getLevel().intValue() < intValue || intValue == Handler.offValue) {
            return false;
        }
        final Filter filter = this.getFilter();
        return filter == null || filter.isLoggable(logRecord);
    }
    
    void checkPermission() throws SecurityException {
        if (this.sealed) {
            this.manager.checkPermission();
        }
    }
    
    static {
        offValue = Level.OFF.intValue();
    }
}
