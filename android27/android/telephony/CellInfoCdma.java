package android.telephony;

import android.os.*;

public final class CellInfoCdma extends CellInfo implements Parcelable
{
    public static final Creator<CellInfoCdma> CREATOR;
    
    CellInfoCdma() {
        throw new RuntimeException("Stub!");
    }
    
    public CellIdentityCdma getCellIdentity() {
        throw new RuntimeException("Stub!");
    }
    
    public CellSignalStrengthCdma getCellSignalStrength() {
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
