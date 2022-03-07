package java.security.spec;

import java.math.*;

public class DSAPublicKeySpec implements KeySpec
{
    private BigInteger y;
    private BigInteger p;
    private BigInteger q;
    private BigInteger g;
    
    public DSAPublicKeySpec(final BigInteger y, final BigInteger p4, final BigInteger q, final BigInteger g) {
        this.y = y;
        this.p = p4;
        this.q = q;
        this.g = g;
    }
    
    public BigInteger getY() {
        return this.y;
    }
    
    public BigInteger getP() {
        return this.p;
    }
    
    public BigInteger getQ() {
        return this.q;
    }
    
    public BigInteger getG() {
        return this.g;
    }
}
