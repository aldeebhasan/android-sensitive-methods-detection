package java.sql;

import java.time.*;

public class Date extends java.util.Date
{
    static final long serialVersionUID = 1511598038487230103L;
    
    public Date(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public Date(final long n) {
        super(n);
    }
    
    @Override
    public void setTime(final long time) {
        super.setTime(time);
    }
    
    public static Date valueOf(final String s) {
        Date date = null;
        if (s == null) {
            throw new IllegalArgumentException();
        }
        final int index = s.indexOf(45);
        final int index2 = s.indexOf(45, index + 1);
        if (index > 0 && index2 > 0 && index2 < s.length() - 1) {
            final String substring = s.substring(0, index);
            final String substring2 = s.substring(index + 1, index2);
            final String substring3 = s.substring(index2 + 1);
            if (substring.length() == 4 && substring2.length() >= 1 && substring2.length() <= 2 && substring3.length() >= 1 && substring3.length() <= 2) {
                final int int1 = Integer.parseInt(substring);
                final int int2 = Integer.parseInt(substring2);
                final int int3 = Integer.parseInt(substring3);
                if (int2 >= 1 && int2 <= 12 && int3 >= 1 && int3 <= 31) {
                    date = new Date(int1 - 1900, int2 - 1, int3);
                }
            }
        }
        if (date == null) {
            throw new IllegalArgumentException();
        }
        return date;
    }
    
    @Override
    public String toString() {
        final int n = super.getYear() + 1900;
        final int n2 = super.getMonth() + 1;
        final int date = super.getDate();
        final char[] charArray = "2000-00-00".toCharArray();
        charArray[0] = Character.forDigit(n / 1000, 10);
        charArray[1] = Character.forDigit(n / 100 % 10, 10);
        charArray[2] = Character.forDigit(n / 10 % 10, 10);
        charArray[3] = Character.forDigit(n % 10, 10);
        charArray[5] = Character.forDigit(n2 / 10, 10);
        charArray[6] = Character.forDigit(n2 % 10, 10);
        charArray[8] = Character.forDigit(date / 10, 10);
        charArray[9] = Character.forDigit(date % 10, 10);
        return new String(charArray);
    }
    
    @Deprecated
    @Override
    public int getHours() {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public int getMinutes() {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public int getSeconds() {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public void setHours(final int n) {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public void setMinutes(final int n) {
        throw new IllegalArgumentException();
    }
    
    @Deprecated
    @Override
    public void setSeconds(final int n) {
        throw new IllegalArgumentException();
    }
    
    public static Date valueOf(final LocalDate localDate) {
        return new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
    }
    
    public LocalDate toLocalDate() {
        return LocalDate.of(this.getYear() + 1900, this.getMonth() + 1, this.getDate());
    }
    
    @Override
    public Instant toInstant() {
        throw new UnsupportedOperationException();
    }
}
