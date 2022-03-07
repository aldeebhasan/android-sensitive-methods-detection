package android.graphics.drawable;

import android.content.res.*;

public abstract static class ConstantState
{
    public ConstantState() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Drawable newDrawable();
    
    public Drawable newDrawable(final Resources res) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable newDrawable(final Resources res, final Resources.Theme theme) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getChangingConfigurations();
    
    public boolean canApplyTheme() {
        throw new RuntimeException("Stub!");
    }
}
