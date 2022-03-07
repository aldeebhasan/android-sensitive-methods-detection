package java.util.concurrent;

import java.util.concurrent.locks.*;
import java.util.*;

public class DelayQueue<E extends Delayed> extends AbstractQueue<E> implements BlockingQueue<E>
{
    private final transient ReentrantLock lock;
    private final PriorityQueue<E> q;
    private Thread leader;
    private final Condition available;
    
    public DelayQueue() {
        this.lock = new ReentrantLock();
        this.q = new PriorityQueue<E>();
        this.leader = null;
        this.available = this.lock.newCondition();
    }
    
    public DelayQueue(final Collection<? extends E> collection) {
        this.lock = new ReentrantLock();
        this.q = new PriorityQueue<E>();
        this.leader = null;
        this.available = this.lock.newCondition();
        this.addAll(collection);
    }
    
    @Override
    public boolean add(final E e) {
        return this.offer(e);
    }
    
    @Override
    public boolean offer(final E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.q.offer(e);
            if (this.q.peek() == e) {
                this.leader = null;
                this.available.signal();
            }
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void put(final E e) {
        this.offer(e);
    }
    
    @Override
    public boolean offer(final E e, final long n, final TimeUnit timeUnit) {
        return this.offer(e);
    }
    
    @Override
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Delayed delayed = this.q.peek();
            if (delayed == null || delayed.getDelay(TimeUnit.NANOSECONDS) > 0L) {
                return null;
            }
            return this.q.poll();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (true) {
                final Delayed delayed = this.q.peek();
                if (delayed == null) {
                    this.available.await();
                }
                else {
                    final long delay = delayed.getDelay(TimeUnit.NANOSECONDS);
                    if (delay <= 0L) {
                        break;
                    }
                    if (this.leader != null) {
                        this.available.await();
                    }
                    else {
                        final Thread currentThread = Thread.currentThread();
                        this.leader = currentThread;
                        try {
                            this.available.awaitNanos(delay);
                        }
                        finally {
                            if (this.leader == currentThread) {
                                this.leader = null;
                            }
                        }
                    }
                }
            }
            return this.q.poll();
        }
        finally {
            if (this.leader == null && this.q.peek() != null) {
                this.available.signal();
            }
            lock.unlock();
        }
    }
    
    @Override
    public E poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (true) {
                final Delayed delayed = this.q.peek();
                if (delayed == null) {
                    if (n2 <= 0L) {
                        return null;
                    }
                    n2 = this.available.awaitNanos(n2);
                }
                else {
                    final long delay = delayed.getDelay(TimeUnit.NANOSECONDS);
                    if (delay <= 0L) {
                        return this.q.poll();
                    }
                    if (n2 <= 0L) {
                        return null;
                    }
                    if (n2 < delay || this.leader != null) {
                        n2 = this.available.awaitNanos(n2);
                    }
                    else {
                        final Thread currentThread = Thread.currentThread();
                        this.leader = currentThread;
                        try {
                            n2 -= delay - this.available.awaitNanos(delay);
                        }
                        finally {
                            if (this.leader == currentThread) {
                                this.leader = null;
                            }
                        }
                    }
                }
            }
        }
        finally {
            if (this.leader == null && this.q.peek() != null) {
                this.available.signal();
            }
            lock.unlock();
        }
    }
    
    @Override
    public E peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.q.peek();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.q.size();
        }
        finally {
            lock.unlock();
        }
    }
    
    private E peekExpired() {
        final Delayed delayed = this.q.peek();
        return (E)((delayed == null || delayed.getDelay(TimeUnit.NANOSECONDS) > 0L) ? null : delayed);
    }
    
    @Override
    public int drainTo(final Collection<? super E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int n = 0;
            Object peekExpired;
            while ((peekExpired = this.peekExpired()) != null) {
                collection.add((Object)peekExpired);
                this.q.poll();
                ++n;
            }
            return n;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public int drainTo(final Collection<? super E> collection, final int n) {
        if (collection == null) {
            throw new NullPointerException();
        }
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        if (n <= 0) {
            return 0;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int n2;
            Object peekExpired;
            for (n2 = 0; n2 < n && (peekExpired = this.peekExpired()) != null; ++n2) {
                collection.add((Object)peekExpired);
                this.q.poll();
            }
            return n2;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void clear() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.q.clear();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }
    
    @Override
    public Object[] toArray() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.q.toArray();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.q.toArray(array);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean remove(final Object o) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.q.remove(o);
        }
        finally {
            lock.unlock();
        }
    }
    
    void removeEQ(final Object o) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Iterator<E> iterator = this.q.iterator();
            while (iterator.hasNext()) {
                if (o == iterator.next()) {
                    iterator.remove();
                    break;
                }
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr(this.toArray());
    }
    
    private class Itr implements Iterator<E>
    {
        final Object[] array;
        int cursor;
        int lastRet;
        
        Itr(final Object[] array) {
            this.lastRet = -1;
            this.array = array;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor < this.array.length;
        }
        
        @Override
        public E next() {
            if (this.cursor >= this.array.length) {
                throw new NoSuchElementException();
            }
            this.lastRet = this.cursor;
            return (E)this.array[this.cursor++];
        }
        
        @Override
        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            DelayQueue.this.removeEQ(this.array[this.lastRet]);
            this.lastRet = -1;
        }
    }
}
