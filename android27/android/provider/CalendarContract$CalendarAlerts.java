package android.provider;

import android.net.*;

public static final class CalendarAlerts implements BaseColumns, CalendarAlertsColumns, EventsColumns, CalendarColumns
{
    public static final Uri CONTENT_URI;
    public static final Uri CONTENT_URI_BY_INSTANCE;
    
    CalendarAlerts() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        CONTENT_URI_BY_INSTANCE = null;
    }
}
