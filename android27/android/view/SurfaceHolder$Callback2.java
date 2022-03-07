package android.view;

public interface Callback2 extends Callback
{
    void surfaceRedrawNeeded(final SurfaceHolder p0);
    
    default void surfaceRedrawNeededAsync(final SurfaceHolder holder, final Runnable drawingFinished) {
        throw new RuntimeException("Stub!");
    }
}
