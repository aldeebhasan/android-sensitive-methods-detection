package android.app.job;

import android.content.*;
import android.net.*;
import android.os.*;

public class JobParameters implements Parcelable
{
    public static final Creator<JobParameters> CREATOR;
    
    JobParameters() {
        throw new RuntimeException("Stub!");
    }
    
    public int getJobId() {
        throw new RuntimeException("Stub!");
    }
    
    public PersistableBundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getTransientExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public ClipData getClipData() {
        throw new RuntimeException("Stub!");
    }
    
    public int getClipGrantFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOverrideDeadlineExpired() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri[] getTriggeredContentUris() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getTriggeredContentAuthorities() {
        throw new RuntimeException("Stub!");
    }
    
    public JobWorkItem dequeueWork() {
        throw new RuntimeException("Stub!");
    }
    
    public void completeWork(final JobWorkItem work) {
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
