package java.security.spec;

import java.math.*;

public class ECParameterSpec implements AlgorithmParameterSpec
{
    private final EllipticCurve curve;
    private final ECPoint g;
    private final BigInteger n;
    private final int h;
    
    public ECParameterSpec(final EllipticCurve curve, final ECPoint g, final BigInteger n, final int h) {
        if (curve == null) {
            throw new NullPointerException("curve is null");
        }
        if (g == null) {
            throw new NullPointerException("g is null");
        }
        if (n == null) {
            throw new NullPointerException("n is null");
        }
        if (n.signum() != 1) {
            throw new IllegalArgumentException("n is not positive");
        }
        if (h <= 0) {
            throw new IllegalArgumentException("h is not positive");
        }
        this.curve = curve;
        this.g = g;
        this.n = n;
        this.h = h;
    }
    
    public EllipticCurve getCurve() {
        return this.curve;
    }
    
    public ECPoint getGenerator() {
        return this.g;
    }
    
    public BigInteger getOrder() {
        return this.n;
    }
    
    public int getCofactor() {
        return this.h;
    }
}
