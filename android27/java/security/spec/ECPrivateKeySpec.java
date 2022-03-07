package java.security.spec;

import java.math.*;

public class ECPrivateKeySpec implements KeySpec
{
    private BigInteger s;
    private ECParameterSpec params;
    
    public ECPrivateKeySpec(final BigInteger s, final ECParameterSpec params) {
        if (s == null) {
            throw new NullPointerException("s is null");
        }
        if (params == null) {
            throw new NullPointerException("params is null");
        }
        this.s = s;
        this.params = params;
    }
    
    public BigInteger getS() {
        return this.s;
    }
    
    public ECParameterSpec getParams() {
        return this.params;
    }
}
