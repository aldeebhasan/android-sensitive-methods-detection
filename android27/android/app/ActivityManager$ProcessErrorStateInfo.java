package android.app;

import android.os.*;

public static class ProcessErrorStateInfo implements Parcelable
{
    public static final int CRASHED = 1;
    public static final Creator<ProcessErrorStateInfo> CREATOR;
    public static final int NOT_RESPONDING = 2;
    public static final int NO_ERROR = 0;
    public int condition;
    public byte[] crashData;
    public String longMsg;
    public int pid;
    public String processName;
    public String shortMsg;
    public String stackTrace;
    public String tag;
    public int uid;
    
    public ProcessErrorStateInfo() {
        this.crashData = null;
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
