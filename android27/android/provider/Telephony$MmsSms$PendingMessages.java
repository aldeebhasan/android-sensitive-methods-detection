package android.provider;

import android.net.*;

public static final class PendingMessages implements BaseColumns
{
    public static final Uri CONTENT_URI;
    public static final String DUE_TIME = "due_time";
    public static final String ERROR_CODE = "err_code";
    public static final String ERROR_TYPE = "err_type";
    public static final String LAST_TRY = "last_try";
    public static final String MSG_ID = "msg_id";
    public static final String MSG_TYPE = "msg_type";
    public static final String PROTO_TYPE = "proto_type";
    public static final String RETRY_INDEX = "retry_index";
    public static final String SUBSCRIPTION_ID = "pending_sub_id";
    
    PendingMessages() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
