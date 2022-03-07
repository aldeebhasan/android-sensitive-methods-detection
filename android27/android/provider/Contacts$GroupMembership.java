package android.provider;

import android.net.*;

@Deprecated
public static final class GroupMembership implements BaseColumns, GroupsColumns
{
    @Deprecated
    public static final String CONTENT_DIRECTORY = "groupmembership";
    @Deprecated
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contactsgroupmembership";
    @Deprecated
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contactsgroupmembership";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "group_id ASC";
    @Deprecated
    public static final String GROUP_ID = "group_id";
    @Deprecated
    public static final String GROUP_SYNC_ACCOUNT = "group_sync_account";
    @Deprecated
    public static final String GROUP_SYNC_ACCOUNT_TYPE = "group_sync_account_type";
    @Deprecated
    public static final String GROUP_SYNC_ID = "group_sync_id";
    @Deprecated
    public static final String PERSON_ID = "person";
    @Deprecated
    public static final Uri RAW_CONTENT_URI;
    
    GroupMembership() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        RAW_CONTENT_URI = null;
    }
}
