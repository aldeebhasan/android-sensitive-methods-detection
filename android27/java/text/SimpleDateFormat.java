package java.text;

import sun.util.locale.provider.*;
import sun.util.calendar.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public class SimpleDateFormat extends DateFormat
{
    static final long serialVersionUID = 4774881970558875024L;
    static final int currentSerialVersion = 1;
    private int serialVersionOnStream;
    private String pattern;
    private transient NumberFormat originalNumberFormat;
    private transient String originalNumberPattern;
    private transient char minusSign;
    private transient boolean hasFollowingMinusSign;
    private transient boolean forceStandaloneForm;
    private transient char[] compiledPattern;
    private static final int TAG_QUOTE_ASCII_CHAR = 100;
    private static final int TAG_QUOTE_CHARS = 101;
    private transient char zeroDigit;
    private DateFormatSymbols formatData;
    private Date defaultCenturyStart;
    private transient int defaultCenturyStartYear;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final String GMT = "GMT";
    private static final ConcurrentMap<Locale, NumberFormat> cachedNumberFormatData;
    private Locale locale;
    transient boolean useDateFormatSymbols;
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD;
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD;
    private static final Field[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID;
    private static final int[] REST_OF_STYLES;
    
    public SimpleDateFormat() {
        this("", Locale.getDefault(Locale.Category.FORMAT));
        this.applyPatternImpl(LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(this.locale).getDateTimePattern(3, 3, this.calendar));
    }
    
    public SimpleDateFormat(final String s) {
        this(s, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public SimpleDateFormat(final String pattern, final Locale locale) {
        this.serialVersionOnStream = 1;
        this.minusSign = '-';
        this.hasFollowingMinusSign = false;
        this.forceStandaloneForm = false;
        if (pattern == null || locale == null) {
            throw new NullPointerException();
        }
        this.initializeCalendar(locale);
        this.pattern = pattern;
        this.formatData = DateFormatSymbols.getInstanceRef(locale);
        this.initialize(this.locale = locale);
    }
    
    public SimpleDateFormat(final String pattern, final DateFormatSymbols dateFormatSymbols) {
        this.serialVersionOnStream = 1;
        this.minusSign = '-';
        this.hasFollowingMinusSign = false;
        this.forceStandaloneForm = false;
        if (pattern == null || dateFormatSymbols == null) {
            throw new NullPointerException();
        }
        this.pattern = pattern;
        this.formatData = (DateFormatSymbols)dateFormatSymbols.clone();
        this.initializeCalendar(this.locale = Locale.getDefault(Locale.Category.FORMAT));
        this.initialize(this.locale);
        this.useDateFormatSymbols = true;
    }
    
    private void initialize(final Locale locale) {
        this.compiledPattern = this.compile(this.pattern);
        this.numberFormat = SimpleDateFormat.cachedNumberFormatData.get(locale);
        if (this.numberFormat == null) {
            (this.numberFormat = NumberFormat.getIntegerInstance(locale)).setGroupingUsed(false);
            SimpleDateFormat.cachedNumberFormatData.putIfAbsent(locale, this.numberFormat);
        }
        this.numberFormat = (NumberFormat)this.numberFormat.clone();
        this.initializeDefaultCentury();
    }
    
    private void initializeCalendar(final Locale locale) {
        if (this.calendar == null) {
            assert locale != null;
            this.calendar = Calendar.getInstance(TimeZone.getDefault(), locale);
        }
    }
    
    private char[] compile(final String s) {
        final int length = s.length();
        int n = 0;
        final StringBuilder sb = new StringBuilder(length * 2);
        StringBuilder sb2 = null;
        int n2 = 0;
        int n3 = 0;
        int n4 = -1;
        int n5 = -1;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\'') {
                if (i + 1 < length) {
                    final char char2 = s.charAt(i + 1);
                    if (char2 == '\'') {
                        ++i;
                        if (n2 != 0) {
                            encode(n4, n2, sb);
                            ++n3;
                            n5 = n4;
                            n4 = -1;
                            n2 = 0;
                        }
                        if (n != 0) {
                            sb2.append(char2);
                            continue;
                        }
                        sb.append((char)('\u6400' | char2));
                        continue;
                    }
                }
                if (n == 0) {
                    if (n2 != 0) {
                        encode(n4, n2, sb);
                        ++n3;
                        n5 = n4;
                        n4 = -1;
                        n2 = 0;
                    }
                    if (sb2 == null) {
                        sb2 = new StringBuilder(length);
                    }
                    else {
                        sb2.setLength(0);
                    }
                    n = 1;
                }
                else {
                    final int length2 = sb2.length();
                    if (length2 == 1) {
                        final char char3 = sb2.charAt(0);
                        if (char3 < '\u0080') {
                            sb.append((char)('\u6400' | char3));
                        }
                        else {
                            sb.append('\u6501');
                            sb.append(char3);
                        }
                    }
                    else {
                        encode(101, length2, sb);
                        sb.append((CharSequence)sb2);
                    }
                    n = 0;
                }
            }
            else if (n != 0) {
                sb2.append(char1);
            }
            else if ((char1 < 'a' || char1 > 'z') && (char1 < 'A' || char1 > 'Z')) {
                if (n2 != 0) {
                    encode(n4, n2, sb);
                    ++n3;
                    n5 = n4;
                    n4 = -1;
                    n2 = 0;
                }
                if (char1 < '\u0080') {
                    sb.append((char)('\u6400' | char1));
                }
                else {
                    int j;
                    for (j = i + 1; j < length; ++j) {
                        final char char4 = s.charAt(j);
                        if (char4 == '\'' || (char4 >= 'a' && char4 <= 'z')) {
                            break;
                        }
                        if (char4 >= 'A' && char4 <= 'Z') {
                            break;
                        }
                    }
                    sb.append((char)(0x6500 | j - i));
                    while (i < j) {
                        sb.append(s.charAt(i));
                        ++i;
                    }
                    --i;
                }
            }
            else {
                final int index;
                if ((index = "GyMdkHmsSEDFwWahKzZYuXL".indexOf(char1)) == -1) {
                    throw new IllegalArgumentException("Illegal pattern character '" + char1 + "'");
                }
                if (n4 == -1 || n4 == index) {
                    n4 = index;
                    ++n2;
                }
                else {
                    encode(n4, n2, sb);
                    ++n3;
                    n5 = n4;
                    n4 = index;
                    n2 = 1;
                }
            }
        }
        if (n != 0) {
            throw new IllegalArgumentException("Unterminated quote");
        }
        if (n2 != 0) {
            encode(n4, n2, sb);
            ++n3;
            n5 = n4;
        }
        this.forceStandaloneForm = (n3 == 1 && n5 == 2);
        final int length3 = sb.length();
        final char[] array = new char[length3];
        sb.getChars(0, length3, array, 0);
        return array;
    }
    
    private static void encode(final int n, final int n2, final StringBuilder sb) {
        if (n == 21 && n2 >= 4) {
            throw new IllegalArgumentException("invalid ISO 8601 format: length=" + n2);
        }
        if (n2 < 255) {
            sb.append((char)(n << 8 | n2));
        }
        else {
            sb.append((char)(n << 8 | 0xFF));
            sb.append((char)(n2 >>> 16));
            sb.append((char)(n2 & 0xFFFF));
        }
    }
    
    private void initializeDefaultCentury() {
        this.calendar.setTimeInMillis(System.currentTimeMillis());
        this.calendar.add(1, -80);
        this.parseAmbiguousDatesAsAfter(this.calendar.getTime());
    }
    
    private void parseAmbiguousDatesAsAfter(final Date date) {
        this.defaultCenturyStart = date;
        this.calendar.setTime(date);
        this.defaultCenturyStartYear = this.calendar.get(1);
    }
    
    public void set2DigitYearStart(final Date date) {
        this.parseAmbiguousDatesAsAfter(new Date(date.getTime()));
    }
    
    public Date get2DigitYearStart() {
        return (Date)this.defaultCenturyStart.clone();
    }
    
    @Override
    public StringBuffer format(final Date date, final StringBuffer sb, final FieldPosition fieldPosition) {
        final boolean b = false;
        fieldPosition.endIndex = (b ? 1 : 0);
        fieldPosition.beginIndex = (b ? 1 : 0);
        return this.format(date, sb, fieldPosition.getFieldDelegate());
    }
    
    private StringBuffer format(final Date time, final StringBuffer sb, final FieldDelegate fieldDelegate) {
        this.calendar.setTime(time);
        final boolean useDateFormatSymbols = this.useDateFormatSymbols();
        char c = '\0';
        while (c < this.compiledPattern.length) {
            final char c2 = (char)(this.compiledPattern[c] >>> 8);
            char c3 = (char)(this.compiledPattern[c++] & '\u00ff');
            if (c3 == '\u00ff') {
                c3 = (char)(this.compiledPattern[c++] << 16 | this.compiledPattern[c++]);
            }
            switch (c2) {
                case 100: {
                    sb.append(c3);
                    continue;
                }
                case 101: {
                    sb.append(this.compiledPattern, c, c3);
                    c += c3;
                    continue;
                }
                default: {
                    this.subFormat(c2, c3, fieldDelegate, sb, useDateFormatSymbols);
                    continue;
                }
            }
        }
        return sb;
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        final StringBuffer sb = new StringBuffer();
        final CharacterIteratorFieldDelegate characterIteratorFieldDelegate = new CharacterIteratorFieldDelegate();
        if (o instanceof Date) {
            this.format((Date)o, sb, characterIteratorFieldDelegate);
        }
        else if (o instanceof Number) {
            this.format(new Date(((Number)o).longValue()), sb, characterIteratorFieldDelegate);
        }
        else {
            if (o == null) {
                throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
            }
            throw new IllegalArgumentException("Cannot format given Object as a Date");
        }
        return characterIteratorFieldDelegate.getIterator(sb.toString());
    }
    
    private void subFormat(int n, final int n2, final FieldDelegate fieldDelegate, final StringBuffer sb, final boolean b) {
        final int n3 = Integer.MAX_VALUE;
        String s = null;
        final int length = sb.length();
        int n4 = SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[n];
        int n5;
        if (n4 == 17) {
            if (this.calendar.isWeekDateSupported()) {
                n5 = this.calendar.getWeekYear();
            }
            else {
                n = 1;
                n4 = SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[n];
                n5 = this.calendar.get(n4);
            }
        }
        else if (n4 == 1000) {
            n5 = CalendarBuilder.toISODayOfWeek(this.calendar.get(7));
        }
        else {
            n5 = this.calendar.get(n4);
        }
        final int n6 = (n2 >= 4) ? 2 : 1;
        if (!b && n4 < 15 && n != 22) {
            s = this.calendar.getDisplayName(n4, n6, this.locale);
        }
        switch (n) {
            case 0: {
                if (b) {
                    final String[] eras = this.formatData.getEras();
                    if (n5 < eras.length) {
                        s = eras[n5];
                    }
                }
                if (s == null) {
                    s = "";
                    break;
                }
                break;
            }
            case 1:
            case 19: {
                if (this.calendar instanceof GregorianCalendar) {
                    if (n2 != 2) {
                        this.zeroPaddingNumber(n5, n2, n3, sb);
                        break;
                    }
                    this.zeroPaddingNumber(n5, 2, 2, sb);
                    break;
                }
                else {
                    if (s == null) {
                        this.zeroPaddingNumber(n5, (n6 == 2) ? 1 : n2, n3, sb);
                        break;
                    }
                    break;
                }
                break;
            }
            case 2: {
                if (b) {
                    if (n2 >= 4) {
                        s = this.formatData.getMonths()[n5];
                    }
                    else if (n2 == 3) {
                        s = this.formatData.getShortMonths()[n5];
                    }
                }
                else if (n2 < 3) {
                    s = null;
                }
                else if (this.forceStandaloneForm) {
                    s = this.calendar.getDisplayName(n4, n6 | 0x8000, this.locale);
                    if (s == null) {
                        s = this.calendar.getDisplayName(n4, n6, this.locale);
                    }
                }
                if (s == null) {
                    this.zeroPaddingNumber(n5 + 1, n2, n3, sb);
                    break;
                }
                break;
            }
            case 22: {
                assert s == null;
                if (this.locale == null) {
                    if (n2 >= 4) {
                        s = this.formatData.getMonths()[n5];
                    }
                    else if (n2 == 3) {
                        s = this.formatData.getShortMonths()[n5];
                    }
                }
                else if (n2 >= 3) {
                    s = this.calendar.getDisplayName(n4, n6 | 0x8000, this.locale);
                }
                if (s == null) {
                    this.zeroPaddingNumber(n5 + 1, n2, n3, sb);
                    break;
                }
                break;
            }
            case 4: {
                if (s != null) {
                    break;
                }
                if (n5 == 0) {
                    this.zeroPaddingNumber(this.calendar.getMaximum(11) + 1, n2, n3, sb);
                    break;
                }
                this.zeroPaddingNumber(n5, n2, n3, sb);
                break;
            }
            case 9: {
                if (b) {
                    if (n2 >= 4) {
                        s = this.formatData.getWeekdays()[n5];
                    }
                    else {
                        s = this.formatData.getShortWeekdays()[n5];
                    }
                    break;
                }
                break;
            }
            case 14: {
                if (b) {
                    s = this.formatData.getAmPmStrings()[n5];
                    break;
                }
                break;
            }
            case 15: {
                if (s != null) {
                    break;
                }
                if (n5 == 0) {
                    this.zeroPaddingNumber(this.calendar.getLeastMaximum(10) + 1, n2, n3, sb);
                    break;
                }
                this.zeroPaddingNumber(n5, n2, n3, sb);
                break;
            }
            case 17: {
                if (s != null) {
                    break;
                }
                if (this.formatData.locale == null || this.formatData.isZoneStringsSet) {
                    final int zoneIndex = this.formatData.getZoneIndex(this.calendar.getTimeZone().getID());
                    if (zoneIndex == -1) {
                        sb.append(ZoneInfoFile.toCustomID(this.calendar.get(15) + this.calendar.get(16)));
                    }
                    else {
                        int n7 = (this.calendar.get(16) == 0) ? 1 : 3;
                        if (n2 < 4) {
                            ++n7;
                        }
                        sb.append(this.formatData.getZoneStringsWrapper()[zoneIndex][n7]);
                    }
                    break;
                }
                sb.append(this.calendar.getTimeZone().getDisplayName(this.calendar.get(16) != 0, (int)((n2 >= 4) ? 1 : 0), this.formatData.locale));
                break;
            }
            case 18: {
                final int n8 = (this.calendar.get(15) + this.calendar.get(16)) / 60000;
                int n9 = 4;
                if (n8 >= 0) {
                    sb.append('+');
                }
                else {
                    ++n9;
                }
                CalendarUtils.sprintf0d(sb, n8 / 60 * 100 + n8 % 60, n9);
                break;
            }
            case 21: {
                final int n10 = this.calendar.get(15) + this.calendar.get(16);
                if (n10 == 0) {
                    sb.append('Z');
                    break;
                }
                int n11 = n10 / 60000;
                if (n11 >= 0) {
                    sb.append('+');
                }
                else {
                    sb.append('-');
                    n11 = -n11;
                }
                CalendarUtils.sprintf0d(sb, n11 / 60, 2);
                if (n2 == 1) {
                    break;
                }
                if (n2 == 3) {
                    sb.append(':');
                }
                CalendarUtils.sprintf0d(sb, n11 % 60, 2);
                break;
            }
            default: {
                if (s == null) {
                    this.zeroPaddingNumber(n5, n2, n3, sb);
                    break;
                }
                break;
            }
        }
        if (s != null) {
            sb.append(s);
        }
        final int n12 = SimpleDateFormat.PATTERN_INDEX_TO_DATE_FORMAT_FIELD[n];
        final Field field = SimpleDateFormat.PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID[n];
        fieldDelegate.formatted(n12, field, field, length, sb.length(), sb);
    }
    
    private void zeroPaddingNumber(int n, final int minimumIntegerDigits, final int maximumIntegerDigits, final StringBuffer sb) {
        try {
            if (this.zeroDigit == '\0') {
                this.zeroDigit = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getZeroDigit();
            }
            if (n >= 0) {
                if (n < 100 && minimumIntegerDigits >= 1 && minimumIntegerDigits <= 2) {
                    if (n < 10) {
                        if (minimumIntegerDigits == 2) {
                            sb.append(this.zeroDigit);
                        }
                        sb.append((char)(this.zeroDigit + n));
                    }
                    else {
                        sb.append((char)(this.zeroDigit + n / 10));
                        sb.append((char)(this.zeroDigit + n % 10));
                    }
                    return;
                }
                if (n >= 1000 && n < 10000) {
                    if (minimumIntegerDigits == 4) {
                        sb.append((char)(this.zeroDigit + n / 1000));
                        n %= 1000;
                        sb.append((char)(this.zeroDigit + n / 100));
                        n %= 100;
                        sb.append((char)(this.zeroDigit + n / 10));
                        sb.append((char)(this.zeroDigit + n % 10));
                        return;
                    }
                    if (minimumIntegerDigits == 2 && maximumIntegerDigits == 2) {
                        this.zeroPaddingNumber(n % 100, 2, 2, sb);
                        return;
                    }
                }
            }
        }
        catch (Exception ex) {}
        this.numberFormat.setMinimumIntegerDigits(minimumIntegerDigits);
        this.numberFormat.setMaximumIntegerDigits(maximumIntegerDigits);
        this.numberFormat.format(n, sb, DontCareFieldPosition.INSTANCE);
    }
    
    @Override
    public Date parse(final String s, final ParsePosition parsePosition) {
        this.checkNegativeNumberExpression();
        final int index;
        int subParse = index = parsePosition.index;
        final int length = s.length();
        final boolean[] array = { false };
        final CalendarBuilder calendarBuilder = new CalendarBuilder();
        int i = 0;
        while (i < this.compiledPattern.length) {
            final char c = (char)(this.compiledPattern[i] >>> 8);
            int n = this.compiledPattern[i++] & '\u00ff';
            if (n == 255) {
                n = (this.compiledPattern[i++] << 16 | this.compiledPattern[i++]);
            }
            switch (c) {
                case 100: {
                    if (subParse >= length || s.charAt(subParse) != (char)n) {
                        parsePosition.index = index;
                        parsePosition.errorIndex = subParse;
                        return null;
                    }
                    ++subParse;
                    continue;
                }
                case 101: {
                    while (n-- > 0) {
                        if (subParse >= length || s.charAt(subParse) != this.compiledPattern[i++]) {
                            parsePosition.index = index;
                            parsePosition.errorIndex = subParse;
                            return null;
                        }
                        ++subParse;
                    }
                    continue;
                }
                default: {
                    boolean b = false;
                    boolean b2 = false;
                    if (i < this.compiledPattern.length) {
                        final char c2 = (char)(this.compiledPattern[i] >>> 8);
                        if (c2 != 'd' && c2 != 'e') {
                            b = true;
                        }
                        if (this.hasFollowingMinusSign && (c2 == 'd' || c2 == 'e')) {
                            char c3;
                            if (c2 == 'd') {
                                c3 = (char)(this.compiledPattern[i] & '\u00ff');
                            }
                            else {
                                c3 = this.compiledPattern[i + 1];
                            }
                            if (c3 == this.minusSign) {
                                b2 = true;
                            }
                        }
                    }
                    subParse = this.subParse(s, subParse, c, n, b, array, parsePosition, b2, calendarBuilder);
                    if (subParse < 0) {
                        parsePosition.index = index;
                        return null;
                    }
                    continue;
                }
            }
        }
        parsePosition.index = subParse;
        Date date;
        try {
            date = calendarBuilder.establish(this.calendar).getTime();
            if (array[0] && date.before(this.defaultCenturyStart)) {
                date = calendarBuilder.addYear(100).establish(this.calendar).getTime();
            }
        }
        catch (IllegalArgumentException ex) {
            parsePosition.errorIndex = subParse;
            parsePosition.index = index;
            return null;
        }
        return date;
    }
    
    private int matchString(final String s, final int n, final int n2, final String[] array, final CalendarBuilder calendarBuilder) {
        int i = 0;
        final int length = array.length;
        if (n2 == 7) {
            i = 1;
        }
        int n3 = 0;
        int n4 = -1;
        while (i < length) {
            final int length2 = array[i].length();
            if (length2 > n3 && s.regionMatches(true, n, array[i], 0, length2)) {
                n4 = i;
                n3 = length2;
            }
            ++i;
        }
        if (n4 >= 0) {
            calendarBuilder.set(n2, n4);
            return n + n3;
        }
        return -n;
    }
    
    private int matchString(final String s, final int n, final int n2, final Map<String, Integer> map, final CalendarBuilder calendarBuilder) {
        if (map != null) {
            if (map instanceof SortedMap) {
                for (final String s2 : map.keySet()) {
                    if (s.regionMatches(true, n, s2, 0, s2.length())) {
                        calendarBuilder.set(n2, map.get(s2));
                        return n + s2.length();
                    }
                }
                return -n;
            }
            String s3 = null;
            for (final String s4 : map.keySet()) {
                final int length = s4.length();
                if ((s3 == null || length > s3.length()) && s.regionMatches(true, n, s4, 0, length)) {
                    s3 = s4;
                }
            }
            if (s3 != null) {
                calendarBuilder.set(n2, map.get(s3));
                return n + s3.length();
            }
        }
        return -n;
    }
    
    private int matchZoneString(final String s, final int n, final String[] array) {
        for (int i = 1; i <= 4; ++i) {
            final String s2 = array[i];
            if (s.regionMatches(true, n, s2, 0, s2.length())) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean matchDSTString(final String s, final int n, final int n2, final int n3, final String[][] array) {
        final String s2 = array[n2][n3 + 2];
        return s.regionMatches(true, n, s2, 0, s2.length());
    }
    
    private int subParseZoneString(final String s, final int n, final CalendarBuilder calendarBuilder) {
        boolean b = false;
        final TimeZone timeZone = this.getTimeZone();
        final int zoneIndex = this.formatData.getZoneIndex(timeZone.getID());
        TimeZone timeZone2 = null;
        final String[][] zoneStringsWrapper = this.formatData.getZoneStringsWrapper();
        String[] array = null;
        int n2 = 0;
        if (zoneIndex != -1) {
            array = zoneStringsWrapper[zoneIndex];
            if ((n2 = this.matchZoneString(s, n, array)) > 0) {
                if (n2 <= 2) {
                    b = array[n2].equalsIgnoreCase(array[n2 + 2]);
                }
                timeZone2 = TimeZone.getTimeZone(array[0]);
            }
        }
        if (timeZone2 == null) {
            final int zoneIndex2 = this.formatData.getZoneIndex(TimeZone.getDefault().getID());
            if (zoneIndex2 != -1) {
                array = zoneStringsWrapper[zoneIndex2];
                if ((n2 = this.matchZoneString(s, n, array)) > 0) {
                    if (n2 <= 2) {
                        b = array[n2].equalsIgnoreCase(array[n2 + 2]);
                    }
                    timeZone2 = TimeZone.getTimeZone(array[0]);
                }
            }
        }
        if (timeZone2 == null) {
            for (int length = zoneStringsWrapper.length, i = 0; i < length; ++i) {
                array = zoneStringsWrapper[i];
                if ((n2 = this.matchZoneString(s, n, array)) > 0) {
                    if (n2 <= 2) {
                        b = array[n2].equalsIgnoreCase(array[n2 + 2]);
                    }
                    timeZone2 = TimeZone.getTimeZone(array[0]);
                    break;
                }
            }
        }
        if (timeZone2 != null) {
            if (!timeZone2.equals(timeZone)) {
                this.setTimeZone(timeZone2);
            }
            final int n3 = (n2 >= 3) ? timeZone2.getDSTSavings() : 0;
            if (!b && (n2 < 3 || n3 != 0)) {
                calendarBuilder.clear(15).set(16, n3);
            }
            return n + array[n2].length();
        }
        return -n;
    }
    
    private int subParseNumericZone(final String s, final int n, final int n2, final int n3, final boolean b, final CalendarBuilder calendarBuilder) {
        int n4 = n;
        try {
            final char char1 = s.charAt(n4++);
            if (this.isDigit(char1)) {
                int n5 = char1 - '0';
                final char char2 = s.charAt(n4++);
                if (this.isDigit(char2)) {
                    n5 = n5 * 10 + (char2 - '0');
                }
                else {
                    if (n3 > 0 || !b) {
                        return 1 - n4;
                    }
                    --n4;
                }
                if (n5 <= 23) {
                    int n6 = 0;
                    if (n3 != 1) {
                        char c = s.charAt(n4++);
                        if (b) {
                            if (c != ':') {
                                return 1 - n4;
                            }
                            c = s.charAt(n4++);
                        }
                        if (!this.isDigit(c)) {
                            return 1 - n4;
                        }
                        final char c2 = (char)(c - '0');
                        final char char3 = s.charAt(n4++);
                        if (!this.isDigit(char3)) {
                            return 1 - n4;
                        }
                        n6 = c2 * '\n' + (char3 - '0');
                        if (n6 > 59) {
                            return 1 - n4;
                        }
                    }
                    calendarBuilder.set(15, (n6 + n5 * 60) * 60000 * n2).set(16, 0);
                    return n4;
                }
            }
        }
        catch (IndexOutOfBoundsException ex) {}
        return 1 - n4;
    }
    
    private boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }
    
    private int subParse(final String s, final int n, int n2, final int n3, final boolean b, final boolean[] array, final ParsePosition parsePosition, final boolean b2, final CalendarBuilder calendarBuilder) {
        int intValue = 0;
        final ParsePosition parsePosition2 = new ParsePosition(0);
        parsePosition2.index = n;
        if (n2 == 19 && !this.calendar.isWeekDateSupported()) {
            n2 = 1;
        }
        final int n4 = SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[n2];
        while (parsePosition2.index < s.length()) {
            final char char1 = s.charAt(parsePosition2.index);
            if (char1 != ' ' && char1 != '\t') {
                final int index = parsePosition2.index;
                Label_1753: {
                    if (n2 == 4 || n2 == 15 || (n2 == 2 && n3 <= 2) || n2 == 1 || n2 == 19) {
                        Number n5;
                        if (b) {
                            if (n + n3 > s.length()) {
                                break Label_1753;
                            }
                            n5 = this.numberFormat.parse(s.substring(0, n + n3), parsePosition2);
                        }
                        else {
                            n5 = this.numberFormat.parse(s, parsePosition2);
                        }
                        if (n5 == null) {
                            if (n2 != 1) {
                                break Label_1753;
                            }
                            if (this.calendar instanceof GregorianCalendar) {
                                break Label_1753;
                            }
                        }
                        else {
                            intValue = n5.intValue();
                            if (b2 && intValue < 0 && ((parsePosition2.index < s.length() && s.charAt(parsePosition2.index) != this.minusSign) || (parsePosition2.index == s.length() && s.charAt(parsePosition2.index - 1) == this.minusSign))) {
                                intValue = -intValue;
                                final ParsePosition parsePosition3 = parsePosition2;
                                --parsePosition3.index;
                            }
                        }
                    }
                    final boolean useDateFormatSymbols = this.useDateFormatSymbols();
                    switch (n2) {
                        case 0: {
                            if (useDateFormatSymbols) {
                                final int matchString;
                                if ((matchString = this.matchString(s, n, 0, this.formatData.getEras(), calendarBuilder)) > 0) {
                                    return matchString;
                                }
                                break;
                            }
                            else {
                                final int matchString2;
                                if ((matchString2 = this.matchString(s, n, n4, this.getDisplayNamesMap(n4, this.locale), calendarBuilder)) > 0) {
                                    return matchString2;
                                }
                                break;
                            }
                            break;
                        }
                        case 1:
                        case 19: {
                            if (this.calendar instanceof GregorianCalendar) {
                                if (n3 <= 2 && parsePosition2.index - index == 2 && Character.isDigit(s.charAt(index)) && Character.isDigit(s.charAt(index + 1))) {
                                    final int n6 = this.defaultCenturyStartYear % 100;
                                    array[0] = (intValue == n6);
                                    intValue += this.defaultCenturyStartYear / 100 * 100 + ((intValue < n6) ? 100 : 0);
                                }
                                calendarBuilder.set(n4, intValue);
                                return parsePosition2.index;
                            }
                            final Map<String, Integer> displayNames = this.calendar.getDisplayNames(n4, (n3 >= 4) ? 2 : 1, this.locale);
                            final int matchString3;
                            if (displayNames != null && (matchString3 = this.matchString(s, n, n4, displayNames, calendarBuilder)) > 0) {
                                return matchString3;
                            }
                            calendarBuilder.set(n4, intValue);
                            return parsePosition2.index;
                        }
                        case 2: {
                            if (n3 <= 2) {
                                calendarBuilder.set(2, intValue - 1);
                                return parsePosition2.index;
                            }
                            if (useDateFormatSymbols) {
                                final int matchString4;
                                if ((matchString4 = this.matchString(s, n, 2, this.formatData.getMonths(), calendarBuilder)) > 0) {
                                    return matchString4;
                                }
                                final int matchString5;
                                if ((matchString5 = this.matchString(s, n, 2, this.formatData.getShortMonths(), calendarBuilder)) > 0) {
                                    return matchString5;
                                }
                                break;
                            }
                            else {
                                final int matchString6;
                                if ((matchString6 = this.matchString(s, n, n4, this.getDisplayNamesMap(n4, this.locale), calendarBuilder)) > 0) {
                                    return matchString6;
                                }
                                break;
                            }
                            break;
                        }
                        case 4: {
                            if (!this.isLenient()) {
                                if (intValue < 1) {
                                    break;
                                }
                                if (intValue > 24) {
                                    break;
                                }
                            }
                            if (intValue == this.calendar.getMaximum(11) + 1) {
                                intValue = 0;
                            }
                            calendarBuilder.set(11, intValue);
                            return parsePosition2.index;
                        }
                        case 9: {
                            if (!useDateFormatSymbols) {
                                final int[] array2 = { 2, 1 };
                                for (int length = array2.length, i = 0; i < length; ++i) {
                                    final int matchString7;
                                    if ((matchString7 = this.matchString(s, n, n4, this.calendar.getDisplayNames(n4, array2[i], this.locale), calendarBuilder)) > 0) {
                                        return matchString7;
                                    }
                                }
                                break;
                            }
                            final int matchString8;
                            if ((matchString8 = this.matchString(s, n, 7, this.formatData.getWeekdays(), calendarBuilder)) > 0) {
                                return matchString8;
                            }
                            final int matchString9;
                            if ((matchString9 = this.matchString(s, n, 7, this.formatData.getShortWeekdays(), calendarBuilder)) > 0) {
                                return matchString9;
                            }
                            break;
                        }
                        case 14: {
                            if (useDateFormatSymbols) {
                                final int matchString10;
                                if ((matchString10 = this.matchString(s, n, 9, this.formatData.getAmPmStrings(), calendarBuilder)) > 0) {
                                    return matchString10;
                                }
                                break;
                            }
                            else {
                                final int matchString11;
                                if ((matchString11 = this.matchString(s, n, n4, this.getDisplayNamesMap(n4, this.locale), calendarBuilder)) > 0) {
                                    return matchString11;
                                }
                                break;
                            }
                            break;
                        }
                        case 15: {
                            if (!this.isLenient()) {
                                if (intValue < 1) {
                                    break;
                                }
                                if (intValue > 12) {
                                    break;
                                }
                            }
                            if (intValue == this.calendar.getLeastMaximum(10) + 1) {
                                intValue = 0;
                            }
                            calendarBuilder.set(10, intValue);
                            return parsePosition2.index;
                        }
                        case 17:
                        case 18: {
                            int n7 = 0;
                            try {
                                final char char2 = s.charAt(parsePosition2.index);
                                if (char2 == '+') {
                                    n7 = 1;
                                }
                                else if (char2 == '-') {
                                    n7 = -1;
                                }
                                if (n7 == 0) {
                                    if ((char2 == 'G' || char2 == 'g') && s.length() - n >= "GMT".length() && s.regionMatches(true, n, "GMT", 0, "GMT".length())) {
                                        parsePosition2.index = n + "GMT".length();
                                        if (s.length() - parsePosition2.index > 0) {
                                            final char char3 = s.charAt(parsePosition2.index);
                                            if (char3 == '+') {
                                                n7 = 1;
                                            }
                                            else if (char3 == '-') {
                                                n7 = -1;
                                            }
                                        }
                                        if (n7 == 0) {
                                            calendarBuilder.set(15, 0).set(16, 0);
                                            return parsePosition2.index;
                                        }
                                        final int subParseNumericZone = this.subParseNumericZone(s, ++parsePosition2.index, n7, 0, true, calendarBuilder);
                                        if (subParseNumericZone > 0) {
                                            return subParseNumericZone;
                                        }
                                        parsePosition2.index = -subParseNumericZone;
                                    }
                                    else {
                                        final int subParseZoneString = this.subParseZoneString(s, parsePosition2.index, calendarBuilder);
                                        if (subParseZoneString > 0) {
                                            return subParseZoneString;
                                        }
                                        parsePosition2.index = -subParseZoneString;
                                    }
                                }
                                else {
                                    final int subParseNumericZone2 = this.subParseNumericZone(s, ++parsePosition2.index, n7, 0, false, calendarBuilder);
                                    if (subParseNumericZone2 > 0) {
                                        return subParseNumericZone2;
                                    }
                                    parsePosition2.index = -subParseNumericZone2;
                                }
                            }
                            catch (IndexOutOfBoundsException ex) {}
                            break;
                        }
                        case 21: {
                            if (s.length() - parsePosition2.index <= 0) {
                                break;
                            }
                            final char char4 = s.charAt(parsePosition2.index);
                            if (char4 == 'Z') {
                                calendarBuilder.set(15, 0).set(16, 0);
                                return ++parsePosition2.index;
                            }
                            int n8;
                            if (char4 == '+') {
                                n8 = 1;
                            }
                            else {
                                if (char4 != '-') {
                                    final ParsePosition parsePosition4 = parsePosition2;
                                    ++parsePosition4.index;
                                    break;
                                }
                                n8 = -1;
                            }
                            final int subParseNumericZone3 = this.subParseNumericZone(s, ++parsePosition2.index, n8, n3, n3 == 3, calendarBuilder);
                            if (subParseNumericZone3 > 0) {
                                return subParseNumericZone3;
                            }
                            parsePosition2.index = -subParseNumericZone3;
                            break;
                        }
                        default: {
                            Number n9;
                            if (b) {
                                if (n + n3 > s.length()) {
                                    break;
                                }
                                n9 = this.numberFormat.parse(s.substring(0, n + n3), parsePosition2);
                            }
                            else {
                                n9 = this.numberFormat.parse(s, parsePosition2);
                            }
                            if (n9 != null) {
                                int intValue2 = n9.intValue();
                                if (b2 && intValue2 < 0 && ((parsePosition2.index < s.length() && s.charAt(parsePosition2.index) != this.minusSign) || (parsePosition2.index == s.length() && s.charAt(parsePosition2.index - 1) == this.minusSign))) {
                                    intValue2 = -intValue2;
                                    final ParsePosition parsePosition5 = parsePosition2;
                                    --parsePosition5.index;
                                }
                                calendarBuilder.set(n4, intValue2);
                                return parsePosition2.index;
                            }
                            break;
                        }
                    }
                }
                parsePosition.errorIndex = parsePosition2.index;
                return -1;
            }
            final ParsePosition parsePosition6 = parsePosition2;
            ++parsePosition6.index;
        }
        parsePosition.errorIndex = n;
        return -1;
    }
    
    private boolean useDateFormatSymbols() {
        return this.useDateFormatSymbols || this.locale == null;
    }
    
    private String translatePattern(final String s, final String s2, final String s3) {
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (n != 0) {
                if (c == '\'') {
                    n = 0;
                }
            }
            else if (c == '\'') {
                n = 1;
            }
            else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                final int index = s2.indexOf(c);
                if (index < 0) {
                    throw new IllegalArgumentException("Illegal pattern  character '" + c + "'");
                }
                if (index < s3.length()) {
                    c = s3.charAt(index);
                }
            }
            sb.append(c);
        }
        if (n != 0) {
            throw new IllegalArgumentException("Unfinished quote in pattern");
        }
        return sb.toString();
    }
    
    public String toPattern() {
        return this.pattern;
    }
    
    public String toLocalizedPattern() {
        return this.translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzZYuXL", this.formatData.getLocalPatternChars());
    }
    
    public void applyPattern(final String s) {
        this.applyPatternImpl(s);
    }
    
    private void applyPatternImpl(final String pattern) {
        this.compiledPattern = this.compile(pattern);
        this.pattern = pattern;
    }
    
    public void applyLocalizedPattern(final String s) {
        final String translatePattern = this.translatePattern(s, this.formatData.getLocalPatternChars(), "GyMdkHmsSEDFwWahKzZYuXL");
        this.compiledPattern = this.compile(translatePattern);
        this.pattern = translatePattern;
    }
    
    public DateFormatSymbols getDateFormatSymbols() {
        return (DateFormatSymbols)this.formatData.clone();
    }
    
    public void setDateFormatSymbols(final DateFormatSymbols dateFormatSymbols) {
        this.formatData = (DateFormatSymbols)dateFormatSymbols.clone();
        this.useDateFormatSymbols = true;
    }
    
    @Override
    public Object clone() {
        final SimpleDateFormat simpleDateFormat = (SimpleDateFormat)super.clone();
        simpleDateFormat.formatData = (DateFormatSymbols)this.formatData.clone();
        return simpleDateFormat;
    }
    
    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return false;
        }
        final SimpleDateFormat simpleDateFormat = (SimpleDateFormat)o;
        return this.pattern.equals(simpleDateFormat.pattern) && this.formatData.equals(simpleDateFormat.formatData);
    }
    
    private Map<String, Integer> getDisplayNamesMap(final int n, final Locale locale) {
        final Map<String, Integer> displayNames = this.calendar.getDisplayNames(n, 1, locale);
        final int[] rest_OF_STYLES = SimpleDateFormat.REST_OF_STYLES;
        for (int length = rest_OF_STYLES.length, i = 0; i < length; ++i) {
            final Map<String, Integer> displayNames2 = this.calendar.getDisplayNames(n, rest_OF_STYLES[i], locale);
            if (displayNames2 != null) {
                displayNames.putAll(displayNames2);
            }
        }
        return displayNames;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        try {
            this.compiledPattern = this.compile(this.pattern);
        }
        catch (Exception ex) {
            throw new InvalidObjectException("invalid pattern");
        }
        if (this.serialVersionOnStream < 1) {
            this.initializeDefaultCentury();
        }
        else {
            this.parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
        }
        this.serialVersionOnStream = 1;
        final TimeZone timeZone = this.getTimeZone();
        if (timeZone instanceof SimpleTimeZone) {
            final String id = timeZone.getID();
            final TimeZone timeZone2 = TimeZone.getTimeZone(id);
            if (timeZone2 != null && timeZone2.hasSameRules(timeZone) && timeZone2.getID().equals(id)) {
                this.setTimeZone(timeZone2);
            }
        }
    }
    
    private void checkNegativeNumberExpression() {
        if (this.numberFormat instanceof DecimalFormat && !this.numberFormat.equals(this.originalNumberFormat)) {
            final String pattern = ((DecimalFormat)this.numberFormat).toPattern();
            if (!pattern.equals(this.originalNumberPattern)) {
                this.hasFollowingMinusSign = false;
                final int index = pattern.indexOf(59);
                if (index > -1) {
                    final int index2 = pattern.indexOf(45, index);
                    if (index2 > pattern.lastIndexOf(48) && index2 > pattern.lastIndexOf(35)) {
                        this.hasFollowingMinusSign = true;
                        this.minusSign = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getMinusSign();
                    }
                }
                this.originalNumberPattern = pattern;
            }
            this.originalNumberFormat = this.numberFormat;
        }
    }
    
    static {
        cachedNumberFormatData = new ConcurrentHashMap<Locale, NumberFormat>(3);
        PATTERN_INDEX_TO_CALENDAR_FIELD = new int[] { 0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 15, 17, 1000, 15, 2 };
        PATTERN_INDEX_TO_DATE_FORMAT_FIELD = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 17, 1, 9, 17, 2 };
        PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID = new Field[] { Field.ERA, Field.YEAR, Field.MONTH, Field.DAY_OF_MONTH, Field.HOUR_OF_DAY1, Field.HOUR_OF_DAY0, Field.MINUTE, Field.SECOND, Field.MILLISECOND, Field.DAY_OF_WEEK, Field.DAY_OF_YEAR, Field.DAY_OF_WEEK_IN_MONTH, Field.WEEK_OF_YEAR, Field.WEEK_OF_MONTH, Field.AM_PM, Field.HOUR1, Field.HOUR0, Field.TIME_ZONE, Field.TIME_ZONE, Field.YEAR, Field.DAY_OF_WEEK, Field.TIME_ZONE, Field.MONTH };
        REST_OF_STYLES = new int[] { 32769, 2, 32770 };
    }
}
