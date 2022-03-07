package javax.crypto;

import sun.security.jca.*;
import java.security.spec.*;
import java.security.*;

public class ExemptionMechanism
{
    private Provider provider;
    private ExemptionMechanismSpi exmechSpi;
    private String mechanism;
    private boolean done;
    private boolean initialized;
    private Key keyStored;
    
    protected ExemptionMechanism(final ExemptionMechanismSpi exmechSpi, final Provider provider, final String mechanism) {
        this.done = false;
        this.initialized = false;
        this.keyStored = null;
        this.exmechSpi = exmechSpi;
        this.provider = provider;
        this.mechanism = mechanism;
    }
    
    public final String getName() {
        return this.mechanism;
    }
    
    public static final ExemptionMechanism getInstance(final String s) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = JceSecurity.getInstance("ExemptionMechanism", ExemptionMechanismSpi.class, s);
        return new ExemptionMechanism((ExemptionMechanismSpi)instance.impl, instance.provider, s);
    }
    
    public static final ExemptionMechanism getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = JceSecurity.getInstance("ExemptionMechanism", ExemptionMechanismSpi.class, s, s2);
        return new ExemptionMechanism((ExemptionMechanismSpi)instance.impl, instance.provider, s);
    }
    
    public static final ExemptionMechanism getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = JceSecurity.getInstance("ExemptionMechanism", ExemptionMechanismSpi.class, s, provider);
        return new ExemptionMechanism((ExemptionMechanismSpi)instance.impl, instance.provider, s);
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final boolean isCryptoAllowed(final Key key) throws ExemptionMechanismException {
        boolean equals = false;
        if (this.done && key != null) {
            equals = this.keyStored.equals(key);
        }
        return equals;
    }
    
    public final int getOutputSize(final int n) throws IllegalStateException {
        if (!this.initialized) {
            throw new IllegalStateException("ExemptionMechanism not initialized");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Input size must be equal to or greater than zero");
        }
        return this.exmechSpi.engineGetOutputSize(n);
    }
    
    public final void init(final Key keyStored) throws InvalidKeyException, ExemptionMechanismException {
        this.done = false;
        this.initialized = false;
        this.keyStored = keyStored;
        this.exmechSpi.engineInit(keyStored);
        this.initialized = true;
    }
    
    public final void init(final Key keyStored, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException, ExemptionMechanismException {
        this.done = false;
        this.initialized = false;
        this.keyStored = keyStored;
        this.exmechSpi.engineInit(keyStored, algorithmParameterSpec);
        this.initialized = true;
    }
    
    public final void init(final Key keyStored, final AlgorithmParameters algorithmParameters) throws InvalidKeyException, InvalidAlgorithmParameterException, ExemptionMechanismException {
        this.done = false;
        this.initialized = false;
        this.keyStored = keyStored;
        this.exmechSpi.engineInit(keyStored, algorithmParameters);
        this.initialized = true;
    }
    
    public final byte[] genExemptionBlob() throws IllegalStateException, ExemptionMechanismException {
        if (!this.initialized) {
            throw new IllegalStateException("ExemptionMechanism not initialized");
        }
        final byte[] engineGenExemptionBlob = this.exmechSpi.engineGenExemptionBlob();
        this.done = true;
        return engineGenExemptionBlob;
    }
    
    public final int genExemptionBlob(final byte[] array) throws IllegalStateException, ShortBufferException, ExemptionMechanismException {
        if (!this.initialized) {
            throw new IllegalStateException("ExemptionMechanism not initialized");
        }
        final int engineGenExemptionBlob = this.exmechSpi.engineGenExemptionBlob(array, 0);
        this.done = true;
        return engineGenExemptionBlob;
    }
    
    public final int genExemptionBlob(final byte[] array, final int n) throws IllegalStateException, ShortBufferException, ExemptionMechanismException {
        if (!this.initialized) {
            throw new IllegalStateException("ExemptionMechanism not initialized");
        }
        final int engineGenExemptionBlob = this.exmechSpi.engineGenExemptionBlob(array, n);
        this.done = true;
        return engineGenExemptionBlob;
    }
    
    @Override
    protected void finalize() {
        this.keyStored = null;
    }
}
