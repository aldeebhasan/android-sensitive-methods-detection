package android.net.wifi.p2p;

import android.os.*;

public class WifiP2pDevice implements Parcelable
{
    public static final int AVAILABLE = 3;
    public static final int CONNECTED = 0;
    public static final Creator<WifiP2pDevice> CREATOR;
    public static final int FAILED = 2;
    public static final int INVITED = 1;
    public static final int UNAVAILABLE = 4;
    public String deviceAddress;
    public String deviceName;
    public String primaryDeviceType;
    public String secondaryDeviceType;
    public int status;
    
    public WifiP2pDevice() {
        throw new RuntimeException("Stub!");
    }
    
    public WifiP2pDevice(final WifiP2pDevice source) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean wpsPbcSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean wpsKeypadSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean wpsDisplaySupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isServiceDiscoveryCapable() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isGroupOwner() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
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
    
    static {
        CREATOR = null;
    }
}
