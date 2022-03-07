package android.companion;

import android.bluetooth.*;
import java.util.regex.*;
import android.os.*;

public final class BluetoothDeviceFilter implements DeviceFilter<BluetoothDevice>
{
    public static final Parcelable.Creator<BluetoothDeviceFilter> CREATOR;
    
    BluetoothDeviceFilter() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
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
        
        public Builder setAddress(final String address) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addServiceUuid(final ParcelUuid serviceUuid, final ParcelUuid serviceUuidMask) {
            throw new RuntimeException("Stub!");
        }
        
        public BluetoothDeviceFilter build() {
            throw new RuntimeException("Stub!");
        }
    }
}
