package android.net;

import android.content.*;

public final class Proxy
{
    @Deprecated
    public static final String EXTRA_PROXY_INFO = "android.intent.extra.PROXY_INFO";
    public static final String PROXY_CHANGE_ACTION = "android.intent.action.PROXY_CHANGE";
    
    public Proxy() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final String getHost(final Context ctx) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final int getPort(final Context ctx) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final String getDefaultHost() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final int getDefaultPort() {
        throw new RuntimeException("Stub!");
    }
}
