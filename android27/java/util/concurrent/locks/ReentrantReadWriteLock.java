package java.util.concurrent.locks;

import sun.misc.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class ReentrantReadWriteLock implements ReadWriteLock, Serializable
{
    private static final long serialVersionUID = -6992448646407690164L;
    private final ReadLock readerLock;
    private final WriteLock writerLock;
    final Sync sync;
    private static final Unsafe UNSAFE;
    private static final long TID_OFFSET;
    
    public ReentrantReadWriteLock() {
        this(false);
    }
    
    public ReentrantReadWriteLock(final boolean b) {
        this.sync = (b ? new FairSync() : new NonfairSync());
        this.readerLock = new ReadLock(this);
        this.writerLock = new WriteLock(this);
    }
    
    @Override
    public WriteLock writeLock() {
        return this.writerLock;
    }
    
    @Override
    public ReadLock readLock() {
        return this.readerLock;
    }
    
    public final boolean isFair() {
        return this.sync instanceof FairSync;
    }
    
    protected Thread getOwner() {
        return this.sync.getOwner();
    }
    
    public int getReadLockCount() {
        return this.sync.getReadLockCount();
    }
    
    public boolean isWriteLocked() {
        return this.sync.isWriteLocked();
    }
    
    public boolean isWriteLockedByCurrentThread() {
        return this.sync.isHeldExclusively();
    }
    
    public int getWriteHoldCount() {
        return this.sync.getWriteHoldCount();
    }
    
    public int getReadHoldCount() {
        return this.sync.getReadHoldCount();
    }
    
    protected Collection<Thread> getQueuedWriterThreads() {
        return this.sync.getExclusiveQueuedThreads();
    }
    
    protected Collection<Thread> getQueuedReaderThreads() {
        return this.sync.getSharedQueuedThreads();
    }
    
    public final boolean hasQueuedThreads() {
        return this.sync.hasQueuedThreads();
    }
    
    public final boolean hasQueuedThread(final Thread thread) {
        return this.sync.isQueued(thread);
    }
    
    public final int getQueueLength() {
        return this.sync.getQueueLength();
    }
    
    protected Collection<Thread> getQueuedThreads() {
        return this.sync.getQueuedThreads();
    }
    
    public boolean hasWaiters(final Condition condition) {
        if (condition == null) {
            throw new NullPointerException();
        }
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject)) {
            throw new IllegalArgumentException("not owner");
        }
        return this.sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject)condition);
    }
    
    public int getWaitQueueLength(final Condition condition) {
        if (condition == null) {
            throw new NullPointerException();
        }
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject)) {
            throw new IllegalArgumentException("not owner");
        }
        return this.sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject)condition);
    }
    
    protected Collection<Thread> getWaitingThreads(final Condition condition) {
        if (condition == null) {
            throw new NullPointerException();
        }
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject)) {
            throw new IllegalArgumentException("not owner");
        }
        return this.sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)condition);
    }
    
    @Override
    public String toString() {
        final int count = this.sync.getCount();
        return super.toString() + "[Write locks = " + Sync.exclusiveCount(count) + ", Read locks = " + Sync.sharedCount(count) + "]";
    }
    
    static final long getThreadId(final Thread thread) {
        return ReentrantReadWriteLock.UNSAFE.getLongVolatile(thread, ReentrantReadWriteLock.TID_OFFSET);
    }
    
    static {
        try {
            UNSAFE = Unsafe.getUnsafe();
            TID_OFFSET = ReentrantReadWriteLock.UNSAFE.objectFieldOffset(Thread.class.getDeclaredField("tid"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class FairSync extends Sync
    {
        private static final long serialVersionUID = -2274990926593161451L;
        
        @Override
        final boolean writerShouldBlock() {
            return this.hasQueuedPredecessors();
        }
        
        @Override
        final boolean readerShouldBlock() {
            return this.hasQueuedPredecessors();
        }
    }
    
    abstract static class Sync extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = 6317671515068378041L;
        static final int SHARED_SHIFT = 16;
        static final int SHARED_UNIT = 65536;
        static final int MAX_COUNT = 65535;
        static final int EXCLUSIVE_MASK = 65535;
        private transient ThreadLocalHoldCounter readHolds;
        private transient HoldCounter cachedHoldCounter;
        private transient Thread firstReader;
        private transient int firstReaderHoldCount;
        
        static int sharedCount(final int n) {
            return n >>> 16;
        }
        
        static int exclusiveCount(final int n) {
            return n & 0xFFFF;
        }
        
        Sync() {
            this.firstReader = null;
            this.readHolds = new ThreadLocalHoldCounter();
            this.setState(this.getState());
        }
        
        abstract boolean readerShouldBlock();
        
        abstract boolean writerShouldBlock();
        
        @Override
        protected final boolean tryRelease(final int n) {
            if (!this.isHeldExclusively()) {
                throw new IllegalMonitorStateException();
            }
            final int state = this.getState() - n;
            final boolean b = exclusiveCount(state) == 0;
            if (b) {
                this.setExclusiveOwnerThread(null);
            }
            this.setState(state);
            return b;
        }
        
        @Override
        protected final boolean tryAcquire(final int n) {
            final Thread currentThread = Thread.currentThread();
            final int state = this.getState();
            final int exclusiveCount = exclusiveCount(state);
            if (state != 0) {
                if (exclusiveCount == 0 || currentThread != this.getExclusiveOwnerThread()) {
                    return false;
                }
                if (exclusiveCount + exclusiveCount(n) > 65535) {
                    throw new Error("Maximum lock count exceeded");
                }
                this.setState(state + n);
                return true;
            }
            else {
                if (this.writerShouldBlock() || !this.compareAndSetState(state, state + n)) {
                    return false;
                }
                this.setExclusiveOwnerThread(currentThread);
                return true;
            }
        }
        
        @Override
        protected final boolean tryReleaseShared(final int n) {
            final Thread currentThread = Thread.currentThread();
            if (this.firstReader == currentThread) {
                if (this.firstReaderHoldCount == 1) {
                    this.firstReader = null;
                }
                else {
                    --this.firstReaderHoldCount;
                }
            }
            else {
                HoldCounter cachedHoldCounter = this.cachedHoldCounter;
                if (cachedHoldCounter == null || cachedHoldCounter.tid != ReentrantReadWriteLock.getThreadId(currentThread)) {
                    cachedHoldCounter = this.readHolds.get();
                }
                final int count = cachedHoldCounter.count;
                if (count <= 1) {
                    this.readHolds.remove();
                    if (count <= 0) {
                        throw this.unmatchedUnlockException();
                    }
                }
                final HoldCounter holdCounter = cachedHoldCounter;
                --holdCounter.count;
            }
            int state;
            int n2;
            do {
                state = this.getState();
                n2 = state - 65536;
            } while (!this.compareAndSetState(state, n2));
            return n2 == 0;
        }
        
        private IllegalMonitorStateException unmatchedUnlockException() {
            return new IllegalMonitorStateException("attempt to unlock read lock, not locked by current thread");
        }
        
        @Override
        protected final int tryAcquireShared(final int n) {
            final Thread currentThread = Thread.currentThread();
            final int state = this.getState();
            if (exclusiveCount(state) != 0 && this.getExclusiveOwnerThread() != currentThread) {
                return -1;
            }
            final int sharedCount = sharedCount(state);
            if (!this.readerShouldBlock() && sharedCount < 65535 && this.compareAndSetState(state, state + 65536)) {
                if (sharedCount == 0) {
                    this.firstReader = currentThread;
                    this.firstReaderHoldCount = 1;
                }
                else if (this.firstReader == currentThread) {
                    ++this.firstReaderHoldCount;
                }
                else {
                    HoldCounter cachedHoldCounter = this.cachedHoldCounter;
                    if (cachedHoldCounter == null || cachedHoldCounter.tid != ReentrantReadWriteLock.getThreadId(currentThread)) {
                        cachedHoldCounter = (this.cachedHoldCounter = this.readHolds.get());
                    }
                    else if (cachedHoldCounter.count == 0) {
                        this.readHolds.set(cachedHoldCounter);
                    }
                    final HoldCounter holdCounter = cachedHoldCounter;
                    ++holdCounter.count;
                }
                return 1;
            }
            return this.fullTryAcquireShared(currentThread);
        }
        
        final int fullTryAcquireShared(final Thread firstReader) {
            HoldCounter cachedHoldCounter = null;
            while (true) {
                final int state = this.getState();
                if (exclusiveCount(state) != 0) {
                    if (this.getExclusiveOwnerThread() != firstReader) {
                        return -1;
                    }
                }
                else if (this.readerShouldBlock()) {
                    if (this.firstReader != firstReader) {
                        if (cachedHoldCounter == null) {
                            cachedHoldCounter = this.cachedHoldCounter;
                            if (cachedHoldCounter == null || cachedHoldCounter.tid != ReentrantReadWriteLock.getThreadId(firstReader)) {
                                cachedHoldCounter = this.readHolds.get();
                                if (cachedHoldCounter.count == 0) {
                                    this.readHolds.remove();
                                }
                            }
                        }
                        if (cachedHoldCounter.count == 0) {
                            return -1;
                        }
                    }
                }
                if (sharedCount(state) == 65535) {
                    throw new Error("Maximum lock count exceeded");
                }
                if (this.compareAndSetState(state, state + 65536)) {
                    if (sharedCount(state) == 0) {
                        this.firstReader = firstReader;
                        this.firstReaderHoldCount = 1;
                    }
                    else if (this.firstReader == firstReader) {
                        ++this.firstReaderHoldCount;
                    }
                    else {
                        if (cachedHoldCounter == null) {
                            cachedHoldCounter = this.cachedHoldCounter;
                        }
                        if (cachedHoldCounter == null || cachedHoldCounter.tid != ReentrantReadWriteLock.getThreadId(firstReader)) {
                            cachedHoldCounter = this.readHolds.get();
                        }
                        else if (cachedHoldCounter.count == 0) {
                            this.readHolds.set(cachedHoldCounter);
                        }
                        final HoldCounter holdCounter = cachedHoldCounter;
                        ++holdCounter.count;
                        this.cachedHoldCounter = cachedHoldCounter;
                    }
                    return 1;
                }
            }
        }
        
        final boolean tryWriteLock() {
            final Thread currentThread = Thread.currentThread();
            final int state = this.getState();
            if (state != 0) {
                final int exclusiveCount = exclusiveCount(state);
                if (exclusiveCount == 0 || currentThread != this.getExclusiveOwnerThread()) {
                    return false;
                }
                if (exclusiveCount == 65535) {
                    throw new Error("Maximum lock count exceeded");
                }
            }
            if (!this.compareAndSetState(state, state + 1)) {
                return false;
            }
            this.setExclusiveOwnerThread(currentThread);
            return true;
        }
        
        final boolean tryReadLock() {
            final Thread currentThread = Thread.currentThread();
            while (true) {
                final int state = this.getState();
                if (exclusiveCount(state) != 0 && this.getExclusiveOwnerThread() != currentThread) {
                    return false;
                }
                final int sharedCount = sharedCount(state);
                if (sharedCount == 65535) {
                    throw new Error("Maximum lock count exceeded");
                }
                if (this.compareAndSetState(state, state + 65536)) {
                    if (sharedCount == 0) {
                        this.firstReader = currentThread;
                        this.firstReaderHoldCount = 1;
                    }
                    else if (this.firstReader == currentThread) {
                        ++this.firstReaderHoldCount;
                    }
                    else {
                        HoldCounter cachedHoldCounter = this.cachedHoldCounter;
                        if (cachedHoldCounter == null || cachedHoldCounter.tid != ReentrantReadWriteLock.getThreadId(currentThread)) {
                            cachedHoldCounter = (this.cachedHoldCounter = this.readHolds.get());
                        }
                        else if (cachedHoldCounter.count == 0) {
                            this.readHolds.set(cachedHoldCounter);
                        }
                        final HoldCounter holdCounter = cachedHoldCounter;
                        ++holdCounter.count;
                    }
                    return true;
                }
            }
        }
        
        @Override
        protected final boolean isHeldExclusively() {
            return this.getExclusiveOwnerThread() == Thread.currentThread();
        }
        
        final ConditionObject newCondition() {
            return new ConditionObject(this);
        }
        
        final Thread getOwner() {
            return (exclusiveCount(this.getState()) == 0) ? null : this.getExclusiveOwnerThread();
        }
        
        final int getReadLockCount() {
            return sharedCount(this.getState());
        }
        
        final boolean isWriteLocked() {
            return exclusiveCount(this.getState()) != 0;
        }
        
        final int getWriteHoldCount() {
            return this.isHeldExclusively() ? exclusiveCount(this.getState()) : 0;
        }
        
        final int getReadHoldCount() {
            if (this.getReadLockCount() == 0) {
                return 0;
            }
            final Thread currentThread = Thread.currentThread();
            if (this.firstReader == currentThread) {
                return this.firstReaderHoldCount;
            }
            final HoldCounter cachedHoldCounter = this.cachedHoldCounter;
            if (cachedHoldCounter != null && cachedHoldCounter.tid == ReentrantReadWriteLock.getThreadId(currentThread)) {
                return cachedHoldCounter.count;
            }
            final int count = this.readHolds.get().count;
            if (count == 0) {
                this.readHolds.remove();
            }
            return count;
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.readHolds = new ThreadLocalHoldCounter();
            this.setState(0);
        }
        
        final int getCount() {
            return this.getState();
        }
        
        static final class HoldCounter
        {
            int count;
            final long tid;
            
            HoldCounter() {
                this.count = 0;
                this.tid = ReentrantReadWriteLock.getThreadId(Thread.currentThread());
            }
        }
        
        static final class ThreadLocalHoldCounter extends ThreadLocal<HoldCounter>
        {
            public HoldCounter initialValue() {
                return new HoldCounter();
            }
        }
    }
    
    static final class NonfairSync extends Sync
    {
        private static final long serialVersionUID = -8159625535654395037L;
        
        @Override
        final boolean writerShouldBlock() {
            return false;
        }
        
        @Override
        final boolean readerShouldBlock() {
            return this.apparentlyFirstQueuedIsExclusive();
        }
    }
    
    public static class ReadLock implements Lock, Serializable
    {
        private static final long serialVersionUID = -5992448646407690164L;
        private final Sync sync;
        
        protected ReadLock(final ReentrantReadWriteLock reentrantReadWriteLock) {
            this.sync = reentrantReadWriteLock.sync;
        }
        
        @Override
        public void lock() {
            this.sync.acquireShared(1);
        }
        
        @Override
        public void lockInterruptibly() throws InterruptedException {
            this.sync.acquireSharedInterruptibly(1);
        }
        
        @Override
        public boolean tryLock() {
            return this.sync.tryReadLock();
        }
        
        @Override
        public boolean tryLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
            return this.sync.tryAcquireSharedNanos(1, timeUnit.toNanos(n));
        }
        
        @Override
        public void unlock() {
            this.sync.releaseShared(1);
        }
        
        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public String toString() {
            return super.toString() + "[Read locks = " + this.sync.getReadLockCount() + "]";
        }
    }
    
    public static class WriteLock implements Lock, Serializable
    {
        private static final long serialVersionUID = -4992448646407690164L;
        private final Sync sync;
        
        protected WriteLock(final ReentrantReadWriteLock reentrantReadWriteLock) {
            this.sync = reentrantReadWriteLock.sync;
        }
        
        @Override
        public void lock() {
            this.sync.acquire(1);
        }
        
        @Override
        public void lockInterruptibly() throws InterruptedException {
            this.sync.acquireInterruptibly(1);
        }
        
        @Override
        public boolean tryLock() {
            return this.sync.tryWriteLock();
        }
        
        @Override
        public boolean tryLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
            return this.sync.tryAcquireNanos(1, timeUnit.toNanos(n));
        }
        
        @Override
        public void unlock() {
            this.sync.release(1);
        }
        
        @Override
        public Condition newCondition() {
            return this.sync.newCondition();
        }
        
        @Override
        public String toString() {
            final Thread owner = this.sync.getOwner();
            return super.toString() + ((owner == null) ? "[Unlocked]" : ("[Locked by thread " + owner.getName() + "]"));
        }
        
        public boolean isHeldByCurrentThread() {
            return this.sync.isHeldExclusively();
        }
        
        public int getHoldCount() {
            return this.sync.getWriteHoldCount();
        }
    }
}
