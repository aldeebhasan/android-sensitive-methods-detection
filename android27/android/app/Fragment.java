package android.app;

import android.content.*;
import android.animation.*;
import android.content.res.*;
import android.view.*;
import android.transition.*;
import java.io.*;
import android.os.*;
import android.util.*;

public class Fragment implements ComponentCallbacks2, View.OnCreateContextMenuListener
{
    public Fragment() {
        throw new RuntimeException("Stub!");
    }
    
    public static Fragment instantiate(final Context context, final String fname) {
        throw new RuntimeException("Stub!");
    }
    
    public static Fragment instantiate(final Context context, final String fname, final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getId() {
        throw new RuntimeException("Stub!");
    }
    
    public final String getTag() {
        throw new RuntimeException("Stub!");
    }
    
    public void setArguments(final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    public final Bundle getArguments() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isStateSaved() {
        throw new RuntimeException("Stub!");
    }
    
    public void setInitialSavedState(final SavedState state) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTargetFragment(final Fragment fragment, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public final Fragment getTargetFragment() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getTargetRequestCode() {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public final Activity getActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public final Object getHost() {
        throw new RuntimeException("Stub!");
    }
    
    public final Resources getResources() {
        throw new RuntimeException("Stub!");
    }
    
    public final CharSequence getText(final int resId) {
        throw new RuntimeException("Stub!");
    }
    
    public final String getString(final int resId) {
        throw new RuntimeException("Stub!");
    }
    
    public final String getString(final int resId, final Object... formatArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public final FragmentManager getFragmentManager() {
        throw new RuntimeException("Stub!");
    }
    
    public final FragmentManager getChildFragmentManager() {
        throw new RuntimeException("Stub!");
    }
    
    public final Fragment getParentFragment() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isAdded() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isDetached() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isRemoving() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isInLayout() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isResumed() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isVisible() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isHidden() {
        throw new RuntimeException("Stub!");
    }
    
    public void onHiddenChanged(final boolean hidden) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRetainInstance(final boolean retain) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean getRetainInstance() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHasOptionsMenu(final boolean hasMenu) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMenuVisibility(final boolean menuVisible) {
        throw new RuntimeException("Stub!");
    }
    
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getUserVisibleHint() {
        throw new RuntimeException("Stub!");
    }
    
    public LoaderManager getLoaderManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivity(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivity(final Intent intent, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivityForResult(final Intent intent, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void startActivityForResult(final Intent intent, final int requestCode, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public void startIntentSenderForResult(final IntentSender intent, final int requestCode, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags, final Bundle options) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
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
    
    public LayoutInflater onGetLayoutInflater(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public final LayoutInflater getLayoutInflater() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onInflate(final AttributeSet attrs, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onInflate(final Context context, final AttributeSet attrs, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onInflate(final Activity activity, final AttributeSet attrs, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onAttachFragment(final Fragment childFragment) {
        throw new RuntimeException("Stub!");
    }
    
    public void onAttach(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onAttach(final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public Animator onCreateAnimator(final int transit, final boolean enter, final int nextAnim) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreate(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public View getView() {
        throw new RuntimeException("Stub!");
    }
    
    public void onActivityCreated(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onViewStateRestored(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStart() {
        throw new RuntimeException("Stub!");
    }
    
    public void onResume() {
        throw new RuntimeException("Stub!");
    }
    
    public void onSaveInstanceState(final Bundle outState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onMultiWindowModeChanged(final boolean isInMultiWindowMode, final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onMultiWindowModeChanged(final boolean isInMultiWindowMode) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPictureInPictureModeChanged(final boolean isInPictureInPictureMode, final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onPictureInPictureModeChanged(final boolean isInPictureInPictureMode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPause() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStop() {
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
    
    public void onDestroyView() {
        throw new RuntimeException("Stub!");
    }
    
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    public void onDetach() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPrepareOptionsMenu(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDestroyOptionsMenu() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onOptionsItemSelected(final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    public void onOptionsMenuClosed(final Menu menu) {
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
    
    public boolean onContextItemSelected(final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEnterSharedElementCallback(final SharedElementCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void setExitSharedElementCallback(final SharedElementCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEnterTransition(final Transition transition) {
        throw new RuntimeException("Stub!");
    }
    
    public Transition getEnterTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void setReturnTransition(final Transition transition) {
        throw new RuntimeException("Stub!");
    }
    
    public Transition getReturnTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void setExitTransition(final Transition transition) {
        throw new RuntimeException("Stub!");
    }
    
    public Transition getExitTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void setReenterTransition(final Transition transition) {
        throw new RuntimeException("Stub!");
    }
    
    public Transition getReenterTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSharedElementEnterTransition(final Transition transition) {
        throw new RuntimeException("Stub!");
    }
    
    public Transition getSharedElementEnterTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSharedElementReturnTransition(final Transition transition) {
        throw new RuntimeException("Stub!");
    }
    
    public Transition getSharedElementReturnTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAllowEnterTransitionOverlap(final boolean allow) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getAllowEnterTransitionOverlap() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAllowReturnTransitionOverlap(final boolean allow) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getAllowReturnTransitionOverlap() {
        throw new RuntimeException("Stub!");
    }
    
    public void postponeEnterTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void startPostponedEnterTransition() {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    public static class SavedState implements Parcelable
    {
        public static final ClassLoaderCreator<SavedState> CREATOR;
        
        SavedState() {
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
        
        static {
            CREATOR = null;
        }
    }
    
    public static class InstantiationException extends AndroidRuntimeException
    {
        public InstantiationException(final String msg, final Exception cause) {
            throw new RuntimeException("Stub!");
        }
    }
}
