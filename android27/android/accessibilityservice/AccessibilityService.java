package android.accessibilityservice;

import android.app.*;
import android.view.*;
import java.util.*;
import android.view.accessibility.*;
import android.content.*;
import android.os.*;
import android.graphics.*;

public abstract class AccessibilityService extends Service
{
    public static final int GESTURE_SWIPE_DOWN = 2;
    public static final int GESTURE_SWIPE_DOWN_AND_LEFT = 15;
    public static final int GESTURE_SWIPE_DOWN_AND_RIGHT = 16;
    public static final int GESTURE_SWIPE_DOWN_AND_UP = 8;
    public static final int GESTURE_SWIPE_LEFT = 3;
    public static final int GESTURE_SWIPE_LEFT_AND_DOWN = 10;
    public static final int GESTURE_SWIPE_LEFT_AND_RIGHT = 5;
    public static final int GESTURE_SWIPE_LEFT_AND_UP = 9;
    public static final int GESTURE_SWIPE_RIGHT = 4;
    public static final int GESTURE_SWIPE_RIGHT_AND_DOWN = 12;
    public static final int GESTURE_SWIPE_RIGHT_AND_LEFT = 6;
    public static final int GESTURE_SWIPE_RIGHT_AND_UP = 11;
    public static final int GESTURE_SWIPE_UP = 1;
    public static final int GESTURE_SWIPE_UP_AND_DOWN = 7;
    public static final int GESTURE_SWIPE_UP_AND_LEFT = 13;
    public static final int GESTURE_SWIPE_UP_AND_RIGHT = 14;
    public static final int GLOBAL_ACTION_BACK = 1;
    public static final int GLOBAL_ACTION_HOME = 2;
    public static final int GLOBAL_ACTION_NOTIFICATIONS = 4;
    public static final int GLOBAL_ACTION_POWER_DIALOG = 6;
    public static final int GLOBAL_ACTION_QUICK_SETTINGS = 5;
    public static final int GLOBAL_ACTION_RECENTS = 3;
    public static final int GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN = 7;
    public static final String SERVICE_INTERFACE = "android.accessibilityservice.AccessibilityService";
    public static final String SERVICE_META_DATA = "android.accessibilityservice";
    public static final int SHOW_MODE_AUTO = 0;
    public static final int SHOW_MODE_HIDDEN = 1;
    
    public AccessibilityService() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onAccessibilityEvent(final AccessibilityEvent p0);
    
    public abstract void onInterrupt();
    
    protected void onServiceConnected() {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean onGesture(final int gestureId) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean onKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public List<AccessibilityWindowInfo> getWindows() {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getRootInActiveWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public final void disableSelf() {
        throw new RuntimeException("Stub!");
    }
    
    public final MagnificationController getMagnificationController() {
        throw new RuntimeException("Stub!");
    }
    
    public final FingerprintGestureController getFingerprintGestureController() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean dispatchGesture(final GestureDescription gesture, final GestureResultCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public final SoftKeyboardController getSoftKeyboardController() {
        throw new RuntimeException("Stub!");
    }
    
    public final AccessibilityButtonController getAccessibilityButtonController() {
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
    
    @Override
    public Object getSystemService(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public static final class MagnificationController
    {
        MagnificationController() {
            throw new RuntimeException("Stub!");
        }
        
        public void addListener(final OnMagnificationChangedListener listener) {
            throw new RuntimeException("Stub!");
        }
        
        public void addListener(final OnMagnificationChangedListener listener, final Handler handler) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean removeListener(final OnMagnificationChangedListener listener) {
            throw new RuntimeException("Stub!");
        }
        
        public float getScale() {
            throw new RuntimeException("Stub!");
        }
        
        public float getCenterX() {
            throw new RuntimeException("Stub!");
        }
        
        public float getCenterY() {
            throw new RuntimeException("Stub!");
        }
        
        public Region getMagnificationRegion() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean reset(final boolean animate) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean setScale(final float scale, final boolean animate) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean setCenter(final float centerX, final float centerY, final boolean animate) {
            throw new RuntimeException("Stub!");
        }
        
        public interface OnMagnificationChangedListener
        {
            void onMagnificationChanged(final MagnificationController p0, final Region p1, final float p2, final float p3, final float p4);
        }
    }
    
    public static final class SoftKeyboardController
    {
        SoftKeyboardController() {
            throw new RuntimeException("Stub!");
        }
        
        public void addOnShowModeChangedListener(final OnShowModeChangedListener listener) {
            throw new RuntimeException("Stub!");
        }
        
        public void addOnShowModeChangedListener(final OnShowModeChangedListener listener, final Handler handler) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean removeOnShowModeChangedListener(final OnShowModeChangedListener listener) {
            throw new RuntimeException("Stub!");
        }
        
        public int getShowMode() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean setShowMode(final int showMode) {
            throw new RuntimeException("Stub!");
        }
        
        public interface OnShowModeChangedListener
        {
            void onShowModeChanged(final SoftKeyboardController p0, final int p1);
        }
    }
    
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
}
