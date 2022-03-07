package android.net.wifi.aware;

import android.net.*;

public class DiscoverySession implements AutoCloseable
{
    DiscoverySession() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendMessage(final PeerHandle peerHandle, final int messageId, final byte[] message) {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkSpecifier createNetworkSpecifierOpen(final PeerHandle peerHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkSpecifier createNetworkSpecifierPassphrase(final PeerHandle peerHandle, final String passphrase) {
        throw new RuntimeException("Stub!");
    }
}
