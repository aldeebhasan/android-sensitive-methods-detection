package android.content.pm;

import android.os.*;

public class InstrumentationInfo extends PackageItemInfo implements Parcelable
{
    public static final Creator<InstrumentationInfo> CREATOR;
    public String dataDir;
    public boolean functionalTest;
    public boolean handleProfiling;
    public String publicSourceDir;
    public String sourceDir;
    public String[] splitNames;
    public String[] splitPublicSourceDirs;
    public String[] splitSourceDirs;
    public String targetPackage;
    public String targetProcesses;
    
    public InstrumentationInfo() {
        this.splitNames = null;
        this.splitPublicSourceDirs = null;
        this.splitSourceDirs = null;
        throw new RuntimeException("Stub!");
    }
    
    public InstrumentationInfo(final InstrumentationInfo orig) {
        this.splitNames = null;
        this.splitPublicSourceDirs = null;
        this.splitSourceDirs = null;
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
