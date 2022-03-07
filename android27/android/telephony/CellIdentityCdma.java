package android.telephony;

import android.os.*;

public final class CellIdentityCdma implements Parcelable
{
    public static final Creator<CellIdentityCdma> CREATOR;
    
    CellIdentityCdma() {
        throw new RuntimeException("Stub!");
    }
    
    public int getNetworkId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSystemId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getBasestationId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLongitude() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLatitude() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object other) {
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
