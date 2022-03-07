package android.net.rtp;

public class AudioGroup
{
    public static final int MODE_ECHO_SUPPRESSION = 3;
    public static final int MODE_MUTED = 1;
    public static final int MODE_NORMAL = 2;
    public static final int MODE_ON_HOLD = 0;
    
    public AudioGroup() {
        throw new RuntimeException("Stub!");
    }
    
    public AudioStream[] getStreams() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendDtmf(final int event) {
        throw new RuntimeException("Stub!");
    }
    
    public void clear() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
