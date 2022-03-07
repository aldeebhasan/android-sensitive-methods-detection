package android.provider;

import android.net.*;
import android.content.*;

public static final class Data implements DataColumnsWithJoins
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/data";
    public static final Uri CONTENT_URI;
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";
    
    Data() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getContactLookupUri(final ContentResolver resolver, final Uri dataUri) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
