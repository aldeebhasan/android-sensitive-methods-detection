package joansoft.dailyread;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import java.util.Calendar;

public class UpdateService extends IntentService {
    public static final String LOCK_NAME_STATIC = "joansoft.dailyread.UpdateService";
    private static WakeLock lockStatic = null;
    private int ONEDAY = 86400000;
    private int ONEHOUR = 3600000;
    AlarmManager alarmManager;
    NotificationManager mNotificationManager;
    DailyPageFetcher pageFetcher = new DailyPageFetcher();
    PendingIntent pendingIntent;
    private String todaysWord = "";

    public UpdateService() {
        super("DailyRead Update Service");
    }

    private boolean refreshList() {
        try {
            String newWord = this.pageFetcher.fetchPage("http://www.joansoft.com/word_2_.txt");
            this.pageFetcher.clear();
            if (!(newWord == null || newWord.equals("") || newWord.equals(this.todaysWord))) {
                this.todaysWord = newWord;
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    void notifyUser() {
        int index = this.todaysWord.indexOf("\n");
        CharSequence tickerText = this.todaysWord;
        if (index != -1) {
            tickerText = tickerText.subSequence(0, index);
        }
        Notification notification = new Notification(R.drawable.icon, tickerText, System.currentTimeMillis());
        notification.setLatestEventInfo(this, "Today's DailyRead", tickerText + " Click to read more", PendingIntent.getActivity(this, 0, new Intent(this, DailyEnglish.class), 16));
        if (getSharedPreferences("dailyread_nextupdate", 0).getBoolean("dalert.opt", true)) {
            this.mNotificationManager.notify(DailyEnglish.DAILYREAD_ID, notification);
        }
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    protected void onHandleIntent(Intent intent1) {
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
        this.alarmManager = (AlarmManager) getSystemService("alarm");
        this.pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, DailyReadUpdateReciever.class), 134217728);
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();
        SharedPreferences pref = getSharedPreferences("dailyread_nextupdate", 0);
        long nextUpdate = pref.getLong("dailyread.nextupdate", 0);
        this.todaysWord = pref.getString("dailyread.word", "");
        Editor edit = pref.edit();
        if (nextUpdate == 0) {
            nextUpdate = currentTime + ((long) ((24 - cal.get(11)) * this.ONEHOUR));
        } else if (refreshList()) {
            notifyUser();
            nextUpdate = currentTime + ((long) this.ONEDAY);
            edit.putString("dailyread.word", this.todaysWord);
        } else {
            nextUpdate = currentTime + ((long) (this.ONEHOUR * 2));
        }
        edit.putLong("dailyread.nextupdate", nextUpdate);
        edit.commit();
        this.alarmManager.set(0, nextUpdate, this.pendingIntent);
        try {
            getLock(this).release();
            lockStatic = null;
        } catch (Exception e) {
        }
        try {
            stopSelf();
        } catch (Exception e2) {
        }
    }

    public static void acquireStaticLock(Context context) {
        getLock(context).acquire();
    }

    private static synchronized WakeLock getLock(Context context) {
        WakeLock wakeLock;
        synchronized (UpdateService.class) {
            if (lockStatic == null) {
                lockStatic = ((PowerManager) context.getSystemService("power")).newWakeLock(1, LOCK_NAME_STATIC);
                lockStatic.setReferenceCounted(true);
            }
            wakeLock = lockStatic;
        }
        return wakeLock;
    }
}
