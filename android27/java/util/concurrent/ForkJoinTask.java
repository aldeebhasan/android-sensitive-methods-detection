package java.util.concurrent;

import java.util.concurrent.locks.*;
import sun.misc.*;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;
import java.lang.ref.*;

public abstract class ForkJoinTask<V> implements Future<V>, Serializable
{
    volatile int status;
    static final int DONE_MASK = -268435456;
    static final int NORMAL = -268435456;
    static final int CANCELLED = -1073741824;
    static final int EXCEPTIONAL = Integer.MIN_VALUE;
    static final int SIGNAL = 65536;
    static final int SMASK = 65535;
    private static final ExceptionNode[] exceptionTable;
    private static final ReentrantLock exceptionTableLock;
    private static final ReferenceQueue<Object> exceptionTableRefQueue;
    private static final int EXCEPTION_MAP_CAPACITY = 32;
    private static final long serialVersionUID = -7721805057305804111L;
    private static final Unsafe U;
    private static final long STATUS;
    
    private int setCompletion(final int n) {
        int status;
        while ((status = this.status) >= 0) {
            if (ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status, status | n)) {
                if (status >>> 16 != 0) {
                    synchronized (this) {
                        this.notifyAll();
                    }
                }
                return n;
            }
        }
        return status;
    }
    
    final int doExec() {
        int n;
        if ((n = this.status) >= 0) {
            boolean exec;
            try {
                exec = this.exec();
            }
            catch (Throwable exceptionalCompletion) {
                return this.setExceptionalCompletion(exceptionalCompletion);
            }
            if (exec) {
                n = this.setCompletion(-268435456);
            }
        }
        return n;
    }
    
    final void internalWait(final long n) {
        final int status;
        if ((status = this.status) >= 0 && ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status, status | 0x10000)) {
            synchronized (this) {
                if (this.status >= 0) {
                    try {
                        this.wait(n);
                    }
                    catch (InterruptedException ex) {}
                }
                else {
                    this.notifyAll();
                }
            }
        }
    }
    
    private int externalAwaitDone() {
        int n = (this instanceof CountedCompleter) ? ForkJoinPool.common.externalHelpComplete((CountedCompleter<?>)this, 0) : (ForkJoinPool.common.tryExternalUnpush(this) ? this.doExec() : 0);
        if (n >= 0 && (n = this.status) >= 0) {
            boolean b = false;
            do {
                if (ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, n, n | 0x10000)) {
                    synchronized (this) {
                        if (this.status >= 0) {
                            try {
                                this.wait(0L);
                            }
                            catch (InterruptedException ex) {
                                b = true;
                            }
                        }
                        else {
                            this.notifyAll();
                        }
                    }
                }
            } while ((n = this.status) >= 0);
            if (b) {
                Thread.currentThread().interrupt();
            }
        }
        return n;
    }
    
    private int externalInterruptibleAwaitDone() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        int n;
        if ((n = this.status) >= 0) {
            int n3;
            final int n2 = (this instanceof CountedCompleter) ? (n3 = ForkJoinPool.common.externalHelpComplete((CountedCompleter<?>)this, 0)) : (ForkJoinPool.common.tryExternalUnpush(this) ? (n3 = this.doExec()) : (n3 = 0));
            n = n3;
            if (n2 >= 0) {
                while ((n = this.status) >= 0) {
                    if (ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, n, n | 0x10000)) {
                        synchronized (this) {
                            if (this.status >= 0) {
                                this.wait(0L);
                            }
                            else {
                                this.notifyAll();
                            }
                        }
                    }
                }
            }
        }
        return n;
    }
    
    private int doJoin() {
        final int status;
        final ForkJoinWorkerThread currentThread;
        final ForkJoinWorkerThread forkJoinWorkerThread;
        final ForkJoinPool.WorkQueue workQueue;
        final int doExec;
        return ((status = this.status) < 0) ? status : (((currentThread = (ForkJoinWorkerThread)Thread.currentThread()) instanceof ForkJoinWorkerThread) ? (((workQueue = (forkJoinWorkerThread = currentThread).workQueue).tryUnpush(this) && (doExec = this.doExec()) < 0) ? doExec : forkJoinWorkerThread.pool.awaitJoin(workQueue, this, 0L)) : this.externalAwaitDone());
    }
    
    private int doInvoke() {
        final int doExec;
        int n;
        if ((doExec = this.doExec()) < 0) {
            n = doExec;
        }
        else {
            final Thread currentThread;
            if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
                final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
                n = forkJoinWorkerThread.pool.awaitJoin(forkJoinWorkerThread.workQueue, this, 0L);
            }
            else {
                n = this.externalAwaitDone();
            }
        }
        return n;
    }
    
    final int recordExceptionalCompletion(final Throwable t) {
        int n;
        if ((n = this.status) >= 0) {
            final int identityHashCode = System.identityHashCode(this);
            final ReentrantLock exceptionTableLock = ForkJoinTask.exceptionTableLock;
            exceptionTableLock.lock();
            Label_0115: {
                try {
                    expungeStaleExceptions();
                    final ExceptionNode[] exceptionTable = ForkJoinTask.exceptionTable;
                    final int n2 = identityHashCode & exceptionTable.length - 1;
                    for (ExceptionNode next = exceptionTable[n2]; next != null; next = next.next) {
                        if (next.get() == this) {
                            break Label_0115;
                        }
                    }
                    exceptionTable[n2] = new ExceptionNode(this, t, exceptionTable[n2]);
                }
                finally {
                    exceptionTableLock.unlock();
                }
            }
            n = this.setCompletion(Integer.MIN_VALUE);
        }
        return n;
    }
    
    private int setExceptionalCompletion(final Throwable t) {
        final int recordExceptionalCompletion = this.recordExceptionalCompletion(t);
        if ((recordExceptionalCompletion & 0xF0000000) == Integer.MIN_VALUE) {
            this.internalPropagateException(t);
        }
        return recordExceptionalCompletion;
    }
    
    void internalPropagateException(final Throwable t) {
    }
    
    static final void cancelIgnoringExceptions(final ForkJoinTask<?> forkJoinTask) {
        if (forkJoinTask != null && forkJoinTask.status >= 0) {
            try {
                forkJoinTask.cancel(false);
            }
            catch (Throwable t) {}
        }
    }
    
    private void clearExceptionalCompletion() {
        final int identityHashCode = System.identityHashCode(this);
        final ReentrantLock exceptionTableLock = ForkJoinTask.exceptionTableLock;
        exceptionTableLock.lock();
        try {
            final ExceptionNode[] exceptionTable = ForkJoinTask.exceptionTable;
            final int n = identityHashCode & exceptionTable.length - 1;
            ExceptionNode exceptionNode = exceptionTable[n];
            ExceptionNode exceptionNode2 = null;
            while (exceptionNode != null) {
                final ExceptionNode next = exceptionNode.next;
                if (exceptionNode.get() == this) {
                    if (exceptionNode2 == null) {
                        exceptionTable[n] = next;
                        break;
                    }
                    exceptionNode2.next = next;
                    break;
                }
                else {
                    exceptionNode2 = exceptionNode;
                    exceptionNode = next;
                }
            }
            expungeStaleExceptions();
            this.status = 0;
        }
        finally {
            exceptionTableLock.unlock();
        }
    }
    
    private Throwable getThrowableException() {
        if ((this.status & 0xF0000000) != Integer.MIN_VALUE) {
            return null;
        }
        final int identityHashCode = System.identityHashCode(this);
        final ReentrantLock exceptionTableLock = ForkJoinTask.exceptionTableLock;
        exceptionTableLock.lock();
        ExceptionNode next;
        try {
            expungeStaleExceptions();
            final ExceptionNode[] exceptionTable = ForkJoinTask.exceptionTable;
            for (next = exceptionTable[identityHashCode & exceptionTable.length - 1]; next != null && next.get() != this; next = next.next) {}
        }
        finally {
            exceptionTableLock.unlock();
        }
        final Throwable ex;
        if (next == null || (ex = next.ex) == null) {
            return null;
        }
        if (next.thrower != Thread.currentThread().getId()) {
            final Class<? extends Throwable> class1 = ex.getClass();
            try {
                Constructor<Throwable> constructor = null;
                final Constructor<?>[] constructors = class1.getConstructors();
                for (int i = 0; i < constructors.length; ++i) {
                    final Constructor<?> constructor2 = constructors[i];
                    final Class[] parameterTypes = constructor2.getParameterTypes();
                    if (parameterTypes.length == 0) {
                        constructor = (Constructor<Throwable>)constructor2;
                    }
                    else if (parameterTypes.length == 1 && parameterTypes[0] == Throwable.class) {
                        final Throwable t = constructor2.newInstance(ex);
                        return (t == null) ? ex : t;
                    }
                }
                if (constructor != null) {
                    final Throwable t2 = constructor.newInstance(new Object[0]);
                    if (t2 != null) {
                        t2.initCause(ex);
                        return t2;
                    }
                }
            }
            catch (Exception ex2) {}
        }
        return ex;
    }
    
    private static void expungeStaleExceptions() {
        Reference<?> poll;
        while ((poll = ForkJoinTask.exceptionTableRefQueue.poll()) != null) {
            if (poll instanceof ExceptionNode) {
                final int hashCode = ((ExceptionNode)poll).hashCode;
                final ExceptionNode[] exceptionTable = ForkJoinTask.exceptionTable;
                final int n = hashCode & exceptionTable.length - 1;
                ExceptionNode exceptionNode = exceptionTable[n];
                ExceptionNode exceptionNode2 = null;
                while (exceptionNode != null) {
                    final ExceptionNode next = exceptionNode.next;
                    if (exceptionNode == poll) {
                        if (exceptionNode2 == null) {
                            exceptionTable[n] = next;
                            break;
                        }
                        exceptionNode2.next = next;
                        break;
                    }
                    else {
                        exceptionNode2 = exceptionNode;
                        exceptionNode = next;
                    }
                }
            }
        }
    }
    
    static final void helpExpungeStaleExceptions() {
        final ReentrantLock exceptionTableLock = ForkJoinTask.exceptionTableLock;
        if (exceptionTableLock.tryLock()) {
            try {
                expungeStaleExceptions();
            }
            finally {
                exceptionTableLock.unlock();
            }
        }
    }
    
    static void rethrow(final Throwable t) {
        if (t != null) {
            uncheckedThrow(t);
        }
    }
    
    static <T extends Throwable> void uncheckedThrow(final Throwable t) throws T, Throwable {
        throw t;
    }
    
    private void reportException(final int n) {
        if (n == -1073741824) {
            throw new CancellationException();
        }
        if (n == Integer.MIN_VALUE) {
            rethrow(this.getThrowableException());
        }
    }
    
    public final ForkJoinTask<V> fork() {
        final Thread currentThread;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            ((ForkJoinWorkerThread)currentThread).workQueue.push(this);
        }
        else {
            ForkJoinPool.common.externalPush(this);
        }
        return this;
    }
    
    public final V join() {
        final int n;
        if ((n = (this.doJoin() & 0xF0000000)) != -268435456) {
            this.reportException(n);
        }
        return this.getRawResult();
    }
    
    public final V invoke() {
        final int n;
        if ((n = (this.doInvoke() & 0xF0000000)) != -268435456) {
            this.reportException(n);
        }
        return this.getRawResult();
    }
    
    public static void invokeAll(final ForkJoinTask<?> forkJoinTask, final ForkJoinTask<?> forkJoinTask2) {
        forkJoinTask2.fork();
        final int n;
        if ((n = (forkJoinTask.doInvoke() & 0xF0000000)) != -268435456) {
            forkJoinTask.reportException(n);
        }
        final int n2;
        if ((n2 = (forkJoinTask2.doJoin() & 0xF0000000)) != -268435456) {
            forkJoinTask2.reportException(n2);
        }
    }
    
    public static void invokeAll(final ForkJoinTask<?>... array) {
        Throwable t = null;
        int i;
        int n;
        for (n = (i = array.length - 1); i >= 0; --i) {
            final ForkJoinTask<?> forkJoinTask = array[i];
            if (forkJoinTask == null) {
                if (t == null) {
                    t = new NullPointerException();
                }
            }
            else if (i != 0) {
                forkJoinTask.fork();
            }
            else if (forkJoinTask.doInvoke() < -268435456 && t == null) {
                t = forkJoinTask.getException();
            }
        }
        for (int j = 1; j <= n; ++j) {
            final ForkJoinTask<?> forkJoinTask2 = array[j];
            if (forkJoinTask2 != null) {
                if (t != null) {
                    forkJoinTask2.cancel(false);
                }
                else if (forkJoinTask2.doJoin() < -268435456) {
                    t = forkJoinTask2.getException();
                }
            }
        }
        if (t != null) {
            rethrow(t);
        }
    }
    
    public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(final Collection<T> collection) {
        if (!(collection instanceof RandomAccess) || !(collection instanceof List)) {
            invokeAll((ForkJoinTask<?>[])collection.toArray(new ForkJoinTask[collection.size()]));
            return collection;
        }
        final List<ForkJoinTask> list = (List<ForkJoinTask>)collection;
        Throwable t = null;
        int i;
        int n;
        for (n = (i = list.size() - 1); i >= 0; --i) {
            final ForkJoinTask forkJoinTask = list.get(i);
            if (forkJoinTask == null) {
                if (t == null) {
                    t = new NullPointerException();
                }
            }
            else if (i != 0) {
                forkJoinTask.fork();
            }
            else if (forkJoinTask.doInvoke() < -268435456 && t == null) {
                t = forkJoinTask.getException();
            }
        }
        for (int j = 1; j <= n; ++j) {
            final ForkJoinTask forkJoinTask2 = list.get(j);
            if (forkJoinTask2 != null) {
                if (t != null) {
                    forkJoinTask2.cancel(false);
                }
                else if (forkJoinTask2.doJoin() < -268435456) {
                    t = forkJoinTask2.getException();
                }
            }
        }
        if (t != null) {
            rethrow(t);
        }
        return collection;
    }
    
    @Override
    public boolean cancel(final boolean b) {
        return (this.setCompletion(-1073741824) & 0xF0000000) == 0xC0000000;
    }
    
    @Override
    public final boolean isDone() {
        return this.status < 0;
    }
    
    @Override
    public final boolean isCancelled() {
        return (this.status & 0xF0000000) == 0xC0000000;
    }
    
    public final boolean isCompletedAbnormally() {
        return this.status < -268435456;
    }
    
    public final boolean isCompletedNormally() {
        return (this.status & 0xF0000000) == 0xF0000000;
    }
    
    public final Throwable getException() {
        final int n = this.status & 0xF0000000;
        return (n >= -268435456) ? null : ((n == -1073741824) ? new CancellationException() : this.getThrowableException());
    }
    
    public void completeExceptionally(final Throwable t) {
        this.setExceptionalCompletion((t instanceof RuntimeException || t instanceof Error) ? t : new RuntimeException(t));
    }
    
    public void complete(final V rawResult) {
        try {
            this.setRawResult(rawResult);
        }
        catch (Throwable exceptionalCompletion) {
            this.setExceptionalCompletion(exceptionalCompletion);
            return;
        }
        this.setCompletion(-268435456);
    }
    
    public final void quietlyComplete() {
        this.setCompletion(-268435456);
    }
    
    @Override
    public final V get() throws InterruptedException, ExecutionException {
        final int n;
        if ((n = (((Thread.currentThread() instanceof ForkJoinWorkerThread) ? this.doJoin() : this.externalInterruptibleAwaitDone()) & 0xF0000000)) == -1073741824) {
            throw new CancellationException();
        }
        final Throwable throwableException;
        if (n == Integer.MIN_VALUE && (throwableException = this.getThrowableException()) != null) {
            throw new ExecutionException(throwableException);
        }
        return this.getRawResult();
    }
    
    @Override
    public final V get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        final long nanos = timeUnit.toNanos(n);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        int n2;
        if ((n2 = this.status) >= 0 && nanos > 0L) {
            final long n3 = System.nanoTime() + nanos;
            final long n4 = (n3 == 0L) ? 1L : n3;
            final Thread currentThread = Thread.currentThread();
            if (currentThread instanceof ForkJoinWorkerThread) {
                final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
                n2 = forkJoinWorkerThread.pool.awaitJoin(forkJoinWorkerThread.workQueue, this, n4);
            }
            else {
                int n6;
                final int n5 = (this instanceof CountedCompleter) ? (n6 = ForkJoinPool.common.externalHelpComplete((CountedCompleter<?>)this, 0)) : (ForkJoinPool.common.tryExternalUnpush(this) ? (n6 = this.doExec()) : (n6 = 0));
                n2 = n6;
                if (n5 >= 0) {
                    long n7;
                    while ((n2 = this.status) >= 0 && (n7 = n4 - System.nanoTime()) > 0L) {
                        final long millis;
                        if ((millis = TimeUnit.NANOSECONDS.toMillis(n7)) > 0L && ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, n2, n2 | 0x10000)) {
                            synchronized (this) {
                                if (this.status >= 0) {
                                    this.wait(millis);
                                }
                                else {
                                    this.notifyAll();
                                }
                            }
                        }
                    }
                }
            }
        }
        if (n2 >= 0) {
            n2 = this.status;
        }
        final int n8;
        if ((n8 = (n2 & 0xF0000000)) != -268435456) {
            if (n8 == -1073741824) {
                throw new CancellationException();
            }
            if (n8 != Integer.MIN_VALUE) {
                throw new TimeoutException();
            }
            final Throwable throwableException;
            if ((throwableException = this.getThrowableException()) != null) {
                throw new ExecutionException(throwableException);
            }
        }
        return this.getRawResult();
    }
    
    public final void quietlyJoin() {
        this.doJoin();
    }
    
    public final void quietlyInvoke() {
        this.doInvoke();
    }
    
    public static void helpQuiesce() {
        final Thread currentThread;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
            forkJoinWorkerThread.pool.helpQuiescePool(forkJoinWorkerThread.workQueue);
        }
        else {
            ForkJoinPool.quiesceCommonPool();
        }
    }
    
    public void reinitialize() {
        if ((this.status & 0xF0000000) == Integer.MIN_VALUE) {
            this.clearExceptionalCompletion();
        }
        else {
            this.status = 0;
        }
    }
    
    public static ForkJoinPool getPool() {
        final Thread currentThread = Thread.currentThread();
        return (currentThread instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)currentThread).pool : null;
    }
    
    public static boolean inForkJoinPool() {
        return Thread.currentThread() instanceof ForkJoinWorkerThread;
    }
    
    public boolean tryUnfork() {
        final Thread currentThread;
        return ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)currentThread).workQueue.tryUnpush(this) : ForkJoinPool.common.tryExternalUnpush(this);
    }
    
    public static int getQueuedTaskCount() {
        final Thread currentThread;
        ForkJoinPool.WorkQueue workQueue;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            workQueue = ((ForkJoinWorkerThread)currentThread).workQueue;
        }
        else {
            workQueue = ForkJoinPool.commonSubmitterQueue();
        }
        return (workQueue == null) ? 0 : workQueue.queueSize();
    }
    
    public static int getSurplusQueuedTaskCount() {
        return ForkJoinPool.getSurplusQueuedTaskCount();
    }
    
    public abstract V getRawResult();
    
    protected abstract void setRawResult(final V p0);
    
    protected abstract boolean exec();
    
    protected static ForkJoinTask<?> peekNextLocalTask() {
        final Thread currentThread;
        ForkJoinPool.WorkQueue workQueue;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            workQueue = ((ForkJoinWorkerThread)currentThread).workQueue;
        }
        else {
            workQueue = ForkJoinPool.commonSubmitterQueue();
        }
        return (workQueue == null) ? null : workQueue.peek();
    }
    
    protected static ForkJoinTask<?> pollNextLocalTask() {
        final Thread currentThread;
        return ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)currentThread).workQueue.nextLocalTask() : null;
    }
    
    protected static ForkJoinTask<?> pollTask() {
        final Thread currentThread;
        ForkJoinTask<?> nextTask;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
            nextTask = forkJoinWorkerThread.pool.nextTaskFor(forkJoinWorkerThread.workQueue);
        }
        else {
            nextTask = null;
        }
        return nextTask;
    }
    
    public final short getForkJoinTaskTag() {
        return (short)this.status;
    }
    
    public final short setForkJoinTaskTag(final short n) {
        int status;
        while (!ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status = this.status, (status & 0xFFFF0000) | (n & 0xFFFF))) {}
        return (short)status;
    }
    
    public final boolean compareAndSetForkJoinTaskTag(final short n, final short n2) {
        int status;
        while ((short)(status = this.status) == n) {
            if (ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status, (status & 0xFFFF0000) | (n2 & 0xFFFF))) {
                return true;
            }
        }
        return false;
    }
    
    public static ForkJoinTask<?> adapt(final Runnable runnable) {
        return new AdaptedRunnableAction(runnable);
    }
    
    public static <T> ForkJoinTask<T> adapt(final Runnable runnable, final T t) {
        return new AdaptedRunnable<T>(runnable, t);
    }
    
    public static <T> ForkJoinTask<T> adapt(final Callable<? extends T> callable) {
        return new AdaptedCallable<T>(callable);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.getException());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final Object object = objectInputStream.readObject();
        if (object != null) {
            this.setExceptionalCompletion((Throwable)object);
        }
    }
    
    static {
        exceptionTableLock = new ReentrantLock();
        exceptionTableRefQueue = new ReferenceQueue<Object>();
        exceptionTable = new ExceptionNode[32];
        try {
            U = Unsafe.getUnsafe();
            STATUS = ForkJoinTask.U.objectFieldOffset(ForkJoinTask.class.getDeclaredField("status"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class AdaptedCallable<T> extends ForkJoinTask<T> implements RunnableFuture<T>
    {
        final Callable<? extends T> callable;
        T result;
        private static final long serialVersionUID = 2838392045355241008L;
        
        AdaptedCallable(final Callable<? extends T> callable) {
            if (callable == null) {
                throw new NullPointerException();
            }
            this.callable = callable;
        }
        
        @Override
        public final T getRawResult() {
            return this.result;
        }
        
        public final void setRawResult(final T result) {
            this.result = result;
        }
        
        public final boolean exec() {
            try {
                this.result = (T)this.callable.call();
                return true;
            }
            catch (Error error) {
                throw error;
            }
            catch (RuntimeException ex) {
                throw ex;
            }
            catch (Exception ex2) {
                throw new RuntimeException(ex2);
            }
        }
        
        @Override
        public final void run() {
            this.invoke();
        }
    }
    
    static final class AdaptedRunnable<T> extends ForkJoinTask<T> implements RunnableFuture<T>
    {
        final Runnable runnable;
        T result;
        private static final long serialVersionUID = 5232453952276885070L;
        
        AdaptedRunnable(final Runnable runnable, final T result) {
            if (runnable == null) {
                throw new NullPointerException();
            }
            this.runnable = runnable;
            this.result = result;
        }
        
        @Override
        public final T getRawResult() {
            return this.result;
        }
        
        public final void setRawResult(final T result) {
            this.result = result;
        }
        
        public final boolean exec() {
            this.runnable.run();
            return true;
        }
        
        @Override
        public final void run() {
            this.invoke();
        }
    }
    
    static final class AdaptedRunnableAction extends ForkJoinTask<Void> implements RunnableFuture<Void>
    {
        final Runnable runnable;
        private static final long serialVersionUID = 5232453952276885070L;
        
        AdaptedRunnableAction(final Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException();
            }
            this.runnable = runnable;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
        
        public final boolean exec() {
            this.runnable.run();
            return true;
        }
        
        @Override
        public final void run() {
            this.invoke();
        }
    }
    
    static final class ExceptionNode extends WeakReference<ForkJoinTask<?>>
    {
        final Throwable ex;
        ExceptionNode next;
        final long thrower;
        final int hashCode;
        
        ExceptionNode(final ForkJoinTask<?> forkJoinTask, final Throwable ex, final ExceptionNode next) {
            super(forkJoinTask, ForkJoinTask.exceptionTableRefQueue);
            this.ex = ex;
            this.next = next;
            this.thrower = Thread.currentThread().getId();
            this.hashCode = System.identityHashCode(forkJoinTask);
        }
    }
    
    static final class RunnableExecuteAction extends ForkJoinTask<Void>
    {
        final Runnable runnable;
        private static final long serialVersionUID = 5232453952276885070L;
        
        RunnableExecuteAction(final Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException();
            }
            this.runnable = runnable;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
        
        public final boolean exec() {
            this.runnable.run();
            return true;
        }
        
        @Override
        void internalPropagateException(final Throwable t) {
            ForkJoinTask.rethrow(t);
        }
    }
}
