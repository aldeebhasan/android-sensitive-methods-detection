package java.util;

import sun.util.locale.provider.*;
import java.util.spi.*;
import java.util.regex.*;
import java.text.*;
import sun.util.logging.*;
import java.util.concurrent.*;
import java.io.*;
import java.security.*;

public final class Currency implements Serializable
{
    private static final long serialVersionUID = -158308464356906721L;
    private final String currencyCode;
    private final transient int defaultFractionDigits;
    private final transient int numericCode;
    private static ConcurrentMap<String, Currency> instances;
    private static HashSet<Currency> available;
    static int formatVersion;
    static int dataVersion;
    static int[] mainTable;
    static long[] scCutOverTimes;
    static String[] scOldCurrencies;
    static String[] scNewCurrencies;
    static int[] scOldCurrenciesDFD;
    static int[] scNewCurrenciesDFD;
    static int[] scOldCurrenciesNumericCode;
    static int[] scNewCurrenciesNumericCode;
    static String otherCurrencies;
    static int[] otherCurrenciesDFD;
    static int[] otherCurrenciesNumericCode;
    private static final int MAGIC_NUMBER = 1131770436;
    private static final int A_TO_Z = 26;
    private static final int INVALID_COUNTRY_ENTRY = 127;
    private static final int COUNTRY_WITHOUT_CURRENCY_ENTRY = 512;
    private static final int SIMPLE_CASE_COUNTRY_MASK = 0;
    private static final int SIMPLE_CASE_COUNTRY_FINAL_CHAR_MASK = 31;
    private static final int SIMPLE_CASE_COUNTRY_DEFAULT_DIGITS_MASK = 480;
    private static final int SIMPLE_CASE_COUNTRY_DEFAULT_DIGITS_SHIFT = 5;
    private static final int SIMPLE_CASE_COUNTRY_MAX_DEFAULT_DIGITS = 9;
    private static final int SPECIAL_CASE_COUNTRY_MASK = 512;
    private static final int SPECIAL_CASE_COUNTRY_INDEX_MASK = 31;
    private static final int SPECIAL_CASE_COUNTRY_INDEX_DELTA = 1;
    private static final int COUNTRY_TYPE_MASK = 512;
    private static final int NUMERIC_CODE_MASK = 1047552;
    private static final int NUMERIC_CODE_SHIFT = 10;
    private static final int VALID_FORMAT_VERSION = 2;
    private static final int SYMBOL = 0;
    private static final int DISPLAYNAME = 1;
    
    private Currency(final String currencyCode, final int defaultFractionDigits, final int numericCode) {
        this.currencyCode = currencyCode;
        this.defaultFractionDigits = defaultFractionDigits;
        this.numericCode = numericCode;
    }
    
    public static Currency getInstance(final String s) {
        return getInstance(s, Integer.MIN_VALUE, 0);
    }
    
    private static Currency getInstance(final String s, int n, int n2) {
        final Currency currency = Currency.instances.get(s);
        if (currency != null) {
            return currency;
        }
        if (n == Integer.MIN_VALUE) {
            if (s.length() != 3) {
                throw new IllegalArgumentException();
            }
            final int mainTableEntry = getMainTableEntry(s.charAt(0), s.charAt(1));
            if ((mainTableEntry & 0x200) == 0x0 && mainTableEntry != 127 && s.charAt(2) - 'A' == (mainTableEntry & 0x1F)) {
                n = (mainTableEntry & 0x1E0) >> 5;
                n2 = (mainTableEntry & 0xFFC00) >> 10;
            }
            else {
                if (s.charAt(2) == '-') {
                    throw new IllegalArgumentException();
                }
                final int index = Currency.otherCurrencies.indexOf(s);
                if (index == -1) {
                    throw new IllegalArgumentException();
                }
                n = Currency.otherCurrenciesDFD[index / 4];
                n2 = Currency.otherCurrenciesNumericCode[index / 4];
            }
        }
        final Currency currency2 = new Currency(s, n, n2);
        final Currency currency3 = Currency.instances.putIfAbsent(s, currency2);
        return (currency3 != null) ? currency3 : currency2;
    }
    
    public static Currency getInstance(final Locale locale) {
        final String country = locale.getCountry();
        if (country == null) {
            throw new NullPointerException();
        }
        if (country.length() != 2) {
            throw new IllegalArgumentException();
        }
        final int mainTableEntry = getMainTableEntry(country.charAt(0), country.charAt(1));
        if ((mainTableEntry & 0x200) == 0x0 && mainTableEntry != 127) {
            final char c = (char)((mainTableEntry & 0x1F) + 65);
            final int n = (mainTableEntry & 0x1E0) >> 5;
            final int n2 = (mainTableEntry & 0xFFC00) >> 10;
            final StringBuilder sb = new StringBuilder(country);
            sb.append(c);
            return getInstance(sb.toString(), n, n2);
        }
        if (mainTableEntry == 127) {
            throw new IllegalArgumentException();
        }
        if (mainTableEntry == 512) {
            return null;
        }
        final int n3 = (mainTableEntry & 0x1F) - 1;
        if (Currency.scCutOverTimes[n3] == Long.MAX_VALUE || System.currentTimeMillis() < Currency.scCutOverTimes[n3]) {
            return getInstance(Currency.scOldCurrencies[n3], Currency.scOldCurrenciesDFD[n3], Currency.scOldCurrenciesNumericCode[n3]);
        }
        return getInstance(Currency.scNewCurrencies[n3], Currency.scNewCurrenciesDFD[n3], Currency.scNewCurrenciesNumericCode[n3]);
    }
    
    public static Set<Currency> getAvailableCurrencies() {
        synchronized (Currency.class) {
            if (Currency.available == null) {
                Currency.available = new HashSet<Currency>(256);
                for (char c = 'A'; c <= 'Z'; ++c) {
                    for (char c2 = 'A'; c2 <= 'Z'; ++c2) {
                        final int mainTableEntry = getMainTableEntry(c, c2);
                        if ((mainTableEntry & 0x200) == 0x0 && mainTableEntry != 127) {
                            final char c3 = (char)((mainTableEntry & 0x1F) + 65);
                            final int n = (mainTableEntry & 0x1E0) >> 5;
                            final int n2 = (mainTableEntry & 0xFFC00) >> 10;
                            final StringBuilder sb = new StringBuilder();
                            sb.append(c);
                            sb.append(c2);
                            sb.append(c3);
                            Currency.available.add(getInstance(sb.toString(), n, n2));
                        }
                    }
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(Currency.otherCurrencies, "-");
                while (stringTokenizer.hasMoreElements()) {
                    Currency.available.add(getInstance((String)stringTokenizer.nextElement()));
                }
            }
        }
        return (Set<Currency>)Currency.available.clone();
    }
    
    public String getCurrencyCode() {
        return this.currencyCode;
    }
    
    public String getSymbol() {
        return this.getSymbol(Locale.getDefault(Locale.Category.DISPLAY));
    }
    
    public String getSymbol(final Locale locale) {
        final String s = LocaleServiceProviderPool.getPool(CurrencyNameProvider.class).getLocalizedObject((LocaleServiceProviderPool.LocalizedObjectGetter<LocaleServiceProvider, String>)CurrencyNameGetter.INSTANCE, locale, this.currencyCode, 0);
        if (s != null) {
            return s;
        }
        return this.currencyCode;
    }
    
    public int getDefaultFractionDigits() {
        return this.defaultFractionDigits;
    }
    
    public int getNumericCode() {
        return this.numericCode;
    }
    
    public String getDisplayName() {
        return this.getDisplayName(Locale.getDefault(Locale.Category.DISPLAY));
    }
    
    public String getDisplayName(final Locale locale) {
        final String s = LocaleServiceProviderPool.getPool(CurrencyNameProvider.class).getLocalizedObject((LocaleServiceProviderPool.LocalizedObjectGetter<LocaleServiceProvider, String>)CurrencyNameGetter.INSTANCE, locale, this.currencyCode, 1);
        if (s != null) {
            return s;
        }
        return this.currencyCode;
    }
    
    @Override
    public String toString() {
        return this.currencyCode;
    }
    
    private Object readResolve() {
        return getInstance(this.currencyCode);
    }
    
    private static int getMainTableEntry(final char c, final char c2) {
        if (c < 'A' || c > 'Z' || c2 < 'A' || c2 > 'Z') {
            throw new IllegalArgumentException();
        }
        return Currency.mainTable[(c - 'A') * '\u001a' + (c2 - 'A')];
    }
    
    private static void setMainTableEntry(final char c, final char c2, final int n) {
        if (c < 'A' || c > 'Z' || c2 < 'A' || c2 > 'Z') {
            throw new IllegalArgumentException();
        }
        Currency.mainTable[(c - 'A') * '\u001a' + (c2 - 'A')] = n;
    }
    
    private static int[] readIntArray(final DataInputStream dataInputStream, final int n) throws IOException {
        final int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = dataInputStream.readInt();
        }
        return array;
    }
    
    private static long[] readLongArray(final DataInputStream dataInputStream, final int n) throws IOException {
        final long[] array = new long[n];
        for (int i = 0; i < n; ++i) {
            array[i] = dataInputStream.readLong();
        }
        return array;
    }
    
    private static String[] readStringArray(final DataInputStream dataInputStream, final int n) throws IOException {
        final String[] array = new String[n];
        for (int i = 0; i < n; ++i) {
            array[i] = dataInputStream.readUTF();
        }
        return array;
    }
    
    private static void replaceCurrencyData(final Pattern pattern, final String s, final String s2) {
        if (s.length() != 2) {
            info("currency.properties entry for " + s + " is ignored because of the invalid country code.", null);
            return;
        }
        final Matcher matcher = pattern.matcher(s2);
        if (!matcher.find() || (matcher.group(4) == null && countOccurrences(s2, ',') >= 3)) {
            info("currency.properties entry for " + s + " ignored because the value format is not recognized.", null);
            return;
        }
        try {
            if (matcher.group(4) != null && !isPastCutoverDate(matcher.group(4))) {
                info("currency.properties entry for " + s + " ignored since cutover date has not passed :" + s2, null);
                return;
            }
        }
        catch (ParseException ex) {
            info("currency.properties entry for " + s + " ignored since exception encountered :" + ex.getMessage(), null);
            return;
        }
        final String group = matcher.group(1);
        final int n = Integer.parseInt(matcher.group(2)) << 10;
        final int int1 = Integer.parseInt(matcher.group(3));
        if (int1 > 9) {
            info("currency.properties entry for " + s + " ignored since the fraction is more than " + 9 + ":" + s2, null);
            return;
        }
        int n2;
        for (n2 = 0; n2 < Currency.scOldCurrencies.length && !Currency.scOldCurrencies[n2].equals(group); ++n2) {}
        int n3;
        if (n2 == Currency.scOldCurrencies.length) {
            n3 = (n | (int1 << 5 | group.charAt(2) - 'A'));
        }
        else {
            n3 = (n | (0x200 | n2 + 1));
        }
        setMainTableEntry(s.charAt(0), s.charAt(1), n3);
    }
    
    private static boolean isPastCutoverDate(final String s) throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        simpleDateFormat.setLenient(false);
        return System.currentTimeMillis() > simpleDateFormat.parse(s.trim()).getTime();
    }
    
    private static int countOccurrences(final String s, final char c) {
        int n = 0;
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            if (charArray[i] == c) {
                ++n;
            }
        }
        return n;
    }
    
    private static void info(final String s, final Throwable t) {
        final PlatformLogger logger = PlatformLogger.getLogger("java.util.Currency");
        if (logger.isLoggable(PlatformLogger.Level.INFO)) {
            if (t != null) {
                logger.info(s, t);
            }
            else {
                logger.info(s);
            }
        }
    }
    
    static {
        Currency.instances = new ConcurrentHashMap<String, Currency>(7);
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                final String property = System.getProperty("java.home");
                try (final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(property + File.separator + "lib" + File.separator + "currency.data")))) {
                    if (dataInputStream.readInt() != 1131770436) {
                        throw new InternalError("Currency data is possibly corrupted");
                    }
                    Currency.formatVersion = dataInputStream.readInt();
                    if (Currency.formatVersion != 2) {
                        throw new InternalError("Currency data format is incorrect");
                    }
                    Currency.dataVersion = dataInputStream.readInt();
                    Currency.mainTable = readIntArray(dataInputStream, 676);
                    final int int1 = dataInputStream.readInt();
                    Currency.scCutOverTimes = readLongArray(dataInputStream, int1);
                    Currency.scOldCurrencies = readStringArray(dataInputStream, int1);
                    Currency.scNewCurrencies = readStringArray(dataInputStream, int1);
                    Currency.scOldCurrenciesDFD = readIntArray(dataInputStream, int1);
                    Currency.scNewCurrenciesDFD = readIntArray(dataInputStream, int1);
                    Currency.scOldCurrenciesNumericCode = readIntArray(dataInputStream, int1);
                    Currency.scNewCurrenciesNumericCode = readIntArray(dataInputStream, int1);
                    final int int2 = dataInputStream.readInt();
                    Currency.otherCurrencies = dataInputStream.readUTF();
                    Currency.otherCurrenciesDFD = readIntArray(dataInputStream, int2);
                    Currency.otherCurrenciesNumericCode = readIntArray(dataInputStream, int2);
                }
                catch (IOException ex) {
                    throw new InternalError(ex);
                }
                String s = System.getProperty("java.util.currency.data");
                if (s == null) {
                    s = property + File.separator + "lib" + File.separator + "currency.properties";
                }
                try {
                    final File file = new File(s);
                    if (file.exists()) {
                        final Properties properties = new Properties();
                        try (final FileReader fileReader = new FileReader(file)) {
                            properties.load(fileReader);
                        }
                        final Set<String> stringPropertyNames = properties.stringPropertyNames();
                        final Pattern compile = Pattern.compile("([A-Z]{3})\\s*,\\s*(\\d{3})\\s*,\\s*(\\d+)\\s*,?\\s*(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})?");
                        for (final String s2 : stringPropertyNames) {
                            replaceCurrencyData(compile, s2.toUpperCase(Locale.ROOT), properties.getProperty(s2).toUpperCase(Locale.ROOT));
                        }
                    }
                }
                catch (IOException ex2) {
                    info("currency.properties is ignored because of an IOException", ex2);
                }
                return null;
            }
        });
    }
    
    private static class CurrencyNameGetter implements LocaleServiceProviderPool.LocalizedObjectGetter<CurrencyNameProvider, String>
    {
        private static final CurrencyNameGetter INSTANCE;
        
        @Override
        public String getObject(final CurrencyNameProvider currencyNameProvider, final Locale locale, final String s, final Object... array) {
            assert array.length == 1;
            switch ((int)array[0]) {
                case 0: {
                    return currencyNameProvider.getSymbol(s, locale);
                }
                case 1: {
                    return currencyNameProvider.getDisplayName(s, locale);
                }
                default: {
                    assert false;
                    return null;
                }
            }
        }
        
        static {
            INSTANCE = new CurrencyNameGetter();
        }
    }
}
