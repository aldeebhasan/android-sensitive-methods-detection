package java.util;

import java.io.*;
import java.util.function.*;

public class LinkedHashMap<K, V> extends HashMap<K, V> implements Map<K, V>
{
    private static final long serialVersionUID = 3801124242820219131L;
    transient Entry<K, V> head;
    transient Entry<K, V> tail;
    final boolean accessOrder;
    
    private void linkNodeLast(final Entry<K, V> after) {
        final Entry<K, V> tail = this.tail;
        this.tail = after;
        if (tail == null) {
            this.head = after;
        }
        else {
            after.before = tail;
            tail.after = after;
        }
    }
    
    private void transferLinks(final Entry<K, V> entry, final Entry<K, V> entry2) {
        final Entry<K, V> before = entry.before;
        entry2.before = before;
        final Entry<K, V> entry3 = before;
        final Entry<K, V> after = entry.after;
        entry2.after = after;
        final Entry<K, V> entry4 = after;
        if (entry3 == null) {
            this.head = entry2;
        }
        else {
            entry3.after = entry2;
        }
        if (entry4 == null) {
            this.tail = entry2;
        }
        else {
            entry4.before = entry2;
        }
    }
    
    @Override
    void reinitialize() {
        super.reinitialize();
        final Entry<K, V> entry = null;
        this.tail = entry;
        this.head = entry;
    }
    
    @Override
    Node<K, V> newNode(final int n, final K k, final V v, final Node<K, V> node) {
        final Entry<K, V> entry = new Entry<K, V>(n, k, v, node);
        this.linkNodeLast(entry);
        return entry;
    }
    
    @Override
    Node<K, V> replacementNode(final Node<K, V> node, final Node<K, V> node2) {
        final Entry<K, V> entry = (Entry<K, V>)node;
        final Entry entry2 = new Entry<K, V>(entry.hash, entry.key, entry.value, (Node<Object, Object>)node2);
        this.transferLinks(entry, (Entry<K, V>)entry2);
        return (Node<K, V>)entry2;
    }
    
    @Override
    TreeNode<K, V> newTreeNode(final int n, final K k, final V v, final Node<K, V> node) {
        final TreeNode<K, V> treeNode = new TreeNode<K, V>(n, k, v, node);
        this.linkNodeLast(treeNode);
        return treeNode;
    }
    
    @Override
    TreeNode<K, V> replacementTreeNode(final Node<K, V> node, final Node<K, V> node2) {
        final Entry<K, V> entry = (Entry<K, V>)node;
        final TreeNode treeNode = new TreeNode<K, V>(entry.hash, entry.key, entry.value, (Node<Object, Object>)node2);
        this.transferLinks(entry, (Entry<K, V>)treeNode);
        return (TreeNode<K, V>)treeNode;
    }
    
    @Override
    void afterNodeRemoval(final Node<K, V> node) {
        final Entry entry = (Entry)node;
        final Entry<K, V> before = entry.before;
        final Entry<K, V> after = entry.after;
        final Entry entry2 = entry;
        final Entry entry3 = entry;
        final Entry<K, V> entry4 = null;
        entry3.after = entry4;
        entry2.before = entry4;
        if (before == null) {
            this.head = (Entry<K, V>)after;
        }
        else {
            before.after = after;
        }
        if (after == null) {
            this.tail = (Entry<K, V>)before;
        }
        else {
            after.before = before;
        }
    }
    
    @Override
    void afterNodeInsertion(final boolean b) {
        final Entry<K, V> head;
        if (b && (head = this.head) != null && this.removeEldestEntry(head)) {
            final K key = head.key;
            this.removeNode(HashMap.hash(key), key, null, false, true);
        }
    }
    
    @Override
    void afterNodeAccess(final Node<K, V> node) {
        Entry<K, V> tail;
        if (this.accessOrder && (tail = this.tail) != node) {
            final Entry<K, V> tail2 = (Entry<K, V>)node;
            final Entry<K, V> before = tail2.before;
            final Entry<K, V> after = tail2.after;
            tail2.after = null;
            if (before == null) {
                this.head = after;
            }
            else {
                before.after = after;
            }
            if (after != null) {
                after.before = before;
            }
            else {
                tail = before;
            }
            if (tail == null) {
                this.head = tail2;
            }
            else {
                tail2.before = tail;
                tail.after = tail2;
            }
            this.tail = tail2;
            ++this.modCount;
        }
    }
    
    @Override
    void internalWriteEntries(final ObjectOutputStream objectOutputStream) throws IOException {
        for (Entry<K, V> entry = this.head; entry != null; entry = entry.after) {
            objectOutputStream.writeObject(entry.key);
            objectOutputStream.writeObject(entry.value);
        }
    }
    
    public LinkedHashMap(final int n, final float n2) {
        super(n, n2);
        this.accessOrder = false;
    }
    
    public LinkedHashMap(final int n) {
        super(n);
        this.accessOrder = false;
    }
    
    public LinkedHashMap() {
        this.accessOrder = false;
    }
    
    public LinkedHashMap(final Map<? extends K, ? extends V> map) {
        this.putMapEntries(map, this.accessOrder = false);
    }
    
    public LinkedHashMap(final int n, final float n2, final boolean accessOrder) {
        super(n, n2);
        this.accessOrder = accessOrder;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        for (Entry<K, V> entry = this.head; entry != null; entry = entry.after) {
            final V value = entry.value;
            if (value == o || (o != null && o.equals(value))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public V get(final Object o) {
        final Node<K, V> node;
        if ((node = this.getNode(HashMap.hash(o), o)) == null) {
            return null;
        }
        if (this.accessOrder) {
            this.afterNodeAccess(node);
        }
        return node.value;
    }
    
    @Override
    public V getOrDefault(final Object o, final V v) {
        final Node<K, V> node;
        if ((node = this.getNode(HashMap.hash(o), o)) == null) {
            return v;
        }
        if (this.accessOrder) {
            this.afterNodeAccess(node);
        }
        return node.value;
    }
    
    @Override
    public void clear() {
        super.clear();
        final Entry<K, V> entry = null;
        this.tail = entry;
        this.head = entry;
    }
    
    protected boolean removeEldestEntry(final Map.Entry<K, V> entry) {
        return false;
    }
    
    @Override
    public Set<K> keySet() {
        Set<K> keySet = this.keySet;
        if (keySet == null) {
            keySet = new LinkedKeySet();
            this.keySet = keySet;
        }
        return keySet;
    }
    
    @Override
    public Collection<V> values() {
        Collection<V> values = this.values;
        if (values == null) {
            values = new LinkedValues();
            this.values = values;
        }
        return values;
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> entrySet;
        return ((entrySet = this.entrySet) == null) ? (this.entrySet = new LinkedEntrySet()) : entrySet;
    }
    
    @Override
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer == null) {
            throw new NullPointerException();
        }
        final int modCount = this.modCount;
        for (Entry<K, V> entry = this.head; entry != null; entry = entry.after) {
            biConsumer.accept(entry.key, entry.value);
        }
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
    }
    
    @Override
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction == null) {
            throw new NullPointerException();
        }
        final int modCount = this.modCount;
        for (Entry<K, V> entry = this.head; entry != null; entry = entry.after) {
            entry.value = (V)biFunction.apply(entry.key, entry.value);
        }
        if (this.modCount != modCount) {
            throw new ConcurrentModificationException();
        }
    }
    
    static class Entry<K, V> extends Node<K, V>
    {
        Entry<K, V> before;
        Entry<K, V> after;
        
        Entry(final int n, final K k, final V v, final Node<K, V> node) {
            super(n, k, v, node);
        }
    }
    
    final class LinkedEntryIterator extends LinkedHashIterator implements Iterator<Map.Entry<K, V>>
    {
        @Override
        public final Map.Entry<K, V> next() {
            return this.nextNode();
        }
    }
    
    abstract class LinkedHashIterator
    {
        Entry<K, V> next;
        Entry<K, V> current;
        int expectedModCount;
        
        LinkedHashIterator() {
            this.next = LinkedHashMap.this.head;
            this.expectedModCount = LinkedHashMap.this.modCount;
            this.current = null;
        }
        
        public final boolean hasNext() {
            return this.next != null;
        }
        
        final Entry<K, V> nextNode() {
            final Entry<K, V> next = this.next;
            if (LinkedHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (next == null) {
                throw new NoSuchElementException();
            }
            this.current = next;
            this.next = next.after;
            return next;
        }
        
        public final void remove() {
            final Entry<K, V> current = this.current;
            if (current == null) {
                throw new IllegalStateException();
            }
            if (LinkedHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.current = null;
            final K key = current.key;
            LinkedHashMap.this.removeNode(HashMap.hash(key), key, null, false, false);
            this.expectedModCount = LinkedHashMap.this.modCount;
        }
    }
    
    final class LinkedEntrySet extends AbstractSet<Map.Entry<K, V>>
    {
        @Override
        public final int size() {
            return LinkedHashMap.this.size;
        }
        
        @Override
        public final void clear() {
            LinkedHashMap.this.clear();
        }
        
        @Override
        public final Iterator<Map.Entry<K, V>> iterator() {
            return new LinkedEntryIterator();
        }
        
        @Override
        public final boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object key = entry.getKey();
            final Node<Object, Object> node = LinkedHashMap.this.getNode(HashMap.hash(key), key);
            return node != null && node.equals(entry);
        }
        
        @Override
        public final boolean remove(final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                final Object key = entry.getKey();
                return LinkedHashMap.this.removeNode(HashMap.hash(key), key, entry.getValue(), true, true) != null;
            }
            return false;
        }
        
        @Override
        public final Spliterator<Map.Entry<K, V>> spliterator() {
            return Spliterators.spliterator((Collection<? extends Map.Entry<K, V>>)this, 81);
        }
        
        @Override
        public final void forEach(final Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final int modCount = LinkedHashMap.this.modCount;
            for (Entry<K, V> entry = LinkedHashMap.this.head; entry != null; entry = entry.after) {
                consumer.accept(entry);
            }
            if (LinkedHashMap.this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    final class LinkedKeyIterator extends LinkedHashIterator implements Iterator<K>
    {
        @Override
        public final K next() {
            return this.nextNode().getKey();
        }
    }
    
    final class LinkedKeySet extends AbstractSet<K>
    {
        @Override
        public final int size() {
            return LinkedHashMap.this.size;
        }
        
        @Override
        public final void clear() {
            LinkedHashMap.this.clear();
        }
        
        @Override
        public final Iterator<K> iterator() {
            return new LinkedKeyIterator();
        }
        
        @Override
        public final boolean contains(final Object o) {
            return LinkedHashMap.this.containsKey(o);
        }
        
        @Override
        public final boolean remove(final Object o) {
            return LinkedHashMap.this.removeNode(HashMap.hash(o), o, null, false, true) != null;
        }
        
        @Override
        public final Spliterator<K> spliterator() {
            return Spliterators.spliterator((Collection<? extends K>)this, 81);
        }
        
        @Override
        public final void forEach(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final int modCount = LinkedHashMap.this.modCount;
            for (Entry<K, V> entry = LinkedHashMap.this.head; entry != null; entry = entry.after) {
                consumer.accept(entry.key);
            }
            if (LinkedHashMap.this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    final class LinkedValueIterator extends LinkedHashIterator implements Iterator<V>
    {
        @Override
        public final V next() {
            return this.nextNode().value;
        }
    }
    
    final class LinkedValues extends AbstractCollection<V>
    {
        @Override
        public final int size() {
            return LinkedHashMap.this.size;
        }
        
        @Override
        public final void clear() {
            LinkedHashMap.this.clear();
        }
        
        @Override
        public final Iterator<V> iterator() {
            return new LinkedValueIterator();
        }
        
        @Override
        public final boolean contains(final Object o) {
            return LinkedHashMap.this.containsValue(o);
        }
        
        @Override
        public final Spliterator<V> spliterator() {
            return Spliterators.spliterator((Collection<? extends V>)this, 80);
        }
        
        @Override
        public final void forEach(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final int modCount = LinkedHashMap.this.modCount;
            for (Entry<K, V> entry = LinkedHashMap.this.head; entry != null; entry = entry.after) {
                consumer.accept(entry.value);
            }
            if (LinkedHashMap.this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
