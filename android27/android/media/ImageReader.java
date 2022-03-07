package android.media;

import android.view.*;
import android.os.*;

public class ImageReader implements AutoCloseable
{
    ImageReader() {
        throw new RuntimeException("Stub!");
    }
    
    public static ImageReader newInstance(final int width, final int height, final int format, final int maxImages) {
        throw new RuntimeException("Stub!");
    }
    
    public int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public int getImageFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxImages() {
        throw new RuntimeException("Stub!");
    }
    
    public Surface getSurface() {
        throw new RuntimeException("Stub!");
    }
    
    public Image acquireLatestImage() {
        throw new RuntimeException("Stub!");
    }
    
    public Image acquireNextImage() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnImageAvailableListener(final OnImageAvailableListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnImageAvailableListener
    {
        void onImageAvailable(final ImageReader p0);
    }
}
