package android.view;

import android.os.*;
import android.content.*;

public class GestureDetector
{
    public GestureDetector(final OnGestureListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public GestureDetector(final OnGestureListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public GestureDetector(final Context context, final OnGestureListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public GestureDetector(final Context context, final OnGestureListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public GestureDetector(final Context context, final OnGestureListener listener, final Handler handler, final boolean unused) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnDoubleTapListener(final OnDoubleTapListener onDoubleTapListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setContextClickListener(final OnContextClickListener onContextClickListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIsLongpressEnabled(final boolean isLongpressEnabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isLongpressEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onTouchEvent(final MotionEvent ev) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onGenericMotionEvent(final MotionEvent ev) {
        throw new RuntimeException("Stub!");
    }
    
    public static class SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener, OnContextClickListener
    {
        public SimpleOnGestureListener() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onSingleTapUp(final MotionEvent e) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void onLongPress(final MotionEvent e) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void onShowPress(final MotionEvent e) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onDown(final MotionEvent e) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onDoubleTap(final MotionEvent e) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onDoubleTapEvent(final MotionEvent e) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onSingleTapConfirmed(final MotionEvent e) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean onContextClick(final MotionEvent e) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnGestureListener
    {
        boolean onDown(final MotionEvent p0);
        
        void onShowPress(final MotionEvent p0);
        
        boolean onSingleTapUp(final MotionEvent p0);
        
        boolean onScroll(final MotionEvent p0, final MotionEvent p1, final float p2, final float p3);
        
        void onLongPress(final MotionEvent p0);
        
        boolean onFling(final MotionEvent p0, final MotionEvent p1, final float p2, final float p3);
    }
    
    public interface OnDoubleTapListener
    {
        boolean onSingleTapConfirmed(final MotionEvent p0);
        
        boolean onDoubleTap(final MotionEvent p0);
        
        boolean onDoubleTapEvent(final MotionEvent p0);
    }
    
    public interface OnContextClickListener
    {
        boolean onContextClick(final MotionEvent p0);
    }
}
