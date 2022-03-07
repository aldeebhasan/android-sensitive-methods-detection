package android.app;

import android.content.*;
import android.os.*;
import android.view.*;

public abstract static class FragmentLifecycleCallbacks
{
    public FragmentLifecycleCallbacks() {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentPreAttached(final FragmentManager fm, final Fragment f, final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentAttached(final FragmentManager fm, final Fragment f, final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentPreCreated(final FragmentManager fm, final Fragment f, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentCreated(final FragmentManager fm, final Fragment f, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentActivityCreated(final FragmentManager fm, final Fragment f, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentViewCreated(final FragmentManager fm, final Fragment f, final View v, final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentStarted(final FragmentManager fm, final Fragment f) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentResumed(final FragmentManager fm, final Fragment f) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentPaused(final FragmentManager fm, final Fragment f) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentStopped(final FragmentManager fm, final Fragment f) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentSaveInstanceState(final FragmentManager fm, final Fragment f, final Bundle outState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentViewDestroyed(final FragmentManager fm, final Fragment f) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentDestroyed(final FragmentManager fm, final Fragment f) {
        throw new RuntimeException("Stub!");
    }
    
    public void onFragmentDetached(final FragmentManager fm, final Fragment f) {
        throw new RuntimeException("Stub!");
    }
}
