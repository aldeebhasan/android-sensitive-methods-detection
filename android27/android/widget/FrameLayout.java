package android.widget;

import android.view.*;
import android.content.*;
import android.util.*;

@RemoteViews.RemoteView
public class FrameLayout extends ViewGroup
{
    public FrameLayout(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public FrameLayout(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public FrameLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public FrameLayout(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setForegroundGravity(final int foregroundGravity) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMeasureAllChildren(final boolean measureAll) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean getConsiderGoneChildrenWhenMeasuring() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getMeasureAllChildren() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public LayoutParams generateLayoutParams(final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean shouldDelayChildPressedState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean checkLayoutParams(final ViewGroup.LayoutParams p) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(final ViewGroup.LayoutParams lp) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public static class LayoutParams extends MarginLayoutParams
    {
        public static final int UNSPECIFIED_GRAVITY = -1;
        public int gravity;
        
        public LayoutParams(final Context c, final AttributeSet attrs) {
            super((ViewGroup.LayoutParams)null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final int width, final int height) {
            super((ViewGroup.LayoutParams)null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final int width, final int height, final int gravity) {
            super((ViewGroup.LayoutParams)null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final ViewGroup.LayoutParams source) {
            super((ViewGroup.LayoutParams)null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final MarginLayoutParams source) {
            super((ViewGroup.LayoutParams)null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final LayoutParams source) {
            super((ViewGroup.LayoutParams)null);
            throw new RuntimeException("Stub!");
        }
    }
}
