package android.content.pm;

import android.os.*;

public class PackageInfo implements Parcelable
{
    public static final Creator<PackageInfo> CREATOR;
    public static final int INSTALL_LOCATION_AUTO = 0;
    public static final int INSTALL_LOCATION_INTERNAL_ONLY = 1;
    public static final int INSTALL_LOCATION_PREFER_EXTERNAL = 2;
    public static final int REQUESTED_PERMISSION_GRANTED = 2;
    public ActivityInfo[] activities;
    public ApplicationInfo applicationInfo;
    public int baseRevisionCode;
    public ConfigurationInfo[] configPreferences;
    public FeatureGroupInfo[] featureGroups;
    public long firstInstallTime;
    public int[] gids;
    public int installLocation;
    public InstrumentationInfo[] instrumentation;
    public long lastUpdateTime;
    public String packageName;
    public PermissionInfo[] permissions;
    public ProviderInfo[] providers;
    public ActivityInfo[] receivers;
    public FeatureInfo[] reqFeatures;
    public String[] requestedPermissions;
    public int[] requestedPermissionsFlags;
    public ServiceInfo[] services;
    public String sharedUserId;
    public int sharedUserLabel;
    public Signature[] signatures;
    public String[] splitNames;
    public int[] splitRevisionCodes;
    public int versionCode;
    public String versionName;
    
    public PackageInfo() {
        this.activities = null;
        this.configPreferences = null;
        this.featureGroups = null;
        this.gids = null;
        this.instrumentation = null;
        this.permissions = null;
        this.providers = null;
        this.receivers = null;
        this.reqFeatures = null;
        this.requestedPermissions = null;
        this.requestedPermissionsFlags = null;
        this.services = null;
        this.signatures = null;
        this.splitNames = null;
        this.splitRevisionCodes = null;
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
