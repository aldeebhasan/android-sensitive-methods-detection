package javax.crypto.spec;

import java.security.spec.*;

public class DHGenParameterSpec implements AlgorithmParameterSpec
{
    private int primeSize;
    private int exponentSize;
    
    public DHGenParameterSpec(final int primeSize, final int exponentSize) {
        this.primeSize = primeSize;
        this.exponentSize = exponentSize;
    }
    
    public int getPrimeSize() {
        return this.primeSize;
    }
    
    public int getExponentSize() {
        return this.exponentSize;
    }
}
