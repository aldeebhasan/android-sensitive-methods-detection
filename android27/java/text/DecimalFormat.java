package java.text;

import java.math.*;
import java.text.spi.*;
import java.util.spi.*;
import sun.util.locale.provider.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import java.io.*;

public class DecimalFormat extends NumberFormat
{
    private transient BigInteger bigIntegerMultiplier;
    private transient BigDecimal bigDecimalMultiplier;
    private static final int STATUS_INFINITE = 0;
    private static final int STATUS_POSITIVE = 1;
    private static final int STATUS_LENGTH = 2;
    private transient DigitList digitList;
    private String positivePrefix;
    private String positiveSuffix;
    private String negativePrefix;
    private String negativeSuffix;
    private String posPrefixPattern;
    private String posSuffixPattern;
    private String negPrefixPattern;
    private String negSuffixPattern;
    private int multiplier;
    private byte groupingSize;
    private boolean decimalSeparatorAlwaysShown;
    private boolean parseBigDecimal;
    private transient boolean isCurrencyFormat;
    private DecimalFormatSymbols symbols;
    private boolean useExponentialNotation;
    private transient FieldPosition[] positivePrefixFieldPositions;
    private transient FieldPosition[] positiveSuffixFieldPositions;
    private transient FieldPosition[] negativePrefixFieldPositions;
    private transient FieldPosition[] negativeSuffixFieldPositions;
    private byte minExponentDigits;
    private int maximumIntegerDigits;
    private int minimumIntegerDigits;
    private int maximumFractionDigits;
    private int minimumFractionDigits;
    private RoundingMode roundingMode;
    private transient boolean isFastPath;
    private transient boolean fastPathCheckNeeded;
    private transient FastPathData fastPathData;
    static final int currentSerialVersion = 4;
    private int serialVersionOnStream;
    private static final double MAX_INT_AS_DOUBLE = 2.147483647E9;
    private static final char PATTERN_ZERO_DIGIT = '0';
    private static final char PATTERN_GROUPING_SEPARATOR = ',';
    private static final char PATTERN_DECIMAL_SEPARATOR = '.';
    private static final char PATTERN_PER_MILLE = '\u2030';
    private static final char PATTERN_PERCENT = '%';
    private static final char PATTERN_DIGIT = '#';
    private static final char PATTERN_SEPARATOR = ';';
    private static final String PATTERN_EXPONENT = "E";
    private static final char PATTERN_MINUS = '-';
    private static final char CURRENCY_SIGN = '¤';
    private static final char QUOTE = '\'';
    private static FieldPosition[] EmptyFieldPositionArray;
    static final int DOUBLE_INTEGER_DIGITS = 309;
    static final int DOUBLE_FRACTION_DIGITS = 340;
    static final int MAXIMUM_INTEGER_DIGITS = Integer.MAX_VALUE;
    static final int MAXIMUM_FRACTION_DIGITS = Integer.MAX_VALUE;
    static final long serialVersionUID = 864413376551465018L;
    
    public DecimalFormat() {
        this.digitList = new DigitList();
        this.positivePrefix = "";
        this.positiveSuffix = "";
        this.negativePrefix = "-";
        this.negativeSuffix = "";
        this.multiplier = 1;
        this.groupingSize = 3;
        this.decimalSeparatorAlwaysShown = false;
        this.parseBigDecimal = false;
        this.isCurrencyFormat = false;
        this.symbols = null;
        this.maximumIntegerDigits = super.getMaximumIntegerDigits();
        this.minimumIntegerDigits = super.getMinimumIntegerDigits();
        this.maximumFractionDigits = super.getMaximumFractionDigits();
        this.minimumFractionDigits = super.getMinimumFractionDigits();
        this.roundingMode = RoundingMode.HALF_EVEN;
        this.isFastPath = false;
        this.fastPathCheckNeeded = true;
        this.serialVersionOnStream = 4;
        final Locale default1 = Locale.getDefault(Locale.Category.FORMAT);
        LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter(NumberFormatProvider.class, default1);
        if (!(localeProviderAdapter instanceof ResourceBundleBasedAdapter)) {
            localeProviderAdapter = LocaleProviderAdapter.getResourceBundleBased();
        }
        final String[] numberPatterns = localeProviderAdapter.getLocaleResources(default1).getNumberPatterns();
        this.symbols = DecimalFormatSymbols.getInstance(default1);
        this.applyPattern(numberPatterns[0], false);
    }
    
    public DecimalFormat(final String s) {
        this.digitList = new DigitList();
        this.positivePrefix = "";
        this.positiveSuffix = "";
        this.negativePrefix = "-";
        this.negativeSuffix = "";
        this.multiplier = 1;
        this.groupingSize = 3;
        this.decimalSeparatorAlwaysShown = false;
        this.parseBigDecimal = false;
        this.isCurrencyFormat = false;
        this.symbols = null;
        this.maximumIntegerDigits = super.getMaximumIntegerDigits();
        this.minimumIntegerDigits = super.getMinimumIntegerDigits();
        this.maximumFractionDigits = super.getMaximumFractionDigits();
        this.minimumFractionDigits = super.getMinimumFractionDigits();
        this.roundingMode = RoundingMode.HALF_EVEN;
        this.isFastPath = false;
        this.fastPathCheckNeeded = true;
        this.serialVersionOnStream = 4;
        this.symbols = DecimalFormatSymbols.getInstance(Locale.getDefault(Locale.Category.FORMAT));
        this.applyPattern(s, false);
    }
    
    public DecimalFormat(final String s, final DecimalFormatSymbols decimalFormatSymbols) {
        this.digitList = new DigitList();
        this.positivePrefix = "";
        this.positiveSuffix = "";
        this.negativePrefix = "-";
        this.negativeSuffix = "";
        this.multiplier = 1;
        this.groupingSize = 3;
        this.decimalSeparatorAlwaysShown = false;
        this.parseBigDecimal = false;
        this.isCurrencyFormat = false;
        this.symbols = null;
        this.maximumIntegerDigits = super.getMaximumIntegerDigits();
        this.minimumIntegerDigits = super.getMinimumIntegerDigits();
        this.maximumFractionDigits = super.getMaximumFractionDigits();
        this.minimumFractionDigits = super.getMinimumFractionDigits();
        this.roundingMode = RoundingMode.HALF_EVEN;
        this.isFastPath = false;
        this.fastPathCheckNeeded = true;
        this.serialVersionOnStream = 4;
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.applyPattern(s, false);
    }
    
    @Override
    public final StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Long || o instanceof Integer || o instanceof Short || o instanceof Byte || o instanceof AtomicInteger || o instanceof AtomicLong || (o instanceof BigInteger && ((BigInteger)o).bitLength() < 64)) {
            return this.format(((Number)o).longValue(), sb, fieldPosition);
        }
        if (o instanceof BigDecimal) {
            return this.format((BigDecimal)o, sb, fieldPosition);
        }
        if (o instanceof BigInteger) {
            return this.format((BigInteger)o, sb, fieldPosition);
        }
        if (o instanceof Number) {
            return this.format(((Number)o).doubleValue(), sb, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Number");
    }
    
    @Override
    public StringBuffer format(final double n, final StringBuffer sb, final FieldPosition fieldPosition) {
        boolean b = false;
        if (fieldPosition == DontCareFieldPosition.INSTANCE) {
            b = true;
        }
        else {
            fieldPosition.setBeginIndex(0);
            fieldPosition.setEndIndex(0);
        }
        if (b) {
            final String fastFormat = this.fastFormat(n);
            if (fastFormat != null) {
                sb.append(fastFormat);
                return sb;
            }
        }
        return this.format(n, sb, fieldPosition.getFieldDelegate());
    }
    
    private StringBuffer format(double n, final StringBuffer sb, final FieldDelegate fieldDelegate) {
        if (Double.isNaN(n) || (Double.isInfinite(n) && this.multiplier == 0)) {
            final int length = sb.length();
            sb.append(this.symbols.getNaN());
            fieldDelegate.formatted(0, Field.INTEGER, Field.INTEGER, length, sb.length(), sb);
            return sb;
        }
        final boolean b = (n < 0.0 || (n == 0.0 && 1.0 / n < 0.0)) ^ this.multiplier < 0;
        if (this.multiplier != 1) {
            n *= this.multiplier;
        }
        if (Double.isInfinite(n)) {
            if (b) {
                this.append(sb, this.negativePrefix, fieldDelegate, this.getNegativePrefixFieldPositions(), Field.SIGN);
            }
            else {
                this.append(sb, this.positivePrefix, fieldDelegate, this.getPositivePrefixFieldPositions(), Field.SIGN);
            }
            final int length2 = sb.length();
            sb.append(this.symbols.getInfinity());
            fieldDelegate.formatted(0, Field.INTEGER, Field.INTEGER, length2, sb.length(), sb);
            if (b) {
                this.append(sb, this.negativeSuffix, fieldDelegate, this.getNegativeSuffixFieldPositions(), Field.SIGN);
            }
            else {
                this.append(sb, this.positiveSuffix, fieldDelegate, this.getPositiveSuffixFieldPositions(), Field.SIGN);
            }
            return sb;
        }
        if (b) {
            n = -n;
        }
        assert n >= 0.0 && !Double.isInfinite(n);
        synchronized (this.digitList) {
            final int maximumIntegerDigits = super.getMaximumIntegerDigits();
            final int minimumIntegerDigits = super.getMinimumIntegerDigits();
            final int maximumFractionDigits = super.getMaximumFractionDigits();
            final int minimumFractionDigits = super.getMinimumFractionDigits();
            this.digitList.set(b, n, this.useExponentialNotation ? (maximumIntegerDigits + maximumFractionDigits) : maximumFractionDigits, !this.useExponentialNotation);
            return this.subformat(sb, fieldDelegate, b, false, maximumIntegerDigits, minimumIntegerDigits, maximumFractionDigits, minimumFractionDigits);
        }
    }
    
    @Override
    public StringBuffer format(final long n, final StringBuffer sb, final FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        return this.format(n, sb, fieldPosition.getFieldDelegate());
    }
    
    private StringBuffer format(long n, final StringBuffer sb, final FieldDelegate fieldDelegate) {
        boolean b = n < 0L;
        if (b) {
            n = -n;
        }
        int n2 = 0;
        if (n < 0L) {
            if (this.multiplier != 0) {
                n2 = 1;
            }
        }
        else if (this.multiplier != 1 && this.multiplier != 0) {
            long n3 = Long.MAX_VALUE / this.multiplier;
            if (n3 < 0L) {
                n3 = -n3;
            }
            n2 = ((n > n3) ? 1 : 0);
        }
        if (n2 != 0) {
            if (b) {
                n = -n;
            }
            return this.format(BigInteger.valueOf(n), sb, fieldDelegate, true);
        }
        n *= this.multiplier;
        if (n == 0L) {
            b = false;
        }
        else if (this.multiplier < 0) {
            n = -n;
            b = !b;
        }
        synchronized (this.digitList) {
            final int maximumIntegerDigits = super.getMaximumIntegerDigits();
            final int minimumIntegerDigits = super.getMinimumIntegerDigits();
            final int maximumFractionDigits = super.getMaximumFractionDigits();
            final int minimumFractionDigits = super.getMinimumFractionDigits();
            this.digitList.set(b, n, this.useExponentialNotation ? (maximumIntegerDigits + maximumFractionDigits) : 0);
            return this.subformat(sb, fieldDelegate, b, true, maximumIntegerDigits, minimumIntegerDigits, maximumFractionDigits, minimumFractionDigits);
        }
    }
    
    private StringBuffer format(final BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        return this.format(bigDecimal, sb, fieldPosition.getFieldDelegate());
    }
    
    private StringBuffer format(BigDecimal bigDecimal, final StringBuffer sb, final FieldDelegate fieldDelegate) {
        if (this.multiplier != 1) {
            bigDecimal = bigDecimal.multiply(this.getBigDecimalMultiplier());
        }
        final boolean b = bigDecimal.signum() == -1;
        if (b) {
            bigDecimal = bigDecimal.negate();
        }
        synchronized (this.digitList) {
            final int maximumIntegerDigits = this.getMaximumIntegerDigits();
            final int minimumIntegerDigits = this.getMinimumIntegerDigits();
            final int maximumFractionDigits = this.getMaximumFractionDigits();
            final int minimumFractionDigits = this.getMinimumFractionDigits();
            final int n = maximumIntegerDigits + maximumFractionDigits;
            this.digitList.set(b, bigDecimal, this.useExponentialNotation ? ((n < 0) ? Integer.MAX_VALUE : n) : maximumFractionDigits, !this.useExponentialNotation);
            return this.subformat(sb, fieldDelegate, b, false, maximumIntegerDigits, minimumIntegerDigits, maximumFractionDigits, minimumFractionDigits);
        }
    }
    
    private StringBuffer format(final BigInteger bigInteger, final StringBuffer sb, final FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        return this.format(bigInteger, sb, fieldPosition.getFieldDelegate(), false);
    }
    
    private StringBuffer format(BigInteger bigInteger, final StringBuffer sb, final FieldDelegate fieldDelegate, final boolean b) {
        if (this.multiplier != 1) {
            bigInteger = bigInteger.multiply(this.getBigIntegerMultiplier());
        }
        final boolean b2 = bigInteger.signum() == -1;
        if (b2) {
            bigInteger = bigInteger.negate();
        }
        synchronized (this.digitList) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            if (b) {
                n = super.getMaximumIntegerDigits();
                n2 = super.getMinimumIntegerDigits();
                n3 = super.getMaximumFractionDigits();
                n4 = super.getMinimumFractionDigits();
                n5 = n + n3;
            }
            else {
                n = this.getMaximumIntegerDigits();
                n2 = this.getMinimumIntegerDigits();
                n3 = this.getMaximumFractionDigits();
                n4 = this.getMinimumFractionDigits();
                n5 = n + n3;
                if (n5 < 0) {
                    n5 = Integer.MAX_VALUE;
                }
            }
            this.digitList.set(b2, bigInteger, this.useExponentialNotation ? n5 : false);
            return this.subformat(sb, fieldDelegate, b2, true, n, n2, n3, n4);
        }
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        final CharacterIteratorFieldDelegate characterIteratorFieldDelegate = new CharacterIteratorFieldDelegate();
        final StringBuffer sb = new StringBuffer();
        if (o instanceof Double || o instanceof Float) {
            this.format(((Number)o).doubleValue(), sb, characterIteratorFieldDelegate);
        }
        else if (o instanceof Long || o instanceof Integer || o instanceof Short || o instanceof Byte || o instanceof AtomicInteger || o instanceof AtomicLong) {
            this.format(((Number)o).longValue(), sb, characterIteratorFieldDelegate);
        }
        else if (o instanceof BigDecimal) {
            this.format((BigDecimal)o, sb, characterIteratorFieldDelegate);
        }
        else if (o instanceof BigInteger) {
            this.format((BigInteger)o, sb, characterIteratorFieldDelegate, false);
        }
        else {
            if (o == null) {
                throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
            }
            throw new IllegalArgumentException("Cannot format given Object as a Number");
        }
        return characterIteratorFieldDelegate.getIterator(sb.toString());
    }
    
    private boolean checkAndSetFastPathStatus() {
        final boolean isFastPath = this.isFastPath;
        if (this.roundingMode == RoundingMode.HALF_EVEN && this.isGroupingUsed() && this.groupingSize == 3 && this.multiplier == 1 && !this.decimalSeparatorAlwaysShown && !this.useExponentialNotation) {
            this.isFastPath = (this.minimumIntegerDigits == 1 && this.maximumIntegerDigits >= 10);
            if (this.isFastPath) {
                if (this.isCurrencyFormat) {
                    if (this.minimumFractionDigits != 2 || this.maximumFractionDigits != 2) {
                        this.isFastPath = false;
                    }
                }
                else if (this.minimumFractionDigits != 0 || this.maximumFractionDigits != 3) {
                    this.isFastPath = false;
                }
            }
        }
        else {
            this.isFastPath = false;
        }
        this.resetFastPathData(isFastPath);
        this.fastPathCheckNeeded = false;
        return true;
    }
    
    private void resetFastPathData(final boolean b) {
        if (this.isFastPath) {
            if (this.fastPathData == null) {
                this.fastPathData = new FastPathData();
            }
            this.fastPathData.zeroDelta = this.symbols.getZeroDigit() - '0';
            this.fastPathData.groupingChar = this.symbols.getGroupingSeparator();
            this.fastPathData.fractionalMaxIntBound = (this.isCurrencyFormat ? 99 : 999);
            this.fastPathData.fractionalScaleFactor = (this.isCurrencyFormat ? 100.0 : 1000.0);
            this.fastPathData.positiveAffixesRequired = (this.positivePrefix.length() != 0 || this.positiveSuffix.length() != 0);
            this.fastPathData.negativeAffixesRequired = (this.negativePrefix.length() != 0 || this.negativeSuffix.length() != 0);
            final int n = 10;
            final int n2 = 3;
            this.fastPathData.fastPathContainer = new char[Math.max(this.positivePrefix.length(), this.negativePrefix.length()) + n + n2 + 1 + this.maximumFractionDigits + Math.max(this.positiveSuffix.length(), this.negativeSuffix.length())];
            this.fastPathData.charsPositiveSuffix = this.positiveSuffix.toCharArray();
            this.fastPathData.charsNegativeSuffix = this.negativeSuffix.toCharArray();
            this.fastPathData.charsPositivePrefix = this.positivePrefix.toCharArray();
            this.fastPathData.charsNegativePrefix = this.negativePrefix.toCharArray();
            final int n3 = n + n2 + Math.max(this.positivePrefix.length(), this.negativePrefix.length());
            this.fastPathData.integralLastIndex = n3 - 1;
            this.fastPathData.fractionalFirstIndex = n3 + 1;
            this.fastPathData.fastPathContainer[n3] = (this.isCurrencyFormat ? this.symbols.getMonetaryDecimalSeparator() : this.symbols.getDecimalSeparator());
        }
        else if (b) {
            this.fastPathData.fastPathContainer = null;
            this.fastPathData.charsPositiveSuffix = null;
            this.fastPathData.charsNegativeSuffix = null;
            this.fastPathData.charsPositivePrefix = null;
            this.fastPathData.charsNegativePrefix = null;
        }
    }
    
    private boolean exactRoundUp(final double n, final int n2) {
        double n3;
        double n4;
        double n5;
        if (this.isCurrencyFormat) {
            n3 = n * 128.0;
            n4 = -(n * 32.0);
            n5 = n * 4.0;
        }
        else {
            n3 = n * 1024.0;
            n4 = -(n * 16.0);
            n5 = -(n * 8.0);
        }
        assert -n4 >= Math.abs(n5);
        final double n6 = n4 + n5;
        final double n7 = n5 - (n6 - n4);
        final double n8 = n6;
        final double n9 = n7;
        assert n3 >= Math.abs(n8);
        final double n10 = n3 + n8;
        final double n11 = n8 - (n10 - n3);
        final double n12 = n10;
        final double n13 = n9 + n11;
        assert n12 >= Math.abs(n13);
        final double n14 = n13 - (n12 + n13 - n12);
        return n14 > 0.0 || (n14 >= 0.0 && (n2 & 0x1) != 0x0);
    }
    
    private void collectIntegralDigits(int i, final char[] array, final int n) {
        int firstUsedIndex;
        int n2;
        int n3;
        for (firstUsedIndex = n; i > 999; i = n2, array[firstUsedIndex--] = DigitArrays.DigitOnes1000[n3], array[firstUsedIndex--] = DigitArrays.DigitTens1000[n3], array[firstUsedIndex--] = DigitArrays.DigitHundreds1000[n3], array[firstUsedIndex--] = this.fastPathData.groupingChar) {
            n2 = i / 1000;
            n3 = i - (n2 << 10) + (n2 << 4) + (n2 << 3);
        }
        array[firstUsedIndex] = DigitArrays.DigitOnes1000[i];
        if (i > 9) {
            array[--firstUsedIndex] = DigitArrays.DigitTens1000[i];
            if (i > 99) {
                array[--firstUsedIndex] = DigitArrays.DigitHundreds1000[i];
            }
        }
        this.fastPathData.firstUsedIndex = firstUsedIndex;
    }
    
    private void collectFractionalDigits(final int n, final char[] array, final int n2) {
        int lastFreeIndex = n2;
        final char c = DigitArrays.DigitOnes1000[n];
        final char c2 = DigitArrays.DigitTens1000[n];
        if (this.isCurrencyFormat) {
            array[lastFreeIndex++] = c2;
            array[lastFreeIndex++] = c;
        }
        else if (n != 0) {
            array[lastFreeIndex++] = DigitArrays.DigitHundreds1000[n];
            if (c != '0') {
                array[lastFreeIndex++] = c2;
                array[lastFreeIndex++] = c;
            }
            else if (c2 != '0') {
                array[lastFreeIndex++] = c2;
            }
        }
        else {
            --lastFreeIndex;
        }
        this.fastPathData.lastFreeIndex = lastFreeIndex;
    }
    
    private void addAffixes(final char[] array, final char[] array2, final char[] array3) {
        final int length = array2.length;
        final int length2 = array3.length;
        if (length != 0) {
            this.prependPrefix(array2, length, array);
        }
        if (length2 != 0) {
            this.appendSuffix(array3, length2, array);
        }
    }
    
    private void prependPrefix(final char[] array, final int n, final char[] array2) {
        final FastPathData fastPathData = this.fastPathData;
        fastPathData.firstUsedIndex -= n;
        final int firstUsedIndex = this.fastPathData.firstUsedIndex;
        if (n == 1) {
            array2[firstUsedIndex] = array[0];
        }
        else if (n <= 4) {
            int n2 = firstUsedIndex;
            int n3 = n2 + n - 1;
            final int n4 = n - 1;
            array2[n2] = array[0];
            array2[n3] = array[n4];
            if (n > 2) {
                array2[++n2] = array[1];
            }
            if (n == 4) {
                array2[--n3] = array[2];
            }
        }
        else {
            System.arraycopy(array, 0, array2, firstUsedIndex, n);
        }
    }
    
    private void appendSuffix(final char[] array, final int n, final char[] array2) {
        final int lastFreeIndex = this.fastPathData.lastFreeIndex;
        if (n == 1) {
            array2[lastFreeIndex] = array[0];
        }
        else if (n <= 4) {
            int n2 = lastFreeIndex;
            int n3 = n2 + n - 1;
            final int n4 = n - 1;
            array2[n2] = array[0];
            array2[n3] = array[n4];
            if (n > 2) {
                array2[++n2] = array[1];
            }
            if (n == 4) {
                array2[--n3] = array[2];
            }
        }
        else {
            System.arraycopy(array, 0, array2, lastFreeIndex, n);
        }
        final FastPathData fastPathData = this.fastPathData;
        fastPathData.lastFreeIndex += n;
    }
    
    private void localizeDigits(final char[] array) {
        int n = this.fastPathData.lastFreeIndex - this.fastPathData.fractionalFirstIndex;
        if (n < 0) {
            n = this.groupingSize;
        }
        for (int i = this.fastPathData.lastFreeIndex - 1; i >= this.fastPathData.firstUsedIndex; --i) {
            if (n != 0) {
                final int n2 = i;
                array[n2] += (char)this.fastPathData.zeroDelta;
                --n;
            }
            else {
                n = this.groupingSize;
            }
        }
    }
    
    private void fastDoubleFormat(final double n, final boolean b) {
        final char[] fastPathContainer = this.fastPathData.fastPathContainer;
        int n2 = (int)n;
        final double n3 = n - n2;
        final double n4 = n3 * this.fastPathData.fractionalScaleFactor;
        int n5 = (int)n4;
        final double n6 = n4 - n5;
        if (n6 >= 0.5 && (n6 != 0.5 || this.exactRoundUp(n3, n5))) {
            if (n5 < this.fastPathData.fractionalMaxIntBound) {
                ++n5;
            }
            else {
                n5 = 0;
                ++n2;
            }
        }
        this.collectFractionalDigits(n5, fastPathContainer, this.fastPathData.fractionalFirstIndex);
        this.collectIntegralDigits(n2, fastPathContainer, this.fastPathData.integralLastIndex);
        if (this.fastPathData.zeroDelta != 0) {
            this.localizeDigits(fastPathContainer);
        }
        if (b) {
            if (this.fastPathData.negativeAffixesRequired) {
                this.addAffixes(fastPathContainer, this.fastPathData.charsNegativePrefix, this.fastPathData.charsNegativeSuffix);
            }
        }
        else if (this.fastPathData.positiveAffixesRequired) {
            this.addAffixes(fastPathContainer, this.fastPathData.charsPositivePrefix, this.fastPathData.charsPositiveSuffix);
        }
    }
    
    @Override
    String fastFormat(double n) {
        boolean checkAndSetFastPathStatus = false;
        if (this.fastPathCheckNeeded) {
            checkAndSetFastPathStatus = this.checkAndSetFastPathStatus();
        }
        if (!this.isFastPath) {
            return null;
        }
        if (!Double.isFinite(n)) {
            return null;
        }
        boolean b = false;
        if (n < 0.0) {
            b = true;
            n = -n;
        }
        else if (n == 0.0) {
            b = (Math.copySign(1.0, n) == -1.0);
            n = 0.0;
        }
        if (n > 2.147483647E9) {
            return null;
        }
        if (!checkAndSetFastPathStatus) {
            this.resetFastPathData(this.isFastPath);
        }
        this.fastDoubleFormat(n, b);
        return new String(this.fastPathData.fastPathContainer, this.fastPathData.firstUsedIndex, this.fastPathData.lastFreeIndex - this.fastPathData.firstUsedIndex);
    }
    
    private StringBuffer subformat(final StringBuffer sb, final FieldDelegate fieldDelegate, final boolean b, final boolean b2, final int n, final int n2, final int n3, final int n4) {
        final char zeroDigit = this.symbols.getZeroDigit();
        final char c = (char)(zeroDigit - '0');
        final char groupingSeparator = this.symbols.getGroupingSeparator();
        final char c2 = this.isCurrencyFormat ? this.symbols.getMonetaryDecimalSeparator() : this.symbols.getDecimalSeparator();
        if (this.digitList.isZero()) {
            this.digitList.decimalAt = 0;
        }
        if (b) {
            this.append(sb, this.negativePrefix, fieldDelegate, this.getNegativePrefixFieldPositions(), Field.SIGN);
        }
        else {
            this.append(sb, this.positivePrefix, fieldDelegate, this.getPositivePrefixFieldPositions(), Field.SIGN);
        }
        if (this.useExponentialNotation) {
            final int length = sb.length();
            int n5 = -1;
            int n6 = -1;
            final int decimalAt = this.digitList.decimalAt;
            int n7 = n2;
            int n8;
            if (n > 1 && n > n2) {
                if (decimalAt >= 1) {
                    n8 = (decimalAt - 1) / n * n;
                }
                else {
                    n8 = (decimalAt - n) / n * n;
                }
                n7 = 1;
            }
            else {
                n8 = decimalAt - n7;
            }
            int n9 = n2 + n4;
            if (n9 < 0) {
                n9 = Integer.MAX_VALUE;
            }
            final int n10 = this.digitList.isZero() ? n7 : (this.digitList.decimalAt - n8);
            if (n9 < n10) {
                n9 = n10;
            }
            int count = this.digitList.count;
            if (n9 > count) {
                count = n9;
            }
            boolean b3 = false;
            for (int i = 0; i < count; ++i) {
                if (i == n10) {
                    n5 = sb.length();
                    sb.append(c2);
                    b3 = true;
                    n6 = sb.length();
                }
                sb.append((i < this.digitList.count) ? ((char)(this.digitList.digits[i] + c)) : zeroDigit);
            }
            if (this.decimalSeparatorAlwaysShown && count == n10) {
                n5 = sb.length();
                sb.append(c2);
                b3 = true;
                n6 = sb.length();
            }
            if (n5 == -1) {
                n5 = sb.length();
            }
            fieldDelegate.formatted(0, Field.INTEGER, Field.INTEGER, length, n5, sb);
            if (b3) {
                fieldDelegate.formatted(Field.DECIMAL_SEPARATOR, Field.DECIMAL_SEPARATOR, n5, n6, sb);
            }
            if (n6 == -1) {
                n6 = sb.length();
            }
            fieldDelegate.formatted(1, Field.FRACTION, Field.FRACTION, n6, sb.length(), sb);
            final int length2 = sb.length();
            sb.append(this.symbols.getExponentSeparator());
            fieldDelegate.formatted(Field.EXPONENT_SYMBOL, Field.EXPONENT_SYMBOL, length2, sb.length(), sb);
            if (this.digitList.isZero()) {
                n8 = 0;
            }
            final boolean b4 = n8 < 0;
            if (b4) {
                n8 = -n8;
                final int length3 = sb.length();
                sb.append(this.symbols.getMinusSign());
                fieldDelegate.formatted(Field.EXPONENT_SIGN, Field.EXPONENT_SIGN, length3, sb.length(), sb);
            }
            this.digitList.set(b4, n8);
            final int length4 = sb.length();
            for (int j = this.digitList.decimalAt; j < this.minExponentDigits; ++j) {
                sb.append(zeroDigit);
            }
            for (int k = 0; k < this.digitList.decimalAt; ++k) {
                sb.append((k < this.digitList.count) ? ((char)(this.digitList.digits[k] + c)) : zeroDigit);
            }
            fieldDelegate.formatted(Field.EXPONENT, Field.EXPONENT, length4, sb.length(), sb);
        }
        else {
            final int length5 = sb.length();
            int decimalAt2 = n2;
            int n11 = 0;
            if (this.digitList.decimalAt > 0 && decimalAt2 < this.digitList.decimalAt) {
                decimalAt2 = this.digitList.decimalAt;
            }
            if (decimalAt2 > n) {
                decimalAt2 = n;
                n11 = this.digitList.decimalAt - decimalAt2;
            }
            final int length6 = sb.length();
            for (int l = decimalAt2 - 1; l >= 0; --l) {
                if (l < this.digitList.decimalAt && n11 < this.digitList.count) {
                    sb.append((char)(this.digitList.digits[n11++] + c));
                }
                else {
                    sb.append(zeroDigit);
                }
                if (this.isGroupingUsed() && l > 0 && this.groupingSize != 0 && l % this.groupingSize == 0) {
                    final int length7 = sb.length();
                    sb.append(groupingSeparator);
                    fieldDelegate.formatted(Field.GROUPING_SEPARATOR, Field.GROUPING_SEPARATOR, length7, sb.length(), sb);
                }
            }
            final boolean b5 = n4 > 0 || (!b2 && n11 < this.digitList.count);
            if (!b5 && sb.length() == length6) {
                sb.append(zeroDigit);
            }
            fieldDelegate.formatted(0, Field.INTEGER, Field.INTEGER, length5, sb.length(), sb);
            final int length8 = sb.length();
            if (this.decimalSeparatorAlwaysShown || b5) {
                sb.append(c2);
            }
            if (length8 != sb.length()) {
                fieldDelegate.formatted(Field.DECIMAL_SEPARATOR, Field.DECIMAL_SEPARATOR, length8, sb.length(), sb);
            }
            final int length9 = sb.length();
            for (int n12 = 0; n12 < n3; ++n12) {
                if (n12 >= n4) {
                    if (b2) {
                        break;
                    }
                    if (n11 >= this.digitList.count) {
                        break;
                    }
                }
                if (-1 - n12 > this.digitList.decimalAt - 1) {
                    sb.append(zeroDigit);
                }
                else if (!b2 && n11 < this.digitList.count) {
                    sb.append((char)(this.digitList.digits[n11++] + c));
                }
                else {
                    sb.append(zeroDigit);
                }
            }
            fieldDelegate.formatted(1, Field.FRACTION, Field.FRACTION, length9, sb.length(), sb);
        }
        if (b) {
            this.append(sb, this.negativeSuffix, fieldDelegate, this.getNegativeSuffixFieldPositions(), Field.SIGN);
        }
        else {
            this.append(sb, this.positiveSuffix, fieldDelegate, this.getPositiveSuffixFieldPositions(), Field.SIGN);
        }
        return sb;
    }
    
    private void append(final StringBuffer sb, final String s, final FieldDelegate fieldDelegate, final FieldPosition[] array, final Format.Field field) {
        final int length = sb.length();
        if (s.length() > 0) {
            sb.append(s);
            for (int i = 0; i < array.length; ++i) {
                final FieldPosition fieldPosition = array[i];
                Format.Field fieldAttribute = fieldPosition.getFieldAttribute();
                if (fieldAttribute == Field.SIGN) {
                    fieldAttribute = field;
                }
                fieldDelegate.formatted(fieldAttribute, fieldAttribute, length + fieldPosition.getBeginIndex(), length + fieldPosition.getEndIndex(), sb);
            }
        }
    }
    
    @Override
    public Number parse(final String s, final ParsePosition parsePosition) {
        if (s.regionMatches(parsePosition.index, this.symbols.getNaN(), 0, this.symbols.getNaN().length())) {
            parsePosition.index += this.symbols.getNaN().length();
            return new Double(Double.NaN);
        }
        final boolean[] array = new boolean[2];
        if (!this.subparse(s, parsePosition, this.positivePrefix, this.negativePrefix, this.digitList, false, array)) {
            return null;
        }
        if (array[0]) {
            if (array[1] == this.multiplier >= 0) {
                return new Double(Double.POSITIVE_INFINITY);
            }
            return new Double(Double.NEGATIVE_INFINITY);
        }
        else if (this.multiplier == 0) {
            if (this.digitList.isZero()) {
                return new Double(Double.NaN);
            }
            if (array[1]) {
                return new Double(Double.POSITIVE_INFINITY);
            }
            return new Double(Double.NEGATIVE_INFINITY);
        }
        else {
            if (this.isParseBigDecimal()) {
                BigDecimal bigDecimal = this.digitList.getBigDecimal();
                if (this.multiplier != 1) {
                    try {
                        bigDecimal = bigDecimal.divide(this.getBigDecimalMultiplier());
                    }
                    catch (ArithmeticException ex) {
                        bigDecimal = bigDecimal.divide(this.getBigDecimalMultiplier(), this.roundingMode);
                    }
                }
                if (!array[1]) {
                    bigDecimal = bigDecimal.negate();
                }
                return bigDecimal;
            }
            int n = 1;
            boolean b = false;
            double double1 = 0.0;
            long long1 = 0L;
            if (this.digitList.fitsIntoLong(array[1], this.isParseIntegerOnly())) {
                n = 0;
                long1 = this.digitList.getLong();
                if (long1 < 0L) {
                    b = true;
                }
            }
            else {
                double1 = this.digitList.getDouble();
            }
            if (this.multiplier != 1) {
                if (n != 0) {
                    double1 /= this.multiplier;
                }
                else if (long1 % this.multiplier == 0L) {
                    long1 /= this.multiplier;
                }
                else {
                    double1 = long1 / this.multiplier;
                    n = 1;
                }
            }
            if (!array[1] && !b) {
                double1 = -double1;
                long1 = -long1;
            }
            if (this.multiplier != 1 && n != 0) {
                long1 = (long)double1;
                n = (((double1 != long1 || (double1 == 0.0 && 1.0 / double1 < 0.0)) && !this.isParseIntegerOnly()) ? 1 : 0);
            }
            return (n != 0) ? new Double(double1) : new Long(long1);
        }
    }
    
    private BigInteger getBigIntegerMultiplier() {
        if (this.bigIntegerMultiplier == null) {
            this.bigIntegerMultiplier = BigInteger.valueOf(this.multiplier);
        }
        return this.bigIntegerMultiplier;
    }
    
    private BigDecimal getBigDecimalMultiplier() {
        if (this.bigDecimalMultiplier == null) {
            this.bigDecimalMultiplier = new BigDecimal(this.multiplier);
        }
        return this.bigDecimalMultiplier;
    }
    
    private final boolean subparse(final String s, final ParsePosition parsePosition, final String s2, final String s3, final DigitList list, final boolean b, final boolean[] array) {
        final int index = parsePosition.index;
        final int index2 = parsePosition.index;
        boolean b2 = s.regionMatches(index, s2, 0, s2.length());
        boolean b3 = s.regionMatches(index, s3, 0, s3.length());
        if (b2 && b3) {
            if (s2.length() > s3.length()) {
                b3 = false;
            }
            else if (s2.length() < s3.length()) {
                b2 = false;
            }
        }
        int i;
        if (b2) {
            i = index + s2.length();
        }
        else {
            if (!b3) {
                parsePosition.errorIndex = index;
                return false;
            }
            i = index + s3.length();
        }
        array[0] = false;
        if (!b && s.regionMatches(i, this.symbols.getInfinity(), 0, this.symbols.getInfinity().length())) {
            i += this.symbols.getInfinity().length();
            array[0] = true;
        }
        else {
            final boolean b4 = false;
            list.count = (b4 ? 1 : 0);
            list.decimalAt = (b4 ? 1 : 0);
            final char zeroDigit = this.symbols.getZeroDigit();
            final char c = this.isCurrencyFormat ? this.symbols.getMonetaryDecimalSeparator() : this.symbols.getDecimalSeparator();
            final char groupingSeparator = this.symbols.getGroupingSeparator();
            final String exponentSeparator = this.symbols.getExponentSeparator();
            int n = 0;
            final boolean b5 = false;
            boolean b6 = false;
            int n2 = 0;
            int n3 = 0;
            int n4 = -1;
            while (i < s.length()) {
                final char char1 = s.charAt(i);
                int digit = char1 - zeroDigit;
                if (digit < 0 || digit > 9) {
                    digit = Character.digit(char1, 10);
                }
                if (digit == 0) {
                    n4 = -1;
                    b6 = true;
                    if (list.count == 0) {
                        if (n != 0) {
                            --list.decimalAt;
                        }
                    }
                    else {
                        ++n3;
                        list.append((char)(digit + 48));
                    }
                }
                else if (digit > 0 && digit <= 9) {
                    b6 = true;
                    ++n3;
                    list.append((char)(digit + 48));
                    n4 = -1;
                }
                else if (!b && char1 == c) {
                    if (this.isParseIntegerOnly()) {
                        break;
                    }
                    if (n != 0) {
                        break;
                    }
                    list.decimalAt = n3;
                    n = 1;
                }
                else if (!b && char1 == groupingSeparator && this.isGroupingUsed()) {
                    if (n != 0) {
                        break;
                    }
                    n4 = i;
                }
                else {
                    if (b || !s.regionMatches(i, exponentSeparator, 0, exponentSeparator.length()) || b5) {
                        break;
                    }
                    final ParsePosition parsePosition2 = new ParsePosition(i + exponentSeparator.length());
                    final boolean[] array2 = new boolean[2];
                    final DigitList list2 = new DigitList();
                    if (this.subparse(s, parsePosition2, "", Character.toString(this.symbols.getMinusSign()), list2, true, array2) && list2.fitsIntoLong(array2[1], true)) {
                        i = parsePosition2.index;
                        n2 = (int)list2.getLong();
                        if (!array2[1]) {
                            n2 = -n2;
                        }
                        break;
                    }
                    break;
                }
                ++i;
            }
            if (n4 != -1) {
                i = n4;
            }
            if (n == 0) {
                list.decimalAt = n3;
            }
            list.decimalAt += n2;
            if (!b6 && n3 == 0) {
                parsePosition.index = index2;
                parsePosition.errorIndex = index2;
                return false;
            }
        }
        if (!b) {
            if (b2) {
                b2 = s.regionMatches(i, this.positiveSuffix, 0, this.positiveSuffix.length());
            }
            if (b3) {
                b3 = s.regionMatches(i, this.negativeSuffix, 0, this.negativeSuffix.length());
            }
            if (b2 && b3) {
                if (this.positiveSuffix.length() > this.negativeSuffix.length()) {
                    b3 = false;
                }
                else if (this.positiveSuffix.length() < this.negativeSuffix.length()) {
                    b2 = false;
                }
            }
            if (b2 == b3) {
                parsePosition.errorIndex = i;
                return false;
            }
            parsePosition.index = i + (b2 ? this.positiveSuffix.length() : this.negativeSuffix.length());
        }
        else {
            parsePosition.index = i;
        }
        array[1] = b2;
        if (parsePosition.index == index2) {
            parsePosition.errorIndex = i;
            return false;
        }
        return true;
    }
    
    public DecimalFormatSymbols getDecimalFormatSymbols() {
        try {
            return (DecimalFormatSymbols)this.symbols.clone();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public void setDecimalFormatSymbols(final DecimalFormatSymbols decimalFormatSymbols) {
        try {
            this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
            this.expandAffixes();
            this.fastPathCheckNeeded = true;
        }
        catch (Exception ex) {}
    }
    
    public String getPositivePrefix() {
        return this.positivePrefix;
    }
    
    public void setPositivePrefix(final String positivePrefix) {
        this.positivePrefix = positivePrefix;
        this.posPrefixPattern = null;
        this.positivePrefixFieldPositions = null;
        this.fastPathCheckNeeded = true;
    }
    
    private FieldPosition[] getPositivePrefixFieldPositions() {
        if (this.positivePrefixFieldPositions == null) {
            if (this.posPrefixPattern != null) {
                this.positivePrefixFieldPositions = this.expandAffix(this.posPrefixPattern);
            }
            else {
                this.positivePrefixFieldPositions = DecimalFormat.EmptyFieldPositionArray;
            }
        }
        return this.positivePrefixFieldPositions;
    }
    
    public String getNegativePrefix() {
        return this.negativePrefix;
    }
    
    public void setNegativePrefix(final String negativePrefix) {
        this.negativePrefix = negativePrefix;
        this.negPrefixPattern = null;
        this.fastPathCheckNeeded = true;
    }
    
    private FieldPosition[] getNegativePrefixFieldPositions() {
        if (this.negativePrefixFieldPositions == null) {
            if (this.negPrefixPattern != null) {
                this.negativePrefixFieldPositions = this.expandAffix(this.negPrefixPattern);
            }
            else {
                this.negativePrefixFieldPositions = DecimalFormat.EmptyFieldPositionArray;
            }
        }
        return this.negativePrefixFieldPositions;
    }
    
    public String getPositiveSuffix() {
        return this.positiveSuffix;
    }
    
    public void setPositiveSuffix(final String positiveSuffix) {
        this.positiveSuffix = positiveSuffix;
        this.posSuffixPattern = null;
        this.fastPathCheckNeeded = true;
    }
    
    private FieldPosition[] getPositiveSuffixFieldPositions() {
        if (this.positiveSuffixFieldPositions == null) {
            if (this.posSuffixPattern != null) {
                this.positiveSuffixFieldPositions = this.expandAffix(this.posSuffixPattern);
            }
            else {
                this.positiveSuffixFieldPositions = DecimalFormat.EmptyFieldPositionArray;
            }
        }
        return this.positiveSuffixFieldPositions;
    }
    
    public String getNegativeSuffix() {
        return this.negativeSuffix;
    }
    
    public void setNegativeSuffix(final String negativeSuffix) {
        this.negativeSuffix = negativeSuffix;
        this.negSuffixPattern = null;
        this.fastPathCheckNeeded = true;
    }
    
    private FieldPosition[] getNegativeSuffixFieldPositions() {
        if (this.negativeSuffixFieldPositions == null) {
            if (this.negSuffixPattern != null) {
                this.negativeSuffixFieldPositions = this.expandAffix(this.negSuffixPattern);
            }
            else {
                this.negativeSuffixFieldPositions = DecimalFormat.EmptyFieldPositionArray;
            }
        }
        return this.negativeSuffixFieldPositions;
    }
    
    public int getMultiplier() {
        return this.multiplier;
    }
    
    public void setMultiplier(final int multiplier) {
        this.multiplier = multiplier;
        this.bigDecimalMultiplier = null;
        this.bigIntegerMultiplier = null;
        this.fastPathCheckNeeded = true;
    }
    
    @Override
    public void setGroupingUsed(final boolean groupingUsed) {
        super.setGroupingUsed(groupingUsed);
        this.fastPathCheckNeeded = true;
    }
    
    public int getGroupingSize() {
        return this.groupingSize;
    }
    
    public void setGroupingSize(final int n) {
        this.groupingSize = (byte)n;
        this.fastPathCheckNeeded = true;
    }
    
    public boolean isDecimalSeparatorAlwaysShown() {
        return this.decimalSeparatorAlwaysShown;
    }
    
    public void setDecimalSeparatorAlwaysShown(final boolean decimalSeparatorAlwaysShown) {
        this.decimalSeparatorAlwaysShown = decimalSeparatorAlwaysShown;
        this.fastPathCheckNeeded = true;
    }
    
    public boolean isParseBigDecimal() {
        return this.parseBigDecimal;
    }
    
    public void setParseBigDecimal(final boolean parseBigDecimal) {
        this.parseBigDecimal = parseBigDecimal;
    }
    
    @Override
    public Object clone() {
        final DecimalFormat decimalFormat = (DecimalFormat)super.clone();
        decimalFormat.symbols = (DecimalFormatSymbols)this.symbols.clone();
        decimalFormat.digitList = (DigitList)this.digitList.clone();
        decimalFormat.fastPathCheckNeeded = true;
        decimalFormat.isFastPath = false;
        decimalFormat.fastPathData = null;
        return decimalFormat;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final DecimalFormat decimalFormat = (DecimalFormat)o;
        return ((this.posPrefixPattern == decimalFormat.posPrefixPattern && this.positivePrefix.equals(decimalFormat.positivePrefix)) || (this.posPrefixPattern != null && this.posPrefixPattern.equals(decimalFormat.posPrefixPattern))) && ((this.posSuffixPattern == decimalFormat.posSuffixPattern && this.positiveSuffix.equals(decimalFormat.positiveSuffix)) || (this.posSuffixPattern != null && this.posSuffixPattern.equals(decimalFormat.posSuffixPattern))) && ((this.negPrefixPattern == decimalFormat.negPrefixPattern && this.negativePrefix.equals(decimalFormat.negativePrefix)) || (this.negPrefixPattern != null && this.negPrefixPattern.equals(decimalFormat.negPrefixPattern))) && ((this.negSuffixPattern == decimalFormat.negSuffixPattern && this.negativeSuffix.equals(decimalFormat.negativeSuffix)) || (this.negSuffixPattern != null && this.negSuffixPattern.equals(decimalFormat.negSuffixPattern))) && this.multiplier == decimalFormat.multiplier && this.groupingSize == decimalFormat.groupingSize && this.decimalSeparatorAlwaysShown == decimalFormat.decimalSeparatorAlwaysShown && this.parseBigDecimal == decimalFormat.parseBigDecimal && this.useExponentialNotation == decimalFormat.useExponentialNotation && (!this.useExponentialNotation || this.minExponentDigits == decimalFormat.minExponentDigits) && this.maximumIntegerDigits == decimalFormat.maximumIntegerDigits && this.minimumIntegerDigits == decimalFormat.minimumIntegerDigits && this.maximumFractionDigits == decimalFormat.maximumFractionDigits && this.minimumFractionDigits == decimalFormat.minimumFractionDigits && this.roundingMode == decimalFormat.roundingMode && this.symbols.equals(decimalFormat.symbols);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() * 37 + this.positivePrefix.hashCode();
    }
    
    public String toPattern() {
        return this.toPattern(false);
    }
    
    public String toLocalizedPattern() {
        return this.toPattern(true);
    }
    
    private void expandAffixes() {
        final StringBuffer sb = new StringBuffer();
        if (this.posPrefixPattern != null) {
            this.positivePrefix = this.expandAffix(this.posPrefixPattern, sb);
            this.positivePrefixFieldPositions = null;
        }
        if (this.posSuffixPattern != null) {
            this.positiveSuffix = this.expandAffix(this.posSuffixPattern, sb);
            this.positiveSuffixFieldPositions = null;
        }
        if (this.negPrefixPattern != null) {
            this.negativePrefix = this.expandAffix(this.negPrefixPattern, sb);
            this.negativePrefixFieldPositions = null;
        }
        if (this.negSuffixPattern != null) {
            this.negativeSuffix = this.expandAffix(this.negSuffixPattern, sb);
            this.negativeSuffixFieldPositions = null;
        }
    }
    
    private String expandAffix(final String s, final StringBuffer sb) {
        sb.setLength(0);
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i++);
            if (c == '\'') {
                c = s.charAt(i++);
                switch (c) {
                    case 164: {
                        if (i < s.length() && s.charAt(i) == '¤') {
                            ++i;
                            sb.append(this.symbols.getInternationalCurrencySymbol());
                            continue;
                        }
                        sb.append(this.symbols.getCurrencySymbol());
                        continue;
                    }
                    case 37: {
                        c = this.symbols.getPercent();
                        break;
                    }
                    case 8240: {
                        c = this.symbols.getPerMill();
                        break;
                    }
                    case 45: {
                        c = this.symbols.getMinusSign();
                        break;
                    }
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
    
    private FieldPosition[] expandAffix(final String s) {
        ArrayList<FieldPosition> list = null;
        int n = 0;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i++) == '\'') {
                int n2 = -1;
                Format.Field field = null;
                switch (s.charAt(i++)) {
                    case '¤': {
                        String s2;
                        if (i < s.length() && s.charAt(i) == '¤') {
                            ++i;
                            s2 = this.symbols.getInternationalCurrencySymbol();
                        }
                        else {
                            s2 = this.symbols.getCurrencySymbol();
                        }
                        if (s2.length() > 0) {
                            if (list == null) {
                                list = new ArrayList<FieldPosition>(2);
                            }
                            final FieldPosition fieldPosition = new FieldPosition(Field.CURRENCY);
                            fieldPosition.setBeginIndex(n);
                            fieldPosition.setEndIndex(n + s2.length());
                            list.add(fieldPosition);
                            n += s2.length();
                            continue;
                        }
                        continue;
                    }
                    case '%': {
                        this.symbols.getPercent();
                        n2 = -1;
                        field = Field.PERCENT;
                        break;
                    }
                    case '\u2030': {
                        this.symbols.getPerMill();
                        n2 = -1;
                        field = Field.PERMILLE;
                        break;
                    }
                    case '-': {
                        this.symbols.getMinusSign();
                        n2 = -1;
                        field = Field.SIGN;
                        break;
                    }
                }
                if (field != null) {
                    if (list == null) {
                        list = new ArrayList<FieldPosition>(2);
                    }
                    final FieldPosition fieldPosition2 = new FieldPosition(field, n2);
                    fieldPosition2.setBeginIndex(n);
                    fieldPosition2.setEndIndex(n + 1);
                    list.add(fieldPosition2);
                }
            }
            ++n;
        }
        if (list != null) {
            return list.toArray(DecimalFormat.EmptyFieldPositionArray);
        }
        return DecimalFormat.EmptyFieldPositionArray;
    }
    
    private void appendAffix(final StringBuffer sb, final String s, final String s2, final boolean b) {
        if (s == null) {
            this.appendAffix(sb, s2, b);
        }
        else {
            int index;
            for (int i = 0; i < s.length(); i = index) {
                index = s.indexOf(39, i);
                if (index < 0) {
                    this.appendAffix(sb, s.substring(i), b);
                    break;
                }
                if (index > i) {
                    this.appendAffix(sb, s.substring(i, index), b);
                }
                char c = s.charAt(++index);
                ++index;
                if (c == '\'') {
                    sb.append(c);
                }
                else if (c == '¤' && index < s.length() && s.charAt(index) == '¤') {
                    ++index;
                    sb.append(c);
                }
                else if (b) {
                    switch (c) {
                        case '%': {
                            c = this.symbols.getPercent();
                            break;
                        }
                        case '\u2030': {
                            c = this.symbols.getPerMill();
                            break;
                        }
                        case '-': {
                            c = this.symbols.getMinusSign();
                            break;
                        }
                    }
                }
                sb.append(c);
            }
        }
    }
    
    private void appendAffix(final StringBuffer sb, final String s, final boolean b) {
        boolean b2;
        if (b) {
            b2 = (s.indexOf(this.symbols.getZeroDigit()) >= 0 || s.indexOf(this.symbols.getGroupingSeparator()) >= 0 || s.indexOf(this.symbols.getDecimalSeparator()) >= 0 || s.indexOf(this.symbols.getPercent()) >= 0 || s.indexOf(this.symbols.getPerMill()) >= 0 || s.indexOf(this.symbols.getDigit()) >= 0 || s.indexOf(this.symbols.getPatternSeparator()) >= 0 || s.indexOf(this.symbols.getMinusSign()) >= 0 || s.indexOf(164) >= 0);
        }
        else {
            b2 = (s.indexOf(48) >= 0 || s.indexOf(44) >= 0 || s.indexOf(46) >= 0 || s.indexOf(37) >= 0 || s.indexOf(8240) >= 0 || s.indexOf(35) >= 0 || s.indexOf(59) >= 0 || s.indexOf(45) >= 0 || s.indexOf(164) >= 0);
        }
        if (b2) {
            sb.append('\'');
        }
        if (s.indexOf(39) < 0) {
            sb.append(s);
        }
        else {
            for (int i = 0; i < s.length(); ++i) {
                final char char1 = s.charAt(i);
                sb.append(char1);
                if (char1 == '\'') {
                    sb.append(char1);
                }
            }
        }
        if (b2) {
            sb.append('\'');
        }
    }
    
    private String toPattern(final boolean b) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 1; i >= 0; --i) {
            if (i == 1) {
                this.appendAffix(sb, this.posPrefixPattern, this.positivePrefix, b);
            }
            else {
                this.appendAffix(sb, this.negPrefixPattern, this.negativePrefix, b);
            }
            byte b2;
            for (int n = b2 = (byte)(this.useExponentialNotation ? this.getMaximumIntegerDigits() : (Math.max(this.groupingSize, this.getMinimumIntegerDigits()) + 1)); b2 > 0; --b2) {
                if (b2 != n && this.isGroupingUsed() && this.groupingSize != 0 && b2 % this.groupingSize == 0) {
                    sb.append(b ? this.symbols.getGroupingSeparator() : ',');
                }
                sb.append((b2 <= this.getMinimumIntegerDigits()) ? (b ? this.symbols.getZeroDigit() : '0') : (b ? this.symbols.getDigit() : '#'));
            }
            if (this.getMaximumFractionDigits() > 0 || this.decimalSeparatorAlwaysShown) {
                sb.append(b ? this.symbols.getDecimalSeparator() : '.');
            }
            for (int j = 0; j < this.getMaximumFractionDigits(); ++j) {
                if (j < this.getMinimumFractionDigits()) {
                    sb.append(b ? this.symbols.getZeroDigit() : '0');
                }
                else {
                    sb.append(b ? this.symbols.getDigit() : '#');
                }
            }
            if (this.useExponentialNotation) {
                sb.append(b ? this.symbols.getExponentSeparator() : "E");
                for (byte b3 = 0; b3 < this.minExponentDigits; ++b3) {
                    sb.append(b ? this.symbols.getZeroDigit() : '0');
                }
            }
            if (i == 1) {
                this.appendAffix(sb, this.posSuffixPattern, this.positiveSuffix, b);
                if ((this.negSuffixPattern == this.posSuffixPattern && this.negativeSuffix.equals(this.positiveSuffix)) || (this.negSuffixPattern != null && this.negSuffixPattern.equals(this.posSuffixPattern))) {
                    if (this.negPrefixPattern != null && this.posPrefixPattern != null && this.negPrefixPattern.equals("'-" + this.posPrefixPattern)) {
                        break;
                    }
                    if (this.negPrefixPattern == this.posPrefixPattern && this.negativePrefix.equals(this.symbols.getMinusSign() + this.positivePrefix)) {
                        break;
                    }
                }
                sb.append(b ? this.symbols.getPatternSeparator() : ';');
            }
            else {
                this.appendAffix(sb, this.negSuffixPattern, this.negativeSuffix, b);
            }
        }
        return sb.toString();
    }
    
    public void applyPattern(final String s) {
        this.applyPattern(s, false);
    }
    
    public void applyLocalizedPattern(final String s) {
        this.applyPattern(s, true);
    }
    
    private void applyPattern(final String s, final boolean b) {
        char zeroDigit = '0';
        char groupingSeparator = ',';
        char decimalSeparator = '.';
        char percent = '%';
        char perMill = '\u2030';
        char digit = '#';
        char patternSeparator = ';';
        String exponentSeparator = "E";
        char minusSign = '-';
        if (b) {
            zeroDigit = this.symbols.getZeroDigit();
            groupingSeparator = this.symbols.getGroupingSeparator();
            decimalSeparator = this.symbols.getDecimalSeparator();
            percent = this.symbols.getPercent();
            perMill = this.symbols.getPerMill();
            digit = this.symbols.getDigit();
            patternSeparator = this.symbols.getPatternSeparator();
            exponentSeparator = this.symbols.getExponentSeparator();
            minusSign = this.symbols.getMinusSign();
        }
        boolean b2 = false;
        this.decimalSeparatorAlwaysShown = false;
        this.isCurrencyFormat = false;
        this.useExponentialNotation = false;
        int n = 0;
        for (int n2 = 0, n3 = 1; n3 >= 0 && n2 < s.length(); --n3) {
            int n4 = 0;
            final StringBuffer sb = new StringBuffer();
            final StringBuffer sb2 = new StringBuffer();
            int n5 = -1;
            int multiplier = 1;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = -1;
            int n10 = 0;
            StringBuffer sb3 = sb;
            for (int i = n2; i < s.length(); ++i) {
                final char char1 = s.charAt(i);
                switch (n10) {
                    case 0:
                    case 2: {
                        if (n4 != 0) {
                            if (char1 == '\'') {
                                if (i + 1 < s.length() && s.charAt(i + 1) == '\'') {
                                    ++i;
                                    sb3.append("''");
                                    break;
                                }
                                n4 = 0;
                                break;
                            }
                        }
                        else {
                            if (char1 == digit || char1 == zeroDigit || char1 == groupingSeparator || char1 == decimalSeparator) {
                                n10 = 1;
                                if (n3 == 1) {}
                                --i;
                                break;
                            }
                            if (char1 == '¤') {
                                final boolean b3 = i + 1 < s.length() && s.charAt(i + 1) == '¤';
                                if (b3) {
                                    ++i;
                                }
                                this.isCurrencyFormat = true;
                                sb3.append(b3 ? "'¤¤" : "'¤");
                                break;
                            }
                            if (char1 == '\'') {
                                if (char1 == '\'') {
                                    if (i + 1 < s.length() && s.charAt(i + 1) == '\'') {
                                        ++i;
                                        sb3.append("''");
                                        break;
                                    }
                                    n4 = 1;
                                    break;
                                }
                            }
                            else if (char1 == patternSeparator) {
                                if (n10 == 0 || n3 == 0) {
                                    throw new IllegalArgumentException("Unquoted special character '" + char1 + "' in pattern \"" + s + '\"');
                                }
                                n2 = i + 1;
                                i = s.length();
                                break;
                            }
                            else if (char1 == percent) {
                                if (multiplier != 1) {
                                    throw new IllegalArgumentException("Too many percent/per mille characters in pattern \"" + s + '\"');
                                }
                                multiplier = 100;
                                sb3.append("'%");
                                break;
                            }
                            else if (char1 == perMill) {
                                if (multiplier != 1) {
                                    throw new IllegalArgumentException("Too many percent/per mille characters in pattern \"" + s + '\"');
                                }
                                multiplier = 1000;
                                sb3.append("'\u2030");
                                break;
                            }
                            else if (char1 == minusSign) {
                                sb3.append("'-");
                                break;
                            }
                        }
                        sb3.append(char1);
                        break;
                    }
                    case 1: {
                        if (n3 == 1) {
                            ++n;
                            if (char1 == digit) {
                                if (n7 > 0) {
                                    ++n8;
                                }
                                else {
                                    ++n6;
                                }
                                if (n9 >= 0 && n5 < 0) {
                                    n9 = (byte)(n9 + 1);
                                    break;
                                }
                                break;
                            }
                            else if (char1 == zeroDigit) {
                                if (n8 > 0) {
                                    throw new IllegalArgumentException("Unexpected '0' in pattern \"" + s + '\"');
                                }
                                ++n7;
                                if (n9 >= 0 && n5 < 0) {
                                    n9 = (byte)(n9 + 1);
                                    break;
                                }
                                break;
                            }
                            else {
                                if (char1 == groupingSeparator) {
                                    n9 = 0;
                                    break;
                                }
                                if (char1 == decimalSeparator) {
                                    if (n5 >= 0) {
                                        throw new IllegalArgumentException("Multiple decimal separators in pattern \"" + s + '\"');
                                    }
                                    n5 = n6 + n7 + n8;
                                    break;
                                }
                                else {
                                    if (!s.regionMatches(i, exponentSeparator, 0, exponentSeparator.length())) {
                                        n10 = 2;
                                        sb3 = sb2;
                                        --i;
                                        --n;
                                        break;
                                    }
                                    if (this.useExponentialNotation) {
                                        throw new IllegalArgumentException("Multiple exponential symbols in pattern \"" + s + '\"');
                                    }
                                    this.useExponentialNotation = true;
                                    this.minExponentDigits = 0;
                                    for (i += exponentSeparator.length(); i < s.length() && s.charAt(i) == zeroDigit; ++i) {
                                        ++this.minExponentDigits;
                                        ++n;
                                    }
                                    if (n6 + n7 < 1 || this.minExponentDigits < 1) {
                                        throw new IllegalArgumentException("Malformed exponential pattern \"" + s + '\"');
                                    }
                                    n10 = 2;
                                    sb3 = sb2;
                                    --i;
                                    break;
                                }
                            }
                        }
                        else {
                            if (--n == 0) {
                                n10 = 2;
                                sb3 = sb2;
                                break;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
            if (n7 == 0 && n6 > 0 && n5 >= 0) {
                int n11 = n5;
                if (n11 == 0) {
                    ++n11;
                }
                n8 = n6 - n11;
                n6 = n11 - 1;
                n7 = 1;
            }
            if ((n5 < 0 && n8 > 0) || (n5 >= 0 && (n5 < n6 || n5 > n6 + n7)) || n9 == 0 || n4 != 0) {
                throw new IllegalArgumentException("Malformed pattern \"" + s + '\"');
            }
            if (n3 == 1) {
                this.posPrefixPattern = sb.toString();
                this.posSuffixPattern = sb2.toString();
                this.negPrefixPattern = this.posPrefixPattern;
                this.negSuffixPattern = this.posSuffixPattern;
                final int n12 = n6 + n7 + n8;
                this.setMinimumIntegerDigits(((n5 >= 0) ? n5 : n12) - n6);
                this.setMaximumIntegerDigits(this.useExponentialNotation ? (n6 + this.getMinimumIntegerDigits()) : Integer.MAX_VALUE);
                this.setMaximumFractionDigits((n5 >= 0) ? (n12 - n5) : 0);
                this.setMinimumFractionDigits((n5 >= 0) ? (n6 + n7 - n5) : 0);
                this.setGroupingUsed(n9 > 0);
                this.groupingSize = (byte)((n9 > 0) ? n9 : false);
                this.multiplier = multiplier;
                this.setDecimalSeparatorAlwaysShown(n5 == 0 || n5 == n12);
            }
            else {
                this.negPrefixPattern = sb.toString();
                this.negSuffixPattern = sb2.toString();
                b2 = true;
            }
        }
        if (s.length() == 0) {
            final String s2 = "";
            this.posSuffixPattern = s2;
            this.posPrefixPattern = s2;
            this.setMinimumIntegerDigits(0);
            this.setMaximumIntegerDigits(Integer.MAX_VALUE);
            this.setMinimumFractionDigits(0);
            this.setMaximumFractionDigits(Integer.MAX_VALUE);
        }
        if (!b2 || (this.negPrefixPattern.equals(this.posPrefixPattern) && this.negSuffixPattern.equals(this.posSuffixPattern))) {
            this.negSuffixPattern = this.posSuffixPattern;
            this.negPrefixPattern = "'-" + this.posPrefixPattern;
        }
        this.expandAffixes();
    }
    
    @Override
    public void setMaximumIntegerDigits(final int n) {
        this.maximumIntegerDigits = Math.min(Math.max(0, n), Integer.MAX_VALUE);
        super.setMaximumIntegerDigits((this.maximumIntegerDigits > 309) ? 309 : this.maximumIntegerDigits);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.minimumIntegerDigits = this.maximumIntegerDigits;
            super.setMinimumIntegerDigits((this.minimumIntegerDigits > 309) ? 309 : this.minimumIntegerDigits);
        }
        this.fastPathCheckNeeded = true;
    }
    
    @Override
    public void setMinimumIntegerDigits(final int n) {
        this.minimumIntegerDigits = Math.min(Math.max(0, n), Integer.MAX_VALUE);
        super.setMinimumIntegerDigits((this.minimumIntegerDigits > 309) ? 309 : this.minimumIntegerDigits);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.maximumIntegerDigits = this.minimumIntegerDigits;
            super.setMaximumIntegerDigits((this.maximumIntegerDigits > 309) ? 309 : this.maximumIntegerDigits);
        }
        this.fastPathCheckNeeded = true;
    }
    
    @Override
    public void setMaximumFractionDigits(final int n) {
        this.maximumFractionDigits = Math.min(Math.max(0, n), Integer.MAX_VALUE);
        super.setMaximumFractionDigits((this.maximumFractionDigits > 340) ? 340 : this.maximumFractionDigits);
        if (this.minimumFractionDigits > this.maximumFractionDigits) {
            this.minimumFractionDigits = this.maximumFractionDigits;
            super.setMinimumFractionDigits((this.minimumFractionDigits > 340) ? 340 : this.minimumFractionDigits);
        }
        this.fastPathCheckNeeded = true;
    }
    
    @Override
    public void setMinimumFractionDigits(final int n) {
        this.minimumFractionDigits = Math.min(Math.max(0, n), Integer.MAX_VALUE);
        super.setMinimumFractionDigits((this.minimumFractionDigits > 340) ? 340 : this.minimumFractionDigits);
        if (this.minimumFractionDigits > this.maximumFractionDigits) {
            this.maximumFractionDigits = this.minimumFractionDigits;
            super.setMaximumFractionDigits((this.maximumFractionDigits > 340) ? 340 : this.maximumFractionDigits);
        }
        this.fastPathCheckNeeded = true;
    }
    
    @Override
    public int getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }
    
    @Override
    public int getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }
    
    @Override
    public int getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }
    
    @Override
    public int getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }
    
    @Override
    public Currency getCurrency() {
        return this.symbols.getCurrency();
    }
    
    @Override
    public void setCurrency(final Currency currency) {
        if (currency != this.symbols.getCurrency()) {
            this.symbols.setCurrency(currency);
            if (this.isCurrencyFormat) {
                this.expandAffixes();
            }
        }
        this.fastPathCheckNeeded = true;
    }
    
    @Override
    public RoundingMode getRoundingMode() {
        return this.roundingMode;
    }
    
    @Override
    public void setRoundingMode(final RoundingMode roundingMode) {
        if (roundingMode == null) {
            throw new NullPointerException();
        }
        this.roundingMode = roundingMode;
        this.digitList.setRoundingMode(roundingMode);
        this.fastPathCheckNeeded = true;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.digitList = new DigitList();
        this.fastPathCheckNeeded = true;
        this.isFastPath = false;
        this.fastPathData = null;
        if (this.serialVersionOnStream < 4) {
            this.setRoundingMode(RoundingMode.HALF_EVEN);
        }
        else {
            this.setRoundingMode(this.getRoundingMode());
        }
        if (super.getMaximumIntegerDigits() > 309 || super.getMaximumFractionDigits() > 340) {
            throw new InvalidObjectException("Digit count out of range");
        }
        if (this.serialVersionOnStream < 3) {
            this.setMaximumIntegerDigits(super.getMaximumIntegerDigits());
            this.setMinimumIntegerDigits(super.getMinimumIntegerDigits());
            this.setMaximumFractionDigits(super.getMaximumFractionDigits());
            this.setMinimumFractionDigits(super.getMinimumFractionDigits());
        }
        if (this.serialVersionOnStream < 1) {
            this.useExponentialNotation = false;
        }
        this.serialVersionOnStream = 4;
    }
    
    static {
        DecimalFormat.EmptyFieldPositionArray = new FieldPosition[0];
    }
    
    private static class DigitArrays
    {
        static final char[] DigitOnes1000;
        static final char[] DigitTens1000;
        static final char[] DigitHundreds1000;
        
        static {
            DigitOnes1000 = new char[1000];
            DigitTens1000 = new char[1000];
            DigitHundreds1000 = new char[1000];
            int n = 0;
            int n2 = 0;
            char c = '0';
            char c2 = '0';
            char c3 = '0';
            for (int i = 0; i < 1000; ++i) {
                if ((DigitArrays.DigitOnes1000[i] = c) == '9') {
                    c = '0';
                }
                else {
                    ++c;
                }
                DigitArrays.DigitTens1000[i] = c2;
                if (i == n + 9) {
                    n += 10;
                    if (c2 == '9') {
                        c2 = '0';
                    }
                    else {
                        ++c2;
                    }
                }
                DigitArrays.DigitHundreds1000[i] = c3;
                if (i == n2 + 99) {
                    ++c3;
                    n2 += 100;
                }
            }
        }
    }
    
    private static class FastPathData
    {
        int lastFreeIndex;
        int firstUsedIndex;
        int zeroDelta;
        char groupingChar;
        int integralLastIndex;
        int fractionalFirstIndex;
        double fractionalScaleFactor;
        int fractionalMaxIntBound;
        char[] fastPathContainer;
        char[] charsPositivePrefix;
        char[] charsNegativePrefix;
        char[] charsPositiveSuffix;
        char[] charsNegativeSuffix;
        boolean positiveAffixesRequired;
        boolean negativeAffixesRequired;
        
        private FastPathData() {
            this.positiveAffixesRequired = true;
            this.negativeAffixesRequired = true;
        }
    }
}
