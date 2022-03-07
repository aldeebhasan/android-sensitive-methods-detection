package java.util.concurrent;

import java.util.*;

public interface ExecutorService extends Executor
{
    void shutdown();
    
    List<Runnable> shutdownNow();
    
    boolean isShutdown();
    
    boolean isTerminated();
    
    boolean awaitTermination(final long p0, final TimeUnit p1) throws InterruptedException;
    
     <T> Future<T> submit(final Callable<T> p0);
    
     <T> Future<T> submit(final Runnable p0, final T p1);
    
    Future<?> submit(final Runnable p0);
    
     <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> p0) throws InterruptedException;
    
     <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> p0, final long p1, final TimeUnit p2) throws InterruptedException;
    
     <T> T invokeAny(final Collection<? extends Callable<T>> p0) throws InterruptedException, ExecutionException;
    
     <T> T invokeAny(final Collection<? extends Callable<T>> p0, final long p1, final TimeUnit p2) throws InterruptedException, ExecutionException, TimeoutException;
}
