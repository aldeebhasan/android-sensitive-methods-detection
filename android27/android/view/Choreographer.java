package android.view;

public final class Choreographer
{
    Choreographer() {
        throw new RuntimeException("Stub!");
    }
    
    public static Choreographer getInstance() {
        throw new RuntimeException("Stub!");
    }
    
    public void postFrameCallback(final FrameCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void postFrameCallbackDelayed(final FrameCallback callback, final long delayMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeFrameCallback(final FrameCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public interface FrameCallback
    {
        void doFrame(final long p0);
    }
}
