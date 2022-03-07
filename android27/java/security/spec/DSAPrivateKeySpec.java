package java.security.spec;

import java.math.*;

public class DSAPrivateKeySpec implements KeySpec
{
    private BigInteger x;
    private BigInteger p;
    private BigInteger q;
    private BigInteger g;
    
    public DSAPrivateKeySpec(final BigInteger x, final BigInteger p4, final BigInteger q, final BigInteger g) {
        this.x = x;
        this.p = p4;
        this.q = q;
        this.g = g;
    }
    
    public BigInteger getX() {
        return this.x;
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
