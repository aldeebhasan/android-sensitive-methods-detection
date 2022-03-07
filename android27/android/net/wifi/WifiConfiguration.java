package android.net.wifi;

import java.util.*;
import android.net.*;
import android.os.*;

public class WifiConfiguration implements Parcelable
{
    public String BSSID;
    public String FQDN;
    public String SSID;
    public BitSet allowedAuthAlgorithms;
    public BitSet allowedGroupCiphers;
    public BitSet allowedKeyManagement;
    public BitSet allowedPairwiseCiphers;
    public BitSet allowedProtocols;
    public WifiEnterpriseConfig enterpriseConfig;
    public boolean hiddenSSID;
    public boolean isHomeProviderNetwork;
    public int networkId;
    public String preSharedKey;
    @Deprecated
    public int priority;
    public String providerFriendlyName;
    public long[] roamingConsortiumIds;
    public int status;
    public String[] wepKeys;
    public int wepTxKeyIndex;
    
    public WifiConfiguration() {
        this.roamingConsortiumIds = null;
        this.wepKeys = null;
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPasspoint() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public ProxyInfo getHttpProxy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHttpProxy(final ProxyInfo httpProxy) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static class KeyMgmt
    {
        public static final int IEEE8021X = 3;
        public static final int NONE = 0;
        public static final int WPA_EAP = 2;
        public static final int WPA_PSK = 1;
        public static final String[] strings;
        public static final String varName = "key_mgmt";
        
        KeyMgmt() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            strings = null;
        }
    }
    
    public static class Protocol
    {
        public static final int RSN = 1;
        public static final int WPA = 0;
        public static final String[] strings;
        public static final String varName = "proto";
        
        Protocol() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            strings = null;
        }
    }
    
    public static class AuthAlgorithm
    {
        public static final int LEAP = 2;
        public static final int OPEN = 0;
        public static final int SHARED = 1;
        public static final String[] strings;
        public static final String varName = "auth_alg";
        
        AuthAlgorithm() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            strings = null;
        }
    }
    
    public static class PairwiseCipher
    {
        public static final int CCMP = 2;
        public static final int NONE = 0;
        public static final int TKIP = 1;
        public static final String[] strings;
        public static final String varName = "pairwise";
        
        PairwiseCipher() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            strings = null;
        }
    }
    
    public static class GroupCipher
    {
        public static final int CCMP = 3;
        public static final int TKIP = 2;
        public static final int WEP104 = 1;
        public static final int WEP40 = 0;
        public static final String[] strings;
        public static final String varName = "group";
        
        GroupCipher() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            strings = null;
        }
    }
    
    public static class Status
    {
        public static final int CURRENT = 0;
        public static final int DISABLED = 1;
        public static final int ENABLED = 2;
        public static final String[] strings;
        
        Status() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            strings = null;
        }
    }
}
