package android.provider;

public static final class Website implements DataColumnsWithJoins, CommonColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/website";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final int TYPE_BLOG = 2;
    public static final int TYPE_FTP = 6;
    public static final int TYPE_HOME = 4;
    public static final int TYPE_HOMEPAGE = 1;
    public static final int TYPE_OTHER = 7;
    public static final int TYPE_PROFILE = 3;
    public static final int TYPE_WORK = 5;
    public static final String URL = "data1";
    
    Website() {
        throw new RuntimeException("Stub!");
    }
}
