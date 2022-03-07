package android.os;

import android.content.*;
import java.util.*;

public class UserManager
{
    public static final String ALLOW_PARENT_PROFILE_APP_LINKING = "allow_parent_profile_app_linking";
    public static final String DISALLOW_ADD_MANAGED_PROFILE = "no_add_managed_profile";
    public static final String DISALLOW_ADD_USER = "no_add_user";
    public static final String DISALLOW_ADJUST_VOLUME = "no_adjust_volume";
    public static final String DISALLOW_APPS_CONTROL = "no_control_apps";
    public static final String DISALLOW_AUTOFILL = "no_autofill";
    public static final String DISALLOW_BLUETOOTH = "no_bluetooth";
    public static final String DISALLOW_BLUETOOTH_SHARING = "no_bluetooth_sharing";
    public static final String DISALLOW_CONFIG_BLUETOOTH = "no_config_bluetooth";
    public static final String DISALLOW_CONFIG_CELL_BROADCASTS = "no_config_cell_broadcasts";
    public static final String DISALLOW_CONFIG_CREDENTIALS = "no_config_credentials";
    public static final String DISALLOW_CONFIG_MOBILE_NETWORKS = "no_config_mobile_networks";
    public static final String DISALLOW_CONFIG_TETHERING = "no_config_tethering";
    public static final String DISALLOW_CONFIG_VPN = "no_config_vpn";
    public static final String DISALLOW_CONFIG_WIFI = "no_config_wifi";
    public static final String DISALLOW_CREATE_WINDOWS = "no_create_windows";
    public static final String DISALLOW_CROSS_PROFILE_COPY_PASTE = "no_cross_profile_copy_paste";
    public static final String DISALLOW_DATA_ROAMING = "no_data_roaming";
    public static final String DISALLOW_DEBUGGING_FEATURES = "no_debugging_features";
    public static final String DISALLOW_FACTORY_RESET = "no_factory_reset";
    public static final String DISALLOW_FUN = "no_fun";
    public static final String DISALLOW_INSTALL_APPS = "no_install_apps";
    public static final String DISALLOW_INSTALL_UNKNOWN_SOURCES = "no_install_unknown_sources";
    public static final String DISALLOW_MODIFY_ACCOUNTS = "no_modify_accounts";
    public static final String DISALLOW_MOUNT_PHYSICAL_MEDIA = "no_physical_media";
    public static final String DISALLOW_NETWORK_RESET = "no_network_reset";
    public static final String DISALLOW_OUTGOING_BEAM = "no_outgoing_beam";
    public static final String DISALLOW_OUTGOING_CALLS = "no_outgoing_calls";
    public static final String DISALLOW_REMOVE_MANAGED_PROFILE = "no_remove_managed_profile";
    public static final String DISALLOW_REMOVE_USER = "no_remove_user";
    public static final String DISALLOW_SAFE_BOOT = "no_safe_boot";
    public static final String DISALLOW_SET_USER_ICON = "no_set_user_icon";
    public static final String DISALLOW_SET_WALLPAPER = "no_set_wallpaper";
    public static final String DISALLOW_SHARE_LOCATION = "no_share_location";
    public static final String DISALLOW_SMS = "no_sms";
    public static final String DISALLOW_UNINSTALL_APPS = "no_uninstall_apps";
    public static final String DISALLOW_UNMUTE_MICROPHONE = "no_unmute_microphone";
    public static final String DISALLOW_USB_FILE_TRANSFER = "no_usb_file_transfer";
    public static final String ENSURE_VERIFY_APPS = "ensure_verify_apps";
    public static final String KEY_RESTRICTIONS_PENDING = "restrictions_pending";
    public static final int USER_CREATION_FAILED_NOT_PERMITTED = 1;
    public static final int USER_CREATION_FAILED_NO_MORE_USERS = 2;
    
    UserManager() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean supportsMultipleUsers() {
        throw new RuntimeException("Stub!");
    }
    
    public String getUserName() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserAGoat() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSystemUser() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDemoUser() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserRunning(final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserRunningOrStopping(final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserUnlocked() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserUnlocked(final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getUserRestrictions() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getUserRestrictions(final UserHandle userHandle) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setUserRestrictions(final Bundle restrictions) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setUserRestrictions(final Bundle restrictions, final UserHandle userHandle) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setUserRestriction(final String key, final boolean value) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasUserRestriction(final String restrictionKey) {
        throw new RuntimeException("Stub!");
    }
    
    public long getSerialNumberForUser(final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public UserHandle getUserForSerialNumber(final long serialNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static Intent createUserCreationIntent(final String userName, final String accountName, final String accountType, final PersistableBundle accountOptions) {
        throw new RuntimeException("Stub!");
    }
    
    public int getUserCount() {
        throw new RuntimeException("Stub!");
    }
    
    public List<UserHandle> getUserProfiles() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isQuietModeEnabled(final UserHandle userHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getApplicationRestrictions(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean setRestrictionsChallenge(final String newPin) {
        throw new RuntimeException("Stub!");
    }
    
    public long getUserCreationTime(final UserHandle userHandle) {
        throw new RuntimeException("Stub!");
    }
}
