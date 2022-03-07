package android.net;

import android.content.*;
import android.app.*;
import java.net.*;
import android.content.pm.*;
import android.os.*;

public class VpnService extends Service
{
    public static final String SERVICE_INTERFACE = "android.net.VpnService";
    public static final String SERVICE_META_DATA_SUPPORTS_ALWAYS_ON = "android.net.VpnService.SUPPORTS_ALWAYS_ON";
    
    public VpnService() {
        throw new RuntimeException("Stub!");
    }
    
    public static Intent prepare(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean protect(final int socket) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean protect(final Socket socket) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean protect(final DatagramSocket socket) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setUnderlyingNetworks(final Network[] networks) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRevoke() {
        throw new RuntimeException("Stub!");
    }
    
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
}
