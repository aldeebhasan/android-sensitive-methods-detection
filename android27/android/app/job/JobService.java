package android.app.job;

import android.app.*;
import android.content.*;
import android.os.*;

public abstract class JobService extends Service
{
    public static final String PERMISSION_BIND = "android.permission.BIND_JOB_SERVICE";
    
    public JobService() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean onStartJob(final JobParameters p0);
    
    public abstract boolean onStopJob(final JobParameters p0);
    
    public final void jobFinished(final JobParameters params, final boolean needsReschedule) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
