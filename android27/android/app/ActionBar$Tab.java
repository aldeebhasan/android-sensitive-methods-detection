package android.app;

import android.graphics.drawable.*;
import android.view.*;

@Deprecated
public abstract static class Tab
{
    public static final int INVALID_POSITION = -1;
    
    public Tab() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getPosition();
    
    public abstract Drawable getIcon();
    
    public abstract CharSequence getText();
    
    public abstract Tab setIcon(final Drawable p0);
    
    public abstract Tab setIcon(final int p0);
    
    public abstract Tab setText(final CharSequence p0);
    
    public abstract Tab setText(final int p0);
    
    public abstract Tab setCustomView(final View p0);
    
    public abstract Tab setCustomView(final int p0);
    
    public abstract View getCustomView();
    
    public abstract Tab setTag(final Object p0);
    
    public abstract Object getTag();
    
    public abstract Tab setTabListener(final TabListener p0);
    
    public abstract void select();
    
    public abstract Tab setContentDescription(final int p0);
    
    public abstract Tab setContentDescription(final CharSequence p0);
    
    public abstract CharSequence getContentDescription();
}
