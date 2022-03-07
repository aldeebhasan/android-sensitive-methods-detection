package android.telephony;

import android.os.*;

public final class CellIdentityLte implements Parcelable
{
    public static final Creator<CellIdentityLte> CREATOR;
    
    CellIdentityLte() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMcc() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMnc() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCi() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPci() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTac() {
        throw new RuntimeException("Stub!");
    }
    
    public int getEarfcn() {
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
