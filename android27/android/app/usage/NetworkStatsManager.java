package android.app.usage;

import android.os.*;

public class NetworkStatsManager
{
    NetworkStatsManager() {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkStats.Bucket querySummaryForDevice(final int networkType, final String subscriberId, final long startTime, final long endTime) throws SecurityException, RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkStats.Bucket querySummaryForUser(final int networkType, final String subscriberId, final long startTime, final long endTime) throws SecurityException, RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkStats querySummary(final int networkType, final String subscriberId, final long startTime, final long endTime) throws SecurityException, RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkStats queryDetailsForUid(final int networkType, final String subscriberId, final long startTime, final long endTime, final int uid) throws SecurityException, RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkStats queryDetailsForUidTag(final int networkType, final String subscriberId, final long startTime, final long endTime, final int uid, final int tag) throws SecurityException {
        throw new RuntimeException("Stub!");
    }
    
    public NetworkStats queryDetails(final int networkType, final String subscriberId, final long startTime, final long endTime) throws SecurityException, RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public void registerUsageCallback(final int networkType, final String subscriberId, final long thresholdBytes, final UsageCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerUsageCallback(final int networkType, final String subscriberId, final long thresholdBytes, final UsageCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterUsageCallback(final UsageCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class UsageCallback
    {
        public UsageCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onThresholdReached(final int p0, final String p1);
    }
}
