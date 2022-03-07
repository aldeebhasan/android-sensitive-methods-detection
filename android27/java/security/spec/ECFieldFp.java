package java.security.spec;

import java.math.*;

public class ECFieldFp implements ECField
{
    private BigInteger p;
    
    public ECFieldFp(final BigInteger p) {
        if (p.signum() != 1) {
            throw new IllegalArgumentException("p is not positive");
        }
        this.p = p;
    }
    
    @Override
    public int getFieldSize() {
        return this.p.bitLength();
    }
    
    public BigInteger getP() {
        return this.p;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ECFieldFp && this.p.equals(((ECFieldFp)o).p));
    }
    
    @Override
    public int hashCode() {
        return this.p.hashCode();
    }
}
