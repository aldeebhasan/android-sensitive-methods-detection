package android.companion;

import android.net.wifi.*;
import android.os.*;
import java.util.regex.*;

public final class WifiDeviceFilter implements DeviceFilter<ScanResult>
{
    public static final Parcelable.Creator<WifiDeviceFilter> CREATOR;
    
    WifiDeviceFilter() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
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
    
    public static final class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setNamePattern(final Pattern regex) {
            throw new RuntimeException("Stub!");
        }
        
        public WifiDeviceFilter build() {
            throw new RuntimeException("Stub!");
        }
    }
}
