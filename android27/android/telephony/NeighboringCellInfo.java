package android.telephony;

import android.os.*;

public class NeighboringCellInfo implements Parcelable
{
    public static final Creator<NeighboringCellInfo> CREATOR;
    public static final int UNKNOWN_CID = -1;
    public static final int UNKNOWN_RSSI = 99;
    
    public NeighboringCellInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public NeighboringCellInfo(final int rssi, final int cid) {
        throw new RuntimeException("Stub!");
    }
    
    public NeighboringCellInfo(final int rssi, final String location, final int radioType) {
        throw new RuntimeException("Stub!");
    }
    
    public NeighboringCellInfo(final Parcel in) {
        throw new RuntimeException("Stub!");
    }
    
    public int getRssi() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLac() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCid() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPsc() {
        throw new RuntimeException("Stub!");
    }
    
    public int getNetworkType() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setCid(final int cid) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setRssi(final int rssi) {
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
