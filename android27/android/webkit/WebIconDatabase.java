package android.webkit;

import android.graphics.*;

@Deprecated
public abstract class WebIconDatabase
{
    public WebIconDatabase() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void open(final String p0);
    
    public abstract void close();
    
    public abstract void removeAllIcons();
    
    public abstract void requestIconForPageUrl(final String p0, final IconListener p1);
    
    public abstract void retainIconForPageUrl(final String p0);
    
    public abstract void releaseIconForPageUrl(final String p0);
    
    public static WebIconDatabase getInstance() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public interface IconListener
    {
        void onReceivedIcon(final String p0, final Bitmap p1);
    }
}
