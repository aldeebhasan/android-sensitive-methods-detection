package android.bluetooth.le;

import android.os.*;

public final class ScanFilter implements Parcelable
{
    public static final Creator<ScanFilter> CREATOR;
    
    ScanFilter() {
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
    
    public String getDeviceName() {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelUuid getServiceUuid() {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelUuid getServiceUuidMask() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDeviceAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getServiceData() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getServiceDataMask() {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelUuid getServiceDataUuid() {
        throw new RuntimeException("Stub!");
    }
    
    public int getManufacturerId() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getManufacturerData() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getManufacturerDataMask() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean matches(final ScanResult scanResult) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
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
        
        public Builder setDeviceName(final String deviceName) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDeviceAddress(final String deviceAddress) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setServiceUuid(final ParcelUuid serviceUuid) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setServiceUuid(final ParcelUuid serviceUuid, final ParcelUuid uuidMask) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setServiceData(final ParcelUuid serviceDataUuid, final byte[] serviceData) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setServiceData(final ParcelUuid serviceDataUuid, final byte[] serviceData, final byte[] serviceDataMask) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setManufacturerData(final int manufacturerId, final byte[] manufacturerData) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setManufacturerData(final int manufacturerId, final byte[] manufacturerData, final byte[] manufacturerDataMask) {
            throw new RuntimeException("Stub!");
        }
        
        public ScanFilter build() {
            throw new RuntimeException("Stub!");
        }
    }
}
