package android.provider;

import android.net.*;
import android.content.*;

public static final class Global extends NameValueTable
{
    public static final String ADB_ENABLED = "adb_enabled";
    public static final String AIRPLANE_MODE_ON = "airplane_mode_on";
    public static final String AIRPLANE_MODE_RADIOS = "airplane_mode_radios";
    public static final String ALWAYS_FINISH_ACTIVITIES = "always_finish_activities";
    public static final String ANIMATOR_DURATION_SCALE = "animator_duration_scale";
    public static final String AUTO_TIME = "auto_time";
    public static final String AUTO_TIME_ZONE = "auto_time_zone";
    public static final String BLUETOOTH_ON = "bluetooth_on";
    public static final String BOOT_COUNT = "boot_count";
    public static final String CONTACT_METADATA_SYNC_ENABLED = "contact_metadata_sync_enabled";
    public static final Uri CONTENT_URI;
    public static final String DATA_ROAMING = "data_roaming";
    public static final String DEBUG_APP = "debug_app";
    public static final String DEVELOPMENT_SETTINGS_ENABLED = "development_settings_enabled";
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_PROVISIONED = "device_provisioned";
    public static final String HTTP_PROXY = "http_proxy";
    @Deprecated
    public static final String INSTALL_NON_MARKET_APPS = "install_non_market_apps";
    public static final String MODE_RINGER = "mode_ringer";
    public static final String NETWORK_PREFERENCE = "network_preference";
    public static final String RADIO_BLUETOOTH = "bluetooth";
    public static final String RADIO_CELL = "cell";
    public static final String RADIO_NFC = "nfc";
    public static final String RADIO_WIFI = "wifi";
    @Deprecated
    public static final String SHOW_PROCESSES = "show_processes";
    public static final String STAY_ON_WHILE_PLUGGED_IN = "stay_on_while_plugged_in";
    public static final String TRANSITION_ANIMATION_SCALE = "transition_animation_scale";
    public static final String USB_MASS_STORAGE_ENABLED = "usb_mass_storage_enabled";
    public static final String USE_GOOGLE_MAIL = "use_google_mail";
    public static final String WAIT_FOR_DEBUGGER = "wait_for_debugger";
    public static final String WIFI_DEVICE_OWNER_CONFIGS_LOCKDOWN = "wifi_device_owner_configs_lockdown";
    public static final String WIFI_MAX_DHCP_RETRY_COUNT = "wifi_max_dhcp_retry_count";
    public static final String WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS = "wifi_mobile_data_transition_wakelock_timeout_ms";
    @Deprecated
    public static final String WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON = "wifi_networks_available_notification_on";
    public static final String WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY = "wifi_networks_available_repeat_delay";
    public static final String WIFI_NUM_OPEN_NETWORKS_KEPT = "wifi_num_open_networks_kept";
    public static final String WIFI_ON = "wifi_on";
    public static final String WIFI_SLEEP_POLICY = "wifi_sleep_policy";
    public static final int WIFI_SLEEP_POLICY_DEFAULT = 0;
    public static final int WIFI_SLEEP_POLICY_NEVER = 2;
    public static final int WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED = 1;
    public static final String WIFI_WATCHDOG_ON = "wifi_watchdog_on";
    public static final String WINDOW_ANIMATION_SCALE = "window_animation_scale";
    
    public Global() {
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
    
    static {
        CONTENT_URI = null;
    }
}
