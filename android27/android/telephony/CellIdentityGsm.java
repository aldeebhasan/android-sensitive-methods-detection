package android.telephony;

import android.os.*;

public final class CellIdentityGsm implements Parcelable
{
    public static final Creator<CellIdentityGsm> CREATOR;
    
    CellIdentityGsm() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMcc() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMnc() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLac() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCid() {
        throw new RuntimeException("Stub!");
    }
    
    public int getArfcn() {
        throw new RuntimeException("Stub!");
    }
    
    public int getBsic() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int getPsc() {
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
