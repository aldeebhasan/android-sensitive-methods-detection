package android.gesture;

import android.view.*;

public interface OnGestureListener
{
    void onGestureStarted(final GestureOverlayView p0, final MotionEvent p1);
    
    void onGesture(final GestureOverlayView p0, final MotionEvent p1);
    
    void onGestureEnded(final GestureOverlayView p0, final MotionEvent p1);
    
    void onGestureCancelled(final GestureOverlayView p0, final MotionEvent p1);
}
