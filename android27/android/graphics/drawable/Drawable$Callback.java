package android.graphics.drawable;

public interface Callback
{
    void invalidateDrawable(final Drawable p0);
    
    void scheduleDrawable(final Drawable p0, final Runnable p1, final long p2);
    
    void unscheduleDrawable(final Drawable p0, final Runnable p1);
}
