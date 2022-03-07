package android.app;

import java.io.*;
import android.os.*;
import android.content.*;
import android.view.*;

public abstract class FragmentHostCallback<E> extends FragmentContainer
{
    public FragmentHostCallback(final Context context, final Handler handler, final int windowAnimations) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDump(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onShouldSaveFragmentState(final Fragment fragment) {
        throw new RuntimeException("Stub!");
    }
    
    public LayoutInflater onGetLayoutInflater() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onUseFragmentManagerInflaterFactory() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract E onGetHost();
    
    public void onInvalidateOptionsMenu() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStartActivityFromFragment(final Fragment fragment, final Intent intent, final int requestCode, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStartIntentSenderFromFragment(final Fragment fragment, final IntentSender intent, final int requestCode, final Intent fillInIntent, final int flagsMask, final int flagsValues, final int extraFlags, final Bundle options) throws IntentSender.SendIntentException {
        throw new RuntimeException("Stub!");
    }
    
    public void onRequestPermissionsFromFragment(final Fragment fragment, final String[] permissions, final int requestCode) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onHasWindowAnimations() {
        throw new RuntimeException("Stub!");
    }
    
    public int onGetWindowAnimations() {
        throw new RuntimeException("Stub!");
    }
    
    public void onAttachFragment(final Fragment fragment) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public <T extends View> T onFindViewById(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onHasView() {
        throw new RuntimeException("Stub!");
    }
}
