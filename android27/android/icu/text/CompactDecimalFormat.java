package android.icu.text;

import android.icu.util.*;
import java.util.*;
import java.math.*;
import java.text.*;

public class CompactDecimalFormat extends DecimalFormat
{
    CompactDecimalFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public static CompactDecimalFormat getInstance(final ULocale locale, final CompactStyle style) {
        throw new RuntimeException("Stub!");
    }
    
    public static CompactDecimalFormat getInstance(final Locale locale, final CompactStyle style) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final double number, final StringBuffer toAppendTo, final FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final long number, final StringBuffer toAppendTo, final FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final BigInteger number, final StringBuffer toAppendTo, final FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final BigDecimal number, final StringBuffer toAppendTo, final FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final android.icu.math.BigDecimal number, final StringBuffer toAppendTo, final FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Number parse(final String text, final ParsePosition parsePosition) {
        throw new RuntimeException("Stub!");
    }
    
    public enum CompactStyle
    {
        LONG, 
        SHORT;
    }
}
