package java.lang;

import sun.misc.*;

public final class Double extends Number implements Comparable<Double>
{
    public static final double POSITIVE_INFINITY = 1.0 / 0.0;
    public static final double NEGATIVE_INFINITY = -1.0 / 0.0;
    public static final double NaN = 0.0 / 0.0;
    public static final double MAX_VALUE = 1.7976931348623157E308;
    public static final double MIN_NORMAL = 2.2250738585072014E-308;
    public static final double MIN_VALUE = 4.9E-324;
    public static final int MAX_EXPONENT = 1023;
    public static final int MIN_EXPONENT = -1022;
    public static final int SIZE = 64;
    public static final int BYTES = 8;
    public static final Class<Double> TYPE;
    private final double value;
    private static final long serialVersionUID = -9172774392245257468L;
    
    public static String toString(final double n) {
        return FloatingDecimal.toJavaFormatString(n);
    }
    
    public static String toHexString(double abs) {
        if (!isFinite(abs)) {
            return toString(abs);
        }
        final StringBuilder sb = new StringBuilder(24);
        if (Math.copySign(1.0, abs) == -1.0) {
            sb.append("-");
        }
        sb.append("0x");
        abs = Math.abs(abs);
        if (abs == 0.0) {
            sb.append("0.0p0");
        }
        else {
            final boolean b = abs < Double.MIN_NORMAL;
            final long n = (doubleToLongBits(abs) & 0xFFFFFFFFFFFFFL) | 0x1000000000000000L;
            sb.append(b ? "0." : "1.");
            final String substring = Long.toHexString(n).substring(3, 16);
            sb.append(substring.equals("0000000000000") ? "0" : substring.replaceFirst("0{1,12}$", ""));
            sb.append('p');
            sb.append(b ? -1022 : Math.getExponent(abs));
        }
        return sb.toString();
    }
    
    public static Double valueOf(final String s) throws NumberFormatException {
        return new Double(parseDouble(s));
    }
    
    public static Double valueOf(final double n) {
        return new Double(n);
    }
    
    public static double parseDouble(final String s) throws NumberFormatException {
        return FloatingDecimal.parseDouble(s);
    }
    
    public static boolean isNaN(final double n) {
        return n != n;
    }
    
    public static boolean isInfinite(final double n) {
        return n == Double.POSITIVE_INFINITY || n == Double.NEGATIVE_INFINITY;
    }
    
    public static boolean isFinite(final double n) {
        return Math.abs(n) <= Double.MAX_VALUE;
    }
    
    public Double(final double value) {
        this.value = value;
    }
    
    public Double(final String s) throws NumberFormatException {
        this.value = parseDouble(s);
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
        return (float)this.value;
    }
    
    @Override
    public double doubleValue() {
        return this.value;
    }
    
    @Override
    public int hashCode() {
        return hashCode(this.value);
    }
    
    public static int hashCode(final double n) {
        final long doubleToLongBits = doubleToLongBits(n);
        return (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Double && doubleToLongBits(((Double)o).value) == doubleToLongBits(this.value);
    }
    
    public static long doubleToLongBits(final double n) {
        long doubleToRawLongBits = doubleToRawLongBits(n);
        if ((doubleToRawLongBits & 0x7FF0000000000000L) == 0x7FF0000000000000L && (doubleToRawLongBits & 0xFFFFFFFFFFFFFL) != 0x0L) {
            doubleToRawLongBits = 9221120237041090560L;
        }
        return doubleToRawLongBits;
    }
    
    public static native long doubleToRawLongBits(final double p0);
    
    public static native double longBitsToDouble(final long p0);
    
    @Override
    public int compareTo(final Double n) {
        return compare(this.value, n.value);
    }
    
    public static int compare(final double n, final double n2) {
        if (n < n2) {
            return -1;
        }
        if (n > n2) {
            return 1;
        }
        final long doubleToLongBits = doubleToLongBits(n);
        final long doubleToLongBits2 = doubleToLongBits(n2);
        return (doubleToLongBits == doubleToLongBits2) ? 0 : ((doubleToLongBits < doubleToLongBits2) ? -1 : 1);
    }
    
    public static double sum(final double n, final double n2) {
        return n + n2;
    }
    
    public static double max(final double n, final double n2) {
        return Math.max(n, n2);
    }
    
    public static double min(final double n, final double n2) {
        return Math.min(n, n2);
    }
    
    static {
        TYPE = Class.getPrimitiveClass("double");
    }
}
