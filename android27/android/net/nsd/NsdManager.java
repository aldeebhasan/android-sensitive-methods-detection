package android.net.nsd;

public final class NsdManager
{
    public static final String ACTION_NSD_STATE_CHANGED = "android.net.nsd.STATE_CHANGED";
    public static final String EXTRA_NSD_STATE = "nsd_state";
    public static final int FAILURE_ALREADY_ACTIVE = 3;
    public static final int FAILURE_INTERNAL_ERROR = 0;
    public static final int FAILURE_MAX_LIMIT = 4;
    public static final int NSD_STATE_DISABLED = 1;
    public static final int NSD_STATE_ENABLED = 2;
    public static final int PROTOCOL_DNS_SD = 1;
    
    NsdManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void registerService(final NsdServiceInfo serviceInfo, final int protocolType, final RegistrationListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterService(final RegistrationListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void discoverServices(final String serviceType, final int protocolType, final DiscoveryListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void stopServiceDiscovery(final DiscoveryListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void resolveService(final NsdServiceInfo serviceInfo, final ResolveListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public interface ResolveListener
    {
        void onResolveFailed(final NsdServiceInfo p0, final int p1);
        
        void onServiceResolved(final NsdServiceInfo p0);
    }
    
    public interface RegistrationListener
    {
        void onRegistrationFailed(final NsdServiceInfo p0, final int p1);
        
        void onUnregistrationFailed(final NsdServiceInfo p0, final int p1);
        
        void onServiceRegistered(final NsdServiceInfo p0);
        
        void onServiceUnregistered(final NsdServiceInfo p0);
    }
    
    public interface DiscoveryListener
    {
        void onStartDiscoveryFailed(final String p0, final int p1);
        
        void onStopDiscoveryFailed(final String p0, final int p1);
        
        void onDiscoveryStarted(final String p0);
        
        void onDiscoveryStopped(final String p0);
        
        void onServiceFound(final NsdServiceInfo p0);
        
        void onServiceLost(final NsdServiceInfo p0);
    }
}
