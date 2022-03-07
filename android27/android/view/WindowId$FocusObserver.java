package android.view;

public abstract static class FocusObserver
{
    public FocusObserver() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onFocusGained(final WindowId p0);
    
    public abstract void onFocusLost(final WindowId p0);
}
