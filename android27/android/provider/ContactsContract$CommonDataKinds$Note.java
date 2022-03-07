package android.provider;

public static final class Note implements DataColumnsWithJoins
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/note";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String NOTE = "data1";
    
    Note() {
        throw new RuntimeException("Stub!");
    }
}
