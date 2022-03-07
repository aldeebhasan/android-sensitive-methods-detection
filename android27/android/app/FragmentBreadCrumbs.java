package android.app;

import android.content.*;
import android.util.*;
import android.view.*;

@Deprecated
public class FragmentBreadCrumbs extends ViewGroup implements FragmentManager.OnBackStackChangedListener
{
    public FragmentBreadCrumbs(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public FragmentBreadCrumbs(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public FragmentBreadCrumbs(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public void setActivity(final Activity a) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMaxVisible(final int visibleCrumbs) {
        throw new RuntimeException("Stub!");
    }
    
    public void setParentTitle(final CharSequence title, final CharSequence shortTitle, final OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnBreadCrumbClickListener(final OnBreadCrumbClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTitle(final CharSequence title, final CharSequence shortTitle) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onBackStackChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnBreadCrumbClickListener
    {
        boolean onBreadCrumbClick(final FragmentManager.BackStackEntry p0, final int p1);
    }
}
