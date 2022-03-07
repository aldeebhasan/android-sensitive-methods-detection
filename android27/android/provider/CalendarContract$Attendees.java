package android.provider;

import android.net.*;
import android.content.*;
import android.database.*;

public static final class Attendees implements BaseColumns, AttendeesColumns, EventsColumns
{
    public static final Uri CONTENT_URI;
    
    Attendees() {
        throw new RuntimeException("Stub!");
    }
    
    public static final Cursor query(final ContentResolver cr, final long eventId, final String[] projection) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
