package android.telephony;

import android.os.*;

public final class CellIdentityWcdma implements Parcelable
{
    public static final Creator<CellIdentityWcdma> CREATOR;
    
    CellIdentityWcdma() {
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
    
    public int getPsc() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    public int getUarfcn() {
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
