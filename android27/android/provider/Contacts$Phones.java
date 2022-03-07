package android.provider;

import android.net.*;
import android.content.*;

@Deprecated
public static final class Phones implements BaseColumns, PhonesColumns, PeopleColumns
{
    @Deprecated
    public static final Uri CONTENT_FILTER_URL;
    @Deprecated
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone";
    @Deprecated
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "name ASC";
    @Deprecated
    public static final String PERSON_ID = "person";
    
    Phones() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final CharSequence getDisplayLabel(final Context context, final int type, final CharSequence label, final CharSequence[] labelArray) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final CharSequence getDisplayLabel(final Context context, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_FILTER_URL = null;
        CONTENT_URI = null;
    }
}
