package android.content.pm;

import android.os.*;

public final class FeatureGroupInfo implements Parcelable
{
    public static final Creator<FeatureGroupInfo> CREATOR;
    public FeatureInfo[] features;
    
    public FeatureGroupInfo() {
        this.features = null;
        throw new RuntimeException("Stub!");
    }
    
    public FeatureGroupInfo(final FeatureGroupInfo other) {
        this.features = null;
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
