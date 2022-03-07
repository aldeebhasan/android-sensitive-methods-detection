package java.lang;

public final class Byte extends Number implements Comparable<Byte>
{
    public static final byte MIN_VALUE = -128;
    public static final byte MAX_VALUE = 127;
    public static final Class<Byte> TYPE;
    private final byte value;
    public static final int SIZE = 8;
    public static final int BYTES = 1;
    private static final long serialVersionUID = -7183698231559129828L;
    
    public static String toString(final byte b) {
        return Integer.toString(b, 10);
    }
    
    public static Byte valueOf(final byte b) {
        return ByteCache.cache[b + 128];
    }
    
    public static byte parseByte(final String s, final int n) throws NumberFormatException {
        final int int1 = Integer.parseInt(s, n);
        if (int1 < -128 || int1 > 127) {
            throw new NumberFormatException("Value out of range. Value:\"" + s + "\" Radix:" + n);
        }
        return (byte)int1;
    }
    
    public static byte parseByte(final String s) throws NumberFormatException {
        return parseByte(s, 10);
    }
    
    public static Byte valueOf(final String s, final int n) throws NumberFormatException {
        return parseByte(s, n);
    }
    
    public static Byte valueOf(final String s) throws NumberFormatException {
        return valueOf(s, 10);
    }
    
    public static Byte decode(final String s) throws NumberFormatException {
        final int intValue = Integer.decode(s);
        if (intValue < -128 || intValue > 127) {
            throw new NumberFormatException("Value " + intValue + " out of range from input " + s);
        }
        return (byte)intValue;
    }
    
    public Byte(final byte value) {
        this.value = value;
    }
    
    public Byte(final String s) throws NumberFormatException {
        this.value = parseByte(s, 10);
    }
    
    @Override
    public byte byteValue() {
        return this.value;
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
    
    public static int hashCode(final byte b) {
        return b;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Byte && this.value == (byte)o;
    }
    
    @Override
    public int compareTo(final Byte b) {
        return compare(this.value, b.value);
    }
    
    public static int compare(final byte b, final byte b2) {
        return b - b2;
    }
    
    public static int toUnsignedInt(final byte b) {
        return b & 0xFF;
    }
    
    public static long toUnsignedLong(final byte b) {
        return b & 0xFFL;
    }
    
    static {
        TYPE = Class.getPrimitiveClass("byte");
    }
    
    private static class ByteCache
    {
        static final Byte[] cache;
        
        static {
            cache = new Byte[256];
            for (int i = 0; i < ByteCache.cache.length; ++i) {
                ByteCache.cache[i] = new Byte((byte)(i - 128));
            }
        }
    }
}
