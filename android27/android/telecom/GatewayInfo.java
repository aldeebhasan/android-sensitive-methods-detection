package android.telecom;

import android.net.*;
import android.os.*;

public class GatewayInfo implements Parcelable
{
    public static final Creator<GatewayInfo> CREATOR;
    
    public GatewayInfo(final String packageName, final Uri gatewayUri, final Uri originalAddress) {
        throw new RuntimeException("Stub!");
    }
    
    public String getGatewayProviderPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getGatewayAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getOriginalAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel destination, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
