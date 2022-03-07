package java.net;

import java.util.*;
import java.security.cert.*;
import javax.net.ssl.*;
import java.security.*;

public abstract class SecureCacheResponse extends CacheResponse
{
    public abstract String getCipherSuite();
    
    public abstract List<Certificate> getLocalCertificateChain();
    
    public abstract List<Certificate> getServerCertificateChain() throws SSLPeerUnverifiedException;
    
    public abstract Principal getPeerPrincipal() throws SSLPeerUnverifiedException;
    
    public abstract Principal getLocalPrincipal();
}
