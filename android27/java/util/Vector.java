package java.util;

import java.io.*;
import java.util.function.*;

public class Vector<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable
{
    protected Object[] elementData;
    protected int elementCount;
    protected int capacityIncrement;
    private static final long serialVersionUID = -2767605614048989439L;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    
    public Vector(final int n, final int capacityIncrement) {
        if (n < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + n);
        }
        this.elementData = new Object[n];
        this.capacityIncrement = capacityIncrement;
    }
    
    public Vector(final int n) {
        this(n, 0);
    }
    
    public Vector() {
        this(10);
    }
    
    public Vector(final Collection<? extends E> collection) {
        final Object[] array = collection.toArray();
        this.elementCount = array.length;
        if (collection.getClass() == ArrayList.class) {
            this.elementData = array;
        }
        else {
            this.elementData = Arrays.copyOf(array, this.elementCount, (Class<? extends Object[]>)Object[].class);
        }
    }
    
    public synchronized void copyInto(final Object[] array) {
        System.arraycopy(this.elementData, 0, array, 0, this.elementCount);
    }
    
    public synchronized void trimToSize() {
        ++this.modCount;
        if (this.elementCount < this.elementData.length) {
            this.elementData = Arrays.copyOf(this.elementData, this.elementCount);
        }
    }
    
    public synchronized void ensureCapacity(final int n) {
        if (n > 0) {
            ++this.modCount;
            this.ensureCapacityHelper(n);
        }
    }
    
    private void ensureCapacityHelper(final int n) {
        if (n - this.elementData.length > 0) {
            this.grow(n);
        }
    }
    
    private void grow(final int n) {
        final int length = this.elementData.length;
        int hugeCapacity = length + ((this.capacityIncrement > 0) ? this.capacityIncrement : length);
        if (hugeCapacity - n < 0) {
            hugeCapacity = n;
        }
        if (hugeCapacity - 2147483639 > 0) {
            hugeCapacity = hugeCapacity(n);
        }
        this.elementData = Arrays.copyOf(this.elementData, hugeCapacity);
    }
    
    private static int hugeCapacity(final int n) {
        if (n < 0) {
            throw new OutOfMemoryError();
        }
        return (n > 2147483639) ? Integer.MAX_VALUE : 2147483639;
    }
    
    public synchronized void setSize(final int elementCount) {
        ++this.modCount;
        if (elementCount > this.elementCount) {
            this.ensureCapacityHelper(elementCount);
        }
        else {
            for (int i = elementCount; i < this.elementCount; ++i) {
                this.elementData[i] = null;
            }
        }
        this.elementCount = elementCount;
    }
    
    public synchronized int capacity() {
        return this.elementData.length;
    }
    
    @Override
    public synchronized int size() {
        return this.elementCount;
    }
    
    @Override
    public synchronized boolean isEmpty() {
        return this.elementCount == 0;
    }
    
    public Enumeration<E> elements() {
        return new Enumeration<E>() {
            int count = 0;
            
            @Override
            public boolean hasMoreElements() {
                return this.count < Vector.this.elementCount;
            }
            
            @Override
            public E nextElement() {
                synchronized (Vector.this) {
                    if (this.count < Vector.this.elementCount) {
                        return Vector.this.elementData(this.count++);
                    }
                }
                throw new NoSuchElementException("Vector Enumeration");
            }
        };
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.indexOf(o, 0) >= 0;
    }
    
    @Override
    public int indexOf(final Object o) {
        return this.indexOf(o, 0);
    }
    
    public synchronized int indexOf(final Object o, final int n) {
        if (o == null) {
            for (int i = n; i < this.elementCount; ++i) {
                if (this.elementData[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int j = n; j < this.elementCount; ++j) {
                if (o.equals(this.elementData[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    @Override
    public synchronized int lastIndexOf(final Object o) {
        return this.lastIndexOf(o, this.elementCount - 1);
    }
    
    public synchronized int lastIndexOf(final Object o, final int n) {
        if (n >= this.elementCount) {
            throw new IndexOutOfBoundsException(n + " >= " + this.elementCount);
        }
        if (o == null) {
            for (int i = n; i >= 0; --i) {
                if (this.elementData[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int j = n; j >= 0; --j) {
                if (o.equals(this.elementData[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    public synchronized E elementAt(final int n) {
        if (n >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(n + " >= " + this.elementCount);
        }
        return this.elementData(n);
    }
    
    public synchronized E firstElement() {
        if (this.elementCount == 0) {
            throw new NoSuchElementException();
        }
        return this.elementData(0);
    }
    
    public synchronized E lastElement() {
        if (this.elementCount == 0) {
            throw new NoSuchElementException();
        }
        return this.elementData(this.elementCount - 1);
    }
    
    public synchronized void setElementAt(final E e, final int n) {
        if (n >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(n + " >= " + this.elementCount);
        }
        this.elementData[n] = e;
    }
    
    public synchronized void removeElementAt(final int n) {
        ++this.modCount;
        if (n >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(n + " >= " + this.elementCount);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        final int n2 = this.elementCount - n - 1;
        if (n2 > 0) {
            System.arraycopy(this.elementData, n + 1, this.elementData, n, n2);
        }
        --this.elementCount;
        this.elementData[this.elementCount] = null;
    }
    
    public synchronized void insertElementAt(final E e, final int n) {
        ++this.modCount;
        if (n > this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(n + " > " + this.elementCount);
        }
        this.ensureCapacityHelper(this.elementCount + 1);
        System.arraycopy(this.elementData, n, this.elementData, n + 1, this.elementCount - n);
        this.elementData[n] = e;
        ++this.elementCount;
    }
    
    public synchronized void addElement(final E e) {
        ++this.modCount;
        this.ensureCapacityHelper(this.elementCount + 1);
        this.elementData[this.elementCount++] = e;
    }
    
    public synchronized boolean removeElement(final Object o) {
        ++this.modCount;
        final int index = this.indexOf(o);
        if (index >= 0) {
            this.removeElementAt(index);
            return true;
        }
        return false;
    }
    
    public synchronized void removeAllElements() {
        ++this.modCount;
        for (int i = 0; i < this.elementCount; ++i) {
            this.elementData[i] = null;
        }
        this.elementCount = 0;
    }
    
    public synchronized Object clone() {
        try {
            final Vector vector = (Vector)super.clone();
            vector.elementData = Arrays.copyOf(this.elementData, this.elementCount);
            vector.modCount = 0;
            return vector;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    @Override
    public synchronized Object[] toArray() {
        return Arrays.copyOf(this.elementData, this.elementCount);
    }
    
    @Override
    public synchronized <T> T[] toArray(final T[] array) {
        if (array.length < this.elementCount) {
            return Arrays.copyOf(this.elementData, this.elementCount, (Class<? extends T[]>)array.getClass());
        }
        System.arraycopy(this.elementData, 0, array, 0, this.elementCount);
        if (array.length > this.elementCount) {
            array[this.elementCount] = null;
        }
        return array;
    }
    
    E elementData(final int n) {
        return (E)this.elementData[n];
    }
    
    @Override
    public synchronized E get(final int n) {
        if (n >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return this.elementData(n);
    }
    
    @Override
    public synchronized E set(final int n, final E e) {
        if (n >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        final E elementData = this.elementData(n);
        this.elementData[n] = e;
        return elementData;
    }
    
    @Override
    public synchronized boolean add(final E e) {
        ++this.modCount;
        this.ensureCapacityHelper(this.elementCount + 1);
        this.elementData[this.elementCount++] = e;
        return true;
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.removeElement(o);
    }
    
    @Override
    public void add(final int n, final E e) {
        this.insertElementAt(e, n);
    }
    
    @Override
    public synchronized E remove(final int n) {
        ++this.modCount;
        if (n >= this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        final E elementData = this.elementData(n);
        final int n2 = this.elementCount - n - 1;
        if (n2 > 0) {
            System.arraycopy(this.elementData, n + 1, this.elementData, n, n2);
        }
        this.elementData[--this.elementCount] = null;
        return elementData;
    }
    
    @Override
    public void clear() {
        this.removeAllElements();
    }
    
    @Override
    public synchronized boolean containsAll(final Collection<?> collection) {
        return super.containsAll(collection);
    }
    
    @Override
    public synchronized boolean addAll(final Collection<? extends E> collection) {
        ++this.modCount;
        final Object[] array = collection.toArray();
        final int length = array.length;
        this.ensureCapacityHelper(this.elementCount + length);
        System.arraycopy(array, 0, this.elementData, this.elementCount, length);
        this.elementCount += length;
        return length != 0;
    }
    
    @Override
    public synchronized boolean removeAll(final Collection<?> collection) {
        return super.removeAll(collection);
    }
    
    @Override
    public synchronized boolean retainAll(final Collection<?> collection) {
        return super.retainAll(collection);
    }
    
    @Override
    public synchronized boolean addAll(final int n, final Collection<? extends E> collection) {
        ++this.modCount;
        if (n < 0 || n > this.elementCount) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        this.ensureCapacityHelper(this.elementCount + length);
        final int n2 = this.elementCount - n;
        if (n2 > 0) {
            System.arraycopy(this.elementData, n, this.elementData, n + length, n2);
        }
        System.arraycopy(array, 0, this.elementData, n, length);
        this.elementCount += length;
        return length != 0;
    }
    
    @Override
    public synchronized boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public synchronized int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public synchronized String toString() {
        return super.toString();
    }
    
    @Override
    public synchronized List<E> subList(final int n, final int n2) {
        return Collections.synchronizedList(super.subList(n, n2), this);
    }
    
    @Override
    protected synchronized void removeRange(final int n, final int n2) {
        ++this.modCount;
        System.arraycopy(this.elementData, n2, this.elementData, n, this.elementCount - n2);
        while (this.elementCount != this.elementCount - (n2 - n)) {
            this.elementData[--this.elementCount] = null;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final int value = fields.get("elementCount", 0);
        final Object[] array = (Object[])fields.get("elementData", null);
        if (value < 0 || array == null || value > array.length) {
            throw new StreamCorruptedException("Inconsistent vector internals");
        }
        this.elementCount = value;
        this.elementData = array.clone();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        final Object[] array;
        synchronized (this) {
            putFields.put("capacityIncrement", this.capacityIncrement);
            putFields.put("elementCount", this.elementCount);
            array = this.elementData.clone();
        }
        putFields.put("elementData", array);
        objectOutputStream.writeFields();
    }
    
    @Override
    public synchronized ListIterator<E> listIterator(final int n) {
        if (n < 0 || n > this.elementCount) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
        return new ListItr(n);
    }
    
    @Override
    public synchronized ListIterator<E> listIterator() {
        return new ListItr(0);
    }
    
    @Override
    public synchronized Iterator<E> iterator() {
        return new Itr();
    }
    
    @Override
    public synchronized void forEach(final Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        final int modCount = this.modCount;
        final Object[] array = this.elementData;
        for (int elementCount = this.elementCount, n = 0; this.modCount == modCount && n < elementCount; ++n) {
            consumer.accept((Object)array[n]);
        }
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
    }
    
    @Override
    public synchronized boolean removeIf(final Predicate<? super E> predicate) {
        Objects.requireNonNull(predicate);
        int n = 0;
        final int elementCount = this.elementCount;
        final BitSet set = new BitSet(elementCount);
        final int modCount = this.modCount;
        for (int n2 = 0; this.modCount == modCount && n2 < elementCount; ++n2) {
            if (predicate.test((Object)this.elementData[n2])) {
                set.set(n2);
                ++n;
            }
        }
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
        final boolean b = n > 0;
        if (b) {
            final int elementCount2 = elementCount - n;
            for (int nextClearBit = 0, n3 = 0; nextClearBit < elementCount && n3 < elementCount2; nextClearBit = set.nextClearBit(nextClearBit), this.elementData[n3] = this.elementData[nextClearBit], ++nextClearBit, ++n3) {}
            for (int i = elementCount2; i < elementCount; ++i) {
                this.elementData[i] = null;
            }
            this.elementCount = elementCount2;
            if (this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
            ++this.modCount;
        }
        return b;
    }
    
    @Override
    public synchronized void replaceAll(final UnaryOperator<E> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        final int modCount = this.modCount;
        for (int elementCount = this.elementCount, n = 0; this.modCount == modCount && n < elementCount; ++n) {
            this.elementData[n] = unaryOperator.apply((E)this.elementData[n]);
        }
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
        ++this.modCount;
    }
    
    @Override
    public synchronized void sort(final Comparator<? super E> comparator) {
        final int modCount = this.modCount;
        Arrays.sort(this.elementData, 0, this.elementCount, (Comparator<? super Object>)comparator);
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
        ++this.modCount;
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new VectorSpliterator<E>(this, null, 0, -1, 0);
    }
    
    private class Itr implements Iterator<E>
    {
        int cursor;
        int lastRet;
        int expectedModCount;
        
        private Itr() {
            this.lastRet = -1;
            this.expectedModCount = Vector.this.modCount;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor != Vector.this.elementCount;
        }
        
        @Override
        public E next() {
            synchronized (Vector.this) {
                this.checkForComodification();
                final int cursor = this.cursor;
                if (cursor >= Vector.this.elementCount) {
                    throw new NoSuchElementException();
                }
                this.cursor = cursor + 1;
                final Vector this$0 = Vector.this;
                final int lastRet = cursor;
                this.lastRet = lastRet;
                return this$0.elementData(lastRet);
            }
        }
        
        @Override
        public void remove() {
            if (this.lastRet == -1) {
                throw new IllegalStateException();
            }
            synchronized (Vector.this) {
                this.checkForComodification();
                Vector.this.remove(this.lastRet);
                this.expectedModCount = Vector.this.modCount;
            }
            this.cursor = this.lastRet;
            this.lastRet = -1;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            synchronized (Vector.this) {
                final int elementCount = Vector.this.elementCount;
                int cursor = this.cursor;
                if (cursor >= elementCount) {
                    return;
                }
                final Object[] array = Vector.this.elementData;
                if (cursor >= array.length) {
                    throw new ConcurrentModificationException();
                }
                while (cursor != elementCount && Vector.this.modCount == this.expectedModCount) {
                    consumer.accept((Object)array[cursor++]);
                }
                this.cursor = cursor;
                this.lastRet = cursor - 1;
                this.checkForComodification();
            }
        }
        
        final void checkForComodification() {
            if (Vector.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    final class ListItr extends Itr implements ListIterator<E>
    {
        ListItr(final int cursor) {
            this.cursor = cursor;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.cursor != 0;
        }
        
        @Override
        public int nextIndex() {
            return this.cursor;
        }
        
        @Override
        public int previousIndex() {
            return this.cursor - 1;
        }
        
        @Override
        public E previous() {
            synchronized (Vector.this) {
                this.checkForComodification();
                final int cursor = this.cursor - 1;
                if (cursor < 0) {
                    throw new NoSuchElementException();
                }
                this.cursor = cursor;
                final Vector this$0 = Vector.this;
                final int lastRet = cursor;
                this.lastRet = lastRet;
                return this$0.elementData(lastRet);
            }
        }
        
        @Override
        public void set(final E e) {
            if (this.lastRet == -1) {
                throw new IllegalStateException();
            }
            synchronized (Vector.this) {
                this.checkForComodification();
                Vector.this.set(this.lastRet, e);
            }
        }
        
        @Override
        public void add(final E e) {
            final int cursor = this.cursor;
            synchronized (Vector.this) {
                this.checkForComodification();
                Vector.this.add(cursor, e);
                this.expectedModCount = Vector.this.modCount;
            }
            this.cursor = cursor + 1;
            this.lastRet = -1;
        }
    }
    
    static final class VectorSpliterator<E> implements Spliterator<E>
    {
        private final Vector<E> list;
        private Object[] array;
        private int index;
        private int fence;
        private int expectedModCount;
        
        VectorSpliterator(final Vector<E> list, final Object[] array, final int index, final int fence, final int expectedModCount) {
            this.list = list;
            this.array = array;
            this.index = index;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }
        
        private int getFence() {
            int fence;
            if ((fence = this.fence) < 0) {
                synchronized (this.list) {
                    this.array = this.list.elementData;
                    this.expectedModCount = this.list.modCount;
                    final int elementCount = this.list.elementCount;
                    this.fence = elementCount;
                    fence = elementCount;
                }
            }
            return fence;
        }
        
        @Override
        public Spliterator<E> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int index2 = index + fence >>> 1;
            return (index >= index2) ? null : new VectorSpliterator((Vector<Object>)this.list, this.array, index, this.index = index2, this.expectedModCount);
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final int index;
            if (this.getFence() <= (index = this.index)) {
                return false;
            }
            this.index = index + 1;
            consumer.accept((Object)this.array[index]);
            if (this.list.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return true;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Vector<E> list;
            if ((list = this.list) != null) {
                int fence;
                Object[] array;
                if ((fence = this.fence) < 0) {
                    synchronized (list) {
                        this.expectedModCount = list.modCount;
                        final Object[] elementData = list.elementData;
                        this.array = elementData;
                        array = elementData;
                        final int elementCount = list.elementCount;
                        this.fence = elementCount;
                        fence = elementCount;
                    }
                }
                else {
                    array = this.array;
                }
                int i;
                if (array != null && (i = this.index) >= 0 && (this.index = fence) <= array.length) {
                    while (i < fence) {
                        consumer.accept((Object)array[i++]);
                    }
                    if (list.modCount == this.expectedModCount) {
                        return;
                    }
                }
            }
            throw new ConcurrentModificationException();
        }
        
        @Override
        public long estimateSize() {
            return this.getFence() - this.index;
        }
        
        @Override
        public int characteristics() {
            return 16464;
        }
    }
}
