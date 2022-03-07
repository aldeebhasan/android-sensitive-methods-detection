package android.service.voice;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onAvailabilityChanged(final int p0);
    
    public abstract void onDetected(final EventPayload p0);
    
    public abstract void onError();
    
    public abstract void onRecognitionPaused();
    
    public abstract void onRecognitionResumed();
}
