package android.preference;

import android.util.*;
import android.content.*;
import android.net.*;
import android.content.res.*;

public class RingtonePreference extends Preference implements PreferenceManager.OnActivityResultListener
{
    public RingtonePreference(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public RingtonePreference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public RingtonePreference(final Context context, final AttributeSet attrs) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public RingtonePreference(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public int getRingtoneType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRingtoneType(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getShowDefault() {
        throw new RuntimeException("Stub!");
    }
    
    public void setShowDefault(final boolean showDefault) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getShowSilent() {
        throw new RuntimeException("Stub!");
    }
    
    public void setShowSilent(final boolean showSilent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onClick() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onPrepareRingtonePickerIntent(final Intent ringtonePickerIntent) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onSaveRingtone(final Uri ringtoneUri) {
        throw new RuntimeException("Stub!");
    }
    
    protected Uri onRestoreRingtone() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Object onGetDefaultValue(final TypedArray a, final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onSetInitialValue(final boolean restorePersistedValue, final Object defaultValueObj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAttachedToHierarchy(final PreferenceManager preferenceManager) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        throw new RuntimeException("Stub!");
    }
}
