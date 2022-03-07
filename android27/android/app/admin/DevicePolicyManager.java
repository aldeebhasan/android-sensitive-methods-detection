package android.app.admin;

import android.net.*;
import java.security.*;
import java.security.cert.*;
import android.content.pm.*;
import android.os.*;
import android.graphics.*;
import java.util.*;
import android.content.*;

public class DevicePolicyManager
{
    public static final String ACTION_ADD_DEVICE_ADMIN = "android.app.action.ADD_DEVICE_ADMIN";
    public static final String ACTION_APPLICATION_DELEGATION_SCOPES_CHANGED = "android.app.action.APPLICATION_DELEGATION_SCOPES_CHANGED";
    public static final String ACTION_DEVICE_ADMIN_SERVICE = "android.app.action.DEVICE_ADMIN_SERVICE";
    public static final String ACTION_DEVICE_OWNER_CHANGED = "android.app.action.DEVICE_OWNER_CHANGED";
    public static final String ACTION_MANAGED_PROFILE_PROVISIONED = "android.app.action.MANAGED_PROFILE_PROVISIONED";
    public static final String ACTION_PROVISIONING_SUCCESSFUL = "android.app.action.PROVISIONING_SUCCESSFUL";
    public static final String ACTION_PROVISION_MANAGED_DEVICE = "android.app.action.PROVISION_MANAGED_DEVICE";
    public static final String ACTION_PROVISION_MANAGED_PROFILE = "android.app.action.PROVISION_MANAGED_PROFILE";
    public static final String ACTION_SET_NEW_PARENT_PROFILE_PASSWORD = "android.app.action.SET_NEW_PARENT_PROFILE_PASSWORD";
    public static final String ACTION_SET_NEW_PASSWORD = "android.app.action.SET_NEW_PASSWORD";
    public static final String ACTION_START_ENCRYPTION = "android.app.action.START_ENCRYPTION";
    public static final String ACTION_SYSTEM_UPDATE_POLICY_CHANGED = "android.app.action.SYSTEM_UPDATE_POLICY_CHANGED";
    public static final String DELEGATION_APP_RESTRICTIONS = "delegation-app-restrictions";
    public static final String DELEGATION_BLOCK_UNINSTALL = "delegation-block-uninstall";
    public static final String DELEGATION_CERT_INSTALL = "delegation-cert-install";
    public static final String DELEGATION_ENABLE_SYSTEM_APP = "delegation-enable-system-app";
    public static final String DELEGATION_PACKAGE_ACCESS = "delegation-package-access";
    public static final String DELEGATION_PERMISSION_GRANT = "delegation-permission-grant";
    public static final int ENCRYPTION_STATUS_ACTIVATING = 2;
    public static final int ENCRYPTION_STATUS_ACTIVE = 3;
    public static final int ENCRYPTION_STATUS_ACTIVE_DEFAULT_KEY = 4;
    public static final int ENCRYPTION_STATUS_ACTIVE_PER_USER = 5;
    public static final int ENCRYPTION_STATUS_INACTIVE = 1;
    public static final int ENCRYPTION_STATUS_UNSUPPORTED = 0;
    public static final String EXTRA_ADD_EXPLANATION = "android.app.extra.ADD_EXPLANATION";
    public static final String EXTRA_DELEGATION_SCOPES = "android.app.extra.DELEGATION_SCOPES";
    public static final String EXTRA_DEVICE_ADMIN = "android.app.extra.DEVICE_ADMIN";
    public static final String EXTRA_PROVISIONING_ACCOUNT_TO_MIGRATE = "android.app.extra.PROVISIONING_ACCOUNT_TO_MIGRATE";
    public static final String EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE = "android.app.extra.PROVISIONING_ADMIN_EXTRAS_BUNDLE";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE = "android.app.extra.PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION";
    @Deprecated
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM";
    public static final String EXTRA_PROVISIONING_DISCLAIMERS = "android.app.extra.PROVISIONING_DISCLAIMERS";
    public static final String EXTRA_PROVISIONING_DISCLAIMER_CONTENT = "android.app.extra.PROVISIONING_DISCLAIMER_CONTENT";
    public static final String EXTRA_PROVISIONING_DISCLAIMER_HEADER = "android.app.extra.PROVISIONING_DISCLAIMER_HEADER";
    @Deprecated
    public static final String EXTRA_PROVISIONING_EMAIL_ADDRESS = "android.app.extra.PROVISIONING_EMAIL_ADDRESS";
    public static final String EXTRA_PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION = "android.app.extra.PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION";
    public static final String EXTRA_PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED = "android.app.extra.PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED";
    public static final String EXTRA_PROVISIONING_LOCALE = "android.app.extra.PROVISIONING_LOCALE";
    public static final String EXTRA_PROVISIONING_LOCAL_TIME = "android.app.extra.PROVISIONING_LOCAL_TIME";
    public static final String EXTRA_PROVISIONING_LOGO_URI = "android.app.extra.PROVISIONING_LOGO_URI";
    public static final String EXTRA_PROVISIONING_MAIN_COLOR = "android.app.extra.PROVISIONING_MAIN_COLOR";
    public static final String EXTRA_PROVISIONING_SKIP_ENCRYPTION = "android.app.extra.PROVISIONING_SKIP_ENCRYPTION";
    public static final String EXTRA_PROVISIONING_SKIP_USER_CONSENT = "android.app.extra.PROVISIONING_SKIP_USER_CONSENT";
    public static final String EXTRA_PROVISIONING_TIME_ZONE = "android.app.extra.PROVISIONING_TIME_ZONE";
    public static final String EXTRA_PROVISIONING_WIFI_HIDDEN = "android.app.extra.PROVISIONING_WIFI_HIDDEN";
    public static final String EXTRA_PROVISIONING_WIFI_PAC_URL = "android.app.extra.PROVISIONING_WIFI_PAC_URL";
    public static final String EXTRA_PROVISIONING_WIFI_PASSWORD = "android.app.extra.PROVISIONING_WIFI_PASSWORD";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_BYPASS = "android.app.extra.PROVISIONING_WIFI_PROXY_BYPASS";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_HOST = "android.app.extra.PROVISIONING_WIFI_PROXY_HOST";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_PORT = "android.app.extra.PROVISIONING_WIFI_PROXY_PORT";
    public static final String EXTRA_PROVISIONING_WIFI_SECURITY_TYPE = "android.app.extra.PROVISIONING_WIFI_SECURITY_TYPE";
    public static final String EXTRA_PROVISIONING_WIFI_SSID = "android.app.extra.PROVISIONING_WIFI_SSID";
    public static final int FLAG_EVICT_CREDENTIAL_ENCRYPTION_KEY = 1;
    public static final int FLAG_MANAGED_CAN_ACCESS_PARENT = 2;
    public static final int FLAG_PARENT_CAN_ACCESS_MANAGED = 1;
    public static final int KEYGUARD_DISABLE_FEATURES_ALL = Integer.MAX_VALUE;
    public static final int KEYGUARD_DISABLE_FEATURES_NONE = 0;
    public static final int KEYGUARD_DISABLE_FINGERPRINT = 32;
    public static final int KEYGUARD_DISABLE_REMOTE_INPUT = 64;
    public static final int KEYGUARD_DISABLE_SECURE_CAMERA = 2;
    public static final int KEYGUARD_DISABLE_SECURE_NOTIFICATIONS = 4;
    public static final int KEYGUARD_DISABLE_TRUST_AGENTS = 16;
    public static final int KEYGUARD_DISABLE_UNREDACTED_NOTIFICATIONS = 8;
    public static final int KEYGUARD_DISABLE_WIDGETS_ALL = 1;
    public static final String MIME_TYPE_PROVISIONING_NFC = "application/com.android.managedprovisioning";
    public static final int PASSWORD_QUALITY_ALPHABETIC = 262144;
    public static final int PASSWORD_QUALITY_ALPHANUMERIC = 327680;
    public static final int PASSWORD_QUALITY_BIOMETRIC_WEAK = 32768;
    public static final int PASSWORD_QUALITY_COMPLEX = 393216;
    public static final int PASSWORD_QUALITY_NUMERIC = 131072;
    public static final int PASSWORD_QUALITY_NUMERIC_COMPLEX = 196608;
    public static final int PASSWORD_QUALITY_SOMETHING = 65536;
    public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;
    public static final int PERMISSION_GRANT_STATE_DEFAULT = 0;
    public static final int PERMISSION_GRANT_STATE_DENIED = 2;
    public static final int PERMISSION_GRANT_STATE_GRANTED = 1;
    public static final int PERMISSION_POLICY_AUTO_DENY = 2;
    public static final int PERMISSION_POLICY_AUTO_GRANT = 1;
    public static final int PERMISSION_POLICY_PROMPT = 0;
    public static final String POLICY_DISABLE_CAMERA = "policy_disable_camera";
    public static final String POLICY_DISABLE_SCREEN_CAPTURE = "policy_disable_screen_capture";
    public static final int RESET_PASSWORD_DO_NOT_ASK_CREDENTIALS_ON_BOOT = 2;
    public static final int RESET_PASSWORD_REQUIRE_ENTRY = 1;
    public static final int SKIP_SETUP_WIZARD = 1;
    public static final int WIPE_EXTERNAL_STORAGE = 1;
    public static final int WIPE_RESET_PROTECTION_DATA = 2;
    
    DevicePolicyManager() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAdminActive(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public List<ComponentName> getActiveAdmins() {
        throw new RuntimeException("Stub!");
    }
    
    public void removeActiveAdmin(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasGrantedPolicy(final ComponentName admin, final int usesPolicy) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordQuality(final ComponentName admin, final int quality) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordQuality(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordMinimumLength(final ComponentName admin, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordMinimumLength(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordMinimumUpperCase(final ComponentName admin, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordMinimumUpperCase(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordMinimumLowerCase(final ComponentName admin, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordMinimumLowerCase(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordMinimumLetters(final ComponentName admin, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordMinimumLetters(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordMinimumNumeric(final ComponentName admin, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordMinimumNumeric(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordMinimumSymbols(final ComponentName admin, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordMinimumSymbols(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordMinimumNonLetter(final ComponentName admin, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordMinimumNonLetter(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordHistoryLength(final ComponentName admin, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPasswordExpirationTimeout(final ComponentName admin, final long timeout) {
        throw new RuntimeException("Stub!");
    }
    
    public long getPasswordExpirationTimeout(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public long getPasswordExpiration(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordHistoryLength(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPasswordMaximumLength(final int quality) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isActivePasswordSufficient() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCurrentFailedPasswordAttempts() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMaximumFailedPasswordsForWipe(final ComponentName admin, final int num) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaximumFailedPasswordsForWipe(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean resetPassword(final String password, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setResetPasswordToken(final ComponentName admin, final byte[] token) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clearResetPasswordToken(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isResetPasswordTokenActive(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean resetPasswordWithToken(final ComponentName admin, final String password, final byte[] token, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMaximumTimeToLock(final ComponentName admin, final long timeMs) {
        throw new RuntimeException("Stub!");
    }
    
    public long getMaximumTimeToLock(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRequiredStrongAuthTimeout(final ComponentName admin, final long timeoutMs) {
        throw new RuntimeException("Stub!");
    }
    
    public long getRequiredStrongAuthTimeout(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void lockNow() {
        throw new RuntimeException("Stub!");
    }
    
    public void lockNow(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void wipeData(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRecommendedGlobalProxy(final ComponentName admin, final ProxyInfo proxyInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public int setStorageEncryption(final ComponentName admin, final boolean encrypt) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getStorageEncryption(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public int getStorageEncryptionStatus() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean installCaCert(final ComponentName admin, final byte[] certBuffer) {
        throw new RuntimeException("Stub!");
    }
    
    public void uninstallCaCert(final ComponentName admin, final byte[] certBuffer) {
        throw new RuntimeException("Stub!");
    }
    
    public List<byte[]> getInstalledCaCerts(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void uninstallAllUserCaCerts(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasCaCertInstalled(final ComponentName admin, final byte[] certBuffer) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean installKeyPair(final ComponentName admin, final PrivateKey privKey, final Certificate cert, final String alias) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean installKeyPair(final ComponentName admin, final PrivateKey privKey, final Certificate[] certs, final String alias, final boolean requestAccess) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeKeyPair(final ComponentName admin, final String alias) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setCertInstallerPackage(final ComponentName admin, final String installerPackage) throws SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getCertInstallerPackage(final ComponentName admin) throws SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    public void setDelegatedScopes(final ComponentName admin, final String delegatePackage, final List<String> scopes) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getDelegatedScopes(final ComponentName admin, final String delegatedPackage) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getDelegatePackages(final ComponentName admin, final String delegationScope) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAlwaysOnVpnPackage(final ComponentName admin, final String vpnPackage, final boolean lockdownEnabled) throws PackageManager.NameNotFoundException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public String getAlwaysOnVpnPackage(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCameraDisabled(final ComponentName admin, final boolean disabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getCameraDisabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean requestBugreport(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setScreenCaptureDisabled(final ComponentName admin, final boolean disabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getScreenCaptureDisabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAutoTimeRequired(final ComponentName admin, final boolean required) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getAutoTimeRequired() {
        throw new RuntimeException("Stub!");
    }
    
    public void setKeyguardDisabledFeatures(final ComponentName admin, final int which) {
        throw new RuntimeException("Stub!");
    }
    
    public int getKeyguardDisabledFeatures(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDeviceOwnerApp(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void clearDeviceOwnerApp(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void clearProfileOwner(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDeviceOwnerLockScreenInfo(final ComponentName admin, final CharSequence info) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDeviceOwnerLockScreenInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] setPackagesSuspended(final ComponentName admin, final String[] packageNames, final boolean suspended) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPackageSuspended(final ComponentName admin, final String packageName) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public void setProfileEnabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProfileName(final ComponentName admin, final String profileName) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isProfileOwnerApp(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void addPersistentPreferredActivity(final ComponentName admin, final IntentFilter filter, final ComponentName activity) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearPackagePersistentPreferredActivities(final ComponentName admin, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setApplicationRestrictionsManagingPackage(final ComponentName admin, final String packageName) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getApplicationRestrictionsManagingPackage(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean isCallerApplicationRestrictionsManagingPackage() {
        throw new RuntimeException("Stub!");
    }
    
    public void setApplicationRestrictions(final ComponentName admin, final String packageName, final Bundle settings) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTrustAgentConfiguration(final ComponentName admin, final ComponentName target, final PersistableBundle configuration) {
        throw new RuntimeException("Stub!");
    }
    
    public List<PersistableBundle> getTrustAgentConfiguration(final ComponentName admin, final ComponentName agent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCrossProfileCallerIdDisabled(final ComponentName admin, final boolean disabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getCrossProfileCallerIdDisabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCrossProfileContactsSearchDisabled(final ComponentName admin, final boolean disabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getCrossProfileContactsSearchDisabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBluetoothContactSharingDisabled(final ComponentName admin, final boolean disabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getBluetoothContactSharingDisabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void addCrossProfileIntentFilter(final ComponentName admin, final IntentFilter filter, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearCrossProfileIntentFilters(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setPermittedAccessibilityServices(final ComponentName admin, final List<String> packageNames) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getPermittedAccessibilityServices(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setPermittedInputMethods(final ComponentName admin, final List<String> packageNames) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getPermittedInputMethods(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setPermittedCrossProfileNotificationListeners(final ComponentName admin, final List<String> packageList) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getPermittedCrossProfileNotificationListeners(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public UserHandle createAndManageUser(final ComponentName admin, final String name, final ComponentName profileOwner, final PersistableBundle adminExtras, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeUser(final ComponentName admin, final UserHandle userHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean switchUser(final ComponentName admin, final UserHandle userHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getApplicationRestrictions(final ComponentName admin, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void addUserRestriction(final ComponentName admin, final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearUserRestriction(final ComponentName admin, final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getUserRestrictions(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public Intent createAdminSupportIntent(final String restriction) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setApplicationHidden(final ComponentName admin, final String packageName, final boolean hidden) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isApplicationHidden(final ComponentName admin, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void enableSystemApp(final ComponentName admin, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public int enableSystemApp(final ComponentName admin, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAccountManagementDisabled(final ComponentName admin, final String accountType, final boolean disabled) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getAccountTypesWithManagementDisabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLockTaskPackages(final ComponentName admin, final String[] packages) throws SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getLockTaskPackages(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isLockTaskPermitted(final String pkg) {
        throw new RuntimeException("Stub!");
    }
    
    public void setGlobalSetting(final ComponentName admin, final String setting, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSecureSetting(final ComponentName admin, final String setting, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setRestrictionsProvider(final ComponentName admin, final ComponentName provider) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMasterVolumeMuted(final ComponentName admin, final boolean on) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isMasterVolumeMuted(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setUninstallBlocked(final ComponentName admin, final String packageName, final boolean uninstallBlocked) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUninstallBlocked(final ComponentName admin, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean addCrossProfileWidgetProvider(final ComponentName admin, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeCrossProfileWidgetProvider(final ComponentName admin, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getCrossProfileWidgetProviders(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setUserIcon(final ComponentName admin, final Bitmap icon) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSystemUpdatePolicy(final ComponentName admin, final SystemUpdatePolicy policy) {
        throw new RuntimeException("Stub!");
    }
    
    public SystemUpdatePolicy getSystemUpdatePolicy() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setKeyguardDisabled(final ComponentName admin, final boolean disabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setStatusBarDisabled(final ComponentName admin, final boolean disabled) {
        throw new RuntimeException("Stub!");
    }
    
    public SystemUpdateInfo getPendingSystemUpdate(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPermissionPolicy(final ComponentName admin, final int policy) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPermissionPolicy(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setPermissionGrantState(final ComponentName admin, final String packageName, final String permission, final int grantState) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPermissionGrantState(final ComponentName admin, final String packageName, final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isProvisioningAllowed(final String action) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isManagedProfile(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public String getWifiMacAddress(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void reboot(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setShortSupportMessage(final ComponentName admin, final CharSequence message) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getShortSupportMessage(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLongSupportMessage(final ComponentName admin, final CharSequence message) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLongSupportMessage(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public DevicePolicyManager getParentProfileInstance(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSecurityLoggingEnabled(final ComponentName admin, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSecurityLoggingEnabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public List<SecurityLog.SecurityEvent> retrieveSecurityLogs(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public List<SecurityLog.SecurityEvent> retrievePreRebootSecurityLogs(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOrganizationColor(final ComponentName admin, final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOrganizationColor(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOrganizationName(final ComponentName admin, final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getOrganizationName(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAffiliationIds(final ComponentName admin, final Set<String> ids) {
        throw new RuntimeException("Stub!");
    }
    
    public Set<String> getAffiliationIds(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBackupServiceEnabled(final ComponentName admin, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isBackupServiceEnabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public void setNetworkLoggingEnabled(final ComponentName admin, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isNetworkLoggingEnabled(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
    
    public List<NetworkEvent> retrieveNetworkLogs(final ComponentName admin, final long batchToken) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean bindDeviceAdminServiceAsUser(final ComponentName admin, final Intent serviceIntent, final ServiceConnection conn, final int flags, final UserHandle targetUser) {
        throw new RuntimeException("Stub!");
    }
    
    public List<UserHandle> getBindDeviceAdminTargetUsers(final ComponentName admin) {
        throw new RuntimeException("Stub!");
    }
}
