package java.lang;

import java.util.*;
import java.io.*;
import sun.misc.*;

public class ThreadGroup implements Thread.UncaughtExceptionHandler
{
    private final ThreadGroup parent;
    String name;
    int maxPriority;
    boolean destroyed;
    boolean daemon;
    boolean vmAllowSuspension;
    int nUnstartedThreads;
    int nthreads;
    Thread[] threads;
    int ngroups;
    ThreadGroup[] groups;
    
    private ThreadGroup() {
        this.nUnstartedThreads = 0;
        this.name = "system";
        this.maxPriority = 10;
        this.parent = null;
    }
    
    public ThreadGroup(final String s) {
        this(Thread.currentThread().getThreadGroup(), s);
    }
    
    public ThreadGroup(final ThreadGroup threadGroup, final String s) {
        this(checkParentAccess(threadGroup), threadGroup, s);
    }
    
    private ThreadGroup(final Void void1, final ThreadGroup parent, final String name) {
        this.nUnstartedThreads = 0;
        this.name = name;
        this.maxPriority = parent.maxPriority;
        this.daemon = parent.daemon;
        this.vmAllowSuspension = parent.vmAllowSuspension;
        (this.parent = parent).add(this);
    }
    
    private static Void checkParentAccess(final ThreadGroup threadGroup) {
        threadGroup.checkAccess();
        return null;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final ThreadGroup getParent() {
        if (this.parent != null) {
            this.parent.checkAccess();
        }
        return this.parent;
    }
    
    public final int getMaxPriority() {
        return this.maxPriority;
    }
    
    public final boolean isDaemon() {
        return this.daemon;
    }
    
    public synchronized boolean isDestroyed() {
        return this.destroyed;
    }
    
    public final void setDaemon(final boolean daemon) {
        this.checkAccess();
        this.daemon = daemon;
    }
    
    public final void setMaxPriority(final int maxPriority) {
        final int ngroups;
        ThreadGroup[] array;
        synchronized (this) {
            this.checkAccess();
            if (maxPriority < 1 || maxPriority > 10) {
                return;
            }
            this.maxPriority = ((this.parent != null) ? Math.min(maxPriority, this.parent.maxPriority) : maxPriority);
            ngroups = this.ngroups;
            if (this.groups != null) {
                array = Arrays.copyOf(this.groups, ngroups);
            }
            else {
                array = null;
            }
        }
        for (int i = 0; i < ngroups; ++i) {
            array[i].setMaxPriority(maxPriority);
        }
    }
    
    public final boolean parentOf(ThreadGroup parent) {
        while (parent != null) {
            if (parent == this) {
                return true;
            }
            parent = parent.parent;
        }
        return false;
    }
    
    public final void checkAccess() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkAccess(this);
        }
    }
    
    public int activeCount() {
        int nthreads;
        final int ngroups;
        ThreadGroup[] array;
        synchronized (this) {
            if (this.destroyed) {
                return 0;
            }
            nthreads = this.nthreads;
            ngroups = this.ngroups;
            if (this.groups != null) {
                array = Arrays.copyOf(this.groups, ngroups);
            }
            else {
                array = null;
            }
        }
        for (int i = 0; i < ngroups; ++i) {
            nthreads += array[i].activeCount();
        }
        return nthreads;
    }
    
    public int enumerate(final Thread[] array) {
        this.checkAccess();
        return this.enumerate(array, 0, true);
    }
    
    public int enumerate(final Thread[] array, final boolean b) {
        this.checkAccess();
        return this.enumerate(array, 0, b);
    }
    
    private int enumerate(final Thread[] array, int enumerate, final boolean b) {
        int ngroups = 0;
        ThreadGroup[] array2 = null;
        synchronized (this) {
            if (this.destroyed) {
                return 0;
            }
            int nthreads = this.nthreads;
            if (nthreads > array.length - enumerate) {
                nthreads = array.length - enumerate;
            }
            for (int i = 0; i < nthreads; ++i) {
                if (this.threads[i].isAlive()) {
                    array[enumerate++] = this.threads[i];
                }
            }
            if (b) {
                ngroups = this.ngroups;
                if (this.groups != null) {
                    array2 = Arrays.copyOf(this.groups, ngroups);
                }
                else {
                    array2 = null;
                }
            }
        }
        if (b) {
            for (int j = 0; j < ngroups; ++j) {
                enumerate = array2[j].enumerate(array, enumerate, true);
            }
        }
        return enumerate;
    }
    
    public int activeGroupCount() {
        final int ngroups;
        ThreadGroup[] array;
        synchronized (this) {
            if (this.destroyed) {
                return 0;
            }
            ngroups = this.ngroups;
            if (this.groups != null) {
                array = Arrays.copyOf(this.groups, ngroups);
            }
            else {
                array = null;
            }
        }
        int n = ngroups;
        for (int i = 0; i < ngroups; ++i) {
            n += array[i].activeGroupCount();
        }
        return n;
    }
    
    public int enumerate(final ThreadGroup[] array) {
        this.checkAccess();
        return this.enumerate(array, 0, true);
    }
    
    public int enumerate(final ThreadGroup[] array, final boolean b) {
        this.checkAccess();
        return this.enumerate(array, 0, b);
    }
    
    private int enumerate(final ThreadGroup[] array, int enumerate, final boolean b) {
        int ngroups = 0;
        ThreadGroup[] array2 = null;
        synchronized (this) {
            if (this.destroyed) {
                return 0;
            }
            int ngroups2 = this.ngroups;
            if (ngroups2 > array.length - enumerate) {
                ngroups2 = array.length - enumerate;
            }
            if (ngroups2 > 0) {
                System.arraycopy(this.groups, 0, array, enumerate, ngroups2);
                enumerate += ngroups2;
            }
            if (b) {
                ngroups = this.ngroups;
                if (this.groups != null) {
                    array2 = Arrays.copyOf(this.groups, ngroups);
                }
                else {
                    array2 = null;
                }
            }
        }
        if (b) {
            for (int i = 0; i < ngroups; ++i) {
                enumerate = array2[i].enumerate(array, enumerate, true);
            }
        }
        return enumerate;
    }
    
    @Deprecated
    public final void stop() {
        if (this.stopOrSuspend(false)) {
            Thread.currentThread().stop();
        }
    }
    
    public final void interrupt() {
        final int ngroups;
        ThreadGroup[] array;
        synchronized (this) {
            this.checkAccess();
            for (int i = 0; i < this.nthreads; ++i) {
                this.threads[i].interrupt();
            }
            ngroups = this.ngroups;
            if (this.groups != null) {
                array = Arrays.copyOf(this.groups, ngroups);
            }
            else {
                array = null;
            }
        }
        for (int j = 0; j < ngroups; ++j) {
            array[j].interrupt();
        }
    }
    
    @Deprecated
    public final void suspend() {
        if (this.stopOrSuspend(true)) {
            Thread.currentThread().suspend();
        }
    }
    
    private boolean stopOrSuspend(final boolean b) {
        boolean b2 = false;
        final Thread currentThread = Thread.currentThread();
        ThreadGroup[] array = null;
        final int ngroups;
        synchronized (this) {
            this.checkAccess();
            for (int i = 0; i < this.nthreads; ++i) {
                if (this.threads[i] == currentThread) {
                    b2 = true;
                }
                else if (b) {
                    this.threads[i].suspend();
                }
                else {
                    this.threads[i].stop();
                }
            }
            ngroups = this.ngroups;
            if (this.groups != null) {
                array = Arrays.copyOf(this.groups, ngroups);
            }
        }
        for (int j = 0; j < ngroups; ++j) {
            b2 = (array[j].stopOrSuspend(b) || b2);
        }
        return b2;
    }
    
    @Deprecated
    public final void resume() {
        final int ngroups;
        ThreadGroup[] array;
        synchronized (this) {
            this.checkAccess();
            for (int i = 0; i < this.nthreads; ++i) {
                this.threads[i].resume();
            }
            ngroups = this.ngroups;
            if (this.groups != null) {
                array = Arrays.copyOf(this.groups, ngroups);
            }
            else {
                array = null;
            }
        }
        for (int j = 0; j < ngroups; ++j) {
            array[j].resume();
        }
    }
    
    public final void destroy() {
        final int ngroups;
        ThreadGroup[] array;
        synchronized (this) {
            this.checkAccess();
            if (this.destroyed || this.nthreads > 0) {
                throw new IllegalThreadStateException();
            }
            ngroups = this.ngroups;
            if (this.groups != null) {
                array = Arrays.copyOf(this.groups, ngroups);
            }
            else {
                array = null;
            }
            if (this.parent != null) {
                this.destroyed = true;
                this.ngroups = 0;
                this.groups = null;
                this.nthreads = 0;
                this.threads = null;
            }
        }
        for (int i = 0; i < ngroups; ++i) {
            array[i].destroy();
        }
        if (this.parent != null) {
            this.parent.remove(this);
        }
    }
    
    private final void add(final ThreadGroup threadGroup) {
        synchronized (this) {
            if (this.destroyed) {
                throw new IllegalThreadStateException();
            }
            if (this.groups == null) {
                this.groups = new ThreadGroup[4];
            }
            else if (this.ngroups == this.groups.length) {
                this.groups = Arrays.copyOf(this.groups, this.ngroups * 2);
            }
            this.groups[this.ngroups] = threadGroup;
            ++this.ngroups;
        }
    }
    
    private void remove(final ThreadGroup threadGroup) {
        synchronized (this) {
            if (this.destroyed) {
                return;
            }
            for (int i = 0; i < this.ngroups; ++i) {
                if (this.groups[i] == threadGroup) {
                    --this.ngroups;
                    System.arraycopy(this.groups, i + 1, this.groups, i, this.ngroups - i);
                    this.groups[this.ngroups] = null;
                    break;
                }
            }
            if (this.nthreads == 0) {
                this.notifyAll();
            }
            if (this.daemon && this.nthreads == 0 && this.nUnstartedThreads == 0 && this.ngroups == 0) {
                this.destroy();
            }
        }
    }
    
    void addUnstarted() {
        synchronized (this) {
            if (this.destroyed) {
                throw new IllegalThreadStateException();
            }
            ++this.nUnstartedThreads;
        }
    }
    
    void add(final Thread thread) {
        synchronized (this) {
            if (this.destroyed) {
                throw new IllegalThreadStateException();
            }
            if (this.threads == null) {
                this.threads = new Thread[4];
            }
            else if (this.nthreads == this.threads.length) {
                this.threads = Arrays.copyOf(this.threads, this.nthreads * 2);
            }
            this.threads[this.nthreads] = thread;
            ++this.nthreads;
            --this.nUnstartedThreads;
        }
    }
    
    void threadStartFailed(final Thread thread) {
        synchronized (this) {
            this.remove(thread);
            ++this.nUnstartedThreads;
        }
    }
    
    void threadTerminated(final Thread thread) {
        synchronized (this) {
            this.remove(thread);
            if (this.nthreads == 0) {
                this.notifyAll();
            }
            if (this.daemon && this.nthreads == 0 && this.nUnstartedThreads == 0 && this.ngroups == 0) {
                this.destroy();
            }
        }
    }
    
    private void remove(final Thread thread) {
        synchronized (this) {
            if (this.destroyed) {
                return;
            }
            for (int i = 0; i < this.nthreads; ++i) {
                if (this.threads[i] == thread) {
                    System.arraycopy(this.threads, i + 1, this.threads, i, --this.nthreads - i);
                    this.threads[this.nthreads] = null;
                    break;
                }
            }
        }
    }
    
    public void list() {
        this.list(System.out, 0);
    }
    
    void list(final PrintStream printStream, int n) {
        final int ngroups;
        ThreadGroup[] array;
        synchronized (this) {
            for (int i = 0; i < n; ++i) {
                printStream.print(" ");
            }
            printStream.println(this);
            n += 4;
            for (int j = 0; j < this.nthreads; ++j) {
                for (int k = 0; k < n; ++k) {
                    printStream.print(" ");
                }
                printStream.println(this.threads[j]);
            }
            ngroups = this.ngroups;
            if (this.groups != null) {
                array = Arrays.copyOf(this.groups, ngroups);
            }
            else {
                array = null;
            }
        }
        for (int l = 0; l < ngroups; ++l) {
            array[l].list(printStream, n);
        }
    }
    
    @Override
    public void uncaughtException(final Thread thread, final Throwable t) {
        if (this.parent != null) {
            this.parent.uncaughtException(thread, t);
        }
        else {
            final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (defaultUncaughtExceptionHandler != null) {
                defaultUncaughtExceptionHandler.uncaughtException(thread, t);
            }
            else if (!(t instanceof ThreadDeath)) {
                System.err.print("Exception in thread \"" + thread.getName() + "\" ");
                t.printStackTrace(System.err);
            }
        }
    }
    
    @Deprecated
    public boolean allowThreadSuspension(final boolean vmAllowSuspension) {
        if (!(this.vmAllowSuspension = vmAllowSuspension)) {
            VM.unsuspendSomeThreads();
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[name=" + this.getName() + ",maxpri=" + this.maxPriority + "]";
    }
}
