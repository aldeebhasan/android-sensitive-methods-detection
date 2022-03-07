package android.view.accessibility;

import java.util.*;
import android.content.pm.*;
import android.accessibilityservice.*;
import android.os.*;

public final class AccessibilityManager
{
    AccessibilityManager() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isTouchExplorationEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendAccessibilityEvent(final AccessibilityEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void interrupt() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public List<ServiceInfo> getAccessibilityServiceList() {
        throw new RuntimeException("Stub!");
    }
    
    public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList() {
        throw new RuntimeException("Stub!");
    }
    
    public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(final int feedbackTypeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean addAccessibilityStateChangeListener(final AccessibilityStateChangeListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void addAccessibilityStateChangeListener(final AccessibilityStateChangeListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeAccessibilityStateChangeListener(final AccessibilityStateChangeListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean addTouchExplorationStateChangeListener(final TouchExplorationStateChangeListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void addTouchExplorationStateChangeListener(final TouchExplorationStateChangeListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeTouchExplorationStateChangeListener(final TouchExplorationStateChangeListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void addAccessibilityRequestPreparer(final AccessibilityRequestPreparer preparer) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeAccessibilityRequestPreparer(final AccessibilityRequestPreparer preparer) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isAccessibilityButtonSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public interface TouchExplorationStateChangeListener
    {
        void onTouchExplorationStateChanged(final boolean p0);
    }
    
    public interface AccessibilityStateChangeListener
    {
        void onAccessibilityStateChanged(final boolean p0);
    }
}
