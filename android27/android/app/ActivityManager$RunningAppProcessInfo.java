package android.app;

import android.content.*;
import android.os.*;

public static class RunningAppProcessInfo implements Parcelable
{
    public static final Creator<RunningAppProcessInfo> CREATOR;
    @Deprecated
    public static final int IMPORTANCE_BACKGROUND = 400;
    public static final int IMPORTANCE_CACHED = 400;
    @Deprecated
    public static final int IMPORTANCE_EMPTY = 500;
    public static final int IMPORTANCE_FOREGROUND = 100;
    public static final int IMPORTANCE_FOREGROUND_SERVICE = 125;
    public static final int IMPORTANCE_GONE = 1000;
    public static final int IMPORTANCE_PERCEPTIBLE = 230;
    public static final int IMPORTANCE_PERCEPTIBLE_PRE_26 = 130;
    public static final int IMPORTANCE_SERVICE = 300;
    public static final int IMPORTANCE_TOP_SLEEPING = 150;
    public static final int IMPORTANCE_VISIBLE = 200;
    public static final int REASON_PROVIDER_IN_USE = 1;
    public static final int REASON_SERVICE_IN_USE = 2;
    public static final int REASON_UNKNOWN = 0;
    public int importance;
    public int importanceReasonCode;
    public ComponentName importanceReasonComponent;
    public int importanceReasonPid;
    public int lastTrimLevel;
    public int lru;
    public int pid;
    public String[] pkgList;
    public String processName;
    public int uid;
    
    public RunningAppProcessInfo() {
        this.pkgList = null;
        throw new RuntimeException("Stub!");
    }
    
    public RunningAppProcessInfo(final String pProcessName, final int pPid, final String[] pArr) {
        this.pkgList = null;
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
