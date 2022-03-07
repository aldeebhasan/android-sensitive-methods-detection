package android.provider;

import android.net.*;

public static final class Mms implements BaseMmsColumns
{
    public static final Uri CONTENT_URI;
    public static final String DEFAULT_SORT_ORDER = "date DESC";
    public static final Uri REPORT_REQUEST_URI;
    public static final Uri REPORT_STATUS_URI;
    
    Mms() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        REPORT_REQUEST_URI = null;
        REPORT_STATUS_URI = null;
    }
    
    public static final class Inbox implements BaseMmsColumns
    {
        public static final Uri CONTENT_URI;
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        
        Inbox() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_URI = null;
        }
    }
    
    public static final class Sent implements BaseMmsColumns
    {
        public static final Uri CONTENT_URI;
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        
        Sent() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_URI = null;
        }
    }
    
    public static final class Draft implements BaseMmsColumns
    {
        public static final Uri CONTENT_URI;
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        
        Draft() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_URI = null;
        }
    }
    
    public static final class Outbox implements BaseMmsColumns
    {
        public static final Uri CONTENT_URI;
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        
        Outbox() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_URI = null;
        }
    }
    
    public static final class Addr implements BaseColumns
    {
        public static final String ADDRESS = "address";
        public static final String CHARSET = "charset";
        public static final String CONTACT_ID = "contact_id";
        public static final String MSG_ID = "msg_id";
        public static final String TYPE = "type";
        
        Addr() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Part implements BaseColumns
    {
        public static final String CHARSET = "chset";
        public static final String CONTENT_DISPOSITION = "cd";
        public static final String CONTENT_ID = "cid";
        public static final String CONTENT_LOCATION = "cl";
        public static final String CONTENT_TYPE = "ct";
        public static final String CT_START = "ctt_s";
        public static final String CT_TYPE = "ctt_t";
        public static final String FILENAME = "fn";
        public static final String MSG_ID = "mid";
        public static final String NAME = "name";
        public static final String SEQ = "seq";
        public static final String TEXT = "text";
        public static final String _DATA = "_data";
        
        Part() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Rate
    {
        public static final Uri CONTENT_URI;
        public static final String SENT_TIME = "sent_time";
        
        Rate() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_URI = null;
        }
    }
    
    public static final class Intents
    {
        public static final String CONTENT_CHANGED_ACTION = "android.intent.action.CONTENT_CHANGED";
        public static final String DELETED_CONTENTS = "deleted_contents";
        
        Intents() {
            throw new RuntimeException("Stub!");
        }
    }
}
