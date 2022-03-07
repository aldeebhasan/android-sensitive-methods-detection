package java.util.concurrent;

import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.util.function.*;

public class LinkedBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, Serializable
{
    private static final long serialVersionUID = -6903933977591709194L;
    private final int capacity;
    private final AtomicInteger count;
    transient Node<E> head;
    private transient Node<E> last;
    private final ReentrantLock takeLock;
    private final Condition notEmpty;
    private final ReentrantLock putLock;
    private final Condition notFull;
    
    private void signalNotEmpty() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            this.notEmpty.signal();
        }
        finally {
            takeLock.unlock();
        }
    }
    
    private void signalNotFull() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            this.notFull.signal();
        }
        finally {
            putLock.unlock();
        }
    }
    
    private void enqueue(final Node<E> node) {
        this.last.next = node;
        this.last = node;
    }
    
    private E dequeue() {
        final Node<E> head = this.head;
        final Node<E> next = head.next;
        head.next = head;
        this.head = next;
        final E item = next.item;
        next.item = null;
        return item;
    }
    
    void fullyLock() {
        this.putLock.lock();
        this.takeLock.lock();
    }
    
    void fullyUnlock() {
        this.takeLock.unlock();
        this.putLock.unlock();
    }
    
    public LinkedBlockingQueue() {
        this(Integer.MAX_VALUE);
    }
    
    public LinkedBlockingQueue(final int capacity) {
        this.count = new AtomicInteger();
        this.takeLock = new ReentrantLock();
        this.notEmpty = this.takeLock.newCondition();
        this.putLock = new ReentrantLock();
        this.notFull = this.putLock.newCondition();
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        final Node<E> node = new Node<E>(null);
        this.head = node;
        this.last = node;
    }
    
    public LinkedBlockingQueue(final Collection<? extends E> collection) {
        this(Integer.MAX_VALUE);
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            int n = 0;
            for (final E next : collection) {
                if (next == null) {
                    throw new NullPointerException();
                }
                if (n == this.capacity) {
                    throw new IllegalStateException("Queue full");
                }
                this.enqueue(new Node<E>(next));
                ++n;
            }
            this.count.set(n);
        }
        finally {
            putLock.unlock();
        }
    }
    
    @Override
    public int size() {
        return this.count.get();
    }
    
    @Override
    public int remainingCapacity() {
        return this.capacity - this.count.get();
    }
    
    @Override
    public void put(final E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        int andIncrement = -1;
        final Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            while (count.get() == this.capacity) {
                this.notFull.await();
            }
            this.enqueue(node);
            andIncrement = count.getAndIncrement();
            if (andIncrement + 1 < this.capacity) {
                this.notFull.signal();
            }
        }
        finally {
            putLock.unlock();
        }
        if (andIncrement == 0) {
            this.signalNotEmpty();
        }
    }
    
    @Override
    public boolean offer(final E e, final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        long n2 = timeUnit.toNanos(n);
        int andIncrement = -1;
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            while (count.get() == this.capacity) {
                if (n2 <= 0L) {
                    return false;
                }
                n2 = this.notFull.awaitNanos(n2);
            }
            this.enqueue(new Node<E>(e));
            andIncrement = count.getAndIncrement();
            if (andIncrement + 1 < this.capacity) {
                this.notFull.signal();
            }
        }
        finally {
            putLock.unlock();
        }
        if (andIncrement == 0) {
            this.signalNotEmpty();
        }
        return true;
    }
    
    @Override
    public boolean offer(final E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        final AtomicInteger count = this.count;
        if (count.get() == this.capacity) {
            return false;
        }
        int andIncrement = -1;
        final Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            if (count.get() < this.capacity) {
                this.enqueue(node);
                andIncrement = count.getAndIncrement();
                if (andIncrement + 1 < this.capacity) {
                    this.notFull.signal();
                }
            }
        }
        finally {
            putLock.unlock();
        }
        if (andIncrement == 0) {
            this.signalNotEmpty();
        }
        return andIncrement >= 0;
    }
    
    @Override
    public E take() throws InterruptedException {
        int andDecrement = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        E dequeue;
        try {
            while (count.get() == 0) {
                this.notEmpty.await();
            }
            dequeue = this.dequeue();
            andDecrement = count.getAndDecrement();
            if (andDecrement > 1) {
                this.notEmpty.signal();
            }
        }
        finally {
            takeLock.unlock();
        }
        if (andDecrement == this.capacity) {
            this.signalNotFull();
        }
        return dequeue;
    }
    
    @Override
    public E poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        E dequeue = null;
        int andDecrement = -1;
        long n2 = timeUnit.toNanos(n);
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                if (n2 <= 0L) {
                    return null;
                }
                n2 = this.notEmpty.awaitNanos(n2);
            }
            dequeue = this.dequeue();
            andDecrement = count.getAndDecrement();
            if (andDecrement > 1) {
                this.notEmpty.signal();
            }
        }
        finally {
            takeLock.unlock();
        }
        if (andDecrement == this.capacity) {
            this.signalNotFull();
        }
        return dequeue;
    }
    
    @Override
    public E poll() {
        final AtomicInteger count = this.count;
        if (count.get() == 0) {
            return null;
        }
        E dequeue = null;
        int andDecrement = -1;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            if (count.get() > 0) {
                dequeue = this.dequeue();
                andDecrement = count.getAndDecrement();
                if (andDecrement > 1) {
                    this.notEmpty.signal();
                }
            }
        }
        finally {
            takeLock.unlock();
        }
        if (andDecrement == this.capacity) {
            this.signalNotFull();
        }
        return dequeue;
    }
    
    @Override
    public E peek() {
        if (this.count.get() == 0) {
            return null;
        }
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            final Node<E> next = this.head.next;
            if (next == null) {
                return null;
            }
            return next.item;
        }
        finally {
            takeLock.unlock();
        }
    }
    
    void unlink(final Node<E> node, final Node<E> last) {
        node.item = null;
        last.next = node.next;
        if (this.last == node) {
            this.last = last;
        }
        if (this.count.getAndDecrement() == this.capacity) {
            this.notFull.signal();
        }
    }
    
    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            return false;
        }
        this.fullyLock();
        try {
            Node<E> head = this.head;
            for (Node<E> node = head.next; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    this.unlink(node, head);
                    return true;
                }
                head = node;
            }
            return false;
        }
        finally {
            this.fullyUnlock();
        }
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        this.fullyLock();
        try {
            for (Node<E> node = this.head.next; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    return true;
                }
            }
            return false;
        }
        finally {
            this.fullyUnlock();
        }
    }
    
    @Override
    public Object[] toArray() {
        this.fullyLock();
        try {
            final Object[] array = new Object[this.count.get()];
            int n = 0;
            for (Node<E> node = this.head.next; node != null; node = node.next) {
                array[n++] = node.item;
            }
            return array;
        }
        finally {
            this.fullyUnlock();
        }
    }
    
    @Override
    public <T> T[] toArray(T[] array) {
        this.fullyLock();
        try {
            final int value = this.count.get();
            if (array.length < value) {
                array = (T[])Array.newInstance(array.getClass().getComponentType(), value);
            }
            int n = 0;
            for (Node<E> node = this.head.next; node != null; node = node.next) {
                array[n++] = (T)node.item;
            }
            if (array.length > n) {
                array[n] = null;
            }
            return array;
        }
        finally {
            this.fullyUnlock();
        }
    }
    
    @Override
    public String toString() {
        this.fullyLock();
        try {
            Node<E> node = this.head.next;
            if (node == null) {
                return "[]";
            }
            final StringBuilder sb = new StringBuilder();
            sb.append('[');
            while (true) {
                final E item = node.item;
                sb.append((item == this) ? "(this Collection)" : item);
                node = node.next;
                if (node == null) {
                    break;
                }
                sb.append(',').append(' ');
            }
            return sb.append(']').toString();
        }
        finally {
            this.fullyUnlock();
        }
    }
    
    @Override
    public void clear() {
        this.fullyLock();
        try {
            Node<E> next;
            for (Node<E> head = this.head; (next = head.next) != null; head = next) {
                head.next = head;
                next.item = null;
            }
            this.head = this.last;
            if (this.count.getAndSet(0) == this.capacity) {
                this.notFull.signal();
            }
        }
        finally {
            this.fullyUnlock();
        }
    }
    
    @Override
    public int drainTo(final Collection<? super E> collection) {
        return this.drainTo(collection, Integer.MAX_VALUE);
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
        int n2 = 0;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            final int min = Math.min(n, this.count.get());
            Node<E> head = this.head;
            int i = 0;
            try {
                while (i < min) {
                    final Node<E> next = head.next;
                    collection.add((Object)next.item);
                    next.item = null;
                    head.next = head;
                    head = next;
                    ++i;
                }
                return min;
            }
            finally {
                if (i > 0) {
                    this.head = head;
                    n2 = ((this.count.getAndAdd(-i) == this.capacity) ? 1 : 0);
                }
            }
        }
        finally {
            takeLock.unlock();
            if (n2 != 0) {
                this.signalNotFull();
            }
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new LBQSpliterator<E>(this);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.fullyLock();
        try {
            objectOutputStream.defaultWriteObject();
            for (Node<E> node = this.head.next; node != null; node = node.next) {
                objectOutputStream.writeObject(node.item);
            }
            objectOutputStream.writeObject(null);
        }
        finally {
            this.fullyUnlock();
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.count.set(0);
        final Node<E> node = new Node<E>(null);
        this.head = node;
        this.last = node;
        while (true) {
            final Object object = objectInputStream.readObject();
            if (object == null) {
                break;
            }
            this.add((E)object);
        }
    }
    
    private class Itr implements Iterator<E>
    {
        private Node<E> current;
        private Node<E> lastRet;
        private E currentElement;
        
        Itr() {
            LinkedBlockingQueue.this.fullyLock();
            try {
                this.current = LinkedBlockingQueue.this.head.next;
                if (this.current != null) {
                    this.currentElement = this.current.item;
                }
            }
            finally {
                LinkedBlockingQueue.this.fullyUnlock();
            }
        }
        
        @Override
        public boolean hasNext() {
            return this.current != null;
        }
        
        private Node<E> nextNode(Node<E> node) {
            while (true) {
                final Node<E> next = node.next;
                if (next == node) {
                    return LinkedBlockingQueue.this.head.next;
                }
                if (next == null || next.item != null) {
                    return next;
                }
                node = next;
            }
        }
        
        @Override
        public E next() {
            LinkedBlockingQueue.this.fullyLock();
            try {
                if (this.current == null) {
                    throw new NoSuchElementException();
                }
                final E currentElement = this.currentElement;
                this.lastRet = this.current;
                this.current = this.nextNode(this.current);
                this.currentElement = ((this.current == null) ? null : this.current.item);
                return currentElement;
            }
            finally {
                LinkedBlockingQueue.this.fullyUnlock();
            }
        }
        
        @Override
        public void remove() {
            if (this.lastRet == null) {
                throw new IllegalStateException();
            }
            LinkedBlockingQueue.this.fullyLock();
            try {
                final Node<E> lastRet = this.lastRet;
                this.lastRet = null;
                Node<E> head = LinkedBlockingQueue.this.head;
                for (Node<E> node = head.next; node != null; node = node.next) {
                    if (node == lastRet) {
                        LinkedBlockingQueue.this.unlink(node, head);
                        break;
                    }
                    head = node;
                }
            }
            finally {
                LinkedBlockingQueue.this.fullyUnlock();
            }
        }
    }
    
    static class Node<E>
    {
        E item;
        Node<E> next;
        
        Node(final E item) {
            this.item = item;
        }
    }
    
    static final class LBQSpliterator<E> implements Spliterator<E>
    {
        static final int MAX_BATCH = 33554432;
        final LinkedBlockingQueue<E> queue;
        Node<E> current;
        int batch;
        boolean exhausted;
        long est;
        
        LBQSpliterator(final LinkedBlockingQueue<E> queue) {
            this.queue = queue;
            this.est = queue.size();
        }
        
        @Override
        public long estimateSize() {
            return this.est;
        }
        
        @Override
        public Spliterator<E> trySplit() {
            final LinkedBlockingQueue<E> queue = this.queue;
            final int batch = this.batch;
            final int n = (batch <= 0) ? 1 : ((batch >= 33554432) ? 33554432 : (batch + 1));
            Node<E> node;
            if (!this.exhausted && ((node = this.current) != null || (node = queue.head.next) != null) && node.next != null) {
                final Object[] array = new Object[n];
                int batch2 = 0;
                Node<E> node2 = this.current;
                queue.fullyLock();
                try {
                    if (node2 != null || (node2 = queue.head.next) != null) {
                        do {
                            if ((array[batch2] = node2.item) != null) {
                                ++batch2;
                            }
                        } while ((node2 = node2.next) != null && batch2 < n);
                    }
                }
                finally {
                    queue.fullyUnlock();
                }
                final Node<E> current = node2;
                this.current = current;
                if (current == null) {
                    this.est = 0L;
                    this.exhausted = true;
                }
                else {
                    final long est = this.est - batch2;
                    this.est = est;
                    if (est < 0L) {
                        this.est = 0L;
                    }
                }
                if (batch2 > 0) {
                    this.batch = batch2;
                    return (Spliterator<E>)Spliterators.spliterator(array, 0, batch2, 4368);
                }
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final LinkedBlockingQueue<E> queue = this.queue;
            if (!this.exhausted) {
                this.exhausted = true;
                Node<E> node = this.current;
                do {
                    Object item = null;
                    queue.fullyLock();
                    try {
                        if (node == null) {
                            node = queue.head.next;
                        }
                        while (node != null) {
                            item = node.item;
                            node = node.next;
                            if (item != null) {
                                break;
                            }
                        }
                    }
                    finally {
                        queue.fullyUnlock();
                    }
                    if (item != null) {
                        consumer.accept((Object)item);
                    }
                } while (node != null);
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final LinkedBlockingQueue<E> queue = this.queue;
            if (!this.exhausted) {
                Object item = null;
                queue.fullyLock();
                try {
                    if (this.current == null) {
                        this.current = queue.head.next;
                    }
                    while (this.current != null) {
                        item = this.current.item;
                        this.current = this.current.next;
                        if (item != null) {
                            break;
                        }
                    }
                }
                finally {
                    queue.fullyUnlock();
                }
                if (this.current == null) {
                    this.exhausted = true;
                }
                if (item != null) {
                    consumer.accept((Object)item);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int characteristics() {
            return 4368;
        }
    }
}
