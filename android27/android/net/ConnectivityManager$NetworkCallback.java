package android.net;

public static class NetworkCallback
{
    public NetworkCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onAvailable(final Network network) {
        throw new RuntimeException("Stub!");
    }
    
    public void onLosing(final Network network, final int maxMsToLive) {
        throw new RuntimeException("Stub!");
    }
    
    public void onLost(final Network network) {
        throw new RuntimeException("Stub!");
    }
    
    public void onUnavailable() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCapabilitiesChanged(final Network network, final NetworkCapabilities networkCapabilities) {
        throw new RuntimeException("Stub!");
    }
    
    public void onLinkPropertiesChanged(final Network network, final LinkProperties linkProperties) {
        throw new RuntimeException("Stub!");
    }
}
