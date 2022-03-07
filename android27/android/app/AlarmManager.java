package android.app;

import android.os.*;

public class AlarmManager
{
    public static final String ACTION_NEXT_ALARM_CLOCK_CHANGED = "android.app.action.NEXT_ALARM_CLOCK_CHANGED";
    public static final int ELAPSED_REALTIME = 3;
    public static final int ELAPSED_REALTIME_WAKEUP = 2;
    public static final long INTERVAL_DAY = 86400000L;
    public static final long INTERVAL_FIFTEEN_MINUTES = 900000L;
    public static final long INTERVAL_HALF_DAY = 43200000L;
    public static final long INTERVAL_HALF_HOUR = 1800000L;
    public static final long INTERVAL_HOUR = 3600000L;
    public static final int RTC = 1;
    public static final int RTC_WAKEUP = 0;
    
    AlarmManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final int type, final long triggerAtMillis, final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final int type, final long triggerAtMillis, final String tag, final OnAlarmListener listener, final Handler targetHandler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRepeating(final int type, final long triggerAtMillis, final long intervalMillis, final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setWindow(final int type, final long windowStartMillis, final long windowLengthMillis, final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setWindow(final int type, final long windowStartMillis, final long windowLengthMillis, final String tag, final OnAlarmListener listener, final Handler targetHandler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setExact(final int type, final long triggerAtMillis, final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setExact(final int type, final long triggerAtMillis, final String tag, final OnAlarmListener listener, final Handler targetHandler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAlarmClock(final AlarmClockInfo info, final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInexactRepeating(final int type, final long triggerAtMillis, final long intervalMillis, final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAndAllowWhileIdle(final int type, final long triggerAtMillis, final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setExactAndAllowWhileIdle(final int type, final long triggerAtMillis, final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel(final PendingIntent operation) {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel(final OnAlarmListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTime(final long millis) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTimeZone(final String timeZone) {
        throw new RuntimeException("Stub!");
    }
    
    public AlarmClockInfo getNextAlarmClock() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class AlarmClockInfo implements Parcelable
    {
        public static final Creator<AlarmClockInfo> CREATOR;
        
        public AlarmClockInfo(final long triggerTime, final PendingIntent showIntent) {
            throw new RuntimeException("Stub!");
        }
        
        public long getTriggerTime() {
            throw new RuntimeException("Stub!");
        }
        
        public PendingIntent getShowIntent() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
    
    public interface OnAlarmListener
    {
        void onAlarm();
    }
}
