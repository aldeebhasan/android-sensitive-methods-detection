package java.util.concurrent;

import java.util.concurrent.atomic.*;
import sun.misc.*;
import java.util.*;
import java.security.*;

@Contended
public class ForkJoinPool extends AbstractExecutorService
{
    static final int SMASK = 65535;
    static final int MAX_CAP = 32767;
    static final int EVENMASK = 65534;
    static final int SQMASK = 126;
    static final int SCANNING = 1;
    static final int INACTIVE = Integer.MIN_VALUE;
    static final int SS_SEQ = 65536;
    static final int MODE_MASK = -65536;
    static final int LIFO_QUEUE = 0;
    static final int FIFO_QUEUE = 65536;
    static final int SHARED_QUEUE = Integer.MIN_VALUE;
    public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
    private static final RuntimePermission modifyThreadPermission;
    static final ForkJoinPool common;
    static final int commonParallelism;
    private static int commonMaxSpares;
    private static int poolNumberSequence;
    private static final long IDLE_TIMEOUT = 2000000000L;
    private static final long TIMEOUT_SLOP = 20000000L;
    private static final int DEFAULT_COMMON_MAX_SPARES = 256;
    private static final int SPINS = 0;
    private static final int SEED_INCREMENT = -1640531527;
    private static final long SP_MASK = 4294967295L;
    private static final long UC_MASK = -4294967296L;
    private static final int AC_SHIFT = 48;
    private static final long AC_UNIT = 281474976710656L;
    private static final long AC_MASK = -281474976710656L;
    private static final int TC_SHIFT = 32;
    private static final long TC_UNIT = 4294967296L;
    private static final long TC_MASK = 281470681743360L;
    private static final long ADD_WORKER = 140737488355328L;
    private static final int RSLOCK = 1;
    private static final int RSIGNAL = 2;
    private static final int STARTED = 4;
    private static final int STOP = 536870912;
    private static final int TERMINATED = 1073741824;
    private static final int SHUTDOWN = Integer.MIN_VALUE;
    volatile long ctl;
    volatile int runState;
    final int config;
    int indexSeed;
    volatile WorkQueue[] workQueues;
    final ForkJoinWorkerThreadFactory factory;
    final Thread.UncaughtExceptionHandler ueh;
    final String workerNamePrefix;
    volatile AtomicLong stealCounter;
    private static final Unsafe U;
    private static final int ABASE;
    private static final int ASHIFT;
    private static final long CTL;
    private static final long RUNSTATE;
    private static final long STEALCOUNTER;
    private static final long PARKBLOCKER;
    private static final long QTOP;
    private static final long QLOCK;
    private static final long QSCANSTATE;
    private static final long QPARKER;
    private static final long QCURRENTSTEAL;
    private static final long QCURRENTJOIN;
    
    private static void checkPermission() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(ForkJoinPool.modifyThreadPermission);
        }
    }
    
    private static final synchronized int nextPoolId() {
        return ++ForkJoinPool.poolNumberSequence;
    }
    
    private int lockRunState() {
        final int runState;
        final int n;
        return (((runState = this.runState) & 0x1) != 0x0 || !ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.RUNSTATE, runState, n = (runState | 0x1))) ? this.awaitRunStateLock() : n;
    }
    
    private int awaitRunStateLock() {
        int n = 0;
        int n2 = 0;
        int nextSecondarySeed = 0;
        int n3;
        while (true) {
            final int runState;
            if (((runState = this.runState) & 0x1) == 0x0) {
                if (ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.RUNSTATE, runState, n3 = (runState | 0x1))) {
                    break;
                }
                continue;
            }
            else if (nextSecondarySeed == 0) {
                nextSecondarySeed = ThreadLocalRandom.nextSecondarySeed();
            }
            else if (n2 > 0) {
                final int n4 = nextSecondarySeed ^ nextSecondarySeed << 6;
                final int n5 = n4 ^ n4 >>> 21;
                nextSecondarySeed = (n5 ^ n5 << 7);
                if (nextSecondarySeed < 0) {
                    continue;
                }
                --n2;
            }
            else {
                final AtomicLong stealCounter;
                if ((runState & 0x4) == 0x0 || (stealCounter = this.stealCounter) == null) {
                    Thread.yield();
                }
                else {
                    if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.RUNSTATE, runState, runState | 0x2)) {
                        continue;
                    }
                    synchronized (stealCounter) {
                        if ((this.runState & 0x2) != 0x0) {
                            try {
                                stealCounter.wait();
                            }
                            catch (InterruptedException ex) {
                                if (Thread.currentThread() instanceof ForkJoinWorkerThread) {
                                    continue;
                                }
                                n = 1;
                            }
                        }
                        else {
                            stealCounter.notifyAll();
                        }
                    }
                }
            }
        }
        if (n != 0) {
            try {
                Thread.currentThread().interrupt();
            }
            catch (SecurityException ex2) {}
        }
        return n3;
    }
    
    private void unlockRunState(final int n, final int runState) {
        if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.RUNSTATE, n, runState)) {
            final AtomicLong stealCounter = this.stealCounter;
            this.runState = runState;
            if (stealCounter != null) {
                synchronized (stealCounter) {
                    stealCounter.notifyAll();
                }
            }
        }
    }
    
    private boolean createWorker() {
        final ForkJoinWorkerThreadFactory factory = this.factory;
        Throwable t = null;
        ForkJoinWorkerThread thread = null;
        try {
            if (factory != null && (thread = factory.newThread(this)) != null) {
                thread.start();
                return true;
            }
        }
        catch (Throwable t2) {
            t = t2;
        }
        this.deregisterWorker(thread, t);
        return false;
    }
    
    private void tryAddWorker(long ctl) {
        boolean compareAndSwapLong = false;
        do {
            final long n = (0xFFFF000000000000L & ctl + 281474976710656L) | (0xFFFF00000000L & ctl + 4294967296L);
            if (this.ctl == ctl) {
                final int lockRunState;
                final int n2;
                if ((n2 = ((lockRunState = this.lockRunState()) & 0x20000000)) == 0) {
                    compareAndSwapLong = ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, n);
                }
                this.unlockRunState(lockRunState, lockRunState & 0xFFFFFFFE);
                if (n2 != 0) {
                    break;
                }
                if (compareAndSwapLong) {
                    this.createWorker();
                    break;
                }
                continue;
            }
        } while (((ctl = this.ctl) & 0x800000000000L) != 0x0L && (int)ctl == 0);
    }
    
    final WorkQueue registerWorker(final ForkJoinWorkerThread forkJoinWorkerThread) {
        forkJoinWorkerThread.setDaemon(true);
        final Thread.UncaughtExceptionHandler ueh;
        if ((ueh = this.ueh) != null) {
            forkJoinWorkerThread.setUncaughtExceptionHandler(ueh);
        }
        final WorkQueue workQueue = new WorkQueue(this, forkJoinWorkerThread);
        int scanState = 0;
        final int n = this.config & 0xFFFF0000;
        final int lockRunState = this.lockRunState();
        try {
            WorkQueue[] workQueues;
            int length;
            if ((workQueues = this.workQueues) != null && (length = workQueues.length) > 0) {
                final int indexSeed = this.indexSeed - 1640531527;
                this.indexSeed = indexSeed;
                final int hint = indexSeed;
                int n2 = length - 1;
                scanState = ((hint << 1 | 0x1) & n2);
                if (workQueues[scanState] != null) {
                    int n3 = 0;
                    while (workQueues[scanState = (scanState + ((length <= 4) ? 2 : ((length >>> 1 & 0xFFFE) + 2)) & n2)] != null) {
                        if (++n3 >= length) {
                            workQueues = (this.workQueues = Arrays.copyOf(workQueues, length <<= 1));
                            n2 = length - 1;
                            n3 = 0;
                        }
                    }
                }
                workQueue.hint = hint;
                workQueue.config = (scanState | n);
                workQueues[workQueue.scanState = scanState] = workQueue;
            }
        }
        finally {
            this.unlockRunState(lockRunState, lockRunState & 0xFFFFFFFE);
        }
        forkJoinWorkerThread.setName(this.workerNamePrefix.concat(Integer.toString(scanState >>> 1)));
        return workQueue;
    }
    
    final void deregisterWorker(final ForkJoinWorkerThread forkJoinWorkerThread, final Throwable t) {
        WorkQueue workQueue = null;
        if (forkJoinWorkerThread != null && (workQueue = forkJoinWorkerThread.workQueue) != null) {
            final int n = workQueue.config & 0xFFFF;
            final int lockRunState = this.lockRunState();
            final WorkQueue[] workQueues;
            if ((workQueues = this.workQueues) != null && workQueues.length > n && workQueues[n] == workQueue) {
                workQueues[n] = null;
            }
            this.unlockRunState(lockRunState, lockRunState & 0xFFFFFFFE);
        }
        long ctl;
        while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl = this.ctl, (0xFFFF000000000000L & ctl - 281474976710656L) | (0xFFFF00000000L & ctl - 4294967296L) | (0xFFFFFFFFL & ctl))) {}
        if (workQueue != null) {
            workQueue.qlock = -1;
            workQueue.transferStealCount(this);
            workQueue.cancelAll();
        }
        WorkQueue[] workQueues2;
        while (!this.tryTerminate(false, false) && workQueue != null && workQueue.array != null && (this.runState & 0x20000000) == 0x0 && (workQueues2 = this.workQueues) != null) {
            final int n2;
            if ((n2 = workQueues2.length - 1) < 0) {
                break;
            }
            final long ctl2;
            final int n3;
            if ((n3 = (int)(ctl2 = this.ctl)) != 0) {
                if (this.tryRelease(ctl2, workQueues2[n3 & n2], 281474976710656L)) {
                    break;
                }
                continue;
            }
            else {
                if (t != null && (ctl2 & 0x800000000000L) != 0x0L) {
                    this.tryAddWorker(ctl2);
                    break;
                }
                break;
            }
        }
        if (t == null) {
            ForkJoinTask.helpExpungeStaleExceptions();
        }
        else {
            ForkJoinTask.rethrow(t);
        }
    }
    
    final void signalWork(final WorkQueue[] array, final WorkQueue workQueue) {
        long ctl;
        while ((ctl = this.ctl) < 0L) {
            final int n;
            if ((n = (int)ctl) == 0) {
                if ((ctl & 0x800000000000L) != 0x0L) {
                    this.tryAddWorker(ctl);
                    break;
                }
                break;
            }
            else {
                if (array == null) {
                    break;
                }
                final int n2;
                if (array.length <= (n2 = (n & 0xFFFF))) {
                    break;
                }
                final WorkQueue workQueue2;
                if ((workQueue2 = array[n2]) == null) {
                    break;
                }
                final int scanState = n + 65536 & Integer.MAX_VALUE;
                final int n3 = n - workQueue2.scanState;
                final long n4 = (0xFFFFFFFF00000000L & ctl + 281474976710656L) | (0xFFFFFFFFL & workQueue2.stackPred);
                if (n3 == 0 && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, n4)) {
                    workQueue2.scanState = scanState;
                    final Thread parker;
                    if ((parker = workQueue2.parker) != null) {
                        ForkJoinPool.U.unpark(parker);
                        break;
                    }
                    break;
                }
                else {
                    if (workQueue != null && workQueue.base == workQueue.top) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    private boolean tryRelease(final long n, final WorkQueue workQueue, final long n2) {
        final int n3 = (int)n;
        final int scanState = n3 + 65536 & Integer.MAX_VALUE;
        if (workQueue != null && workQueue.scanState == n3 && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, n, (0xFFFFFFFF00000000L & n + n2) | (0xFFFFFFFFL & workQueue.stackPred))) {
            workQueue.scanState = scanState;
            final Thread parker;
            if ((parker = workQueue.parker) != null) {
                ForkJoinPool.U.unpark(parker);
            }
            return true;
        }
        return false;
    }
    
    final void runWorker(final WorkQueue workQueue) {
        workQueue.growArray();
        final int hint = workQueue.hint;
        int n = (hint == 0) ? 1 : hint;
        while (true) {
            final ForkJoinTask<?> scan;
            if ((scan = this.scan(workQueue, n)) != null) {
                workQueue.runTask(scan);
            }
            else if (!this.awaitWork(workQueue, n)) {
                break;
            }
            final int n2 = n ^ n << 13;
            final int n3 = n2 ^ n2 >>> 17;
            n = (n3 ^ n3 << 5);
        }
    }
    
    private ForkJoinTask<?> scan(final WorkQueue workQueue, int n) {
        final WorkQueue[] workQueues;
        final int n2;
        if ((workQueues = this.workQueues) != null && (n2 = workQueues.length - 1) > 0 && workQueue != null) {
            int scanState = workQueue.scanState;
            int n4;
            int n3 = n4 = (n & n2);
            int n5 = 0;
            int n6 = 0;
            while (true) {
                final WorkQueue workQueue2;
                if ((workQueue2 = workQueues[n4]) != null) {
                    final int base;
                    final int n7;
                    final ForkJoinTask<?>[] array;
                    if ((n7 = (base = workQueue2.base) - workQueue2.top) < 0 && (array = workQueue2.array) != null) {
                        final long n8 = ((array.length - 1 & base) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                        final ForkJoinTask forkJoinTask;
                        if ((forkJoinTask = (ForkJoinTask)ForkJoinPool.U.getObjectVolatile(array, n8)) != null && workQueue2.base == base) {
                            if (scanState >= 0) {
                                if (ForkJoinPool.U.compareAndSwapObject(array, n8, forkJoinTask, null)) {
                                    workQueue2.base = base + 1;
                                    if (n7 < -1) {
                                        this.signalWork(workQueues, workQueue2);
                                    }
                                    return (ForkJoinTask<?>)forkJoinTask;
                                }
                            }
                            else if (n5 == 0 && workQueue.scanState < 0) {
                                final long ctl = this.ctl;
                                this.tryRelease(ctl, workQueues[n2 & (int)ctl], 281474976710656L);
                            }
                        }
                        if (scanState < 0) {
                            scanState = workQueue.scanState;
                        }
                        n ^= n << 1;
                        n ^= n >>> 3;
                        n ^= n << 10;
                        n4 = (n3 = (n & n2));
                        n6 = (n5 = 0);
                        continue;
                    }
                    n6 += base;
                }
                if ((n4 = (n4 + 1 & n2)) == n3) {
                    if ((scanState >= 0 || scanState == (scanState = workQueue.scanState)) && n5 == (n5 = n6)) {
                        if (scanState < 0) {
                            break;
                        }
                        if (workQueue.qlock < 0) {
                            break;
                        }
                        final int n9 = scanState | Integer.MIN_VALUE;
                        final long ctl2;
                        final long n10 = (0xFFFFFFFFL & n9) | (0xFFFFFFFF00000000L & (ctl2 = this.ctl) - 281474976710656L);
                        workQueue.stackPred = (int)ctl2;
                        ForkJoinPool.U.putInt(workQueue, ForkJoinPool.QSCANSTATE, n9);
                        if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl2, n10)) {
                            scanState = n9;
                        }
                        else {
                            workQueue.scanState = scanState;
                        }
                    }
                    n6 = 0;
                }
            }
        }
        return null;
    }
    
    private boolean awaitWork(final WorkQueue workQueue, int n) {
        if (workQueue == null || workQueue.qlock < 0) {
            return false;
        }
        final int stackPred = workQueue.stackPred;
        int n2 = 0;
        int scanState;
        while ((scanState = workQueue.scanState) < 0) {
            if (n2 > 0) {
                n ^= n << 6;
                n ^= n >>> 21;
                n ^= n << 7;
                if (n < 0 || --n2 != 0) {
                    continue;
                }
                final WorkQueue[] workQueues;
                final int n3;
                final WorkQueue workQueue2;
                if (stackPred == 0 || (workQueues = this.workQueues) == null || (n3 = (stackPred & 0xFFFF)) >= workQueues.length || (workQueue2 = workQueues[n3]) == null || (workQueue2.parker != null && workQueue2.scanState < 0)) {
                    continue;
                }
                n2 = 0;
            }
            else {
                if (workQueue.qlock < 0) {
                    return false;
                }
                if (Thread.interrupted()) {
                    continue;
                }
                final long ctl;
                final int n4 = (int)((ctl = this.ctl) >> 48) + (this.config & 0xFFFF);
                if ((n4 <= 0 && this.tryTerminate(false, false)) || (this.runState & 0x20000000) != 0x0) {
                    return false;
                }
                long n5;
                long n7;
                long n8;
                if (n4 <= 0 && scanState == (int)ctl) {
                    n5 = ((0xFFFFFFFF00000000L & ctl + 281474976710656L) | (0xFFFFFFFFL & stackPred));
                    final short n6 = (short)(ctl >>> 32);
                    if (n6 > 2 && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, n5)) {
                        return false;
                    }
                    n7 = 2000000000L * ((n6 >= 0) ? 1 : (1 - n6));
                    n8 = System.nanoTime() + n7 - 20000000L;
                }
                else {
                    n7 = (n5 = (n8 = 0L));
                }
                final Thread currentThread = Thread.currentThread();
                ForkJoinPool.U.putObject(currentThread, ForkJoinPool.PARKBLOCKER, this);
                workQueue.parker = currentThread;
                if (workQueue.scanState < 0 && this.ctl == ctl) {
                    ForkJoinPool.U.park(false, n7);
                }
                ForkJoinPool.U.putOrderedObject(workQueue, ForkJoinPool.QPARKER, null);
                ForkJoinPool.U.putObject(currentThread, ForkJoinPool.PARKBLOCKER, null);
                if (workQueue.scanState >= 0) {
                    return true;
                }
                if (n7 != 0L && this.ctl == ctl && n8 - System.nanoTime() <= 0L && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, n5)) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    final int helpComplete(final WorkQueue workQueue, final CountedCompleter<?> countedCompleter, int n) {
        int status = 0;
        final WorkQueue[] workQueues;
        final int n2;
        if ((workQueues = this.workQueues) != null && (n2 = workQueues.length - 1) >= 0 && countedCompleter != null && workQueue != null) {
            final int config = workQueue.config;
            int n3 = workQueue.hint ^ workQueue.top;
            int n4 = n3 & n2;
            int pollAndExecCC = 1;
            int n5 = n4;
            int n6 = 0;
            int n7 = 0;
            while ((status = countedCompleter.status) >= 0) {
                final CountedCompleter<?> popCC;
                if (pollAndExecCC == 1 && (popCC = workQueue.popCC(countedCompleter, config)) != null) {
                    popCC.doExec();
                    if (n != 0 && --n == 0) {
                        break;
                    }
                    n4 = n5;
                    n7 = (n6 = 0);
                }
                else {
                    final WorkQueue workQueue2;
                    if ((workQueue2 = workQueues[n5]) == null) {
                        pollAndExecCC = 0;
                    }
                    else if ((pollAndExecCC = workQueue2.pollAndExecCC(countedCompleter)) < 0) {
                        n7 += pollAndExecCC;
                    }
                    if (pollAndExecCC > 0) {
                        if (pollAndExecCC == 1 && n != 0 && --n == 0) {
                            break;
                        }
                        final int n8 = n3 ^ n3 << 13;
                        final int n9 = n8 ^ n8 >>> 17;
                        n3 = (n9 ^ n9 << 5);
                        n5 = (n4 = (n3 & n2));
                        n7 = (n6 = 0);
                    }
                    else {
                        if ((n5 = (n5 + 1 & n2)) != n4) {
                            continue;
                        }
                        if (n6 == (n6 = n7)) {
                            break;
                        }
                        n7 = 0;
                    }
                }
            }
        }
        return status;
    }
    
    private void helpStealer(final WorkQueue workQueue, final ForkJoinTask<?> forkJoinTask) {
        final WorkQueue[] workQueues = this.workQueues;
        int n = 0;
        final int n2;
        if (workQueues != null && (n2 = workQueues.length - 1) >= 0 && workQueue != null && forkJoinTask != null) {
        Label_0376:
            while (true) {
                int n3;
                do {
                    n3 = 0;
                    WorkQueue workQueue2 = workQueue;
                    ForkJoinTask<?> forkJoinTask2 = forkJoinTask;
                Label_0039:
                    while (forkJoinTask2.status >= 0) {
                        final int n4 = workQueue2.hint | 0x1;
                        for (int i = 0; i <= n2; i += 2) {
                            final int hint;
                            final WorkQueue workQueue3;
                            if ((workQueue3 = workQueues[hint = (n4 + i & n2)]) != null) {
                                if (workQueue3.currentSteal == forkJoinTask2) {
                                    workQueue2.hint = hint;
                                    while (true) {
                                        final int base;
                                        n3 += (base = workQueue3.base);
                                        final ForkJoinTask<?> currentJoin = workQueue3.currentJoin;
                                        if (forkJoinTask2.status < 0 || workQueue2.currentJoin != forkJoinTask2) {
                                            continue Label_0376;
                                        }
                                        if (workQueue3.currentSteal != forkJoinTask2) {
                                            continue Label_0376;
                                        }
                                        final ForkJoinTask<?>[] array;
                                        if (base - workQueue3.top >= 0 || (array = workQueue3.array) == null) {
                                            if ((forkJoinTask2 = currentJoin) == null) {
                                                continue Label_0376;
                                            }
                                            workQueue2 = workQueue3;
                                            continue Label_0039;
                                        }
                                        else {
                                            final int n5 = ((array.length - 1 & base) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                                            ForkJoinTask<?> pop = (ForkJoinTask<?>)ForkJoinPool.U.getObjectVolatile(array, n5);
                                            if (workQueue3.base != base) {
                                                continue;
                                            }
                                            if (pop == null) {
                                                continue Label_0376;
                                            }
                                            if (!ForkJoinPool.U.compareAndSwapObject(array, n5, pop, null)) {
                                                continue;
                                            }
                                            workQueue3.base = base + 1;
                                            final ForkJoinTask<?> currentSteal = workQueue.currentSteal;
                                            final int top = workQueue.top;
                                            do {
                                                ForkJoinPool.U.putOrderedObject(workQueue, ForkJoinPool.QCURRENTSTEAL, pop);
                                                pop.doExec();
                                            } while (forkJoinTask.status >= 0 && workQueue.top != top && (pop = workQueue.pop()) != null);
                                            ForkJoinPool.U.putOrderedObject(workQueue, ForkJoinPool.QCURRENTSTEAL, currentSteal);
                                            if (workQueue.base != workQueue.top) {
                                                return;
                                            }
                                            continue;
                                        }
                                    }
                                }
                                else {
                                    n3 += workQueue3.base;
                                }
                            }
                        }
                        break;
                    }
                } while (forkJoinTask.status >= 0 && n != (n = n3));
                break;
            }
        }
    }
    
    private boolean tryCompensate(final WorkQueue workQueue) {
        final WorkQueue[] workQueues;
        final int n;
        final int n2;
        boolean b;
        if (workQueue == null || workQueue.qlock < 0 || (workQueues = this.workQueues) == null || (n = workQueues.length - 1) <= 0 || (n2 = (this.config & 0xFFFF)) == 0) {
            b = false;
        }
        else {
            final long ctl;
            final int n3;
            if ((n3 = (int)(ctl = this.ctl)) != 0) {
                b = this.tryRelease(ctl, workQueues[n3 & n], 0L);
            }
            else {
                final int n4 = (int)(ctl >> 48) + n2;
                final short n5 = (short)((short)(ctl >> 32) + n2);
                int n6 = 0;
                for (int i = 0; i <= n; ++i) {
                    final WorkQueue workQueue2;
                    if ((workQueue2 = workQueues[(i << 1 | 0x1) & n]) != null) {
                        if ((workQueue2.scanState & 0x1) != 0x0) {
                            break;
                        }
                        ++n6;
                    }
                }
                if (n6 != n5 << 1 || this.ctl != ctl) {
                    b = false;
                }
                else if (n5 >= n2 && n4 > 1 && workQueue.isEmpty()) {
                    b = ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, (0xFFFF000000000000L & ctl - 281474976710656L) | (0xFFFFFFFFFFFFL & ctl));
                }
                else {
                    if (n5 >= 32767 || (this == ForkJoinPool.common && n5 >= n2 + ForkJoinPool.commonMaxSpares)) {
                        throw new RejectedExecutionException("Thread limit exceeded replacing blocked worker");
                    }
                    boolean compareAndSwapLong = false;
                    final long n7 = (0xFFFF000000000000L & ctl) | (0xFFFF00000000L & ctl + 4294967296L);
                    final int lockRunState;
                    if (((lockRunState = this.lockRunState()) & 0x20000000) == 0x0) {
                        compareAndSwapLong = ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, n7);
                    }
                    this.unlockRunState(lockRunState, lockRunState & 0xFFFFFFFE);
                    b = (compareAndSwapLong && this.createWorker());
                }
            }
        }
        return b;
    }
    
    final int awaitJoin(final WorkQueue workQueue, final ForkJoinTask<?> forkJoinTask, final long n) {
        int n2 = 0;
        if (forkJoinTask != null && workQueue != null) {
            final ForkJoinTask<?> currentJoin = workQueue.currentJoin;
            ForkJoinPool.U.putOrderedObject(workQueue, ForkJoinPool.QCURRENTJOIN, forkJoinTask);
            final CountedCompleter<?> countedCompleter = (forkJoinTask instanceof CountedCompleter) ? forkJoinTask : null;
            while (true) {
                while ((n2 = forkJoinTask.status) >= 0) {
                    if (countedCompleter != null) {
                        this.helpComplete(workQueue, countedCompleter, 0);
                    }
                    else if (workQueue.base == workQueue.top || workQueue.tryRemoveAndExec(forkJoinTask)) {
                        this.helpStealer(workQueue, forkJoinTask);
                    }
                    if ((n2 = forkJoinTask.status) >= 0) {
                        long millis;
                        if (n == 0L) {
                            millis = 0L;
                        }
                        else {
                            final long n3;
                            if ((n3 = n - System.nanoTime()) <= 0L) {
                                break;
                            }
                            if ((millis = TimeUnit.NANOSECONDS.toMillis(n3)) <= 0L) {
                                millis = 1L;
                            }
                        }
                        if (!this.tryCompensate(workQueue)) {
                            continue;
                        }
                        forkJoinTask.internalWait(millis);
                        ForkJoinPool.U.getAndAddLong(this, ForkJoinPool.CTL, 281474976710656L);
                        continue;
                    }
                    ForkJoinPool.U.putOrderedObject(workQueue, ForkJoinPool.QCURRENTJOIN, currentJoin);
                    return n2;
                }
                continue;
            }
        }
        return n2;
    }
    
    private WorkQueue findNonEmptyStealQueue() {
        final int nextSecondarySeed = ThreadLocalRandom.nextSecondarySeed();
        final WorkQueue[] workQueues;
        final int n;
        if ((workQueues = this.workQueues) != null && (n = workQueues.length - 1) >= 0) {
            int n3;
            final int n2 = n3 = (nextSecondarySeed & n);
            int n4 = 0;
            int n5 = 0;
            while (true) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[n3]) != null) {
                    final int base;
                    if ((base = workQueue.base) - workQueue.top < 0) {
                        return workQueue;
                    }
                    n5 += base;
                }
                if ((n3 = (n3 + 1 & n)) == n2) {
                    if (n4 == (n4 = n5)) {
                        break;
                    }
                    n5 = 0;
                }
            }
        }
        return null;
    }
    
    final void helpQuiescePool(final WorkQueue workQueue) {
        final ForkJoinTask<?> currentSteal = workQueue.currentSteal;
        int n = 1;
        while (true) {
            workQueue.execLocalTasks();
            final WorkQueue nonEmptyStealQueue;
            if ((nonEmptyStealQueue = this.findNonEmptyStealQueue()) != null) {
                if (n == 0) {
                    n = 1;
                    ForkJoinPool.U.getAndAddLong(this, ForkJoinPool.CTL, 281474976710656L);
                }
                final int base;
                final ForkJoinTask<?> poll;
                if ((base = nonEmptyStealQueue.base) - nonEmptyStealQueue.top >= 0 || (poll = nonEmptyStealQueue.pollAt(base)) == null) {
                    continue;
                }
                ForkJoinPool.U.putOrderedObject(workQueue, ForkJoinPool.QCURRENTSTEAL, poll);
                poll.doExec();
                if (++workQueue.nsteals >= 0) {
                    continue;
                }
                workQueue.transferStealCount(this);
            }
            else if (n != 0) {
                final long ctl;
                final long n2 = (0xFFFF000000000000L & (ctl = this.ctl) - 281474976710656L) | (0xFFFFFFFFFFFFL & ctl);
                if ((int)(n2 >> 48) + (this.config & 0xFFFF) <= 0) {
                    break;
                }
                if (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, n2)) {
                    continue;
                }
                n = 0;
            }
            else {
                final long ctl2;
                if ((int)((ctl2 = this.ctl) >> 48) + (this.config & 0xFFFF) <= 0 && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl2, ctl2 + 281474976710656L)) {
                    break;
                }
                continue;
            }
        }
        ForkJoinPool.U.putOrderedObject(workQueue, ForkJoinPool.QCURRENTSTEAL, currentSteal);
    }
    
    final ForkJoinTask<?> nextTaskFor(final WorkQueue workQueue) {
        ForkJoinTask<?> nextLocalTask;
        while ((nextLocalTask = workQueue.nextLocalTask()) == null) {
            final WorkQueue nonEmptyStealQueue;
            if ((nonEmptyStealQueue = this.findNonEmptyStealQueue()) == null) {
                return null;
            }
            final int base;
            final ForkJoinTask<?> poll;
            if ((base = nonEmptyStealQueue.base) - nonEmptyStealQueue.top < 0 && (poll = nonEmptyStealQueue.pollAt(base)) != null) {
                return poll;
            }
        }
        return nextLocalTask;
    }
    
    static int getSurplusQueuedTaskCount() {
        final Thread currentThread;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            final ForkJoinWorkerThread forkJoinWorkerThread;
            final ForkJoinPool pool;
            final int n = (pool = (forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread).pool).config & 0xFFFF;
            final WorkQueue workQueue = forkJoinWorkerThread.workQueue;
            final int n2 = workQueue.top - workQueue.base;
            final int n3 = (int)(pool.ctl >> 48) + n;
            final int n4;
            final int n5;
            final int n6;
            return n2 - ((n3 > (n4 = n >>> 1)) ? 0 : ((n3 > (n5 = n4 >>> 1)) ? 1 : ((n3 > (n6 = n5 >>> 1)) ? 2 : ((n3 > n6 >>> 1) ? 4 : 8))));
        }
        return 0;
    }
    
    private boolean tryTerminate(final boolean b, final boolean b2) {
        if (this == ForkJoinPool.common) {
            return false;
        }
        int n;
        if ((n = this.runState) >= 0) {
            if (!b2) {
                return false;
            }
            n = this.lockRunState();
            this.unlockRunState(n, (n & 0xFFFFFFFE) | Integer.MIN_VALUE);
        }
        if ((n & 0x20000000) == 0x0) {
            if (!b) {
                long n2 = 0L;
                long ctl;
                do {
                    ctl = this.ctl;
                    if ((int)(ctl >> 48) + (this.config & 0xFFFF) > 0) {
                        return false;
                    }
                    final WorkQueue[] workQueues;
                    if ((workQueues = this.workQueues) == null) {
                        break;
                    }
                    final int n3;
                    if ((n3 = workQueues.length - 1) <= 0) {
                        break;
                    }
                    for (int i = 0; i <= n3; ++i) {
                        final WorkQueue workQueue;
                        if ((workQueue = workQueues[i]) != null) {
                            final int base;
                            if ((base = workQueue.base) != workQueue.top || workQueue.scanState >= 0 || workQueue.currentSteal != null) {
                                final long ctl2 = this.ctl;
                                this.tryRelease(ctl2, workQueues[n3 & (int)ctl2], 281474976710656L);
                                return false;
                            }
                            ctl += base;
                            if ((i & 0x1) == 0x0) {
                                workQueue.qlock = -1;
                            }
                        }
                    }
                } while (n2 != (n2 = ctl));
            }
            if ((this.runState & 0x20000000) == 0x0) {
                final int lockRunState = this.lockRunState();
                this.unlockRunState(lockRunState, (lockRunState & 0xFFFFFFFE) | 0x20000000);
            }
        }
        int n4 = 0;
        long n5 = 0L;
        while (true) {
            long ctl3 = this.ctl;
            final WorkQueue[] workQueues2;
            final int n6;
            if ((short)(ctl3 >>> 32) + (this.config & 0xFFFF) <= 0 || (workQueues2 = this.workQueues) == null || (n6 = workQueues2.length - 1) <= 0) {
                if ((this.runState & 0x40000000) == 0x0) {
                    final int lockRunState2 = this.lockRunState();
                    this.unlockRunState(lockRunState2, (lockRunState2 & 0xFFFFFFFE) | 0x40000000);
                    synchronized (this) {
                        this.notifyAll();
                    }
                    break;
                }
                break;
            }
            else {
                for (int j = 0; j <= n6; ++j) {
                    final WorkQueue workQueue2;
                    if ((workQueue2 = workQueues2[j]) != null) {
                        ctl3 += workQueue2.base;
                        workQueue2.qlock = -1;
                        if (n4 > 0) {
                            workQueue2.cancelAll();
                            final ForkJoinWorkerThread owner;
                            if (n4 > 1 && (owner = workQueue2.owner) != null) {
                                if (!owner.isInterrupted()) {
                                    try {
                                        owner.interrupt();
                                    }
                                    catch (Throwable t) {}
                                }
                                if (workQueue2.scanState < 0) {
                                    ForkJoinPool.U.unpark(owner);
                                }
                            }
                        }
                    }
                }
                if (ctl3 != n5) {
                    n5 = ctl3;
                    n4 = 0;
                }
                else {
                    if (n4 > 3 && n4 > n6) {
                        break;
                    }
                    if (++n4 <= 1) {
                        continue;
                    }
                    int n7 = 0;
                    long ctl4;
                    int n8;
                    while (n7++ <= n6 && (n8 = (int)(ctl4 = this.ctl)) != 0) {
                        this.tryRelease(ctl4, workQueues2[n8 & n6], 281474976710656L);
                    }
                }
            }
        }
        return true;
    }
    
    private void externalSubmit(final ForkJoinTask<?> forkJoinTask) {
        int hint;
        if ((hint = ThreadLocalRandom.getProbe()) == 0) {
            ThreadLocalRandom.localInit();
            hint = ThreadLocalRandom.getProbe();
        }
        while (true) {
            boolean b = false;
            final int runState;
            if ((runState = this.runState) < 0) {
                this.tryTerminate(false, false);
                throw new RejectedExecutionException();
            }
            final WorkQueue[] workQueues;
            final int n;
            if ((runState & 0x4) == 0x0 || (workQueues = this.workQueues) == null || (n = workQueues.length - 1) < 0) {
                int n2 = 0;
                final int lockRunState = this.lockRunState();
                try {
                    if ((lockRunState & 0x4) == 0x0) {
                        ForkJoinPool.U.compareAndSwapObject(this, ForkJoinPool.STEALCOUNTER, null, new AtomicLong());
                        final int n3 = this.config & 0xFFFF;
                        final int n4 = (n3 > 1) ? (n3 - 1) : 1;
                        final int n5 = n4 | n4 >>> 1;
                        final int n6 = n5 | n5 >>> 2;
                        final int n7 = n6 | n6 >>> 4;
                        final int n8 = n7 | n7 >>> 8;
                        this.workQueues = new WorkQueue[(n8 | n8 >>> 16) + 1 << 1];
                        n2 = 4;
                    }
                }
                finally {
                    this.unlockRunState(lockRunState, (lockRunState & 0xFFFFFFFE) | n2);
                }
            }
            else {
                final int n9;
                final WorkQueue workQueue;
                if ((workQueue = workQueues[n9 = (hint & n & 0x7E)]) != null) {
                    if (workQueue.qlock == 0 && ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 0, 1)) {
                        ForkJoinTask<?>[] array = workQueue.array;
                        final int top = workQueue.top;
                        boolean b2 = false;
                        try {
                            if ((array != null && array.length > top + 1 - workQueue.base) || (array = workQueue.growArray()) != null) {
                                ForkJoinPool.U.putOrderedObject(array, ((array.length - 1 & top) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE, forkJoinTask);
                                ForkJoinPool.U.putOrderedInt(workQueue, ForkJoinPool.QTOP, top + 1);
                                b2 = true;
                            }
                        }
                        finally {
                            ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 1, 0);
                        }
                        if (b2) {
                            this.signalWork(workQueues, workQueue);
                            return;
                        }
                    }
                    b = true;
                }
                else if ((this.runState & 0x1) == 0x0) {
                    final WorkQueue workQueue2 = new WorkQueue(this, null);
                    workQueue2.hint = hint;
                    workQueue2.config = (n9 | Integer.MIN_VALUE);
                    workQueue2.scanState = Integer.MIN_VALUE;
                    final int lockRunState2 = this.lockRunState();
                    final WorkQueue[] workQueues2;
                    if (lockRunState2 > 0 && (workQueues2 = this.workQueues) != null && n9 < workQueues2.length && workQueues2[n9] == null) {
                        workQueues2[n9] = workQueue2;
                    }
                    this.unlockRunState(lockRunState2, lockRunState2 & 0xFFFFFFFE);
                }
                else {
                    b = true;
                }
            }
            if (!b) {
                continue;
            }
            hint = ThreadLocalRandom.advanceProbe(hint);
        }
    }
    
    final void externalPush(final ForkJoinTask<?> forkJoinTask) {
        final int probe = ThreadLocalRandom.getProbe();
        final int runState = this.runState;
        final WorkQueue[] workQueues;
        final int n;
        final WorkQueue workQueue;
        if ((workQueues = this.workQueues) != null && (n = workQueues.length - 1) >= 0 && (workQueue = workQueues[n & probe & 0x7E]) != null && probe != 0 && runState > 0 && ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 0, 1)) {
            final ForkJoinTask<?>[] array;
            if ((array = workQueue.array) != null) {
                final int n2 = array.length - 1;
                final int top;
                final int n3;
                if (n2 > (n3 = (top = workQueue.top) - workQueue.base)) {
                    ForkJoinPool.U.putOrderedObject(array, ((n2 & top) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE, forkJoinTask);
                    ForkJoinPool.U.putOrderedInt(workQueue, ForkJoinPool.QTOP, top + 1);
                    ForkJoinPool.U.putIntVolatile(workQueue, ForkJoinPool.QLOCK, 0);
                    if (n3 <= 1) {
                        this.signalWork(workQueues, workQueue);
                    }
                    return;
                }
            }
            ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 1, 0);
        }
        this.externalSubmit(forkJoinTask);
    }
    
    static WorkQueue commonSubmitterQueue() {
        final ForkJoinPool common = ForkJoinPool.common;
        final int probe = ThreadLocalRandom.getProbe();
        final WorkQueue[] workQueues;
        final int n;
        return (common != null && (workQueues = common.workQueues) != null && (n = workQueues.length - 1) >= 0) ? workQueues[n & probe & 0x7E] : null;
    }
    
    final boolean tryExternalUnpush(final ForkJoinTask<?> forkJoinTask) {
        final int probe = ThreadLocalRandom.getProbe();
        final WorkQueue[] workQueues;
        final int n;
        final WorkQueue workQueue;
        final ForkJoinTask<?>[] array;
        final int top;
        if ((workQueues = this.workQueues) != null && (n = workQueues.length - 1) >= 0 && (workQueue = workQueues[n & probe & 0x7E]) != null && (array = workQueue.array) != null && (top = workQueue.top) != workQueue.base) {
            final long n2 = ((array.length - 1 & top - 1) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
            if (ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 0, 1)) {
                if (workQueue.top == top && workQueue.array == array && ForkJoinPool.U.getObject(array, n2) == forkJoinTask && ForkJoinPool.U.compareAndSwapObject(array, n2, forkJoinTask, null)) {
                    ForkJoinPool.U.putOrderedInt(workQueue, ForkJoinPool.QTOP, top - 1);
                    ForkJoinPool.U.putOrderedInt(workQueue, ForkJoinPool.QLOCK, 0);
                    return true;
                }
                ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 1, 0);
            }
        }
        return false;
    }
    
    final int externalHelpComplete(final CountedCompleter<?> countedCompleter, final int n) {
        final int probe = ThreadLocalRandom.getProbe();
        final WorkQueue[] workQueues;
        final int length;
        return ((workQueues = this.workQueues) == null || (length = workQueues.length) == 0) ? 0 : this.helpComplete(workQueues[length - 1 & probe & 0x7E], countedCompleter, n);
    }
    
    public ForkJoinPool() {
        this(Math.min(32767, Runtime.getRuntime().availableProcessors()), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
    }
    
    public ForkJoinPool(final int n) {
        this(n, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
    }
    
    public ForkJoinPool(final int n, final ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory, final Thread.UncaughtExceptionHandler uncaughtExceptionHandler, final boolean b) {
        this(checkParallelism(n), checkFactory(forkJoinWorkerThreadFactory), uncaughtExceptionHandler, b ? 65536 : 0, "ForkJoinPool-" + nextPoolId() + "-worker-");
        checkPermission();
    }
    
    private static int checkParallelism(final int n) {
        if (n <= 0 || n > 32767) {
            throw new IllegalArgumentException();
        }
        return n;
    }
    
    private static ForkJoinWorkerThreadFactory checkFactory(final ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory) {
        if (forkJoinWorkerThreadFactory == null) {
            throw new NullPointerException();
        }
        return forkJoinWorkerThreadFactory;
    }
    
    private ForkJoinPool(final int n, final ForkJoinWorkerThreadFactory factory, final Thread.UncaughtExceptionHandler ueh, final int n2, final String workerNamePrefix) {
        this.workerNamePrefix = workerNamePrefix;
        this.factory = factory;
        this.ueh = ueh;
        this.config = ((n & 0xFFFF) | n2);
        final long n3 = -n;
        this.ctl = ((n3 << 48 & 0xFFFF000000000000L) | (n3 << 32 & 0xFFFF00000000L));
    }
    
    public static ForkJoinPool commonPool() {
        return ForkJoinPool.common;
    }
    
    public <T> T invoke(final ForkJoinTask<T> forkJoinTask) {
        if (forkJoinTask == null) {
            throw new NullPointerException();
        }
        this.externalPush(forkJoinTask);
        return forkJoinTask.join();
    }
    
    public void execute(final ForkJoinTask<?> forkJoinTask) {
        if (forkJoinTask == null) {
            throw new NullPointerException();
        }
        this.externalPush(forkJoinTask);
    }
    
    @Override
    public void execute(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        ForkJoinTask forkJoinTask;
        if (runnable instanceof ForkJoinTask) {
            forkJoinTask = (ForkJoinTask)runnable;
        }
        else {
            forkJoinTask = new ForkJoinTask.RunnableExecuteAction(runnable);
        }
        this.externalPush(forkJoinTask);
    }
    
    public <T> ForkJoinTask<T> submit(final ForkJoinTask<T> forkJoinTask) {
        if (forkJoinTask == null) {
            throw new NullPointerException();
        }
        this.externalPush(forkJoinTask);
        return forkJoinTask;
    }
    
    @Override
    public <T> ForkJoinTask<T> submit(final Callable<T> callable) {
        final ForkJoinTask.AdaptedCallable<Object> adaptedCallable = new ForkJoinTask.AdaptedCallable<Object>(callable);
        this.externalPush(adaptedCallable);
        return (ForkJoinTask<T>)adaptedCallable;
    }
    
    @Override
    public <T> ForkJoinTask<T> submit(final Runnable runnable, final T t) {
        final ForkJoinTask.AdaptedRunnable<Object> adaptedRunnable = new ForkJoinTask.AdaptedRunnable<Object>(runnable, t);
        this.externalPush(adaptedRunnable);
        return (ForkJoinTask<T>)adaptedRunnable;
    }
    
    @Override
    public ForkJoinTask<?> submit(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        ForkJoinTask forkJoinTask;
        if (runnable instanceof ForkJoinTask) {
            forkJoinTask = (ForkJoinTask)runnable;
        }
        else {
            forkJoinTask = new ForkJoinTask.AdaptedRunnableAction(runnable);
        }
        this.externalPush(forkJoinTask);
        return (ForkJoinTask<?>)forkJoinTask;
    }
    
    @Override
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> collection) {
        final ArrayList<ForkJoinTask.AdaptedCallable<T>> list = new ArrayList<ForkJoinTask.AdaptedCallable<T>>(collection.size());
        boolean b = false;
        try {
            final Iterator<? extends Callable<T>> iterator = collection.iterator();
            while (iterator.hasNext()) {
                final ForkJoinTask.AdaptedCallable adaptedCallable = new ForkJoinTask.AdaptedCallable<T>((Callable<?>)iterator.next());
                list.add((ForkJoinTask.AdaptedCallable<T>)adaptedCallable);
                this.externalPush(adaptedCallable);
            }
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).quietlyJoin();
            }
            b = true;
            return (List<Future<T>>)list;
        }
        finally {
            if (!b) {
                for (int j = 0; j < list.size(); ++j) {
                    list.get(j).cancel(false);
                }
            }
        }
    }
    
    public ForkJoinWorkerThreadFactory getFactory() {
        return this.factory;
    }
    
    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.ueh;
    }
    
    public int getParallelism() {
        final int n;
        return ((n = (this.config & 0xFFFF)) > 0) ? n : true;
    }
    
    public static int getCommonPoolParallelism() {
        return ForkJoinPool.commonParallelism;
    }
    
    public int getPoolSize() {
        return (this.config & 0xFFFF) + (short)(this.ctl >>> 32);
    }
    
    public boolean getAsyncMode() {
        return (this.config & 0x10000) != 0x0;
    }
    
    public int getRunningThreadCount() {
        int n = 0;
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            for (int i = 1; i < workQueues.length; i += 2) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[i]) != null && workQueue.isApparentlyUnblocked()) {
                    ++n;
                }
            }
        }
        return n;
    }
    
    public int getActiveThreadCount() {
        final int n = (this.config & 0xFFFF) + (int)(this.ctl >> 48);
        return (n <= 0) ? 0 : n;
    }
    
    public boolean isQuiescent() {
        return (this.config & 0xFFFF) + (int)(this.ctl >> 48) <= 0;
    }
    
    public long getStealCount() {
        final AtomicLong stealCounter = this.stealCounter;
        long n = (stealCounter == null) ? 0L : stealCounter.get();
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            for (int i = 1; i < workQueues.length; i += 2) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[i]) != null) {
                    n += workQueue.nsteals;
                }
            }
        }
        return n;
    }
    
    public long getQueuedTaskCount() {
        long n = 0L;
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            for (int i = 1; i < workQueues.length; i += 2) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[i]) != null) {
                    n += workQueue.queueSize();
                }
            }
        }
        return n;
    }
    
    public int getQueuedSubmissionCount() {
        int n = 0;
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            for (int i = 0; i < workQueues.length; i += 2) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[i]) != null) {
                    n += workQueue.queueSize();
                }
            }
        }
        return n;
    }
    
    public boolean hasQueuedSubmissions() {
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            for (int i = 0; i < workQueues.length; i += 2) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[i]) != null && !workQueue.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected ForkJoinTask<?> pollSubmission() {
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            for (int i = 0; i < workQueues.length; i += 2) {
                final WorkQueue workQueue;
                final ForkJoinTask<?> poll;
                if ((workQueue = workQueues[i]) != null && (poll = workQueue.poll()) != null) {
                    return poll;
                }
            }
        }
        return null;
    }
    
    protected int drainTasksTo(final Collection<? super ForkJoinTask<?>> collection) {
        int n = 0;
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            for (int i = 0; i < workQueues.length; ++i) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[i]) != null) {
                    ForkJoinTask<?> poll;
                    while ((poll = workQueue.poll()) != null) {
                        collection.add(poll);
                        ++n;
                    }
                }
            }
        }
        return n;
    }
    
    @Override
    public String toString() {
        long n = 0L;
        long n2 = 0L;
        int n3 = 0;
        final AtomicLong stealCounter = this.stealCounter;
        long n4 = (stealCounter == null) ? 0L : stealCounter.get();
        final long ctl = this.ctl;
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            for (int i = 0; i < workQueues.length; ++i) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[i]) != null) {
                    final int queueSize = workQueue.queueSize();
                    if ((i & 0x1) == 0x0) {
                        n2 += queueSize;
                    }
                    else {
                        n += queueSize;
                        n4 += workQueue.nsteals;
                        if (workQueue.isApparentlyUnblocked()) {
                            ++n3;
                        }
                    }
                }
            }
        }
        final int n5 = this.config & 0xFFFF;
        final short n6 = (short)(n5 + (short)(ctl >>> 32));
        int n7 = n5 + (int)(ctl >> 48);
        if (n7 < 0) {
            n7 = 0;
        }
        final int runState = this.runState;
        return super.toString() + "[" + (((runState & 0x40000000) != 0x0) ? "Terminated" : (((runState & 0x20000000) != 0x0) ? "Terminating" : (((runState & Integer.MIN_VALUE) != 0x0) ? "Shutting down" : "Running"))) + ", parallelism = " + n5 + ", size = " + n6 + ", active = " + n7 + ", running = " + n3 + ", steals = " + n4 + ", tasks = " + n + ", submissions = " + n2 + "]";
    }
    
    @Override
    public void shutdown() {
        checkPermission();
        this.tryTerminate(false, true);
    }
    
    @Override
    public List<Runnable> shutdownNow() {
        checkPermission();
        this.tryTerminate(true, true);
        return Collections.emptyList();
    }
    
    @Override
    public boolean isTerminated() {
        return (this.runState & 0x40000000) != 0x0;
    }
    
    public boolean isTerminating() {
        final int runState = this.runState;
        return (runState & 0x20000000) != 0x0 && (runState & 0x40000000) == 0x0;
    }
    
    @Override
    public boolean isShutdown() {
        return (this.runState & Integer.MIN_VALUE) != 0x0;
    }
    
    @Override
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (this == ForkJoinPool.common) {
            this.awaitQuiescence(n, timeUnit);
            return false;
        }
        long nanos = timeUnit.toNanos(n);
        if (this.isTerminated()) {
            return true;
        }
        if (nanos <= 0L) {
            return false;
        }
        final long n2 = System.nanoTime() + nanos;
        synchronized (this) {
            while (!this.isTerminated()) {
                if (nanos <= 0L) {
                    return false;
                }
                final long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
                this.wait((millis > 0L) ? millis : 1L);
                nanos = n2 - System.nanoTime();
            }
            return true;
        }
    }
    
    public boolean awaitQuiescence(final long n, final TimeUnit timeUnit) {
        final long nanos = timeUnit.toNanos(n);
        final Thread currentThread = Thread.currentThread();
        final ForkJoinWorkerThread forkJoinWorkerThread;
        if (currentThread instanceof ForkJoinWorkerThread && (forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread).pool == this) {
            this.helpQuiescePool(forkJoinWorkerThread.workQueue);
            return true;
        }
        final long nanoTime = System.nanoTime();
        int n2 = 0;
        int n3 = 1;
        WorkQueue[] workQueues;
        int n4;
        while (!this.isQuiescent() && (workQueues = this.workQueues) != null && (n4 = workQueues.length - 1) >= 0) {
            if (n3 == 0) {
                if (System.nanoTime() - nanoTime > nanos) {
                    return false;
                }
                Thread.yield();
            }
            n3 = 0;
            int i = n4 + 1 << 2;
            while (i >= 0) {
                final int n5;
                final WorkQueue workQueue;
                final int base;
                if ((n5 = (n2++ & n4)) <= n4 && n5 >= 0 && (workQueue = workQueues[n5]) != null && (base = workQueue.base) - workQueue.top < 0) {
                    n3 = 1;
                    final ForkJoinTask<?> poll;
                    if ((poll = workQueue.pollAt(base)) != null) {
                        poll.doExec();
                        break;
                    }
                    break;
                }
                else {
                    --i;
                }
            }
        }
        return true;
    }
    
    static void quiesceCommonPool() {
        ForkJoinPool.common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
    
    public static void managedBlock(final ManagedBlocker managedBlocker) throws InterruptedException {
        final Thread currentThread = Thread.currentThread();
        final ForkJoinWorkerThread forkJoinWorkerThread;
        final ForkJoinPool pool;
        if (currentThread instanceof ForkJoinWorkerThread && (pool = (forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread).pool) != null) {
            final WorkQueue workQueue = forkJoinWorkerThread.workQueue;
            while (!managedBlocker.isReleasable()) {
                if (pool.tryCompensate(workQueue)) {
                    try {
                        while (!managedBlocker.isReleasable() && !managedBlocker.block()) {}
                    }
                    finally {
                        ForkJoinPool.U.getAndAddLong(pool, ForkJoinPool.CTL, 281474976710656L);
                    }
                    break;
                }
            }
        }
        else {
            while (!managedBlocker.isReleasable() && !managedBlocker.block()) {}
        }
    }
    
    @Override
    protected <T> RunnableFuture<T> newTaskFor(final Runnable runnable, final T t) {
        return new ForkJoinTask.AdaptedRunnable<T>(runnable, t);
    }
    
    @Override
    protected <T> RunnableFuture<T> newTaskFor(final Callable<T> callable) {
        return new ForkJoinTask.AdaptedCallable<T>((Callable<? extends T>)callable);
    }
    
    private static ForkJoinPool makeCommonPool() {
        int int1 = -1;
        ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory = null;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
        try {
            final String property = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
            final String property2 = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
            final String property3 = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
            if (property != null) {
                int1 = Integer.parseInt(property);
            }
            if (property2 != null) {
                defaultForkJoinWorkerThreadFactory = (ForkJoinWorkerThreadFactory)ClassLoader.getSystemClassLoader().loadClass(property2).newInstance();
            }
            if (property3 != null) {
                uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler)ClassLoader.getSystemClassLoader().loadClass(property3).newInstance();
            }
        }
        catch (Exception ex) {}
        if (defaultForkJoinWorkerThreadFactory == null) {
            if (System.getSecurityManager() == null) {
                defaultForkJoinWorkerThreadFactory = ForkJoinPool.defaultForkJoinWorkerThreadFactory;
            }
            else {
                defaultForkJoinWorkerThreadFactory = new InnocuousForkJoinWorkerThreadFactory();
            }
        }
        if (int1 < 0 && (int1 = Runtime.getRuntime().availableProcessors() - 1) <= 0) {
            int1 = 1;
        }
        if (int1 > 32767) {
            int1 = 32767;
        }
        return new ForkJoinPool(int1, defaultForkJoinWorkerThreadFactory, uncaughtExceptionHandler, 0, "ForkJoinPool.commonPool-worker-");
    }
    
    static {
        try {
            U = Unsafe.getUnsafe();
            final Class<ForkJoinPool> clazz = ForkJoinPool.class;
            CTL = ForkJoinPool.U.objectFieldOffset(clazz.getDeclaredField("ctl"));
            RUNSTATE = ForkJoinPool.U.objectFieldOffset(clazz.getDeclaredField("runState"));
            STEALCOUNTER = ForkJoinPool.U.objectFieldOffset(clazz.getDeclaredField("stealCounter"));
            PARKBLOCKER = ForkJoinPool.U.objectFieldOffset(Thread.class.getDeclaredField("parkBlocker"));
            final Class<WorkQueue> clazz2 = WorkQueue.class;
            QTOP = ForkJoinPool.U.objectFieldOffset(clazz2.getDeclaredField("top"));
            QLOCK = ForkJoinPool.U.objectFieldOffset(clazz2.getDeclaredField("qlock"));
            QSCANSTATE = ForkJoinPool.U.objectFieldOffset(clazz2.getDeclaredField("scanState"));
            QPARKER = ForkJoinPool.U.objectFieldOffset(clazz2.getDeclaredField("parker"));
            QCURRENTSTEAL = ForkJoinPool.U.objectFieldOffset(clazz2.getDeclaredField("currentSteal"));
            QCURRENTJOIN = ForkJoinPool.U.objectFieldOffset(clazz2.getDeclaredField("currentJoin"));
            final Class<ForkJoinTask[]> clazz3 = ForkJoinTask[].class;
            ABASE = ForkJoinPool.U.arrayBaseOffset(clazz3);
            final int arrayIndexScale = ForkJoinPool.U.arrayIndexScale(clazz3);
            if ((arrayIndexScale & arrayIndexScale - 1) != 0x0) {
                throw new Error("data type scale not a power of two");
            }
            ASHIFT = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
        ForkJoinPool.commonMaxSpares = 256;
        defaultForkJoinWorkerThreadFactory = new DefaultForkJoinWorkerThreadFactory();
        modifyThreadPermission = new RuntimePermission("modifyThread");
        common = AccessController.doPrivileged((PrivilegedAction<ForkJoinPool>)new PrivilegedAction<ForkJoinPool>() {
            @Override
            public ForkJoinPool run() {
                return makeCommonPool();
            }
        });
        final int n = ForkJoinPool.common.config & 0xFFFF;
        commonParallelism = ((n > 0) ? n : true);
    }
    
    static final class DefaultForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory
    {
        @Override
        public final ForkJoinWorkerThread newThread(final ForkJoinPool forkJoinPool) {
            return new ForkJoinWorkerThread(forkJoinPool, null);
        }
    }
    
    public interface ForkJoinWorkerThreadFactory
    {
        ForkJoinWorkerThread newThread(final ForkJoinPool p0);
    }
    
    static final class EmptyTask extends ForkJoinTask<Void>
    {
        private static final long serialVersionUID = -7721805057305804111L;
        
        EmptyTask() {
            this.status = -268435456;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
        
        public final boolean exec() {
            return true;
        }
    }
    
    static final class InnocuousForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory
    {
        private static final AccessControlContext innocuousAcc;
        
        @Override
        public final ForkJoinWorkerThread newThread(final ForkJoinPool forkJoinPool) {
            return AccessController.doPrivileged((PrivilegedAction<ForkJoinWorkerThread.InnocuousForkJoinWorkerThread>)new PrivilegedAction<ForkJoinWorkerThread>() {
                @Override
                public ForkJoinWorkerThread run() {
                    return new ForkJoinWorkerThread.InnocuousForkJoinWorkerThread(forkJoinPool);
                }
            }, InnocuousForkJoinWorkerThreadFactory.innocuousAcc);
        }
        
        static {
            final Permissions permissions = new Permissions();
            permissions.add(ForkJoinPool.modifyThreadPermission);
            permissions.add(new RuntimePermission("enableContextClassLoaderOverride"));
            permissions.add(new RuntimePermission("modifyThreadGroup"));
            innocuousAcc = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, permissions) });
        }
    }
    
    @Contended
    static final class WorkQueue
    {
        static final int INITIAL_QUEUE_CAPACITY = 8192;
        static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
        volatile int scanState;
        int stackPred;
        int nsteals;
        int hint;
        int config;
        volatile int qlock;
        volatile int base;
        int top;
        ForkJoinTask<?>[] array;
        final ForkJoinPool pool;
        final ForkJoinWorkerThread owner;
        volatile Thread parker;
        volatile ForkJoinTask<?> currentJoin;
        volatile ForkJoinTask<?> currentSteal;
        private static final Unsafe U;
        private static final int ABASE;
        private static final int ASHIFT;
        private static final long QTOP;
        private static final long QLOCK;
        private static final long QCURRENTSTEAL;
        
        WorkQueue(final ForkJoinPool pool, final ForkJoinWorkerThread owner) {
            this.pool = pool;
            this.owner = owner;
            final int n = 4096;
            this.top = n;
            this.base = n;
        }
        
        final int getPoolIndex() {
            return (this.config & 0xFFFF) >>> 1;
        }
        
        final int queueSize() {
            final int n = this.base - this.top;
            return (n >= 0) ? 0 : (-n);
        }
        
        final boolean isEmpty() {
            final int top;
            final int n;
            final ForkJoinTask<?>[] array;
            final int n2;
            return (n = this.base - (top = this.top)) >= 0 || (n == -1 && ((array = this.array) == null || (n2 = array.length - 1) < 0 || WorkQueue.U.getObject(array, ((n2 & top - 1) << WorkQueue.ASHIFT) + WorkQueue.ABASE) == null));
        }
        
        final void push(final ForkJoinTask<?> forkJoinTask) {
            final int base = this.base;
            final int top = this.top;
            final ForkJoinTask<?>[] array;
            if ((array = this.array) != null) {
                final int n = array.length - 1;
                WorkQueue.U.putOrderedObject(array, ((n & top) << WorkQueue.ASHIFT) + WorkQueue.ABASE, forkJoinTask);
                WorkQueue.U.putOrderedInt(this, WorkQueue.QTOP, top + 1);
                final int n2;
                if ((n2 = top - base) <= 1) {
                    final ForkJoinPool pool;
                    if ((pool = this.pool) != null) {
                        pool.signalWork(pool.workQueues, this);
                    }
                }
                else if (n2 >= n) {
                    this.growArray();
                }
            }
        }
        
        final ForkJoinTask<?>[] growArray() {
            final ForkJoinTask<?>[] array = this.array;
            final int n = (array != null) ? (array.length << 1) : 8192;
            if (n > 67108864) {
                throw new RejectedExecutionException("Queue capacity exceeded");
            }
            final ForkJoinTask[] array2 = new ForkJoinTask[n];
            this.array = (ForkJoinTask<?>[])array2;
            final ForkJoinTask<?>[] array3 = (ForkJoinTask<?>[])array2;
            final int n2;
            if (array != null && (n2 = array.length - 1) >= 0) {
                final int top = this.top;
                int base;
                if (top - (base = this.base) > 0) {
                    final int n3 = n - 1;
                    do {
                        final int n4 = ((base & n2) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                        final int n5 = ((base & n3) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                        final ForkJoinTask forkJoinTask = (ForkJoinTask)WorkQueue.U.getObjectVolatile(array, n4);
                        if (forkJoinTask != null && WorkQueue.U.compareAndSwapObject(array, n4, forkJoinTask, null)) {
                            WorkQueue.U.putObjectVolatile(array3, n5, forkJoinTask);
                        }
                    } while (++base != top);
                }
            }
            return array3;
        }
        
        final ForkJoinTask<?> pop() {
            final ForkJoinTask<?>[] array;
            final int n;
            if ((array = this.array) != null && (n = array.length - 1) >= 0) {
                int n2;
                while ((n2 = this.top - 1) - this.base >= 0) {
                    final long n3 = ((n & n2) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                    final ForkJoinTask forkJoinTask;
                    if ((forkJoinTask = (ForkJoinTask)WorkQueue.U.getObject(array, n3)) == null) {
                        break;
                    }
                    if (WorkQueue.U.compareAndSwapObject(array, n3, forkJoinTask, null)) {
                        WorkQueue.U.putOrderedInt(this, WorkQueue.QTOP, n2);
                        return (ForkJoinTask<?>)forkJoinTask;
                    }
                }
            }
            return null;
        }
        
        final ForkJoinTask<?> pollAt(final int n) {
            final ForkJoinTask<?>[] array;
            if ((array = this.array) != null) {
                final int n2 = ((array.length - 1 & n) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final ForkJoinTask forkJoinTask;
                if ((forkJoinTask = (ForkJoinTask)WorkQueue.U.getObjectVolatile(array, n2)) != null && this.base == n && WorkQueue.U.compareAndSwapObject(array, n2, forkJoinTask, null)) {
                    this.base = n + 1;
                    return (ForkJoinTask<?>)forkJoinTask;
                }
            }
            return null;
        }
        
        final ForkJoinTask<?> poll() {
            int base;
            ForkJoinTask<?>[] array;
            while ((base = this.base) - this.top < 0 && (array = this.array) != null) {
                final int n = ((array.length - 1 & base) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final ForkJoinTask forkJoinTask = (ForkJoinTask)WorkQueue.U.getObjectVolatile(array, n);
                if (this.base == base) {
                    if (forkJoinTask != null) {
                        if (WorkQueue.U.compareAndSwapObject(array, n, forkJoinTask, null)) {
                            this.base = base + 1;
                            return (ForkJoinTask<?>)forkJoinTask;
                        }
                        continue;
                    }
                    else {
                        if (base + 1 == this.top) {
                            break;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        
        final ForkJoinTask<?> nextLocalTask() {
            return ((this.config & 0x10000) == 0x0) ? this.pop() : this.poll();
        }
        
        final ForkJoinTask<?> peek() {
            final ForkJoinTask<?>[] array = this.array;
            final int n;
            if (array == null || (n = array.length - 1) < 0) {
                return null;
            }
            return (ForkJoinTask<?>)WorkQueue.U.getObjectVolatile(array, (((((this.config & 0x10000) == 0x0) ? (this.top - 1) : this.base) & n) << WorkQueue.ASHIFT) + WorkQueue.ABASE);
        }
        
        final boolean tryUnpush(final ForkJoinTask<?> forkJoinTask) {
            final ForkJoinTask<?>[] array;
            int top;
            if ((array = this.array) != null && (top = this.top) != this.base && WorkQueue.U.compareAndSwapObject(array, ((array.length - 1 & --top) << WorkQueue.ASHIFT) + WorkQueue.ABASE, forkJoinTask, null)) {
                WorkQueue.U.putOrderedInt(this, WorkQueue.QTOP, top);
                return true;
            }
            return false;
        }
        
        final void cancelAll() {
            final ForkJoinTask<?> currentJoin;
            if ((currentJoin = this.currentJoin) != null) {
                this.currentJoin = null;
                ForkJoinTask.cancelIgnoringExceptions(currentJoin);
            }
            final ForkJoinTask<?> currentSteal;
            if ((currentSteal = this.currentSteal) != null) {
                this.currentSteal = null;
                ForkJoinTask.cancelIgnoringExceptions(currentSteal);
            }
            ForkJoinTask<?> poll;
            while ((poll = this.poll()) != null) {
                ForkJoinTask.cancelIgnoringExceptions(poll);
            }
        }
        
        final void pollAndExecAll() {
            ForkJoinTask<?> poll;
            while ((poll = this.poll()) != null) {
                poll.doExec();
            }
        }
        
        final void execLocalTasks() {
            final int base = this.base;
            final ForkJoinTask<?>[] array = this.array;
            int n;
            final int n2;
            if (base - (n = this.top - 1) <= 0 && array != null && (n2 = array.length - 1) >= 0) {
                if ((this.config & 0x10000) == 0x0) {
                    ForkJoinTask forkJoinTask;
                    while ((forkJoinTask = (ForkJoinTask)WorkQueue.U.getAndSetObject(array, ((n2 & n) << WorkQueue.ASHIFT) + WorkQueue.ABASE, null)) != null) {
                        WorkQueue.U.putOrderedInt(this, WorkQueue.QTOP, n);
                        forkJoinTask.doExec();
                        if (this.base - (n = this.top - 1) > 0) {
                            return;
                        }
                    }
                    return;
                }
                this.pollAndExecAll();
            }
        }
        
        final void runTask(final ForkJoinTask<?> currentSteal) {
            if (currentSteal != null) {
                this.scanState &= 0xFFFFFFFE;
                (this.currentSteal = currentSteal).doExec();
                WorkQueue.U.putOrderedObject(this, WorkQueue.QCURRENTSTEAL, null);
                this.execLocalTasks();
                final ForkJoinWorkerThread owner = this.owner;
                if (++this.nsteals < 0) {
                    this.transferStealCount(this.pool);
                }
                this.scanState |= 0x1;
                if (owner != null) {
                    owner.afterTopLevelExec();
                }
            }
        }
        
        final void transferStealCount(final ForkJoinPool forkJoinPool) {
            final AtomicLong stealCounter;
            if (forkJoinPool != null && (stealCounter = forkJoinPool.stealCounter) != null) {
                final int nsteals = this.nsteals;
                this.nsteals = 0;
                stealCounter.getAndAdd((nsteals < 0) ? Integer.MAX_VALUE : nsteals);
            }
        }
        
        final boolean tryRemoveAndExec(final ForkJoinTask<?> forkJoinTask) {
            final ForkJoinTask<?>[] array;
            final int n;
            if ((array = this.array) != null && (n = array.length - 1) >= 0 && forkJoinTask != null) {
                int top;
                int base;
                int n2;
                while ((n2 = (top = this.top) - (base = this.base)) > 0) {
                    while (true) {
                        final long n3 = ((--top & n) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                        final ForkJoinTask forkJoinTask2;
                        if ((forkJoinTask2 = (ForkJoinTask)WorkQueue.U.getObject(array, n3)) == null) {
                            return top + 1 == this.top;
                        }
                        if (forkJoinTask2 == forkJoinTask) {
                            boolean compareAndSwapObject = false;
                            if (top + 1 == this.top) {
                                if (WorkQueue.U.compareAndSwapObject(array, n3, forkJoinTask, null)) {
                                    WorkQueue.U.putOrderedInt(this, WorkQueue.QTOP, top);
                                    compareAndSwapObject = true;
                                }
                            }
                            else if (this.base == base) {
                                compareAndSwapObject = WorkQueue.U.compareAndSwapObject(array, n3, forkJoinTask, new EmptyTask());
                            }
                            if (compareAndSwapObject) {
                                forkJoinTask.doExec();
                                break;
                            }
                            break;
                        }
                        else if (forkJoinTask2.status < 0 && top + 1 == this.top) {
                            if (WorkQueue.U.compareAndSwapObject(array, n3, forkJoinTask2, null)) {
                                WorkQueue.U.putOrderedInt(this, WorkQueue.QTOP, top);
                                break;
                            }
                            break;
                        }
                        else {
                            if (--n2 == 0) {
                                return false;
                            }
                            continue;
                        }
                    }
                    if (forkJoinTask.status < 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        
        final CountedCompleter<?> popCC(final CountedCompleter<?> countedCompleter, final int n) {
            final int top;
            final ForkJoinTask<?>[] array;
            if (this.base - (top = this.top) < 0 && (array = this.array) != null) {
                final long n2 = ((array.length - 1 & top - 1) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final Object objectVolatile;
                if ((objectVolatile = WorkQueue.U.getObjectVolatile(array, n2)) != null && objectVolatile instanceof CountedCompleter) {
                    CountedCompleter<?> completer;
                    final CountedCompleter<?> countedCompleter2 = completer = (CountedCompleter<?>)objectVolatile;
                    while (completer != countedCompleter) {
                        if ((completer = completer.completer) == null) {
                            return null;
                        }
                    }
                    if (n < 0) {
                        if (WorkQueue.U.compareAndSwapInt(this, WorkQueue.QLOCK, 0, 1)) {
                            if (this.top == top && this.array == array && WorkQueue.U.compareAndSwapObject(array, n2, countedCompleter2, null)) {
                                WorkQueue.U.putOrderedInt(this, WorkQueue.QTOP, top - 1);
                                WorkQueue.U.putOrderedInt(this, WorkQueue.QLOCK, 0);
                                return countedCompleter2;
                            }
                            WorkQueue.U.compareAndSwapInt(this, WorkQueue.QLOCK, 1, 0);
                        }
                    }
                    else if (WorkQueue.U.compareAndSwapObject(array, n2, countedCompleter2, null)) {
                        WorkQueue.U.putOrderedInt(this, WorkQueue.QTOP, top - 1);
                        return countedCompleter2;
                    }
                }
            }
            return null;
        }
        
        final int pollAndExecCC(final CountedCompleter<?> countedCompleter) {
            final int base;
            final ForkJoinTask<?>[] array;
            int n;
            if ((base = this.base) - this.top >= 0 || (array = this.array) == null) {
                n = (base | Integer.MIN_VALUE);
            }
            else {
                final long n2 = ((array.length - 1 & base) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final Object objectVolatile;
                if ((objectVolatile = WorkQueue.U.getObjectVolatile(array, n2)) == null) {
                    n = 2;
                }
                else if (!(objectVolatile instanceof CountedCompleter)) {
                    n = -1;
                }
                else {
                    CountedCompleter<?> completer;
                    final CountedCompleter<?> countedCompleter2 = completer = (CountedCompleter<?>)objectVolatile;
                    while (completer != countedCompleter) {
                        if ((completer = completer.completer) == null) {
                            n = -1;
                            return n;
                        }
                    }
                    if (this.base == base && WorkQueue.U.compareAndSwapObject(array, n2, countedCompleter2, null)) {
                        this.base = base + 1;
                        countedCompleter2.doExec();
                        n = 1;
                    }
                    else {
                        n = 2;
                    }
                }
            }
            return n;
        }
        
        final boolean isApparentlyUnblocked() {
            final ForkJoinWorkerThread owner;
            final Thread.State state;
            return this.scanState >= 0 && (owner = this.owner) != null && (state = owner.getState()) != Thread.State.BLOCKED && state != Thread.State.WAITING && state != Thread.State.TIMED_WAITING;
        }
        
        static {
            try {
                U = Unsafe.getUnsafe();
                final Class<WorkQueue> clazz = WorkQueue.class;
                final Class<ForkJoinTask[]> clazz2 = ForkJoinTask[].class;
                QTOP = WorkQueue.U.objectFieldOffset(clazz.getDeclaredField("top"));
                QLOCK = WorkQueue.U.objectFieldOffset(clazz.getDeclaredField("qlock"));
                QCURRENTSTEAL = WorkQueue.U.objectFieldOffset(clazz.getDeclaredField("currentSteal"));
                ABASE = WorkQueue.U.arrayBaseOffset(clazz2);
                final int arrayIndexScale = WorkQueue.U.arrayIndexScale(clazz2);
                if ((arrayIndexScale & arrayIndexScale - 1) != 0x0) {
                    throw new Error("data type scale not a power of two");
                }
                ASHIFT = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }
    
    public interface ManagedBlocker
    {
        boolean block() throws InterruptedException;
        
        boolean isReleasable();
    }
}
