package android.service.carrier;

import android.app.*;
import android.content.*;
import android.os.*;

public abstract class CarrierService extends Service
{
    public static final String CARRIER_SERVICE_INTERFACE = "android.service.carrier.CarrierService";
    
    public CarrierService() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract PersistableBundle onLoadConfig(final CarrierIdentifier p0);
    
    public final void notifyCarrierNetworkChange(final boolean active) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
