package java.security;

import java.security.spec.*;

public abstract class KeyFactorySpi
{
    protected abstract PublicKey engineGeneratePublic(final KeySpec p0) throws InvalidKeySpecException;
    
    protected abstract PrivateKey engineGeneratePrivate(final KeySpec p0) throws InvalidKeySpecException;
    
    protected abstract <T extends KeySpec> T engineGetKeySpec(final Key p0, final Class<T> p1) throws InvalidKeySpecException;
    
    protected abstract Key engineTranslateKey(final Key p0) throws InvalidKeyException;
}
