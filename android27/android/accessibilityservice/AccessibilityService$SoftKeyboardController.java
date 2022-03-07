package android.accessibilityservice;

import android.os.*;

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
