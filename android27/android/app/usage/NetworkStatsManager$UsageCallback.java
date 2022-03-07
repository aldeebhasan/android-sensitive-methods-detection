package android.app.usage;

public abstract static class UsageCallback
{
    public UsageCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onThresholdReached(final int p0, final String p1);
}
