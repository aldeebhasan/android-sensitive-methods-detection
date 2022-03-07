package android.app;

@Deprecated
public interface TabListener
{
    void onTabSelected(final Tab p0, final FragmentTransaction p1);
    
    void onTabUnselected(final Tab p0, final FragmentTransaction p1);
    
    void onTabReselected(final Tab p0, final FragmentTransaction p1);
}
