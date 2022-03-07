package javax.net.ssl;

import java.net.*;
import java.security.cert.*;
import java.security.*;

public interface X509KeyManager extends KeyManager
{
    String[] getClientAliases(final String p0, final Principal[] p1);
    
    String chooseClientAlias(final String[] p0, final Principal[] p1, final Socket p2);
    
    String[] getServerAliases(final String p0, final Principal[] p1);
    
    String chooseServerAlias(final String p0, final Principal[] p1, final Socket p2);
    
    X509Certificate[] getCertificateChain(final String p0);
    
    PrivateKey getPrivateKey(final String p0);
}
