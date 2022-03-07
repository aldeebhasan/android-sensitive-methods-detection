package android.provider;

import android.net.*;

public static final class Calendars implements BaseColumns, SyncColumns, CalendarColumns
{
    public static final String CALENDAR_LOCATION = "calendar_location";
    public static final Uri CONTENT_URI;
    public static final String DEFAULT_SORT_ORDER = "calendar_displayName";
    public static final String NAME = "name";
    
    Calendars() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
