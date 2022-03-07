package joansoft.dailyread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyReadUpdateReciever extends BroadcastReceiver {
    public void onReceive(Context context, Intent arg1) {
        UpdateService.acquireStaticLock(context);
        context.startService(new Intent(context, UpdateService.class));
    }
}
