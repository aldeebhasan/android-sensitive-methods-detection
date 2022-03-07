package android.os;

import java.util.concurrent.*;

public abstract class AsyncTask<Params, Progress, Result>
{
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR;
    
    public AsyncTask() {
        throw new RuntimeException("Stub!");
    }
    
    public final Status getStatus() {
        throw new RuntimeException("Stub!");
    }
    
    protected abstract Result doInBackground(final Params... p0);
    
    protected void onPreExecute() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onPostExecute(final Result result) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onProgressUpdate(final Progress... values) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onCancelled(final Result result) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onCancelled() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isCancelled() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean cancel(final boolean mayInterruptIfRunning) {
        throw new RuntimeException("Stub!");
    }
    
    public final Result get() throws InterruptedException, ExecutionException {
        throw new RuntimeException("Stub!");
    }
    
    public final Result get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new RuntimeException("Stub!");
    }
    
    public final AsyncTask<Params, Progress, Result> execute(final Params... params) {
        throw new RuntimeException("Stub!");
    }
    
    public final AsyncTask<Params, Progress, Result> executeOnExecutor(final Executor exec, final Params... params) {
        throw new RuntimeException("Stub!");
    }
    
    public static void execute(final Runnable runnable) {
        throw new RuntimeException("Stub!");
    }
    
    protected final void publishProgress(final Progress... values) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        SERIAL_EXECUTOR = null;
        THREAD_POOL_EXECUTOR = null;
    }
    
    public enum Status
    {
        FINISHED, 
        PENDING, 
        RUNNING;
    }
}
