package java.util.concurrent.locks;

import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class ReentrantLock implements Lock, Serializable
{
    private static final long serialVersionUID = 7373984872572414699L;
    private final Sync sync;
    
    public ReentrantLock() {
        this.sync = new NonfairSync();
    }
    
    public ReentrantLock(final boolean b) {
        this.sync = (b ? new FairSync() : new NonfairSync());
    }
    
    @Override
    public void lock() {
        this.sync.lock();
    }
    
    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.sync.acquireInterruptibly(1);
    }
    
    @Override
    public boolean tryLock() {
        return this.sync.nonfairTryAcquire(1);
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
    
    public int getHoldCount() {
        return this.sync.getHoldCount();
    }
    
    public boolean isHeldByCurrentThread() {
        return this.sync.isHeldExclusively();
    }
    
    public boolean isLocked() {
        return this.sync.isLocked();
    }
    
    public final boolean isFair() {
        return this.sync instanceof FairSync;
    }
    
    protected Thread getOwner() {
        return this.sync.getOwner();
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
        final Thread owner = this.sync.getOwner();
        return super.toString() + ((owner == null) ? "[Unlocked]" : ("[Locked by thread " + owner.getName() + "]"));
    }
    
    static final class FairSync extends Sync
    {
        private static final long serialVersionUID = -3000897897090466540L;
        
        @Override
        final void lock() {
            this.acquire(1);
        }
        
        @Override
        protected final boolean tryAcquire(final int n) {
            final Thread currentThread = Thread.currentThread();
            final int state = this.getState();
            if (state == 0) {
                if (!this.hasQueuedPredecessors() && this.compareAndSetState(0, n)) {
                    this.setExclusiveOwnerThread(currentThread);
                    return true;
                }
            }
            else if (currentThread == this.getExclusiveOwnerThread()) {
                final int state2 = state + n;
                if (state2 < 0) {
                    throw new Error("Maximum lock count exceeded");
                }
                this.setState(state2);
                return true;
            }
            return false;
        }
    }
    
    abstract static class Sync extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = -5179523762034025860L;
        
        abstract void lock();
        
        final boolean nonfairTryAcquire(final int n) {
            final Thread currentThread = Thread.currentThread();
            final int state = this.getState();
            if (state == 0) {
                if (this.compareAndSetState(0, n)) {
                    this.setExclusiveOwnerThread(currentThread);
                    return true;
                }
            }
            else if (currentThread == this.getExclusiveOwnerThread()) {
                final int state2 = state + n;
                if (state2 < 0) {
                    throw new Error("Maximum lock count exceeded");
                }
                this.setState(state2);
                return true;
            }
            return false;
        }
        
        @Override
        protected final boolean tryRelease(final int n) {
            final int state = this.getState() - n;
            if (Thread.currentThread() != this.getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }
            boolean b = false;
            if (state == 0) {
                b = true;
                this.setExclusiveOwnerThread(null);
            }
            this.setState(state);
            return b;
        }
        
        @Override
        protected final boolean isHeldExclusively() {
            return this.getExclusiveOwnerThread() == Thread.currentThread();
        }
        
        final ConditionObject newCondition() {
            return new ConditionObject(this);
        }
        
        final Thread getOwner() {
            return (this.getState() == 0) ? null : this.getExclusiveOwnerThread();
        }
        
        final int getHoldCount() {
            return this.isHeldExclusively() ? this.getState() : 0;
        }
        
        final boolean isLocked() {
            return this.getState() != 0;
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.setState(0);
        }
    }
    
    static final class NonfairSync extends Sync
    {
        private static final long serialVersionUID = 7316153563782823691L;
        
        @Override
        final void lock() {
            if (this.compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
            }
            else {
                this.acquire(1);
            }
        }
        
        @Override
        protected final boolean tryAcquire(final int n) {
            return this.nonfairTryAcquire(n);
        }
    }
}
