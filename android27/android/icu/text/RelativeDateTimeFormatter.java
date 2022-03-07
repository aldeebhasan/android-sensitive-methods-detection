package android.icu.text;

import android.icu.util.*;
import java.util.*;

public final class RelativeDateTimeFormatter
{
    RelativeDateTimeFormatter() {
        throw new RuntimeException("Stub!");
    }
    
    public static RelativeDateTimeFormatter getInstance() {
        throw new RuntimeException("Stub!");
    }
    
    public static RelativeDateTimeFormatter getInstance(final ULocale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public static RelativeDateTimeFormatter getInstance(final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public static RelativeDateTimeFormatter getInstance(final ULocale locale, final NumberFormat nf) {
        throw new RuntimeException("Stub!");
    }
    
    public static RelativeDateTimeFormatter getInstance(final ULocale locale, final NumberFormat nf, final Style style, final DisplayContext capitalizationContext) {
        throw new RuntimeException("Stub!");
    }
    
    public static RelativeDateTimeFormatter getInstance(final Locale locale, final NumberFormat nf) {
        throw new RuntimeException("Stub!");
    }
    
    public String format(final double quantity, final Direction direction, final RelativeUnit unit) {
        throw new RuntimeException("Stub!");
    }
    
    public String format(final Direction direction, final AbsoluteUnit unit) {
        throw new RuntimeException("Stub!");
    }
    
    public String combineDateAndTime(final String relativeDateString, final String timeString) {
        throw new RuntimeException("Stub!");
    }
    
    public NumberFormat getNumberFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public DisplayContext getCapitalizationContext() {
        throw new RuntimeException("Stub!");
    }
    
    public Style getFormatStyle() {
        throw new RuntimeException("Stub!");
    }
    
    public enum Style
    {
        LONG, 
        NARROW, 
        SHORT;
    }
    
    public enum RelativeUnit
    {
        DAYS, 
        HOURS, 
        MINUTES, 
        MONTHS, 
        SECONDS, 
        WEEKS, 
        YEARS;
    }
    
    public enum AbsoluteUnit
    {
        DAY, 
        FRIDAY, 
        MONDAY, 
        MONTH, 
        NOW, 
        SATURDAY, 
        SUNDAY, 
        THURSDAY, 
        TUESDAY, 
        WEDNESDAY, 
        WEEK, 
        YEAR;
    }
    
    public enum Direction
    {
        LAST, 
        LAST_2, 
        NEXT, 
        NEXT_2, 
        PLAIN, 
        THIS;
    }
}
