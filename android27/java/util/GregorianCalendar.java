package java.util;

import sun.util.calendar.*;
import java.io.*;
import java.time.*;
import java.time.temporal.*;

public class GregorianCalendar extends Calendar
{
    public static final int BC = 0;
    static final int BCE = 0;
    public static final int AD = 1;
    static final int CE = 1;
    private static final int EPOCH_OFFSET = 719163;
    private static final int EPOCH_YEAR = 1970;
    static final int[] MONTH_LENGTH;
    static final int[] LEAP_MONTH_LENGTH;
    private static final int ONE_SECOND = 1000;
    private static final int ONE_MINUTE = 60000;
    private static final int ONE_HOUR = 3600000;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    static final int[] MIN_VALUES;
    static final int[] LEAST_MAX_VALUES;
    static final int[] MAX_VALUES;
    static final long serialVersionUID = -8125100834729963327L;
    private static final Gregorian gcal;
    private static JulianCalendar jcal;
    private static Era[] jeras;
    static final long DEFAULT_GREGORIAN_CUTOVER = -12219292800000L;
    private long gregorianCutover;
    private transient long gregorianCutoverDate;
    private transient int gregorianCutoverYear;
    private transient int gregorianCutoverYearJulian;
    private transient BaseCalendar.Date gdate;
    private transient BaseCalendar.Date cdate;
    private transient BaseCalendar calsys;
    private transient int[] zoneOffsets;
    private transient int[] originalFields;
    private transient long cachedFixedDate;
    
    public GregorianCalendar() {
        this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
        this.setZoneShared(true);
    }
    
    public GregorianCalendar(final TimeZone timeZone) {
        this(timeZone, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public GregorianCalendar(final Locale locale) {
        this(TimeZone.getDefaultRef(), locale);
        this.setZoneShared(true);
    }
    
    public GregorianCalendar(final TimeZone timeZone, final Locale locale) {
        super(timeZone, locale);
        this.gregorianCutover = -12219292800000L;
        this.gregorianCutoverDate = 577736L;
        this.gregorianCutoverYear = 1582;
        this.gregorianCutoverYearJulian = 1582;
        this.cachedFixedDate = Long.MIN_VALUE;
        this.gdate = GregorianCalendar.gcal.newCalendarDate(timeZone);
        this.setTimeInMillis(System.currentTimeMillis());
    }
    
    public GregorianCalendar(final int n, final int n2, final int n3) {
        this(n, n2, n3, 0, 0, 0, 0);
    }
    
    public GregorianCalendar(final int n, final int n2, final int n3, final int n4, final int n5) {
        this(n, n2, n3, n4, n5, 0, 0);
    }
    
    public GregorianCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this(n, n2, n3, n4, n5, n6, 0);
    }
    
    GregorianCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this.gregorianCutover = -12219292800000L;
        this.gregorianCutoverDate = 577736L;
        this.gregorianCutoverYear = 1582;
        this.gregorianCutoverYearJulian = 1582;
        this.cachedFixedDate = Long.MIN_VALUE;
        this.gdate = GregorianCalendar.gcal.newCalendarDate(this.getZone());
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        if (n4 >= 12 && n4 <= 23) {
            this.internalSet(9, 1);
            this.internalSet(10, n4 - 12);
        }
        else {
            this.internalSet(10, n4);
        }
        this.setFieldsComputed(1536);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
        this.internalSet(14, n7);
    }
    
    GregorianCalendar(final TimeZone timeZone, final Locale locale, final boolean b) {
        super(timeZone, locale);
        this.gregorianCutover = -12219292800000L;
        this.gregorianCutoverDate = 577736L;
        this.gregorianCutoverYear = 1582;
        this.gregorianCutoverYearJulian = 1582;
        this.cachedFixedDate = Long.MIN_VALUE;
        this.gdate = GregorianCalendar.gcal.newCalendarDate(this.getZone());
    }
    
    public void setGregorianChange(final Date date) {
        final long time = date.getTime();
        if (time == this.gregorianCutover) {
            return;
        }
        this.complete();
        this.setGregorianChange(time);
    }
    
    private void setGregorianChange(final long gregorianCutover) {
        this.gregorianCutover = gregorianCutover;
        this.gregorianCutoverDate = CalendarUtils.floorDivide(gregorianCutover, 86400000L) + 719163L;
        if (gregorianCutover == Long.MAX_VALUE) {
            ++this.gregorianCutoverDate;
        }
        this.gregorianCutoverYear = this.getGregorianCutoverDate().getYear();
        final BaseCalendar julianCalendarSystem = getJulianCalendarSystem();
        final BaseCalendar.Date date = (BaseCalendar.Date)julianCalendarSystem.newCalendarDate(TimeZone.NO_TIMEZONE);
        julianCalendarSystem.getCalendarDateFromFixedDate(date, this.gregorianCutoverDate - 1L);
        this.gregorianCutoverYearJulian = date.getNormalizedYear();
        if (this.time < this.gregorianCutover) {
            this.setUnnormalized();
        }
    }
    
    public final Date getGregorianChange() {
        return new Date(this.gregorianCutover);
    }
    
    public boolean isLeapYear(final int n) {
        if ((n & 0x3) != 0x0) {
            return false;
        }
        if (n > this.gregorianCutoverYear) {
            return n % 100 != 0 || n % 400 == 0;
        }
        if (n < this.gregorianCutoverYearJulian) {
            return true;
        }
        boolean b;
        if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian) {
            b = (this.getCalendarDate(this.gregorianCutoverDate).getMonth() < 3);
        }
        else {
            b = (n == this.gregorianCutoverYear);
        }
        return !b || (n % 100 != 0 || n % 400 == 0);
    }
    
    @Override
    public String getCalendarType() {
        return "gregory";
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof GregorianCalendar && super.equals(o) && this.gregorianCutover == ((GregorianCalendar)o).gregorianCutover;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)this.gregorianCutoverDate;
    }
    
    @Override
    public void add(final int n, final int n2) {
        if (n2 == 0) {
            return;
        }
        if (n < 0 || n >= 15) {
            throw new IllegalArgumentException();
        }
        this.complete();
        if (n == 1) {
            final int internalGet = this.internalGet(1);
            if (this.internalGetEra() == 1) {
                final int n3 = internalGet + n2;
                if (n3 > 0) {
                    this.set(1, n3);
                }
                else {
                    this.set(1, 1 - n3);
                    this.set(0, 0);
                }
            }
            else {
                final int n4 = internalGet - n2;
                if (n4 > 0) {
                    this.set(1, n4);
                }
                else {
                    this.set(1, 1 - n4);
                    this.set(0, 1);
                }
            }
            this.pinDayOfMonth();
        }
        else if (n == 2) {
            final int n5 = this.internalGet(2) + n2;
            final int internalGet2 = this.internalGet(1);
            int n6;
            if (n5 >= 0) {
                n6 = n5 / 12;
            }
            else {
                n6 = (n5 + 1) / 12 - 1;
            }
            if (n6 != 0) {
                if (this.internalGetEra() == 1) {
                    final int n7 = internalGet2 + n6;
                    if (n7 > 0) {
                        this.set(1, n7);
                    }
                    else {
                        this.set(1, 1 - n7);
                        this.set(0, 0);
                    }
                }
                else {
                    final int n8 = internalGet2 - n6;
                    if (n8 > 0) {
                        this.set(1, n8);
                    }
                    else {
                        this.set(1, 1 - n8);
                        this.set(0, 1);
                    }
                }
            }
            if (n5 >= 0) {
                this.set(2, n5 % 12);
            }
            else {
                int n9 = n5 % 12;
                if (n9 < 0) {
                    n9 += 12;
                }
                this.set(2, 0 + n9);
            }
            this.pinDayOfMonth();
        }
        else if (n == 0) {
            int n10 = this.internalGet(0) + n2;
            if (n10 < 0) {
                n10 = 0;
            }
            if (n10 > 1) {
                n10 = 1;
            }
            this.set(0, n10);
        }
        else {
            long n11 = n2;
            long n12 = 0L;
            switch (n) {
                case 10:
                case 11: {
                    n11 *= 3600000L;
                    break;
                }
                case 12: {
                    n11 *= 60000L;
                    break;
                }
                case 13: {
                    n11 *= 1000L;
                }
                case 3:
                case 4:
                case 8: {
                    n11 *= 7L;
                }
                case 9: {
                    n11 = n2 / 2;
                    n12 = 12 * (n2 % 2);
                    break;
                }
            }
            if (n >= 10) {
                this.setTimeInMillis(this.time + n11);
                return;
            }
            long currentFixedDate = this.getCurrentFixedDate();
            long n13 = (((n12 + this.internalGet(11)) * 60L + this.internalGet(12)) * 60L + this.internalGet(13)) * 1000L + this.internalGet(14);
            if (n13 >= 86400000L) {
                ++currentFixedDate;
                n13 -= 86400000L;
            }
            else if (n13 < 0L) {
                --currentFixedDate;
                n13 += 86400000L;
            }
            final long n14 = currentFixedDate + n11;
            final int n15 = this.internalGet(15) + this.internalGet(16);
            this.setTimeInMillis((n14 - 719163L) * 86400000L + n13 - n15);
            final int n16 = n15 - (this.internalGet(15) + this.internalGet(16));
            if (n16 != 0) {
                this.setTimeInMillis(this.time + n16);
                if (this.getCurrentFixedDate() != n14) {
                    this.setTimeInMillis(this.time - n16);
                }
            }
        }
    }
    
    @Override
    public void roll(final int n, final boolean b) {
        this.roll(n, b ? 1 : -1);
    }
    
    @Override
    public void roll(final int n, int n2) {
        if (n2 == 0) {
            return;
        }
        if (n < 0 || n >= 15) {
            throw new IllegalArgumentException();
        }
        this.complete();
        int minimum = this.getMinimum(n);
        int n3 = this.getMaximum(n);
        switch (n) {
            case 10:
            case 11: {
                final int n4 = n3 + 1;
                final int internalGet = this.internalGet(n);
                int n5 = (internalGet + n2) % n4;
                if (n5 < 0) {
                    n5 += n4;
                }
                this.time += 3600000 * (n5 - internalGet);
                final CalendarDate calendarDate = this.calsys.getCalendarDate(this.time, this.getZone());
                if (this.internalGet(5) != calendarDate.getDayOfMonth()) {
                    calendarDate.setDate(this.internalGet(1), this.internalGet(2) + 1, this.internalGet(5));
                    if (n == 10) {
                        assert this.internalGet(9) == 1;
                        calendarDate.addHours(12);
                    }
                    this.time = this.calsys.getTime(calendarDate);
                }
                final int hours = calendarDate.getHours();
                this.internalSet(n, hours % n4);
                if (n == 10) {
                    this.internalSet(11, hours);
                }
                else {
                    this.internalSet(9, hours / 12);
                    this.internalSet(10, hours % 12);
                }
                final int zoneOffset = calendarDate.getZoneOffset();
                final int daylightSaving = calendarDate.getDaylightSaving();
                this.internalSet(15, zoneOffset - daylightSaving);
                this.internalSet(16, daylightSaving);
                return;
            }
            case 2: {
                if (!this.isCutoverYear(this.cdate.getNormalizedYear())) {
                    int n6 = (this.internalGet(2) + n2) % 12;
                    if (n6 < 0) {
                        n6 += 12;
                    }
                    this.set(2, n6);
                    final int monthLength = this.monthLength(n6);
                    if (this.internalGet(5) > monthLength) {
                        this.set(5, monthLength);
                    }
                }
                else {
                    final int n7 = this.getActualMaximum(2) + 1;
                    int n8 = (this.internalGet(2) + n2) % n7;
                    if (n8 < 0) {
                        n8 += n7;
                    }
                    this.set(2, n8);
                    final int actualMaximum = this.getActualMaximum(5);
                    if (this.internalGet(5) > actualMaximum) {
                        this.set(5, actualMaximum);
                    }
                }
                return;
            }
            case 3: {
                final int normalizedYear = this.cdate.getNormalizedYear();
                int actualMaximum2 = this.getActualMaximum(3);
                this.set(7, this.internalGet(7));
                int internalGet2 = this.internalGet(3);
                final int n9 = internalGet2 + n2;
                if (!this.isCutoverYear(normalizedYear)) {
                    final int weekYear = this.getWeekYear();
                    if (weekYear == normalizedYear) {
                        if (n9 > minimum && n9 < actualMaximum2) {
                            this.set(3, n9);
                            return;
                        }
                        final long currentFixedDate = this.getCurrentFixedDate();
                        if (this.calsys.getYearFromFixedDate(currentFixedDate - 7 * (internalGet2 - minimum)) != normalizedYear) {
                            ++minimum;
                        }
                        if (this.calsys.getYearFromFixedDate(currentFixedDate + 7 * (actualMaximum2 - this.internalGet(3))) != normalizedYear) {
                            --actualMaximum2;
                        }
                    }
                    else if (weekYear > normalizedYear) {
                        if (n2 < 0) {
                            ++n2;
                        }
                        internalGet2 = actualMaximum2;
                    }
                    else {
                        if (n2 > 0) {
                            n2 -= internalGet2 - actualMaximum2;
                        }
                        internalGet2 = minimum;
                    }
                    this.set(n, getRolledValue(internalGet2, n2, minimum, actualMaximum2));
                    return;
                }
                final long currentFixedDate2 = this.getCurrentFixedDate();
                BaseCalendar baseCalendar;
                if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian) {
                    baseCalendar = this.getCutoverCalendarSystem();
                }
                else if (normalizedYear == this.gregorianCutoverYear) {
                    baseCalendar = GregorianCalendar.gcal;
                }
                else {
                    baseCalendar = getJulianCalendarSystem();
                }
                final long n10 = currentFixedDate2 - 7 * (internalGet2 - minimum);
                if (baseCalendar.getYearFromFixedDate(n10) != normalizedYear) {
                    ++minimum;
                }
                final long n11 = currentFixedDate2 + 7 * (actualMaximum2 - internalGet2);
                if (((n11 >= this.gregorianCutoverDate) ? GregorianCalendar.gcal : getJulianCalendarSystem()).getYearFromFixedDate(n11) != normalizedYear) {
                    --actualMaximum2;
                }
                final BaseCalendar.Date calendarDate2 = this.getCalendarDate(n10 + (getRolledValue(internalGet2, n2, minimum, actualMaximum2) - 1) * 7);
                this.set(2, calendarDate2.getMonth() - 1);
                this.set(5, calendarDate2.getDayOfMonth());
                return;
            }
            case 4: {
                final boolean cutoverYear = this.isCutoverYear(this.cdate.getNormalizedYear());
                int n12 = this.internalGet(7) - this.getFirstDayOfWeek();
                if (n12 < 0) {
                    n12 += 7;
                }
                final long currentFixedDate3 = this.getCurrentFixedDate();
                long fixedDateMonth1;
                int n13;
                if (cutoverYear) {
                    fixedDateMonth1 = this.getFixedDateMonth1(this.cdate, currentFixedDate3);
                    n13 = this.actualMonthLength();
                }
                else {
                    fixedDateMonth1 = currentFixedDate3 - this.internalGet(5) + 1L;
                    n13 = this.calsys.getMonthLength(this.cdate);
                }
                long dayOfWeekDateOnOrBefore = AbstractCalendar.getDayOfWeekDateOnOrBefore(fixedDateMonth1 + 6L, this.getFirstDayOfWeek());
                if ((int)(dayOfWeekDateOnOrBefore - fixedDateMonth1) >= this.getMinimalDaysInFirstWeek()) {
                    dayOfWeekDateOnOrBefore -= 7L;
                }
                long n14 = dayOfWeekDateOnOrBefore + (getRolledValue(this.internalGet(n), n2, 1, this.getActualMaximum(n)) - 1) * 7 + n12;
                if (n14 < fixedDateMonth1) {
                    n14 = fixedDateMonth1;
                }
                else if (n14 >= fixedDateMonth1 + n13) {
                    n14 = fixedDateMonth1 + n13 - 1L;
                }
                int dayOfMonth;
                if (cutoverYear) {
                    dayOfMonth = this.getCalendarDate(n14).getDayOfMonth();
                }
                else {
                    dayOfMonth = (int)(n14 - fixedDateMonth1) + 1;
                }
                this.set(5, dayOfMonth);
                return;
            }
            case 5: {
                if (!this.isCutoverYear(this.cdate.getNormalizedYear())) {
                    n3 = this.calsys.getMonthLength(this.cdate);
                    break;
                }
                final long currentFixedDate4 = this.getCurrentFixedDate();
                final long fixedDateMonth2 = this.getFixedDateMonth1(this.cdate, currentFixedDate4);
                final BaseCalendar.Date calendarDate3 = this.getCalendarDate(fixedDateMonth2 + getRolledValue((int)(currentFixedDate4 - fixedDateMonth2), n2, 0, this.actualMonthLength() - 1));
                assert calendarDate3.getMonth() - 1 == this.internalGet(2);
                this.set(5, calendarDate3.getDayOfMonth());
                return;
            }
            case 6: {
                n3 = this.getActualMaximum(n);
                if (!this.isCutoverYear(this.cdate.getNormalizedYear())) {
                    break;
                }
                final long currentFixedDate5 = this.getCurrentFixedDate();
                final long n15 = currentFixedDate5 - this.internalGet(6) + 1L;
                final BaseCalendar.Date calendarDate4 = this.getCalendarDate(n15 + getRolledValue((int)(currentFixedDate5 - n15) + 1, n2, minimum, n3) - 1L);
                this.set(2, calendarDate4.getMonth() - 1);
                this.set(5, calendarDate4.getDayOfMonth());
                return;
            }
            case 7: {
                if (!this.isCutoverYear(this.cdate.getNormalizedYear())) {
                    final int internalGet3 = this.internalGet(3);
                    if (internalGet3 > 1 && internalGet3 < 52) {
                        this.set(3, internalGet3);
                        n3 = 7;
                        break;
                    }
                }
                n2 %= 7;
                if (n2 == 0) {
                    return;
                }
                final long currentFixedDate6 = this.getCurrentFixedDate();
                final long dayOfWeekDateOnOrBefore2 = AbstractCalendar.getDayOfWeekDateOnOrBefore(currentFixedDate6, this.getFirstDayOfWeek());
                long n16 = currentFixedDate6 + n2;
                if (n16 < dayOfWeekDateOnOrBefore2) {
                    n16 += 7L;
                }
                else if (n16 >= dayOfWeekDateOnOrBefore2 + 7L) {
                    n16 -= 7L;
                }
                final BaseCalendar.Date calendarDate5 = this.getCalendarDate(n16);
                this.set(0, (calendarDate5.getNormalizedYear() > 0) ? 1 : 0);
                this.set(calendarDate5.getYear(), calendarDate5.getMonth() - 1, calendarDate5.getDayOfMonth());
                return;
            }
            case 8: {
                minimum = 1;
                if (!this.isCutoverYear(this.cdate.getNormalizedYear())) {
                    final int internalGet4 = this.internalGet(5);
                    final int monthLength2 = this.calsys.getMonthLength(this.cdate);
                    final int n17 = monthLength2 % 7;
                    n3 = monthLength2 / 7;
                    if ((internalGet4 - 1) % 7 < n17) {
                        ++n3;
                    }
                    this.set(7, this.internalGet(7));
                    break;
                }
                final long currentFixedDate7 = this.getCurrentFixedDate();
                final long fixedDateMonth3 = this.getFixedDateMonth1(this.cdate, currentFixedDate7);
                final int actualMonthLength = this.actualMonthLength();
                final int n18 = actualMonthLength % 7;
                int n19 = actualMonthLength / 7;
                final int n20 = (int)(currentFixedDate7 - fixedDateMonth3) % 7;
                if (n20 < n18) {
                    ++n19;
                }
                final long n21 = fixedDateMonth3 + (getRolledValue(this.internalGet(n), n2, minimum, n19) - 1) * 7 + n20;
                final BaseCalendar baseCalendar2 = (n21 >= this.gregorianCutoverDate) ? GregorianCalendar.gcal : getJulianCalendarSystem();
                final BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar2.newCalendarDate(TimeZone.NO_TIMEZONE);
                baseCalendar2.getCalendarDateFromFixedDate(date, n21);
                this.set(5, date.getDayOfMonth());
                return;
            }
        }
        this.set(n, getRolledValue(this.internalGet(n), n2, minimum, n3));
    }
    
    @Override
    public int getMinimum(final int n) {
        return GregorianCalendar.MIN_VALUES[n];
    }
    
    @Override
    public int getMaximum(final int n) {
        switch (n) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8: {
                if (this.gregorianCutoverYear > 200) {
                    break;
                }
                final GregorianCalendar gregorianCalendar = (GregorianCalendar)this.clone();
                gregorianCalendar.setLenient(true);
                gregorianCalendar.setTimeInMillis(this.gregorianCutover);
                final int actualMaximum = gregorianCalendar.getActualMaximum(n);
                gregorianCalendar.setTimeInMillis(this.gregorianCutover - 1L);
                return Math.max(GregorianCalendar.MAX_VALUES[n], Math.max(actualMaximum, gregorianCalendar.getActualMaximum(n)));
            }
        }
        return GregorianCalendar.MAX_VALUES[n];
    }
    
    @Override
    public int getGreatestMinimum(final int n) {
        if (n == 5) {
            return Math.max(GregorianCalendar.MIN_VALUES[n], this.getCalendarDate(this.getFixedDateMonth1(this.getGregorianCutoverDate(), this.gregorianCutoverDate)).getDayOfMonth());
        }
        return GregorianCalendar.MIN_VALUES[n];
    }
    
    @Override
    public int getLeastMaximum(final int n) {
        switch (n) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8: {
                final GregorianCalendar gregorianCalendar = (GregorianCalendar)this.clone();
                gregorianCalendar.setLenient(true);
                gregorianCalendar.setTimeInMillis(this.gregorianCutover);
                final int actualMaximum = gregorianCalendar.getActualMaximum(n);
                gregorianCalendar.setTimeInMillis(this.gregorianCutover - 1L);
                return Math.min(GregorianCalendar.LEAST_MAX_VALUES[n], Math.min(actualMaximum, gregorianCalendar.getActualMaximum(n)));
            }
            default: {
                return GregorianCalendar.LEAST_MAX_VALUES[n];
            }
        }
    }
    
    @Override
    public int getActualMinimum(final int n) {
        if (n == 5) {
            final GregorianCalendar normalizedCalendar = this.getNormalizedCalendar();
            final int normalizedYear = normalizedCalendar.cdate.getNormalizedYear();
            if (normalizedYear == this.gregorianCutoverYear || normalizedYear == this.gregorianCutoverYearJulian) {
                return this.getCalendarDate(this.getFixedDateMonth1(normalizedCalendar.cdate, normalizedCalendar.calsys.getFixedDate(normalizedCalendar.cdate))).getDayOfMonth();
            }
        }
        return this.getMinimum(n);
    }
    
    @Override
    public int getActualMaximum(final int n) {
        if ((0x1FE81 & 1 << n) != 0x0) {
            return this.getMaximum(n);
        }
        GregorianCalendar normalizedCalendar = this.getNormalizedCalendar();
        final BaseCalendar.Date cdate = normalizedCalendar.cdate;
        final BaseCalendar calsys = normalizedCalendar.calsys;
        int normalizedYear = cdate.getNormalizedYear();
        int n2 = 0;
        switch (n) {
            case 2: {
                if (!normalizedCalendar.isCutoverYear(normalizedYear)) {
                    n2 = 11;
                    break;
                }
                long fixedDate;
                do {
                    fixedDate = GregorianCalendar.gcal.getFixedDate(++normalizedYear, 1, 1, null);
                } while (fixedDate < this.gregorianCutoverDate);
                final BaseCalendar.Date date = (BaseCalendar.Date)cdate.clone();
                calsys.getCalendarDateFromFixedDate(date, fixedDate - 1L);
                n2 = date.getMonth() - 1;
                break;
            }
            case 5: {
                n2 = calsys.getMonthLength(cdate);
                if (!normalizedCalendar.isCutoverYear(normalizedYear)) {
                    break;
                }
                if (cdate.getDayOfMonth() == n2) {
                    break;
                }
                final long currentFixedDate = normalizedCalendar.getCurrentFixedDate();
                if (currentFixedDate >= this.gregorianCutoverDate) {
                    break;
                }
                n2 = normalizedCalendar.getCalendarDate(normalizedCalendar.getFixedDateMonth1(normalizedCalendar.cdate, currentFixedDate) + normalizedCalendar.actualMonthLength() - 1L).getDayOfMonth();
                break;
            }
            case 6: {
                if (!normalizedCalendar.isCutoverYear(normalizedYear)) {
                    n2 = calsys.getYearLength(cdate);
                    break;
                }
                long n3;
                if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian) {
                    n3 = normalizedCalendar.getCutoverCalendarSystem().getFixedDate(normalizedYear, 1, 1, null);
                }
                else if (normalizedYear == this.gregorianCutoverYearJulian) {
                    n3 = calsys.getFixedDate(normalizedYear, 1, 1, null);
                }
                else {
                    n3 = this.gregorianCutoverDate;
                }
                long n4 = GregorianCalendar.gcal.getFixedDate(++normalizedYear, 1, 1, null);
                if (n4 < this.gregorianCutoverDate) {
                    n4 = this.gregorianCutoverDate;
                }
                assert n3 <= calsys.getFixedDate(cdate.getNormalizedYear(), cdate.getMonth(), cdate.getDayOfMonth(), cdate);
                assert n4 >= calsys.getFixedDate(cdate.getNormalizedYear(), cdate.getMonth(), cdate.getDayOfMonth(), cdate);
                n2 = (int)(n4 - n3);
                break;
            }
            case 3: {
                if (normalizedCalendar.isCutoverYear(normalizedYear)) {
                    if (normalizedCalendar == this) {
                        normalizedCalendar = (GregorianCalendar)normalizedCalendar.clone();
                    }
                    final int actualMaximum = this.getActualMaximum(6);
                    normalizedCalendar.set(6, actualMaximum);
                    n2 = normalizedCalendar.get(3);
                    if (this.internalGet(1) != normalizedCalendar.getWeekYear()) {
                        normalizedCalendar.set(6, actualMaximum - 7);
                        n2 = normalizedCalendar.get(3);
                    }
                    break;
                }
                final CalendarDate calendarDate = calsys.newCalendarDate(TimeZone.NO_TIMEZONE);
                calendarDate.setDate(cdate.getYear(), 1, 1);
                int n5 = calsys.getDayOfWeek(calendarDate) - this.getFirstDayOfWeek();
                if (n5 < 0) {
                    n5 += 7;
                }
                n2 = 52;
                final int n6 = n5 + this.getMinimalDaysInFirstWeek() - 1;
                if (n6 == 6 || (cdate.isLeapYear() && (n6 == 5 || n6 == 12))) {
                    ++n2;
                    break;
                }
                break;
            }
            case 4: {
                if (normalizedCalendar.isCutoverYear(normalizedYear)) {
                    if (normalizedCalendar == this) {
                        normalizedCalendar = (GregorianCalendar)normalizedCalendar.clone();
                    }
                    final int internalGet = normalizedCalendar.internalGet(1);
                    final int internalGet2 = normalizedCalendar.internalGet(2);
                    do {
                        n2 = normalizedCalendar.get(4);
                        normalizedCalendar.add(4, 1);
                    } while (normalizedCalendar.get(1) == internalGet && normalizedCalendar.get(2) == internalGet2);
                    break;
                }
                final CalendarDate calendarDate2 = calsys.newCalendarDate(null);
                calendarDate2.setDate(cdate.getYear(), cdate.getMonth(), 1);
                final int dayOfWeek = calsys.getDayOfWeek(calendarDate2);
                final int monthLength = calsys.getMonthLength(calendarDate2);
                int n7 = dayOfWeek - this.getFirstDayOfWeek();
                if (n7 < 0) {
                    n7 += 7;
                }
                final int n8 = 7 - n7;
                n2 = 3;
                if (n8 >= this.getMinimalDaysInFirstWeek()) {
                    ++n2;
                }
                final int n9 = monthLength - (n8 + 21);
                if (n9 <= 0) {
                    break;
                }
                ++n2;
                if (n9 > 7) {
                    ++n2;
                    break;
                }
                break;
            }
            case 8: {
                final int dayOfWeek2 = cdate.getDayOfWeek();
                int n10;
                int n11;
                if (!normalizedCalendar.isCutoverYear(normalizedYear)) {
                    final BaseCalendar.Date date2 = (BaseCalendar.Date)cdate.clone();
                    n10 = calsys.getMonthLength(date2);
                    date2.setDayOfMonth(1);
                    calsys.normalize(date2);
                    n11 = date2.getDayOfWeek();
                }
                else {
                    if (normalizedCalendar == this) {
                        normalizedCalendar = (GregorianCalendar)this.clone();
                    }
                    n10 = normalizedCalendar.actualMonthLength();
                    normalizedCalendar.set(5, normalizedCalendar.getActualMinimum(5));
                    n11 = normalizedCalendar.get(7);
                }
                int n12 = dayOfWeek2 - n11;
                if (n12 < 0) {
                    n12 += 7;
                }
                n2 = (n10 - n12 + 6) / 7;
                break;
            }
            case 1: {
                if (normalizedCalendar == this) {
                    normalizedCalendar = (GregorianCalendar)this.clone();
                }
                final long yearOffsetInMillis = normalizedCalendar.getYearOffsetInMillis();
                if (normalizedCalendar.internalGetEra() == 1) {
                    normalizedCalendar.setTimeInMillis(Long.MAX_VALUE);
                    n2 = normalizedCalendar.get(1);
                    if (yearOffsetInMillis > normalizedCalendar.getYearOffsetInMillis()) {
                        --n2;
                    }
                }
                else {
                    final BaseCalendar baseCalendar = (normalizedCalendar.getTimeInMillis() >= this.gregorianCutover) ? GregorianCalendar.gcal : getJulianCalendarSystem();
                    final CalendarDate calendarDate3 = baseCalendar.getCalendarDate(Long.MIN_VALUE, this.getZone());
                    final long n13 = ((((calsys.getDayOfYear(calendarDate3) - 1L) * 24L + calendarDate3.getHours()) * 60L + calendarDate3.getMinutes()) * 60L + calendarDate3.getSeconds()) * 1000L + calendarDate3.getMillis();
                    n2 = calendarDate3.getYear();
                    if (n2 <= 0) {
                        assert baseCalendar == GregorianCalendar.gcal;
                        n2 = 1 - n2;
                    }
                    if (yearOffsetInMillis < n13) {
                        --n2;
                    }
                }
                break;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(n);
            }
        }
        return n2;
    }
    
    private long getYearOffsetInMillis() {
        return ((((this.internalGet(6) - 1) * 24 + this.internalGet(11)) * 60L + this.internalGet(12)) * 60L + this.internalGet(13)) * 1000L + this.internalGet(14) - (this.internalGet(15) + this.internalGet(16));
    }
    
    @Override
    public Object clone() {
        final GregorianCalendar gregorianCalendar = (GregorianCalendar)super.clone();
        gregorianCalendar.gdate = (BaseCalendar.Date)this.gdate.clone();
        if (this.cdate != null) {
            if (this.cdate != this.gdate) {
                gregorianCalendar.cdate = (BaseCalendar.Date)this.cdate.clone();
            }
            else {
                gregorianCalendar.cdate = gregorianCalendar.gdate;
            }
        }
        gregorianCalendar.originalFields = null;
        gregorianCalendar.zoneOffsets = null;
        return gregorianCalendar;
    }
    
    @Override
    public TimeZone getTimeZone() {
        final TimeZone timeZone = super.getTimeZone();
        this.gdate.setZone(timeZone);
        if (this.cdate != null && this.cdate != this.gdate) {
            this.cdate.setZone(timeZone);
        }
        return timeZone;
    }
    
    @Override
    public void setTimeZone(final TimeZone zone) {
        super.setTimeZone(zone);
        this.gdate.setZone(zone);
        if (this.cdate != null && this.cdate != this.gdate) {
            this.cdate.setZone(zone);
        }
    }
    
    @Override
    public final boolean isWeekDateSupported() {
        return true;
    }
    
    @Override
    public int getWeekYear() {
        int value = this.get(1);
        if (this.internalGetEra() == 0) {
            value = 1 - value;
        }
        if (value > this.gregorianCutoverYear + 1) {
            final int internalGet = this.internalGet(3);
            if (this.internalGet(2) == 0) {
                if (internalGet >= 52) {
                    --value;
                }
            }
            else if (internalGet == 1) {
                ++value;
            }
            return value;
        }
        final int internalGet2 = this.internalGet(6);
        final int actualMaximum = this.getActualMaximum(6);
        final int minimalDaysInFirstWeek = this.getMinimalDaysInFirstWeek();
        if (internalGet2 > minimalDaysInFirstWeek && internalGet2 < actualMaximum - 6) {
            return value;
        }
        final GregorianCalendar gregorianCalendar = (GregorianCalendar)this.clone();
        gregorianCalendar.setLenient(true);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        gregorianCalendar.set(6, 1);
        gregorianCalendar.complete();
        int n = this.getFirstDayOfWeek() - gregorianCalendar.get(7);
        if (n != 0) {
            if (n < 0) {
                n += 7;
            }
            gregorianCalendar.add(6, n);
        }
        final int value2 = gregorianCalendar.get(6);
        if (internalGet2 < value2) {
            if (value2 <= minimalDaysInFirstWeek) {
                --value;
            }
        }
        else {
            gregorianCalendar.set(1, value + 1);
            gregorianCalendar.set(6, 1);
            gregorianCalendar.complete();
            int n2 = this.getFirstDayOfWeek() - gregorianCalendar.get(7);
            if (n2 != 0) {
                if (n2 < 0) {
                    n2 += 7;
                }
                gregorianCalendar.add(6, n2);
            }
            int n3 = gregorianCalendar.get(6) - 1;
            if (n3 == 0) {
                n3 = 7;
            }
            if (n3 >= minimalDaysInFirstWeek && actualMaximum - internalGet2 + 1 <= 7 - n3) {
                ++value;
            }
        }
        return value;
    }
    
    @Override
    public void setWeekDate(final int n, final int n2, final int n3) {
        if (n3 < 1 || n3 > 7) {
            throw new IllegalArgumentException("invalid dayOfWeek: " + n3);
        }
        final GregorianCalendar gregorianCalendar = (GregorianCalendar)this.clone();
        gregorianCalendar.setLenient(true);
        final int value = gregorianCalendar.get(0);
        gregorianCalendar.clear();
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        gregorianCalendar.set(0, value);
        gregorianCalendar.set(1, n);
        gregorianCalendar.set(3, 1);
        gregorianCalendar.set(7, this.getFirstDayOfWeek());
        int n4 = n3 - this.getFirstDayOfWeek();
        if (n4 < 0) {
            n4 += 7;
        }
        final int n5 = n4 + 7 * (n2 - 1);
        if (n5 != 0) {
            gregorianCalendar.add(6, n5);
        }
        else {
            gregorianCalendar.complete();
        }
        if (!this.isLenient() && (gregorianCalendar.getWeekYear() != n || gregorianCalendar.internalGet(3) != n2 || gregorianCalendar.internalGet(7) != n3)) {
            throw new IllegalArgumentException();
        }
        this.set(0, gregorianCalendar.internalGet(0));
        this.set(1, gregorianCalendar.internalGet(1));
        this.set(2, gregorianCalendar.internalGet(2));
        this.set(5, gregorianCalendar.internalGet(5));
        this.internalSet(3, n2);
        this.complete();
    }
    
    @Override
    public int getWeeksInWeekYear() {
        GregorianCalendar normalizedCalendar = this.getNormalizedCalendar();
        final int weekYear = normalizedCalendar.getWeekYear();
        if (weekYear == normalizedCalendar.internalGet(1)) {
            return normalizedCalendar.getActualMaximum(3);
        }
        if (normalizedCalendar == this) {
            normalizedCalendar = (GregorianCalendar)normalizedCalendar.clone();
        }
        normalizedCalendar.setWeekDate(weekYear, 2, this.internalGet(7));
        return normalizedCalendar.getActualMaximum(3);
    }
    
    @Override
    protected void computeFields() {
        int setStateFields;
        if (this.isPartiallyNormalized()) {
            setStateFields = this.getSetStateFields();
            final int n = ~setStateFields & 0x1FFFF;
            if (n != 0 || this.calsys == null) {
                setStateFields |= this.computeFields(n, setStateFields & 0x18000);
                assert setStateFields == 131071;
            }
        }
        else {
            setStateFields = 131071;
            this.computeFields(setStateFields, 0);
        }
        this.setFieldsComputed(setStateFields);
    }
    
    private int computeFields(final int n, final int n2) {
        int n3 = 0;
        final TimeZone zone = this.getZone();
        if (this.zoneOffsets == null) {
            this.zoneOffsets = new int[2];
        }
        if (n2 != 98304) {
            if (zone instanceof ZoneInfo) {
                n3 = ((ZoneInfo)zone).getOffsets(this.time, this.zoneOffsets);
            }
            else {
                n3 = zone.getOffset(this.time);
                this.zoneOffsets[0] = zone.getRawOffset();
                this.zoneOffsets[1] = n3 - this.zoneOffsets[0];
            }
        }
        if (n2 != 0) {
            if (Calendar.isFieldSet(n2, 15)) {
                this.zoneOffsets[0] = this.internalGet(15);
            }
            if (Calendar.isFieldSet(n2, 16)) {
                this.zoneOffsets[1] = this.internalGet(16);
            }
            n3 = this.zoneOffsets[0] + this.zoneOffsets[1];
        }
        final long n4 = n3 / 86400000L;
        final int n5 = n3 % 86400000;
        long n6 = n4 + this.time / 86400000L;
        int i = n5 + (int)(this.time % 86400000L);
        if (i >= 86400000L) {
            i -= (int)86400000L;
            ++n6;
        }
        else {
            while (i < 0) {
                i += (int)86400000L;
                --n6;
            }
        }
        final long cachedFixedDate = n6 + 719163L;
        int n7 = 1;
        int n8;
        if (cachedFixedDate >= this.gregorianCutoverDate) {
            assert !(!this.gdate.isNormalized()) : "cache control: not normalized";
            assert GregorianCalendar.gcal.getFixedDate(this.gdate.getNormalizedYear(), this.gdate.getMonth(), this.gdate.getDayOfMonth(), this.gdate) == this.cachedFixedDate : "cache control: inconsictency, cachedFixedDate=" + this.cachedFixedDate + ", computed=" + GregorianCalendar.gcal.getFixedDate(this.gdate.getNormalizedYear(), this.gdate.getMonth(), this.gdate.getDayOfMonth(), this.gdate) + ", date=" + this.gdate;
            if (cachedFixedDate != this.cachedFixedDate) {
                GregorianCalendar.gcal.getCalendarDateFromFixedDate(this.gdate, cachedFixedDate);
                this.cachedFixedDate = cachedFixedDate;
            }
            n8 = this.gdate.getYear();
            if (n8 <= 0) {
                n8 = 1 - n8;
                n7 = 0;
            }
            this.calsys = GregorianCalendar.gcal;
            this.cdate = this.gdate;
            assert this.cdate.getDayOfWeek() > 0 : "dow=" + this.cdate.getDayOfWeek() + ", date=" + this.cdate;
        }
        else {
            this.calsys = getJulianCalendarSystem();
            this.cdate = GregorianCalendar.jcal.newCalendarDate(this.getZone());
            GregorianCalendar.jcal.getCalendarDateFromFixedDate(this.cdate, cachedFixedDate);
            if (this.cdate.getEra() == GregorianCalendar.jeras[0]) {
                n7 = 0;
            }
            n8 = this.cdate.getYear();
        }
        this.internalSet(0, n7);
        this.internalSet(1, n8);
        int n9 = n | 0x3;
        final int n10 = this.cdate.getMonth() - 1;
        final int dayOfMonth = this.cdate.getDayOfMonth();
        if ((n & 0xA4) != 0x0) {
            this.internalSet(2, n10);
            this.internalSet(5, dayOfMonth);
            this.internalSet(7, this.cdate.getDayOfWeek());
            n9 |= 0xA4;
        }
        if ((n & 0x7E00) != 0x0) {
            if (i != 0) {
                final int n11 = i / 3600000;
                this.internalSet(11, n11);
                this.internalSet(9, n11 / 12);
                this.internalSet(10, n11 % 12);
                final int n12 = i % 3600000;
                this.internalSet(12, n12 / 60000);
                final int n13 = n12 % 60000;
                this.internalSet(13, n13 / 1000);
                this.internalSet(14, n13 % 1000);
            }
            else {
                this.internalSet(11, 0);
                this.internalSet(9, 0);
                this.internalSet(10, 0);
                this.internalSet(12, 0);
                this.internalSet(13, 0);
                this.internalSet(14, 0);
            }
            n9 |= 0x7E00;
        }
        if ((n & 0x18000) != 0x0) {
            this.internalSet(15, this.zoneOffsets[0]);
            this.internalSet(16, this.zoneOffsets[1]);
            n9 |= 0x18000;
        }
        if ((n & 0x158) != 0x0) {
            final int normalizedYear = this.cdate.getNormalizedYear();
            long n14 = this.calsys.getFixedDate(normalizedYear, 1, 1, this.cdate);
            int n15 = (int)(cachedFixedDate - n14) + 1;
            long fixedDateMonth1 = cachedFixedDate - dayOfMonth + 1L;
            final int n16 = (this.calsys == GregorianCalendar.gcal) ? this.gregorianCutoverYear : this.gregorianCutoverYearJulian;
            int n17 = dayOfMonth - 1;
            if (normalizedYear == n16) {
                if (this.gregorianCutoverYearJulian <= this.gregorianCutoverYear) {
                    n14 = this.getFixedDateJan1(this.cdate, cachedFixedDate);
                    if (cachedFixedDate >= this.gregorianCutoverDate) {
                        fixedDateMonth1 = this.getFixedDateMonth1(this.cdate, cachedFixedDate);
                    }
                }
                n15 = (int)(cachedFixedDate - n14) + 1;
                n17 = (int)(cachedFixedDate - fixedDateMonth1);
            }
            this.internalSet(6, n15);
            this.internalSet(8, n17 / 7 + 1);
            int n18 = this.getWeekNumber(n14, cachedFixedDate);
            if (n18 == 0) {
                final long n19 = n14 - 1L;
                long n20 = n14 - 365L;
                if (normalizedYear > n16 + 1) {
                    if (CalendarUtils.isGregorianLeapYear(normalizedYear - 1)) {
                        --n20;
                    }
                }
                else if (normalizedYear <= this.gregorianCutoverYearJulian) {
                    if (CalendarUtils.isJulianLeapYear(normalizedYear - 1)) {
                        --n20;
                    }
                }
                else {
                    final BaseCalendar calsys = this.calsys;
                    final int normalizedYear2 = this.getCalendarDate(n19).getNormalizedYear();
                    if (normalizedYear2 == this.gregorianCutoverYear) {
                        final BaseCalendar cutoverCalendarSystem = this.getCutoverCalendarSystem();
                        if (cutoverCalendarSystem == GregorianCalendar.jcal) {
                            n20 = cutoverCalendarSystem.getFixedDate(normalizedYear2, 1, 1, null);
                        }
                        else {
                            n20 = this.gregorianCutoverDate;
                            final Gregorian gcal = GregorianCalendar.gcal;
                        }
                    }
                    else if (normalizedYear2 <= this.gregorianCutoverYearJulian) {
                        n20 = getJulianCalendarSystem().getFixedDate(normalizedYear2, 1, 1, null);
                    }
                }
                n18 = this.getWeekNumber(n20, n19);
            }
            else if (normalizedYear > this.gregorianCutoverYear || normalizedYear < this.gregorianCutoverYearJulian - 1) {
                if (n18 >= 52) {
                    long n21 = n14 + 365L;
                    if (this.cdate.isLeapYear()) {
                        ++n21;
                    }
                    final long dayOfWeekDateOnOrBefore = AbstractCalendar.getDayOfWeekDateOnOrBefore(n21 + 6L, this.getFirstDayOfWeek());
                    if ((int)(dayOfWeekDateOnOrBefore - n21) >= this.getMinimalDaysInFirstWeek() && cachedFixedDate >= dayOfWeekDateOnOrBefore - 7L) {
                        n18 = 1;
                    }
                }
            }
            else {
                BaseCalendar baseCalendar = this.calsys;
                int gregorianCutoverYear = normalizedYear + 1;
                if (gregorianCutoverYear == this.gregorianCutoverYearJulian + 1 && gregorianCutoverYear < this.gregorianCutoverYear) {
                    gregorianCutoverYear = this.gregorianCutoverYear;
                }
                if (gregorianCutoverYear == this.gregorianCutoverYear) {
                    baseCalendar = this.getCutoverCalendarSystem();
                }
                long n22;
                if (gregorianCutoverYear > this.gregorianCutoverYear || this.gregorianCutoverYearJulian == this.gregorianCutoverYear || gregorianCutoverYear == this.gregorianCutoverYearJulian) {
                    n22 = baseCalendar.getFixedDate(gregorianCutoverYear, 1, 1, null);
                }
                else {
                    n22 = this.gregorianCutoverDate;
                    final Gregorian gcal2 = GregorianCalendar.gcal;
                }
                final long dayOfWeekDateOnOrBefore2 = AbstractCalendar.getDayOfWeekDateOnOrBefore(n22 + 6L, this.getFirstDayOfWeek());
                if ((int)(dayOfWeekDateOnOrBefore2 - n22) >= this.getMinimalDaysInFirstWeek() && cachedFixedDate >= dayOfWeekDateOnOrBefore2 - 7L) {
                    n18 = 1;
                }
            }
            this.internalSet(3, n18);
            this.internalSet(4, this.getWeekNumber(fixedDateMonth1, cachedFixedDate));
            n9 |= 0x158;
        }
        return n9;
    }
    
    private int getWeekNumber(final long n, final long n2) {
        long dayOfWeekDateOnOrBefore = AbstractCalendar.getDayOfWeekDateOnOrBefore(n + 6L, this.getFirstDayOfWeek());
        final int n3 = (int)(dayOfWeekDateOnOrBefore - n);
        assert n3 <= 7;
        if (n3 >= this.getMinimalDaysInFirstWeek()) {
            dayOfWeekDateOnOrBefore -= 7L;
        }
        final int n4 = (int)(n2 - dayOfWeekDateOnOrBefore);
        if (n4 >= 0) {
            return n4 / 7 + 1;
        }
        return CalendarUtils.floorDivide(n4, 7) + 1;
    }
    
    @Override
    protected void computeTime() {
        if (!this.isLenient()) {
            if (this.originalFields == null) {
                this.originalFields = new int[17];
            }
            for (int i = 0; i < 17; ++i) {
                final int internalGet = this.internalGet(i);
                if (this.isExternallySet(i) && (internalGet < this.getMinimum(i) || internalGet > this.getMaximum(i))) {
                    throw new IllegalArgumentException(Calendar.getFieldName(i));
                }
                this.originalFields[i] = internalGet;
            }
        }
        int selectFields = this.selectFields();
        int n = this.isSet(1) ? this.internalGet(1) : 1970;
        final int internalGetEra = this.internalGetEra();
        if (internalGetEra == 0) {
            n = 1 - n;
        }
        else if (internalGetEra != 1) {
            throw new IllegalArgumentException("Invalid era");
        }
        if (n <= 0 && !this.isSet(0)) {
            selectFields |= 0x1;
            this.setFieldsComputed(1);
        }
        final long n2 = 0L;
        long n3;
        if (Calendar.isFieldSet(selectFields, 11)) {
            n3 = n2 + this.internalGet(11);
        }
        else {
            n3 = n2 + this.internalGet(10);
            if (Calendar.isFieldSet(selectFields, 9)) {
                n3 += 12 * this.internalGet(9);
            }
        }
        final long n4 = ((n3 * 60L + this.internalGet(12)) * 60L + this.internalGet(13)) * 1000L + this.internalGet(14);
        long n5;
        long n6;
        for (n5 = n4 / 86400000L, n6 = n4 % 86400000L; n6 < 0L; n6 += 86400000L, --n5) {}
        long n8 = 0L;
        Label_0619: {
            long n7;
            long n9;
            if (n > this.gregorianCutoverYear && n > this.gregorianCutoverYearJulian) {
                n7 = n5 + this.getFixedDate(GregorianCalendar.gcal, n, selectFields);
                if (n7 >= this.gregorianCutoverDate) {
                    n8 = n7;
                    break Label_0619;
                }
                n9 = n5 + this.getFixedDate(getJulianCalendarSystem(), n, selectFields);
            }
            else if (n < this.gregorianCutoverYear && n < this.gregorianCutoverYearJulian) {
                n9 = n5 + this.getFixedDate(getJulianCalendarSystem(), n, selectFields);
                if (n9 < this.gregorianCutoverDate) {
                    n8 = n9;
                    break Label_0619;
                }
                n7 = n9;
            }
            else {
                n9 = n5 + this.getFixedDate(getJulianCalendarSystem(), n, selectFields);
                n7 = n5 + this.getFixedDate(GregorianCalendar.gcal, n, selectFields);
            }
            if (Calendar.isFieldSet(selectFields, 6) || Calendar.isFieldSet(selectFields, 3)) {
                if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian) {
                    n8 = n9;
                    break Label_0619;
                }
                if (n == this.gregorianCutoverYear) {
                    n8 = n7;
                    break Label_0619;
                }
            }
            if (n7 >= this.gregorianCutoverDate) {
                if (n9 >= this.gregorianCutoverDate) {
                    n8 = n7;
                }
                else if (this.calsys == GregorianCalendar.gcal || this.calsys == null) {
                    n8 = n7;
                }
                else {
                    n8 = n9;
                }
            }
            else if (n9 < this.gregorianCutoverDate) {
                n8 = n9;
            }
            else {
                if (!this.isLenient()) {
                    throw new IllegalArgumentException("the specified date doesn't exist");
                }
                n8 = n9;
            }
        }
        final long n10 = (n8 - 719163L) * 86400000L + n6;
        final TimeZone zone = this.getZone();
        if (this.zoneOffsets == null) {
            this.zoneOffsets = new int[2];
        }
        final int n11 = selectFields & 0x18000;
        if (n11 != 98304) {
            if (zone instanceof ZoneInfo) {
                ((ZoneInfo)zone).getOffsetsByWall(n10, this.zoneOffsets);
            }
            else {
                zone.getOffsets(n10 - (Calendar.isFieldSet(selectFields, 15) ? this.internalGet(15) : zone.getRawOffset()), this.zoneOffsets);
            }
        }
        if (n11 != 0) {
            if (Calendar.isFieldSet(n11, 15)) {
                this.zoneOffsets[0] = this.internalGet(15);
            }
            if (Calendar.isFieldSet(n11, 16)) {
                this.zoneOffsets[1] = this.internalGet(16);
            }
        }
        this.time = n10 - (this.zoneOffsets[0] + this.zoneOffsets[1]);
        final int computeFields = this.computeFields(selectFields | this.getSetStateFields(), n11);
        if (!this.isLenient()) {
            for (int j = 0; j < 17; ++j) {
                if (this.isExternallySet(j)) {
                    if (this.originalFields[j] != this.internalGet(j)) {
                        final String string = this.originalFields[j] + " -> " + this.internalGet(j);
                        System.arraycopy(this.originalFields, 0, this.fields, 0, this.fields.length);
                        throw new IllegalArgumentException(Calendar.getFieldName(j) + ": " + string);
                    }
                }
            }
        }
        this.setFieldsNormalized(computeFields);
    }
    
    private long getFixedDate(final BaseCalendar baseCalendar, int n, final int n2) {
        int internalGet = 0;
        if (Calendar.isFieldSet(n2, 2)) {
            internalGet = this.internalGet(2);
            if (internalGet > 11) {
                n += internalGet / 12;
                internalGet %= 12;
            }
            else if (internalGet < 0) {
                final int[] array = { 0 };
                n += CalendarUtils.floorDivide(internalGet, 12, array);
                internalGet = array[0];
            }
        }
        long n3 = baseCalendar.getFixedDate(n, internalGet + 1, 1, (baseCalendar == GregorianCalendar.gcal) ? this.gdate : null);
        if (Calendar.isFieldSet(n2, 2)) {
            if (Calendar.isFieldSet(n2, 5)) {
                if (this.isSet(5)) {
                    n3 = n3 + this.internalGet(5) - 1L;
                }
            }
            else if (Calendar.isFieldSet(n2, 4)) {
                long n4 = AbstractCalendar.getDayOfWeekDateOnOrBefore(n3 + 6L, this.getFirstDayOfWeek());
                if (n4 - n3 >= this.getMinimalDaysInFirstWeek()) {
                    n4 -= 7L;
                }
                if (Calendar.isFieldSet(n2, 7)) {
                    n4 = AbstractCalendar.getDayOfWeekDateOnOrBefore(n4 + 6L, this.internalGet(7));
                }
                n3 = n4 + 7 * (this.internalGet(4) - 1);
            }
            else {
                int n5;
                if (Calendar.isFieldSet(n2, 7)) {
                    n5 = this.internalGet(7);
                }
                else {
                    n5 = this.getFirstDayOfWeek();
                }
                int internalGet2;
                if (Calendar.isFieldSet(n2, 8)) {
                    internalGet2 = this.internalGet(8);
                }
                else {
                    internalGet2 = 1;
                }
                if (internalGet2 >= 0) {
                    n3 = AbstractCalendar.getDayOfWeekDateOnOrBefore(n3 + 7 * internalGet2 - 1L, n5);
                }
                else {
                    n3 = AbstractCalendar.getDayOfWeekDateOnOrBefore(n3 + (this.monthLength(internalGet, n) + 7 * (internalGet2 + 1)) - 1L, n5);
                }
            }
        }
        else {
            if (n == this.gregorianCutoverYear && baseCalendar == GregorianCalendar.gcal && n3 < this.gregorianCutoverDate && this.gregorianCutoverYear != this.gregorianCutoverYearJulian) {
                n3 = this.gregorianCutoverDate;
            }
            if (Calendar.isFieldSet(n2, 6)) {
                n3 = n3 + this.internalGet(6) - 1L;
            }
            else {
                long n6 = AbstractCalendar.getDayOfWeekDateOnOrBefore(n3 + 6L, this.getFirstDayOfWeek());
                if (n6 - n3 >= this.getMinimalDaysInFirstWeek()) {
                    n6 -= 7L;
                }
                if (Calendar.isFieldSet(n2, 7)) {
                    final int internalGet3 = this.internalGet(7);
                    if (internalGet3 != this.getFirstDayOfWeek()) {
                        n6 = AbstractCalendar.getDayOfWeekDateOnOrBefore(n6 + 6L, internalGet3);
                    }
                }
                n3 = n6 + 7L * (this.internalGet(3) - 1L);
            }
        }
        return n3;
    }
    
    private GregorianCalendar getNormalizedCalendar() {
        GregorianCalendar gregorianCalendar;
        if (this.isFullyNormalized()) {
            gregorianCalendar = this;
        }
        else {
            gregorianCalendar = (GregorianCalendar)this.clone();
            gregorianCalendar.setLenient(true);
            gregorianCalendar.complete();
        }
        return gregorianCalendar;
    }
    
    private static synchronized BaseCalendar getJulianCalendarSystem() {
        if (GregorianCalendar.jcal == null) {
            GregorianCalendar.jcal = (JulianCalendar)CalendarSystem.forName("julian");
            GregorianCalendar.jeras = GregorianCalendar.jcal.getEras();
        }
        return GregorianCalendar.jcal;
    }
    
    private BaseCalendar getCutoverCalendarSystem() {
        if (this.gregorianCutoverYearJulian < this.gregorianCutoverYear) {
            return GregorianCalendar.gcal;
        }
        return getJulianCalendarSystem();
    }
    
    private boolean isCutoverYear(final int n) {
        return n == ((this.calsys == GregorianCalendar.gcal) ? this.gregorianCutoverYear : this.gregorianCutoverYearJulian);
    }
    
    private long getFixedDateJan1(final BaseCalendar.Date date, final long n) {
        assert date.getNormalizedYear() == this.gregorianCutoverYearJulian;
        if (this.gregorianCutoverYear != this.gregorianCutoverYearJulian && n >= this.gregorianCutoverDate) {
            return this.gregorianCutoverDate;
        }
        return getJulianCalendarSystem().getFixedDate(date.getNormalizedYear(), 1, 1, null);
    }
    
    private long getFixedDateMonth1(final BaseCalendar.Date date, final long n) {
        assert date.getNormalizedYear() == this.gregorianCutoverYearJulian;
        final BaseCalendar.Date gregorianCutoverDate = this.getGregorianCutoverDate();
        if (gregorianCutoverDate.getMonth() == 1 && gregorianCutoverDate.getDayOfMonth() == 1) {
            return n - date.getDayOfMonth() + 1L;
        }
        long n2;
        if (date.getMonth() == gregorianCutoverDate.getMonth()) {
            final BaseCalendar.Date lastJulianDate = this.getLastJulianDate();
            if (this.gregorianCutoverYear == this.gregorianCutoverYearJulian && gregorianCutoverDate.getMonth() == lastJulianDate.getMonth()) {
                n2 = GregorianCalendar.jcal.getFixedDate(date.getNormalizedYear(), date.getMonth(), 1, null);
            }
            else {
                n2 = this.gregorianCutoverDate;
            }
        }
        else {
            n2 = n - date.getDayOfMonth() + 1L;
        }
        return n2;
    }
    
    private BaseCalendar.Date getCalendarDate(final long n) {
        final BaseCalendar baseCalendar = (n >= this.gregorianCutoverDate) ? GregorianCalendar.gcal : getJulianCalendarSystem();
        final BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
        baseCalendar.getCalendarDateFromFixedDate(date, n);
        return date;
    }
    
    private BaseCalendar.Date getGregorianCutoverDate() {
        return this.getCalendarDate(this.gregorianCutoverDate);
    }
    
    private BaseCalendar.Date getLastJulianDate() {
        return this.getCalendarDate(this.gregorianCutoverDate - 1L);
    }
    
    private int monthLength(final int n, final int n2) {
        return this.isLeapYear(n2) ? GregorianCalendar.LEAP_MONTH_LENGTH[n] : GregorianCalendar.MONTH_LENGTH[n];
    }
    
    private int monthLength(final int n) {
        int internalGet = this.internalGet(1);
        if (this.internalGetEra() == 0) {
            internalGet = 1 - internalGet;
        }
        return this.monthLength(n, internalGet);
    }
    
    private int actualMonthLength() {
        final int normalizedYear = this.cdate.getNormalizedYear();
        if (normalizedYear != this.gregorianCutoverYear && normalizedYear != this.gregorianCutoverYearJulian) {
            return this.calsys.getMonthLength(this.cdate);
        }
        BaseCalendar.Date calendarDate = (BaseCalendar.Date)this.cdate.clone();
        final long fixedDateMonth1 = this.getFixedDateMonth1(calendarDate, this.calsys.getFixedDate(calendarDate));
        final long n = fixedDateMonth1 + this.calsys.getMonthLength(calendarDate);
        if (n < this.gregorianCutoverDate) {
            return (int)(n - fixedDateMonth1);
        }
        if (this.cdate != this.gdate) {
            calendarDate = GregorianCalendar.gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
        }
        GregorianCalendar.gcal.getCalendarDateFromFixedDate(calendarDate, n);
        return (int)(this.getFixedDateMonth1(calendarDate, n) - fixedDateMonth1);
    }
    
    private int yearLength(final int n) {
        return this.isLeapYear(n) ? 366 : 365;
    }
    
    private int yearLength() {
        int internalGet = this.internalGet(1);
        if (this.internalGetEra() == 0) {
            internalGet = 1 - internalGet;
        }
        return this.yearLength(internalGet);
    }
    
    private void pinDayOfMonth() {
        final int internalGet = this.internalGet(1);
        int n;
        if (internalGet > this.gregorianCutoverYear || internalGet < this.gregorianCutoverYearJulian) {
            n = this.monthLength(this.internalGet(2));
        }
        else {
            n = this.getNormalizedCalendar().getActualMaximum(5);
        }
        if (this.internalGet(5) > n) {
            this.set(5, n);
        }
    }
    
    private long getCurrentFixedDate() {
        return (this.calsys == GregorianCalendar.gcal) ? this.cachedFixedDate : this.calsys.getFixedDate(this.cdate);
    }
    
    private static int getRolledValue(final int n, int n2, final int n3, final int n4) {
        assert n >= n3 && n <= n4;
        final int n5 = n4 - n3 + 1;
        n2 %= n5;
        int n6 = n + n2;
        if (n6 > n4) {
            n6 -= n5;
        }
        else if (n6 < n3) {
            n6 += n5;
        }
        assert n6 >= n3 && n6 <= n4;
        return n6;
    }
    
    private int internalGetEra() {
        return this.isSet(0) ? this.internalGet(0) : 1;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.gdate == null) {
            this.gdate = GregorianCalendar.gcal.newCalendarDate(this.getZone());
            this.cachedFixedDate = Long.MIN_VALUE;
        }
        this.setGregorianChange(this.gregorianCutover);
    }
    
    public ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(this.getTimeInMillis()), this.getTimeZone().toZoneId());
    }
    
    public static GregorianCalendar from(final ZonedDateTime zonedDateTime) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(zonedDateTime.getZone()));
        gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
        gregorianCalendar.setFirstDayOfWeek(2);
        gregorianCalendar.setMinimalDaysInFirstWeek(4);
        try {
            gregorianCalendar.setTimeInMillis(Math.addExact(Math.multiplyExact(zonedDateTime.toEpochSecond(), 1000L), zonedDateTime.get(ChronoField.MILLI_OF_SECOND)));
        }
        catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
        return gregorianCalendar;
    }
    
    static {
        MONTH_LENGTH = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        LEAP_MONTH_LENGTH = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        MIN_VALUES = new int[] { 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, -46800000, 0 };
        LEAST_MAX_VALUES = new int[] { 1, 292269054, 11, 52, 4, 28, 365, 7, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000 };
        MAX_VALUES = new int[] { 1, 292278994, 11, 53, 6, 31, 366, 7, 6, 1, 11, 23, 59, 59, 999, 50400000, 7200000 };
        gcal = CalendarSystem.getGregorianCalendar();
    }
}
