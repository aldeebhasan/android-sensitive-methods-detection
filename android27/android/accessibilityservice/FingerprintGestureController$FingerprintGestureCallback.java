package android.accessibilityservice;

public abstract static class FingerprintGestureCallback
{
    public FingerprintGestureCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onGestureDetectionAvailabilityChanged(final boolean available) {
        throw new RuntimeException("Stub!");
    }
    
    public void onGestureDetected(final int gesture) {
        throw new RuntimeException("Stub!");
    }
}
