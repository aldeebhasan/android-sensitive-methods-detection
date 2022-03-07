package android.animation;

import android.view.*;

public interface TransitionListener
{
    void startTransition(final LayoutTransition p0, final ViewGroup p1, final View p2, final int p3);
    
    void endTransition(final LayoutTransition p0, final ViewGroup p1, final View p2, final int p3);
}
