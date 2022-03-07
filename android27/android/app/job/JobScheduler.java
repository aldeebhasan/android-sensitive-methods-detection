package android.app.job;

import java.util.*;

public abstract class JobScheduler
{
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    
    public JobScheduler() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int schedule(final JobInfo p0);
    
    public abstract int enqueue(final JobInfo p0, final JobWorkItem p1);
    
    public abstract void cancel(final int p0);
    
    public abstract void cancelAll();
    
    public abstract List<JobInfo> getAllPendingJobs();
    
    public abstract JobInfo getPendingJob(final int p0);
}
