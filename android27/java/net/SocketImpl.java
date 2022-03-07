package java.net;

import java.io.*;

public abstract class SocketImpl implements SocketOptions
{
    Socket socket;
    ServerSocket serverSocket;
    protected FileDescriptor fd;
    protected InetAddress address;
    protected int port;
    protected int localport;
    
    public SocketImpl() {
        this.socket = null;
        this.serverSocket = null;
    }
    
    protected abstract void create(final boolean p0) throws IOException;
    
    protected abstract void connect(final String p0, final int p1) throws IOException;
    
    protected abstract void connect(final InetAddress p0, final int p1) throws IOException;
    
    protected abstract void connect(final SocketAddress p0, final int p1) throws IOException;
    
    protected abstract void bind(final InetAddress p0, final int p1) throws IOException;
    
    protected abstract void listen(final int p0) throws IOException;
    
    protected abstract void accept(final SocketImpl p0) throws IOException;
    
    protected abstract InputStream getInputStream() throws IOException;
    
    protected abstract OutputStream getOutputStream() throws IOException;
    
    protected abstract int available() throws IOException;
    
    protected abstract void close() throws IOException;
    
    protected void shutdownInput() throws IOException {
        throw new IOException("Method not implemented!");
    }
    
    protected void shutdownOutput() throws IOException {
        throw new IOException("Method not implemented!");
    }
    
    protected FileDescriptor getFileDescriptor() {
        return this.fd;
    }
    
    protected InetAddress getInetAddress() {
        return this.address;
    }
    
    protected int getPort() {
        return this.port;
    }
    
    protected boolean supportsUrgentData() {
        return false;
    }
    
    protected abstract void sendUrgentData(final int p0) throws IOException;
    
    protected int getLocalPort() {
        return this.localport;
    }
    
    void setSocket(final Socket socket) {
        this.socket = socket;
    }
    
    Socket getSocket() {
        return this.socket;
    }
    
    void setServerSocket(final ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    ServerSocket getServerSocket() {
        return this.serverSocket;
    }
    
    @Override
    public String toString() {
        return "Socket[addr=" + this.getInetAddress() + ",port=" + this.getPort() + ",localport=" + this.getLocalPort() + "]";
    }
    
    void reset() throws IOException {
        this.address = null;
        this.port = 0;
        this.localport = 0;
    }
    
    protected void setPerformancePreferences(final int n, final int n2, final int n3) {
    }
    
     <T> void setOption(final SocketOption<T> socketOption, final T t) throws IOException {
        if (socketOption == StandardSocketOptions.SO_KEEPALIVE) {
            this.setOption(8, t);
        }
        else if (socketOption == StandardSocketOptions.SO_SNDBUF) {
            this.setOption(4097, t);
        }
        else if (socketOption == StandardSocketOptions.SO_RCVBUF) {
            this.setOption(4098, t);
        }
        else if (socketOption == StandardSocketOptions.SO_REUSEADDR) {
            this.setOption(4, t);
        }
        else if (socketOption == StandardSocketOptions.SO_LINGER) {
            this.setOption(128, t);
        }
        else if (socketOption == StandardSocketOptions.IP_TOS) {
            this.setOption(3, t);
        }
        else {
            if (socketOption != StandardSocketOptions.TCP_NODELAY) {
                throw new UnsupportedOperationException("unsupported option");
            }
            this.setOption(1, t);
        }
    }
    
     <T> T getOption(final SocketOption<T> socketOption) throws IOException {
        if (socketOption == StandardSocketOptions.SO_KEEPALIVE) {
            return (T)this.getOption(8);
        }
        if (socketOption == StandardSocketOptions.SO_SNDBUF) {
            return (T)this.getOption(4097);
        }
        if (socketOption == StandardSocketOptions.SO_RCVBUF) {
            return (T)this.getOption(4098);
        }
        if (socketOption == StandardSocketOptions.SO_REUSEADDR) {
            return (T)this.getOption(4);
        }
        if (socketOption == StandardSocketOptions.SO_LINGER) {
            return (T)this.getOption(128);
        }
        if (socketOption == StandardSocketOptions.IP_TOS) {
            return (T)this.getOption(3);
        }
        if (socketOption == StandardSocketOptions.TCP_NODELAY) {
            return (T)this.getOption(1);
        }
        throw new UnsupportedOperationException("unsupported option");
    }
}
