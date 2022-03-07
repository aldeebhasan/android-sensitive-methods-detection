package android.app;

import android.content.*;
import android.os.*;

public static class RunningServiceInfo implements Parcelable
{
    public static final Creator<RunningServiceInfo> CREATOR;
    public static final int FLAG_FOREGROUND = 2;
    public static final int FLAG_PERSISTENT_PROCESS = 8;
    public static final int FLAG_STARTED = 1;
    public static final int FLAG_SYSTEM_PROCESS = 4;
    public long activeSince;
    public int clientCount;
    public int clientLabel;
    public String clientPackage;
    public int crashCount;
    public int flags;
    public boolean foreground;
    public long lastActivityTime;
    public int pid;
    public String process;
    public long restarting;
    public ComponentName service;
    public boolean started;
    public int uid;
    
    public RunningServiceInfo() {
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
