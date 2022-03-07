package android.net.wifi;

import android.os.*;

public class ScanResult implements Parcelable
{
    public String BSSID;
    public static final int CHANNEL_WIDTH_160MHZ = 3;
    public static final int CHANNEL_WIDTH_20MHZ = 0;
    public static final int CHANNEL_WIDTH_40MHZ = 1;
    public static final int CHANNEL_WIDTH_80MHZ = 2;
    public static final int CHANNEL_WIDTH_80MHZ_PLUS_MHZ = 4;
    public String SSID;
    public String capabilities;
    public int centerFreq0;
    public int centerFreq1;
    public int channelWidth;
    public int frequency;
    public int level;
    public CharSequence operatorFriendlyName;
    public long timestamp;
    public CharSequence venueName;
    
    ScanResult() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean is80211mcResponder() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPasspointNetwork() {
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
