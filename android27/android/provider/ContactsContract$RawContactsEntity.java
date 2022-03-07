package android.provider;

import android.net.*;

public static final class RawContactsEntity implements BaseColumns, DataColumns, RawContactsColumns
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/raw_contact_entity";
    public static final Uri CONTENT_URI;
    public static final String DATA_ID = "data_id";
    public static final Uri PROFILE_CONTENT_URI;
    
    RawContactsEntity() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        PROFILE_CONTENT_URI = null;
    }
}
