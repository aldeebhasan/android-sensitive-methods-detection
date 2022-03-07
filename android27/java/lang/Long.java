package java.lang;

import java.math.*;

public final class Long extends Number implements Comparable<Long>
{
    public static final long MIN_VALUE = -9223372036854775808L;
    public static final long MAX_VALUE = 9223372036854775807L;
    public static final Class<Long> TYPE;
    private final long value;
    public static final int SIZE = 64;
    public static final int BYTES = 8;
    private static final long serialVersionUID = 4290774380558885855L;
    
    public static String toString(long n, int n2) {
        if (n2 < 2 || n2 > 36) {
            n2 = 10;
        }
        if (n2 == 10) {
            return toString(n);
        }
        final char[] array = new char[65];
        int n3 = 64;
        final boolean b = n < 0L;
        if (!b) {
            n = -n;
        }
        while (n <= -n2) {
            array[n3--] = Integer.digits[(int)(-(n % n2))];
            n /= n2;
        }
        array[n3] = Integer.digits[(int)(-n)];
        if (b) {
            array[--n3] = '-';
        }
        return new String(array, n3, 65 - n3);
    }
    
    public static String toUnsignedString(final long n, final int n2) {
        if (n >= 0L) {
            return toString(n, n2);
        }
        switch (n2) {
            case 2: {
                return toBinaryString(n);
            }
            case 4: {
                return toUnsignedString0(n, 2);
            }
            case 8: {
                return toOctalString(n);
            }
            case 10: {
                final long n3 = (n >>> 1) / 5L;
                return toString(n3) + (n - n3 * 10L);
            }
            case 16: {
                return toHexString(n);
            }
            case 32: {
                return toUnsignedString0(n, 5);
            }
            default: {
                return toUnsignedBigInteger(n).toString(n2);
            }
        }
    }
    
    private static BigInteger toUnsignedBigInteger(final long n) {
        if (n >= 0L) {
            return BigInteger.valueOf(n);
        }
        return BigInteger.valueOf(Integer.toUnsignedLong((int)(n >>> 32))).shiftLeft(32).add(BigInteger.valueOf(Integer.toUnsignedLong((int)n)));
    }
    
    public static String toHexString(final long n) {
        return toUnsignedString0(n, 4);
    }
    
    public static String toOctalString(final long n) {
        return toUnsignedString0(n, 3);
    }
    
    public static String toBinaryString(final long n) {
        return toUnsignedString0(n, 1);
    }
    
    static String toUnsignedString0(final long n, final int n2) {
        final int max = Math.max((64 - numberOfLeadingZeros(n) + (n2 - 1)) / n2, 1);
        final char[] array = new char[max];
        formatUnsignedLong(n, n2, array, 0, max);
        return new String(array, true);
    }
    
    static int formatUnsignedLong(long n, final int n2, final char[] array, final int n3, int n4) {
        final int n5 = (1 << n2) - 1;
        do {
            array[n3 + --n4] = Integer.digits[(int)n & n5];
            n >>>= n2;
        } while (n != 0L && n4 > 0);
        return n4;
    }
    
    public static String toString(final long n) {
        if (n == Long.MIN_VALUE) {
            return "-9223372036854775808";
        }
        final int n2 = (n < 0L) ? (stringSize(-n) + 1) : stringSize(n);
        final char[] array = new char[n2];
        getChars(n, n2, array);
        return new String(array, true);
    }
    
    public static String toUnsignedString(final long n) {
        return toUnsignedString(n, 10);
    }
    
    static void getChars(long n, int n2, final char[] array) {
        char c = '\0';
        if (n < 0L) {
            c = '-';
            n = -n;
        }
        while (n > 2147483647L) {
            final long n3 = n / 100L;
            final int n4 = (int)(n - ((n3 << 6) + (n3 << 5) + (n3 << 2)));
            n = n3;
            array[--n2] = Integer.DigitOnes[n4];
            array[--n2] = Integer.DigitTens[n4];
        }
        int i;
        int n5;
        int n6;
        for (i = (int)n; i >= 65536; i = n5, array[--n2] = Integer.DigitOnes[n6], array[--n2] = Integer.DigitTens[n6]) {
            n5 = i / 100;
            n6 = i - ((n5 << 6) + (n5 << 5) + (n5 << 2));
        }
        do {
            final int n7 = i * 52429 >>> 19;
            array[--n2] = Integer.digits[i - ((n7 << 3) + (n7 << 1))];
            i = n7;
        } while (i != 0);
        if (c != '\0') {
            array[--n2] = c;
        }
    }
    
    static int stringSize(final long n) {
        long n2 = 10L;
        for (int i = 1; i < 19; ++i) {
            if (n < n2) {
                return i;
            }
            n2 *= 10L;
        }
        return 19;
    }
    
    public static long parseLong(final String s, final int n) throws NumberFormatException {
        if (s == null) {
            throw new NumberFormatException("null");
        }
        if (n < 2) {
            throw new NumberFormatException("radix " + n + " less than Character.MIN_RADIX");
        }
        if (n > 36) {
            throw new NumberFormatException("radix " + n + " greater than Character.MAX_RADIX");
        }
        long n2 = 0L;
        boolean b = false;
        int i = 0;
        final int length = s.length();
        long n3 = -9223372036854775807L;
        if (length > 0) {
            final char char1 = s.charAt(0);
            if (char1 < '0') {
                if (char1 == '-') {
                    b = true;
                    n3 = Long.MIN_VALUE;
                }
                else if (char1 != '+') {
                    throw NumberFormatException.forInputString(s);
                }
                if (length == 1) {
                    throw NumberFormatException.forInputString(s);
                }
                ++i;
            }
            final long n4 = n3 / n;
            while (i < length) {
                final int digit = Character.digit(s.charAt(i++), n);
                if (digit < 0) {
                    throw NumberFormatException.forInputString(s);
                }
                if (n2 < n4) {
                    throw NumberFormatException.forInputString(s);
                }
                final long n5 = n2 * n;
                if (n5 < n3 + digit) {
                    throw NumberFormatException.forInputString(s);
                }
                n2 = n5 - digit;
            }
            return b ? n2 : (-n2);
        }
        throw NumberFormatException.forInputString(s);
    }
    
    public static long parseLong(final String s) throws NumberFormatException {
        return parseLong(s, 10);
    }
    
    public static long parseUnsignedLong(final String s, final int n) throws NumberFormatException {
        if (s == null) {
            throw new NumberFormatException("null");
        }
        final int length = s.length();
        if (length <= 0) {
            throw NumberFormatException.forInputString(s);
        }
        if (s.charAt(0) == '-') {
            throw new NumberFormatException(String.format("Illegal leading minus sign on unsigned string %s.", s));
        }
        if (length <= 12 || (n == 10 && length <= 18)) {
            return parseLong(s, n);
        }
        final long long1 = parseLong(s.substring(0, length - 1), n);
        final int digit = Character.digit(s.charAt(length - 1), n);
        if (digit < 0) {
            throw new NumberFormatException("Bad digit at end of " + s);
        }
        final long n2 = long1 * n + digit;
        if (compareUnsigned(n2, long1) < 0) {
            throw new NumberFormatException(String.format("String value %s exceeds range of unsigned long.", s));
        }
        return n2;
    }
    
    public static long parseUnsignedLong(final String s) throws NumberFormatException {
        return parseUnsignedLong(s, 10);
    }
    
    public static Long valueOf(final String s, final int n) throws NumberFormatException {
        return parseLong(s, n);
    }
    
    public static Long valueOf(final String s) throws NumberFormatException {
        return parseLong(s, 10);
    }
    
    public static Long valueOf(final long n) {
        if (n >= -128L && n <= 127L) {
            return LongCache.cache[(int)n + 128];
        }
        return new Long(n);
    }
    
    public static Long decode(final String s) throws NumberFormatException {
        int n = 10;
        int n2 = 0;
        boolean b = false;
        if (s.length() == 0) {
            throw new NumberFormatException("Zero length string");
        }
        final char char1 = s.charAt(0);
        if (char1 == '-') {
            b = true;
            ++n2;
        }
        else if (char1 == '+') {
            ++n2;
        }
        if (s.startsWith("0x", n2) || s.startsWith("0X", n2)) {
            n2 += 2;
            n = 16;
        }
        else if (s.startsWith("#", n2)) {
            ++n2;
            n = 16;
        }
        else if (s.startsWith("0", n2) && s.length() > 1 + n2) {
            ++n2;
            n = 8;
        }
        if (s.startsWith("-", n2) || s.startsWith("+", n2)) {
            throw new NumberFormatException("Sign character in wrong position");
        }
        Long value2;
        try {
            final Long value = valueOf(s.substring(n2), n);
            value2 = (b ? (-value) : value);
        }
        catch (NumberFormatException ex) {
            value2 = valueOf(b ? ("-" + s.substring(n2)) : s.substring(n2), n);
        }
        return value2;
    }
    
    public Long(final long value) {
        this.value = value;
    }
    
    public Long(final String s) throws NumberFormatException {
        this.value = parseLong(s, 10);
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
        return toString(this.value);
    }
    
    @Override
    public int hashCode() {
        return hashCode(this.value);
    }
    
    public static int hashCode(final long n) {
        return (int)(n ^ n >>> 32);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Long && this.value == (long)o;
    }
    
    public static Long getLong(final String s) {
        return getLong(s, null);
    }
    
    public static Long getLong(final String s, final long n) {
        final Long long1 = getLong(s, null);
        return (long1 == null) ? n : long1;
    }
    
    public static Long getLong(final String s, final Long n) {
        String property = null;
        try {
            property = System.getProperty(s);
        }
        catch (IllegalArgumentException ex) {}
        catch (NullPointerException ex2) {}
        if (property != null) {
            try {
                return decode(property);
            }
            catch (NumberFormatException ex3) {}
        }
        return n;
    }
    
    @Override
    public int compareTo(final Long n) {
        return compare(this.value, n.value);
    }
    
    public static int compare(final long n, final long n2) {
        return (n < n2) ? -1 : ((n == n2) ? 0 : 1);
    }
    
    public static int compareUnsigned(final long n, final long n2) {
        return compare(n + Long.MIN_VALUE, n2 + Long.MIN_VALUE);
    }
    
    public static long divideUnsigned(final long n, final long n2) {
        if (n2 < 0L) {
            return (compareUnsigned(n, n2) >= 0) ? 1 : 0;
        }
        if (n > 0L) {
            return n / n2;
        }
        return toUnsignedBigInteger(n).divide(toUnsignedBigInteger(n2)).longValue();
    }
    
    public static long remainderUnsigned(final long n, final long n2) {
        if (n > 0L && n2 > 0L) {
            return n % n2;
        }
        if (compareUnsigned(n, n2) < 0) {
            return n;
        }
        return toUnsignedBigInteger(n).remainder(toUnsignedBigInteger(n2)).longValue();
    }
    
    public static long highestOneBit(long n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        n |= n >> 32;
        return n - (n >>> 1);
    }
    
    public static long lowestOneBit(final long n) {
        return n & -n;
    }
    
    public static int numberOfLeadingZeros(final long n) {
        if (n == 0L) {
            return 64;
        }
        int n2 = 1;
        int n3 = (int)(n >>> 32);
        if (n3 == 0) {
            n2 += 32;
            n3 = (int)n;
        }
        if (n3 >>> 16 == 0) {
            n2 += 16;
            n3 <<= 16;
        }
        if (n3 >>> 24 == 0) {
            n2 += 8;
            n3 <<= 8;
        }
        if (n3 >>> 28 == 0) {
            n2 += 4;
            n3 <<= 4;
        }
        if (n3 >>> 30 == 0) {
            n2 += 2;
            n3 <<= 2;
        }
        return n2 - (n3 >>> 31);
    }
    
    public static int numberOfTrailingZeros(final long n) {
        if (n == 0L) {
            return 64;
        }
        int n2 = 63;
        final int n3 = (int)n;
        int n4;
        if (n3 != 0) {
            n2 -= 32;
            n4 = n3;
        }
        else {
            n4 = (int)(n >>> 32);
        }
        final int n5 = n4 << 16;
        if (n5 != 0) {
            n2 -= 16;
            n4 = n5;
        }
        final int n6 = n4 << 8;
        if (n6 != 0) {
            n2 -= 8;
            n4 = n6;
        }
        final int n7 = n4 << 4;
        if (n7 != 0) {
            n2 -= 4;
            n4 = n7;
        }
        final int n8 = n4 << 2;
        if (n8 != 0) {
            n2 -= 2;
            n4 = n8;
        }
        return n2 - (n4 << 1 >>> 31);
    }
    
    public static int bitCount(long n) {
        n -= (n >>> 1 & 0x5555555555555555L);
        n = (n & 0x3333333333333333L) + (n >>> 2 & 0x3333333333333333L);
        n = (n + (n >>> 4) & 0xF0F0F0F0F0F0F0FL);
        n += n >>> 8;
        n += n >>> 16;
        n += n >>> 32;
        return (int)n & 0x7F;
    }
    
    public static long rotateLeft(final long n, final int n2) {
        return n << n2 | n >>> -n2;
    }
    
    public static long rotateRight(final long n, final int n2) {
        return n >>> n2 | n << -n2;
    }
    
    public static long reverse(long n) {
        n = ((n & 0x5555555555555555L) << 1 | (n >>> 1 & 0x5555555555555555L));
        n = ((n & 0x3333333333333333L) << 2 | (n >>> 2 & 0x3333333333333333L));
        n = ((n & 0xF0F0F0F0F0F0F0FL) << 4 | (n >>> 4 & 0xF0F0F0F0F0F0F0FL));
        n = ((n & 0xFF00FF00FF00FFL) << 8 | (n >>> 8 & 0xFF00FF00FF00FFL));
        n = (n << 48 | (n & 0xFFFF0000L) << 16 | (n >>> 16 & 0xFFFF0000L) | n >>> 48);
        return n;
    }
    
    public static int signum(final long n) {
        return (int)(n >> 63 | -n >>> 63);
    }
    
    public static long reverseBytes(long n) {
        n = ((n & 0xFF00FF00FF00FFL) << 8 | (n >>> 8 & 0xFF00FF00FF00FFL));
        return n << 48 | (n & 0xFFFF0000L) << 16 | (n >>> 16 & 0xFFFF0000L) | n >>> 48;
    }
    
    public static long sum(final long n, final long n2) {
        return n + n2;
    }
    
    public static long max(final long n, final long n2) {
        return Math.max(n, n2);
    }
    
    public static long min(final long n, final long n2) {
        return Math.min(n, n2);
    }
    
    static {
        TYPE = Class.getPrimitiveClass("long");
    }
    
    private static class LongCache
    {
        static final Long[] cache;
        
        static {
            cache = new Long[256];
            for (int i = 0; i < LongCache.cache.length; ++i) {
                LongCache.cache[i] = new Long(i - 128);
            }
        }
    }
}
