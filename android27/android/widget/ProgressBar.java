package android.widget;

import android.content.*;
import android.util.*;
import android.view.*;
import android.graphics.drawable.*;
import android.content.res.*;
import android.view.animation.*;
import android.graphics.*;
import android.os.*;

@RemoteViews.RemoteView
public class ProgressBar extends View
{
    public ProgressBar(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public ProgressBar(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public ProgressBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public ProgressBar(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized boolean isIndeterminate() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void setIndeterminate(final boolean indeterminate) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getIndeterminateDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIndeterminateDrawable(final Drawable d) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIndeterminateTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getIndeterminateTintList() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIndeterminateTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    public PorterDuff.Mode getIndeterminateTintMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIndeterminateDrawableTiled(final Drawable d) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getProgressDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressDrawable(final Drawable d) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getProgressTintList() {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    public PorterDuff.Mode getProgressTintMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressBackgroundTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getProgressBackgroundTintList() {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressBackgroundTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    public PorterDuff.Mode getProgressBackgroundTintMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSecondaryProgressTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getSecondaryProgressTintList() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSecondaryProgressTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    public PorterDuff.Mode getSecondaryProgressTintMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressDrawableTiled(final Drawable d) {
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
    public void postInvalidate() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void setProgress(final int progress) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgress(final int progress, final boolean animate) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void setSecondaryProgress(final int secondaryProgress) {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized int getProgress() {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized int getSecondaryProgress() {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized int getMin() {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized int getMax() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void setMin(final int min) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void setMax(final int max) {
        throw new RuntimeException("Stub!");
    }
    
    public final synchronized void incrementProgressBy(final int diff) {
        throw new RuntimeException("Stub!");
    }
    
    public final synchronized void incrementSecondaryProgressBy(final int diff) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInterpolator(final Context context, final int resID) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInterpolator(final Interpolator interpolator) {
        throw new RuntimeException("Stub!");
    }
    
    public Interpolator getInterpolator() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onVisibilityAggregated(final boolean isVisible) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void invalidateDrawable(final Drawable dr) {
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
    protected void drawableStateChanged() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void drawableHotspotChanged(final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    public Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    public void onRestoreInstanceState(final Parcelable state) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAttachedToWindow() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onDetachedFromWindow() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAnimating() {
        throw new RuntimeException("Stub!");
    }
}
