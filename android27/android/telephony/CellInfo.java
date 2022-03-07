package android.telephony;

import android.os.*;

public abstract class CellInfo implements Parcelable
{
    public static final Creator<CellInfo> CREATOR;
    
    CellInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRegistered() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTimeStamp() {
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
    public abstract void writeToParcel(final Parcel p0, final int p1);
    
    static {
        CREATOR = null;
    }
}
