package android.preference;

import android.content.*;

public class PreferenceManager
{
    public static final String KEY_HAS_SET_DEFAULT_VALUES = "_has_set_default_values";
    public static final String METADATA_KEY_PREFERENCES = "android.preference";
    
    PreferenceManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreferenceDataStore(final PreferenceDataStore dataStore) {
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceDataStore getPreferenceDataStore() {
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceScreen createPreferenceScreen(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public String getSharedPreferencesName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSharedPreferencesName(final String sharedPreferencesName) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSharedPreferencesMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSharedPreferencesMode(final int sharedPreferencesMode) {
        throw new RuntimeException("Stub!");
    }
    
    public void setStorageDefault() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStorageDeviceProtected() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isStorageDefault() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isStorageDeviceProtected() {
        throw new RuntimeException("Stub!");
    }
    
    public SharedPreferences getSharedPreferences() {
        throw new RuntimeException("Stub!");
    }
    
    public static SharedPreferences getDefaultSharedPreferences(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getDefaultSharedPreferencesName(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public Preference findPreference(final CharSequence key) {
        throw new RuntimeException("Stub!");
    }
    
    public static void setDefaultValues(final Context context, final int resId, final boolean readAgain) {
        throw new RuntimeException("Stub!");
    }
    
    public static void setDefaultValues(final Context context, final String sharedPreferencesName, final int sharedPreferencesMode, final int resId, final boolean readAgain) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnActivityDestroyListener
    {
        void onActivityDestroy();
    }
    
    public interface OnActivityStopListener
    {
        void onActivityStop();
    }
    
    public interface OnActivityResultListener
    {
        boolean onActivityResult(final int p0, final int p1, final Intent p2);
    }
}
