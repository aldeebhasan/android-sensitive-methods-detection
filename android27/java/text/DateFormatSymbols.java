package java.text;

import java.lang.ref.*;
import java.text.spi.*;
import java.util.spi.*;
import java.util.*;
import sun.util.locale.provider.*;
import java.io.*;
import java.util.concurrent.*;

public class DateFormatSymbols implements Serializable, Cloneable
{
    String[] eras;
    String[] months;
    String[] shortMonths;
    String[] weekdays;
    String[] shortWeekdays;
    String[] ampms;
    String[][] zoneStrings;
    transient boolean isZoneStringsSet;
    static final String patternChars = "GyMdkHmsSEDFwWahKzZYuXL";
    static final int PATTERN_ERA = 0;
    static final int PATTERN_YEAR = 1;
    static final int PATTERN_MONTH = 2;
    static final int PATTERN_DAY_OF_MONTH = 3;
    static final int PATTERN_HOUR_OF_DAY1 = 4;
    static final int PATTERN_HOUR_OF_DAY0 = 5;
    static final int PATTERN_MINUTE = 6;
    static final int PATTERN_SECOND = 7;
    static final int PATTERN_MILLISECOND = 8;
    static final int PATTERN_DAY_OF_WEEK = 9;
    static final int PATTERN_DAY_OF_YEAR = 10;
    static final int PATTERN_DAY_OF_WEEK_IN_MONTH = 11;
    static final int PATTERN_WEEK_OF_YEAR = 12;
    static final int PATTERN_WEEK_OF_MONTH = 13;
    static final int PATTERN_AM_PM = 14;
    static final int PATTERN_HOUR1 = 15;
    static final int PATTERN_HOUR0 = 16;
    static final int PATTERN_ZONE_NAME = 17;
    static final int PATTERN_ZONE_VALUE = 18;
    static final int PATTERN_WEEK_YEAR = 19;
    static final int PATTERN_ISO_DAY_OF_WEEK = 20;
    static final int PATTERN_ISO_ZONE = 21;
    static final int PATTERN_MONTH_STANDALONE = 22;
    String localPatternChars;
    Locale locale;
    static final long serialVersionUID = -5987973545549424702L;
    static final int millisPerHour = 3600000;
    private static final ConcurrentMap<Locale, SoftReference<DateFormatSymbols>> cachedInstances;
    private transient int lastZoneIndex;
    transient volatile int cachedHashCode;
    
    public DateFormatSymbols() {
        this.eras = null;
        this.months = null;
        this.shortMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.ampms = null;
        this.zoneStrings = null;
        this.isZoneStringsSet = false;
        this.localPatternChars = null;
        this.locale = null;
        this.lastZoneIndex = 0;
        this.cachedHashCode = 0;
        this.initializeData(Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public DateFormatSymbols(final Locale locale) {
        this.eras = null;
        this.months = null;
        this.shortMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.ampms = null;
        this.zoneStrings = null;
        this.isZoneStringsSet = false;
        this.localPatternChars = null;
        this.locale = null;
        this.lastZoneIndex = 0;
        this.cachedHashCode = 0;
        this.initializeData(locale);
    }
    
    private DateFormatSymbols(final boolean b) {
        this.eras = null;
        this.months = null;
        this.shortMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.ampms = null;
        this.zoneStrings = null;
        this.isZoneStringsSet = false;
        this.localPatternChars = null;
        this.locale = null;
        this.lastZoneIndex = 0;
        this.cachedHashCode = 0;
    }
    
    public static Locale[] getAvailableLocales() {
        return LocaleServiceProviderPool.getPool(DateFormatSymbolsProvider.class).getAvailableLocales();
    }
    
    public static final DateFormatSymbols getInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static final DateFormatSymbols getInstance(final Locale locale) {
        final DateFormatSymbols providerInstance = getProviderInstance(locale);
        if (providerInstance != null) {
            return providerInstance;
        }
        throw new RuntimeException("DateFormatSymbols instance creation failed.");
    }
    
    static final DateFormatSymbols getInstanceRef(final Locale locale) {
        final DateFormatSymbols providerInstance = getProviderInstance(locale);
        if (providerInstance != null) {
            return providerInstance;
        }
        throw new RuntimeException("DateFormatSymbols instance creation failed.");
    }
    
    private static DateFormatSymbols getProviderInstance(final Locale locale) {
        DateFormatSymbols dateFormatSymbols = LocaleProviderAdapter.getAdapter(DateFormatSymbolsProvider.class, locale).getDateFormatSymbolsProvider().getInstance(locale);
        if (dateFormatSymbols == null) {
            dateFormatSymbols = LocaleProviderAdapter.forJRE().getDateFormatSymbolsProvider().getInstance(locale);
        }
        return dateFormatSymbols;
    }
    
    public String[] getEras() {
        return Arrays.copyOf(this.eras, this.eras.length);
    }
    
    public void setEras(final String[] array) {
        this.eras = Arrays.copyOf(array, array.length);
        this.cachedHashCode = 0;
    }
    
    public String[] getMonths() {
        return Arrays.copyOf(this.months, this.months.length);
    }
    
    public void setMonths(final String[] array) {
        this.months = Arrays.copyOf(array, array.length);
        this.cachedHashCode = 0;
    }
    
    public String[] getShortMonths() {
        return Arrays.copyOf(this.shortMonths, this.shortMonths.length);
    }
    
    public void setShortMonths(final String[] array) {
        this.shortMonths = Arrays.copyOf(array, array.length);
        this.cachedHashCode = 0;
    }
    
    public String[] getWeekdays() {
        return Arrays.copyOf(this.weekdays, this.weekdays.length);
    }
    
    public void setWeekdays(final String[] array) {
        this.weekdays = Arrays.copyOf(array, array.length);
        this.cachedHashCode = 0;
    }
    
    public String[] getShortWeekdays() {
        return Arrays.copyOf(this.shortWeekdays, this.shortWeekdays.length);
    }
    
    public void setShortWeekdays(final String[] array) {
        this.shortWeekdays = Arrays.copyOf(array, array.length);
        this.cachedHashCode = 0;
    }
    
    public String[] getAmPmStrings() {
        return Arrays.copyOf(this.ampms, this.ampms.length);
    }
    
    public void setAmPmStrings(final String[] array) {
        this.ampms = Arrays.copyOf(array, array.length);
        this.cachedHashCode = 0;
    }
    
    public String[][] getZoneStrings() {
        return this.getZoneStringsImpl(true);
    }
    
    public void setZoneStrings(final String[][] array) {
        final String[][] zoneStrings = new String[array.length][];
        for (int i = 0; i < array.length; ++i) {
            final int length = array[i].length;
            if (length < 5) {
                throw new IllegalArgumentException();
            }
            zoneStrings[i] = Arrays.copyOf(array[i], length);
        }
        this.zoneStrings = zoneStrings;
        this.isZoneStringsSet = true;
        this.cachedHashCode = 0;
    }
    
    public String getLocalPatternChars() {
        return this.localPatternChars;
    }
    
    public void setLocalPatternChars(final String s) {
        this.localPatternChars = s.toString();
        this.cachedHashCode = 0;
    }
    
    public Object clone() {
        try {
            final DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)super.clone();
            this.copyMembers(this, dateFormatSymbols);
            return dateFormatSymbols;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    @Override
    public int hashCode() {
        int cachedHashCode = this.cachedHashCode;
        if (cachedHashCode == 0) {
            cachedHashCode = 11 * (11 * (11 * (11 * (11 * (11 * (11 * (11 * 5 + Arrays.hashCode(this.eras)) + Arrays.hashCode(this.months)) + Arrays.hashCode(this.shortMonths)) + Arrays.hashCode(this.weekdays)) + Arrays.hashCode(this.shortWeekdays)) + Arrays.hashCode(this.ampms)) + Arrays.deepHashCode(this.getZoneStringsWrapper())) + Objects.hashCode(this.localPatternChars);
            this.cachedHashCode = cachedHashCode;
        }
        return cachedHashCode;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)o;
        return Arrays.equals(this.eras, dateFormatSymbols.eras) && Arrays.equals(this.months, dateFormatSymbols.months) && Arrays.equals(this.shortMonths, dateFormatSymbols.shortMonths) && Arrays.equals(this.weekdays, dateFormatSymbols.weekdays) && Arrays.equals(this.shortWeekdays, dateFormatSymbols.shortWeekdays) && Arrays.equals(this.ampms, dateFormatSymbols.ampms) && Arrays.deepEquals(this.getZoneStringsWrapper(), dateFormatSymbols.getZoneStringsWrapper()) && ((this.localPatternChars != null && this.localPatternChars.equals(dateFormatSymbols.localPatternChars)) || (this.localPatternChars == null && dateFormatSymbols.localPatternChars == null));
    }
    
    private void initializeData(final Locale locale) {
        final SoftReference<DateFormatSymbols> softReference = DateFormatSymbols.cachedInstances.get(locale);
        DateFormatSymbols dateFormatSymbols;
        if (softReference == null || (dateFormatSymbols = softReference.get()) == null) {
            if (softReference != null) {
                DateFormatSymbols.cachedInstances.remove(locale, softReference);
            }
            dateFormatSymbols = new DateFormatSymbols(false);
            LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter(DateFormatSymbolsProvider.class, locale);
            if (!(localeProviderAdapter instanceof ResourceBundleBasedAdapter)) {
                localeProviderAdapter = LocaleProviderAdapter.getResourceBundleBased();
            }
            final ResourceBundle dateFormatData = ((ResourceBundleBasedAdapter)localeProviderAdapter).getLocaleData().getDateFormatData(locale);
            dateFormatSymbols.locale = locale;
            if (dateFormatData.containsKey("Eras")) {
                dateFormatSymbols.eras = dateFormatData.getStringArray("Eras");
            }
            else if (dateFormatData.containsKey("long.Eras")) {
                dateFormatSymbols.eras = dateFormatData.getStringArray("long.Eras");
            }
            else if (dateFormatData.containsKey("short.Eras")) {
                dateFormatSymbols.eras = dateFormatData.getStringArray("short.Eras");
            }
            dateFormatSymbols.months = dateFormatData.getStringArray("MonthNames");
            dateFormatSymbols.shortMonths = dateFormatData.getStringArray("MonthAbbreviations");
            dateFormatSymbols.ampms = dateFormatData.getStringArray("AmPmMarkers");
            dateFormatSymbols.localPatternChars = dateFormatData.getString("DateTimePatternChars");
            dateFormatSymbols.weekdays = toOneBasedArray(dateFormatData.getStringArray("DayNames"));
            dateFormatSymbols.shortWeekdays = toOneBasedArray(dateFormatData.getStringArray("DayAbbreviations"));
            SoftReference<DateFormatSymbols> softReference2 = new SoftReference<DateFormatSymbols>(dateFormatSymbols);
            final SoftReference<DateFormatSymbols> softReference3 = DateFormatSymbols.cachedInstances.putIfAbsent(locale, softReference2);
            if (softReference3 != null) {
                final DateFormatSymbols dateFormatSymbols2 = softReference3.get();
                if (dateFormatSymbols2 == null) {
                    DateFormatSymbols.cachedInstances.replace(locale, softReference3, softReference2);
                }
                else {
                    softReference2 = softReference3;
                    dateFormatSymbols = dateFormatSymbols2;
                }
            }
            final Locale locale2 = dateFormatData.getLocale();
            if (!locale2.equals(locale)) {
                final SoftReference<DateFormatSymbols> softReference4 = DateFormatSymbols.cachedInstances.putIfAbsent(locale2, softReference2);
                if (softReference4 != null && softReference4.get() == null) {
                    DateFormatSymbols.cachedInstances.replace(locale2, softReference4, softReference2);
                }
            }
        }
        this.copyMembers(dateFormatSymbols, this);
    }
    
    private static String[] toOneBasedArray(final String[] array) {
        final int length = array.length;
        final String[] array2 = new String[length + 1];
        array2[0] = "";
        for (int i = 0; i < length; ++i) {
            array2[i + 1] = array[i];
        }
        return array2;
    }
    
    final int getZoneIndex(final String s) {
        final String[][] zoneStringsWrapper = this.getZoneStringsWrapper();
        if (this.lastZoneIndex < zoneStringsWrapper.length && s.equals(zoneStringsWrapper[this.lastZoneIndex][0])) {
            return this.lastZoneIndex;
        }
        for (int i = 0; i < zoneStringsWrapper.length; ++i) {
            if (s.equals(zoneStringsWrapper[i][0])) {
                return this.lastZoneIndex = i;
            }
        }
        return -1;
    }
    
    final String[][] getZoneStringsWrapper() {
        if (this.isSubclassObject()) {
            return this.getZoneStrings();
        }
        return this.getZoneStringsImpl(false);
    }
    
    private String[][] getZoneStringsImpl(final boolean b) {
        if (this.zoneStrings == null) {
            this.zoneStrings = TimeZoneNameUtility.getZoneStrings(this.locale);
        }
        if (!b) {
            return this.zoneStrings;
        }
        final int length = this.zoneStrings.length;
        final String[][] array = new String[length][];
        for (int i = 0; i < length; ++i) {
            array[i] = Arrays.copyOf(this.zoneStrings[i], this.zoneStrings[i].length);
        }
        return array;
    }
    
    private boolean isSubclassObject() {
        return !this.getClass().getName().equals("java.text.DateFormatSymbols");
    }
    
    private void copyMembers(final DateFormatSymbols dateFormatSymbols, final DateFormatSymbols dateFormatSymbols2) {
        dateFormatSymbols2.locale = dateFormatSymbols.locale;
        dateFormatSymbols2.eras = Arrays.copyOf(dateFormatSymbols.eras, dateFormatSymbols.eras.length);
        dateFormatSymbols2.months = Arrays.copyOf(dateFormatSymbols.months, dateFormatSymbols.months.length);
        dateFormatSymbols2.shortMonths = Arrays.copyOf(dateFormatSymbols.shortMonths, dateFormatSymbols.shortMonths.length);
        dateFormatSymbols2.weekdays = Arrays.copyOf(dateFormatSymbols.weekdays, dateFormatSymbols.weekdays.length);
        dateFormatSymbols2.shortWeekdays = Arrays.copyOf(dateFormatSymbols.shortWeekdays, dateFormatSymbols.shortWeekdays.length);
        dateFormatSymbols2.ampms = Arrays.copyOf(dateFormatSymbols.ampms, dateFormatSymbols.ampms.length);
        if (dateFormatSymbols.zoneStrings != null) {
            dateFormatSymbols2.zoneStrings = dateFormatSymbols.getZoneStringsImpl(true);
        }
        else {
            dateFormatSymbols2.zoneStrings = null;
        }
        dateFormatSymbols2.localPatternChars = dateFormatSymbols.localPatternChars;
        dateFormatSymbols2.cachedHashCode = 0;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.zoneStrings == null) {
            this.zoneStrings = TimeZoneNameUtility.getZoneStrings(this.locale);
        }
        objectOutputStream.defaultWriteObject();
    }
    
    static {
        cachedInstances = new ConcurrentHashMap<Locale, SoftReference<DateFormatSymbols>>(3);
    }
}
