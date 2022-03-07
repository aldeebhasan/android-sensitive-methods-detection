package android.app;

import java.util.*;
import android.content.*;
import android.service.notification.*;
import android.os.*;

public class NotificationManager
{
    public static final String ACTION_INTERRUPTION_FILTER_CHANGED = "android.app.action.INTERRUPTION_FILTER_CHANGED";
    public static final String ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED = "android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED";
    public static final String ACTION_NOTIFICATION_POLICY_CHANGED = "android.app.action.NOTIFICATION_POLICY_CHANGED";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    
    NotificationManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void notify(final int id, final Notification notification) {
        throw new RuntimeException("Stub!");
    }
    
    public void notify(final String tag, final int id, final Notification notification) {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel(final String tag, final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public void cancelAll() {
        throw new RuntimeException("Stub!");
    }
    
    public void createNotificationChannelGroup(final NotificationChannelGroup group) {
        throw new RuntimeException("Stub!");
    }
    
    public void createNotificationChannelGroups(final List<NotificationChannelGroup> groups) {
        throw new RuntimeException("Stub!");
    }
    
    public void createNotificationChannel(final NotificationChannel channel) {
        throw new RuntimeException("Stub!");
    }
    
    public void createNotificationChannels(final List<NotificationChannel> channels) {
        throw new RuntimeException("Stub!");
    }
    
    public NotificationChannel getNotificationChannel(final String channelId) {
        throw new RuntimeException("Stub!");
    }
    
    public List<NotificationChannel> getNotificationChannels() {
        throw new RuntimeException("Stub!");
    }
    
    public void deleteNotificationChannel(final String channelId) {
        throw new RuntimeException("Stub!");
    }
    
    public List<NotificationChannelGroup> getNotificationChannelGroups() {
        throw new RuntimeException("Stub!");
    }
    
    public void deleteNotificationChannelGroup(final String groupId) {
        throw new RuntimeException("Stub!");
    }
    
    public Map<String, AutomaticZenRule> getAutomaticZenRules() {
        throw new RuntimeException("Stub!");
    }
    
    public AutomaticZenRule getAutomaticZenRule(final String id) {
        throw new RuntimeException("Stub!");
    }
    
    public String addAutomaticZenRule(final AutomaticZenRule automaticZenRule) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean updateAutomaticZenRule(final String id, final AutomaticZenRule automaticZenRule) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeAutomaticZenRule(final String id) {
        throw new RuntimeException("Stub!");
    }
    
    public int getImportance() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean areNotificationsEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isNotificationPolicyAccessGranted() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isNotificationListenerAccessGranted(final ComponentName listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Policy getNotificationPolicy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setNotificationPolicy(final Policy policy) {
        throw new RuntimeException("Stub!");
    }
    
    public StatusBarNotification[] getActiveNotifications() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getCurrentInterruptionFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setInterruptionFilter(final int interruptionFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public static class Policy implements Parcelable
    {
        public static final Creator<Policy> CREATOR;
        public static final int PRIORITY_CATEGORY_CALLS = 8;
        public static final int PRIORITY_CATEGORY_EVENTS = 2;
        public static final int PRIORITY_CATEGORY_MESSAGES = 4;
        public static final int PRIORITY_CATEGORY_REMINDERS = 1;
        public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 16;
        public static final int PRIORITY_SENDERS_ANY = 0;
        public static final int PRIORITY_SENDERS_CONTACTS = 1;
        public static final int PRIORITY_SENDERS_STARRED = 2;
        public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;
        public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;
        public final int priorityCallSenders;
        public final int priorityCategories;
        public final int priorityMessageSenders;
        public final int suppressedVisualEffects;
        
        public Policy(final int priorityCategories, final int priorityCallSenders, final int priorityMessageSenders) {
            throw new RuntimeException("Stub!");
        }
        
        public Policy(final int priorityCategories, final int priorityCallSenders, final int priorityMessageSenders, final int suppressedVisualEffects) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int hashCode() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean equals(final Object o) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        public static String suppressedEffectsToString(final int effects) {
            throw new RuntimeException("Stub!");
        }
        
        public static String priorityCategoriesToString(final int priorityCategories) {
            throw new RuntimeException("Stub!");
        }
        
        public static String prioritySendersToString(final int prioritySenders) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
}
