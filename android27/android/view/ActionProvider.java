package android.view;

import android.content.*;

public abstract class ActionProvider
{
    public ActionProvider(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public abstract View onCreateActionView();
    
    public View onCreateActionView(final MenuItem forItem) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean overridesItemVisibility() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVisible() {
        throw new RuntimeException("Stub!");
    }
    
    public void refreshVisibility() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onPerformDefaultAction() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasSubMenu() {
        throw new RuntimeException("Stub!");
    }
    
    public void onPrepareSubMenu(final SubMenu subMenu) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVisibilityListener(final VisibilityListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public interface VisibilityListener
    {
        void onActionProviderVisibilityChanged(final boolean p0);
    }
}
