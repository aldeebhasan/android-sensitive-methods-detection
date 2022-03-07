package android.app;

import android.content.*;
import android.view.accessibility.*;
import android.net.*;
import android.graphics.drawable.*;
import android.view.*;
import android.os.*;

public class Dialog implements DialogInterface, Window.Callback, KeyEvent.Callback, View.OnCreateContextMenuListener
{
    public Dialog(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public Dialog(final Context context, final int themeResId) {
        throw new RuntimeException("Stub!");
    }
    
    protected Dialog(final Context context, final boolean cancelable, final OnCancelListener cancelListener) {
        throw new RuntimeException("Stub!");
    }
    
    public final Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public ActionBar getActionBar() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setOwnerActivity(final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public final Activity getOwnerActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isShowing() {
        throw new RuntimeException("Stub!");
    }
    
    public void create() {
        throw new RuntimeException("Stub!");
    }
    
    public void show() {
        throw new RuntimeException("Stub!");
    }
    
    public void hide() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dismiss() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onCreate(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onStart() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onStop() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public Window getWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public View getCurrentFocus() {
        throw new RuntimeException("Stub!");
    }
    
    public <T extends View> T findViewById(final int id) {
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
    
    public void setTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTitle(final int titleId) {
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
    
    public boolean onCreateOptionsMenu(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onPrepareOptionsMenu(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onOptionsItemSelected(final MenuItem item) {
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
    
    public void invalidateOptionsMenu() {
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
    
    public boolean onContextItemSelected(final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    public void onContextMenuClosed(final Menu menu) {
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
    
    public void setCancelable(final boolean flag) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCanceledOnTouchOutside(final boolean cancel) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void cancel() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnCancelListener(final OnCancelListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCancelMessage(final Message msg) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnDismissListener(final OnDismissListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnShowListener(final OnShowListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDismissMessage(final Message msg) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setVolumeControlStream(final int streamType) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getVolumeControlStream() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnKeyListener(final DialogInterface.OnKeyListener onKeyListener) {
        throw new RuntimeException("Stub!");
    }
}
