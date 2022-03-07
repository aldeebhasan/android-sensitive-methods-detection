package android.provider;

import android.net.*;

public static final class Conversations implements BaseColumns, TextBasedSmsColumns
{
    public static final Uri CONTENT_URI;
    public static final String DEFAULT_SORT_ORDER = "date DESC";
    public static final String MESSAGE_COUNT = "msg_count";
    public static final String SNIPPET = "snippet";
    
    Conversations() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
