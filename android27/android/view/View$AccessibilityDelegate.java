package android.view;

import android.os.*;
import android.view.accessibility.*;

public static class AccessibilityDelegate
{
    public AccessibilityDelegate() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendAccessibilityEvent(final View host, final int eventType) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean performAccessibilityAction(final View host, final int action, final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendAccessibilityEventUnchecked(final View host, final AccessibilityEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean dispatchPopulateAccessibilityEvent(final View host, final AccessibilityEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPopulateAccessibilityEvent(final View host, final AccessibilityEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onInitializeAccessibilityEvent(final View host, final AccessibilityEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onInitializeAccessibilityNodeInfo(final View host, final AccessibilityNodeInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    public void addExtraDataToAccessibilityNodeInfo(final View host, final AccessibilityNodeInfo info, final String extraDataKey, final Bundle arguments) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onRequestSendAccessibilityEvent(final ViewGroup host, final View child, final AccessibilityEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeProvider getAccessibilityNodeProvider(final View host) {
        throw new RuntimeException("Stub!");
    }
}
