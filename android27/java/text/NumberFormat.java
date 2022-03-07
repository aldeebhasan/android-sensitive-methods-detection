package java.text;

import java.util.concurrent.atomic.*;
import java.text.spi.*;
import java.util.spi.*;
import java.math.*;
import sun.util.locale.provider.*;
import java.io.*;
import java.util.*;

public abstract class NumberFormat extends Format
{
    public static final int INTEGER_FIELD = 0;
    public static final int FRACTION_FIELD = 1;
    private static final int NUMBERSTYLE = 0;
    private static final int CURRENCYSTYLE = 1;
    private static final int PERCENTSTYLE = 2;
    private static final int SCIENTIFICSTYLE = 3;
    private static final int INTEGERSTYLE = 4;
    private boolean groupingUsed;
    private byte maxIntegerDigits;
    private byte minIntegerDigits;
    private byte maxFractionDigits;
    private byte minFractionDigits;
    private boolean parseIntegerOnly;
    private int maximumIntegerDigits;
    private int minimumIntegerDigits;
    private int maximumFractionDigits;
    private int minimumFractionDigits;
    static final int currentSerialVersion = 1;
    private int serialVersionOnStream;
    static final long serialVersionUID = -2308460125733713944L;
    
    protected NumberFormat() {
        this.groupingUsed = true;
        this.maxIntegerDigits = 40;
        this.minIntegerDigits = 1;
        this.maxFractionDigits = 3;
        this.minFractionDigits = 0;
        this.parseIntegerOnly = false;
        this.maximumIntegerDigits = 40;
        this.minimumIntegerDigits = 1;
        this.maximumFractionDigits = 3;
        this.minimumFractionDigits = 0;
        this.serialVersionOnStream = 1;
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Long || o instanceof Integer || o instanceof Short || o instanceof Byte || o instanceof AtomicInteger || o instanceof AtomicLong || (o instanceof BigInteger && ((BigInteger)o).bitLength() < 64)) {
            return this.format(((Number)o).longValue(), sb, fieldPosition);
        }
        if (o instanceof Number) {
            return this.format(((Number)o).doubleValue(), sb, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Number");
    }
    
    @Override
    public final Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parse(s, parsePosition);
    }
    
    public final String format(final double n) {
        final String fastFormat = this.fastFormat(n);
        if (fastFormat != null) {
            return fastFormat;
        }
        return this.format(n, new StringBuffer(), DontCareFieldPosition.INSTANCE).toString();
    }
    
    String fastFormat(final double n) {
        return null;
    }
    
    public final String format(final long n) {
        return this.format(n, new StringBuffer(), DontCareFieldPosition.INSTANCE).toString();
    }
    
    public abstract StringBuffer format(final double p0, final StringBuffer p1, final FieldPosition p2);
    
    public abstract StringBuffer format(final long p0, final StringBuffer p1, final FieldPosition p2);
    
    public abstract Number parse(final String p0, final ParsePosition p1);
    
    public Number parse(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final Number parse = this.parse(s, parsePosition);
        if (parsePosition.index == 0) {
            throw new ParseException("Unparseable number: \"" + s + "\"", parsePosition.errorIndex);
        }
        return parse;
    }
    
    public boolean isParseIntegerOnly() {
        return this.parseIntegerOnly;
    }
    
    public void setParseIntegerOnly(final boolean parseIntegerOnly) {
        this.parseIntegerOnly = parseIntegerOnly;
    }
    
    public static final NumberFormat getInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), 0);
    }
    
    public static NumberFormat getInstance(final Locale locale) {
        return getInstance(locale, 0);
    }
    
    public static final NumberFormat getNumberInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), 0);
    }
    
    public static NumberFormat getNumberInstance(final Locale locale) {
        return getInstance(locale, 0);
    }
    
    public static final NumberFormat getIntegerInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), 4);
    }
    
    public static NumberFormat getIntegerInstance(final Locale locale) {
        return getInstance(locale, 4);
    }
    
    public static final NumberFormat getCurrencyInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), 1);
    }
    
    public static NumberFormat getCurrencyInstance(final Locale locale) {
        return getInstance(locale, 1);
    }
    
    public static final NumberFormat getPercentInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), 2);
    }
    
    public static NumberFormat getPercentInstance(final Locale locale) {
        return getInstance(locale, 2);
    }
    
    static final NumberFormat getScientificInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), 3);
    }
    
    static NumberFormat getScientificInstance(final Locale locale) {
        return getInstance(locale, 3);
    }
    
    public static Locale[] getAvailableLocales() {
        return LocaleServiceProviderPool.getPool(NumberFormatProvider.class).getAvailableLocales();
    }
    
    @Override
    public int hashCode() {
        return this.maximumIntegerDigits * 37 + this.maxFractionDigits;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final NumberFormat numberFormat = (NumberFormat)o;
        return this.maximumIntegerDigits == numberFormat.maximumIntegerDigits && this.minimumIntegerDigits == numberFormat.minimumIntegerDigits && this.maximumFractionDigits == numberFormat.maximumFractionDigits && this.minimumFractionDigits == numberFormat.minimumFractionDigits && this.groupingUsed == numberFormat.groupingUsed && this.parseIntegerOnly == numberFormat.parseIntegerOnly;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    public boolean isGroupingUsed() {
        return this.groupingUsed;
    }
    
    public void setGroupingUsed(final boolean groupingUsed) {
        this.groupingUsed = groupingUsed;
    }
    
    public int getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }
    
    public void setMaximumIntegerDigits(final int n) {
        this.maximumIntegerDigits = Math.max(0, n);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.minimumIntegerDigits = this.maximumIntegerDigits;
        }
    }
    
    public int getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }
    
    public void setMinimumIntegerDigits(final int n) {
        this.minimumIntegerDigits = Math.max(0, n);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.maximumIntegerDigits = this.minimumIntegerDigits;
        }
    }
    
    public int getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }
    
    public void setMaximumFractionDigits(final int n) {
        this.maximumFractionDigits = Math.max(0, n);
        if (this.maximumFractionDigits < this.minimumFractionDigits) {
            this.minimumFractionDigits = this.maximumFractionDigits;
        }
    }
    
    public int getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }
    
    public void setMinimumFractionDigits(final int n) {
        this.minimumFractionDigits = Math.max(0, n);
        if (this.maximumFractionDigits < this.minimumFractionDigits) {
            this.maximumFractionDigits = this.minimumFractionDigits;
        }
    }
    
    public Currency getCurrency() {
        throw new UnsupportedOperationException();
    }
    
    public void setCurrency(final Currency currency) {
        throw new UnsupportedOperationException();
    }
    
    public RoundingMode getRoundingMode() {
        throw new UnsupportedOperationException();
    }
    
    public void setRoundingMode(final RoundingMode roundingMode) {
        throw new UnsupportedOperationException();
    }
    
    private static NumberFormat getInstance(final Locale locale, final int n) {
        NumberFormat numberFormat = getInstance(LocaleProviderAdapter.getAdapter(NumberFormatProvider.class, locale), locale, n);
        if (numberFormat == null) {
            numberFormat = getInstance(LocaleProviderAdapter.forJRE(), locale, n);
        }
        return numberFormat;
    }
    
    private static NumberFormat getInstance(final LocaleProviderAdapter localeProviderAdapter, final Locale locale, final int n) {
        final NumberFormatProvider numberFormatProvider = localeProviderAdapter.getNumberFormatProvider();
        NumberFormat numberFormat = null;
        switch (n) {
            case 0: {
                numberFormat = numberFormatProvider.getNumberInstance(locale);
                break;
            }
            case 2: {
                numberFormat = numberFormatProvider.getPercentInstance(locale);
                break;
            }
            case 1: {
                numberFormat = numberFormatProvider.getCurrencyInstance(locale);
                break;
            }
            case 4: {
                numberFormat = numberFormatProvider.getIntegerInstance(locale);
                break;
            }
        }
        return numberFormat;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.maximumIntegerDigits = this.maxIntegerDigits;
            this.minimumIntegerDigits = this.minIntegerDigits;
            this.maximumFractionDigits = this.maxFractionDigits;
            this.minimumFractionDigits = this.minFractionDigits;
        }
        if (this.minimumIntegerDigits > this.maximumIntegerDigits || this.minimumFractionDigits > this.maximumFractionDigits || this.minimumIntegerDigits < 0 || this.minimumFractionDigits < 0) {
            throw new InvalidObjectException("Digit count range invalid");
        }
        this.serialVersionOnStream = 1;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.maxIntegerDigits = (byte)((this.maximumIntegerDigits > 127) ? 127 : ((byte)this.maximumIntegerDigits));
        this.minIntegerDigits = (byte)((this.minimumIntegerDigits > 127) ? 127 : ((byte)this.minimumIntegerDigits));
        this.maxFractionDigits = (byte)((this.maximumFractionDigits > 127) ? 127 : ((byte)this.maximumFractionDigits));
        this.minFractionDigits = (byte)((this.minimumFractionDigits > 127) ? 127 : ((byte)this.minimumFractionDigits));
        objectOutputStream.defaultWriteObject();
    }
    
    public static class Field extends Format.Field
    {
        private static final long serialVersionUID = 7494728892700160890L;
        private static final Map<String, Field> instanceMap;
        public static final Field INTEGER;
        public static final Field FRACTION;
        public static final Field EXPONENT;
        public static final Field DECIMAL_SEPARATOR;
        public static final Field SIGN;
        public static final Field GROUPING_SEPARATOR;
        public static final Field EXPONENT_SYMBOL;
        public static final Field PERCENT;
        public static final Field PERMILLE;
        public static final Field CURRENCY;
        public static final Field EXPONENT_SIGN;
        
        protected Field(final String s) {
            super(s);
            if (this.getClass() == Field.class) {
                Field.instanceMap.put(s, this);
            }
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
            instanceMap = new HashMap<String, Field>(11);
            INTEGER = new Field("integer");
            FRACTION = new Field("fraction");
            EXPONENT = new Field("exponent");
            DECIMAL_SEPARATOR = new Field("decimal separator");
            SIGN = new Field("sign");
            GROUPING_SEPARATOR = new Field("grouping separator");
            EXPONENT_SYMBOL = new Field("exponent symbol");
            PERCENT = new Field("percent");
            PERMILLE = new Field("per mille");
            CURRENCY = new Field("currency");
            EXPONENT_SIGN = new Field("exponent sign");
        }
    }
}
