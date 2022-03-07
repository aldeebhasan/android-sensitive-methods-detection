package java.util;

import sun.util.calendar.*;
import java.io.*;

public class SimpleTimeZone extends TimeZone
{
    private int startMonth;
    private int startDay;
    private int startDayOfWeek;
    private int startTime;
    private int startTimeMode;
    private int endMonth;
    private int endDay;
    private int endDayOfWeek;
    private int endTime;
    private int endTimeMode;
    private int startYear;
    private int rawOffset;
    private boolean useDaylight;
    private static final int millisPerHour = 3600000;
    private static final int millisPerDay = 86400000;
    private final byte[] monthLength;
    private static final byte[] staticMonthLength;
    private static final byte[] staticLeapMonthLength;
    private int startMode;
    private int endMode;
    private int dstSavings;
    private static final Gregorian gcal;
    private transient long cacheYear;
    private transient long cacheStart;
    private transient long cacheEnd;
    private static final int DOM_MODE = 1;
    private static final int DOW_IN_MONTH_MODE = 2;
    private static final int DOW_GE_DOM_MODE = 3;
    private static final int DOW_LE_DOM_MODE = 4;
    public static final int WALL_TIME = 0;
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    static final long serialVersionUID = -403250971215465050L;
    static final int currentSerialVersion = 2;
    private int serialVersionOnStream;
    private static final int MAX_RULE_NUM = 6;
    
    public SimpleTimeZone(final int rawOffset, final String id) {
        this.useDaylight = false;
        this.monthLength = SimpleTimeZone.staticMonthLength;
        this.serialVersionOnStream = 2;
        this.rawOffset = rawOffset;
        this.setID(id);
        this.dstSavings = 3600000;
    }
    
    public SimpleTimeZone(final int n, final String s, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        this(n, s, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, 3600000);
    }
    
    public SimpleTimeZone(final int n, final String s, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        this(n, s, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, n10);
    }
    
    public SimpleTimeZone(final int rawOffset, final String id, final int startMonth, final int startDay, final int startDayOfWeek, final int startTime, final int startTimeMode, final int endMonth, final int endDay, final int endDayOfWeek, final int endTime, final int endTimeMode, final int dstSavings) {
        this.useDaylight = false;
        this.monthLength = SimpleTimeZone.staticMonthLength;
        this.serialVersionOnStream = 2;
        this.setID(id);
        this.rawOffset = rawOffset;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.startDayOfWeek = startDayOfWeek;
        this.startTime = startTime;
        this.startTimeMode = startTimeMode;
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.endDayOfWeek = endDayOfWeek;
        this.endTime = endTime;
        this.endTimeMode = endTimeMode;
        this.dstSavings = dstSavings;
        this.decodeRules();
        if (dstSavings <= 0) {
            throw new IllegalArgumentException("Illegal daylight saving value: " + dstSavings);
        }
    }
    
    public void setStartYear(final int startYear) {
        this.startYear = startYear;
        this.invalidateCache();
    }
    
    public void setStartRule(final int startMonth, final int startDay, final int startDayOfWeek, final int startTime) {
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.startDayOfWeek = startDayOfWeek;
        this.startTime = startTime;
        this.startTimeMode = 0;
        this.decodeStartRule();
        this.invalidateCache();
    }
    
    public void setStartRule(final int n, final int n2, final int n3) {
        this.setStartRule(n, n2, 0, n3);
    }
    
    public void setStartRule(final int n, final int n2, final int n3, final int n4, final boolean b) {
        if (b) {
            this.setStartRule(n, n2, -n3, n4);
        }
        else {
            this.setStartRule(n, -n2, -n3, n4);
        }
    }
    
    public void setEndRule(final int endMonth, final int endDay, final int endDayOfWeek, final int endTime) {
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.endDayOfWeek = endDayOfWeek;
        this.endTime = endTime;
        this.endTimeMode = 0;
        this.decodeEndRule();
        this.invalidateCache();
    }
    
    public void setEndRule(final int n, final int n2, final int n3) {
        this.setEndRule(n, n2, 0, n3);
    }
    
    public void setEndRule(final int n, final int n2, final int n3, final int n4, final boolean b) {
        if (b) {
            this.setEndRule(n, n2, -n3, n4);
        }
        else {
            this.setEndRule(n, -n2, -n3, n4);
        }
    }
    
    @Override
    public int getOffset(final long n) {
        return this.getOffsets(n, null);
    }
    
    @Override
    int getOffsets(final long n, final int[] array) {
        int n2 = this.rawOffset;
        Label_0165: {
            if (this.useDaylight) {
                synchronized (this) {
                    if (this.cacheStart != 0L && n >= this.cacheStart && n < this.cacheEnd) {
                        n2 += this.dstSavings;
                        break Label_0165;
                    }
                }
                final BaseCalendar baseCalendar = (n >= -12219292800000L) ? SimpleTimeZone.gcal : ((BaseCalendar)CalendarSystem.forName("julian"));
                final BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
                baseCalendar.getCalendarDate(n + this.rawOffset, date);
                final int normalizedYear = date.getNormalizedYear();
                if (normalizedYear >= this.startYear) {
                    date.setTimeOfDay(0, 0, 0, 0);
                    n2 = this.getOffset(baseCalendar, date, normalizedYear, n);
                }
            }
        }
        if (array != null) {
            array[0] = this.rawOffset;
            array[1] = n2 - this.rawOffset;
        }
        return n2;
    }
    
    @Override
    public int getOffset(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n != 1 && n != 0) {
            throw new IllegalArgumentException("Illegal era " + n);
        }
        int n7 = n2;
        if (n == 0) {
            n7 = 1 - n7;
        }
        if (n7 >= 292278994) {
            n7 = 2800 + n7 % 2800;
        }
        else if (n7 <= -292269054) {
            n7 = (int)CalendarUtils.mod(n7, 28L);
        }
        final int n8 = n3 + 1;
        BaseCalendar gcal = SimpleTimeZone.gcal;
        BaseCalendar.Date date = (BaseCalendar.Date)gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
        date.setDate(n7, n8, n4);
        long n9 = gcal.getTime(date) + (n6 - this.rawOffset);
        if (n9 < -12219292800000L) {
            gcal = (BaseCalendar)CalendarSystem.forName("julian");
            date = (BaseCalendar.Date)gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
            date.setNormalizedDate(n7, n8, n4);
            n9 = gcal.getTime(date) + n6 - this.rawOffset;
        }
        if (date.getNormalizedYear() != n7 || date.getMonth() != n8 || date.getDayOfMonth() != n4 || n5 < 1 || n5 > 7 || n6 < 0 || n6 >= 86400000) {
            throw new IllegalArgumentException();
        }
        if (!this.useDaylight || n2 < this.startYear || n != 1) {
            return this.rawOffset;
        }
        return this.getOffset(gcal, date, n7, n9);
    }
    
    private int getOffset(final BaseCalendar baseCalendar, final BaseCalendar.Date date, final int n, final long n2) {
        synchronized (this) {
            if (this.cacheStart != 0L) {
                if (n2 >= this.cacheStart && n2 < this.cacheEnd) {
                    return this.rawOffset + this.dstSavings;
                }
                if (n == this.cacheYear) {
                    return this.rawOffset;
                }
            }
        }
        long n3 = this.getStart(baseCalendar, date, n);
        long n4 = this.getEnd(baseCalendar, date, n);
        int rawOffset = this.rawOffset;
        if (n3 <= n4) {
            if (n2 >= n3 && n2 < n4) {
                rawOffset += this.dstSavings;
            }
            synchronized (this) {
                this.cacheYear = n;
                this.cacheStart = n3;
                this.cacheEnd = n4;
            }
        }
        else {
            if (n2 < n4) {
                n3 = this.getStart(baseCalendar, date, n - 1);
                if (n2 >= n3) {
                    rawOffset += this.dstSavings;
                }
            }
            else if (n2 >= n3) {
                n4 = this.getEnd(baseCalendar, date, n + 1);
                if (n2 < n4) {
                    rawOffset += this.dstSavings;
                }
            }
            if (n3 <= n4) {
                synchronized (this) {
                    this.cacheYear = this.startYear - 1L;
                    this.cacheStart = n3;
                    this.cacheEnd = n4;
                }
            }
        }
        return rawOffset;
    }
    
    private long getStart(final BaseCalendar baseCalendar, final BaseCalendar.Date date, final int n) {
        int startTime = this.startTime;
        if (this.startTimeMode != 2) {
            startTime -= this.rawOffset;
        }
        return this.getTransition(baseCalendar, date, this.startMode, n, this.startMonth, this.startDay, this.startDayOfWeek, startTime);
    }
    
    private long getEnd(final BaseCalendar baseCalendar, final BaseCalendar.Date date, final int n) {
        int endTime = this.endTime;
        if (this.endTimeMode != 2) {
            endTime -= this.rawOffset;
        }
        if (this.endTimeMode == 0) {
            endTime -= this.dstSavings;
        }
        return this.getTransition(baseCalendar, date, this.endMode, n, this.endMonth, this.endDay, this.endDayOfWeek, endTime);
    }
    
    private long getTransition(final BaseCalendar baseCalendar, BaseCalendar.Date date, final int n, final int normalizedYear, final int n2, final int dayOfMonth, final int n3, final int n4) {
        date.setNormalizedYear(normalizedYear);
        date.setMonth(n2 + 1);
        switch (n) {
            case 1: {
                date.setDayOfMonth(dayOfMonth);
                break;
            }
            case 2: {
                date.setDayOfMonth(1);
                if (dayOfMonth < 0) {
                    date.setDayOfMonth(baseCalendar.getMonthLength(date));
                }
                date = (BaseCalendar.Date)baseCalendar.getNthDayOfWeek(dayOfMonth, n3, date);
                break;
            }
            case 3: {
                date.setDayOfMonth(dayOfMonth);
                date = (BaseCalendar.Date)baseCalendar.getNthDayOfWeek(1, n3, date);
                break;
            }
            case 4: {
                date.setDayOfMonth(dayOfMonth);
                date = (BaseCalendar.Date)baseCalendar.getNthDayOfWeek(-1, n3, date);
                break;
            }
        }
        return baseCalendar.getTime(date) + n4;
    }
    
    @Override
    public int getRawOffset() {
        return this.rawOffset;
    }
    
    @Override
    public void setRawOffset(final int rawOffset) {
        this.rawOffset = rawOffset;
    }
    
    public void setDSTSavings(final int dstSavings) {
        if (dstSavings <= 0) {
            throw new IllegalArgumentException("Illegal daylight saving value: " + dstSavings);
        }
        this.dstSavings = dstSavings;
    }
    
    @Override
    public int getDSTSavings() {
        return this.useDaylight ? this.dstSavings : 0;
    }
    
    @Override
    public boolean useDaylightTime() {
        return this.useDaylight;
    }
    
    @Override
    public boolean observesDaylightTime() {
        return this.useDaylightTime();
    }
    
    @Override
    public boolean inDaylightTime(final Date date) {
        return this.getOffset(date.getTime()) != this.rawOffset;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public synchronized int hashCode() {
        return this.startMonth ^ this.startDay ^ this.startDayOfWeek ^ this.startTime ^ this.endMonth ^ this.endDay ^ this.endDayOfWeek ^ this.endTime ^ this.rawOffset;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleTimeZone)) {
            return false;
        }
        final SimpleTimeZone simpleTimeZone = (SimpleTimeZone)o;
        return this.getID().equals(simpleTimeZone.getID()) && this.hasSameRules(simpleTimeZone);
    }
    
    @Override
    public boolean hasSameRules(final TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (!(timeZone instanceof SimpleTimeZone)) {
            return false;
        }
        final SimpleTimeZone simpleTimeZone = (SimpleTimeZone)timeZone;
        return this.rawOffset == simpleTimeZone.rawOffset && this.useDaylight == simpleTimeZone.useDaylight && (!this.useDaylight || (this.dstSavings == simpleTimeZone.dstSavings && this.startMode == simpleTimeZone.startMode && this.startMonth == simpleTimeZone.startMonth && this.startDay == simpleTimeZone.startDay && this.startDayOfWeek == simpleTimeZone.startDayOfWeek && this.startTime == simpleTimeZone.startTime && this.startTimeMode == simpleTimeZone.startTimeMode && this.endMode == simpleTimeZone.endMode && this.endMonth == simpleTimeZone.endMonth && this.endDay == simpleTimeZone.endDay && this.endDayOfWeek == simpleTimeZone.endDayOfWeek && this.endTime == simpleTimeZone.endTime && this.endTimeMode == simpleTimeZone.endTimeMode && this.startYear == simpleTimeZone.startYear));
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + this.getID() + ",offset=" + this.rawOffset + ",dstSavings=" + this.dstSavings + ",useDaylight=" + this.useDaylight + ",startYear=" + this.startYear + ",startMode=" + this.startMode + ",startMonth=" + this.startMonth + ",startDay=" + this.startDay + ",startDayOfWeek=" + this.startDayOfWeek + ",startTime=" + this.startTime + ",startTimeMode=" + this.startTimeMode + ",endMode=" + this.endMode + ",endMonth=" + this.endMonth + ",endDay=" + this.endDay + ",endDayOfWeek=" + this.endDayOfWeek + ",endTime=" + this.endTime + ",endTimeMode=" + this.endTimeMode + ']';
    }
    
    private synchronized void invalidateCache() {
        this.cacheYear = this.startYear - 1;
        final long n = 0L;
        this.cacheEnd = n;
        this.cacheStart = n;
    }
    
    private void decodeRules() {
        this.decodeStartRule();
        this.decodeEndRule();
    }
    
    private void decodeStartRule() {
        this.useDaylight = (this.startDay != 0 && this.endDay != 0);
        if (this.startDay != 0) {
            if (this.startMonth < 0 || this.startMonth > 11) {
                throw new IllegalArgumentException("Illegal start month " + this.startMonth);
            }
            if (this.startTime < 0 || this.startTime > 86400000) {
                throw new IllegalArgumentException("Illegal start time " + this.startTime);
            }
            if (this.startDayOfWeek == 0) {
                this.startMode = 1;
            }
            else {
                if (this.startDayOfWeek > 0) {
                    this.startMode = 2;
                }
                else {
                    this.startDayOfWeek = -this.startDayOfWeek;
                    if (this.startDay > 0) {
                        this.startMode = 3;
                    }
                    else {
                        this.startDay = -this.startDay;
                        this.startMode = 4;
                    }
                }
                if (this.startDayOfWeek > 7) {
                    throw new IllegalArgumentException("Illegal start day of week " + this.startDayOfWeek);
                }
            }
            if (this.startMode == 2) {
                if (this.startDay < -5 || this.startDay > 5) {
                    throw new IllegalArgumentException("Illegal start day of week in month " + this.startDay);
                }
            }
            else if (this.startDay < 1 || this.startDay > SimpleTimeZone.staticMonthLength[this.startMonth]) {
                throw new IllegalArgumentException("Illegal start day " + this.startDay);
            }
        }
    }
    
    private void decodeEndRule() {
        this.useDaylight = (this.startDay != 0 && this.endDay != 0);
        if (this.endDay != 0) {
            if (this.endMonth < 0 || this.endMonth > 11) {
                throw new IllegalArgumentException("Illegal end month " + this.endMonth);
            }
            if (this.endTime < 0 || this.endTime > 86400000) {
                throw new IllegalArgumentException("Illegal end time " + this.endTime);
            }
            if (this.endDayOfWeek == 0) {
                this.endMode = 1;
            }
            else {
                if (this.endDayOfWeek > 0) {
                    this.endMode = 2;
                }
                else {
                    this.endDayOfWeek = -this.endDayOfWeek;
                    if (this.endDay > 0) {
                        this.endMode = 3;
                    }
                    else {
                        this.endDay = -this.endDay;
                        this.endMode = 4;
                    }
                }
                if (this.endDayOfWeek > 7) {
                    throw new IllegalArgumentException("Illegal end day of week " + this.endDayOfWeek);
                }
            }
            if (this.endMode == 2) {
                if (this.endDay < -5 || this.endDay > 5) {
                    throw new IllegalArgumentException("Illegal end day of week in month " + this.endDay);
                }
            }
            else if (this.endDay < 1 || this.endDay > SimpleTimeZone.staticMonthLength[this.endMonth]) {
                throw new IllegalArgumentException("Illegal end day " + this.endDay);
            }
        }
    }
    
    private void makeRulesCompatible() {
        switch (this.startMode) {
            case 1: {
                this.startDay = 1 + this.startDay / 7;
                this.startDayOfWeek = 1;
                break;
            }
            case 3: {
                if (this.startDay != 1) {
                    this.startDay = 1 + this.startDay / 7;
                    break;
                }
                break;
            }
            case 4: {
                if (this.startDay >= 30) {
                    this.startDay = -1;
                    break;
                }
                this.startDay = 1 + this.startDay / 7;
                break;
            }
        }
        switch (this.endMode) {
            case 1: {
                this.endDay = 1 + this.endDay / 7;
                this.endDayOfWeek = 1;
                break;
            }
            case 3: {
                if (this.endDay != 1) {
                    this.endDay = 1 + this.endDay / 7;
                    break;
                }
                break;
            }
            case 4: {
                if (this.endDay >= 30) {
                    this.endDay = -1;
                    break;
                }
                this.endDay = 1 + this.endDay / 7;
                break;
            }
        }
        switch (this.startTimeMode) {
            case 2: {
                this.startTime += this.rawOffset;
                break;
            }
        }
        while (this.startTime < 0) {
            this.startTime += 86400000;
            this.startDayOfWeek = 1 + (this.startDayOfWeek + 5) % 7;
        }
        while (this.startTime >= 86400000) {
            this.startTime -= 86400000;
            this.startDayOfWeek = 1 + this.startDayOfWeek % 7;
        }
        switch (this.endTimeMode) {
            case 2: {
                this.endTime += this.rawOffset + this.dstSavings;
                break;
            }
            case 1: {
                this.endTime += this.dstSavings;
                break;
            }
        }
        while (this.endTime < 0) {
            this.endTime += 86400000;
            this.endDayOfWeek = 1 + (this.endDayOfWeek + 5) % 7;
        }
        while (this.endTime >= 86400000) {
            this.endTime -= 86400000;
            this.endDayOfWeek = 1 + this.endDayOfWeek % 7;
        }
    }
    
    private byte[] packRules() {
        return new byte[] { (byte)this.startDay, (byte)this.startDayOfWeek, (byte)this.endDay, (byte)this.endDayOfWeek, (byte)this.startTimeMode, (byte)this.endTimeMode };
    }
    
    private void unpackRules(final byte[] array) {
        this.startDay = array[0];
        this.startDayOfWeek = array[1];
        this.endDay = array[2];
        this.endDayOfWeek = array[3];
        if (array.length >= 6) {
            this.startTimeMode = array[4];
            this.endTimeMode = array[5];
        }
    }
    
    private int[] packTimes() {
        return new int[] { this.startTime, this.endTime };
    }
    
    private void unpackTimes(final int[] array) {
        this.startTime = array[0];
        this.endTime = array[1];
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final byte[] packRules = this.packRules();
        final int[] packTimes = this.packTimes();
        this.makeRulesCompatible();
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(packRules.length);
        objectOutputStream.write(packRules);
        objectOutputStream.writeObject(packTimes);
        this.unpackRules(packRules);
        this.unpackTimes(packTimes);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            if (this.startDayOfWeek == 0) {
                this.startDayOfWeek = 1;
            }
            if (this.endDayOfWeek == 0) {
                this.endDayOfWeek = 1;
            }
            final int n = 2;
            this.endMode = n;
            this.startMode = n;
            this.dstSavings = 3600000;
        }
        else {
            final int int1 = objectInputStream.readInt();
            if (int1 > 6) {
                throw new InvalidObjectException("Too many rules: " + int1);
            }
            final byte[] array = new byte[int1];
            objectInputStream.readFully(array);
            this.unpackRules(array);
        }
        if (this.serialVersionOnStream >= 2) {
            this.unpackTimes((int[])objectInputStream.readObject());
        }
        this.serialVersionOnStream = 2;
    }
    
    static {
        staticMonthLength = new byte[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        staticLeapMonthLength = new byte[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        gcal = CalendarSystem.getGregorianCalendar();
    }
}
