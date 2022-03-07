package java.util;

import java.lang.reflect.*;
import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class ArrayDeque<E> extends AbstractCollection<E> implements Deque<E>, Cloneable, Serializable
{
    transient Object[] elements;
    transient int head;
    transient int tail;
    private static final int MIN_INITIAL_CAPACITY = 8;
    private static final long serialVersionUID = 2340985798034038923L;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    private static int calculateSize(final int n) {
        int n2 = 8;
        if (n >= n2) {
            final int n3 = n | n >>> 1;
            final int n4 = n3 | n3 >>> 2;
            final int n5 = n4 | n4 >>> 4;
            final int n6 = n5 | n5 >>> 8;
            n2 = (n6 | n6 >>> 16);
            if (++n2 < 0) {
                n2 >>>= 1;
            }
        }
        return n2;
    }
    
    private void allocateElements(final int n) {
        this.elements = new Object[calculateSize(n)];
    }
    
    private void doubleCapacity() {
        assert this.head == this.tail;
        final int head = this.head;
        final int length = this.elements.length;
        final int n = length - head;
        final int n2 = length << 1;
        if (n2 < 0) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        final Object[] elements = new Object[n2];
        System.arraycopy(this.elements, head, elements, 0, n);
        System.arraycopy(this.elements, 0, elements, n, head);
        this.elements = elements;
        this.head = 0;
        this.tail = length;
    }
    
    private <T> T[] copyElements(final T[] array) {
        if (this.head < this.tail) {
            System.arraycopy(this.elements, this.head, array, 0, this.size());
        }
        else if (this.head > this.tail) {
            final int n = this.elements.length - this.head;
            System.arraycopy(this.elements, this.head, array, 0, n);
            System.arraycopy(this.elements, 0, array, n, this.tail);
        }
        return array;
    }
    
    public ArrayDeque() {
        this.elements = new Object[16];
    }
    
    public ArrayDeque(final int n) {
        this.allocateElements(n);
    }
    
    public ArrayDeque(final Collection<? extends E> collection) {
        this.allocateElements(collection.size());
        this.addAll(collection);
    }
    
    @Override
    public void addFirst(final E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        this.elements[this.head = (this.head - 1 & this.elements.length - 1)] = e;
        if (this.head == this.tail) {
            this.doubleCapacity();
        }
    }
    
    @Override
    public void addLast(final E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        this.elements[this.tail] = e;
        if ((this.tail = (this.tail + 1 & this.elements.length - 1)) == this.head) {
            this.doubleCapacity();
        }
    }
    
    @Override
    public boolean offerFirst(final E e) {
        this.addFirst(e);
        return true;
    }
    
    @Override
    public boolean offerLast(final E e) {
        this.addLast(e);
        return true;
    }
    
    @Override
    public E removeFirst() {
        final E pollFirst = this.pollFirst();
        if (pollFirst == null) {
            throw new NoSuchElementException();
        }
        return pollFirst;
    }
    
    @Override
    public E removeLast() {
        final E pollLast = this.pollLast();
        if (pollLast == null) {
            throw new NoSuchElementException();
        }
        return pollLast;
    }
    
    @Override
    public E pollFirst() {
        final int head = this.head;
        final Object o = this.elements[head];
        if (o == null) {
            return null;
        }
        this.elements[head] = null;
        this.head = (head + 1 & this.elements.length - 1);
        return (E)o;
    }
    
    @Override
    public E pollLast() {
        final int tail = this.tail - 1 & this.elements.length - 1;
        final Object o = this.elements[tail];
        if (o == null) {
            return null;
        }
        this.elements[tail] = null;
        this.tail = tail;
        return (E)o;
    }
    
    @Override
    public E getFirst() {
        final Object o = this.elements[this.head];
        if (o == null) {
            throw new NoSuchElementException();
        }
        return (E)o;
    }
    
    @Override
    public E getLast() {
        final Object o = this.elements[this.tail - 1 & this.elements.length - 1];
        if (o == null) {
            throw new NoSuchElementException();
        }
        return (E)o;
    }
    
    @Override
    public E peekFirst() {
        return (E)this.elements[this.head];
    }
    
    @Override
    public E peekLast() {
        return (E)this.elements[this.tail - 1 & this.elements.length - 1];
    }
    
    @Override
    public boolean removeFirstOccurrence(final Object o) {
        if (o == null) {
            return false;
        }
        Object o2;
        for (int n = this.elements.length - 1, head = this.head; (o2 = this.elements[head]) != null; head = (head + 1 & n)) {
            if (o.equals(o2)) {
                this.delete(head);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean removeLastOccurrence(final Object o) {
        if (o == null) {
            return false;
        }
        Object o2;
        for (int n = this.elements.length - 1, n2 = this.tail - 1 & n; (o2 = this.elements[n2]) != null; n2 = (n2 - 1 & n)) {
            if (o.equals(o2)) {
                this.delete(n2);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean add(final E e) {
        this.addLast(e);
        return true;
    }
    
    @Override
    public boolean offer(final E e) {
        return this.offerLast(e);
    }
    
    @Override
    public E remove() {
        return this.removeFirst();
    }
    
    @Override
    public E poll() {
        return this.pollFirst();
    }
    
    @Override
    public E element() {
        return this.getFirst();
    }
    
    @Override
    public E peek() {
        return this.peekFirst();
    }
    
    @Override
    public void push(final E e) {
        this.addFirst(e);
    }
    
    @Override
    public E pop() {
        return this.removeFirst();
    }
    
    private void checkInvariants() {
        assert this.elements[this.tail] == null;
        Label_0100: {
            if (!ArrayDeque.$assertionsDisabled) {
                if (this.head == this.tail) {
                    if (this.elements[this.head] == null) {
                        break Label_0100;
                    }
                }
                else if (this.elements[this.head] != null && this.elements[this.tail - 1 & this.elements.length - 1] != null) {
                    break Label_0100;
                }
                throw new AssertionError();
            }
        }
        assert this.elements[this.head - 1 & this.elements.length - 1] == null;
    }
    
    private boolean delete(final int n) {
        this.checkInvariants();
        final Object[] elements = this.elements;
        final int n2 = elements.length - 1;
        final int head = this.head;
        final int tail = this.tail;
        final int n3 = n - head & n2;
        final int n4 = tail - n & n2;
        if (n3 >= (tail - head & n2)) {
            throw new ConcurrentModificationException();
        }
        if (n3 < n4) {
            if (head <= n) {
                System.arraycopy(elements, head, elements, head + 1, n3);
            }
            else {
                System.arraycopy(elements, 0, elements, 1, n);
                elements[0] = elements[n2];
                System.arraycopy(elements, head, elements, head + 1, n2 - head);
            }
            elements[head] = null;
            this.head = (head + 1 & n2);
            return false;
        }
        if (n < tail) {
            System.arraycopy(elements, n + 1, elements, n, n4);
            this.tail = tail - 1;
        }
        else {
            System.arraycopy(elements, n + 1, elements, n, n2 - n);
            elements[n2] = elements[0];
            System.arraycopy(elements, 1, elements, 0, tail);
            this.tail = (tail - 1 & n2);
        }
        return true;
    }
    
    @Override
    public int size() {
        return this.tail - this.head & this.elements.length - 1;
    }
    
    @Override
    public boolean isEmpty() {
        return this.head == this.tail;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new DeqIterator();
    }
    
    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        Object o2;
        for (int n = this.elements.length - 1, head = this.head; (o2 = this.elements[head]) != null; head = (head + 1 & n)) {
            if (o.equals(o2)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.removeFirstOccurrence(o);
    }
    
    @Override
    public void clear() {
        final int head = this.head;
        final int tail = this.tail;
        if (head != tail) {
            final boolean b = false;
            this.tail = (b ? 1 : 0);
            this.head = (b ? 1 : 0);
            int i = head;
            final int n = this.elements.length - 1;
            do {
                this.elements[i] = null;
                i = (i + 1 & n);
            } while (i != tail);
        }
    }
    
    @Override
    public Object[] toArray() {
        return this.copyElements(new Object[this.size()]);
    }
    
    @Override
    public <T> T[] toArray(T[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = (T[])Array.newInstance(array.getClass().getComponentType(), size);
        }
        this.copyElements((Object[])array);
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }
    
    public ArrayDeque<E> clone() {
        try {
            final ArrayDeque arrayDeque = (ArrayDeque)super.clone();
            arrayDeque.elements = Arrays.copyOf(this.elements, this.elements.length);
            return arrayDeque;
        }
        catch (CloneNotSupportedException ex) {
            throw new AssertionError();
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size());
        for (int n = this.elements.length - 1, i = this.head; i != this.tail; i = (i + 1 & n)) {
            objectOutputStream.writeObject(this.elements[i]);
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int int1 = objectInputStream.readInt();
        SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Object[].class, calculateSize(int1));
        this.allocateElements(int1);
        this.head = 0;
        this.tail = int1;
        for (int i = 0; i < int1; ++i) {
            this.elements[i] = objectInputStream.readObject();
        }
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new DeqSpliterator<E>(this, -1, -1);
    }
    
    private class DeqIterator implements Iterator<E>
    {
        private int cursor;
        private int fence;
        private int lastRet;
        
        private DeqIterator() {
            this.cursor = ArrayDeque.this.head;
            this.fence = ArrayDeque.this.tail;
            this.lastRet = -1;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor != this.fence;
        }
        
        @Override
        public E next() {
            if (this.cursor == this.fence) {
                throw new NoSuchElementException();
            }
            final Object o = ArrayDeque.this.elements[this.cursor];
            if (ArrayDeque.this.tail != this.fence || o == null) {
                throw new ConcurrentModificationException();
            }
            this.lastRet = this.cursor;
            this.cursor = (this.cursor + 1 & ArrayDeque.this.elements.length - 1);
            return (E)o;
        }
        
        @Override
        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            if (ArrayDeque.this.delete(this.lastRet)) {
                this.cursor = (this.cursor - 1 & ArrayDeque.this.elements.length - 1);
                this.fence = ArrayDeque.this.tail;
            }
            this.lastRet = -1;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final Object[] elements = ArrayDeque.this.elements;
            final int n = elements.length - 1;
            final int fence = this.fence;
            int i = this.cursor;
            this.cursor = fence;
            while (i != fence) {
                final Object o = elements[i];
                i = (i + 1 & n);
                if (o == null) {
                    throw new ConcurrentModificationException();
                }
                consumer.accept((Object)o);
            }
        }
    }
    
    static final class DeqSpliterator<E> implements Spliterator<E>
    {
        private final ArrayDeque<E> deq;
        private int fence;
        private int index;
        
        DeqSpliterator(final ArrayDeque<E> deq, final int index, final int fence) {
            this.deq = deq;
            this.index = index;
            this.fence = fence;
        }
        
        private int getFence() {
            int fence;
            if ((fence = this.fence) < 0) {
                final int tail = this.deq.tail;
                this.fence = tail;
                fence = tail;
                this.index = this.deq.head;
            }
            return fence;
        }
        
        @Override
        public DeqSpliterator<E> trySplit() {
            int fence = this.getFence();
            final int index = this.index;
            final int length = this.deq.elements.length;
            if (index != fence && (index + 1 & length - 1) != fence) {
                if (index > fence) {
                    fence += length;
                }
                return new DeqSpliterator<E>((ArrayDeque<Object>)this.deq, index, this.index = (index + fence >>> 1 & length - 1));
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Object[] elements = this.deq.elements;
            final int n = elements.length - 1;
            final int fence = this.getFence();
            int i = this.index;
            this.index = fence;
            while (i != fence) {
                final Object o = elements[i];
                i = (i + 1 & n);
                if (o == null) {
                    throw new ConcurrentModificationException();
                }
                consumer.accept((Object)o);
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Object[] elements = this.deq.elements;
            final int n = elements.length - 1;
            this.getFence();
            final int index = this.index;
            if (index == this.fence) {
                return false;
            }
            final Object o = elements[index];
            this.index = (index + 1 & n);
            if (o == null) {
                throw new ConcurrentModificationException();
            }
            consumer.accept((Object)o);
            return true;
        }
        
        @Override
        public long estimateSize() {
            int n = this.getFence() - this.index;
            if (n < 0) {
                n += this.deq.elements.length;
            }
            return n;
        }
        
        @Override
        public int characteristics() {
            return 16720;
        }
    }
    
    private class DescendingIterator implements Iterator<E>
    {
        private int cursor;
        private int fence;
        private int lastRet;
        
        private DescendingIterator() {
            this.cursor = ArrayDeque.this.tail;
            this.fence = ArrayDeque.this.head;
            this.lastRet = -1;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor != this.fence;
        }
        
        @Override
        public E next() {
            if (this.cursor == this.fence) {
                throw new NoSuchElementException();
            }
            this.cursor = (this.cursor - 1 & ArrayDeque.this.elements.length - 1);
            final Object o = ArrayDeque.this.elements[this.cursor];
            if (ArrayDeque.this.head != this.fence || o == null) {
                throw new ConcurrentModificationException();
            }
            this.lastRet = this.cursor;
            return (E)o;
        }
        
        @Override
        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            if (!ArrayDeque.this.delete(this.lastRet)) {
                this.cursor = (this.cursor + 1 & ArrayDeque.this.elements.length - 1);
                this.fence = ArrayDeque.this.head;
            }
            this.lastRet = -1;
        }
    }
}
