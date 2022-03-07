package android.provider;

import android.net.*;

public static final class Status implements BaseColumns
{
    public static final String CONFIGURATION_STATE = "configuration_state";
    public static final int CONFIGURATION_STATE_CAN_BE_CONFIGURED = 2;
    public static final int CONFIGURATION_STATE_CONFIGURING = 3;
    public static final int CONFIGURATION_STATE_DISABLED = 5;
    public static final int CONFIGURATION_STATE_FAILED = 4;
    public static final int CONFIGURATION_STATE_NOT_CONFIGURED = 1;
    public static final int CONFIGURATION_STATE_OK = 0;
    public static final Uri CONTENT_URI;
    public static final String DATA_CHANNEL_STATE = "data_channel_state";
    public static final int DATA_CHANNEL_STATE_BAD_CONFIGURATION = 3;
    public static final int DATA_CHANNEL_STATE_COMMUNICATION_ERROR = 4;
    public static final int DATA_CHANNEL_STATE_NO_CONNECTION = 1;
    public static final int DATA_CHANNEL_STATE_NO_CONNECTION_CELLULAR_REQUIRED = 2;
    public static final int DATA_CHANNEL_STATE_OK = 0;
    public static final int DATA_CHANNEL_STATE_SERVER_CONNECTION_ERROR = 6;
    public static final int DATA_CHANNEL_STATE_SERVER_ERROR = 5;
    public static final String DIR_TYPE = "vnd.android.cursor.dir/voicemail.source.status";
    public static final String ITEM_TYPE = "vnd.android.cursor.item/voicemail.source.status";
    public static final String NOTIFICATION_CHANNEL_STATE = "notification_channel_state";
    public static final int NOTIFICATION_CHANNEL_STATE_MESSAGE_WAITING = 2;
    public static final int NOTIFICATION_CHANNEL_STATE_NO_CONNECTION = 1;
    public static final int NOTIFICATION_CHANNEL_STATE_OK = 0;
    public static final String PHONE_ACCOUNT_COMPONENT_NAME = "phone_account_component_name";
    public static final String PHONE_ACCOUNT_ID = "phone_account_id";
    public static final String QUOTA_OCCUPIED = "quota_occupied";
    public static final String QUOTA_TOTAL = "quota_total";
    public static final int QUOTA_UNAVAILABLE = -1;
    public static final String SETTINGS_URI = "settings_uri";
    public static final String SOURCE_PACKAGE = "source_package";
    public static final String SOURCE_TYPE = "source_type";
    public static final String VOICEMAIL_ACCESS_URI = "voicemail_access_uri";
    
    Status() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildSourceUri(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
