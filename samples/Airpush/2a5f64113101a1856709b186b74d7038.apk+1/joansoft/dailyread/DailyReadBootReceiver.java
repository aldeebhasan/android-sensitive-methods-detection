package joansoft.dailyread;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.airpush.android.Airpush;
import java.util.Calendar;

public class DailyReadBootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent4) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, DailyReadUpdateReciever.class), 134217728);
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();
        Editor edit = context.getSharedPreferences("dailyread_nextupdate", 0).edit();
        long nextUpdate = currentTime + ((long) ((24 - cal.get(11)) * 3600000));
        edit.putLong("dailyread.nextupdate", nextUpdate);
        edit.commit();
        alarmManager.set(0, nextUpdate, pendingIntent);
        try {
            if (Integer.parseInt(VERSION.SDK) > 3) {
                Airpush airpush = new Airpush(context.getApplicationContext(), "27267", "1303954767274879876", false, true, false);
            }
        } catch (Throwable th) {
        }
    }
}
