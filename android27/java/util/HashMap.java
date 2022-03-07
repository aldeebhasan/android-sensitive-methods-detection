package java.util;

import java.lang.reflect.*;
import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable
{
    private static final long serialVersionUID = 362498820763181265L;
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int TREEIFY_THRESHOLD = 8;
    static final int UNTREEIFY_THRESHOLD = 6;
    static final int MIN_TREEIFY_CAPACITY = 64;
    transient Node<K, V>[] table;
    transient Set<Entry<K, V>> entrySet;
    transient int size;
    transient int modCount;
    int threshold;
    final float loadFactor;
    
    static final int hash(final Object o) {
        int n;
        if (o == null) {
            n = 0;
        }
        else {
            final int hashCode = o.hashCode();
            n = (hashCode ^ hashCode >>> 16);
        }
        return n;
    }
    
    static Class<?> comparableClassFor(final Object o) {
        if (o instanceof Comparable) {
            final Class<?> class1;
            if ((class1 = o.getClass()) == String.class) {
                return class1;
            }
            final Type[] genericInterfaces;
            if ((genericInterfaces = class1.getGenericInterfaces()) != null) {
                for (int i = 0; i < genericInterfaces.length; ++i) {
                    final Type type;
                    final ParameterizedType parameterizedType;
                    final Type[] actualTypeArguments;
                    if ((type = genericInterfaces[i]) instanceof ParameterizedType && (parameterizedType = (ParameterizedType)type).getRawType() == Comparable.class && (actualTypeArguments = parameterizedType.getActualTypeArguments()) != null && actualTypeArguments.length == 1 && actualTypeArguments[0] == class1) {
                        return class1;
                    }
                }
            }
        }
        return null;
    }
    
    static int compareComparables(final Class<?> clazz, final Object o, final Object o2) {
        return (o2 == null || o2.getClass() != clazz) ? 0 : ((Comparable)o).compareTo(o2);
    }
    
    static final int tableSizeFor(final int n) {
        final int n2 = n - 1;
        final int n3 = n2 | n2 >>> 1;
        final int n4 = n3 | n3 >>> 2;
        final int n5 = n4 | n4 >>> 4;
        final int n6 = n5 | n5 >>> 8;
        final int n7 = n6 | n6 >>> 16;
        return (n7 < 0) ? 1 : ((n7 >= 1073741824) ? 1073741824 : (n7 + 1));
    }
    
    public HashMap(int n, final float loadFactor) {
        if (n < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + n);
        }
        if (n > 1073741824) {
            n = 1073741824;
        }
        if (loadFactor <= 0.0f || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(n);
    }
    
    public HashMap(final int n) {
        this(n, 0.75f);
    }
    
    public HashMap() {
        this.loadFactor = 0.75f;
    }
    
    public HashMap(final Map<? extends K, ? extends V> map) {
        this.loadFactor = 0.75f;
        this.putMapEntries(map, false);
    }
    
    final void putMapEntries(final Map<? extends K, ? extends V> map, final boolean b) {
        final int size = map.size();
        if (size > 0) {
            if (this.table == null) {
                final float n = size / this.loadFactor + 1.0f;
                final int n2 = (n < 1.07374182E9f) ? ((int)n) : 1073741824;
                if (n2 > this.threshold) {
                    this.threshold = tableSizeFor(n2);
                }
            }
            else if (size > this.threshold) {
                this.resize();
            }
            for (final Entry<? extends K, ? extends V> entry : map.entrySet()) {
                final K key = (K)entry.getKey();
                this.putVal(hash(key), key, entry.getValue(), false, b);
            }
        }
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
    public V get(final Object o) {
        final Node<K, V> node;
        return ((node = this.getNode(hash(o), o)) == null) ? null : node.value;
    }
    
    final Node<K, V> getNode(final int n, final Object o) {
        final Node<K, V>[] table;
        final int length;
        final Node<K, V> node;
        if ((table = this.table) != null && (length = table.length) > 0 && (node = table[length - 1 & n]) != null) {
            final K key;
            if (node.hash == n && ((key = node.key) == o || (o != null && o.equals(key)))) {
                return node;
            }
            Node<K, V> node2;
            if ((node2 = node.next) != null) {
                if (node instanceof TreeNode) {
                    return ((TreeNode<K, V>)node).getTreeNode(n, o);
                }
                K key2;
                while (node2.hash != n || ((key2 = node2.key) != o && (o == null || !o.equals(key2)))) {
                    if ((node2 = node2.next) == null) {
                        return null;
                    }
                }
                return node2;
            }
        }
        return null;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.getNode(hash(o), o) != null;
    }
    
    @Override
    public V put(final K k, final V v) {
        return this.putVal(hash(k), k, v, false, true);
    }
    
    final V putVal(final int n, final K k, final V value, final boolean b, final boolean b2) {
        Node<K, V>[] array;
        int n2;
        if ((array = this.table) == null || (n2 = array.length) == 0) {
            n2 = (array = this.resize()).length;
        }
        final int n3;
        Node<K, V> node;
        if ((node = array[n3 = (n2 - 1 & n)]) == null) {
            array[n3] = this.newNode(n, k, value, null);
        }
        else {
            Node<K, V> node2 = null;
            Label_0222: {
                final K key;
                if (node.hash == n && ((key = node.key) == k || (k != null && k.equals(key)))) {
                    node2 = node;
                }
                else if (node instanceof TreeNode) {
                    node2 = ((TreeNode<K, V>)node).putTreeVal(this, array, n, k, value);
                }
                else {
                    int n4;
                    for (n4 = 0; (node2 = node.next) != null; node = node2, ++n4) {
                        if (node2.hash == n) {
                            final K key2;
                            if ((key2 = node2.key) == k) {
                                break Label_0222;
                            }
                            if (k != null && k.equals(key2)) {
                                break Label_0222;
                            }
                        }
                    }
                    node.next = (Node<K, V>)this.newNode(n, (K)k, (V)value, (Node<K, V>)null);
                    if (n4 >= 7) {
                        this.treeifyBin(array, n);
                    }
                }
            }
            if (node2 != null) {
                final V value2 = node2.value;
                if (!b || value2 == null) {
                    node2.value = value;
                }
                this.afterNodeAccess(node2);
                return value2;
            }
        }
        ++this.modCount;
        if (++this.size > this.threshold) {
            this.resize();
        }
        this.afterNodeInsertion(b2);
        return null;
    }
    
    final Node<K, V>[] resize() {
        final Node<K, V>[] table = this.table;
        final int n = (table == null) ? 0 : table.length;
        final int threshold = this.threshold;
        int threshold2 = 0;
        int n2;
        if (n > 0) {
            if (n >= 1073741824) {
                this.threshold = Integer.MAX_VALUE;
                return table;
            }
            if ((n2 = n << 1) < 1073741824 && n >= 16) {
                threshold2 = threshold << 1;
            }
        }
        else if (threshold > 0) {
            n2 = threshold;
        }
        else {
            n2 = 16;
            threshold2 = 12;
        }
        if (threshold2 == 0) {
            final float n3 = n2 * this.loadFactor;
            threshold2 = ((n2 < 1073741824 && n3 < 1.07374182E9f) ? ((int)n3) : Integer.MAX_VALUE);
        }
        this.threshold = threshold2;
        final Node[] table2 = new Node[n2];
        this.table = (Node<K, V>[])table2;
        if (table != null) {
            for (int i = 0; i < n; ++i) {
                Node<K, V> node;
                if ((node = table[i]) != null) {
                    table[i] = null;
                    if (node.next == null) {
                        table2[node.hash & n2 - 1] = node;
                    }
                    else if (node instanceof TreeNode) {
                        ((TreeNode<K, V>)node).split(this, table2, i, n);
                    }
                    else {
                        Node<K, V> node2 = null;
                        Node<K, V> node3 = null;
                        Node<K, V> node4 = null;
                        Node<K, V> node5 = null;
                        Node<K, V> next;
                        do {
                            next = (Node<K, V>)node.next;
                            if ((node.hash & n) == 0x0) {
                                if (node3 == null) {
                                    node2 = node;
                                }
                                else {
                                    node3.next = node;
                                }
                                node3 = node;
                            }
                            else {
                                if (node5 == null) {
                                    node4 = node;
                                }
                                else {
                                    node5.next = node;
                                }
                                node5 = node;
                            }
                        } while ((node = (Node<K, V>)next) != null);
                        if (node3 != null) {
                            node3.next = null;
                            table2[i] = node2;
                        }
                        if (node5 != null) {
                            node5.next = null;
                            table2[i + n] = node4;
                        }
                    }
                }
            }
        }
        return (Node<K, V>[])table2;
    }
    
    final void treeifyBin(final Node<K, V>[] array, final int n) {
        final int length;
        if (array == null || (length = array.length) < 64) {
            this.resize();
        }
        else {
            final int n2;
            Node<K, V> next;
            if ((next = array[n2 = (length - 1 & n)]) != null) {
                TreeNode<K, V> treeNode = null;
                TreeNode<K, V> prev = null;
                do {
                    final TreeNode<K, V> replacementTreeNode = this.replacementTreeNode((Node<K, V>)next, (Node<K, V>)null);
                    if (prev == null) {
                        treeNode = (TreeNode<K, V>)replacementTreeNode;
                    }
                    else {
                        replacementTreeNode.prev = (TreeNode<K, V>)prev;
                        prev.next = (Node<K, V>)replacementTreeNode;
                    }
                    prev = (TreeNode<K, V>)replacementTreeNode;
                } while ((next = next.next) != null);
                if ((array[n2] = treeNode) != null) {
                    treeNode.treeify(array);
                }
            }
        }
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        this.putMapEntries(map, true);
    }
    
    @Override
    public V remove(final Object o) {
        final Node<K, V> removeNode;
        return ((removeNode = this.removeNode(hash(o), o, null, false, true)) == null) ? null : removeNode.value;
    }
    
    final Node<K, V> removeNode(final int n, final Object o, final Object o2, final boolean b, final boolean b2) {
        final Node<K, V>[] table;
        final int length;
        final int n2;
        Node<K, V> node;
        if ((table = this.table) != null && (length = table.length) > 0 && (node = table[n2 = (length - 1 & n)]) != null) {
            Node<K, V> treeNode = null;
            Label_0171: {
                final K key;
                if (node.hash == n && ((key = node.key) == o || (o != null && o.equals(key)))) {
                    treeNode = node;
                }
                else {
                    Node<K, V> node2;
                    if ((node2 = node.next) != null) {
                        if (node instanceof TreeNode) {
                            treeNode = ((TreeNode<K, V>)node).getTreeNode(n, o);
                        }
                        else {
                            K key2;
                            while (node2.hash != n || ((key2 = node2.key) != o && (o == null || !o.equals(key2)))) {
                                node = node2;
                                if ((node2 = node2.next) == null) {
                                    break Label_0171;
                                }
                            }
                            treeNode = node2;
                        }
                    }
                }
            }
            final V value;
            if (treeNode != null && (!b || (value = treeNode.value) == o2 || (o2 != null && o2.equals(value)))) {
                if (treeNode instanceof TreeNode) {
                    ((TreeNode<K, V>)treeNode).removeTreeNode(this, table, b2);
                }
                else if (treeNode == node) {
                    table[n2] = treeNode.next;
                }
                else {
                    node.next = treeNode.next;
                }
                ++this.modCount;
                --this.size;
                this.afterNodeRemoval(treeNode);
                return treeNode;
            }
        }
        return null;
    }
    
    @Override
    public void clear() {
        ++this.modCount;
        final Node<K, V>[] table;
        if ((table = this.table) != null && this.size > 0) {
            this.size = 0;
            for (int i = 0; i < table.length; ++i) {
                table[i] = null;
            }
        }
    }
    
    @Override
    public boolean containsValue(final Object o) {
        final Node<K, V>[] table;
        if ((table = this.table) != null && this.size > 0) {
            for (int i = 0; i < table.length; ++i) {
                for (Node<K, V> next = table[i]; next != null; next = next.next) {
                    final V value;
                    if ((value = next.value) == o || (o != null && o.equals(value))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public Set<K> keySet() {
        Set<K> keySet = this.keySet;
        if (keySet == null) {
            keySet = new KeySet();
            this.keySet = keySet;
        }
        return keySet;
    }
    
    @Override
    public Collection<V> values() {
        Collection<V> values = this.values;
        if (values == null) {
            values = new Values();
            this.values = values;
        }
        return values;
    }
    
    @Override
    public Set<Entry<K, V>> entrySet() {
        final Set<Entry<K, V>> entrySet;
        return ((entrySet = this.entrySet) == null) ? (this.entrySet = new EntrySet()) : entrySet;
    }
    
    @Override
    public V getOrDefault(final Object o, final V v) {
        final Node<K, V> node;
        return ((node = this.getNode(hash(o), o)) == null) ? v : node.value;
    }
    
    @Override
    public V putIfAbsent(final K k, final V v) {
        return this.putVal(hash(k), k, v, true, true);
    }
    
    @Override
    public boolean remove(final Object o, final Object o2) {
        return this.removeNode(hash(o), o, o2, true, true) != null;
    }
    
    @Override
    public boolean replace(final K k, final V v, final V value) {
        final Node<K, V> node;
        final V value2;
        if ((node = this.getNode(hash(k), k)) != null && ((value2 = node.value) == v || (value2 != null && value2.equals(v)))) {
            node.value = value;
            this.afterNodeAccess(node);
            return true;
        }
        return false;
    }
    
    @Override
    public V replace(final K k, final V value) {
        final Node<K, V> node;
        if ((node = this.getNode(hash(k), k)) != null) {
            final V value2 = node.value;
            node.value = value;
            this.afterNodeAccess(node);
            return value2;
        }
        return null;
    }
    
    @Override
    public V computeIfAbsent(final K k, final Function<? super K, ? extends V> function) {
        if (function == null) {
            throw new NullPointerException();
        }
        final int hash = hash(k);
        int n = 0;
        TreeNode<K, V> treeNode = null;
        Node<K, V> treeNode2 = null;
        Node<K, V>[] array;
        int n2;
        if (this.size > this.threshold || (array = this.table) == null || (n2 = array.length) == 0) {
            n2 = (array = this.resize()).length;
        }
        final int n3;
        final Node<K, V> node;
        if ((node = array[n3 = (n2 - 1 & hash)]) != null) {
            Label_0169: {
                if (node instanceof TreeNode) {
                    treeNode2 = (treeNode = (TreeNode<K, V>)node).getTreeNode(hash, k);
                }
                else {
                    Node<K, V> next = node;
                    K key;
                    while (next.hash != hash || ((key = next.key) != k && (k == null || !k.equals(key)))) {
                        ++n;
                        if ((next = next.next) == null) {
                            break Label_0169;
                        }
                    }
                    treeNode2 = next;
                }
            }
            final V value;
            if (treeNode2 != null && (value = treeNode2.value) != null) {
                this.afterNodeAccess(treeNode2);
                return value;
            }
        }
        final V apply = (V)function.apply(k);
        if (apply == null) {
            return null;
        }
        if (treeNode2 != null) {
            treeNode2.value = apply;
            this.afterNodeAccess(treeNode2);
            return apply;
        }
        if (treeNode != null) {
            treeNode.putTreeVal(this, array, hash, k, apply);
        }
        else {
            array[n3] = this.newNode(hash, k, apply, node);
            if (n >= 7) {
                this.treeifyBin(array, hash);
            }
        }
        ++this.modCount;
        ++this.size;
        this.afterNodeInsertion(true);
        return apply;
    }
    
    @Override
    public V computeIfPresent(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction == null) {
            throw new NullPointerException();
        }
        final int hash = hash(k);
        final Node<K, V> node;
        final V value;
        if ((node = this.getNode(hash, k)) != null && (value = node.value) != null) {
            final V apply = (V)biFunction.apply(k, value);
            if (apply != null) {
                node.value = apply;
                this.afterNodeAccess(node);
                return apply;
            }
            this.removeNode(hash, k, null, false, true);
        }
        return null;
    }
    
    @Override
    public V compute(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction == null) {
            throw new NullPointerException();
        }
        final int hash = hash(k);
        int n = 0;
        TreeNode<K, V> treeNode = null;
        Node<K, V> treeNode2 = null;
        Node<K, V>[] array;
        int n2;
        if (this.size > this.threshold || (array = this.table) == null || (n2 = array.length) == 0) {
            n2 = (array = this.resize()).length;
        }
        final int n3;
        final Node<K, V> node;
        Label_0169: {
            if ((node = array[n3 = (n2 - 1 & hash)]) != null) {
                if (node instanceof TreeNode) {
                    treeNode2 = (treeNode = (TreeNode<K, V>)node).getTreeNode(hash, k);
                }
                else {
                    Node<K, V> next = node;
                    K key;
                    while (next.hash != hash || ((key = next.key) != k && (k == null || !k.equals(key)))) {
                        ++n;
                        if ((next = next.next) == null) {
                            break Label_0169;
                        }
                    }
                    treeNode2 = next;
                }
            }
        }
        final V apply = (V)biFunction.apply(k, (treeNode2 == null) ? null : treeNode2.value);
        if (treeNode2 != null) {
            if (apply != null) {
                treeNode2.value = apply;
                this.afterNodeAccess(treeNode2);
            }
            else {
                this.removeNode(hash, k, null, false, true);
            }
        }
        else if (apply != null) {
            if (treeNode != null) {
                treeNode.putTreeVal(this, array, hash, k, apply);
            }
            else {
                array[n3] = this.newNode(hash, k, apply, node);
                if (n >= 7) {
                    this.treeifyBin(array, hash);
                }
            }
            ++this.modCount;
            ++this.size;
            this.afterNodeInsertion(true);
        }
        return apply;
    }
    
    @Override
    public V merge(final K k, final V v, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
        if (v == null) {
            throw new NullPointerException();
        }
        if (biFunction == null) {
            throw new NullPointerException();
        }
        final int hash = hash(k);
        int n = 0;
        TreeNode<K, V> treeNode = null;
        Node<K, V> treeNode2 = null;
        Node<K, V>[] array;
        int n2;
        if (this.size > this.threshold || (array = this.table) == null || (n2 = array.length) == 0) {
            n2 = (array = this.resize()).length;
        }
        final int n3;
        final Node<K, V> node;
        Label_0185: {
            if ((node = array[n3 = (n2 - 1 & hash)]) != null) {
                if (node instanceof TreeNode) {
                    treeNode2 = (treeNode = (TreeNode<K, V>)node).getTreeNode(hash, k);
                }
                else {
                    Node<K, V> next = node;
                    K key;
                    while (next.hash != hash || ((key = next.key) != k && (k == null || !k.equals(key)))) {
                        ++n;
                        if ((next = next.next) == null) {
                            break Label_0185;
                        }
                    }
                    treeNode2 = next;
                }
            }
        }
        if (treeNode2 != null) {
            V apply;
            if (treeNode2.value != null) {
                apply = (V)biFunction.apply(treeNode2.value, v);
            }
            else {
                apply = v;
            }
            if (apply != null) {
                treeNode2.value = apply;
                this.afterNodeAccess(treeNode2);
            }
            else {
                this.removeNode(hash, k, null, false, true);
            }
            return apply;
        }
        if (v != null) {
            if (treeNode != null) {
                treeNode.putTreeVal(this, array, hash, k, v);
            }
            else {
                array[n3] = this.newNode(hash, k, v, node);
                if (n >= 7) {
                    this.treeifyBin(array, hash);
                }
            }
            ++this.modCount;
            ++this.size;
            this.afterNodeInsertion(true);
        }
        return v;
    }
    
    @Override
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer == null) {
            throw new NullPointerException();
        }
        final Node<K, V>[] table;
        if (this.size > 0 && (table = this.table) != null) {
            final int modCount = this.modCount;
            for (int i = 0; i < table.length; ++i) {
                for (Node<K, V> next = table[i]; next != null; next = next.next) {
                    biConsumer.accept(next.key, next.value);
                }
            }
            if (this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    @Override
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction == null) {
            throw new NullPointerException();
        }
        final Node<K, V>[] table;
        if (this.size > 0 && (table = this.table) != null) {
            final int modCount = this.modCount;
            for (int i = 0; i < table.length; ++i) {
                for (Node<K, V> next = table[i]; next != null; next = next.next) {
                    next.value = (V)biFunction.apply(next.key, next.value);
                }
            }
            if (this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    public Object clone() {
        HashMap hashMap;
        try {
            hashMap = (HashMap)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
        hashMap.reinitialize();
        hashMap.putMapEntries(this, false);
        return hashMap;
    }
    
    final float loadFactor() {
        return this.loadFactor;
    }
    
    final int capacity() {
        return (this.table != null) ? this.table.length : ((this.threshold > 0) ? this.threshold : 16);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final int capacity = this.capacity();
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(capacity);
        objectOutputStream.writeInt(this.size);
        this.internalWriteEntries(objectOutputStream);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.reinitialize();
        if (this.loadFactor <= 0.0f || Float.isNaN(this.loadFactor)) {
            throw new InvalidObjectException("Illegal load factor: " + this.loadFactor);
        }
        objectInputStream.readInt();
        final int int1 = objectInputStream.readInt();
        if (int1 < 0) {
            throw new InvalidObjectException("Illegal mappings count: " + int1);
        }
        if (int1 > 0) {
            final float min = Math.min(Math.max(0.25f, this.loadFactor), 4.0f);
            final float n = int1 / min + 1.0f;
            final int n2 = (n < 16.0f) ? 16 : ((n >= 1.07374182E9f) ? 1073741824 : tableSizeFor((int)n));
            final float n3 = n2 * min;
            this.threshold = ((n2 < 1073741824 && n3 < 1.07374182E9f) ? ((int)n3) : Integer.MAX_VALUE);
            SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Entry[].class, n2);
            this.table = (Node<K, V>[])new Node[n2];
            for (int i = 0; i < int1; ++i) {
                final Object object = objectInputStream.readObject();
                this.putVal(hash(object), (K)object, objectInputStream.readObject(), false, false);
            }
        }
    }
    
    Node<K, V> newNode(final int n, final K k, final V v, final Node<K, V> node) {
        return new Node<K, V>(n, k, v, node);
    }
    
    Node<K, V> replacementNode(final Node<K, V> node, final Node<K, V> node2) {
        return new Node<K, V>(node.hash, node.key, node.value, node2);
    }
    
    TreeNode<K, V> newTreeNode(final int n, final K k, final V v, final Node<K, V> node) {
        return new TreeNode<K, V>(n, k, v, node);
    }
    
    TreeNode<K, V> replacementTreeNode(final Node<K, V> node, final Node<K, V> node2) {
        return new TreeNode<K, V>(node.hash, node.key, node.value, node2);
    }
    
    void reinitialize() {
        this.table = null;
        this.entrySet = null;
        this.keySet = null;
        this.values = null;
        this.modCount = 0;
        this.threshold = 0;
        this.size = 0;
    }
    
    void afterNodeAccess(final Node<K, V> node) {
    }
    
    void afterNodeInsertion(final boolean b) {
    }
    
    void afterNodeRemoval(final Node<K, V> node) {
    }
    
    void internalWriteEntries(final ObjectOutputStream objectOutputStream) throws IOException {
        final Node<K, V>[] table;
        if (this.size > 0 && (table = this.table) != null) {
            for (int i = 0; i < table.length; ++i) {
                for (Node<K, V> next = table[i]; next != null; next = next.next) {
                    objectOutputStream.writeObject(next.key);
                    objectOutputStream.writeObject(next.value);
                }
            }
        }
    }
    
    final class EntryIterator extends HashIterator implements Iterator<Entry<K, V>>
    {
        @Override
        public final Entry<K, V> next() {
            return this.nextNode();
        }
    }
    
    abstract class HashIterator
    {
        Node<K, V> next;
        Node<K, V> current;
        int expectedModCount;
        int index;
        
        HashIterator() {
            this.expectedModCount = HashMap.this.modCount;
            final Node<K, V>[] table = HashMap.this.table;
            final Node<K, V> node = null;
            this.next = node;
            this.current = node;
            this.index = 0;
            if (table != null && HashMap.this.size > 0) {
                while (this.index < table.length && (this.next = table[this.index++]) == null) {}
            }
        }
        
        public final boolean hasNext() {
            return this.next != null;
        }
        
        final Node<K, V> nextNode() {
            final Node<K, V> next = this.next;
            if (HashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (next == null) {
                throw new NoSuchElementException();
            }
            final Node<K, V> current = next;
            this.current = current;
            final Node<K, V> next2 = current.next;
            this.next = next2;
            final Node<K, V>[] table;
            if (next2 == null && (table = HashMap.this.table) != null) {
                while (this.index < table.length && (this.next = table[this.index++]) == null) {}
            }
            return next;
        }
        
        public final void remove() {
            final Node<K, V> current = this.current;
            if (current == null) {
                throw new IllegalStateException();
            }
            if (HashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.current = null;
            final K key = current.key;
            HashMap.this.removeNode(HashMap.hash(key), key, null, false, false);
            this.expectedModCount = HashMap.this.modCount;
        }
    }
    
    static class Node<K, V> implements Entry<K, V>
    {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;
        
        Node(final int hash, final K key, final V value, final Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        @Override
        public final K getKey() {
            return this.key;
        }
        
        @Override
        public final V getValue() {
            return this.value;
        }
        
        @Override
        public final String toString() {
            return this.key + "=" + this.value;
        }
        
        @Override
        public final int hashCode() {
            return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
        }
        
        @Override
        public final V setValue(final V value) {
            final V value2 = this.value;
            this.value = value;
            return value2;
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                if (Objects.equals(this.key, entry.getKey()) && Objects.equals(this.value, entry.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }
    
    final class EntrySet extends AbstractSet<Entry<K, V>>
    {
        @Override
        public final int size() {
            return HashMap.this.size;
        }
        
        @Override
        public final void clear() {
            HashMap.this.clear();
        }
        
        @Override
        public final Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }
        
        @Override
        public final boolean contains(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry entry = (Entry)o;
            final Object key = entry.getKey();
            final Node<K, V> node = HashMap.this.getNode(HashMap.hash(key), key);
            return node != null && node.equals(entry);
        }
        
        @Override
        public final boolean remove(final Object o) {
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                final Object key = entry.getKey();
                return HashMap.this.removeNode(HashMap.hash(key), key, entry.getValue(), true, true) != null;
            }
            return false;
        }
        
        @Override
        public final Spliterator<Entry<K, V>> spliterator() {
            return new EntrySpliterator<K, V>(HashMap.this, 0, -1, 0, 0);
        }
        
        @Override
        public final void forEach(final Consumer<? super Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Node<K, V>[] table;
            if (HashMap.this.size > 0 && (table = HashMap.this.table) != null) {
                final int modCount = HashMap.this.modCount;
                for (int i = 0; i < table.length; ++i) {
                    for (Node<K, V> next = table[i]; next != null; next = next.next) {
                        consumer.accept(next);
                    }
                }
                if (HashMap.this.modCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
    
    static final class EntrySpliterator<K, V> extends HashMapSpliterator<K, V> implements Spliterator<Entry<K, V>>
    {
        EntrySpliterator(final HashMap<K, V> hashMap, final int n, final int n2, final int n3, final int n4) {
            super(hashMap, n, n2, n3, n4);
        }
        
        @Override
        public EntrySpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1;
            EntrySpliterator<K, V> entrySpliterator;
            if (index >= n || this.current != null) {
                entrySpliterator = null;
            }
            else {
                final HashMap<K, V> map;
                final int n2;
                final int index2;
                entrySpliterator = new EntrySpliterator<K, V>((HashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
                map = this.map;
                n2 = index;
                index2 = n;
                this.index = index2;
            }
            return entrySpliterator;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final HashMap<K, V> map = this.map;
            final Node<K, V>[] table = map.table;
            int fence;
            int expectedModCount;
            if ((fence = this.fence) < 0) {
                final int modCount = map.modCount;
                this.expectedModCount = modCount;
                expectedModCount = modCount;
                final int fence2 = (table == null) ? 0 : table.length;
                this.fence = fence2;
                fence = fence2;
            }
            else {
                expectedModCount = this.expectedModCount;
            }
            int index;
            if (table != null && table.length >= fence && (index = this.index) >= 0) {
                final int n = index;
                final int index2 = fence;
                this.index = index2;
                if (n < index2 || this.current != null) {
                    Node<K, V> node = this.current;
                    this.current = null;
                    do {
                        if (node == null) {
                            node = table[index++];
                        }
                        else {
                            consumer.accept(node);
                            node = node.next;
                        }
                    } while (node != null || index < fence);
                    if (map.modCount != expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Node<K, V>[] table = this.map.table;
            final int fence;
            if (table != null && table.length >= (fence = this.getFence()) && this.index >= 0) {
                while (this.current != null || this.index < fence) {
                    if (this.current == null) {
                        this.current = table[this.index++];
                    }
                    else {
                        final Node<K, V> current = this.current;
                        this.current = this.current.next;
                        consumer.accept(current);
                        if (this.map.modCount != this.expectedModCount) {
                            throw new ConcurrentModificationException();
                        }
                        return true;
                    }
                }
            }
            return false;
        }
        
        @Override
        public int characteristics() {
            return ((this.fence < 0 || this.est == this.map.size) ? 64 : 0) | 0x1;
        }
    }
    
    static class HashMapSpliterator<K, V>
    {
        final HashMap<K, V> map;
        Node<K, V> current;
        int index;
        int fence;
        int est;
        int expectedModCount;
        
        HashMapSpliterator(final HashMap<K, V> map, final int index, final int fence, final int est, final int expectedModCount) {
            this.map = map;
            this.index = index;
            this.fence = fence;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }
        
        final int getFence() {
            int fence;
            if ((fence = this.fence) < 0) {
                final HashMap<K, V> map = this.map;
                this.est = map.size;
                this.expectedModCount = map.modCount;
                final Node<K, V>[] table = map.table;
                final int fence2 = (table == null) ? 0 : table.length;
                this.fence = fence2;
                fence = fence2;
            }
            return fence;
        }
        
        public final long estimateSize() {
            this.getFence();
            return this.est;
        }
    }
    
    final class KeyIterator extends HashIterator implements Iterator<K>
    {
        @Override
        public final K next() {
            return this.nextNode().key;
        }
    }
    
    final class KeySet extends AbstractSet<K>
    {
        @Override
        public final int size() {
            return HashMap.this.size;
        }
        
        @Override
        public final void clear() {
            HashMap.this.clear();
        }
        
        @Override
        public final Iterator<K> iterator() {
            return new KeyIterator();
        }
        
        @Override
        public final boolean contains(final Object o) {
            return HashMap.this.containsKey(o);
        }
        
        @Override
        public final boolean remove(final Object o) {
            return HashMap.this.removeNode(HashMap.hash(o), o, null, false, true) != null;
        }
        
        @Override
        public final Spliterator<K> spliterator() {
            return new KeySpliterator<K, Object>(HashMap.this, 0, -1, 0, 0);
        }
        
        @Override
        public final void forEach(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Node<K, V>[] table;
            if (HashMap.this.size > 0 && (table = HashMap.this.table) != null) {
                final int modCount = HashMap.this.modCount;
                for (int i = 0; i < table.length; ++i) {
                    for (Node<K, V> next = table[i]; next != null; next = next.next) {
                        consumer.accept(next.key);
                    }
                }
                if (HashMap.this.modCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
    
    static final class KeySpliterator<K, V> extends HashMapSpliterator<K, V> implements Spliterator<K>
    {
        KeySpliterator(final HashMap<K, V> hashMap, final int n, final int n2, final int n3, final int n4) {
            super(hashMap, n, n2, n3, n4);
        }
        
        @Override
        public KeySpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1;
            KeySpliterator<K, V> keySpliterator;
            if (index >= n || this.current != null) {
                keySpliterator = null;
            }
            else {
                final HashMap<K, V> map;
                final int n2;
                final int index2;
                keySpliterator = new KeySpliterator<K, V>((HashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
                map = this.map;
                n2 = index;
                index2 = n;
                this.index = index2;
            }
            return keySpliterator;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final HashMap<K, V> map = this.map;
            final Node<K, V>[] table = map.table;
            int fence;
            int expectedModCount;
            if ((fence = this.fence) < 0) {
                final int modCount = map.modCount;
                this.expectedModCount = modCount;
                expectedModCount = modCount;
                final int fence2 = (table == null) ? 0 : table.length;
                this.fence = fence2;
                fence = fence2;
            }
            else {
                expectedModCount = this.expectedModCount;
            }
            int index;
            if (table != null && table.length >= fence && (index = this.index) >= 0) {
                final int n = index;
                final int index2 = fence;
                this.index = index2;
                if (n < index2 || this.current != null) {
                    Node<K, V> node = this.current;
                    this.current = null;
                    do {
                        if (node == null) {
                            node = table[index++];
                        }
                        else {
                            consumer.accept(node.key);
                            node = node.next;
                        }
                    } while (node != null || index < fence);
                    if (map.modCount != expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Node<K, V>[] table = this.map.table;
            final int fence;
            if (table != null && table.length >= (fence = this.getFence()) && this.index >= 0) {
                while (this.current != null || this.index < fence) {
                    if (this.current == null) {
                        this.current = table[this.index++];
                    }
                    else {
                        final K key = this.current.key;
                        this.current = this.current.next;
                        consumer.accept(key);
                        if (this.map.modCount != this.expectedModCount) {
                            throw new ConcurrentModificationException();
                        }
                        return true;
                    }
                }
            }
            return false;
        }
        
        @Override
        public int characteristics() {
            return ((this.fence < 0 || this.est == this.map.size) ? 64 : 0) | 0x1;
        }
    }
    
    static final class TreeNode<K, V> extends LinkedHashMap.Entry<K, V>
    {
        TreeNode<K, V> parent;
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;
        boolean red;
        
        TreeNode(final int n, final K k, final V v, final Node<K, V> node) {
            super(n, k, v, node);
        }
        
        final TreeNode<K, V> root() {
            TreeNode treeNode;
            TreeNode<K, V> parent;
            for (treeNode = this; (parent = treeNode.parent) != null; treeNode = parent) {}
            return (TreeNode<K, V>)treeNode;
        }
        
        static <K, V> void moveRootToFront(final Node<K, V>[] array, final TreeNode<K, V> prev) {
            final int length;
            if (prev != null && array != null && (length = array.length) > 0) {
                final int n = length - 1 & prev.hash;
                final TreeNode next = (TreeNode)array[n];
                if (prev != next) {
                    array[n] = prev;
                    final TreeNode<K, V> prev2 = prev.prev;
                    final Node<K, V> next2;
                    if ((next2 = prev.next) != null) {
                        ((TreeNode)next2).prev = (TreeNode<K, V>)prev2;
                    }
                    if (prev2 != null) {
                        prev2.next = next2;
                    }
                    if (next != null) {
                        next.prev = (TreeNode<K, V>)prev;
                    }
                    prev.next = (Node<K, V>)next;
                    prev.prev = null;
                }
                assert checkInvariants(prev);
            }
        }
        
        final TreeNode<K, V> find(final int n, final Object o, Class<?> comparableClass) {
            TreeNode treeNode = this;
            do {
                final TreeNode<K, V> left = treeNode.left;
                final TreeNode<K, V> right = treeNode.right;
                final int hash;
                if ((hash = treeNode.hash) > n) {
                    treeNode = left;
                }
                else if (hash < n) {
                    treeNode = right;
                }
                else {
                    final K key;
                    if ((key = treeNode.key) == o || (o != null && o.equals(key))) {
                        return (TreeNode<K, V>)treeNode;
                    }
                    if (left == null) {
                        treeNode = right;
                    }
                    else if (right == null) {
                        treeNode = left;
                    }
                    else {
                        final int compareComparables;
                        if ((comparableClass != null || (comparableClass = HashMap.comparableClassFor(o)) != null) && (compareComparables = HashMap.compareComparables(comparableClass, o, key)) != 0) {
                            treeNode = ((compareComparables < 0) ? left : right);
                        }
                        else {
                            final TreeNode find;
                            if ((find = right.find(n, o, comparableClass)) != null) {
                                return (TreeNode<K, V>)find;
                            }
                            treeNode = left;
                        }
                    }
                }
            } while (treeNode != null);
            return null;
        }
        
        final TreeNode<K, V> getTreeNode(final int n, final Object o) {
            return ((this.parent != null) ? this.root() : this).find(n, o, null);
        }
        
        static int tieBreakOrder(final Object o, final Object o2) {
            int compareTo;
            if (o == null || o2 == null || (compareTo = o.getClass().getName().compareTo(o2.getClass().getName())) == 0) {
                compareTo = ((System.identityHashCode(o) <= System.identityHashCode(o2)) ? -1 : 1);
            }
            return compareTo;
        }
        
        final void treeify(final Node<K, V>[] array) {
            LinkedHashMap.Entry<K, V> balanceInsertion = null;
            TreeNode treeNode2;
            for (TreeNode treeNode = this; treeNode != null; treeNode = treeNode2) {
                treeNode2 = (TreeNode)treeNode.next;
                final TreeNode treeNode3 = treeNode;
                final TreeNode treeNode4 = treeNode;
                final TreeNode<K, V> treeNode5 = null;
                treeNode4.right = treeNode5;
                treeNode3.left = treeNode5;
                if (balanceInsertion == null) {
                    treeNode.parent = null;
                    treeNode.red = false;
                    balanceInsertion = treeNode;
                }
                else {
                    final K key = treeNode.key;
                    final int hash = treeNode.hash;
                    Class<?> comparableClass = null;
                    LinkedHashMap.Entry<K, V> entry = balanceInsertion;
                    int n;
                    TreeNode parent;
                    do {
                        final K key2 = ((TreeNode)entry).key;
                        final int hash2;
                        if ((hash2 = ((TreeNode)entry).hash) > hash) {
                            n = -1;
                        }
                        else if (hash2 < hash) {
                            n = 1;
                        }
                        else if ((comparableClass == null && (comparableClass = HashMap.comparableClassFor(key)) == null) || (n = HashMap.compareComparables(comparableClass, key, key2)) == 0) {
                            n = tieBreakOrder(key, key2);
                        }
                        parent = (TreeNode)entry;
                    } while ((entry = ((n <= 0) ? ((TreeNode)entry).left : ((TreeNode)entry).right)) != null);
                    treeNode.parent = parent;
                    if (n <= 0) {
                        parent.left = treeNode;
                    }
                    else {
                        parent.right = treeNode;
                    }
                    balanceInsertion = balanceInsertion((TreeNode<K, V>)balanceInsertion, treeNode);
                }
            }
            moveRootToFront(array, (TreeNode<K, V>)balanceInsertion);
        }
        
        final Node<K, V> untreeify(final HashMap<K, V> hashMap) {
            Node<K, V> node = null;
            Node<K, V> node2 = null;
            for (Node<K, V> next = this; next != null; next = next.next) {
                final Node<K, V> replacementNode = hashMap.replacementNode(next, null);
                if (node2 == null) {
                    node = replacementNode;
                }
                else {
                    node2.next = replacementNode;
                }
                node2 = replacementNode;
            }
            return node;
        }
        
        final TreeNode<K, V> putTreeVal(final HashMap<K, V> hashMap, final Node<K, V>[] array, final int n, final K k, final V v) {
            Class<?> comparableClass = null;
            int n2 = 0;
            TreeNode<K, V> treeNode2;
            final TreeNode<K, V> treeNode = treeNode2 = ((this.parent != null) ? this.root() : this);
            while (true) {
                final int hash;
                int n3;
                if ((hash = treeNode2.hash) > n) {
                    n3 = -1;
                }
                else if (hash < n) {
                    n3 = 1;
                }
                else {
                    final K key;
                    if ((key = treeNode2.key) == k || (k != null && k.equals(key))) {
                        return treeNode2;
                    }
                    if ((comparableClass == null && (comparableClass = HashMap.comparableClassFor(k)) == null) || (n3 = HashMap.compareComparables(comparableClass, k, key)) == 0) {
                        if (n2 == 0) {
                            n2 = 1;
                            final TreeNode<K, V> left;
                            TreeNode<K, V> treeNode3;
                            final TreeNode<K, V> right;
                            if (((left = treeNode2.left) != null && (treeNode3 = (TreeNode<K, V>)left.find(n, k, comparableClass)) != null) || ((right = treeNode2.right) != null && (treeNode3 = right.find(n, k, comparableClass)) != null)) {
                                return treeNode3;
                            }
                        }
                        n3 = tieBreakOrder(k, key);
                    }
                }
                final TreeNode<K, V> treeNode4 = treeNode2;
                if ((treeNode2 = ((n3 <= 0) ? treeNode2.left : treeNode2.right)) == null) {
                    final Node<K, V> next = treeNode4.next;
                    final TreeNode<K, V> treeNode5 = hashMap.newTreeNode(n, k, v, next);
                    if (n3 <= 0) {
                        treeNode4.left = treeNode5;
                    }
                    else {
                        treeNode4.right = treeNode5;
                    }
                    treeNode4.next = treeNode5;
                    final TreeNode<K, V> treeNode6 = treeNode5;
                    final TreeNode<K, V> treeNode7 = treeNode5;
                    final TreeNode<K, V> treeNode8 = treeNode4;
                    treeNode7.prev = treeNode8;
                    treeNode6.parent = treeNode8;
                    if (next != null) {
                        ((TreeNode)next).prev = treeNode5;
                    }
                    moveRootToFront(array, (TreeNode<K, V>)balanceInsertion((TreeNode<K, V>)treeNode, (TreeNode<K, V>)treeNode5));
                    return null;
                }
            }
        }
        
        final void removeTreeNode(final HashMap<K, V> hashMap, final Node<K, V>[] array, final boolean b) {
            final int length;
            if (array == null || (length = array.length) == 0) {
                return;
            }
            final int n = length - 1 & this.hash;
            LinkedHashMap.Entry<K, V> root;
            Node<K, V> node = root = (LinkedHashMap.Entry<K, V>)(TreeNode)array[n];
            final TreeNode next = (TreeNode)this.next;
            final TreeNode<K, V> prev = this.prev;
            if (prev == null) {
                node = (array[n] = (Node<K, V>)next);
            }
            else {
                prev.next = (Node<K, V>)next;
            }
            if (next != null) {
                next.prev = prev;
            }
            if (node == null) {
                return;
            }
            if (((TreeNode)root).parent != null) {
                root = ((TreeNode)root).root();
            }
            final TreeNode<K, V> left;
            if (root == null || (b && (((TreeNode)root).right == null || (left = ((TreeNode)root).left) == null || left.left == null))) {
                array[n] = ((TreeNode)node).untreeify(hashMap);
                return;
            }
            final TreeNode<K, V> left2 = this.left;
            final TreeNode<K, V> right = this.right;
            TreeNode<K, V> treeNode;
            if (left2 != null && right != null) {
                TreeNode<K, V> right2;
                TreeNode<K, V> left3;
                for (right2 = right; (left3 = right2.left) != null; right2 = left3) {}
                final boolean red = right2.red;
                right2.red = this.red;
                this.red = red;
                final TreeNode<K, V> right3 = right2.right;
                final TreeNode<K, V> parent = this.parent;
                if (right2 == right) {
                    this.parent = right2;
                    right2.right = this;
                }
                else {
                    final TreeNode<K, V> parent2 = right2.parent;
                    if ((this.parent = parent2) != null) {
                        if (right2 == parent2.left) {
                            parent2.left = this;
                        }
                        else {
                            parent2.right = this;
                        }
                    }
                    if ((right2.right = right) != null) {
                        right.parent = right2;
                    }
                }
                this.left = null;
                if ((this.right = right3) != null) {
                    right3.parent = this;
                }
                if ((right2.left = left2) != null) {
                    left2.parent = right2;
                }
                if ((right2.parent = parent) == null) {
                    root = right2;
                }
                else if (this == parent.left) {
                    parent.left = right2;
                }
                else {
                    parent.right = right2;
                }
                if (right3 != null) {
                    treeNode = right3;
                }
                else {
                    treeNode = this;
                }
            }
            else if (left2 != null) {
                treeNode = left2;
            }
            else if (right != null) {
                treeNode = right;
            }
            else {
                treeNode = this;
            }
            if (treeNode != this) {
                final TreeNode<K, V> treeNode2 = treeNode;
                final TreeNode<K, V> parent3 = this.parent;
                treeNode2.parent = parent3;
                final TreeNode<K, V> treeNode3 = parent3;
                if (treeNode3 == null) {
                    root = treeNode;
                }
                else if (this == treeNode3.left) {
                    treeNode3.left = treeNode;
                }
                else {
                    treeNode3.right = treeNode;
                }
                final TreeNode<K, V> left4 = null;
                this.parent = left4;
                this.right = left4;
                this.left = left4;
            }
            final TreeNode<K, V> treeNode4 = (TreeNode<K, V>)(this.red ? root : balanceDeletion((TreeNode<K, V>)root, treeNode));
            if (treeNode == this) {
                final TreeNode<K, V> parent4 = this.parent;
                this.parent = null;
                if (parent4 != null) {
                    if (this == parent4.left) {
                        parent4.left = null;
                    }
                    else if (this == parent4.right) {
                        parent4.right = null;
                    }
                }
            }
            if (b) {
                moveRootToFront(array, treeNode4);
            }
        }
        
        final void split(final HashMap<K, V> hashMap, final Node<K, V>[] array, final int n, final int n2) {
            TreeNode treeNode = null;
            TreeNode<K, V> prev = null;
            TreeNode treeNode2 = null;
            TreeNode<K, V> prev2 = null;
            int n3 = 0;
            int n4 = 0;
            TreeNode treeNode4;
            for (TreeNode treeNode3 = this; treeNode3 != null; treeNode3 = treeNode4) {
                treeNode4 = (TreeNode)treeNode3.next;
                treeNode3.next = null;
                if ((treeNode3.hash & n2) == 0x0) {
                    if ((treeNode3.prev = prev) == null) {
                        treeNode = treeNode3;
                    }
                    else {
                        prev.next = treeNode3;
                    }
                    prev = treeNode3;
                    ++n3;
                }
                else {
                    if ((treeNode3.prev = prev2) == null) {
                        treeNode2 = treeNode3;
                    }
                    else {
                        prev2.next = treeNode3;
                    }
                    prev2 = treeNode3;
                    ++n4;
                }
            }
            if (treeNode != null) {
                if (n3 <= 6) {
                    array[n] = treeNode.untreeify(hashMap);
                }
                else {
                    array[n] = treeNode;
                    if (treeNode2 != null) {
                        treeNode.treeify(array);
                    }
                }
            }
            if (treeNode2 != null) {
                if (n4 <= 6) {
                    array[n + n2] = treeNode2.untreeify(hashMap);
                }
                else {
                    array[n + n2] = treeNode2;
                    if (treeNode != null) {
                        treeNode2.treeify(array);
                    }
                }
            }
        }
        
        static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> treeNode, final TreeNode<K, V> treeNode2) {
            final TreeNode<K, V> right;
            if (treeNode2 != null && (right = treeNode2.right) != null) {
                final TreeNode<K, V> left = right.left;
                treeNode2.right = left;
                final TreeNode<K, V> treeNode3;
                if ((treeNode3 = left) != null) {
                    treeNode3.parent = treeNode2;
                }
                final TreeNode<K, V> treeNode4 = right;
                final TreeNode<K, V> parent = treeNode2.parent;
                treeNode4.parent = parent;
                final TreeNode<K, V> treeNode5;
                if ((treeNode5 = parent) == null) {
                    (treeNode = right).red = false;
                }
                else if (treeNode5.left == treeNode2) {
                    treeNode5.left = right;
                }
                else {
                    treeNode5.right = right;
                }
                right.left = treeNode2;
                treeNode2.parent = right;
            }
            return treeNode;
        }
        
        static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> treeNode, final TreeNode<K, V> treeNode2) {
            final TreeNode<K, V> left;
            if (treeNode2 != null && (left = treeNode2.left) != null) {
                final TreeNode<K, V> right = left.right;
                treeNode2.left = right;
                final TreeNode<K, V> treeNode3;
                if ((treeNode3 = right) != null) {
                    treeNode3.parent = treeNode2;
                }
                final TreeNode<K, V> treeNode4 = left;
                final TreeNode<K, V> parent = treeNode2.parent;
                treeNode4.parent = parent;
                final TreeNode<K, V> treeNode5;
                if ((treeNode5 = parent) == null) {
                    (treeNode = left).red = false;
                }
                else if (treeNode5.right == treeNode2) {
                    treeNode5.right = left;
                }
                else {
                    treeNode5.left = left;
                }
                left.right = treeNode2;
                treeNode2.parent = left;
            }
            return treeNode;
        }
        
        static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> treeNode, TreeNode<K, V> treeNode2) {
            treeNode2.red = true;
            TreeNode<K, V> treeNode3;
            while ((treeNode3 = treeNode2.parent) != null) {
                TreeNode<K, V> parent;
                if (!treeNode3.red || (parent = treeNode3.parent) == null) {
                    return (TreeNode<K, V>)treeNode;
                }
                final TreeNode<K, V> left;
                if (treeNode3 == (left = parent.left)) {
                    final TreeNode<K, V> right;
                    if ((right = parent.right) != null && right.red) {
                        right.red = false;
                        treeNode3.red = false;
                        parent.red = true;
                        treeNode2 = parent;
                    }
                    else {
                        if (treeNode2 == treeNode3.right) {
                            treeNode = rotateLeft(treeNode, (TreeNode<Object, Object>)(treeNode2 = treeNode3));
                            parent = (((treeNode3 = treeNode2.parent) == null) ? null : treeNode3.parent);
                        }
                        if (treeNode3 == null) {
                            continue;
                        }
                        treeNode3.red = false;
                        if (parent == null) {
                            continue;
                        }
                        parent.red = true;
                        treeNode = rotateRight(treeNode, (TreeNode<Object, Object>)parent);
                    }
                }
                else if (left != null && left.red) {
                    left.red = false;
                    treeNode3.red = false;
                    parent.red = true;
                    treeNode2 = parent;
                }
                else {
                    if (treeNode2 == treeNode3.left) {
                        treeNode = rotateRight(treeNode, (TreeNode<Object, Object>)(treeNode2 = treeNode3));
                        parent = (((treeNode3 = treeNode2.parent) == null) ? null : treeNode3.parent);
                    }
                    if (treeNode3 == null) {
                        continue;
                    }
                    treeNode3.red = false;
                    if (parent == null) {
                        continue;
                    }
                    parent.red = true;
                    treeNode = rotateLeft(treeNode, (TreeNode<Object, Object>)parent);
                }
            }
            treeNode2.red = false;
            return treeNode2;
        }
        
        static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> treeNode, TreeNode<K, V> o) {
            while (o != null && o != treeNode) {
                LinkedHashMap.Entry<K, V> entry;
                if ((entry = ((TreeNode)o).parent) == null) {
                    ((TreeNode)o).red = false;
                    return (TreeNode<K, V>)o;
                }
                if (((TreeNode)o).red) {
                    ((TreeNode)o).red = false;
                    return (TreeNode<K, V>)treeNode;
                }
                Object left;
                if ((left = ((TreeNode)entry).left) == o) {
                    Object right;
                    if ((right = ((TreeNode)entry).right) != null && ((TreeNode)right).red) {
                        ((TreeNode)right).red = false;
                        ((TreeNode)entry).red = true;
                        treeNode = rotateLeft(treeNode, (TreeNode<Object, Object>)entry);
                        right = (((entry = ((TreeNode)o).parent) == null) ? null : ((TreeNode)entry).right);
                    }
                    if (right == null) {
                        o = entry;
                    }
                    else {
                        final TreeNode<K, V> left2 = ((TreeNode)right).left;
                        final TreeNode<K, V> right2 = ((TreeNode)right).right;
                        if ((right2 == null || !right2.red) && (left2 == null || !left2.red)) {
                            ((TreeNode)right).red = true;
                            o = entry;
                        }
                        else {
                            if (right2 == null || !right2.red) {
                                if (left2 != null) {
                                    left2.red = false;
                                }
                                ((TreeNode)right).red = true;
                                treeNode = rotateRight(treeNode, (TreeNode<Object, Object>)right);
                                right = (((entry = ((TreeNode)o).parent) == null) ? null : ((TreeNode)entry).right);
                            }
                            if (right != null) {
                                ((TreeNode)right).red = (entry != null && ((TreeNode)entry).red);
                                final LinkedHashMap.Entry<K, V> right3;
                                if ((right3 = ((TreeNode)right).right) != null) {
                                    ((TreeNode)right3).red = false;
                                }
                            }
                            if (entry != null) {
                                ((TreeNode)entry).red = false;
                                treeNode = rotateLeft(treeNode, (TreeNode<Object, Object>)entry);
                            }
                            o = treeNode;
                        }
                    }
                }
                else {
                    if (left != null && ((TreeNode)left).red) {
                        ((TreeNode)left).red = false;
                        ((TreeNode)entry).red = true;
                        treeNode = rotateRight(treeNode, (TreeNode<Object, Object>)entry);
                        left = (((entry = ((TreeNode)o).parent) == null) ? null : ((TreeNode)entry).left);
                    }
                    if (left == null) {
                        o = entry;
                    }
                    else {
                        final TreeNode<K, V> left3 = ((TreeNode)left).left;
                        final TreeNode<K, V> right4 = ((TreeNode)left).right;
                        if ((left3 == null || !left3.red) && (right4 == null || !right4.red)) {
                            ((TreeNode)left).red = true;
                            o = entry;
                        }
                        else {
                            if (left3 == null || !left3.red) {
                                if (right4 != null) {
                                    right4.red = false;
                                }
                                ((TreeNode)left).red = true;
                                treeNode = rotateLeft(treeNode, (TreeNode<Object, Object>)left);
                                left = (((entry = ((TreeNode)o).parent) == null) ? null : ((TreeNode)entry).left);
                            }
                            if (left != null) {
                                ((TreeNode)left).red = (entry != null && ((TreeNode)entry).red);
                                final LinkedHashMap.Entry<K, V> left4;
                                if ((left4 = ((TreeNode)left).left) != null) {
                                    ((TreeNode)left4).red = false;
                                }
                            }
                            if (entry != null) {
                                ((TreeNode)entry).red = false;
                                treeNode = rotateRight(treeNode, (TreeNode<Object, Object>)entry);
                            }
                            o = treeNode;
                        }
                    }
                }
            }
            return (TreeNode<K, V>)treeNode;
        }
        
        static <K, V> boolean checkInvariants(final TreeNode<K, V> treeNode) {
            final TreeNode<K, V> parent = treeNode.parent;
            final TreeNode<K, V> left = treeNode.left;
            final TreeNode<K, V> right = treeNode.right;
            final TreeNode<K, V> prev = treeNode.prev;
            final TreeNode treeNode2 = (TreeNode)treeNode.next;
            return (prev == null || prev.next == treeNode) && (treeNode2 == null || treeNode2.prev == treeNode) && (parent == null || treeNode == parent.left || treeNode == parent.right) && (left == null || (left.parent == treeNode && left.hash <= treeNode.hash)) && (right == null || (right.parent == treeNode && right.hash >= treeNode.hash)) && (!treeNode.red || left == null || !left.red || right == null || !right.red) && (left == null || checkInvariants((TreeNode<Object, Object>)left)) && (right == null || checkInvariants((TreeNode<Object, Object>)right));
        }
    }
    
    final class ValueIterator extends HashIterator implements Iterator<V>
    {
        @Override
        public final V next() {
            return this.nextNode().value;
        }
    }
    
    static final class ValueSpliterator<K, V> extends HashMapSpliterator<K, V> implements Spliterator<V>
    {
        ValueSpliterator(final HashMap<K, V> hashMap, final int n, final int n2, final int n3, final int n4) {
            super(hashMap, n, n2, n3, n4);
        }
        
        @Override
        public ValueSpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1;
            ValueSpliterator<K, V> valueSpliterator;
            if (index >= n || this.current != null) {
                valueSpliterator = null;
            }
            else {
                final HashMap<K, V> map;
                final int n2;
                final int index2;
                valueSpliterator = new ValueSpliterator<K, V>((HashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
                map = this.map;
                n2 = index;
                index2 = n;
                this.index = index2;
            }
            return valueSpliterator;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final HashMap<K, V> map = this.map;
            final Node<K, V>[] table = map.table;
            int fence;
            int expectedModCount;
            if ((fence = this.fence) < 0) {
                final int modCount = map.modCount;
                this.expectedModCount = modCount;
                expectedModCount = modCount;
                final int fence2 = (table == null) ? 0 : table.length;
                this.fence = fence2;
                fence = fence2;
            }
            else {
                expectedModCount = this.expectedModCount;
            }
            int index;
            if (table != null && table.length >= fence && (index = this.index) >= 0) {
                final int n = index;
                final int index2 = fence;
                this.index = index2;
                if (n < index2 || this.current != null) {
                    Node<K, V> node = this.current;
                    this.current = null;
                    do {
                        if (node == null) {
                            node = table[index++];
                        }
                        else {
                            consumer.accept(node.value);
                            node = node.next;
                        }
                    } while (node != null || index < fence);
                    if (map.modCount != expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Node<K, V>[] table = this.map.table;
            final int fence;
            if (table != null && table.length >= (fence = this.getFence()) && this.index >= 0) {
                while (this.current != null || this.index < fence) {
                    if (this.current == null) {
                        this.current = table[this.index++];
                    }
                    else {
                        final V value = this.current.value;
                        this.current = this.current.next;
                        consumer.accept(value);
                        if (this.map.modCount != this.expectedModCount) {
                            throw new ConcurrentModificationException();
                        }
                        return true;
                    }
                }
            }
            return false;
        }
        
        @Override
        public int characteristics() {
            return (this.fence < 0 || this.est == this.map.size) ? 64 : 0;
        }
    }
    
    final class Values extends AbstractCollection<V>
    {
        @Override
        public final int size() {
            return HashMap.this.size;
        }
        
        @Override
        public final void clear() {
            HashMap.this.clear();
        }
        
        @Override
        public final Iterator<V> iterator() {
            return new ValueIterator();
        }
        
        @Override
        public final boolean contains(final Object o) {
            return HashMap.this.containsValue(o);
        }
        
        @Override
        public final Spliterator<V> spliterator() {
            return new ValueSpliterator<Object, V>(HashMap.this, 0, -1, 0, 0);
        }
        
        @Override
        public final void forEach(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Node<K, V>[] table;
            if (HashMap.this.size > 0 && (table = HashMap.this.table) != null) {
                final int modCount = HashMap.this.modCount;
                for (int i = 0; i < table.length; ++i) {
                    for (Node<K, V> next = table[i]; next != null; next = next.next) {
                        consumer.accept(next.value);
                    }
                }
                if (HashMap.this.modCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
}
