package java.util.concurrent.locks;

import java.io.*;
import java.util.concurrent.*;

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
