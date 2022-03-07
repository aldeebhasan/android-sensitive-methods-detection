package android.widget;

import android.view.*;
import android.content.*;
import android.util.*;

@Deprecated
@RemoteViews.RemoteView
public class AbsoluteLayout extends ViewGroup
{
    public AbsoluteLayout(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public AbsoluteLayout(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public AbsoluteLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public AbsoluteLayout(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean checkLayoutParams(final ViewGroup.LayoutParams p) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(final ViewGroup.LayoutParams p) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean shouldDelayChildPressedState() {
        throw new RuntimeException("Stub!");
    }
    
    public static class LayoutParams extends ViewGroup.LayoutParams
    {
        public int x;
        public int y;
        
        public LayoutParams(final int width, final int height, final int x, final int y) {
            super(null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final Context c, final AttributeSet attrs) {
            super(null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final ViewGroup.LayoutParams source) {
            super(null);
            throw new RuntimeException("Stub!");
        }
        
        public String debug(final String output) {
            throw new RuntimeException("Stub!");
        }
    }
}
