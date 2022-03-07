package android.media.projection;

import android.os.*;
import android.view.*;
import android.hardware.display.*;

public final class MediaProjection
{
    MediaProjection() {
        throw new RuntimeException("Stub!");
    }
    
    public void registerCallback(final Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterCallback(final Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public VirtualDisplay createVirtualDisplay(final String name, final int width, final int height, final int dpi, final int flags, final Surface surface, final VirtualDisplay.Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void stop() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onStop() {
            throw new RuntimeException("Stub!");
        }
    }
}
