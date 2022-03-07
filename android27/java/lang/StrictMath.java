package java.lang;

import java.util.*;

public final class StrictMath
{
    public static final double E = 2.718281828459045;
    public static final double PI = 3.141592653589793;
    
    public static native double sin(final double p0);
    
    public static native double cos(final double p0);
    
    public static native double tan(final double p0);
    
    public static native double asin(final double p0);
    
    public static native double acos(final double p0);
    
    public static native double atan(final double p0);
    
    public static strictfp double toRadians(final double n) {
        return n / 180.0 * 3.141592653589793;
    }
    
    public static strictfp double toDegrees(final double n) {
        return n * 180.0 / 3.141592653589793;
    }
    
    public static native double exp(final double p0);
    
    public static native double log(final double p0);
    
    public static native double log10(final double p0);
    
    public static native double sqrt(final double p0);
    
    public static native double cbrt(final double p0);
    
    public static native double IEEEremainder(final double p0, final double p1);
    
    public static double ceil(final double n) {
        return floorOrCeil(n, -0.0, 1.0, 1.0);
    }
    
    public static double floor(final double n) {
        return floorOrCeil(n, -1.0, 0.0, -1.0);
    }
    
    private static double floorOrCeil(final double n, final double n2, final double n3, final double n4) {
        final int exponent = Math.getExponent(n);
        if (exponent < 0) {
            return (n == 0.0) ? n : ((n < 0.0) ? n2 : n3);
        }
        if (exponent >= 52) {
            return n;
        }
        assert exponent >= 0 && exponent <= 51;
        final long doubleToRawLongBits = Double.doubleToRawLongBits(n);
        final long n5 = 4503599627370495L >> exponent;
        if ((n5 & doubleToRawLongBits) == 0x0L) {
            return n;
        }
        double longBitsToDouble = Double.longBitsToDouble(doubleToRawLongBits & ~n5);
        if (n4 * n > 0.0) {
            longBitsToDouble += n4;
        }
        return longBitsToDouble;
    }
    
    public static double rint(double abs) {
        final double n = 4.503599627370496E15;
        final double copySign = Math.copySign(1.0, abs);
        abs = Math.abs(abs);
        if (abs < n) {
            abs = n + abs - n;
        }
        return copySign * abs;
    }
    
    public static native double atan2(final double p0, final double p1);
    
    public static native double pow(final double p0, final double p1);
    
    public static int round(final float n) {
        return Math.round(n);
    }
    
    public static long round(final double n) {
        return Math.round(n);
    }
    
    public static double random() {
        return RandomNumberGeneratorHolder.randomNumberGenerator.nextDouble();
    }
    
    public static int addExact(final int n, final int n2) {
        return Math.addExact(n, n2);
    }
    
    public static long addExact(final long n, final long n2) {
        return Math.addExact(n, n2);
    }
    
    public static int subtractExact(final int n, final int n2) {
        return Math.subtractExact(n, n2);
    }
    
    public static long subtractExact(final long n, final long n2) {
        return Math.subtractExact(n, n2);
    }
    
    public static int multiplyExact(final int n, final int n2) {
        return Math.multiplyExact(n, n2);
    }
    
    public static long multiplyExact(final long n, final long n2) {
        return Math.multiplyExact(n, n2);
    }
    
    public static int toIntExact(final long n) {
        return Math.toIntExact(n);
    }
    
    public static int floorDiv(final int n, final int n2) {
        return Math.floorDiv(n, n2);
    }
    
    public static long floorDiv(final long n, final long n2) {
        return Math.floorDiv(n, n2);
    }
    
    public static int floorMod(final int n, final int n2) {
        return Math.floorMod(n, n2);
    }
    
    public static long floorMod(final long n, final long n2) {
        return Math.floorMod(n, n2);
    }
    
    public static int abs(final int n) {
        return Math.abs(n);
    }
    
    public static long abs(final long n) {
        return Math.abs(n);
    }
    
    public static float abs(final float n) {
        return Math.abs(n);
    }
    
    public static double abs(final double n) {
        return Math.abs(n);
    }
    
    public static int max(final int n, final int n2) {
        return Math.max(n, n2);
    }
    
    public static long max(final long n, final long n2) {
        return Math.max(n, n2);
    }
    
    public static float max(final float n, final float n2) {
        return Math.max(n, n2);
    }
    
    public static double max(final double n, final double n2) {
        return Math.max(n, n2);
    }
    
    public static int min(final int n, final int n2) {
        return Math.min(n, n2);
    }
    
    public static long min(final long n, final long n2) {
        return Math.min(n, n2);
    }
    
    public static float min(final float n, final float n2) {
        return Math.min(n, n2);
    }
    
    public static double min(final double n, final double n2) {
        return Math.min(n, n2);
    }
    
    public static double ulp(final double n) {
        return Math.ulp(n);
    }
    
    public static float ulp(final float n) {
        return Math.ulp(n);
    }
    
    public static double signum(final double n) {
        return Math.signum(n);
    }
    
    public static float signum(final float n) {
        return Math.signum(n);
    }
    
    public static native double sinh(final double p0);
    
    public static native double cosh(final double p0);
    
    public static native double tanh(final double p0);
    
    public static native double hypot(final double p0, final double p1);
    
    public static native double expm1(final double p0);
    
    public static native double log1p(final double p0);
    
    public static double copySign(final double n, final double n2) {
        return Math.copySign(n, Double.isNaN(n2) ? 1.0 : n2);
    }
    
    public static float copySign(final float n, final float n2) {
        return Math.copySign(n, Float.isNaN(n2) ? 1.0f : n2);
    }
    
    public static int getExponent(final float n) {
        return Math.getExponent(n);
    }
    
    public static int getExponent(final double n) {
        return Math.getExponent(n);
    }
    
    public static double nextAfter(final double n, final double n2) {
        return Math.nextAfter(n, n2);
    }
    
    public static float nextAfter(final float n, final double n2) {
        return Math.nextAfter(n, n2);
    }
    
    public static double nextUp(final double n) {
        return Math.nextUp(n);
    }
    
    public static float nextUp(final float n) {
        return Math.nextUp(n);
    }
    
    public static double nextDown(final double n) {
        return Math.nextDown(n);
    }
    
    public static float nextDown(final float n) {
        return Math.nextDown(n);
    }
    
    public static double scalb(final double n, final int n2) {
        return Math.scalb(n, n2);
    }
    
    public static float scalb(final float n, final int n2) {
        return Math.scalb(n, n2);
    }
    
    private static final class RandomNumberGeneratorHolder
    {
        static final Random randomNumberGenerator;
        
        static {
            randomNumberGenerator = new Random();
        }
    }
}
