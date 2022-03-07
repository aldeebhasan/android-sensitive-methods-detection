package android.net;

import android.os.*;

public class NetworkInfo implements Parcelable
{
    public static final Creator<NetworkInfo> CREATOR;
    
    NetworkInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSubtype() {
        throw new RuntimeException("Stub!");
    }
    
    public String getTypeName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSubtypeName() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isConnectedOrConnecting() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isConnected() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAvailable() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFailover() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRoaming() {
        throw new RuntimeException("Stub!");
    }
    
    public State getState() {
        throw new RuntimeException("Stub!");
    }
    
    public DetailedState getDetailedState() {
        throw new RuntimeException("Stub!");
    }
    
    public String getReason() {
        throw new RuntimeException("Stub!");
    }
    
    public String getExtraInfo() {
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
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public enum State
    {
        CONNECTED, 
        CONNECTING, 
        DISCONNECTED, 
        DISCONNECTING, 
        SUSPENDED, 
        UNKNOWN;
    }
    
    public enum DetailedState
    {
        AUTHENTICATING, 
        BLOCKED, 
        CAPTIVE_PORTAL_CHECK, 
        CONNECTED, 
        CONNECTING, 
        DISCONNECTED, 
        DISCONNECTING, 
        FAILED, 
        IDLE, 
        OBTAINING_IPADDR, 
        SCANNING, 
        SUSPENDED, 
        VERIFYING_POOR_LINK;
    }
}
