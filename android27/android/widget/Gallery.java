package android.widget;

import android.content.*;
import android.util.*;
import android.view.animation.*;
import android.view.*;
import android.graphics.*;

@Deprecated
public class Gallery extends AbsSpinner implements GestureDetector.OnGestureListener
{
    public Gallery(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public Gallery(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public Gallery(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public Gallery(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAttachedToWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCallbackDuringFling(final boolean shouldCallback) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAnimationDuration(final int animationDurationMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSpacing(final int spacing) {
        throw new RuntimeException("Stub!");
    }
    
    public void setUnselectedAlpha(final float unselectedAlpha) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean getChildStaticTransformation(final View child, final Transformation t) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeHorizontalScrollExtent() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeHorizontalScrollOffset() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeHorizontalScrollRange() {
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
    public ViewGroup.LayoutParams generateLayoutParams(final AttributeSet attrs) {
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
    public boolean onTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onSingleTapUp(final MotionEvent e) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onDown(final MotionEvent e) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onLongPress(final MotionEvent e) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onShowPress(final MotionEvent e) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dispatchSetSelected(final boolean selected) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dispatchSetPressed(final boolean pressed) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean showContextMenuForChild(final View originalView) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean showContextMenuForChild(final View originalView, final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean showContextMenu() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean showContextMenu(final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void setGravity(final int gravity) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int getChildDrawingOrder(final int childCount, final int i) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onFocusChanged(final boolean gainFocus, final int direction, final Rect previouslyFocusedRect) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public static class LayoutParams extends ViewGroup.LayoutParams
    {
        public LayoutParams(final Context c, final AttributeSet attrs) {
            super(null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final int w, final int h) {
            super(null);
            throw new RuntimeException("Stub!");
        }
        
        public LayoutParams(final ViewGroup.LayoutParams source) {
            super(null);
            throw new RuntimeException("Stub!");
        }
    }
}
