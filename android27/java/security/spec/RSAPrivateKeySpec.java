package java.security.spec;

import java.math.*;

public class RSAPrivateKeySpec implements KeySpec
{
    private final BigInteger modulus;
    private final BigInteger privateExponent;
    private final AlgorithmParameterSpec params;
    
    public RSAPrivateKeySpec(final BigInteger bigInteger, final BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, null);
    }
    
    public RSAPrivateKeySpec(final BigInteger modulus, final BigInteger privateExponent, final AlgorithmParameterSpec params) {
        this.modulus = modulus;
        this.privateExponent = privateExponent;
        this.params = params;
    }
    
    public BigInteger getModulus() {
        return this.modulus;
    }
    
    public BigInteger getPrivateExponent() {
        return this.privateExponent;
    }
    
    public AlgorithmParameterSpec getParams() {
        return this.params;
    }
}
