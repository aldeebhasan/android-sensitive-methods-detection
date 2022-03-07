package java.util;

import java.text.*;
import sun.util.calendar.*;
import java.io.*;
import java.time.*;

public class Date implements Serializable, Cloneable, Comparable<Date>
{
    private static final BaseCalendar gcal;
    private static BaseCalendar jcal;
    private transient long fastTime;
    private transient BaseCalendar.Date cdate;
    private static int defaultCenturyStart;
    private static final long serialVersionUID = 7523967970034938905L;
    private static final String[] wtb;
    private static final int[] ttb;
    
    public Date() {
        this(System.currentTimeMillis());
    }
    
    public Date(final long fastTime) {
        this.fastTime = fastTime;
    }
    
    public Date(final int n, final int n2, final int n3) {
        this(n, n2, n3, 0, 0, 0);
    }
    
    public Date(final int n, final int n2, final int n3, final int n4, final int n5) {
        this(n, n2, n3, n4, n5, 0);
    }
    
    public Date(final int n, int mod, final int n2, final int n3, final int n4, final int n5) {
        int n6 = n + 1900;
        if (mod >= 12) {
            n6 += mod / 12;
            mod %= 12;
        }
        else if (mod < 0) {
            n6 += CalendarUtils.floorDivide(mod, 12);
            mod = CalendarUtils.mod(mod, 12);
        }
        this.cdate = (BaseCalendar.Date)getCalendarSystem(n6).newCalendarDate(TimeZone.getDefaultRef());
        this.cdate.setNormalizedDate(n6, mod + 1, n2).setTimeOfDay(n3, n4, n5, 0);
        this.getTimeImpl();
        this.cdate = null;
    }
    
    public Date(final String s) {
        this(parse(s));
    }
    
    public Object clone() {
        Date date = null;
        try {
            date = (Date)super.clone();
            if (this.cdate != null) {
                date.cdate = (BaseCalendar.Date)this.cdate.clone();
            }
        }
        catch (CloneNotSupportedException ex) {}
        return date;
    }
    
    @Deprecated
    public static long UTC(final int n, int mod, final int n2, final int n3, final int n4, final int n5) {
        int n6 = n + 1900;
        if (mod >= 12) {
            n6 += mod / 12;
            mod %= 12;
        }
        else if (mod < 0) {
            n6 += CalendarUtils.floorDivide(mod, 12);
            mod = CalendarUtils.mod(mod, 12);
        }
        final int n7 = mod + 1;
        final BaseCalendar.Date date = (BaseCalendar.Date)getCalendarSystem(n6).newCalendarDate(null);
        date.setNormalizedDate(n6, n7, n2).setTimeOfDay(n3, n4, n5, 0);
        final Date date2 = new Date(0L);
        date2.normalize(date);
        return date2.fastTime;
    }
    
    @Deprecated
    public static long parse(final String s) {
        int n = Integer.MIN_VALUE;
        int n2 = -1;
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        int i = 0;
        int n7 = -1;
        int n8 = 0;
        if (s != null) {
            final int length = s.length();
            while (i < length) {
                char c = s.charAt(i);
                ++i;
                if (c > ' ') {
                    if (c == ',') {
                        continue;
                    }
                    if (c == '(') {
                        int n9 = 1;
                        while (i < length) {
                            final char char1 = s.charAt(i);
                            ++i;
                            if (char1 == '(') {
                                ++n9;
                            }
                            else {
                                if (char1 == ')' && --n9 <= 0) {
                                    break;
                                }
                                continue;
                            }
                        }
                    }
                    else if ('0' <= c && c <= '9') {
                        int n10 = c - '0';
                        while (i < length && '0' <= (c = s.charAt(i)) && c <= '9') {
                            n10 = n10 * 10 + c - 48;
                            ++i;
                        }
                        if (n8 == 43 || (n8 == 45 && n != Integer.MIN_VALUE)) {
                            int n11;
                            if (n10 < 24) {
                                n11 = n10 * 60;
                            }
                            else {
                                n11 = n10 % 100 + n10 / 100 * 60;
                            }
                            if (n8 == 43) {
                                n11 = -n11;
                            }
                            if (n7 != 0 && n7 != -1) {
                                throw new IllegalArgumentException();
                            }
                            n7 = n11;
                        }
                        else if (n10 >= 70) {
                            if (n != Integer.MIN_VALUE) {
                                throw new IllegalArgumentException();
                            }
                            if (c > ' ' && c != ',' && c != '/' && i < length) {
                                throw new IllegalArgumentException();
                            }
                            n = n10;
                        }
                        else if (c == ':') {
                            if (n4 < 0) {
                                n4 = (byte)n10;
                            }
                            else {
                                if (n5 >= 0) {
                                    throw new IllegalArgumentException();
                                }
                                n5 = (byte)n10;
                            }
                        }
                        else if (c == '/') {
                            if (n2 < 0) {
                                n2 = (byte)(n10 - 1);
                            }
                            else {
                                if (n3 >= 0) {
                                    throw new IllegalArgumentException();
                                }
                                n3 = (byte)n10;
                            }
                        }
                        else {
                            if (i < length && c != ',' && c > ' ' && c != '-') {
                                throw new IllegalArgumentException();
                            }
                            if (n4 >= 0 && n5 < 0) {
                                n5 = (byte)n10;
                            }
                            else if (n5 >= 0 && n6 < 0) {
                                n6 = (byte)n10;
                            }
                            else if (n3 < 0) {
                                n3 = (byte)n10;
                            }
                            else {
                                if (n != Integer.MIN_VALUE || n2 < 0 || n3 < 0) {
                                    throw new IllegalArgumentException();
                                }
                                n = n10;
                            }
                        }
                        n8 = 0;
                    }
                    else if (c == '/' || c == ':' || c == '+' || c == '-') {
                        n8 = c;
                    }
                    else {
                        final int n12 = i - 1;
                        while (i < length) {
                            final char char2 = s.charAt(i);
                            if ('A' > char2 || char2 > 'Z') {
                                if ('a' > char2) {
                                    break;
                                }
                                if (char2 > 'z') {
                                    break;
                                }
                            }
                            ++i;
                        }
                        if (i <= n12 + 1) {
                            throw new IllegalArgumentException();
                        }
                        int length2 = Date.wtb.length;
                        while (--length2 >= 0) {
                            if (Date.wtb[length2].regionMatches(true, 0, s, n12, i - n12)) {
                                final int n13 = Date.ttb[length2];
                                if (n13 == 0) {
                                    break;
                                }
                                if (n13 == 1) {
                                    if (n4 > 12) {
                                        throw new IllegalArgumentException();
                                    }
                                    if (n4 < 1) {
                                        throw new IllegalArgumentException();
                                    }
                                    if (n4 < 12) {
                                        n4 += 12;
                                        break;
                                    }
                                    break;
                                }
                                else if (n13 == 14) {
                                    if (n4 > 12) {
                                        throw new IllegalArgumentException();
                                    }
                                    if (n4 < 1) {
                                        throw new IllegalArgumentException();
                                    }
                                    if (n4 == 12) {
                                        n4 = 0;
                                        break;
                                    }
                                    break;
                                }
                                else {
                                    if (n13 > 13) {
                                        n7 = n13 - 10000;
                                        break;
                                    }
                                    if (n2 < 0) {
                                        n2 = (byte)(n13 - 2);
                                        break;
                                    }
                                    throw new IllegalArgumentException();
                                }
                            }
                        }
                        if (length2 < 0) {
                            throw new IllegalArgumentException();
                        }
                        n8 = 0;
                    }
                }
            }
            if (n != Integer.MIN_VALUE && n2 >= 0) {
                if (n3 >= 0) {
                    if (n < 100) {
                        synchronized (Date.class) {
                            if (Date.defaultCenturyStart == 0) {
                                Date.defaultCenturyStart = Date.gcal.getCalendarDate().getYear() - 80;
                            }
                        }
                        n += Date.defaultCenturyStart / 100 * 100;
                        if (n < Date.defaultCenturyStart) {
                            n += 100;
                        }
                    }
                    if (n6 < 0) {
                        n6 = 0;
                    }
                    if (n5 < 0) {
                        n5 = 0;
                    }
                    if (n4 < 0) {
                        n4 = 0;
                    }
                    final BaseCalendar calendarSystem = getCalendarSystem(n);
                    if (n7 == -1) {
                        final BaseCalendar.Date date = (BaseCalendar.Date)calendarSystem.newCalendarDate(TimeZone.getDefaultRef());
                        date.setDate(n, n2 + 1, n3);
                        date.setTimeOfDay(n4, n5, n6, 0);
                        return calendarSystem.getTime(date);
                    }
                    final BaseCalendar.Date date2 = (BaseCalendar.Date)calendarSystem.newCalendarDate(null);
                    date2.setDate(n, n2 + 1, n3);
                    date2.setTimeOfDay(n4, n5, n6, 0);
                    return calendarSystem.getTime(date2) + n7 * 60000;
                }
            }
        }
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    public int getYear() {
        return this.normalize().getYear() - 1900;
    }
    
    @Deprecated
    public void setYear(final int n) {
        this.getCalendarDate().setNormalizedYear(n + 1900);
    }
    
    @Deprecated
    public int getMonth() {
        return this.normalize().getMonth() - 1;
    }
    
    @Deprecated
    public void setMonth(int mod) {
        int floorDivide = 0;
        if (mod >= 12) {
            floorDivide = mod / 12;
            mod %= 12;
        }
        else if (mod < 0) {
            floorDivide = CalendarUtils.floorDivide(mod, 12);
            mod = CalendarUtils.mod(mod, 12);
        }
        final BaseCalendar.Date calendarDate = this.getCalendarDate();
        if (floorDivide != 0) {
            calendarDate.setNormalizedYear(calendarDate.getNormalizedYear() + floorDivide);
        }
        calendarDate.setMonth(mod + 1);
    }
    
    @Deprecated
    public int getDate() {
        return this.normalize().getDayOfMonth();
    }
    
    @Deprecated
    public void setDate(final int dayOfMonth) {
        this.getCalendarDate().setDayOfMonth(dayOfMonth);
    }
    
    @Deprecated
    public int getDay() {
        return this.normalize().getDayOfWeek() - 1;
    }
    
    @Deprecated
    public int getHours() {
        return this.normalize().getHours();
    }
    
    @Deprecated
    public void setHours(final int hours) {
        this.getCalendarDate().setHours(hours);
    }
    
    @Deprecated
    public int getMinutes() {
        return this.normalize().getMinutes();
    }
    
    @Deprecated
    public void setMinutes(final int minutes) {
        this.getCalendarDate().setMinutes(minutes);
    }
    
    @Deprecated
    public int getSeconds() {
        return this.normalize().getSeconds();
    }
    
    @Deprecated
    public void setSeconds(final int seconds) {
        this.getCalendarDate().setSeconds(seconds);
    }
    
    public long getTime() {
        return this.getTimeImpl();
    }
    
    private final long getTimeImpl() {
        if (this.cdate != null && !this.cdate.isNormalized()) {
            this.normalize();
        }
        return this.fastTime;
    }
    
    public void setTime(final long fastTime) {
        this.fastTime = fastTime;
        this.cdate = null;
    }
    
    public boolean before(final Date date) {
        return getMillisOf(this) < getMillisOf(date);
    }
    
    public boolean after(final Date date) {
        return getMillisOf(this) > getMillisOf(date);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Date && this.getTime() == ((Date)o).getTime();
    }
    
    static final long getMillisOf(final Date date) {
        if (date.cdate == null || date.cdate.isNormalized()) {
            return date.fastTime;
        }
        return Date.gcal.getTime((CalendarDate)date.cdate.clone());
    }
    
    @Override
    public int compareTo(final Date date) {
        final long millis = getMillisOf(this);
        final long millis2 = getMillisOf(date);
        return (millis < millis2) ? -1 : ((millis == millis2) ? false : true);
    }
    
    @Override
    public int hashCode() {
        final long time = this.getTime();
        return (int)time ^ (int)(time >> 32);
    }
    
    @Override
    public String toString() {
        final BaseCalendar.Date normalize = this.normalize();
        final StringBuilder sb = new StringBuilder(28);
        int dayOfWeek = normalize.getDayOfWeek();
        if (dayOfWeek == 1) {
            dayOfWeek = 8;
        }
        convertToAbbr(sb, Date.wtb[dayOfWeek]).append(' ');
        convertToAbbr(sb, Date.wtb[normalize.getMonth() - 1 + 2 + 7]).append(' ');
        CalendarUtils.sprintf0d(sb, normalize.getDayOfMonth(), 2).append(' ');
        CalendarUtils.sprintf0d(sb, normalize.getHours(), 2).append(':');
        CalendarUtils.sprintf0d(sb, normalize.getMinutes(), 2).append(':');
        CalendarUtils.sprintf0d(sb, normalize.getSeconds(), 2).append(' ');
        final TimeZone zone = normalize.getZone();
        if (zone != null) {
            sb.append(zone.getDisplayName(normalize.isDaylightTime(), 0, Locale.US));
        }
        else {
            sb.append("GMT");
        }
        sb.append(' ').append(normalize.getYear());
        return sb.toString();
    }
    
    private static final StringBuilder convertToAbbr(final StringBuilder sb, final String s) {
        sb.append(Character.toUpperCase(s.charAt(0)));
        sb.append(s.charAt(1)).append(s.charAt(2));
        return sb;
    }
    
    @Deprecated
    public String toLocaleString() {
        return DateFormat.getDateTimeInstance().format(this);
    }
    
    @Deprecated
    public String toGMTString() {
        final BaseCalendar.Date date = (BaseCalendar.Date)getCalendarSystem(this.getTime()).getCalendarDate(this.getTime(), (TimeZone)null);
        final StringBuilder sb = new StringBuilder(32);
        CalendarUtils.sprintf0d(sb, date.getDayOfMonth(), 1).append(' ');
        convertToAbbr(sb, Date.wtb[date.getMonth() - 1 + 2 + 7]).append(' ');
        sb.append(date.getYear()).append(' ');
        CalendarUtils.sprintf0d(sb, date.getHours(), 2).append(':');
        CalendarUtils.sprintf0d(sb, date.getMinutes(), 2).append(':');
        CalendarUtils.sprintf0d(sb, date.getSeconds(), 2);
        sb.append(" GMT");
        return sb.toString();
    }
    
    @Deprecated
    public int getTimezoneOffset() {
        int n;
        if (this.cdate == null) {
            final TimeZone defaultRef = TimeZone.getDefaultRef();
            if (defaultRef instanceof ZoneInfo) {
                n = ((ZoneInfo)defaultRef).getOffsets(this.fastTime, null);
            }
            else {
                n = defaultRef.getOffset(this.fastTime);
            }
        }
        else {
            this.normalize();
            n = this.cdate.getZoneOffset();
        }
        return -n / 60000;
    }
    
    private final BaseCalendar.Date getCalendarDate() {
        if (this.cdate == null) {
            this.cdate = (BaseCalendar.Date)getCalendarSystem(this.fastTime).getCalendarDate(this.fastTime, TimeZone.getDefaultRef());
        }
        return this.cdate;
    }
    
    private final BaseCalendar.Date normalize() {
        if (this.cdate == null) {
            return this.cdate = (BaseCalendar.Date)getCalendarSystem(this.fastTime).getCalendarDate(this.fastTime, TimeZone.getDefaultRef());
        }
        if (!this.cdate.isNormalized()) {
            this.cdate = this.normalize(this.cdate);
        }
        final TimeZone defaultRef = TimeZone.getDefaultRef();
        if (defaultRef != this.cdate.getZone()) {
            this.cdate.setZone(defaultRef);
            getCalendarSystem(this.cdate).getCalendarDate(this.fastTime, this.cdate);
        }
        return this.cdate;
    }
    
    private final BaseCalendar.Date normalize(BaseCalendar.Date date) {
        final int normalizedYear = date.getNormalizedYear();
        final int month = date.getMonth();
        final int dayOfMonth = date.getDayOfMonth();
        final int hours = date.getHours();
        final int minutes = date.getMinutes();
        final int seconds = date.getSeconds();
        final int millis = date.getMillis();
        TimeZone timeZone = date.getZone();
        if (normalizedYear == 1582 || normalizedYear > 280000000 || normalizedYear < -280000000) {
            if (timeZone == null) {
                timeZone = TimeZone.getTimeZone("GMT");
            }
            final GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);
            gregorianCalendar.clear();
            gregorianCalendar.set(14, millis);
            gregorianCalendar.set(normalizedYear, month - 1, dayOfMonth, hours, minutes, seconds);
            this.fastTime = gregorianCalendar.getTimeInMillis();
            date = (BaseCalendar.Date)getCalendarSystem(this.fastTime).getCalendarDate(this.fastTime, timeZone);
            return date;
        }
        final BaseCalendar calendarSystem = getCalendarSystem(normalizedYear);
        if (calendarSystem != getCalendarSystem(date)) {
            date = (BaseCalendar.Date)calendarSystem.newCalendarDate(timeZone);
            date.setNormalizedDate(normalizedYear, month, dayOfMonth).setTimeOfDay(hours, minutes, seconds, millis);
        }
        this.fastTime = calendarSystem.getTime(date);
        final BaseCalendar calendarSystem2 = getCalendarSystem(this.fastTime);
        if (calendarSystem2 != calendarSystem) {
            date = (BaseCalendar.Date)calendarSystem2.newCalendarDate(timeZone);
            date.setNormalizedDate(normalizedYear, month, dayOfMonth).setTimeOfDay(hours, minutes, seconds, millis);
            this.fastTime = calendarSystem2.getTime(date);
        }
        return date;
    }
    
    private static final BaseCalendar getCalendarSystem(final int n) {
        if (n >= 1582) {
            return Date.gcal;
        }
        return getJulianCalendar();
    }
    
    private static final BaseCalendar getCalendarSystem(final long n) {
        if (n >= 0L || n >= -12219292800000L - TimeZone.getDefaultRef().getOffset(n)) {
            return Date.gcal;
        }
        return getJulianCalendar();
    }
    
    private static final BaseCalendar getCalendarSystem(final BaseCalendar.Date date) {
        if (Date.jcal == null) {
            return Date.gcal;
        }
        if (date.getEra() != null) {
            return Date.jcal;
        }
        return Date.gcal;
    }
    
    private static final synchronized BaseCalendar getJulianCalendar() {
        if (Date.jcal == null) {
            Date.jcal = (BaseCalendar)CalendarSystem.forName("julian");
        }
        return Date.jcal;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeLong(this.getTimeImpl());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.fastTime = objectInputStream.readLong();
    }
    
    public static Date from(final Instant instant) {
        try {
            return new Date(instant.toEpochMilli());
        }
        catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    public Instant toInstant() {
        return Instant.ofEpochMilli(this.getTime());
    }
    
    static {
        gcal = CalendarSystem.getGregorianCalendar();
        wtb = new String[] { "am", "pm", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt" };
        ttb = new int[] { 14, 1, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 10000, 10000, 10000, 10300, 10240, 10360, 10300, 10420, 10360, 10480, 10420 };
    }
}
