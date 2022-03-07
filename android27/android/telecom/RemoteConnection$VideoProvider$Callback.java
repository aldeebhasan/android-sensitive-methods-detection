package android.telecom;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onSessionModifyRequestReceived(final VideoProvider videoProvider, final VideoProfile videoProfile) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSessionModifyResponseReceived(final VideoProvider videoProvider, final int status, final VideoProfile requestedProfile, final VideoProfile responseProfile) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallSessionEvent(final VideoProvider videoProvider, final int event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPeerDimensionsChanged(final VideoProvider videoProvider, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallDataUsageChanged(final VideoProvider videoProvider, final long dataUsage) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCameraCapabilitiesChanged(final VideoProvider videoProvider, final VideoProfile.CameraCapabilities cameraCapabilities) {
        throw new RuntimeException("Stub!");
    }
    
    public void onVideoQualityChanged(final VideoProvider videoProvider, final int videoQuality) {
        throw new RuntimeException("Stub!");
    }
}
