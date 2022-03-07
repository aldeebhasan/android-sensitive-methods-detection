package android.net;

import java.net.*;
import android.os.*;

public final class IpPrefix implements Parcelable
{
    public static final Creator<IpPrefix> CREATOR;
    
    IpPrefix() {
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
    
    public InetAddress getAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getRawAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPrefixLength() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean contains(final InetAddress address) {
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
