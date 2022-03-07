package android.provider;

import android.net.*;

public static final class Events implements BaseColumns, SyncColumns, EventsColumns, CalendarColumns
{
    public static final Uri CONTENT_EXCEPTION_URI;
    public static final Uri CONTENT_URI;
    
    Events() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_EXCEPTION_URI = null;
        CONTENT_URI = null;
    }
}
