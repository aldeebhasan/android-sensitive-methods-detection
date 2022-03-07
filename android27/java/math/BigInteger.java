package java.math;

import java.util.concurrent.*;
import java.util.*;
import sun.misc.*;
import java.io.*;

public class BigInteger extends Number implements Comparable<BigInteger>
{
    final int signum;
    final int[] mag;
    @Deprecated
    private int bitCount;
    @Deprecated
    private int bitLength;
    @Deprecated
    private int lowestSetBit;
    @Deprecated
    private int firstNonzeroIntNum;
    static final long LONG_MASK = 4294967295L;
    private static final int MAX_MAG_LENGTH = 67108864;
    private static final int PRIME_SEARCH_BIT_LENGTH_LIMIT = 500000000;
    private static final int KARATSUBA_THRESHOLD = 80;
    private static final int TOOM_COOK_THRESHOLD = 240;
    private static final int KARATSUBA_SQUARE_THRESHOLD = 128;
    private static final int TOOM_COOK_SQUARE_THRESHOLD = 216;
    static final int BURNIKEL_ZIEGLER_THRESHOLD = 80;
    static final int BURNIKEL_ZIEGLER_OFFSET = 40;
    private static final int SCHOENHAGE_BASE_CONVERSION_THRESHOLD = 20;
    private static final int MULTIPLY_SQUARE_THRESHOLD = 20;
    private static final int MONTGOMERY_INTRINSIC_THRESHOLD = 512;
    private static long[] bitsPerDigit;
    private static final int SMALL_PRIME_THRESHOLD = 95;
    private static final int DEFAULT_PRIME_CERTAINTY = 100;
    private static final BigInteger SMALL_PRIME_PRODUCT;
    private static final int MAX_CONSTANT = 16;
    private static BigInteger[] posConst;
    private static BigInteger[] negConst;
    private static volatile BigInteger[][] powerCache;
    private static final double[] logCache;
    private static final double LOG_TWO;
    public static final BigInteger ZERO;
    public static final BigInteger ONE;
    private static final BigInteger TWO;
    private static final BigInteger NEGATIVE_ONE;
    public static final BigInteger TEN;
    static int[] bnExpModThreshTable;
    private static String[] zeros;
    private static int[] digitsPerLong;
    private static BigInteger[] longRadix;
    private static int[] digitsPerInt;
    private static int[] intRadix;
    private static final long serialVersionUID = -8287574255936472291L;
    private static final ObjectStreamField[] serialPersistentFields;
    
    public BigInteger(final byte[] array) {
        if (array.length == 0) {
            throw new NumberFormatException("Zero length BigInteger");
        }
        if (array[0] < 0) {
            this.mag = makePositive(array);
            this.signum = -1;
        }
        else {
            this.mag = stripLeadingZeroBytes(array);
            this.signum = ((this.mag.length != 0) ? 1 : 0);
        }
        if (this.mag.length >= 67108864) {
            this.checkRange();
        }
    }
    
    private BigInteger(final int[] array) {
        if (array.length == 0) {
            throw new NumberFormatException("Zero length BigInteger");
        }
        if (array[0] < 0) {
            this.mag = makePositive(array);
            this.signum = -1;
        }
        else {
            this.mag = trustedStripLeadingZeroInts(array);
            this.signum = ((this.mag.length != 0) ? 1 : 0);
        }
        if (this.mag.length >= 67108864) {
            this.checkRange();
        }
    }
    
    public BigInteger(final int signum, final byte[] array) {
        this.mag = stripLeadingZeroBytes(array);
        if (signum < -1 || signum > 1) {
            throw new NumberFormatException("Invalid signum value");
        }
        if (this.mag.length == 0) {
            this.signum = 0;
        }
        else {
            if (signum == 0) {
                throw new NumberFormatException("signum-magnitude mismatch");
            }
            this.signum = signum;
        }
        if (this.mag.length >= 67108864) {
            this.checkRange();
        }
    }
    
    private BigInteger(final int signum, final int[] array) {
        this.mag = stripLeadingZeroInts(array);
        if (signum < -1 || signum > 1) {
            throw new NumberFormatException("Invalid signum value");
        }
        if (this.mag.length == 0) {
            this.signum = 0;
        }
        else {
            if (signum == 0) {
                throw new NumberFormatException("signum-magnitude mismatch");
            }
            this.signum = signum;
        }
        if (this.mag.length >= 67108864) {
            this.checkRange();
        }
    }
    
    public BigInteger(final String s, final int n) {
        int n2 = 0;
        final int length = s.length();
        if (n < 2 || n > 36) {
            throw new NumberFormatException("Radix out of range");
        }
        if (length == 0) {
            throw new NumberFormatException("Zero length BigInteger");
        }
        int signum = 1;
        final int lastIndex = s.lastIndexOf(45);
        final int lastIndex2 = s.lastIndexOf(43);
        if (lastIndex >= 0) {
            if (lastIndex != 0 || lastIndex2 >= 0) {
                throw new NumberFormatException("Illegal embedded sign character");
            }
            signum = -1;
            n2 = 1;
        }
        else if (lastIndex2 >= 0) {
            if (lastIndex2 != 0) {
                throw new NumberFormatException("Illegal embedded sign character");
            }
            n2 = 1;
        }
        if (n2 == length) {
            throw new NumberFormatException("Zero length BigInteger");
        }
        while (n2 < length && Character.digit(s.charAt(n2), n) == 0) {
            ++n2;
        }
        if (n2 == length) {
            this.signum = 0;
            this.mag = BigInteger.ZERO.mag;
            return;
        }
        final int n3 = length - n2;
        this.signum = signum;
        final long n4 = (n3 * BigInteger.bitsPerDigit[n] >>> 10) + 1L;
        if (n4 + 31L >= 4294967296L) {
            reportOverflow();
        }
        final int n5 = (int)(n4 + 31L) >>> 5;
        final int[] array = new int[n5];
        int n6 = n3 % BigInteger.digitsPerInt[n];
        if (n6 == 0) {
            n6 = BigInteger.digitsPerInt[n];
        }
        int i;
        array[n5 - 1] = Integer.parseInt(s.substring(n2, i = n2 + n6), n);
        if (array[n5 - 1] < 0) {
            throw new NumberFormatException("Illegal digit");
        }
        final int n7 = BigInteger.intRadix[n];
        while (i < length) {
            final int int1 = Integer.parseInt(s.substring(i, i += BigInteger.digitsPerInt[n]), n);
            if (int1 < 0) {
                throw new NumberFormatException("Illegal digit");
            }
            destructiveMulAdd(array, n7, int1);
        }
        this.mag = trustedStripLeadingZeroInts(array);
        if (this.mag.length >= 67108864) {
            this.checkRange();
        }
    }
    
    BigInteger(final char[] array, final int signum, final int n) {
        int n2;
        for (n2 = 0; n2 < n && Character.digit(array[n2], 10) == 0; ++n2) {}
        if (n2 == n) {
            this.signum = 0;
            this.mag = BigInteger.ZERO.mag;
            return;
        }
        final int n3 = n - n2;
        this.signum = signum;
        int n4;
        if (n < 10) {
            n4 = 1;
        }
        else {
            final long n5 = (n3 * BigInteger.bitsPerDigit[10] >>> 10) + 1L;
            if (n5 + 31L >= 4294967296L) {
                reportOverflow();
            }
            n4 = (int)(n5 + 31L) >>> 5;
        }
        final int[] array2 = new int[n4];
        int n6 = n3 % BigInteger.digitsPerInt[10];
        if (n6 == 0) {
            n6 = BigInteger.digitsPerInt[10];
        }
        int i;
        array2[n4 - 1] = this.parseInt(array, n2, i = n2 + n6);
        while (i < n) {
            destructiveMulAdd(array2, BigInteger.intRadix[10], this.parseInt(array, i, i += BigInteger.digitsPerInt[10]));
        }
        this.mag = trustedStripLeadingZeroInts(array2);
        if (this.mag.length >= 67108864) {
            this.checkRange();
        }
    }
    
    private int parseInt(final char[] array, int n, final int n2) {
        int digit = Character.digit(array[n++], 10);
        if (digit == -1) {
            throw new NumberFormatException(new String(array));
        }
        for (int i = n; i < n2; ++i) {
            final int digit2 = Character.digit(array[i], 10);
            if (digit2 == -1) {
                throw new NumberFormatException(new String(array));
            }
            digit = 10 * digit + digit2;
        }
        return digit;
    }
    
    private static void destructiveMulAdd(final int[] array, final int n, final int n2) {
        final long n3 = n & 0xFFFFFFFFL;
        final long n4 = n2 & 0xFFFFFFFFL;
        final int length = array.length;
        long n5 = 0L;
        for (int i = length - 1; i >= 0; --i) {
            final long n6 = n3 * (array[i] & 0xFFFFFFFFL) + n5;
            array[i] = (int)n6;
            n5 = n6 >>> 32;
        }
        final long n7 = (array[length - 1] & 0xFFFFFFFFL) + n4;
        array[length - 1] = (int)n7;
        long n8 = n7 >>> 32;
        for (int j = length - 2; j >= 0; --j) {
            final long n9 = (array[j] & 0xFFFFFFFFL) + n8;
            array[j] = (int)n9;
            n8 = n9 >>> 32;
        }
    }
    
    public BigInteger(final String s) {
        this(s, 10);
    }
    
    public BigInteger(final int n, final Random random) {
        this(1, randomBits(n, random));
    }
    
    private static byte[] randomBits(final int n, final Random random) {
        if (n < 0) {
            throw new IllegalArgumentException("numBits must be non-negative");
        }
        final int n2 = (int)((n + 7L) / 8L);
        final byte[] array = new byte[n2];
        if (n2 > 0) {
            random.nextBytes(array);
            final int n3 = 8 * n2 - n;
            final byte[] array2 = array;
            final int n4 = 0;
            array2[n4] &= (byte)((1 << 8 - n3) - 1);
        }
        return array;
    }
    
    public BigInteger(final int n, final int n2, final Random random) {
        if (n < 2) {
            throw new ArithmeticException("bitLength < 2");
        }
        final BigInteger bigInteger = (n < 95) ? smallPrime(n, n2, random) : largePrime(n, n2, random);
        this.signum = 1;
        this.mag = bigInteger.mag;
    }
    
    public static BigInteger probablePrime(final int n, final Random random) {
        if (n < 2) {
            throw new ArithmeticException("bitLength < 2");
        }
        return (n < 95) ? smallPrime(n, 100, random) : largePrime(n, 100, random);
    }
    
    private static BigInteger smallPrime(final int n, final int n2, final Random random) {
        final int n3 = n + 31 >>> 5;
        final int[] array = new int[n3];
        final int n4 = 1 << (n + 31 & 0x1F);
        final int n5 = (n4 << 1) - 1;
        while (true) {
            for (int i = 0; i < n3; ++i) {
                array[i] = random.nextInt();
            }
            array[0] = ((array[0] & n5) | n4);
            if (n > 2) {
                final int[] array2 = array;
                final int n6 = n3 - 1;
                array2[n6] |= 0x1;
            }
            final BigInteger bigInteger = new BigInteger(array, 1);
            if (n > 6) {
                final long longValue = bigInteger.remainder(BigInteger.SMALL_PRIME_PRODUCT).longValue();
                if (longValue % 3L == 0L || longValue % 5L == 0L || longValue % 7L == 0L || longValue % 11L == 0L || longValue % 13L == 0L || longValue % 17L == 0L || longValue % 19L == 0L || longValue % 23L == 0L || longValue % 29L == 0L || longValue % 31L == 0L || longValue % 37L == 0L) {
                    continue;
                }
                if (longValue % 41L == 0L) {
                    continue;
                }
            }
            if (n < 4) {
                return bigInteger;
            }
            if (bigInteger.primeToCertainty(n2, random)) {
                return bigInteger;
            }
        }
    }
    
    private static BigInteger largePrime(final int n, final int n2, final Random random) {
        BigInteger bigInteger = new BigInteger(n, random).setBit(n - 1);
        final int[] mag = bigInteger.mag;
        final int n3 = bigInteger.mag.length - 1;
        mag[n3] &= 0xFFFFFFFE;
        int primeSearchLen;
        BigInteger bigInteger2;
        int[] mag2;
        int n4;
        for (primeSearchLen = getPrimeSearchLen(n), bigInteger2 = new BitSieve(bigInteger, primeSearchLen).retrieve(bigInteger, n2, random); bigInteger2 == null || bigInteger2.bitLength() != n; bigInteger2 = new BitSieve(bigInteger, primeSearchLen).retrieve(bigInteger, n2, random)) {
            bigInteger = bigInteger.add(valueOf(2 * primeSearchLen));
            if (bigInteger.bitLength() != n) {
                bigInteger = new BigInteger(n, random).setBit(n - 1);
            }
            mag2 = bigInteger.mag;
            n4 = bigInteger.mag.length - 1;
            mag2[n4] &= 0xFFFFFFFE;
        }
        return bigInteger2;
    }
    
    public BigInteger nextProbablePrime() {
        if (this.signum < 0) {
            throw new ArithmeticException("start < 0: " + this);
        }
        if (this.signum == 0 || this.equals(BigInteger.ONE)) {
            return BigInteger.TWO;
        }
        BigInteger bigInteger = this.add(BigInteger.ONE);
        if (bigInteger.bitLength() >= 95) {
            if (bigInteger.testBit(0)) {
                bigInteger = bigInteger.subtract(BigInteger.ONE);
            }
            final int primeSearchLen = getPrimeSearchLen(bigInteger.bitLength());
            BigInteger retrieve;
            while (true) {
                retrieve = new BitSieve(bigInteger, primeSearchLen).retrieve(bigInteger, 100, null);
                if (retrieve != null) {
                    break;
                }
                bigInteger = bigInteger.add(valueOf(2 * primeSearchLen));
            }
            return retrieve;
        }
        if (!bigInteger.testBit(0)) {
            bigInteger = bigInteger.add(BigInteger.ONE);
        }
        while (true) {
            if (bigInteger.bitLength() > 6) {
                final long longValue = bigInteger.remainder(BigInteger.SMALL_PRIME_PRODUCT).longValue();
                if (longValue % 3L == 0L || longValue % 5L == 0L || longValue % 7L == 0L || longValue % 11L == 0L || longValue % 13L == 0L || longValue % 17L == 0L || longValue % 19L == 0L || longValue % 23L == 0L || longValue % 29L == 0L || longValue % 31L == 0L || longValue % 37L == 0L || longValue % 41L == 0L) {
                    bigInteger = bigInteger.add(BigInteger.TWO);
                    continue;
                }
            }
            if (bigInteger.bitLength() < 4) {
                return bigInteger;
            }
            if (bigInteger.primeToCertainty(100, null)) {
                return bigInteger;
            }
            bigInteger = bigInteger.add(BigInteger.TWO);
        }
    }
    
    private static int getPrimeSearchLen(final int n) {
        if (n > 500000001) {
            throw new ArithmeticException("Prime search implementation restriction on bitLength");
        }
        return n / 20 * 64;
    }
    
    boolean primeToCertainty(final int n, final Random random) {
        final int n2 = (Math.min(n, 2147483646) + 1) / 2;
        final int bitLength = this.bitLength();
        if (bitLength < 100) {
            final int n3 = 50;
            return this.passesMillerRabin((n2 < n3) ? n2 : n3, random);
        }
        int n4;
        if (bitLength < 256) {
            n4 = 27;
        }
        else if (bitLength < 512) {
            n4 = 15;
        }
        else if (bitLength < 768) {
            n4 = 8;
        }
        else if (bitLength < 1024) {
            n4 = 4;
        }
        else {
            n4 = 2;
        }
        return this.passesMillerRabin((n2 < n4) ? n2 : n4, random) && this.passesLucasLehmer();
    }
    
    private boolean passesLucasLehmer() {
        final BigInteger add = this.add(BigInteger.ONE);
        int n;
        for (n = 5; jacobiSymbol(n, this) != -1; n = ((n < 0) ? (Math.abs(n) + 2) : (-(n + 2)))) {}
        return lucasLehmerSequence(n, add, this).mod(this).equals(BigInteger.ZERO);
    }
    
    private static int jacobiSymbol(int n, final BigInteger bigInteger) {
        if (n == 0) {
            return 0;
        }
        int n2 = 1;
        final int n3 = bigInteger.mag[bigInteger.mag.length - 1];
        if (n < 0) {
            n = -n;
            final int n4 = n3 & 0x7;
            if (n4 == 3 || n4 == 7) {
                n2 = -n2;
            }
        }
        while ((n & 0x3) == 0x0) {
            n >>= 2;
        }
        if ((n & 0x1) == 0x0) {
            n >>= 1;
            if (((n3 ^ n3 >> 1) & 0x2) != 0x0) {
                n2 = -n2;
            }
        }
        if (n == 1) {
            return n2;
        }
        if ((n & n3 & 0x2) != 0x0) {
            n2 = -n2;
        }
        int n6;
        for (int i = bigInteger.mod(valueOf(n)).intValue(); i != 0; i = n6 % n) {
            while ((i & 0x3) == 0x0) {
                i >>= 2;
            }
            if ((i & 0x1) == 0x0) {
                i >>= 1;
                if (((n ^ n >> 1) & 0x2) != 0x0) {
                    n2 = -n2;
                }
            }
            if (i == 1) {
                return n2;
            }
            assert i < n;
            final int n5 = i;
            n6 = n;
            n = n5;
            if ((n6 & n & 0x2) != 0x0) {
                n2 = -n2;
            }
        }
        return 0;
    }
    
    private static BigInteger lucasLehmerSequence(final int n, final BigInteger bigInteger, final BigInteger bigInteger2) {
        final BigInteger value = valueOf(n);
        BigInteger one = BigInteger.ONE;
        BigInteger one2 = BigInteger.ONE;
        for (int i = bigInteger.bitLength() - 2; i >= 0; --i) {
            final BigInteger mod = one.multiply(one2).mod(bigInteger2);
            BigInteger bigInteger3 = one2.square().add(value.multiply(one.square())).mod(bigInteger2);
            if (bigInteger3.testBit(0)) {
                bigInteger3 = bigInteger3.subtract(bigInteger2);
            }
            final BigInteger shiftRight = bigInteger3.shiftRight(1);
            one = mod;
            one2 = shiftRight;
            if (bigInteger.testBit(i)) {
                BigInteger bigInteger4 = one.add(one2).mod(bigInteger2);
                if (bigInteger4.testBit(0)) {
                    bigInteger4 = bigInteger4.subtract(bigInteger2);
                }
                final BigInteger shiftRight2 = bigInteger4.shiftRight(1);
                BigInteger bigInteger5 = one2.add(value.multiply(one)).mod(bigInteger2);
                if (bigInteger5.testBit(0)) {
                    bigInteger5 = bigInteger5.subtract(bigInteger2);
                }
                final BigInteger shiftRight3 = bigInteger5.shiftRight(1);
                one = shiftRight2;
                one2 = shiftRight3;
            }
        }
        return one;
    }
    
    private boolean passesMillerRabin(final int n, Random current) {
        final BigInteger subtract;
        final BigInteger bigInteger = subtract = this.subtract(BigInteger.ONE);
        final int lowestSetBit = subtract.getLowestSetBit();
        final BigInteger shiftRight = subtract.shiftRight(lowestSetBit);
        if (current == null) {
            current = ThreadLocalRandom.current();
        }
        for (int i = 0; i < n; ++i) {
            BigInteger bigInteger2;
            do {
                bigInteger2 = new BigInteger(this.bitLength(), current);
            } while (bigInteger2.compareTo(BigInteger.ONE) <= 0 || bigInteger2.compareTo(this) >= 0);
            int n2 = 0;
            for (BigInteger bigInteger3 = bigInteger2.modPow(shiftRight, this); (n2 != 0 || !bigInteger3.equals(BigInteger.ONE)) && !bigInteger3.equals(bigInteger); bigInteger3 = bigInteger3.modPow(BigInteger.TWO, this)) {
                if ((n2 > 0 && bigInteger3.equals(BigInteger.ONE)) || ++n2 == lowestSetBit) {
                    return false;
                }
            }
        }
        return true;
    }
    
    BigInteger(final int[] mag, final int n) {
        this.signum = ((mag.length == 0) ? 0 : n);
        this.mag = mag;
        if (this.mag.length >= 67108864) {
            this.checkRange();
        }
    }
    
    private BigInteger(final byte[] array, final int n) {
        this.signum = ((array.length == 0) ? 0 : n);
        this.mag = stripLeadingZeroBytes(array);
        if (this.mag.length >= 67108864) {
            this.checkRange();
        }
    }
    
    private void checkRange() {
        if (this.mag.length > 67108864 || (this.mag.length == 67108864 && this.mag[0] < 0)) {
            reportOverflow();
        }
    }
    
    private static void reportOverflow() {
        throw new ArithmeticException("BigInteger would overflow supported range");
    }
    
    public static BigInteger valueOf(final long n) {
        if (n == 0L) {
            return BigInteger.ZERO;
        }
        if (n > 0L && n <= 16L) {
            return BigInteger.posConst[(int)n];
        }
        if (n < 0L && n >= -16L) {
            return BigInteger.negConst[(int)(-n)];
        }
        return new BigInteger(n);
    }
    
    private BigInteger(long n) {
        if (n < 0L) {
            n = -n;
            this.signum = -1;
        }
        else {
            this.signum = 1;
        }
        final int n2 = (int)(n >>> 32);
        if (n2 == 0) {
            (this.mag = new int[1])[0] = (int)n;
        }
        else {
            (this.mag = new int[2])[0] = n2;
            this.mag[1] = (int)n;
        }
    }
    
    private static BigInteger valueOf(final int[] array) {
        return (array[0] > 0) ? new BigInteger(array, 1) : new BigInteger(array);
    }
    
    public BigInteger add(final BigInteger bigInteger) {
        if (bigInteger.signum == 0) {
            return this;
        }
        if (this.signum == 0) {
            return bigInteger;
        }
        if (bigInteger.signum == this.signum) {
            return new BigInteger(add(this.mag, bigInteger.mag), this.signum);
        }
        final int compareMagnitude = this.compareMagnitude(bigInteger);
        if (compareMagnitude == 0) {
            return BigInteger.ZERO;
        }
        return new BigInteger(trustedStripLeadingZeroInts((compareMagnitude > 0) ? subtract(this.mag, bigInteger.mag) : subtract(bigInteger.mag, this.mag)), (compareMagnitude == this.signum) ? 1 : -1);
    }
    
    BigInteger add(final long n) {
        if (n == 0L) {
            return this;
        }
        if (this.signum == 0) {
            return valueOf(n);
        }
        if (Long.signum(n) == this.signum) {
            return new BigInteger(add(this.mag, Math.abs(n)), this.signum);
        }
        final int compareMagnitude = this.compareMagnitude(n);
        if (compareMagnitude == 0) {
            return BigInteger.ZERO;
        }
        return new BigInteger(trustedStripLeadingZeroInts((compareMagnitude > 0) ? subtract(this.mag, Math.abs(n)) : subtract(Math.abs(n), this.mag)), (compareMagnitude == this.signum) ? 1 : -1);
    }
    
    private static int[] add(final int[] array, final long n) {
        int i = array.length;
        final int n2 = (int)(n >>> 32);
        int[] array2;
        long n3;
        if (n2 == 0) {
            array2 = new int[i];
            n3 = (array[--i] & 0xFFFFFFFFL) + n;
            array2[i] = (int)n3;
        }
        else {
            if (i == 1) {
                final int[] array3 = new int[2];
                final long n4 = n + (array[0] & 0xFFFFFFFFL);
                array3[1] = (int)n4;
                array3[0] = (int)(n4 >>> 32);
                return array3;
            }
            array2 = new int[i];
            final long n5 = (array[--i] & 0xFFFFFFFFL) + (n & 0xFFFFFFFFL);
            array2[i] = (int)n5;
            n3 = (array[--i] & 0xFFFFFFFFL) + (n2 & 0xFFFFFFFFL) + (n5 >>> 32);
            array2[i] = (int)n3;
        }
        boolean b;
        int[] array4;
        int n6;
        int n7;
        for (b = (n3 >>> 32 != 0L); i > 0 && b; n6 = --i, n7 = array[i] + 1, array4[n6] = n7, b = (n7 == 0)) {
            array4 = array2;
        }
        while (i > 0) {
            array2[--i] = array[i];
        }
        if (b) {
            final int[] array5 = new int[array2.length + 1];
            System.arraycopy(array2, 0, array5, 1, array2.length);
            array5[0] = 1;
            return array5;
        }
        return array2;
    }
    
    private static int[] add(int[] array, int[] array2) {
        if (array.length < array2.length) {
            final int[] array3 = array;
            array = array2;
            array2 = array3;
        }
        int i = array.length;
        int j = array2.length;
        final int[] array4 = new int[i];
        long n = 0L;
        if (j == 1) {
            n = (array[--i] & 0xFFFFFFFFL) + (array2[0] & 0xFFFFFFFFL);
            array4[i] = (int)n;
        }
        else {
            while (j > 0) {
                n = (array[--i] & 0xFFFFFFFFL) + (array2[--j] & 0xFFFFFFFFL) + (n >>> 32);
                array4[i] = (int)n;
            }
        }
        boolean b;
        int[] array5;
        int n2;
        int n3;
        for (b = (n >>> 32 != 0L); i > 0 && b; n2 = --i, n3 = array[i] + 1, array5[n2] = n3, b = (n3 == 0)) {
            array5 = array4;
        }
        while (i > 0) {
            array4[--i] = array[i];
        }
        if (b) {
            final int[] array6 = new int[array4.length + 1];
            System.arraycopy(array4, 0, array6, 1, array4.length);
            array6[0] = 1;
            return array6;
        }
        return array4;
    }
    
    private static int[] subtract(final long n, final int[] array) {
        final int n2 = (int)(n >>> 32);
        if (n2 == 0) {
            return new int[] { (int)(n - (array[0] & 0xFFFFFFFFL)) };
        }
        final int[] array2 = new int[2];
        if (array.length == 1) {
            final long n3 = ((int)n & 0xFFFFFFFFL) - (array[0] & 0xFFFFFFFFL);
            array2[1] = (int)n3;
            if (n3 >> 32 != 0L) {
                array2[0] = n2 - 1;
            }
            else {
                array2[0] = n2;
            }
            return array2;
        }
        final long n4 = ((int)n & 0xFFFFFFFFL) - (array[1] & 0xFFFFFFFFL);
        array2[1] = (int)n4;
        array2[0] = (int)((n2 & 0xFFFFFFFFL) - (array[0] & 0xFFFFFFFFL) + (n4 >> 32));
        return array2;
    }
    
    private static int[] subtract(final int[] array, final long n) {
        final int n2 = (int)(n >>> 32);
        int i = array.length;
        final int[] array2 = new int[i];
        long n3;
        if (n2 == 0) {
            n3 = (array[--i] & 0xFFFFFFFFL) - n;
            array2[i] = (int)n3;
        }
        else {
            final long n4 = (array[--i] & 0xFFFFFFFFL) - (n & 0xFFFFFFFFL);
            array2[i] = (int)n4;
            n3 = (array[--i] & 0xFFFFFFFFL) - (n2 & 0xFFFFFFFFL) + (n4 >> 32);
            array2[i] = (int)n3;
        }
        int[] array3;
        int n5;
        int n6;
        for (boolean b = n3 >> 32 != 0L; i > 0 && b; n5 = --i, n6 = array[i] - 1, array3[n5] = n6, b = (n6 == -1)) {
            array3 = array2;
        }
        while (i > 0) {
            array2[--i] = array[i];
        }
        return array2;
    }
    
    public BigInteger subtract(final BigInteger bigInteger) {
        if (bigInteger.signum == 0) {
            return this;
        }
        if (this.signum == 0) {
            return bigInteger.negate();
        }
        if (bigInteger.signum != this.signum) {
            return new BigInteger(add(this.mag, bigInteger.mag), this.signum);
        }
        final int compareMagnitude = this.compareMagnitude(bigInteger);
        if (compareMagnitude == 0) {
            return BigInteger.ZERO;
        }
        return new BigInteger(trustedStripLeadingZeroInts((compareMagnitude > 0) ? subtract(this.mag, bigInteger.mag) : subtract(bigInteger.mag, this.mag)), (compareMagnitude == this.signum) ? 1 : -1);
    }
    
    private static int[] subtract(final int[] array, final int[] array2) {
        int i;
        int[] array3;
        int j;
        long n;
        for (i = array.length, array3 = new int[i], j = array2.length, n = 0L; j > 0; n = (array[--i] & 0xFFFFFFFFL) - (array2[--j] & 0xFFFFFFFFL) + (n >> 32), array3[i] = (int)n) {}
        int[] array4;
        int n2;
        int n3;
        for (boolean b = n >> 32 != 0L; i > 0 && b; n2 = --i, n3 = array[i] - 1, array4[n2] = n3, b = (n3 == -1)) {
            array4 = array3;
        }
        while (i > 0) {
            array3[--i] = array[i];
        }
        return array3;
    }
    
    public BigInteger multiply(final BigInteger bigInteger) {
        return this.multiply(bigInteger, false);
    }
    
    private BigInteger multiply(final BigInteger bigInteger, final boolean b) {
        if (bigInteger.signum == 0 || this.signum == 0) {
            return BigInteger.ZERO;
        }
        final int length = this.mag.length;
        if (bigInteger == this && length > 20) {
            return this.square();
        }
        final int length2 = bigInteger.mag.length;
        if (length < 80 || length2 < 80) {
            final int n = (this.signum == bigInteger.signum) ? 1 : -1;
            if (bigInteger.mag.length == 1) {
                return multiplyByInt(this.mag, bigInteger.mag[0], n);
            }
            if (this.mag.length == 1) {
                return multiplyByInt(bigInteger.mag, this.mag[0], n);
            }
            return new BigInteger(trustedStripLeadingZeroInts(multiplyToLen(this.mag, length, bigInteger.mag, length2, null)), n);
        }
        else {
            if (length < 240 && length2 < 240) {
                return multiplyKaratsuba(this, bigInteger);
            }
            if (!b && bitLength(this.mag, this.mag.length) + bitLength(bigInteger.mag, bigInteger.mag.length) > 2147483648L) {
                reportOverflow();
            }
            return multiplyToomCook3(this, bigInteger);
        }
    }
    
    private static BigInteger multiplyByInt(final int[] array, final int n, final int n2) {
        if (Integer.bitCount(n) == 1) {
            return new BigInteger(shiftLeft(array, Integer.numberOfTrailingZeros(n)), n2);
        }
        final int length = array.length;
        int[] copyOfRange = new int[length + 1];
        long n3 = 0L;
        final long n4 = n & 0xFFFFFFFFL;
        int n5 = copyOfRange.length - 1;
        for (int i = length - 1; i >= 0; --i) {
            final long n6 = (array[i] & 0xFFFFFFFFL) * n4 + n3;
            copyOfRange[n5--] = (int)n6;
            n3 = n6 >>> 32;
        }
        if (n3 == 0L) {
            copyOfRange = Arrays.copyOfRange(copyOfRange, 1, copyOfRange.length);
        }
        else {
            copyOfRange[n5] = (int)n3;
        }
        return new BigInteger(copyOfRange, n2);
    }
    
    BigInteger multiply(long n) {
        if (n == 0L || this.signum == 0) {
            return BigInteger.ZERO;
        }
        if (n == Long.MIN_VALUE) {
            return this.multiply(valueOf(n));
        }
        final int n2 = (n > 0L) ? this.signum : (-this.signum);
        if (n < 0L) {
            n = -n;
        }
        final long n3 = n >>> 32;
        final long n4 = n & 0xFFFFFFFFL;
        final int length = this.mag.length;
        final int[] mag = this.mag;
        int[] copyOfRange = (n3 == 0L) ? new int[length + 1] : new int[length + 2];
        long n5 = 0L;
        int n6 = copyOfRange.length - 1;
        for (int i = length - 1; i >= 0; --i) {
            final long n7 = (mag[i] & 0xFFFFFFFFL) * n4 + n5;
            copyOfRange[n6--] = (int)n7;
            n5 = n7 >>> 32;
        }
        copyOfRange[n6] = (int)n5;
        if (n3 != 0L) {
            n5 = 0L;
            int n8 = copyOfRange.length - 2;
            for (int j = length - 1; j >= 0; --j) {
                final long n9 = (mag[j] & 0xFFFFFFFFL) * n3 + (copyOfRange[n8] & 0xFFFFFFFFL) + n5;
                copyOfRange[n8--] = (int)n9;
                n5 = n9 >>> 32;
            }
            copyOfRange[0] = (int)n5;
        }
        if (n5 == 0L) {
            copyOfRange = Arrays.copyOfRange(copyOfRange, 1, copyOfRange.length);
        }
        return new BigInteger(copyOfRange, n2);
    }
    
    private static int[] multiplyToLen(final int[] array, final int n, final int[] array2, final int n2, int[] array3) {
        final int n3 = n - 1;
        final int n4 = n2 - 1;
        if (array3 == null || array3.length < n + n2) {
            array3 = new int[n + n2];
        }
        long n5 = 0L;
        for (int i = n4, n6 = n4 + 1 + n3; i >= 0; --i, --n6) {
            final long n7 = (array2[i] & 0xFFFFFFFFL) * (array[n3] & 0xFFFFFFFFL) + n5;
            array3[n6] = (int)n7;
            n5 = n7 >>> 32;
        }
        array3[n3] = (int)n5;
        for (int j = n3 - 1; j >= 0; --j) {
            long n8 = 0L;
            for (int k = n4, n9 = n4 + 1 + j; k >= 0; --k, --n9) {
                final long n10 = (array2[k] & 0xFFFFFFFFL) * (array[j] & 0xFFFFFFFFL) + (array3[n9] & 0xFFFFFFFFL) + n8;
                array3[n9] = (int)n10;
                n8 = n10 >>> 32;
            }
            array3[j] = (int)n8;
        }
        return array3;
    }
    
    private static BigInteger multiplyKaratsuba(final BigInteger bigInteger, final BigInteger bigInteger2) {
        final int n = (Math.max(bigInteger.mag.length, bigInteger2.mag.length) + 1) / 2;
        final BigInteger lower = bigInteger.getLower(n);
        final BigInteger upper = bigInteger.getUpper(n);
        final BigInteger lower2 = bigInteger2.getLower(n);
        final BigInteger upper2 = bigInteger2.getUpper(n);
        final BigInteger multiply = upper.multiply(upper2);
        final BigInteger multiply2 = lower.multiply(lower2);
        final BigInteger add = multiply.shiftLeft(32 * n).add(upper.add(lower).multiply(upper2.add(lower2)).subtract(multiply).subtract(multiply2)).shiftLeft(32 * n).add(multiply2);
        if (bigInteger.signum != bigInteger2.signum) {
            return add.negate();
        }
        return add;
    }
    
    private static BigInteger multiplyToomCook3(final BigInteger bigInteger, final BigInteger bigInteger2) {
        final int max = Math.max(bigInteger.mag.length, bigInteger2.mag.length);
        final int n = (max + 2) / 3;
        final int n2 = max - 2 * n;
        final BigInteger toomSlice = bigInteger.getToomSlice(n, n2, 0, max);
        final BigInteger toomSlice2 = bigInteger.getToomSlice(n, n2, 1, max);
        final BigInteger toomSlice3 = bigInteger.getToomSlice(n, n2, 2, max);
        final BigInteger toomSlice4 = bigInteger2.getToomSlice(n, n2, 0, max);
        final BigInteger toomSlice5 = bigInteger2.getToomSlice(n, n2, 1, max);
        final BigInteger toomSlice6 = bigInteger2.getToomSlice(n, n2, 2, max);
        final BigInteger multiply = toomSlice3.multiply(toomSlice6, true);
        final BigInteger add = toomSlice.add(toomSlice3);
        final BigInteger add2 = toomSlice4.add(toomSlice6);
        final BigInteger multiply2 = add.subtract(toomSlice2).multiply(add2.subtract(toomSlice5), true);
        final BigInteger add3 = add.add(toomSlice2);
        final BigInteger add4 = add2.add(toomSlice5);
        final BigInteger multiply3 = add3.multiply(add4, true);
        final BigInteger multiply4 = add3.add(toomSlice).shiftLeft(1).subtract(toomSlice3).multiply(add4.add(toomSlice4).shiftLeft(1).subtract(toomSlice6), true);
        final BigInteger multiply5 = toomSlice.multiply(toomSlice4, true);
        final BigInteger exactDivideBy3 = multiply4.subtract(multiply2).exactDivideBy3();
        final BigInteger shiftRight = multiply3.subtract(multiply2).shiftRight(1);
        final BigInteger subtract = multiply3.subtract(multiply);
        final BigInteger shiftRight2 = exactDivideBy3.subtract(subtract).shiftRight(1);
        final BigInteger subtract2 = subtract.subtract(shiftRight).subtract(multiply5);
        final BigInteger subtract3 = shiftRight2.subtract(multiply5.shiftLeft(1));
        final BigInteger subtract4 = shiftRight.subtract(subtract3);
        final int n3 = n * 32;
        final BigInteger add5 = multiply5.shiftLeft(n3).add(subtract3).shiftLeft(n3).add(subtract2).shiftLeft(n3).add(subtract4).shiftLeft(n3).add(multiply);
        if (bigInteger.signum != bigInteger2.signum) {
            return add5.negate();
        }
        return add5;
    }
    
    private BigInteger getToomSlice(final int n, final int n2, final int n3, final int n4) {
        final int length = this.mag.length;
        final int n5 = n4 - length;
        int n6;
        int n7;
        if (n3 == 0) {
            n6 = 0 - n5;
            n7 = n2 - 1 - n5;
        }
        else {
            n6 = n2 + (n3 - 1) * n - n5;
            n7 = n6 + n - 1;
        }
        if (n6 < 0) {
            n6 = 0;
        }
        if (n7 < 0) {
            return BigInteger.ZERO;
        }
        final int n8 = n7 - n6 + 1;
        if (n8 <= 0) {
            return BigInteger.ZERO;
        }
        if (n6 == 0 && n8 >= length) {
            return this.abs();
        }
        final int[] array = new int[n8];
        System.arraycopy(this.mag, n6, array, 0, n8);
        return new BigInteger(trustedStripLeadingZeroInts(array), 1);
    }
    
    private BigInteger exactDivideBy3() {
        final int length = this.mag.length;
        final int[] array = new int[length];
        long n = 0L;
        for (int i = length - 1; i >= 0; --i) {
            final long n2 = this.mag[i] & 0xFFFFFFFFL;
            final long n3 = n2 - n;
            if (n > n2) {
                n = 1L;
            }
            else {
                n = 0L;
            }
            final long n4 = n3 * 2863311531L & 0xFFFFFFFFL;
            array[i] = (int)n4;
            if (n4 >= 1431655766L) {
                ++n;
                if (n4 >= 2863311531L) {
                    ++n;
                }
            }
        }
        return new BigInteger(trustedStripLeadingZeroInts(array), this.signum);
    }
    
    private BigInteger getLower(final int n) {
        final int length = this.mag.length;
        if (length <= n) {
            return this.abs();
        }
        final int[] array = new int[n];
        System.arraycopy(this.mag, length - n, array, 0, n);
        return new BigInteger(trustedStripLeadingZeroInts(array), 1);
    }
    
    private BigInteger getUpper(final int n) {
        final int length = this.mag.length;
        if (length <= n) {
            return BigInteger.ZERO;
        }
        final int n2 = length - n;
        final int[] array = new int[n2];
        System.arraycopy(this.mag, 0, array, 0, n2);
        return new BigInteger(trustedStripLeadingZeroInts(array), 1);
    }
    
    private BigInteger square() {
        return this.square(false);
    }
    
    private BigInteger square(final boolean b) {
        if (this.signum == 0) {
            return BigInteger.ZERO;
        }
        final int length = this.mag.length;
        if (length < 128) {
            return new BigInteger(trustedStripLeadingZeroInts(squareToLen(this.mag, length, null)), 1);
        }
        if (length < 216) {
            return this.squareKaratsuba();
        }
        if (!b && bitLength(this.mag, this.mag.length) > 1073741824L) {
            reportOverflow();
        }
        return this.squareToomCook3();
    }
    
    private static final int[] squareToLen(final int[] array, final int n, int[] array2) {
        final int n2 = n << 1;
        if (array2 == null || array2.length < n2) {
            array2 = new int[n2];
        }
        implSquareToLenChecks(array, n, array2, n2);
        return implSquareToLen(array, n, array2, n2);
    }
    
    private static void implSquareToLenChecks(final int[] array, final int n, final int[] array2, final int n2) throws RuntimeException {
        if (n < 1) {
            throw new IllegalArgumentException("invalid input length: " + n);
        }
        if (n > array.length) {
            throw new IllegalArgumentException("input length out of bound: " + n + " > " + array.length);
        }
        if (n * 2 > array2.length) {
            throw new IllegalArgumentException("input length out of bound: " + n * 2 + " > " + array2.length);
        }
        if (n2 < 1) {
            throw new IllegalArgumentException("invalid input length: " + n2);
        }
        if (n2 > array2.length) {
            throw new IllegalArgumentException("input length out of bound: " + n + " > " + array2.length);
        }
    }
    
    private static final int[] implSquareToLen(final int[] array, final int n, final int[] array2, final int n2) {
        int n3 = 0;
        int i = 0;
        int n4 = 0;
        while (i < n) {
            final long n5 = array[i] & 0xFFFFFFFFL;
            final long n6 = n5 * n5;
            array2[n4++] = (n3 << 31 | (int)(n6 >>> 33));
            array2[n4++] = (int)(n6 >>> 1);
            n3 = (int)n6;
            ++i;
        }
        for (int j = n, n7 = 1; j > 0; --j, n7 += 2) {
            addOne(array2, n7 - 1, j, mulAdd(array2, array, n7, j - 1, array[j - 1]));
        }
        primitiveLeftShift(array2, n2, 1);
        final int n8 = n2 - 1;
        array2[n8] |= (array[n - 1] & 0x1);
        return array2;
    }
    
    private BigInteger squareKaratsuba() {
        final int n = (this.mag.length + 1) / 2;
        final BigInteger lower = this.getLower(n);
        final BigInteger upper = this.getUpper(n);
        final BigInteger square = upper.square();
        final BigInteger square2 = lower.square();
        return square.shiftLeft(n * 32).add(lower.add(upper).square().subtract(square.add(square2))).shiftLeft(n * 32).add(square2);
    }
    
    private BigInteger squareToomCook3() {
        final int length = this.mag.length;
        final int n = (length + 2) / 3;
        final int n2 = length - 2 * n;
        final BigInteger toomSlice = this.getToomSlice(n, n2, 0, length);
        final BigInteger toomSlice2 = this.getToomSlice(n, n2, 1, length);
        final BigInteger toomSlice3 = this.getToomSlice(n, n2, 2, length);
        final BigInteger square = toomSlice3.square(true);
        final BigInteger add = toomSlice.add(toomSlice3);
        final BigInteger square2 = add.subtract(toomSlice2).square(true);
        final BigInteger add2 = add.add(toomSlice2);
        final BigInteger square3 = add2.square(true);
        final BigInteger square4 = toomSlice.square(true);
        final BigInteger exactDivideBy3 = add2.add(toomSlice).shiftLeft(1).subtract(toomSlice3).square(true).subtract(square2).exactDivideBy3();
        final BigInteger shiftRight = square3.subtract(square2).shiftRight(1);
        final BigInteger subtract = square3.subtract(square);
        final BigInteger shiftRight2 = exactDivideBy3.subtract(subtract).shiftRight(1);
        final BigInteger subtract2 = subtract.subtract(shiftRight).subtract(square4);
        final BigInteger subtract3 = shiftRight2.subtract(square4.shiftLeft(1));
        final BigInteger subtract4 = shiftRight.subtract(subtract3);
        final int n3 = n * 32;
        return square4.shiftLeft(n3).add(subtract3).shiftLeft(n3).add(subtract2).shiftLeft(n3).add(subtract4).shiftLeft(n3).add(square);
    }
    
    public BigInteger divide(final BigInteger bigInteger) {
        if (bigInteger.mag.length < 80 || this.mag.length - bigInteger.mag.length < 40) {
            return this.divideKnuth(bigInteger);
        }
        return this.divideBurnikelZiegler(bigInteger);
    }
    
    private BigInteger divideKnuth(final BigInteger bigInteger) {
        final MutableBigInteger mutableBigInteger = new MutableBigInteger();
        new MutableBigInteger(this.mag).divideKnuth(new MutableBigInteger(bigInteger.mag), mutableBigInteger, false);
        return mutableBigInteger.toBigInteger(this.signum * bigInteger.signum);
    }
    
    public BigInteger[] divideAndRemainder(final BigInteger bigInteger) {
        if (bigInteger.mag.length < 80 || this.mag.length - bigInteger.mag.length < 40) {
            return this.divideAndRemainderKnuth(bigInteger);
        }
        return this.divideAndRemainderBurnikelZiegler(bigInteger);
    }
    
    private BigInteger[] divideAndRemainderKnuth(final BigInteger bigInteger) {
        final BigInteger[] array = new BigInteger[2];
        final MutableBigInteger mutableBigInteger = new MutableBigInteger();
        final MutableBigInteger divideKnuth = new MutableBigInteger(this.mag).divideKnuth(new MutableBigInteger(bigInteger.mag), mutableBigInteger);
        array[0] = mutableBigInteger.toBigInteger((this.signum == bigInteger.signum) ? 1 : -1);
        array[1] = divideKnuth.toBigInteger(this.signum);
        return array;
    }
    
    public BigInteger remainder(final BigInteger bigInteger) {
        if (bigInteger.mag.length < 80 || this.mag.length - bigInteger.mag.length < 40) {
            return this.remainderKnuth(bigInteger);
        }
        return this.remainderBurnikelZiegler(bigInteger);
    }
    
    private BigInteger remainderKnuth(final BigInteger bigInteger) {
        return new MutableBigInteger(this.mag).divideKnuth(new MutableBigInteger(bigInteger.mag), new MutableBigInteger()).toBigInteger(this.signum);
    }
    
    private BigInteger divideBurnikelZiegler(final BigInteger bigInteger) {
        return this.divideAndRemainderBurnikelZiegler(bigInteger)[0];
    }
    
    private BigInteger remainderBurnikelZiegler(final BigInteger bigInteger) {
        return this.divideAndRemainderBurnikelZiegler(bigInteger)[1];
    }
    
    private BigInteger[] divideAndRemainderBurnikelZiegler(final BigInteger bigInteger) {
        final MutableBigInteger mutableBigInteger = new MutableBigInteger();
        final MutableBigInteger divideAndRemainderBurnikelZiegler = new MutableBigInteger(this).divideAndRemainderBurnikelZiegler(new MutableBigInteger(bigInteger), mutableBigInteger);
        return new BigInteger[] { mutableBigInteger.isZero() ? BigInteger.ZERO : mutableBigInteger.toBigInteger(this.signum * bigInteger.signum), divideAndRemainderBurnikelZiegler.isZero() ? BigInteger.ZERO : divideAndRemainderBurnikelZiegler.toBigInteger(this.signum) };
    }
    
    public BigInteger pow(final int n) {
        if (n < 0) {
            throw new ArithmeticException("Negative exponent");
        }
        if (this.signum == 0) {
            return (n == 0) ? BigInteger.ONE : this;
        }
        BigInteger bigInteger = this.abs();
        final int lowestSetBit = bigInteger.getLowestSetBit();
        final long n2 = lowestSetBit * n;
        if (n2 > 2147483647L) {
            reportOverflow();
        }
        final int n3 = (int)n2;
        int n4;
        if (lowestSetBit > 0) {
            bigInteger = bigInteger.shiftRight(lowestSetBit);
            n4 = bigInteger.bitLength();
            if (n4 == 1) {
                if (this.signum < 0 && (n & 0x1) == 0x1) {
                    return BigInteger.NEGATIVE_ONE.shiftLeft(n3);
                }
                return BigInteger.ONE.shiftLeft(n3);
            }
        }
        else {
            n4 = bigInteger.bitLength();
            if (n4 == 1) {
                if (this.signum < 0 && (n & 0x1) == 0x1) {
                    return BigInteger.NEGATIVE_ONE;
                }
                return BigInteger.ONE;
            }
        }
        final long n5 = n4 * n;
        if (bigInteger.mag.length == 1 && n5 <= 62L) {
            final int n6 = (this.signum < 0 && (n & 0x1) == 0x1) ? -1 : 1;
            long n7 = 1L;
            long n8 = bigInteger.mag[0] & 0xFFFFFFFFL;
            int i = n;
            while (i != 0) {
                if ((i & 0x1) == 0x1) {
                    n7 *= n8;
                }
                if ((i >>>= 1) != 0) {
                    n8 *= n8;
                }
            }
            if (lowestSetBit <= 0) {
                return valueOf(n7 * n6);
            }
            if (n3 + n5 <= 62L) {
                return valueOf((n7 << n3) * n6);
            }
            return valueOf(n7 * n6).shiftLeft(n3);
        }
        else {
            if (this.bitLength() * n / 32L > 67108864L) {
                reportOverflow();
            }
            BigInteger bigInteger2 = BigInteger.ONE;
            int j = n;
            while (j != 0) {
                if ((j & 0x1) == 0x1) {
                    bigInteger2 = bigInteger2.multiply(bigInteger);
                }
                if ((j >>>= 1) != 0) {
                    bigInteger = bigInteger.square();
                }
            }
            if (lowestSetBit > 0) {
                bigInteger2 = bigInteger2.shiftLeft(n3);
            }
            if (this.signum < 0 && (n & 0x1) == 0x1) {
                return bigInteger2.negate();
            }
            return bigInteger2;
        }
    }
    
    public BigInteger gcd(final BigInteger bigInteger) {
        if (bigInteger.signum == 0) {
            return this.abs();
        }
        if (this.signum == 0) {
            return bigInteger.abs();
        }
        return new MutableBigInteger(this).hybridGCD(new MutableBigInteger(bigInteger)).toBigInteger(1);
    }
    
    static int bitLengthForInt(final int n) {
        return 32 - Integer.numberOfLeadingZeros(n);
    }
    
    private static int[] leftShift(final int[] array, final int n, final int n2) {
        final int n3 = n2 >>> 5;
        final int n4 = n2 & 0x1F;
        final int bitLengthForInt = bitLengthForInt(array[0]);
        if (n2 <= 32 - bitLengthForInt) {
            primitiveLeftShift(array, n, n4);
            return array;
        }
        if (n4 <= 32 - bitLengthForInt) {
            final int[] array2 = new int[n3 + n];
            System.arraycopy(array, 0, array2, 0, n);
            primitiveLeftShift(array2, array2.length, n4);
            return array2;
        }
        final int[] array3 = new int[n3 + n + 1];
        System.arraycopy(array, 0, array3, 0, n);
        primitiveRightShift(array3, array3.length, 32 - n4);
        return array3;
    }
    
    static void primitiveRightShift(final int[] array, final int n, final int n2) {
        final int n3 = 32 - n2;
        int i = n - 1;
        int n4 = array[i];
        while (i > 0) {
            final int n5 = n4;
            n4 = array[i - 1];
            array[i] = (n4 << n3 | n5 >>> n2);
            --i;
        }
        final int n6 = 0;
        array[n6] >>>= n2;
    }
    
    static void primitiveLeftShift(final int[] array, final int n, final int n2) {
        if (n == 0 || n2 == 0) {
            return;
        }
        final int n3 = 32 - n2;
        int i = 0;
        int n4 = array[i];
        while (i < i + n - 1) {
            final int n5 = n4;
            n4 = array[i + 1];
            array[i] = (n5 << n2 | n4 >>> n3);
            ++i;
        }
        final int n6 = n - 1;
        array[n6] <<= n2;
    }
    
    private static int bitLength(final int[] array, final int n) {
        if (n == 0) {
            return 0;
        }
        return (n - 1 << 5) + bitLengthForInt(array[0]);
    }
    
    public BigInteger abs() {
        return (this.signum >= 0) ? this : this.negate();
    }
    
    public BigInteger negate() {
        return new BigInteger(this.mag, -this.signum);
    }
    
    public int signum() {
        return this.signum;
    }
    
    public BigInteger mod(final BigInteger bigInteger) {
        if (bigInteger.signum <= 0) {
            throw new ArithmeticException("BigInteger: modulus not positive");
        }
        final BigInteger remainder = this.remainder(bigInteger);
        return (remainder.signum >= 0) ? remainder : remainder.add(bigInteger);
    }
    
    public BigInteger modPow(BigInteger negate, final BigInteger bigInteger) {
        if (bigInteger.signum <= 0) {
            throw new ArithmeticException("BigInteger: modulus not positive");
        }
        if (negate.signum == 0) {
            return bigInteger.equals(BigInteger.ONE) ? BigInteger.ZERO : BigInteger.ONE;
        }
        if (this.equals(BigInteger.ONE)) {
            return bigInteger.equals(BigInteger.ONE) ? BigInteger.ZERO : BigInteger.ONE;
        }
        if (this.equals(BigInteger.ZERO) && negate.signum >= 0) {
            return BigInteger.ZERO;
        }
        if (this.equals(BigInteger.negConst[1]) && !negate.testBit(0)) {
            return bigInteger.equals(BigInteger.ONE) ? BigInteger.ZERO : BigInteger.ONE;
        }
        final boolean b;
        if (b = (negate.signum < 0)) {
            negate = negate.negate();
        }
        final BigInteger bigInteger2 = (this.signum < 0 || this.compareTo(bigInteger) >= 0) ? this.mod(bigInteger) : this;
        BigInteger bigInteger3;
        if (bigInteger.testBit(0)) {
            bigInteger3 = bigInteger2.oddModPow(negate, bigInteger);
        }
        else {
            final int lowestSetBit = bigInteger.getLowestSetBit();
            final BigInteger shiftRight = bigInteger.shiftRight(lowestSetBit);
            final BigInteger shiftLeft = BigInteger.ONE.shiftLeft(lowestSetBit);
            final BigInteger bigInteger4 = (this.signum < 0 || this.compareTo(shiftRight) >= 0) ? this.mod(shiftRight) : this;
            final BigInteger bigInteger5 = shiftRight.equals(BigInteger.ONE) ? BigInteger.ZERO : bigInteger4.oddModPow(negate, shiftRight);
            final BigInteger modPow2 = bigInteger2.modPow2(negate, lowestSetBit);
            final BigInteger modInverse = shiftLeft.modInverse(shiftRight);
            final BigInteger modInverse2 = shiftRight.modInverse(shiftLeft);
            if (bigInteger.mag.length < 33554432) {
                bigInteger3 = bigInteger5.multiply(shiftLeft).multiply(modInverse).add(modPow2.multiply(shiftRight).multiply(modInverse2)).mod(bigInteger);
            }
            else {
                final MutableBigInteger mutableBigInteger = new MutableBigInteger();
                new MutableBigInteger(bigInteger5.multiply(shiftLeft)).multiply(new MutableBigInteger(modInverse), mutableBigInteger);
                final MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
                new MutableBigInteger(modPow2.multiply(shiftRight)).multiply(new MutableBigInteger(modInverse2), mutableBigInteger2);
                mutableBigInteger.add(mutableBigInteger2);
                bigInteger3 = mutableBigInteger.divide(new MutableBigInteger(bigInteger), new MutableBigInteger()).toBigInteger();
            }
        }
        return b ? bigInteger3.modInverse(bigInteger) : bigInteger3;
    }
    
    private static int[] montgomeryMultiply(final int[] array, final int[] array2, final int[] array3, final int n, final long n2, int[] multiplyToLen) {
        implMontgomeryMultiplyChecks(array, array2, array3, n, multiplyToLen);
        if (n > 512) {
            multiplyToLen = multiplyToLen(array, n, array2, n, multiplyToLen);
            return montReduce(multiplyToLen, array3, n, (int)n2);
        }
        return implMontgomeryMultiply(array, array2, array3, n, n2, materialize(multiplyToLen, n));
    }
    
    private static int[] montgomerySquare(final int[] array, final int[] array2, final int n, final long n2, int[] squareToLen) {
        implMontgomeryMultiplyChecks(array, array, array2, n, squareToLen);
        if (n > 512) {
            squareToLen = squareToLen(array, n, squareToLen);
            return montReduce(squareToLen, array2, n, (int)n2);
        }
        return implMontgomerySquare(array, array2, n, n2, materialize(squareToLen, n));
    }
    
    private static void implMontgomeryMultiplyChecks(final int[] array, final int[] array2, final int[] array3, final int n, final int[] array4) throws RuntimeException {
        if (n % 2 != 0) {
            throw new IllegalArgumentException("input array length must be even: " + n);
        }
        if (n < 1) {
            throw new IllegalArgumentException("invalid input length: " + n);
        }
        if (n > array.length || n > array2.length || n > array3.length || (array4 != null && n > array4.length)) {
            throw new IllegalArgumentException("input array length out of bound: " + n);
        }
    }
    
    private static int[] materialize(int[] array, final int n) {
        if (array == null || array.length < n) {
            array = new int[n];
        }
        return array;
    }
    
    private static int[] implMontgomeryMultiply(final int[] array, final int[] array2, final int[] array3, final int n, final long n2, int[] multiplyToLen) {
        multiplyToLen = multiplyToLen(array, n, array2, n, multiplyToLen);
        return montReduce(multiplyToLen, array3, n, (int)n2);
    }
    
    private static int[] implMontgomerySquare(final int[] array, final int[] array2, final int n, final long n2, int[] squareToLen) {
        squareToLen = squareToLen(array, n, squareToLen);
        return montReduce(squareToLen, array2, n, (int)n2);
    }
    
    private BigInteger oddModPow(final BigInteger bigInteger, final BigInteger bigInteger2) {
        if (bigInteger.equals(BigInteger.ONE)) {
            return this;
        }
        if (this.signum == 0) {
            return BigInteger.ZERO;
        }
        final int[] array = this.mag.clone();
        final int[] mag = bigInteger.mag;
        int[] mag2 = bigInteger2.mag;
        int length = mag2.length;
        if ((length & 0x1) != 0x0) {
            final int[] array2 = new int[length + 1];
            System.arraycopy(mag2, 0, array2, 1, length);
            mag2 = array2;
            ++length;
        }
        int n = 0;
        int i = bitLength(mag, mag.length);
        if (i != 17 || mag[0] != 65537) {
            while (i > BigInteger.bnExpModThreshTable[n]) {
                ++n;
            }
        }
        final int n2 = 1 << n;
        final int[][] array3 = new int[n2][];
        for (int j = 0; j < n2; ++j) {
            array3[j] = new int[length];
        }
        final long n3 = -MutableBigInteger.inverseMod64((mag2[length - 1] & 0xFFFFFFFFL) + ((mag2[length - 2] & 0xFFFFFFFFL) << 32));
        int[] leftShift = leftShift(array, array.length, length << 5);
        final MutableBigInteger mutableBigInteger = new MutableBigInteger();
        final MutableBigInteger mutableBigInteger2 = new MutableBigInteger(leftShift);
        final MutableBigInteger mutableBigInteger3 = new MutableBigInteger(mag2);
        mutableBigInteger3.normalize();
        array3[0] = mutableBigInteger2.divide(mutableBigInteger3, mutableBigInteger).toIntArray();
        if (array3[0].length < length) {
            final int n4 = length - array3[0].length;
            final int[] array4 = new int[length];
            System.arraycopy(array3[0], 0, array4, n4, array3[0].length);
            array3[0] = array4;
        }
        int[] montgomerySquare = montgomerySquare(array3[0], mag2, length, n3, null);
        final int[] copy = Arrays.copyOf(montgomerySquare, length);
        for (int k = 1; k < n2; ++k) {
            array3[k] = montgomeryMultiply(copy, array3[k - 1], mag2, length, n3, null);
        }
        int n5 = 1 << (i - 1 & 0x1F);
        int n6 = 0;
        int length2 = mag.length;
        int n7 = 0;
        for (int l = 0; l <= n; ++l) {
            n6 = (n6 << 1 | (((mag[n7] & n5) != 0x0) ? 1 : 0));
            n5 >>>= 1;
            if (n5 == 0) {
                ++n7;
                n5 = Integer.MIN_VALUE;
                --length2;
            }
        }
        --i;
        int n8 = 1;
        int n9;
        for (n9 = i - n; (n6 & 0x1) == 0x0; n6 >>>= 1, ++n9) {}
        int[] array5 = array3[n6 >>> 1];
        int n10 = 0;
        if (n9 == i) {
            n8 = 0;
        }
        while (true) {
            --i;
            n10 <<= 1;
            if (length2 != 0) {
                n10 |= (((mag[n7] & n5) != 0x0) ? 1 : 0);
                n5 >>>= 1;
                if (n5 == 0) {
                    ++n7;
                    n5 = Integer.MIN_VALUE;
                    --length2;
                }
            }
            if ((n10 & n2) != 0x0) {
                for (n9 = i - n; (n10 & 0x1) == 0x0; n10 >>>= 1, ++n9) {}
                array5 = array3[n10 >>> 1];
                n10 = 0;
            }
            if (i == n9) {
                if (n8 != 0) {
                    montgomerySquare = array5.clone();
                    n8 = 0;
                }
                else {
                    final int[] montgomeryMultiply = montgomeryMultiply(montgomerySquare, array5, mag2, length, n3, leftShift);
                    leftShift = montgomerySquare;
                    montgomerySquare = montgomeryMultiply;
                }
            }
            if (i == 0) {
                break;
            }
            if (n8 != 0) {
                continue;
            }
            final int[] montgomerySquare2 = montgomerySquare(montgomerySquare, mag2, length, n3, leftShift);
            leftShift = montgomerySquare;
            montgomerySquare = montgomerySquare2;
        }
        final int[] array6 = new int[2 * length];
        System.arraycopy(montgomerySquare, 0, array6, length, length);
        return new BigInteger(1, Arrays.copyOf(montReduce(array6, mag2, length, (int)n3), length));
    }
    
    private static int[] montReduce(final int[] array, final int[] array2, int n, final int n2) {
        int i = 0;
        int n3 = 0;
        do {
            i += addOne(array, n3, n, mulAdd(array, array2, n3, n, n2 * array[array.length - 1 - n3]));
            ++n3;
        } while (--n > 0);
        while (i > 0) {
            i += subN(array, array2, n);
        }
        while (intArrayCmpToLen(array, array2, n) >= 0) {
            subN(array, array2, n);
        }
        return array;
    }
    
    private static int intArrayCmpToLen(final int[] array, final int[] array2, final int n) {
        for (int i = 0; i < n; ++i) {
            final long n2 = array[i] & 0xFFFFFFFFL;
            final long n3 = array2[i] & 0xFFFFFFFFL;
            if (n2 < n3) {
                return -1;
            }
            if (n2 > n3) {
                return 1;
            }
        }
        return 0;
    }
    
    private static int subN(final int[] array, final int[] array2, int n) {
        long n2 = 0L;
        while (--n >= 0) {
            n2 = (array[n] & 0xFFFFFFFFL) - (array2[n] & 0xFFFFFFFFL) + (n2 >> 32);
            array[n] = (int)n2;
        }
        return (int)(n2 >> 32);
    }
    
    static int mulAdd(final int[] array, final int[] array2, final int n, final int n2, final int n3) {
        implMulAddCheck(array, array2, n, n2, n3);
        return implMulAdd(array, array2, n, n2, n3);
    }
    
    private static void implMulAddCheck(final int[] array, final int[] array2, final int n, final int n2, final int n3) {
        if (n2 > array2.length) {
            throw new IllegalArgumentException("input length is out of bound: " + n2 + " > " + array2.length);
        }
        if (n < 0) {
            throw new IllegalArgumentException("input offset is invalid: " + n);
        }
        if (n > array.length - 1) {
            throw new IllegalArgumentException("input offset is out of bound: " + n + " > " + (array.length - 1));
        }
        if (n2 > array.length - n) {
            throw new IllegalArgumentException("input len is out of bound: " + n2 + " > " + (array.length - n));
        }
    }
    
    private static int implMulAdd(final int[] array, final int[] array2, int n, final int n2, final int n3) {
        final long n4 = n3 & 0xFFFFFFFFL;
        long n5 = 0L;
        n = array.length - n - 1;
        for (int i = n2 - 1; i >= 0; --i) {
            final long n6 = (array2[i] & 0xFFFFFFFFL) * n4 + (array[n] & 0xFFFFFFFFL) + n5;
            array[n--] = (int)n6;
            n5 = n6 >>> 32;
        }
        return (int)n5;
    }
    
    static int addOne(final int[] array, int n, int n2, final int n3) {
        n = array.length - 1 - n2 - n;
        final long n4 = (array[n] & 0xFFFFFFFFL) + (n3 & 0xFFFFFFFFL);
        array[n] = (int)n4;
        if (n4 >>> 32 == 0L) {
            return 0;
        }
        while (--n2 >= 0) {
            if (--n < 0) {
                return 1;
            }
            final int n5 = n;
            ++array[n5];
            if (array[n] != 0) {
                return 0;
            }
        }
        return 1;
    }
    
    private BigInteger modPow2(final BigInteger bigInteger, final int n) {
        BigInteger bigInteger2 = BigInteger.ONE;
        BigInteger bigInteger3 = this.mod2(n);
        int i = 0;
        int bitLength = bigInteger.bitLength();
        if (this.testBit(0)) {
            bitLength = ((n - 1 < bitLength) ? (n - 1) : bitLength);
        }
        while (i < bitLength) {
            if (bigInteger.testBit(i)) {
                bigInteger2 = bigInteger2.multiply(bigInteger3).mod2(n);
            }
            if (++i < bitLength) {
                bigInteger3 = bigInteger3.square().mod2(n);
            }
        }
        return bigInteger2;
    }
    
    private BigInteger mod2(final int n) {
        if (this.bitLength() <= n) {
            return this;
        }
        final int n2 = n + 31 >>> 5;
        final int[] array = new int[n2];
        System.arraycopy(this.mag, this.mag.length - n2, array, 0, n2);
        final int n3 = (n2 << 5) - n;
        final int[] array2 = array;
        final int n4 = 0;
        array2[n4] &= (int)((1L << 32 - n3) - 1L);
        return (array[0] == 0) ? new BigInteger(1, array) : new BigInteger(array, 1);
    }
    
    public BigInteger modInverse(final BigInteger bigInteger) {
        if (bigInteger.signum != 1) {
            throw new ArithmeticException("BigInteger: modulus not positive");
        }
        if (bigInteger.equals(BigInteger.ONE)) {
            return BigInteger.ZERO;
        }
        BigInteger mod = this;
        if (this.signum < 0 || this.compareMagnitude(bigInteger) >= 0) {
            mod = this.mod(bigInteger);
        }
        if (mod.equals(BigInteger.ONE)) {
            return BigInteger.ONE;
        }
        return new MutableBigInteger(mod).mutableModInverse(new MutableBigInteger(bigInteger)).toBigInteger(1);
    }
    
    public BigInteger shiftLeft(final int n) {
        if (this.signum == 0) {
            return BigInteger.ZERO;
        }
        if (n > 0) {
            return new BigInteger(shiftLeft(this.mag, n), this.signum);
        }
        if (n == 0) {
            return this;
        }
        return this.shiftRightImpl(-n);
    }
    
    private static int[] shiftLeft(final int[] array, final int n) {
        final int n2 = n >>> 5;
        final int n3 = n & 0x1F;
        final int length = array.length;
        int[] array2;
        if (n3 == 0) {
            array2 = new int[length + n2];
            System.arraycopy(array, 0, array2, 0, length);
        }
        else {
            int n4 = 0;
            final int n5 = 32 - n3;
            final int n6 = array[0] >>> n5;
            if (n6 != 0) {
                array2 = new int[length + n2 + 1];
                array2[n4++] = n6;
            }
            else {
                array2 = new int[length + n2];
            }
            int i;
            for (i = 0; i < length - 1; array2[n4++] = (array[i++] << n3 | array[i] >>> n5)) {}
            array2[n4] = array[i] << n3;
        }
        return array2;
    }
    
    public BigInteger shiftRight(final int n) {
        if (this.signum == 0) {
            return BigInteger.ZERO;
        }
        if (n > 0) {
            return this.shiftRightImpl(n);
        }
        if (n == 0) {
            return this;
        }
        return new BigInteger(shiftLeft(this.mag, -n), this.signum);
    }
    
    private BigInteger shiftRightImpl(final int n) {
        final int n2 = n >>> 5;
        final int n3 = n & 0x1F;
        final int length = this.mag.length;
        if (n2 >= length) {
            return (this.signum >= 0) ? BigInteger.ZERO : BigInteger.negConst[1];
        }
        int[] array;
        if (n3 == 0) {
            array = Arrays.copyOf(this.mag, length - n2);
        }
        else {
            int n4 = 0;
            final int n5 = this.mag[0] >>> n3;
            if (n5 != 0) {
                array = new int[length - n2];
                array[n4++] = n5;
            }
            else {
                array = new int[length - n2 - 1];
            }
            for (int n6 = 32 - n3, i = 0; i < length - n2 - 1; array[n4++] = (this.mag[i++] << n6 | this.mag[i] >>> n3)) {}
        }
        if (this.signum < 0) {
            boolean b = false;
            for (int n7 = length - 1; n7 >= length - n2 && !b; b = (this.mag[n7] != 0), --n7) {}
            if (!b && n3 != 0) {
                b = (this.mag[length - n2 - 1] << 32 - n3 != 0);
            }
            if (b) {
                array = this.javaIncrement(array);
            }
        }
        return new BigInteger(array, this.signum);
    }
    
    int[] javaIncrement(int[] array) {
        int n = 0;
        for (int n2 = array.length - 1; n2 >= 0 && n == 0; n = ++array[n2], --n2) {}
        if (n == 0) {
            array = new int[array.length + 1];
            array[0] = 1;
        }
        return array;
    }
    
    public BigInteger and(final BigInteger bigInteger) {
        final int[] array = new int[Math.max(this.intLength(), bigInteger.intLength())];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (this.getInt(array.length - i - 1) & bigInteger.getInt(array.length - i - 1));
        }
        return valueOf(array);
    }
    
    public BigInteger or(final BigInteger bigInteger) {
        final int[] array = new int[Math.max(this.intLength(), bigInteger.intLength())];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (this.getInt(array.length - i - 1) | bigInteger.getInt(array.length - i - 1));
        }
        return valueOf(array);
    }
    
    public BigInteger xor(final BigInteger bigInteger) {
        final int[] array = new int[Math.max(this.intLength(), bigInteger.intLength())];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (this.getInt(array.length - i - 1) ^ bigInteger.getInt(array.length - i - 1));
        }
        return valueOf(array);
    }
    
    public BigInteger not() {
        final int[] array = new int[this.intLength()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = ~this.getInt(array.length - i - 1);
        }
        return valueOf(array);
    }
    
    public BigInteger andNot(final BigInteger bigInteger) {
        final int[] array = new int[Math.max(this.intLength(), bigInteger.intLength())];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (this.getInt(array.length - i - 1) & ~bigInteger.getInt(array.length - i - 1));
        }
        return valueOf(array);
    }
    
    public boolean testBit(final int n) {
        if (n < 0) {
            throw new ArithmeticException("Negative bit address");
        }
        return (this.getInt(n >>> 5) & 1 << (n & 0x1F)) != 0x0;
    }
    
    public BigInteger setBit(final int n) {
        if (n < 0) {
            throw new ArithmeticException("Negative bit address");
        }
        final int n2 = n >>> 5;
        final int[] array = new int[Math.max(this.intLength(), n2 + 2)];
        for (int i = 0; i < array.length; ++i) {
            array[array.length - i - 1] = this.getInt(i);
        }
        final int[] array2 = array;
        final int n3 = array.length - n2 - 1;
        array2[n3] |= 1 << (n & 0x1F);
        return valueOf(array);
    }
    
    public BigInteger clearBit(final int n) {
        if (n < 0) {
            throw new ArithmeticException("Negative bit address");
        }
        final int n2 = n >>> 5;
        final int[] array = new int[Math.max(this.intLength(), (n + 1 >>> 5) + 1)];
        for (int i = 0; i < array.length; ++i) {
            array[array.length - i - 1] = this.getInt(i);
        }
        final int[] array2 = array;
        final int n3 = array.length - n2 - 1;
        array2[n3] &= ~(1 << (n & 0x1F));
        return valueOf(array);
    }
    
    public BigInteger flipBit(final int n) {
        if (n < 0) {
            throw new ArithmeticException("Negative bit address");
        }
        final int n2 = n >>> 5;
        final int[] array = new int[Math.max(this.intLength(), n2 + 2)];
        for (int i = 0; i < array.length; ++i) {
            array[array.length - i - 1] = this.getInt(i);
        }
        final int[] array2 = array;
        final int n3 = array.length - n2 - 1;
        array2[n3] ^= 1 << (n & 0x1F);
        return valueOf(array);
    }
    
    public int getLowestSetBit() {
        int n = this.lowestSetBit - 2;
        if (n == -2) {
            n = 0;
            if (this.signum == 0) {
                --n;
            }
            else {
                int n2;
                int int1;
                for (n2 = 0; (int1 = this.getInt(n2)) == 0; ++n2) {}
                n += (n2 << 5) + Integer.numberOfTrailingZeros(int1);
            }
            this.lowestSetBit = n + 2;
        }
        return n;
    }
    
    public int bitLength() {
        int n = this.bitLength - 1;
        if (n == -1) {
            final int length = this.mag.length;
            if (length == 0) {
                n = 0;
            }
            else {
                final int n2 = (length - 1 << 5) + bitLengthForInt(this.mag[0]);
                if (this.signum < 0) {
                    boolean b = Integer.bitCount(this.mag[0]) == 1;
                    for (int n3 = 1; n3 < length && b; b = (this.mag[n3] == 0), ++n3) {}
                    n = (b ? (n2 - 1) : n2);
                }
                else {
                    n = n2;
                }
            }
            this.bitLength = n + 1;
        }
        return n;
    }
    
    public int bitCount() {
        int n = this.bitCount - 1;
        if (n == -1) {
            n = 0;
            for (int i = 0; i < this.mag.length; ++i) {
                n += Integer.bitCount(this.mag[i]);
            }
            if (this.signum < 0) {
                int n2 = 0;
                int n3;
                for (n3 = this.mag.length - 1; this.mag[n3] == 0; --n3) {
                    n2 += 32;
                }
                n += n2 + Integer.numberOfTrailingZeros(this.mag[n3]) - 1;
            }
            this.bitCount = n + 1;
        }
        return n;
    }
    
    public boolean isProbablePrime(final int n) {
        if (n <= 0) {
            return true;
        }
        final BigInteger abs = this.abs();
        return abs.equals(BigInteger.TWO) || (abs.testBit(0) && !abs.equals(BigInteger.ONE) && abs.primeToCertainty(n, null));
    }
    
    @Override
    public int compareTo(final BigInteger bigInteger) {
        if (this.signum != bigInteger.signum) {
            return (this.signum > bigInteger.signum) ? 1 : -1;
        }
        switch (this.signum) {
            case 1: {
                return this.compareMagnitude(bigInteger);
            }
            case -1: {
                return bigInteger.compareMagnitude(this);
            }
            default: {
                return 0;
            }
        }
    }
    
    final int compareMagnitude(final BigInteger bigInteger) {
        final int[] mag = this.mag;
        final int length = mag.length;
        final int[] mag2 = bigInteger.mag;
        final int length2 = mag2.length;
        if (length < length2) {
            return -1;
        }
        if (length > length2) {
            return 1;
        }
        for (int i = 0; i < length; ++i) {
            final int n = mag[i];
            final int n2 = mag2[i];
            if (n != n2) {
                return ((n & 0xFFFFFFFFL) < (n2 & 0xFFFFFFFFL)) ? -1 : 1;
            }
        }
        return 0;
    }
    
    final int compareMagnitude(long n) {
        assert n != Long.MIN_VALUE;
        final int[] mag = this.mag;
        final int length = mag.length;
        if (length > 2) {
            return 1;
        }
        if (n < 0L) {
            n = -n;
        }
        final int n2 = (int)(n >>> 32);
        if (n2 == 0) {
            if (length < 1) {
                return -1;
            }
            if (length > 1) {
                return 1;
            }
            final int n3 = mag[0];
            final int n4 = (int)n;
            if (n3 != n4) {
                return ((n3 & 0xFFFFFFFFL) < (n4 & 0xFFFFFFFFL)) ? -1 : 1;
            }
            return 0;
        }
        else {
            if (length < 2) {
                return -1;
            }
            final int n5 = mag[0];
            final int n6 = n2;
            if (n5 != n6) {
                return ((n5 & 0xFFFFFFFFL) < (n6 & 0xFFFFFFFFL)) ? -1 : 1;
            }
            final int n7 = mag[1];
            final int n8 = (int)n;
            if (n7 != n8) {
                return ((n7 & 0xFFFFFFFFL) < (n8 & 0xFFFFFFFFL)) ? -1 : 1;
            }
            return 0;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BigInteger)) {
            return false;
        }
        final BigInteger bigInteger = (BigInteger)o;
        if (bigInteger.signum != this.signum) {
            return false;
        }
        final int[] mag = this.mag;
        final int length = mag.length;
        final int[] mag2 = bigInteger.mag;
        if (length != mag2.length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (mag2[i] != mag[i]) {
                return false;
            }
        }
        return true;
    }
    
    public BigInteger min(final BigInteger bigInteger) {
        return (this.compareTo(bigInteger) < 0) ? this : bigInteger;
    }
    
    public BigInteger max(final BigInteger bigInteger) {
        return (this.compareTo(bigInteger) > 0) ? this : bigInteger;
    }
    
    @Override
    public int hashCode() {
        int n = 0;
        for (int i = 0; i < this.mag.length; ++i) {
            n = (int)(31 * n + (this.mag[i] & 0xFFFFFFFFL));
        }
        return n * this.signum;
    }
    
    public String toString(int n) {
        if (this.signum == 0) {
            return "0";
        }
        if (n < 2 || n > 36) {
            n = 10;
        }
        if (this.mag.length <= 20) {
            return this.smallToString(n);
        }
        final StringBuilder sb = new StringBuilder();
        if (this.signum < 0) {
            toString(this.negate(), sb, n, 0);
            sb.insert(0, '-');
        }
        else {
            toString(this, sb, n, 0);
        }
        return sb.toString();
    }
    
    private String smallToString(final int n) {
        if (this.signum == 0) {
            return "0";
        }
        final String[] array = new String[(4 * this.mag.length + 6) / 7];
        BigInteger abs = this.abs();
        int n2 = 0;
        while (abs.signum != 0) {
            final BigInteger bigInteger = BigInteger.longRadix[n];
            final MutableBigInteger mutableBigInteger = new MutableBigInteger();
            final MutableBigInteger divide = new MutableBigInteger(abs.mag).divide(new MutableBigInteger(bigInteger.mag), mutableBigInteger);
            final BigInteger bigInteger2 = mutableBigInteger.toBigInteger(abs.signum * bigInteger.signum);
            array[n2++] = Long.toString(divide.toBigInteger(abs.signum * bigInteger.signum).longValue(), n);
            abs = bigInteger2;
        }
        final StringBuilder sb = new StringBuilder(n2 * BigInteger.digitsPerLong[n] + 1);
        if (this.signum < 0) {
            sb.append('-');
        }
        sb.append(array[n2 - 1]);
        for (int i = n2 - 2; i >= 0; --i) {
            final int n3 = BigInteger.digitsPerLong[n] - array[i].length();
            if (n3 != 0) {
                sb.append(BigInteger.zeros[n3]);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    private static void toString(final BigInteger bigInteger, final StringBuilder sb, final int n, final int n2) {
        if (bigInteger.mag.length <= 20) {
            final String smallToString = bigInteger.smallToString(n);
            if (smallToString.length() < n2 && sb.length() > 0) {
                for (int i = smallToString.length(); i < n2; ++i) {
                    sb.append('0');
                }
            }
            sb.append(smallToString);
            return;
        }
        final int n3 = (int)Math.round(Math.log(bigInteger.bitLength() * BigInteger.LOG_TWO / BigInteger.logCache[n]) / BigInteger.LOG_TWO - 1.0);
        final BigInteger[] divideAndRemainder = bigInteger.divideAndRemainder(getRadixConversionCache(n, n3));
        final int n4 = 1 << n3;
        toString(divideAndRemainder[0], sb, n, n2 - n4);
        toString(divideAndRemainder[1], sb, n, n4);
    }
    
    private static BigInteger getRadixConversionCache(final int n, final int n2) {
        final BigInteger[] array = BigInteger.powerCache[n];
        if (n2 < array.length) {
            return array[n2];
        }
        final int length = array.length;
        final BigInteger[] array2 = Arrays.copyOf(array, n2 + 1);
        for (int i = length; i <= n2; ++i) {
            array2[i] = array2[i - 1].pow(2);
        }
        final BigInteger[][] powerCache = BigInteger.powerCache;
        if (n2 >= powerCache[n].length) {
            final BigInteger[][] powerCache2 = powerCache.clone();
            powerCache2[n] = array2;
            BigInteger.powerCache = powerCache2;
        }
        return array2[n2];
    }
    
    @Override
    public String toString() {
        return this.toString(10);
    }
    
    public byte[] toByteArray() {
        final int n = this.bitLength() / 8 + 1;
        final byte[] array = new byte[n];
        int i = n - 1;
        int n2 = 4;
        int int1 = 0;
        int n3 = 0;
        while (i >= 0) {
            if (n2 == 4) {
                int1 = this.getInt(n3++);
                n2 = 1;
            }
            else {
                int1 >>>= 8;
                ++n2;
            }
            array[i] = (byte)int1;
            --i;
        }
        return array;
    }
    
    @Override
    public int intValue() {
        return this.getInt(0);
    }
    
    @Override
    public long longValue() {
        long n = 0L;
        for (int i = 1; i >= 0; --i) {
            n = (n << 32) + (this.getInt(i) & 0xFFFFFFFFL);
        }
        return n;
    }
    
    @Override
    public float floatValue() {
        if (this.signum == 0) {
            return 0.0f;
        }
        final int n = (this.mag.length - 1 << 5) + bitLengthForInt(this.mag[0]) - 1;
        if (n < 63) {
            return this.longValue();
        }
        if (n > 127) {
            return (this.signum > 0) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
        }
        final int n2 = n - 24;
        final int n3 = n2 & 0x1F;
        final int n4 = 32 - n3;
        int n5;
        if (n3 == 0) {
            n5 = this.mag[0];
        }
        else {
            n5 = this.mag[0] >>> n3;
            if (n5 == 0) {
                n5 = (this.mag[0] << n4 | this.mag[1] >>> n3);
            }
        }
        final int n6 = n5 >> 1 & 0x7FFFFF;
        return Float.intBitsToFloat((n + 127 << 23) + (((n5 & 0x1) != 0x0 && ((n6 & 0x1) != 0x0 || this.abs().getLowestSetBit() < n2)) ? (n6 + 1) : n6) | (this.signum & Integer.MIN_VALUE));
    }
    
    @Override
    public double doubleValue() {
        if (this.signum == 0) {
            return 0.0;
        }
        final int n = (this.mag.length - 1 << 5) + bitLengthForInt(this.mag[0]) - 1;
        if (n < 63) {
            return this.longValue();
        }
        if (n > 1023) {
            return (this.signum > 0) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        }
        final int n2 = n - 53;
        final int n3 = n2 & 0x1F;
        final int n4 = 32 - n3;
        int n5;
        int n6;
        if (n3 == 0) {
            n5 = this.mag[0];
            n6 = this.mag[1];
        }
        else {
            n5 = this.mag[0] >>> n3;
            n6 = (this.mag[0] << n4 | this.mag[1] >>> n3);
            if (n5 == 0) {
                n5 = n6;
                n6 = (this.mag[1] << n4 | this.mag[2] >>> n3);
            }
        }
        final long n7 = (n5 & 0xFFFFFFFFL) << 32 | (n6 & 0xFFFFFFFFL);
        final long n8 = n7 >> 1 & 0xFFFFFFFFFFFFFL;
        return Double.longBitsToDouble((n + 1023 << 52) + (((n7 & 0x1L) != 0x0L && ((n8 & 0x1L) != 0x0L || this.abs().getLowestSetBit() < n2)) ? (n8 + 1L) : n8) | (this.signum & Long.MIN_VALUE));
    }
    
    private static int[] stripLeadingZeroInts(final int[] array) {
        int length;
        int n;
        for (length = array.length, n = 0; n < length && array[n] == 0; ++n) {}
        return Arrays.copyOfRange(array, n, length);
    }
    
    private static int[] trustedStripLeadingZeroInts(final int[] array) {
        int length;
        int n;
        for (length = array.length, n = 0; n < length && array[n] == 0; ++n) {}
        return (n == 0) ? array : Arrays.copyOfRange(array, n, length);
    }
    
    private static int[] stripLeadingZeroBytes(final byte[] array) {
        int length;
        int n;
        for (length = array.length, n = 0; n < length && array[n] == 0; ++n) {}
        final int n2 = length - n + 3 >>> 2;
        final int[] array2 = new int[n2];
        int n3 = length - 1;
        for (int i = n2 - 1; i >= 0; --i) {
            array2[i] = (array[n3--] & 0xFF);
            for (int min = Math.min(3, n3 - n + 1), j = 8; j <= min << 3; j += 8) {
                final int[] array3 = array2;
                final int n4 = i;
                array3[n4] |= (array[n3--] & 0xFF) << j;
            }
        }
        return array2;
    }
    
    private static int[] makePositive(final byte[] array) {
        int length;
        int n;
        for (length = array.length, n = 0; n < length && array[n] == -1; ++n) {}
        int n2;
        for (n2 = n; n2 < length && array[n2] == 0; ++n2) {}
        final int n3 = length - n + ((n2 == length) ? 1 : 0) + 3 >>> 2;
        final int[] array2 = new int[n3];
        int n4 = length - 1;
        for (int i = n3 - 1; i >= 0; --i) {
            array2[i] = (array[n4--] & 0xFF);
            int min = Math.min(3, n4 - n + 1);
            if (min < 0) {
                min = 0;
            }
            for (int j = 8; j <= 8 * min; j += 8) {
                final int[] array3 = array2;
                final int n5 = i;
                array3[n5] |= (array[n4--] & 0xFF) << j;
            }
            array2[i] = (~array2[i] & -1 >>> 8 * (3 - min));
        }
        for (int k = array2.length - 1; k >= 0; --k) {
            array2[k] = (int)((array2[k] & 0xFFFFFFFFL) + 1L);
            if (array2[k] != 0) {
                break;
            }
        }
        return array2;
    }
    
    private static int[] makePositive(final int[] array) {
        int n;
        for (n = 0; n < array.length && array[n] == -1; ++n) {}
        int n2;
        for (n2 = n; n2 < array.length && array[n2] == 0; ++n2) {}
        final int n3 = (n2 == array.length) ? 1 : 0;
        final int[] array2 = new int[array.length - n + n3];
        for (int i = n; i < array.length; ++i) {
            array2[i - n + n3] = ~array[i];
        }
        for (int n4 = array2.length - 1; ++array2[n4] == 0; --n4) {}
        return array2;
    }
    
    private int intLength() {
        return (this.bitLength() >>> 5) + 1;
    }
    
    private int signBit() {
        return (this.signum < 0) ? 1 : 0;
    }
    
    private int signInt() {
        return (this.signum < 0) ? -1 : 0;
    }
    
    private int getInt(final int n) {
        if (n < 0) {
            return 0;
        }
        if (n >= this.mag.length) {
            return this.signInt();
        }
        final int n2 = this.mag[this.mag.length - n - 1];
        return (this.signum >= 0) ? n2 : ((n <= this.firstNonzeroIntNum()) ? (-n2) : (~n2));
    }
    
    private int firstNonzeroIntNum() {
        int n = this.firstNonzeroIntNum - 2;
        if (n == -2) {
            final int length = this.mag.length;
            int n2;
            for (n2 = length - 1; n2 >= 0 && this.mag[n2] == 0; --n2) {}
            n = length - n2 - 1;
            this.firstNonzeroIntNum = n + 2;
        }
        return n;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final int value = fields.get("signum", -2);
        final byte[] array = (byte[])fields.get("magnitude", null);
        if (value < -1 || value > 1) {
            String s = "BigInteger: Invalid signum value";
            if (fields.defaulted("signum")) {
                s = "BigInteger: Signum not present in stream";
            }
            throw new StreamCorruptedException(s);
        }
        final int[] stripLeadingZeroBytes = stripLeadingZeroBytes(array);
        if (stripLeadingZeroBytes.length == 0 != (value == 0)) {
            String s2 = "BigInteger: signum-magnitude mismatch";
            if (fields.defaulted("magnitude")) {
                s2 = "BigInteger: Magnitude not present in stream";
            }
            throw new StreamCorruptedException(s2);
        }
        UnsafeHolder.putSign(this, value);
        UnsafeHolder.putMag(this, stripLeadingZeroBytes);
        if (stripLeadingZeroBytes.length >= 67108864) {
            try {
                this.checkRange();
            }
            catch (ArithmeticException ex) {
                throw new StreamCorruptedException("BigInteger: Out of the supported range");
            }
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("signum", this.signum);
        putFields.put("magnitude", this.magSerializedForm());
        putFields.put("bitCount", -1);
        putFields.put("bitLength", -1);
        putFields.put("lowestSetBit", -2);
        putFields.put("firstNonzeroByteNum", -2);
        objectOutputStream.writeFields();
    }
    
    private byte[] magSerializedForm() {
        final int length = this.mag.length;
        final int n = ((length == 0) ? 0 : ((length - 1 << 5) + bitLengthForInt(this.mag[0]))) + 7 >>> 3;
        final byte[] array = new byte[n];
        int i = n - 1;
        int n2 = 4;
        int n3 = length - 1;
        int n4 = 0;
        while (i >= 0) {
            if (n2 == 4) {
                n4 = this.mag[n3--];
                n2 = 1;
            }
            else {
                n4 >>>= 8;
                ++n2;
            }
            array[i] = (byte)n4;
            --i;
        }
        return array;
    }
    
    public long longValueExact() {
        if (this.mag.length <= 2 && this.bitLength() <= 63) {
            return this.longValue();
        }
        throw new ArithmeticException("BigInteger out of long range");
    }
    
    public int intValueExact() {
        if (this.mag.length <= 1 && this.bitLength() <= 31) {
            return this.intValue();
        }
        throw new ArithmeticException("BigInteger out of int range");
    }
    
    public short shortValueExact() {
        if (this.mag.length <= 1 && this.bitLength() <= 31) {
            final int intValue = this.intValue();
            if (intValue >= -32768 && intValue <= 32767) {
                return this.shortValue();
            }
        }
        throw new ArithmeticException("BigInteger out of short range");
    }
    
    public byte byteValueExact() {
        if (this.mag.length <= 1 && this.bitLength() <= 31) {
            final int intValue = this.intValue();
            if (intValue >= -128 && intValue <= 127) {
                return this.byteValue();
            }
        }
        throw new ArithmeticException("BigInteger out of byte range");
    }
    
    static {
        BigInteger.bitsPerDigit = new long[] { 0L, 0L, 1024L, 1624L, 2048L, 2378L, 2648L, 2875L, 3072L, 3247L, 3402L, 3543L, 3672L, 3790L, 3899L, 4001L, 4096L, 4186L, 4271L, 4350L, 4426L, 4498L, 4567L, 4633L, 4696L, 4756L, 4814L, 4870L, 4923L, 4975L, 5025L, 5074L, 5120L, 5166L, 5210L, 5253L, 5295L };
        SMALL_PRIME_PRODUCT = valueOf(152125131763605L);
        BigInteger.posConst = new BigInteger[17];
        BigInteger.negConst = new BigInteger[17];
        LOG_TWO = Math.log(2.0);
        for (int i = 1; i <= 16; ++i) {
            final int[] array = { i };
            BigInteger.posConst[i] = new BigInteger(array, 1);
            BigInteger.negConst[i] = new BigInteger(array, -1);
        }
        BigInteger.powerCache = new BigInteger[37][];
        logCache = new double[37];
        for (int j = 2; j <= 36; ++j) {
            BigInteger.powerCache[j] = new BigInteger[] { valueOf(j) };
            BigInteger.logCache[j] = Math.log(j);
        }
        ZERO = new BigInteger(new int[0], 0);
        ONE = valueOf(1L);
        TWO = valueOf(2L);
        NEGATIVE_ONE = valueOf(-1L);
        TEN = valueOf(10L);
        BigInteger.bnExpModThreshTable = new int[] { 7, 25, 81, 241, 673, 1793, Integer.MAX_VALUE };
        (BigInteger.zeros = new String[64])[63] = "000000000000000000000000000000000000000000000000000000000000000";
        for (int k = 0; k < 63; ++k) {
            BigInteger.zeros[k] = BigInteger.zeros[63].substring(0, k);
        }
        BigInteger.digitsPerLong = new int[] { 0, 0, 62, 39, 31, 27, 24, 22, 20, 19, 18, 18, 17, 17, 16, 16, 15, 15, 15, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 12, 12 };
        BigInteger.longRadix = new BigInteger[] { null, null, valueOf(4611686018427387904L), valueOf(4052555153018976267L), valueOf(4611686018427387904L), valueOf(7450580596923828125L), valueOf(4738381338321616896L), valueOf(3909821048582988049L), valueOf(1152921504606846976L), valueOf(1350851717672992089L), valueOf(1000000000000000000L), valueOf(5559917313492231481L), valueOf(2218611106740436992L), valueOf(8650415919381337933L), valueOf(2177953337809371136L), valueOf(6568408355712890625L), valueOf(1152921504606846976L), valueOf(2862423051509815793L), valueOf(6746640616477458432L), valueOf(799006685782884121L), valueOf(1638400000000000000L), valueOf(3243919932521508681L), valueOf(6221821273427820544L), valueOf(504036361936467383L), valueOf(876488338465357824L), valueOf(1490116119384765625L), valueOf(2481152873203736576L), valueOf(4052555153018976267L), valueOf(6502111422497947648L), valueOf(353814783205469041L), valueOf(531441000000000000L), valueOf(787662783788549761L), valueOf(1152921504606846976L), valueOf(1667889514952984961L), valueOf(2386420683693101056L), valueOf(3379220508056640625L), valueOf(4738381338321616896L) };
        BigInteger.digitsPerInt = new int[] { 0, 0, 30, 19, 15, 13, 11, 11, 10, 9, 9, 8, 8, 8, 8, 7, 7, 7, 7, 7, 7, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5 };
        BigInteger.intRadix = new int[] { 0, 0, 1073741824, 1162261467, 1073741824, 1220703125, 362797056, 1977326743, 1073741824, 387420489, 1000000000, 214358881, 429981696, 815730721, 1475789056, 170859375, 268435456, 410338673, 612220032, 893871739, 1280000000, 1801088541, 113379904, 148035889, 191102976, 244140625, 308915776, 387420489, 481890304, 594823321, 729000000, 887503681, 1073741824, 1291467969, 1544804416, 1838265625, 60466176 };
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("signum", Integer.TYPE), new ObjectStreamField("magnitude", byte[].class), new ObjectStreamField("bitCount", Integer.TYPE), new ObjectStreamField("bitLength", Integer.TYPE), new ObjectStreamField("firstNonzeroByteNum", Integer.TYPE), new ObjectStreamField("lowestSetBit", Integer.TYPE) };
    }
    
    private static class UnsafeHolder
    {
        private static final Unsafe unsafe;
        private static final long signumOffset;
        private static final long magOffset;
        
        static void putSign(final BigInteger bigInteger, final int n) {
            UnsafeHolder.unsafe.putIntVolatile(bigInteger, UnsafeHolder.signumOffset, n);
        }
        
        static void putMag(final BigInteger bigInteger, final int[] array) {
            UnsafeHolder.unsafe.putObjectVolatile(bigInteger, UnsafeHolder.magOffset, array);
        }
        
        static {
            try {
                unsafe = Unsafe.getUnsafe();
                signumOffset = UnsafeHolder.unsafe.objectFieldOffset(BigInteger.class.getDeclaredField("signum"));
                magOffset = UnsafeHolder.unsafe.objectFieldOffset(BigInteger.class.getDeclaredField("mag"));
            }
            catch (Exception ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
    }
}
