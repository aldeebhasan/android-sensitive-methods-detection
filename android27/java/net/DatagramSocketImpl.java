package java.net;

import java.io.*;

public abstract class DatagramSocketImpl implements SocketOptions
{
    protected int localPort;
    protected FileDescriptor fd;
    DatagramSocket socket;
    
    int dataAvailable() {
        return 0;
    }
    
    void setDatagramSocket(final DatagramSocket socket) {
        this.socket = socket;
    }
    
    DatagramSocket getDatagramSocket() {
        return this.socket;
    }
    
    protected abstract void create() throws SocketException;
    
    protected abstract void bind(final int p0, final InetAddress p1) throws SocketException;
    
    protected abstract void send(final DatagramPacket p0) throws IOException;
    
    protected void connect(final InetAddress inetAddress, final int n) throws SocketException {
    }
    
    protected void disconnect() {
    }
    
    protected abstract int peek(final InetAddress p0) throws IOException;
    
    protected abstract int peekData(final DatagramPacket p0) throws IOException;
    
    protected abstract void receive(final DatagramPacket p0) throws IOException;
    
    @Deprecated
    protected abstract void setTTL(final byte p0) throws IOException;
    
    @Deprecated
    protected abstract byte getTTL() throws IOException;
    
    protected abstract void setTimeToLive(final int p0) throws IOException;
    
    protected abstract int getTimeToLive() throws IOException;
    
    protected abstract void join(final InetAddress p0) throws IOException;
    
    protected abstract void leave(final InetAddress p0) throws IOException;
    
    protected abstract void joinGroup(final SocketAddress p0, final NetworkInterface p1) throws IOException;
    
    protected abstract void leaveGroup(final SocketAddress p0, final NetworkInterface p1) throws IOException;
    
    protected abstract void close();
    
    protected int getLocalPort() {
        return this.localPort;
    }
    
     <T> void setOption(final SocketOption<T> socketOption, final T t) throws IOException {
        if (socketOption == StandardSocketOptions.SO_SNDBUF) {
            this.setOption(4097, t);
        }
        else if (socketOption == StandardSocketOptions.SO_RCVBUF) {
            this.setOption(4098, t);
        }
        else if (socketOption == StandardSocketOptions.SO_REUSEADDR) {
            this.setOption(4, t);
        }
        else if (socketOption == StandardSocketOptions.IP_TOS) {
            this.setOption(3, t);
        }
        else if (socketOption == StandardSocketOptions.IP_MULTICAST_IF && this.getDatagramSocket() instanceof MulticastSocket) {
            this.setOption(31, t);
        }
        else if (socketOption == StandardSocketOptions.IP_MULTICAST_TTL && this.getDatagramSocket() instanceof MulticastSocket) {
            if (!(t instanceof Integer)) {
                throw new IllegalArgumentException("not an integer");
            }
            this.setTimeToLive((int)t);
        }
        else {
            if (socketOption != StandardSocketOptions.IP_MULTICAST_LOOP || !(this.getDatagramSocket() instanceof MulticastSocket)) {
                throw new UnsupportedOperationException("unsupported option");
            }
            this.setOption(18, t);
        }
    }
    
     <T> T getOption(final SocketOption<T> socketOption) throws IOException {
        if (socketOption == StandardSocketOptions.SO_SNDBUF) {
            return (T)this.getOption(4097);
        }
        if (socketOption == StandardSocketOptions.SO_RCVBUF) {
            return (T)this.getOption(4098);
        }
        if (socketOption == StandardSocketOptions.SO_REUSEADDR) {
            return (T)this.getOption(4);
        }
        if (socketOption == StandardSocketOptions.IP_TOS) {
            return (T)this.getOption(3);
        }
        if (socketOption == StandardSocketOptions.IP_MULTICAST_IF && this.getDatagramSocket() instanceof MulticastSocket) {
            return (T)this.getOption(31);
        }
        if (socketOption == StandardSocketOptions.IP_MULTICAST_TTL && this.getDatagramSocket() instanceof MulticastSocket) {
            return (T)this.getTimeToLive();
        }
        if (socketOption == StandardSocketOptions.IP_MULTICAST_LOOP && this.getDatagramSocket() instanceof MulticastSocket) {
            return (T)this.getOption(18);
        }
        throw new UnsupportedOperationException("unsupported option");
    }
    
    protected FileDescriptor getFileDescriptor() {
        return this.fd;
    }
}
