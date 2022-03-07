package android.provider;

import android.net.*;

public static class BlockedNumbers
{
    public static final String COLUMN_E164_NUMBER = "e164_number";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ORIGINAL_NUMBER = "original_number";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/blocked_number";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/blocked_number";
    public static final Uri CONTENT_URI;
    
    BlockedNumbers() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
