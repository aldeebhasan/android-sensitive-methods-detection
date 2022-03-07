package android.test;

import java.util.*;
import android.content.*;
import android.net.*;
import java.io.*;

@Deprecated
public class IsolatedContext extends ContextWrapper
{
    public IsolatedContext(final ContentResolver resolver, final Context targetContext) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public List<Intent> getAndClearBroadcastIntents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ContentResolver getContentResolver() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean bindService(final Intent service, final ServiceConnection conn, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Intent registerReceiver(final BroadcastReceiver receiver, final IntentFilter filter) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void unregisterReceiver(final BroadcastReceiver receiver) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendBroadcast(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void sendOrderedBroadcast(final Intent intent, final String receiverPermission) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkUriPermission(final Uri uri, final String readPermission, final String writePermission, final int pid, final int uid, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkUriPermission(final Uri uri, final int pid, final int uid, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Object getSystemService(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getFilesDir() {
        throw new RuntimeException("Stub!");
    }
}
