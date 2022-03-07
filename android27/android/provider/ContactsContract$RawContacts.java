package android.provider;

import android.net.*;
import android.database.*;
import android.content.*;

public static final class RawContacts implements BaseColumns, RawContactsColumns, ContactOptionsColumns, ContactNameColumns, SyncColumns
{
    public static final int AGGREGATION_MODE_DEFAULT = 0;
    public static final int AGGREGATION_MODE_DISABLED = 3;
    @Deprecated
    public static final int AGGREGATION_MODE_IMMEDIATE = 1;
    public static final int AGGREGATION_MODE_SUSPENDED = 2;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/raw_contact";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/raw_contact";
    public static final Uri CONTENT_URI;
    
    RawContacts() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContactLookupUri(final ContentResolver resolver, final Uri rawContactUri) {
        throw new RuntimeException("Stub!");
    }
    
    public static EntityIterator newEntityIterator(final Cursor cursor) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
    
    public static final class Data implements BaseColumns, DataColumns
    {
        public static final String CONTENT_DIRECTORY = "data";
        
        Data() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Entity implements BaseColumns, DataColumns
    {
        public static final String CONTENT_DIRECTORY = "entity";
        public static final String DATA_ID = "data_id";
        
        Entity() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class DisplayPhoto
    {
        public static final String CONTENT_DIRECTORY = "display_photo";
        
        DisplayPhoto() {
            throw new RuntimeException("Stub!");
        }
    }
}
