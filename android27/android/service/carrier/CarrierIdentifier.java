package android.service.carrier;

import android.os.*;

public class CarrierIdentifier implements Parcelable
{
    public static final Creator<CarrierIdentifier> CREATOR;
    
    public CarrierIdentifier(final String mcc, final String mnc, final String spn, final String imsi, final String gid1, final String gid2) {
        throw new RuntimeException("Stub!");
    }
    
    public String getMcc() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMnc() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSpn() {
        throw new RuntimeException("Stub!");
    }
    
    public String getImsi() {
        throw new RuntimeException("Stub!");
    }
    
    public String getGid1() {
        throw new RuntimeException("Stub!");
    }
    
    public String getGid2() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
