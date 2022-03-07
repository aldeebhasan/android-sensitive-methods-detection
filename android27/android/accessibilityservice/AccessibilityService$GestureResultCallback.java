package android.accessibilityservice;

public abstract static class GestureResultCallback
{
    public GestureResultCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCompleted(final GestureDescription gestureDescription) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCancelled(final GestureDescription gestureDescription) {
        throw new RuntimeException("Stub!");
    }
}
