package android.app;

import android.os.*;
import android.graphics.*;
import android.app.assist.*;
import java.util.*;
import android.net.*;
import android.database.*;
import android.widget.*;
import android.transition.*;
import android.view.accessibility.*;
import android.graphics.drawable.*;
import android.content.res.*;
import android.content.*;
import android.media.session.*;
import android.util.*;
import java.io.*;
import android.content.pm.*;
import android.view.*;

public class Activity extends ContextThemeWrapper implements LayoutInflater.Factory2, Window.Callback, KeyEvent.Callback, View.OnCreateContextMenuListener, ComponentCallbacks2
{
    public static final int DEFAULT_KEYS_DIALER = 1;
    public static final int DEFAULT_KEYS_DISABLE = 0;
    public static final int DEFAULT_KEYS_SEARCH_GLOBAL = 4;
    public static final int DEFAULT_KEYS_SEARCH_LOCAL = 3;
    public static final int DEFAULT_KEYS_SHORTCUT = 2;
    protected static final int[] FOCUSED_STATE_SET;
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_FIRST_USER = 1;
    public static final int RESULT_OK = -1;
    
    public Activity() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent getIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIntent(final Intent newIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public final Application getApplication() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isChild() {
        throw new RuntimeException("Stub!");
    }
    
    public final Activity getParent() {
        throw new RuntimeException("Stub!");
    }
    
    public WindowManager getWindowManager() {
        throw new RuntimeException("Stub!");
    }
    
    public Window getWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public LoaderManager getLoaderManager() {
        throw new RuntimeException("Stub!");
    }
    
    public View getCurrentFocus() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void attachBaseContext(final Context newBase) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onCreate(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreate(final Bundle savedInstanceState, final PersistableBundle persistentState) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRestoreInstanceState(final Bundle savedInstanceState, final PersistableBundle persistentState) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onPostCreate(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPostCreate(final Bundle savedInstanceState, final PersistableBundle persistentState) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onStart() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onRestart() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStateNotSaved() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onResume() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onPostResume() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVoiceInteraction() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVoiceInteractionRoot() {
        throw new RuntimeException("Stub!");
    }
    
    public VoiceInteractor getVoiceInteractor() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isLocalVoiceInteractionSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public void startLocalVoiceInteraction(final Bundle privateOptions) {
        throw new RuntimeException("Stub!");
    }
    
    public void onLocalVoiceInteractionStarted() {
        throw new RuntimeException("Stub!");
    }
    
    public void onLocalVoiceInteractionStopped() {
        throw new RuntimeException("Stub!");
    }
    
    public void stopLocalVoiceInteraction() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onNewIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onSaveInstanceState(final Bundle outState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSaveInstanceState(final Bundle outState, final PersistableBundle outPersistentState) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onPause() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onUserLeaveHint() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onCreateThumbnail(final Bitmap outBitmap, final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence onCreateDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public void onProvideAssistData(final Bundle data) {
        throw new RuntimeException("Stub!");
    }
    
    public void onProvideAssistContent(final AssistContent outContent) {
        throw new RuntimeException("Stub!");
    }
    
    public final void requestShowKeyboardShortcuts() {
        throw new RuntimeException("Stub!");
    }
    
    public final void dismissKeyboardShortcutsHelper() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onProvideKeyboardShortcuts(final List<KeyboardShortcutGroup> data, final Menu menu, final int deviceId) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean showAssist(final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onStop() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    public void reportFullyDrawn() {
        throw new RuntimeException("Stub!");
    }
    
    public void onMultiWindowModeChanged(final boolean isInMultiWindowMode, final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onMultiWindowModeChanged(final boolean isInMultiWindowMode) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInMultiWindowMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void onPictureInPictureModeChanged(final boolean isInPictureInPictureMode, final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onPictureInPictureModeChanged(final boolean isInPictureInPictureMode) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInPictureInPictureMode() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void enterPictureInPictureMode() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean enterPictureInPictureMode(final PictureInPictureParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPictureInPictureParams(final PictureInPictureParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxNumPictureInPictureActions() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    public int getChangingConfigurations() {
        throw new RuntimeException("Stub!");
    }
    
    public Object getLastNonConfigurationInstance() {
        throw new RuntimeException("Stub!");
    }
    
    public Object onRetainNonConfigurationInstance() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onLowMemory() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onTrimMemory(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    public FragmentManager getFragmentManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void onAttachFragment(final Fragment fragment) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final Cursor managedQuery(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void startManagingCursor(final Cursor c) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void stopManagingCursor(final Cursor c) {
        throw new RuntimeException("Stub!");
    }
    
    public <T extends View> T findViewById(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public ActionBar getActionBar() {
        throw new RuntimeException("Stub!");
    }
    
    public void setActionBar(final Toolbar toolbar) {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentView(final int layoutResID) {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentView(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentView(final View view, final ViewGroup.LayoutParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public void addContentView(final View view, final ViewGroup.LayoutParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public TransitionManager getContentTransitionManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentTransitionManager(final TransitionManager tm) {
        throw new RuntimeException("Stub!");
    }
    
    public Scene getContentScene() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFinishOnTouchOutside(final boolean finish) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDefaultKeyMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyLongPress(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyMultiple(final int keyCode, final int repeatCount, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onBackPressed() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onKeyShortcut(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onTrackballEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onGenericMotionEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onUserInteraction() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onWindowAttributesChanged(final WindowManager.LayoutParams params) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onContentChanged() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onAttachedToWindow() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDetachedFromWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasWindowFocus() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchKeyShortcutEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchTrackballEvent(final MotionEvent ev) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchGenericMotionEvent(final MotionEvent ev) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchPopulateAccessibilityEvent(final AccessibilityEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View onCreatePanelView(final int featureId) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onCreatePanelMenu(final int featureId, final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onPreparePanel(final int featureId, final View view, final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onMenuOpened(final int featureId, final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onMenuItemSelected(final int featureId, final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onPanelClosed(final int featureId, final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public void invalidateOptionsMenu() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onCreateOptionsMenu(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onPrepareOptionsMenu(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onOptionsItemSelected(final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onNavigateUp() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onNavigateUpFromChild(final Activity child) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreateNavigateUpTaskStack(final TaskStackBuilder builder) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPrepareNavigateUpTaskStack(final TaskStackBuilder builder) {
        throw new RuntimeException("Stub!");
    }
    
    public void onOptionsMenuClosed(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public void openOptionsMenu() {
        throw new RuntimeException("Stub!");
    }
    
    public void closeOptionsMenu() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerForContextMenu(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterForContextMenu(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void openContextMenu(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void closeContextMenu() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onContextItemSelected(final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    public void onContextMenuClosed(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    protected Dialog onCreateDialog(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    protected Dialog onCreateDialog(final int id, final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    protected void onPrepareDialog(final int id, final Dialog dialog) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    protected void onPrepareDialog(final int id, final Dialog dialog, final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void showDialog(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final boolean showDialog(final int id, final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void dismissDialog(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void removeDialog(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onSearchRequested(final SearchEvent searchEvent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onSearchRequested() {
        throw new RuntimeException("Stub!");
    }
    
    public final SearchEvent getSearchEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public void startSearch(final String initialQuery, final boolean selectInitialQuery, final Bundle appSearchData, final boolean globalSearch) {
        throw new RuntimeException("Stub!");
    }
    
    public void triggerSearch(final String query, final Bundle appSearchData) {
        throw new RuntimeException("Stub!");
    }
    
    public void takeKeyEvents(final boolean get) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean requestWindowFeature(final int featureId) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setFeatureDrawableResource(final int featureId, final int resId) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setFeatureDrawableUri(final int featureId, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setFeatureDrawable(final int featureId, final Drawable drawable) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setFeatureDrawableAlpha(final int featureId, final int alpha) {
        throw new RuntimeException("Stub!");
    }
    
    public LayoutInflater getLayoutInflater() {
        throw new RuntimeException("Stub!");
    }
    
    public MenuInflater getMenuInflater() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setTheme(final int resid) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onApplyThemeResource(final Resources.Theme theme, final int resid, final boolean first) {
        throw new RuntimeException("Stub!");
    }
    
    public final void requestPermissions(final String[] permissions, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean shouldShowRequestPermissionRationale(final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivityForResult(final Intent intent, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivityForResult(final Intent intent, final int requestCode, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isActivityTransitionRunning() {
        throw new RuntimeException("Stub!");
    }
    
    public void startIntentSenderForResult(final IntentSender intent, final int requestCode, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    public void startIntentSenderForResult(final IntentSender intent, final int requestCode, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags, final Bundle options) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startActivity(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startActivity(final Intent intent, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startActivities(final Intent[] intents) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startActivities(final Intent[] intents, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startIntentSender(final IntentSender intent, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void startIntentSender(final IntentSender intent, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags, final Bundle options) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean startActivityIfNeeded(final Intent intent, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean startActivityIfNeeded(final Intent intent, final int requestCode, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean startNextMatchingActivity(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean startNextMatchingActivity(final Intent intent, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivityFromChild(final Activity child, final Intent intent, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivityFromChild(final Activity child, final Intent intent, final int requestCode, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivityFromFragment(final Fragment fragment, final Intent intent, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivityFromFragment(final Fragment fragment, final Intent intent, final int requestCode, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public void startIntentSenderFromChild(final Activity child, final IntentSender intent, final int requestCode, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    public void startIntentSenderFromChild(final Activity child, final IntentSender intent, final int requestCode, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags, final Bundle options) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    public void overridePendingTransition(final int enterAnim, final int exitAnim) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setResult(final int resultCode) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setResult(final int resultCode, final Intent data) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getReferrer() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri onProvideReferrer() {
        throw new RuntimeException("Stub!");
    }
    
    public String getCallingPackage() {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getCallingActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVisible(final boolean visible) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFinishing() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDestroyed() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isChangingConfigurations() {
        throw new RuntimeException("Stub!");
    }
    
    public void recreate() {
        throw new RuntimeException("Stub!");
    }
    
    public void finish() {
        throw new RuntimeException("Stub!");
    }
    
    public void finishAffinity() {
        throw new RuntimeException("Stub!");
    }
    
    public void finishFromChild(final Activity child) {
        throw new RuntimeException("Stub!");
    }
    
    public void finishAfterTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void finishActivity(final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void finishActivityFromChild(final Activity child, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void finishAndRemoveTask() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean releaseInstance() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        throw new RuntimeException("Stub!");
    }
    
    public void onActivityReenter(final int resultCode, final Intent data) {
        throw new RuntimeException("Stub!");
    }
    
    public PendingIntent createPendingResult(final int requestCode, final Intent data, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRequestedOrientation(final int requestedOrientation) {
        throw new RuntimeException("Stub!");
    }
    
    public int getRequestedOrientation() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTaskId() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isTaskRoot() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean moveTaskToBack(final boolean nonRoot) {
        throw new RuntimeException("Stub!");
    }
    
    public String getLocalClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getComponentName() {
        throw new RuntimeException("Stub!");
    }
    
    public SharedPreferences getPreferences(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Object getSystemService(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTitle(final int titleId) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setTitleColor(final int textColor) {
        throw new RuntimeException("Stub!");
    }
    
    public final CharSequence getTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getTitleColor() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onTitleChanged(final CharSequence title, final int color) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onChildTitleChanged(final Activity childActivity, final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTaskDescription(final ActivityManager.TaskDescription taskDescription) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void setProgressBarVisibility(final boolean visible) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void setProgressBarIndeterminateVisibility(final boolean visible) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void setProgressBarIndeterminate(final boolean indeterminate) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void setProgress(final int progress) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void setSecondaryProgress(final int secondaryProgress) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setVolumeControlStream(final int streamType) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getVolumeControlStream() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setMediaController(final MediaController controller) {
        throw new RuntimeException("Stub!");
    }
    
    public final MediaController getMediaController() {
        throw new RuntimeException("Stub!");
    }
    
    public final void runOnUiThread(final Runnable action) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View onCreateView(final String name, final Context context, final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View onCreateView(final View parent, final String name, final Context context, final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isImmersive() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean requestVisibleBehind(final boolean visible) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onVisibleBehindCanceled() {
        throw new RuntimeException("Stub!");
    }
    
    public void onEnterAnimationComplete() {
        throw new RuntimeException("Stub!");
    }
    
    public void setImmersive(final boolean i) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVrModeEnabled(final boolean enabled, final ComponentName requestedComponent) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public ActionMode startActionMode(final ActionMode.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public ActionMode startActionMode(final ActionMode.Callback callback, final int type) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ActionMode onWindowStartingActionMode(final ActionMode.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ActionMode onWindowStartingActionMode(final ActionMode.Callback callback, final int type) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onActionModeStarted(final ActionMode mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onActionModeFinished(final ActionMode mode) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean shouldUpRecreateTask(final Intent targetIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean navigateUpTo(final Intent upIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean navigateUpToFromChild(final Activity child, final Intent upIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public Intent getParentActivityIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEnterSharedElementCallback(final SharedElementCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void setExitSharedElementCallback(final SharedElementCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void postponeEnterTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void startPostponedEnterTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public DragAndDropPermissions requestDragAndDropPermissions(final DragEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void startLockTask() {
        throw new RuntimeException("Stub!");
    }
    
    public void stopLockTask() {
        throw new RuntimeException("Stub!");
    }
    
    public void showLockTaskEscapeMessage() {
        throw new RuntimeException("Stub!");
    }
    
    public void setShowWhenLocked(final boolean showWhenLocked) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTurnScreenOn(final boolean turnScreenOn) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        FOCUSED_STATE_SET = null;
    }
}
