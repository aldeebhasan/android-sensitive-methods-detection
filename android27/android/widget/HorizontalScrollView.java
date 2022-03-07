package android.widget;

import android.content.*;
import android.util.*;
import android.view.*;
import android.graphics.*;
import android.os.*;

public class HorizontalScrollView extends FrameLayout
{
    public HorizontalScrollView(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public HorizontalScrollView(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public HorizontalScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public HorizontalScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected float getLeftFadingEdgeStrength() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected float getRightFadingEdgeStrength() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxScrollAmount() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void addView(final View child) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void addView(final View child, final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void addView(final View child, final ViewGroup.LayoutParams params) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void addView(final View child, final int index, final ViewGroup.LayoutParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFillViewport() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFillViewport(final boolean fillViewport) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSmoothScrollingEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSmoothScrollingEnabled(final boolean smoothScrollingEnabled) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean executeKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void requestDisallowInterceptTouchEvent(final boolean disallowIntercept) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onGenericMotionEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean shouldDelayChildPressedState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onOverScrolled(final int scrollX, final int scrollY, final boolean clampedX, final boolean clampedY) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean pageScroll(final int direction) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean fullScroll(final int direction) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean arrowScroll(final int direction) {
        throw new RuntimeException("Stub!");
    }
    
    public final void smoothScrollBy(final int dx, final int dy) {
        throw new RuntimeException("Stub!");
    }
    
    public final void smoothScrollTo(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeHorizontalScrollRange() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeHorizontalScrollOffset() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void measureChild(final View child, final int parentWidthMeasureSpec, final int parentHeightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void measureChildWithMargins(final View child, final int parentWidthMeasureSpec, final int widthUsed, final int parentHeightMeasureSpec, final int heightUsed) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void computeScroll() {
        throw new RuntimeException("Stub!");
    }
    
    protected int computeScrollDeltaToGetChildRectOnScreen(final Rect rect) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void requestChildFocus(final View child, final View focused) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean onRequestFocusInDescendants(final int direction, final Rect previouslyFocusedRect) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean requestChildRectangleOnScreen(final View child, final Rect rectangle, final boolean immediate) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void requestLayout() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        throw new RuntimeException("Stub!");
    }
    
    public void fling(final int velocityX) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void scrollTo(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setOverScrollMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void draw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
}
