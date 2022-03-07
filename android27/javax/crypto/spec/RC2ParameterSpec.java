package javax.crypto.spec;

import java.security.spec.*;
import java.util.*;

public class RC2ParameterSpec implements AlgorithmParameterSpec
{
    private byte[] iv;
    private int effectiveKeyBits;
    
    public RC2ParameterSpec(final int effectiveKeyBits) {
        this.iv = null;
        this.effectiveKeyBits = effectiveKeyBits;
    }
    
    public RC2ParameterSpec(final int n, final byte[] array) {
        this(n, array, 0);
    }
    
    public RC2ParameterSpec(final int effectiveKeyBits, final byte[] array, final int n) {
        this.iv = null;
        this.effectiveKeyBits = effectiveKeyBits;
        if (array == null) {
            throw new IllegalArgumentException("IV missing");
        }
        final int n2 = 8;
        if (array.length - n < n2) {
            throw new IllegalArgumentException("IV too short");
        }
        System.arraycopy(array, n, this.iv = new byte[n2], 0, n2);
    }
    
    public int getEffectiveKeyBits() {
        return this.effectiveKeyBits;
    }
    
    public byte[] getIV() {
        return (byte[])((this.iv == null) ? null : ((byte[])this.iv.clone()));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RC2ParameterSpec)) {
            return false;
        }
        final RC2ParameterSpec rc2ParameterSpec = (RC2ParameterSpec)o;
        return this.effectiveKeyBits == rc2ParameterSpec.effectiveKeyBits && Arrays.equals(this.iv, rc2ParameterSpec.iv);
    }
    
    @Override
    public int hashCode() {
        byte b = 0;
        if (this.iv != null) {
            for (byte b2 = 1; b2 < this.iv.length; ++b2) {
                b += (byte)(this.iv[b2] * b2);
            }
        }
        return b + this.effectiveKeyBits;
    }
}
