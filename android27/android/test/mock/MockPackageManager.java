package android.test.mock;

import java.util.*;
import android.graphics.drawable.*;
import android.os.*;
import android.graphics.*;
import android.content.res.*;
import android.content.*;
import android.content.pm.*;

@Deprecated
public class MockPackageManager extends PackageManager
{
    public MockPackageManager() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public PackageInfo getPackageInfo(final String packageName, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public PackageInfo getPackageInfo(final VersionedPackage versionedPackage, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] currentToCanonicalPackageNames(final String[] names) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] canonicalToCurrentPackageNames(final String[] names) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Intent getLaunchIntentForPackage(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Intent getLeanbackLaunchIntentForPackage(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int[] getPackageGids(final String packageName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int[] getPackageGids(final String packageName, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getPackageUid(final String packageName, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public PermissionInfo getPermissionInfo(final String name, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<PermissionInfo> queryPermissionsByGroup(final String group, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public PermissionGroupInfo getPermissionGroupInfo(final String name, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<PermissionGroupInfo> getAllPermissionGroups(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ApplicationInfo getApplicationInfo(final String packageName, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ActivityInfo getActivityInfo(final ComponentName className, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ActivityInfo getReceiverInfo(final ComponentName className, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ServiceInfo getServiceInfo(final ComponentName className, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ProviderInfo getProviderInfo(final ComponentName className, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<PackageInfo> getInstalledPackages(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<PackageInfo> getPackagesHoldingPermissions(final String[] permissions, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkPermission(final String permName, final String pkgName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean canRequestPackageInstalls() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isPermissionRevokedByPolicy(final String permName, final String pkgName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean addPermission(final PermissionInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean addPermissionAsync(final PermissionInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void removePermission(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkSignatures(final String pkg1, final String pkg2) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int checkSignatures(final int uid1, final int uid2) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] getPackagesForUid(final int uid) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getNameForUid(final int uid) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<ApplicationInfo> getInstalledApplications(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ResolveInfo resolveActivity(final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<ResolveInfo> queryIntentActivities(final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<ResolveInfo> queryIntentActivityOptions(final ComponentName caller, final Intent[] specifics, final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<ResolveInfo> queryBroadcastReceivers(final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ResolveInfo resolveService(final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<ResolveInfo> queryIntentServices(final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<ResolveInfo> queryIntentContentProviders(final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ProviderInfo resolveContentProvider(final String name, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<ProviderInfo> queryContentProviders(final String processName, final int uid, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public InstrumentationInfo getInstrumentationInfo(final ComponentName className, final int flags) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<InstrumentationInfo> queryInstrumentation(final String targetPackage, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getDrawable(final String packageName, final int resid, final ApplicationInfo appInfo) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getActivityIcon(final ComponentName activityName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getActivityIcon(final Intent intent) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getDefaultActivityIcon() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getActivityBanner(final ComponentName activityName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getActivityBanner(final Intent intent) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getApplicationBanner(final ApplicationInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getApplicationBanner(final String packageName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getApplicationIcon(final ApplicationInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getApplicationIcon(final String packageName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getActivityLogo(final ComponentName activityName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getActivityLogo(final Intent intent) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getApplicationLogo(final ApplicationInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getApplicationLogo(final String packageName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getUserBadgedIcon(final Drawable icon, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getUserBadgedDrawableForDensity(final Drawable drawable, final UserHandle user, final Rect badgeLocation, final int badgeDensity) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getUserBadgedLabel(final CharSequence label, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getText(final String packageName, final int resid, final ApplicationInfo appInfo) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public XmlResourceParser getXml(final String packageName, final int resid, final ApplicationInfo appInfo) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getApplicationLabel(final ApplicationInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Resources getResourcesForActivity(final ComponentName activityName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Resources getResourcesForApplication(final ApplicationInfo app) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Resources getResourcesForApplication(final String appPackageName) throws NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public PackageInfo getPackageArchiveInfo(final String archiveFilePath, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setInstallerPackageName(final String targetPackage, final String installerPackageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getInstallerPackageName(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void addPackageToPreferred(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void removePackageFromPreferred(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<PackageInfo> getPreferredPackages(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setComponentEnabledSetting(final ComponentName componentName, final int newState, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getComponentEnabledSetting(final ComponentName componentName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setApplicationEnabledSetting(final String packageName, final int newState, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getApplicationEnabledSetting(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void addPreferredActivity(final IntentFilter filter, final int match, final ComponentName[] set, final ComponentName activity) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void clearPackagePreferredActivities(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getPreferredActivities(final List<IntentFilter> outFilters, final List<ComponentName> outActivities, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] getSystemSharedLibraryNames() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<SharedLibraryInfo> getSharedLibraries(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public FeatureInfo[] getSystemAvailableFeatures() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean hasSystemFeature(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean hasSystemFeature(final String name, final int version) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isSafeMode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void verifyPendingInstall(final int id, final int verificationCode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void extendVerificationTimeout(final int id, final int verificationCodeAtTimeout, final long millisecondsToDelay) {
        throw new RuntimeException("Stub!");
    }
    
    public List<IntentFilter> getAllIntentFilters(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public byte[] getInstantAppCookie() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isInstantApp() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isInstantApp(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getInstantAppCookieMaxBytes() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void clearInstantAppCookie() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateInstantAppCookie(final byte[] cookie) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ChangedPackages getChangedPackages(final int sequenceNumber) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setApplicationCategoryHint(final String packageName, final int categoryHint) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public PackageInstaller getPackageInstaller() {
        throw new RuntimeException("Stub!");
    }
}
