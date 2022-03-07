package java.util.concurrent;

import java.util.concurrent.atomic.*;
import java.security.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class ThreadPoolExecutor extends AbstractExecutorService
{
    private final AtomicInteger ctl;
    private static final int COUNT_BITS = 29;
    private static final int CAPACITY = 536870911;
    private static final int RUNNING = -536870912;
    private static final int SHUTDOWN = 0;
    private static final int STOP = 536870912;
    private static final int TIDYING = 1073741824;
    private static final int TERMINATED = 1610612736;
    private final BlockingQueue<Runnable> workQueue;
    private final ReentrantLock mainLock;
    private final HashSet<Worker> workers;
    private final Condition termination;
    private int largestPoolSize;
    private long completedTaskCount;
    private volatile ThreadFactory threadFactory;
    private volatile RejectedExecutionHandler handler;
    private volatile long keepAliveTime;
    private volatile boolean allowCoreThreadTimeOut;
    private volatile int corePoolSize;
    private volatile int maximumPoolSize;
    private static final RejectedExecutionHandler defaultHandler;
    private static final RuntimePermission shutdownPerm;
    private final AccessControlContext acc;
    private static final boolean ONLY_ONE = true;
    
    private static int runStateOf(final int n) {
        return n & 0xE0000000;
    }
    
    private static int workerCountOf(final int n) {
        return n & 0x1FFFFFFF;
    }
    
    private static int ctlOf(final int n, final int n2) {
        return n | n2;
    }
    
    private static boolean runStateLessThan(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean runStateAtLeast(final int n, final int n2) {
        return n >= n2;
    }
    
    private static boolean isRunning(final int n) {
        return n < 0;
    }
    
    private boolean compareAndIncrementWorkerCount(final int n) {
        return this.ctl.compareAndSet(n, n + 1);
    }
    
    private boolean compareAndDecrementWorkerCount(final int n) {
        return this.ctl.compareAndSet(n, n - 1);
    }
    
    private void decrementWorkerCount() {
        while (!this.compareAndDecrementWorkerCount(this.ctl.get())) {}
    }
    
    private void advanceRunState(final int n) {
        int value;
        do {
            value = this.ctl.get();
        } while (!runStateAtLeast(value, n) && !this.ctl.compareAndSet(value, ctlOf(n, workerCountOf(value))));
    }
    
    final void tryTerminate() {
        while (true) {
            final int value = this.ctl.get();
            if (isRunning(value) || runStateAtLeast(value, 1073741824) || (runStateOf(value) == 0 && !this.workQueue.isEmpty())) {
                return;
            }
            if (workerCountOf(value) != 0) {
                this.interruptIdleWorkers(true);
                return;
            }
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if (this.ctl.compareAndSet(value, ctlOf(1073741824, 0))) {
                    try {
                        this.terminated();
                    }
                    finally {
                        this.ctl.set(ctlOf(1610612736, 0));
                        this.termination.signalAll();
                    }
                    return;
                }
                continue;
            }
            finally {
                mainLock.unlock();
            }
        }
    }
    
    private void checkShutdownAccess() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(ThreadPoolExecutor.shutdownPerm);
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                final Iterator<Worker> iterator = this.workers.iterator();
                while (iterator.hasNext()) {
                    securityManager.checkAccess(iterator.next().thread);
                }
            }
            finally {
                mainLock.unlock();
            }
        }
    }
    
    private void interruptWorkers() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            final Iterator<Worker> iterator = this.workers.iterator();
            while (iterator.hasNext()) {
                iterator.next().interruptIfStarted();
            }
        }
        finally {
            mainLock.unlock();
        }
    }
    
    private void interruptIdleWorkers(final boolean b) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (final Worker worker : this.workers) {
                final Thread thread = worker.thread;
                if (!thread.isInterrupted() && worker.tryLock()) {
                    try {
                        thread.interrupt();
                    }
                    catch (SecurityException ex) {}
                    finally {
                        worker.unlock();
                    }
                }
                if (b) {
                    break;
                }
            }
        }
        finally {
            mainLock.unlock();
        }
    }
    
    private void interruptIdleWorkers() {
        this.interruptIdleWorkers(false);
    }
    
    final void reject(final Runnable runnable) {
        this.handler.rejectedExecution(runnable, this);
    }
    
    void onShutdown() {
    }
    
    final boolean isRunningOrShutdown(final boolean b) {
        final int runState = runStateOf(this.ctl.get());
        return runState == -536870912 || (runState == 0 && b);
    }
    
    private List<Runnable> drainQueue() {
        final BlockingQueue<Runnable> workQueue = this.workQueue;
        final ArrayList<Runnable> list = new ArrayList<Runnable>();
        workQueue.drainTo(list);
        if (!workQueue.isEmpty()) {
            for (final Runnable runnable : workQueue.toArray(new Runnable[0])) {
                if (workQueue.remove(runnable)) {
                    list.add(runnable);
                }
            }
        }
        return list;
    }
    
    private boolean addWorker(final Runnable runnable, final boolean b) {
        while (true) {
            int n = this.ctl.get();
            final int runState = runStateOf(n);
            if (runState >= 0 && (runState != 0 || runnable != null || this.workQueue.isEmpty())) {
                return false;
            }
            do {
                final int workerCount = workerCountOf(n);
                if (workerCount >= 536870911 || workerCount >= (b ? this.corePoolSize : this.maximumPoolSize)) {
                    return false;
                }
                if (this.compareAndIncrementWorkerCount(n)) {
                    boolean b2 = false;
                    boolean b3 = false;
                    Worker worker = null;
                    try {
                        worker = new Worker(runnable);
                        final Thread thread = worker.thread;
                        if (thread != null) {
                            final ReentrantLock mainLock = this.mainLock;
                            mainLock.lock();
                            try {
                                final int runState2 = runStateOf(this.ctl.get());
                                if (runState2 < 0 || (runState2 == 0 && runnable == null)) {
                                    if (thread.isAlive()) {
                                        throw new IllegalThreadStateException();
                                    }
                                    this.workers.add(worker);
                                    final int size = this.workers.size();
                                    if (size > this.largestPoolSize) {
                                        this.largestPoolSize = size;
                                    }
                                    b3 = true;
                                }
                            }
                            finally {
                                mainLock.unlock();
                            }
                            if (b3) {
                                thread.start();
                                b2 = true;
                            }
                        }
                    }
                    finally {
                        if (!b2) {
                            this.addWorkerFailed(worker);
                        }
                    }
                    return b2;
                }
                n = this.ctl.get();
            } while (runStateOf(n) == runState);
        }
    }
    
    private void addWorkerFailed(final Worker worker) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            if (worker != null) {
                this.workers.remove(worker);
            }
            this.decrementWorkerCount();
            this.tryTerminate();
        }
        finally {
            mainLock.unlock();
        }
    }
    
    private void processWorkerExit(final Worker worker, final boolean b) {
        if (b) {
            this.decrementWorkerCount();
        }
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            this.completedTaskCount += worker.completedTasks;
            this.workers.remove(worker);
        }
        finally {
            mainLock.unlock();
        }
        this.tryTerminate();
        final int value = this.ctl.get();
        if (runStateLessThan(value, 536870912)) {
            if (!b) {
                int n = this.allowCoreThreadTimeOut ? 0 : this.corePoolSize;
                if (n == 0 && !this.workQueue.isEmpty()) {
                    n = 1;
                }
                if (workerCountOf(value) >= n) {
                    return;
                }
            }
            this.addWorker(null, false);
        }
    }
    
    private Runnable getTask() {
        int n = 0;
        while (true) {
            final int value = this.ctl.get();
            final int runState = runStateOf(value);
            if (runState >= 0 && (runState >= 536870912 || this.workQueue.isEmpty())) {
                this.decrementWorkerCount();
                return null;
            }
            final int workerCount = workerCountOf(value);
            final boolean b = this.allowCoreThreadTimeOut || workerCount > this.corePoolSize;
            if ((workerCount > this.maximumPoolSize || (b && n != 0)) && (workerCount > 1 || this.workQueue.isEmpty())) {
                if (this.compareAndDecrementWorkerCount(value)) {
                    return null;
                }
                continue;
            }
            else {
                try {
                    final Runnable runnable = b ? this.workQueue.poll(this.keepAliveTime, TimeUnit.NANOSECONDS) : this.workQueue.take();
                    if (runnable != null) {
                        return runnable;
                    }
                    n = 1;
                }
                catch (InterruptedException ex) {
                    n = 0;
                }
            }
        }
    }
    
    final void runWorker(final Worker worker) {
        final Thread currentThread = Thread.currentThread();
        Runnable runnable = worker.firstTask;
        worker.firstTask = null;
        worker.unlock();
        boolean b = true;
        try {
            while (runnable != null || (runnable = this.getTask()) != null) {
                worker.lock();
                if ((runStateAtLeast(this.ctl.get(), 536870912) || (Thread.interrupted() && runStateAtLeast(this.ctl.get(), 536870912))) && !currentThread.isInterrupted()) {
                    currentThread.interrupt();
                }
                try {
                    this.beforeExecute(currentThread, runnable);
                    Throwable t = null;
                    try {
                        runnable.run();
                    }
                    catch (RuntimeException ex) {
                        t = ex;
                        throw ex;
                    }
                    catch (Error error) {
                        t = error;
                        throw error;
                    }
                    catch (Throwable t2) {
                        t = t2;
                        throw new Error(t2);
                    }
                    finally {
                        this.afterExecute(runnable, t);
                    }
                }
                finally {
                    runnable = null;
                    ++worker.completedTasks;
                    worker.unlock();
                }
            }
            b = false;
        }
        finally {
            this.processWorkerExit(worker, b);
        }
    }
    
    public ThreadPoolExecutor(final int n, final int n2, final long n3, final TimeUnit timeUnit, final BlockingQueue<Runnable> blockingQueue) {
        this(n, n2, n3, timeUnit, blockingQueue, Executors.defaultThreadFactory(), ThreadPoolExecutor.defaultHandler);
    }
    
    public ThreadPoolExecutor(final int n, final int n2, final long n3, final TimeUnit timeUnit, final BlockingQueue<Runnable> blockingQueue, final ThreadFactory threadFactory) {
        this(n, n2, n3, timeUnit, blockingQueue, threadFactory, ThreadPoolExecutor.defaultHandler);
    }
    
    public ThreadPoolExecutor(final int n, final int n2, final long n3, final TimeUnit timeUnit, final BlockingQueue<Runnable> blockingQueue, final RejectedExecutionHandler rejectedExecutionHandler) {
        this(n, n2, n3, timeUnit, blockingQueue, Executors.defaultThreadFactory(), rejectedExecutionHandler);
    }
    
    public ThreadPoolExecutor(final int corePoolSize, final int maximumPoolSize, final long n, final TimeUnit timeUnit, final BlockingQueue<Runnable> workQueue, final ThreadFactory threadFactory, final RejectedExecutionHandler handler) {
        this.ctl = new AtomicInteger(ctlOf(-536870912, 0));
        this.mainLock = new ReentrantLock();
        this.workers = new HashSet<Worker>();
        this.termination = this.mainLock.newCondition();
        if (corePoolSize < 0 || maximumPoolSize <= 0 || maximumPoolSize < corePoolSize || n < 0L) {
            throw new IllegalArgumentException();
        }
        if (workQueue == null || threadFactory == null || handler == null) {
            throw new NullPointerException();
        }
        this.acc = ((System.getSecurityManager() == null) ? null : AccessController.getContext());
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = timeUnit.toNanos(n);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
    
    @Override
    public void execute(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        int n = this.ctl.get();
        if (workerCountOf(n) < this.corePoolSize) {
            if (this.addWorker(runnable, true)) {
                return;
            }
            n = this.ctl.get();
        }
        if (isRunning(n) && this.workQueue.offer(runnable)) {
            final int value = this.ctl.get();
            if (!isRunning(value) && this.remove(runnable)) {
                this.reject(runnable);
            }
            else if (workerCountOf(value) == 0) {
                this.addWorker(null, false);
            }
        }
        else if (!this.addWorker(runnable, false)) {
            this.reject(runnable);
        }
    }
    
    @Override
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            this.checkShutdownAccess();
            this.advanceRunState(0);
            this.interruptIdleWorkers();
            this.onShutdown();
        }
        finally {
            mainLock.unlock();
        }
        this.tryTerminate();
    }
    
    @Override
    public List<Runnable> shutdownNow() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        List<Runnable> drainQueue;
        try {
            this.checkShutdownAccess();
            this.advanceRunState(536870912);
            this.interruptWorkers();
            drainQueue = this.drainQueue();
        }
        finally {
            mainLock.unlock();
        }
        this.tryTerminate();
        return drainQueue;
    }
    
    @Override
    public boolean isShutdown() {
        return !isRunning(this.ctl.get());
    }
    
    public boolean isTerminating() {
        final int value = this.ctl.get();
        return !isRunning(value) && runStateLessThan(value, 1610612736);
    }
    
    @Override
    public boolean isTerminated() {
        return runStateAtLeast(this.ctl.get(), 1610612736);
    }
    
    @Override
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            while (!runStateAtLeast(this.ctl.get(), 1610612736)) {
                if (n2 <= 0L) {
                    return false;
                }
                n2 = this.termination.awaitNanos(n2);
            }
            return true;
        }
        finally {
            mainLock.unlock();
        }
    }
    
    @Override
    protected void finalize() {
        if (System.getSecurityManager() == null || this.acc == null) {
            this.shutdown();
        }
        else {
            AccessController.doPrivileged(() -> {
                this.shutdown();
                return null;
            }, this.acc);
        }
    }
    
    public void setThreadFactory(final ThreadFactory threadFactory) {
        if (threadFactory == null) {
            throw new NullPointerException();
        }
        this.threadFactory = threadFactory;
    }
    
    public ThreadFactory getThreadFactory() {
        return this.threadFactory;
    }
    
    public void setRejectedExecutionHandler(final RejectedExecutionHandler handler) {
        if (handler == null) {
            throw new NullPointerException();
        }
        this.handler = handler;
    }
    
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return this.handler;
    }
    
    public void setCorePoolSize(final int corePoolSize) {
        if (corePoolSize < 0) {
            throw new IllegalArgumentException();
        }
        final int n = corePoolSize - this.corePoolSize;
        if (workerCountOf(this.ctl.get()) > (this.corePoolSize = corePoolSize)) {
            this.interruptIdleWorkers();
        }
        else if (n > 0) {
            int min = Math.min(n, this.workQueue.size());
            while (min-- > 0 && this.addWorker(null, true) && !this.workQueue.isEmpty()) {}
        }
    }
    
    public int getCorePoolSize() {
        return this.corePoolSize;
    }
    
    public boolean prestartCoreThread() {
        return workerCountOf(this.ctl.get()) < this.corePoolSize && this.addWorker(null, true);
    }
    
    void ensurePrestart() {
        final int workerCount = workerCountOf(this.ctl.get());
        if (workerCount < this.corePoolSize) {
            this.addWorker(null, true);
        }
        else if (workerCount == 0) {
            this.addWorker(null, false);
        }
    }
    
    public int prestartAllCoreThreads() {
        int n = 0;
        while (this.addWorker(null, true)) {
            ++n;
        }
        return n;
    }
    
    public boolean allowsCoreThreadTimeOut() {
        return this.allowCoreThreadTimeOut;
    }
    
    public void allowCoreThreadTimeOut(final boolean allowCoreThreadTimeOut) {
        if (allowCoreThreadTimeOut && this.keepAliveTime <= 0L) {
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        }
        if (allowCoreThreadTimeOut != this.allowCoreThreadTimeOut && (this.allowCoreThreadTimeOut = allowCoreThreadTimeOut)) {
            this.interruptIdleWorkers();
        }
    }
    
    public void setMaximumPoolSize(final int maximumPoolSize) {
        if (maximumPoolSize <= 0 || maximumPoolSize < this.corePoolSize) {
            throw new IllegalArgumentException();
        }
        if (workerCountOf(this.ctl.get()) > (this.maximumPoolSize = maximumPoolSize)) {
            this.interruptIdleWorkers();
        }
    }
    
    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }
    
    public void setKeepAliveTime(final long n, final TimeUnit timeUnit) {
        if (n < 0L) {
            throw new IllegalArgumentException();
        }
        if (n == 0L && this.allowsCoreThreadTimeOut()) {
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        }
        final long nanos = timeUnit.toNanos(n);
        final long n2 = nanos - this.keepAliveTime;
        this.keepAliveTime = nanos;
        if (n2 < 0L) {
            this.interruptIdleWorkers();
        }
    }
    
    public long getKeepAliveTime(final TimeUnit timeUnit) {
        return timeUnit.convert(this.keepAliveTime, TimeUnit.NANOSECONDS);
    }
    
    public BlockingQueue<Runnable> getQueue() {
        return this.workQueue;
    }
    
    public boolean remove(final Runnable runnable) {
        final boolean remove = this.workQueue.remove(runnable);
        this.tryTerminate();
        return remove;
    }
    
    public void purge() {
        final BlockingQueue<Runnable> workQueue = this.workQueue;
        try {
            final Iterator<Object> iterator = workQueue.iterator();
            while (iterator.hasNext()) {
                final Runnable runnable = iterator.next();
                if (runnable instanceof Future && ((Future)runnable).isCancelled()) {
                    iterator.remove();
                }
            }
        }
        catch (ConcurrentModificationException ex) {
            for (final Object o : workQueue.toArray()) {
                if (o instanceof Future && ((Future)o).isCancelled()) {
                    workQueue.remove(o);
                }
            }
        }
        this.tryTerminate();
    }
    
    public int getPoolSize() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            return runStateAtLeast(this.ctl.get(), 1073741824) ? 0 : this.workers.size();
        }
        finally {
            mainLock.unlock();
        }
    }
    
    public int getActiveCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            int n = 0;
            final Iterator<Worker> iterator = this.workers.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().isLocked()) {
                    ++n;
                }
            }
            return n;
        }
        finally {
            mainLock.unlock();
        }
    }
    
    public int getLargestPoolSize() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            return this.largestPoolSize;
        }
        finally {
            mainLock.unlock();
        }
    }
    
    public long getTaskCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            long completedTaskCount = this.completedTaskCount;
            for (final Worker worker : this.workers) {
                completedTaskCount += worker.completedTasks;
                if (worker.isLocked()) {
                    ++completedTaskCount;
                }
            }
            return completedTaskCount + this.workQueue.size();
        }
        finally {
            mainLock.unlock();
        }
    }
    
    public long getCompletedTaskCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            long completedTaskCount = this.completedTaskCount;
            final Iterator<Worker> iterator = this.workers.iterator();
            while (iterator.hasNext()) {
                completedTaskCount += iterator.next().completedTasks;
            }
            return completedTaskCount;
        }
        finally {
            mainLock.unlock();
        }
    }
    
    @Override
    public String toString() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        long completedTaskCount;
        int n;
        int size;
        try {
            completedTaskCount = this.completedTaskCount;
            n = 0;
            size = this.workers.size();
            for (final Worker worker : this.workers) {
                completedTaskCount += worker.completedTasks;
                if (worker.isLocked()) {
                    ++n;
                }
            }
        }
        finally {
            mainLock.unlock();
        }
        final int value = this.ctl.get();
        return super.toString() + "[" + (runStateLessThan(value, 0) ? "Running" : (runStateAtLeast(value, 1610612736) ? "Terminated" : "Shutting down")) + ", pool size = " + size + ", active threads = " + n + ", queued tasks = " + this.workQueue.size() + ", completed tasks = " + completedTaskCount + "]";
    }
    
    protected void beforeExecute(final Thread thread, final Runnable runnable) {
    }
    
    protected void afterExecute(final Runnable runnable, final Throwable t) {
    }
    
    protected void terminated() {
    }
    
    static {
        defaultHandler = new AbortPolicy();
        shutdownPerm = new RuntimePermission("modifyThread");
    }
    
    public static class AbortPolicy implements RejectedExecutionHandler
    {
        @Override
        public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor threadPoolExecutor) {
            throw new RejectedExecutionException("Task " + runnable.toString() + " rejected from " + threadPoolExecutor.toString());
        }
    }
    
    public static class CallerRunsPolicy implements RejectedExecutionHandler
    {
        @Override
        public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor threadPoolExecutor) {
            if (!threadPoolExecutor.isShutdown()) {
                runnable.run();
            }
        }
    }
    
    public static class DiscardOldestPolicy implements RejectedExecutionHandler
    {
        @Override
        public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor threadPoolExecutor) {
            if (!threadPoolExecutor.isShutdown()) {
                threadPoolExecutor.getQueue().poll();
                threadPoolExecutor.execute(runnable);
            }
        }
    }
    
    public static class DiscardPolicy implements RejectedExecutionHandler
    {
        @Override
        public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor threadPoolExecutor) {
        }
    }
    
    private final class Worker extends AbstractQueuedSynchronizer implements Runnable
    {
        private static final long serialVersionUID = 6138294804551838833L;
        final Thread thread;
        Runnable firstTask;
        volatile long completedTasks;
        
        Worker(final Runnable firstTask) {
            this.setState(-1);
            this.firstTask = firstTask;
            this.thread = ThreadPoolExecutor.this.getThreadFactory().newThread(this);
        }
        
        @Override
        public void run() {
            ThreadPoolExecutor.this.runWorker(this);
        }
        
        @Override
        protected boolean isHeldExclusively() {
            return this.getState() != 0;
        }
        
        @Override
        protected boolean tryAcquire(final int n) {
            if (this.compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        
        @Override
        protected boolean tryRelease(final int n) {
            this.setExclusiveOwnerThread(null);
            this.setState(0);
            return true;
        }
        
        public void lock() {
            this.acquire(1);
        }
        
        public boolean tryLock() {
            return this.tryAcquire(1);
        }
        
        public void unlock() {
            this.release(1);
        }
        
        public boolean isLocked() {
            return this.isHeldExclusively();
        }
        
        void interruptIfStarted() {
            final Thread thread;
            if (this.getState() >= 0 && (thread = this.thread) != null && !thread.isInterrupted()) {
                try {
                    thread.interrupt();
                }
                catch (SecurityException ex) {}
            }
        }
    }
}
