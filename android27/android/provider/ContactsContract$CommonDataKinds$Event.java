package android.provider;

import android.content.res.*;

public static final class Event implements DataColumnsWithJoins, CommonColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_event";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String START_DATE = "data1";
    public static final int TYPE_ANNIVERSARY = 1;
    public static final int TYPE_BIRTHDAY = 3;
    public static final int TYPE_OTHER = 2;
    
    Event() {
        throw new RuntimeException("Stub!");
    }
    
    public static int getTypeResource(final Integer type) {
        throw new RuntimeException("Stub!");
    }
    
    public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
}
