package javax.xml.datatype;

import java.math.*;
import javax.xml.namespace.*;
import java.util.*;

public abstract class XMLGregorianCalendar implements Cloneable
{
    public abstract void clear();
    
    public abstract void reset();
    
    public abstract void setYear(final BigInteger p0);
    
    public abstract void setYear(final int p0);
    
    public abstract void setMonth(final int p0);
    
    public abstract void setDay(final int p0);
    
    public abstract void setTimezone(final int p0);
    
    public void setTime(final int n, final int n2, final int n3) {
        this.setTime(n, n2, n3, null);
    }
    
    public abstract void setHour(final int p0);
    
    public abstract void setMinute(final int p0);
    
    public abstract void setSecond(final int p0);
    
    public abstract void setMillisecond(final int p0);
    
    public abstract void setFractionalSecond(final BigDecimal p0);
    
    public void setTime(final int hour, final int minute, final int second, final BigDecimal fractionalSecond) {
        this.setHour(hour);
        this.setMinute(minute);
        this.setSecond(second);
        this.setFractionalSecond(fractionalSecond);
    }
    
    public void setTime(final int hour, final int minute, final int second, final int millisecond) {
        this.setHour(hour);
        this.setMinute(minute);
        this.setSecond(second);
        this.setMillisecond(millisecond);
    }
    
    public abstract BigInteger getEon();
    
    public abstract int getYear();
    
    public abstract BigInteger getEonAndYear();
    
    public abstract int getMonth();
    
    public abstract int getDay();
    
    public abstract int getTimezone();
    
    public abstract int getHour();
    
    public abstract int getMinute();
    
    public abstract int getSecond();
    
    public int getMillisecond() {
        if (this.getFractionalSecond() == null) {
            return Integer.MIN_VALUE;
        }
        return this.getFractionalSecond().movePointRight(3).intValue();
    }
    
    public abstract BigDecimal getFractionalSecond();
    
    public abstract int compare(final XMLGregorianCalendar p0);
    
    public abstract XMLGregorianCalendar normalize();
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof XMLGregorianCalendar && this.compare((XMLGregorianCalendar)o) == 0;
    }
    
    @Override
    public int hashCode() {
        int timezone = this.getTimezone();
        if (timezone == Integer.MIN_VALUE) {
            timezone = 0;
        }
        XMLGregorianCalendar normalize = this;
        if (timezone != 0) {
            normalize = this.normalize();
        }
        return normalize.getYear() + normalize.getMonth() + normalize.getDay() + normalize.getHour() + normalize.getMinute() + normalize.getSecond();
    }
    
    public abstract String toXMLFormat();
    
    public abstract QName getXMLSchemaType();
    
    @Override
    public String toString() {
        return this.toXMLFormat();
    }
    
    public abstract boolean isValid();
    
    public abstract void add(final Duration p0);
    
    public abstract GregorianCalendar toGregorianCalendar();
    
    public abstract GregorianCalendar toGregorianCalendar(final TimeZone p0, final Locale p1, final XMLGregorianCalendar p2);
    
    public abstract TimeZone getTimeZone(final int p0);
    
    public abstract Object clone();
}
