package android.provider;

import android.net.*;
import android.database.*;
import android.content.*;

public static final class EventsEntity implements BaseColumns, SyncColumns, EventsColumns
{
    public static final Uri CONTENT_URI;
    
    EventsEntity() {
        throw new RuntimeException("Stub!");
    }
    
    public static EntityIterator newEntityIterator(final Cursor cursor, final ContentResolver resolver) {
        throw new RuntimeException("Stub!");
    }
    
    public static EntityIterator newEntityIterator(final Cursor cursor, final ContentProviderClient provider) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
