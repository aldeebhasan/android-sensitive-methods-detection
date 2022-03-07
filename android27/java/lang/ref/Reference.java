package java.lang.ref;

import sun.misc.*;

public abstract class Reference<T>
{
    private T referent;
    volatile ReferenceQueue<? super T> queue;
    volatile Reference next;
    private transient Reference<T> discovered;
    private static Lock lock;
    private static Reference<Object> pending;
    
    static boolean tryHandlePending(final boolean b) {
        Reference<Object> pending;
        Cleaner cleaner;
        try {
            synchronized (Reference.lock) {
                if (Reference.pending == null) {
                    if (b) {
                        Reference.lock.wait();
                    }
                    return b;
                }
                pending = Reference.pending;
                cleaner = ((pending instanceof Cleaner) ? ((Cleaner)pending) : null);
                Reference.pending = pending.discovered;
                pending.discovered = null;
            }
        }
        catch (OutOfMemoryError outOfMemoryError) {
            Thread.yield();
            return true;
        }
        catch (InterruptedException ex) {
            return true;
        }
        if (cleaner != null) {
            cleaner.clean();
            return true;
        }
        final ReferenceQueue<? super T> queue = pending.queue;
        if (queue != ReferenceQueue.NULL) {
            queue.enqueue(pending);
        }
        return true;
    }
    
    public T get() {
        return this.referent;
    }
    
    public void clear() {
        this.referent = null;
    }
    
    public boolean isEnqueued() {
        return this.queue == ReferenceQueue.ENQUEUED;
    }
    
    public boolean enqueue() {
        return this.queue.enqueue((Reference<? extends T>)this);
    }
    
    Reference(final T t) {
        this(t, null);
    }
    
    Reference(final T referent, final ReferenceQueue<? super T> referenceQueue) {
        this.referent = referent;
        this.queue = ((referenceQueue == null) ? ReferenceQueue.NULL : referenceQueue);
    }
    
    static {
        Reference.lock = new Lock();
        Reference.pending = null;
        ThreadGroup threadGroup2;
        ThreadGroup threadGroup;
        for (threadGroup = (threadGroup2 = Thread.currentThread().getThreadGroup()); threadGroup2 != null; threadGroup2 = threadGroup.getParent()) {
            threadGroup = threadGroup2;
        }
        final ReferenceHandler referenceHandler = new ReferenceHandler(threadGroup, "Reference Handler");
        referenceHandler.setPriority(10);
        referenceHandler.setDaemon(true);
        referenceHandler.start();
        SharedSecrets.setJavaLangRefAccess(new JavaLangRefAccess() {
            @Override
            public boolean tryHandlePendingReference() {
                return Reference.tryHandlePending(false);
            }
        });
    }
    
    private static class Lock
    {
    }
    
    private static class ReferenceHandler extends Thread
    {
        private static void ensureClassInitialized(final Class<?> clazz) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            }
            catch (ClassNotFoundException ex) {
                throw (Error)new NoClassDefFoundError(ex.getMessage()).initCause(ex);
            }
        }
        
        ReferenceHandler(final ThreadGroup threadGroup, final String s) {
            super(threadGroup, s);
        }
        
        @Override
        public void run() {
            while (true) {
                Reference.tryHandlePending(true);
            }
        }
        
        static {
            ensureClassInitialized(InterruptedException.class);
            ensureClassInitialized(Cleaner.class);
        }
    }
}
