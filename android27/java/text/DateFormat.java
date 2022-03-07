package java.text;

import java.text.spi.*;
import java.util.spi.*;
import sun.util.locale.provider.*;
import java.io.*;
import java.util.*;

public abstract class DateFormat extends Format
{
    protected Calendar calendar;
    protected NumberFormat numberFormat;
    public static final int ERA_FIELD = 0;
    public static final int YEAR_FIELD = 1;
    public static final int MONTH_FIELD = 2;
    public static final int DATE_FIELD = 3;
    public static final int HOUR_OF_DAY1_FIELD = 4;
    public static final int HOUR_OF_DAY0_FIELD = 5;
    public static final int MINUTE_FIELD = 6;
    public static final int SECOND_FIELD = 7;
    public static final int MILLISECOND_FIELD = 8;
    public static final int DAY_OF_WEEK_FIELD = 9;
    public static final int DAY_OF_YEAR_FIELD = 10;
    public static final int DAY_OF_WEEK_IN_MONTH_FIELD = 11;
    public static final int WEEK_OF_YEAR_FIELD = 12;
    public static final int WEEK_OF_MONTH_FIELD = 13;
    public static final int AM_PM_FIELD = 14;
    public static final int HOUR1_FIELD = 15;
    public static final int HOUR0_FIELD = 16;
    public static final int TIMEZONE_FIELD = 17;
    private static final long serialVersionUID = 7218322306649953788L;
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    public static final int DEFAULT = 2;
    
    @Override
    public final StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Date) {
            return this.format((Date)o, sb, fieldPosition);
        }
        if (o instanceof Number) {
            return this.format(new Date(((Number)o).longValue()), sb, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Date");
    }
    
    public abstract StringBuffer format(final Date p0, final StringBuffer p1, final FieldPosition p2);
    
    public final String format(final Date date) {
        return this.format(date, new StringBuffer(), DontCareFieldPosition.INSTANCE).toString();
    }
    
    public Date parse(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final Date parse = this.parse(s, parsePosition);
        if (parsePosition.index == 0) {
            throw new ParseException("Unparseable date: \"" + s + "\"", parsePosition.errorIndex);
        }
        return parse;
    }
    
    public abstract Date parse(final String p0, final ParsePosition p1);
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parse(s, parsePosition);
    }
    
    public static final DateFormat getTimeInstance() {
        return get(2, 0, 1, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static final DateFormat getTimeInstance(final int n) {
        return get(n, 0, 1, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static final DateFormat getTimeInstance(final int n, final Locale locale) {
        return get(n, 0, 1, locale);
    }
    
    public static final DateFormat getDateInstance() {
        return get(0, 2, 2, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static final DateFormat getDateInstance(final int n) {
        return get(0, n, 2, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static final DateFormat getDateInstance(final int n, final Locale locale) {
        return get(0, n, 2, locale);
    }
    
    public static final DateFormat getDateTimeInstance() {
        return get(2, 2, 3, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static final DateFormat getDateTimeInstance(final int n, final int n2) {
        return get(n2, n, 3, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static final DateFormat getDateTimeInstance(final int n, final int n2, final Locale locale) {
        return get(n2, n, 3, locale);
    }
    
    public static final DateFormat getInstance() {
        return getDateTimeInstance(3, 3);
    }
    
    public static Locale[] getAvailableLocales() {
        return LocaleServiceProviderPool.getPool(DateFormatProvider.class).getAvailableLocales();
    }
    
    public void setCalendar(final Calendar calendar) {
        this.calendar = calendar;
    }
    
    public Calendar getCalendar() {
        return this.calendar;
    }
    
    public void setNumberFormat(final NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }
    
    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }
    
    public void setTimeZone(final TimeZone timeZone) {
        this.calendar.setTimeZone(timeZone);
    }
    
    public TimeZone getTimeZone() {
        return this.calendar.getTimeZone();
    }
    
    public void setLenient(final boolean lenient) {
        this.calendar.setLenient(lenient);
    }
    
    public boolean isLenient() {
        return this.calendar.isLenient();
    }
    
    @Override
    public int hashCode() {
        return this.numberFormat.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DateFormat dateFormat = (DateFormat)o;
        return this.calendar.getFirstDayOfWeek() == dateFormat.calendar.getFirstDayOfWeek() && this.calendar.getMinimalDaysInFirstWeek() == dateFormat.calendar.getMinimalDaysInFirstWeek() && this.calendar.isLenient() == dateFormat.calendar.isLenient() && this.calendar.getTimeZone().equals(dateFormat.calendar.getTimeZone()) && this.numberFormat.equals(dateFormat.numberFormat);
    }
    
    @Override
    public Object clone() {
        final DateFormat dateFormat = (DateFormat)super.clone();
        dateFormat.calendar = (Calendar)this.calendar.clone();
        dateFormat.numberFormat = (NumberFormat)this.numberFormat.clone();
        return dateFormat;
    }
    
    private static DateFormat get(int n, int n2, final int n3, final Locale locale) {
        if ((n3 & 0x1) != 0x0) {
            if (n < 0 || n > 3) {
                throw new IllegalArgumentException("Illegal time style " + n);
            }
        }
        else {
            n = -1;
        }
        if ((n3 & 0x2) != 0x0) {
            if (n2 < 0 || n2 > 3) {
                throw new IllegalArgumentException("Illegal date style " + n2);
            }
        }
        else {
            n2 = -1;
        }
        DateFormat dateFormat = get(LocaleProviderAdapter.getAdapter(DateFormatProvider.class, locale), n, n2, locale);
        if (dateFormat == null) {
            dateFormat = get(LocaleProviderAdapter.forJRE(), n, n2, locale);
        }
        return dateFormat;
    }
    
    private static DateFormat get(final LocaleProviderAdapter localeProviderAdapter, final int n, final int n2, final Locale locale) {
        final DateFormatProvider dateFormatProvider = localeProviderAdapter.getDateFormatProvider();
        DateFormat dateFormat;
        if (n == -1) {
            dateFormat = dateFormatProvider.getDateInstance(n2, locale);
        }
        else if (n2 == -1) {
            dateFormat = dateFormatProvider.getTimeInstance(n, locale);
        }
        else {
            dateFormat = dateFormatProvider.getDateTimeInstance(n2, n, locale);
        }
        return dateFormat;
    }
    
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
}
