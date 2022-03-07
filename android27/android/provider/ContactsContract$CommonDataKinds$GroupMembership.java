package android.provider;

public static final class GroupMembership implements DataColumnsWithJoins
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group_membership";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String GROUP_ROW_ID = "data1";
    public static final String GROUP_SOURCE_ID = "group_sourceid";
    
    GroupMembership() {
        throw new RuntimeException("Stub!");
    }
}
