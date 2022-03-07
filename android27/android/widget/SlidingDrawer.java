package android.widget;

import android.content.*;
import android.util.*;
import android.graphics.*;
import android.view.*;

@Deprecated
public class SlidingDrawer extends ViewGroup
{
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    
    public SlidingDrawer(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public SlidingDrawer(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public SlidingDrawer(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onFinishInflate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dispatchDraw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void toggle() {
        throw new RuntimeException("Stub!");
    }
    
    public void animateToggle() {
        throw new RuntimeException("Stub!");
    }
    
    public void open() {
        throw new RuntimeException("Stub!");
    }
    
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public void animateClose() {
        throw new RuntimeException("Stub!");
    }
    
    public void animateOpen() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnDrawerOpenListener(final OnDrawerOpenListener onDrawerOpenListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnDrawerCloseListener(final OnDrawerCloseListener onDrawerCloseListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnDrawerScrollListener(final OnDrawerScrollListener onDrawerScrollListener) {
        throw new RuntimeException("Stub!");
    }
    
    public View getHandle() {
        throw new RuntimeException("Stub!");
    }
    
    public View getContent() {
        throw new RuntimeException("Stub!");
    }
    
    public void unlock() {
        throw new RuntimeException("Stub!");
    }
    
    public void lock() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOpened() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isMoving() {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnDrawerScrollListener
    {
        void onScrollStarted();
        
        void onScrollEnded();
    }
    
    public interface OnDrawerCloseListener
    {
        void onDrawerClosed();
    }
    
    public interface OnDrawerOpenListener
    {
        void onDrawerOpened();
    }
}
