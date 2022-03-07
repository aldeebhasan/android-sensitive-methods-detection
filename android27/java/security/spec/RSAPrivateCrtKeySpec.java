package java.security.spec;

import java.math.*;

public class RSAPrivateCrtKeySpec extends RSAPrivateKeySpec
{
    private final BigInteger publicExponent;
    private final BigInteger primeP;
    private final BigInteger primeQ;
    private final BigInteger primeExponentP;
    private final BigInteger primeExponentQ;
    private final BigInteger crtCoefficient;
    
    public RSAPrivateCrtKeySpec(final BigInteger bigInteger, final BigInteger bigInteger2, final BigInteger bigInteger3, final BigInteger bigInteger4, final BigInteger bigInteger5, final BigInteger bigInteger6, final BigInteger bigInteger7, final BigInteger bigInteger8) {
        this(bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigInteger6, bigInteger7, bigInteger8, null);
    }
    
    public RSAPrivateCrtKeySpec(final BigInteger bigInteger, final BigInteger publicExponent, final BigInteger bigInteger2, final BigInteger primeP, final BigInteger primeQ, final BigInteger primeExponentP, final BigInteger primeExponentQ, final BigInteger crtCoefficient, final AlgorithmParameterSpec algorithmParameterSpec) {
        super(bigInteger, bigInteger2, algorithmParameterSpec);
        this.publicExponent = publicExponent;
        this.primeP = primeP;
        this.primeQ = primeQ;
        this.primeExponentP = primeExponentP;
        this.primeExponentQ = primeExponentQ;
        this.crtCoefficient = crtCoefficient;
    }
    
    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
    
    public BigInteger getPrimeP() {
        return this.primeP;
    }
    
    public BigInteger getPrimeQ() {
        return this.primeQ;
    }
    
    public BigInteger getPrimeExponentP() {
        return this.primeExponentP;
    }
    
    public BigInteger getPrimeExponentQ() {
        return this.primeExponentQ;
    }
    
    public BigInteger getCrtCoefficient() {
        return this.crtCoefficient;
    }
}
