package android.provider;

import android.net.*;
import android.database.*;
import android.content.*;

public static final class Groups implements BaseColumns, GroupsColumns, SyncColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group";
    public static final Uri CONTENT_SUMMARY_URI;
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/group";
    public static final Uri CONTENT_URI;
    
    Groups() {
        throw new RuntimeException("Stub!");
    }
    
    public static EntityIterator newEntityIterator(final Cursor cursor) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_SUMMARY_URI = null;
        CONTENT_URI = null;
    }
}
