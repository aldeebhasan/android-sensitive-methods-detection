package android.app.usage;

import android.os.*;

public final class UsageStats implements Parcelable
{
    public static final Creator<UsageStats> CREATOR;
    
    public UsageStats(final UsageStats stats) {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public long getFirstTimeStamp() {
        throw new RuntimeException("Stub!");
    }
    
    public long getLastTimeStamp() {
        throw new RuntimeException("Stub!");
    }
    
    public long getLastTimeUsed() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTotalTimeInForeground() {
        throw new RuntimeException("Stub!");
    }
    
    public void add(final UsageStats right) {
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
