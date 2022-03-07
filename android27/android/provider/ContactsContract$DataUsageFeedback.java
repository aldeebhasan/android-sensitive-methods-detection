package android.provider;

import android.net.*;

public static final class DataUsageFeedback
{
    public static final Uri DELETE_USAGE_URI;
    public static final Uri FEEDBACK_URI;
    public static final String USAGE_TYPE = "type";
    public static final String USAGE_TYPE_CALL = "call";
    public static final String USAGE_TYPE_LONG_TEXT = "long_text";
    public static final String USAGE_TYPE_SHORT_TEXT = "short_text";
    
    public DataUsageFeedback() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        DELETE_USAGE_URI = null;
        FEEDBACK_URI = null;
    }
}
