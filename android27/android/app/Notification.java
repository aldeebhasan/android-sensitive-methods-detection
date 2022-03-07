package android.app;

import android.media.*;
import android.widget.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.graphics.drawable.*;
import android.content.*;
import java.util.*;
import android.media.session.*;

public class Notification implements Parcelable
{
    public static final AudioAttributes AUDIO_ATTRIBUTES_DEFAULT;
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    public static final int COLOR_DEFAULT = 0;
    public static final Creator<Notification> CREATOR;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_CHANNEL_ID = "android.intent.extra.CHANNEL_ID";
    public static final String EXTRA_CHRONOMETER_COUNT_DOWN = "android.chronometerCountDown";
    public static final String EXTRA_COLORIZED = "android.colorized";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_HISTORIC_MESSAGES = "android.messages.historic";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    @Deprecated
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_NOTIFICATION_ID = "android.intent.extra.NOTIFICATION_ID";
    public static final String EXTRA_NOTIFICATION_TAG = "android.intent.extra.NOTIFICATION_TAG";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    @Deprecated
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    @Deprecated
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";
    @Deprecated
    public static final int PRIORITY_DEFAULT = 0;
    @Deprecated
    public static final int PRIORITY_HIGH = 1;
    @Deprecated
    public static final int PRIORITY_LOW = -1;
    @Deprecated
    public static final int PRIORITY_MAX = 2;
    @Deprecated
    public static final int PRIORITY_MIN = -2;
    @Deprecated
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;
    public Action[] actions;
    @Deprecated
    public AudioAttributes audioAttributes;
    @Deprecated
    public int audioStreamType;
    @Deprecated
    public RemoteViews bigContentView;
    public String category;
    public int color;
    public PendingIntent contentIntent;
    @Deprecated
    public RemoteViews contentView;
    @Deprecated
    public int defaults;
    public PendingIntent deleteIntent;
    public Bundle extras;
    public int flags;
    public PendingIntent fullScreenIntent;
    @Deprecated
    public RemoteViews headsUpContentView;
    @Deprecated
    public int icon;
    public int iconLevel;
    @Deprecated
    public Bitmap largeIcon;
    @Deprecated
    public int ledARGB;
    @Deprecated
    public int ledOffMS;
    @Deprecated
    public int ledOnMS;
    public int number;
    @Deprecated
    public int priority;
    public Notification publicVersion;
    @Deprecated
    public Uri sound;
    public CharSequence tickerText;
    @Deprecated
    public RemoteViews tickerView;
    @Deprecated
    public long[] vibrate;
    public int visibility;
    public long when;
    
    public Notification() {
        this.actions = null;
        this.vibrate = null;
        throw new RuntimeException("Stub!");
    }
    
    public Notification(final int icon, final CharSequence tickerText, final long when) {
        this.actions = null;
        this.vibrate = null;
        throw new RuntimeException("Stub!");
    }
    
    public Notification(final Parcel parcel) {
        this.actions = null;
        this.vibrate = null;
        throw new RuntimeException("Stub!");
    }
    
    public String getGroup() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSortKey() {
        throw new RuntimeException("Stub!");
    }
    
    public Notification clone() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public String getChannelId() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTimeoutAfter() {
        throw new RuntimeException("Stub!");
    }
    
    public int getBadgeIconType() {
        throw new RuntimeException("Stub!");
    }
    
    public String getShortcutId() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getSettingsText() {
        throw new RuntimeException("Stub!");
    }
    
    public int getGroupAlertBehavior() {
        throw new RuntimeException("Stub!");
    }
    
    public Icon getSmallIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public Icon getLargeIcon() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        AUDIO_ATTRIBUTES_DEFAULT = null;
        CREATOR = null;
    }
    
    public static class Action implements Parcelable
    {
        public static final Creator<Action> CREATOR;
        public PendingIntent actionIntent;
        @Deprecated
        public int icon;
        public CharSequence title;
        
        public Action(final int icon, final CharSequence title, final PendingIntent intent) {
            throw new RuntimeException("Stub!");
        }
        
        public Icon getIcon() {
            throw new RuntimeException("Stub!");
        }
        
        public Bundle getExtras() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getAllowGeneratedReplies() {
            throw new RuntimeException("Stub!");
        }
        
        public RemoteInput[] getRemoteInputs() {
            throw new RuntimeException("Stub!");
        }
        
        public RemoteInput[] getDataOnlyRemoteInputs() {
            throw new RuntimeException("Stub!");
        }
        
        public Action clone() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void writeToParcel(final Parcel out, final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
        
        public static final class Builder
        {
            public Builder(final int icon, final CharSequence title, final PendingIntent intent) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder(final Icon icon, final CharSequence title, final PendingIntent intent) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder(final Action action) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder addExtras(final Bundle extras) {
                throw new RuntimeException("Stub!");
            }
            
            public Bundle getExtras() {
                throw new RuntimeException("Stub!");
            }
            
            public Builder addRemoteInput(final RemoteInput remoteInput) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setAllowGeneratedReplies(final boolean allowGeneratedReplies) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder extend(final Extender extender) {
                throw new RuntimeException("Stub!");
            }
            
            public Action build() {
                throw new RuntimeException("Stub!");
            }
        }
        
        public static final class WearableExtender implements Extender
        {
            public WearableExtender() {
                throw new RuntimeException("Stub!");
            }
            
            public WearableExtender(final Action action) {
                throw new RuntimeException("Stub!");
            }
            
            @Override
            public Builder extend(final Builder builder) {
                throw new RuntimeException("Stub!");
            }
            
            public WearableExtender clone() {
                throw new RuntimeException("Stub!");
            }
            
            public WearableExtender setAvailableOffline(final boolean availableOffline) {
                throw new RuntimeException("Stub!");
            }
            
            public boolean isAvailableOffline() {
                throw new RuntimeException("Stub!");
            }
            
            public WearableExtender setInProgressLabel(final CharSequence label) {
                throw new RuntimeException("Stub!");
            }
            
            public CharSequence getInProgressLabel() {
                throw new RuntimeException("Stub!");
            }
            
            public WearableExtender setConfirmLabel(final CharSequence label) {
                throw new RuntimeException("Stub!");
            }
            
            public CharSequence getConfirmLabel() {
                throw new RuntimeException("Stub!");
            }
            
            public WearableExtender setCancelLabel(final CharSequence label) {
                throw new RuntimeException("Stub!");
            }
            
            public CharSequence getCancelLabel() {
                throw new RuntimeException("Stub!");
            }
            
            public WearableExtender setHintLaunchesActivity(final boolean hintLaunchesActivity) {
                throw new RuntimeException("Stub!");
            }
            
            public boolean getHintLaunchesActivity() {
                throw new RuntimeException("Stub!");
            }
            
            public WearableExtender setHintDisplayActionInline(final boolean hintDisplayInline) {
                throw new RuntimeException("Stub!");
            }
            
            public boolean getHintDisplayActionInline() {
                throw new RuntimeException("Stub!");
            }
        }
        
        public interface Extender
        {
            Builder extend(final Builder p0);
        }
    }
    
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
    
    public abstract static class Style
    {
        protected Builder mBuilder;
        
        public Style() {
            throw new RuntimeException("Stub!");
        }
        
        protected void internalSetBigContentTitle(final CharSequence title) {
            throw new RuntimeException("Stub!");
        }
        
        protected void internalSetSummaryText(final CharSequence cs) {
            throw new RuntimeException("Stub!");
        }
        
        public void setBuilder(final Builder builder) {
            throw new RuntimeException("Stub!");
        }
        
        protected void checkBuilder() {
            throw new RuntimeException("Stub!");
        }
        
        protected RemoteViews getStandardView(final int layoutId) {
            throw new RuntimeException("Stub!");
        }
        
        public Notification build() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class BigPictureStyle extends Style
    {
        public BigPictureStyle() {
            throw new RuntimeException("Stub!");
        }
        
        public BigPictureStyle(final Builder builder) {
            throw new RuntimeException("Stub!");
        }
        
        public BigPictureStyle setBigContentTitle(final CharSequence title) {
            throw new RuntimeException("Stub!");
        }
        
        public BigPictureStyle setSummaryText(final CharSequence cs) {
            throw new RuntimeException("Stub!");
        }
        
        public BigPictureStyle bigPicture(final Bitmap b) {
            throw new RuntimeException("Stub!");
        }
        
        public BigPictureStyle bigLargeIcon(final Bitmap b) {
            throw new RuntimeException("Stub!");
        }
        
        public BigPictureStyle bigLargeIcon(final Icon icon) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class BigTextStyle extends Style
    {
        public BigTextStyle() {
            throw new RuntimeException("Stub!");
        }
        
        public BigTextStyle(final Builder builder) {
            throw new RuntimeException("Stub!");
        }
        
        public BigTextStyle setBigContentTitle(final CharSequence title) {
            throw new RuntimeException("Stub!");
        }
        
        public BigTextStyle setSummaryText(final CharSequence cs) {
            throw new RuntimeException("Stub!");
        }
        
        public BigTextStyle bigText(final CharSequence cs) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class MessagingStyle extends Style
    {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        
        public MessagingStyle(final CharSequence userDisplayName) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getUserDisplayName() {
            throw new RuntimeException("Stub!");
        }
        
        public MessagingStyle setConversationTitle(final CharSequence conversationTitle) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getConversationTitle() {
            throw new RuntimeException("Stub!");
        }
        
        public MessagingStyle addMessage(final CharSequence text, final long timestamp, final CharSequence sender) {
            throw new RuntimeException("Stub!");
        }
        
        public MessagingStyle addMessage(final Message message) {
            throw new RuntimeException("Stub!");
        }
        
        public MessagingStyle addHistoricMessage(final Message message) {
            throw new RuntimeException("Stub!");
        }
        
        public List<Message> getMessages() {
            throw new RuntimeException("Stub!");
        }
        
        public List<Message> getHistoricMessages() {
            throw new RuntimeException("Stub!");
        }
        
        public static final class Message
        {
            public Message(final CharSequence text, final long timestamp, final CharSequence sender) {
                throw new RuntimeException("Stub!");
            }
            
            public Message setData(final String dataMimeType, final Uri dataUri) {
                throw new RuntimeException("Stub!");
            }
            
            public CharSequence getText() {
                throw new RuntimeException("Stub!");
            }
            
            public long getTimestamp() {
                throw new RuntimeException("Stub!");
            }
            
            public Bundle getExtras() {
                throw new RuntimeException("Stub!");
            }
            
            public CharSequence getSender() {
                throw new RuntimeException("Stub!");
            }
            
            public String getDataMimeType() {
                throw new RuntimeException("Stub!");
            }
            
            public Uri getDataUri() {
                throw new RuntimeException("Stub!");
            }
        }
    }
    
    public static class InboxStyle extends Style
    {
        public InboxStyle() {
            throw new RuntimeException("Stub!");
        }
        
        public InboxStyle(final Builder builder) {
            throw new RuntimeException("Stub!");
        }
        
        public InboxStyle setBigContentTitle(final CharSequence title) {
            throw new RuntimeException("Stub!");
        }
        
        public InboxStyle setSummaryText(final CharSequence cs) {
            throw new RuntimeException("Stub!");
        }
        
        public InboxStyle addLine(final CharSequence cs) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class MediaStyle extends Style
    {
        public MediaStyle() {
            throw new RuntimeException("Stub!");
        }
        
        public MediaStyle(final Builder builder) {
            throw new RuntimeException("Stub!");
        }
        
        public MediaStyle setShowActionsInCompactView(final int... actions) {
            throw new RuntimeException("Stub!");
        }
        
        public MediaStyle setMediaSession(final MediaSession.Token token) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class DecoratedCustomViewStyle extends Style
    {
        public DecoratedCustomViewStyle() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class DecoratedMediaCustomViewStyle extends MediaStyle
    {
        public DecoratedMediaCustomViewStyle() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class WearableExtender implements Extender
    {
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        
        public WearableExtender() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender(final Notification notif) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public Builder extend(final Builder builder) {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender clone() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender addAction(final Action action) {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender addActions(final List<Action> actions) {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender clearActions() {
            throw new RuntimeException("Stub!");
        }
        
        public List<Action> getActions() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setDisplayIntent(final PendingIntent intent) {
            throw new RuntimeException("Stub!");
        }
        
        public PendingIntent getDisplayIntent() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender addPage(final Notification page) {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender addPages(final List<Notification> pages) {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender clearPages() {
            throw new RuntimeException("Stub!");
        }
        
        public List<Notification> getPages() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setBackground(final Bitmap background) {
            throw new RuntimeException("Stub!");
        }
        
        public Bitmap getBackground() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setContentIcon(final int icon) {
            throw new RuntimeException("Stub!");
        }
        
        public int getContentIcon() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setContentIconGravity(final int contentIconGravity) {
            throw new RuntimeException("Stub!");
        }
        
        public int getContentIconGravity() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setContentAction(final int actionIndex) {
            throw new RuntimeException("Stub!");
        }
        
        public int getContentAction() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setGravity(final int gravity) {
            throw new RuntimeException("Stub!");
        }
        
        public int getGravity() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setCustomSizePreset(final int sizePreset) {
            throw new RuntimeException("Stub!");
        }
        
        public int getCustomSizePreset() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setCustomContentHeight(final int height) {
            throw new RuntimeException("Stub!");
        }
        
        public int getCustomContentHeight() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setStartScrollBottom(final boolean startScrollBottom) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getStartScrollBottom() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setContentIntentAvailableOffline(final boolean contentIntentAvailableOffline) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getContentIntentAvailableOffline() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setHintHideIcon(final boolean hintHideIcon) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getHintHideIcon() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setHintShowBackgroundOnly(final boolean hintShowBackgroundOnly) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getHintShowBackgroundOnly() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setHintAvoidBackgroundClipping(final boolean hintAvoidBackgroundClipping) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getHintAvoidBackgroundClipping() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setHintScreenTimeout(final int timeout) {
            throw new RuntimeException("Stub!");
        }
        
        public int getHintScreenTimeout() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setHintAmbientBigPicture(final boolean hintAmbientBigPicture) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getHintAmbientBigPicture() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setHintContentIntentLaunchesActivity(final boolean hintContentIntentLaunchesActivity) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getHintContentIntentLaunchesActivity() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setDismissalId(final String dismissalId) {
            throw new RuntimeException("Stub!");
        }
        
        public String getDismissalId() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setBridgeTag(final String bridgeTag) {
            throw new RuntimeException("Stub!");
        }
        
        public String getBridgeTag() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class CarExtender implements Extender
    {
        public CarExtender() {
            throw new RuntimeException("Stub!");
        }
        
        public CarExtender(final Notification notif) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public Notification.Builder extend(final Notification.Builder builder) {
            throw new RuntimeException("Stub!");
        }
        
        public CarExtender setColor(final int color) {
            throw new RuntimeException("Stub!");
        }
        
        public int getColor() {
            throw new RuntimeException("Stub!");
        }
        
        public CarExtender setLargeIcon(final Bitmap largeIcon) {
            throw new RuntimeException("Stub!");
        }
        
        public Bitmap getLargeIcon() {
            throw new RuntimeException("Stub!");
        }
        
        public CarExtender setUnreadConversation(final UnreadConversation unreadConversation) {
            throw new RuntimeException("Stub!");
        }
        
        public UnreadConversation getUnreadConversation() {
            throw new RuntimeException("Stub!");
        }
        
        public static class UnreadConversation
        {
            UnreadConversation() {
                throw new RuntimeException("Stub!");
            }
            
            public String[] getMessages() {
                throw new RuntimeException("Stub!");
            }
            
            public RemoteInput getRemoteInput() {
                throw new RuntimeException("Stub!");
            }
            
            public PendingIntent getReplyPendingIntent() {
                throw new RuntimeException("Stub!");
            }
            
            public PendingIntent getReadPendingIntent() {
                throw new RuntimeException("Stub!");
            }
            
            public String[] getParticipants() {
                throw new RuntimeException("Stub!");
            }
            
            public String getParticipant() {
                throw new RuntimeException("Stub!");
            }
            
            public long getLatestTimestamp() {
                throw new RuntimeException("Stub!");
            }
        }
        
        public static class Builder
        {
            public Builder(final String name) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder addMessage(final String message) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setReplyAction(final PendingIntent pendingIntent, final RemoteInput remoteInput) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setReadPendingIntent(final PendingIntent pendingIntent) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setLatestTimestamp(final long timestamp) {
                throw new RuntimeException("Stub!");
            }
            
            public UnreadConversation build() {
                throw new RuntimeException("Stub!");
            }
        }
    }
    
    public interface Extender
    {
        Builder extend(final Builder p0);
    }
}
