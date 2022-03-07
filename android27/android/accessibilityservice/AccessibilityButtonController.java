package android.accessibilityservice;

import android.os.*;

public final class AccessibilityButtonController
{
    AccessibilityButtonController() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAccessibilityButtonAvailable() {
        throw new RuntimeException("Stub!");
    }
    
    public void registerAccessibilityButtonCallback(final AccessibilityButtonCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerAccessibilityButtonCallback(final AccessibilityButtonCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterAccessibilityButtonCallback(final AccessibilityButtonCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
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
}
