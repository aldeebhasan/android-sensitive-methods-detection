package android.provider;

import android.net.*;
import android.content.res.*;

public static final class Phone implements DataColumnsWithJoins, CommonColumns
{
    public static final Uri CONTENT_FILTER_URI;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone_v2";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone_v2";
    public static final Uri CONTENT_URI;
    public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String NORMALIZED_NUMBER = "data4";
    public static final String NUMBER = "data1";
    public static final String SEARCH_DISPLAY_NAME_KEY = "search_display_name";
    public static final String SEARCH_PHONE_NUMBER_KEY = "search_phone_number";
    public static final int TYPE_ASSISTANT = 19;
    public static final int TYPE_CALLBACK = 8;
    public static final int TYPE_CAR = 9;
    public static final int TYPE_COMPANY_MAIN = 10;
    public static final int TYPE_FAX_HOME = 5;
    public static final int TYPE_FAX_WORK = 4;
    public static final int TYPE_HOME = 1;
    public static final int TYPE_ISDN = 11;
    public static final int TYPE_MAIN = 12;
    public static final int TYPE_MMS = 20;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_OTHER = 7;
    public static final int TYPE_OTHER_FAX = 13;
    public static final int TYPE_PAGER = 6;
    public static final int TYPE_RADIO = 14;
    public static final int TYPE_TELEX = 15;
    public static final int TYPE_TTY_TDD = 16;
    public static final int TYPE_WORK = 3;
    public static final int TYPE_WORK_MOBILE = 17;
    public static final int TYPE_WORK_PAGER = 18;
    
    Phone() {
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
        CONTENT_URI = null;
        ENTERPRISE_CONTENT_FILTER_URI = null;
    }
}
