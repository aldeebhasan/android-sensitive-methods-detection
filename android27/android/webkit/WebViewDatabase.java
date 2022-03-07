package android.webkit;

import android.content.*;

public abstract class WebViewDatabase
{
    public WebViewDatabase() {
        throw new RuntimeException("Stub!");
    }
    
    public static WebViewDatabase getInstance(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public abstract boolean hasUsernamePassword();
    
    @Deprecated
    public abstract void clearUsernamePassword();
    
    public abstract boolean hasHttpAuthUsernamePassword();
    
    public abstract void clearHttpAuthUsernamePassword();
    
    public abstract void setHttpAuthUsernamePassword(final String p0, final String p1, final String p2, final String p3);
    
    public abstract String[] getHttpAuthUsernamePassword(final String p0, final String p1);
    
    @Deprecated
    public abstract boolean hasFormData();
    
    @Deprecated
    public abstract void clearFormData();
}
