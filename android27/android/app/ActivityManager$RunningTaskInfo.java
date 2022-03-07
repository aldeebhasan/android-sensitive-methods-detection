package android.app;

import android.content.*;
import android.graphics.*;
import android.os.*;

public static class RunningTaskInfo implements Parcelable
{
    public static final Creator<RunningTaskInfo> CREATOR;
    public ComponentName baseActivity;
    public CharSequence description;
    public int id;
    public int numActivities;
    public int numRunning;
    public Bitmap thumbnail;
    public ComponentName topActivity;
    
    public RunningTaskInfo() {
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
