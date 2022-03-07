package android.provider;

import android.net.*;

public static final class AggregationExceptions implements BaseColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/aggregation_exception";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/aggregation_exception";
    public static final Uri CONTENT_URI;
    public static final String RAW_CONTACT_ID1 = "raw_contact_id1";
    public static final String RAW_CONTACT_ID2 = "raw_contact_id2";
    public static final String TYPE = "type";
    public static final int TYPE_AUTOMATIC = 0;
    public static final int TYPE_KEEP_SEPARATE = 2;
    public static final int TYPE_KEEP_TOGETHER = 1;
    
    AggregationExceptions() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
