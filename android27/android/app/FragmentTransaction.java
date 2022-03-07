package android.app;

import android.view.*;

public abstract class FragmentTransaction
{
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;
    
    public FragmentTransaction() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract FragmentTransaction add(final Fragment p0, final String p1);
    
    public abstract FragmentTransaction add(final int p0, final Fragment p1);
    
    public abstract FragmentTransaction add(final int p0, final Fragment p1, final String p2);
    
    public abstract FragmentTransaction replace(final int p0, final Fragment p1);
    
    public abstract FragmentTransaction replace(final int p0, final Fragment p1, final String p2);
    
    public abstract FragmentTransaction remove(final Fragment p0);
    
    public abstract FragmentTransaction hide(final Fragment p0);
    
    public abstract FragmentTransaction show(final Fragment p0);
    
    public abstract FragmentTransaction detach(final Fragment p0);
    
    public abstract FragmentTransaction attach(final Fragment p0);
    
    public abstract FragmentTransaction setPrimaryNavigationFragment(final Fragment p0);
    
    public abstract boolean isEmpty();
    
    public abstract FragmentTransaction setCustomAnimations(final int p0, final int p1);
    
    public abstract FragmentTransaction setCustomAnimations(final int p0, final int p1, final int p2, final int p3);
    
    public abstract FragmentTransaction setTransition(final int p0);
    
    public abstract FragmentTransaction addSharedElement(final View p0, final String p1);
    
    public abstract FragmentTransaction setTransitionStyle(final int p0);
    
    public abstract FragmentTransaction addToBackStack(final String p0);
    
    public abstract boolean isAddToBackStackAllowed();
    
    public abstract FragmentTransaction disallowAddToBackStack();
    
    public abstract FragmentTransaction setBreadCrumbTitle(final int p0);
    
    public abstract FragmentTransaction setBreadCrumbTitle(final CharSequence p0);
    
    public abstract FragmentTransaction setBreadCrumbShortTitle(final int p0);
    
    public abstract FragmentTransaction setBreadCrumbShortTitle(final CharSequence p0);
    
    public abstract FragmentTransaction setReorderingAllowed(final boolean p0);
    
    public abstract FragmentTransaction runOnCommit(final Runnable p0);
    
    public abstract int commit();
    
    public abstract int commitAllowingStateLoss();
    
    public abstract void commitNow();
    
    public abstract void commitNowAllowingStateLoss();
}
