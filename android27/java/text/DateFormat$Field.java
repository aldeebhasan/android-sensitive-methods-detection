package java.text;

import java.io.*;
import java.util.*;

public static class Field extends Format.Field
{
    private static final long serialVersionUID = 7441350119349544720L;
    private static final Map<String, Field> instanceMap;
    private static final Field[] calendarToFieldMapping;
    private int calendarField;
    public static final Field ERA;
    public static final Field YEAR;
    public static final Field MONTH;
    public static final Field DAY_OF_MONTH;
    public static final Field HOUR_OF_DAY1;
    public static final Field HOUR_OF_DAY0;
    public static final Field MINUTE;
    public static final Field SECOND;
    public static final Field MILLISECOND;
    public static final Field DAY_OF_WEEK;
    public static final Field DAY_OF_YEAR;
    public static final Field DAY_OF_WEEK_IN_MONTH;
    public static final Field WEEK_OF_YEAR;
    public static final Field WEEK_OF_MONTH;
    public static final Field AM_PM;
    public static final Field HOUR1;
    public static final Field HOUR0;
    public static final Field TIME_ZONE;
    
    public static Field ofCalendarField(final int n) {
        if (n < 0 || n >= Field.calendarToFieldMapping.length) {
            throw new IllegalArgumentException("Unknown Calendar constant " + n);
        }
        return Field.calendarToFieldMapping[n];
    }
    
    protected Field(final String s, final int calendarField) {
        super(s);
        this.calendarField = calendarField;
        if (this.getClass() == Field.class) {
            Field.instanceMap.put(s, this);
            if (calendarField >= 0) {
                Field.calendarToFieldMapping[calendarField] = this;
            }
        }
    }
    
    public int getCalendarField() {
        return this.calendarField;
    }
    
    @Override
    protected Object readResolve() throws InvalidObjectException {
        if (this.getClass() != Field.class) {
            throw new InvalidObjectException("subclass didn't correctly implement readResolve");
        }
        final Field value = Field.instanceMap.get(this.getName());
        if (value != null) {
            return value;
        }
        throw new InvalidObjectException("unknown attribute name");
    }
    
    static {
        instanceMap = new HashMap<String, Field>(18);
        calendarToFieldMapping = new Field[17];
        ERA = new Field("era", 0);
        YEAR = new Field("year", 1);
        MONTH = new Field("month", 2);
        DAY_OF_MONTH = new Field("day of month", 5);
        HOUR_OF_DAY1 = new Field("hour of day 1", -1);
        HOUR_OF_DAY0 = new Field("hour of day", 11);
        MINUTE = new Field("minute", 12);
        SECOND = new Field("second", 13);
        MILLISECOND = new Field("millisecond", 14);
        DAY_OF_WEEK = new Field("day of week", 7);
        DAY_OF_YEAR = new Field("day of year", 6);
        DAY_OF_WEEK_IN_MONTH = new Field("day of week in month", 8);
        WEEK_OF_YEAR = new Field("week of year", 3);
        WEEK_OF_MONTH = new Field("week of month", 4);
        AM_PM = new Field("am pm", 9);
        HOUR1 = new Field("hour 1", -1);
        HOUR0 = new Field("hour", 10);
        TIME_ZONE = new Field("time zone", -1);
    }
}
