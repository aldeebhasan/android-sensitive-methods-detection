package java.net;

import java.security.*;

public final class DatagramPacket
{
    byte[] buf;
    int offset;
    int length;
    int bufLength;
    InetAddress address;
    int port;
    
    public DatagramPacket(final byte[] array, final int n, final int n2) {
        this.setData(array, n, n2);
        this.address = null;
        this.port = -1;
    }
    
    public DatagramPacket(final byte[] array, final int n) {
        this(array, 0, n);
    }
    
    public DatagramPacket(final byte[] array, final int n, final int n2, final InetAddress address, final int port) {
        this.setData(array, n, n2);
        this.setAddress(address);
        this.setPort(port);
    }
    
    public DatagramPacket(final byte[] array, final int n, final int n2, final SocketAddress socketAddress) {
        this.setData(array, n, n2);
        this.setSocketAddress(socketAddress);
    }
    
    public DatagramPacket(final byte[] array, final int n, final InetAddress inetAddress, final int n2) {
        this(array, 0, n, inetAddress, n2);
    }
    
    public DatagramPacket(final byte[] array, final int n, final SocketAddress socketAddress) {
        this(array, 0, n, socketAddress);
    }
    
    public synchronized InetAddress getAddress() {
        return this.address;
    }
    
    public synchronized int getPort() {
        return this.port;
    }
    
    public synchronized byte[] getData() {
        return this.buf;
    }
    
    public synchronized int getOffset() {
        return this.offset;
    }
    
    public synchronized int getLength() {
        return this.length;
    }
    
    public synchronized void setData(final byte[] buf, final int offset, final int n) {
        if (n < 0 || offset < 0 || n + offset < 0 || n + offset > buf.length) {
            throw new IllegalArgumentException("illegal length or offset");
        }
        this.buf = buf;
        this.length = n;
        this.bufLength = n;
        this.offset = offset;
    }
    
    public synchronized void setAddress(final InetAddress address) {
        this.address = address;
    }
    
    public synchronized void setPort(final int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Port out of range:" + port);
        }
        this.port = port;
    }
    
    public synchronized void setSocketAddress(final SocketAddress socketAddress) {
        if (socketAddress == null || !(socketAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("unsupported address type");
        }
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        if (inetSocketAddress.isUnresolved()) {
            throw new IllegalArgumentException("unresolved address");
        }
        this.setAddress(inetSocketAddress.getAddress());
        this.setPort(inetSocketAddress.getPort());
    }
    
    public synchronized SocketAddress getSocketAddress() {
        return new InetSocketAddress(this.getAddress(), this.getPort());
    }
    
    public synchronized void setData(final byte[] buf) {
        if (buf == null) {
            throw new NullPointerException("null packet buffer");
        }
        this.buf = buf;
        this.offset = 0;
        this.length = buf.length;
        this.bufLength = buf.length;
    }
    
    public synchronized void setLength(final int length) {
        if (length + this.offset > this.buf.length || length < 0 || length + this.offset < 0) {
            throw new IllegalArgumentException("illegal length");
        }
        this.length = length;
        this.bufLength = this.length;
    }
    
    private static native void init();
    
    static {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                System.loadLibrary("net");
                return null;
            }
        });
        init();
    }
}
