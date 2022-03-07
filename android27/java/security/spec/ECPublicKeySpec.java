package java.security.spec;

public class ECPublicKeySpec implements KeySpec
{
    private ECPoint w;
    private ECParameterSpec params;
    
    public ECPublicKeySpec(final ECPoint w, final ECParameterSpec params) {
        if (w == null) {
            throw new NullPointerException("w is null");
        }
        if (params == null) {
            throw new NullPointerException("params is null");
        }
        if (w == ECPoint.POINT_INFINITY) {
            throw new IllegalArgumentException("w is ECPoint.POINT_INFINITY");
        }
        this.w = w;
        this.params = params;
    }
    
    public ECPoint getW() {
        return this.w;
    }
    
    public ECParameterSpec getParams() {
        return this.params;
    }
}
