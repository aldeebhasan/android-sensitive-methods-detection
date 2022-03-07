package android.icu.text;

import java.util.*;
import android.icu.util.*;
import java.text.*;

public class MeasureFormat extends UFormat
{
    MeasureFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public static MeasureFormat getInstance(final ULocale locale, final FormatWidth formatWidth) {
        throw new RuntimeException("Stub!");
    }
    
    public static MeasureFormat getInstance(final Locale locale, final FormatWidth formatWidth) {
        throw new RuntimeException("Stub!");
    }
    
    public static MeasureFormat getInstance(final ULocale locale, final FormatWidth formatWidth, final NumberFormat format) {
        throw new RuntimeException("Stub!");
    }
    
    public static MeasureFormat getInstance(final Locale locale, final FormatWidth formatWidth, final NumberFormat format) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    public final String formatMeasures(final Measure... measures) {
        throw new RuntimeException("Stub!");
    }
    
    public StringBuilder formatMeasurePerUnit(final Measure measure, final MeasureUnit perUnit, final StringBuilder appendTo, final FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    public StringBuilder formatMeasures(final StringBuilder appendTo, final FieldPosition fieldPosition, final Measure... measures) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final boolean equals(final Object other) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    public FormatWidth getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public final ULocale getLocale() {
        throw new RuntimeException("Stub!");
    }
    
    public NumberFormat getNumberFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public static MeasureFormat getCurrencyFormat(final ULocale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public static MeasureFormat getCurrencyFormat(final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public static MeasureFormat getCurrencyFormat() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Measure parseObject(final String source, final ParsePosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    public enum FormatWidth
    {
        NARROW, 
        NUMERIC, 
        SHORT, 
        WIDE;
    }
}
