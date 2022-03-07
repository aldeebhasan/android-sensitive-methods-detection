package android.provider;

import android.net.*;
import android.content.res.*;
import android.content.*;

public static final class System extends NameValueTable
{
    public static final String ACCELEROMETER_ROTATION = "accelerometer_rotation";
    @Deprecated
    public static final String ADB_ENABLED = "adb_enabled";
    @Deprecated
    public static final String AIRPLANE_MODE_ON = "airplane_mode_on";
    @Deprecated
    public static final String AIRPLANE_MODE_RADIOS = "airplane_mode_radios";
    public static final String ALARM_ALERT = "alarm_alert";
    @Deprecated
    public static final String ALWAYS_FINISH_ACTIVITIES = "always_finish_activities";
    @Deprecated
    public static final String ANDROID_ID = "android_id";
    @Deprecated
    public static final String ANIMATOR_DURATION_SCALE = "animator_duration_scale";
    @Deprecated
    public static final String AUTO_TIME = "auto_time";
    @Deprecated
    public static final String AUTO_TIME_ZONE = "auto_time_zone";
    public static final String BLUETOOTH_DISCOVERABILITY = "bluetooth_discoverability";
    public static final String BLUETOOTH_DISCOVERABILITY_TIMEOUT = "bluetooth_discoverability_timeout";
    @Deprecated
    public static final String BLUETOOTH_ON = "bluetooth_on";
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DATA_ROAMING = "data_roaming";
    public static final String DATE_FORMAT = "date_format";
    @Deprecated
    public static final String DEBUG_APP = "debug_app";
    public static final Uri DEFAULT_ALARM_ALERT_URI;
    public static final Uri DEFAULT_NOTIFICATION_URI;
    public static final Uri DEFAULT_RINGTONE_URI;
    @Deprecated
    public static final String DEVICE_PROVISIONED = "device_provisioned";
    @Deprecated
    public static final String DIM_SCREEN = "dim_screen";
    public static final String DTMF_TONE_TYPE_WHEN_DIALING = "dtmf_tone_type";
    public static final String DTMF_TONE_WHEN_DIALING = "dtmf_tone";
    public static final String END_BUTTON_BEHAVIOR = "end_button_behavior";
    public static final String FONT_SCALE = "font_scale";
    public static final String HAPTIC_FEEDBACK_ENABLED = "haptic_feedback_enabled";
    @Deprecated
    public static final String HTTP_PROXY = "http_proxy";
    @Deprecated
    public static final String INSTALL_NON_MARKET_APPS = "install_non_market_apps";
    @Deprecated
    public static final String LOCATION_PROVIDERS_ALLOWED = "location_providers_allowed";
    @Deprecated
    public static final String LOCK_PATTERN_ENABLED = "lock_pattern_autolock";
    @Deprecated
    public static final String LOCK_PATTERN_TACTILE_FEEDBACK_ENABLED = "lock_pattern_tactile_feedback_enabled";
    @Deprecated
    public static final String LOCK_PATTERN_VISIBLE = "lock_pattern_visible_pattern";
    @Deprecated
    public static final String LOGGING_ID = "logging_id";
    @Deprecated
    public static final String MODE_RINGER = "mode_ringer";
    public static final String MODE_RINGER_STREAMS_AFFECTED = "mode_ringer_streams_affected";
    public static final String MUTE_STREAMS_AFFECTED = "mute_streams_affected";
    @Deprecated
    public static final String NETWORK_PREFERENCE = "network_preference";
    @Deprecated
    public static final String NEXT_ALARM_FORMATTED = "next_alarm_formatted";
    public static final String NOTIFICATION_SOUND = "notification_sound";
    @Deprecated
    public static final String PARENTAL_CONTROL_ENABLED = "parental_control_enabled";
    @Deprecated
    public static final String PARENTAL_CONTROL_LAST_UPDATE = "parental_control_last_update";
    @Deprecated
    public static final String PARENTAL_CONTROL_REDIRECT_URL = "parental_control_redirect_url";
    @Deprecated
    public static final String RADIO_BLUETOOTH = "bluetooth";
    @Deprecated
    public static final String RADIO_CELL = "cell";
    @Deprecated
    public static final String RADIO_NFC = "nfc";
    @Deprecated
    public static final String RADIO_WIFI = "wifi";
    public static final String RINGTONE = "ringtone";
    public static final String SCREEN_BRIGHTNESS = "screen_brightness";
    public static final String SCREEN_BRIGHTNESS_MODE = "screen_brightness_mode";
    public static final int SCREEN_BRIGHTNESS_MODE_AUTOMATIC = 1;
    public static final int SCREEN_BRIGHTNESS_MODE_MANUAL = 0;
    public static final String SCREEN_OFF_TIMEOUT = "screen_off_timeout";
    @Deprecated
    public static final String SETTINGS_CLASSNAME = "settings_classname";
    public static final String SETUP_WIZARD_HAS_RUN = "setup_wizard_has_run";
    public static final String SHOW_GTALK_SERVICE_STATUS = "SHOW_GTALK_SERVICE_STATUS";
    @Deprecated
    public static final String SHOW_PROCESSES = "show_processes";
    @Deprecated
    public static final String SHOW_WEB_SUGGESTIONS = "show_web_suggestions";
    public static final String SOUND_EFFECTS_ENABLED = "sound_effects_enabled";
    @Deprecated
    public static final String STAY_ON_WHILE_PLUGGED_IN = "stay_on_while_plugged_in";
    public static final String TEXT_AUTO_CAPS = "auto_caps";
    public static final String TEXT_AUTO_PUNCTUATE = "auto_punctuate";
    public static final String TEXT_AUTO_REPLACE = "auto_replace";
    public static final String TEXT_SHOW_PASSWORD = "show_password";
    public static final String TIME_12_24 = "time_12_24";
    @Deprecated
    public static final String TRANSITION_ANIMATION_SCALE = "transition_animation_scale";
    @Deprecated
    public static final String USB_MASS_STORAGE_ENABLED = "usb_mass_storage_enabled";
    public static final String USER_ROTATION = "user_rotation";
    @Deprecated
    public static final String USE_GOOGLE_MAIL = "use_google_mail";
    public static final String VIBRATE_ON = "vibrate_on";
    public static final String VIBRATE_WHEN_RINGING = "vibrate_when_ringing";
    @Deprecated
    public static final String WAIT_FOR_DEBUGGER = "wait_for_debugger";
    @Deprecated
    public static final String WALLPAPER_ACTIVITY = "wallpaper_activity";
    @Deprecated
    public static final String WIFI_MAX_DHCP_RETRY_COUNT = "wifi_max_dhcp_retry_count";
    @Deprecated
    public static final String WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS = "wifi_mobile_data_transition_wakelock_timeout_ms";
    @Deprecated
    public static final String WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON = "wifi_networks_available_notification_on";
    @Deprecated
    public static final String WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY = "wifi_networks_available_repeat_delay";
    @Deprecated
    public static final String WIFI_NUM_OPEN_NETWORKS_KEPT = "wifi_num_open_networks_kept";
    @Deprecated
    public static final String WIFI_ON = "wifi_on";
    @Deprecated
    public static final String WIFI_SLEEP_POLICY = "wifi_sleep_policy";
    @Deprecated
    public static final int WIFI_SLEEP_POLICY_DEFAULT = 0;
    @Deprecated
    public static final int WIFI_SLEEP_POLICY_NEVER = 2;
    @Deprecated
    public static final int WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED = 1;
    @Deprecated
    public static final String WIFI_STATIC_DNS1 = "wifi_static_dns1";
    @Deprecated
    public static final String WIFI_STATIC_DNS2 = "wifi_static_dns2";
    @Deprecated
    public static final String WIFI_STATIC_GATEWAY = "wifi_static_gateway";
    @Deprecated
    public static final String WIFI_STATIC_IP = "wifi_static_ip";
    @Deprecated
    public static final String WIFI_STATIC_NETMASK = "wifi_static_netmask";
    @Deprecated
    public static final String WIFI_USE_STATIC_IP = "wifi_use_static_ip";
    @Deprecated
    public static final String WIFI_WATCHDOG_ACCEPTABLE_PACKET_LOSS_PERCENTAGE = "wifi_watchdog_acceptable_packet_loss_percentage";
    @Deprecated
    public static final String WIFI_WATCHDOG_AP_COUNT = "wifi_watchdog_ap_count";
    @Deprecated
    public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_DELAY_MS = "wifi_watchdog_background_check_delay_ms";
    @Deprecated
    public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_ENABLED = "wifi_watchdog_background_check_enabled";
    @Deprecated
    public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_TIMEOUT_MS = "wifi_watchdog_background_check_timeout_ms";
    @Deprecated
    public static final String WIFI_WATCHDOG_INITIAL_IGNORED_PING_COUNT = "wifi_watchdog_initial_ignored_ping_count";
    @Deprecated
    public static final String WIFI_WATCHDOG_MAX_AP_CHECKS = "wifi_watchdog_max_ap_checks";
    @Deprecated
    public static final String WIFI_WATCHDOG_ON = "wifi_watchdog_on";
    @Deprecated
    public static final String WIFI_WATCHDOG_PING_COUNT = "wifi_watchdog_ping_count";
    @Deprecated
    public static final String WIFI_WATCHDOG_PING_DELAY_MS = "wifi_watchdog_ping_delay_ms";
    @Deprecated
    public static final String WIFI_WATCHDOG_PING_TIMEOUT_MS = "wifi_watchdog_ping_timeout_ms";
    @Deprecated
    public static final String WINDOW_ANIMATION_SCALE = "window_animation_scale";
    
    public System() {
        throw new RuntimeException("Stub!");
    }
    
    public static String getString(final ContentResolver resolver, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean putString(final ContentResolver resolver, final String name, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getUriFor(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getInt(final ContentResolver cr, final String name, final int def) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getInt(final ContentResolver cr, final String name) throws SettingNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean putInt(final ContentResolver cr, final String name, final int value) {
        throw new RuntimeException("Stub!");
    }
    
    public static long getLong(final ContentResolver cr, final String name, final long def) {
        throw new RuntimeException("Stub!");
    }
    
    public static long getLong(final ContentResolver cr, final String name) throws SettingNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean putLong(final ContentResolver cr, final String name, final long value) {
        throw new RuntimeException("Stub!");
    }
    
    public static float getFloat(final ContentResolver cr, final String name, final float def) {
        throw new RuntimeException("Stub!");
    }
    
    public static float getFloat(final ContentResolver cr, final String name) throws SettingNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean putFloat(final ContentResolver cr, final String name, final float value) {
        throw new RuntimeException("Stub!");
    }
    
    public static void getConfiguration(final ContentResolver cr, final Configuration outConfig) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean putConfiguration(final ContentResolver cr, final Configuration config) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static boolean getShowGTalkServiceStatus(final ContentResolver cr) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void setShowGTalkServiceStatus(final ContentResolver cr, final boolean flag) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean canWrite(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        DEFAULT_ALARM_ALERT_URI = null;
        DEFAULT_NOTIFICATION_URI = null;
        DEFAULT_RINGTONE_URI = null;
    }
}
