package javax.crypto.spec;

import java.security.spec.*;
import java.math.*;

public class DHPublicKeySpec implements KeySpec
{
    private BigInteger y;
    private BigInteger p;
    private BigInteger g;
    
    public DHPublicKeySpec(final BigInteger y, final BigInteger p3, final BigInteger g) {
        this.y = y;
        this.p = p3;
        this.g = g;
    }
    
    public BigInteger getY() {
        return this.y;
    }
    
    public BigInteger getP() {
        return this.p;
    }
    
    public BigInteger getG() {
        return this.g;
    }
}
