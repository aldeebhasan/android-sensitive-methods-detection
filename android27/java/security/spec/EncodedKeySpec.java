package java.security.spec;

public abstract class EncodedKeySpec implements KeySpec
{
    private byte[] encodedKey;
    
    public EncodedKeySpec(final byte[] array) {
        this.encodedKey = array.clone();
    }
    
    public byte[] getEncoded() {
        return this.encodedKey.clone();
    }
    
    public abstract String getFormat();
}
