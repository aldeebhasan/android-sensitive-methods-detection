package javax.crypto.spec;

import java.security.spec.*;
import java.math.*;

public class DHParameterSpec implements AlgorithmParameterSpec
{
    private BigInteger p;
    private BigInteger g;
    private int l;
    
    public DHParameterSpec(final BigInteger p2, final BigInteger g) {
        this.p = p2;
        this.g = g;
        this.l = 0;
    }
    
    public DHParameterSpec(final BigInteger p3, final BigInteger g, final int l) {
        this.p = p3;
        this.g = g;
        this.l = l;
    }
    
    public BigInteger getP() {
        return this.p;
    }
    
    public BigInteger getG() {
        return this.g;
    }
    
    public int getL() {
        return this.l;
    }
}
