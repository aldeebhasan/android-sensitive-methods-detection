package android.telephony;

import android.os.*;

public final class CellInfoLte extends CellInfo implements Parcelable
{
    public static final Creator<CellInfoLte> CREATOR;
    
    CellInfoLte() {
        throw new RuntimeException("Stub!");
    }
    
    public CellIdentityLte getCellIdentity() {
        throw new RuntimeException("Stub!");
    }
    
    public CellSignalStrengthLte getCellSignalStrength() {
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
