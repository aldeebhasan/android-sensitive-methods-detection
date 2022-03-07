package android.accessibilityservice;

public abstract static class AccessibilityButtonCallback
{
    public AccessibilityButtonCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onClicked(final AccessibilityButtonController controller) {
        throw new RuntimeException("Stub!");
    }
    
    public void onAvailabilityChanged(final AccessibilityButtonController controller, final boolean available) {
        throw new RuntimeException("Stub!");
    }
}
