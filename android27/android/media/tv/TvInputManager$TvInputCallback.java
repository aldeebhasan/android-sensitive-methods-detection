package android.media.tv;

public abstract static class TvInputCallback
{
    public TvInputCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onInputStateChanged(final String inputId, final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public void onInputAdded(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onInputRemoved(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onInputUpdated(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTvInputInfoUpdated(final TvInputInfo inputInfo) {
        throw new RuntimeException("Stub!");
    }
}
