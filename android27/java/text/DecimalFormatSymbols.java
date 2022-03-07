package java.text;

import java.util.*;
import java.text.spi.*;
import java.util.spi.*;
import sun.util.locale.provider.*;
import java.io.*;

public class DecimalFormatSymbols implements Cloneable, Serializable
{
    private char zeroDigit;
    private char groupingSeparator;
    private char decimalSeparator;
    private char perMill;
    private char percent;
    private char digit;
    private char patternSeparator;
    private String infinity;
    private String NaN;
    private char minusSign;
    private String currencySymbol;
    private String intlCurrencySymbol;
    private char monetarySeparator;
    private char exponential;
    private String exponentialSeparator;
    private Locale locale;
    private transient Currency currency;
    static final long serialVersionUID = 5772796243397350300L;
    private static final int currentSerialVersion = 3;
    private int serialVersionOnStream;
    
    public DecimalFormatSymbols() {
        this.serialVersionOnStream = 3;
        this.initialize(Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public DecimalFormatSymbols(final Locale locale) {
        this.serialVersionOnStream = 3;
        this.initialize(locale);
    }
    
    public static Locale[] getAvailableLocales() {
        return LocaleServiceProviderPool.getPool(DecimalFormatSymbolsProvider.class).getAvailableLocales();
    }
    
    public static final DecimalFormatSymbols getInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public static final DecimalFormatSymbols getInstance(final Locale locale) {
        DecimalFormatSymbols decimalFormatSymbols = LocaleProviderAdapter.getAdapter(DecimalFormatSymbolsProvider.class, locale).getDecimalFormatSymbolsProvider().getInstance(locale);
        if (decimalFormatSymbols == null) {
            decimalFormatSymbols = LocaleProviderAdapter.forJRE().getDecimalFormatSymbolsProvider().getInstance(locale);
        }
        return decimalFormatSymbols;
    }
    
    public char getZeroDigit() {
        return this.zeroDigit;
    }
    
    public void setZeroDigit(final char zeroDigit) {
        this.zeroDigit = zeroDigit;
    }
    
    public char getGroupingSeparator() {
        return this.groupingSeparator;
    }
    
    public void setGroupingSeparator(final char groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }
    
    public char getDecimalSeparator() {
        return this.decimalSeparator;
    }
    
    public void setDecimalSeparator(final char decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }
    
    public char getPerMill() {
        return this.perMill;
    }
    
    public void setPerMill(final char perMill) {
        this.perMill = perMill;
    }
    
    public char getPercent() {
        return this.percent;
    }
    
    public void setPercent(final char percent) {
        this.percent = percent;
    }
    
    public char getDigit() {
        return this.digit;
    }
    
    public void setDigit(final char digit) {
        this.digit = digit;
    }
    
    public char getPatternSeparator() {
        return this.patternSeparator;
    }
    
    public void setPatternSeparator(final char patternSeparator) {
        this.patternSeparator = patternSeparator;
    }
    
    public String getInfinity() {
        return this.infinity;
    }
    
    public void setInfinity(final String infinity) {
        this.infinity = infinity;
    }
    
    public String getNaN() {
        return this.NaN;
    }
    
    public void setNaN(final String naN) {
        this.NaN = naN;
    }
    
    public char getMinusSign() {
        return this.minusSign;
    }
    
    public void setMinusSign(final char minusSign) {
        this.minusSign = minusSign;
    }
    
    public String getCurrencySymbol() {
        return this.currencySymbol;
    }
    
    public void setCurrencySymbol(final String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
    
    public String getInternationalCurrencySymbol() {
        return this.intlCurrencySymbol;
    }
    
    public void setInternationalCurrencySymbol(final String intlCurrencySymbol) {
        this.intlCurrencySymbol = intlCurrencySymbol;
        this.currency = null;
        if (intlCurrencySymbol != null) {
            try {
                this.currency = Currency.getInstance(intlCurrencySymbol);
                this.currencySymbol = this.currency.getSymbol();
            }
            catch (IllegalArgumentException ex) {}
        }
    }
    
    public Currency getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(final Currency currency) {
        if (currency == null) {
            throw new NullPointerException();
        }
        this.currency = currency;
        this.intlCurrencySymbol = currency.getCurrencyCode();
        this.currencySymbol = currency.getSymbol(this.locale);
    }
    
    public char getMonetaryDecimalSeparator() {
        return this.monetarySeparator;
    }
    
    public void setMonetaryDecimalSeparator(final char monetarySeparator) {
        this.monetarySeparator = monetarySeparator;
    }
    
    char getExponentialSymbol() {
        return this.exponential;
    }
    
    public String getExponentSeparator() {
        return this.exponentialSeparator;
    }
    
    void setExponentialSymbol(final char exponential) {
        this.exponential = exponential;
    }
    
    public void setExponentSeparator(final String exponentialSeparator) {
        if (exponentialSeparator == null) {
            throw new NullPointerException();
        }
        this.exponentialSeparator = exponentialSeparator;
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
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
        final DecimalFormatSymbols decimalFormatSymbols = (DecimalFormatSymbols)o;
        return this.zeroDigit == decimalFormatSymbols.zeroDigit && this.groupingSeparator == decimalFormatSymbols.groupingSeparator && this.decimalSeparator == decimalFormatSymbols.decimalSeparator && this.percent == decimalFormatSymbols.percent && this.perMill == decimalFormatSymbols.perMill && this.digit == decimalFormatSymbols.digit && this.minusSign == decimalFormatSymbols.minusSign && this.patternSeparator == decimalFormatSymbols.patternSeparator && this.infinity.equals(decimalFormatSymbols.infinity) && this.NaN.equals(decimalFormatSymbols.NaN) && this.currencySymbol.equals(decimalFormatSymbols.currencySymbol) && this.intlCurrencySymbol.equals(decimalFormatSymbols.intlCurrencySymbol) && this.currency == decimalFormatSymbols.currency && this.monetarySeparator == decimalFormatSymbols.monetarySeparator && this.exponentialSeparator.equals(decimalFormatSymbols.exponentialSeparator) && this.locale.equals(decimalFormatSymbols.locale);
    }
    
    @Override
    public int hashCode() {
        return (this.zeroDigit * '%' + this.groupingSeparator) * '%' + this.decimalSeparator;
    }
    
    private void initialize(final Locale locale) {
        this.locale = locale;
        LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter(DecimalFormatSymbolsProvider.class, locale);
        if (!(localeProviderAdapter instanceof ResourceBundleBasedAdapter)) {
            localeProviderAdapter = LocaleProviderAdapter.getResourceBundleBased();
        }
        final Object[] decimalFormatSymbolsData = localeProviderAdapter.getLocaleResources(locale).getDecimalFormatSymbolsData();
        final String[] array = (String[])decimalFormatSymbolsData[0];
        this.decimalSeparator = array[0].charAt(0);
        this.groupingSeparator = array[1].charAt(0);
        this.patternSeparator = array[2].charAt(0);
        this.percent = array[3].charAt(0);
        this.zeroDigit = array[4].charAt(0);
        this.digit = array[5].charAt(0);
        this.minusSign = array[6].charAt(0);
        this.exponential = array[7].charAt(0);
        this.exponentialSeparator = array[7];
        this.perMill = array[8].charAt(0);
        this.infinity = array[9];
        this.NaN = array[10];
        if (locale.getCountry().length() > 0) {
            try {
                this.currency = Currency.getInstance(locale);
            }
            catch (IllegalArgumentException ex) {}
        }
        if (this.currency != null) {
            this.intlCurrencySymbol = this.currency.getCurrencyCode();
            if (decimalFormatSymbolsData[1] != null && decimalFormatSymbolsData[1] == this.intlCurrencySymbol) {
                this.currencySymbol = (String)decimalFormatSymbolsData[2];
            }
            else {
                this.currencySymbol = this.currency.getSymbol(locale);
                decimalFormatSymbolsData[1] = this.intlCurrencySymbol;
                decimalFormatSymbolsData[2] = this.currencySymbol;
            }
        }
        else {
            this.intlCurrencySymbol = "XXX";
            try {
                this.currency = Currency.getInstance(this.intlCurrencySymbol);
            }
            catch (IllegalArgumentException ex2) {}
            this.currencySymbol = "¤";
        }
        this.monetarySeparator = this.decimalSeparator;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.monetarySeparator = this.decimalSeparator;
            this.exponential = 'E';
        }
        if (this.serialVersionOnStream < 2) {
            this.locale = Locale.ROOT;
        }
        if (this.serialVersionOnStream < 3) {
            this.exponentialSeparator = Character.toString(this.exponential);
        }
        this.serialVersionOnStream = 3;
        if (this.intlCurrencySymbol != null) {
            try {
                this.currency = Currency.getInstance(this.intlCurrencySymbol);
            }
            catch (IllegalArgumentException ex) {}
        }
    }
}
