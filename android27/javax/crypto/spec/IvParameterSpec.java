package javax.crypto.spec;

import java.security.spec.*;

public class IvParameterSpec implements AlgorithmParameterSpec
{
    private byte[] iv;
    
    public IvParameterSpec(final byte[] array) {
        this(array, 0, array.length);
    }
    
    public IvParameterSpec(final byte[] array, final int n, final int n2) {
        if (array == null) {
            throw new IllegalArgumentException("IV missing");
        }
        if (array.length - n < n2) {
            throw new IllegalArgumentException("IV buffer too short for given offset/length combination");
        }
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("len is negative");
        }
        System.arraycopy(array, n, this.iv = new byte[n2], 0, n2);
    }
    
    public byte[] getIV() {
        return this.iv.clone();
    }
}
