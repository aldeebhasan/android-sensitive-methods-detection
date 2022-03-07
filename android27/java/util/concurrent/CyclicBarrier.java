package java.util.concurrent;

import java.util.concurrent.locks.*;

public class CyclicBarrier
{
    private final ReentrantLock lock;
    private final Condition trip;
    private final int parties;
    private final Runnable barrierCommand;
    private Generation generation;
    private int count;
    
    private void nextGeneration() {
        this.trip.signalAll();
        this.count = this.parties;
        this.generation = new Generation();
    }
    
    private void breakBarrier() {
        this.generation.broken = true;
        this.count = this.parties;
        this.trip.signalAll();
    }
    
    private int dowait(final boolean b, long awaitNanos) throws InterruptedException, BrokenBarrierException, TimeoutException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Generation generation = this.generation;
            if (generation.broken) {
                throw new BrokenBarrierException();
            }
            if (Thread.interrupted()) {
                this.breakBarrier();
                throw new InterruptedException();
            }
            final int count = this.count - 1;
            this.count = count;
            final int n = count;
            if (n == 0) {
                boolean b2 = false;
                try {
                    final Runnable barrierCommand = this.barrierCommand;
                    if (barrierCommand != null) {
                        barrierCommand.run();
                    }
                    b2 = true;
                    this.nextGeneration();
                    return 0;
                }
                finally {
                    if (!b2) {
                        this.breakBarrier();
                    }
                }
            }
            while (true) {
                try {
                    if (!b) {
                        this.trip.await();
                    }
                    else if (awaitNanos > 0L) {
                        awaitNanos = this.trip.awaitNanos(awaitNanos);
                    }
                }
                catch (InterruptedException ex) {
                    if (generation == this.generation && !generation.broken) {
                        this.breakBarrier();
                        throw ex;
                    }
                    Thread.currentThread().interrupt();
                }
                if (generation.broken) {
                    throw new BrokenBarrierException();
                }
                if (generation != this.generation) {
                    return n;
                }
                if (b && awaitNanos <= 0L) {
                    this.breakBarrier();
                    throw new TimeoutException();
                }
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    public CyclicBarrier(final int n, final Runnable barrierCommand) {
        this.lock = new ReentrantLock();
        this.trip = this.lock.newCondition();
        this.generation = new Generation();
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.parties = n;
        this.count = n;
        this.barrierCommand = barrierCommand;
    }
    
    public CyclicBarrier(final int n) {
        this(n, null);
    }
    
    public int getParties() {
        return this.parties;
    }
    
    public int await() throws InterruptedException, BrokenBarrierException {
        try {
            return this.dowait(false, 0L);
        }
        catch (TimeoutException ex) {
            throw new Error(ex);
        }
    }
    
    public int await(final long n, final TimeUnit timeUnit) throws InterruptedException, BrokenBarrierException, TimeoutException {
        return this.dowait(true, timeUnit.toNanos(n));
    }
    
    public boolean isBroken() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.generation.broken;
        }
        finally {
            lock.unlock();
        }
    }
    
    public void reset() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.breakBarrier();
            this.nextGeneration();
        }
        finally {
            lock.unlock();
        }
    }
    
    public int getNumberWaiting() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.parties - this.count;
        }
        finally {
            lock.unlock();
        }
    }
    
    private static class Generation
    {
        boolean broken;
        
        private Generation() {
            this.broken = false;
        }
    }
}
