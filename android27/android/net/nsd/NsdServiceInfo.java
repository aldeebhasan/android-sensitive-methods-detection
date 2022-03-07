package android.net.nsd;

import java.net.*;
import java.util.*;
import android.os.*;

public final class NsdServiceInfo implements Parcelable
{
    public static final Creator<NsdServiceInfo> CREATOR;
    
    public NsdServiceInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String getServiceName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setServiceName(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public String getServiceType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setServiceType(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public InetAddress getHost() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHost(final InetAddress s) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPort() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPort(final int p) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAttribute(final String key, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeAttribute(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public Map<String, byte[]> getAttributes() {
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
}
