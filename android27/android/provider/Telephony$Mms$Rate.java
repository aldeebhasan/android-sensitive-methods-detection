package android.provider;

import android.net.*;

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
