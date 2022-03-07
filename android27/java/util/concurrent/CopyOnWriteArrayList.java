package java.util.concurrent;

import java.util.concurrent.locks.*;
import java.util.function.*;
import java.io.*;
import sun.misc.*;
import java.util.*;

public class CopyOnWriteArrayList<E> implements List<E>, RandomAccess, Cloneable, Serializable
{
    private static final long serialVersionUID = 8673264195747942595L;
    final transient ReentrantLock lock;
    private transient volatile Object[] array;
    private static final Unsafe UNSAFE;
    private static final long lockOffset;
    
    final Object[] getArray() {
        return this.array;
    }
    
    final void setArray(final Object[] array) {
        this.array = array;
    }
    
    public CopyOnWriteArrayList() {
        this.lock = new ReentrantLock();
        this.setArray(new Object[0]);
    }
    
    public CopyOnWriteArrayList(final Collection<? extends E> collection) {
        this.lock = new ReentrantLock();
        Object[] array;
        if (((CopyOnWriteArrayList<? extends E>)collection).getClass() == CopyOnWriteArrayList.class) {
            array = ((CopyOnWriteArrayList<? extends E>)collection).getArray();
        }
        else {
            array = collection.toArray();
            if (((CopyOnWriteArrayList<? extends E>)collection).getClass() != ArrayList.class) {
                array = Arrays.copyOf(array, array.length, (Class<? extends Object[]>)Object[].class);
            }
        }
        this.setArray(array);
    }
    
    public CopyOnWriteArrayList(final E[] array) {
        this.lock = new ReentrantLock();
        this.setArray(Arrays.copyOf(array, array.length, (Class<? extends Object[]>)Object[].class));
    }
    
    @Override
    public int size() {
        return this.getArray().length;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    private static boolean eq(final Object o, final Object o2) {
        return (o == null) ? (o2 == null) : o.equals(o2);
    }
    
    private static int indexOf(final Object o, final Object[] array, final int n, final int n2) {
        if (o == null) {
            for (int i = n; i < n2; ++i) {
                if (array[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int j = n; j < n2; ++j) {
                if (o.equals(array[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    private static int lastIndexOf(final Object o, final Object[] array, final int n) {
        if (o == null) {
            for (int i = n; i >= 0; --i) {
                if (array[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int j = n; j >= 0; --j) {
                if (o.equals(array[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    @Override
    public boolean contains(final Object o) {
        final Object[] array = this.getArray();
        return indexOf(o, array, 0, array.length) >= 0;
    }
    
    @Override
    public int indexOf(final Object o) {
        final Object[] array = this.getArray();
        return indexOf(o, array, 0, array.length);
    }
    
    public int indexOf(final E e, final int n) {
        final Object[] array = this.getArray();
        return indexOf(e, array, n, array.length);
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        final Object[] array = this.getArray();
        return lastIndexOf(o, array, array.length - 1);
    }
    
    public int lastIndexOf(final E e, final int n) {
        return lastIndexOf(e, this.getArray(), n);
    }
    
    public Object clone() {
        try {
            final CopyOnWriteArrayList list = (CopyOnWriteArrayList)super.clone();
            list.resetLock();
            return list;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }
    
    @Override
    public Object[] toArray() {
        final Object[] array = this.getArray();
        return Arrays.copyOf(array, array.length);
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        final Object[] array2 = this.getArray();
        final int length = array2.length;
        if (array.length < length) {
            return (T[])Arrays.copyOf(array2, length, array.getClass());
        }
        System.arraycopy(array2, 0, array, 0, length);
        if (array.length > length) {
            array[length] = null;
        }
        return array;
    }
    
    private E get(final Object[] array, final int n) {
        return (E)array[n];
    }
    
    @Override
    public E get(final int n) {
        return this.get(this.getArray(), n);
    }
    
    @Override
    public E set(final int n, final E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final E value = this.get(array, n);
            if (value != e) {
                final Object[] copy = Arrays.copyOf(array, array.length);
                copy[n] = e;
                this.setArray(copy);
            }
            else {
                this.setArray(array);
            }
            return value;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean add(final E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final int length = array.length;
            final Object[] copy = Arrays.copyOf(array, length + 1);
            copy[length] = e;
            this.setArray(copy);
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void add(final int n, final E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final int length = array.length;
            if (n > length || n < 0) {
                throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + length);
            }
            final int n2 = length - n;
            Object[] copy;
            if (n2 == 0) {
                copy = Arrays.copyOf(array, length + 1);
            }
            else {
                copy = new Object[length + 1];
                System.arraycopy(array, 0, copy, 0, n);
                System.arraycopy(array, n, copy, n + 1, n2);
            }
            copy[n] = e;
            this.setArray(copy);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E remove(final int n) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final int length = array.length;
            final E value = this.get(array, n);
            final int n2 = length - n - 1;
            if (n2 == 0) {
                this.setArray(Arrays.copyOf(array, length - 1));
            }
            else {
                final Object[] array2 = new Object[length - 1];
                System.arraycopy(array, 0, array2, 0, n);
                System.arraycopy(array, n + 1, array2, n, n2);
                this.setArray(array2);
            }
            return value;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean remove(final Object o) {
        final Object[] array = this.getArray();
        final int index = indexOf(o, array, 0, array.length);
        return index >= 0 && this.remove(o, array, index);
    }
    
    private boolean remove(final Object o, final Object[] array, int index) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array2 = this.getArray();
            final int length = array2.length;
            Label_0135: {
                if (array != array2) {
                    for (int min = Math.min(index, length), i = 0; i < min; ++i) {
                        if (array2[i] != array[i] && eq(o, array2[i])) {
                            index = i;
                            break Label_0135;
                        }
                    }
                    if (index >= length) {
                        return false;
                    }
                    if (array2[index] != o) {
                        index = indexOf(o, array2, index, length);
                        if (index < 0) {
                            return false;
                        }
                    }
                }
            }
            final Object[] array3 = new Object[length - 1];
            System.arraycopy(array2, 0, array3, 0, index);
            System.arraycopy(array2, index + 1, array3, index, length - index - 1);
            this.setArray(array3);
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    void removeRange(final int n, final int n2) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final int length = array.length;
            if (n < 0 || n2 > length || n2 < n) {
                throw new IndexOutOfBoundsException();
            }
            final int n3 = length - (n2 - n);
            final int n4 = length - n2;
            if (n4 == 0) {
                this.setArray(Arrays.copyOf(array, n3));
            }
            else {
                final Object[] array2 = new Object[n3];
                System.arraycopy(array, 0, array2, 0, n);
                System.arraycopy(array, n2, array2, n, n4);
                this.setArray(array2);
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    public boolean addIfAbsent(final E e) {
        final Object[] array = this.getArray();
        return indexOf(e, array, 0, array.length) < 0 && this.addIfAbsent(e, array);
    }
    
    private boolean addIfAbsent(final E e, final Object[] array) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array2 = this.getArray();
            final int length = array2.length;
            if (array != array2) {
                final int min = Math.min(array.length, length);
                for (int i = 0; i < min; ++i) {
                    if (array2[i] != array[i] && eq(e, array2[i])) {
                        return false;
                    }
                }
                if (indexOf(e, array2, min, length) >= 0) {
                    return false;
                }
            }
            final Object[] copy = Arrays.copyOf(array2, length + 1);
            copy[length] = e;
            this.setArray(copy);
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean containsAll(final Collection<?> collection) {
        final Object[] array = this.getArray();
        final int length = array.length;
        final Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (indexOf(iterator.next(), array, 0, length) < 0) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean removeAll(final Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final int length = array.length;
            if (length != 0) {
                int n = 0;
                final Object[] array2 = new Object[length];
                for (final Object o : array) {
                    if (!collection.contains(o)) {
                        array2[n++] = o;
                    }
                }
                if (n != length) {
                    this.setArray(Arrays.copyOf(array2, n));
                    return true;
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean retainAll(final Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final int length = array.length;
            if (length != 0) {
                int n = 0;
                final Object[] array2 = new Object[length];
                for (final Object o : array) {
                    if (collection.contains(o)) {
                        array2[n++] = o;
                    }
                }
                if (n != length) {
                    this.setArray(Arrays.copyOf(array2, n));
                    return true;
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }
    
    public int addAllAbsent(final Collection<? extends E> collection) {
        Object[] array = collection.toArray();
        if (collection.getClass() != ArrayList.class) {
            array = array.clone();
        }
        if (array.length == 0) {
            return 0;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array2 = this.getArray();
            final int length = array2.length;
            int n = 0;
            for (int i = 0; i < array.length; ++i) {
                final Object o = array[i];
                if (indexOf(o, array2, 0, length) < 0 && indexOf(o, array, 0, n) < 0) {
                    array[n++] = o;
                }
            }
            if (n > 0) {
                final Object[] copy = Arrays.copyOf(array2, length + n);
                System.arraycopy(array, 0, copy, length, n);
                this.setArray(copy);
            }
            return n;
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
            this.setArray(new Object[0]);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        final Object[] array = (((CopyOnWriteArrayList<? extends E>)collection).getClass() == CopyOnWriteArrayList.class) ? ((CopyOnWriteArrayList<? extends E>)collection).getArray() : collection.toArray();
        if (array.length == 0) {
            return false;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array2 = this.getArray();
            final int length = array2.length;
            if (length == 0 && (((CopyOnWriteArrayList<? extends E>)collection).getClass() == CopyOnWriteArrayList.class || ((CopyOnWriteArrayList<? extends E>)collection).getClass() == ArrayList.class)) {
                this.setArray(array);
            }
            else {
                final Object[] copy = Arrays.copyOf(array2, length + array.length);
                System.arraycopy(array, 0, copy, length, array.length);
                this.setArray(copy);
            }
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean addAll(final int n, final Collection<? extends E> collection) {
        final Object[] array = collection.toArray();
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array2 = this.getArray();
            final int length = array2.length;
            if (n > length || n < 0) {
                throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + length);
            }
            if (array.length == 0) {
                return false;
            }
            final int n2 = length - n;
            Object[] copy;
            if (n2 == 0) {
                copy = Arrays.copyOf(array2, length + array.length);
            }
            else {
                copy = new Object[length + array.length];
                System.arraycopy(array2, 0, copy, 0, n);
                System.arraycopy(array2, n, copy, n + array.length, n2);
            }
            System.arraycopy(array, 0, copy, n, array.length);
            this.setArray(copy);
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void forEach(final Consumer<? super E> consumer) {
        if (consumer == null) {
            throw new NullPointerException();
        }
        final Object[] array = this.getArray();
        for (int length = array.length, i = 0; i < length; ++i) {
            consumer.accept((Object)array[i]);
        }
    }
    
    @Override
    public boolean removeIf(final Predicate<? super E> predicate) {
        if (predicate == null) {
            throw new NullPointerException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final int length = array.length;
            if (length != 0) {
                int n = 0;
                final Object[] array2 = new Object[length];
                for (final Object o : array) {
                    if (!predicate.test((Object)o)) {
                        array2[n++] = o;
                    }
                }
                if (n != length) {
                    this.setArray(Arrays.copyOf(array2, n));
                    return true;
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void replaceAll(final UnaryOperator<E> unaryOperator) {
        if (unaryOperator == null) {
            throw new NullPointerException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final int length = array.length;
            final Object[] copy = Arrays.copyOf(array, length);
            for (int i = 0; i < length; ++i) {
                copy[i] = unaryOperator.apply((E)array[i]);
            }
            this.setArray(copy);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void sort(final Comparator<? super E> comparator) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = this.getArray();
            final Object[] copy = Arrays.copyOf(array, array.length);
            Arrays.sort(copy, (Comparator<? super Object>)comparator);
            this.setArray(copy);
        }
        finally {
            lock.unlock();
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        final Object[] array = this.getArray();
        objectOutputStream.writeInt(array.length);
        final Object[] array2 = array;
        for (int length = array2.length, i = 0; i < length; ++i) {
            objectOutputStream.writeObject(array2[i]);
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.resetLock();
        final int int1 = objectInputStream.readInt();
        SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Object[].class, int1);
        final Object[] array = new Object[int1];
        for (int i = 0; i < int1; ++i) {
            array[i] = objectInputStream.readObject();
        }
        this.setArray(array);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.getArray());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        final Iterator iterator = ((List)o).iterator();
        final Object[] array = this.getArray();
        for (int length = array.length, i = 0; i < length; ++i) {
            if (!iterator.hasNext() || !eq(array[i], iterator.next())) {
                return false;
            }
        }
        return !iterator.hasNext();
    }
    
    @Override
    public int hashCode() {
        int n = 1;
        for (final Object o : this.getArray()) {
            n = 31 * n + ((o == null) ? 0 : o.hashCode());
        }
        return n;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new COWIterator<E>(this.getArray(), 0);
    }
    
    @Override
    public ListIterator<E> listIterator() {
        return new COWIterator<E>(this.getArray(), 0);
    }
    
    @Override
    public ListIterator<E> listIterator(final int n) {
        final Object[] array = this.getArray();
        final int length = array.length;
        if (n < 0 || n > length) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
        return new COWIterator<E>(array, n);
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.getArray(), 1040);
    }
    
    @Override
    public List<E> subList(final int n, final int n2) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int length = this.getArray().length;
            if (n < 0 || n2 > length || n > n2) {
                throw new IndexOutOfBoundsException();
            }
            return new COWSubList<E>(this, n, n2);
        }
        finally {
            lock.unlock();
        }
    }
    
    private void resetLock() {
        CopyOnWriteArrayList.UNSAFE.putObjectVolatile(this, CopyOnWriteArrayList.lockOffset, new ReentrantLock());
    }
    
    static {
        try {
            UNSAFE = Unsafe.getUnsafe();
            lockOffset = CopyOnWriteArrayList.UNSAFE.objectFieldOffset(CopyOnWriteArrayList.class.getDeclaredField("lock"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class COWIterator<E> implements ListIterator<E>
    {
        private final Object[] snapshot;
        private int cursor;
        
        private COWIterator(final Object[] snapshot, final int cursor) {
            this.cursor = cursor;
            this.snapshot = snapshot;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor < this.snapshot.length;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.cursor > 0;
        }
        
        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return (E)this.snapshot[this.cursor++];
        }
        
        @Override
        public E previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            final Object[] snapshot = this.snapshot;
            final int cursor = this.cursor - 1;
            this.cursor = cursor;
            return (E)snapshot[cursor];
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
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void set(final E e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void add(final E e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final Object[] snapshot = this.snapshot;
            final int length = snapshot.length;
            for (int i = this.cursor; i < length; ++i) {
                consumer.accept((Object)snapshot[i]);
            }
            this.cursor = length;
        }
    }
    
    private static class COWSubList<E> extends AbstractList<E> implements RandomAccess
    {
        private final CopyOnWriteArrayList<E> l;
        private final int offset;
        private int size;
        private Object[] expectedArray;
        
        COWSubList(final CopyOnWriteArrayList<E> l, final int offset, final int n) {
            this.l = l;
            this.expectedArray = this.l.getArray();
            this.offset = offset;
            this.size = n - offset;
        }
        
        private void checkForComodification() {
            if (this.l.getArray() != this.expectedArray) {
                throw new ConcurrentModificationException();
            }
        }
        
        private void rangeCheck(final int n) {
            if (n < 0 || n >= this.size) {
                throw new IndexOutOfBoundsException("Index: " + n + ",Size: " + this.size);
            }
        }
        
        @Override
        public E set(final int n, final E e) {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.rangeCheck(n);
                this.checkForComodification();
                final E set = this.l.set(n + this.offset, e);
                this.expectedArray = this.l.getArray();
                return set;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public E get(final int n) {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.rangeCheck(n);
                this.checkForComodification();
                return this.l.get(n + this.offset);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public int size() {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.checkForComodification();
                return this.size;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public void add(final int n, final E e) {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.checkForComodification();
                if (n < 0 || n > this.size) {
                    throw new IndexOutOfBoundsException();
                }
                this.l.add(n + this.offset, e);
                this.expectedArray = this.l.getArray();
                ++this.size;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public void clear() {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.checkForComodification();
                this.l.removeRange(this.offset, this.offset + this.size);
                this.expectedArray = this.l.getArray();
                this.size = 0;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public E remove(final int n) {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.rangeCheck(n);
                this.checkForComodification();
                final E remove = this.l.remove(n + this.offset);
                this.expectedArray = this.l.getArray();
                --this.size;
                return remove;
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public boolean remove(final Object o) {
            final int index = this.indexOf(o);
            if (index == -1) {
                return false;
            }
            this.remove(index);
            return true;
        }
        
        @Override
        public Iterator<E> iterator() {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.checkForComodification();
                return new COWSubListIterator<E>(this.l, 0, this.offset, this.size);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public ListIterator<E> listIterator(final int n) {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.checkForComodification();
                if (n < 0 || n > this.size) {
                    throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + this.size);
                }
                return new COWSubListIterator<E>(this.l, n, this.offset, this.size);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                this.checkForComodification();
                if (n < 0 || n2 > this.size || n > n2) {
                    throw new IndexOutOfBoundsException();
                }
                return new COWSubList((CopyOnWriteArrayList<Object>)this.l, n + this.offset, n2 + this.offset);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final int offset = this.offset;
            final int n = this.offset + this.size;
            final Object[] expectedArray = this.expectedArray;
            if (this.l.getArray() != expectedArray) {
                throw new ConcurrentModificationException();
            }
            if (offset < 0 || n > expectedArray.length) {
                throw new IndexOutOfBoundsException();
            }
            for (int i = offset; i < n; ++i) {
                consumer.accept((Object)expectedArray[i]);
            }
        }
        
        @Override
        public void replaceAll(final UnaryOperator<E> unaryOperator) {
            if (unaryOperator == null) {
                throw new NullPointerException();
            }
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                final int offset = this.offset;
                final int n = this.offset + this.size;
                final Object[] expectedArray = this.expectedArray;
                if (this.l.getArray() != expectedArray) {
                    throw new ConcurrentModificationException();
                }
                final int length = expectedArray.length;
                if (offset < 0 || n > length) {
                    throw new IndexOutOfBoundsException();
                }
                final Object[] copy = Arrays.copyOf(expectedArray, length);
                for (int i = offset; i < n; ++i) {
                    copy[i] = unaryOperator.apply((E)expectedArray[i]);
                }
                this.l.setArray(this.expectedArray = copy);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public void sort(final Comparator<? super E> comparator) {
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                final int offset = this.offset;
                final int n = this.offset + this.size;
                final Object[] expectedArray = this.expectedArray;
                if (this.l.getArray() != expectedArray) {
                    throw new ConcurrentModificationException();
                }
                final int length = expectedArray.length;
                if (offset < 0 || n > length) {
                    throw new IndexOutOfBoundsException();
                }
                final Object[] copy = Arrays.copyOf(expectedArray, length);
                Arrays.sort(copy, offset, n, (Comparator<? super Object>)comparator);
                this.l.setArray(this.expectedArray = copy);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            if (collection == null) {
                throw new NullPointerException();
            }
            boolean b = false;
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                final int size = this.size;
                if (size > 0) {
                    final int offset = this.offset;
                    final int n = this.offset + size;
                    final Object[] expectedArray = this.expectedArray;
                    if (this.l.getArray() != expectedArray) {
                        throw new ConcurrentModificationException();
                    }
                    final int length = expectedArray.length;
                    if (offset < 0 || n > length) {
                        throw new IndexOutOfBoundsException();
                    }
                    int size2 = 0;
                    final Object[] array = new Object[size];
                    for (int i = offset; i < n; ++i) {
                        final Object o = expectedArray[i];
                        if (!collection.contains(o)) {
                            array[size2++] = o;
                        }
                    }
                    if (size2 != size) {
                        final Object[] expectedArray2 = new Object[length - size + size2];
                        System.arraycopy(expectedArray, 0, expectedArray2, 0, offset);
                        System.arraycopy(array, 0, expectedArray2, offset, size2);
                        System.arraycopy(expectedArray, n, expectedArray2, offset + size2, length - n);
                        this.size = size2;
                        b = true;
                        this.l.setArray(this.expectedArray = expectedArray2);
                    }
                }
            }
            finally {
                lock.unlock();
            }
            return b;
        }
        
        @Override
        public boolean retainAll(final Collection<?> collection) {
            if (collection == null) {
                throw new NullPointerException();
            }
            boolean b = false;
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                final int size = this.size;
                if (size > 0) {
                    final int offset = this.offset;
                    final int n = this.offset + size;
                    final Object[] expectedArray = this.expectedArray;
                    if (this.l.getArray() != expectedArray) {
                        throw new ConcurrentModificationException();
                    }
                    final int length = expectedArray.length;
                    if (offset < 0 || n > length) {
                        throw new IndexOutOfBoundsException();
                    }
                    int size2 = 0;
                    final Object[] array = new Object[size];
                    for (int i = offset; i < n; ++i) {
                        final Object o = expectedArray[i];
                        if (collection.contains(o)) {
                            array[size2++] = o;
                        }
                    }
                    if (size2 != size) {
                        final Object[] expectedArray2 = new Object[length - size + size2];
                        System.arraycopy(expectedArray, 0, expectedArray2, 0, offset);
                        System.arraycopy(array, 0, expectedArray2, offset, size2);
                        System.arraycopy(expectedArray, n, expectedArray2, offset + size2, length - n);
                        this.size = size2;
                        b = true;
                        this.l.setArray(this.expectedArray = expectedArray2);
                    }
                }
            }
            finally {
                lock.unlock();
            }
            return b;
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            if (predicate == null) {
                throw new NullPointerException();
            }
            boolean b = false;
            final ReentrantLock lock = this.l.lock;
            lock.lock();
            try {
                final int size = this.size;
                if (size > 0) {
                    final int offset = this.offset;
                    final int n = this.offset + size;
                    final Object[] expectedArray = this.expectedArray;
                    if (this.l.getArray() != expectedArray) {
                        throw new ConcurrentModificationException();
                    }
                    final int length = expectedArray.length;
                    if (offset < 0 || n > length) {
                        throw new IndexOutOfBoundsException();
                    }
                    int size2 = 0;
                    final Object[] array = new Object[size];
                    for (int i = offset; i < n; ++i) {
                        final Object o = expectedArray[i];
                        if (!predicate.test((Object)o)) {
                            array[size2++] = o;
                        }
                    }
                    if (size2 != size) {
                        final Object[] expectedArray2 = new Object[length - size + size2];
                        System.arraycopy(expectedArray, 0, expectedArray2, 0, offset);
                        System.arraycopy(array, 0, expectedArray2, offset, size2);
                        System.arraycopy(expectedArray, n, expectedArray2, offset + size2, length - n);
                        this.size = size2;
                        b = true;
                        this.l.setArray(this.expectedArray = expectedArray2);
                    }
                }
            }
            finally {
                lock.unlock();
            }
            return b;
        }
        
        @Override
        public Spliterator<E> spliterator() {
            final int offset = this.offset;
            final int n = this.offset + this.size;
            final Object[] expectedArray = this.expectedArray;
            if (this.l.getArray() != expectedArray) {
                throw new ConcurrentModificationException();
            }
            if (offset < 0 || n > expectedArray.length) {
                throw new IndexOutOfBoundsException();
            }
            return (Spliterator<E>)Spliterators.spliterator(expectedArray, offset, n, 1040);
        }
    }
    
    private static class COWSubListIterator<E> implements ListIterator<E>
    {
        private final ListIterator<E> it;
        private final int offset;
        private final int size;
        
        COWSubListIterator(final List<E> list, final int n, final int offset, final int size) {
            this.offset = offset;
            this.size = size;
            this.it = list.listIterator(n + offset);
        }
        
        @Override
        public boolean hasNext() {
            return this.nextIndex() < this.size;
        }
        
        @Override
        public E next() {
            if (this.hasNext()) {
                return this.it.next();
            }
            throw new NoSuchElementException();
        }
        
        @Override
        public boolean hasPrevious() {
            return this.previousIndex() >= 0;
        }
        
        @Override
        public E previous() {
            if (this.hasPrevious()) {
                return this.it.previous();
            }
            throw new NoSuchElementException();
        }
        
        @Override
        public int nextIndex() {
            return this.it.nextIndex() - this.offset;
        }
        
        @Override
        public int previousIndex() {
            return this.it.previousIndex() - this.offset;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void set(final E e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void add(final E e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = this.size;
            final ListIterator<E> it = this.it;
            while (this.nextIndex() < size) {
                consumer.accept(it.next());
            }
        }
    }
}
