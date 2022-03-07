package android.provider;

import android.net.*;
import android.content.res.*;

public static final class StructuredPostal implements DataColumnsWithJoins, CommonColumns
{
    public static final String CITY = "data7";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/postal-address_v2";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/postal-address_v2";
    public static final Uri CONTENT_URI;
    public static final String COUNTRY = "data10";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String FORMATTED_ADDRESS = "data1";
    public static final String NEIGHBORHOOD = "data6";
    public static final String POBOX = "data5";
    public static final String POSTCODE = "data9";
    public static final String REGION = "data8";
    public static final String STREET = "data4";
    public static final int TYPE_HOME = 1;
    public static final int TYPE_OTHER = 3;
    public static final int TYPE_WORK = 2;
    
    StructuredPostal() {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getTypeLabelResource(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
