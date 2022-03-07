package android.app.usage;

import java.util.*;

public final class UsageStatsManager
{
    public static final int INTERVAL_BEST = 4;
    public static final int INTERVAL_DAILY = 0;
    public static final int INTERVAL_MONTHLY = 2;
    public static final int INTERVAL_WEEKLY = 1;
    public static final int INTERVAL_YEARLY = 3;
    
    UsageStatsManager() {
        throw new RuntimeException("Stub!");
    }
    
    public List<UsageStats> queryUsageStats(final int intervalType, final long beginTime, final long endTime) {
        throw new RuntimeException("Stub!");
    }
    
    public List<ConfigurationStats> queryConfigurations(final int intervalType, final long beginTime, final long endTime) {
        throw new RuntimeException("Stub!");
    }
    
    public UsageEvents queryEvents(final long beginTime, final long endTime) {
        throw new RuntimeException("Stub!");
    }
    
    public Map<String, UsageStats> queryAndAggregateUsageStats(final long beginTime, final long endTime) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAppInactive(final String packageName) {
        throw new RuntimeException("Stub!");
    }
}
