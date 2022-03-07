package java.util.concurrent;

public interface Future<V>
{
    boolean cancel(final boolean p0);
    
    boolean isCancelled();
    
    boolean isDone();
    
    V get() throws InterruptedException, ExecutionException;
    
    V get(final long p0, final TimeUnit p1) throws InterruptedException, ExecutionException, TimeoutException;
}
