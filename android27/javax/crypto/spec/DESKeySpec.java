package javax.crypto.spec;

import java.security.spec.*;
import java.security.*;

public class DESKeySpec implements KeySpec
{
    public static final int DES_KEY_LEN = 8;
    private byte[] key;
    private static final byte[][] WEAK_KEYS;
    
    public DESKeySpec(final byte[] array) throws InvalidKeyException {
        this(array, 0);
    }
    
    public DESKeySpec(final byte[] array, final int n) throws InvalidKeyException {
        if (array.length - n < 8) {
            throw new InvalidKeyException("Wrong key size");
        }
        System.arraycopy(array, n, this.key = new byte[8], 0, 8);
    }
    
    public byte[] getKey() {
        return this.key.clone();
    }
    
    public static boolean isParityAdjusted(final byte[] array, int n) throws InvalidKeyException {
        if (array == null) {
            throw new InvalidKeyException("null key");
        }
        if (array.length - n < 8) {
            throw new InvalidKeyException("Wrong key size");
        }
        for (int i = 0; i < 8; ++i) {
            if ((Integer.bitCount(array[n++] & 0xFF) & 0x1) == 0x0) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isWeak(final byte[] array, final int n) throws InvalidKeyException {
        if (array == null) {
            throw new InvalidKeyException("null key");
        }
        if (array.length - n < 8) {
            throw new InvalidKeyException("Wrong key size");
        }
        for (int i = 0; i < DESKeySpec.WEAK_KEYS.length; ++i) {
            boolean b = true;
            for (int n2 = 0; n2 < 8 && b; ++n2) {
                if (DESKeySpec.WEAK_KEYS[i][n2] != array[n2 + n]) {
                    b = false;
                }
            }
            if (b) {
                return b;
            }
        }
        return false;
    }
    
    static {
        WEAK_KEYS = new byte[][] { { 1, 1, 1, 1, 1, 1, 1, 1 }, { -2, -2, -2, -2, -2, -2, -2, -2 }, { 31, 31, 31, 31, 14, 14, 14, 14 }, { -32, -32, -32, -32, -15, -15, -15, -15 }, { 1, -2, 1, -2, 1, -2, 1, -2 }, { 31, -32, 31, -32, 14, -15, 14, -15 }, { 1, -32, 1, -32, 1, -15, 1, -15 }, { 31, -2, 31, -2, 14, -2, 14, -2 }, { 1, 31, 1, 31, 1, 14, 1, 14 }, { -32, -2, -32, -2, -15, -2, -15, -2 }, { -2, 1, -2, 1, -2, 1, -2, 1 }, { -32, 31, -32, 31, -15, 14, -15, 14 }, { -32, 1, -32, 1, -15, 1, -15, 1 }, { -2, 31, -2, 31, -2, 14, -2, 14 }, { 31, 1, 31, 1, 14, 1, 14, 1 }, { -2, -32, -2, -32, -2, -15, -2, -15 } };
    }
}
