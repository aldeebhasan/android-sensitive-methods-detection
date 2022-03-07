package android.app;

import java.util.*;
import android.util.*;
import android.graphics.*;
import android.content.pm.*;
import java.io.*;
import android.os.*;
import android.content.*;

public class ActivityManager
{
    public static final String ACTION_REPORT_HEAP_LIMIT = "android.app.action.REPORT_HEAP_LIMIT";
    public static final int LOCK_TASK_MODE_LOCKED = 1;
    public static final int LOCK_TASK_MODE_NONE = 0;
    public static final int LOCK_TASK_MODE_PINNED = 2;
    public static final String META_HOME_ALTERNATE = "android.app.home.alternate";
    public static final int MOVE_TASK_NO_USER_ACTION = 2;
    public static final int MOVE_TASK_WITH_HOME = 1;
    public static final int RECENT_IGNORE_UNAVAILABLE = 2;
    public static final int RECENT_WITH_EXCLUDED = 1;
    
    ActivityManager() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMemoryClass() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLargeMemoryClass() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isLowRamDevice() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public List<RecentTaskInfo> getRecentTasks(final int maxNum, final int flags) throws SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    public List<AppTask> getAppTasks() {
        throw new RuntimeException("Stub!");
    }
    
    public Size getAppTaskThumbnailSize() {
        throw new RuntimeException("Stub!");
    }
    
    public int addAppTask(final Activity activity, final Intent intent, final TaskDescription description, final Bitmap thumbnail) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public List<RunningTaskInfo> getRunningTasks(final int maxNum) throws SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    public void moveTaskToFront(final int taskId, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void moveTaskToFront(final int taskId, final int flags, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public List<RunningServiceInfo> getRunningServices(final int maxNum) throws SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    public PendingIntent getRunningServiceControlPanel(final ComponentName service) throws SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    public void getMemoryInfo(final MemoryInfo outInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clearApplicationUserData() {
        throw new RuntimeException("Stub!");
    }
    
    public List<ProcessErrorStateInfo> getProcessesInErrorState() {
        throw new RuntimeException("Stub!");
    }
    
    public List<RunningAppProcessInfo> getRunningAppProcesses() {
        throw new RuntimeException("Stub!");
    }
    
    public static void getMyMemoryState(final RunningAppProcessInfo outState) {
        throw new RuntimeException("Stub!");
    }
    
    public Debug.MemoryInfo[] getProcessMemoryInfo(final int[] pids) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void restartPackage(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void killBackgroundProcesses(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public ConfigurationInfo getDeviceConfigurationInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLauncherLargeIconDensity() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLauncherLargeIconSize() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isUserAMonkey() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isRunningInTestHarness() {
        throw new RuntimeException("Stub!");
    }
    
    public void dumpPackageState(final FileDescriptor fd, final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void setWatchHeapLimit(final long pssSize) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearWatchHeapLimit() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean isInLockTaskMode() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLockTaskModeState() {
        throw new RuntimeException("Stub!");
    }
    
    public static void setVrThread(final int tid) {
        throw new RuntimeException("Stub!");
    }
    
    public static class TaskDescription implements Parcelable
    {
        public static final Creator<TaskDescription> CREATOR;
        
        public TaskDescription(final String label, final Bitmap icon, final int colorPrimary) {
            throw new RuntimeException("Stub!");
        }
        
        public TaskDescription(final String label, final Bitmap icon) {
            throw new RuntimeException("Stub!");
        }
        
        public TaskDescription(final String label) {
            throw new RuntimeException("Stub!");
        }
        
        public TaskDescription() {
            throw new RuntimeException("Stub!");
        }
        
        public TaskDescription(final TaskDescription td) {
            throw new RuntimeException("Stub!");
        }
        
        public String getLabel() {
            throw new RuntimeException("Stub!");
        }
        
        public Bitmap getIcon() {
            throw new RuntimeException("Stub!");
        }
        
        public int getPrimaryColor() {
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
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
    
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
    
    public static class MemoryInfo implements Parcelable
    {
        public static final Creator<MemoryInfo> CREATOR;
        public long availMem;
        public boolean lowMemory;
        public long threshold;
        public long totalMem;
        
        public MemoryInfo() {
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
    
    public static class AppTask
    {
        AppTask() {
            throw new RuntimeException("Stub!");
        }
        
        public void finishAndRemoveTask() {
            throw new RuntimeException("Stub!");
        }
        
        public RecentTaskInfo getTaskInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public void moveToFront() {
            throw new RuntimeException("Stub!");
        }
        
        public void startActivity(final Context context, final Intent intent, final Bundle options) {
            throw new RuntimeException("Stub!");
        }
        
        public void setExcludeFromRecents(final boolean exclude) {
            throw new RuntimeException("Stub!");
        }
    }
}
