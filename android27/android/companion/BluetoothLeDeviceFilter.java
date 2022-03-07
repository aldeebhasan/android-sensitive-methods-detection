package android.companion;

import android.os.*;
import java.util.regex.*;
import android.bluetooth.le.*;
import java.nio.*;

public final class BluetoothLeDeviceFilter implements DeviceFilter<ScanResult>
{
    public static final Parcelable.Creator<BluetoothLeDeviceFilter> CREATOR;
    
    BluetoothLeDeviceFilter() {
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
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static int getRenamePrefixLengthLimit() {
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
        
        public Builder setScanFilter(final ScanFilter scanFilter) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setRawDataFilter(final byte[] rawDataFilter, final byte[] rawDataFilterMask) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setRenameFromBytes(final String prefix, final String suffix, final int bytesFrom, final int bytesLength, final ByteOrder byteOrder) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setRenameFromName(final String prefix, final String suffix, final int nameFrom, final int nameLength) {
            throw new RuntimeException("Stub!");
        }
        
        public BluetoothLeDeviceFilter build() {
            throw new RuntimeException("Stub!");
        }
    }
}
