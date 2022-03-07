package android.net.nsd;

public interface RegistrationListener
{
    void onRegistrationFailed(final NsdServiceInfo p0, final int p1);
    
    void onUnregistrationFailed(final NsdServiceInfo p0, final int p1);
    
    void onServiceRegistered(final NsdServiceInfo p0);
    
    void onServiceUnregistered(final NsdServiceInfo p0);
}
