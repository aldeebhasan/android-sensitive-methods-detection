package android.hardware.display;

import android.os.*;
import android.view.*;

public final class DisplayManager
{
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    public static final int VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR = 16;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY = 8;
    public static final int VIRTUAL_DISPLAY_FLAG_PRESENTATION = 2;
    public static final int VIRTUAL_DISPLAY_FLAG_PUBLIC = 1;
    public static final int VIRTUAL_DISPLAY_FLAG_SECURE = 4;
    
    DisplayManager() {
        throw new RuntimeException("Stub!");
    }
    
    public Display getDisplay(final int displayId) {
        throw new RuntimeException("Stub!");
    }
    
    public Display[] getDisplays() {
        throw new RuntimeException("Stub!");
    }
    
    public Display[] getDisplays(final String category) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerDisplayListener(final DisplayListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterDisplayListener(final DisplayListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public VirtualDisplay createVirtualDisplay(final String name, final int width, final int height, final int densityDpi, final Surface surface, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public VirtualDisplay createVirtualDisplay(final String name, final int width, final int height, final int densityDpi, final Surface surface, final int flags, final VirtualDisplay.Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public interface DisplayListener
    {
        void onDisplayAdded(final int p0);
        
        void onDisplayRemoved(final int p0);
        
        void onDisplayChanged(final int p0);
    }
}
