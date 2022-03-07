package android.net.wifi.aware;

import android.os.*;
import android.net.*;

public class WifiAwareSession implements AutoCloseable
{
    WifiAwareSession() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public void publish(final PublishConfig publishConfig, final DiscoverySessionCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void subscribe(final SubscribeConfig subscribeConfig, final DiscoverySessionCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkSpecifier createNetworkSpecifierOpen(final int role, final byte[] peer) {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkSpecifier createNetworkSpecifierPassphrase(final int role, final byte[] peer, final String passphrase) {
        throw new RuntimeException("Stub!");
    }
}
