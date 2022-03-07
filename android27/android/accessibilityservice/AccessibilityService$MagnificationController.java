package android.accessibilityservice;

import android.os.*;
import android.graphics.*;

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
