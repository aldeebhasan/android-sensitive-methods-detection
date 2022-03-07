package android.content.pm;

import android.os.*;

public class ConfigurationInfo implements Parcelable
{
    public static final Creator<ConfigurationInfo> CREATOR;
    public static final int GL_ES_VERSION_UNDEFINED = 0;
    public static final int INPUT_FEATURE_FIVE_WAY_NAV = 2;
    public static final int INPUT_FEATURE_HARD_KEYBOARD = 1;
    public int reqGlEsVersion;
    public int reqInputFeatures;
    public int reqKeyboardType;
    public int reqNavigation;
    public int reqTouchScreen;
    
    public ConfigurationInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public ConfigurationInfo(final ConfigurationInfo orig) {
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
    
    public String getGlEsVersion() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
