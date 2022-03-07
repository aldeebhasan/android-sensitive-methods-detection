package java.security.spec;

public class X509EncodedKeySpec extends EncodedKeySpec
{
    public X509EncodedKeySpec(final byte[] array) {
        super(array);
    }
    
    @Override
    public byte[] getEncoded() {
        return super.getEncoded();
    }
    
    @Override
    public final String getFormat() {
        return "X.509";
    }
}
