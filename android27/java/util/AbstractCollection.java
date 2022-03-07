package java.util;

import java.lang.reflect.*;

public abstract class AbstractCollection<E> implements Collection<E>
{
    private static final int MAX_ARRAY_SIZE = 2147483639;
    
    @Override
    public abstract Iterator<E> iterator();
    
    @Override
    public abstract int size();
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public boolean contains(final Object o) {
        final Iterator<Object> iterator = (Iterator<Object>)this.iterator();
        if (o == null) {
            while (iterator.hasNext()) {
                if (iterator.next() == null) {
                    return true;
                }
            }
        }
        else {
            while (iterator.hasNext()) {
                if (o.equals(iterator.next())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Object[] toArray() {
        final Object[] array = new Object[this.size()];
        final Iterator<Object> iterator = (Iterator<Object>)this.iterator();
        for (int i = 0; i < array.length; ++i) {
            if (!iterator.hasNext()) {
                return Arrays.copyOf(array, i);
            }
            array[i] = iterator.next();
        }
        return iterator.hasNext() ? finishToArray(array, iterator) : array;
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        final int size = this.size();
        final Object[] array2 = (array.length >= size) ? array : ((Object[])Array.newInstance(array.getClass().getComponentType(), size));
        final Iterator<T> iterator = (Iterator<T>)this.iterator();
        for (int i = 0; i < ((T[])array2).length; ++i) {
            if (!iterator.hasNext()) {
                if (array == array2) {
                    array2[i] = null;
                }
                else {
                    if (array.length < i) {
                        return Arrays.copyOf(array2, i);
                    }
                    System.arraycopy(array2, 0, array, 0, i);
                    if (array.length > i) {
                        array[i] = null;
                    }
                }
                return array;
            }
            array2[i] = iterator.next();
        }
        return (T[])(iterator.hasNext() ? finishToArray(array2, iterator) : array2);
    }
    
    private static <T> T[] finishToArray(T[] copy, final Iterator<?> iterator) {
        int length = copy.length;
        while (iterator.hasNext()) {
            final int length2 = copy.length;
            if (length == length2) {
                int hugeCapacity = length2 + (length2 >> 1) + 1;
                if (hugeCapacity - 2147483639 > 0) {
                    hugeCapacity = hugeCapacity(length2 + 1);
                }
                copy = Arrays.copyOf(copy, hugeCapacity);
            }
            copy[length++] = (T)iterator.next();
        }
        return (length == copy.length) ? copy : Arrays.copyOf(copy, length);
    }
    
    private static int hugeCapacity(final int n) {
        if (n < 0) {
            throw new OutOfMemoryError("Required array size too large");
        }
        return (n > 2147483639) ? Integer.MAX_VALUE : 2147483639;
    }
    
    @Override
    public boolean add(final E e) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean remove(final Object o) {
        final Iterator<Object> iterator = (Iterator<Object>)this.iterator();
        if (o == null) {
            while (iterator.hasNext()) {
                if (iterator.next() == null) {
                    iterator.remove();
                    return true;
                }
            }
        }
        else {
            while (iterator.hasNext()) {
                if (o.equals(iterator.next())) {
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean containsAll(final Collection<?> collection) {
        final Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!this.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        boolean b = false;
        final Iterator<? extends E> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.add(iterator.next())) {
                b = true;
            }
        }
        return b;
    }
    
    @Override
    public boolean removeAll(final Collection<?> collection) {
        Objects.requireNonNull(collection);
        boolean b = false;
        final Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (collection.contains(iterator.next())) {
                iterator.remove();
                b = true;
            }
        }
        return b;
    }
    
    @Override
    public boolean retainAll(final Collection<?> collection) {
        Objects.requireNonNull(collection);
        boolean b = false;
        final Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!collection.contains(iterator.next())) {
                iterator.remove();
                b = true;
            }
        }
        return b;
    }
    
    @Override
    public void clear() {
        final Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
    
    @Override
    public String toString() {
        final Iterator<AbstractCollection<AbstractCollection<AbstractCollection<AbstractCollection>>>> iterator = (Iterator<AbstractCollection<AbstractCollection<AbstractCollection<AbstractCollection>>>>)this.iterator();
        if (!iterator.hasNext()) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        while (true) {
            final AbstractCollection<AbstractCollection<AbstractCollection<AbstractCollection>>> next = iterator.next();
            sb.append((next == this) ? "(this Collection)" : next);
            if (!iterator.hasNext()) {
                break;
            }
            sb.append(',').append(' ');
        }
        return sb.append(']').toString();
    }
}
