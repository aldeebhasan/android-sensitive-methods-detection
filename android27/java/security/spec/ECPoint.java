package java.security.spec;

import java.math.*;

public class ECPoint
{
    private final BigInteger x;
    private final BigInteger y;
    public static final ECPoint POINT_INFINITY;
    
    private ECPoint() {
        this.x = null;
        this.y = null;
    }
    
    public ECPoint(final BigInteger x, final BigInteger y) {
        if (x == null || y == null) {
            throw new NullPointerException("affine coordinate x or y is null");
        }
        this.x = x;
        this.y = y;
    }
    
    public BigInteger getAffineX() {
        return this.x;
    }
    
    public BigInteger getAffineY() {
        return this.y;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (this != ECPoint.POINT_INFINITY && o instanceof ECPoint && this.x.equals(((ECPoint)o).x) && this.y.equals(((ECPoint)o).y));
    }
    
    @Override
    public int hashCode() {
        if (this == ECPoint.POINT_INFINITY) {
            return 0;
        }
        return this.x.hashCode() << 5 + this.y.hashCode();
    }
    
    static {
        POINT_INFINITY = new ECPoint();
    }
}
