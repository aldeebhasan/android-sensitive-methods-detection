package android.service.autofill;

import android.app.*;
import android.content.*;
import android.os.*;

public abstract class AutofillService extends Service
{
    public static final String SERVICE_INTERFACE = "android.service.autofill.AutofillService";
    public static final String SERVICE_META_DATA = "android.autofill";
    
    public AutofillService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnected() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onFillRequest(final FillRequest p0, final CancellationSignal p1, final FillCallback p2);
    
    public abstract void onSaveRequest(final SaveRequest p0, final SaveCallback p1);
    
    public void onDisconnected() {
        throw new RuntimeException("Stub!");
    }
    
    public final FillEventHistory getFillEventHistory() {
        throw new RuntimeException("Stub!");
    }
}
