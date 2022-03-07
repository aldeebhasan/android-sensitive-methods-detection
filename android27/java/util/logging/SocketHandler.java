package java.util.logging;

import java.net.*;
import java.io.*;

public class SocketHandler extends StreamHandler
{
    private Socket sock;
    private String host;
    private int port;
    
    private void configure() {
        final LogManager logManager = LogManager.getLogManager();
        final String name = this.getClass().getName();
        this.setLevel(logManager.getLevelProperty(name + ".level", Level.ALL));
        this.setFilter(logManager.getFilterProperty(name + ".filter", null));
        this.setFormatter(logManager.getFormatterProperty(name + ".formatter", new XMLFormatter()));
        try {
            this.setEncoding(logManager.getStringProperty(name + ".encoding", null));
        }
        catch (Exception ex) {
            try {
                this.setEncoding(null);
            }
            catch (Exception ex2) {}
        }
        this.port = logManager.getIntProperty(name + ".port", 0);
        this.host = logManager.getStringProperty(name + ".host", null);
    }
    
    public SocketHandler() throws IOException {
        this.sealed = false;
        this.configure();
        try {
            this.connect();
        }
        catch (IOException ex) {
            System.err.println("SocketHandler: connect failed to " + this.host + ":" + this.port);
            throw ex;
        }
        this.sealed = true;
    }
    
    public SocketHandler(final String host, final int port) throws IOException {
        this.sealed = false;
        this.configure();
        this.sealed = true;
        this.port = port;
        this.host = host;
        this.connect();
    }
    
    private void connect() throws IOException {
        if (this.port == 0) {
            throw new IllegalArgumentException("Bad port: " + this.port);
        }
        if (this.host == null) {
            throw new IllegalArgumentException("Null host name: " + this.host);
        }
        this.sock = new Socket(this.host, this.port);
        this.setOutputStream(new BufferedOutputStream(this.sock.getOutputStream()));
    }
    
    @Override
    public synchronized void close() throws SecurityException {
        super.close();
        if (this.sock != null) {
            try {
                this.sock.close();
            }
            catch (IOException ex) {}
        }
        this.sock = null;
    }
    
    @Override
    public synchronized void publish(final LogRecord logRecord) {
        if (!this.isLoggable(logRecord)) {
            return;
        }
        super.publish(logRecord);
        this.flush();
    }
}
