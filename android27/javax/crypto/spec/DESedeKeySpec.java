package javax.crypto.spec;

import java.security.spec.*;
import java.security.*;

public class DESedeKeySpec implements KeySpec
{
    public static final int DES_EDE_KEY_LEN = 24;
    private byte[] key;
    
    public DESedeKeySpec(final byte[] array) throws InvalidKeyException {
        this(array, 0);
    }
    
    public DESedeKeySpec(final byte[] array, final int n) throws InvalidKeyException {
        if (array.length - n < 24) {
            throw new InvalidKeyException("Wrong key size");
        }
        System.arraycopy(array, n, this.key = new byte[24], 0, 24);
    }
    
    public byte[] getKey() {
        return this.key.clone();
    }
    
    public static boolean isParityAdjusted(final byte[] array, final int n) throws InvalidKeyException {
        if (array.length - n < 24) {
            throw new InvalidKeyException("Wrong key size");
        }
        return DESKeySpec.isParityAdjusted(array, n) && DESKeySpec.isParityAdjusted(array, n + 8) && DESKeySpec.isParityAdjusted(array, n + 16);
    }
}
