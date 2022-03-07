package android.app;

import android.os.*;
import java.util.*;
import java.io.*;
import android.content.*;
import android.view.*;

public abstract class FragmentManager
{
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    
    public FragmentManager() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract FragmentTransaction beginTransaction();
    
    public abstract boolean executePendingTransactions();
    
    public abstract Fragment findFragmentById(final int p0);
    
    public abstract Fragment findFragmentByTag(final String p0);
    
    public abstract void popBackStack();
    
    public abstract boolean popBackStackImmediate();
    
    public abstract void popBackStack(final String p0, final int p1);
    
    public abstract boolean popBackStackImmediate(final String p0, final int p1);
    
    public abstract void popBackStack(final int p0, final int p1);
    
    public abstract boolean popBackStackImmediate(final int p0, final int p1);
    
    public abstract int getBackStackEntryCount();
    
    public abstract BackStackEntry getBackStackEntryAt(final int p0);
    
    public abstract void addOnBackStackChangedListener(final OnBackStackChangedListener p0);
    
    public abstract void removeOnBackStackChangedListener(final OnBackStackChangedListener p0);
    
    public abstract void putFragment(final Bundle p0, final String p1, final Fragment p2);
    
    public abstract Fragment getFragment(final Bundle p0, final String p1);
    
    public abstract List<Fragment> getFragments();
    
    public abstract Fragment.SavedState saveFragmentInstanceState(final Fragment p0);
    
    public abstract boolean isDestroyed();
    
    public abstract void registerFragmentLifecycleCallbacks(final FragmentLifecycleCallbacks p0, final boolean p1);
    
    public abstract void unregisterFragmentLifecycleCallbacks(final FragmentLifecycleCallbacks p0);
    
    public abstract Fragment getPrimaryNavigationFragment();
    
    public abstract void dump(final String p0, final FileDescriptor p1, final PrintWriter p2, final String[] p3);
    
    public static void enableDebugLogging(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void invalidateOptionsMenu() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean isStateSaved();
    
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
    
    public interface OnBackStackChangedListener
    {
        void onBackStackChanged();
    }
    
    public interface BackStackEntry
    {
        int getId();
        
        String getName();
        
        int getBreadCrumbTitleRes();
        
        int getBreadCrumbShortTitleRes();
        
        CharSequence getBreadCrumbTitle();
        
        CharSequence getBreadCrumbShortTitle();
    }
}
