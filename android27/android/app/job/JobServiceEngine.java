package android.app.job;

import android.app.*;
import android.os.*;

public abstract class JobServiceEngine
{
    public JobServiceEngine(final Service service) {
        throw new RuntimeException("Stub!");
    }
    
    public final IBinder getBinder() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean onStartJob(final JobParameters p0);
    
    public abstract boolean onStopJob(final JobParameters p0);
    
    public void jobFinished(final JobParameters params, final boolean needsReschedule) {
        throw new RuntimeException("Stub!");
    }
}
