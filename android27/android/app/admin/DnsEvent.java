package android.app.admin;

import java.util.*;
import java.net.*;
import android.os.*;

public final class DnsEvent extends NetworkEvent implements Parcelable
{
    public static final Creator<DnsEvent> CREATOR;
    
    DnsEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public String getHostname() {
        throw new RuntimeException("Stub!");
    }
    
    public List<InetAddress> getInetAddresses() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTotalResolvedAddressCount() {
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
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
