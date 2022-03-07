package android.app;

import android.os.*;

public static final class AlarmClockInfo implements Parcelable
{
    public static final Creator<AlarmClockInfo> CREATOR;
    
    public AlarmClockInfo(final long triggerTime, final PendingIntent showIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public long getTriggerTime() {
        throw new RuntimeException("Stub!");
    }
    
    public PendingIntent getShowIntent() {
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
