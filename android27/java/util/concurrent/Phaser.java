package java.util.concurrent;

import java.util.concurrent.atomic.*;
import sun.misc.*;
import java.util.concurrent.locks.*;

public class Phaser
{
    private volatile long state;
    private static final int MAX_PARTIES = 65535;
    private static final int MAX_PHASE = Integer.MAX_VALUE;
    private static final int PARTIES_SHIFT = 16;
    private static final int PHASE_SHIFT = 32;
    private static final int UNARRIVED_MASK = 65535;
    private static final long PARTIES_MASK = 4294901760L;
    private static final long COUNTS_MASK = 4294967295L;
    private static final long TERMINATION_BIT = Long.MIN_VALUE;
    private static final int ONE_ARRIVAL = 1;
    private static final int ONE_PARTY = 65536;
    private static final int ONE_DEREGISTER = 65537;
    private static final int EMPTY = 1;
    private final Phaser parent;
    private final Phaser root;
    private final AtomicReference<QNode> evenQ;
    private final AtomicReference<QNode> oddQ;
    private static final int NCPU;
    static final int SPINS_PER_ARRIVAL;
    private static final Unsafe UNSAFE;
    private static final long stateOffset;
    
    private static int unarrivedOf(final long n) {
        final int n2 = (int)n;
        return (n2 == 1) ? 0 : (n2 & 0xFFFF);
    }
    
    private static int partiesOf(final long n) {
        return (int)n >>> 16;
    }
    
    private static int phaseOf(final long n) {
        return (int)(n >>> 32);
    }
    
    private static int arrivedOf(final long n) {
        final int n2 = (int)n;
        return (n2 == 1) ? 0 : ((n2 >>> 16) - (n2 & 0xFFFF));
    }
    
    private AtomicReference<QNode> queueFor(final int n) {
        return ((n & 0x1) == 0x0) ? this.evenQ : this.oddQ;
    }
    
    private String badArrive(final long n) {
        return "Attempted arrival of unregistered party for " + this.stateToString(n);
    }
    
    private String badRegister(final long n) {
        return "Attempt to register more than 65535 parties for " + this.stateToString(n);
    }
    
    private int doArrive(final int n) {
        final Phaser root = this.root;
        while (true) {
            final long n2 = (root == this) ? this.state : this.reconcileState();
            int n3 = (int)(n2 >>> 32);
            if (n3 < 0) {
                return n3;
            }
            final int n4 = (int)n2;
            final int n5 = (n4 == 1) ? 0 : (n4 & 0xFFFF);
            if (n5 <= 0) {
                throw new IllegalStateException(this.badArrive(n2));
            }
            final long n6;
            if (Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, n2, n6 = n2 - n)) {
                if (n5 == 1) {
                    final long n7 = n6 & 0xFFFF0000L;
                    final int n8 = (int)n7 >>> 16;
                    if (root == this) {
                        long n9;
                        if (this.onAdvance(n3, n8)) {
                            n9 = (n7 | Long.MIN_VALUE);
                        }
                        else if (n8 == 0) {
                            n9 = (n7 | 0x1L);
                        }
                        else {
                            n9 = (n7 | n8);
                        }
                        Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, n6, n9 | (n3 + 1 & Integer.MAX_VALUE) << 32);
                        this.releaseWaiters(n3);
                    }
                    else if (n8 == 0) {
                        n3 = this.parent.doArrive(65537);
                        Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, n6, n6 | 0x1L);
                    }
                    else {
                        n3 = this.parent.doArrive(1);
                    }
                }
                return n3;
            }
        }
    }
    
    private int doRegister(final int n) {
        final long n2 = n << 16 | n;
        final Phaser parent = this.parent;
        int doRegister;
        while (true) {
            long state = (parent == null) ? this.state : this.reconcileState();
            final int n3 = (int)state;
            final int n4 = n3 >>> 16;
            final int n5 = n3 & 0xFFFF;
            if (n > 65535 - n4) {
                throw new IllegalStateException(this.badRegister(state));
            }
            doRegister = (int)(state >>> 32);
            if (doRegister < 0) {
                break;
            }
            if (n3 != 1) {
                if (parent != null && this.reconcileState() != state) {
                    continue;
                }
                if (n5 == 0) {
                    this.root.internalAwaitAdvance(doRegister, null);
                }
                else {
                    if (Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, state, state + n2)) {
                        break;
                    }
                    continue;
                }
            }
            else if (parent == null) {
                if (Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, state, doRegister << 32 | n2)) {
                    break;
                }
                continue;
            }
            else {
                synchronized (this) {
                    if (this.state != state) {
                        continue;
                    }
                    doRegister = parent.doRegister(1);
                    if (doRegister < 0) {
                        break;
                    }
                    while (!Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, state, doRegister << 32 | n2)) {
                        state = this.state;
                        doRegister = (int)(this.root.state >>> 32);
                    }
                    break;
                }
            }
        }
        return doRegister;
    }
    
    private long reconcileState() {
        final Phaser root = this.root;
        long n = this.state;
        if (root != this) {
            int n2;
            int n3;
            while ((n2 = (int)(root.state >>> 32)) != (int)(n >>> 32) && !Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, n, n = (n2 << 32 | ((n2 < 0) ? (n & 0xFFFFFFFFL) : (((n3 = (int)n >>> 16) == 0) ? 1L : ((n & 0xFFFF0000L) | n3)))))) {
                n = this.state;
            }
        }
        return n;
    }
    
    public Phaser() {
        this(null, 0);
    }
    
    public Phaser(final int n) {
        this(null, n);
    }
    
    public Phaser(final Phaser phaser) {
        this(phaser, 0);
    }
    
    public Phaser(final Phaser parent, final int n) {
        if (n >>> 16 != 0) {
            throw new IllegalArgumentException("Illegal number of parties");
        }
        int doRegister = 0;
        if ((this.parent = parent) != null) {
            final Phaser root = parent.root;
            this.root = root;
            this.evenQ = root.evenQ;
            this.oddQ = root.oddQ;
            if (n != 0) {
                doRegister = parent.doRegister(1);
            }
        }
        else {
            this.root = this;
            this.evenQ = new AtomicReference<QNode>();
            this.oddQ = new AtomicReference<QNode>();
        }
        this.state = ((n == 0) ? 1L : (doRegister << 32 | n << 16 | n));
    }
    
    public int register() {
        return this.doRegister(1);
    }
    
    public int bulkRegister(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n == 0) {
            return this.getPhase();
        }
        return this.doRegister(n);
    }
    
    public int arrive() {
        return this.doArrive(1);
    }
    
    public int arriveAndDeregister() {
        return this.doArrive(65537);
    }
    
    public int arriveAndAwaitAdvance() {
        final Phaser root = this.root;
        while (true) {
            final long n = (root == this) ? this.state : this.reconcileState();
            final int n2 = (int)(n >>> 32);
            if (n2 < 0) {
                return n2;
            }
            final int n3 = (int)n;
            final int n4 = (n3 == 1) ? 0 : (n3 & 0xFFFF);
            if (n4 <= 0) {
                throw new IllegalStateException(this.badArrive(n));
            }
            final long n5;
            if (!Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, n, n5 = n - 1L)) {
                continue;
            }
            if (n4 > 1) {
                return root.internalAwaitAdvance(n2, null);
            }
            if (root != this) {
                return this.parent.arriveAndAwaitAdvance();
            }
            final long n6 = n5 & 0xFFFF0000L;
            final int n7 = (int)n6 >>> 16;
            long n8;
            if (this.onAdvance(n2, n7)) {
                n8 = (n6 | Long.MIN_VALUE);
            }
            else if (n7 == 0) {
                n8 = (n6 | 0x1L);
            }
            else {
                n8 = (n6 | n7);
            }
            final int n9 = n2 + 1 & Integer.MAX_VALUE;
            if (!Phaser.UNSAFE.compareAndSwapLong(this, Phaser.stateOffset, n5, n8 | n9 << 32)) {
                return (int)(this.state >>> 32);
            }
            this.releaseWaiters(n2);
            return n9;
        }
    }
    
    public int awaitAdvance(final int n) {
        final Phaser root = this.root;
        final int n2 = (int)(((root == this) ? this.state : this.reconcileState()) >>> 32);
        if (n < 0) {
            return n;
        }
        if (n2 == n) {
            return root.internalAwaitAdvance(n, null);
        }
        return n2;
    }
    
    public int awaitAdvanceInterruptibly(final int n) throws InterruptedException {
        final Phaser root = this.root;
        int internalAwaitAdvance = (int)(((root == this) ? this.state : this.reconcileState()) >>> 32);
        if (n < 0) {
            return n;
        }
        if (internalAwaitAdvance == n) {
            final QNode qNode = new QNode(this, n, true, false, 0L);
            internalAwaitAdvance = root.internalAwaitAdvance(n, qNode);
            if (qNode.wasInterrupted) {
                throw new InterruptedException();
            }
        }
        return internalAwaitAdvance;
    }
    
    public int awaitAdvanceInterruptibly(final int n, final long n2, final TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        final long nanos = timeUnit.toNanos(n2);
        final Phaser root = this.root;
        int internalAwaitAdvance = (int)(((root == this) ? this.state : this.reconcileState()) >>> 32);
        if (n < 0) {
            return n;
        }
        if (internalAwaitAdvance == n) {
            final QNode qNode = new QNode(this, n, true, true, nanos);
            internalAwaitAdvance = root.internalAwaitAdvance(n, qNode);
            if (qNode.wasInterrupted) {
                throw new InterruptedException();
            }
            if (internalAwaitAdvance == n) {
                throw new TimeoutException();
            }
        }
        return internalAwaitAdvance;
    }
    
    public void forceTermination() {
        final Phaser root = this.root;
        long state;
        while ((state = root.state) >= 0L) {
            if (Phaser.UNSAFE.compareAndSwapLong(root, Phaser.stateOffset, state, state | Long.MIN_VALUE)) {
                this.releaseWaiters(0);
                this.releaseWaiters(1);
            }
        }
    }
    
    public final int getPhase() {
        return (int)(this.root.state >>> 32);
    }
    
    public int getRegisteredParties() {
        return partiesOf(this.state);
    }
    
    public int getArrivedParties() {
        return arrivedOf(this.reconcileState());
    }
    
    public int getUnarrivedParties() {
        return unarrivedOf(this.reconcileState());
    }
    
    public Phaser getParent() {
        return this.parent;
    }
    
    public Phaser getRoot() {
        return this.root;
    }
    
    public boolean isTerminated() {
        return this.root.state < 0L;
    }
    
    protected boolean onAdvance(final int n, final int n2) {
        return n2 == 0;
    }
    
    @Override
    public String toString() {
        return this.stateToString(this.reconcileState());
    }
    
    private String stateToString(final long n) {
        return super.toString() + "[phase = " + phaseOf(n) + " parties = " + partiesOf(n) + " arrived = " + arrivedOf(n) + "]";
    }
    
    private void releaseWaiters(final int n) {
        final AtomicReference<QNode> atomicReference = ((n & 0x1) == 0x0) ? this.evenQ : this.oddQ;
        QNode qNode;
        while ((qNode = atomicReference.get()) != null && qNode.phase != (int)(this.root.state >>> 32)) {
            final Thread thread;
            if (atomicReference.compareAndSet(qNode, qNode.next) && (thread = qNode.thread) != null) {
                qNode.thread = null;
                LockSupport.unpark(thread);
            }
        }
    }
    
    private int abortWait(final int n) {
        final AtomicReference<QNode> atomicReference = ((n & 0x1) == 0x0) ? this.evenQ : this.oddQ;
        int n2;
        while (true) {
            final QNode qNode = atomicReference.get();
            n2 = (int)(this.root.state >>> 32);
            final Thread thread;
            if (qNode == null || ((thread = qNode.thread) != null && qNode.phase == n2)) {
                break;
            }
            if (!atomicReference.compareAndSet(qNode, qNode.next) || thread == null) {
                continue;
            }
            qNode.thread = null;
            LockSupport.unpark(thread);
        }
        return n2;
    }
    
    private int internalAwaitAdvance(final int n, QNode qNode) {
        this.releaseWaiters(n - 1);
        boolean compareAndSet = false;
        int n2 = 0;
        int spins_PER_ARRIVAL = Phaser.SPINS_PER_ARRIVAL;
        long state;
        int n3;
        while ((n3 = (int)((state = this.state) >>> 32)) == n) {
            if (qNode == null) {
                final int n4 = (int)state & 0xFFFF;
                if (n4 != n2 && (n2 = n4) < Phaser.NCPU) {
                    spins_PER_ARRIVAL += Phaser.SPINS_PER_ARRIVAL;
                }
                final boolean interrupted = Thread.interrupted();
                if (!interrupted && --spins_PER_ARRIVAL >= 0) {
                    continue;
                }
                qNode = new QNode(this, n, false, false, 0L);
                qNode.wasInterrupted = interrupted;
            }
            else {
                if (qNode.isReleasable()) {
                    break;
                }
                if (!compareAndSet) {
                    final AtomicReference<QNode> atomicReference = ((n & 0x1) == 0x0) ? this.evenQ : this.oddQ;
                    final QNode qNode2 = qNode;
                    final QNode next = atomicReference.get();
                    qNode2.next = next;
                    final QNode qNode3 = next;
                    if ((qNode3 != null && qNode3.phase != n) || (int)(this.state >>> 32) != n) {
                        continue;
                    }
                    compareAndSet = atomicReference.compareAndSet(qNode3, qNode);
                }
                else {
                    try {
                        ForkJoinPool.managedBlock(qNode);
                    }
                    catch (InterruptedException ex) {
                        qNode.wasInterrupted = true;
                    }
                }
            }
        }
        if (qNode != null) {
            if (qNode.thread != null) {
                qNode.thread = null;
            }
            if (qNode.wasInterrupted && !qNode.interruptible) {
                Thread.currentThread().interrupt();
            }
            if (n3 == n && (n3 = (int)(this.state >>> 32)) == n) {
                return this.abortWait(n);
            }
        }
        this.releaseWaiters(n);
        return n3;
    }
    
    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        SPINS_PER_ARRIVAL = ((Phaser.NCPU < 2) ? 1 : 256);
        try {
            UNSAFE = Unsafe.getUnsafe();
            stateOffset = Phaser.UNSAFE.objectFieldOffset(Phaser.class.getDeclaredField("state"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class QNode implements ForkJoinPool.ManagedBlocker
    {
        final Phaser phaser;
        final int phase;
        final boolean interruptible;
        final boolean timed;
        boolean wasInterrupted;
        long nanos;
        final long deadline;
        volatile Thread thread;
        QNode next;
        
        QNode(final Phaser phaser, final int phase, final boolean interruptible, final boolean timed, final long nanos) {
            this.phaser = phaser;
            this.phase = phase;
            this.interruptible = interruptible;
            this.nanos = nanos;
            this.timed = timed;
            this.deadline = (timed ? (System.nanoTime() + nanos) : 0L);
            this.thread = Thread.currentThread();
        }
        
        @Override
        public boolean isReleasable() {
            if (this.thread == null) {
                return true;
            }
            if (this.phaser.getPhase() != this.phase) {
                this.thread = null;
                return true;
            }
            if (Thread.interrupted()) {
                this.wasInterrupted = true;
            }
            if (this.wasInterrupted && this.interruptible) {
                this.thread = null;
                return true;
            }
            if (this.timed) {
                if (this.nanos > 0L) {
                    this.nanos = this.deadline - System.nanoTime();
                }
                if (this.nanos <= 0L) {
                    this.thread = null;
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean block() {
            if (this.isReleasable()) {
                return true;
            }
            if (!this.timed) {
                LockSupport.park(this);
            }
            else if (this.nanos > 0L) {
                LockSupport.parkNanos(this, this.nanos);
            }
            return this.isReleasable();
        }
    }
}
