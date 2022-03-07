package android.app;

public interface BackStackEntry
{
    int getId();
    
    String getName();
    
    int getBreadCrumbTitleRes();
    
    int getBreadCrumbShortTitleRes();
    
    CharSequence getBreadCrumbTitle();
    
    CharSequence getBreadCrumbShortTitle();
}
