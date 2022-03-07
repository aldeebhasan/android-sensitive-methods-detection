package android.content;

import android.os.*;

public interface ServiceConnection
{
    void onServiceConnected(final ComponentName p0, final IBinder p1);
    
    void onServiceDisconnected(final ComponentName p0);
    
    default void onBindingDied(final ComponentName name) {
        throw new RuntimeException("Stub!");
    }
}
