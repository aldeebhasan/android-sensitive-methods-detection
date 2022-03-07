package android.provider;

import android.content.res.*;

public static final class Relation implements DataColumnsWithJoins, CommonColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/relation";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String NAME = "data1";
    public static final int TYPE_ASSISTANT = 1;
    public static final int TYPE_BROTHER = 2;
    public static final int TYPE_CHILD = 3;
    public static final int TYPE_DOMESTIC_PARTNER = 4;
    public static final int TYPE_FATHER = 5;
    public static final int TYPE_FRIEND = 6;
    public static final int TYPE_MANAGER = 7;
    public static final int TYPE_MOTHER = 8;
    public static final int TYPE_PARENT = 9;
    public static final int TYPE_PARTNER = 10;
    public static final int TYPE_REFERRED_BY = 11;
    public static final int TYPE_RELATIVE = 12;
    public static final int TYPE_SISTER = 13;
    public static final int TYPE_SPOUSE = 14;
    
    Relation() {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getTypeLabelResource(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
}
