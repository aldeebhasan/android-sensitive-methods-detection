package android.widget;

import android.content.*;
import android.util.*;
import android.graphics.drawable.*;
import android.content.res.*;
import android.graphics.*;
import android.view.*;

public abstract class AbsSeekBar extends ProgressBar
{
    public AbsSeekBar(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public AbsSeekBar(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public AbsSeekBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public AbsSeekBar(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public void setThumb(final Drawable thumb) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getThumb() {
        throw new RuntimeException("Stub!");
    }
    
    public void setThumbTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getThumbTintList() {
        throw new RuntimeException("Stub!");
    }
    
    public void setThumbTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    public PorterDuff.Mode getThumbTintMode() {
        throw new RuntimeException("Stub!");
    }
    
    public int getThumbOffset() {
        throw new RuntimeException("Stub!");
    }
    
    public void setThumbOffset(final int thumbOffset) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSplitTrack(final boolean splitTrack) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getSplitTrack() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTickMark(final Drawable tickMark) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getTickMark() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTickMarkTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getTickMarkTintList() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTickMarkTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    public PorterDuff.Mode getTickMarkTintMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setKeyProgressIncrement(final int increment) {
        throw new RuntimeException("Stub!");
    }
    
    public int getKeyProgressIncrement() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void setMin(final int min) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void setMax(final int max) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean verifyDrawable(final Drawable who) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void jumpDrawablesToCurrentState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void drawableStateChanged() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void drawableHotspotChanged(final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected synchronized void onDraw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected synchronized void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRtlPropertiesChanged(final int layoutDirection) {
        throw new RuntimeException("Stub!");
    }
}
