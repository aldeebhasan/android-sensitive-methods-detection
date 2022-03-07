package android.nfc.cardemulation;

import android.app.*;
import android.content.*;
import android.os.*;

public abstract class OffHostApduService extends Service
{
    public static final String SERVICE_INTERFACE = "android.nfc.cardemulation.action.OFF_HOST_APDU_SERVICE";
    public static final String SERVICE_META_DATA = "android.nfc.cardemulation.off_host_apdu_service";
    
    public OffHostApduService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public abstract IBinder onBind(final Intent p0);
}
