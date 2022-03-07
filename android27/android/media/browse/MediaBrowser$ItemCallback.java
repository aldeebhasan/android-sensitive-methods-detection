package android.media.browse;

public abstract static class ItemCallback
{
    public ItemCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onItemLoaded(final MediaItem item) {
        throw new RuntimeException("Stub!");
    }
    
    public void onError(final String mediaId) {
        throw new RuntimeException("Stub!");
    }
}
