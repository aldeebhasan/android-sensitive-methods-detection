package android.net;

import java.util.*;
import android.os.*;

public class ProxyInfo implements Parcelable
{
    public static final Creator<ProxyInfo> CREATOR;
    
    ProxyInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public static ProxyInfo buildDirectProxy(final String host, final int port) {
        throw new RuntimeException("Stub!");
    }
    
    public static ProxyInfo buildDirectProxy(final String host, final int port, final List<String> exclList) {
        throw new RuntimeException("Stub!");
    }
    
    public static ProxyInfo buildPacProxy(final Uri pacUri) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getPacFileUrl() {
        throw new RuntimeException("Stub!");
    }
    
    public String getHost() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPort() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getExclusionList() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
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
