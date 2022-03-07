package android.preference;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.content.res.*;
import android.os.*;

public class EditTextPreference extends DialogPreference
{
    public EditTextPreference(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public EditTextPreference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public EditTextPreference(final Context context, final AttributeSet attrs) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public EditTextPreference(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void setText(final String text) {
        throw new RuntimeException("Stub!");
    }
    
    public String getText() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onBindDialogView(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onAddEditTextToDialogView(final View dialogView, final EditText editText) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onDialogClosed(final boolean positiveResult) {
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
    public boolean shouldDisableDependents() {
        throw new RuntimeException("Stub!");
    }
    
    public EditText getEditText() {
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
