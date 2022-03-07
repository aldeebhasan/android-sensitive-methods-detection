package android.preference;

import java.util.*;
import android.view.*;
import android.content.*;
import android.widget.*;
import android.app.*;
import android.content.res.*;
import android.os.*;

public abstract class PreferenceActivity extends ListActivity implements PreferenceFragment.OnPreferenceStartFragmentCallback
{
    public static final String EXTRA_NO_HEADERS = ":android:no_headers";
    public static final String EXTRA_SHOW_FRAGMENT = ":android:show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":android:show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_SHORT_TITLE = ":android:show_fragment_short_title";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = ":android:show_fragment_title";
    public static final long HEADER_ID_UNDEFINED = -1L;
    
    public PreferenceActivity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onBackPressed() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasHeaders() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isMultiPane() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onIsMultiPane() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onIsHidingHeaders() {
        throw new RuntimeException("Stub!");
    }
    
    public Header onGetInitialHeader() {
        throw new RuntimeException("Stub!");
    }
    
    public Header onGetNewHeader() {
        throw new RuntimeException("Stub!");
    }
    
    public void onBuildHeaders(final List<Header> target) {
        throw new RuntimeException("Stub!");
    }
    
    public void invalidateHeaders() {
        throw new RuntimeException("Stub!");
    }
    
    public void loadHeadersFromResource(final int resid, final List<Header> target) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean isValidFragment(final String fragmentName) {
        throw new RuntimeException("Stub!");
    }
    
    public void setListFooter(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onStop() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onRestoreInstanceState(final Bundle state) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onContentChanged() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        throw new RuntimeException("Stub!");
    }
    
    public void onHeaderClick(final Header header, final int position) {
        throw new RuntimeException("Stub!");
    }
    
    public Intent onBuildStartFragmentIntent(final String fragmentName, final Bundle args, final int titleRes, final int shortTitleRes) {
        throw new RuntimeException("Stub!");
    }
    
    public void startWithFragment(final String fragmentName, final Bundle args, final Fragment resultTo, final int resultRequestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void startWithFragment(final String fragmentName, final Bundle args, final Fragment resultTo, final int resultRequestCode, final int titleRes, final int shortTitleRes) {
        throw new RuntimeException("Stub!");
    }
    
    public void showBreadCrumbs(final CharSequence title, final CharSequence shortTitle) {
        throw new RuntimeException("Stub!");
    }
    
    public void setParentTitle(final CharSequence title, final CharSequence shortTitle, final View.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void switchToHeader(final String fragmentName, final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    public void switchToHeader(final Header header) {
        throw new RuntimeException("Stub!");
    }
    
    public void startPreferenceFragment(final Fragment fragment, final boolean push) {
        throw new RuntimeException("Stub!");
    }
    
    public void startPreferencePanel(final String fragmentClass, final Bundle args, final int titleRes, final CharSequence titleText, final Fragment resultTo, final int resultRequestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void finishPreferencePanel(final Fragment caller, final int resultCode, final Intent resultData) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onPreferenceStartFragment(final PreferenceFragment caller, final Preference pref) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public PreferenceManager getPreferenceManager() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setPreferenceScreen(final PreferenceScreen preferenceScreen) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public PreferenceScreen getPreferenceScreen() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void addPreferencesFromIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void addPreferencesFromResource(final int preferencesResId) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean onPreferenceTreeClick(final PreferenceScreen preferenceScreen, final Preference preference) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Preference findPreference(final CharSequence key) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onNewIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Header implements Parcelable
    {
        public static final Creator<Header> CREATOR;
        public CharSequence breadCrumbShortTitle;
        public int breadCrumbShortTitleRes;
        public CharSequence breadCrumbTitle;
        public int breadCrumbTitleRes;
        public Bundle extras;
        public String fragment;
        public Bundle fragmentArguments;
        public int iconRes;
        public long id;
        public Intent intent;
        public CharSequence summary;
        public int summaryRes;
        public CharSequence title;
        public int titleRes;
        
        public Header() {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getTitle(final Resources res) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getSummary(final Resources res) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getBreadCrumbTitle(final Resources res) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getBreadCrumbShortTitle(final Resources res) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        public void readFromParcel(final Parcel in) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
}
