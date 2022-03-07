package android.text.format;

@Deprecated
public class Time
{
    public static final int EPOCH_JULIAN_DAY = 2440588;
    public static final int FRIDAY = 5;
    public static final int HOUR = 3;
    public static final int MINUTE = 2;
    public static final int MONDAY = 1;
    public static final int MONDAY_BEFORE_JULIAN_EPOCH = 2440585;
    public static final int MONTH = 5;
    public static final int MONTH_DAY = 4;
    public static final int SATURDAY = 6;
    public static final int SECOND = 1;
    public static final int SUNDAY = 0;
    public static final int THURSDAY = 4;
    public static final String TIMEZONE_UTC = "UTC";
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int WEEK_DAY = 7;
    public static final int WEEK_NUM = 9;
    public static final int YEAR = 6;
    public static final int YEAR_DAY = 8;
    public boolean allDay;
    public long gmtoff;
    public int hour;
    public int isDst;
    public int minute;
    public int month;
    public int monthDay;
    public int second;
    public String timezone;
    public int weekDay;
    public int year;
    public int yearDay;
    
    public Time(final String timezoneId) {
        throw new RuntimeException("Stub!");
    }
    
    public Time() {
        throw new RuntimeException("Stub!");
    }
    
    public Time(final Time other) {
        throw new RuntimeException("Stub!");
    }
    
    public long normalize(final boolean ignoreDst) {
        throw new RuntimeException("Stub!");
    }
    
    public void switchTimezone(final String timezone) {
        throw new RuntimeException("Stub!");
    }
    
    public int getActualMaximum(final int field) {
        throw new RuntimeException("Stub!");
    }
    
    public void clear(final String timezoneId) {
        throw new RuntimeException("Stub!");
    }
    
    public static int compare(final Time a, final Time b) {
        throw new RuntimeException("Stub!");
    }
    
    public String format(final String format) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean parse(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean parse3339(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getCurrentTimezone() {
        throw new RuntimeException("Stub!");
    }
    
    public void setToNow() {
        throw new RuntimeException("Stub!");
    }
    
    public long toMillis(final boolean ignoreDst) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final long millis) {
        throw new RuntimeException("Stub!");
    }
    
    public String format2445() {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final Time that) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final int second, final int minute, final int hour, final int monthDay, final int month, final int year) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final int monthDay, final int month, final int year) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean before(final Time that) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean after(final Time that) {
        throw new RuntimeException("Stub!");
    }
    
    public int getWeekNumber() {
        throw new RuntimeException("Stub!");
    }
    
    public String format3339(final boolean allDay) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isEpoch(final Time time) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getJulianDay(final long millis, final long gmtoff) {
        throw new RuntimeException("Stub!");
    }
    
    public long setJulianDay(final int julianDay) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getWeeksSinceEpochFromJulianDay(final int julianDay, final int firstDayOfWeek) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getJulianMondayFromWeeksSinceEpoch(final int week) {
        throw new RuntimeException("Stub!");
    }
}
