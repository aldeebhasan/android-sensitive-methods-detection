package java.util.logging;

import java.io.*;

public class ConsoleHandler extends StreamHandler
{
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
    
    public ConsoleHandler() {
        this.sealed = false;
        this.configure();
        this.setOutputStream(System.err);
        this.sealed = true;
    }
    
    @Override
    public void publish(final LogRecord logRecord) {
        super.publish(logRecord);
        this.flush();
    }
    
    @Override
    public void close() {
        this.flush();
    }
}
