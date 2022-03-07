package javax.crypto.spec;

import java.security.spec.*;

public class GCMParameterSpec implements AlgorithmParameterSpec
{
    private byte[] iv;
    private int tLen;
    
    public GCMParameterSpec(final int n, final byte[] array) {
        if (array == null) {
            throw new IllegalArgumentException("src array is null");
        }
        this.init(n, array, 0, array.length);
    }
    
    public GCMParameterSpec(final int n, final byte[] array, final int n2, final int n3) {
        this.init(n, array, n2, n3);
    }
    
    private void init(final int tLen, final byte[] array, final int n, final int n2) {
        if (tLen < 0) {
            throw new IllegalArgumentException("Length argument is negative");
        }
        this.tLen = tLen;
        if (array == null || n2 < 0 || n < 0 || n2 > array.length - n) {
            throw new IllegalArgumentException("Invalid buffer arguments");
        }
        System.arraycopy(array, n, this.iv = new byte[n2], 0, n2);
    }
    
    public int getTLen() {
        return this.tLen;
    }
    
    public byte[] getIV() {
        return this.iv.clone();
    }
}
