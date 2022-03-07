package android.provider;

import android.net.*;

public static final class MmsSms implements BaseColumns
{
    public static final Uri CONTENT_CONVERSATIONS_URI;
    public static final Uri CONTENT_DRAFT_URI;
    public static final Uri CONTENT_FILTER_BYPHONE_URI;
    public static final Uri CONTENT_LOCKED_URI;
    public static final Uri CONTENT_UNDELIVERED_URI;
    public static final Uri CONTENT_URI;
    public static final int ERR_TYPE_GENERIC = 1;
    public static final int ERR_TYPE_GENERIC_PERMANENT = 10;
    public static final int ERR_TYPE_MMS_PROTO_PERMANENT = 12;
    public static final int ERR_TYPE_MMS_PROTO_TRANSIENT = 3;
    public static final int ERR_TYPE_SMS_PROTO_PERMANENT = 11;
    public static final int ERR_TYPE_SMS_PROTO_TRANSIENT = 2;
    public static final int ERR_TYPE_TRANSPORT_FAILURE = 4;
    public static final int MMS_PROTO = 1;
    public static final int NO_ERROR = 0;
    public static final Uri SEARCH_URI;
    public static final int SMS_PROTO = 0;
    public static final String TYPE_DISCRIMINATOR_COLUMN = "transport_type";
    
    MmsSms() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_CONVERSATIONS_URI = null;
        CONTENT_DRAFT_URI = null;
        CONTENT_FILTER_BYPHONE_URI = null;
        CONTENT_LOCKED_URI = null;
        CONTENT_UNDELIVERED_URI = null;
        CONTENT_URI = null;
        SEARCH_URI = null;
    }
    
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
}
