package javax.net.ssl;

import java.security.*;

public abstract class TrustManagerFactorySpi
{
    protected abstract void engineInit(final KeyStore p0) throws KeyStoreException;
    
    protected abstract void engineInit(final ManagerFactoryParameters p0) throws InvalidAlgorithmParameterException;
    
    protected abstract TrustManager[] engineGetTrustManagers();
}
