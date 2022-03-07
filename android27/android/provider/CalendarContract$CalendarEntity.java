package android.provider;

import android.net.*;
import android.database.*;
import android.content.*;

public static final class CalendarEntity implements BaseColumns, SyncColumns, CalendarColumns
{
    public static final Uri CONTENT_URI;
    
    CalendarEntity() {
        throw new RuntimeException("Stub!");
    }
    
    public static EntityIterator newEntityIterator(final Cursor cursor) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
