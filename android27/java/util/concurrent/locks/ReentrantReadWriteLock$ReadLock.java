package java.util.concurrent.locks;

import java.io.*;
import java.util.concurrent.*;

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
