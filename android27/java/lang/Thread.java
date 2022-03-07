package java.lang;

import sun.nio.ch.*;
import sun.security.util.*;
import sun.reflect.*;
import java.util.*;
import java.security.*;
import sun.misc.*;
import java.lang.ref.*;
import java.util.concurrent.*;

public class Thread implements Runnable
{
    private volatile String name;
    private int priority;
    private Thread threadQ;
    private long eetop;
    private boolean single_step;
    private boolean daemon;
    private boolean stillborn;
    private Runnable target;
    private ThreadGroup group;
    private ClassLoader contextClassLoader;
    private AccessControlContext inheritedAccessControlContext;
    private static int threadInitNumber;
    ThreadLocal.ThreadLocalMap threadLocals;
    ThreadLocal.ThreadLocalMap inheritableThreadLocals;
    private long stackSize;
    private long nativeParkEventPointer;
    private long tid;
    private static long threadSeqNumber;
    private volatile int threadStatus;
    volatile Object parkBlocker;
    private volatile Interruptible blocker;
    private final Object blockerLock;
    public static final int MIN_PRIORITY = 1;
    public static final int NORM_PRIORITY = 5;
    public static final int MAX_PRIORITY = 10;
    private static final StackTraceElement[] EMPTY_STACK_TRACE;
    private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION;
    private volatile UncaughtExceptionHandler uncaughtExceptionHandler;
    private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    @Contended("tlr")
    long threadLocalRandomSeed;
    @Contended("tlr")
    int threadLocalRandomProbe;
    @Contended("tlr")
    int threadLocalRandomSecondarySeed;
    
    private static native void registerNatives();
    
    private static synchronized int nextThreadNum() {
        return Thread.threadInitNumber++;
    }
    
    private static synchronized long nextThreadID() {
        return ++Thread.threadSeqNumber;
    }
    
    void blockedOn(final Interruptible blocker) {
        synchronized (this.blockerLock) {
            this.blocker = blocker;
        }
    }
    
    public static native Thread currentThread();
    
    public static native void yield();
    
    public static native void sleep(final long p0) throws InterruptedException;
    
    public static void sleep(long n, final int n2) throws InterruptedException {
        if (n < 0L) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (n2 < 0 || n2 > 999999) {
            throw new IllegalArgumentException("nanosecond timeout value out of range");
        }
        if (n2 >= 500000 || (n2 != 0 && n == 0L)) {
            ++n;
        }
        sleep(n);
    }
    
    private void init(final ThreadGroup threadGroup, final Runnable runnable, final String s, final long n) {
        this.init(threadGroup, runnable, s, n, null, true);
    }
    
    private void init(ThreadGroup group, final Runnable target, final String name, final long stackSize, final AccessControlContext accessControlContext, final boolean b) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        this.name = name;
        final Thread currentThread = currentThread();
        final SecurityManager securityManager = System.getSecurityManager();
        if (group == null) {
            if (securityManager != null) {
                group = securityManager.getThreadGroup();
            }
            if (group == null) {
                group = currentThread.getThreadGroup();
            }
        }
        group.checkAccess();
        if (securityManager != null && isCCLOverridden(this.getClass())) {
            securityManager.checkPermission(Thread.SUBCLASS_IMPLEMENTATION_PERMISSION);
        }
        group.addUnstarted();
        this.group = group;
        this.daemon = currentThread.isDaemon();
        this.priority = currentThread.getPriority();
        if (securityManager == null || isCCLOverridden(currentThread.getClass())) {
            this.contextClassLoader = currentThread.getContextClassLoader();
        }
        else {
            this.contextClassLoader = currentThread.contextClassLoader;
        }
        this.inheritedAccessControlContext = ((accessControlContext != null) ? accessControlContext : AccessController.getContext());
        this.target = target;
        this.setPriority(this.priority);
        if (b && currentThread.inheritableThreadLocals != null) {
            this.inheritableThreadLocals = ThreadLocal.createInheritedMap(currentThread.inheritableThreadLocals);
        }
        this.stackSize = stackSize;
        this.tid = nextThreadID();
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    public Thread() {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(null, null, "Thread-" + nextThreadNum(), 0L);
    }
    
    public Thread(final Runnable runnable) {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(null, runnable, "Thread-" + nextThreadNum(), 0L);
    }
    
    Thread(final Runnable runnable, final AccessControlContext accessControlContext) {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(null, runnable, "Thread-" + nextThreadNum(), 0L, accessControlContext, false);
    }
    
    public Thread(final ThreadGroup threadGroup, final Runnable runnable) {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(threadGroup, runnable, "Thread-" + nextThreadNum(), 0L);
    }
    
    public Thread(final String s) {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(null, null, s, 0L);
    }
    
    public Thread(final ThreadGroup threadGroup, final String s) {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(threadGroup, null, s, 0L);
    }
    
    public Thread(final Runnable runnable, final String s) {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(null, runnable, s, 0L);
    }
    
    public Thread(final ThreadGroup threadGroup, final Runnable runnable, final String s) {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(threadGroup, runnable, s, 0L);
    }
    
    public Thread(final ThreadGroup threadGroup, final Runnable runnable, final String s, final long n) {
        this.daemon = false;
        this.stillborn = false;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.threadStatus = 0;
        this.blockerLock = new Object();
        this.init(threadGroup, runnable, s, n);
    }
    
    public synchronized void start() {
        if (this.threadStatus != 0) {
            throw new IllegalThreadStateException();
        }
        this.group.add(this);
        boolean b = false;
        try {
            this.start0();
            b = true;
        }
        finally {
            try {
                if (!b) {
                    this.group.threadStartFailed(this);
                }
            }
            catch (Throwable t) {}
        }
    }
    
    private native void start0();
    
    @Override
    public void run() {
        if (this.target != null) {
            this.target.run();
        }
    }
    
    private void exit() {
        if (this.group != null) {
            this.group.threadTerminated(this);
            this.group = null;
        }
        this.target = null;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.inheritedAccessControlContext = null;
        this.blocker = null;
        this.uncaughtExceptionHandler = null;
    }
    
    @Deprecated
    public final void stop() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            this.checkAccess();
            if (this != currentThread()) {
                securityManager.checkPermission(SecurityConstants.STOP_THREAD_PERMISSION);
            }
        }
        if (this.threadStatus != 0) {
            this.resume();
        }
        this.stop0(new ThreadDeath());
    }
    
    @Deprecated
    public final synchronized void stop(final Throwable t) {
        throw new UnsupportedOperationException();
    }
    
    public void interrupt() {
        if (this != currentThread()) {
            this.checkAccess();
        }
        synchronized (this.blockerLock) {
            final Interruptible blocker = this.blocker;
            if (blocker != null) {
                this.interrupt0();
                blocker.interrupt(this);
                return;
            }
        }
        this.interrupt0();
    }
    
    public static boolean interrupted() {
        return currentThread().isInterrupted(true);
    }
    
    public boolean isInterrupted() {
        return this.isInterrupted(false);
    }
    
    private native boolean isInterrupted(final boolean p0);
    
    @Deprecated
    public void destroy() {
        throw new NoSuchMethodError();
    }
    
    public final native boolean isAlive();
    
    @Deprecated
    public final void suspend() {
        this.checkAccess();
        this.suspend0();
    }
    
    @Deprecated
    public final void resume() {
        this.checkAccess();
        this.resume0();
    }
    
    public final void setPriority(int maxPriority) {
        this.checkAccess();
        if (maxPriority > 10 || maxPriority < 1) {
            throw new IllegalArgumentException();
        }
        final ThreadGroup threadGroup;
        if ((threadGroup = this.getThreadGroup()) != null) {
            if (maxPriority > threadGroup.getMaxPriority()) {
                maxPriority = threadGroup.getMaxPriority();
            }
            this.setPriority0(this.priority = maxPriority);
        }
    }
    
    public final int getPriority() {
        return this.priority;
    }
    
    public final synchronized void setName(final String s) {
        this.checkAccess();
        if (s == null) {
            throw new NullPointerException("name cannot be null");
        }
        this.name = s;
        if (this.threadStatus != 0) {
            this.setNativeName(s);
        }
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final ThreadGroup getThreadGroup() {
        return this.group;
    }
    
    public static int activeCount() {
        return currentThread().getThreadGroup().activeCount();
    }
    
    public static int enumerate(final Thread[] array) {
        return currentThread().getThreadGroup().enumerate(array);
    }
    
    @Deprecated
    public native int countStackFrames();
    
    public final synchronized void join(final long n) throws InterruptedException {
        final long currentTimeMillis = System.currentTimeMillis();
        long n2 = 0L;
        if (n < 0L) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (n == 0L) {
            while (this.isAlive()) {
                this.wait(0L);
            }
        }
        else {
            while (this.isAlive()) {
                final long n3 = n - n2;
                if (n3 <= 0L) {
                    break;
                }
                this.wait(n3);
                n2 = System.currentTimeMillis() - currentTimeMillis;
            }
        }
    }
    
    public final synchronized void join(long n, final int n2) throws InterruptedException {
        if (n < 0L) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (n2 < 0 || n2 > 999999) {
            throw new IllegalArgumentException("nanosecond timeout value out of range");
        }
        if (n2 >= 500000 || (n2 != 0 && n == 0L)) {
            ++n;
        }
        this.join(n);
    }
    
    public final void join() throws InterruptedException {
        this.join(0L);
    }
    
    public static void dumpStack() {
        new Exception("Stack trace").printStackTrace();
    }
    
    public final void setDaemon(final boolean daemon) {
        this.checkAccess();
        if (this.isAlive()) {
            throw new IllegalThreadStateException();
        }
        this.daemon = daemon;
    }
    
    public final boolean isDaemon() {
        return this.daemon;
    }
    
    public final void checkAccess() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkAccess(this);
        }
    }
    
    @Override
    public String toString() {
        final ThreadGroup threadGroup = this.getThreadGroup();
        if (threadGroup != null) {
            return "Thread[" + this.getName() + "," + this.getPriority() + "," + threadGroup.getName() + "]";
        }
        return "Thread[" + this.getName() + "," + this.getPriority() + ",]";
    }
    
    @CallerSensitive
    public ClassLoader getContextClassLoader() {
        if (this.contextClassLoader == null) {
            return null;
        }
        if (System.getSecurityManager() != null) {
            ClassLoader.checkClassLoaderPermission(this.contextClassLoader, Reflection.getCallerClass());
        }
        return this.contextClassLoader;
    }
    
    public void setContextClassLoader(final ClassLoader contextClassLoader) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
        }
        this.contextClassLoader = contextClassLoader;
    }
    
    public static native boolean holdsLock(final Object p0);
    
    public StackTraceElement[] getStackTrace() {
        if (this == currentThread()) {
            return new Exception().getStackTrace();
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
        }
        if (!this.isAlive()) {
            return Thread.EMPTY_STACK_TRACE;
        }
        StackTraceElement[] empty_STACK_TRACE = dumpThreads(new Thread[] { this })[0];
        if (empty_STACK_TRACE == null) {
            empty_STACK_TRACE = Thread.EMPTY_STACK_TRACE;
        }
        return empty_STACK_TRACE;
    }
    
    public static Map<Thread, StackTraceElement[]> getAllStackTraces() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
            securityManager.checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
        }
        final Thread[] threads = getThreads();
        final StackTraceElement[][] dumpThreads = dumpThreads(threads);
        final HashMap hashMap = new HashMap<Thread, StackTraceElement[]>(threads.length);
        for (int i = 0; i < threads.length; ++i) {
            final StackTraceElement[] array = dumpThreads[i];
            if (array != null) {
                hashMap.put(threads[i], array);
            }
        }
        return (Map<Thread, StackTraceElement[]>)hashMap;
    }
    
    private static boolean isCCLOverridden(final Class<?> clazz) {
        if (clazz == Thread.class) {
            return false;
        }
        processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
        final WeakClassKey weakClassKey = new WeakClassKey(clazz, Caches.subclassAuditsQueue);
        Boolean value = Caches.subclassAudits.get(weakClassKey);
        if (value == null) {
            value = auditSubclass(clazz);
            Caches.subclassAudits.putIfAbsent(weakClassKey, value);
        }
        return value;
    }
    
    private static boolean auditSubclass(final Class<?> clazz) {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                Class<? super Thread> clazz = (Class<? super Thread>)clazz;
                while (clazz != Thread.class) {
                    try {
                        clazz.getDeclaredMethod("getContextClassLoader", (Class<?>[])new Class[0]);
                        return Boolean.TRUE;
                    }
                    catch (NoSuchMethodException ex) {
                        try {
                            clazz.getDeclaredMethod("setContextClassLoader", ClassLoader.class);
                            return Boolean.TRUE;
                        }
                        catch (NoSuchMethodException ex2) {
                            clazz = clazz.getSuperclass();
                        }
                    }
                    break;
                }
                return Boolean.FALSE;
            }
        });
    }
    
    private static native StackTraceElement[][] dumpThreads(final Thread[] p0);
    
    private static native Thread[] getThreads();
    
    public long getId() {
        return this.tid;
    }
    
    public State getState() {
        return VM.toThreadState(this.threadStatus);
    }
    
    public static void setDefaultUncaughtExceptionHandler(final UncaughtExceptionHandler defaultUncaughtExceptionHandler) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("setDefaultUncaughtExceptionHandler"));
        }
        Thread.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
    }
    
    public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
        return Thread.defaultUncaughtExceptionHandler;
    }
    
    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return (this.uncaughtExceptionHandler != null) ? this.uncaughtExceptionHandler : this.group;
    }
    
    public void setUncaughtExceptionHandler(final UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.checkAccess();
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }
    
    private void dispatchUncaughtException(final Throwable t) {
        this.getUncaughtExceptionHandler().uncaughtException(this, t);
    }
    
    static void processQueue(final ReferenceQueue<Class<?>> referenceQueue, final ConcurrentMap<? extends WeakReference<Class<?>>, ?> concurrentMap) {
        Reference<? extends Class<?>> poll;
        while ((poll = referenceQueue.poll()) != null) {
            concurrentMap.remove(poll);
        }
    }
    
    private native void setPriority0(final int p0);
    
    private native void stop0(final Object p0);
    
    private native void suspend0();
    
    private native void resume0();
    
    private native void interrupt0();
    
    private native void setNativeName(final String p0);
    
    static {
        registerNatives();
        EMPTY_STACK_TRACE = new StackTraceElement[0];
        SUBCLASS_IMPLEMENTATION_PERMISSION = new RuntimePermission("enableContextClassLoaderOverride");
    }
    
    private static class Caches
    {
        static final ConcurrentMap<WeakClassKey, Boolean> subclassAudits;
        static final ReferenceQueue<Class<?>> subclassAuditsQueue;
        
        static {
            subclassAudits = new ConcurrentHashMap<WeakClassKey, Boolean>();
            subclassAuditsQueue = new ReferenceQueue<Class<?>>();
        }
    }
    
    static class WeakClassKey extends WeakReference<Class<?>>
    {
        private final int hash;
        
        WeakClassKey(final Class<?> clazz, final ReferenceQueue<Class<?>> referenceQueue) {
            super(clazz, referenceQueue);
            this.hash = System.identityHashCode(clazz);
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof WeakClassKey) {
                final Object value = ((Reference<Object>)this).get();
                return value != null && value == ((Reference<Object>)o).get();
            }
            return false;
        }
    }
    
    public enum State
    {
        NEW, 
        RUNNABLE, 
        BLOCKED, 
        WAITING, 
        TIMED_WAITING, 
        TERMINATED;
    }
    
    @FunctionalInterface
    public interface UncaughtExceptionHandler
    {
        void uncaughtException(final Thread p0, final Throwable p1);
    }
}
