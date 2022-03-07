package android.net;

import java.net.*;
import android.os.*;

public final class RouteInfo implements Parcelable
{
    public static final Creator<RouteInfo> CREATOR;
    
    RouteInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public IpPrefix getDestination() {
        throw new RuntimeException("Stub!");
    }
    
    public InetAddress getGateway() {
        throw new RuntimeException("Stub!");
    }
    
    public String getInterface() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDefaultRoute() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean matches(final InetAddress destination) {
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
