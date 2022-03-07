package android.location;

import android.app.*;
import android.content.*;
import android.os.*;

public abstract class SettingInjectorService extends Service
{
    public static final String ACTION_INJECTED_SETTING_CHANGED = "android.location.InjectedSettingChanged";
    public static final String ACTION_SERVICE_INTENT = "android.location.SettingInjectorService";
    public static final String ATTRIBUTES_NAME = "injected-location-setting";
    public static final String META_DATA_NAME = "android.location.SettingInjectorService";
    
    public SettingInjectorService(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final void onStart(final Intent intent, final int startId) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final int onStartCommand(final Intent intent, final int flags, final int startId) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    protected abstract String onGetSummary();
    
    protected abstract boolean onGetEnabled();
}
