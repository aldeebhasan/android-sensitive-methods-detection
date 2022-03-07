package java.security;

import java.security.spec.*;

public class AlgorithmParameterGenerator
{
    private Provider provider;
    private AlgorithmParameterGeneratorSpi paramGenSpi;
    private String algorithm;
    
    protected AlgorithmParameterGenerator(final AlgorithmParameterGeneratorSpi paramGenSpi, final Provider provider, final String algorithm) {
        this.paramGenSpi = paramGenSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public static AlgorithmParameterGenerator getInstance(final String s) throws NoSuchAlgorithmException {
        try {
            final Object[] impl = Security.getImpl(s, "AlgorithmParameterGenerator", (String)null);
            return new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)impl[0], (Provider)impl[1], s);
        }
        catch (NoSuchProviderException ex) {
            throw new NoSuchAlgorithmException(s + " not found");
        }
    }
    
    public static AlgorithmParameterGenerator getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (s2 == null || s2.length() == 0) {
            throw new IllegalArgumentException("missing provider");
        }
        final Object[] impl = Security.getImpl(s, "AlgorithmParameterGenerator", s2);
        return new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)impl[0], (Provider)impl[1], s);
    }
    
    public static AlgorithmParameterGenerator getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("missing provider");
        }
        final Object[] impl = Security.getImpl(s, "AlgorithmParameterGenerator", provider);
        return new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)impl[0], (Provider)impl[1], s);
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final void init(final int n) {
        this.paramGenSpi.engineInit(n, new SecureRandom());
    }
    
    public final void init(final int n, final SecureRandom secureRandom) {
        this.paramGenSpi.engineInit(n, secureRandom);
    }
    
    public final void init(final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.paramGenSpi.engineInit(algorithmParameterSpec, new SecureRandom());
    }
    
    public final void init(final AlgorithmParameterSpec algorithmParameterSpec, final SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        this.paramGenSpi.engineInit(algorithmParameterSpec, secureRandom);
    }
    
    public final AlgorithmParameters generateParameters() {
        return this.paramGenSpi.engineGenerateParameters();
    }
}
