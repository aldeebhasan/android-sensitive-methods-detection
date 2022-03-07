package android.net.wifi;

import android.net.*;
import android.os.*;

public class WifiInfo implements Parcelable
{
    public static final String FREQUENCY_UNITS = "MHz";
    public static final String LINK_SPEED_UNITS = "Mbps";
    
    WifiInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSSID() {
        throw new RuntimeException("Stub!");
    }
    
    public String getBSSID() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRssi() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLinkSpeed() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFrequency() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMacAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public int getNetworkId() {
        throw new RuntimeException("Stub!");
    }
    
    public SupplicantState getSupplicantState() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIpAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getHiddenSSID() {
        throw new RuntimeException("Stub!");
    }
    
    public static NetworkInfo.DetailedState getDetailedStateOf(final SupplicantState suppState) {
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
}
