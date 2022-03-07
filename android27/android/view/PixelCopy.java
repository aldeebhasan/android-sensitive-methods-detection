package android.view;

import android.os.*;
import android.graphics.*;

public final class PixelCopy
{
    public static final int ERROR_DESTINATION_INVALID = 5;
    public static final int ERROR_SOURCE_INVALID = 4;
    public static final int ERROR_SOURCE_NO_DATA = 3;
    public static final int ERROR_TIMEOUT = 2;
    public static final int ERROR_UNKNOWN = 1;
    public static final int SUCCESS = 0;
    
    PixelCopy() {
        throw new RuntimeException("Stub!");
    }
    
    public static void request(final SurfaceView source, final Bitmap dest, final OnPixelCopyFinishedListener listener, final Handler listenerThread) {
        throw new RuntimeException("Stub!");
    }
    
    public static void request(final SurfaceView source, final Rect srcRect, final Bitmap dest, final OnPixelCopyFinishedListener listener, final Handler listenerThread) {
        throw new RuntimeException("Stub!");
    }
    
    public static void request(final Surface source, final Bitmap dest, final OnPixelCopyFinishedListener listener, final Handler listenerThread) {
        throw new RuntimeException("Stub!");
    }
    
    public static void request(final Surface source, final Rect srcRect, final Bitmap dest, final OnPixelCopyFinishedListener listener, final Handler listenerThread) {
        throw new RuntimeException("Stub!");
    }
    
    public static void request(final Window source, final Bitmap dest, final OnPixelCopyFinishedListener listener, final Handler listenerThread) {
        throw new RuntimeException("Stub!");
    }
    
    public static void request(final Window source, final Rect srcRect, final Bitmap dest, final OnPixelCopyFinishedListener listener, final Handler listenerThread) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnPixelCopyFinishedListener
    {
        void onPixelCopyFinished(final int p0);
    }
}
