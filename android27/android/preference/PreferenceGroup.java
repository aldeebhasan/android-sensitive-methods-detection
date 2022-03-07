package android.preference;

import android.content.*;
import android.util.*;
import android.os.*;

public abstract class PreferenceGroup extends Preference
{
    public PreferenceGroup(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceGroup(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceGroup(final Context context, final AttributeSet attrs) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void setOrderingAsAdded(final boolean orderingAsAdded) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOrderingAsAdded() {
        throw new RuntimeException("Stub!");
    }
    
    public void addItemFromInflater(final Preference preference) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPreferenceCount() {
        throw new RuntimeException("Stub!");
    }
    
    public Preference getPreference(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean addPreference(final Preference preference) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removePreference(final Preference preference) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeAll() {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean onPrepareAddPreference(final Preference preference) {
        throw new RuntimeException("Stub!");
    }
    
    public Preference findPreference(final CharSequence key) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean isOnSameScreenAsChildren() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAttachedToActivity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onPrepareForRemoval() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void notifyDependencyChange(final boolean disableDependents) {
        throw new RuntimeException("Stub!");
    }
    
    protected void dispatchSaveInstanceState(final Bundle container) {
        throw new RuntimeException("Stub!");
    }
    
    protected void dispatchRestoreInstanceState(final Bundle container) {
        throw new RuntimeException("Stub!");
    }
}
