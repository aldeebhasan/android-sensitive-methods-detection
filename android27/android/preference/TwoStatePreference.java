package android.preference;

import android.content.*;
import android.util.*;
import android.content.res.*;
import android.os.*;

public abstract class TwoStatePreference extends Preference
{
    public TwoStatePreference(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public TwoStatePreference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public TwoStatePreference(final Context context, final AttributeSet attrs) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public TwoStatePreference(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onClick() {
        throw new RuntimeException("Stub!");
    }
    
    public void setChecked(final boolean checked) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isChecked() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean shouldDisableDependents() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSummaryOn(final CharSequence summary) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSummaryOn(final int summaryResId) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getSummaryOn() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSummaryOff(final CharSequence summary) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSummaryOff(final int summaryResId) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getSummaryOff() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getDisableDependentsState() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDisableDependentsState(final boolean disableDependentsState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Object onGetDefaultValue(final TypedArray a, final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onSetInitialValue(final boolean restoreValue, final Object defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        throw new RuntimeException("Stub!");
    }
}
