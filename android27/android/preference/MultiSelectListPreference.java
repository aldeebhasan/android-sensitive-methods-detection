package android.preference;

import android.content.*;
import android.util.*;
import java.util.*;
import android.app.*;
import android.content.res.*;
import android.os.*;

public class MultiSelectListPreference extends DialogPreference
{
    public MultiSelectListPreference(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public MultiSelectListPreference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public MultiSelectListPreference(final Context context, final AttributeSet attrs) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public MultiSelectListPreference(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void setEntries(final CharSequence[] entries) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEntries(final int entriesResId) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence[] getEntries() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEntryValues(final CharSequence[] entryValues) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEntryValues(final int entryValuesResId) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence[] getEntryValues() {
        throw new RuntimeException("Stub!");
    }
    
    public void setValues(final Set<String> values) {
        throw new RuntimeException("Stub!");
    }
    
    public Set<String> getValues() {
        throw new RuntimeException("Stub!");
    }
    
    public int findIndexOfValue(final String value) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onPrepareDialogBuilder(final AlertDialog.Builder builder) {
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
    protected Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
}
