package java.security;

import java.security.spec.*;

public abstract class AlgorithmParameterGeneratorSpi
{
    protected abstract void engineInit(final int p0, final SecureRandom p1);
    
    protected abstract void engineInit(final AlgorithmParameterSpec p0, final SecureRandom p1) throws InvalidAlgorithmParameterException;
    
    protected abstract AlgorithmParameters engineGenerateParameters();
}
