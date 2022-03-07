package java.util.concurrent;

import java.util.concurrent.locks.*;
import java.io.*;
import sun.misc.*;
import java.util.*;
import java.util.function.*;

public class PriorityBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, Serializable
{
    private static final long serialVersionUID = 5595510919245408276L;
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private transient Object[] queue;
    private transient int size;
    private transient Comparator<? super E> comparator;
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private transient volatile int allocationSpinLock;
    private PriorityQueue<E> q;
    private static final Unsafe UNSAFE;
    private static final long allocationSpinLockOffset;
    
    public PriorityBlockingQueue() {
        this(11, null);
    }
    
    public PriorityBlockingQueue(final int n) {
        this(n, null);
    }
    
    public PriorityBlockingQueue(final int n, final Comparator<? super E> comparator) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        this.lock = new ReentrantLock();
        this.notEmpty = this.lock.newCondition();
        this.comparator = comparator;
        this.queue = new Object[n];
    }
    
    public PriorityBlockingQueue(final Collection<? extends E> collection) {
        this.lock = new ReentrantLock();
        this.notEmpty = this.lock.newCondition();
        boolean b = true;
        boolean b2 = true;
        if (collection instanceof SortedSet) {
            this.comparator = (Comparator<? super E>)((SortedSet)collection).comparator();
            b = false;
        }
        else if (collection instanceof PriorityBlockingQueue) {
            final PriorityBlockingQueue priorityBlockingQueue = (PriorityBlockingQueue)collection;
            this.comparator = (Comparator<? super E>)priorityBlockingQueue.comparator();
            b2 = false;
            if (priorityBlockingQueue.getClass() == PriorityBlockingQueue.class) {
                b = false;
            }
        }
        Object[] queue = collection.toArray();
        final int length = queue.length;
        if (((PriorityBlockingQueue)collection).getClass() != ArrayList.class) {
            queue = Arrays.copyOf(queue, length, (Class<? extends Object[]>)Object[].class);
        }
        if (b2 && (length == 1 || this.comparator != null)) {
            for (int i = 0; i < length; ++i) {
                if (queue[i] == null) {
                    throw new NullPointerException();
                }
            }
        }
        this.queue = queue;
        this.size = length;
        if (b) {
            this.heapify();
        }
    }
    
    private void tryGrow(final Object[] array, final int n) {
        this.lock.unlock();
        Object[] queue = null;
        if (this.allocationSpinLock == 0 && PriorityBlockingQueue.UNSAFE.compareAndSwapInt(this, PriorityBlockingQueue.allocationSpinLockOffset, 0, 1)) {
            try {
                int n2 = n + ((n < 64) ? (n + 2) : (n >> 1));
                if (n2 - 2147483639 > 0) {
                    final int n3 = n + 1;
                    if (n3 < 0 || n3 > 2147483639) {
                        throw new OutOfMemoryError();
                    }
                    n2 = 2147483639;
                }
                if (n2 > n && this.queue == array) {
                    queue = new Object[n2];
                }
            }
            finally {
                this.allocationSpinLock = 0;
            }
        }
        if (queue == null) {
            Thread.yield();
        }
        this.lock.lock();
        if (queue != null && this.queue == array) {
            System.arraycopy(array, 0, this.queue = queue, 0, n);
        }
    }
    
    private E dequeue() {
        final int size = this.size - 1;
        if (size < 0) {
            return null;
        }
        final Object[] queue = this.queue;
        final Object o = queue[0];
        final Object o2 = queue[size];
        queue[size] = null;
        final Comparator<? super E> comparator = this.comparator;
        if (comparator == null) {
            siftDownComparable(0, o2, queue, size);
        }
        else {
            siftDownUsingComparator(0, o2, queue, size, (Comparator<? super Object>)comparator);
        }
        this.size = size;
        return (E)o;
    }
    
    private static <T> void siftUpComparable(int i, final T t, final Object[] array) {
        final Comparable comparable = (Comparable)t;
        while (i > 0) {
            final int n = i - 1 >>> 1;
            final Object o = array[n];
            if (comparable.compareTo(o) >= 0) {
                break;
            }
            array[i] = o;
            i = n;
        }
        array[i] = comparable;
    }
    
    private static <T> void siftUpUsingComparator(int i, final T t, final Object[] array, final Comparator<? super T> comparator) {
        while (i > 0) {
            final int n = i - 1 >>> 1;
            final Object o = array[n];
            if (comparator.compare((Object)t, (Object)o) >= 0) {
                break;
            }
            array[i] = o;
            i = n;
        }
        array[i] = t;
    }
    
    private static <T> void siftDownComparable(int i, final T t, final Object[] array, final int n) {
        if (n > 0) {
            final Comparable comparable = (Comparable)t;
            while (i < n >>> 1) {
                int n2 = (i << 1) + 1;
                Object o = array[n2];
                final int n3 = n2 + 1;
                if (n3 < n && ((Comparable<Object>)o).compareTo(array[n3]) > 0) {
                    o = array[n2 = n3];
                }
                if (comparable.compareTo(o) <= 0) {
                    break;
                }
                array[i] = o;
                i = n2;
            }
            array[i] = comparable;
        }
    }
    
    private static <T> void siftDownUsingComparator(int i, final T t, final Object[] array, final int n, final Comparator<? super T> comparator) {
        if (n > 0) {
            while (i < n >>> 1) {
                int n2 = (i << 1) + 1;
                Object o = array[n2];
                final int n3 = n2 + 1;
                if (n3 < n && comparator.compare((Object)o, (Object)array[n3]) > 0) {
                    o = array[n2 = n3];
                }
                if (comparator.compare((Object)t, (Object)o) <= 0) {
                    break;
                }
                array[i] = o;
                i = n2;
            }
            array[i] = t;
        }
    }
    
    private void heapify() {
        final Object[] queue = this.queue;
        final int size = this.size;
        final int n = (size >>> 1) - 1;
        final Comparator<? super E> comparator = this.comparator;
        if (comparator == null) {
            for (int i = n; i >= 0; --i) {
                siftDownComparable(i, queue[i], queue, size);
            }
        }
        else {
            for (int j = n; j >= 0; --j) {
                siftDownUsingComparator(j, queue[j], queue, size, (Comparator<? super Object>)comparator);
            }
        }
    }
    
    @Override
    public boolean add(final E e) {
        return this.offer(e);
    }
    
    @Override
    public boolean offer(final E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        int size;
        Object[] queue;
        int length;
        while ((size = this.size) >= (length = (queue = this.queue).length)) {
            this.tryGrow(queue, length);
        }
        try {
            final Comparator<? super E> comparator = this.comparator;
            if (comparator == null) {
                siftUpComparable(size, e, queue);
            }
            else {
                siftUpUsingComparator(size, e, queue, comparator);
            }
            this.size = size + 1;
            this.notEmpty.signal();
        }
        finally {
            lock.unlock();
        }
        return true;
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
            return this.dequeue();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        E dequeue;
        try {
            while ((dequeue = this.dequeue()) == null) {
                this.notEmpty.await();
            }
        }
        finally {
            lock.unlock();
        }
        return dequeue;
    }
    
    @Override
    public E poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        E dequeue;
        try {
            while ((dequeue = this.dequeue()) == null && n2 > 0L) {
                n2 = this.notEmpty.awaitNanos(n2);
            }
        }
        finally {
            lock.unlock();
        }
        return dequeue;
    }
    
    @Override
    public E peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (E)((this.size == 0) ? null : this.queue[0]);
        }
        finally {
            lock.unlock();
        }
    }
    
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    @Override
    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.size;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }
    
    private int indexOf(final Object o) {
        if (o != null) {
            final Object[] queue = this.queue;
            for (int size = this.size, i = 0; i < size; ++i) {
                if (o.equals(queue[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private void removeAt(final int n) {
        final Object[] queue = this.queue;
        final int size = this.size - 1;
        if (size == n) {
            queue[n] = null;
        }
        else {
            final Object o = queue[size];
            queue[size] = null;
            final Comparator<? super E> comparator = this.comparator;
            if (comparator == null) {
                siftDownComparable(n, o, queue, size);
            }
            else {
                siftDownUsingComparator(n, o, queue, size, (Comparator<? super Object>)comparator);
            }
            if (queue[n] == o) {
                if (comparator == null) {
                    siftUpComparable(n, o, queue);
                }
                else {
                    siftUpUsingComparator(n, o, queue, (Comparator<? super Object>)comparator);
                }
            }
        }
        this.size = size;
    }
    
    @Override
    public boolean remove(final Object o) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int index = this.indexOf(o);
            if (index == -1) {
                return false;
            }
            this.removeAt(index);
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    void removeEQ(final Object o) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] queue = this.queue;
            for (int i = 0; i < this.size; ++i) {
                if (o == queue[i]) {
                    this.removeAt(i);
                    break;
                }
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean contains(final Object o) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.indexOf(o) != -1;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public Object[] toArray() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return Arrays.copyOf(this.queue, this.size);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public String toString() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int size = this.size;
            if (size == 0) {
                return "[]";
            }
            final StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0; i < size; ++i) {
                final Object o = this.queue[i];
                sb.append((o == this) ? "(this Collection)" : o);
                if (i != size - 1) {
                    sb.append(',').append(' ');
                }
            }
            return sb.append(']').toString();
        }
        finally {
            lock.unlock();
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
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int min = Math.min(this.size, n);
            for (int i = 0; i < min; ++i) {
                collection.add((Object)this.queue[0]);
                this.dequeue();
            }
            return min;
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
            final Object[] queue = this.queue;
            final int size = this.size;
            this.size = 0;
            for (int i = 0; i < size; ++i) {
                queue[i] = null;
            }
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
            final int size = this.size;
            if (array.length < size) {
                return Arrays.copyOf(this.queue, this.size, (Class<? extends T[]>)array.getClass());
            }
            System.arraycopy(this.queue, 0, array, 0, size);
            if (array.length > size) {
                array[size] = null;
            }
            return array;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr(this.toArray());
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.lock.lock();
        try {
            (this.q = new PriorityQueue<E>(Math.max(this.size, 1), this.comparator)).addAll((Collection<?>)this);
            objectOutputStream.defaultWriteObject();
        }
        finally {
            this.q = null;
            this.lock.unlock();
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            objectInputStream.defaultReadObject();
            final int size = this.q.size();
            SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Object[].class, size);
            this.queue = new Object[size];
            this.comparator = this.q.comparator();
            this.addAll((Collection<? extends E>)this.q);
        }
        finally {
            this.q = null;
        }
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new PBQSpliterator<E>(this, null, 0, -1);
    }
    
    static {
        try {
            UNSAFE = Unsafe.getUnsafe();
            allocationSpinLockOffset = PriorityBlockingQueue.UNSAFE.objectFieldOffset(PriorityBlockingQueue.class.getDeclaredField("allocationSpinLock"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    final class Itr implements Iterator<E>
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
            PriorityBlockingQueue.this.removeEQ(this.array[this.lastRet]);
            this.lastRet = -1;
        }
    }
    
    static final class PBQSpliterator<E> implements Spliterator<E>
    {
        final PriorityBlockingQueue<E> queue;
        Object[] array;
        int index;
        int fence;
        
        PBQSpliterator(final PriorityBlockingQueue<E> queue, final Object[] array, final int index, final int fence) {
            this.queue = queue;
            this.array = array;
            this.index = index;
            this.fence = fence;
        }
        
        final int getFence() {
            int fence;
            if ((fence = this.fence) < 0) {
                final Object[] array = this.queue.toArray();
                this.array = array;
                final int length = array.length;
                this.fence = length;
                fence = length;
            }
            return fence;
        }
        
        @Override
        public Spliterator<E> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int index2 = index + fence >>> 1;
            return (index >= index2) ? null : new PBQSpliterator((PriorityBlockingQueue<Object>)this.queue, this.array, index, this.index = index2);
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            Object[] array;
            if ((array = this.array) == null) {
                this.fence = (array = this.queue.toArray()).length;
            }
            final int fence;
            int index;
            if ((fence = this.fence) <= array.length && (index = this.index) >= 0 && index < (this.index = fence)) {
                do {
                    consumer.accept((Object)array[index]);
                } while (++index < fence);
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.getFence() > this.index && this.index >= 0) {
                consumer.accept((Object)this.array[this.index++]);
                return true;
            }
            return false;
        }
        
        @Override
        public long estimateSize() {
            return this.getFence() - this.index;
        }
        
        @Override
        public int characteristics() {
            return 16704;
        }
    }
}
