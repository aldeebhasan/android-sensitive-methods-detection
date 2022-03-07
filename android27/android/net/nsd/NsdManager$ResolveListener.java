package android.net.nsd;

public interface ResolveListener
{
    void onResolveFailed(final NsdServiceInfo p0, final int p1);
    
    void onServiceResolved(final NsdServiceInfo p0);
}
