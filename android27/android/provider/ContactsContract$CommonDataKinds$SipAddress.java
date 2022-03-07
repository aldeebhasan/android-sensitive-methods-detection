package android.provider;

import android.content.res.*;

public static final class SipAddress implements DataColumnsWithJoins, CommonColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/sip_address";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String SIP_ADDRESS = "data1";
    public static final int TYPE_HOME = 1;
    public static final int TYPE_OTHER = 3;
    public static final int TYPE_WORK = 2;
    
    SipAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getTypeLabelResource(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
}
