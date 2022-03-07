package java.security.spec;

import java.math.*;

public class RSAKeyGenParameterSpec implements AlgorithmParameterSpec
{
    private int keysize;
    private BigInteger publicExponent;
    private AlgorithmParameterSpec keyParams;
    public static final BigInteger F0;
    public static final BigInteger F4;
    
    public RSAKeyGenParameterSpec(final int n, final BigInteger bigInteger) {
        this(n, bigInteger, null);
    }
    
    public RSAKeyGenParameterSpec(final int keysize, final BigInteger publicExponent, final AlgorithmParameterSpec keyParams) {
        this.keysize = keysize;
        this.publicExponent = publicExponent;
        this.keyParams = keyParams;
    }
    
    public int getKeysize() {
        return this.keysize;
    }
    
    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
    
    public AlgorithmParameterSpec getKeyParams() {
        return this.keyParams;
    }
    
    static {
        F0 = BigInteger.valueOf(3L);
        F4 = BigInteger.valueOf(65537L);
    }
}
