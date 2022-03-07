package android.service.chooser;

import android.app.*;
import java.util.*;
import android.content.*;
import android.os.*;

public abstract class ChooserTargetService extends Service
{
    public static final String BIND_PERMISSION = "android.permission.BIND_CHOOSER_TARGET_SERVICE";
    public static final String META_DATA_NAME = "android.service.chooser.chooser_target_service";
    public static final String SERVICE_INTERFACE = "android.service.chooser.ChooserTargetService";
    
    public ChooserTargetService() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract List<ChooserTarget> onGetChooserTargets(final ComponentName p0, final IntentFilter p1);
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
