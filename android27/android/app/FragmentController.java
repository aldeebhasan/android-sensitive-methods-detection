package android.app;

import android.content.*;
import android.os.*;
import java.util.*;
import android.content.res.*;
import android.view.*;
import android.util.*;
import java.io.*;

public class FragmentController
{
    FragmentController() {
        throw new RuntimeException("Stub!");
    }
    
    public static final FragmentController createController(final FragmentHostCallback<?> callbacks) {
        throw new RuntimeException("Stub!");
    }
    
    public FragmentManager getFragmentManager() {
        throw new RuntimeException("Stub!");
    }
    
    public LoaderManager getLoaderManager() {
        throw new RuntimeException("Stub!");
    }
    
    public Fragment findFragmentByWho(final String who) {
        throw new RuntimeException("Stub!");
    }
    
    public void attachHost(final Fragment parent) {
        throw new RuntimeException("Stub!");
    }
    
    public View onCreateView(final View parent, final String name, final Context context, final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public void noteStateNotSaved() {
        throw new RuntimeException("Stub!");
    }
    
    public Parcelable saveAllState() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void restoreAllState(final Parcelable state, final List<Fragment> nonConfigList) {
        throw new RuntimeException("Stub!");
    }
    
    public void restoreAllState(final Parcelable state, final FragmentManagerNonConfig nonConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public List<Fragment> retainNonConfig() {
        throw new RuntimeException("Stub!");
    }
    
    public FragmentManagerNonConfig retainNestedNonConfig() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchCreate() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchActivityCreated() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchStart() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchResume() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchPause() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchStop() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchDestroyView() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void dispatchMultiWindowModeChanged(final boolean isInMultiWindowMode) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchMultiWindowModeChanged(final boolean isInMultiWindowMode, final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void dispatchPictureInPictureModeChanged(final boolean isInPictureInPictureMode) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchPictureInPictureModeChanged(final boolean isInPictureInPictureMode, final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchLowMemory() {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchTrimMemory(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean dispatchCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean dispatchPrepareOptionsMenu(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean dispatchOptionsItemSelected(final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean dispatchContextItemSelected(final MenuItem item) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchOptionsMenuClosed(final Menu menu) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean execPendingActions() {
        throw new RuntimeException("Stub!");
    }
    
    public void doLoaderStart() {
        throw new RuntimeException("Stub!");
    }
    
    public void doLoaderStop(final boolean retain) {
        throw new RuntimeException("Stub!");
    }
    
    public void doLoaderDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    public void reportLoaderStart() {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        throw new RuntimeException("Stub!");
    }
    
    public void restoreLoaderNonConfig(final ArrayMap<String, LoaderManager> loaderManagers) {
        throw new RuntimeException("Stub!");
    }
    
    public void dumpLoaders(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
}
