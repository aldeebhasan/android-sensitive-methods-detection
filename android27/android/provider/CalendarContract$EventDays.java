package android.provider;

import android.net.*;
import android.content.*;
import android.database.*;

public static final class EventDays implements EventDaysColumns
{
    public static final Uri CONTENT_URI;
    
    EventDays() {
        throw new RuntimeException("Stub!");
    }
    
    public static final Cursor query(final ContentResolver cr, final int startDay, final int numDays, final String[] projection) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
