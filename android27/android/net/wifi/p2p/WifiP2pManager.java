package android.net.wifi.p2p;

import android.content.*;
import android.os.*;
import android.net.wifi.p2p.nsd.*;
import java.util.*;

public class WifiP2pManager
{
    public static final int BUSY = 2;
    public static final int ERROR = 0;
    public static final String EXTRA_DISCOVERY_STATE = "discoveryState";
    public static final String EXTRA_NETWORK_INFO = "networkInfo";
    public static final String EXTRA_P2P_DEVICE_LIST = "wifiP2pDeviceList";
    public static final String EXTRA_WIFI_P2P_DEVICE = "wifiP2pDevice";
    public static final String EXTRA_WIFI_P2P_GROUP = "p2pGroupInfo";
    public static final String EXTRA_WIFI_P2P_INFO = "wifiP2pInfo";
    public static final String EXTRA_WIFI_STATE = "wifi_p2p_state";
    public static final int NO_SERVICE_REQUESTS = 3;
    public static final int P2P_UNSUPPORTED = 1;
    public static final String WIFI_P2P_CONNECTION_CHANGED_ACTION = "android.net.wifi.p2p.CONNECTION_STATE_CHANGE";
    public static final String WIFI_P2P_DISCOVERY_CHANGED_ACTION = "android.net.wifi.p2p.DISCOVERY_STATE_CHANGE";
    public static final int WIFI_P2P_DISCOVERY_STARTED = 2;
    public static final int WIFI_P2P_DISCOVERY_STOPPED = 1;
    public static final String WIFI_P2P_PEERS_CHANGED_ACTION = "android.net.wifi.p2p.PEERS_CHANGED";
    public static final String WIFI_P2P_STATE_CHANGED_ACTION = "android.net.wifi.p2p.STATE_CHANGED";
    public static final int WIFI_P2P_STATE_DISABLED = 1;
    public static final int WIFI_P2P_STATE_ENABLED = 2;
    public static final String WIFI_P2P_THIS_DEVICE_CHANGED_ACTION = "android.net.wifi.p2p.THIS_DEVICE_CHANGED";
    
    WifiP2pManager() {
        throw new RuntimeException("Stub!");
    }
    
    public Channel initialize(final Context srcContext, final Looper srcLooper, final ChannelListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void discoverPeers(final Channel c, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void stopPeerDiscovery(final Channel c, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void connect(final Channel c, final WifiP2pConfig config, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void cancelConnect(final Channel c, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void createGroup(final Channel c, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeGroup(final Channel c, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void addLocalService(final Channel c, final WifiP2pServiceInfo servInfo, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeLocalService(final Channel c, final WifiP2pServiceInfo servInfo, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearLocalServices(final Channel c, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setServiceResponseListener(final Channel c, final ServiceResponseListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDnsSdResponseListeners(final Channel c, final DnsSdServiceResponseListener servListener, final DnsSdTxtRecordListener txtListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setUpnpServiceResponseListener(final Channel c, final UpnpServiceResponseListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void discoverServices(final Channel c, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void addServiceRequest(final Channel c, final WifiP2pServiceRequest req, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeServiceRequest(final Channel c, final WifiP2pServiceRequest req, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearServiceRequests(final Channel c, final ActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestPeers(final Channel c, final PeerListListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestConnectionInfo(final Channel c, final ConnectionInfoListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestGroupInfo(final Channel c, final GroupInfoListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public static class Channel implements AutoCloseable
    {
        Channel() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void close() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface UpnpServiceResponseListener
    {
        void onUpnpServiceAvailable(final List<String> p0, final WifiP2pDevice p1);
    }
    
    public interface DnsSdTxtRecordListener
    {
        void onDnsSdTxtRecordAvailable(final String p0, final Map<String, String> p1, final WifiP2pDevice p2);
    }
    
    public interface DnsSdServiceResponseListener
    {
        void onDnsSdServiceAvailable(final String p0, final String p1, final WifiP2pDevice p2);
    }
    
    public interface ServiceResponseListener
    {
        void onServiceAvailable(final int p0, final byte[] p1, final WifiP2pDevice p2);
    }
    
    public interface GroupInfoListener
    {
        void onGroupInfoAvailable(final WifiP2pGroup p0);
    }
    
    public interface ConnectionInfoListener
    {
        void onConnectionInfoAvailable(final WifiP2pInfo p0);
    }
    
    public interface PeerListListener
    {
        void onPeersAvailable(final WifiP2pDeviceList p0);
    }
    
    public interface ActionListener
    {
        void onSuccess();
        
        void onFailure(final int p0);
    }
    
    public interface ChannelListener
    {
        void onChannelDisconnected();
    }
}
