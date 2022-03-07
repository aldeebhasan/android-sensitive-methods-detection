package android.provider;

import android.net.*;

public static final class Callable implements DataColumnsWithJoins, CommonColumns
{
    public static final Uri CONTENT_FILTER_URI;
    public static final Uri CONTENT_URI;
    public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    
    public Callable() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_FILTER_URI = null;
        CONTENT_URI = null;
        ENTERPRISE_CONTENT_FILTER_URI = null;
    }
}
