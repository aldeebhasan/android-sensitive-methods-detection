package android.icu.util;

import java.util.*;

abstract class CECalendar extends Calendar
{
    protected CECalendar() {
        throw new RuntimeException("Stub!");
    }
    
    protected CECalendar(final TimeZone zone) {
        throw new RuntimeException("Stub!");
    }
    
    protected CECalendar(final Locale aLocale) {
        throw new RuntimeException("Stub!");
    }
    
    protected CECalendar(final ULocale locale) {
        throw new RuntimeException("Stub!");
    }
    
    protected CECalendar(final TimeZone zone, final Locale aLocale) {
        throw new RuntimeException("Stub!");
    }
    
    protected CECalendar(final TimeZone zone, final ULocale locale) {
        throw new RuntimeException("Stub!");
    }
    
    protected CECalendar(final int year, final int month, final int date) {
        throw new RuntimeException("Stub!");
    }
    
    protected CECalendar(final Date date) {
        throw new RuntimeException("Stub!");
    }
    
    protected CECalendar(final int year, final int month, final int date, final int hour, final int minute, final int second) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int handleComputeMonthStart(final int eyear, final int emonth, final boolean useMonth) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int handleGetLimit(final int field, final int limitType) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int handleGetMonthLength(final int extendedYear, final int month) {
        throw new RuntimeException("Stub!");
    }
}
