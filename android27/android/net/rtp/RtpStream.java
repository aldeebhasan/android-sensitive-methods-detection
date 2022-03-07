package android.net.rtp;

import java.net.*;

public class RtpStream
{
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RECEIVE_ONLY = 2;
    public static final int MODE_SEND_ONLY = 1;
    
    RtpStream() {
        throw new RuntimeException("Stub!");
    }
    
    public InetAddress getLocalAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLocalPort() {
        throw new RuntimeException("Stub!");
    }
    
    public InetAddress getRemoteAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRemotePort() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isBusy() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public void associate(final InetAddress address, final int port) {
        throw new RuntimeException("Stub!");
    }
    
    public void release() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
