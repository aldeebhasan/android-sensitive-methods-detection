package javax.net.ssl;

import java.security.*;

public abstract class X509ExtendedKeyManager implements X509KeyManager
{
    public String chooseEngineClientAlias(final String[] array, final Principal[] array2, final SSLEngine sslEngine) {
        return null;
    }
    
    public String chooseEngineServerAlias(final String s, final Principal[] array, final SSLEngine sslEngine) {
        return null;
    }
}
