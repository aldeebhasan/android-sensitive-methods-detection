package java.net;

import sun.security.util.*;
import java.security.*;
import java.util.*;
import java.io.*;

public abstract class ProxySelector
{
    private static ProxySelector theProxySelector;
    
    public static ProxySelector getDefault() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.GET_PROXYSELECTOR_PERMISSION);
        }
        return ProxySelector.theProxySelector;
    }
    
    public static void setDefault(final ProxySelector theProxySelector) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.SET_PROXYSELECTOR_PERMISSION);
        }
        ProxySelector.theProxySelector = theProxySelector;
    }
    
    public abstract List<Proxy> select(final URI p0);
    
    public abstract void connectFailed(final URI p0, final SocketAddress p1, final IOException p2);
    
    static {
        try {
            final Class<?> forName = Class.forName("sun.net.spi.DefaultProxySelector");
            if (forName != null && ProxySelector.class.isAssignableFrom(forName)) {
                ProxySelector.theProxySelector = (ProxySelector)forName.newInstance();
            }
        }
        catch (Exception ex) {
            ProxySelector.theProxySelector = null;
        }
    }
}
