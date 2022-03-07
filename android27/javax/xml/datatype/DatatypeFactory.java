package javax.xml.datatype;

import java.util.regex.*;
import java.math.*;
import java.util.*;

public abstract class DatatypeFactory
{
    public static final String DATATYPEFACTORY_PROPERTY = "javax.xml.datatype.DatatypeFactory";
    public static final String DATATYPEFACTORY_IMPLEMENTATION_CLASS;
    private static final Pattern XDTSCHEMA_YMD;
    private static final Pattern XDTSCHEMA_DTD;
    
    public static DatatypeFactory newInstance() throws DatatypeConfigurationException {
        return FactoryFinder.find(DatatypeFactory.class, DatatypeFactory.DATATYPEFACTORY_IMPLEMENTATION_CLASS);
    }
    
    public static DatatypeFactory newInstance(final String s, final ClassLoader classLoader) throws DatatypeConfigurationException {
        return FactoryFinder.newInstance(DatatypeFactory.class, s, classLoader, false);
    }
    
    public abstract Duration newDuration(final String p0);
    
    public abstract Duration newDuration(final long p0);
    
    public abstract Duration newDuration(final boolean p0, final BigInteger p1, final BigInteger p2, final BigInteger p3, final BigInteger p4, final BigInteger p5, final BigDecimal p6);
    
    public Duration newDuration(final boolean b, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return this.newDuration(b, (n != Integer.MIN_VALUE) ? BigInteger.valueOf(n) : null, (n2 != Integer.MIN_VALUE) ? BigInteger.valueOf(n2) : null, (n3 != Integer.MIN_VALUE) ? BigInteger.valueOf(n3) : null, (n4 != Integer.MIN_VALUE) ? BigInteger.valueOf(n4) : null, (n5 != Integer.MIN_VALUE) ? BigInteger.valueOf(n5) : null, (n6 != Integer.MIN_VALUE) ? BigDecimal.valueOf(n6) : null);
    }
    
    public Duration newDurationDayTime(final String s) {
        if (s == null) {
            throw new NullPointerException("Trying to create an xdt:dayTimeDuration with an invalid lexical representation of \"null\"");
        }
        if (!DatatypeFactory.XDTSCHEMA_DTD.matcher(s).matches()) {
            throw new IllegalArgumentException("Trying to create an xdt:dayTimeDuration with an invalid lexical representation of \"" + s + "\", data model requires years and months only.");
        }
        return this.newDuration(s);
    }
    
    public Duration newDurationDayTime(final long n) {
        return this.newDuration(n);
    }
    
    public Duration newDurationDayTime(final boolean b, final BigInteger bigInteger, final BigInteger bigInteger2, final BigInteger bigInteger3, final BigInteger bigInteger4) {
        return this.newDuration(b, null, null, bigInteger, bigInteger2, bigInteger3, (bigInteger4 != null) ? new BigDecimal(bigInteger4) : null);
    }
    
    public Duration newDurationDayTime(final boolean b, final int n, final int n2, final int n3, final int n4) {
        return this.newDurationDayTime(b, BigInteger.valueOf(n), BigInteger.valueOf(n2), BigInteger.valueOf(n3), BigInteger.valueOf(n4));
    }
    
    public Duration newDurationYearMonth(final String s) {
        if (s == null) {
            throw new NullPointerException("Trying to create an xdt:yearMonthDuration with an invalid lexical representation of \"null\"");
        }
        if (!DatatypeFactory.XDTSCHEMA_YMD.matcher(s).matches()) {
            throw new IllegalArgumentException("Trying to create an xdt:yearMonthDuration with an invalid lexical representation of \"" + s + "\", data model requires days and times only.");
        }
        return this.newDuration(s);
    }
    
    public Duration newDurationYearMonth(final long n) {
        final Duration duration = this.newDuration(n);
        final boolean b = duration.getSign() != -1;
        BigInteger zero = (BigInteger)duration.getField(DatatypeConstants.YEARS);
        if (zero == null) {
            zero = BigInteger.ZERO;
        }
        BigInteger zero2 = (BigInteger)duration.getField(DatatypeConstants.MONTHS);
        if (zero2 == null) {
            zero2 = BigInteger.ZERO;
        }
        return this.newDurationYearMonth(b, zero, zero2);
    }
    
    public Duration newDurationYearMonth(final boolean b, final BigInteger bigInteger, final BigInteger bigInteger2) {
        return this.newDuration(b, bigInteger, bigInteger2, null, null, null, null);
    }
    
    public Duration newDurationYearMonth(final boolean b, final int n, final int n2) {
        return this.newDurationYearMonth(b, BigInteger.valueOf(n), BigInteger.valueOf(n2));
    }
    
    public abstract XMLGregorianCalendar newXMLGregorianCalendar();
    
    public abstract XMLGregorianCalendar newXMLGregorianCalendar(final String p0);
    
    public abstract XMLGregorianCalendar newXMLGregorianCalendar(final GregorianCalendar p0);
    
    public abstract XMLGregorianCalendar newXMLGregorianCalendar(final BigInteger p0, final int p1, final int p2, final int p3, final int p4, final int p5, final BigDecimal p6, final int p7);
    
    public XMLGregorianCalendar newXMLGregorianCalendar(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final BigInteger bigInteger = (n != Integer.MIN_VALUE) ? BigInteger.valueOf(n) : null;
        BigDecimal movePointLeft = null;
        if (n7 != Integer.MIN_VALUE) {
            if (n7 < 0 || n7 > 1000) {
                throw new IllegalArgumentException("javax.xml.datatype.DatatypeFactory#newXMLGregorianCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond, int timezone)with invalid millisecond: " + n7);
            }
            movePointLeft = BigDecimal.valueOf(n7).movePointLeft(3);
        }
        return this.newXMLGregorianCalendar(bigInteger, n2, n3, n4, n5, n6, movePointLeft, n8);
    }
    
    public XMLGregorianCalendar newXMLGregorianCalendarDate(final int n, final int n2, final int n3, final int n4) {
        return this.newXMLGregorianCalendar(n, n2, n3, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, n4);
    }
    
    public XMLGregorianCalendar newXMLGregorianCalendarTime(final int n, final int n2, final int n3, final int n4) {
        return this.newXMLGregorianCalendar(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, n, n2, n3, Integer.MIN_VALUE, n4);
    }
    
    public XMLGregorianCalendar newXMLGregorianCalendarTime(final int n, final int n2, final int n3, final BigDecimal bigDecimal, final int n4) {
        return this.newXMLGregorianCalendar(null, Integer.MIN_VALUE, Integer.MIN_VALUE, n, n2, n3, bigDecimal, n4);
    }
    
    public XMLGregorianCalendar newXMLGregorianCalendarTime(final int n, final int n2, final int n3, final int n4, final int n5) {
        BigDecimal movePointLeft = null;
        if (n4 != Integer.MIN_VALUE) {
            if (n4 < 0 || n4 > 1000) {
                throw new IllegalArgumentException("javax.xml.datatype.DatatypeFactory#newXMLGregorianCalendarTime(int hours, int minutes, int seconds, int milliseconds, int timezone)with invalid milliseconds: " + n4);
            }
            movePointLeft = BigDecimal.valueOf(n4).movePointLeft(3);
        }
        return this.newXMLGregorianCalendarTime(n, n2, n3, movePointLeft, n5);
    }
    
    static {
        DATATYPEFACTORY_IMPLEMENTATION_CLASS = new String("com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl");
        XDTSCHEMA_YMD = Pattern.compile("[^DT]*");
        XDTSCHEMA_DTD = Pattern.compile("[^YM]*[DT].*");
    }
}
