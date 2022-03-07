package java.util.concurrent;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

abstract static class CollectionView<K, V, E> implements Collection<E>, Serializable
{
    private static final long serialVersionUID = 7249069246763182397L;
    final ConcurrentHashMap<K, V> map;
    private static final String oomeMsg = "Required array size too large";
    
    CollectionView(final ConcurrentHashMap<K, V> map) {
        this.map = map;
    }
    
    public ConcurrentHashMap<K, V> getMap() {
        return this.map;
    }
    
    @Override
    public final void clear() {
        this.map.clear();
    }
    
    @Override
    public final int size() {
        return this.map.size();
    }
    
    @Override
    public final boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    @Override
    public abstract Iterator<E> iterator();
    
    @Override
    public abstract boolean contains(final Object p0);
    
    @Override
    public abstract boolean remove(final Object p0);
    
    @Override
    public final Object[] toArray() {
        final long mappingCount = this.map.mappingCount();
        if (mappingCount > 2147483639L) {
            throw new OutOfMemoryError("Required array size too large");
        }
        int n = (int)mappingCount;
        Object[] copy = new Object[n];
        int n2 = 0;
        for (final E next : this) {
            if (n2 == n) {
                if (n >= 2147483639) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                if (n >= 1073741819) {
                    n = 2147483639;
                }
                else {
                    n += (n >>> 1) + 1;
                }
                copy = Arrays.copyOf(copy, n);
            }
            copy[n2++] = next;
        }
        return (n2 == n) ? copy : Arrays.copyOf(copy, n2);
    }
    
    @Override
    public final <T> T[] toArray(final T[] array) {
        final long mappingCount = this.map.mappingCount();
        if (mappingCount > 2147483639L) {
            throw new OutOfMemoryError("Required array size too large");
        }
        final int n = (int)mappingCount;
        Object[] copy = (array.length >= n) ? array : ((Object[])Array.newInstance(array.getClass().getComponentType(), n));
        int length = ((T[])copy).length;
        int n2 = 0;
        for (final T next : this) {
            if (n2 == length) {
                if (length >= 2147483639) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                if (length >= 1073741819) {
                    length = 2147483639;
                }
                else {
                    length += (length >>> 1) + 1;
                }
                copy = Arrays.copyOf(copy, length);
            }
            copy[n2++] = next;
        }
        if (array == copy && n2 < length) {
            copy[n2] = null;
            return (T[])copy;
        }
        return (T[])((n2 == length) ? copy : Arrays.copyOf(copy, n2));
    }
    
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        final Iterator<CollectionView<K, V, CollectionView<K, V, CollectionView<K, V, CollectionView>>>> iterator = (Iterator<CollectionView<K, V, CollectionView<K, V, CollectionView<K, V, CollectionView>>>>)this.iterator();
        if (iterator.hasNext()) {
            while (true) {
                final CollectionView<K, V, CollectionView<K, V, CollectionView<K, V, CollectionView>>> next = iterator.next();
                sb.append((next == this) ? "(this Collection)" : next);
                if (!iterator.hasNext()) {
                    break;
                }
                sb.append(',').append(' ');
            }
        }
        return sb.append(']').toString();
    }
    
    @Override
    public final boolean containsAll(final Collection<?> collection) {
        if (collection != this) {
            for (final Object next : collection) {
                if (next == null || !this.contains(next)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public final boolean removeAll(final Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
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
    public final boolean retainAll(final Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
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
}
