package android.view;

public interface OnGestureListener
{
    boolean onDown(final MotionEvent p0);
    
    void onShowPress(final MotionEvent p0);
    
    boolean onSingleTapUp(final MotionEvent p0);
    
    boolean onScroll(final MotionEvent p0, final MotionEvent p1, final float p2, final float p3);
    
    void onLongPress(final MotionEvent p0);
    
    boolean onFling(final MotionEvent p0, final MotionEvent p1, final float p2, final float p3);
}
