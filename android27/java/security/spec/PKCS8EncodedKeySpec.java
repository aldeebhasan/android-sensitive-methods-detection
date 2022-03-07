package java.security.spec;

public class PKCS8EncodedKeySpec extends EncodedKeySpec
{
    public PKCS8EncodedKeySpec(final byte[] array) {
        super(array);
    }
    
    @Override
    public byte[] getEncoded() {
        return super.getEncoded();
    }
    
    @Override
    public final String getFormat() {
        return "PKCS#8";
    }
}
