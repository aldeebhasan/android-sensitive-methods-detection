package javax.crypto.spec;

import java.security.spec.*;
import java.math.*;

public class DHPrivateKeySpec implements KeySpec
{
    private BigInteger x;
    private BigInteger p;
    private BigInteger g;
    
    public DHPrivateKeySpec(final BigInteger x, final BigInteger p3, final BigInteger g) {
        this.x = x;
        this.p = p3;
        this.g = g;
    }
    
    public BigInteger getX() {
        return this.x;
    }
    
    public BigInteger getP() {
        return this.p;
    }
    
    public BigInteger getG() {
        return this.g;
    }
}
