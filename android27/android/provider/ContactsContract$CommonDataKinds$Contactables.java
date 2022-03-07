package android.provider;

import android.net.*;

public static final class Contactables implements DataColumnsWithJoins, CommonColumns
{
    public static final Uri CONTENT_FILTER_URI;
    public static final Uri CONTENT_URI;
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";
    
    public Contactables() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_FILTER_URI = null;
        CONTENT_URI = null;
    }
}
