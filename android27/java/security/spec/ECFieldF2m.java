package java.security.spec;

import java.math.*;
import java.util.*;

public class ECFieldF2m implements ECField
{
    private int m;
    private int[] ks;
    private BigInteger rp;
    
    public ECFieldF2m(final int m) {
        if (m <= 0) {
            throw new IllegalArgumentException("m is not positive");
        }
        this.m = m;
        this.ks = null;
        this.rp = null;
    }
    
    public ECFieldF2m(final int m, final BigInteger rp) {
        this.m = m;
        this.rp = rp;
        if (m <= 0) {
            throw new IllegalArgumentException("m is not positive");
        }
        final int bitCount = this.rp.bitCount();
        if (!this.rp.testBit(0) || !this.rp.testBit(m) || (bitCount != 3 && bitCount != 5)) {
            throw new IllegalArgumentException("rp does not represent a valid reduction polynomial");
        }
        BigInteger bigInteger = this.rp.clearBit(0).clearBit(m);
        this.ks = new int[bitCount - 2];
        for (int i = this.ks.length - 1; i >= 0; --i) {
            final int lowestSetBit = bigInteger.getLowestSetBit();
            this.ks[i] = lowestSetBit;
            bigInteger = bigInteger.clearBit(lowestSetBit);
        }
    }
    
    public ECFieldF2m(final int n, final int[] array) {
        this.m = n;
        this.ks = array.clone();
        if (n <= 0) {
            throw new IllegalArgumentException("m is not positive");
        }
        if (this.ks.length != 1 && this.ks.length != 3) {
            throw new IllegalArgumentException("length of ks is neither 1 nor 3");
        }
        for (int i = 0; i < this.ks.length; ++i) {
            if (this.ks[i] < 1 || this.ks[i] > n - 1) {
                throw new IllegalArgumentException("ks[" + i + "] is out of range");
            }
            if (i != 0 && this.ks[i] >= this.ks[i - 1]) {
                throw new IllegalArgumentException("values in ks are not in descending order");
            }
        }
        this.rp = BigInteger.ONE;
        this.rp = this.rp.setBit(n);
        for (int j = 0; j < this.ks.length; ++j) {
            this.rp = this.rp.setBit(this.ks[j]);
        }
    }
    
    @Override
    public int getFieldSize() {
        return this.m;
    }
    
    public int getM() {
        return this.m;
    }
    
    public BigInteger getReductionPolynomial() {
        return this.rp;
    }
    
    public int[] getMidTermsOfReductionPolynomial() {
        if (this.ks == null) {
            return null;
        }
        return this.ks.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ECFieldF2m && this.m == ((ECFieldF2m)o).m && Arrays.equals(this.ks, ((ECFieldF2m)o).ks));
    }
    
    @Override
    public int hashCode() {
        return (this.m << 5) + ((this.rp == null) ? 0 : this.rp.hashCode());
    }
}
