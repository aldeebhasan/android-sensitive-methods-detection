package android.app.job;

import android.os.*;
import android.content.*;

public static final class Builder
{
    public Builder(final int jobId, final ComponentName jobService) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setExtras(final PersistableBundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setTransientExtras(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setClipData(final ClipData clip, final int grantFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setRequiredNetworkType(final int networkType) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setRequiresCharging(final boolean requiresCharging) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setRequiresBatteryNotLow(final boolean batteryNotLow) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setRequiresDeviceIdle(final boolean requiresDeviceIdle) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setRequiresStorageNotLow(final boolean storageNotLow) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder addTriggerContentUri(final TriggerContentUri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setTriggerContentUpdateDelay(final long durationMs) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setTriggerContentMaxDelay(final long durationMs) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setPeriodic(final long intervalMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setPeriodic(final long intervalMillis, final long flexMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setMinimumLatency(final long minLatencyMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setOverrideDeadline(final long maxExecutionDelayMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setBackoffCriteria(final long initialBackoffMillis, final int backoffPolicy) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setPersisted(final boolean isPersisted) {
        throw new RuntimeException("Stub!");
    }
    
    public JobInfo build() {
        throw new RuntimeException("Stub!");
    }
}
