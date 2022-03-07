package java.security.spec;

import java.security.interfaces.*;
import java.math.*;

public class DSAParameterSpec implements AlgorithmParameterSpec, DSAParams
{
    BigInteger p;
    BigInteger q;
    BigInteger g;
    
    public DSAParameterSpec(final BigInteger p3, final BigInteger q, final BigInteger g) {
        this.p = p3;
        this.q = q;
        this.g = g;
    }
    
    @Override
    public BigInteger getP() {
        return this.p;
    }
    
    @Override
    public BigInteger getQ() {
        return this.q;
    }
    
    @Override
    public BigInteger getG() {
        return this.g;
    }
}
