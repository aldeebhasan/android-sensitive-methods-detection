package java.util;

import sun.util.*;

public static class Builder
{
    private static final int NFIELDS = 18;
    private static final int WEEK_YEAR = 17;
    private long instant;
    private int[] fields;
    private int nextStamp;
    private int maxFieldIndex;
    private String type;
    private TimeZone zone;
    private boolean lenient;
    private Locale locale;
    private int firstDayOfWeek;
    private int minimalDaysInFirstWeek;
    
    public Builder() {
        this.lenient = true;
    }
    
    public Builder setInstant(final long instant) {
        if (this.fields != null) {
            throw new IllegalStateException();
        }
        this.instant = instant;
        this.nextStamp = 1;
        return this;
    }
    
    public Builder setInstant(final Date date) {
        return this.setInstant(date.getTime());
    }
    
    public Builder set(final int n, final int n2) {
        if (n < 0 || n >= 17) {
            throw new IllegalArgumentException("field is invalid");
        }
        if (this.isInstantSet()) {
            throw new IllegalStateException("instant has been set");
        }
        this.allocateFields();
        this.internalSet(n, n2);
        return this;
    }
    
    public Builder setFields(final int... array) {
        final int length = array.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        if (this.isInstantSet()) {
            throw new IllegalStateException("instant has been set");
        }
        if (this.nextStamp + length / 2 < 0) {
            throw new IllegalStateException("stamp counter overflow");
        }
        this.allocateFields();
        int i = 0;
        while (i < length) {
            final int n = array[i++];
            if (n < 0 || n >= 17) {
                throw new IllegalArgumentException("field is invalid");
            }
            this.internalSet(n, array[i++]);
        }
        return this;
    }
    
    public Builder setDate(final int n, final int n2, final int n3) {
        return this.setFields(1, n, 2, n2, 5, n3);
    }
    
    public Builder setTimeOfDay(final int n, final int n2, final int n3) {
        return this.setTimeOfDay(n, n2, n3, 0);
    }
    
    public Builder setTimeOfDay(final int n, final int n2, final int n3, final int n4) {
        return this.setFields(11, n, 12, n2, 13, n3, 14, n4);
    }
    
    public Builder setWeekDate(final int n, final int n2, final int n3) {
        this.allocateFields();
        this.internalSet(17, n);
        this.internalSet(3, n2);
        this.internalSet(7, n3);
        return this;
    }
    
    public Builder setTimeZone(final TimeZone zone) {
        if (zone == null) {
            throw new NullPointerException();
        }
        this.zone = zone;
        return this;
    }
    
    public Builder setLenient(final boolean lenient) {
        this.lenient = lenient;
        return this;
    }
    
    public Builder setCalendarType(String type) {
        if (type.equals("gregorian")) {
            type = "gregory";
        }
        if (!Calendar.getAvailableCalendarTypes().contains(type) && !type.equals("iso8601")) {
            throw new IllegalArgumentException("unknown calendar type: " + type);
        }
        if (this.type == null) {
            this.type = type;
        }
        else if (!this.type.equals(type)) {
            throw new IllegalStateException("calendar type override");
        }
        return this;
    }
    
    public Builder setLocale(final Locale locale) {
        if (locale == null) {
            throw new NullPointerException();
        }
        this.locale = locale;
        return this;
    }
    
    public Builder setWeekDefinition(final int firstDayOfWeek, final int minimalDaysInFirstWeek) {
        if (!this.isValidWeekParameter(firstDayOfWeek) || !this.isValidWeekParameter(minimalDaysInFirstWeek)) {
            throw new IllegalArgumentException();
        }
        this.firstDayOfWeek = firstDayOfWeek;
        this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
        return this;
    }
    
    public Calendar build() {
        if (this.locale == null) {
            this.locale = Locale.getDefault();
        }
        if (this.zone == null) {
            this.zone = TimeZone.getDefault();
        }
        if (this.type == null) {
            this.type = this.locale.getUnicodeLocaleType("ca");
        }
        if (this.type == null) {
            if (this.locale.getCountry() == "TH" && this.locale.getLanguage() == "th") {
                this.type = "buddhist";
            }
            else {
                this.type = "gregory";
            }
        }
        final String type = this.type;
        Calendar calendar = null;
        switch (type) {
            case "gregory": {
                calendar = new GregorianCalendar(this.zone, this.locale, true);
                break;
            }
            case "iso8601": {
                final GregorianCalendar gregorianCalendar = new GregorianCalendar(this.zone, this.locale, true);
                gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
                this.setWeekDefinition(2, 4);
                calendar = gregorianCalendar;
                break;
            }
            case "buddhist": {
                calendar = new BuddhistCalendar(this.zone, this.locale);
                calendar.clear();
                break;
            }
            case "japanese": {
                calendar = new JapaneseImperialCalendar(this.zone, this.locale, true);
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown calendar type: " + this.type);
            }
        }
        calendar.setLenient(this.lenient);
        if (this.firstDayOfWeek != 0) {
            calendar.setFirstDayOfWeek(this.firstDayOfWeek);
            calendar.setMinimalDaysInFirstWeek(this.minimalDaysInFirstWeek);
        }
        if (this.isInstantSet()) {
            calendar.setTimeInMillis(this.instant);
            calendar.complete();
            return calendar;
        }
        if (this.fields != null) {
            final boolean b = this.isSet(17) && this.fields[17] > this.fields[1];
            if (b && !calendar.isWeekDateSupported()) {
                throw new IllegalArgumentException("week date is unsupported by " + this.type);
            }
            for (int i = 2; i < this.nextStamp; ++i) {
                for (int j = 0; j <= this.maxFieldIndex; ++j) {
                    if (this.fields[j] == i) {
                        calendar.set(j, this.fields[18 + j]);
                        break;
                    }
                }
            }
            if (b) {
                calendar.setWeekDate(this.fields[35], this.isSet(3) ? this.fields[21] : 1, this.isSet(7) ? this.fields[25] : calendar.getFirstDayOfWeek());
            }
            calendar.complete();
        }
        return calendar;
    }
    
    private void allocateFields() {
        if (this.fields == null) {
            this.fields = new int[36];
            this.nextStamp = 2;
            this.maxFieldIndex = -1;
        }
    }
    
    private void internalSet(final int maxFieldIndex, final int n) {
        this.fields[maxFieldIndex] = this.nextStamp++;
        if (this.nextStamp < 0) {
            throw new IllegalStateException("stamp counter overflow");
        }
        this.fields[18 + maxFieldIndex] = n;
        if (maxFieldIndex > this.maxFieldIndex && maxFieldIndex < 17) {
            this.maxFieldIndex = maxFieldIndex;
        }
    }
    
    private boolean isInstantSet() {
        return this.nextStamp == 1;
    }
    
    private boolean isSet(final int n) {
        return this.fields != null && this.fields[n] > 0;
    }
    
    private boolean isValidWeekParameter(final int n) {
        return n > 0 && n <= 7;
    }
}
