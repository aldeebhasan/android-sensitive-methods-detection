package android.provider;

import android.net.*;

public static final class CalendarCache implements CalendarCacheColumns
{
    public static final String KEY_TIMEZONE_INSTANCES = "timezoneInstances";
    public static final String KEY_TIMEZONE_INSTANCES_PREVIOUS = "timezoneInstancesPrevious";
    public static final String KEY_TIMEZONE_TYPE = "timezoneType";
    public static final String TIMEZONE_TYPE_AUTO = "auto";
    public static final String TIMEZONE_TYPE_HOME = "home";
    public static final Uri URI;
    
    CalendarCache() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        URI = null;
    }
}
