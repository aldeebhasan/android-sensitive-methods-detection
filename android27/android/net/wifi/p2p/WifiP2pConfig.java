package android.net.wifi.p2p;

import android.net.wifi.*;
import android.os.*;

public class WifiP2pConfig implements Parcelable
{
    public static final Creator<WifiP2pConfig> CREATOR;
    public String deviceAddress;
    public int groupOwnerIntent;
    public WpsInfo wps;
    
    public WifiP2pConfig() {
        throw new RuntimeException("Stub!");
    }
    
    public WifiP2pConfig(final WifiP2pConfig source) {
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
