package java.util;

import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class PriorityQueue<E> extends AbstractQueue<E> implements Serializable
{
    private static final long serialVersionUID = -7720805057305804111L;
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    transient Object[] queue;
    private int size;
    private final Comparator<? super E> comparator;
    transient int modCount;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    
    public PriorityQueue() {
        this(11, null);
    }
    
    public PriorityQueue(final int n) {
        this(n, null);
    }
    
    public PriorityQueue(final Comparator<? super E> comparator) {
        this(11, comparator);
    }
    
    public PriorityQueue(final int n, final Comparator<? super E> comparator) {
        this.size = 0;
        this.modCount = 0;
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        this.queue = new Object[n];
        this.comparator = comparator;
    }
    
    public PriorityQueue(final Collection<? extends E> collection) {
        this.size = 0;
        this.modCount = 0;
        if (collection instanceof SortedSet) {
            final SortedSet<? extends E> set = (SortedSet<? extends E>)collection;
            this.comparator = set.comparator();
            this.initElementsFromCollection(set);
        }
        else if (collection instanceof PriorityQueue) {
            final PriorityQueue<? extends E> priorityQueue = (PriorityQueue<? extends E>)collection;
            this.comparator = priorityQueue.comparator();
            this.initFromPriorityQueue(priorityQueue);
        }
        else {
            this.comparator = null;
            this.initFromCollection(collection);
        }
    }
    
    public PriorityQueue(final PriorityQueue<? extends E> priorityQueue) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = (Comparator<? super E>)priorityQueue.comparator();
        this.initFromPriorityQueue(priorityQueue);
    }
    
    public PriorityQueue(final SortedSet<? extends E> set) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = set.comparator();
        this.initElementsFromCollection(set);
    }
    
    private void initFromPriorityQueue(final PriorityQueue<? extends E> priorityQueue) {
        if (priorityQueue.getClass() == PriorityQueue.class) {
            this.queue = priorityQueue.toArray();
            this.size = priorityQueue.size();
        }
        else {
            this.initFromCollection(priorityQueue);
        }
    }
    
    private void initElementsFromCollection(final Collection<? extends E> collection) {
        Object[] queue = collection.toArray();
        if (collection.getClass() != ArrayList.class) {
            queue = Arrays.copyOf(queue, queue.length, (Class<? extends Object[]>)Object[].class);
        }
        final int length = queue.length;
        if (length == 1 || this.comparator != null) {
            for (int i = 0; i < length; ++i) {
                if (queue[i] == null) {
                    throw new NullPointerException();
                }
            }
        }
        this.queue = queue;
        this.size = queue.length;
    }
    
    private void initFromCollection(final Collection<? extends E> collection) {
        this.initElementsFromCollection(collection);
        this.heapify();
    }
    
    private void grow(final int n) {
        final int length = this.queue.length;
        int hugeCapacity = length + ((length < 64) ? (length + 2) : (length >> 1));
        if (hugeCapacity - 2147483639 > 0) {
            hugeCapacity = hugeCapacity(n);
        }
        this.queue = Arrays.copyOf(this.queue, hugeCapacity);
    }
    
    private static int hugeCapacity(final int n) {
        if (n < 0) {
            throw new OutOfMemoryError();
        }
        return (n > 2147483639) ? Integer.MAX_VALUE : 2147483639;
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
        ++this.modCount;
        final int size = this.size;
        if (size >= this.queue.length) {
            this.grow(size + 1);
        }
        this.size = size + 1;
        if (size == 0) {
            this.queue[0] = e;
        }
        else {
            this.siftUp(size, e);
        }
        return true;
    }
    
    @Override
    public E peek() {
        return (E)((this.size == 0) ? null : this.queue[0]);
    }
    
    private int indexOf(final Object o) {
        if (o != null) {
            for (int i = 0; i < this.size; ++i) {
                if (o.equals(this.queue[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    @Override
    public boolean remove(final Object o) {
        final int index = this.indexOf(o);
        if (index == -1) {
            return false;
        }
        this.removeAt(index);
        return true;
    }
    
    boolean removeEq(final Object o) {
        for (int i = 0; i < this.size; ++i) {
            if (o == this.queue[i]) {
                this.removeAt(i);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.indexOf(o) != -1;
    }
    
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.queue, this.size);
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        final int size = this.size;
        if (array.length < size) {
            return (T[])Arrays.copyOf(this.queue, size, array.getClass());
        }
        System.arraycopy(this.queue, 0, array, 0, size);
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public void clear() {
        ++this.modCount;
        for (int i = 0; i < this.size; ++i) {
            this.queue[i] = null;
        }
        this.size = 0;
    }
    
    @Override
    public E poll() {
        if (this.size == 0) {
            return null;
        }
        final int size = this.size - 1;
        this.size = size;
        final int n = size;
        ++this.modCount;
        final Object o = this.queue[0];
        final Object o2 = this.queue[n];
        this.queue[n] = null;
        if (n != 0) {
            this.siftDown(0, (E)o2);
        }
        return (E)o;
    }
    
    private E removeAt(final int n) {
        ++this.modCount;
        final int size = this.size - 1;
        this.size = size;
        final int n2 = size;
        if (n2 == n) {
            this.queue[n] = null;
        }
        else {
            final Object o = this.queue[n2];
            this.queue[n2] = null;
            this.siftDown(n, (E)o);
            if (this.queue[n] == o) {
                this.siftUp(n, (E)o);
                if (this.queue[n] != o) {
                    return (E)o;
                }
            }
        }
        return null;
    }
    
    private void siftUp(final int n, final E e) {
        if (this.comparator != null) {
            this.siftUpUsingComparator(n, e);
        }
        else {
            this.siftUpComparable(n, e);
        }
    }
    
    private void siftUpComparable(int i, final E e) {
        final Comparable comparable = (Comparable)e;
        while (i > 0) {
            final int n = i - 1 >>> 1;
            final Object o = this.queue[n];
            if (comparable.compareTo(o) >= 0) {
                break;
            }
            this.queue[i] = o;
            i = n;
        }
        this.queue[i] = comparable;
    }
    
    private void siftUpUsingComparator(int i, final E e) {
        while (i > 0) {
            final int n = i - 1 >>> 1;
            final Object o = this.queue[n];
            if (this.comparator.compare((Object)e, (Object)o) >= 0) {
                break;
            }
            this.queue[i] = o;
            i = n;
        }
        this.queue[i] = e;
    }
    
    private void siftDown(final int n, final E e) {
        if (this.comparator != null) {
            this.siftDownUsingComparator(n, e);
        }
        else {
            this.siftDownComparable(n, e);
        }
    }
    
    private void siftDownComparable(int i, final E e) {
        final Comparable comparable = (Comparable)e;
        while (i < this.size >>> 1) {
            int n = (i << 1) + 1;
            Object o = this.queue[n];
            final int n2 = n + 1;
            if (n2 < this.size && ((Comparable<Object>)o).compareTo(this.queue[n2]) > 0) {
                o = this.queue[n = n2];
            }
            if (comparable.compareTo(o) <= 0) {
                break;
            }
            this.queue[i] = o;
            i = n;
        }
        this.queue[i] = comparable;
    }
    
    private void siftDownUsingComparator(int i, final E e) {
        while (i < this.size >>> 1) {
            int n = (i << 1) + 1;
            Object o = this.queue[n];
            final int n2 = n + 1;
            if (n2 < this.size && this.comparator.compare((Object)o, (Object)this.queue[n2]) > 0) {
                o = this.queue[n = n2];
            }
            if (this.comparator.compare((Object)e, (Object)o) <= 0) {
                break;
            }
            this.queue[i] = o;
            i = n;
        }
        this.queue[i] = e;
    }
    
    private void heapify() {
        for (int i = (this.size >>> 1) - 1; i >= 0; --i) {
            this.siftDown(i, this.queue[i]);
        }
    }
    
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(Math.max(2, this.size + 1));
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.queue[i]);
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        objectInputStream.readInt();
        SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Object[].class, this.size);
        this.queue = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.queue[i] = objectInputStream.readObject();
        }
        this.heapify();
    }
    
    @Override
    public final Spliterator<E> spliterator() {
        return new PriorityQueueSpliterator<E>(this, 0, -1, 0);
    }
    
    private final class Itr implements Iterator<E>
    {
        private int cursor;
        private int lastRet;
        private ArrayDeque<E> forgetMeNot;
        private E lastRetElt;
        private int expectedModCount;
        
        private Itr() {
            this.cursor = 0;
            this.lastRet = -1;
            this.forgetMeNot = null;
            this.lastRetElt = null;
            this.expectedModCount = PriorityQueue.this.modCount;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor < PriorityQueue.this.size || (this.forgetMeNot != null && !this.forgetMeNot.isEmpty());
        }
        
        @Override
        public E next() {
            if (this.expectedModCount != PriorityQueue.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (this.cursor < PriorityQueue.this.size) {
                final Object[] queue = PriorityQueue.this.queue;
                final int lastRet = this.cursor++;
                this.lastRet = lastRet;
                return (E)queue[lastRet];
            }
            if (this.forgetMeNot != null) {
                this.lastRet = -1;
                this.lastRetElt = this.forgetMeNot.poll();
                if (this.lastRetElt != null) {
                    return this.lastRetElt;
                }
            }
            throw new NoSuchElementException();
        }
        
        @Override
        public void remove() {
            if (this.expectedModCount != PriorityQueue.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (this.lastRet != -1) {
                final Object access$200 = PriorityQueue.this.removeAt(this.lastRet);
                this.lastRet = -1;
                if (access$200 == null) {
                    --this.cursor;
                }
                else {
                    if (this.forgetMeNot == null) {
                        this.forgetMeNot = new ArrayDeque<E>();
                    }
                    this.forgetMeNot.add((E)access$200);
                }
            }
            else {
                if (this.lastRetElt == null) {
                    throw new IllegalStateException();
                }
                PriorityQueue.this.removeEq(this.lastRetElt);
                this.lastRetElt = null;
            }
            this.expectedModCount = PriorityQueue.this.modCount;
        }
    }
    
    static final class PriorityQueueSpliterator<E> implements Spliterator<E>
    {
        private final PriorityQueue<E> pq;
        private int index;
        private int fence;
        private int expectedModCount;
        
        PriorityQueueSpliterator(final PriorityQueue<E> pq, final int index, final int fence, final int expectedModCount) {
            this.pq = pq;
            this.index = index;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }
        
        private int getFence() {
            int fence;
            if ((fence = this.fence) < 0) {
                this.expectedModCount = this.pq.modCount;
                final int access$100 = ((PriorityQueue<Object>)this.pq).size;
                this.fence = access$100;
                fence = access$100;
            }
            return fence;
        }
        
        @Override
        public PriorityQueueSpliterator<E> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int index2 = index + fence >>> 1;
            return (index >= index2) ? null : new PriorityQueueSpliterator<E>((PriorityQueue<Object>)this.pq, index, this.index = index2, this.expectedModCount);
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final PriorityQueue<E> pq;
            final Object[] queue;
            if ((pq = this.pq) != null && (queue = pq.queue) != null) {
                int index;
                int n;
                if ((index = this.fence) < 0) {
                    n = pq.modCount;
                    index = ((PriorityQueue<Object>)pq).size;
                }
                else {
                    n = this.expectedModCount;
                }
                int i;
                if ((i = this.index) >= 0 && (this.index = index) <= queue.length) {
                    while (i < index) {
                        final Object o;
                        if ((o = queue[i]) == null) {
                            throw new ConcurrentModificationException();
                        }
                        consumer.accept((Object)o);
                        ++i;
                    }
                    if (pq.modCount == n) {
                        return;
                    }
                }
            }
            throw new ConcurrentModificationException();
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final int fence = this.getFence();
            final int index = this.index;
            if (index < 0 || index >= fence) {
                return false;
            }
            this.index = index + 1;
            final Object o = this.pq.queue[index];
            if (o == null) {
                throw new ConcurrentModificationException();
            }
            consumer.accept((Object)o);
            if (this.pq.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return true;
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
