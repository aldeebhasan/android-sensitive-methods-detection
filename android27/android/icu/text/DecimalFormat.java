package android.icu.text;

import java.math.*;
import android.icu.math.*;
import java.text.*;
import android.icu.util.*;

public class DecimalFormat extends NumberFormat
{
    public static final int PAD_AFTER_PREFIX = 1;
    public static final int PAD_AFTER_SUFFIX = 3;
    public static final int PAD_BEFORE_PREFIX = 0;
    public static final int PAD_BEFORE_SUFFIX = 2;
    
    public DecimalFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public DecimalFormat(final String pattern) {
        throw new RuntimeException("Stub!");
    }
    
    public DecimalFormat(final String pattern, final DecimalFormatSymbols symbols) {
        throw new RuntimeException("Stub!");
    }
    
    public DecimalFormat(final String pattern, final DecimalFormatSymbols symbols, final CurrencyPluralInfo infoInput, final int style) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final double number, final StringBuffer result, final FieldPosition fieldPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final long number, final StringBuffer result, final FieldPosition fieldPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final BigInteger number, final StringBuffer result, final FieldPosition fieldPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final BigDecimal number, final StringBuffer result, final FieldPosition fieldPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final android.icu.math.BigDecimal number, final StringBuffer result, final FieldPosition fieldPosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Number parse(final String text, final ParsePosition parsePosition) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CurrencyAmount parseCurrency(final CharSequence text, final ParsePosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    public DecimalFormatSymbols getDecimalFormatSymbols() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDecimalFormatSymbols(final DecimalFormatSymbols newSymbols) {
        throw new RuntimeException("Stub!");
    }
    
    public String getPositivePrefix() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPositivePrefix(final String newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public String getNegativePrefix() {
        throw new RuntimeException("Stub!");
    }
    
    public void setNegativePrefix(final String newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public String getPositiveSuffix() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPositiveSuffix(final String newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public String getNegativeSuffix() {
        throw new RuntimeException("Stub!");
    }
    
    public void setNegativeSuffix(final String newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMultiplier() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMultiplier(final int newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public BigDecimal getRoundingIncrement() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRoundingIncrement(final BigDecimal newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRoundingIncrement(final android.icu.math.BigDecimal newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRoundingIncrement(final double newValue) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getRoundingMode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setRoundingMode(final int roundingMode) {
        throw new RuntimeException("Stub!");
    }
    
    public int getFormatWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFormatWidth(final int width) {
        throw new RuntimeException("Stub!");
    }
    
    public char getPadCharacter() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPadCharacter(final char padChar) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPadPosition() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPadPosition(final int padPos) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isScientificNotation() {
        throw new RuntimeException("Stub!");
    }
    
    public void setScientificNotation(final boolean useScientific) {
        throw new RuntimeException("Stub!");
    }
    
    public byte getMinimumExponentDigits() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMinimumExponentDigits(final byte minExpDig) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isExponentSignAlwaysShown() {
        throw new RuntimeException("Stub!");
    }
    
    public void setExponentSignAlwaysShown(final boolean expSignAlways) {
        throw new RuntimeException("Stub!");
    }
    
    public int getGroupingSize() {
        throw new RuntimeException("Stub!");
    }
    
    public void setGroupingSize(final int newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSecondaryGroupingSize() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSecondaryGroupingSize(final int newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public MathContext getMathContextICU() {
        throw new RuntimeException("Stub!");
    }
    
    public java.math.MathContext getMathContext() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMathContextICU(final MathContext newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMathContext(final java.math.MathContext newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDecimalSeparatorAlwaysShown() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDecimalPatternMatchRequired(final boolean value) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDecimalPatternMatchRequired() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDecimalSeparatorAlwaysShown(final boolean newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public CurrencyPluralInfo getCurrencyPluralInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCurrencyPluralInfo(final CurrencyPluralInfo newInfo) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Object clone() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    public String toPattern() {
        throw new RuntimeException("Stub!");
    }
    
    public String toLocalizedPattern() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    public void applyPattern(final String pattern) {
        throw new RuntimeException("Stub!");
    }
    
    public void applyLocalizedPattern(final String pattern) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setMaximumIntegerDigits(final int newValue) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setMinimumIntegerDigits(final int newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMinimumSignificantDigits() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaximumSignificantDigits() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMinimumSignificantDigits(final int min) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMaximumSignificantDigits(final int max) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean areSignificantDigitsUsed() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSignificantDigitsUsed(final boolean useSignificantDigits) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setCurrency(final Currency theCurrency) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCurrencyUsage(final Currency.CurrencyUsage newUsage) {
        throw new RuntimeException("Stub!");
    }
    
    public Currency.CurrencyUsage getCurrencyUsage() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setMaximumFractionDigits(final int newValue) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setMinimumFractionDigits(final int newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public void setParseBigDecimal(final boolean value) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isParseBigDecimal() {
        throw new RuntimeException("Stub!");
    }
    
    public void setParseMaxDigits(final int newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public int getParseMaxDigits() {
        throw new RuntimeException("Stub!");
    }
}
