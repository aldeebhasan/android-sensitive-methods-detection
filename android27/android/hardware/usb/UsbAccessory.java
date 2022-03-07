package android.hardware.usb;

import android.os.*;

public class UsbAccessory implements Parcelable
{
    public static final Creator<UsbAccessory> CREATOR;
    
    UsbAccessory() {
        throw new RuntimeException("Stub!");
    }
    
    public String getManufacturer() {
        throw new RuntimeException("Stub!");
    }
    
    public String getModel() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public String getVersion() {
        throw new RuntimeException("Stub!");
    }
    
    public String getUri() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSerial() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
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
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
