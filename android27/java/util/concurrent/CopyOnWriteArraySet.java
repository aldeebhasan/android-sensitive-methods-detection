package java.util.concurrent;

import java.io.*;
import java.util.function.*;
import java.util.*;

public class CopyOnWriteArraySet<E> extends AbstractSet<E> implements Serializable
{
    private static final long serialVersionUID = 5457747651344034263L;
    private final CopyOnWriteArrayList<E> al;
    
    public CopyOnWriteArraySet() {
        this.al = new CopyOnWriteArrayList<E>();
    }
    
    public CopyOnWriteArraySet(final Collection<? extends E> collection) {
        if (((CopyOnWriteArraySet<? extends E>)collection).getClass() == CopyOnWriteArraySet.class) {
            this.al = new CopyOnWriteArrayList<E>(((CopyOnWriteArraySet<? extends E>)collection).al);
        }
        else {
            (this.al = new CopyOnWriteArrayList<E>()).addAllAbsent(collection);
        }
    }
    
    @Override
    public int size() {
        return this.al.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.al.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.al.contains(o);
    }
    
    @Override
    public Object[] toArray() {
        return this.al.toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        return this.al.toArray(array);
    }
    
    @Override
    public void clear() {
        this.al.clear();
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.al.remove(o);
    }
    
    @Override
    public boolean add(final E e) {
        return this.al.addIfAbsent(e);
    }
    
    @Override
    public boolean containsAll(final Collection<?> collection) {
        return this.al.containsAll(collection);
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        return this.al.addAllAbsent(collection) > 0;
    }
    
    @Override
    public boolean removeAll(final Collection<?> collection) {
        return this.al.removeAll(collection);
    }
    
    @Override
    public boolean retainAll(final Collection<?> collection) {
        return this.al.retainAll(collection);
    }
    
    @Override
    public Iterator<E> iterator() {
        return this.al.iterator();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Set)) {
            return false;
        }
        final Iterator<Object> iterator = ((Set)o).iterator();
        final Object[] array = this.al.getArray();
        final int length = array.length;
        final boolean[] array2 = new boolean[length];
        int n = 0;
    Label_0054:
        while (iterator.hasNext()) {
            if (++n > length) {
                return false;
            }
            final Object next = iterator.next();
            for (int i = 0; i < length; ++i) {
                if (!array2[i] && eq(next, array[i])) {
                    array2[i] = true;
                    continue Label_0054;
                }
            }
            return false;
        }
        return n == length;
    }
    
    @Override
    public boolean removeIf(final Predicate<? super E> predicate) {
        return this.al.removeIf(predicate);
    }
    
    @Override
    public void forEach(final Consumer<? super E> consumer) {
        this.al.forEach(consumer);
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.al.getArray(), 1025);
    }
    
    private static boolean eq(final Object o, final Object o2) {
        return (o == null) ? (o2 == null) : o.equals(o2);
    }
}
