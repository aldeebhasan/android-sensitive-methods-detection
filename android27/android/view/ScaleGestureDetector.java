package android.view;

import android.content.*;
import android.os.*;

public class ScaleGestureDetector
{
    public ScaleGestureDetector(final Context context, final OnScaleGestureListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public ScaleGestureDetector(final Context context, final OnScaleGestureListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void setQuickScaleEnabled(final boolean scales) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isQuickScaleEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStylusScaleEnabled(final boolean scales) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isStylusScaleEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInProgress() {
        throw new RuntimeException("Stub!");
    }
    
    public float getFocusX() {
        throw new RuntimeException("Stub!");
    }
    
    public float getFocusY() {
        throw new RuntimeException("Stub!");
    }
    
    public float getCurrentSpan() {
        throw new RuntimeException("Stub!");
    }
    
    public float getCurrentSpanX() {
        throw new RuntimeException("Stub!");
    }
    
    public float getCurrentSpanY() {
        throw new RuntimeException("Stub!");
    }
    
    public float getPreviousSpan() {
        throw new RuntimeException("Stub!");
    }
    
    public float getPreviousSpanX() {
        throw new RuntimeException("Stub!");
    }
    
    public float getPreviousSpanY() {
        throw new RuntimeException("Stub!");
    }
    
    public float getScaleFactor() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTimeDelta() {
        throw new RuntimeException("Stub!");
    }
    
    public long getEventTime() {
        throw new RuntimeException("Stub!");
    }
    
    public static class SimpleOnScaleGestureListener implements OnScaleGestureListener
    {
        public SimpleOnScaleGestureListener() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onScale(final ScaleGestureDetector detector) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onScaleBegin(final ScaleGestureDetector detector) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void onScaleEnd(final ScaleGestureDetector detector) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnScaleGestureListener
    {
        boolean onScale(final ScaleGestureDetector p0);
        
        boolean onScaleBegin(final ScaleGestureDetector p0);
        
        void onScaleEnd(final ScaleGestureDetector p0);
    }
}
