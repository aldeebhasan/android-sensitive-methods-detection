package android.location;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStarted() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStopped() {
        throw new RuntimeException("Stub!");
    }
    
    public void onFirstFix(final int ttffMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSatelliteStatusChanged(final GnssStatus status) {
        throw new RuntimeException("Stub!");
    }
}
