package java.security.spec;

import java.math.*;

public class EllipticCurve
{
    private final ECField field;
    private final BigInteger a;
    private final BigInteger b;
    private final byte[] seed;
    
    private static void checkValidity(final ECField ecField, final BigInteger bigInteger, final String s) {
        if (ecField instanceof ECFieldFp) {
            if (((ECFieldFp)ecField).getP().compareTo(bigInteger) != 1) {
                throw new IllegalArgumentException(s + " is too large");
            }
            if (bigInteger.signum() < 0) {
                throw new IllegalArgumentException(s + " is negative");
            }
        }
        else if (ecField instanceof ECFieldF2m && bigInteger.bitLength() > ((ECFieldF2m)ecField).getM()) {
            throw new IllegalArgumentException(s + " is too large");
        }
    }
    
    public EllipticCurve(final ECField ecField, final BigInteger bigInteger, final BigInteger bigInteger2) {
        this(ecField, bigInteger, bigInteger2, null);
    }
    
    public EllipticCurve(final ECField field, final BigInteger a, final BigInteger b, final byte[] array) {
        if (field == null) {
            throw new NullPointerException("field is null");
        }
        if (a == null) {
            throw new NullPointerException("first coefficient is null");
        }
        if (b == null) {
            throw new NullPointerException("second coefficient is null");
        }
        checkValidity(field, a, "first coefficient");
        checkValidity(field, b, "second coefficient");
        this.field = field;
        this.a = a;
        this.b = b;
        if (array != null) {
            this.seed = array.clone();
        }
        else {
            this.seed = null;
        }
    }
    
    public ECField getField() {
        return this.field;
    }
    
    public BigInteger getA() {
        return this.a;
    }
    
    public BigInteger getB() {
        return this.b;
    }
    
    public byte[] getSeed() {
        if (this.seed == null) {
            return null;
        }
        return this.seed.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof EllipticCurve) {
            final EllipticCurve ellipticCurve = (EllipticCurve)o;
            if (this.field.equals(ellipticCurve.field) && this.a.equals(ellipticCurve.a) && this.b.equals(ellipticCurve.b)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.field.hashCode() << 6 + (this.a.hashCode() << 4) + (this.b.hashCode() << 2);
    }
}
