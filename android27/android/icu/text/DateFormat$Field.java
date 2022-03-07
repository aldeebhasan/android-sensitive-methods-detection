package android.icu.text;

import java.text.*;
import java.io.*;

public static class Field extends Format.Field
{
    public static final Field AM_PM;
    public static final Field DAY_OF_MONTH;
    public static final Field DAY_OF_WEEK;
    public static final Field DAY_OF_WEEK_IN_MONTH;
    public static final Field DAY_OF_YEAR;
    public static final Field DOW_LOCAL;
    public static final Field ERA;
    public static final Field EXTENDED_YEAR;
    public static final Field HOUR0;
    public static final Field HOUR1;
    public static final Field HOUR_OF_DAY0;
    public static final Field HOUR_OF_DAY1;
    public static final Field JULIAN_DAY;
    public static final Field MILLISECOND;
    public static final Field MILLISECONDS_IN_DAY;
    public static final Field MINUTE;
    public static final Field MONTH;
    public static final Field QUARTER;
    public static final Field SECOND;
    public static final Field TIME_ZONE;
    public static final Field WEEK_OF_MONTH;
    public static final Field WEEK_OF_YEAR;
    public static final Field YEAR;
    public static final Field YEAR_WOY;
    
    protected Field(final String name, final int calendarField) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public static Field ofCalendarField(final int calendarField) {
        throw new RuntimeException("Stub!");
    }
    
    public int getCalendarField() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Object readResolve() throws InvalidObjectException {
        throw new RuntimeException("Stub!");
    }
    
    static {
        AM_PM = null;
        DAY_OF_MONTH = null;
        DAY_OF_WEEK = null;
        DAY_OF_WEEK_IN_MONTH = null;
        DAY_OF_YEAR = null;
        DOW_LOCAL = null;
        ERA = null;
        EXTENDED_YEAR = null;
        HOUR0 = null;
        HOUR1 = null;
        HOUR_OF_DAY0 = null;
        HOUR_OF_DAY1 = null;
        JULIAN_DAY = null;
        MILLISECOND = null;
        MILLISECONDS_IN_DAY = null;
        MINUTE = null;
        MONTH = null;
        QUARTER = null;
        SECOND = null;
        TIME_ZONE = null;
        WEEK_OF_MONTH = null;
        WEEK_OF_YEAR = null;
        YEAR = null;
        YEAR_WOY = null;
    }
}
