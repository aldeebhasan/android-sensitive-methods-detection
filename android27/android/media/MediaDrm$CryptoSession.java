package android.media;

public final class CryptoSession
{
    CryptoSession() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] encrypt(final byte[] keyid, final byte[] input, final byte[] iv) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] decrypt(final byte[] keyid, final byte[] input, final byte[] iv) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] sign(final byte[] keyid, final byte[] message) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean verify(final byte[] keyid, final byte[] message, final byte[] signature) {
        throw new RuntimeException("Stub!");
    }
}
