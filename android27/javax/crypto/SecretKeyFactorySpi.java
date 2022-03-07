package javax.crypto;

import java.security.spec.*;
import java.security.*;

public abstract class SecretKeyFactorySpi
{
    protected abstract SecretKey engineGenerateSecret(final KeySpec p0) throws InvalidKeySpecException;
    
    protected abstract KeySpec engineGetKeySpec(final SecretKey p0, final Class<?> p1) throws InvalidKeySpecException;
    
    protected abstract SecretKey engineTranslateKey(final SecretKey p0) throws InvalidKeyException;
}
