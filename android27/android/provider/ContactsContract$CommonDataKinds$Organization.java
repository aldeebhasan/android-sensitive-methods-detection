package android.provider;

import android.content.res.*;

public static final class Organization implements DataColumnsWithJoins, CommonColumns
{
    public static final String COMPANY = "data1";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/organization";
    public static final String DEPARTMENT = "data5";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String JOB_DESCRIPTION = "data6";
    public static final String OFFICE_LOCATION = "data9";
    public static final String PHONETIC_NAME = "data8";
    public static final String PHONETIC_NAME_STYLE = "data10";
    public static final String SYMBOL = "data7";
    public static final String TITLE = "data4";
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_WORK = 1;
    
    Organization() {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getTypeLabelResource(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
}
