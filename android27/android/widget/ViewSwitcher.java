package android.widget;

import android.content.*;
import android.util.*;
import android.view.*;

public class ViewSwitcher extends ViewAnimator
{
    public ViewSwitcher(final Context context) {
        super(null, null);
        throw new RuntimeException("Stub!");
    }
    
    public ViewSwitcher(final Context context, final AttributeSet attrs) {
        super(null, null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void addView(final View child, final int index, final ViewGroup.LayoutParams params) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public View getNextView() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFactory(final ViewFactory factory) {
        throw new RuntimeException("Stub!");
    }
    
    public void reset() {
        throw new RuntimeException("Stub!");
    }
    
    public interface ViewFactory
    {
        View makeView();
    }
}
