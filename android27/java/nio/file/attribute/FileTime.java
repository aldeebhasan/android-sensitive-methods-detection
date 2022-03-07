package java.nio.file.attribute;

import java.util.concurrent.*;
import java.util.*;
import java.time.*;

public final class FileTime implements Comparable<FileTime>
{
    private final TimeUnit unit;
    private final long value;
    private Instant instant;
    private String valueAsString;
    private static final long HOURS_PER_DAY = 24L;
    private static final long MINUTES_PER_HOUR = 60L;
    private static final long SECONDS_PER_MINUTE = 60L;
    private static final long SECONDS_PER_HOUR = 3600L;
    private static final long SECONDS_PER_DAY = 86400L;
    private static final long MILLIS_PER_SECOND = 1000L;
    private static final long MICROS_PER_SECOND = 1000000L;
    private static final long NANOS_PER_SECOND = 1000000000L;
    private static final int NANOS_PER_MILLI = 1000000;
    private static final int NANOS_PER_MICRO = 1000;
    private static final long MIN_SECOND = -31557014167219200L;
    private static final long MAX_SECOND = 31556889864403199L;
    private static final long DAYS_PER_10000_YEARS = 3652425L;
    private static final long SECONDS_PER_10000_YEARS = 315569520000L;
    private static final long SECONDS_0000_TO_1970 = 62167219200L;
    
    private FileTime(final long value, final TimeUnit unit, final Instant instant) {
        this.value = value;
        this.unit = unit;
        this.instant = instant;
    }
    
    public static FileTime from(final long n, final TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit, "unit");
        return new FileTime(n, timeUnit, null);
    }
    
    public static FileTime fromMillis(final long n) {
        return new FileTime(n, TimeUnit.MILLISECONDS, null);
    }
    
    public static FileTime from(final Instant instant) {
        Objects.requireNonNull(instant, "instant");
        return new FileTime(0L, null, instant);
    }
    
    public long to(final TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit, "unit");
        if (this.unit != null) {
            return timeUnit.convert(this.value, this.unit);
        }
        final long convert = timeUnit.convert(this.instant.getEpochSecond(), TimeUnit.SECONDS);
        if (convert == Long.MIN_VALUE || convert == Long.MAX_VALUE) {
            return convert;
        }
        final long convert2 = timeUnit.convert(this.instant.getNano(), TimeUnit.NANOSECONDS);
        final long n = convert + convert2;
        if (((convert ^ n) & (convert2 ^ n)) < 0L) {
            return (convert < 0L) ? Long.MIN_VALUE : Long.MAX_VALUE;
        }
        return n;
    }
    
    public long toMillis() {
        if (this.unit != null) {
            return this.unit.toMillis(this.value);
        }
        final long epochSecond = this.instant.getEpochSecond();
        final int nano = this.instant.getNano();
        final long n = epochSecond * 1000L;
        if ((Math.abs(epochSecond) | 0x3E8L) >>> 31 != 0L && n / 1000L != epochSecond) {
            return (epochSecond < 0L) ? Long.MIN_VALUE : Long.MAX_VALUE;
        }
        return n + nano / 1000000;
    }
    
    private static long scale(final long n, final long n2, final long n3) {
        if (n > n3) {
            return Long.MAX_VALUE;
        }
        if (n < -n3) {
            return Long.MIN_VALUE;
        }
        return n * n2;
    }
    
    public Instant toInstant() {
        if (this.instant == null) {
            int n = 0;
            long n2 = 0L;
            switch (this.unit) {
                case DAYS: {
                    n2 = scale(this.value, 86400L, 106751991167300L);
                    break;
                }
                case HOURS: {
                    n2 = scale(this.value, 3600L, 2562047788015215L);
                    break;
                }
                case MINUTES: {
                    n2 = scale(this.value, 60L, 153722867280912930L);
                    break;
                }
                case SECONDS: {
                    n2 = this.value;
                    break;
                }
                case MILLISECONDS: {
                    n2 = Math.floorDiv(this.value, 1000L);
                    n = (int)Math.floorMod(this.value, 1000L) * 1000000;
                    break;
                }
                case MICROSECONDS: {
                    n2 = Math.floorDiv(this.value, 1000000L);
                    n = (int)Math.floorMod(this.value, 1000000L) * 1000;
                    break;
                }
                case NANOSECONDS: {
                    n2 = Math.floorDiv(this.value, 1000000000L);
                    n = (int)Math.floorMod(this.value, 1000000000L);
                    break;
                }
                default: {
                    throw new AssertionError((Object)"Unit not handled");
                }
            }
            if (n2 <= -31557014167219200L) {
                this.instant = Instant.MIN;
            }
            else if (n2 >= 31556889864403199L) {
                this.instant = Instant.MAX;
            }
            else {
                this.instant = Instant.ofEpochSecond(n2, n);
            }
        }
        return this.instant;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof FileTime && this.compareTo((FileTime)o) == 0;
    }
    
    @Override
    public int hashCode() {
        return this.toInstant().hashCode();
    }
    
    private long toDays() {
        if (this.unit != null) {
            return this.unit.toDays(this.value);
        }
        return TimeUnit.SECONDS.toDays(this.toInstant().getEpochSecond());
    }
    
    private long toExcessNanos(final long n) {
        if (this.unit != null) {
            return this.unit.toNanos(this.value - this.unit.convert(n, TimeUnit.DAYS));
        }
        return TimeUnit.SECONDS.toNanos(this.toInstant().getEpochSecond() - TimeUnit.DAYS.toSeconds(n));
    }
    
    @Override
    public int compareTo(final FileTime fileTime) {
        if (this.unit != null && this.unit == fileTime.unit) {
            return Long.compare(this.value, fileTime.value);
        }
        final long epochSecond = this.toInstant().getEpochSecond();
        final int compare = Long.compare(epochSecond, fileTime.toInstant().getEpochSecond());
        if (compare != 0) {
            return compare;
        }
        final int compare2 = Long.compare(this.toInstant().getNano(), fileTime.toInstant().getNano());
        if (compare2 != 0) {
            return compare2;
        }
        if (epochSecond != 31556889864403199L && epochSecond != -31557014167219200L) {
            return 0;
        }
        final long days = this.toDays();
        final long days2 = fileTime.toDays();
        if (days == days2) {
            return Long.compare(this.toExcessNanos(days), fileTime.toExcessNanos(days2));
        }
        return Long.compare(days, days2);
    }
    
    private StringBuilder append(final StringBuilder sb, int i, int n) {
        while (i > 0) {
            sb.append((char)(n / i + 48));
            n %= i;
            i /= 10;
        }
        return sb;
    }
    
    @Override
    public String toString() {
        if (this.valueAsString == null) {
            int nano = 0;
            long n;
            if (this.instant == null && this.unit.compareTo(TimeUnit.SECONDS) >= 0) {
                n = this.unit.toSeconds(this.value);
            }
            else {
                n = this.toInstant().getEpochSecond();
                nano = this.toInstant().getNano();
            }
            LocalDateTime localDateTime;
            int n4;
            if (n >= -62167219200L) {
                final long n2 = n - 315569520000L + 62167219200L;
                final long n3 = Math.floorDiv(n2, 315569520000L) + 1L;
                localDateTime = LocalDateTime.ofEpochSecond(Math.floorMod(n2, 315569520000L) - 62167219200L, nano, ZoneOffset.UTC);
                n4 = localDateTime.getYear() + (int)n3 * 10000;
            }
            else {
                final long n5 = n + 62167219200L;
                final long n6 = n5 / 315569520000L;
                localDateTime = LocalDateTime.ofEpochSecond(n5 % 315569520000L - 62167219200L, nano, ZoneOffset.UTC);
                n4 = localDateTime.getYear() + (int)n6 * 10000;
            }
            if (n4 <= 0) {
                --n4;
            }
            int nano2 = localDateTime.getNano();
            final StringBuilder sb = new StringBuilder(64);
            sb.append((n4 < 0) ? "-" : "");
            final int abs = Math.abs(n4);
            if (abs < 10000) {
                this.append(sb, 1000, Math.abs(abs));
            }
            else {
                sb.append(String.valueOf(abs));
            }
            sb.append('-');
            this.append(sb, 10, localDateTime.getMonthValue());
            sb.append('-');
            this.append(sb, 10, localDateTime.getDayOfMonth());
            sb.append('T');
            this.append(sb, 10, localDateTime.getHour());
            sb.append(':');
            this.append(sb, 10, localDateTime.getMinute());
            sb.append(':');
            this.append(sb, 10, localDateTime.getSecond());
            if (nano2 != 0) {
                sb.append('.');
                int n7;
                for (n7 = 100000000; nano2 % 10 == 0; nano2 /= 10, n7 /= 10) {}
                this.append(sb, n7, nano2);
            }
            sb.append('Z');
            this.valueAsString = sb.toString();
        }
        return this.valueAsString;
    }
}
