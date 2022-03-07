package java.security;

import java.security.spec.*;
import java.io.*;

public class AlgorithmParameters
{
    private Provider provider;
    private AlgorithmParametersSpi paramSpi;
    private String algorithm;
    private boolean initialized;
    
    protected AlgorithmParameters(final AlgorithmParametersSpi paramSpi, final Provider provider, final String algorithm) {
        this.initialized = false;
        this.paramSpi = paramSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public static AlgorithmParameters getInstance(final String s) throws NoSuchAlgorithmException {
        try {
            final Object[] impl = Security.getImpl(s, "AlgorithmParameters", (String)null);
            return new AlgorithmParameters((AlgorithmParametersSpi)impl[0], (Provider)impl[1], s);
        }
        catch (NoSuchProviderException ex) {
            throw new NoSuchAlgorithmException(s + " not found");
        }
    }
    
    public static AlgorithmParameters getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (s2 == null || s2.length() == 0) {
            throw new IllegalArgumentException("missing provider");
        }
        final Object[] impl = Security.getImpl(s, "AlgorithmParameters", s2);
        return new AlgorithmParameters((AlgorithmParametersSpi)impl[0], (Provider)impl[1], s);
    }
    
    public static AlgorithmParameters getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("missing provider");
        }
        final Object[] impl = Security.getImpl(s, "AlgorithmParameters", provider);
        return new AlgorithmParameters((AlgorithmParametersSpi)impl[0], (Provider)impl[1], s);
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final void init(final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (this.initialized) {
            throw new InvalidParameterSpecException("already initialized");
        }
        this.paramSpi.engineInit(algorithmParameterSpec);
        this.initialized = true;
    }
    
    public final void init(final byte[] array) throws IOException {
        if (this.initialized) {
            throw new IOException("already initialized");
        }
        this.paramSpi.engineInit(array);
        this.initialized = true;
    }
    
    public final void init(final byte[] array, final String s) throws IOException {
        if (this.initialized) {
            throw new IOException("already initialized");
        }
        this.paramSpi.engineInit(array, s);
        this.initialized = true;
    }
    
    public final <T extends AlgorithmParameterSpec> T getParameterSpec(final Class<T> clazz) throws InvalidParameterSpecException {
        if (!this.initialized) {
            throw new InvalidParameterSpecException("not initialized");
        }
        return this.paramSpi.engineGetParameterSpec(clazz);
    }
    
    public final byte[] getEncoded() throws IOException {
        if (!this.initialized) {
            throw new IOException("not initialized");
        }
        return this.paramSpi.engineGetEncoded();
    }
    
    public final byte[] getEncoded(final String s) throws IOException {
        if (!this.initialized) {
            throw new IOException("not initialized");
        }
        return this.paramSpi.engineGetEncoded(s);
    }
    
    @Override
    public final String toString() {
        if (!this.initialized) {
            return null;
        }
        return this.paramSpi.engineToString();
    }
}
