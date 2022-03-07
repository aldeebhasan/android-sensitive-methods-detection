package android.companion;

import java.util.regex.*;
import android.bluetooth.le.*;
import java.nio.*;

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
