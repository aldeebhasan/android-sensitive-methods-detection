package android.view;

public interface OnScaleGestureListener
{
    boolean onScale(final ScaleGestureDetector p0);
    
    boolean onScaleBegin(final ScaleGestureDetector p0);
    
    void onScaleEnd(final ScaleGestureDetector p0);
}
