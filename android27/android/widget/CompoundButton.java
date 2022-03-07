package android.widget;

import android.content.*;
import android.util.*;
import android.graphics.drawable.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.autofill.*;

public abstract class CompoundButton extends Button implements Checkable
{
    public CompoundButton(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public CompoundButton(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public CompoundButton(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public CompoundButton(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void toggle() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean performClick() {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty
    @Override
    public boolean isChecked() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setChecked(final boolean checked) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnCheckedChangeListener(final OnCheckedChangeListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setButtonDrawable(final int resId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setButtonDrawable(final Drawable drawable) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getButtonDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setButtonTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getButtonTintList() {
        throw new RuntimeException("Stub!");
    }
    
    public void setButtonTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    public PorterDuff.Mode getButtonTintMode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getCompoundPaddingLeft() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getCompoundPaddingRight() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onDraw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int[] onCreateDrawableState(final int extraSpace) {
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
    protected boolean verifyDrawable(final Drawable who) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void jumpDrawablesToCurrentState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRestoreInstanceState(final Parcelable state) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onProvideAutofillStructure(final ViewStructure structure, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void autofill(final AutofillValue value) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getAutofillType() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public AutofillValue getAutofillValue() {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnCheckedChangeListener
    {
        void onCheckedChanged(final CompoundButton p0, final boolean p1);
    }
}
