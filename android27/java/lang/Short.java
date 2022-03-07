package java.lang;

public final class Short extends Number implements Comparable<Short>
{
    public static final short MIN_VALUE = -32768;
    public static final short MAX_VALUE = 32767;
    public static final Class<Short> TYPE;
    private final short value;
    public static final int SIZE = 16;
    public static final int BYTES = 2;
    private static final long serialVersionUID = 7515723908773894738L;
    
    public static String toString(final short n) {
        return Integer.toString(n, 10);
    }
    
    public static short parseShort(final String s, final int n) throws NumberFormatException {
        final int int1 = Integer.parseInt(s, n);
        if (int1 < -32768 || int1 > 32767) {
            throw new NumberFormatException("Value out of range. Value:\"" + s + "\" Radix:" + n);
        }
        return (short)int1;
    }
    
    public static short parseShort(final String s) throws NumberFormatException {
        return parseShort(s, 10);
    }
    
    public static Short valueOf(final String s, final int n) throws NumberFormatException {
        return parseShort(s, n);
    }
    
    public static Short valueOf(final String s) throws NumberFormatException {
        return valueOf(s, 10);
    }
    
    public static Short valueOf(final short n) {
        if (n >= -128 && n <= 127) {
            return ShortCache.cache[n + 128];
        }
        return new Short(n);
    }
    
    public static Short decode(final String s) throws NumberFormatException {
        final int intValue = Integer.decode(s);
        if (intValue < -32768 || intValue > 32767) {
            throw new NumberFormatException("Value " + intValue + " out of range from input " + s);
        }
        return (short)intValue;
    }
    
    public Short(final short value) {
        this.value = value;
    }
    
    public Short(final String s) throws NumberFormatException {
        this.value = parseShort(s, 10);
    }
    
    @Override
    public byte byteValue() {
        return (byte)this.value;
    }
    
    @Override
    public short shortValue() {
        return this.value;
    }
    
    @Override
    public int intValue() {
        return this.value;
    }
    
    @Override
    public long longValue() {
        return this.value;
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
    public String toString() {
        return Integer.toString(this.value);
    }
    
    @Override
    public int hashCode() {
        return hashCode(this.value);
    }
    
    public static int hashCode(final short n) {
        return n;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Short && this.value == (short)o;
    }
    
    @Override
    public int compareTo(final Short n) {
        return compare(this.value, n.value);
    }
    
    public static int compare(final short n, final short n2) {
        return n - n2;
    }
    
    public static short reverseBytes(final short n) {
        return (short)((n & 0xFF00) >> 8 | n << 8);
    }
    
    public static int toUnsignedInt(final short n) {
        return n & 0xFFFF;
    }
    
    public static long toUnsignedLong(final short n) {
        return n & 0xFFFFL;
    }
    
    static {
        TYPE = Class.getPrimitiveClass("short");
    }
    
    private static class ShortCache
    {
        static final Short[] cache;
        
        static {
            cache = new Short[256];
            for (int i = 0; i < ShortCache.cache.length; ++i) {
                ShortCache.cache[i] = new Short((short)(i - 128));
            }
        }
    }
}
