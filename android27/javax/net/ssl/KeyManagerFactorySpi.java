package javax.net.ssl;

import java.security.*;

public abstract class KeyManagerFactorySpi
{
    protected abstract void engineInit(final KeyStore p0, final char[] p1) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException;
    
    protected abstract void engineInit(final ManagerFactoryParameters p0) throws InvalidAlgorithmParameterException;
    
    protected abstract KeyManager[] engineGetKeyManagers();
}
