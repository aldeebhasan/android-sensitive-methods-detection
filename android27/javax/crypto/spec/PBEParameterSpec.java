package javax.crypto.spec;

import java.security.spec.*;

public class PBEParameterSpec implements AlgorithmParameterSpec
{
    private byte[] salt;
    private int iterationCount;
    private AlgorithmParameterSpec paramSpec;
    
    public PBEParameterSpec(final byte[] array, final int iterationCount) {
        this.paramSpec = null;
        this.salt = array.clone();
        this.iterationCount = iterationCount;
    }
    
    public PBEParameterSpec(final byte[] array, final int iterationCount, final AlgorithmParameterSpec paramSpec) {
        this.paramSpec = null;
        this.salt = array.clone();
        this.iterationCount = iterationCount;
        this.paramSpec = paramSpec;
    }
    
    public byte[] getSalt() {
        return this.salt.clone();
    }
    
    public int getIterationCount() {
        return this.iterationCount;
    }
    
    public AlgorithmParameterSpec getParameterSpec() {
        return this.paramSpec;
    }
}
