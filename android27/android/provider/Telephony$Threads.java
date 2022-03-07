package android.provider;

import android.net.*;
import android.content.*;
import java.util.*;

public static final class Threads implements ThreadsColumns
{
    public static final int BROADCAST_THREAD = 1;
    public static final int COMMON_THREAD = 0;
    public static final Uri CONTENT_URI;
    public static final Uri OBSOLETE_THREADS_URI;
    
    Threads() {
        throw new RuntimeException("Stub!");
    }
    
    public static long getOrCreateThreadId(final Context context, final String recipient) {
        throw new RuntimeException("Stub!");
    }
    
    public static long getOrCreateThreadId(final Context context, final Set<String> recipients) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        OBSOLETE_THREADS_URI = null;
    }
}
