package android.app;

import android.content.*;
import android.os.*;

public static class RecentTaskInfo implements Parcelable
{
    public static final Creator<RecentTaskInfo> CREATOR;
    public int affiliatedTaskId;
    public ComponentName baseActivity;
    public Intent baseIntent;
    public CharSequence description;
    public int id;
    public int numActivities;
    public ComponentName origActivity;
    public int persistentId;
    public TaskDescription taskDescription;
    public ComponentName topActivity;
    
    public RecentTaskInfo() {
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
    
    public void readFromParcel(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
