package android.net.wifi;

import java.util.*;
import android.net.wifi.hotspot2.*;
import android.net.*;
import java.net.*;
import android.os.*;

public class WifiManager
{
    public static final String ACTION_PICK_WIFI_NETWORK = "android.net.wifi.PICK_WIFI_NETWORK";
    public static final String ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE = "android.net.wifi.action.REQUEST_SCAN_ALWAYS_AVAILABLE";
    public static final int ERROR_AUTHENTICATING = 1;
    public static final String EXTRA_BSSID = "bssid";
    public static final String EXTRA_NETWORK_INFO = "networkInfo";
    public static final String EXTRA_NEW_RSSI = "newRssi";
    public static final String EXTRA_NEW_STATE = "newState";
    public static final String EXTRA_PREVIOUS_WIFI_STATE = "previous_wifi_state";
    public static final String EXTRA_RESULTS_UPDATED = "resultsUpdated";
    public static final String EXTRA_SUPPLICANT_CONNECTED = "connected";
    public static final String EXTRA_SUPPLICANT_ERROR = "supplicantError";
    public static final String EXTRA_WIFI_INFO = "wifiInfo";
    public static final String EXTRA_WIFI_STATE = "wifi_state";
    public static final String NETWORK_IDS_CHANGED_ACTION = "android.net.wifi.NETWORK_IDS_CHANGED";
    public static final String NETWORK_STATE_CHANGED_ACTION = "android.net.wifi.STATE_CHANGE";
    public static final String RSSI_CHANGED_ACTION = "android.net.wifi.RSSI_CHANGED";
    public static final String SCAN_RESULTS_AVAILABLE_ACTION = "android.net.wifi.SCAN_RESULTS";
    public static final String SUPPLICANT_CONNECTION_CHANGE_ACTION = "android.net.wifi.supplicant.CONNECTION_CHANGE";
    public static final String SUPPLICANT_STATE_CHANGED_ACTION = "android.net.wifi.supplicant.STATE_CHANGE";
    public static final int WIFI_MODE_FULL = 1;
    public static final int WIFI_MODE_FULL_HIGH_PERF = 3;
    public static final int WIFI_MODE_SCAN_ONLY = 2;
    public static final String WIFI_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_STATE_CHANGED";
    public static final int WIFI_STATE_DISABLED = 1;
    public static final int WIFI_STATE_DISABLING = 0;
    public static final int WIFI_STATE_ENABLED = 3;
    public static final int WIFI_STATE_ENABLING = 2;
    public static final int WIFI_STATE_UNKNOWN = 4;
    public static final int WPS_AUTH_FAILURE = 6;
    public static final int WPS_OVERLAP_ERROR = 3;
    public static final int WPS_TIMED_OUT = 7;
    public static final int WPS_TKIP_ONLY_PROHIBITED = 5;
    public static final int WPS_WEP_PROHIBITED = 4;
    
    WifiManager() {
        throw new RuntimeException("Stub!");
    }
    
    public List<WifiConfiguration> getConfiguredNetworks() {
        throw new RuntimeException("Stub!");
    }
    
    public int addNetwork(final WifiConfiguration config) {
        throw new RuntimeException("Stub!");
    }
    
    public int updateNetwork(final WifiConfiguration config) {
        throw new RuntimeException("Stub!");
    }
    
    public void addOrUpdatePasspointConfiguration(final PasspointConfiguration config) {
        throw new RuntimeException("Stub!");
    }
    
    public void removePasspointConfiguration(final String fqdn) {
        throw new RuntimeException("Stub!");
    }
    
    public List<PasspointConfiguration> getPasspointConfigurations() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeNetwork(final int netId) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean enableNetwork(final int netId, final boolean attemptConnect) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean disableNetwork(final int netId) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean disconnect() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean reconnect() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean reassociate() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean pingSupplicant() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean is5GHzBandSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isP2pSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDeviceToApRttSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPreferredNetworkOffloadSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isTdlsSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnhancedPowerReportingSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean startScan() {
        throw new RuntimeException("Stub!");
    }
    
    public WifiInfo getConnectionInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public List<ScanResult> getScanResults() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isScanAlwaysAvailable() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean saveConfiguration() {
        throw new RuntimeException("Stub!");
    }
    
    public DhcpInfo getDhcpInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setWifiEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public int getWifiState() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isWifiEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public static int calculateSignalLevel(final int rssi, final int numLevels) {
        throw new RuntimeException("Stub!");
    }
    
    public static int compareSignalLevel(final int rssiA, final int rssiB) {
        throw new RuntimeException("Stub!");
    }
    
    public void startLocalOnlyHotspot(final LocalOnlyHotspotCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTdlsEnabled(final InetAddress remoteIPAddress, final boolean enable) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTdlsEnabledWithMacAddress(final String remoteMacAddress, final boolean enable) {
        throw new RuntimeException("Stub!");
    }
    
    public void startWps(final WpsInfo config, final WpsCallback listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void cancelWps(final WpsCallback listener) {
        throw new RuntimeException("Stub!");
    }
    
    public WifiLock createWifiLock(final int lockType, final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    public WifiLock createWifiLock(final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    public MulticastLock createMulticastLock(final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class WpsCallback
    {
        public WpsCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onStarted(final String p0);
        
        public abstract void onSucceeded();
        
        public abstract void onFailed(final int p0);
    }
    
    public class LocalOnlyHotspotReservation implements AutoCloseable
    {
        LocalOnlyHotspotReservation() {
            throw new RuntimeException("Stub!");
        }
        
        public WifiConfiguration getWifiConfiguration() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void close() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        protected void finalize() throws Throwable {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class LocalOnlyHotspotCallback
    {
        public static final int ERROR_GENERIC = 2;
        public static final int ERROR_INCOMPATIBLE_MODE = 3;
        public static final int ERROR_NO_CHANNEL = 1;
        public static final int ERROR_TETHERING_DISALLOWED = 4;
        
        public LocalOnlyHotspotCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onStarted(final LocalOnlyHotspotReservation reservation) {
            throw new RuntimeException("Stub!");
        }
        
        public void onStopped() {
            throw new RuntimeException("Stub!");
        }
        
        public void onFailed(final int reason) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public class WifiLock
    {
        WifiLock() {
            throw new RuntimeException("Stub!");
        }
        
        public void acquire() {
            throw new RuntimeException("Stub!");
        }
        
        public void release() {
            throw new RuntimeException("Stub!");
        }
        
        public void setReferenceCounted(final boolean refCounted) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isHeld() {
            throw new RuntimeException("Stub!");
        }
        
        public void setWorkSource(final WorkSource ws) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        protected void finalize() throws Throwable {
            throw new RuntimeException("Stub!");
        }
    }
    
    public class MulticastLock
    {
        MulticastLock() {
            throw new RuntimeException("Stub!");
        }
        
        public void acquire() {
            throw new RuntimeException("Stub!");
        }
        
        public void release() {
            throw new RuntimeException("Stub!");
        }
        
        public void setReferenceCounted(final boolean refCounted) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isHeld() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        protected void finalize() throws Throwable {
            throw new RuntimeException("Stub!");
        }
    }
}
