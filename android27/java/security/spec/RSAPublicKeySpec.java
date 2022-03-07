package java.security.spec;

import java.math.*;

public class RSAPublicKeySpec implements KeySpec
{
    private final BigInteger modulus;
    private final BigInteger publicExponent;
    private final AlgorithmParameterSpec params;
    
    public RSAPublicKeySpec(final BigInteger bigInteger, final BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, null);
    }
    
    public RSAPublicKeySpec(final BigInteger modulus, final BigInteger publicExponent, final AlgorithmParameterSpec params) {
        this.modulus = modulus;
        this.publicExponent = publicExponent;
        this.params = params;
    }
    
    public BigInteger getModulus() {
        return this.modulus;
    }
    
    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
    
    public AlgorithmParameterSpec getParams() {
        return this.params;
    }
}
