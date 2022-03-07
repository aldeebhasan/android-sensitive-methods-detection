package java.lang;

import sun.misc.*;

public final class Float extends Number implements Comparable<Float>
{
    public static final float POSITIVE_INFINITY = 1.0 / 0.0;
    public static final float NEGATIVE_INFINITY = -1.0 / 0.0;
    public static final float NaN = 0.0 / 0.0;
    public static final float MAX_VALUE = 3.4028235E38f;
    public static final float MIN_NORMAL = 1.17549435E-38f;
    public static final float MIN_VALUE = 1.4E-45f;
    public static final int MAX_EXPONENT = 127;
    public static final int MIN_EXPONENT = -126;
    public static final int SIZE = 32;
    public static final int BYTES = 4;
    public static final Class<Float> TYPE;
    private final float value;
    private static final long serialVersionUID = -2671257302660747028L;
    
    public static String toString(final float n) {
        return FloatingDecimal.toJavaFormatString(n);
    }
    
    public static String toHexString(final float n) {
        if (Math.abs(n) < Float.MIN_NORMAL && n != 0.0f) {
            return Double.toHexString(Math.scalb((double)n, -896)).replaceFirst("p-1022$", "p-126");
        }
        return Double.toHexString(n);
    }
    
    public static Float valueOf(final String s) throws NumberFormatException {
        return new Float(parseFloat(s));
    }
    
    public static Float valueOf(final float n) {
        return new Float(n);
    }
    
    public static float parseFloat(final String s) throws NumberFormatException {
        return FloatingDecimal.parseFloat(s);
    }
    
    public static boolean isNaN(final float n) {
        return n != n;
    }
    
    public static boolean isInfinite(final float n) {
        return n == Float.POSITIVE_INFINITY || n == Float.NEGATIVE_INFINITY;
    }
    
    public static boolean isFinite(final float n) {
        return Math.abs(n) <= Float.MAX_VALUE;
    }
    
    public Float(final float value) {
        this.value = value;
    }
    
    public Float(final double n) {
        this.value = (float)n;
    }
    
    public Float(final String s) throws NumberFormatException {
        this.value = parseFloat(s);
    }
    
    public boolean isNaN() {
        return isNaN(this.value);
    }
    
    public boolean isInfinite() {
        return isInfinite(this.value);
    }
    
    @Override
    public String toString() {
        return toString(this.value);
    }
    
    @Override
    public byte byteValue() {
        return (byte)this.value;
    }
    
    @Override
    public short shortValue() {
        return (short)this.value;
    }
    
    @Override
    public int intValue() {
        return (int)this.value;
    }
    
    @Override
    public long longValue() {
        return (long)this.value;
    }
    
    @Override
    public float floatValue() {
        return this.value;
    }
    
    @Override
    public double doubleValue() {
        return this.value;
    }
    
    @Override
    public int hashCode() {
        return hashCode(this.value);
    }
    
    public static int hashCode(final float n) {
        return floatToIntBits(n);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Float && floatToIntBits(((Float)o).value) == floatToIntBits(this.value);
    }
    
    public static int floatToIntBits(final float n) {
        int floatToRawIntBits = floatToRawIntBits(n);
        if ((floatToRawIntBits & 0x7F800000) == 0x7F800000 && (floatToRawIntBits & 0x7FFFFF) != 0x0) {
            floatToRawIntBits = 2143289344;
        }
        return floatToRawIntBits;
    }
    
    public static native int floatToRawIntBits(final float p0);
    
    public static native float intBitsToFloat(final int p0);
    
    @Override
    public int compareTo(final Float n) {
        return compare(this.value, n.value);
    }
    
    public static int compare(final float n, final float n2) {
        if (n < n2) {
            return -1;
        }
        if (n > n2) {
            return 1;
        }
        final int floatToIntBits = floatToIntBits(n);
        final int floatToIntBits2 = floatToIntBits(n2);
        return (floatToIntBits == floatToIntBits2) ? 0 : ((floatToIntBits < floatToIntBits2) ? -1 : 1);
    }
    
    public static float sum(final float n, final float n2) {
        return n + n2;
    }
    
    public static float max(final float n, final float n2) {
        return Math.max(n, n2);
    }
    
    public static float min(final float n, final float n2) {
        return Math.min(n, n2);
    }
    
    static {
        TYPE = Class.getPrimitiveClass("float");
    }
}
