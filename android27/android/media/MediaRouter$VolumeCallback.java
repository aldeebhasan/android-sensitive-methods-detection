package android.media;

public abstract static class VolumeCallback
{
    public VolumeCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onVolumeUpdateRequest(final RouteInfo p0, final int p1);
    
    public abstract void onVolumeSetRequest(final RouteInfo p0, final int p1);
}
