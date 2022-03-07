package android.view;

public interface Callback
{
    void surfaceCreated(final SurfaceHolder p0);
    
    void surfaceChanged(final SurfaceHolder p0, final int p1, final int p2, final int p3);
    
    void surfaceDestroyed(final SurfaceHolder p0);
}
