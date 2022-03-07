package android.net;

import java.util.*;
import java.net.*;
import android.os.*;

public final class LinkProperties implements Parcelable
{
    public static final Creator<LinkProperties> CREATOR;
    
    LinkProperties() {
        throw new RuntimeException("Stub!");
    }
    
    public String getInterfaceName() {
        throw new RuntimeException("Stub!");
    }
    
    public List<LinkAddress> getLinkAddresses() {
        throw new RuntimeException("Stub!");
    }
    
    public List<InetAddress> getDnsServers() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDomains() {
        throw new RuntimeException("Stub!");
    }
    
    public List<RouteInfo> getRoutes() {
        throw new RuntimeException("Stub!");
    }
    
    public ProxyInfo getHttpProxy() {
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
    
    @Override
    public boolean equals(final Object obj) {
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
    
    static {
        CREATOR = null;
    }
}
