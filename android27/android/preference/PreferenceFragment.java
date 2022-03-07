package android.preference;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;

public abstract class PreferenceFragment extends Fragment
{
    public PreferenceFragment() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onStart() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onStop() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDestroyView() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onSaveInstanceState(final Bundle outState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceManager getPreferenceManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreferenceScreen(final PreferenceScreen preferenceScreen) {
        throw new RuntimeException("Stub!");
    }
    
    public PreferenceScreen getPreferenceScreen() {
        throw new RuntimeException("Stub!");
    }
    
    public void addPreferencesFromIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void addPreferencesFromResource(final int preferencesResId) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onPreferenceTreeClick(final PreferenceScreen preferenceScreen, final Preference preference) {
        throw new RuntimeException("Stub!");
    }
    
    public Preference findPreference(final CharSequence key) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnPreferenceStartFragmentCallback
    {
        boolean onPreferenceStartFragment(final PreferenceFragment p0, final Preference p1);
    }
}
