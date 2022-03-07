package android.net.rtp;

import java.net.*;

public class AudioStream extends RtpStream
{
    public AudioStream(final InetAddress address) throws SocketException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final boolean isBusy() {
        throw new RuntimeException("Stub!");
    }
    
    public AudioGroup getGroup() {
        throw new RuntimeException("Stub!");
    }
    
    public void join(final AudioGroup group) {
        throw new RuntimeException("Stub!");
    }
    
    public AudioCodec getCodec() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCodec(final AudioCodec codec) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDtmfType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDtmfType(final int type) {
        throw new RuntimeException("Stub!");
    }
}
