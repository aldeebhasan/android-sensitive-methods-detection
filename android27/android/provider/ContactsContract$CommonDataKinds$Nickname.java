package android.provider;

public static final class Nickname implements DataColumnsWithJoins, CommonColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/nickname";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String NAME = "data1";
    public static final int TYPE_DEFAULT = 1;
    public static final int TYPE_INITIALS = 5;
    public static final int TYPE_MAIDEN_NAME = 3;
    @Deprecated
    public static final int TYPE_MAINDEN_NAME = 3;
    public static final int TYPE_OTHER_NAME = 2;
    public static final int TYPE_SHORT_NAME = 4;
    
    Nickname() {
        throw new RuntimeException("Stub!");
    }
}
