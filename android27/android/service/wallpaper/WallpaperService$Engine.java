package android.service.wallpaper;

import android.view.*;
import android.os.*;
import android.app.*;
import java.io.*;

public class Engine
{
    public Engine() {
        throw new RuntimeException("Stub!");
    }
    
    public SurfaceHolder getSurfaceHolder() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDesiredMinimumWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDesiredMinimumHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVisible() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPreview() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTouchEventsEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOffsetNotificationsEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreate(final SurfaceHolder surfaceHolder) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    public void onVisibilityChanged(final boolean visible) {
        throw new RuntimeException("Stub!");
    }
    
    public void onApplyWindowInsets(final WindowInsets insets) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onOffsetsChanged(final float xOffset, final float yOffset, final float xOffsetStep, final float yOffsetStep, final int xPixelOffset, final int yPixelOffset) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle onCommand(final String action, final int x, final int y, final int z, final Bundle extras, final boolean resultRequested) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDesiredSizeChanged(final int desiredWidth, final int desiredHeight) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSurfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSurfaceRedrawNeeded(final SurfaceHolder holder) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSurfaceCreated(final SurfaceHolder holder) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSurfaceDestroyed(final SurfaceHolder holder) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyColorsChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public WallpaperColors onComputeColors() {
        throw new RuntimeException("Stub!");
    }
    
    protected void dump(final String prefix, final FileDescriptor fd, final PrintWriter out, final String[] args) {
        throw new RuntimeException("Stub!");
    }
}
