package javax.crypto;

import java.security.spec.*;
import java.security.*;

public abstract class KeyAgreementSpi
{
    protected abstract void engineInit(final Key p0, final SecureRandom p1) throws InvalidKeyException;
    
    protected abstract void engineInit(final Key p0, final AlgorithmParameterSpec p1, final SecureRandom p2) throws InvalidKeyException, InvalidAlgorithmParameterException;
    
    protected abstract Key engineDoPhase(final Key p0, final boolean p1) throws InvalidKeyException, IllegalStateException;
    
    protected abstract byte[] engineGenerateSecret() throws IllegalStateException;
    
    protected abstract int engineGenerateSecret(final byte[] p0, final int p1) throws IllegalStateException, ShortBufferException;
    
    protected abstract SecretKey engineGenerateSecret(final String p0) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException;
}
