package java.util.concurrent;

import sun.misc.*;

public abstract class CountedCompleter<T> extends ForkJoinTask<T>
{
    private static final long serialVersionUID = 5232453752276485070L;
    final CountedCompleter<?> completer;
    volatile int pending;
    private static final Unsafe U;
    private static final long PENDING;
    
    protected CountedCompleter(final CountedCompleter<?> completer, final int pending) {
        this.completer = completer;
        this.pending = pending;
    }
    
    protected CountedCompleter(final CountedCompleter<?> completer) {
        this.completer = completer;
    }
    
    protected CountedCompleter() {
        this.completer = null;
    }
    
    public abstract void compute();
    
    public void onCompletion(final CountedCompleter<?> countedCompleter) {
    }
    
    public boolean onExceptionalCompletion(final Throwable t, final CountedCompleter<?> countedCompleter) {
        return true;
    }
    
    public final CountedCompleter<?> getCompleter() {
        return this.completer;
    }
    
    public final int getPendingCount() {
        return this.pending;
    }
    
    public final void setPendingCount(final int pending) {
        this.pending = pending;
    }
    
    public final void addToPendingCount(final int n) {
        CountedCompleter.U.getAndAddInt(this, CountedCompleter.PENDING, n);
    }
    
    public final boolean compareAndSetPendingCount(final int n, final int n2) {
        return CountedCompleter.U.compareAndSwapInt(this, CountedCompleter.PENDING, n, n2);
    }
    
    public final int decrementPendingCountUnlessZero() {
        int pending;
        while ((pending = this.pending) != 0 && !CountedCompleter.U.compareAndSwapInt(this, CountedCompleter.PENDING, pending, pending - 1)) {}
        return pending;
    }
    
    public final CountedCompleter<?> getRoot() {
        CountedCompleter countedCompleter;
        CountedCompleter<?> completer;
        for (countedCompleter = this; (completer = countedCompleter.completer) != null; countedCompleter = completer) {}
        return (CountedCompleter<?>)countedCompleter;
    }
    
    public final void tryComplete() {
        CountedCompleter countedCompleter;
        CountedCompleter<?> completer = countedCompleter = this;
        while (true) {
            final int pending;
            if ((pending = completer.pending) == 0) {
                completer.onCompletion(countedCompleter);
                if ((completer = (countedCompleter = completer).completer) == null) {
                    countedCompleter.quietlyComplete();
                    return;
                }
                continue;
            }
            else {
                if (CountedCompleter.U.compareAndSwapInt(completer, CountedCompleter.PENDING, pending, pending - 1)) {
                    return;
                }
                continue;
            }
        }
    }
    
    public final void propagateCompletion() {
        CountedCompleter<?> completer = this;
        while (true) {
            final int pending;
            if ((pending = completer.pending) == 0) {
                final CountedCompleter<?> countedCompleter;
                if ((completer = (countedCompleter = completer).completer) == null) {
                    countedCompleter.quietlyComplete();
                    return;
                }
                continue;
            }
            else {
                if (CountedCompleter.U.compareAndSwapInt(completer, CountedCompleter.PENDING, pending, pending - 1)) {
                    return;
                }
                continue;
            }
        }
    }
    
    @Override
    public void complete(final T rawResult) {
        this.setRawResult(rawResult);
        this.onCompletion(this);
        this.quietlyComplete();
        final CountedCompleter<?> completer;
        if ((completer = this.completer) != null) {
            completer.tryComplete();
        }
    }
    
    public final CountedCompleter<?> firstComplete() {
        int pending;
        while ((pending = this.pending) != 0) {
            if (CountedCompleter.U.compareAndSwapInt(this, CountedCompleter.PENDING, pending, pending - 1)) {
                return null;
            }
        }
        return this;
    }
    
    public final CountedCompleter<?> nextComplete() {
        final CountedCompleter<?> completer;
        if ((completer = this.completer) != null) {
            return (CountedCompleter<?>)completer.firstComplete();
        }
        this.quietlyComplete();
        return null;
    }
    
    public final void quietlyCompleteRoot() {
        CountedCompleter countedCompleter;
        CountedCompleter<?> completer;
        for (countedCompleter = this; (completer = countedCompleter.completer) != null; countedCompleter = completer) {}
        countedCompleter.quietlyComplete();
    }
    
    public final void helpComplete(final int n) {
        if (n > 0 && this.status >= 0) {
            final Thread currentThread;
            if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
                final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
                forkJoinWorkerThread.pool.helpComplete(forkJoinWorkerThread.workQueue, this, n);
            }
            else {
                ForkJoinPool.common.externalHelpComplete(this, n);
            }
        }
    }
    
    @Override
    void internalPropagateException(final Throwable t) {
        CountedCompleter countedCompleter;
        CountedCompleter<?> completer = countedCompleter = this;
        while (completer.onExceptionalCompletion(t, countedCompleter) && (completer = (countedCompleter = completer).completer) != null && completer.status >= 0 && completer.recordExceptionalCompletion(t) == Integer.MIN_VALUE) {}
    }
    
    @Override
    protected final boolean exec() {
        this.compute();
        return false;
    }
    
    @Override
    public T getRawResult() {
        return null;
    }
    
    @Override
    protected void setRawResult(final T t) {
    }
    
    static {
        try {
            U = Unsafe.getUnsafe();
            PENDING = CountedCompleter.U.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
