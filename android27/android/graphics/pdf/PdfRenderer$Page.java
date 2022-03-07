package android.graphics.pdf;

import android.graphics.*;

public final class Page implements AutoCloseable
{
    public static final int RENDER_MODE_FOR_DISPLAY = 1;
    public static final int RENDER_MODE_FOR_PRINT = 2;
    
    Page() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIndex() {
        throw new RuntimeException("Stub!");
    }
    
    public int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public void render(final Bitmap destination, final Rect destClip, final Matrix transform, final int renderMode) {
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
}
