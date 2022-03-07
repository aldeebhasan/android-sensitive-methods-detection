package java.lang;

import java.util.*;

public final class Math
{
    public static final double E = 2.718281828459045;
    public static final double PI = 3.141592653589793;
    private static long negativeZeroFloatBits;
    private static long negativeZeroDoubleBits;
    static double twoToTheDoubleScaleUp;
    static double twoToTheDoubleScaleDown;
    
    public static double sin(final double n) {
        return StrictMath.sin(n);
    }
    
    public static double cos(final double n) {
        return StrictMath.cos(n);
    }
    
    public static double tan(final double n) {
        return StrictMath.tan(n);
    }
    
    public static double asin(final double n) {
        return StrictMath.asin(n);
    }
    
    public static double acos(final double n) {
        return StrictMath.acos(n);
    }
    
    public static double atan(final double n) {
        return StrictMath.atan(n);
    }
    
    public static double toRadians(final double n) {
        return n / 180.0 * 3.141592653589793;
    }
    
    public static double toDegrees(final double n) {
        return n * 180.0 / 3.141592653589793;
    }
    
    public static double exp(final double n) {
        return StrictMath.exp(n);
    }
    
    public static double log(final double n) {
        return StrictMath.log(n);
    }
    
    public static double log10(final double n) {
        return StrictMath.log10(n);
    }
    
    public static double sqrt(final double n) {
        return StrictMath.sqrt(n);
    }
    
    public static double cbrt(final double n) {
        return StrictMath.cbrt(n);
    }
    
    public static double IEEEremainder(final double n, final double n2) {
        return StrictMath.IEEEremainder(n, n2);
    }
    
    public static double ceil(final double n) {
        return StrictMath.ceil(n);
    }
    
    public static double floor(final double n) {
        return StrictMath.floor(n);
    }
    
    public static double rint(final double n) {
        return StrictMath.rint(n);
    }
    
    public static double atan2(final double n, final double n2) {
        return StrictMath.atan2(n, n2);
    }
    
    public static double pow(final double n, final double n2) {
        return StrictMath.pow(n, n2);
    }
    
    public static int round(final float n) {
        final int floatToRawIntBits = Float.floatToRawIntBits(n);
        final int n2 = 149 - ((floatToRawIntBits & 0x7F800000) >> 23);
        if ((n2 & 0xFFFFFFE0) == 0x0) {
            int n3 = (floatToRawIntBits & 0x7FFFFF) | 0x800000;
            if (floatToRawIntBits < 0) {
                n3 = -n3;
            }
            return (n3 >> n2) + 1 >> 1;
        }
        return (int)n;
    }
    
    public static long round(final double n) {
        final long doubleToRawLongBits = Double.doubleToRawLongBits(n);
        final long n2 = 1074L - ((doubleToRawLongBits & 0x7FF0000000000000L) >> 52);
        if ((n2 & 0xFFFFFFFFFFFFFFC0L) == 0x0L) {
            long n3 = (doubleToRawLongBits & 0xFFFFFFFFFFFFFL) | 0x10000000000000L;
            if (doubleToRawLongBits < 0L) {
                n3 = -n3;
            }
            return (n3 >> (int)n2) + 1L >> 1;
        }
        return (long)n;
    }
    
    public static double random() {
        return RandomNumberGeneratorHolder.randomNumberGenerator.nextDouble();
    }
    
    public static int addExact(final int n, final int n2) {
        final int n3 = n + n2;
        if (((n ^ n3) & (n2 ^ n3)) < 0) {
            throw new ArithmeticException("integer overflow");
        }
        return n3;
    }
    
    public static long addExact(final long n, final long n2) {
        final long n3 = n + n2;
        if (((n ^ n3) & (n2 ^ n3)) < 0L) {
            throw new ArithmeticException("long overflow");
        }
        return n3;
    }
    
    public static int subtractExact(final int n, final int n2) {
        final int n3 = n - n2;
        if (((n ^ n2) & (n ^ n3)) < 0) {
            throw new ArithmeticException("integer overflow");
        }
        return n3;
    }
    
    public static long subtractExact(final long n, final long n2) {
        final long n3 = n - n2;
        if (((n ^ n2) & (n ^ n3)) < 0L) {
            throw new ArithmeticException("long overflow");
        }
        return n3;
    }
    
    public static int multiplyExact(final int n, final int n2) {
        final long n3 = n * n2;
        if ((int)n3 != n3) {
            throw new ArithmeticException("integer overflow");
        }
        return (int)n3;
    }
    
    public static long multiplyExact(final long n, final long n2) {
        final long n3 = n * n2;
        if ((abs(n) | abs(n2)) >>> 31 != 0L && ((n2 != 0L && n3 / n2 != n) || (n == Long.MIN_VALUE && n2 == -1L))) {
            throw new ArithmeticException("long overflow");
        }
        return n3;
    }
    
    public static int incrementExact(final int n) {
        if (n == Integer.MAX_VALUE) {
            throw new ArithmeticException("integer overflow");
        }
        return n + 1;
    }
    
    public static long incrementExact(final long n) {
        if (n == Long.MAX_VALUE) {
            throw new ArithmeticException("long overflow");
        }
        return n + 1L;
    }
    
    public static int decrementExact(final int n) {
        if (n == Integer.MIN_VALUE) {
            throw new ArithmeticException("integer overflow");
        }
        return n - 1;
    }
    
    public static long decrementExact(final long n) {
        if (n == Long.MIN_VALUE) {
            throw new ArithmeticException("long overflow");
        }
        return n - 1L;
    }
    
    public static int negateExact(final int n) {
        if (n == Integer.MIN_VALUE) {
            throw new ArithmeticException("integer overflow");
        }
        return -n;
    }
    
    public static long negateExact(final long n) {
        if (n == Long.MIN_VALUE) {
            throw new ArithmeticException("long overflow");
        }
        return -n;
    }
    
    public static int toIntExact(final long n) {
        if ((int)n != n) {
            throw new ArithmeticException("integer overflow");
        }
        return (int)n;
    }
    
    public static int floorDiv(final int n, final int n2) {
        int n3 = n / n2;
        if ((n ^ n2) < 0 && n3 * n2 != n) {
            --n3;
        }
        return n3;
    }
    
    public static long floorDiv(final long n, final long n2) {
        long n3 = n / n2;
        if ((n ^ n2) < 0L && n3 * n2 != n) {
            --n3;
        }
        return n3;
    }
    
    public static int floorMod(final int n, final int n2) {
        return n - floorDiv(n, n2) * n2;
    }
    
    public static long floorMod(final long n, final long n2) {
        return n - floorDiv(n, n2) * n2;
    }
    
    public static int abs(final int n) {
        return (n < 0) ? (-n) : n;
    }
    
    public static long abs(final long n) {
        return (n < 0L) ? (-n) : n;
    }
    
    public static float abs(final float n) {
        return (n <= 0.0f) ? (0.0f - n) : n;
    }
    
    public static double abs(final double n) {
        return (n <= 0.0) ? (0.0 - n) : n;
    }
    
    public static int max(final int n, final int n2) {
        return (n >= n2) ? n : n2;
    }
    
    public static long max(final long n, final long n2) {
        return (n >= n2) ? n : n2;
    }
    
    public static float max(final float n, final float n2) {
        if (n != n) {
            return n;
        }
        if (n == 0.0f && n2 == 0.0f && Float.floatToRawIntBits(n) == Math.negativeZeroFloatBits) {
            return n2;
        }
        return (n >= n2) ? n : n2;
    }
    
    public static double max(final double n, final double n2) {
        if (n != n) {
            return n;
        }
        if (n == 0.0 && n2 == 0.0 && Double.doubleToRawLongBits(n) == Math.negativeZeroDoubleBits) {
            return n2;
        }
        return (n >= n2) ? n : n2;
    }
    
    public static int min(final int n, final int n2) {
        return (n <= n2) ? n : n2;
    }
    
    public static long min(final long n, final long n2) {
        return (n <= n2) ? n : n2;
    }
    
    public static float min(final float n, final float n2) {
        if (n != n) {
            return n;
        }
        if (n == 0.0f && n2 == 0.0f && Float.floatToRawIntBits(n2) == Math.negativeZeroFloatBits) {
            return n2;
        }
        return (n <= n2) ? n : n2;
    }
    
    public static double min(final double n, final double n2) {
        if (n != n) {
            return n;
        }
        if (n == 0.0 && n2 == 0.0 && Double.doubleToRawLongBits(n2) == Math.negativeZeroDoubleBits) {
            return n2;
        }
        return (n <= n2) ? n : n2;
    }
    
    public static double ulp(final double n) {
        final int exponent = getExponent(n);
        switch (exponent) {
            case 1024: {
                return abs(n);
            }
            case -1023: {
                return Double.MIN_VALUE;
            }
            default: {
                assert exponent <= 1023 && exponent >= -1022;
                final int n2 = exponent - 52;
                if (n2 >= -1022) {
                    return powerOfTwoD(n2);
                }
                return Double.longBitsToDouble(1L << n2 + 1074);
            }
        }
    }
    
    public static float ulp(final float n) {
        final int exponent = getExponent(n);
        switch (exponent) {
            case 128: {
                return abs(n);
            }
            case -127: {
                return Float.MIN_VALUE;
            }
            default: {
                assert exponent <= 127 && exponent >= -126;
                final int n2 = exponent - 23;
                if (n2 >= -126) {
                    return powerOfTwoF(n2);
                }
                return Float.intBitsToFloat(1 << n2 + 149);
            }
        }
    }
    
    public static double signum(final double n) {
        return (n == 0.0 || Double.isNaN(n)) ? n : copySign(1.0, n);
    }
    
    public static float signum(final float n) {
        return (n == 0.0f || Float.isNaN(n)) ? n : copySign(1.0f, n);
    }
    
    public static double sinh(final double n) {
        return StrictMath.sinh(n);
    }
    
    public static double cosh(final double n) {
        return StrictMath.cosh(n);
    }
    
    public static double tanh(final double n) {
        return StrictMath.tanh(n);
    }
    
    public static double hypot(final double n, final double n2) {
        return StrictMath.hypot(n, n2);
    }
    
    public static double expm1(final double n) {
        return StrictMath.expm1(n);
    }
    
    public static double log1p(final double n) {
        return StrictMath.log1p(n);
    }
    
    public static double copySign(final double n, final double n2) {
        return Double.longBitsToDouble((Double.doubleToRawLongBits(n2) & Long.MIN_VALUE) | (Double.doubleToRawLongBits(n) & Long.MAX_VALUE));
    }
    
    public static float copySign(final float n, final float n2) {
        return Float.intBitsToFloat((Float.floatToRawIntBits(n2) & Integer.MIN_VALUE) | (Float.floatToRawIntBits(n) & Integer.MAX_VALUE));
    }
    
    public static int getExponent(final float n) {
        return ((Float.floatToRawIntBits(n) & 0x7F800000) >> 23) - 127;
    }
    
    public static int getExponent(final double n) {
        return (int)(((Double.doubleToRawLongBits(n) & 0x7FF0000000000000L) >> 52) - 1023L);
    }
    
    public static double nextAfter(final double n, final double n2) {
        if (Double.isNaN(n) || Double.isNaN(n2)) {
            return n + n2;
        }
        if (n == n2) {
            return n2;
        }
        final long doubleToRawLongBits = Double.doubleToRawLongBits(n + 0.0);
        long n3;
        if (n2 > n) {
            n3 = doubleToRawLongBits + ((doubleToRawLongBits >= 0L) ? 1L : -1L);
        }
        else {
            assert n2 < n;
            if (doubleToRawLongBits > 0L) {
                n3 = doubleToRawLongBits - 1L;
            }
            else if (doubleToRawLongBits < 0L) {
                n3 = doubleToRawLongBits + 1L;
            }
            else {
                n3 = -9223372036854775807L;
            }
        }
        return Double.longBitsToDouble(n3);
    }
    
    public static float nextAfter(final float n, final double n2) {
        if (Float.isNaN(n) || Double.isNaN(n2)) {
            return n + (float)n2;
        }
        if (n == n2) {
            return (float)n2;
        }
        int floatToRawIntBits = Float.floatToRawIntBits(n + 0.0f);
        if (n2 > n) {
            floatToRawIntBits += ((floatToRawIntBits >= 0) ? 1 : -1);
        }
        else {
            assert n2 < n;
            if (floatToRawIntBits > 0) {
                --floatToRawIntBits;
            }
            else if (floatToRawIntBits < 0) {
                ++floatToRawIntBits;
            }
            else {
                floatToRawIntBits = -2147483647;
            }
        }
        return Float.intBitsToFloat(floatToRawIntBits);
    }
    
    public static double nextUp(double n) {
        if (Double.isNaN(n) || n == Double.POSITIVE_INFINITY) {
            return n;
        }
        n += 0.0;
        return Double.longBitsToDouble(Double.doubleToRawLongBits(n) + ((n >= 0.0) ? 1L : -1L));
    }
    
    public static float nextUp(float n) {
        if (Float.isNaN(n) || n == Float.POSITIVE_INFINITY) {
            return n;
        }
        n += 0.0f;
        return Float.intBitsToFloat(Float.floatToRawIntBits(n) + ((n >= 0.0f) ? 1 : -1));
    }
    
    public static double nextDown(final double n) {
        if (Double.isNaN(n) || n == Double.NEGATIVE_INFINITY) {
            return n;
        }
        if (n == 0.0) {
            return -4.9E-324;
        }
        return Double.longBitsToDouble(Double.doubleToRawLongBits(n) + ((n > 0.0) ? -1L : 1L));
    }
    
    public static float nextDown(final float n) {
        if (Float.isNaN(n) || n == Float.NEGATIVE_INFINITY) {
            return n;
        }
        if (n == 0.0f) {
            return -1.4E-45f;
        }
        return Float.intBitsToFloat(Float.floatToRawIntBits(n) + ((n > 0.0f) ? -1 : 1));
    }
    
    public static double scalb(double n, int i) {
        int n2;
        double n3;
        if (i < 0) {
            i = max(i, -2099);
            n2 = -512;
            n3 = Math.twoToTheDoubleScaleDown;
        }
        else {
            i = min(i, 2099);
            n2 = 512;
            n3 = Math.twoToTheDoubleScaleUp;
        }
        final int n4 = i >> 8 >>> 23;
        final int n5 = (i + n4 & 0x1FF) - n4;
        n *= powerOfTwoD(n5);
        for (i -= n5; i != 0; i -= n2) {
            n *= n3;
        }
        return n;
    }
    
    public static float scalb(final float n, int max) {
        max = max(min(max, 278), -278);
        return (float)(n * powerOfTwoD(max));
    }
    
    static double powerOfTwoD(final int n) {
        assert n >= -1022 && n <= 1023;
        return Double.longBitsToDouble(n + 1023L << 52 & 0x7FF0000000000000L);
    }
    
    static float powerOfTwoF(final int n) {
        assert n >= -126 && n <= 127;
        return Float.intBitsToFloat(n + 127 << 23 & 0x7F800000);
    }
    
    static {
        Math.negativeZeroFloatBits = Float.floatToRawIntBits(-0.0f);
        Math.negativeZeroDoubleBits = Double.doubleToRawLongBits(-0.0);
        Math.twoToTheDoubleScaleUp = powerOfTwoD(512);
        Math.twoToTheDoubleScaleDown = powerOfTwoD(-512);
    }
    
    private static final class RandomNumberGeneratorHolder
    {
        static final Random randomNumberGenerator;
        
        static {
            randomNumberGenerator = new Random();
        }
    }
}
