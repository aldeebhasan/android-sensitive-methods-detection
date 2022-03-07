package android.net.nsd;

public interface DiscoveryListener
{
    void onStartDiscoveryFailed(final String p0, final int p1);
    
    void onStopDiscoveryFailed(final String p0, final int p1);
    
    void onDiscoveryStarted(final String p0);
    
    void onDiscoveryStopped(final String p0);
    
    void onServiceFound(final NsdServiceInfo p0);
    
    void onServiceLost(final NsdServiceInfo p0);
}
