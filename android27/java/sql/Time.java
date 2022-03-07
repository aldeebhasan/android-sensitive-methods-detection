package java.sql;

import java.util.*;
import java.time.*;

public class Time extends Date
{
    static final long serialVersionUID = 8397324403548013681L;
    
    public Time(final int n, final int n2, final int n3) {
        super(70, 0, 1, n, n2, n3);
    }
    
    public Time(final long n) {
        super(n);
    }
    
    @Override
    public void setTime(final long time) {
        super.setTime(time);
    }
    
    public static Time valueOf(final String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        final int index = s.indexOf(58);
        final int index2 = s.indexOf(58, index + 1);
        if (index > 0 & index2 > 0 & index2 < s.length() - 1) {
            return new Time(Integer.parseInt(s.substring(0, index)), Integer.parseInt(s.substring(index + 1, index2)), Integer.parseInt(s.substring(index2 + 1)));
        }
        throw new IllegalArgumentException();
    }
    
    @Override
    public String toString() {
        final int hours = super.getHours();
        final int minutes = super.getMinutes();
        final int seconds = super.getSeconds();
        String s;
        if (hours < 10) {
            s = "0" + hours;
        }
        else {
            s = Integer.toString(hours);
        }
        String s2;
        if (minutes < 10) {
            s2 = "0" + minutes;
        }
        else {
            s2 = Integer.toString(minutes);
        }
        String s3;
        if (seconds < 10) {
            s3 = "0" + seconds;
        }
        else {
            s3 = Integer.toString(seconds);
        }
        return s + ":" + s2 + ":" + s3;
    }
    
    @Deprecated
    @Override
    public int getYear() {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public int getMonth() {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public int getDay() {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public int getDate() {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public void setYear(final int n) {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public void setMonth(final int n) {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public void setDate(final int n) {
        throw new IllegalArgumentException();
    }
    
    public static Time valueOf(final LocalTime localTime) {
        return new Time(localTime.getHour(), localTime.getMinute(), localTime.getSecond());
    }
    
    public LocalTime toLocalTime() {
        return LocalTime.of(this.getHours(), this.getMinutes(), this.getSeconds());
    }
    
    @Override
    public Instant toInstant() {
        throw new UnsupportedOperationException();
    }
}
