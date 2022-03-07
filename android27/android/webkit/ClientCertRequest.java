package android.webkit;

import java.security.*;
import java.security.cert.*;

public abstract class ClientCertRequest
{
    public ClientCertRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract String[] getKeyTypes();
    
    public abstract Principal[] getPrincipals();
    
    public abstract String getHost();
    
    public abstract int getPort();
    
    public abstract void proceed(final PrivateKey p0, final X509Certificate[] p1);
    
    public abstract void ignore();
    
    public abstract void cancel();
}
