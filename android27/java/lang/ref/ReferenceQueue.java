package java.lang.ref;

import sun.misc.*;
import java.util.function.*;

public class ReferenceQueue<T>
{
    static ReferenceQueue<Object> NULL;
    static ReferenceQueue<Object> ENQUEUED;
    private Lock lock;
    private volatile Reference<? extends T> head;
    private long queueLength;
    
    public ReferenceQueue() {
        this.lock = new Lock();
        this.head = null;
        this.queueLength = 0L;
    }
    
    boolean enqueue(final Reference<? extends T> head) {
        synchronized (this.lock) {
            final ReferenceQueue<? super T> queue = head.queue;
            if (queue == ReferenceQueue.NULL || queue == ReferenceQueue.ENQUEUED) {
                return false;
            }
            assert queue == this;
            head.queue = ReferenceQueue.ENQUEUED;
            head.next = ((this.head == null) ? head : this.head);
            this.head = head;
            ++this.queueLength;
            if (head instanceof FinalReference) {
                VM.addFinalRefCount(1);
            }
            this.lock.notifyAll();
            return true;
        }
    }
    
    private Reference<? extends T> reallyPoll() {
        final Reference<? extends T> head = this.head;
        if (head != null) {
            final Reference<? extends T> next = head.next;
            this.head = ((next == head) ? null : next);
            head.queue = ReferenceQueue.NULL;
            head.next = head;
            --this.queueLength;
            if (head instanceof FinalReference) {
                VM.addFinalRefCount(-1);
            }
            return head;
        }
        return null;
    }
    
    public Reference<? extends T> poll() {
        if (this.head == null) {
            return null;
        }
        synchronized (this.lock) {
            return this.reallyPoll();
        }
    }
    
    public Reference<? extends T> remove(long n) throws IllegalArgumentException, InterruptedException {
        if (n < 0L) {
            throw new IllegalArgumentException("Negative timeout value");
        }
        synchronized (this.lock) {
            final Reference<? extends T> reallyPoll = this.reallyPoll();
            if (reallyPoll != null) {
                return reallyPoll;
            }
            long n2 = (n == 0L) ? 0L : System.nanoTime();
            while (true) {
                this.lock.wait(n);
                final Reference<? extends T> reallyPoll2 = this.reallyPoll();
                if (reallyPoll2 != null) {
                    return reallyPoll2;
                }
                if (n == 0L) {
                    continue;
                }
                final long nanoTime = System.nanoTime();
                n -= (nanoTime - n2) / 1000000L;
                if (n <= 0L) {
                    return null;
                }
                n2 = nanoTime;
            }
        }
    }
    
    public Reference<? extends T> remove() throws InterruptedException {
        return this.remove(0L);
    }
    
    void forEach(final Consumer<? super Reference<? extends T>> consumer) {
        Reference<? extends T> reference = this.head;
        while (reference != null) {
            consumer.accept(reference);
            final Reference<? extends T> next = reference.next;
            if (next == reference) {
                if (reference.queue == ReferenceQueue.ENQUEUED) {
                    reference = null;
                }
                else {
                    reference = this.head;
                }
            }
            else {
                reference = next;
            }
        }
    }
    
    static {
        ReferenceQueue.NULL = new Null<Object>();
        ReferenceQueue.ENQUEUED = new Null<Object>();
    }
    
    private static class Lock
    {
    }
    
    private static class Null<S> extends ReferenceQueue<S>
    {
        @Override
        boolean enqueue(final Reference<? extends S> reference) {
            return false;
        }
    }
}
