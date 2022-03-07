package java.sql;

import java.util.*;
import java.time.*;

public class Timestamp extends Date
{
    private int nanos;
    static final long serialVersionUID = 2745179027874758501L;
    private static final int MILLIS_PER_SECOND = 1000;
    
    public Timestamp(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int nanos) {
        super(n, n2, n3, n4, n5, n6);
        if (nanos > 999999999 || nanos < 0) {
            throw new IllegalArgumentException("nanos > 999999999 or < 0");
        }
        this.nanos = nanos;
    }
    
    public Timestamp(final long n) {
        super(n / 1000L * 1000L);
        this.nanos = (int)(n % 1000L * 1000000L);
        if (this.nanos < 0) {
            this.nanos += 1000000000;
            super.setTime((n / 1000L - 1L) * 1000L);
        }
    }
    
    @Override
    public void setTime(final long n) {
        super.setTime(n / 1000L * 1000L);
        this.nanos = (int)(n % 1000L * 1000000L);
        if (this.nanos < 0) {
            this.nanos += 1000000000;
            super.setTime((n / 1000L - 1L) * 1000L);
        }
    }
    
    @Override
    public long getTime() {
        return super.getTime() + this.nanos / 1000000;
    }
    
    public static Timestamp valueOf(String trim) {
        int int1 = 0;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;
        final String s = "Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]";
        final String s2 = "000000000";
        if (trim == null) {
            throw new IllegalArgumentException("null string");
        }
        trim = trim.trim();
        final int index = trim.indexOf(32);
        if (index <= 0) {
            throw new IllegalArgumentException(s);
        }
        final String substring = trim.substring(0, index);
        final String substring2 = trim.substring(index + 1);
        final int index2 = substring.indexOf(45);
        final int index3 = substring.indexOf(45, index2 + 1);
        if (substring2 == null) {
            throw new IllegalArgumentException(s);
        }
        final int index4 = substring2.indexOf(58);
        final int index5 = substring2.indexOf(58, index4 + 1);
        final int index6 = substring2.indexOf(46, index5 + 1);
        boolean b = false;
        if (index2 > 0 && index3 > 0 && index3 < substring.length() - 1) {
            final String substring3 = substring.substring(0, index2);
            final String substring4 = substring.substring(index2 + 1, index3);
            final String substring5 = substring.substring(index3 + 1);
            if (substring3.length() == 4 && substring4.length() >= 1 && substring4.length() <= 2 && substring5.length() >= 1 && substring5.length() <= 2) {
                int1 = Integer.parseInt(substring3);
                int2 = Integer.parseInt(substring4);
                int3 = Integer.parseInt(substring5);
                if (int2 >= 1 && int2 <= 12 && int3 >= 1 && int3 <= 31) {
                    b = true;
                }
            }
        }
        if (!b) {
            throw new IllegalArgumentException(s);
        }
        if (index4 > 0 & index5 > 0 & index5 < substring2.length() - 1) {
            final int int5 = Integer.parseInt(substring2.substring(0, index4));
            final int int6 = Integer.parseInt(substring2.substring(index4 + 1, index5));
            int n;
            if (index6 > 0 & index6 < substring2.length() - 1) {
                n = Integer.parseInt(substring2.substring(index5 + 1, index6));
                final String substring6 = substring2.substring(index6 + 1);
                if (substring6.length() > 9) {
                    throw new IllegalArgumentException(s);
                }
                if (!Character.isDigit(substring6.charAt(0))) {
                    throw new IllegalArgumentException(s);
                }
                int4 = Integer.parseInt(substring6 + s2.substring(0, 9 - substring6.length()));
            }
            else {
                if (index6 > 0) {
                    throw new IllegalArgumentException(s);
                }
                n = Integer.parseInt(substring2.substring(index5 + 1));
            }
            return new Timestamp(int1 - 1900, int2 - 1, int3, int5, int6, n, int4);
        }
        throw new IllegalArgumentException(s);
    }
    
    @Override
    public String toString() {
        final int n = super.getYear() + 1900;
        final int n2 = super.getMonth() + 1;
        final int date = super.getDate();
        final int hours = super.getHours();
        final int minutes = super.getMinutes();
        final int seconds = super.getSeconds();
        final String s = "000000000";
        final String s2 = "0000";
        String s3;
        if (n < 1000) {
            final String string = "" + n;
            s3 = s2.substring(0, 4 - string.length()) + string;
        }
        else {
            s3 = "" + n;
        }
        String s4;
        if (n2 < 10) {
            s4 = "0" + n2;
        }
        else {
            s4 = Integer.toString(n2);
        }
        String s5;
        if (date < 10) {
            s5 = "0" + date;
        }
        else {
            s5 = Integer.toString(date);
        }
        String s6;
        if (hours < 10) {
            s6 = "0" + hours;
        }
        else {
            s6 = Integer.toString(hours);
        }
        String s7;
        if (minutes < 10) {
            s7 = "0" + minutes;
        }
        else {
            s7 = Integer.toString(minutes);
        }
        String s8;
        if (seconds < 10) {
            s8 = "0" + seconds;
        }
        else {
            s8 = Integer.toString(seconds);
        }
        String s9;
        if (this.nanos == 0) {
            s9 = "0";
        }
        else {
            final String string2 = Integer.toString(this.nanos);
            final String string3 = s.substring(0, 9 - string2.length()) + string2;
            final char[] array = new char[string3.length()];
            string3.getChars(0, string3.length(), array, 0);
            int n3;
            for (n3 = 8; array[n3] == '0'; --n3) {}
            s9 = new String(array, 0, n3 + 1);
        }
        final StringBuffer sb = new StringBuffer(20 + s9.length());
        sb.append(s3);
        sb.append("-");
        sb.append(s4);
        sb.append("-");
        sb.append(s5);
        sb.append(" ");
        sb.append(s6);
        sb.append(":");
        sb.append(s7);
        sb.append(":");
        sb.append(s8);
        sb.append(".");
        sb.append(s9);
        return sb.toString();
    }
    
    public int getNanos() {
        return this.nanos;
    }
    
    public void setNanos(final int nanos) {
        if (nanos > 999999999 || nanos < 0) {
            throw new IllegalArgumentException("nanos > 999999999 or < 0");
        }
        this.nanos = nanos;
    }
    
    public boolean equals(final Timestamp timestamp) {
        return super.equals(timestamp) && this.nanos == timestamp.nanos;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Timestamp && this.equals((Timestamp)o);
    }
    
    public boolean before(final Timestamp timestamp) {
        return this.compareTo(timestamp) < 0;
    }
    
    public boolean after(final Timestamp timestamp) {
        return this.compareTo(timestamp) > 0;
    }
    
    public int compareTo(final Timestamp timestamp) {
        final long time = this.getTime();
        final long time2 = timestamp.getTime();
        final int n = (time < time2) ? -1 : ((time == time2) ? 0 : 1);
        if (n == 0) {
            if (this.nanos > timestamp.nanos) {
                return 1;
            }
            if (this.nanos < timestamp.nanos) {
                return -1;
            }
        }
        return n;
    }
    
    @Override
    public int compareTo(final Date date) {
        if (date instanceof Timestamp) {
            return this.compareTo((Timestamp)date);
        }
        return this.compareTo(new Timestamp(date.getTime()));
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    public static Timestamp valueOf(final LocalDateTime localDateTime) {
        return new Timestamp(localDateTime.getYear() - 1900, localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond(), localDateTime.getNano());
    }
    
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(this.getYear() + 1900, this.getMonth() + 1, this.getDate(), this.getHours(), this.getMinutes(), this.getSeconds(), this.getNanos());
    }
    
    public static Timestamp from(final Instant instant) {
        try {
            final Timestamp timestamp = new Timestamp(instant.getEpochSecond() * 1000L);
            timestamp.nanos = instant.getNano();
            return timestamp;
        }
        catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    @Override
    public Instant toInstant() {
        return Instant.ofEpochSecond(super.getTime() / 1000L, this.nanos);
    }
}
