package android.media;

public static final class CryptoInfo
{
    public byte[] iv;
    public byte[] key;
    public int mode;
    public int[] numBytesOfClearData;
    public int[] numBytesOfEncryptedData;
    public int numSubSamples;
    
    public CryptoInfo() {
        this.iv = null;
        this.key = null;
        this.numBytesOfClearData = null;
        this.numBytesOfEncryptedData = null;
        throw new RuntimeException("Stub!");
    }
    
    public void set(final int newNumSubSamples, final int[] newNumBytesOfClearData, final int[] newNumBytesOfEncryptedData, final byte[] newKey, final byte[] newIV, final int newMode) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPattern(final Pattern newPattern) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Pattern
    {
        public Pattern(final int blocksToEncrypt, final int blocksToSkip) {
            throw new RuntimeException("Stub!");
        }
        
        public void set(final int blocksToEncrypt, final int blocksToSkip) {
            throw new RuntimeException("Stub!");
        }
        
        public int getSkipBlocks() {
            throw new RuntimeException("Stub!");
        }
        
        public int getEncryptBlocks() {
            throw new RuntimeException("Stub!");
        }
    }
}
