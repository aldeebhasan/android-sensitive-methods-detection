package android.content.pm;

public abstract static class SessionCallback
{
    public SessionCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onCreated(final int p0);
    
    public abstract void onBadgingChanged(final int p0);
    
    public abstract void onActiveChanged(final int p0, final boolean p1);
    
    public abstract void onProgressChanged(final int p0, final float p1);
    
    public abstract void onFinished(final int p0, final boolean p1);
}
