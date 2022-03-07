package java.util.concurrent;

import sun.misc.*;
import java.util.concurrent.locks.*;

public class FutureTask<V> implements RunnableFuture<V>
{
    private volatile int state;
    private static final int NEW = 0;
    private static final int COMPLETING = 1;
    private static final int NORMAL = 2;
    private static final int EXCEPTIONAL = 3;
    private static final int CANCELLED = 4;
    private static final int INTERRUPTING = 5;
    private static final int INTERRUPTED = 6;
    private Callable<V> callable;
    private Object outcome;
    private volatile Thread runner;
    private volatile WaitNode waiters;
    private static final Unsafe UNSAFE;
    private static final long stateOffset;
    private static final long runnerOffset;
    private static final long waitersOffset;
    
    private V report(final int n) throws ExecutionException {
        final Object outcome = this.outcome;
        if (n == 2) {
            return (V)outcome;
        }
        if (n >= 4) {
            throw new CancellationException();
        }
        throw new ExecutionException((Throwable)outcome);
    }
    
    public FutureTask(final Callable<V> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        this.callable = callable;
        this.state = 0;
    }
    
    public FutureTask(final Runnable runnable, final V v) {
        this.callable = Executors.callable(runnable, v);
        this.state = 0;
    }
    
    @Override
    public boolean isCancelled() {
        return this.state >= 4;
    }
    
    @Override
    public boolean isDone() {
        return this.state != 0;
    }
    
    @Override
    public boolean cancel(final boolean b) {
        if (this.state != 0 || !FutureTask.UNSAFE.compareAndSwapInt(this, FutureTask.stateOffset, 0, b ? 5 : 4)) {
            return false;
        }
        try {
            if (b) {
                try {
                    final Thread runner = this.runner;
                    if (runner != null) {
                        runner.interrupt();
                    }
                }
                finally {
                    FutureTask.UNSAFE.putOrderedInt(this, FutureTask.stateOffset, 6);
                }
            }
        }
        finally {
            this.finishCompletion();
        }
        return true;
    }
    
    @Override
    public V get() throws InterruptedException, ExecutionException {
        int n = this.state;
        if (n <= 1) {
            n = this.awaitDone(false, 0L);
        }
        return this.report(n);
    }
    
    @Override
    public V get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (timeUnit == null) {
            throw new NullPointerException();
        }
        int n2 = this.state;
        if (n2 <= 1 && (n2 = this.awaitDone(true, timeUnit.toNanos(n))) <= 1) {
            throw new TimeoutException();
        }
        return this.report(n2);
    }
    
    protected void done() {
    }
    
    protected void set(final V outcome) {
        if (FutureTask.UNSAFE.compareAndSwapInt(this, FutureTask.stateOffset, 0, 1)) {
            this.outcome = outcome;
            FutureTask.UNSAFE.putOrderedInt(this, FutureTask.stateOffset, 2);
            this.finishCompletion();
        }
    }
    
    protected void setException(final Throwable outcome) {
        if (FutureTask.UNSAFE.compareAndSwapInt(this, FutureTask.stateOffset, 0, 1)) {
            this.outcome = outcome;
            FutureTask.UNSAFE.putOrderedInt(this, FutureTask.stateOffset, 3);
            this.finishCompletion();
        }
    }
    
    @Override
    public void run() {
        if (this.state != 0 || !FutureTask.UNSAFE.compareAndSwapObject(this, FutureTask.runnerOffset, null, Thread.currentThread())) {
            return;
        }
        try {
            final Callable<V> callable = this.callable;
            if (callable != null && this.state == 0) {
                V call;
                boolean b;
                try {
                    call = callable.call();
                    b = true;
                }
                catch (Throwable exception) {
                    call = null;
                    b = false;
                    this.setException(exception);
                }
                if (b) {
                    this.set(call);
                }
            }
        }
        finally {
            this.runner = null;
            final int state = this.state;
            if (state >= 5) {
                this.handlePossibleCancellationInterrupt(state);
            }
        }
    }
    
    protected boolean runAndReset() {
        if (this.state != 0 || !FutureTask.UNSAFE.compareAndSwapObject(this, FutureTask.runnerOffset, null, Thread.currentThread())) {
            return false;
        }
        boolean b = false;
        final int state = this.state;
        int state2 = 0;
        try {
            final Callable<V> callable = this.callable;
            if (callable != null && state == 0) {
                try {
                    callable.call();
                    b = true;
                }
                catch (Throwable exception) {
                    this.setException(exception);
                }
            }
        }
        finally {
            this.runner = null;
            state2 = this.state;
            if (state2 >= 5) {
                this.handlePossibleCancellationInterrupt(state2);
            }
        }
        return b && state2 == 0;
    }
    
    private void handlePossibleCancellationInterrupt(final int n) {
        if (n == 5) {
            while (this.state == 5) {
                Thread.yield();
            }
        }
    }
    
    private void finishCompletion() {
        WaitNode waiters;
        while ((waiters = this.waiters) != null) {
            if (FutureTask.UNSAFE.compareAndSwapObject(this, FutureTask.waitersOffset, waiters, null)) {
                while (true) {
                    final Thread thread = waiters.thread;
                    if (thread != null) {
                        waiters.thread = null;
                        LockSupport.unpark(thread);
                    }
                    final WaitNode next = waiters.next;
                    if (next == null) {
                        break;
                    }
                    waiters.next = null;
                    waiters = next;
                }
                break;
            }
        }
        this.done();
        this.callable = null;
    }
    
    private int awaitDone(final boolean b, long n) throws InterruptedException {
        final long n2 = b ? (System.nanoTime() + n) : 0L;
        WaitNode waitNode = null;
        boolean compareAndSwapObject = false;
        while (!Thread.interrupted()) {
            final int state = this.state;
            if (state > 1) {
                if (waitNode != null) {
                    waitNode.thread = null;
                }
                return state;
            }
            if (state == 1) {
                Thread.yield();
            }
            else if (waitNode == null) {
                waitNode = new WaitNode();
            }
            else if (!compareAndSwapObject) {
                final Unsafe unsafe = FutureTask.UNSAFE;
                final long waitersOffset = FutureTask.waitersOffset;
                final WaitNode waitNode2 = waitNode;
                final WaitNode waiters = this.waiters;
                waitNode2.next = waiters;
                compareAndSwapObject = unsafe.compareAndSwapObject(this, waitersOffset, waiters, waitNode);
            }
            else if (b) {
                n = n2 - System.nanoTime();
                if (n <= 0L) {
                    this.removeWaiter(waitNode);
                    return this.state;
                }
                LockSupport.parkNanos(this, n);
            }
            else {
                LockSupport.park(this);
            }
        }
        this.removeWaiter(waitNode);
        throw new InterruptedException();
    }
    
    private void removeWaiter(final WaitNode waitNode) {
        if (waitNode != null) {
            waitNode.thread = null;
        Label_0009:
            while (true) {
                WaitNode waitNode2 = null;
                WaitNode next;
                for (WaitNode waiters = this.waiters; waiters != null; waiters = next) {
                    next = waiters.next;
                    if (waiters.thread != null) {
                        waitNode2 = waiters;
                    }
                    else if (waitNode2 != null) {
                        waitNode2.next = next;
                        if (waitNode2.thread == null) {
                            continue Label_0009;
                        }
                    }
                    else if (!FutureTask.UNSAFE.compareAndSwapObject(this, FutureTask.waitersOffset, waiters, next)) {
                        continue Label_0009;
                    }
                }
                break;
            }
        }
    }
    
    static {
        try {
            UNSAFE = Unsafe.getUnsafe();
            final Class<FutureTask> clazz = FutureTask.class;
            stateOffset = FutureTask.UNSAFE.objectFieldOffset(clazz.getDeclaredField("state"));
            runnerOffset = FutureTask.UNSAFE.objectFieldOffset(clazz.getDeclaredField("runner"));
            waitersOffset = FutureTask.UNSAFE.objectFieldOffset(clazz.getDeclaredField("waiters"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class WaitNode
    {
        volatile Thread thread;
        volatile WaitNode next;
        
        WaitNode() {
            this.thread = Thread.currentThread();
        }
    }
}
