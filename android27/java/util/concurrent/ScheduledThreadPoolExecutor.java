package java.util.concurrent;

import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.*;

public class ScheduledThreadPoolExecutor extends ThreadPoolExecutor implements ScheduledExecutorService
{
    private volatile boolean continueExistingPeriodicTasksAfterShutdown;
    private volatile boolean executeExistingDelayedTasksAfterShutdown;
    private volatile boolean removeOnCancel;
    private static final AtomicLong sequencer;
    
    final long now() {
        return System.nanoTime();
    }
    
    boolean canRunInCurrentRunState(final boolean b) {
        return this.isRunningOrShutdown(b ? this.continueExistingPeriodicTasksAfterShutdown : this.executeExistingDelayedTasksAfterShutdown);
    }
    
    private void delayedExecute(final RunnableScheduledFuture<?> runnableScheduledFuture) {
        if (this.isShutdown()) {
            this.reject(runnableScheduledFuture);
        }
        else {
            super.getQueue().add(runnableScheduledFuture);
            if (this.isShutdown() && !this.canRunInCurrentRunState(runnableScheduledFuture.isPeriodic()) && this.remove(runnableScheduledFuture)) {
                runnableScheduledFuture.cancel(false);
            }
            else {
                this.ensurePrestart();
            }
        }
    }
    
    void reExecutePeriodic(final RunnableScheduledFuture<?> runnableScheduledFuture) {
        if (this.canRunInCurrentRunState(true)) {
            super.getQueue().add(runnableScheduledFuture);
            if (!this.canRunInCurrentRunState(true) && this.remove(runnableScheduledFuture)) {
                runnableScheduledFuture.cancel(false);
            }
            else {
                this.ensurePrestart();
            }
        }
    }
    
    @Override
    void onShutdown() {
        final BlockingQueue<Runnable> queue = super.getQueue();
        final boolean executeExistingDelayedTasksAfterShutdownPolicy = this.getExecuteExistingDelayedTasksAfterShutdownPolicy();
        final boolean continueExistingPeriodicTasksAfterShutdownPolicy = this.getContinueExistingPeriodicTasksAfterShutdownPolicy();
        if (!executeExistingDelayedTasksAfterShutdownPolicy && !continueExistingPeriodicTasksAfterShutdownPolicy) {
            for (final Object o : queue.toArray()) {
                if (o instanceof RunnableScheduledFuture) {
                    ((RunnableScheduledFuture)o).cancel(false);
                }
            }
            queue.clear();
        }
        else {
            for (final Object o2 : queue.toArray()) {
                Label_0184: {
                    if (o2 instanceof RunnableScheduledFuture) {
                        final RunnableScheduledFuture runnableScheduledFuture = (RunnableScheduledFuture)o2;
                        Label_0164: {
                            if (runnableScheduledFuture.isPeriodic()) {
                                if (!continueExistingPeriodicTasksAfterShutdownPolicy) {
                                    break Label_0164;
                                }
                            }
                            else if (!executeExistingDelayedTasksAfterShutdownPolicy) {
                                break Label_0164;
                            }
                            if (!runnableScheduledFuture.isCancelled()) {
                                break Label_0184;
                            }
                        }
                        if (queue.remove(runnableScheduledFuture)) {
                            runnableScheduledFuture.cancel(false);
                        }
                    }
                }
            }
        }
        this.tryTerminate();
    }
    
    protected <V> RunnableScheduledFuture<V> decorateTask(final Runnable runnable, final RunnableScheduledFuture<V> runnableScheduledFuture) {
        return runnableScheduledFuture;
    }
    
    protected <V> RunnableScheduledFuture<V> decorateTask(final Callable<V> callable, final RunnableScheduledFuture<V> runnableScheduledFuture) {
        return runnableScheduledFuture;
    }
    
    public ScheduledThreadPoolExecutor(final int n) {
        super(n, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue());
        this.executeExistingDelayedTasksAfterShutdown = true;
        this.removeOnCancel = false;
    }
    
    public ScheduledThreadPoolExecutor(final int n, final ThreadFactory threadFactory) {
        super(n, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), threadFactory);
        this.executeExistingDelayedTasksAfterShutdown = true;
        this.removeOnCancel = false;
    }
    
    public ScheduledThreadPoolExecutor(final int n, final RejectedExecutionHandler rejectedExecutionHandler) {
        super(n, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), rejectedExecutionHandler);
        this.executeExistingDelayedTasksAfterShutdown = true;
        this.removeOnCancel = false;
    }
    
    public ScheduledThreadPoolExecutor(final int n, final ThreadFactory threadFactory, final RejectedExecutionHandler rejectedExecutionHandler) {
        super(n, Integer.MAX_VALUE, 0L, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), threadFactory, rejectedExecutionHandler);
        this.executeExistingDelayedTasksAfterShutdown = true;
        this.removeOnCancel = false;
    }
    
    private long triggerTime(final long n, final TimeUnit timeUnit) {
        return this.triggerTime(timeUnit.toNanos((n < 0L) ? 0L : n));
    }
    
    long triggerTime(final long n) {
        return this.now() + ((n < 4611686018427387903L) ? n : this.overflowFree(n));
    }
    
    private long overflowFree(long n) {
        final Delayed delayed = super.getQueue().peek();
        if (delayed != null) {
            final long delay = delayed.getDelay(TimeUnit.NANOSECONDS);
            if (delay < 0L && n - delay < 0L) {
                n = Long.MAX_VALUE + delay;
            }
        }
        return n;
    }
    
    @Override
    public ScheduledFuture<?> schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        if (runnable == null || timeUnit == null) {
            throw new NullPointerException();
        }
        final RunnableScheduledFuture<Object> decorateTask = this.decorateTask(runnable, new ScheduledFutureTask<Object>(runnable, null, this.triggerTime(n, timeUnit)));
        this.delayedExecute(decorateTask);
        return decorateTask;
    }
    
    @Override
    public <V> ScheduledFuture<V> schedule(final Callable<V> callable, final long n, final TimeUnit timeUnit) {
        if (callable == null || timeUnit == null) {
            throw new NullPointerException();
        }
        final RunnableScheduledFuture<V> decorateTask = this.decorateTask(callable, new ScheduledFutureTask<V>(callable, this.triggerTime(n, timeUnit)));
        this.delayedExecute(decorateTask);
        return decorateTask;
    }
    
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        if (runnable == null || timeUnit == null) {
            throw new NullPointerException();
        }
        if (n2 <= 0L) {
            throw new IllegalArgumentException();
        }
        final ScheduledFutureTask<Object> scheduledFutureTask = new ScheduledFutureTask<Object>(runnable, null, this.triggerTime(n, timeUnit), timeUnit.toNanos(n2));
        final RunnableScheduledFuture<V> decorateTask = this.decorateTask(runnable, (RunnableScheduledFuture<V>)scheduledFutureTask);
        this.delayedExecute(scheduledFutureTask.outerTask = (RunnableScheduledFuture<Object>)decorateTask);
        return decorateTask;
    }
    
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        if (runnable == null || timeUnit == null) {
            throw new NullPointerException();
        }
        if (n2 <= 0L) {
            throw new IllegalArgumentException();
        }
        final ScheduledFutureTask<Object> scheduledFutureTask = new ScheduledFutureTask<Object>(runnable, null, this.triggerTime(n, timeUnit), timeUnit.toNanos(-n2));
        final RunnableScheduledFuture<V> decorateTask = this.decorateTask(runnable, (RunnableScheduledFuture<V>)scheduledFutureTask);
        this.delayedExecute(scheduledFutureTask.outerTask = (RunnableScheduledFuture<Object>)decorateTask);
        return decorateTask;
    }
    
    @Override
    public void execute(final Runnable runnable) {
        this.schedule(runnable, 0L, TimeUnit.NANOSECONDS);
    }
    
    @Override
    public Future<?> submit(final Runnable runnable) {
        return this.schedule(runnable, 0L, TimeUnit.NANOSECONDS);
    }
    
    @Override
    public <T> Future<T> submit(final Runnable runnable, final T t) {
        return (Future<T>)this.schedule((Callable<Object>)Executors.callable(runnable, (V)t), 0L, TimeUnit.NANOSECONDS);
    }
    
    @Override
    public <T> Future<T> submit(final Callable<T> callable) {
        return this.schedule(callable, 0L, TimeUnit.NANOSECONDS);
    }
    
    public void setContinueExistingPeriodicTasksAfterShutdownPolicy(final boolean continueExistingPeriodicTasksAfterShutdown) {
        this.continueExistingPeriodicTasksAfterShutdown = continueExistingPeriodicTasksAfterShutdown;
        if (!continueExistingPeriodicTasksAfterShutdown && this.isShutdown()) {
            this.onShutdown();
        }
    }
    
    public boolean getContinueExistingPeriodicTasksAfterShutdownPolicy() {
        return this.continueExistingPeriodicTasksAfterShutdown;
    }
    
    public void setExecuteExistingDelayedTasksAfterShutdownPolicy(final boolean executeExistingDelayedTasksAfterShutdown) {
        this.executeExistingDelayedTasksAfterShutdown = executeExistingDelayedTasksAfterShutdown;
        if (!executeExistingDelayedTasksAfterShutdown && this.isShutdown()) {
            this.onShutdown();
        }
    }
    
    public boolean getExecuteExistingDelayedTasksAfterShutdownPolicy() {
        return this.executeExistingDelayedTasksAfterShutdown;
    }
    
    public void setRemoveOnCancelPolicy(final boolean removeOnCancel) {
        this.removeOnCancel = removeOnCancel;
    }
    
    public boolean getRemoveOnCancelPolicy() {
        return this.removeOnCancel;
    }
    
    @Override
    public void shutdown() {
        super.shutdown();
    }
    
    @Override
    public List<Runnable> shutdownNow() {
        return super.shutdownNow();
    }
    
    @Override
    public BlockingQueue<Runnable> getQueue() {
        return super.getQueue();
    }
    
    static {
        sequencer = new AtomicLong();
    }
    
    static class DelayedWorkQueue extends AbstractQueue<Runnable> implements BlockingQueue<Runnable>
    {
        private static final int INITIAL_CAPACITY = 16;
        private RunnableScheduledFuture<?>[] queue;
        private final ReentrantLock lock;
        private int size;
        private Thread leader;
        private final Condition available;
        
        DelayedWorkQueue() {
            this.queue = (RunnableScheduledFuture<?>[])new RunnableScheduledFuture[16];
            this.lock = new ReentrantLock();
            this.size = 0;
            this.leader = null;
            this.available = this.lock.newCondition();
        }
        
        private void setIndex(final RunnableScheduledFuture<?> runnableScheduledFuture, final int heapIndex) {
            if (runnableScheduledFuture instanceof ScheduledFutureTask) {
                ((ScheduledFutureTask)runnableScheduledFuture).heapIndex = heapIndex;
            }
        }
        
        private void siftUp(int i, final RunnableScheduledFuture<?> runnableScheduledFuture) {
            while (i > 0) {
                final int n = i - 1 >>> 1;
                final RunnableScheduledFuture<?> runnableScheduledFuture2 = this.queue[n];
                if (runnableScheduledFuture.compareTo(runnableScheduledFuture2) >= 0) {
                    break;
                }
                this.setIndex(this.queue[i] = runnableScheduledFuture2, i);
                i = n;
            }
            this.setIndex(this.queue[i] = runnableScheduledFuture, i);
        }
        
        private void siftDown(int i, final RunnableScheduledFuture<?> runnableScheduledFuture) {
            while (i < this.size >>> 1) {
                int n = (i << 1) + 1;
                RunnableScheduledFuture<?> runnableScheduledFuture2 = this.queue[n];
                final int n2 = n + 1;
                if (n2 < this.size && runnableScheduledFuture2.compareTo(this.queue[n2]) > 0) {
                    runnableScheduledFuture2 = this.queue[n = n2];
                }
                if (runnableScheduledFuture.compareTo(runnableScheduledFuture2) <= 0) {
                    break;
                }
                this.setIndex(this.queue[i] = runnableScheduledFuture2, i);
                i = n;
            }
            this.setIndex(this.queue[i] = runnableScheduledFuture, i);
        }
        
        private void grow() {
            final int length = this.queue.length;
            int n = length + (length >> 1);
            if (n < 0) {
                n = Integer.MAX_VALUE;
            }
            this.queue = Arrays.copyOf(this.queue, n);
        }
        
        private int indexOf(final Object o) {
            if (o != null) {
                if (o instanceof ScheduledFutureTask) {
                    final int heapIndex = ((ScheduledFutureTask)o).heapIndex;
                    if (heapIndex >= 0 && heapIndex < this.size && this.queue[heapIndex] == o) {
                        return heapIndex;
                    }
                }
                else {
                    for (int i = 0; i < this.size; ++i) {
                        if (o.equals(this.queue[i])) {
                            return i;
                        }
                    }
                }
            }
            return -1;
        }
        
        @Override
        public boolean contains(final Object o) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                return this.indexOf(o) != -1;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public boolean remove(final Object o) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                final int index = this.indexOf(o);
                if (index < 0) {
                    return false;
                }
                this.setIndex(this.queue[index], -1);
                final int size = this.size - 1;
                this.size = size;
                final int n = size;
                final RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[n];
                this.queue[n] = null;
                if (n != index) {
                    this.siftDown(index, runnableScheduledFuture);
                    if (this.queue[index] == runnableScheduledFuture) {
                        this.siftUp(index, runnableScheduledFuture);
                    }
                }
                return true;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public int size() {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                return this.size;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }
        
        @Override
        public int remainingCapacity() {
            return Integer.MAX_VALUE;
        }
        
        @Override
        public RunnableScheduledFuture<?> peek() {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                return this.queue[0];
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public boolean offer(final Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException();
            }
            final RunnableScheduledFuture runnableScheduledFuture = (RunnableScheduledFuture)runnable;
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                final int size = this.size;
                if (size >= this.queue.length) {
                    this.grow();
                }
                this.size = size + 1;
                if (size == 0) {
                    this.setIndex(this.queue[0] = (RunnableScheduledFuture<?>)runnableScheduledFuture, 0);
                }
                else {
                    this.siftUp(size, runnableScheduledFuture);
                }
                if (this.queue[0] == runnableScheduledFuture) {
                    this.leader = null;
                    this.available.signal();
                }
            }
            finally {
                lock.unlock();
            }
            return true;
        }
        
        @Override
        public void put(final Runnable runnable) {
            this.offer(runnable);
        }
        
        @Override
        public boolean add(final Runnable runnable) {
            return this.offer(runnable);
        }
        
        @Override
        public boolean offer(final Runnable runnable, final long n, final TimeUnit timeUnit) {
            return this.offer(runnable);
        }
        
        private RunnableScheduledFuture<?> finishPoll(final RunnableScheduledFuture<?> runnableScheduledFuture) {
            final int size = this.size - 1;
            this.size = size;
            final int n = size;
            final RunnableScheduledFuture<?> runnableScheduledFuture2 = this.queue[n];
            this.queue[n] = null;
            if (n != 0) {
                this.siftDown(0, runnableScheduledFuture2);
            }
            this.setIndex(runnableScheduledFuture, -1);
            return runnableScheduledFuture;
        }
        
        @Override
        public RunnableScheduledFuture<?> poll() {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                final RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
                if (runnableScheduledFuture == null || runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS) > 0L) {
                    return null;
                }
                return this.finishPoll(runnableScheduledFuture);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public RunnableScheduledFuture<?> take() throws InterruptedException {
            final ReentrantLock lock = this.lock;
            lock.lockInterruptibly();
            try {
                RunnableScheduledFuture<?> runnableScheduledFuture;
                while (true) {
                    runnableScheduledFuture = this.queue[0];
                    if (runnableScheduledFuture == null) {
                        this.available.await();
                    }
                    else {
                        final long delay = runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS);
                        if (delay <= 0L) {
                            break;
                        }
                        if (this.leader != null) {
                            this.available.await();
                        }
                        else {
                            final Thread currentThread = Thread.currentThread();
                            this.leader = currentThread;
                            try {
                                this.available.awaitNanos(delay);
                            }
                            finally {
                                if (this.leader == currentThread) {
                                    this.leader = null;
                                }
                            }
                        }
                    }
                }
                return this.finishPoll(runnableScheduledFuture);
            }
            finally {
                if (this.leader == null && this.queue[0] != null) {
                    this.available.signal();
                }
                lock.unlock();
            }
        }
        
        @Override
        public RunnableScheduledFuture<?> poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
            long n2 = timeUnit.toNanos(n);
            final ReentrantLock lock = this.lock;
            lock.lockInterruptibly();
            try {
                while (true) {
                    final RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
                    if (runnableScheduledFuture == null) {
                        if (n2 <= 0L) {
                            return null;
                        }
                        n2 = this.available.awaitNanos(n2);
                    }
                    else {
                        final long delay = runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS);
                        if (delay <= 0L) {
                            return this.finishPoll(runnableScheduledFuture);
                        }
                        if (n2 <= 0L) {
                            return null;
                        }
                        if (n2 < delay || this.leader != null) {
                            n2 = this.available.awaitNanos(n2);
                        }
                        else {
                            final Thread currentThread = Thread.currentThread();
                            this.leader = currentThread;
                            try {
                                n2 -= delay - this.available.awaitNanos(delay);
                            }
                            finally {
                                if (this.leader == currentThread) {
                                    this.leader = null;
                                }
                            }
                        }
                    }
                }
            }
            finally {
                if (this.leader == null && this.queue[0] != null) {
                    this.available.signal();
                }
                lock.unlock();
            }
        }
        
        @Override
        public void clear() {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                for (int i = 0; i < this.size; ++i) {
                    final RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[i];
                    if (runnableScheduledFuture != null) {
                        this.queue[i] = null;
                        this.setIndex(runnableScheduledFuture, -1);
                    }
                }
                this.size = 0;
            }
            finally {
                lock.unlock();
            }
        }
        
        private RunnableScheduledFuture<?> peekExpired() {
            final RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
            return (runnableScheduledFuture == null || runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS) > 0L) ? null : runnableScheduledFuture;
        }
        
        @Override
        public int drainTo(final Collection<? super Runnable> collection) {
            if (collection == null) {
                throw new NullPointerException();
            }
            if (collection == this) {
                throw new IllegalArgumentException();
            }
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                int n = 0;
                RunnableScheduledFuture<?> peekExpired;
                while ((peekExpired = this.peekExpired()) != null) {
                    collection.add(peekExpired);
                    this.finishPoll(peekExpired);
                    ++n;
                }
                return n;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public int drainTo(final Collection<? super Runnable> collection, final int n) {
            if (collection == null) {
                throw new NullPointerException();
            }
            if (collection == this) {
                throw new IllegalArgumentException();
            }
            if (n <= 0) {
                return 0;
            }
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                int n2;
                RunnableScheduledFuture<?> peekExpired;
                for (n2 = 0; n2 < n && (peekExpired = this.peekExpired()) != null; ++n2) {
                    collection.add(peekExpired);
                    this.finishPoll(peekExpired);
                }
                return n2;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public Object[] toArray() {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                return Arrays.copyOf(this.queue, this.size, (Class<? extends Object[]>)Object[].class);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                if (array.length < this.size) {
                    return Arrays.copyOf(this.queue, this.size, (Class<? extends T[]>)array.getClass());
                }
                System.arraycopy(this.queue, 0, array, 0, this.size);
                if (array.length > this.size) {
                    array[this.size] = null;
                }
                return array;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public Iterator<Runnable> iterator() {
            return new Itr(Arrays.copyOf(this.queue, this.size));
        }
        
        private class Itr implements Iterator<Runnable>
        {
            final RunnableScheduledFuture<?>[] array;
            int cursor;
            int lastRet;
            
            Itr(final RunnableScheduledFuture<?>[] array) {
                this.cursor = 0;
                this.lastRet = -1;
                this.array = array;
            }
            
            @Override
            public boolean hasNext() {
                return this.cursor < this.array.length;
            }
            
            @Override
            public Runnable next() {
                if (this.cursor >= this.array.length) {
                    throw new NoSuchElementException();
                }
                this.lastRet = this.cursor;
                return this.array[this.cursor++];
            }
            
            @Override
            public void remove() {
                if (this.lastRet < 0) {
                    throw new IllegalStateException();
                }
                DelayedWorkQueue.this.remove(this.array[this.lastRet]);
                this.lastRet = -1;
            }
        }
    }
    
    private class ScheduledFutureTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V>
    {
        private final long sequenceNumber;
        private long time;
        private final long period;
        RunnableScheduledFuture<V> outerTask;
        int heapIndex;
        
        ScheduledFutureTask(final Runnable runnable, final V v, final long time) {
            super(runnable, v);
            this.outerTask = this;
            this.time = time;
            this.period = 0L;
            this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
        }
        
        ScheduledFutureTask(final Runnable runnable, final V v, final long time, final long period) {
            super(runnable, v);
            this.outerTask = this;
            this.time = time;
            this.period = period;
            this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
        }
        
        ScheduledFutureTask(final Callable<V> callable, final long time) {
            super(callable);
            this.outerTask = this;
            this.time = time;
            this.period = 0L;
            this.sequenceNumber = ScheduledThreadPoolExecutor.sequencer.getAndIncrement();
        }
        
        @Override
        public long getDelay(final TimeUnit timeUnit) {
            return timeUnit.convert(this.time - ScheduledThreadPoolExecutor.this.now(), TimeUnit.NANOSECONDS);
        }
        
        @Override
        public int compareTo(final Delayed delayed) {
            if (delayed == this) {
                return 0;
            }
            if (!(delayed instanceof ScheduledFutureTask)) {
                final long n = this.getDelay(TimeUnit.NANOSECONDS) - delayed.getDelay(TimeUnit.NANOSECONDS);
                return (n < 0L) ? -1 : (n > 0L);
            }
            final ScheduledFutureTask scheduledFutureTask = (ScheduledFutureTask)delayed;
            final long n2 = this.time - scheduledFutureTask.time;
            if (n2 < 0L) {
                return -1;
            }
            if (n2 > 0L) {
                return 1;
            }
            if (this.sequenceNumber < scheduledFutureTask.sequenceNumber) {
                return -1;
            }
            return 1;
        }
        
        @Override
        public boolean isPeriodic() {
            return this.period != 0L;
        }
        
        private void setNextRunTime() {
            final long period = this.period;
            if (period > 0L) {
                this.time += period;
            }
            else {
                this.time = ScheduledThreadPoolExecutor.this.triggerTime(-period);
            }
        }
        
        @Override
        public boolean cancel(final boolean b) {
            final boolean cancel = super.cancel(b);
            if (cancel && ScheduledThreadPoolExecutor.this.removeOnCancel && this.heapIndex >= 0) {
                ScheduledThreadPoolExecutor.this.remove(this);
            }
            return cancel;
        }
        
        @Override
        public void run() {
            final boolean periodic = this.isPeriodic();
            if (!ScheduledThreadPoolExecutor.this.canRunInCurrentRunState(periodic)) {
                this.cancel(false);
            }
            else if (!periodic) {
                this.run();
            }
            else if (this.runAndReset()) {
                this.setNextRunTime();
                ScheduledThreadPoolExecutor.this.reExecutePeriodic(this.outerTask);
            }
        }
    }
}
