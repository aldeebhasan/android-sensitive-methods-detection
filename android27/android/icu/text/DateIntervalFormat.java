package android.icu.text;

import java.util.*;
import android.icu.util.*;
import java.text.*;

public class DateIntervalFormat extends UFormat
{
    DateIntervalFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public static final DateIntervalFormat getInstance(final String skeleton) {
        throw new RuntimeException("Stub!");
    }
    
    public static final DateIntervalFormat getInstance(final String skeleton, final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public static final DateIntervalFormat getInstance(final String skeleton, final ULocale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public static final DateIntervalFormat getInstance(final String skeleton, final DateIntervalInfo dtitvinf) {
        throw new RuntimeException("Stub!");
    }
    
    public static final DateIntervalFormat getInstance(final String skeleton, final Locale locale, final DateIntervalInfo dtitvinf) {
        throw new RuntimeException("Stub!");
    }
    
    public static final DateIntervalFormat getInstance(final String skeleton, final ULocale locale, final DateIntervalInfo dtitvinf) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized Object clone() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final StringBuffer format(final Object obj, final StringBuffer appendTo, final FieldPosition fieldPosition) {
        throw new RuntimeException("Stub!");
    }
    
    public final synchronized StringBuffer format(final DateInterval dtInterval, final StringBuffer appendTo, final FieldPosition fieldPosition) {
        throw new RuntimeException("Stub!");
    }
    
    public final synchronized StringBuffer format(final Calendar fromCalendar, final Calendar toCalendar, final StringBuffer appendTo, final FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }
    
    public DateIntervalInfo getDateIntervalInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDateIntervalInfo(final DateIntervalInfo newItvPattern) {
        throw new RuntimeException("Stub!");
    }
    
    public TimeZone getTimeZone() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTimeZone(final TimeZone zone) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized DateFormat getDateFormat() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public Object parseObject(final String source, final ParsePosition parse_pos) {
        throw new RuntimeException("Stub!");
    }
}
