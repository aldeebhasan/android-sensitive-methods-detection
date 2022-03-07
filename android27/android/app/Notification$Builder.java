package android.app;

import android.content.*;
import android.graphics.drawable.*;
import android.widget.*;
import android.graphics.*;
import android.net.*;
import android.media.*;
import android.os.*;

public static class Builder
{
    public Builder(final Context context, final String channelId) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setShortcutId(final String shortcutId) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setBadgeIconType(final int icon) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setGroupAlertBehavior(final int groupAlertBehavior) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setChannelId(final String channelId) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setTimeoutAfter(final long durationMs) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setWhen(final long when) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setShowWhen(final boolean show) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setUsesChronometer(final boolean b) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setChronometerCountDown(final boolean countDown) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSmallIcon(final int icon) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSmallIcon(final int icon, final int level) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSmallIcon(final Icon icon) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setContentTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setContentText(final CharSequence text) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSubText(final CharSequence text) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSettingsText(final CharSequence text) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setRemoteInputHistory(final CharSequence[] text) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setNumber(final int number) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setContentInfo(final CharSequence info) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setProgress(final int max, final int progress, final boolean indeterminate) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setContent(final RemoteViews views) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCustomContentView(final RemoteViews contentView) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCustomBigContentView(final RemoteViews contentView) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCustomHeadsUpContentView(final RemoteViews contentView) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setContentIntent(final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setDeleteIntent(final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setFullScreenIntent(final PendingIntent intent, final boolean highPriority) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setTicker(final CharSequence tickerText) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setTicker(final CharSequence tickerText, final RemoteViews views) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setLargeIcon(final Bitmap b) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setLargeIcon(final Icon icon) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setSound(final Uri sound) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setSound(final Uri sound, final int streamType) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setSound(final Uri sound, final AudioAttributes audioAttributes) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setVibrate(final long[] pattern) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setLights(final int argb, final int onMs, final int offMs) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setOngoing(final boolean ongoing) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setColorized(final boolean colorize) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setOnlyAlertOnce(final boolean onlyAlertOnce) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setAutoCancel(final boolean autoCancel) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setLocalOnly(final boolean localOnly) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setDefaults(final int defaults) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setPriority(final int pri) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCategory(final String category) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addPerson(final String uri) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setGroup(final String groupKey) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setGroupSummary(final boolean isGroupSummary) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSortKey(final String sortKey) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addExtras(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setExtras(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder addAction(final int icon, final CharSequence title, final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addAction(final Action action) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setActions(final Action... actions) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setStyle(final Style style) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setVisibility(final int visibility) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setPublicVersion(final Notification n) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder extend(final Extender extender) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setColor(final int argb) {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteViews createContentView() {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteViews createBigContentView() {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteViews createHeadsUpContentView() {
        throw new RuntimeException("Stub!");
    }
    
    public static Builder recoverBuilder(final Context context, final Notification n) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Notification getNotification() {
        throw new RuntimeException("Stub!");
    }
    
    public Notification build() {
        throw new RuntimeException("Stub!");
    }
}
