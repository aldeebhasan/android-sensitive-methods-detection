package android.app;

import android.accessibilityservice.*;
import java.util.*;
import android.view.accessibility.*;
import java.util.concurrent.*;
import android.graphics.*;
import android.view.*;
import android.os.*;

public final class UiAutomation
{
    public static final int FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES = 1;
    public static final int ROTATION_FREEZE_0 = 0;
    public static final int ROTATION_FREEZE_180 = 2;
    public static final int ROTATION_FREEZE_270 = 3;
    public static final int ROTATION_FREEZE_90 = 1;
    public static final int ROTATION_FREEZE_CURRENT = -1;
    public static final int ROTATION_UNFREEZE = -2;
    
    UiAutomation() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnAccessibilityEventListener(final OnAccessibilityEventListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean performGlobalAction(final int action) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo findFocus(final int focus) {
        throw new RuntimeException("Stub!");
    }
    
    public final AccessibilityServiceInfo getServiceInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setServiceInfo(final AccessibilityServiceInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    public List<AccessibilityWindowInfo> getWindows() {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getRootInActiveWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean injectInputEvent(final InputEvent event, final boolean sync) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setRotation(final int rotation) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityEvent executeAndWaitForEvent(final Runnable command, final AccessibilityEventFilter filter, final long timeoutMillis) throws TimeoutException {
        throw new RuntimeException("Stub!");
    }
    
    public void waitForIdle(final long idleTimeoutMillis, final long globalTimeoutMillis) throws TimeoutException {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap takeScreenshot() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRunAsMonkey(final boolean enable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clearWindowContentFrameStats(final int windowId) {
        throw new RuntimeException("Stub!");
    }
    
    public WindowContentFrameStats getWindowContentFrameStats(final int windowId) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearWindowAnimationFrameStats() {
        throw new RuntimeException("Stub!");
    }
    
    public WindowAnimationFrameStats getWindowAnimationFrameStats() {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelFileDescriptor executeShellCommand(final String command) {
        throw new RuntimeException("Stub!");
    }
    
    public interface AccessibilityEventFilter
    {
        boolean accept(final AccessibilityEvent p0);
    }
    
    public interface OnAccessibilityEventListener
    {
        void onAccessibilityEvent(final AccessibilityEvent p0);
    }
}
