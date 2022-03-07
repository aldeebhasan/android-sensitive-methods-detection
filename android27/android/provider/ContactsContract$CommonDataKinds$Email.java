package android.provider;

import android.net.*;
import android.content.res.*;

public static final class Email implements DataColumnsWithJoins, CommonColumns
{
    public static final String ADDRESS = "data1";
    public static final Uri CONTENT_FILTER_URI;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/email_v2";
    public static final Uri CONTENT_LOOKUP_URI;
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/email_v2";
    public static final Uri CONTENT_URI;
    public static final String DISPLAY_NAME = "data4";
    public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
    public static final Uri ENTERPRISE_CONTENT_LOOKUP_URI;
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final int TYPE_HOME = 1;
    public static final int TYPE_MOBILE = 4;
    public static final int TYPE_OTHER = 3;
    public static final int TYPE_WORK = 2;
    
    Email() {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getTypeLabelResource(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_FILTER_URI = null;
        CONTENT_LOOKUP_URI = null;
        CONTENT_URI = null;
        ENTERPRISE_CONTENT_FILTER_URI = null;
        ENTERPRISE_CONTENT_LOOKUP_URI = null;
    }
}
