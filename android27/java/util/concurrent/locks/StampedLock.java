package java.util.concurrent.locks;

import sun.misc.*;
import java.util.concurrent.*;
import java.io.*;

public class StampedLock implements Serializable
{
    private static final long serialVersionUID = -6001602636862214147L;
    private static final int NCPU;
    private static final int SPINS;
    private static final int HEAD_SPINS;
    private static final int MAX_HEAD_SPINS;
    private static final int OVERFLOW_YIELD_RATE = 7;
    private static final int LG_READERS = 7;
    private static final long RUNIT = 1L;
    private static final long WBIT = 128L;
    private static final long RBITS = 127L;
    private static final long RFULL = 126L;
    private static final long ABITS = 255L;
    private static final long SBITS = -128L;
    private static final long ORIGIN = 256L;
    private static final long INTERRUPTED = 1L;
    private static final int WAITING = -1;
    private static final int CANCELLED = 1;
    private static final int RMODE = 0;
    private static final int WMODE = 1;
    private transient volatile WNode whead;
    private transient volatile WNode wtail;
    transient ReadLockView readLockView;
    transient WriteLockView writeLockView;
    transient ReadWriteLockView readWriteLockView;
    private transient volatile long state;
    private transient int readerOverflow;
    private static final Unsafe U;
    private static final long STATE;
    private static final long WHEAD;
    private static final long WTAIL;
    private static final long WNEXT;
    private static final long WSTATUS;
    private static final long WCOWAIT;
    private static final long PARKBLOCKER;
    
    public StampedLock() {
        this.state = 256L;
    }
    
    public long writeLock() {
        final long state;
        final long n;
        return (((state = this.state) & 0xFFL) == 0x0L && StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n = state + 128L)) ? n : this.acquireWrite(false, 0L);
    }
    
    public long tryWriteLock() {
        final long state;
        final long n;
        return (((state = this.state) & 0xFFL) == 0x0L && StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n = state + 128L)) ? n : 0L;
    }
    
    public long tryWriteLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
        final long nanos = timeUnit.toNanos(n);
        if (!Thread.interrupted()) {
            final long tryWriteLock;
            if ((tryWriteLock = this.tryWriteLock()) != 0L) {
                return tryWriteLock;
            }
            if (nanos <= 0L) {
                return 0L;
            }
            long n2;
            if ((n2 = System.nanoTime() + nanos) == 0L) {
                n2 = 1L;
            }
            final long acquireWrite;
            if ((acquireWrite = this.acquireWrite(true, n2)) != 1L) {
                return acquireWrite;
            }
        }
        throw new InterruptedException();
    }
    
    public long writeLockInterruptibly() throws InterruptedException {
        final long acquireWrite;
        if (!Thread.interrupted() && (acquireWrite = this.acquireWrite(true, 0L)) != 1L) {
            return acquireWrite;
        }
        throw new InterruptedException();
    }
    
    public long readLock() {
        final long state = this.state;
        final long n;
        return (this.whead == this.wtail && (state & 0xFFL) < 126L && StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n = state + 1L)) ? n : this.acquireRead(false, 0L);
    }
    
    public long tryReadLock() {
        long state;
        long n;
        while ((n = ((state = this.state) & 0xFFL)) != 128L) {
            if (n < 126L) {
                final long n2;
                if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n2 = state + 1L)) {
                    return n2;
                }
                continue;
            }
            else {
                final long tryIncReaderOverflow;
                if ((tryIncReaderOverflow = this.tryIncReaderOverflow(state)) != 0L) {
                    return tryIncReaderOverflow;
                }
                continue;
            }
        }
        return 0L;
    }
    
    public long tryReadLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
        final long nanos = timeUnit.toNanos(n);
        if (!Thread.interrupted()) {
            final long state;
            final long n2;
            if ((n2 = ((state = this.state) & 0xFFL)) != 128L) {
                if (n2 < 126L) {
                    final long n3;
                    if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n3 = state + 1L)) {
                        return n3;
                    }
                }
                else {
                    final long tryIncReaderOverflow;
                    if ((tryIncReaderOverflow = this.tryIncReaderOverflow(state)) != 0L) {
                        return tryIncReaderOverflow;
                    }
                }
            }
            if (nanos <= 0L) {
                return 0L;
            }
            long n4;
            if ((n4 = System.nanoTime() + nanos) == 0L) {
                n4 = 1L;
            }
            final long acquireRead;
            if ((acquireRead = this.acquireRead(true, n4)) != 1L) {
                return acquireRead;
            }
        }
        throw new InterruptedException();
    }
    
    public long readLockInterruptibly() throws InterruptedException {
        final long acquireRead;
        if (!Thread.interrupted() && (acquireRead = this.acquireRead(true, 0L)) != 1L) {
            return acquireRead;
        }
        throw new InterruptedException();
    }
    
    public long tryOptimisticRead() {
        final long state;
        return (((state = this.state) & 0x80L) == 0x0L) ? (state & 0xFFFFFFFFFFFFFF80L) : 0L;
    }
    
    public boolean validate(final long n) {
        StampedLock.U.loadFence();
        return (n & 0xFFFFFFFFFFFFFF80L) == (this.state & 0xFFFFFFFFFFFFFF80L);
    }
    
    public void unlockWrite(long n) {
        if (this.state != n || (n & 0x80L) == 0x0L) {
            throw new IllegalMonitorStateException();
        }
        this.state = (((n += 128L) == 0L) ? 256L : n);
        final WNode whead;
        if ((whead = this.whead) != null && whead.status != 0) {
            this.release(whead);
        }
    }
    
    public void unlockRead(final long n) {
        long state;
        long n2;
        while (((state = this.state) & 0xFFFFFFFFFFFFFF80L) == (n & 0xFFFFFFFFFFFFFF80L) && (n & 0xFFL) != 0x0L && (n2 = (state & 0xFFL)) != 0L && n2 != 128L) {
            if (n2 < 126L) {
                if (!StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, state - 1L)) {
                    continue;
                }
                final WNode whead;
                if (n2 == 1L && (whead = this.whead) != null && whead.status != 0) {
                    this.release(whead);
                }
            }
            else if (this.tryDecReaderOverflow(state) == 0L) {
                continue;
            }
            return;
        }
        throw new IllegalMonitorStateException();
    }
    
    public void unlock(final long n) {
        final long n2 = n & 0xFFL;
        long state;
        while (((state = this.state) & 0xFFFFFFFFFFFFFF80L) == (n & 0xFFFFFFFFFFFFFF80L)) {
            final long n3;
            if ((n3 = (state & 0xFFL)) == 0L) {
                break;
            }
            if (n3 == 128L) {
                if (n2 != n3) {
                    break;
                }
                final long n4;
                this.state = (((n4 = state + 128L) == 0L) ? 256L : n4);
                final WNode whead;
                if ((whead = this.whead) != null && whead.status != 0) {
                    this.release(whead);
                }
                return;
            }
            else {
                if (n2 == 0L) {
                    break;
                }
                if (n2 >= 128L) {
                    break;
                }
                if (n3 < 126L) {
                    if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, state - 1L)) {
                        final WNode whead2;
                        if (n3 == 1L && (whead2 = this.whead) != null && whead2.status != 0) {
                            this.release(whead2);
                        }
                        return;
                    }
                    continue;
                }
                else {
                    if (this.tryDecReaderOverflow(state) != 0L) {
                        return;
                    }
                    continue;
                }
            }
        }
        throw new IllegalMonitorStateException();
    }
    
    public long tryConvertToWriteLock(final long n) {
        final long n2 = n & 0xFFL;
        long state;
        while (((state = this.state) & 0xFFFFFFFFFFFFFF80L) == (n & 0xFFFFFFFFFFFFFF80L)) {
            final long n3;
            if ((n3 = (state & 0xFFL)) == 0L) {
                if (n2 != 0L) {
                    break;
                }
                final long n4;
                if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n4 = state + 128L)) {
                    return n4;
                }
                continue;
            }
            else if (n3 == 128L) {
                if (n2 != n3) {
                    break;
                }
                return n;
            }
            else {
                if (n3 != 1L || n2 == 0L) {
                    break;
                }
                final long n5;
                if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n5 = state - 1L + 128L)) {
                    return n5;
                }
                continue;
            }
        }
        return 0L;
    }
    
    public long tryConvertToReadLock(final long n) {
        final long n2 = n & 0xFFL;
        long state;
        while (((state = this.state) & 0xFFFFFFFFFFFFFF80L) == (n & 0xFFFFFFFFFFFFFF80L)) {
            final long n3;
            if ((n3 = (state & 0xFFL)) == 0L) {
                if (n2 != 0L) {
                    break;
                }
                if (n3 < 126L) {
                    final long n4;
                    if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n4 = state + 1L)) {
                        return n4;
                    }
                    continue;
                }
                else {
                    final long tryIncReaderOverflow;
                    if ((tryIncReaderOverflow = this.tryIncReaderOverflow(state)) != 0L) {
                        return tryIncReaderOverflow;
                    }
                    continue;
                }
            }
            else if (n3 == 128L) {
                if (n2 != n3) {
                    break;
                }
                final long n5 = this.state = state + 129L;
                final WNode whead;
                if ((whead = this.whead) != null && whead.status != 0) {
                    this.release(whead);
                }
                return n5;
            }
            else {
                if (n2 != 0L && n2 < 128L) {
                    return n;
                }
                break;
            }
        }
        return 0L;
    }
    
    public long tryConvertToOptimisticRead(final long n) {
        final long n2 = n & 0xFFL;
        StampedLock.U.loadFence();
        long state;
        while (((state = this.state) & 0xFFFFFFFFFFFFFF80L) == (n & 0xFFFFFFFFFFFFFF80L)) {
            final long n3;
            if ((n3 = (state & 0xFFL)) == 0L) {
                if (n2 == 0L) {
                    return state;
                }
            }
            else if (n3 == 128L) {
                if (n2 == n3) {
                    final long n5;
                    final long n4 = this.state = (((n5 = state + 128L) == 0L) ? 256L : n5);
                    final WNode whead;
                    if ((whead = this.whead) != null && whead.status != 0) {
                        this.release(whead);
                    }
                    return n4;
                }
            }
            else if (n2 != 0L) {
                if (n2 < 128L) {
                    if (n3 < 126L) {
                        final long n6;
                        if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n6 = state - 1L)) {
                            final WNode whead2;
                            if (n3 == 1L && (whead2 = this.whead) != null && whead2.status != 0) {
                                this.release(whead2);
                            }
                            return n6 & 0xFFFFFFFFFFFFFF80L;
                        }
                        continue;
                    }
                    else {
                        final long tryDecReaderOverflow;
                        if ((tryDecReaderOverflow = this.tryDecReaderOverflow(state)) != 0L) {
                            return tryDecReaderOverflow & 0xFFFFFFFFFFFFFF80L;
                        }
                        continue;
                    }
                }
            }
            return 0L;
        }
        return 0L;
    }
    
    public boolean tryUnlockWrite() {
        final long state;
        if (((state = this.state) & 0x80L) != 0x0L) {
            final long n;
            this.state = (((n = state + 128L) == 0L) ? 256L : n);
            final WNode whead;
            if ((whead = this.whead) != null && whead.status != 0) {
                this.release(whead);
            }
            return true;
        }
        return false;
    }
    
    public boolean tryUnlockRead() {
        long state;
        long n;
        while ((n = ((state = this.state) & 0xFFL)) != 0L && n < 128L) {
            if (n < 126L) {
                if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, state - 1L)) {
                    final WNode whead;
                    if (n == 1L && (whead = this.whead) != null && whead.status != 0) {
                        this.release(whead);
                    }
                    return true;
                }
                continue;
            }
            else {
                if (this.tryDecReaderOverflow(state) != 0L) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private int getReadLockCount(final long n) {
        long n2;
        if ((n2 = (n & 0x7FL)) >= 126L) {
            n2 = 126L + this.readerOverflow;
        }
        return (int)n2;
    }
    
    public boolean isWriteLocked() {
        return (this.state & 0x80L) != 0x0L;
    }
    
    public boolean isReadLocked() {
        return (this.state & 0x7FL) != 0x0L;
    }
    
    public int getReadLockCount() {
        return this.getReadLockCount(this.state);
    }
    
    @Override
    public String toString() {
        final long state = this.state;
        return super.toString() + (((state & 0xFFL) == 0x0L) ? "[Unlocked]" : (((state & 0x80L) != 0x0L) ? "[Write-locked]" : ("[Read-locks:" + this.getReadLockCount(state) + "]")));
    }
    
    public Lock asReadLock() {
        final ReadLockView readLockView;
        return ((readLockView = this.readLockView) != null) ? readLockView : (this.readLockView = new ReadLockView());
    }
    
    public Lock asWriteLock() {
        final WriteLockView writeLockView;
        return ((writeLockView = this.writeLockView) != null) ? writeLockView : (this.writeLockView = new WriteLockView());
    }
    
    public ReadWriteLock asReadWriteLock() {
        final ReadWriteLockView readWriteLockView;
        return ((readWriteLockView = this.readWriteLockView) != null) ? readWriteLockView : (this.readWriteLockView = new ReadWriteLockView());
    }
    
    final void unstampedUnlockWrite() {
        final long state;
        if (((state = this.state) & 0x80L) == 0x0L) {
            throw new IllegalMonitorStateException();
        }
        final long n;
        this.state = (((n = state + 128L) == 0L) ? 256L : n);
        final WNode whead;
        if ((whead = this.whead) != null && whead.status != 0) {
            this.release(whead);
        }
    }
    
    final void unstampedUnlockRead() {
        long state;
        long n;
        while ((n = ((state = this.state) & 0xFFL)) != 0L && n < 128L) {
            if (n < 126L) {
                if (!StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, state - 1L)) {
                    continue;
                }
                final WNode whead;
                if (n == 1L && (whead = this.whead) != null && whead.status != 0) {
                    this.release(whead);
                }
                return;
            }
            else {
                if (this.tryDecReaderOverflow(state) != 0L) {
                    return;
                }
                continue;
            }
        }
        throw new IllegalMonitorStateException();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.state = 256L;
    }
    
    private long tryIncReaderOverflow(final long state) {
        if ((state & 0xFFL) == 0x7EL) {
            if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, state | 0x7FL)) {
                ++this.readerOverflow;
                return this.state = state;
            }
        }
        else if ((LockSupport.nextSecondarySeed() & 0x7) == 0x0) {
            Thread.yield();
        }
        return 0L;
    }
    
    private long tryDecReaderOverflow(final long n) {
        if ((n & 0xFFL) == 0x7EL) {
            if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, n, n | 0x7FL)) {
                final int readerOverflow;
                long state;
                if ((readerOverflow = this.readerOverflow) > 0) {
                    this.readerOverflow = readerOverflow - 1;
                    state = n;
                }
                else {
                    state = n - 1L;
                }
                return this.state = state;
            }
        }
        else if ((LockSupport.nextSecondarySeed() & 0x7) == 0x0) {
            Thread.yield();
        }
        return 0L;
    }
    
    private void release(final WNode wNode) {
        if (wNode != null) {
            StampedLock.U.compareAndSwapInt(wNode, StampedLock.WSTATUS, -1, 0);
            WNode next;
            if ((next = wNode.next) == null || next.status == 1) {
                for (WNode wNode2 = this.wtail; wNode2 != null && wNode2 != wNode; wNode2 = wNode2.prev) {
                    if (wNode2.status <= 0) {
                        next = wNode2;
                    }
                }
            }
            final Thread thread;
            if (next != null && (thread = next.thread) != null) {
                StampedLock.U.unpark(thread);
            }
        }
    }
    
    private long acquireWrite(final boolean b, final long n) {
        WNode wNode = null;
        int n2 = -1;
        while (true) {
            final long state;
            final long n3;
            if ((n3 = ((state = this.state) & 0xFFL)) == 0L) {
                final long n4;
                if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, n4 = state + 128L)) {
                    return n4;
                }
                continue;
            }
            else if (n2 < 0) {
                n2 = ((n3 == 128L && this.wtail == this.whead) ? StampedLock.SPINS : 0);
            }
            else if (n2 > 0) {
                if (LockSupport.nextSecondarySeed() < 0) {
                    continue;
                }
                --n2;
            }
            else {
                WNode wtail;
                if ((wtail = this.wtail) == null) {
                    final WNode wtail2 = new WNode(1, null);
                    if (!StampedLock.U.compareAndSwapObject(this, StampedLock.WHEAD, null, wtail2)) {
                        continue;
                    }
                    this.wtail = wtail2;
                }
                else if (wNode == null) {
                    wNode = new WNode(1, wtail);
                }
                else if (wNode.prev != wtail) {
                    wNode.prev = wtail;
                }
                else {
                    if (!StampedLock.U.compareAndSwapObject(this, StampedLock.WTAIL, wtail, wNode)) {
                        continue;
                    }
                    wtail.next = wNode;
                    int head_SPINS = -1;
                    while (true) {
                        final WNode whead;
                        if ((whead = this.whead) == wtail) {
                            if (head_SPINS < 0) {
                                head_SPINS = StampedLock.HEAD_SPINS;
                            }
                            else if (head_SPINS < StampedLock.MAX_HEAD_SPINS) {
                                head_SPINS <<= 1;
                            }
                            int n5 = head_SPINS;
                            while (true) {
                                final long state2;
                                if (((state2 = this.state) & 0xFFL) == 0x0L) {
                                    final long n6;
                                    if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state2, n6 = state2 + 128L)) {
                                        this.whead = wNode;
                                        wNode.prev = null;
                                        return n6;
                                    }
                                    continue;
                                }
                                else {
                                    if (LockSupport.nextSecondarySeed() >= 0 && --n5 <= 0) {
                                        break;
                                    }
                                    continue;
                                }
                            }
                        }
                        else if (whead != null) {
                            WNode cowait;
                            while ((cowait = whead.cowait) != null) {
                                final Thread thread;
                                if (StampedLock.U.compareAndSwapObject(whead, StampedLock.WCOWAIT, cowait, cowait.cowait) && (thread = cowait.thread) != null) {
                                    StampedLock.U.unpark(thread);
                                }
                            }
                        }
                        if (this.whead == whead) {
                            final WNode prev;
                            if ((prev = wNode.prev) != wtail) {
                                if (prev == null) {
                                    continue;
                                }
                                (wtail = prev).next = wNode;
                            }
                            else {
                                final int status;
                                if ((status = wtail.status) == 0) {
                                    StampedLock.U.compareAndSwapInt(wtail, StampedLock.WSTATUS, 0, -1);
                                }
                                else if (status == 1) {
                                    final WNode prev2;
                                    if ((prev2 = wtail.prev) == null) {
                                        continue;
                                    }
                                    wNode.prev = prev2;
                                    prev2.next = wNode;
                                }
                                else {
                                    long n7;
                                    if (n == 0L) {
                                        n7 = 0L;
                                    }
                                    else if ((n7 = n - System.nanoTime()) <= 0L) {
                                        return this.cancelWaiter(wNode, wNode, false);
                                    }
                                    final Thread currentThread = Thread.currentThread();
                                    StampedLock.U.putObject(currentThread, StampedLock.PARKBLOCKER, this);
                                    wNode.thread = currentThread;
                                    if (wtail.status < 0 && (wtail != whead || (this.state & 0xFFL) != 0x0L) && this.whead == whead && wNode.prev == wtail) {
                                        StampedLock.U.park(false, n7);
                                    }
                                    wNode.thread = null;
                                    StampedLock.U.putObject(currentThread, StampedLock.PARKBLOCKER, null);
                                    if (b && Thread.interrupted()) {
                                        return this.cancelWaiter(wNode, wNode, true);
                                    }
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private long acquireRead(final boolean b, final long n) {
        WNode wNode = null;
        int spins = -1;
        long tryIncReaderOverflow = 0L;
    Label_0092:
        while (true) {
            WNode whead;
            WNode wtail;
            if ((whead = this.whead) == (wtail = this.wtail)) {
                while (true) {
                    final long state;
                    final long n2;
                    if ((n2 = ((state = this.state) & 0xFFL)) < 126L) {
                        if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state, tryIncReaderOverflow = state + 1L)) {
                            break Label_0092;
                        }
                    }
                    else if (n2 < 128L && (tryIncReaderOverflow = this.tryIncReaderOverflow(state)) != 0L) {
                        break Label_0092;
                    }
                    if (n2 >= 128L) {
                        if (spins > 0) {
                            if (LockSupport.nextSecondarySeed() < 0) {
                                continue;
                            }
                            --spins;
                        }
                        else {
                            if (spins == 0) {
                                final WNode whead2 = this.whead;
                                final WNode wtail2 = this.wtail;
                                if (whead2 == whead && wtail2 == wtail) {
                                    break;
                                }
                                if ((whead = whead2) != (wtail = wtail2)) {
                                    break;
                                }
                            }
                            spins = StampedLock.SPINS;
                        }
                    }
                }
            }
            if (wtail == null) {
                final WNode wtail3 = new WNode(1, null);
                if (!StampedLock.U.compareAndSwapObject(this, StampedLock.WHEAD, null, wtail3)) {
                    continue;
                }
                this.wtail = wtail3;
            }
            else if (wNode == null) {
                wNode = new WNode(0, wtail);
            }
            else if (whead == wtail || wtail.mode != 0) {
                if (wNode.prev != wtail) {
                    wNode.prev = wtail;
                }
                else {
                    if (StampedLock.U.compareAndSwapObject(this, StampedLock.WTAIL, wtail, wNode)) {
                        wtail.next = wNode;
                        int head_SPINS = -1;
                        long tryIncReaderOverflow2 = 0L;
                    Label_0815:
                        while (true) {
                            final WNode whead3;
                            if ((whead3 = this.whead) == wtail) {
                                if (head_SPINS < 0) {
                                    head_SPINS = StampedLock.HEAD_SPINS;
                                }
                                else if (head_SPINS < StampedLock.MAX_HEAD_SPINS) {
                                    head_SPINS <<= 1;
                                }
                                int n3 = head_SPINS;
                                long n4;
                                do {
                                    final long state2;
                                    if ((n4 = ((state2 = this.state) & 0xFFL)) < 126L) {
                                        if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state2, tryIncReaderOverflow2 = state2 + 1L)) {
                                            break Label_0815;
                                        }
                                    }
                                    else if (n4 < 128L && (tryIncReaderOverflow2 = this.tryIncReaderOverflow(state2)) != 0L) {
                                        break Label_0815;
                                    }
                                } while (n4 < 128L || LockSupport.nextSecondarySeed() < 0 || --n3 > 0);
                            }
                            else if (whead3 != null) {
                                WNode cowait;
                                while ((cowait = whead3.cowait) != null) {
                                    final Thread thread;
                                    if (StampedLock.U.compareAndSwapObject(whead3, StampedLock.WCOWAIT, cowait, cowait.cowait) && (thread = cowait.thread) != null) {
                                        StampedLock.U.unpark(thread);
                                    }
                                }
                            }
                            if (this.whead == whead3) {
                                final WNode prev;
                                if ((prev = wNode.prev) != wtail) {
                                    if (prev == null) {
                                        continue;
                                    }
                                    (wtail = prev).next = wNode;
                                }
                                else {
                                    final int status;
                                    if ((status = wtail.status) == 0) {
                                        StampedLock.U.compareAndSwapInt(wtail, StampedLock.WSTATUS, 0, -1);
                                    }
                                    else if (status == 1) {
                                        final WNode prev2;
                                        if ((prev2 = wtail.prev) == null) {
                                            continue;
                                        }
                                        wNode.prev = prev2;
                                        prev2.next = wNode;
                                    }
                                    else {
                                        long n5;
                                        if (n == 0L) {
                                            n5 = 0L;
                                        }
                                        else if ((n5 = n - System.nanoTime()) <= 0L) {
                                            return this.cancelWaiter(wNode, wNode, false);
                                        }
                                        final Thread currentThread = Thread.currentThread();
                                        StampedLock.U.putObject(currentThread, StampedLock.PARKBLOCKER, this);
                                        wNode.thread = currentThread;
                                        if (wtail.status < 0 && (wtail != whead3 || (this.state & 0xFFL) == 0x80L) && this.whead == whead3 && wNode.prev == wtail) {
                                            StampedLock.U.park(false, n5);
                                        }
                                        wNode.thread = null;
                                        StampedLock.U.putObject(currentThread, StampedLock.PARKBLOCKER, null);
                                        if (b && Thread.interrupted()) {
                                            return this.cancelWaiter(wNode, wNode, true);
                                        }
                                        continue;
                                    }
                                }
                            }
                        }
                        this.whead = wNode;
                        wNode.prev = null;
                        WNode cowait2;
                        while ((cowait2 = wNode.cowait) != null) {
                            final Thread thread2;
                            if (StampedLock.U.compareAndSwapObject(wNode, StampedLock.WCOWAIT, cowait2, cowait2.cowait) && (thread2 = cowait2.thread) != null) {
                                StampedLock.U.unpark(thread2);
                            }
                        }
                        return tryIncReaderOverflow2;
                    }
                    continue;
                }
            }
            else {
                final Unsafe u = StampedLock.U;
                final WNode wNode2 = wtail;
                final long wcowait = StampedLock.WCOWAIT;
                final WNode wNode3 = wNode;
                final WNode cowait3 = wtail.cowait;
                wNode3.cowait = cowait3;
                if (u.compareAndSwapObject(wNode2, wcowait, cowait3, wNode)) {
                    while (true) {
                        final WNode whead4;
                        final WNode cowait4;
                        final Thread thread3;
                        if ((whead4 = this.whead) != null && (cowait4 = whead4.cowait) != null && StampedLock.U.compareAndSwapObject(whead4, StampedLock.WCOWAIT, cowait4, cowait4.cowait) && (thread3 = cowait4.thread) != null) {
                            StampedLock.U.unpark(thread3);
                        }
                        final WNode prev3;
                        if (whead4 == (prev3 = wtail.prev) || whead4 == wtail || prev3 == null) {
                            long n6;
                            do {
                                final long state3;
                                if ((n6 = ((state3 = this.state) & 0xFFL)) < 126L) {
                                    final long tryIncReaderOverflow3;
                                    if (StampedLock.U.compareAndSwapLong(this, StampedLock.STATE, state3, tryIncReaderOverflow3 = state3 + 1L)) {
                                        return tryIncReaderOverflow3;
                                    }
                                    continue;
                                }
                                else {
                                    final long tryIncReaderOverflow3;
                                    if (n6 < 128L && (tryIncReaderOverflow3 = this.tryIncReaderOverflow(state3)) != 0L) {
                                        return tryIncReaderOverflow3;
                                    }
                                    continue;
                                }
                            } while (n6 < 128L);
                        }
                        if (this.whead == whead4 && wtail.prev == prev3) {
                            if (prev3 == null || whead4 == wtail || wtail.status > 0) {
                                break;
                            }
                            long n7;
                            if (n == 0L) {
                                n7 = 0L;
                            }
                            else if ((n7 = n - System.nanoTime()) <= 0L) {
                                return this.cancelWaiter(wNode, wtail, false);
                            }
                            final Thread currentThread2 = Thread.currentThread();
                            StampedLock.U.putObject(currentThread2, StampedLock.PARKBLOCKER, this);
                            wNode.thread = currentThread2;
                            if ((whead4 != prev3 || (this.state & 0xFFL) == 0x80L) && this.whead == whead4 && wtail.prev == prev3) {
                                StampedLock.U.park(false, n7);
                            }
                            wNode.thread = null;
                            StampedLock.U.putObject(currentThread2, StampedLock.PARKBLOCKER, null);
                            if (b && Thread.interrupted()) {
                                return this.cancelWaiter(wNode, wtail, true);
                            }
                            continue;
                        }
                    }
                    wNode = null;
                    continue;
                }
                wNode.cowait = null;
            }
        }
        return tryIncReaderOverflow;
    }
    
    private long cancelWaiter(final WNode wNode, final WNode wNode2, final boolean b) {
        if (wNode != null && wNode2 != null) {
            wNode.status = 1;
            WNode wNode3 = wNode2;
            WNode cowait;
            while ((cowait = wNode3.cowait) != null) {
                if (cowait.status == 1) {
                    StampedLock.U.compareAndSwapObject(wNode3, StampedLock.WCOWAIT, cowait, cowait.cowait);
                    wNode3 = wNode2;
                }
                else {
                    wNode3 = cowait;
                }
            }
            if (wNode2 == wNode) {
                for (WNode wNode4 = wNode2.cowait; wNode4 != null; wNode4 = wNode4.cowait) {
                    final Thread thread;
                    if ((thread = wNode4.thread) != null) {
                        StampedLock.U.unpark(thread);
                    }
                }
                WNode prev2;
                for (WNode prev = wNode.prev; prev != null; prev = prev2) {
                    WNode next;
                    while ((next = wNode.next) == null || next.status == 1) {
                        WNode wNode5 = null;
                        for (WNode wNode6 = this.wtail; wNode6 != null && wNode6 != wNode; wNode6 = wNode6.prev) {
                            if (wNode6.status != 1) {
                                wNode5 = wNode6;
                            }
                        }
                        if (next == wNode5 || StampedLock.U.compareAndSwapObject(wNode, StampedLock.WNEXT, next, next = wNode5)) {
                            if (next == null && wNode == this.wtail) {
                                StampedLock.U.compareAndSwapObject(this, StampedLock.WTAIL, wNode, prev);
                                break;
                            }
                            break;
                        }
                    }
                    if (prev.next == wNode) {
                        StampedLock.U.compareAndSwapObject(prev, StampedLock.WNEXT, wNode, next);
                    }
                    final Thread thread2;
                    if (next != null && (thread2 = next.thread) != null) {
                        next.thread = null;
                        StampedLock.U.unpark(thread2);
                    }
                    if (prev.status != 1) {
                        break;
                    }
                    if ((prev2 = prev.prev) == null) {
                        break;
                    }
                    wNode.prev = prev2;
                    StampedLock.U.compareAndSwapObject(prev2, StampedLock.WNEXT, prev, next);
                }
            }
        }
        WNode whead;
        while ((whead = this.whead) != null) {
            WNode next2;
            if ((next2 = whead.next) == null || next2.status == 1) {
                for (WNode wNode7 = this.wtail; wNode7 != null && wNode7 != whead; wNode7 = wNode7.prev) {
                    if (wNode7.status <= 0) {
                        next2 = wNode7;
                    }
                }
            }
            if (whead == this.whead) {
                final long state;
                if (next2 != null && whead.status == 0 && ((state = this.state) & 0xFFL) != 0x80L && (state == 0L || next2.mode == 0)) {
                    this.release(whead);
                    break;
                }
                break;
            }
        }
        return (b || Thread.interrupted()) ? 1 : 0;
    }
    
    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        SPINS = ((StampedLock.NCPU > 1) ? 64 : 0);
        HEAD_SPINS = ((StampedLock.NCPU > 1) ? 1024 : 0);
        MAX_HEAD_SPINS = ((StampedLock.NCPU > 1) ? 65536 : 0);
        try {
            U = Unsafe.getUnsafe();
            final Class<StampedLock> clazz = StampedLock.class;
            final Class<WNode> clazz2 = WNode.class;
            STATE = StampedLock.U.objectFieldOffset(clazz.getDeclaredField("state"));
            WHEAD = StampedLock.U.objectFieldOffset(clazz.getDeclaredField("whead"));
            WTAIL = StampedLock.U.objectFieldOffset(clazz.getDeclaredField("wtail"));
            WSTATUS = StampedLock.U.objectFieldOffset(clazz2.getDeclaredField("status"));
            WNEXT = StampedLock.U.objectFieldOffset(clazz2.getDeclaredField("next"));
            WCOWAIT = StampedLock.U.objectFieldOffset(clazz2.getDeclaredField("cowait"));
            PARKBLOCKER = StampedLock.U.objectFieldOffset(Thread.class.getDeclaredField("parkBlocker"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    final class ReadLockView implements Lock
    {
        @Override
        public void lock() {
            StampedLock.this.readLock();
        }
        
        @Override
        public void lockInterruptibly() throws InterruptedException {
            StampedLock.this.readLockInterruptibly();
        }
        
        @Override
        public boolean tryLock() {
            return StampedLock.this.tryReadLock() != 0L;
        }
        
        @Override
        public boolean tryLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
            return StampedLock.this.tryReadLock(n, timeUnit) != 0L;
        }
        
        @Override
        public void unlock() {
            StampedLock.this.unstampedUnlockRead();
        }
        
        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }
    }
    
    final class ReadWriteLockView implements ReadWriteLock
    {
        @Override
        public Lock readLock() {
            return StampedLock.this.asReadLock();
        }
        
        @Override
        public Lock writeLock() {
            return StampedLock.this.asWriteLock();
        }
    }
    
    static final class WNode
    {
        volatile WNode prev;
        volatile WNode next;
        volatile WNode cowait;
        volatile Thread thread;
        volatile int status;
        final int mode;
        
        WNode(final int mode, final WNode prev) {
            this.mode = mode;
            this.prev = prev;
        }
    }
    
    final class WriteLockView implements Lock
    {
        @Override
        public void lock() {
            StampedLock.this.writeLock();
        }
        
        @Override
        public void lockInterruptibly() throws InterruptedException {
            StampedLock.this.writeLockInterruptibly();
        }
        
        @Override
        public boolean tryLock() {
            return StampedLock.this.tryWriteLock() != 0L;
        }
        
        @Override
        public boolean tryLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
            return StampedLock.this.tryWriteLock(n, timeUnit) != 0L;
        }
        
        @Override
        public void unlock() {
            StampedLock.this.unstampedUnlockWrite();
        }
        
        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }
    }
}
