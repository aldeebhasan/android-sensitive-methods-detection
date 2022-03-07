package java.util.concurrent;

import java.util.concurrent.atomic.*;
import java.util.*;
import sun.security.util.*;
import java.security.*;

public class Executors
{
    public static ExecutorService newFixedThreadPool(final int n) {
        return new ThreadPoolExecutor(n, n, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }
    
    public static ExecutorService newWorkStealingPool(final int n) {
        return new ForkJoinPool(n, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    }
    
    public static ExecutorService newWorkStealingPool() {
        return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    }
    
    public static ExecutorService newFixedThreadPool(final int n, final ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(n, n, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
    }
    
    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
    }
    
    public static ExecutorService newSingleThreadExecutor(final ThreadFactory threadFactory) {
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory));
    }
    
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }
    
    public static ExecutorService newCachedThreadPool(final ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
    }
    
    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1));
    }
    
    public static ScheduledExecutorService newSingleThreadScheduledExecutor(final ThreadFactory threadFactory) {
        return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1, threadFactory));
    }
    
    public static ScheduledExecutorService newScheduledThreadPool(final int n) {
        return new ScheduledThreadPoolExecutor(n);
    }
    
    public static ScheduledExecutorService newScheduledThreadPool(final int n, final ThreadFactory threadFactory) {
        return new ScheduledThreadPoolExecutor(n, threadFactory);
    }
    
    public static ExecutorService unconfigurableExecutorService(final ExecutorService executorService) {
        if (executorService == null) {
            throw new NullPointerException();
        }
        return new DelegatedExecutorService(executorService);
    }
    
    public static ScheduledExecutorService unconfigurableScheduledExecutorService(final ScheduledExecutorService scheduledExecutorService) {
        if (scheduledExecutorService == null) {
            throw new NullPointerException();
        }
        return new DelegatedScheduledExecutorService(scheduledExecutorService);
    }
    
    public static ThreadFactory defaultThreadFactory() {
        return new DefaultThreadFactory();
    }
    
    public static ThreadFactory privilegedThreadFactory() {
        return new PrivilegedThreadFactory();
    }
    
    public static <T> Callable<T> callable(final Runnable runnable, final T t) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        return new RunnableAdapter<T>(runnable, t);
    }
    
    public static Callable<Object> callable(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        return new RunnableAdapter<Object>(runnable, null);
    }
    
    public static Callable<Object> callable(final PrivilegedAction<?> privilegedAction) {
        if (privilegedAction == null) {
            throw new NullPointerException();
        }
        return new Callable<Object>() {
            @Override
            public Object call() {
                return privilegedAction.run();
            }
        };
    }
    
    public static Callable<Object> callable(final PrivilegedExceptionAction<?> privilegedExceptionAction) {
        if (privilegedExceptionAction == null) {
            throw new NullPointerException();
        }
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return privilegedExceptionAction.run();
            }
        };
    }
    
    public static <T> Callable<T> privilegedCallable(final Callable<T> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        return new PrivilegedCallable<T>(callable);
    }
    
    public static <T> Callable<T> privilegedCallableUsingCurrentClassLoader(final Callable<T> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        return new PrivilegedCallableUsingCurrentClassLoader<T>(callable);
    }
    
    static class DefaultThreadFactory implements ThreadFactory
    {
        private static final AtomicInteger poolNumber;
        private final ThreadGroup group;
        private final AtomicInteger threadNumber;
        private final String namePrefix;
        
        DefaultThreadFactory() {
            this.threadNumber = new AtomicInteger(1);
            final SecurityManager securityManager = System.getSecurityManager();
            this.group = ((securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup());
            this.namePrefix = "pool-" + DefaultThreadFactory.poolNumber.getAndIncrement() + "-thread-";
        }
        
        @Override
        public Thread newThread(final Runnable runnable) {
            final Thread thread = new Thread(this.group, runnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != 5) {
                thread.setPriority(5);
            }
            return thread;
        }
        
        static {
            poolNumber = new AtomicInteger(1);
        }
    }
    
    static class DelegatedExecutorService extends AbstractExecutorService
    {
        private final ExecutorService e;
        
        DelegatedExecutorService(final ExecutorService e) {
            this.e = e;
        }
        
        @Override
        public void execute(final Runnable runnable) {
            this.e.execute(runnable);
        }
        
        @Override
        public void shutdown() {
            this.e.shutdown();
        }
        
        @Override
        public List<Runnable> shutdownNow() {
            return this.e.shutdownNow();
        }
        
        @Override
        public boolean isShutdown() {
            return this.e.isShutdown();
        }
        
        @Override
        public boolean isTerminated() {
            return this.e.isTerminated();
        }
        
        @Override
        public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
            return this.e.awaitTermination(n, timeUnit);
        }
        
        @Override
        public Future<?> submit(final Runnable runnable) {
            return this.e.submit(runnable);
        }
        
        @Override
        public <T> Future<T> submit(final Callable<T> callable) {
            return this.e.submit(callable);
        }
        
        @Override
        public <T> Future<T> submit(final Runnable runnable, final T t) {
            return this.e.submit(runnable, t);
        }
        
        @Override
        public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> collection) throws InterruptedException {
            return this.e.invokeAll(collection);
        }
        
        @Override
        public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> collection, final long n, final TimeUnit timeUnit) throws InterruptedException {
            return this.e.invokeAll(collection, n, timeUnit);
        }
        
        @Override
        public <T> T invokeAny(final Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
            return this.e.invokeAny(collection);
        }
        
        @Override
        public <T> T invokeAny(final Collection<? extends Callable<T>> collection, final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.e.invokeAny(collection, n, timeUnit);
        }
    }
    
    static class DelegatedScheduledExecutorService extends DelegatedExecutorService implements ScheduledExecutorService
    {
        private final ScheduledExecutorService e;
        
        DelegatedScheduledExecutorService(final ScheduledExecutorService e) {
            super(e);
            this.e = e;
        }
        
        @Override
        public ScheduledFuture<?> schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
            return this.e.schedule(runnable, n, timeUnit);
        }
        
        @Override
        public <V> ScheduledFuture<V> schedule(final Callable<V> callable, final long n, final TimeUnit timeUnit) {
            return this.e.schedule(callable, n, timeUnit);
        }
        
        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
            return this.e.scheduleAtFixedRate(runnable, n, n2, timeUnit);
        }
        
        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
            return this.e.scheduleWithFixedDelay(runnable, n, n2, timeUnit);
        }
    }
    
    static class FinalizableDelegatedExecutorService extends DelegatedExecutorService
    {
        FinalizableDelegatedExecutorService(final ExecutorService executorService) {
            super(executorService);
        }
        
        @Override
        protected void finalize() {
            super.shutdown();
        }
    }
    
    static final class PrivilegedCallable<T> implements Callable<T>
    {
        private final Callable<T> task;
        private final AccessControlContext acc;
        
        PrivilegedCallable(final Callable<T> task) {
            this.task = task;
            this.acc = AccessController.getContext();
        }
        
        @Override
        public T call() throws Exception {
            try {
                return AccessController.doPrivileged((PrivilegedExceptionAction<T>)new PrivilegedExceptionAction<T>() {
                    @Override
                    public T run() throws Exception {
                        return PrivilegedCallable.this.task.call();
                    }
                }, this.acc);
            }
            catch (PrivilegedActionException ex) {
                throw ex.getException();
            }
        }
    }
    
    static final class PrivilegedCallableUsingCurrentClassLoader<T> implements Callable<T>
    {
        private final Callable<T> task;
        private final AccessControlContext acc;
        private final ClassLoader ccl;
        
        PrivilegedCallableUsingCurrentClassLoader(final Callable<T> task) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
                securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
            }
            this.task = task;
            this.acc = AccessController.getContext();
            this.ccl = Thread.currentThread().getContextClassLoader();
        }
        
        @Override
        public T call() throws Exception {
            try {
                return AccessController.doPrivileged((PrivilegedExceptionAction<T>)new PrivilegedExceptionAction<T>() {
                    @Override
                    public T run() throws Exception {
                        final Thread currentThread = Thread.currentThread();
                        final ClassLoader contextClassLoader = currentThread.getContextClassLoader();
                        if (PrivilegedCallableUsingCurrentClassLoader.this.ccl == contextClassLoader) {
                            return PrivilegedCallableUsingCurrentClassLoader.this.task.call();
                        }
                        currentThread.setContextClassLoader(PrivilegedCallableUsingCurrentClassLoader.this.ccl);
                        try {
                            return PrivilegedCallableUsingCurrentClassLoader.this.task.call();
                        }
                        finally {
                            currentThread.setContextClassLoader(contextClassLoader);
                        }
                    }
                }, this.acc);
            }
            catch (PrivilegedActionException ex) {
                throw ex.getException();
            }
        }
    }
    
    static class PrivilegedThreadFactory extends DefaultThreadFactory
    {
        private final AccessControlContext acc;
        private final ClassLoader ccl;
        
        PrivilegedThreadFactory() {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
                securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
            }
            this.acc = AccessController.getContext();
            this.ccl = Thread.currentThread().getContextClassLoader();
        }
        
        @Override
        public Thread newThread(final Runnable runnable) {
            return super.newThread(new Runnable() {
                @Override
                public void run() {
                    AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                        @Override
                        public Void run() {
                            Thread.currentThread().setContextClassLoader(PrivilegedThreadFactory.this.ccl);
                            runnable.run();
                            return null;
                        }
                    }, PrivilegedThreadFactory.this.acc);
                }
            });
        }
    }
    
    static final class RunnableAdapter<T> implements Callable<T>
    {
        final Runnable task;
        final T result;
        
        RunnableAdapter(final Runnable task, final T result) {
            this.task = task;
            this.result = result;
        }
        
        @Override
        public T call() {
            this.task.run();
            return this.result;
        }
    }
}
