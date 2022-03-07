package android.net.wifi;

public abstract static class WpsCallback
{
    public WpsCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onStarted(final String p0);
    
    public abstract void onSucceeded();
    
    public abstract void onFailed(final int p0);
}
