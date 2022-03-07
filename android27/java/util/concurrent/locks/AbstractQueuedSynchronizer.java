package java.util.concurrent.locks;

import java.io.*;
import sun.misc.*;
import java.util.*;
import java.util.concurrent.*;

public abstract class AbstractQueuedSynchronizer extends AbstractOwnableSynchronizer implements Serializable
{
    private static final long serialVersionUID = 7373984972572414691L;
    private transient volatile Node head;
    private transient volatile Node tail;
    private volatile int state;
    static final long spinForTimeoutThreshold = 1000L;
    private static final Unsafe unsafe;
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;
    
    protected final int getState() {
        return this.state;
    }
    
    protected final void setState(final int state) {
        this.state = state;
    }
    
    protected final boolean compareAndSetState(final int n, final int n2) {
        return AbstractQueuedSynchronizer.unsafe.compareAndSwapInt(this, AbstractQueuedSynchronizer.stateOffset, n, n2);
    }
    
    private Node enq(final Node next) {
        Node tail;
        while (true) {
            tail = this.tail;
            if (tail == null) {
                if (!this.compareAndSetHead(new Node())) {
                    continue;
                }
                this.tail = this.head;
            }
            else {
                next.prev = tail;
                if (this.compareAndSetTail(tail, next)) {
                    break;
                }
                continue;
            }
        }
        tail.next = next;
        return tail;
    }
    
    private Node addWaiter(final Node node) {
        final Node next = new Node(Thread.currentThread(), node);
        final Node tail = this.tail;
        if (tail != null) {
            next.prev = tail;
            if (this.compareAndSetTail(tail, next)) {
                return tail.next = next;
            }
        }
        this.enq(next);
        return next;
    }
    
    private void setHead(final Node head) {
        this.head = head;
        head.thread = null;
        head.prev = null;
    }
    
    private void unparkSuccessor(final Node node) {
        final int waitStatus = node.waitStatus;
        if (waitStatus < 0) {
            compareAndSetWaitStatus(node, waitStatus, 0);
        }
        Node next = node.next;
        if (next == null || next.waitStatus > 0) {
            next = null;
            for (Node node2 = this.tail; node2 != null && node2 != node; node2 = node2.prev) {
                if (node2.waitStatus <= 0) {
                    next = node2;
                }
            }
        }
        if (next != null) {
            LockSupport.unpark(next.thread);
        }
    }
    
    private void doReleaseShared() {
        while (true) {
            final Node head = this.head;
            if (head != null && head != this.tail) {
                final int waitStatus = head.waitStatus;
                if (waitStatus == -1) {
                    if (!compareAndSetWaitStatus(head, -1, 0)) {
                        continue;
                    }
                    this.unparkSuccessor(head);
                }
                else if (waitStatus == 0 && !compareAndSetWaitStatus(head, 0, -3)) {
                    continue;
                }
            }
            if (head == this.head) {
                break;
            }
        }
    }
    
    private void setHeadAndPropagate(final Node head, final int n) {
        final Node head2 = this.head;
        this.setHead(head);
        final Node head3;
        if (n > 0 || head2 == null || head2.waitStatus < 0 || (head3 = this.head) == null || head3.waitStatus < 0) {
            final Node next = head.next;
            if (next == null || next.isShared()) {
                this.doReleaseShared();
            }
        }
    }
    
    private void cancelAcquire(final Node next) {
        if (next == null) {
            return;
        }
        next.thread = null;
        Node prev;
        for (prev = next.prev; prev.waitStatus > 0; prev = (next.prev = prev.prev)) {}
        final Node next2 = prev.next;
        next.waitStatus = 1;
        if (next == this.tail && this.compareAndSetTail(next, prev)) {
            compareAndSetNext(prev, next2, null);
        }
        else {
            final int waitStatus;
            if (prev != this.head && ((waitStatus = prev.waitStatus) == -1 || (waitStatus <= 0 && compareAndSetWaitStatus(prev, waitStatus, -1))) && prev.thread != null) {
                final Node next3 = next.next;
                if (next3 != null && next3.waitStatus <= 0) {
                    compareAndSetNext(prev, next2, next3);
                }
            }
            else {
                this.unparkSuccessor(next);
            }
            next.next = next;
        }
    }
    
    private static boolean shouldParkAfterFailedAcquire(Node node, final Node next) {
        final int waitStatus = node.waitStatus;
        if (waitStatus == -1) {
            return true;
        }
        if (waitStatus > 0) {
            do {
                node = (next.prev = node.prev);
            } while (node.waitStatus > 0);
            node.next = next;
        }
        else {
            compareAndSetWaitStatus(node, waitStatus, -1);
        }
        return false;
    }
    
    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }
    
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }
    
    final boolean acquireQueued(final Node head, final int n) {
        boolean b = true;
        try {
            boolean b2 = false;
            Node predecessor;
            while (true) {
                predecessor = head.predecessor();
                if (predecessor == this.head && this.tryAcquire(n)) {
                    break;
                }
                if (!shouldParkAfterFailedAcquire(predecessor, head) || !this.parkAndCheckInterrupt()) {
                    continue;
                }
                b2 = true;
            }
            this.setHead(head);
            predecessor.next = null;
            b = false;
            return b2;
        }
        finally {
            if (b) {
                this.cancelAcquire(head);
            }
        }
    }
    
    private void doAcquireInterruptibly(final int n) throws InterruptedException {
        final Node addWaiter = this.addWaiter(Node.EXCLUSIVE);
        boolean b = true;
        try {
            while (true) {
                final Node predecessor = addWaiter.predecessor();
                if (predecessor == this.head && this.tryAcquire(n)) {
                    this.setHead(addWaiter);
                    predecessor.next = null;
                    b = false;
                    return;
                }
                if (shouldParkAfterFailedAcquire(predecessor, addWaiter) && this.parkAndCheckInterrupt()) {
                    throw new InterruptedException();
                }
            }
        }
        finally {
            if (b) {
                this.cancelAcquire(addWaiter);
            }
        }
    }
    
    private boolean doAcquireNanos(final int n, long n2) throws InterruptedException {
        if (n2 <= 0L) {
            return false;
        }
        final long n3 = System.nanoTime() + n2;
        final Node addWaiter = this.addWaiter(Node.EXCLUSIVE);
        boolean b = true;
        try {
            while (true) {
                final Node predecessor = addWaiter.predecessor();
                if (predecessor == this.head && this.tryAcquire(n)) {
                    this.setHead(addWaiter);
                    predecessor.next = null;
                    b = false;
                    return true;
                }
                n2 = n3 - System.nanoTime();
                if (n2 <= 0L) {
                    return false;
                }
                if (shouldParkAfterFailedAcquire(predecessor, addWaiter) && n2 > 1000L) {
                    LockSupport.parkNanos(this, n2);
                }
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }
        }
        finally {
            if (b) {
                this.cancelAcquire(addWaiter);
            }
        }
    }
    
    private void doAcquireShared(final int n) {
        final Node addWaiter = this.addWaiter(Node.SHARED);
        boolean b = true;
        try {
            int n2 = 0;
            Node predecessor;
            int tryAcquireShared;
            while (true) {
                predecessor = addWaiter.predecessor();
                if (predecessor == this.head) {
                    tryAcquireShared = this.tryAcquireShared(n);
                    if (tryAcquireShared >= 0) {
                        break;
                    }
                }
                if (shouldParkAfterFailedAcquire(predecessor, addWaiter) && this.parkAndCheckInterrupt()) {
                    n2 = 1;
                }
            }
            this.setHeadAndPropagate(addWaiter, tryAcquireShared);
            predecessor.next = null;
            if (n2 != 0) {
                selfInterrupt();
            }
            b = false;
        }
        finally {
            if (b) {
                this.cancelAcquire(addWaiter);
            }
        }
    }
    
    private void doAcquireSharedInterruptibly(final int n) throws InterruptedException {
        final Node addWaiter = this.addWaiter(Node.SHARED);
        boolean b = true;
        try {
            while (true) {
                final Node predecessor = addWaiter.predecessor();
                if (predecessor == this.head) {
                    final int tryAcquireShared = this.tryAcquireShared(n);
                    if (tryAcquireShared >= 0) {
                        this.setHeadAndPropagate(addWaiter, tryAcquireShared);
                        predecessor.next = null;
                        b = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(predecessor, addWaiter) && this.parkAndCheckInterrupt()) {
                    throw new InterruptedException();
                }
            }
        }
        finally {
            if (b) {
                this.cancelAcquire(addWaiter);
            }
        }
    }
    
    private boolean doAcquireSharedNanos(final int n, long n2) throws InterruptedException {
        if (n2 <= 0L) {
            return false;
        }
        final long n3 = System.nanoTime() + n2;
        final Node addWaiter = this.addWaiter(Node.SHARED);
        boolean b = true;
        try {
            while (true) {
                final Node predecessor = addWaiter.predecessor();
                if (predecessor == this.head) {
                    final int tryAcquireShared = this.tryAcquireShared(n);
                    if (tryAcquireShared >= 0) {
                        this.setHeadAndPropagate(addWaiter, tryAcquireShared);
                        predecessor.next = null;
                        b = false;
                        return true;
                    }
                }
                n2 = n3 - System.nanoTime();
                if (n2 <= 0L) {
                    return false;
                }
                if (shouldParkAfterFailedAcquire(predecessor, addWaiter) && n2 > 1000L) {
                    LockSupport.parkNanos(this, n2);
                }
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }
        }
        finally {
            if (b) {
                this.cancelAcquire(addWaiter);
            }
        }
    }
    
    protected boolean tryAcquire(final int n) {
        throw new UnsupportedOperationException();
    }
    
    protected boolean tryRelease(final int n) {
        throw new UnsupportedOperationException();
    }
    
    protected int tryAcquireShared(final int n) {
        throw new UnsupportedOperationException();
    }
    
    protected boolean tryReleaseShared(final int n) {
        throw new UnsupportedOperationException();
    }
    
    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }
    
    public final void acquire(final int n) {
        if (!this.tryAcquire(n) && this.acquireQueued(this.addWaiter(Node.EXCLUSIVE), n)) {
            selfInterrupt();
        }
    }
    
    public final void acquireInterruptibly(final int n) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (!this.tryAcquire(n)) {
            this.doAcquireInterruptibly(n);
        }
    }
    
    public final boolean tryAcquireNanos(final int n, final long n2) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this.tryAcquire(n) || this.doAcquireNanos(n, n2);
    }
    
    public final boolean release(final int n) {
        if (this.tryRelease(n)) {
            final Node head = this.head;
            if (head != null && head.waitStatus != 0) {
                this.unparkSuccessor(head);
            }
            return true;
        }
        return false;
    }
    
    public final void acquireShared(final int n) {
        if (this.tryAcquireShared(n) < 0) {
            this.doAcquireShared(n);
        }
    }
    
    public final void acquireSharedInterruptibly(final int n) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (this.tryAcquireShared(n) < 0) {
            this.doAcquireSharedInterruptibly(n);
        }
    }
    
    public final boolean tryAcquireSharedNanos(final int n, final long n2) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this.tryAcquireShared(n) >= 0 || this.doAcquireSharedNanos(n, n2);
    }
    
    public final boolean releaseShared(final int n) {
        if (this.tryReleaseShared(n)) {
            this.doReleaseShared();
            return true;
        }
        return false;
    }
    
    public final boolean hasQueuedThreads() {
        return this.head != this.tail;
    }
    
    public final boolean hasContended() {
        return this.head != null;
    }
    
    public final Thread getFirstQueuedThread() {
        return (this.head == this.tail) ? null : this.fullGetFirstQueuedThread();
    }
    
    private Thread fullGetFirstQueuedThread() {
        final Node head;
        final Node next;
        Thread thread;
        final Node head2;
        final Node next2;
        if (((head = this.head) != null && (next = head.next) != null && next.prev == this.head && (thread = next.thread) != null) || ((head2 = this.head) != null && (next2 = head2.next) != null && next2.prev == this.head && (thread = next2.thread) != null)) {
            return thread;
        }
        Node node = this.tail;
        Thread thread2 = null;
        while (node != null && node != this.head) {
            final Thread thread3 = node.thread;
            if (thread3 != null) {
                thread2 = thread3;
            }
            node = node.prev;
        }
        return thread2;
    }
    
    public final boolean isQueued(final Thread thread) {
        if (thread == null) {
            throw new NullPointerException();
        }
        for (Node node = this.tail; node != null; node = node.prev) {
            if (node.thread == thread) {
                return true;
            }
        }
        return false;
    }
    
    final boolean apparentlyFirstQueuedIsExclusive() {
        final Node head;
        final Node next;
        return (head = this.head) != null && (next = head.next) != null && !next.isShared() && next.thread != null;
    }
    
    public final boolean hasQueuedPredecessors() {
        final Node tail = this.tail;
        final Node head = this.head;
        final Node next;
        return head != tail && ((next = head.next) == null || next.thread != Thread.currentThread());
    }
    
    public final int getQueueLength() {
        int n = 0;
        for (Node node = this.tail; node != null; node = node.prev) {
            if (node.thread != null) {
                ++n;
            }
        }
        return n;
    }
    
    public final Collection<Thread> getQueuedThreads() {
        final ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node node = this.tail; node != null; node = node.prev) {
            final Thread thread = node.thread;
            if (thread != null) {
                list.add(thread);
            }
        }
        return list;
    }
    
    public final Collection<Thread> getExclusiveQueuedThreads() {
        final ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node node = this.tail; node != null; node = node.prev) {
            if (!node.isShared()) {
                final Thread thread = node.thread;
                if (thread != null) {
                    list.add(thread);
                }
            }
        }
        return list;
    }
    
    public final Collection<Thread> getSharedQueuedThreads() {
        final ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node node = this.tail; node != null; node = node.prev) {
            if (node.isShared()) {
                final Thread thread = node.thread;
                if (thread != null) {
                    list.add(thread);
                }
            }
        }
        return list;
    }
    
    @Override
    public String toString() {
        return super.toString() + "[State = " + this.getState() + ", " + (this.hasQueuedThreads() ? "non" : "") + "empty queue]";
    }
    
    final boolean isOnSyncQueue(final Node node) {
        return node.waitStatus != -2 && node.prev != null && (node.next != null || this.findNodeFromTail(node));
    }
    
    private boolean findNodeFromTail(final Node node) {
        for (Node node2 = this.tail; node2 != node; node2 = node2.prev) {
            if (node2 == null) {
                return false;
            }
        }
        return true;
    }
    
    final boolean transferForSignal(final Node node) {
        if (!compareAndSetWaitStatus(node, -2, 0)) {
            return false;
        }
        final Node enq = this.enq(node);
        final int waitStatus = enq.waitStatus;
        if (waitStatus > 0 || !compareAndSetWaitStatus(enq, waitStatus, -1)) {
            LockSupport.unpark(node.thread);
        }
        return true;
    }
    
    final boolean transferAfterCancelledWait(final Node node) {
        if (compareAndSetWaitStatus(node, -2, 0)) {
            this.enq(node);
            return true;
        }
        while (!this.isOnSyncQueue(node)) {
            Thread.yield();
        }
        return false;
    }
    
    final int fullyRelease(final Node node) {
        boolean b = true;
        try {
            final int state = this.getState();
            if (this.release(state)) {
                b = false;
                return state;
            }
            throw new IllegalMonitorStateException();
        }
        finally {
            if (b) {
                node.waitStatus = 1;
            }
        }
    }
    
    public final boolean owns(final ConditionObject conditionObject) {
        return conditionObject.isOwnedBy(this);
    }
    
    public final boolean hasWaiters(final ConditionObject conditionObject) {
        if (!this.owns(conditionObject)) {
            throw new IllegalArgumentException("Not owner");
        }
        return conditionObject.hasWaiters();
    }
    
    public final int getWaitQueueLength(final ConditionObject conditionObject) {
        if (!this.owns(conditionObject)) {
            throw new IllegalArgumentException("Not owner");
        }
        return conditionObject.getWaitQueueLength();
    }
    
    public final Collection<Thread> getWaitingThreads(final ConditionObject conditionObject) {
        if (!this.owns(conditionObject)) {
            throw new IllegalArgumentException("Not owner");
        }
        return conditionObject.getWaitingThreads();
    }
    
    private final boolean compareAndSetHead(final Node node) {
        return AbstractQueuedSynchronizer.unsafe.compareAndSwapObject(this, AbstractQueuedSynchronizer.headOffset, null, node);
    }
    
    private final boolean compareAndSetTail(final Node node, final Node node2) {
        return AbstractQueuedSynchronizer.unsafe.compareAndSwapObject(this, AbstractQueuedSynchronizer.tailOffset, node, node2);
    }
    
    private static final boolean compareAndSetWaitStatus(final Node node, final int n, final int n2) {
        return AbstractQueuedSynchronizer.unsafe.compareAndSwapInt(node, AbstractQueuedSynchronizer.waitStatusOffset, n, n2);
    }
    
    private static final boolean compareAndSetNext(final Node node, final Node node2, final Node node3) {
        return AbstractQueuedSynchronizer.unsafe.compareAndSwapObject(node, AbstractQueuedSynchronizer.nextOffset, node2, node3);
    }
    
    static {
        unsafe = Unsafe.getUnsafe();
        try {
            stateOffset = AbstractQueuedSynchronizer.unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("state"));
            headOffset = AbstractQueuedSynchronizer.unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = AbstractQueuedSynchronizer.unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = AbstractQueuedSynchronizer.unsafe.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
            nextOffset = AbstractQueuedSynchronizer.unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
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
    
    static final class Node
    {
        static final Node SHARED;
        static final Node EXCLUSIVE;
        static final int CANCELLED = 1;
        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;
        volatile int waitStatus;
        volatile Node prev;
        volatile Node next;
        volatile Thread thread;
        Node nextWaiter;
        
        final boolean isShared() {
            return this.nextWaiter == Node.SHARED;
        }
        
        final Node predecessor() throws NullPointerException {
            final Node prev = this.prev;
            if (prev == null) {
                throw new NullPointerException();
            }
            return prev;
        }
        
        Node() {
        }
        
        Node(final Thread thread, final Node nextWaiter) {
            this.nextWaiter = nextWaiter;
            this.thread = thread;
        }
        
        Node(final Thread thread, final int waitStatus) {
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
        
        static {
            SHARED = new Node();
            EXCLUSIVE = null;
        }
    }
}
