package android.provider;

public static final class Photo implements DataColumnsWithJoins
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/photo";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String PHOTO = "data15";
    public static final String PHOTO_FILE_ID = "data14";
    
    Photo() {
        throw new RuntimeException("Stub!");
    }
}
