package android.net.wifi.aware;

import android.os.*;

public final class Characteristics implements Parcelable
{
    public static final Creator<Characteristics> CREATOR;
    
    Characteristics() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxServiceNameLength() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxServiceSpecificInfoLength() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxMatchFilterLength() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
