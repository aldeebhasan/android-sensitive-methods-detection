package java.math;

import java.util.*;
import sun.misc.*;
import java.io.*;

public class BigDecimal extends Number implements Comparable<BigDecimal>
{
    private final BigInteger intVal;
    private final int scale;
    private transient int precision;
    private transient String stringCache;
    static final long INFLATED = Long.MIN_VALUE;
    private static final BigInteger INFLATED_BIGINT;
    private final transient long intCompact;
    private static final int MAX_COMPACT_DIGITS = 18;
    private static final long serialVersionUID = 6108874887143696463L;
    private static final ThreadLocal<StringBuilderHelper> threadLocalStringBuilderHelper;
    private static final BigDecimal[] zeroThroughTen;
    private static final BigDecimal[] ZERO_SCALED_BY;
    private static final long HALF_LONG_MAX_VALUE = 4611686018427387903L;
    private static final long HALF_LONG_MIN_VALUE = -4611686018427387904L;
    public static final BigDecimal ZERO;
    public static final BigDecimal ONE;
    public static final BigDecimal TEN;
    public static final int ROUND_UP = 0;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_UNNECESSARY = 7;
    private static final double[] double10pow;
    private static final float[] float10pow;
    private static final long[] LONG_TEN_POWERS_TABLE;
    private static volatile BigInteger[] BIG_TEN_POWERS_TABLE;
    private static final int BIG_TEN_POWERS_TABLE_INITLEN;
    private static final int BIG_TEN_POWERS_TABLE_MAX;
    private static final long[] THRESHOLDS_TABLE;
    private static final long DIV_NUM_BASE = 4294967296L;
    private static final long[][] LONGLONG_TEN_POWERS_TABLE;
    
    BigDecimal(final BigInteger intVal, final long intCompact, final int scale, final int precision) {
        this.scale = scale;
        this.precision = precision;
        this.intCompact = intCompact;
        this.intVal = intVal;
    }
    
    public BigDecimal(final char[] array, final int n, final int n2) {
        this(array, n, n2, MathContext.UNLIMITED);
    }
    
    public BigDecimal(final char[] array, int n, int i, final MathContext mathContext) {
        if ((array.length | i | n) < 0 || i > array.length - n) {
            throw new NumberFormatException("Bad offset or len arguments for char[] input.");
        }
        int precision = 0;
        int scale = 0;
        long n2 = 0L;
        BigInteger divideAndRoundByTenPow = null;
        long intCompact;
        try {
            boolean b = false;
            if (array[n] == '-') {
                b = true;
                ++n;
                --i;
            }
            else if (array[n] == '+') {
                ++n;
                --i;
            }
            int n3 = 0;
            long n4 = 0L;
            final boolean b2 = i <= 18;
            int n5 = 0;
            if (b2) {
                while (i > 0) {
                    final char c = array[n];
                    if (c == '0') {
                        if (precision == 0) {
                            precision = 1;
                        }
                        else if (n2 != 0L) {
                            n2 *= 10L;
                            ++precision;
                        }
                        if (n3 != 0) {
                            ++scale;
                        }
                    }
                    else if (c >= '1' && c <= '9') {
                        final char c2 = (char)(c - '0');
                        if (precision != 1 || n2 != 0L) {
                            ++precision;
                        }
                        n2 = n2 * 10L + c2;
                        if (n3 != 0) {
                            ++scale;
                        }
                    }
                    else if (c == '.') {
                        if (n3 != 0) {
                            throw new NumberFormatException();
                        }
                        n3 = 1;
                    }
                    else if (Character.isDigit(c)) {
                        final int digit = Character.digit(c, 10);
                        if (digit == 0) {
                            if (precision == 0) {
                                precision = 1;
                            }
                            else if (n2 != 0L) {
                                n2 *= 10L;
                                ++precision;
                            }
                        }
                        else {
                            if (precision != 1 || n2 != 0L) {
                                ++precision;
                            }
                            n2 = n2 * 10L + digit;
                        }
                        if (n3 != 0) {
                            ++scale;
                        }
                    }
                    else {
                        if (c != 'e' && c != 'E') {
                            throw new NumberFormatException();
                        }
                        n4 = parseExp(array, n, i);
                        if ((int)n4 != n4) {
                            throw new NumberFormatException();
                        }
                        break;
                    }
                    ++n;
                    --i;
                }
                if (precision == 0) {
                    throw new NumberFormatException();
                }
                if (n4 != 0L) {
                    scale = this.adjustScale(scale, n4);
                }
                intCompact = (b ? (-n2) : n2);
                final int precision2 = mathContext.precision;
                int j = precision - precision2;
                if (precision2 > 0 && j > 0) {
                    while (j > 0) {
                        scale = checkScaleNonZero(scale - j);
                        intCompact = divideAndRound(intCompact, BigDecimal.LONG_TEN_POWERS_TABLE[j], mathContext.roundingMode.oldMode);
                        precision = longDigitLength(intCompact);
                        j = precision - precision2;
                    }
                }
            }
            else {
                final char[] array2 = new char[i];
                while (i > 0) {
                    final char c3 = array[n];
                    if ((c3 >= '0' && c3 <= '9') || Character.isDigit(c3)) {
                        if (c3 == '0' || Character.digit(c3, 10) == 0) {
                            if (precision == 0) {
                                array2[n5] = c3;
                                precision = 1;
                            }
                            else if (n5 != 0) {
                                array2[n5++] = c3;
                                ++precision;
                            }
                        }
                        else {
                            if (precision != 1 || n5 != 0) {
                                ++precision;
                            }
                            array2[n5++] = c3;
                        }
                        if (n3 != 0) {
                            ++scale;
                        }
                    }
                    else if (c3 == '.') {
                        if (n3 != 0) {
                            throw new NumberFormatException();
                        }
                        n3 = 1;
                    }
                    else {
                        if (c3 != 'e' && c3 != 'E') {
                            throw new NumberFormatException();
                        }
                        n4 = parseExp(array, n, i);
                        if ((int)n4 != n4) {
                            throw new NumberFormatException();
                        }
                        break;
                    }
                    ++n;
                    --i;
                }
                if (precision == 0) {
                    throw new NumberFormatException();
                }
                if (n4 != 0L) {
                    scale = this.adjustScale(scale, n4);
                }
                divideAndRoundByTenPow = new BigInteger(array2, b ? -1 : 1, precision);
                intCompact = compactValFor(divideAndRoundByTenPow);
                final int precision3 = mathContext.precision;
                if (precision3 > 0 && precision > precision3) {
                    if (intCompact == Long.MIN_VALUE) {
                        for (int k = precision - precision3; k > 0; k = precision - precision3) {
                            scale = checkScaleNonZero(scale - k);
                            divideAndRoundByTenPow = divideAndRoundByTenPow(divideAndRoundByTenPow, k, mathContext.roundingMode.oldMode);
                            intCompact = compactValFor(divideAndRoundByTenPow);
                            if (intCompact != Long.MIN_VALUE) {
                                precision = longDigitLength(intCompact);
                                break;
                            }
                            precision = bigDigitLength(divideAndRoundByTenPow);
                        }
                    }
                    if (intCompact != Long.MIN_VALUE) {
                        for (int l = precision - precision3; l > 0; l = precision - precision3) {
                            scale = checkScaleNonZero(scale - l);
                            intCompact = divideAndRound(intCompact, BigDecimal.LONG_TEN_POWERS_TABLE[l], mathContext.roundingMode.oldMode);
                            precision = longDigitLength(intCompact);
                        }
                        divideAndRoundByTenPow = null;
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new NumberFormatException();
        }
        catch (NegativeArraySizeException ex2) {
            throw new NumberFormatException();
        }
        this.scale = scale;
        this.precision = precision;
        this.intCompact = intCompact;
        this.intVal = divideAndRoundByTenPow;
    }
    
    private int adjustScale(int n, final long n2) {
        final long n3 = n - n2;
        if (n3 > 2147483647L || n3 < -2147483648L) {
            throw new NumberFormatException("Scale out of range.");
        }
        n = (int)n3;
        return n;
    }
    
    private static long parseExp(final char[] array, int n, int n2) {
        long n3 = 0L;
        ++n;
        char c = array[n];
        --n2;
        final boolean b = c == '-';
        if (b || c == '+') {
            ++n;
            c = array[n];
            --n2;
        }
        if (n2 <= 0) {
            throw new NumberFormatException();
        }
        while (n2 > 10 && (c == '0' || Character.digit(c, 10) == 0)) {
            ++n;
            c = array[n];
            --n2;
        }
        if (n2 > 10) {
            throw new NumberFormatException();
        }
        while (true) {
            int digit;
            if (c >= '0' && c <= '9') {
                digit = c - '0';
            }
            else {
                digit = Character.digit(c, 10);
                if (digit < 0) {
                    throw new NumberFormatException();
                }
            }
            n3 = n3 * 10L + digit;
            if (n2 == 1) {
                if (b) {
                    n3 = -n3;
                }
                return n3;
            }
            ++n;
            c = array[n];
            --n2;
        }
    }
    
    public BigDecimal(final char[] array) {
        this(array, 0, array.length);
    }
    
    public BigDecimal(final char[] array, final MathContext mathContext) {
        this(array, 0, array.length, mathContext);
    }
    
    public BigDecimal(final String s) {
        this(s.toCharArray(), 0, s.length());
    }
    
    public BigDecimal(final String s, final MathContext mathContext) {
        this(s.toCharArray(), 0, s.length(), mathContext);
    }
    
    public BigDecimal(final double n) {
        this(n, MathContext.UNLIMITED);
    }
    
    public BigDecimal(final double n, final MathContext mathContext) {
        if (Double.isInfinite(n) || Double.isNaN(n)) {
            throw new NumberFormatException("Infinite or NaN");
        }
        final long doubleToLongBits = Double.doubleToLongBits(n);
        final int n2 = (doubleToLongBits >> 63 == 0L) ? 1 : -1;
        int n3 = (int)(doubleToLongBits >> 52 & 0x7FFL);
        long n4 = (n3 == 0) ? ((doubleToLongBits & 0xFFFFFFFFFFFFFL) << 1) : ((doubleToLongBits & 0xFFFFFFFFFFFFFL) | 0x10000000000000L);
        n3 -= 1075;
        if (n4 == 0L) {
            this.intVal = BigInteger.ZERO;
            this.scale = 0;
            this.intCompact = 0L;
            this.precision = 1;
            return;
        }
        while ((n4 & 0x1L) == 0x0L) {
            n4 >>= 1;
            ++n3;
        }
        int scale = 0;
        long intCompact = n2 * n4;
        BigInteger intVal;
        if (n3 == 0) {
            intVal = ((intCompact == Long.MIN_VALUE) ? BigDecimal.INFLATED_BIGINT : null);
        }
        else {
            if (n3 < 0) {
                intVal = BigInteger.valueOf(5L).pow(-n3).multiply(intCompact);
                scale = -n3;
            }
            else {
                intVal = BigInteger.valueOf(2L).pow(n3).multiply(intCompact);
            }
            intCompact = compactValFor(intVal);
        }
        int precision = 0;
        final int precision2 = mathContext.precision;
        if (precision2 > 0) {
            final int oldMode = mathContext.roundingMode.oldMode;
            if (intCompact == Long.MIN_VALUE) {
                precision = bigDigitLength(intVal);
                for (int i = precision - precision2; i > 0; i = precision - precision2) {
                    scale = checkScaleNonZero(scale - i);
                    intVal = divideAndRoundByTenPow(intVal, i, oldMode);
                    intCompact = compactValFor(intVal);
                    if (intCompact != Long.MIN_VALUE) {
                        break;
                    }
                    precision = bigDigitLength(intVal);
                }
            }
            if (intCompact != Long.MIN_VALUE) {
                precision = longDigitLength(intCompact);
                for (int j = precision - precision2; j > 0; j = precision - precision2) {
                    scale = checkScaleNonZero(scale - j);
                    intCompact = divideAndRound(intCompact, BigDecimal.LONG_TEN_POWERS_TABLE[j], mathContext.roundingMode.oldMode);
                    precision = longDigitLength(intCompact);
                }
                intVal = null;
            }
        }
        this.intVal = intVal;
        this.intCompact = intCompact;
        this.scale = scale;
        this.precision = precision;
    }
    
    public BigDecimal(final BigInteger intVal) {
        this.scale = 0;
        this.intVal = intVal;
        this.intCompact = compactValFor(intVal);
    }
    
    public BigDecimal(final BigInteger bigInteger, final MathContext mathContext) {
        this(bigInteger, 0, mathContext);
    }
    
    public BigDecimal(final BigInteger intVal, final int scale) {
        this.intVal = intVal;
        this.intCompact = compactValFor(intVal);
        this.scale = scale;
    }
    
    public BigDecimal(BigInteger divideAndRoundByTenPow, int scale, final MathContext mathContext) {
        long intCompact = compactValFor(divideAndRoundByTenPow);
        final int precision = mathContext.precision;
        int precision2 = 0;
        if (precision > 0) {
            final int oldMode = mathContext.roundingMode.oldMode;
            if (intCompact == Long.MIN_VALUE) {
                precision2 = bigDigitLength(divideAndRoundByTenPow);
                for (int i = precision2 - precision; i > 0; i = precision2 - precision) {
                    scale = checkScaleNonZero(scale - i);
                    divideAndRoundByTenPow = divideAndRoundByTenPow(divideAndRoundByTenPow, i, oldMode);
                    intCompact = compactValFor(divideAndRoundByTenPow);
                    if (intCompact != Long.MIN_VALUE) {
                        break;
                    }
                    precision2 = bigDigitLength(divideAndRoundByTenPow);
                }
            }
            if (intCompact != Long.MIN_VALUE) {
                precision2 = longDigitLength(intCompact);
                for (int j = precision2 - precision; j > 0; j = precision2 - precision) {
                    scale = checkScaleNonZero(scale - j);
                    intCompact = divideAndRound(intCompact, BigDecimal.LONG_TEN_POWERS_TABLE[j], oldMode);
                    precision2 = longDigitLength(intCompact);
                }
                divideAndRoundByTenPow = null;
            }
        }
        this.intVal = divideAndRoundByTenPow;
        this.intCompact = intCompact;
        this.scale = scale;
        this.precision = precision2;
    }
    
    public BigDecimal(final int n) {
        this.intCompact = n;
        this.scale = 0;
        this.intVal = null;
    }
    
    public BigDecimal(final int n, final MathContext mathContext) {
        final int precision = mathContext.precision;
        long divideAndRound = n;
        int checkScaleNonZero = 0;
        int precision2 = 0;
        if (precision > 0) {
            precision2 = longDigitLength(divideAndRound);
            for (int i = precision2 - precision; i > 0; i = precision2 - precision) {
                checkScaleNonZero = checkScaleNonZero(checkScaleNonZero - i);
                divideAndRound = divideAndRound(divideAndRound, BigDecimal.LONG_TEN_POWERS_TABLE[i], mathContext.roundingMode.oldMode);
                precision2 = longDigitLength(divideAndRound);
            }
        }
        this.intVal = null;
        this.intCompact = divideAndRound;
        this.scale = checkScaleNonZero;
        this.precision = precision2;
    }
    
    public BigDecimal(final long intCompact) {
        this.intCompact = intCompact;
        this.intVal = ((intCompact == Long.MIN_VALUE) ? BigDecimal.INFLATED_BIGINT : null);
        this.scale = 0;
    }
    
    public BigDecimal(long intCompact, final MathContext mathContext) {
        final int precision = mathContext.precision;
        final int oldMode = mathContext.roundingMode.oldMode;
        int precision2 = 0;
        int scale = 0;
        BigInteger divideAndRoundByTenPow = (intCompact == Long.MIN_VALUE) ? BigDecimal.INFLATED_BIGINT : null;
        if (precision > 0) {
            if (intCompact == Long.MIN_VALUE) {
                precision2 = 19;
                for (int i = precision2 - precision; i > 0; i = precision2 - precision) {
                    scale = checkScaleNonZero(scale - i);
                    divideAndRoundByTenPow = divideAndRoundByTenPow(divideAndRoundByTenPow, i, oldMode);
                    intCompact = compactValFor(divideAndRoundByTenPow);
                    if (intCompact != Long.MIN_VALUE) {
                        break;
                    }
                    precision2 = bigDigitLength(divideAndRoundByTenPow);
                }
            }
            if (intCompact != Long.MIN_VALUE) {
                precision2 = longDigitLength(intCompact);
                for (int j = precision2 - precision; j > 0; j = precision2 - precision) {
                    scale = checkScaleNonZero(scale - j);
                    intCompact = divideAndRound(intCompact, BigDecimal.LONG_TEN_POWERS_TABLE[j], mathContext.roundingMode.oldMode);
                    precision2 = longDigitLength(intCompact);
                }
                divideAndRoundByTenPow = null;
            }
        }
        this.intVal = divideAndRoundByTenPow;
        this.intCompact = intCompact;
        this.scale = scale;
        this.precision = precision2;
    }
    
    public static BigDecimal valueOf(final long n, final int n2) {
        if (n2 == 0) {
            return valueOf(n);
        }
        if (n == 0L) {
            return zeroValueOf(n2);
        }
        return new BigDecimal((n == Long.MIN_VALUE) ? BigDecimal.INFLATED_BIGINT : null, n, n2, 0);
    }
    
    public static BigDecimal valueOf(final long n) {
        if (n >= 0L && n < BigDecimal.zeroThroughTen.length) {
            return BigDecimal.zeroThroughTen[(int)n];
        }
        if (n != Long.MIN_VALUE) {
            return new BigDecimal(null, n, 0, 0);
        }
        return new BigDecimal(BigDecimal.INFLATED_BIGINT, n, 0, 0);
    }
    
    static BigDecimal valueOf(final long n, final int n2, final int n3) {
        if (n2 == 0 && n >= 0L && n < BigDecimal.zeroThroughTen.length) {
            return BigDecimal.zeroThroughTen[(int)n];
        }
        if (n == 0L) {
            return zeroValueOf(n2);
        }
        return new BigDecimal((n == Long.MIN_VALUE) ? BigDecimal.INFLATED_BIGINT : null, n, n2, n3);
    }
    
    static BigDecimal valueOf(final BigInteger bigInteger, final int n, final int n2) {
        final long compactVal = compactValFor(bigInteger);
        if (compactVal == 0L) {
            return zeroValueOf(n);
        }
        if (n == 0 && compactVal >= 0L && compactVal < BigDecimal.zeroThroughTen.length) {
            return BigDecimal.zeroThroughTen[(int)compactVal];
        }
        return new BigDecimal(bigInteger, compactVal, n, n2);
    }
    
    static BigDecimal zeroValueOf(final int n) {
        if (n >= 0 && n < BigDecimal.ZERO_SCALED_BY.length) {
            return BigDecimal.ZERO_SCALED_BY[n];
        }
        return new BigDecimal(BigInteger.ZERO, 0L, n, 1);
    }
    
    public static BigDecimal valueOf(final double n) {
        return new BigDecimal(Double.toString(n));
    }
    
    public BigDecimal add(final BigDecimal bigDecimal) {
        if (this.intCompact != Long.MIN_VALUE) {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return add(this.intCompact, this.scale, bigDecimal.intCompact, bigDecimal.scale);
            }
            return add(this.intCompact, this.scale, bigDecimal.intVal, bigDecimal.scale);
        }
        else {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return add(bigDecimal.intCompact, bigDecimal.scale, this.intVal, this.scale);
            }
            return add(this.intVal, this.scale, bigDecimal.intVal, bigDecimal.scale);
        }
    }
    
    public BigDecimal add(BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.precision == 0) {
            return this.add(bigDecimal);
        }
        BigDecimal bigDecimal2 = this;
        final boolean b = bigDecimal2.signum() == 0;
        final boolean b2 = bigDecimal.signum() == 0;
        if (!b && !b2) {
            final long n = bigDecimal2.scale - bigDecimal.scale;
            if (n != 0L) {
                final BigDecimal[] preAlign = this.preAlign(bigDecimal2, bigDecimal, n, mathContext);
                matchScale(preAlign);
                bigDecimal2 = preAlign[0];
                bigDecimal = preAlign[1];
            }
            return doRound(bigDecimal2.inflated().add(bigDecimal.inflated()), bigDecimal2.scale, mathContext);
        }
        final int max = Math.max(bigDecimal2.scale(), bigDecimal.scale());
        if (b && b2) {
            return zeroValueOf(max);
        }
        final BigDecimal bigDecimal3 = b ? doRound(bigDecimal, mathContext) : doRound(bigDecimal2, mathContext);
        if (bigDecimal3.scale() == max) {
            return bigDecimal3;
        }
        if (bigDecimal3.scale() > max) {
            return stripZerosToMatchScale(bigDecimal3.intVal, bigDecimal3.intCompact, bigDecimal3.scale, max);
        }
        final int n2 = mathContext.precision - bigDecimal3.precision();
        if (n2 >= max - bigDecimal3.scale()) {
            return bigDecimal3.setScale(max);
        }
        return bigDecimal3.setScale(bigDecimal3.scale() + n2);
    }
    
    private BigDecimal[] preAlign(final BigDecimal bigDecimal, final BigDecimal bigDecimal2, final long n, final MathContext mathContext) {
        assert n != 0L;
        BigDecimal bigDecimal3;
        BigDecimal value;
        if (n < 0L) {
            bigDecimal3 = bigDecimal;
            value = bigDecimal2;
        }
        else {
            bigDecimal3 = bigDecimal2;
            value = bigDecimal;
        }
        final long n2 = bigDecimal3.scale - bigDecimal3.precision() + mathContext.precision;
        final long n3 = value.scale - value.precision() + 1L;
        if (n3 > bigDecimal3.scale + 2 && n3 > n2 + 2L) {
            value = valueOf(value.signum(), this.checkScale(Math.max(bigDecimal3.scale, n2) + 3L));
        }
        return new BigDecimal[] { bigDecimal3, value };
    }
    
    public BigDecimal subtract(final BigDecimal bigDecimal) {
        if (this.intCompact != Long.MIN_VALUE) {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return add(this.intCompact, this.scale, -bigDecimal.intCompact, bigDecimal.scale);
            }
            return add(this.intCompact, this.scale, bigDecimal.intVal.negate(), bigDecimal.scale);
        }
        else {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return add(-bigDecimal.intCompact, bigDecimal.scale, this.intVal, this.scale);
            }
            return add(this.intVal, this.scale, bigDecimal.intVal.negate(), bigDecimal.scale);
        }
    }
    
    public BigDecimal subtract(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.precision == 0) {
            return this.subtract(bigDecimal);
        }
        return this.add(bigDecimal.negate(), mathContext);
    }
    
    public BigDecimal multiply(final BigDecimal bigDecimal) {
        final int checkScale = this.checkScale(this.scale + bigDecimal.scale);
        if (this.intCompact != Long.MIN_VALUE) {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return multiply(this.intCompact, bigDecimal.intCompact, checkScale);
            }
            return multiply(this.intCompact, bigDecimal.intVal, checkScale);
        }
        else {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return multiply(bigDecimal.intCompact, this.intVal, checkScale);
            }
            return multiply(this.intVal, bigDecimal.intVal, checkScale);
        }
    }
    
    public BigDecimal multiply(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.precision == 0) {
            return this.multiply(bigDecimal);
        }
        final int checkScale = this.checkScale(this.scale + bigDecimal.scale);
        if (this.intCompact != Long.MIN_VALUE) {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return multiplyAndRound(this.intCompact, bigDecimal.intCompact, checkScale, mathContext);
            }
            return multiplyAndRound(this.intCompact, bigDecimal.intVal, checkScale, mathContext);
        }
        else {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return multiplyAndRound(bigDecimal.intCompact, this.intVal, checkScale, mathContext);
            }
            return multiplyAndRound(this.intVal, bigDecimal.intVal, checkScale, mathContext);
        }
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal, final int n, final int n2) {
        if (n2 < 0 || n2 > 7) {
            throw new IllegalArgumentException("Invalid rounding mode");
        }
        if (this.intCompact != Long.MIN_VALUE) {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return divide(this.intCompact, this.scale, bigDecimal.intCompact, bigDecimal.scale, n, n2);
            }
            return divide(this.intCompact, this.scale, bigDecimal.intVal, bigDecimal.scale, n, n2);
        }
        else {
            if (bigDecimal.intCompact != Long.MIN_VALUE) {
                return divide(this.intVal, this.scale, bigDecimal.intCompact, bigDecimal.scale, n, n2);
            }
            return divide(this.intVal, this.scale, bigDecimal.intVal, bigDecimal.scale, n, n2);
        }
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal, final int n, final RoundingMode roundingMode) {
        return this.divide(bigDecimal, n, roundingMode.oldMode);
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal, final int n) {
        return this.divide(bigDecimal, this.scale, n);
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal, final RoundingMode roundingMode) {
        return this.divide(bigDecimal, this.scale, roundingMode.oldMode);
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal) {
        if (bigDecimal.signum() == 0) {
            if (this.signum() == 0) {
                throw new ArithmeticException("Division undefined");
            }
            throw new ArithmeticException("Division by zero");
        }
        else {
            final int saturateLong = saturateLong(this.scale - bigDecimal.scale);
            if (this.signum() == 0) {
                return zeroValueOf(saturateLong);
            }
            final MathContext mathContext = new MathContext((int)Math.min(this.precision() + (long)Math.ceil(10.0 * bigDecimal.precision() / 3.0), 2147483647L), RoundingMode.UNNECESSARY);
            BigDecimal divide;
            try {
                divide = this.divide(bigDecimal, mathContext);
            }
            catch (ArithmeticException ex) {
                throw new ArithmeticException("Non-terminating decimal expansion; no exact representable decimal result.");
            }
            if (saturateLong > divide.scale()) {
                return divide.setScale(saturateLong, 7);
            }
            return divide;
        }
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.precision == 0) {
            return this.divide(bigDecimal);
        }
        final long n = this.scale - bigDecimal.scale;
        if (bigDecimal.signum() == 0) {
            if (this.signum() == 0) {
                throw new ArithmeticException("Division undefined");
            }
            throw new ArithmeticException("Division by zero");
        }
        else {
            if (this.signum() == 0) {
                return zeroValueOf(saturateLong(n));
            }
            final int precision = this.precision();
            final int precision2 = bigDecimal.precision();
            if (this.intCompact != Long.MIN_VALUE) {
                if (bigDecimal.intCompact != Long.MIN_VALUE) {
                    return divide(this.intCompact, precision, bigDecimal.intCompact, precision2, n, mathContext);
                }
                return divide(this.intCompact, precision, bigDecimal.intVal, precision2, n, mathContext);
            }
            else {
                if (bigDecimal.intCompact != Long.MIN_VALUE) {
                    return divide(this.intVal, precision, bigDecimal.intCompact, precision2, n, mathContext);
                }
                return divide(this.intVal, precision, bigDecimal.intVal, precision2, n, mathContext);
            }
        }
    }
    
    public BigDecimal divideToIntegralValue(final BigDecimal bigDecimal) {
        final int saturateLong = saturateLong(this.scale - bigDecimal.scale);
        if (this.compareMagnitude(bigDecimal) < 0) {
            return zeroValueOf(saturateLong);
        }
        if (this.signum() == 0 && bigDecimal.signum() != 0) {
            return this.setScale(saturateLong, 7);
        }
        BigDecimal bigDecimal2 = this.divide(bigDecimal, new MathContext((int)Math.min(this.precision() + (long)Math.ceil(10.0 * bigDecimal.precision() / 3.0) + Math.abs(this.scale() - bigDecimal.scale()) + 2L, 2147483647L), RoundingMode.DOWN));
        if (bigDecimal2.scale > 0) {
            final BigDecimal setScale = bigDecimal2.setScale(0, RoundingMode.DOWN);
            bigDecimal2 = stripZerosToMatchScale(setScale.intVal, setScale.intCompact, setScale.scale, saturateLong);
        }
        if (bigDecimal2.scale < saturateLong) {
            bigDecimal2 = bigDecimal2.setScale(saturateLong, 7);
        }
        return bigDecimal2;
    }
    
    public BigDecimal divideToIntegralValue(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.precision == 0 || this.compareMagnitude(bigDecimal) < 0) {
            return this.divideToIntegralValue(bigDecimal);
        }
        final int saturateLong = saturateLong(this.scale - bigDecimal.scale);
        BigDecimal bigDecimal2 = this.divide(bigDecimal, new MathContext(mathContext.precision, RoundingMode.DOWN));
        if (bigDecimal2.scale() < 0) {
            if (this.subtract(bigDecimal2.multiply(bigDecimal)).compareMagnitude(bigDecimal) >= 0) {
                throw new ArithmeticException("Division impossible");
            }
        }
        else if (bigDecimal2.scale() > 0) {
            bigDecimal2 = bigDecimal2.setScale(0, RoundingMode.DOWN);
        }
        final int n;
        if (saturateLong > bigDecimal2.scale() && (n = mathContext.precision - bigDecimal2.precision()) > 0) {
            return bigDecimal2.setScale(bigDecimal2.scale() + Math.min(n, saturateLong - bigDecimal2.scale));
        }
        return stripZerosToMatchScale(bigDecimal2.intVal, bigDecimal2.intCompact, bigDecimal2.scale, saturateLong);
    }
    
    public BigDecimal remainder(final BigDecimal bigDecimal) {
        return this.divideAndRemainder(bigDecimal)[1];
    }
    
    public BigDecimal remainder(final BigDecimal bigDecimal, final MathContext mathContext) {
        return this.divideAndRemainder(bigDecimal, mathContext)[1];
    }
    
    public BigDecimal[] divideAndRemainder(final BigDecimal bigDecimal) {
        final BigDecimal[] array = { this.divideToIntegralValue(bigDecimal), null };
        array[1] = this.subtract(array[0].multiply(bigDecimal));
        return array;
    }
    
    public BigDecimal[] divideAndRemainder(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.precision == 0) {
            return this.divideAndRemainder(bigDecimal);
        }
        final BigDecimal[] array = { this.divideToIntegralValue(bigDecimal, mathContext), null };
        array[1] = this.subtract(array[0].multiply(bigDecimal));
        return array;
    }
    
    public BigDecimal pow(final int n) {
        if (n < 0 || n > 999999999) {
            throw new ArithmeticException("Invalid operation");
        }
        return new BigDecimal(this.inflated().pow(n), this.checkScale(this.scale * n));
    }
    
    public BigDecimal pow(final int n, final MathContext mathContext) {
        if (mathContext.precision == 0) {
            return this.pow(n);
        }
        if (n < -999999999 || n > 999999999) {
            throw new ArithmeticException("Invalid operation");
        }
        if (n == 0) {
            return BigDecimal.ONE;
        }
        MathContext mathContext2 = mathContext;
        int abs = Math.abs(n);
        if (mathContext.precision > 0) {
            final int longDigitLength = longDigitLength(abs);
            if (longDigitLength > mathContext.precision) {
                throw new ArithmeticException("Invalid operation");
            }
            mathContext2 = new MathContext(mathContext.precision + longDigitLength + 1, mathContext.roundingMode);
        }
        BigDecimal bigDecimal = BigDecimal.ONE;
        boolean b = false;
        int n2 = 1;
        while (true) {
            abs += abs;
            if (abs < 0) {
                b = true;
                bigDecimal = bigDecimal.multiply(this, mathContext2);
            }
            if (n2 == 31) {
                break;
            }
            if (b) {
                bigDecimal = bigDecimal.multiply(bigDecimal, mathContext2);
            }
            ++n2;
        }
        if (n < 0) {
            bigDecimal = BigDecimal.ONE.divide(bigDecimal, mathContext2);
        }
        return doRound(bigDecimal, mathContext);
    }
    
    public BigDecimal abs() {
        return (this.signum() < 0) ? this.negate() : this;
    }
    
    public BigDecimal abs(final MathContext mathContext) {
        return (this.signum() < 0) ? this.negate(mathContext) : this.plus(mathContext);
    }
    
    public BigDecimal negate() {
        if (this.intCompact == Long.MIN_VALUE) {
            return new BigDecimal(this.intVal.negate(), Long.MIN_VALUE, this.scale, this.precision);
        }
        return valueOf(-this.intCompact, this.scale, this.precision);
    }
    
    public BigDecimal negate(final MathContext mathContext) {
        return this.negate().plus(mathContext);
    }
    
    public BigDecimal plus() {
        return this;
    }
    
    public BigDecimal plus(final MathContext mathContext) {
        if (mathContext.precision == 0) {
            return this;
        }
        return doRound(this, mathContext);
    }
    
    public int signum() {
        return (this.intCompact != Long.MIN_VALUE) ? Long.signum(this.intCompact) : this.intVal.signum();
    }
    
    public int scale() {
        return this.scale;
    }
    
    public int precision() {
        int precision = this.precision;
        if (precision == 0) {
            final long intCompact = this.intCompact;
            if (intCompact != Long.MIN_VALUE) {
                precision = longDigitLength(intCompact);
            }
            else {
                precision = bigDigitLength(this.intVal);
            }
            this.precision = precision;
        }
        return precision;
    }
    
    public BigInteger unscaledValue() {
        return this.inflated();
    }
    
    public BigDecimal round(final MathContext mathContext) {
        return this.plus(mathContext);
    }
    
    public BigDecimal setScale(final int n, final RoundingMode roundingMode) {
        return this.setScale(n, roundingMode.oldMode);
    }
    
    public BigDecimal setScale(final int n, final int n2) {
        if (n2 < 0 || n2 > 7) {
            throw new IllegalArgumentException("Invalid rounding mode");
        }
        final int scale = this.scale;
        if (n == scale) {
            return this;
        }
        if (this.signum() == 0) {
            return zeroValueOf(n);
        }
        if (this.intCompact != Long.MIN_VALUE) {
            final long intCompact = this.intCompact;
            if (n > scale) {
                final int checkScale = this.checkScale(n - scale);
                final long longMultiplyPowerTen;
                if ((longMultiplyPowerTen = longMultiplyPowerTen(intCompact, checkScale)) != Long.MIN_VALUE) {
                    return valueOf(longMultiplyPowerTen, n);
                }
                return new BigDecimal(this.bigMultiplyPowerTen(checkScale), Long.MIN_VALUE, n, (this.precision > 0) ? (this.precision + checkScale) : 0);
            }
            else {
                final int checkScale2 = this.checkScale(scale - n);
                if (checkScale2 < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
                    return divideAndRound(intCompact, BigDecimal.LONG_TEN_POWERS_TABLE[checkScale2], n, n2, n);
                }
                return divideAndRound(this.inflated(), bigTenToThe(checkScale2), n, n2, n);
            }
        }
        else {
            if (n > scale) {
                final int checkScale3 = this.checkScale(n - scale);
                return new BigDecimal(bigMultiplyPowerTen(this.intVal, checkScale3), Long.MIN_VALUE, n, (this.precision > 0) ? (this.precision + checkScale3) : 0);
            }
            final int checkScale4 = this.checkScale(scale - n);
            if (checkScale4 < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
                return divideAndRound(this.intVal, BigDecimal.LONG_TEN_POWERS_TABLE[checkScale4], n, n2, n);
            }
            return divideAndRound(this.intVal, bigTenToThe(checkScale4), n, n2, n);
        }
    }
    
    public BigDecimal setScale(final int n) {
        return this.setScale(n, 7);
    }
    
    public BigDecimal movePointLeft(final int n) {
        final BigDecimal bigDecimal = new BigDecimal(this.intVal, this.intCompact, this.checkScale(this.scale + n), 0);
        return (bigDecimal.scale < 0) ? bigDecimal.setScale(0, 7) : bigDecimal;
    }
    
    public BigDecimal movePointRight(final int n) {
        final BigDecimal bigDecimal = new BigDecimal(this.intVal, this.intCompact, this.checkScale(this.scale - n), 0);
        return (bigDecimal.scale < 0) ? bigDecimal.setScale(0, 7) : bigDecimal;
    }
    
    public BigDecimal scaleByPowerOfTen(final int n) {
        return new BigDecimal(this.intVal, this.intCompact, this.checkScale(this.scale - n), this.precision);
    }
    
    public BigDecimal stripTrailingZeros() {
        if (this.intCompact == 0L || (this.intVal != null && this.intVal.signum() == 0)) {
            return BigDecimal.ZERO;
        }
        if (this.intCompact != Long.MIN_VALUE) {
            return createAndStripZerosToMatchScale(this.intCompact, this.scale, Long.MIN_VALUE);
        }
        return createAndStripZerosToMatchScale(this.intVal, this.scale, Long.MIN_VALUE);
    }
    
    @Override
    public int compareTo(final BigDecimal bigDecimal) {
        if (this.scale == bigDecimal.scale) {
            final long intCompact = this.intCompact;
            final long intCompact2 = bigDecimal.intCompact;
            if (intCompact != Long.MIN_VALUE && intCompact2 != Long.MIN_VALUE) {
                return (intCompact != intCompact2) ? ((intCompact > intCompact2) ? 1 : -1) : 0;
            }
        }
        final int signum = this.signum();
        final int signum2 = bigDecimal.signum();
        if (signum != signum2) {
            return (signum > signum2) ? 1 : -1;
        }
        if (signum == 0) {
            return 0;
        }
        final int compareMagnitude = this.compareMagnitude(bigDecimal);
        return (signum > 0) ? compareMagnitude : (-compareMagnitude);
    }
    
    private int compareMagnitude(final BigDecimal bigDecimal) {
        long n = bigDecimal.intCompact;
        long n2 = this.intCompact;
        if (n2 == 0L) {
            return (n == 0L) ? 0 : -1;
        }
        if (n == 0L) {
            return 1;
        }
        final long n3 = this.scale - bigDecimal.scale;
        if (n3 != 0L) {
            final long n4 = this.precision() - this.scale;
            final long n5 = bigDecimal.precision() - bigDecimal.scale;
            if (n4 < n5) {
                return -1;
            }
            if (n4 > n5) {
                return 1;
            }
            if (n3 < 0L) {
                if (n3 > -2147483648L && (n2 == Long.MIN_VALUE || (n2 = longMultiplyPowerTen(n2, (int)(-n3))) == Long.MIN_VALUE) && n == Long.MIN_VALUE) {
                    return this.bigMultiplyPowerTen((int)(-n3)).compareMagnitude(bigDecimal.intVal);
                }
            }
            else if (n3 <= 2147483647L && (n == Long.MIN_VALUE || (n = longMultiplyPowerTen(n, (int)n3)) == Long.MIN_VALUE) && n2 == Long.MIN_VALUE) {
                return this.intVal.compareMagnitude(bigDecimal.bigMultiplyPowerTen((int)n3));
            }
        }
        if (n2 != Long.MIN_VALUE) {
            return (n != Long.MIN_VALUE) ? longCompareMagnitude(n2, n) : -1;
        }
        if (n != Long.MIN_VALUE) {
            return 1;
        }
        return this.intVal.compareMagnitude(bigDecimal.intVal);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BigDecimal)) {
            return false;
        }
        final BigDecimal bigDecimal = (BigDecimal)o;
        if (o == this) {
            return true;
        }
        if (this.scale != bigDecimal.scale) {
            return false;
        }
        final long intCompact = this.intCompact;
        long n = bigDecimal.intCompact;
        if (intCompact != Long.MIN_VALUE) {
            if (n == Long.MIN_VALUE) {
                n = compactValFor(bigDecimal.intVal);
            }
            return n == intCompact;
        }
        if (n != Long.MIN_VALUE) {
            return n == compactValFor(this.intVal);
        }
        return this.inflated().equals(bigDecimal.inflated());
    }
    
    public BigDecimal min(final BigDecimal bigDecimal) {
        return (this.compareTo(bigDecimal) <= 0) ? this : bigDecimal;
    }
    
    public BigDecimal max(final BigDecimal bigDecimal) {
        return (this.compareTo(bigDecimal) >= 0) ? this : bigDecimal;
    }
    
    @Override
    public int hashCode() {
        if (this.intCompact != Long.MIN_VALUE) {
            final long n = (this.intCompact < 0L) ? (-this.intCompact) : this.intCompact;
            final int n2 = (int)((int)(n >>> 32) * 31 + (n & 0xFFFFFFFFL));
            return 31 * ((this.intCompact < 0L) ? (-n2) : n2) + this.scale;
        }
        return 31 * this.intVal.hashCode() + this.scale;
    }
    
    @Override
    public String toString() {
        String stringCache = this.stringCache;
        if (stringCache == null) {
            stringCache = (this.stringCache = this.layoutChars(true));
        }
        return stringCache;
    }
    
    public String toEngineeringString() {
        return this.layoutChars(false);
    }
    
    public String toPlainString() {
        if (this.scale == 0) {
            if (this.intCompact != Long.MIN_VALUE) {
                return Long.toString(this.intCompact);
            }
            return this.intVal.toString();
        }
        else {
            if (this.scale >= 0) {
                String s;
                if (this.intCompact != Long.MIN_VALUE) {
                    s = Long.toString(Math.abs(this.intCompact));
                }
                else {
                    s = this.intVal.abs().toString();
                }
                return this.getValueString(this.signum(), s, this.scale);
            }
            if (this.signum() == 0) {
                return "0";
            }
            final int checkScaleNonZero = checkScaleNonZero(-this.scale);
            StringBuilder sb;
            if (this.intCompact != Long.MIN_VALUE) {
                sb = new StringBuilder(20 + checkScaleNonZero);
                sb.append(this.intCompact);
            }
            else {
                final String string = this.intVal.toString();
                sb = new StringBuilder(string.length() + checkScaleNonZero);
                sb.append(string);
            }
            for (int i = 0; i < checkScaleNonZero; ++i) {
                sb.append('0');
            }
            return sb.toString();
        }
    }
    
    private String getValueString(final int n, final String s, final int n2) {
        final int n3 = s.length() - n2;
        if (n3 == 0) {
            return ((n < 0) ? "-0." : "0.") + s;
        }
        StringBuilder sb;
        if (n3 > 0) {
            sb = new StringBuilder(s);
            sb.insert(n3, '.');
            if (n < 0) {
                sb.insert(0, '-');
            }
        }
        else {
            sb = new StringBuilder(3 - n3 + s.length());
            sb.append((n < 0) ? "-0." : "0.");
            for (int i = 0; i < -n3; ++i) {
                sb.append('0');
            }
            sb.append(s);
        }
        return sb.toString();
    }
    
    public BigInteger toBigInteger() {
        return this.setScale(0, 1).inflated();
    }
    
    public BigInteger toBigIntegerExact() {
        return this.setScale(0, 7).inflated();
    }
    
    @Override
    public long longValue() {
        if (this.intCompact != Long.MIN_VALUE && this.scale == 0) {
            return this.intCompact;
        }
        if (this.signum() == 0 || this.fractionOnly() || this.scale <= -64) {
            return 0L;
        }
        return this.toBigInteger().longValue();
    }
    
    private boolean fractionOnly() {
        assert this.signum() != 0;
        return this.precision() - this.scale <= 0;
    }
    
    public long longValueExact() {
        if (this.intCompact != Long.MIN_VALUE && this.scale == 0) {
            return this.intCompact;
        }
        if (this.signum() == 0) {
            return 0L;
        }
        if (this.fractionOnly()) {
            throw new ArithmeticException("Rounding necessary");
        }
        if (this.precision() - this.scale > 19) {
            throw new ArithmeticException("Overflow");
        }
        final BigDecimal setScale = this.setScale(0, 7);
        if (setScale.precision() >= 19) {
            LongOverflow.check(setScale);
        }
        return setScale.inflated().longValue();
    }
    
    @Override
    public int intValue() {
        return (this.intCompact != Long.MIN_VALUE && this.scale == 0) ? ((int)this.intCompact) : ((int)this.longValue());
    }
    
    public int intValueExact() {
        final long longValueExact = this.longValueExact();
        if ((int)longValueExact != longValueExact) {
            throw new ArithmeticException("Overflow");
        }
        return (int)longValueExact;
    }
    
    public short shortValueExact() {
        final long longValueExact = this.longValueExact();
        if ((short)longValueExact != longValueExact) {
            throw new ArithmeticException("Overflow");
        }
        return (short)longValueExact;
    }
    
    public byte byteValueExact() {
        final long longValueExact = this.longValueExact();
        if ((byte)longValueExact != longValueExact) {
            throw new ArithmeticException("Overflow");
        }
        return (byte)longValueExact;
    }
    
    @Override
    public float floatValue() {
        if (this.intCompact != Long.MIN_VALUE) {
            if (this.scale == 0) {
                return this.intCompact;
            }
            if (Math.abs(this.intCompact) < 4194304L) {
                if (this.scale > 0 && this.scale < BigDecimal.float10pow.length) {
                    return this.intCompact / BigDecimal.float10pow[this.scale];
                }
                if (this.scale < 0 && this.scale > -BigDecimal.float10pow.length) {
                    return this.intCompact * BigDecimal.float10pow[-this.scale];
                }
            }
        }
        return Float.parseFloat(this.toString());
    }
    
    @Override
    public double doubleValue() {
        if (this.intCompact != Long.MIN_VALUE) {
            if (this.scale == 0) {
                return this.intCompact;
            }
            if (Math.abs(this.intCompact) < 4503599627370496L) {
                if (this.scale > 0 && this.scale < BigDecimal.double10pow.length) {
                    return this.intCompact / BigDecimal.double10pow[this.scale];
                }
                if (this.scale < 0 && this.scale > -BigDecimal.double10pow.length) {
                    return this.intCompact * BigDecimal.double10pow[-this.scale];
                }
            }
        }
        return Double.parseDouble(this.toString());
    }
    
    public BigDecimal ulp() {
        return valueOf(1L, this.scale(), 1);
    }
    
    private String layoutChars(final boolean b) {
        if (this.scale == 0) {
            return (this.intCompact != Long.MIN_VALUE) ? Long.toString(this.intCompact) : this.intVal.toString();
        }
        if (this.scale == 2 && this.intCompact >= 0L && this.intCompact < 2147483647L) {
            final int n = (int)this.intCompact % 100;
            return Integer.toString((int)this.intCompact / 100) + '.' + StringBuilderHelper.DIGIT_TENS[n] + StringBuilderHelper.DIGIT_ONES[n];
        }
        final StringBuilderHelper stringBuilderHelper = BigDecimal.threadLocalStringBuilderHelper.get();
        int putIntCompact;
        char[] array;
        if (this.intCompact != Long.MIN_VALUE) {
            putIntCompact = stringBuilderHelper.putIntCompact(Math.abs(this.intCompact));
            array = stringBuilderHelper.getCompactCharArray();
        }
        else {
            putIntCompact = 0;
            array = this.intVal.abs().toString().toCharArray();
        }
        final StringBuilder stringBuilder = stringBuilderHelper.getStringBuilder();
        if (this.signum() < 0) {
            stringBuilder.append('-');
        }
        final int n2 = array.length - putIntCompact;
        long n3 = -this.scale + (n2 - 1);
        if (this.scale >= 0 && n3 >= -6L) {
            int i = this.scale - n2;
            if (i >= 0) {
                stringBuilder.append('0');
                stringBuilder.append('.');
                while (i > 0) {
                    stringBuilder.append('0');
                    --i;
                }
                stringBuilder.append(array, putIntCompact, n2);
            }
            else {
                stringBuilder.append(array, putIntCompact, -i);
                stringBuilder.append('.');
                stringBuilder.append(array, -i + putIntCompact, this.scale);
            }
        }
        else {
            if (b) {
                stringBuilder.append(array[putIntCompact]);
                if (n2 > 1) {
                    stringBuilder.append('.');
                    stringBuilder.append(array, putIntCompact + 1, n2 - 1);
                }
            }
            else {
                int n4 = (int)(n3 % 3L);
                if (n4 < 0) {
                    n4 += 3;
                }
                n3 -= n4;
                ++n4;
                if (this.signum() == 0) {
                    switch (n4) {
                        case 1: {
                            stringBuilder.append('0');
                            break;
                        }
                        case 2: {
                            stringBuilder.append("0.00");
                            n3 += 3L;
                            break;
                        }
                        case 3: {
                            stringBuilder.append("0.0");
                            n3 += 3L;
                            break;
                        }
                        default: {
                            throw new AssertionError((Object)("Unexpected sig value " + n4));
                        }
                    }
                }
                else if (n4 >= n2) {
                    stringBuilder.append(array, putIntCompact, n2);
                    for (int j = n4 - n2; j > 0; --j) {
                        stringBuilder.append('0');
                    }
                }
                else {
                    stringBuilder.append(array, putIntCompact, n4);
                    stringBuilder.append('.');
                    stringBuilder.append(array, putIntCompact + n4, n2 - n4);
                }
            }
            if (n3 != 0L) {
                stringBuilder.append('E');
                if (n3 > 0L) {
                    stringBuilder.append('+');
                }
                stringBuilder.append(n3);
            }
        }
        return stringBuilder.toString();
    }
    
    private static BigInteger bigTenToThe(final int n) {
        if (n < 0) {
            return BigInteger.ZERO;
        }
        if (n >= BigDecimal.BIG_TEN_POWERS_TABLE_MAX) {
            return BigInteger.TEN.pow(n);
        }
        final BigInteger[] big_TEN_POWERS_TABLE = BigDecimal.BIG_TEN_POWERS_TABLE;
        if (n < big_TEN_POWERS_TABLE.length) {
            return big_TEN_POWERS_TABLE[n];
        }
        return expandBigIntegerTenPowers(n);
    }
    
    private static BigInteger expandBigIntegerTenPowers(final int n) {
        synchronized (BigDecimal.class) {
            BigInteger[] big_TEN_POWERS_TABLE = BigDecimal.BIG_TEN_POWERS_TABLE;
            final int length = big_TEN_POWERS_TABLE.length;
            if (length <= n) {
                int i;
                for (i = length << 1; i <= n; i <<= 1) {}
                big_TEN_POWERS_TABLE = Arrays.copyOf(big_TEN_POWERS_TABLE, i);
                for (int j = length; j < i; ++j) {
                    big_TEN_POWERS_TABLE[j] = big_TEN_POWERS_TABLE[j - 1].multiply(BigInteger.TEN);
                }
                BigDecimal.BIG_TEN_POWERS_TABLE = big_TEN_POWERS_TABLE;
            }
            return big_TEN_POWERS_TABLE[n];
        }
    }
    
    private static long longMultiplyPowerTen(final long n, final int n2) {
        if (n == 0L || n2 <= 0) {
            return n;
        }
        final long[] long_TEN_POWERS_TABLE = BigDecimal.LONG_TEN_POWERS_TABLE;
        final long[] thresholds_TABLE = BigDecimal.THRESHOLDS_TABLE;
        if (n2 < long_TEN_POWERS_TABLE.length && n2 < thresholds_TABLE.length) {
            final long n3 = long_TEN_POWERS_TABLE[n2];
            if (n == 1L) {
                return n3;
            }
            if (Math.abs(n) <= thresholds_TABLE[n2]) {
                return n * n3;
            }
        }
        return Long.MIN_VALUE;
    }
    
    private BigInteger bigMultiplyPowerTen(final int n) {
        if (n <= 0) {
            return this.inflated();
        }
        if (this.intCompact != Long.MIN_VALUE) {
            return bigTenToThe(n).multiply(this.intCompact);
        }
        return this.intVal.multiply(bigTenToThe(n));
    }
    
    private BigInteger inflated() {
        if (this.intVal == null) {
            return BigInteger.valueOf(this.intCompact);
        }
        return this.intVal;
    }
    
    private static void matchScale(final BigDecimal[] array) {
        if (array[0].scale == array[1].scale) {
            return;
        }
        if (array[0].scale < array[1].scale) {
            array[0] = array[0].setScale(array[1].scale, 7);
        }
        else if (array[1].scale < array[0].scale) {
            array[1] = array[1].setScale(array[0].scale, 7);
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.intVal == null) {
            throw new StreamCorruptedException("BigDecimal: null intVal in stream");
        }
        UnsafeHolder.setIntCompactVolatile(this, compactValFor(this.intVal));
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.intVal == null) {
            UnsafeHolder.setIntValVolatile(this, BigInteger.valueOf(this.intCompact));
        }
        objectOutputStream.defaultWriteObject();
    }
    
    static int longDigitLength(long n) {
        assert n != Long.MIN_VALUE;
        if (n < 0L) {
            n = -n;
        }
        if (n < 10L) {
            return 1;
        }
        final int n2 = (64 - Long.numberOfLeadingZeros(n) + 1) * 1233 >>> 12;
        final long[] long_TEN_POWERS_TABLE = BigDecimal.LONG_TEN_POWERS_TABLE;
        return (n2 >= long_TEN_POWERS_TABLE.length || n < long_TEN_POWERS_TABLE[n2]) ? n2 : (n2 + 1);
    }
    
    private static int bigDigitLength(final BigInteger bigInteger) {
        if (bigInteger.signum == 0) {
            return 1;
        }
        final int n = (int)((bigInteger.bitLength() + 1L) * 646456993L >>> 31);
        return (bigInteger.compareMagnitude(bigTenToThe(n)) < 0) ? n : (n + 1);
    }
    
    private int checkScale(final long n) {
        int n2 = (int)n;
        if (n2 != n) {
            n2 = ((n > 2147483647L) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            final BigInteger intVal;
            if (this.intCompact != 0L && ((intVal = this.intVal) == null || intVal.signum() != 0)) {
                throw new ArithmeticException((n2 > 0) ? "Underflow" : "Overflow");
            }
        }
        return n2;
    }
    
    private static long compactValFor(final BigInteger bigInteger) {
        final int[] mag = bigInteger.mag;
        final int length = mag.length;
        if (length == 0) {
            return 0L;
        }
        final int n = mag[0];
        if (length > 2 || (length == 2 && n < 0)) {
            return Long.MIN_VALUE;
        }
        final long n2 = (length == 2) ? ((mag[1] & 0xFFFFFFFFL) + (n << 32)) : (n & 0xFFFFFFFFL);
        return (bigInteger.signum < 0) ? (-n2) : n2;
    }
    
    private static int longCompareMagnitude(long n, long n2) {
        if (n < 0L) {
            n = -n;
        }
        if (n2 < 0L) {
            n2 = -n2;
        }
        return (n < n2) ? -1 : ((n == n2) ? 0 : 1);
    }
    
    private static int saturateLong(final long n) {
        final int n2 = (int)n;
        return (n == n2) ? n2 : ((n < 0L) ? Integer.MIN_VALUE : Integer.MAX_VALUE);
    }
    
    private static void print(final String s, final BigDecimal bigDecimal) {
        System.err.format("%s:\tintCompact %d\tintVal %d\tscale %d\tprecision %d%n", s, bigDecimal.intCompact, bigDecimal.intVal, bigDecimal.scale, bigDecimal.precision);
    }
    
    private BigDecimal audit() {
        if (this.intCompact == Long.MIN_VALUE) {
            if (this.intVal == null) {
                print("audit", this);
                throw new AssertionError((Object)"null intVal");
            }
            if (this.precision > 0 && this.precision != bigDigitLength(this.intVal)) {
                print("audit", this);
                throw new AssertionError((Object)"precision mismatch");
            }
        }
        else {
            if (this.intVal != null) {
                final long longValue = this.intVal.longValue();
                if (longValue != this.intCompact) {
                    print("audit", this);
                    throw new AssertionError((Object)("Inconsistent state, intCompact=" + this.intCompact + "\t intVal=" + longValue));
                }
            }
            if (this.precision > 0 && this.precision != longDigitLength(this.intCompact)) {
                print("audit", this);
                throw new AssertionError((Object)"precision mismatch");
            }
        }
        return this;
    }
    
    private static int checkScaleNonZero(final long n) {
        final int n2 = (int)n;
        if (n2 != n) {
            throw new ArithmeticException((n2 > 0) ? "Underflow" : "Overflow");
        }
        return n2;
    }
    
    private static int checkScale(final long n, final long n2) {
        int n3 = (int)n2;
        if (n3 != n2) {
            n3 = ((n2 > 2147483647L) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            if (n != 0L) {
                throw new ArithmeticException((n3 > 0) ? "Underflow" : "Overflow");
            }
        }
        return n3;
    }
    
    private static int checkScale(final BigInteger bigInteger, final long n) {
        int n2 = (int)n;
        if (n2 != n) {
            n2 = ((n > 2147483647L) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            if (bigInteger.signum() != 0) {
                throw new ArithmeticException((n2 > 0) ? "Underflow" : "Overflow");
            }
        }
        return n2;
    }
    
    private static BigDecimal doRound(final BigDecimal bigDecimal, final MathContext mathContext) {
        final int precision = mathContext.precision;
        boolean b = false;
        if (precision > 0) {
            BigInteger bigInteger = bigDecimal.intVal;
            long n = bigDecimal.intCompact;
            int n2 = bigDecimal.scale;
            int n3 = bigDecimal.precision();
            final int oldMode = mathContext.roundingMode.oldMode;
            if (n == Long.MIN_VALUE) {
                for (int i = n3 - precision; i > 0; i = n3 - precision) {
                    n2 = checkScaleNonZero(n2 - i);
                    bigInteger = divideAndRoundByTenPow(bigInteger, i, oldMode);
                    b = true;
                    n = compactValFor(bigInteger);
                    if (n != Long.MIN_VALUE) {
                        n3 = longDigitLength(n);
                        break;
                    }
                    n3 = bigDigitLength(bigInteger);
                }
            }
            if (n != Long.MIN_VALUE) {
                for (int j = n3 - precision; j > 0; j = n3 - precision, bigInteger = null) {
                    n2 = checkScaleNonZero(n2 - j);
                    n = divideAndRound(n, BigDecimal.LONG_TEN_POWERS_TABLE[j], mathContext.roundingMode.oldMode);
                    b = true;
                    n3 = longDigitLength(n);
                }
            }
            return b ? new BigDecimal(bigInteger, n, n2, n3) : bigDecimal;
        }
        return bigDecimal;
    }
    
    private static BigDecimal doRound(long divideAndRound, int checkScaleNonZero, final MathContext mathContext) {
        final int precision = mathContext.precision;
        if (precision > 0 && precision < 19) {
            int n = longDigitLength(divideAndRound);
            for (int i = n - precision; i > 0; i = n - precision) {
                checkScaleNonZero = checkScaleNonZero(checkScaleNonZero - i);
                divideAndRound = divideAndRound(divideAndRound, BigDecimal.LONG_TEN_POWERS_TABLE[i], mathContext.roundingMode.oldMode);
                n = longDigitLength(divideAndRound);
            }
            return valueOf(divideAndRound, checkScaleNonZero, n);
        }
        return valueOf(divideAndRound, checkScaleNonZero);
    }
    
    private static BigDecimal doRound(BigInteger divideAndRoundByTenPow, int n, final MathContext mathContext) {
        final int precision = mathContext.precision;
        int n2 = 0;
        if (precision > 0) {
            long n3 = compactValFor(divideAndRoundByTenPow);
            final int oldMode = mathContext.roundingMode.oldMode;
            if (n3 == Long.MIN_VALUE) {
                n2 = bigDigitLength(divideAndRoundByTenPow);
                for (int i = n2 - precision; i > 0; i = n2 - precision) {
                    n = checkScaleNonZero(n - i);
                    divideAndRoundByTenPow = divideAndRoundByTenPow(divideAndRoundByTenPow, i, oldMode);
                    n3 = compactValFor(divideAndRoundByTenPow);
                    if (n3 != Long.MIN_VALUE) {
                        break;
                    }
                    n2 = bigDigitLength(divideAndRoundByTenPow);
                }
            }
            if (n3 != Long.MIN_VALUE) {
                int n4 = longDigitLength(n3);
                for (int j = n4 - precision; j > 0; j = n4 - precision) {
                    n = checkScaleNonZero(n - j);
                    n3 = divideAndRound(n3, BigDecimal.LONG_TEN_POWERS_TABLE[j], mathContext.roundingMode.oldMode);
                    n4 = longDigitLength(n3);
                }
                return valueOf(n3, n, n4);
            }
        }
        return new BigDecimal(divideAndRoundByTenPow, Long.MIN_VALUE, n, n2);
    }
    
    private static BigInteger divideAndRoundByTenPow(BigInteger bigInteger, final int n, final int n2) {
        if (n < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
            bigInteger = divideAndRound(bigInteger, BigDecimal.LONG_TEN_POWERS_TABLE[n], n2);
        }
        else {
            bigInteger = divideAndRound(bigInteger, bigTenToThe(n), n2);
        }
        return bigInteger;
    }
    
    private static BigDecimal divideAndRound(final long n, final long n2, final int n3, final int n4, final int n5) {
        final long n6 = n / n2;
        if (n4 == 1 && n3 == n5) {
            return valueOf(n6, n3);
        }
        final long n7 = n % n2;
        final int n8 = (n < 0L == n2 < 0L) ? 1 : -1;
        if (n7 != 0L) {
            return valueOf(needIncrement(n2, n4, n8, n6, n7) ? (n6 + n8) : n6, n3);
        }
        if (n5 != n3) {
            return createAndStripZerosToMatchScale(n6, n3, n5);
        }
        return valueOf(n6, n3);
    }
    
    private static long divideAndRound(final long n, final long n2, final int n3) {
        final long n4 = n / n2;
        if (n3 == 1) {
            return n4;
        }
        final long n5 = n % n2;
        final int n6 = (n < 0L == n2 < 0L) ? 1 : -1;
        if (n5 != 0L) {
            return needIncrement(n2, n3, n6, n4, n5) ? (n4 + n6) : n4;
        }
        return n4;
    }
    
    private static boolean commonNeedIncrement(final int n, final int n2, final int n3, final boolean b) {
        switch (n) {
            case 7: {
                throw new ArithmeticException("Rounding necessary");
            }
            case 0: {
                return true;
            }
            case 1: {
                return false;
            }
            case 2: {
                return n2 > 0;
            }
            case 3: {
                return n2 < 0;
            }
            default: {
                assert n >= 4 && n <= 6 : "Unexpected rounding mode" + RoundingMode.valueOf(n);
                if (n3 < 0) {
                    return false;
                }
                if (n3 > 0) {
                    return true;
                }
                assert n3 == 0;
                switch (n) {
                    case 5: {
                        return false;
                    }
                    case 4: {
                        return true;
                    }
                    case 6: {
                        return b;
                    }
                    default: {
                        throw new AssertionError((Object)("Unexpected rounding mode" + n));
                    }
                }
                break;
            }
        }
    }
    
    private static boolean needIncrement(final long n, final int n2, final int n3, final long n4, final long n5) {
        assert n5 != 0L;
        int longCompareMagnitude;
        if (n5 <= -4611686018427387904L || n5 > 4611686018427387903L) {
            longCompareMagnitude = 1;
        }
        else {
            longCompareMagnitude = longCompareMagnitude(2L * n5, n);
        }
        return commonNeedIncrement(n2, n3, longCompareMagnitude, (n4 & 0x1L) != 0x0L);
    }
    
    private static BigInteger divideAndRound(final BigInteger bigInteger, final long n, final int n2) {
        final MutableBigInteger mutableBigInteger = new MutableBigInteger(bigInteger.mag);
        final MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
        final long divide = mutableBigInteger.divide(n, mutableBigInteger2);
        final boolean b = divide == 0L;
        final int n3 = (n < 0L) ? (-bigInteger.signum) : bigInteger.signum;
        if (!b && needIncrement(n, n2, n3, mutableBigInteger2, divide)) {
            mutableBigInteger2.add(MutableBigInteger.ONE);
        }
        return mutableBigInteger2.toBigInteger(n3);
    }
    
    private static BigDecimal divideAndRound(final BigInteger bigInteger, final long n, final int n2, final int n3, final int n4) {
        final MutableBigInteger mutableBigInteger = new MutableBigInteger(bigInteger.mag);
        final MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
        final long divide = mutableBigInteger.divide(n, mutableBigInteger2);
        final boolean b = divide == 0L;
        final int n5 = (n < 0L) ? (-bigInteger.signum) : bigInteger.signum;
        if (!b) {
            if (needIncrement(n, n3, n5, mutableBigInteger2, divide)) {
                mutableBigInteger2.add(MutableBigInteger.ONE);
            }
            return mutableBigInteger2.toBigDecimal(n5, n2);
        }
        if (n4 == n2) {
            return mutableBigInteger2.toBigDecimal(n5, n2);
        }
        final long compactValue = mutableBigInteger2.toCompactValue(n5);
        if (compactValue != Long.MIN_VALUE) {
            return createAndStripZerosToMatchScale(compactValue, n2, n4);
        }
        return createAndStripZerosToMatchScale(mutableBigInteger2.toBigInteger(n5), n2, n4);
    }
    
    private static boolean needIncrement(final long n, final int n2, final int n3, final MutableBigInteger mutableBigInteger, final long n4) {
        assert n4 != 0L;
        int longCompareMagnitude;
        if (n4 <= -4611686018427387904L || n4 > 4611686018427387903L) {
            longCompareMagnitude = 1;
        }
        else {
            longCompareMagnitude = longCompareMagnitude(2L * n4, n);
        }
        return commonNeedIncrement(n2, n3, longCompareMagnitude, mutableBigInteger.isOdd());
    }
    
    private static BigInteger divideAndRound(final BigInteger bigInteger, final BigInteger bigInteger2, final int n) {
        final MutableBigInteger mutableBigInteger = new MutableBigInteger(bigInteger.mag);
        final MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
        final MutableBigInteger mutableBigInteger3 = new MutableBigInteger(bigInteger2.mag);
        final MutableBigInteger divide = mutableBigInteger.divide(mutableBigInteger3, mutableBigInteger2);
        final boolean zero = divide.isZero();
        final int n2 = (bigInteger.signum != bigInteger2.signum) ? -1 : 1;
        if (!zero && needIncrement(mutableBigInteger3, n, n2, mutableBigInteger2, divide)) {
            mutableBigInteger2.add(MutableBigInteger.ONE);
        }
        return mutableBigInteger2.toBigInteger(n2);
    }
    
    private static BigDecimal divideAndRound(final BigInteger bigInteger, final BigInteger bigInteger2, final int n, final int n2, final int n3) {
        final MutableBigInteger mutableBigInteger = new MutableBigInteger(bigInteger.mag);
        final MutableBigInteger mutableBigInteger2 = new MutableBigInteger();
        final MutableBigInteger mutableBigInteger3 = new MutableBigInteger(bigInteger2.mag);
        final MutableBigInteger divide = mutableBigInteger.divide(mutableBigInteger3, mutableBigInteger2);
        final boolean zero = divide.isZero();
        final int n4 = (bigInteger.signum != bigInteger2.signum) ? -1 : 1;
        if (!zero) {
            if (needIncrement(mutableBigInteger3, n2, n4, mutableBigInteger2, divide)) {
                mutableBigInteger2.add(MutableBigInteger.ONE);
            }
            return mutableBigInteger2.toBigDecimal(n4, n);
        }
        if (n3 == n) {
            return mutableBigInteger2.toBigDecimal(n4, n);
        }
        final long compactValue = mutableBigInteger2.toCompactValue(n4);
        if (compactValue != Long.MIN_VALUE) {
            return createAndStripZerosToMatchScale(compactValue, n, n3);
        }
        return createAndStripZerosToMatchScale(mutableBigInteger2.toBigInteger(n4), n, n3);
    }
    
    private static boolean needIncrement(final MutableBigInteger mutableBigInteger, final int n, final int n2, final MutableBigInteger mutableBigInteger2, final MutableBigInteger mutableBigInteger3) {
        assert !mutableBigInteger3.isZero();
        return commonNeedIncrement(n, n2, mutableBigInteger3.compareHalf(mutableBigInteger), mutableBigInteger2.isOdd());
    }
    
    private static BigDecimal createAndStripZerosToMatchScale(BigInteger bigInteger, int checkScale, final long n) {
        while (bigInteger.compareMagnitude(BigInteger.TEN) >= 0 && checkScale > n) {
            if (bigInteger.testBit(0)) {
                break;
            }
            final BigInteger[] divideAndRemainder = bigInteger.divideAndRemainder(BigInteger.TEN);
            if (divideAndRemainder[1].signum() != 0) {
                break;
            }
            bigInteger = divideAndRemainder[0];
            checkScale = checkScale(bigInteger, checkScale - 1L);
        }
        return valueOf(bigInteger, checkScale, 0);
    }
    
    private static BigDecimal createAndStripZerosToMatchScale(long n, int checkScale, final long n2) {
        while (Math.abs(n) >= 10L && checkScale > n2) {
            if ((n & 0x1L) != 0x0L) {
                break;
            }
            if (n % 10L != 0L) {
                break;
            }
            n /= 10L;
            checkScale = checkScale(n, checkScale - 1L);
        }
        return valueOf(n, checkScale);
    }
    
    private static BigDecimal stripZerosToMatchScale(final BigInteger bigInteger, final long n, final int n2, final int n3) {
        if (n != Long.MIN_VALUE) {
            return createAndStripZerosToMatchScale(n, n2, n3);
        }
        return createAndStripZerosToMatchScale((bigInteger == null) ? BigDecimal.INFLATED_BIGINT : bigInteger, n2, n3);
    }
    
    private static long add(final long n, final long n2) {
        final long n3 = n + n2;
        if (((n3 ^ n) & (n3 ^ n2)) >= 0L) {
            return n3;
        }
        return Long.MIN_VALUE;
    }
    
    private static BigDecimal add(final long n, final long n2, final int n3) {
        final long add = add(n, n2);
        if (add != Long.MIN_VALUE) {
            return valueOf(add, n3);
        }
        return new BigDecimal(BigInteger.valueOf(n).add(n2), n3);
    }
    
    private static BigDecimal add(final long n, final int n2, final long n3, final int n4) {
        final long n5 = n2 - n4;
        if (n5 == 0L) {
            return add(n, n3, n2);
        }
        if (n5 < 0L) {
            final int checkScale = checkScale(n, -n5);
            final long longMultiplyPowerTen = longMultiplyPowerTen(n, checkScale);
            if (longMultiplyPowerTen != Long.MIN_VALUE) {
                return add(longMultiplyPowerTen, n3, n4);
            }
            final BigInteger add = bigMultiplyPowerTen(n, checkScale).add(n3);
            return ((n ^ n3) >= 0L) ? new BigDecimal(add, Long.MIN_VALUE, n4, 0) : valueOf(add, n4, 0);
        }
        else {
            final int checkScale2 = checkScale(n3, n5);
            final long longMultiplyPowerTen2 = longMultiplyPowerTen(n3, checkScale2);
            if (longMultiplyPowerTen2 != Long.MIN_VALUE) {
                return add(n, longMultiplyPowerTen2, n2);
            }
            final BigInteger add2 = bigMultiplyPowerTen(n3, checkScale2).add(n);
            return ((n ^ n3) >= 0L) ? new BigDecimal(add2, Long.MIN_VALUE, n2, 0) : valueOf(add2, n2, 0);
        }
    }
    
    private static BigDecimal add(final long n, final int n2, BigInteger bigMultiplyPowerTen, final int n3) {
        int n4 = n2;
        final long n5 = n4 - n3;
        final boolean b = Long.signum(n) == bigMultiplyPowerTen.signum;
        BigInteger bigInteger;
        if (n5 < 0L) {
            final int checkScale = checkScale(n, -n5);
            n4 = n3;
            final long longMultiplyPowerTen = longMultiplyPowerTen(n, checkScale);
            if (longMultiplyPowerTen == Long.MIN_VALUE) {
                bigInteger = bigMultiplyPowerTen.add(bigMultiplyPowerTen(n, checkScale));
            }
            else {
                bigInteger = bigMultiplyPowerTen.add(longMultiplyPowerTen);
            }
        }
        else {
            bigMultiplyPowerTen = bigMultiplyPowerTen(bigMultiplyPowerTen, checkScale(bigMultiplyPowerTen, n5));
            bigInteger = bigMultiplyPowerTen.add(n);
        }
        return b ? new BigDecimal(bigInteger, Long.MIN_VALUE, n4, 0) : valueOf(bigInteger, n4, 0);
    }
    
    private static BigDecimal add(BigInteger bigMultiplyPowerTen, final int n, BigInteger bigMultiplyPowerTen2, final int n2) {
        int n3 = n;
        final long n4 = n3 - n2;
        if (n4 != 0L) {
            if (n4 < 0L) {
                final int checkScale = checkScale(bigMultiplyPowerTen, -n4);
                n3 = n2;
                bigMultiplyPowerTen = bigMultiplyPowerTen(bigMultiplyPowerTen, checkScale);
            }
            else {
                bigMultiplyPowerTen2 = bigMultiplyPowerTen(bigMultiplyPowerTen2, checkScale(bigMultiplyPowerTen2, n4));
            }
        }
        final BigInteger add = bigMultiplyPowerTen.add(bigMultiplyPowerTen2);
        return (bigMultiplyPowerTen.signum == bigMultiplyPowerTen2.signum) ? new BigDecimal(add, Long.MIN_VALUE, n3, 0) : valueOf(add, n3, 0);
    }
    
    private static BigInteger bigMultiplyPowerTen(final long n, final int n2) {
        if (n2 <= 0) {
            return BigInteger.valueOf(n);
        }
        return bigTenToThe(n2).multiply(n);
    }
    
    private static BigInteger bigMultiplyPowerTen(final BigInteger bigInteger, final int n) {
        if (n <= 0) {
            return bigInteger;
        }
        if (n < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
            return bigInteger.multiply(BigDecimal.LONG_TEN_POWERS_TABLE[n]);
        }
        return bigInteger.multiply(bigTenToThe(n));
    }
    
    private static BigDecimal divideSmallFastPath(final long n, final int n2, final long n3, int n4, final long n5, final MathContext mathContext) {
        final int precision = mathContext.precision;
        final int oldMode = mathContext.roundingMode.oldMode;
        assert n2 <= n4 && n4 < 18 && precision < 18;
        final int n6 = n4 - n2;
        final long n7 = (n6 == 0) ? n : longMultiplyPowerTen(n, n6);
        final int longCompareMagnitude = longCompareMagnitude(n7, n3);
        BigDecimal bigDecimal;
        if (longCompareMagnitude > 0) {
            --n4;
            final int checkScaleNonZero = checkScaleNonZero(n5 + n4 - n2 + precision);
            if (checkScaleNonZero(precision + n4 - n2) > 0) {
                final long longMultiplyPowerTen;
                if ((longMultiplyPowerTen = longMultiplyPowerTen(n, checkScaleNonZero(precision + n4 - n2))) == Long.MIN_VALUE) {
                    bigDecimal = null;
                    if (precision - 1 >= 0 && precision - 1 < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
                        bigDecimal = multiplyDivideAndRound(BigDecimal.LONG_TEN_POWERS_TABLE[precision - 1], n7, n3, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
                    }
                    if (bigDecimal == null) {
                        bigDecimal = divideAndRound(bigMultiplyPowerTen(n7, precision - 1), n3, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
                    }
                }
                else {
                    bigDecimal = divideAndRound(longMultiplyPowerTen, n3, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
                }
            }
            else {
                final int checkScaleNonZero2 = checkScaleNonZero(n2 - precision);
                if (checkScaleNonZero2 == n4) {
                    bigDecimal = divideAndRound(n, n3, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
                }
                else {
                    final int checkScaleNonZero3 = checkScaleNonZero(checkScaleNonZero2 - n4);
                    final long longMultiplyPowerTen2;
                    if ((longMultiplyPowerTen2 = longMultiplyPowerTen(n3, checkScaleNonZero3)) == Long.MIN_VALUE) {
                        bigDecimal = divideAndRound(BigInteger.valueOf(n), bigMultiplyPowerTen(n3, checkScaleNonZero3), checkScaleNonZero, oldMode, checkScaleNonZero(n5));
                    }
                    else {
                        bigDecimal = divideAndRound(n, longMultiplyPowerTen2, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
                    }
                }
            }
        }
        else {
            final int checkScaleNonZero4 = checkScaleNonZero(n5 + n4 - n2 + precision);
            if (longCompareMagnitude == 0) {
                bigDecimal = roundedTenPower((n7 < 0L == n3 < 0L) ? 1 : -1, precision, checkScaleNonZero4, checkScaleNonZero(n5));
            }
            else {
                final long longMultiplyPowerTen3;
                if ((longMultiplyPowerTen3 = longMultiplyPowerTen(n7, precision)) == Long.MIN_VALUE) {
                    bigDecimal = null;
                    if (precision < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
                        bigDecimal = multiplyDivideAndRound(BigDecimal.LONG_TEN_POWERS_TABLE[precision], n7, n3, checkScaleNonZero4, oldMode, checkScaleNonZero(n5));
                    }
                    if (bigDecimal == null) {
                        bigDecimal = divideAndRound(bigMultiplyPowerTen(n7, precision), n3, checkScaleNonZero4, oldMode, checkScaleNonZero(n5));
                    }
                }
                else {
                    bigDecimal = divideAndRound(longMultiplyPowerTen3, n3, checkScaleNonZero4, oldMode, checkScaleNonZero(n5));
                }
            }
        }
        return doRound(bigDecimal, mathContext);
    }
    
    private static BigDecimal divide(final long n, final int n2, final long n3, int n4, final long n5, final MathContext mathContext) {
        final int precision = mathContext.precision;
        if (n2 <= n4 && n4 < 18 && precision < 18) {
            return divideSmallFastPath(n, n2, n3, n4, n5, mathContext);
        }
        if (compareMagnitudeNormalized(n, n2, n3, n4) > 0) {
            --n4;
        }
        final int oldMode = mathContext.roundingMode.oldMode;
        final int checkScaleNonZero = checkScaleNonZero(n5 + n4 - n2 + precision);
        BigDecimal bigDecimal;
        if (checkScaleNonZero(precision + n4 - n2) > 0) {
            final int checkScaleNonZero2 = checkScaleNonZero(precision + n4 - n2);
            final long longMultiplyPowerTen;
            if ((longMultiplyPowerTen = longMultiplyPowerTen(n, checkScaleNonZero2)) == Long.MIN_VALUE) {
                bigDecimal = divideAndRound(bigMultiplyPowerTen(n, checkScaleNonZero2), n3, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
            }
            else {
                bigDecimal = divideAndRound(longMultiplyPowerTen, n3, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
            }
        }
        else {
            final int checkScaleNonZero3 = checkScaleNonZero(n2 - precision);
            if (checkScaleNonZero3 == n4) {
                bigDecimal = divideAndRound(n, n3, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
            }
            else {
                final int checkScaleNonZero4 = checkScaleNonZero(checkScaleNonZero3 - n4);
                final long longMultiplyPowerTen2;
                if ((longMultiplyPowerTen2 = longMultiplyPowerTen(n3, checkScaleNonZero4)) == Long.MIN_VALUE) {
                    bigDecimal = divideAndRound(BigInteger.valueOf(n), bigMultiplyPowerTen(n3, checkScaleNonZero4), checkScaleNonZero, oldMode, checkScaleNonZero(n5));
                }
                else {
                    bigDecimal = divideAndRound(n, longMultiplyPowerTen2, checkScaleNonZero, oldMode, checkScaleNonZero(n5));
                }
            }
        }
        return doRound(bigDecimal, mathContext);
    }
    
    private static BigDecimal divide(final BigInteger bigInteger, final int n, final long n2, int n3, final long n4, final MathContext mathContext) {
        if (-compareMagnitudeNormalized(n2, n3, bigInteger, n) > 0) {
            --n3;
        }
        final int precision = mathContext.precision;
        final int oldMode = mathContext.roundingMode.oldMode;
        final int checkScaleNonZero = checkScaleNonZero(n4 + n3 - n + precision);
        BigDecimal bigDecimal;
        if (checkScaleNonZero(precision + n3 - n) > 0) {
            bigDecimal = divideAndRound(bigMultiplyPowerTen(bigInteger, checkScaleNonZero(precision + n3 - n)), n2, checkScaleNonZero, oldMode, checkScaleNonZero(n4));
        }
        else {
            final int checkScaleNonZero2 = checkScaleNonZero(n - precision);
            if (checkScaleNonZero2 == n3) {
                bigDecimal = divideAndRound(bigInteger, n2, checkScaleNonZero, oldMode, checkScaleNonZero(n4));
            }
            else {
                final int checkScaleNonZero3 = checkScaleNonZero(checkScaleNonZero2 - n3);
                final long longMultiplyPowerTen;
                if ((longMultiplyPowerTen = longMultiplyPowerTen(n2, checkScaleNonZero3)) == Long.MIN_VALUE) {
                    bigDecimal = divideAndRound(bigInteger, bigMultiplyPowerTen(n2, checkScaleNonZero3), checkScaleNonZero, oldMode, checkScaleNonZero(n4));
                }
                else {
                    bigDecimal = divideAndRound(bigInteger, longMultiplyPowerTen, checkScaleNonZero, oldMode, checkScaleNonZero(n4));
                }
            }
        }
        return doRound(bigDecimal, mathContext);
    }
    
    private static BigDecimal divide(final long n, final int n2, final BigInteger bigInteger, int n3, final long n4, final MathContext mathContext) {
        if (compareMagnitudeNormalized(n, n2, bigInteger, n3) > 0) {
            --n3;
        }
        final int precision = mathContext.precision;
        final int oldMode = mathContext.roundingMode.oldMode;
        final int checkScaleNonZero = checkScaleNonZero(n4 + n3 - n2 + precision);
        BigDecimal bigDecimal;
        if (checkScaleNonZero(precision + n3 - n2) > 0) {
            bigDecimal = divideAndRound(bigMultiplyPowerTen(n, checkScaleNonZero(precision + n3 - n2)), bigInteger, checkScaleNonZero, oldMode, checkScaleNonZero(n4));
        }
        else {
            bigDecimal = divideAndRound(BigInteger.valueOf(n), bigMultiplyPowerTen(bigInteger, checkScaleNonZero(checkScaleNonZero(n2 - precision) - n3)), checkScaleNonZero, oldMode, checkScaleNonZero(n4));
        }
        return doRound(bigDecimal, mathContext);
    }
    
    private static BigDecimal divide(final BigInteger bigInteger, final int n, final BigInteger bigInteger2, int n2, final long n3, final MathContext mathContext) {
        if (compareMagnitudeNormalized(bigInteger, n, bigInteger2, n2) > 0) {
            --n2;
        }
        final int precision = mathContext.precision;
        final int oldMode = mathContext.roundingMode.oldMode;
        final int checkScaleNonZero = checkScaleNonZero(n3 + n2 - n + precision);
        BigDecimal bigDecimal;
        if (checkScaleNonZero(precision + n2 - n) > 0) {
            bigDecimal = divideAndRound(bigMultiplyPowerTen(bigInteger, checkScaleNonZero(precision + n2 - n)), bigInteger2, checkScaleNonZero, oldMode, checkScaleNonZero(n3));
        }
        else {
            bigDecimal = divideAndRound(bigInteger, bigMultiplyPowerTen(bigInteger2, checkScaleNonZero(checkScaleNonZero(n - precision) - n2)), checkScaleNonZero, oldMode, checkScaleNonZero(n3));
        }
        return doRound(bigDecimal, mathContext);
    }
    
    private static BigDecimal multiplyDivideAndRound(long abs, long abs2, long abs3, final int n, final int n2, final int n3) {
        final int n4 = Long.signum(abs) * Long.signum(abs2) * Long.signum(abs3);
        abs = Math.abs(abs);
        abs2 = Math.abs(abs2);
        abs3 = Math.abs(abs3);
        final long n5 = abs >>> 32;
        final long n6 = abs & 0xFFFFFFFFL;
        final long n7 = abs2 >>> 32;
        final long n8 = abs2 & 0xFFFFFFFFL;
        final long n9 = n6 * n8;
        final long n10 = n9 & 0xFFFFFFFFL;
        final long n11 = n5 * n8 + (n9 >>> 32);
        final long n12 = n11 & 0xFFFFFFFFL;
        final long n13 = n11 >>> 32;
        final long n14 = n6 * n7 + n12;
        final long n15 = n14 & 0xFFFFFFFFL;
        final long n16 = n13 + (n14 >>> 32);
        final long n17 = n16 >>> 32;
        final long n18 = n5 * n7 + (n16 & 0xFFFFFFFFL);
        return divideAndRound128(make64((n18 >>> 32) + n17 & 0xFFFFFFFFL, n18 & 0xFFFFFFFFL), make64(n15, n10), abs3, n4, n, n2, n3);
    }
    
    private static BigDecimal divideAndRound128(final long n, final long n2, long n3, final int n4, final int n5, final int n6, final int n7) {
        if (n >= n3) {
            return null;
        }
        final int numberOfLeadingZeros = Long.numberOfLeadingZeros(n3);
        n3 <<= numberOfLeadingZeros;
        final long n8 = n3 >>> 32;
        final long n9 = n3 & 0xFFFFFFFFL;
        final long n10 = n2 << numberOfLeadingZeros;
        final long n11 = n10 >>> 32;
        final long n12 = n10 & 0xFFFFFFFFL;
        final long n13 = n << numberOfLeadingZeros | n2 >>> 64 - numberOfLeadingZeros;
        final long n14 = n13 & 0xFFFFFFFFL;
        long n15;
        long n16;
        if (n8 == 1L) {
            n15 = n13;
            n16 = 0L;
        }
        else if (n13 >= 0L) {
            n15 = n13 / n8;
            n16 = n13 - n15 * n8;
        }
        else {
            final long[] divRemNegativeLong = divRemNegativeLong(n13, n8);
            n15 = divRemNegativeLong[1];
            n16 = divRemNegativeLong[0];
        }
        while (n15 >= 4294967296L || unsignedLongCompare(n15 * n9, make64(n16, n11))) {
            --n15;
            n16 += n8;
            if (n16 >= 4294967296L) {
                break;
            }
        }
        final long mulsub = mulsub(n14, n11, n8, n9, n15);
        final long n17 = mulsub & 0xFFFFFFFFL;
        long n18;
        long n19;
        if (n8 == 1L) {
            n18 = mulsub;
            n19 = 0L;
        }
        else if (mulsub >= 0L) {
            n18 = mulsub / n8;
            n19 = mulsub - n18 * n8;
        }
        else {
            final long[] divRemNegativeLong2 = divRemNegativeLong(mulsub, n8);
            n18 = divRemNegativeLong2[1];
            n19 = divRemNegativeLong2[0];
        }
        while (n18 >= 4294967296L || unsignedLongCompare(n18 * n9, make64(n19, n12))) {
            --n18;
            n19 += n8;
            if (n19 >= 4294967296L) {
                break;
            }
        }
        if ((int)n15 < 0) {
            final MutableBigInteger mutableBigInteger = new MutableBigInteger(new int[] { (int)n15, (int)n18 });
            if (n6 == 1 && n5 == n7) {
                return mutableBigInteger.toBigDecimal(n4, n5);
            }
            final long n20 = mulsub(n17, n12, n8, n9, n18) >>> numberOfLeadingZeros;
            if (n20 != 0L) {
                if (needIncrement(n3 >>> numberOfLeadingZeros, n6, n4, mutableBigInteger, n20)) {
                    mutableBigInteger.add(MutableBigInteger.ONE);
                }
                return mutableBigInteger.toBigDecimal(n4, n5);
            }
            if (n7 != n5) {
                return createAndStripZerosToMatchScale(mutableBigInteger.toBigInteger(n4), n5, n7);
            }
            return mutableBigInteger.toBigDecimal(n4, n5);
        }
        else {
            final long n21 = make64(n15, n18) * n4;
            if (n6 == 1 && n5 == n7) {
                return valueOf(n21, n5);
            }
            final long n22 = mulsub(n17, n12, n8, n9, n18) >>> numberOfLeadingZeros;
            if (n22 != 0L) {
                return valueOf(needIncrement(n3 >>> numberOfLeadingZeros, n6, n4, n21, n22) ? (n21 + n4) : n21, n5);
            }
            if (n7 != n5) {
                return createAndStripZerosToMatchScale(n21, n5, n7);
            }
            return valueOf(n21, n5);
        }
    }
    
    private static BigDecimal roundedTenPower(final int n, final int n2, final int n3, final int n4) {
        if (n3 <= n4) {
            return scaledTenPow(n2, n, n3);
        }
        final int n5 = n3 - n4;
        if (n5 < n2) {
            return scaledTenPow(n2 - n5, n, n4);
        }
        return valueOf(n, n3 - n2);
    }
    
    static BigDecimal scaledTenPow(final int n, final int n2, final int n3) {
        if (n < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
            return valueOf(n2 * BigDecimal.LONG_TEN_POWERS_TABLE[n], n3);
        }
        BigInteger bigInteger = bigTenToThe(n);
        if (n2 == -1) {
            bigInteger = bigInteger.negate();
        }
        return new BigDecimal(bigInteger, Long.MIN_VALUE, n3, n + 1);
    }
    
    private static long[] divRemNegativeLong(final long n, final long n2) {
        assert n < 0L : "Non-negative numerator " + n;
        assert n2 != 1L : "Unity denominator";
        long n3;
        long n4;
        for (n3 = (n >>> 1) / (n2 >>> 1), n4 = n - n3 * n2; n4 < 0L; n4 += n2, --n3) {}
        while (n4 >= n2) {
            n4 -= n2;
            ++n3;
        }
        return new long[] { n4, n3 };
    }
    
    private static long make64(final long n, final long n2) {
        return n << 32 | n2;
    }
    
    private static long mulsub(final long n, final long n2, final long n3, final long n4, final long n5) {
        final long n6 = n2 - n5 * n4;
        return make64(n + (n6 >>> 32) - n5 * n3, n6 & 0xFFFFFFFFL);
    }
    
    private static boolean unsignedLongCompare(final long n, final long n2) {
        return n + Long.MIN_VALUE > n2 + Long.MIN_VALUE;
    }
    
    private static boolean unsignedLongCompareEq(final long n, final long n2) {
        return n + Long.MIN_VALUE >= n2 + Long.MIN_VALUE;
    }
    
    private static int compareMagnitudeNormalized(long longMultiplyPowerTen, final int n, long longMultiplyPowerTen2, final int n2) {
        final int n3 = n - n2;
        if (n3 != 0) {
            if (n3 < 0) {
                longMultiplyPowerTen = longMultiplyPowerTen(longMultiplyPowerTen, -n3);
            }
            else {
                longMultiplyPowerTen2 = longMultiplyPowerTen(longMultiplyPowerTen2, n3);
            }
        }
        if (longMultiplyPowerTen != Long.MIN_VALUE) {
            return (longMultiplyPowerTen2 != Long.MIN_VALUE) ? longCompareMagnitude(longMultiplyPowerTen, longMultiplyPowerTen2) : -1;
        }
        return 1;
    }
    
    private static int compareMagnitudeNormalized(final long n, final int n2, final BigInteger bigInteger, final int n3) {
        if (n == 0L) {
            return -1;
        }
        final int n4 = n2 - n3;
        if (n4 < 0 && longMultiplyPowerTen(n, -n4) == Long.MIN_VALUE) {
            return bigMultiplyPowerTen(n, -n4).compareMagnitude(bigInteger);
        }
        return -1;
    }
    
    private static int compareMagnitudeNormalized(final BigInteger bigInteger, final int n, final BigInteger bigInteger2, final int n2) {
        final int n3 = n - n2;
        if (n3 < 0) {
            return bigMultiplyPowerTen(bigInteger, -n3).compareMagnitude(bigInteger2);
        }
        return bigInteger.compareMagnitude(bigMultiplyPowerTen(bigInteger2, n3));
    }
    
    private static long multiply(final long n, final long n2) {
        final long n3 = n * n2;
        if ((Math.abs(n) | Math.abs(n2)) >>> 31 == 0L || n2 == 0L || n3 / n2 == n) {
            return n3;
        }
        return Long.MIN_VALUE;
    }
    
    private static BigDecimal multiply(final long n, final long n2, final int n3) {
        final long multiply = multiply(n, n2);
        if (multiply != Long.MIN_VALUE) {
            return valueOf(multiply, n3);
        }
        return new BigDecimal(BigInteger.valueOf(n).multiply(n2), Long.MIN_VALUE, n3, 0);
    }
    
    private static BigDecimal multiply(final long n, final BigInteger bigInteger, final int n2) {
        if (n == 0L) {
            return zeroValueOf(n2);
        }
        return new BigDecimal(bigInteger.multiply(n), Long.MIN_VALUE, n2, 0);
    }
    
    private static BigDecimal multiply(final BigInteger bigInteger, final BigInteger bigInteger2, final int n) {
        return new BigDecimal(bigInteger.multiply(bigInteger2), Long.MIN_VALUE, n, 0);
    }
    
    private static BigDecimal multiplyAndRound(long n, long n2, final int n3, final MathContext mathContext) {
        final long multiply = multiply(n, n2);
        if (multiply != Long.MIN_VALUE) {
            return doRound(multiply, n3, mathContext);
        }
        int n4 = 1;
        if (n < 0L) {
            n = -n;
            n4 = -1;
        }
        if (n2 < 0L) {
            n2 = -n2;
            n4 *= -1;
        }
        final long n5 = n >>> 32;
        final long n6 = n & 0xFFFFFFFFL;
        final long n7 = n2 >>> 32;
        final long n8 = n2 & 0xFFFFFFFFL;
        final long n9 = n6 * n8;
        final long n10 = n9 & 0xFFFFFFFFL;
        final long n11 = n5 * n8 + (n9 >>> 32);
        final long n12 = n11 & 0xFFFFFFFFL;
        final long n13 = n11 >>> 32;
        final long n14 = n6 * n7 + n12;
        final long n15 = n14 & 0xFFFFFFFFL;
        final long n16 = n13 + (n14 >>> 32);
        final long n17 = n16 >>> 32;
        final long n18 = n5 * n7 + (n16 & 0xFFFFFFFFL);
        final BigDecimal doRound128 = doRound128(make64((n18 >>> 32) + n17 & 0xFFFFFFFFL, n18 & 0xFFFFFFFFL), make64(n15, n10), n4, n3, mathContext);
        if (doRound128 != null) {
            return doRound128;
        }
        return doRound(new BigDecimal(BigInteger.valueOf(n).multiply(n2 * n4), Long.MIN_VALUE, n3, 0), mathContext);
    }
    
    private static BigDecimal multiplyAndRound(final long n, final BigInteger bigInteger, final int n2, final MathContext mathContext) {
        if (n == 0L) {
            return zeroValueOf(n2);
        }
        return doRound(bigInteger.multiply(n), n2, mathContext);
    }
    
    private static BigDecimal multiplyAndRound(final BigInteger bigInteger, final BigInteger bigInteger2, final int n, final MathContext mathContext) {
        return doRound(bigInteger.multiply(bigInteger2), n, mathContext);
    }
    
    private static BigDecimal doRound128(final long n, final long n2, final int n3, int checkScaleNonZero, final MathContext mathContext) {
        final int precision = mathContext.precision;
        BigDecimal divideAndRound128 = null;
        final int n4;
        if ((n4 = precision(n, n2) - precision) > 0 && n4 < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
            checkScaleNonZero = checkScaleNonZero(checkScaleNonZero - n4);
            divideAndRound128 = divideAndRound128(n, n2, BigDecimal.LONG_TEN_POWERS_TABLE[n4], n3, checkScaleNonZero, mathContext.roundingMode.oldMode, checkScaleNonZero);
        }
        if (divideAndRound128 != null) {
            return doRound(divideAndRound128, mathContext);
        }
        return null;
    }
    
    private static int precision(final long n, final long n2) {
        if (n != 0L) {
            final int n3 = (128 - Long.numberOfLeadingZeros(n) + 1) * 1233 >>> 12;
            final int n4 = n3 - 19;
            return (n4 >= BigDecimal.LONGLONG_TEN_POWERS_TABLE.length || longLongCompareMagnitude(n, n2, BigDecimal.LONGLONG_TEN_POWERS_TABLE[n4][0], BigDecimal.LONGLONG_TEN_POWERS_TABLE[n4][1])) ? n3 : (n3 + 1);
        }
        if (n2 >= 0L) {
            return longDigitLength(n2);
        }
        return unsignedLongCompareEq(n2, BigDecimal.LONGLONG_TEN_POWERS_TABLE[0][1]) ? 20 : 19;
    }
    
    private static boolean longLongCompareMagnitude(final long n, final long n2, final long n3, final long n4) {
        if (n != n3) {
            return n < n3;
        }
        return n2 + Long.MIN_VALUE < n4 + Long.MIN_VALUE;
    }
    
    private static BigDecimal divide(final long n, final int n2, final long n3, final int n4, final int n5, final int n6) {
        if (checkScale(n, n5 + n4) > n2) {
            final int n7 = n5 + n4 - n2;
            if (n7 < BigDecimal.LONG_TEN_POWERS_TABLE.length) {
                final long longMultiplyPowerTen;
                if ((longMultiplyPowerTen = longMultiplyPowerTen(n, n7)) != Long.MIN_VALUE) {
                    return divideAndRound(longMultiplyPowerTen, n3, n5, n6, n5);
                }
                final BigDecimal multiplyDivideAndRound = multiplyDivideAndRound(BigDecimal.LONG_TEN_POWERS_TABLE[n7], n, n3, n5, n6, n5);
                if (multiplyDivideAndRound != null) {
                    return multiplyDivideAndRound;
                }
            }
            return divideAndRound(bigMultiplyPowerTen(n, n7), n3, n5, n6, n5);
        }
        final int n8 = checkScale(n3, n2 - n5) - n4;
        final long longMultiplyPowerTen2;
        if (n8 < BigDecimal.LONG_TEN_POWERS_TABLE.length && (longMultiplyPowerTen2 = longMultiplyPowerTen(n3, n8)) != Long.MIN_VALUE) {
            return divideAndRound(n, longMultiplyPowerTen2, n5, n6, n5);
        }
        return divideAndRound(BigInteger.valueOf(n), bigMultiplyPowerTen(n3, n8), n5, n6, n5);
    }
    
    private static BigDecimal divide(final BigInteger bigInteger, final int n, final long n2, final int n3, final int n4, final int n5) {
        if (checkScale(bigInteger, n4 + n3) > n) {
            return divideAndRound(bigMultiplyPowerTen(bigInteger, n4 + n3 - n), n2, n4, n5, n4);
        }
        final int n6 = checkScale(n2, n - n4) - n3;
        final long longMultiplyPowerTen;
        if (n6 < BigDecimal.LONG_TEN_POWERS_TABLE.length && (longMultiplyPowerTen = longMultiplyPowerTen(n2, n6)) != Long.MIN_VALUE) {
            return divideAndRound(bigInteger, longMultiplyPowerTen, n4, n5, n4);
        }
        return divideAndRound(bigInteger, bigMultiplyPowerTen(n2, n6), n4, n5, n4);
    }
    
    private static BigDecimal divide(final long n, final int n2, final BigInteger bigInteger, final int n3, final int n4, final int n5) {
        if (checkScale(n, n4 + n3) > n2) {
            return divideAndRound(bigMultiplyPowerTen(n, n4 + n3 - n2), bigInteger, n4, n5, n4);
        }
        return divideAndRound(BigInteger.valueOf(n), bigMultiplyPowerTen(bigInteger, checkScale(bigInteger, n2 - n4) - n3), n4, n5, n4);
    }
    
    private static BigDecimal divide(final BigInteger bigInteger, final int n, final BigInteger bigInteger2, final int n2, final int n3, final int n4) {
        if (checkScale(bigInteger, n3 + n2) > n) {
            return divideAndRound(bigMultiplyPowerTen(bigInteger, n3 + n2 - n), bigInteger2, n3, n4, n3);
        }
        return divideAndRound(bigInteger, bigMultiplyPowerTen(bigInteger2, checkScale(bigInteger2, n - n3) - n2), n3, n4, n3);
    }
    
    static {
        INFLATED_BIGINT = BigInteger.valueOf(Long.MIN_VALUE);
        threadLocalStringBuilderHelper = new ThreadLocal<StringBuilderHelper>() {
            @Override
            protected StringBuilderHelper initialValue() {
                return new StringBuilderHelper();
            }
        };
        zeroThroughTen = new BigDecimal[] { new BigDecimal(BigInteger.ZERO, 0L, 0, 1), new BigDecimal(BigInteger.ONE, 1L, 0, 1), new BigDecimal(BigInteger.valueOf(2L), 2L, 0, 1), new BigDecimal(BigInteger.valueOf(3L), 3L, 0, 1), new BigDecimal(BigInteger.valueOf(4L), 4L, 0, 1), new BigDecimal(BigInteger.valueOf(5L), 5L, 0, 1), new BigDecimal(BigInteger.valueOf(6L), 6L, 0, 1), new BigDecimal(BigInteger.valueOf(7L), 7L, 0, 1), new BigDecimal(BigInteger.valueOf(8L), 8L, 0, 1), new BigDecimal(BigInteger.valueOf(9L), 9L, 0, 1), new BigDecimal(BigInteger.TEN, 10L, 0, 2) };
        ZERO_SCALED_BY = new BigDecimal[] { BigDecimal.zeroThroughTen[0], new BigDecimal(BigInteger.ZERO, 0L, 1, 1), new BigDecimal(BigInteger.ZERO, 0L, 2, 1), new BigDecimal(BigInteger.ZERO, 0L, 3, 1), new BigDecimal(BigInteger.ZERO, 0L, 4, 1), new BigDecimal(BigInteger.ZERO, 0L, 5, 1), new BigDecimal(BigInteger.ZERO, 0L, 6, 1), new BigDecimal(BigInteger.ZERO, 0L, 7, 1), new BigDecimal(BigInteger.ZERO, 0L, 8, 1), new BigDecimal(BigInteger.ZERO, 0L, 9, 1), new BigDecimal(BigInteger.ZERO, 0L, 10, 1), new BigDecimal(BigInteger.ZERO, 0L, 11, 1), new BigDecimal(BigInteger.ZERO, 0L, 12, 1), new BigDecimal(BigInteger.ZERO, 0L, 13, 1), new BigDecimal(BigInteger.ZERO, 0L, 14, 1), new BigDecimal(BigInteger.ZERO, 0L, 15, 1) };
        ZERO = BigDecimal.zeroThroughTen[0];
        ONE = BigDecimal.zeroThroughTen[1];
        TEN = BigDecimal.zeroThroughTen[10];
        double10pow = new double[] { 1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18, 1.0E19, 1.0E20, 1.0E21, 1.0E22 };
        float10pow = new float[] { 1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 1.0E7f, 1.0E8f, 1.0E9f, 1.0E10f };
        LONG_TEN_POWERS_TABLE = new long[] { 1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L };
        BigDecimal.BIG_TEN_POWERS_TABLE = new BigInteger[] { BigInteger.ONE, BigInteger.valueOf(10L), BigInteger.valueOf(100L), BigInteger.valueOf(1000L), BigInteger.valueOf(10000L), BigInteger.valueOf(100000L), BigInteger.valueOf(1000000L), BigInteger.valueOf(10000000L), BigInteger.valueOf(100000000L), BigInteger.valueOf(1000000000L), BigInteger.valueOf(10000000000L), BigInteger.valueOf(100000000000L), BigInteger.valueOf(1000000000000L), BigInteger.valueOf(10000000000000L), BigInteger.valueOf(100000000000000L), BigInteger.valueOf(1000000000000000L), BigInteger.valueOf(10000000000000000L), BigInteger.valueOf(100000000000000000L), BigInteger.valueOf(1000000000000000000L) };
        BIG_TEN_POWERS_TABLE_INITLEN = BigDecimal.BIG_TEN_POWERS_TABLE.length;
        BIG_TEN_POWERS_TABLE_MAX = 16 * BigDecimal.BIG_TEN_POWERS_TABLE_INITLEN;
        THRESHOLDS_TABLE = new long[] { Long.MAX_VALUE, 922337203685477580L, 92233720368547758L, 9223372036854775L, 922337203685477L, 92233720368547L, 9223372036854L, 922337203685L, 92233720368L, 9223372036L, 922337203L, 92233720L, 9223372L, 922337L, 92233L, 9223L, 922L, 92L, 9L };
        LONGLONG_TEN_POWERS_TABLE = new long[][] { { 0L, -8446744073709551616L }, { 5L, 7766279631452241920L }, { 54L, 3875820019684212736L }, { 542L, 1864712049423024128L }, { 5421L, 200376420520689664L }, { 54210L, 2003764205206896640L }, { 542101L, 1590897978359414784L }, { 5421010L, -2537764290115403776L }, { 54210108L, -6930898827444486144L }, { 542101086L, 4477988020393345024L }, { 5421010862L, 7886392056514347008L }, { 54210108624L, 5076944270305263616L }, { 542101086242L, -4570789518076018688L }, { 5421010862427L, -8814407033341083648L }, { 54210108624275L, 4089650035136921600L }, { 542101086242752L, 4003012203950112768L }, { 5421010862427522L, 3136633892082024448L }, { 54210108624275221L, -5527149226598858752L }, { 542101086242752217L, 68739955140067328L }, { 5421010862427522170L, 687399551400673280L } };
    }
    
    private static class LongOverflow
    {
        private static final BigInteger LONGMIN;
        private static final BigInteger LONGMAX;
        
        public static void check(final BigDecimal bigDecimal) {
            final BigInteger access$000 = bigDecimal.inflated();
            if (access$000.compareTo(LongOverflow.LONGMIN) < 0 || access$000.compareTo(LongOverflow.LONGMAX) > 0) {
                throw new ArithmeticException("Overflow");
            }
        }
        
        static {
            LONGMIN = BigInteger.valueOf(Long.MIN_VALUE);
            LONGMAX = BigInteger.valueOf(Long.MAX_VALUE);
        }
    }
    
    static class StringBuilderHelper
    {
        final StringBuilder sb;
        final char[] cmpCharArray;
        static final char[] DIGIT_TENS;
        static final char[] DIGIT_ONES;
        
        StringBuilderHelper() {
            this.sb = new StringBuilder();
            this.cmpCharArray = new char[19];
        }
        
        StringBuilder getStringBuilder() {
            this.sb.setLength(0);
            return this.sb;
        }
        
        char[] getCompactCharArray() {
            return this.cmpCharArray;
        }
        
        int putIntCompact(long n) {
            assert n >= 0L;
            int length;
            long n2;
            int n3;
            for (length = this.cmpCharArray.length; n > 2147483647L; n = n2, this.cmpCharArray[--length] = StringBuilderHelper.DIGIT_ONES[n3], this.cmpCharArray[--length] = StringBuilderHelper.DIGIT_TENS[n3]) {
                n2 = n / 100L;
                n3 = (int)(n - n2 * 100L);
            }
            int i;
            int n4;
            int n5;
            for (i = (int)n; i >= 100; i = n4, this.cmpCharArray[--length] = StringBuilderHelper.DIGIT_ONES[n5], this.cmpCharArray[--length] = StringBuilderHelper.DIGIT_TENS[n5]) {
                n4 = i / 100;
                n5 = i - n4 * 100;
            }
            this.cmpCharArray[--length] = StringBuilderHelper.DIGIT_ONES[i];
            if (i >= 10) {
                this.cmpCharArray[--length] = StringBuilderHelper.DIGIT_TENS[i];
            }
            return length;
        }
        
        static {
            DIGIT_TENS = new char[] { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9' };
            DIGIT_ONES = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        }
    }
    
    private static class UnsafeHolder
    {
        private static final Unsafe unsafe;
        private static final long intCompactOffset;
        private static final long intValOffset;
        
        static void setIntCompactVolatile(final BigDecimal bigDecimal, final long n) {
            UnsafeHolder.unsafe.putLongVolatile(bigDecimal, UnsafeHolder.intCompactOffset, n);
        }
        
        static void setIntValVolatile(final BigDecimal bigDecimal, final BigInteger bigInteger) {
            UnsafeHolder.unsafe.putObjectVolatile(bigDecimal, UnsafeHolder.intValOffset, bigInteger);
        }
        
        static {
            try {
                unsafe = Unsafe.getUnsafe();
                intCompactOffset = UnsafeHolder.unsafe.objectFieldOffset(BigDecimal.class.getDeclaredField("intCompact"));
                intValOffset = UnsafeHolder.unsafe.objectFieldOffset(BigDecimal.class.getDeclaredField("intVal"));
            }
            catch (Exception ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
    }
}
