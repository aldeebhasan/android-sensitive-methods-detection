package android.service.restrictions;

import android.os.*;
import android.content.*;

public abstract class RestrictionsReceiver extends BroadcastReceiver
{
    public RestrictionsReceiver() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onRequestPermission(final Context p0, final String p1, final String p2, final String p3, final PersistableBundle p4);
    
    @Override
    public void onReceive(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
