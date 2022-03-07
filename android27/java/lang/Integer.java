package java.lang;

import sun.misc.*;

public final class Integer extends Number implements Comparable<Integer>
{
    public static final int MIN_VALUE = -2147483648;
    public static final int MAX_VALUE = 2147483647;
    public static final Class<Integer> TYPE;
    static final char[] digits;
    static final char[] DigitTens;
    static final char[] DigitOnes;
    static final int[] sizeTable;
    private final int value;
    public static final int SIZE = 32;
    public static final int BYTES = 4;
    private static final long serialVersionUID = 1360826667806852920L;
    
    public static String toString(int i, int n) {
        if (n < 2 || n > 36) {
            n = 10;
        }
        if (n == 10) {
            return toString(i);
        }
        final char[] array = new char[33];
        final boolean b = i < 0;
        int n2 = 32;
        if (!b) {
            i = -i;
        }
        while (i <= -n) {
            array[n2--] = Integer.digits[-(i % n)];
            i /= n;
        }
        array[n2] = Integer.digits[-i];
        if (b) {
            array[--n2] = '-';
        }
        return new String(array, n2, 33 - n2);
    }
    
    public static String toUnsignedString(final int n, final int n2) {
        return Long.toUnsignedString(toUnsignedLong(n), n2);
    }
    
    public static String toHexString(final int n) {
        return toUnsignedString0(n, 4);
    }
    
    public static String toOctalString(final int n) {
        return toUnsignedString0(n, 3);
    }
    
    public static String toBinaryString(final int n) {
        return toUnsignedString0(n, 1);
    }
    
    private static String toUnsignedString0(final int n, final int n2) {
        final int max = Math.max((32 - numberOfLeadingZeros(n) + (n2 - 1)) / n2, 1);
        final char[] array = new char[max];
        formatUnsignedInt(n, n2, array, 0, max);
        return new String(array, true);
    }
    
    static int formatUnsignedInt(int n, final int n2, final char[] array, final int n3, int n4) {
        final int n5 = (1 << n2) - 1;
        do {
            array[n3 + --n4] = Integer.digits[n & n5];
            n >>>= n2;
        } while (n != 0 && n4 > 0);
        return n4;
    }
    
    public static String toString(final int n) {
        if (n == Integer.MIN_VALUE) {
            return "-2147483648";
        }
        final int n2 = (n < 0) ? (stringSize(-n) + 1) : stringSize(n);
        final char[] array = new char[n2];
        getChars(n, n2, array);
        return new String(array, true);
    }
    
    public static String toUnsignedString(final int n) {
        return Long.toString(toUnsignedLong(n));
    }
    
    static void getChars(int i, int n, final char[] array) {
        char c = '\0';
        if (i < 0) {
            c = '-';
            i = -i;
        }
        while (i >= 65536) {
            final int n2 = i / 100;
            final int n3 = i - ((n2 << 6) + (n2 << 5) + (n2 << 2));
            i = n2;
            array[--n] = Integer.DigitOnes[n3];
            array[--n] = Integer.DigitTens[n3];
        }
        do {
            final int n4 = i * 52429 >>> 19;
            array[--n] = Integer.digits[i - ((n4 << 3) + (n4 << 1))];
            i = n4;
        } while (i != 0);
        if (c != '\0') {
            array[--n] = c;
        }
    }
    
    static int stringSize(final int i) {
        int n;
        for (n = 0; i > Integer.sizeTable[n]; ++n) {}
        return n + 1;
    }
    
    public static int parseInt(final String s, final int n) throws NumberFormatException {
        if (s == null) {
            throw new NumberFormatException("null");
        }
        if (n < 2) {
            throw new NumberFormatException("radix " + n + " less than Character.MIN_RADIX");
        }
        if (n > 36) {
            throw new NumberFormatException("radix " + n + " greater than Character.MAX_RADIX");
        }
        int n2 = 0;
        boolean b = false;
        int i = 0;
        final int length = s.length();
        int n3 = -2147483647;
        if (length > 0) {
            final char char1 = s.charAt(0);
            if (char1 < '0') {
                if (char1 == '-') {
                    b = true;
                    n3 = Integer.MIN_VALUE;
                }
                else if (char1 != '+') {
                    throw NumberFormatException.forInputString(s);
                }
                if (length == 1) {
                    throw NumberFormatException.forInputString(s);
                }
                ++i;
            }
            final int n4 = n3 / n;
            while (i < length) {
                final int digit = Character.digit(s.charAt(i++), n);
                if (digit < 0) {
                    throw NumberFormatException.forInputString(s);
                }
                if (n2 < n4) {
                    throw NumberFormatException.forInputString(s);
                }
                final int n5 = n2 * n;
                if (n5 < n3 + digit) {
                    throw NumberFormatException.forInputString(s);
                }
                n2 = n5 - digit;
            }
            return b ? n2 : (-n2);
        }
        throw NumberFormatException.forInputString(s);
    }
    
    public static int parseInt(final String s) throws NumberFormatException {
        return parseInt(s, 10);
    }
    
    public static int parseUnsignedInt(final String s, final int n) throws NumberFormatException {
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
        if (length <= 5 || (n == 10 && length <= 9)) {
            return parseInt(s, n);
        }
        final long long1 = Long.parseLong(s, n);
        if ((long1 & 0xFFFFFFFF00000000L) == 0x0L) {
            return (int)long1;
        }
        throw new NumberFormatException(String.format("String value %s exceeds range of unsigned int.", s));
    }
    
    public static int parseUnsignedInt(final String s) throws NumberFormatException {
        return parseUnsignedInt(s, 10);
    }
    
    public static Integer valueOf(final String s, final int n) throws NumberFormatException {
        return parseInt(s, n);
    }
    
    public static Integer valueOf(final String s) throws NumberFormatException {
        return parseInt(s, 10);
    }
    
    public static Integer valueOf(final int n) {
        if (n >= -128 && n <= IntegerCache.high) {
            return IntegerCache.cache[n + 128];
        }
        return new Integer(n);
    }
    
    public Integer(final int value) {
        this.value = value;
    }
    
    public Integer(final String s) throws NumberFormatException {
        this.value = parseInt(s, 10);
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
        return toString(this.value);
    }
    
    @Override
    public int hashCode() {
        return hashCode(this.value);
    }
    
    public static int hashCode(final int n) {
        return n;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Integer && this.value == (int)o;
    }
    
    public static Integer getInteger(final String s) {
        return getInteger(s, null);
    }
    
    public static Integer getInteger(final String s, final int n) {
        final Integer integer = getInteger(s, null);
        return (integer == null) ? n : integer;
    }
    
    public static Integer getInteger(final String s, final Integer n) {
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
    
    public static Integer decode(final String s) throws NumberFormatException {
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
        Integer value2;
        try {
            final Integer value = valueOf(s.substring(n2), n);
            value2 = (b ? (-value) : value);
        }
        catch (NumberFormatException ex) {
            value2 = valueOf(b ? ("-" + s.substring(n2)) : s.substring(n2), n);
        }
        return value2;
    }
    
    @Override
    public int compareTo(final Integer n) {
        return compare(this.value, n.value);
    }
    
    public static int compare(final int n, final int n2) {
        return (n < n2) ? -1 : ((n == n2) ? 0 : 1);
    }
    
    public static int compareUnsigned(final int n, final int n2) {
        return compare(n + Integer.MIN_VALUE, n2 + Integer.MIN_VALUE);
    }
    
    public static long toUnsignedLong(final int n) {
        return n & 0xFFFFFFFFL;
    }
    
    public static int divideUnsigned(final int n, final int n2) {
        return (int)(toUnsignedLong(n) / toUnsignedLong(n2));
    }
    
    public static int remainderUnsigned(final int n, final int n2) {
        return (int)(toUnsignedLong(n) % toUnsignedLong(n2));
    }
    
    public static int highestOneBit(int n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return n - (n >>> 1);
    }
    
    public static int lowestOneBit(final int n) {
        return n & -n;
    }
    
    public static int numberOfLeadingZeros(int n) {
        if (n == 0) {
            return 32;
        }
        int n2 = 1;
        if (n >>> 16 == 0) {
            n2 += 16;
            n <<= 16;
        }
        if (n >>> 24 == 0) {
            n2 += 8;
            n <<= 8;
        }
        if (n >>> 28 == 0) {
            n2 += 4;
            n <<= 4;
        }
        if (n >>> 30 == 0) {
            n2 += 2;
            n <<= 2;
        }
        return n2 - (n >>> 31);
    }
    
    public static int numberOfTrailingZeros(int n) {
        if (n == 0) {
            return 32;
        }
        int n2 = 31;
        final int n3 = n << 16;
        if (n3 != 0) {
            n2 -= 16;
            n = n3;
        }
        final int n4 = n << 8;
        if (n4 != 0) {
            n2 -= 8;
            n = n4;
        }
        final int n5 = n << 4;
        if (n5 != 0) {
            n2 -= 4;
            n = n5;
        }
        final int n6 = n << 2;
        if (n6 != 0) {
            n2 -= 2;
            n = n6;
        }
        return n2 - (n << 1 >>> 31);
    }
    
    public static int bitCount(int n) {
        n -= (n >>> 1 & 0x55555555);
        n = (n & 0x33333333) + (n >>> 2 & 0x33333333);
        n = (n + (n >>> 4) & 0xF0F0F0F);
        n += n >>> 8;
        n += n >>> 16;
        return n & 0x3F;
    }
    
    public static int rotateLeft(final int n, final int n2) {
        return n << n2 | n >>> -n2;
    }
    
    public static int rotateRight(final int n, final int n2) {
        return n >>> n2 | n << -n2;
    }
    
    public static int reverse(int n) {
        n = ((n & 0x55555555) << 1 | (n >>> 1 & 0x55555555));
        n = ((n & 0x33333333) << 2 | (n >>> 2 & 0x33333333));
        n = ((n & 0xF0F0F0F) << 4 | (n >>> 4 & 0xF0F0F0F));
        n = (n << 24 | (n & 0xFF00) << 8 | (n >>> 8 & 0xFF00) | n >>> 24);
        return n;
    }
    
    public static int signum(final int n) {
        return n >> 31 | -n >>> 31;
    }
    
    public static int reverseBytes(final int n) {
        return n >>> 24 | (n >> 8 & 0xFF00) | (n << 8 & 0xFF0000) | n << 24;
    }
    
    public static int sum(final int n, final int n2) {
        return n + n2;
    }
    
    public static int max(final int n, final int n2) {
        return Math.max(n, n2);
    }
    
    public static int min(final int n, final int n2) {
        return Math.min(n, n2);
    }
    
    static {
        TYPE = Class.getPrimitiveClass("int");
        digits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        DigitTens = new char[] { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9' };
        DigitOnes = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        sizeTable = new int[] { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };
    }
    
    private static class IntegerCache
    {
        static final int low = -128;
        static final int high;
        static final Integer[] cache;
        
        static {
            int min = 127;
            final String savedProperty = VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (savedProperty != null) {
                try {
                    min = Math.min(Math.max(Integer.parseInt(savedProperty), 127), 2147483518);
                }
                catch (NumberFormatException ex) {}
            }
            high = min;
            cache = new Integer[IntegerCache.high + 128 + 1];
            int n = -128;
            for (int i = 0; i < IntegerCache.cache.length; ++i) {
                IntegerCache.cache[i] = new Integer(n++);
            }
            assert IntegerCache.high >= 127;
        }
    }
}
