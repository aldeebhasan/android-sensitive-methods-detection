package android.webkit;

import android.graphics.*;

public abstract class WebHistoryItem implements Cloneable
{
    public WebHistoryItem() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract String getUrl();
    
    public abstract String getOriginalUrl();
    
    public abstract String getTitle();
    
    public abstract Bitmap getFavicon();
    
    @Override
    protected abstract WebHistoryItem clone();
}
