package android.webkit;

import android.os.*;
import android.content.*;

@Deprecated
public final class CookieSyncManager extends WebSyncManager
{
    protected static final String LOGTAG = "websync";
    protected WebViewDatabase mDataBase;
    protected Handler mHandler;
    
    CookieSyncManager() {
        super(null, null);
        throw new RuntimeException("Stub!");
    }
    
    public static CookieSyncManager getInstance() {
        throw new RuntimeException("Stub!");
    }
    
    public static CookieSyncManager createInstance(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public void sync() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    protected void syncFromRamToFlash() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public void resetSync() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public void startSync() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public void stopSync() {
        throw new RuntimeException("Stub!");
    }
}
