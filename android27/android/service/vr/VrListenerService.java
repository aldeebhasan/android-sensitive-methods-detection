package android.service.vr;

import android.app.*;
import android.os.*;
import android.content.*;

public abstract class VrListenerService extends Service
{
    public static final String SERVICE_INTERFACE = "android.service.vr.VrListenerService";
    
    public VrListenerService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCurrentVrActivityChanged(final ComponentName component) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean isVrModePackageEnabled(final Context context, final ComponentName requestedComponent) {
        throw new RuntimeException("Stub!");
    }
}
