package java.util.concurrent;

import java.util.concurrent.locks.*;

public class CountDownLatch
{
    private final Sync sync;
    
    public CountDownLatch(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("count < 0");
        }
        this.sync = new Sync(n);
    }
    
    public void await() throws InterruptedException {
        this.sync.acquireSharedInterruptibly(1);
    }
    
    public boolean await(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.sync.tryAcquireSharedNanos(1, timeUnit.toNanos(n));
    }
    
    public void countDown() {
        this.sync.releaseShared(1);
    }
    
    public long getCount() {
        return this.sync.getCount();
    }
    
    @Override
    public String toString() {
        return super.toString() + "[Count = " + this.sync.getCount() + "]";
    }
    
    private static final class Sync extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = 4982264981922014374L;
        
        Sync(final int state) {
            this.setState(state);
        }
        
        int getCount() {
            return this.getState();
        }
        
        @Override
        protected int tryAcquireShared(final int n) {
            return (this.getState() == 0) ? 1 : -1;
        }
        
        @Override
        protected boolean tryReleaseShared(final int n) {
            while (true) {
                final int state = this.getState();
                if (state == 0) {
                    return false;
                }
                final int n2 = state - 1;
                if (this.compareAndSetState(state, n2)) {
                    return n2 == 0;
                }
            }
        }
    }
}
