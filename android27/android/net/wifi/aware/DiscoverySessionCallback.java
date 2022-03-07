package android.net.wifi.aware;

import java.util.*;

public class DiscoverySessionCallback
{
    public DiscoverySessionCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onPublishStarted(final PublishDiscoverySession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSubscribeStarted(final SubscribeDiscoverySession session) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSessionConfigUpdated() {
        throw new RuntimeException("Stub!");
    }
    
    public void onSessionConfigFailed() {
        throw new RuntimeException("Stub!");
    }
    
    public void onSessionTerminated() {
        throw new RuntimeException("Stub!");
    }
    
    public void onServiceDiscovered(final PeerHandle peerHandle, final byte[] serviceSpecificInfo, final List<byte[]> matchFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public void onMessageSendSucceeded(final int messageId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onMessageSendFailed(final int messageId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onMessageReceived(final PeerHandle peerHandle, final byte[] message) {
        throw new RuntimeException("Stub!");
    }
}
