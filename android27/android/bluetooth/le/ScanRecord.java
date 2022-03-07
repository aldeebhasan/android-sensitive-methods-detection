package android.bluetooth.le;

import android.os.*;
import android.util.*;
import java.util.*;

public final class ScanRecord
{
    ScanRecord() {
        throw new RuntimeException("Stub!");
    }
    
    public int getAdvertiseFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public List<ParcelUuid> getServiceUuids() {
        throw new RuntimeException("Stub!");
    }
    
    public SparseArray<byte[]> getManufacturerSpecificData() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getManufacturerSpecificData(final int manufacturerId) {
        throw new RuntimeException("Stub!");
    }
    
    public Map<ParcelUuid, byte[]> getServiceData() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getServiceData(final ParcelUuid serviceDataUuid) {
        throw new RuntimeException("Stub!");
    }
    
    public int getTxPowerLevel() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDeviceName() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getBytes() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
