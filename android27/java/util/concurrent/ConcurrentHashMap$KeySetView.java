package java.util.concurrent;

import java.io.*;
import java.util.*;
import java.util.function.*;

public static class KeySetView<K, V> extends CollectionView<K, V, K> implements Set<K>, Serializable
{
    private static final long serialVersionUID = 7249069246763182397L;
    private final V value;
    
    KeySetView(final ConcurrentHashMap<K, V> concurrentHashMap, final V value) {
        super(concurrentHashMap);
        this.value = value;
    }
    
    public V getMappedValue() {
        return this.value;
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.map.remove(o) != null;
    }
    
    @Override
    public Iterator<K> iterator() {
        final ConcurrentHashMap<K, V> map = this.map;
        final Node<Object, Object>[] table;
        final int n = ((table = (Node<Object, Object>[])map.table) == null) ? 0 : table.length;
        return new KeyIterator<K, Object>(table, n, 0, n, (ConcurrentHashMap<Object, Object>)map);
    }
    
    @Override
    public boolean add(final K k) {
        final V value;
        if ((value = this.value) == null) {
            throw new UnsupportedOperationException();
        }
        return this.map.putVal((K)k, (V)value, true) == null;
    }
    
    @Override
    public boolean addAll(final Collection<? extends K> collection) {
        boolean b = false;
        final V value;
        if ((value = this.value) == null) {
            throw new UnsupportedOperationException();
        }
        final Iterator<? extends K> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.map.putVal((K)iterator.next(), (V)value, true) == null) {
                b = true;
            }
        }
        return b;
    }
    
    @Override
    public int hashCode() {
        int n = 0;
        final Iterator<Object> iterator = this.iterator();
        while (iterator.hasNext()) {
            n += iterator.next().hashCode();
        }
        return n;
    }
    
    @Override
    public boolean equals(final Object o) {
        final Set set;
        return o instanceof Set && ((set = (Set)o) == this || (this.containsAll(set) && set.containsAll(this)));
    }
    
    @Override
    public Spliterator<K> spliterator() {
        final ConcurrentHashMap<K, V> map = this.map;
        final long sumCount = map.sumCount();
        final Node<K, V>[] table;
        final int n = ((table = map.table) == null) ? 0 : ((Node<Object, Object>[])table).length;
        return new KeySpliterator<K, Object>((Node<Object, Object>[])table, n, 0, n, (sumCount < 0L) ? 0L : sumCount);
    }
    
    @Override
    public void forEach(final Consumer<? super K> consumer) {
        if (consumer == null) {
            throw new NullPointerException();
        }
        final Node<K, V>[] table;
        if ((table = this.map.table) != null) {
            Node<Object, Object> advance;
            while ((advance = new Traverser<Object, Object>((Node<Object, Object>[])table, ((Node<Object, Object>[])table).length, 0, ((Node<Object, Object>[])table).length).advance()) != null) {
                consumer.accept((Object)advance.key);
            }
        }
    }
}
