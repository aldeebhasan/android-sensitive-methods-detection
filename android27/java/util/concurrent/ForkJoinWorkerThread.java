package java.util.concurrent;

import sun.misc.*;
import java.security.*;

public class ForkJoinWorkerThread extends Thread
{
    final ForkJoinPool pool;
    final ForkJoinPool.WorkQueue workQueue;
    private static final AccessControlContext INNOCUOUS_ACC;
    private static final Unsafe U;
    private static final long THREADLOCALS;
    private static final long INHERITABLETHREADLOCALS;
    private static final long INHERITEDACCESSCONTROLCONTEXT;
    
    protected ForkJoinWorkerThread(final ForkJoinPool pool) {
        super("aForkJoinWorkerThread");
        this.pool = pool;
        this.workQueue = pool.registerWorker(this);
    }
    
    ForkJoinWorkerThread(final ForkJoinPool pool, final Object o) {
        super("aForkJoinWorkerThread");
        ForkJoinWorkerThread.U.putOrderedObject(this, ForkJoinWorkerThread.INHERITEDACCESSCONTROLCONTEXT, ForkJoinWorkerThread.INNOCUOUS_ACC);
        this.pool = pool;
        this.workQueue = pool.registerWorker(this);
    }
    
    ForkJoinWorkerThread(final ForkJoinPool pool, final ThreadGroup threadGroup, final AccessControlContext accessControlContext) {
        super(threadGroup, null, "aForkJoinWorkerThread");
        ForkJoinWorkerThread.U.putOrderedObject(this, ForkJoinWorkerThread.INHERITEDACCESSCONTROLCONTEXT, accessControlContext);
        this.eraseThreadLocals();
        this.pool = pool;
        this.workQueue = pool.registerWorker(this);
    }
    
    public ForkJoinPool getPool() {
        return this.pool;
    }
    
    public int getPoolIndex() {
        return this.workQueue.getPoolIndex();
    }
    
    protected void onStart() {
    }
    
    protected void onTermination(final Throwable t) {
    }
    
    @Override
    public void run() {
        if (this.workQueue.array == null) {
            Throwable t = null;
            try {
                this.onStart();
                this.pool.runWorker(this.workQueue);
            }
            catch (Throwable t2) {
                t = t2;
                try {
                    this.onTermination(t);
                }
                catch (Throwable t3) {
                    if (t == null) {
                        t = t3;
                    }
                }
                finally {
                    this.pool.deregisterWorker(this, t);
                }
            }
            finally {
                try {
                    this.onTermination(t);
                }
                catch (Throwable t4) {
                    if (t == null) {
                        t = t4;
                    }
                    this.pool.deregisterWorker(this, t);
                }
                finally {
                    this.pool.deregisterWorker(this, t);
                }
            }
        }
    }
    
    final void eraseThreadLocals() {
        ForkJoinWorkerThread.U.putObject(this, ForkJoinWorkerThread.THREADLOCALS, null);
        ForkJoinWorkerThread.U.putObject(this, ForkJoinWorkerThread.INHERITABLETHREADLOCALS, null);
    }
    
    void afterTopLevelExec() {
    }
    
    static {
        INNOCUOUS_ACC = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, null) });
        try {
            U = Unsafe.getUnsafe();
            final Class<Thread> clazz = Thread.class;
            THREADLOCALS = ForkJoinWorkerThread.U.objectFieldOffset(clazz.getDeclaredField("threadLocals"));
            INHERITABLETHREADLOCALS = ForkJoinWorkerThread.U.objectFieldOffset(clazz.getDeclaredField("inheritableThreadLocals"));
            INHERITEDACCESSCONTROLCONTEXT = ForkJoinWorkerThread.U.objectFieldOffset(clazz.getDeclaredField("inheritedAccessControlContext"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class InnocuousForkJoinWorkerThread extends ForkJoinWorkerThread
    {
        private static final ThreadGroup innocuousThreadGroup;
        
        InnocuousForkJoinWorkerThread(final ForkJoinPool forkJoinPool) {
            super(forkJoinPool, InnocuousForkJoinWorkerThread.innocuousThreadGroup, ForkJoinWorkerThread.INNOCUOUS_ACC);
        }
        
        @Override
        void afterTopLevelExec() {
            this.eraseThreadLocals();
        }
        
        @Override
        public ClassLoader getContextClassLoader() {
            return ClassLoader.getSystemClassLoader();
        }
        
        @Override
        public void setUncaughtExceptionHandler(final UncaughtExceptionHandler uncaughtExceptionHandler) {
        }
        
        @Override
        public void setContextClassLoader(final ClassLoader classLoader) {
            throw new SecurityException("setContextClassLoader");
        }
        
        private static ThreadGroup createThreadGroup() {
            try {
                final Unsafe unsafe = Unsafe.getUnsafe();
                final Class<Thread> clazz = Thread.class;
                final Class<ThreadGroup> clazz2 = ThreadGroup.class;
                final long objectFieldOffset = unsafe.objectFieldOffset(clazz.getDeclaredField("group"));
                final long objectFieldOffset2 = unsafe.objectFieldOffset(clazz2.getDeclaredField("parent"));
                ThreadGroup threadGroup2;
                for (ThreadGroup threadGroup = (ThreadGroup)unsafe.getObject(Thread.currentThread(), objectFieldOffset); threadGroup != null; threadGroup = threadGroup2) {
                    threadGroup2 = (ThreadGroup)unsafe.getObject(threadGroup, objectFieldOffset2);
                    if (threadGroup2 == null) {
                        return new ThreadGroup(threadGroup, "InnocuousForkJoinWorkerThreadGroup");
                    }
                }
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
            throw new Error("Cannot create ThreadGroup");
        }
        
        static {
            innocuousThreadGroup = createThreadGroup();
        }
    }
}
