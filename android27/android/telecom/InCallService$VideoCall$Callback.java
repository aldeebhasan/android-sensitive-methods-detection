package android.telecom;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onSessionModifyRequestReceived(final VideoProfile p0);
    
    public abstract void onSessionModifyResponseReceived(final int p0, final VideoProfile p1, final VideoProfile p2);
    
    public abstract void onCallSessionEvent(final int p0);
    
    public abstract void onPeerDimensionsChanged(final int p0, final int p1);
    
    public abstract void onVideoQualityChanged(final int p0);
    
    public abstract void onCallDataUsageChanged(final long p0);
    
    public abstract void onCameraCapabilitiesChanged(final VideoProfile.CameraCapabilities p0);
}
