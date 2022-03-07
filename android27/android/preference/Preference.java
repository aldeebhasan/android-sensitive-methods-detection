package android.preference;

import android.util.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.content.*;
import java.util.*;
import android.view.*;
import android.os.*;

public class Preference implements Comparable<Preference>
{
    public static final int DEFAULT_ORDER = Integer.MAX_VALUE;
    
    public Preference(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        throw new RuntimeException("Stub!");
    }
    
    public Preference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        throw new RuntimeException("Stub!");
    }
    
    public Preference(final Context context, final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public Preference(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    protected Object onGetDefaultValue(final TypedArray a, final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public Intent getIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFragment(final String fragment) {
        throw new RuntimeException("Stub!");
    }
    
    public String getFragment() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreferenceDataStore(final PreferenceDataStore dataStore) {
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceDataStore getPreferenceDataStore() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle peekExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLayoutResource(final int layoutResId) {
        throw new RuntimeException("Stub!");
    }
    
    public int getLayoutResource() {
        throw new RuntimeException("Stub!");
    }
    
    public void setWidgetLayoutResource(final int widgetLayoutResId) {
        throw new RuntimeException("Stub!");
    }
    
    public int getWidgetLayoutResource() {
        throw new RuntimeException("Stub!");
    }
    
    public View getView(final View convertView, final ViewGroup parent) {
        throw new RuntimeException("Stub!");
    }
    
    protected View onCreateView(final ViewGroup parent) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onBindView(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOrder(final int order) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOrder() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTitle(final int titleResId) {
        throw new RuntimeException("Stub!");
    }
    
    public int getTitleRes() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIcon(final Drawable icon) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIcon(final int iconResId) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getSummary() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSummary(final CharSequence summary) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSummary(final int summaryResId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSelectable(final boolean selectable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSelectable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setShouldDisableView(final boolean shouldDisableView) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getShouldDisableView() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRecycleEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRecycleEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSingleLineTitle(final boolean singleLineTitle) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSingleLineTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIconSpaceReserved(final boolean iconSpaceReserved) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isIconSpaceReserved() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onClick() {
        throw new RuntimeException("Stub!");
    }
    
    public void setKey(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public String getKey() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasKey() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPersistent() {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean shouldPersist() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPersistent(final boolean persistent) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean callChangeListener(final Object newValue) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnPreferenceChangeListener(final OnPreferenceChangeListener onPreferenceChangeListener) {
        throw new RuntimeException("Stub!");
    }
    
    public OnPreferenceChangeListener getOnPreferenceChangeListener() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnPreferenceClickListener(final OnPreferenceClickListener onPreferenceClickListener) {
        throw new RuntimeException("Stub!");
    }
    
    public OnPreferenceClickListener getOnPreferenceClickListener() {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public SharedPreferences getSharedPreferences() {
        throw new RuntimeException("Stub!");
    }
    
    public SharedPreferences.Editor getEditor() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean shouldCommit() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int compareTo(final Preference another) {
        throw new RuntimeException("Stub!");
    }
    
    protected void notifyChanged() {
        throw new RuntimeException("Stub!");
    }
    
    protected void notifyHierarchyChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceManager getPreferenceManager() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onAttachedToHierarchy(final PreferenceManager preferenceManager) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onAttachedToActivity() {
        throw new RuntimeException("Stub!");
    }
    
    protected Preference findPreferenceInHierarchy(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyDependencyChange(final boolean disableDependents) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDependencyChanged(final Preference dependency, final boolean disableDependent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onParentChanged(final Preference parent, final boolean disableChild) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean shouldDisableDependents() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDependency(final String dependencyKey) {
        throw new RuntimeException("Stub!");
    }
    
    public String getDependency() {
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceGroup getParent() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onPrepareForRemoval() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDefaultValue(final Object defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onSetInitialValue(final boolean restorePersistedValue, final Object defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean persistString(final String value) {
        throw new RuntimeException("Stub!");
    }
    
    protected String getPersistedString(final String defaultReturnValue) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean persistStringSet(final Set<String> values) {
        throw new RuntimeException("Stub!");
    }
    
    public Set<String> getPersistedStringSet(final Set<String> defaultReturnValue) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean persistInt(final int value) {
        throw new RuntimeException("Stub!");
    }
    
    protected int getPersistedInt(final int defaultReturnValue) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean persistFloat(final float value) {
        throw new RuntimeException("Stub!");
    }
    
    protected float getPersistedFloat(final float defaultReturnValue) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean persistLong(final long value) {
        throw new RuntimeException("Stub!");
    }
    
    protected long getPersistedLong(final long defaultReturnValue) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean persistBoolean(final boolean value) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean getPersistedBoolean(final boolean defaultReturnValue) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public void saveHierarchyState(final Bundle container) {
        throw new RuntimeException("Stub!");
    }
    
    protected Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    public void restoreHierarchyState(final Bundle container) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onRestoreInstanceState(final Parcelable state) {
        throw new RuntimeException("Stub!");
    }
    
    public static class BaseSavedState extends AbsSavedState
    {
        public static final Parcelable.Creator<BaseSavedState> CREATOR;
        
        public BaseSavedState(final Parcel source) {
            super(null, null);
            throw new RuntimeException("Stub!");
        }
        
        public BaseSavedState(final Parcelable superState) {
            super(null, null);
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
    
    public interface OnPreferenceClickListener
    {
        boolean onPreferenceClick(final Preference p0);
    }
    
    public interface OnPreferenceChangeListener
    {
        boolean onPreferenceChange(final Preference p0, final Object p1);
    }
}
