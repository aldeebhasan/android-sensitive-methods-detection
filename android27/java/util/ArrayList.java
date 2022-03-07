package java.util;

import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable
{
    private static final long serialVersionUID = 8683452581122892189L;
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA;
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    transient Object[] elementData;
    private int size;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    
    public ArrayList(final int n) {
        if (n > 0) {
            this.elementData = new Object[n];
        }
        else {
            if (n != 0) {
                throw new IllegalArgumentException("Illegal Capacity: " + n);
            }
            this.elementData = ArrayList.EMPTY_ELEMENTDATA;
        }
    }
    
    public ArrayList() {
        this.elementData = ArrayList.DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
    
    public ArrayList(final Collection<? extends E> collection) {
        final Object[] array = collection.toArray();
        final int length = array.length;
        this.size = length;
        if (length != 0) {
            if (collection.getClass() == ArrayList.class) {
                this.elementData = array;
            }
            else {
                this.elementData = Arrays.copyOf(array, this.size, (Class<? extends Object[]>)Object[].class);
            }
        }
        else {
            this.elementData = ArrayList.EMPTY_ELEMENTDATA;
        }
    }
    
    public void trimToSize() {
        ++this.modCount;
        if (this.size < this.elementData.length) {
            this.elementData = ((this.size == 0) ? ArrayList.EMPTY_ELEMENTDATA : Arrays.copyOf(this.elementData, this.size));
        }
    }
    
    public void ensureCapacity(final int n) {
        if (n > ((this.elementData != ArrayList.DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0 : 10)) {
            this.ensureExplicitCapacity(n);
        }
    }
    
    private static int calculateCapacity(final Object[] array, final int n) {
        if (array == ArrayList.DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(10, n);
        }
        return n;
    }
    
    private void ensureCapacityInternal(final int n) {
        this.ensureExplicitCapacity(calculateCapacity(this.elementData, n));
    }
    
    private void ensureExplicitCapacity(final int n) {
        ++this.modCount;
        if (n - this.elementData.length > 0) {
            this.grow(n);
        }
    }
    
    private void grow(final int n) {
        final int length = this.elementData.length;
        int hugeCapacity = length + (length >> 1);
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
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.indexOf(o) >= 0;
    }
    
    @Override
    public int indexOf(final Object o) {
        if (o == null) {
            for (int i = 0; i < this.size; ++i) {
                if (this.elementData[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int j = 0; j < this.size; ++j) {
                if (o.equals(this.elementData[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        if (o == null) {
            for (int i = this.size - 1; i >= 0; --i) {
                if (this.elementData[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int j = this.size - 1; j >= 0; --j) {
                if (o.equals(this.elementData[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    public Object clone() {
        try {
            final ArrayList list = (ArrayList)super.clone();
            list.elementData = Arrays.copyOf(this.elementData, this.size);
            list.modCount = 0;
            return list;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.elementData, this.size);
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        if (array.length < this.size) {
            return Arrays.copyOf(this.elementData, this.size, (Class<? extends T[]>)array.getClass());
        }
        System.arraycopy(this.elementData, 0, array, 0, this.size);
        if (array.length > this.size) {
            array[this.size] = null;
        }
        return array;
    }
    
    E elementData(final int n) {
        return (E)this.elementData[n];
    }
    
    @Override
    public E get(final int n) {
        this.rangeCheck(n);
        return this.elementData(n);
    }
    
    @Override
    public E set(final int n, final E e) {
        this.rangeCheck(n);
        final E elementData = this.elementData(n);
        this.elementData[n] = e;
        return elementData;
    }
    
    @Override
    public boolean add(final E e) {
        this.ensureCapacityInternal(this.size + 1);
        this.elementData[this.size++] = e;
        return true;
    }
    
    @Override
    public void add(final int n, final E e) {
        this.rangeCheckForAdd(n);
        this.ensureCapacityInternal(this.size + 1);
        System.arraycopy(this.elementData, n, this.elementData, n + 1, this.size - n);
        this.elementData[n] = e;
        ++this.size;
    }
    
    @Override
    public E remove(final int n) {
        this.rangeCheck(n);
        ++this.modCount;
        final E elementData = this.elementData(n);
        final int n2 = this.size - n - 1;
        if (n2 > 0) {
            System.arraycopy(this.elementData, n + 1, this.elementData, n, n2);
        }
        this.elementData[--this.size] = null;
        return elementData;
    }
    
    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            for (int i = 0; i < this.size; ++i) {
                if (this.elementData[i] == null) {
                    this.fastRemove(i);
                    return true;
                }
            }
        }
        else {
            for (int j = 0; j < this.size; ++j) {
                if (o.equals(this.elementData[j])) {
                    this.fastRemove(j);
                    return true;
                }
            }
        }
        return false;
    }
    
    private void fastRemove(final int n) {
        ++this.modCount;
        final int n2 = this.size - n - 1;
        if (n2 > 0) {
            System.arraycopy(this.elementData, n + 1, this.elementData, n, n2);
        }
        this.elementData[--this.size] = null;
    }
    
    @Override
    public void clear() {
        ++this.modCount;
        for (int i = 0; i < this.size; ++i) {
            this.elementData[i] = null;
        }
        this.size = 0;
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        final Object[] array = collection.toArray();
        final int length = array.length;
        this.ensureCapacityInternal(this.size + length);
        System.arraycopy(array, 0, this.elementData, this.size, length);
        this.size += length;
        return length != 0;
    }
    
    @Override
    public boolean addAll(final int n, final Collection<? extends E> collection) {
        this.rangeCheckForAdd(n);
        final Object[] array = collection.toArray();
        final int length = array.length;
        this.ensureCapacityInternal(this.size + length);
        final int n2 = this.size - n;
        if (n2 > 0) {
            System.arraycopy(this.elementData, n, this.elementData, n + length, n2);
        }
        System.arraycopy(array, 0, this.elementData, n, length);
        this.size += length;
        return length != 0;
    }
    
    @Override
    protected void removeRange(final int n, final int n2) {
        ++this.modCount;
        System.arraycopy(this.elementData, n2, this.elementData, n, this.size - n2);
        int i;
        int size;
        for (size = (i = this.size - (n2 - n)); i < this.size; ++i) {
            this.elementData[i] = null;
        }
        this.size = size;
    }
    
    private void rangeCheck(final int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }
    }
    
    private void rangeCheckForAdd(final int n) {
        if (n > this.size || n < 0) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }
    }
    
    private String outOfBoundsMsg(final int n) {
        return "Index: " + n + ", Size: " + this.size;
    }
    
    @Override
    public boolean removeAll(final Collection<?> collection) {
        Objects.requireNonNull(collection);
        return this.batchRemove(collection, false);
    }
    
    @Override
    public boolean retainAll(final Collection<?> collection) {
        Objects.requireNonNull(collection);
        return this.batchRemove(collection, true);
    }
    
    private boolean batchRemove(final Collection<?> collection, final boolean b) {
        final Object[] elementData = this.elementData;
        int i = 0;
        int size = 0;
        boolean b2 = false;
        try {
            while (i < this.size) {
                if (collection.contains(elementData[i]) == b) {
                    elementData[size++] = elementData[i];
                }
                ++i;
            }
        }
        finally {
            if (i != this.size) {
                System.arraycopy(elementData, i, elementData, size, this.size - i);
                size += this.size - i;
            }
            if (size != this.size) {
                for (int j = size; j < this.size; ++j) {
                    elementData[j] = null;
                }
                this.modCount += this.size - size;
                this.size = size;
                b2 = true;
            }
        }
        return b2;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final int modCount = this.modCount;
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.elementData[i]);
        }
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.elementData = ArrayList.EMPTY_ELEMENTDATA;
        objectInputStream.defaultReadObject();
        objectInputStream.readInt();
        if (this.size > 0) {
            SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Object[].class, calculateCapacity(this.elementData, this.size));
            this.ensureCapacityInternal(this.size);
            final Object[] elementData = this.elementData;
            for (int i = 0; i < this.size; ++i) {
                elementData[i] = objectInputStream.readObject();
            }
        }
    }
    
    @Override
    public ListIterator<E> listIterator(final int n) {
        if (n < 0 || n > this.size) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
        return new ListItr(n);
    }
    
    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    @Override
    public List<E> subList(final int n, final int n2) {
        subListRangeCheck(n, n2, this.size);
        return new SubList(this, 0, n, n2);
    }
    
    static void subListRangeCheck(final int n, final int n2, final int n3) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + n);
        }
        if (n2 > n3) {
            throw new IndexOutOfBoundsException("toIndex = " + n2);
        }
        if (n > n2) {
            throw new IllegalArgumentException("fromIndex(" + n + ") > toIndex(" + n2 + ")");
        }
    }
    
    @Override
    public void forEach(final Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        final int modCount = this.modCount;
        final Object[] array = this.elementData;
        for (int size = this.size, n = 0; this.modCount == modCount && n < size; ++n) {
            consumer.accept((Object)array[n]);
        }
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new ArrayListSpliterator<E>(this, 0, -1, 0);
    }
    
    @Override
    public boolean removeIf(final Predicate<? super E> predicate) {
        Objects.requireNonNull(predicate);
        int n = 0;
        final BitSet set = new BitSet(this.size);
        final int modCount = this.modCount;
        final int size = this.size;
        for (int n2 = 0; this.modCount == modCount && n2 < size; ++n2) {
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
            final int size2 = size - n;
            for (int nextClearBit = 0, n3 = 0; nextClearBit < size && n3 < size2; nextClearBit = set.nextClearBit(nextClearBit), this.elementData[n3] = this.elementData[nextClearBit], ++nextClearBit, ++n3) {}
            for (int i = size2; i < size; ++i) {
                this.elementData[i] = null;
            }
            this.size = size2;
            if (this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
            ++this.modCount;
        }
        return b;
    }
    
    @Override
    public void replaceAll(final UnaryOperator<E> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        final int modCount = this.modCount;
        for (int size = this.size, n = 0; this.modCount == modCount && n < size; ++n) {
            this.elementData[n] = unaryOperator.apply((E)this.elementData[n]);
        }
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
        ++this.modCount;
    }
    
    @Override
    public void sort(final Comparator<? super E> comparator) {
        final int modCount = this.modCount;
        Arrays.sort(this.elementData, 0, this.size, (Comparator<? super Object>)comparator);
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
        ++this.modCount;
    }
    
    static {
        EMPTY_ELEMENTDATA = new Object[0];
        DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new Object[0];
    }
    
    static final class ArrayListSpliterator<E> implements Spliterator<E>
    {
        private final ArrayList<E> list;
        private int index;
        private int fence;
        private int expectedModCount;
        
        ArrayListSpliterator(final ArrayList<E> list, final int index, final int fence, final int expectedModCount) {
            this.list = list;
            this.index = index;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }
        
        private int getFence() {
            int fence;
            if ((fence = this.fence) < 0) {
                final ArrayList<E> list;
                if ((list = this.list) == null) {
                    final boolean fence2 = false;
                    this.fence = (fence2 ? 1 : 0);
                    fence = (fence2 ? 1 : 0);
                }
                else {
                    this.expectedModCount = list.modCount;
                    final int access$000 = ((ArrayList<Object>)list).size;
                    this.fence = access$000;
                    fence = access$000;
                }
            }
            return fence;
        }
        
        @Override
        public ArrayListSpliterator<E> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int index2 = index + fence >>> 1;
            return (index >= index2) ? null : new ArrayListSpliterator<E>((ArrayList<Object>)this.list, index, this.index = index2, this.expectedModCount);
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final int fence = this.getFence();
            final int index = this.index;
            if (index >= fence) {
                return false;
            }
            this.index = index + 1;
            consumer.accept((Object)this.list.elementData[index]);
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
            final ArrayList<E> list;
            final Object[] elementData;
            if ((list = this.list) != null && (elementData = list.elementData) != null) {
                int index;
                int n;
                if ((index = this.fence) < 0) {
                    n = list.modCount;
                    index = ((ArrayList<Object>)list).size;
                }
                else {
                    n = this.expectedModCount;
                }
                int i;
                if ((i = this.index) >= 0 && (this.index = index) <= elementData.length) {
                    while (i < index) {
                        consumer.accept((Object)elementData[i]);
                        ++i;
                    }
                    if (list.modCount == n) {
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
    
    private class Itr implements Iterator<E>
    {
        int cursor;
        int lastRet;
        int expectedModCount;
        
        Itr() {
            this.lastRet = -1;
            this.expectedModCount = ArrayList.this.modCount;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor != ArrayList.this.size;
        }
        
        @Override
        public E next() {
            this.checkForComodification();
            final int cursor = this.cursor;
            if (cursor >= ArrayList.this.size) {
                throw new NoSuchElementException();
            }
            final Object[] elementData = ArrayList.this.elementData;
            if (cursor >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            this.cursor = cursor + 1;
            final Object[] array = elementData;
            final int lastRet = cursor;
            this.lastRet = lastRet;
            return (E)array[lastRet];
        }
        
        @Override
        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            try {
                ArrayList.this.remove(this.lastRet);
                this.cursor = this.lastRet;
                this.lastRet = -1;
                this.expectedModCount = ArrayList.this.modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int access$000 = ArrayList.this.size;
            int cursor = this.cursor;
            if (cursor >= access$000) {
                return;
            }
            final Object[] elementData = ArrayList.this.elementData;
            if (cursor >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (cursor != access$000 && ArrayList.this.modCount == this.expectedModCount) {
                consumer.accept((Object)elementData[cursor++]);
            }
            this.cursor = cursor;
            this.lastRet = cursor - 1;
            this.checkForComodification();
        }
        
        final void checkForComodification() {
            if (ArrayList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    private class ListItr extends Itr implements ListIterator<E>
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
            this.checkForComodification();
            final int cursor = this.cursor - 1;
            if (cursor < 0) {
                throw new NoSuchElementException();
            }
            final Object[] elementData = ArrayList.this.elementData;
            if (cursor >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            this.cursor = cursor;
            final Object[] array = elementData;
            final int lastRet = cursor;
            this.lastRet = lastRet;
            return (E)array[lastRet];
        }
        
        @Override
        public void set(final E e) {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            try {
                ArrayList.this.set(this.lastRet, e);
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public void add(final E e) {
            this.checkForComodification();
            try {
                final int cursor = this.cursor;
                ArrayList.this.add(cursor, e);
                this.cursor = cursor + 1;
                this.lastRet = -1;
                this.expectedModCount = ArrayList.this.modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    private class SubList extends AbstractList<E> implements RandomAccess
    {
        private final AbstractList<E> parent;
        private final int parentOffset;
        private final int offset;
        int size;
        
        SubList(final AbstractList<E> parent, final int n, final int parentOffset, final int n2) {
            this.parent = parent;
            this.parentOffset = parentOffset;
            this.offset = n + parentOffset;
            this.size = n2 - parentOffset;
            this.modCount = ArrayList.this.modCount;
        }
        
        @Override
        public E set(final int n, final E e) {
            this.rangeCheck(n);
            this.checkForComodification();
            final E elementData = ArrayList.this.elementData(this.offset + n);
            ArrayList.this.elementData[this.offset + n] = e;
            return elementData;
        }
        
        @Override
        public E get(final int n) {
            this.rangeCheck(n);
            this.checkForComodification();
            return ArrayList.this.elementData(this.offset + n);
        }
        
        @Override
        public int size() {
            this.checkForComodification();
            return this.size;
        }
        
        @Override
        public void add(final int n, final E e) {
            this.rangeCheckForAdd(n);
            this.checkForComodification();
            this.parent.add(this.parentOffset + n, e);
            this.modCount = this.parent.modCount;
            ++this.size;
        }
        
        @Override
        public E remove(final int n) {
            this.rangeCheck(n);
            this.checkForComodification();
            final E remove = this.parent.remove(this.parentOffset + n);
            this.modCount = this.parent.modCount;
            --this.size;
            return remove;
        }
        
        @Override
        protected void removeRange(final int n, final int n2) {
            this.checkForComodification();
            this.parent.removeRange(this.parentOffset + n, this.parentOffset + n2);
            this.modCount = this.parent.modCount;
            this.size -= n2 - n;
        }
        
        @Override
        public boolean addAll(final Collection<? extends E> collection) {
            return this.addAll(this.size, collection);
        }
        
        @Override
        public boolean addAll(final int n, final Collection<? extends E> collection) {
            this.rangeCheckForAdd(n);
            final int size = collection.size();
            if (size == 0) {
                return false;
            }
            this.checkForComodification();
            this.parent.addAll(this.parentOffset + n, collection);
            this.modCount = this.parent.modCount;
            this.size += size;
            return true;
        }
        
        @Override
        public Iterator<E> iterator() {
            return this.listIterator();
        }
        
        @Override
        public ListIterator<E> listIterator(final int n) {
            this.checkForComodification();
            this.rangeCheckForAdd(n);
            return new ListIterator<E>() {
                int cursor = n;
                int lastRet = -1;
                int expectedModCount = ArrayList.this.modCount;
                final /* synthetic */ int val$offset = SubList.this.offset;
                
                @Override
                public boolean hasNext() {
                    return this.cursor != SubList.this.size;
                }
                
                @Override
                public E next() {
                    this.checkForComodification();
                    final int cursor = this.cursor;
                    if (cursor >= SubList.this.size) {
                        throw new NoSuchElementException();
                    }
                    final Object[] elementData = ArrayList.this.elementData;
                    if (this.val$offset + cursor >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    this.cursor = cursor + 1;
                    final Object[] array = elementData;
                    final int val$offset = this.val$offset;
                    final int lastRet = cursor;
                    this.lastRet = lastRet;
                    return (E)array[val$offset + lastRet];
                }
                
                @Override
                public boolean hasPrevious() {
                    return this.cursor != 0;
                }
                
                @Override
                public E previous() {
                    this.checkForComodification();
                    final int cursor = this.cursor - 1;
                    if (cursor < 0) {
                        throw new NoSuchElementException();
                    }
                    final Object[] elementData = ArrayList.this.elementData;
                    if (this.val$offset + cursor >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    this.cursor = cursor;
                    final Object[] array = elementData;
                    final int val$offset = this.val$offset;
                    final int lastRet = cursor;
                    this.lastRet = lastRet;
                    return (E)array[val$offset + lastRet];
                }
                
                @Override
                public void forEachRemaining(final Consumer<? super E> consumer) {
                    Objects.requireNonNull(consumer);
                    final int size = SubList.this.size;
                    int cursor = this.cursor;
                    if (cursor >= size) {
                        return;
                    }
                    final Object[] elementData = ArrayList.this.elementData;
                    if (this.val$offset + cursor >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    while (cursor != size && SubList.this.modCount == this.expectedModCount) {
                        consumer.accept((Object)elementData[this.val$offset + cursor++]);
                    }
                    final int n = cursor;
                    this.cursor = n;
                    this.lastRet = n;
                    this.checkForComodification();
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
                public void remove() {
                    if (this.lastRet < 0) {
                        throw new IllegalStateException();
                    }
                    this.checkForComodification();
                    try {
                        SubList.this.remove(this.lastRet);
                        this.cursor = this.lastRet;
                        this.lastRet = -1;
                        this.expectedModCount = ArrayList.this.modCount;
                    }
                    catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }
                
                @Override
                public void set(final E e) {
                    if (this.lastRet < 0) {
                        throw new IllegalStateException();
                    }
                    this.checkForComodification();
                    try {
                        ArrayList.this.set(this.val$offset + this.lastRet, e);
                    }
                    catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }
                
                @Override
                public void add(final E e) {
                    this.checkForComodification();
                    try {
                        final int cursor = this.cursor;
                        SubList.this.add(cursor, e);
                        this.cursor = cursor + 1;
                        this.lastRet = -1;
                        this.expectedModCount = ArrayList.this.modCount;
                    }
                    catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }
                
                final void checkForComodification() {
                    if (this.expectedModCount != ArrayList.this.modCount) {
                        throw new ConcurrentModificationException();
                    }
                }
            };
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            ArrayList.subListRangeCheck(n, n2, this.size);
            return new SubList(this, this.offset, n, n2);
        }
        
        private void rangeCheck(final int n) {
            if (n < 0 || n >= this.size) {
                throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
            }
        }
        
        private void rangeCheckForAdd(final int n) {
            if (n < 0 || n > this.size) {
                throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
            }
        }
        
        private String outOfBoundsMsg(final int n) {
            return "Index: " + n + ", Size: " + this.size;
        }
        
        private void checkForComodification() {
            if (ArrayList.this.modCount != this.modCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public Spliterator<E> spliterator() {
            this.checkForComodification();
            return new ArrayListSpliterator<E>(ArrayList.this, this.offset, this.offset + this.size, this.modCount);
        }
    }
}
