package android.provider;

import android.net.*;

@Deprecated
public static final class Extensions implements BaseColumns, ExtensionsColumns
{
    @Deprecated
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_extensions";
    @Deprecated
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_extensions";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "person, name ASC";
    @Deprecated
    public static final String PERSON_ID = "person";
    
    Extensions() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
