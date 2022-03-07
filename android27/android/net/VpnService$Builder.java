package android.net;

import android.app.*;
import java.net.*;
import android.content.pm.*;
import android.os.*;

public class Builder
{
    public Builder() {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSession(final String session) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setConfigureIntent(final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setMtu(final int mtu) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addAddress(final InetAddress address, final int prefixLength) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addAddress(final String address, final int prefixLength) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addRoute(final InetAddress address, final int prefixLength) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addRoute(final String address, final int prefixLength) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addDnsServer(final InetAddress address) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addDnsServer(final String address) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addSearchDomain(final String domain) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder allowFamily(final int family) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addAllowedApplication(final String packageName) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addDisallowedApplication(final String packageName) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public Builder allowBypass() {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setBlocking(final boolean blocking) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setUnderlyingNetworks(final Network[] networks) {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelFileDescriptor establish() {
        throw new RuntimeException("Stub!");
    }
}
