package android.provider;

import android.net.*;
import android.content.*;
import android.database.*;

public static final class Instances implements BaseColumns, EventsColumns, CalendarColumns
{
    public static final String BEGIN = "begin";
    public static final Uri CONTENT_BY_DAY_URI;
    public static final Uri CONTENT_SEARCH_BY_DAY_URI;
    public static final Uri CONTENT_SEARCH_URI;
    public static final Uri CONTENT_URI;
    public static final String END = "end";
    public static final String END_DAY = "endDay";
    public static final String END_MINUTE = "endMinute";
    public static final String EVENT_ID = "event_id";
    public static final String START_DAY = "startDay";
    public static final String START_MINUTE = "startMinute";
    
    Instances() {
        throw new RuntimeException("Stub!");
    }
    
    public static final Cursor query(final ContentResolver cr, final String[] projection, final long begin, final long end) {
        throw new RuntimeException("Stub!");
    }
    
    public static final Cursor query(final ContentResolver cr, final String[] projection, final long begin, final long end, final String searchQuery) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_BY_DAY_URI = null;
        CONTENT_SEARCH_BY_DAY_URI = null;
        CONTENT_SEARCH_URI = null;
        CONTENT_URI = null;
    }
}
