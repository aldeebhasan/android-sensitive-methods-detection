package java.util.concurrent.locks;

import java.io.*;
import java.util.concurrent.*;
import java.util.*;

public class ConditionObject implements Condition, Serializable
{
    private static final long serialVersionUID = 1173984872572414699L;
    private transient Node firstWaiter;
    private transient Node lastWaiter;
    private static final int REINTERRUPT = 1;
    private static final int THROW_IE = -1;
    
    private Node addConditionWaiter() {
        Node node = this.lastWaiter;
        if (node != null && node.waitStatus != -2) {
            this.unlinkCancelledWaiters();
            node = this.lastWaiter;
        }
        final Node lastWaiter = new Node(Thread.currentThread(), -2);
        if (node == null) {
            this.firstWaiter = lastWaiter;
        }
        else {
            node.nextWaiter = lastWaiter;
        }
        return this.lastWaiter = lastWaiter;
    }
    
    private void doSignal(Node firstWaiter) {
        do {
            if ((this.firstWaiter = firstWaiter.nextWaiter) == null) {
                this.lastWaiter = null;
            }
            firstWaiter.nextWaiter = null;
        } while (!AbstractQueuedSynchronizer.this.transferForSignal(firstWaiter) && (firstWaiter = this.firstWaiter) != null);
    }
    
    private void doSignalAll(Node node) {
        final Node node2 = null;
        this.firstWaiter = node2;
        this.lastWaiter = node2;
        do {
            final Node nextWaiter = node.nextWaiter;
            node.nextWaiter = null;
            AbstractQueuedSynchronizer.this.transferForSignal(node);
            node = nextWaiter;
        } while (node != null);
    }
    
    private void unlinkCancelledWaiters() {
        Node firstWaiter = this.firstWaiter;
        Node lastWaiter = null;
        while (firstWaiter != null) {
            final Node nextWaiter = firstWaiter.nextWaiter;
            if (firstWaiter.waitStatus != -2) {
                firstWaiter.nextWaiter = null;
                if (lastWaiter == null) {
                    this.firstWaiter = nextWaiter;
                }
                else {
                    lastWaiter.nextWaiter = nextWaiter;
                }
                if (nextWaiter == null) {
                    this.lastWaiter = lastWaiter;
                }
            }
            else {
                lastWaiter = firstWaiter;
            }
            firstWaiter = nextWaiter;
        }
    }
    
    @Override
    public final void signal() {
        if (!AbstractQueuedSynchronizer.this.isHeldExclusively()) {
            throw new IllegalMonitorStateException();
        }
        final Node firstWaiter = this.firstWaiter;
        if (firstWaiter != null) {
            this.doSignal(firstWaiter);
        }
    }
    
    @Override
    public final void signalAll() {
        if (!AbstractQueuedSynchronizer.this.isHeldExclusively()) {
            throw new IllegalMonitorStateException();
        }
        final Node firstWaiter = this.firstWaiter;
        if (firstWaiter != null) {
            this.doSignalAll(firstWaiter);
        }
    }
    
    @Override
    public final void awaitUninterruptibly() {
        final Node addConditionWaiter = this.addConditionWaiter();
        final int fullyRelease = AbstractQueuedSynchronizer.this.fullyRelease(addConditionWaiter);
        boolean b = false;
        while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(addConditionWaiter)) {
            LockSupport.park(this);
            if (Thread.interrupted()) {
                b = true;
            }
        }
        if (AbstractQueuedSynchronizer.this.acquireQueued(addConditionWaiter, fullyRelease) || b) {
            AbstractQueuedSynchronizer.selfInterrupt();
        }
    }
    
    private int checkInterruptWhileWaiting(final Node node) {
        return Thread.interrupted() ? (AbstractQueuedSynchronizer.this.transferAfterCancelledWait(node) ? -1 : 1) : 0;
    }
    
    private void reportInterruptAfterWait(final int n) throws InterruptedException {
        if (n == -1) {
            throw new InterruptedException();
        }
        if (n == 1) {
            AbstractQueuedSynchronizer.selfInterrupt();
        }
    }
    
    @Override
    public final void await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        final Node addConditionWaiter = this.addConditionWaiter();
        final int fullyRelease = AbstractQueuedSynchronizer.this.fullyRelease(addConditionWaiter);
        int checkInterruptWhileWaiting = 0;
        while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(addConditionWaiter)) {
            LockSupport.park(this);
            if ((checkInterruptWhileWaiting = this.checkInterruptWhileWaiting(addConditionWaiter)) != 0) {
                break;
            }
        }
        if (AbstractQueuedSynchronizer.this.acquireQueued(addConditionWaiter, fullyRelease) && checkInterruptWhileWaiting != -1) {
            checkInterruptWhileWaiting = 1;
        }
        if (addConditionWaiter.nextWaiter != null) {
            this.unlinkCancelledWaiters();
        }
        if (checkInterruptWhileWaiting != 0) {
            this.reportInterruptAfterWait(checkInterruptWhileWaiting);
        }
    }
    
    @Override
    public final long awaitNanos(long n) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        final Node addConditionWaiter = this.addConditionWaiter();
        final int fullyRelease = AbstractQueuedSynchronizer.this.fullyRelease(addConditionWaiter);
        final long n2 = System.nanoTime() + n;
        int checkInterruptWhileWaiting = 0;
        while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(addConditionWaiter)) {
            if (n <= 0L) {
                AbstractQueuedSynchronizer.this.transferAfterCancelledWait(addConditionWaiter);
                break;
            }
            if (n >= 1000L) {
                LockSupport.parkNanos(this, n);
            }
            if ((checkInterruptWhileWaiting = this.checkInterruptWhileWaiting(addConditionWaiter)) != 0) {
                break;
            }
            n = n2 - System.nanoTime();
        }
        if (AbstractQueuedSynchronizer.this.acquireQueued(addConditionWaiter, fullyRelease) && checkInterruptWhileWaiting != -1) {
            checkInterruptWhileWaiting = 1;
        }
        if (addConditionWaiter.nextWaiter != null) {
            this.unlinkCancelledWaiters();
        }
        if (checkInterruptWhileWaiting != 0) {
            this.reportInterruptAfterWait(checkInterruptWhileWaiting);
        }
        return n2 - System.nanoTime();
    }
    
    @Override
    public final boolean awaitUntil(final Date date) throws InterruptedException {
        final long time = date.getTime();
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        final Node addConditionWaiter = this.addConditionWaiter();
        final int fullyRelease = AbstractQueuedSynchronizer.this.fullyRelease(addConditionWaiter);
        boolean transferAfterCancelledWait = false;
        int checkInterruptWhileWaiting = 0;
        while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(addConditionWaiter)) {
            if (System.currentTimeMillis() > time) {
                transferAfterCancelledWait = AbstractQueuedSynchronizer.this.transferAfterCancelledWait(addConditionWaiter);
                break;
            }
            LockSupport.parkUntil(this, time);
            if ((checkInterruptWhileWaiting = this.checkInterruptWhileWaiting(addConditionWaiter)) != 0) {
                break;
            }
        }
        if (AbstractQueuedSynchronizer.this.acquireQueued(addConditionWaiter, fullyRelease) && checkInterruptWhileWaiting != -1) {
            checkInterruptWhileWaiting = 1;
        }
        if (addConditionWaiter.nextWaiter != null) {
            this.unlinkCancelledWaiters();
        }
        if (checkInterruptWhileWaiting != 0) {
            this.reportInterruptAfterWait(checkInterruptWhileWaiting);
        }
        return !transferAfterCancelledWait;
    }
    
    @Override
    public final boolean await(final long n, final TimeUnit timeUnit) throws InterruptedException {
        long nanos = timeUnit.toNanos(n);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        final Node addConditionWaiter = this.addConditionWaiter();
        final int fullyRelease = AbstractQueuedSynchronizer.this.fullyRelease(addConditionWaiter);
        final long n2 = System.nanoTime() + nanos;
        boolean transferAfterCancelledWait = false;
        int checkInterruptWhileWaiting = 0;
        while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(addConditionWaiter)) {
            if (nanos <= 0L) {
                transferAfterCancelledWait = AbstractQueuedSynchronizer.this.transferAfterCancelledWait(addConditionWaiter);
                break;
            }
            if (nanos >= 1000L) {
                LockSupport.parkNanos(this, nanos);
            }
            if ((checkInterruptWhileWaiting = this.checkInterruptWhileWaiting(addConditionWaiter)) != 0) {
                break;
            }
            nanos = n2 - System.nanoTime();
        }
        if (AbstractQueuedSynchronizer.this.acquireQueued(addConditionWaiter, fullyRelease) && checkInterruptWhileWaiting != -1) {
            checkInterruptWhileWaiting = 1;
        }
        if (addConditionWaiter.nextWaiter != null) {
            this.unlinkCancelledWaiters();
        }
        if (checkInterruptWhileWaiting != 0) {
            this.reportInterruptAfterWait(checkInterruptWhileWaiting);
        }
        return !transferAfterCancelledWait;
    }
    
    final boolean isOwnedBy(final AbstractQueuedSynchronizer abstractQueuedSynchronizer) {
        return abstractQueuedSynchronizer == AbstractQueuedSynchronizer.this;
    }
    
    protected final boolean hasWaiters() {
        if (!AbstractQueuedSynchronizer.this.isHeldExclusively()) {
            throw new IllegalMonitorStateException();
        }
        for (Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
            if (node.waitStatus == -2) {
                return true;
            }
        }
        return false;
    }
    
    protected final int getWaitQueueLength() {
        if (!AbstractQueuedSynchronizer.this.isHeldExclusively()) {
            throw new IllegalMonitorStateException();
        }
        int n = 0;
        for (Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
            if (node.waitStatus == -2) {
                ++n;
            }
        }
        return n;
    }
    
    protected final Collection<Thread> getWaitingThreads() {
        if (!AbstractQueuedSynchronizer.this.isHeldExclusively()) {
            throw new IllegalMonitorStateException();
        }
        final ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node node = this.firstWaiter; node != null; node = node.nextWaiter) {
            if (node.waitStatus == -2) {
                final Thread thread = node.thread;
                if (thread != null) {
                    list.add(thread);
                }
            }
        }
        return list;
    }
}
