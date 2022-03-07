package android.service.notification;

import android.app.*;
import java.util.*;
import android.content.*;
import android.os.*;

public abstract class NotificationListenerService extends Service
{
    public static final int HINT_HOST_DISABLE_CALL_EFFECTS = 4;
    public static final int HINT_HOST_DISABLE_EFFECTS = 1;
    public static final int HINT_HOST_DISABLE_NOTIFICATION_EFFECTS = 2;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_ADDED = 1;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_DELETED = 3;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_UPDATED = 2;
    public static final int REASON_APP_CANCEL = 8;
    public static final int REASON_APP_CANCEL_ALL = 9;
    public static final int REASON_CANCEL = 2;
    public static final int REASON_CANCEL_ALL = 3;
    public static final int REASON_CHANNEL_BANNED = 17;
    public static final int REASON_CLICK = 1;
    public static final int REASON_ERROR = 4;
    public static final int REASON_GROUP_OPTIMIZATION = 13;
    public static final int REASON_GROUP_SUMMARY_CANCELED = 12;
    public static final int REASON_LISTENER_CANCEL = 10;
    public static final int REASON_LISTENER_CANCEL_ALL = 11;
    public static final int REASON_PACKAGE_BANNED = 7;
    public static final int REASON_PACKAGE_CHANGED = 5;
    public static final int REASON_PACKAGE_SUSPENDED = 14;
    public static final int REASON_PROFILE_TURNED_OFF = 15;
    public static final int REASON_SNOOZED = 18;
    public static final int REASON_TIMEOUT = 19;
    public static final int REASON_UNAUTOBUNDLED = 16;
    public static final int REASON_USER_STOPPED = 6;
    public static final String SERVICE_INTERFACE = "android.service.notification.NotificationListenerService";
    public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;
    public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;
    
    public NotificationListenerService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void attachBaseContext(final Context base) {
        throw new RuntimeException("Stub!");
    }
    
    public void onNotificationPosted(final StatusBarNotification sbn) {
        throw new RuntimeException("Stub!");
    }
    
    public void onNotificationPosted(final StatusBarNotification sbn, final RankingMap rankingMap) {
        throw new RuntimeException("Stub!");
    }
    
    public void onNotificationRemoved(final StatusBarNotification sbn) {
        throw new RuntimeException("Stub!");
    }
    
    public void onNotificationRemoved(final StatusBarNotification sbn, final RankingMap rankingMap) {
        throw new RuntimeException("Stub!");
    }
    
    public void onNotificationRemoved(final StatusBarNotification sbn, final RankingMap rankingMap, final int reason) {
        throw new RuntimeException("Stub!");
    }
    
    public void onListenerConnected() {
        throw new RuntimeException("Stub!");
    }
    
    public void onListenerDisconnected() {
        throw new RuntimeException("Stub!");
    }
    
    public void onNotificationRankingUpdate(final RankingMap rankingMap) {
        throw new RuntimeException("Stub!");
    }
    
    public void onListenerHintsChanged(final int hints) {
        throw new RuntimeException("Stub!");
    }
    
    public void onNotificationChannelModified(final String pkg, final UserHandle user, final NotificationChannel channel, final int modificationType) {
        throw new RuntimeException("Stub!");
    }
    
    public void onNotificationChannelGroupModified(final String pkg, final UserHandle user, final NotificationChannelGroup group, final int modificationType) {
        throw new RuntimeException("Stub!");
    }
    
    public void onInterruptionFilterChanged(final int interruptionFilter) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final void cancelNotification(final String pkg, final String tag, final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public final void cancelNotification(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public final void cancelAllNotifications() {
        throw new RuntimeException("Stub!");
    }
    
    public final void cancelNotifications(final String[] keys) {
        throw new RuntimeException("Stub!");
    }
    
    public final void snoozeNotification(final String key, final long durationMs) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setNotificationsShown(final String[] keys) {
        throw new RuntimeException("Stub!");
    }
    
    public final void updateNotificationChannel(final String pkg, final UserHandle user, final NotificationChannel channel) {
        throw new RuntimeException("Stub!");
    }
    
    public final List<NotificationChannel> getNotificationChannels(final String pkg, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public final List<NotificationChannelGroup> getNotificationChannelGroups(final String pkg, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public StatusBarNotification[] getActiveNotifications() {
        throw new RuntimeException("Stub!");
    }
    
    public final StatusBarNotification[] getSnoozedNotifications() {
        throw new RuntimeException("Stub!");
    }
    
    public StatusBarNotification[] getActiveNotifications(final String[] keys) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getCurrentListenerHints() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getCurrentInterruptionFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public final void requestListenerHints(final int hints) {
        throw new RuntimeException("Stub!");
    }
    
    public final void requestInterruptionFilter(final int interruptionFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public RankingMap getCurrentRanking() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    public static void requestRebind(final ComponentName componentName) {
        throw new RuntimeException("Stub!");
    }
    
    public final void requestUnbind() {
        throw new RuntimeException("Stub!");
    }
    
    public static class Ranking
    {
        public Ranking() {
            throw new RuntimeException("Stub!");
        }
        
        public String getKey() {
            throw new RuntimeException("Stub!");
        }
        
        public int getRank() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isAmbient() {
            throw new RuntimeException("Stub!");
        }
        
        public int getSuppressedVisualEffects() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean matchesInterruptionFilter() {
            throw new RuntimeException("Stub!");
        }
        
        public int getImportance() {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getImportanceExplanation() {
            throw new RuntimeException("Stub!");
        }
        
        public String getOverrideGroupKey() {
            throw new RuntimeException("Stub!");
        }
        
        public NotificationChannel getChannel() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean canShowBadge() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class RankingMap implements Parcelable
    {
        public static final Creator<RankingMap> CREATOR;
        
        RankingMap() {
            throw new RuntimeException("Stub!");
        }
        
        public String[] getOrderedKeys() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getRanking(final String key, final Ranking outRanking) {
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
}
