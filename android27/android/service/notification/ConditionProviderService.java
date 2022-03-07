package android.service.notification;

import android.app.*;
import android.net.*;
import android.content.*;
import android.os.*;

public abstract class ConditionProviderService extends Service
{
    public static final String EXTRA_RULE_ID = "android.service.notification.extra.RULE_ID";
    public static final String META_DATA_CONFIGURATION_ACTIVITY = "android.service.zen.automatic.configurationActivity";
    public static final String META_DATA_RULE_INSTANCE_LIMIT = "android.service.zen.automatic.ruleInstanceLimit";
    public static final String META_DATA_RULE_TYPE = "android.service.zen.automatic.ruleType";
    public static final String SERVICE_INTERFACE = "android.service.notification.ConditionProviderService";
    
    public ConditionProviderService() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onConnected();
    
    public void onRequestConditions(final int relevance) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onSubscribe(final Uri p0);
    
    public abstract void onUnsubscribe(final Uri p0);
    
    public static final void requestRebind(final ComponentName componentName) {
        throw new RuntimeException("Stub!");
    }
    
    public final void requestUnbind() {
        throw new RuntimeException("Stub!");
    }
    
    public final void notifyCondition(final Condition condition) {
        throw new RuntimeException("Stub!");
    }
    
    public final void notifyConditions(final Condition... conditions) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
