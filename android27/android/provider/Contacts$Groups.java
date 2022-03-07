package android.provider;

import android.net.*;

@Deprecated
public static final class Groups implements BaseColumns, GroupsColumns
{
    @Deprecated
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contactsgroup";
    @Deprecated
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contactsgroup";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "name ASC";
    @Deprecated
    public static final Uri DELETED_CONTENT_URI;
    @Deprecated
    public static final String GROUP_ANDROID_STARRED = "Starred in Android";
    @Deprecated
    public static final String GROUP_MY_CONTACTS = "Contacts";
    
    Groups() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        DELETED_CONTENT_URI = null;
    }
}
