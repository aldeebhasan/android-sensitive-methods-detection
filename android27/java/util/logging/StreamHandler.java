package java.util.logging;

import java.io.*;

public class StreamHandler extends Handler
{
    private OutputStream output;
    private boolean doneHeader;
    private volatile Writer writer;
    
    private void configure() {
        final LogManager logManager = LogManager.getLogManager();
        final String name = this.getClass().getName();
        this.setLevel(logManager.getLevelProperty(name + ".level", Level.INFO));
        this.setFilter(logManager.getFilterProperty(name + ".filter", null));
        this.setFormatter(logManager.getFormatterProperty(name + ".formatter", new SimpleFormatter()));
        try {
            this.setEncoding(logManager.getStringProperty(name + ".encoding", null));
        }
        catch (Exception ex) {
            try {
                this.setEncoding(null);
            }
            catch (Exception ex2) {}
        }
    }
    
    public StreamHandler() {
        this.sealed = false;
        this.configure();
        this.sealed = true;
    }
    
    public StreamHandler(final OutputStream outputStream, final Formatter formatter) {
        this.sealed = false;
        this.configure();
        this.setFormatter(formatter);
        this.setOutputStream(outputStream);
        this.sealed = true;
    }
    
    protected synchronized void setOutputStream(final OutputStream output) throws SecurityException {
        if (output == null) {
            throw new NullPointerException();
        }
        this.flushAndClose();
        this.output = output;
        this.doneHeader = false;
        final String encoding = this.getEncoding();
        if (encoding == null) {
            this.writer = new OutputStreamWriter(this.output);
        }
        else {
            try {
                this.writer = new OutputStreamWriter(this.output, encoding);
            }
            catch (UnsupportedEncodingException ex) {
                throw new Error("Unexpected exception " + ex);
            }
        }
    }
    
    @Override
    public synchronized void setEncoding(final String encoding) throws SecurityException, UnsupportedEncodingException {
        super.setEncoding(encoding);
        if (this.output == null) {
            return;
        }
        this.flush();
        if (encoding == null) {
            this.writer = new OutputStreamWriter(this.output);
        }
        else {
            this.writer = new OutputStreamWriter(this.output, encoding);
        }
    }
    
    @Override
    public synchronized void publish(final LogRecord logRecord) {
        if (!this.isLoggable(logRecord)) {
            return;
        }
        String format;
        try {
            format = this.getFormatter().format(logRecord);
        }
        catch (Exception ex) {
            this.reportError(null, ex, 5);
            return;
        }
        try {
            if (!this.doneHeader) {
                this.writer.write(this.getFormatter().getHead(this));
                this.doneHeader = true;
            }
            this.writer.write(format);
        }
        catch (Exception ex2) {
            this.reportError(null, ex2, 1);
        }
    }
    
    @Override
    public boolean isLoggable(final LogRecord logRecord) {
        return this.writer != null && logRecord != null && super.isLoggable(logRecord);
    }
    
    @Override
    public synchronized void flush() {
        if (this.writer != null) {
            try {
                this.writer.flush();
            }
            catch (Exception ex) {
                this.reportError(null, ex, 2);
            }
        }
    }
    
    private synchronized void flushAndClose() throws SecurityException {
        this.checkPermission();
        if (this.writer != null) {
            try {
                if (!this.doneHeader) {
                    this.writer.write(this.getFormatter().getHead(this));
                    this.doneHeader = true;
                }
                this.writer.write(this.getFormatter().getTail(this));
                this.writer.flush();
                this.writer.close();
            }
            catch (Exception ex) {
                this.reportError(null, ex, 3);
            }
            this.writer = null;
            this.output = null;
        }
    }
    
    @Override
    public synchronized void close() throws SecurityException {
        this.flushAndClose();
    }
}
