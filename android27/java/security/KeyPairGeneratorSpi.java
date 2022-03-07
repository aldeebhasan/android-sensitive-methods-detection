package java.security;

import java.security.spec.*;

public abstract class KeyPairGeneratorSpi
{
    public abstract void initialize(final int p0, final SecureRandom p1);
    
    public void initialize(final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        throw new UnsupportedOperationException();
    }
    
    public abstract KeyPair generateKeyPair();
}
