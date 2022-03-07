package android.provider;

import android.net.*;
import android.content.*;

public static final class Secure extends NameValueTable
{
    public static final String ACCESSIBILITY_DISPLAY_INVERSION_ENABLED = "accessibility_display_inversion_enabled";
    public static final String ACCESSIBILITY_ENABLED = "accessibility_enabled";
    @Deprecated
    public static final String ACCESSIBILITY_SPEAK_PASSWORD = "speak_password";
    @Deprecated
    public static final String ADB_ENABLED = "adb_enabled";
    public static final String ALLOWED_GEOLOCATION_ORIGINS = "allowed_geolocation_origins";
    @Deprecated
    public static final String ALLOW_MOCK_LOCATION = "mock_location";
    public static final String ANDROID_ID = "android_id";
    @Deprecated
    public static final String BACKGROUND_DATA = "background_data";
    @Deprecated
    public static final String BLUETOOTH_ON = "bluetooth_on";
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DATA_ROAMING = "data_roaming";
    public static final String DEFAULT_INPUT_METHOD = "default_input_method";
    @Deprecated
    public static final String DEVELOPMENT_SETTINGS_ENABLED = "development_settings_enabled";
    @Deprecated
    public static final String DEVICE_PROVISIONED = "device_provisioned";
    public static final String ENABLED_ACCESSIBILITY_SERVICES = "enabled_accessibility_services";
    public static final String ENABLED_INPUT_METHODS = "enabled_input_methods";
    @Deprecated
    public static final String HTTP_PROXY = "http_proxy";
    public static final String INPUT_METHOD_SELECTOR_VISIBILITY = "input_method_selector_visibility";
    @Deprecated
    public static final String INSTALL_NON_MARKET_APPS = "install_non_market_apps";
    public static final String LOCATION_MODE = "location_mode";
    public static final int LOCATION_MODE_BATTERY_SAVING = 2;
    public static final int LOCATION_MODE_HIGH_ACCURACY = 3;
    public static final int LOCATION_MODE_OFF = 0;
    public static final int LOCATION_MODE_SENSORS_ONLY = 1;
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
    public static final String NETWORK_PREFERENCE = "network_preference";
    public static final String PARENTAL_CONTROL_ENABLED = "parental_control_enabled";
    public static final String PARENTAL_CONTROL_LAST_UPDATE = "parental_control_last_update";
    public static final String PARENTAL_CONTROL_REDIRECT_URL = "parental_control_redirect_url";
    public static final String SELECTED_INPUT_METHOD_SUBTYPE = "selected_input_method_subtype";
    public static final String SETTINGS_CLASSNAME = "settings_classname";
    public static final String SKIP_FIRST_USE_HINTS = "skip_first_use_hints";
    public static final String TOUCH_EXPLORATION_ENABLED = "touch_exploration_enabled";
    @Deprecated
    public static final String TTS_DEFAULT_COUNTRY = "tts_default_country";
    @Deprecated
    public static final String TTS_DEFAULT_LANG = "tts_default_lang";
    public static final String TTS_DEFAULT_PITCH = "tts_default_pitch";
    public static final String TTS_DEFAULT_RATE = "tts_default_rate";
    public static final String TTS_DEFAULT_SYNTH = "tts_default_synth";
    @Deprecated
    public static final String TTS_DEFAULT_VARIANT = "tts_default_variant";
    public static final String TTS_ENABLED_PLUGINS = "tts_enabled_plugins";
    @Deprecated
    public static final String TTS_USE_DEFAULTS = "tts_use_defaults";
    @Deprecated
    public static final String USB_MASS_STORAGE_ENABLED = "usb_mass_storage_enabled";
    @Deprecated
    public static final String USE_GOOGLE_MAIL = "use_google_mail";
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
    public static final String WIFI_WATCHDOG_WATCH_LIST = "wifi_watchdog_watch_list";
    
    public Secure() {
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
    
    @Deprecated
    public static final boolean isLocationProviderEnabled(final ContentResolver cr, final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final void setLocationProviderEnabled(final ContentResolver cr, final String provider, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
