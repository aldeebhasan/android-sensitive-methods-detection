package java.util.concurrent;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class Semaphore implements Serializable
{
    private static final long serialVersionUID = -3222578661600680210L;
    private final Sync sync;
    
    public Semaphore(final int n) {
        this.sync = new NonfairSync(n);
    }
    
    public Semaphore(final int n, final boolean b) {
        this.sync = (b ? new FairSync(n) : new NonfairSync(n));
    }
    
    public void acquire() throws InterruptedException {
        this.sync.acquireSharedInterruptibly(1);
    }
    
    public void acquireUninterruptibly() {
        this.sync.acquireShared(1);
    }
    
    public boolean tryAcquire() {
        return this.sync.nonfairTryAcquireShared(1) >= 0;
    }
    
    public boolean tryAcquire(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.sync.tryAcquireSharedNanos(1, timeUnit.toNanos(n));
    }
    
    public void release() {
        this.sync.releaseShared(1);
    }
    
    public void acquire(final int n) throws InterruptedException {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.sync.acquireSharedInterruptibly(n);
    }
    
    public void acquireUninterruptibly(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.sync.acquireShared(n);
    }
    
    public boolean tryAcquire(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return this.sync.nonfairTryAcquireShared(n) >= 0;
    }
    
    public boolean tryAcquire(final int n, final long n2, final TimeUnit timeUnit) throws InterruptedException {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        return this.sync.tryAcquireSharedNanos(n, timeUnit.toNanos(n2));
    }
    
    public void release(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.sync.releaseShared(n);
    }
    
    public int availablePermits() {
        return this.sync.getPermits();
    }
    
    public int drainPermits() {
        return this.sync.drainPermits();
    }
    
    protected void reducePermits(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.sync.reducePermits(n);
    }
    
    public boolean isFair() {
        return this.sync instanceof FairSync;
    }
    
    public final boolean hasQueuedThreads() {
        return this.sync.hasQueuedThreads();
    }
    
    public final int getQueueLength() {
        return this.sync.getQueueLength();
    }
    
    protected Collection<Thread> getQueuedThreads() {
        return this.sync.getQueuedThreads();
    }
    
    @Override
    public String toString() {
        return super.toString() + "[Permits = " + this.sync.getPermits() + "]";
    }
    
    static final class FairSync extends Sync
    {
        private static final long serialVersionUID = 2014338818796000944L;
        
        FairSync(final int n) {
            super(n);
        }
        
        @Override
        protected int tryAcquireShared(final int n) {
            while (!this.hasQueuedPredecessors()) {
                final int state = this.getState();
                final int n2 = state - n;
                if (n2 < 0 || this.compareAndSetState(state, n2)) {
                    return n2;
                }
            }
            return -1;
        }
    }
    
    abstract static class Sync extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = 1192457210091910933L;
        
        Sync(final int state) {
            this.setState(state);
        }
        
        final int getPermits() {
            return this.getState();
        }
        
        final int nonfairTryAcquireShared(final int n) {
            int state;
            int n2;
            do {
                state = this.getState();
                n2 = state - n;
            } while (n2 >= 0 && !this.compareAndSetState(state, n2));
            return n2;
        }
        
        @Override
        protected final boolean tryReleaseShared(final int n) {
            while (true) {
                final int state = this.getState();
                final int n2 = state + n;
                if (n2 < state) {
                    throw new Error("Maximum permit count exceeded");
                }
                if (this.compareAndSetState(state, n2)) {
                    return true;
                }
            }
        }
        
        final void reducePermits(final int n) {
            while (true) {
                final int state = this.getState();
                final int n2 = state - n;
                if (n2 > state) {
                    throw new Error("Permit count underflow");
                }
                if (this.compareAndSetState(state, n2)) {
                    return;
                }
            }
        }
        
        final int drainPermits() {
            int state;
            do {
                state = this.getState();
            } while (state != 0 && !this.compareAndSetState(state, 0));
            return state;
        }
    }
    
    static final class NonfairSync extends Sync
    {
        private static final long serialVersionUID = -2694183684443567898L;
        
        NonfairSync(final int n) {
            super(n);
        }
        
        @Override
        protected int tryAcquireShared(final int n) {
            return this.nonfairTryAcquireShared(n);
        }
    }
}
