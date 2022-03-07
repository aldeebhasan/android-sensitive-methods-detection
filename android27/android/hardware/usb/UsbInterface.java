package android.hardware.usb;

import android.os.*;

public class UsbInterface implements Parcelable
{
    public static final Creator<UsbInterface> CREATOR;
    
    UsbInterface() {
        throw new RuntimeException("Stub!");
    }
    
    public int getId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getAlternateSetting() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInterfaceClass() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInterfaceSubclass() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInterfaceProtocol() {
        throw new RuntimeException("Stub!");
    }
    
    public int getEndpointCount() {
        throw new RuntimeException("Stub!");
    }
    
    public UsbEndpoint getEndpoint(final int index) {
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
