package java.util;

import sun.util.spi.*;
import java.util.spi.*;
import sun.util.*;
import sun.util.calendar.*;
import sun.util.locale.provider.*;
import java.text.*;
import java.io.*;
import java.time.*;
import java.util.concurrent.*;
import java.security.*;

public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar>
{
    public static final int ERA = 0;
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int WEEK_OF_YEAR = 3;
    public static final int WEEK_OF_MONTH = 4;
    public static final int DATE = 5;
    public static final int DAY_OF_MONTH = 5;
    public static final int DAY_OF_YEAR = 6;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    public static final int AM_PM = 9;
    public static final int HOUR = 10;
    public static final int HOUR_OF_DAY = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int MILLISECOND = 14;
    public static final int ZONE_OFFSET = 15;
    public static final int DST_OFFSET = 16;
    public static final int FIELD_COUNT = 17;
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    public static final int JANUARY = 0;
    public static final int FEBRUARY = 1;
    public static final int MARCH = 2;
    public static final int APRIL = 3;
    public static final int MAY = 4;
    public static final int JUNE = 5;
    public static final int JULY = 6;
    public static final int AUGUST = 7;
    public static final int SEPTEMBER = 8;
    public static final int OCTOBER = 9;
    public static final int NOVEMBER = 10;
    public static final int DECEMBER = 11;
    public static final int UNDECIMBER = 12;
    public static final int AM = 0;
    public static final int PM = 1;
    public static final int ALL_STYLES = 0;
    static final int STANDALONE_MASK = 32768;
    public static final int SHORT = 1;
    public static final int LONG = 2;
    public static final int NARROW_FORMAT = 4;
    public static final int NARROW_STANDALONE = 32772;
    public static final int SHORT_FORMAT = 1;
    public static final int LONG_FORMAT = 2;
    public static final int SHORT_STANDALONE = 32769;
    public static final int LONG_STANDALONE = 32770;
    protected int[] fields;
    protected boolean[] isSet;
    private transient int[] stamp;
    protected long time;
    protected boolean isTimeSet;
    protected boolean areFieldsSet;
    transient boolean areAllFieldsSet;
    private boolean lenient;
    private TimeZone zone;
    private transient boolean sharedZone;
    private int firstDayOfWeek;
    private int minimalDaysInFirstWeek;
    private static final ConcurrentMap<Locale, int[]> cachedLocaleData;
    private static final int UNSET = 0;
    private static final int COMPUTED = 1;
    private static final int MINIMUM_USER_STAMP = 2;
    static final int ALL_FIELDS = 131071;
    private int nextStamp;
    static final int currentSerialVersion = 1;
    private int serialVersionOnStream;
    static final long serialVersionUID = -1807547505821590642L;
    static final int ERA_MASK = 1;
    static final int YEAR_MASK = 2;
    static final int MONTH_MASK = 4;
    static final int WEEK_OF_YEAR_MASK = 8;
    static final int WEEK_OF_MONTH_MASK = 16;
    static final int DAY_OF_MONTH_MASK = 32;
    static final int DATE_MASK = 32;
    static final int DAY_OF_YEAR_MASK = 64;
    static final int DAY_OF_WEEK_MASK = 128;
    static final int DAY_OF_WEEK_IN_MONTH_MASK = 256;
    static final int AM_PM_MASK = 512;
    static final int HOUR_MASK = 1024;
    static final int HOUR_OF_DAY_MASK = 2048;
    static final int MINUTE_MASK = 4096;
    static final int SECOND_MASK = 8192;
    static final int MILLISECOND_MASK = 16384;
    static final int ZONE_OFFSET_MASK = 32768;
    static final int DST_OFFSET_MASK = 65536;
    private static final String[] FIELD_NAME;
    
    protected Calendar() {
        this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
        this.sharedZone = true;
    }
    
    protected Calendar(final TimeZone zone, final Locale weekCountData) {
        this.lenient = true;
        this.sharedZone = false;
        this.nextStamp = 2;
        this.serialVersionOnStream = 1;
        this.fields = new int[17];
        this.isSet = new boolean[17];
        this.stamp = new int[17];
        this.zone = zone;
        this.setWeekCountData(weekCountData);
    }
    
    public static Calendar getInstance() {
        return createCalendar(TimeZone.getDefault(), Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static Calendar getInstance(final TimeZone timeZone) {
        return createCalendar(timeZone, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static Calendar getInstance(final Locale locale) {
        return createCalendar(TimeZone.getDefault(), locale);
    }
    
    public static Calendar getInstance(final TimeZone timeZone, final Locale locale) {
        return createCalendar(timeZone, locale);
    }
    
    private static Calendar createCalendar(final TimeZone timeZone, final Locale locale) {
        final CalendarProvider calendarProvider = LocaleProviderAdapter.getAdapter(CalendarProvider.class, locale).getCalendarProvider();
        if (calendarProvider != null) {
            try {
                return calendarProvider.getInstance(timeZone, locale);
            }
            catch (IllegalArgumentException ex) {}
        }
        Calendar calendar = null;
        if (locale.hasExtensions()) {
            final String unicodeLocaleType = locale.getUnicodeLocaleType("ca");
            if (unicodeLocaleType != null) {
                final String s = unicodeLocaleType;
                switch (s) {
                    case "buddhist": {
                        calendar = new BuddhistCalendar(timeZone, locale);
                        break;
                    }
                    case "japanese": {
                        calendar = new JapaneseImperialCalendar(timeZone, locale);
                        break;
                    }
                    case "gregory": {
                        calendar = new GregorianCalendar(timeZone, locale);
                        break;
                    }
                }
            }
        }
        if (calendar == null) {
            if (locale.getLanguage() == "th" && locale.getCountry() == "TH") {
                calendar = new BuddhistCalendar(timeZone, locale);
            }
            else if (locale.getVariant() == "JP" && locale.getLanguage() == "ja" && locale.getCountry() == "JP") {
                calendar = new JapaneseImperialCalendar(timeZone, locale);
            }
            else {
                calendar = new GregorianCalendar(timeZone, locale);
            }
        }
        return calendar;
    }
    
    public static synchronized Locale[] getAvailableLocales() {
        return DateFormat.getAvailableLocales();
    }
    
    protected abstract void computeTime();
    
    protected abstract void computeFields();
    
    public final Date getTime() {
        return new Date(this.getTimeInMillis());
    }
    
    public final void setTime(final Date date) {
        this.setTimeInMillis(date.getTime());
    }
    
    public long getTimeInMillis() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        return this.time;
    }
    
    public void setTimeInMillis(final long time) {
        if (this.time == time && this.isTimeSet && this.areFieldsSet && this.areAllFieldsSet && this.zone instanceof ZoneInfo && !((ZoneInfo)this.zone).isDirty()) {
            return;
        }
        this.time = time;
        this.isTimeSet = true;
        this.areFieldsSet = false;
        this.computeFields();
        final boolean b = true;
        this.areFieldsSet = b;
        this.areAllFieldsSet = b;
    }
    
    public int get(final int n) {
        this.complete();
        return this.internalGet(n);
    }
    
    protected final int internalGet(final int n) {
        return this.fields[n];
    }
    
    final void internalSet(final int n, final int n2) {
        this.fields[n] = n2;
    }
    
    public void set(final int n, final int n2) {
        if (this.areFieldsSet && !this.areAllFieldsSet) {
            this.computeFields();
        }
        this.internalSet(n, n2);
        this.isTimeSet = false;
        this.areFieldsSet = false;
        this.isSet[n] = true;
        this.stamp[n] = this.nextStamp++;
        if (this.nextStamp == Integer.MAX_VALUE) {
            this.adjustStamp();
        }
    }
    
    public final void set(final int n, final int n2, final int n3) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }
    
    public final void set(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
    }
    
    public final void set(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }
    
    public final void clear() {
        for (int i = 0; i < this.fields.length; this.isSet[i++] = false) {
            this.stamp[i] = (this.fields[i] = 0);
        }
        final boolean b = false;
        this.areFieldsSet = b;
        this.areAllFieldsSet = b;
        this.isTimeSet = false;
    }
    
    public final void clear(final int n) {
        this.fields[n] = 0;
        this.stamp[n] = 0;
        this.isSet[n] = false;
        final boolean b = false;
        this.areFieldsSet = b;
        this.areAllFieldsSet = b;
        this.isTimeSet = false;
    }
    
    public final boolean isSet(final int n) {
        return this.stamp[n] != 0;
    }
    
    public String getDisplayName(final int n, final int n2, final Locale locale) {
        if (!this.checkDisplayNameParams(n, n2, 1, 4, locale, 645)) {
            return null;
        }
        final String calendarType = this.getCalendarType();
        final int value = this.get(n);
        if (this.isStandaloneStyle(n2) || this.isNarrowFormatStyle(n2)) {
            String s = CalendarDataUtility.retrieveFieldValueName(calendarType, n, value, n2, locale);
            if (s == null) {
                if (this.isNarrowFormatStyle(n2)) {
                    s = CalendarDataUtility.retrieveFieldValueName(calendarType, n, value, this.toStandaloneStyle(n2), locale);
                }
                else if (this.isStandaloneStyle(n2)) {
                    s = CalendarDataUtility.retrieveFieldValueName(calendarType, n, value, this.getBaseStyle(n2), locale);
                }
            }
            return s;
        }
        final String[] fieldStrings = this.getFieldStrings(n, n2, DateFormatSymbols.getInstance(locale));
        if (fieldStrings != null && value < fieldStrings.length) {
            return fieldStrings[value];
        }
        return null;
    }
    
    public Map<String, Integer> getDisplayNames(final int n, final int n2, final Locale locale) {
        if (!this.checkDisplayNameParams(n, n2, 0, 4, locale, 645)) {
            return null;
        }
        final String calendarType = this.getCalendarType();
        if (n2 == 0 || this.isStandaloneStyle(n2) || this.isNarrowFormatStyle(n2)) {
            Map<String, Integer> map = CalendarDataUtility.retrieveFieldValueNames(calendarType, n, n2, locale);
            if (map == null) {
                if (this.isNarrowFormatStyle(n2)) {
                    map = CalendarDataUtility.retrieveFieldValueNames(calendarType, n, this.toStandaloneStyle(n2), locale);
                }
                else if (n2 != 0) {
                    map = CalendarDataUtility.retrieveFieldValueNames(calendarType, n, this.getBaseStyle(n2), locale);
                }
            }
            return map;
        }
        return this.getDisplayNamesImpl(n, n2, locale);
    }
    
    private Map<String, Integer> getDisplayNamesImpl(final int n, final int n2, final Locale locale) {
        final String[] fieldStrings = this.getFieldStrings(n, n2, DateFormatSymbols.getInstance(locale));
        if (fieldStrings != null) {
            final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            for (int i = 0; i < fieldStrings.length; ++i) {
                if (fieldStrings[i].length() != 0) {
                    hashMap.put(fieldStrings[i], i);
                }
            }
            return hashMap;
        }
        return null;
    }
    
    boolean checkDisplayNameParams(final int n, final int n2, final int n3, final int n4, final Locale locale, final int n5) {
        final int baseStyle = this.getBaseStyle(n2);
        if (n < 0 || n >= this.fields.length || baseStyle < n3 || baseStyle > n4) {
            throw new IllegalArgumentException();
        }
        if (locale == null) {
            throw new NullPointerException();
        }
        return isFieldSet(n5, n);
    }
    
    private String[] getFieldStrings(final int n, final int n2, final DateFormatSymbols dateFormatSymbols) {
        final int baseStyle = this.getBaseStyle(n2);
        if (baseStyle == 4) {
            return null;
        }
        String[] array = null;
        switch (n) {
            case 0: {
                array = dateFormatSymbols.getEras();
                break;
            }
            case 2: {
                array = ((baseStyle == 2) ? dateFormatSymbols.getMonths() : dateFormatSymbols.getShortMonths());
                break;
            }
            case 7: {
                array = ((baseStyle == 2) ? dateFormatSymbols.getWeekdays() : dateFormatSymbols.getShortWeekdays());
                break;
            }
            case 9: {
                array = dateFormatSymbols.getAmPmStrings();
                break;
            }
        }
        return array;
    }
    
    protected void complete() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        if (!this.areFieldsSet || !this.areAllFieldsSet) {
            this.computeFields();
            final boolean b = true;
            this.areFieldsSet = b;
            this.areAllFieldsSet = b;
        }
    }
    
    final boolean isExternallySet(final int n) {
        return this.stamp[n] >= 2;
    }
    
    final int getSetStateFields() {
        int n = 0;
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.stamp[i] != 0) {
                n |= 1 << i;
            }
        }
        return n;
    }
    
    final void setFieldsComputed(int n) {
        if (n == 131071) {
            for (int i = 0; i < this.fields.length; ++i) {
                this.stamp[i] = 1;
                this.isSet[i] = true;
            }
            final boolean b = true;
            this.areAllFieldsSet = b;
            this.areFieldsSet = b;
        }
        else {
            for (int j = 0; j < this.fields.length; ++j) {
                if ((n & 0x1) == 0x1) {
                    this.stamp[j] = 1;
                    this.isSet[j] = true;
                }
                else if (this.areAllFieldsSet && !this.isSet[j]) {
                    this.areAllFieldsSet = false;
                }
                n >>>= 1;
            }
        }
    }
    
    final void setFieldsNormalized(int n) {
        if (n != 131071) {
            for (int i = 0; i < this.fields.length; ++i) {
                if ((n & 0x1) == 0x0) {
                    this.stamp[i] = (this.fields[i] = 0);
                    this.isSet[i] = false;
                }
                n >>= 1;
            }
        }
        this.areFieldsSet = true;
        this.areAllFieldsSet = false;
    }
    
    final boolean isPartiallyNormalized() {
        return this.areFieldsSet && !this.areAllFieldsSet;
    }
    
    final boolean isFullyNormalized() {
        return this.areFieldsSet && this.areAllFieldsSet;
    }
    
    final void setUnnormalized() {
        final boolean b = false;
        this.areAllFieldsSet = b;
        this.areFieldsSet = b;
    }
    
    static boolean isFieldSet(final int n, final int n2) {
        return (n & 1 << n2) != 0x0;
    }
    
    final int selectFields() {
        int n = 2;
        if (this.stamp[0] != 0) {
            n |= 0x1;
        }
        final int n2 = this.stamp[7];
        final int n3 = this.stamp[2];
        int n4 = this.stamp[5];
        int aggregateStamp = aggregateStamp(this.stamp[4], n2);
        int n5 = aggregateStamp(this.stamp[8], n2);
        final int n6 = this.stamp[6];
        int aggregateStamp2 = aggregateStamp(this.stamp[3], n2);
        int max = n4;
        if (aggregateStamp > max) {
            max = aggregateStamp;
        }
        if (n5 > max) {
            max = n5;
        }
        if (n6 > max) {
            max = n6;
        }
        if (aggregateStamp2 > max) {
            max = aggregateStamp2;
        }
        if (max == 0) {
            aggregateStamp = this.stamp[4];
            n5 = Math.max(this.stamp[8], n2);
            aggregateStamp2 = this.stamp[3];
            max = Math.max(Math.max(aggregateStamp, n5), aggregateStamp2);
            if (max == 0) {
                n4 = (max = n3);
            }
        }
        int n7;
        if (max == n4 || (max == aggregateStamp && this.stamp[4] >= this.stamp[3]) || (max == n5 && this.stamp[8] >= this.stamp[3])) {
            n7 = (n | 0x4);
            if (max == n4) {
                n7 |= 0x20;
            }
            else {
                assert max == n5;
                if (n2 != 0) {
                    n7 |= 0x80;
                }
                if (aggregateStamp == n5) {
                    if (this.stamp[4] >= this.stamp[8]) {
                        n7 |= 0x10;
                    }
                    else {
                        n7 |= 0x100;
                    }
                }
                else if (max == aggregateStamp) {
                    n7 |= 0x10;
                }
                else {
                    assert max == n5;
                    if (this.stamp[8] != 0) {
                        n7 |= 0x100;
                    }
                }
            }
        }
        else {
            assert max == 0;
            if (max == n6) {
                n7 = (n | 0x40);
            }
            else {
                assert max == aggregateStamp2;
                if (n2 != 0) {
                    n |= 0x80;
                }
                n7 = (n | 0x8);
            }
        }
        final int n8 = this.stamp[11];
        final int aggregateStamp3 = aggregateStamp(this.stamp[10], this.stamp[9]);
        int max2 = (aggregateStamp3 > n8) ? aggregateStamp3 : n8;
        if (max2 == 0) {
            max2 = Math.max(this.stamp[10], this.stamp[9]);
        }
        if (max2 != 0) {
            if (max2 == n8) {
                n7 |= 0x800;
            }
            else {
                n7 |= 0x400;
                if (this.stamp[9] != 0) {
                    n7 |= 0x200;
                }
            }
        }
        if (this.stamp[12] != 0) {
            n7 |= 0x1000;
        }
        if (this.stamp[13] != 0) {
            n7 |= 0x2000;
        }
        if (this.stamp[14] != 0) {
            n7 |= 0x4000;
        }
        if (this.stamp[15] >= 2) {
            n7 |= 0x8000;
        }
        if (this.stamp[16] >= 2) {
            n7 |= 0x10000;
        }
        return n7;
    }
    
    int getBaseStyle(final int n) {
        return n & 0xFFFF7FFF;
    }
    
    private int toStandaloneStyle(final int n) {
        return n | 0x8000;
    }
    
    private boolean isStandaloneStyle(final int n) {
        return (n & 0x8000) != 0x0;
    }
    
    private boolean isNarrowStyle(final int n) {
        return n == 4 || n == 32772;
    }
    
    private boolean isNarrowFormatStyle(final int n) {
        return n == 4;
    }
    
    private static int aggregateStamp(final int n, final int n2) {
        if (n == 0 || n2 == 0) {
            return 0;
        }
        return (n > n2) ? n : n2;
    }
    
    public static Set<String> getAvailableCalendarTypes() {
        return AvailableCalendarTypes.SET;
    }
    
    public String getCalendarType() {
        return this.getClass().getName();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        try {
            final Calendar calendar = (Calendar)o;
            return this.compareTo(getMillisOf(calendar)) == 0 && this.lenient == calendar.lenient && this.firstDayOfWeek == calendar.firstDayOfWeek && this.minimalDaysInFirstWeek == calendar.minimalDaysInFirstWeek && this.zone.equals(calendar.zone);
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        final int n = (this.lenient ? 1 : 0) | this.firstDayOfWeek << 1 | this.minimalDaysInFirstWeek << 4 | this.zone.hashCode() << 7;
        final long millis = getMillisOf(this);
        return (int)millis ^ (int)(millis >> 32) ^ n;
    }
    
    public boolean before(final Object o) {
        return o instanceof Calendar && this.compareTo((Calendar)o) < 0;
    }
    
    public boolean after(final Object o) {
        return o instanceof Calendar && this.compareTo((Calendar)o) > 0;
    }
    
    @Override
    public int compareTo(final Calendar calendar) {
        return this.compareTo(getMillisOf(calendar));
    }
    
    public abstract void add(final int p0, final int p1);
    
    public abstract void roll(final int p0, final boolean p1);
    
    public void roll(final int n, int i) {
        while (i > 0) {
            this.roll(n, true);
            --i;
        }
        while (i < 0) {
            this.roll(n, false);
            ++i;
        }
    }
    
    public void setTimeZone(final TimeZone zone) {
        this.zone = zone;
        this.sharedZone = false;
        final boolean b = false;
        this.areFieldsSet = b;
        this.areAllFieldsSet = b;
    }
    
    public TimeZone getTimeZone() {
        if (this.sharedZone) {
            this.zone = (TimeZone)this.zone.clone();
            this.sharedZone = false;
        }
        return this.zone;
    }
    
    TimeZone getZone() {
        return this.zone;
    }
    
    void setZoneShared(final boolean sharedZone) {
        this.sharedZone = sharedZone;
    }
    
    public void setLenient(final boolean lenient) {
        this.lenient = lenient;
    }
    
    public boolean isLenient() {
        return this.lenient;
    }
    
    public void setFirstDayOfWeek(final int firstDayOfWeek) {
        if (this.firstDayOfWeek == firstDayOfWeek) {
            return;
        }
        this.firstDayOfWeek = firstDayOfWeek;
        this.invalidateWeekFields();
    }
    
    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }
    
    public void setMinimalDaysInFirstWeek(final int minimalDaysInFirstWeek) {
        if (this.minimalDaysInFirstWeek == minimalDaysInFirstWeek) {
            return;
        }
        this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
        this.invalidateWeekFields();
    }
    
    public int getMinimalDaysInFirstWeek() {
        return this.minimalDaysInFirstWeek;
    }
    
    public boolean isWeekDateSupported() {
        return false;
    }
    
    public int getWeekYear() {
        throw new UnsupportedOperationException();
    }
    
    public void setWeekDate(final int n, final int n2, final int n3) {
        throw new UnsupportedOperationException();
    }
    
    public int getWeeksInWeekYear() {
        throw new UnsupportedOperationException();
    }
    
    public abstract int getMinimum(final int p0);
    
    public abstract int getMaximum(final int p0);
    
    public abstract int getGreatestMinimum(final int p0);
    
    public abstract int getLeastMaximum(final int p0);
    
    public int getActualMinimum(final int n) {
        int greatestMinimum = this.getGreatestMinimum(n);
        final int minimum = this.getMinimum(n);
        if (greatestMinimum == minimum) {
            return greatestMinimum;
        }
        final Calendar calendar = (Calendar)this.clone();
        calendar.setLenient(true);
        int n2 = greatestMinimum;
        do {
            calendar.set(n, greatestMinimum);
            if (calendar.get(n) != greatestMinimum) {
                break;
            }
            n2 = greatestMinimum;
        } while (--greatestMinimum >= minimum);
        return n2;
    }
    
    public int getActualMaximum(final int n) {
        int leastMaximum = this.getLeastMaximum(n);
        final int maximum = this.getMaximum(n);
        if (leastMaximum == maximum) {
            return leastMaximum;
        }
        final Calendar calendar = (Calendar)this.clone();
        calendar.setLenient(true);
        if (n == 3 || n == 4) {
            calendar.set(7, this.firstDayOfWeek);
        }
        int n2 = leastMaximum;
        do {
            calendar.set(n, leastMaximum);
            if (calendar.get(n) != leastMaximum) {
                break;
            }
            n2 = leastMaximum;
        } while (++leastMaximum <= maximum);
        return n2;
    }
    
    public Object clone() {
        try {
            final Calendar calendar = (Calendar)super.clone();
            calendar.fields = new int[17];
            calendar.isSet = new boolean[17];
            calendar.stamp = new int[17];
            for (int i = 0; i < 17; ++i) {
                calendar.fields[i] = this.fields[i];
                calendar.stamp[i] = this.stamp[i];
                calendar.isSet[i] = this.isSet[i];
            }
            calendar.zone = (TimeZone)this.zone.clone();
            return calendar;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    static String getFieldName(final int n) {
        return Calendar.FIELD_NAME[n];
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(800);
        sb.append(this.getClass().getName()).append('[');
        appendValue(sb, "time", this.isTimeSet, this.time);
        sb.append(",areFieldsSet=").append(this.areFieldsSet);
        sb.append(",areAllFieldsSet=").append(this.areAllFieldsSet);
        sb.append(",lenient=").append(this.lenient);
        sb.append(",zone=").append(this.zone);
        appendValue(sb, ",firstDayOfWeek", true, this.firstDayOfWeek);
        appendValue(sb, ",minimalDaysInFirstWeek", true, this.minimalDaysInFirstWeek);
        for (int i = 0; i < 17; ++i) {
            sb.append(',');
            appendValue(sb, Calendar.FIELD_NAME[i], this.isSet(i), this.fields[i]);
        }
        sb.append(']');
        return sb.toString();
    }
    
    private static void appendValue(final StringBuilder sb, final String s, final boolean b, final long n) {
        sb.append(s).append('=');
        if (b) {
            sb.append(n);
        }
        else {
            sb.append('?');
        }
    }
    
    private void setWeekCountData(final Locale locale) {
        int[] array = Calendar.cachedLocaleData.get(locale);
        if (array == null) {
            array = new int[] { CalendarDataUtility.retrieveFirstDayOfWeek(locale), CalendarDataUtility.retrieveMinimalDaysInFirstWeek(locale) };
            Calendar.cachedLocaleData.putIfAbsent(locale, array);
        }
        this.firstDayOfWeek = array[0];
        this.minimalDaysInFirstWeek = array[1];
    }
    
    private void updateTime() {
        this.computeTime();
        this.isTimeSet = true;
    }
    
    private int compareTo(final long n) {
        final long millis = getMillisOf(this);
        return (millis > n) ? 1 : ((millis == n) ? 0 : -1);
    }
    
    private static long getMillisOf(final Calendar calendar) {
        if (calendar.isTimeSet) {
            return calendar.time;
        }
        final Calendar calendar2 = (Calendar)calendar.clone();
        calendar2.setLenient(true);
        return calendar2.getTimeInMillis();
    }
    
    private void adjustStamp() {
        int n = 2;
        int nextStamp = 2;
        int n2;
        do {
            n2 = Integer.MAX_VALUE;
            for (int i = 0; i < this.stamp.length; ++i) {
                final int n3 = this.stamp[i];
                if (n3 >= nextStamp && n2 > n3) {
                    n2 = n3;
                }
                if (n < n3) {
                    n = n3;
                }
            }
            if (n != n2 && n2 == Integer.MAX_VALUE) {
                break;
            }
            for (int j = 0; j < this.stamp.length; ++j) {
                if (this.stamp[j] == n2) {
                    this.stamp[j] = nextStamp;
                }
            }
            ++nextStamp;
        } while (n2 != n);
        this.nextStamp = nextStamp;
    }
    
    private void invalidateWeekFields() {
        if (this.stamp[4] != 1 && this.stamp[3] != 1) {
            return;
        }
        final Calendar calendar = (Calendar)this.clone();
        calendar.setLenient(true);
        calendar.clear(4);
        calendar.clear(3);
        if (this.stamp[4] == 1) {
            final int value = calendar.get(4);
            if (this.fields[4] != value) {
                this.fields[4] = value;
            }
        }
        if (this.stamp[3] == 1) {
            final int value2 = calendar.get(3);
            if (this.fields[3] != value2) {
                this.fields[3] = value2;
            }
        }
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (!this.isTimeSet) {
            try {
                this.updateTime();
            }
            catch (IllegalArgumentException ex) {}
        }
        TimeZone zone = null;
        if (this.zone instanceof ZoneInfo) {
            SimpleTimeZone lastRuleInstance = ((ZoneInfo)this.zone).getLastRuleInstance();
            if (lastRuleInstance == null) {
                lastRuleInstance = new SimpleTimeZone(this.zone.getRawOffset(), this.zone.getID());
            }
            zone = this.zone;
            this.zone = lastRuleInstance;
        }
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(zone);
        if (zone != null) {
            this.zone = zone;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.stamp = new int[17];
        if (this.serialVersionOnStream >= 2) {
            this.isTimeSet = true;
            if (this.fields == null) {
                this.fields = new int[17];
            }
            if (this.isSet == null) {
                this.isSet = new boolean[17];
            }
        }
        else if (this.serialVersionOnStream >= 0) {
            for (int i = 0; i < 17; ++i) {
                this.stamp[i] = (this.isSet[i] ? 1 : 0);
            }
        }
        this.serialVersionOnStream = 1;
        TimeZone zone = null;
        try {
            zone = AccessController.doPrivileged((PrivilegedExceptionAction<ZoneInfo>)new PrivilegedExceptionAction<ZoneInfo>() {
                @Override
                public ZoneInfo run() throws Exception {
                    return (ZoneInfo)objectInputStream.readObject();
                }
            }, CalendarAccessControlContext.INSTANCE);
        }
        catch (PrivilegedActionException ex) {
            final Exception exception = ex.getException();
            if (!(exception instanceof OptionalDataException)) {
                if (exception instanceof RuntimeException) {
                    throw (RuntimeException)exception;
                }
                if (exception instanceof IOException) {
                    throw (IOException)exception;
                }
                if (exception instanceof ClassNotFoundException) {
                    throw (ClassNotFoundException)exception;
                }
                throw new RuntimeException(exception);
            }
        }
        if (zone != null) {
            this.zone = zone;
        }
        if (this.zone instanceof SimpleTimeZone) {
            final String id = this.zone.getID();
            final TimeZone timeZone = TimeZone.getTimeZone(id);
            if (timeZone != null && timeZone.hasSameRules(this.zone) && timeZone.getID().equals(id)) {
                this.zone = timeZone;
            }
        }
    }
    
    public final Instant toInstant() {
        return Instant.ofEpochMilli(this.getTimeInMillis());
    }
    
    static {
        cachedLocaleData = new ConcurrentHashMap<Locale, int[]>(3);
        FIELD_NAME = new String[] { "ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH", "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR", "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET", "DST_OFFSET" };
    }
    
    private static class AvailableCalendarTypes
    {
        private static final Set<String> SET;
        
        static {
            final HashSet<String> set = new HashSet<String>(3);
            set.add("gregory");
            set.add("buddhist");
            set.add("japanese");
            SET = Collections.unmodifiableSet((Set<?>)set);
        }
    }
    
    public static class Builder
    {
        private static final int NFIELDS = 18;
        private static final int WEEK_YEAR = 17;
        private long instant;
        private int[] fields;
        private int nextStamp;
        private int maxFieldIndex;
        private String type;
        private TimeZone zone;
        private boolean lenient;
        private Locale locale;
        private int firstDayOfWeek;
        private int minimalDaysInFirstWeek;
        
        public Builder() {
            this.lenient = true;
        }
        
        public Builder setInstant(final long instant) {
            if (this.fields != null) {
                throw new IllegalStateException();
            }
            this.instant = instant;
            this.nextStamp = 1;
            return this;
        }
        
        public Builder setInstant(final Date date) {
            return this.setInstant(date.getTime());
        }
        
        public Builder set(final int n, final int n2) {
            if (n < 0 || n >= 17) {
                throw new IllegalArgumentException("field is invalid");
            }
            if (this.isInstantSet()) {
                throw new IllegalStateException("instant has been set");
            }
            this.allocateFields();
            this.internalSet(n, n2);
            return this;
        }
        
        public Builder setFields(final int... array) {
            final int length = array.length;
            if (length % 2 != 0) {
                throw new IllegalArgumentException();
            }
            if (this.isInstantSet()) {
                throw new IllegalStateException("instant has been set");
            }
            if (this.nextStamp + length / 2 < 0) {
                throw new IllegalStateException("stamp counter overflow");
            }
            this.allocateFields();
            int i = 0;
            while (i < length) {
                final int n = array[i++];
                if (n < 0 || n >= 17) {
                    throw new IllegalArgumentException("field is invalid");
                }
                this.internalSet(n, array[i++]);
            }
            return this;
        }
        
        public Builder setDate(final int n, final int n2, final int n3) {
            return this.setFields(1, n, 2, n2, 5, n3);
        }
        
        public Builder setTimeOfDay(final int n, final int n2, final int n3) {
            return this.setTimeOfDay(n, n2, n3, 0);
        }
        
        public Builder setTimeOfDay(final int n, final int n2, final int n3, final int n4) {
            return this.setFields(11, n, 12, n2, 13, n3, 14, n4);
        }
        
        public Builder setWeekDate(final int n, final int n2, final int n3) {
            this.allocateFields();
            this.internalSet(17, n);
            this.internalSet(3, n2);
            this.internalSet(7, n3);
            return this;
        }
        
        public Builder setTimeZone(final TimeZone zone) {
            if (zone == null) {
                throw new NullPointerException();
            }
            this.zone = zone;
            return this;
        }
        
        public Builder setLenient(final boolean lenient) {
            this.lenient = lenient;
            return this;
        }
        
        public Builder setCalendarType(String type) {
            if (type.equals("gregorian")) {
                type = "gregory";
            }
            if (!Calendar.getAvailableCalendarTypes().contains(type) && !type.equals("iso8601")) {
                throw new IllegalArgumentException("unknown calendar type: " + type);
            }
            if (this.type == null) {
                this.type = type;
            }
            else if (!this.type.equals(type)) {
                throw new IllegalStateException("calendar type override");
            }
            return this;
        }
        
        public Builder setLocale(final Locale locale) {
            if (locale == null) {
                throw new NullPointerException();
            }
            this.locale = locale;
            return this;
        }
        
        public Builder setWeekDefinition(final int firstDayOfWeek, final int minimalDaysInFirstWeek) {
            if (!this.isValidWeekParameter(firstDayOfWeek) || !this.isValidWeekParameter(minimalDaysInFirstWeek)) {
                throw new IllegalArgumentException();
            }
            this.firstDayOfWeek = firstDayOfWeek;
            this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
            return this;
        }
        
        public Calendar build() {
            if (this.locale == null) {
                this.locale = Locale.getDefault();
            }
            if (this.zone == null) {
                this.zone = TimeZone.getDefault();
            }
            if (this.type == null) {
                this.type = this.locale.getUnicodeLocaleType("ca");
            }
            if (this.type == null) {
                if (this.locale.getCountry() == "TH" && this.locale.getLanguage() == "th") {
                    this.type = "buddhist";
                }
                else {
                    this.type = "gregory";
                }
            }
            final String type = this.type;
            Calendar calendar = null;
            switch (type) {
                case "gregory": {
                    calendar = new GregorianCalendar(this.zone, this.locale, true);
                    break;
                }
                case "iso8601": {
                    final GregorianCalendar gregorianCalendar = new GregorianCalendar(this.zone, this.locale, true);
                    gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
                    this.setWeekDefinition(2, 4);
                    calendar = gregorianCalendar;
                    break;
                }
                case "buddhist": {
                    calendar = new BuddhistCalendar(this.zone, this.locale);
                    calendar.clear();
                    break;
                }
                case "japanese": {
                    calendar = new JapaneseImperialCalendar(this.zone, this.locale, true);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unknown calendar type: " + this.type);
                }
            }
            calendar.setLenient(this.lenient);
            if (this.firstDayOfWeek != 0) {
                calendar.setFirstDayOfWeek(this.firstDayOfWeek);
                calendar.setMinimalDaysInFirstWeek(this.minimalDaysInFirstWeek);
            }
            if (this.isInstantSet()) {
                calendar.setTimeInMillis(this.instant);
                calendar.complete();
                return calendar;
            }
            if (this.fields != null) {
                final boolean b = this.isSet(17) && this.fields[17] > this.fields[1];
                if (b && !calendar.isWeekDateSupported()) {
                    throw new IllegalArgumentException("week date is unsupported by " + this.type);
                }
                for (int i = 2; i < this.nextStamp; ++i) {
                    for (int j = 0; j <= this.maxFieldIndex; ++j) {
                        if (this.fields[j] == i) {
                            calendar.set(j, this.fields[18 + j]);
                            break;
                        }
                    }
                }
                if (b) {
                    calendar.setWeekDate(this.fields[35], this.isSet(3) ? this.fields[21] : 1, this.isSet(7) ? this.fields[25] : calendar.getFirstDayOfWeek());
                }
                calendar.complete();
            }
            return calendar;
        }
        
        private void allocateFields() {
            if (this.fields == null) {
                this.fields = new int[36];
                this.nextStamp = 2;
                this.maxFieldIndex = -1;
            }
        }
        
        private void internalSet(final int maxFieldIndex, final int n) {
            this.fields[maxFieldIndex] = this.nextStamp++;
            if (this.nextStamp < 0) {
                throw new IllegalStateException("stamp counter overflow");
            }
            this.fields[18 + maxFieldIndex] = n;
            if (maxFieldIndex > this.maxFieldIndex && maxFieldIndex < 17) {
                this.maxFieldIndex = maxFieldIndex;
            }
        }
        
        private boolean isInstantSet() {
            return this.nextStamp == 1;
        }
        
        private boolean isSet(final int n) {
            return this.fields != null && this.fields[n] > 0;
        }
        
        private boolean isValidWeekParameter(final int n) {
            return n > 0 && n <= 7;
        }
    }
    
    private static class CalendarAccessControlContext
    {
        private static final AccessControlContext INSTANCE;
        
        static {
            final RuntimePermission runtimePermission = new RuntimePermission("accessClassInPackage.sun.util.calendar");
            final PermissionCollection permissionCollection = runtimePermission.newPermissionCollection();
            permissionCollection.add(runtimePermission);
            INSTANCE = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, permissionCollection) });
        }
    }
}
