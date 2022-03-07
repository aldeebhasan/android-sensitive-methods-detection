package java.security.spec;

import java.math.*;
import java.util.*;

public class RSAMultiPrimePrivateCrtKeySpec extends RSAPrivateKeySpec
{
    private final BigInteger publicExponent;
    private final BigInteger primeP;
    private final BigInteger primeQ;
    private final BigInteger primeExponentP;
    private final BigInteger primeExponentQ;
    private final BigInteger crtCoefficient;
    private final RSAOtherPrimeInfo[] otherPrimeInfo;
    
    public RSAMultiPrimePrivateCrtKeySpec(final BigInteger bigInteger, final BigInteger bigInteger2, final BigInteger bigInteger3, final BigInteger bigInteger4, final BigInteger bigInteger5, final BigInteger bigInteger6, final BigInteger bigInteger7, final BigInteger bigInteger8, final RSAOtherPrimeInfo[] array) {
        this(bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigInteger6, bigInteger7, bigInteger8, array, null);
    }
    
    public RSAMultiPrimePrivateCrtKeySpec(final BigInteger bigInteger, final BigInteger bigInteger2, final BigInteger bigInteger3, final BigInteger bigInteger4, final BigInteger bigInteger5, final BigInteger bigInteger6, final BigInteger bigInteger7, final BigInteger bigInteger8, final RSAOtherPrimeInfo[] array, final AlgorithmParameterSpec algorithmParameterSpec) {
        super(bigInteger, bigInteger3, algorithmParameterSpec);
        Objects.requireNonNull(bigInteger, "the modulus parameter must be non-null");
        Objects.requireNonNull(bigInteger3, "the privateExponent parameter must be non-null");
        this.publicExponent = Objects.requireNonNull(bigInteger2, "the publicExponent parameter must be non-null");
        this.primeP = Objects.requireNonNull(bigInteger4, "the primeP parameter must be non-null");
        this.primeQ = Objects.requireNonNull(bigInteger5, "the primeQ parameter must be non-null");
        this.primeExponentP = Objects.requireNonNull(bigInteger6, "the primeExponentP parameter must be non-null");
        this.primeExponentQ = Objects.requireNonNull(bigInteger7, "the primeExponentQ parameter must be non-null");
        this.crtCoefficient = Objects.requireNonNull(bigInteger8, "the crtCoefficient parameter must be non-null");
        if (array == null) {
            this.otherPrimeInfo = null;
        }
        else {
            if (array.length == 0) {
                throw new IllegalArgumentException("the otherPrimeInfo parameter must not be empty");
            }
            this.otherPrimeInfo = array.clone();
        }
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
    
    public RSAOtherPrimeInfo[] getOtherPrimeInfo() {
        if (this.otherPrimeInfo == null) {
            return null;
        }
        return this.otherPrimeInfo.clone();
    }
}
