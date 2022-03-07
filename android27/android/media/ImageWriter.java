package android.media;

import android.view.*;
import android.os.*;

public class ImageWriter implements AutoCloseable
{
    ImageWriter() {
        throw new RuntimeException("Stub!");
    }
    
    public static ImageWriter newInstance(final Surface surface, final int maxImages) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxImages() {
        throw new RuntimeException("Stub!");
    }
    
    public Image dequeueInputImage() {
        throw new RuntimeException("Stub!");
    }
    
    public void queueInputImage(final Image image) {
        throw new RuntimeException("Stub!");
    }
    
    public int getFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnImageReleasedListener(final OnImageReleasedListener listener, final Handler handler) {
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
    
    public interface OnImageReleasedListener
    {
        void onImageReleased(final ImageWriter p0);
    }
}
