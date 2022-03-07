package android.hardware.usb;

import android.os.*;

public class UsbConfiguration implements Parcelable
{
    public static final Creator<UsbConfiguration> CREATOR;
    
    UsbConfiguration() {
        throw new RuntimeException("Stub!");
    }
    
    public int getId() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSelfPowered() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRemoteWakeup() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxPower() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInterfaceCount() {
        throw new RuntimeException("Stub!");
    }
    
    public UsbInterface getInterface(final int index) {
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
