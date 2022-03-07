package android.view;

public interface OnDoubleTapListener
{
    boolean onSingleTapConfirmed(final MotionEvent p0);
    
    boolean onDoubleTap(final MotionEvent p0);
    
    boolean onDoubleTapEvent(final MotionEvent p0);
}
