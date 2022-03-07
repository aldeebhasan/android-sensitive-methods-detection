package android.database;

import android.os.*;
import android.net.*;

public abstract class ContentObserver
{
    public ContentObserver(final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean deliverSelfNotifications() {
        throw new RuntimeException("Stub!");
    }
    
    public void onChange(final boolean selfChange) {
        throw new RuntimeException("Stub!");
    }
    
    public void onChange(final boolean selfChange, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void dispatchChange(final boolean selfChange) {
        throw new RuntimeException("Stub!");
    }
    
    public final void dispatchChange(final boolean selfChange, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
}
