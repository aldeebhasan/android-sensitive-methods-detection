package android.net.wifi.hotspot2.pps;

import android.os.*;

public static final class SimCredential implements Parcelable
{
    public static final Creator<SimCredential> CREATOR;
    
    public SimCredential() {
        throw new RuntimeException("Stub!");
    }
    
    public SimCredential(final SimCredential source) {
        throw new RuntimeException("Stub!");
    }
    
    public void setImsi(final String imsi) {
        throw new RuntimeException("Stub!");
    }
    
    public String getImsi() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEapType(final int eapType) {
        throw new RuntimeException("Stub!");
    }
    
    public int getEapType() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object thatObject) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
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
