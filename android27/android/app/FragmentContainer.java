package android.app;

import android.view.*;

public abstract class FragmentContainer
{
    public FragmentContainer() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract <T extends View> T onFindViewById(final int p0);
    
    public abstract boolean onHasView();
}
