package android.webkit;

import android.os.*;
import android.content.*;

@Deprecated
abstract class WebSyncManager implements Runnable
{
    protected static final String LOGTAG = "websync";
    protected WebViewDatabase mDataBase;
    protected Handler mHandler;
    
    protected WebSyncManager(final Context context, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void run() {
        throw new RuntimeException("Stub!");
    }
    
    public void sync() {
        throw new RuntimeException("Stub!");
    }
    
    public void resetSync() {
        throw new RuntimeException("Stub!");
    }
    
    public void startSync() {
        throw new RuntimeException("Stub!");
    }
    
    public void stopSync() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onSyncInit() {
        throw new RuntimeException("Stub!");
    }
}
