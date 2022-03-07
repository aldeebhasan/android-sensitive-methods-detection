package java.util.concurrent;

import sun.misc.*;
import java.io.*;
import java.util.function.*;
import java.util.*;
import java.lang.invoke.*;

public class ConcurrentSkipListMap<K, V> extends AbstractMap<K, V> implements ConcurrentNavigableMap<K, V>, Cloneable, Serializable
{
    private static final long serialVersionUID = -8627078645895051609L;
    private static final Object BASE_HEADER;
    private transient volatile HeadIndex<K, V> head;
    final Comparator<? super K> comparator;
    private transient KeySet<K> keySet;
    private transient EntrySet<K, V> entrySet;
    private transient Values<V> values;
    private transient ConcurrentNavigableMap<K, V> descendingMap;
    private static final int EQ = 1;
    private static final int LT = 2;
    private static final int GT = 0;
    private static final Unsafe UNSAFE;
    private static final long headOffset;
    private static final long SECONDARY;
    
    private void initialize() {
        this.keySet = null;
        this.entrySet = null;
        this.values = null;
        this.descendingMap = null;
        this.head = new HeadIndex<K, V>(new Node<K, V>(null, ConcurrentSkipListMap.BASE_HEADER, null), null, null, 1);
    }
    
    private boolean casHead(final HeadIndex<K, V> headIndex, final HeadIndex<K, V> headIndex2) {
        return ConcurrentSkipListMap.UNSAFE.compareAndSwapObject(this, ConcurrentSkipListMap.headOffset, headIndex, headIndex2);
    }
    
    static final int cpr(final Comparator comparator, final Object o, final Object o2) {
        return (comparator != null) ? comparator.compare(o, o2) : ((Comparable)o).compareTo(o2);
    }
    
    private Node<K, V> findPredecessor(final Object o, final Comparator<? super K> comparator) {
        if (o == null) {
            throw new NullPointerException();
        }
        HeadIndex<K, V> head = null;
    Block_6:
        while (true) {
            head = this.head;
            Index<K, V> index = head.right;
            while (true) {
                if (index != null) {
                    final Node<K, V> node = index.node;
                    final K key = node.key;
                    if (node.value == null) {
                        if (!head.unlink(index)) {
                            break;
                        }
                        index = head.right;
                        continue;
                    }
                    else if (cpr(comparator, o, key) > 0) {
                        head = (HeadIndex<K, V>)index;
                        index = index.right;
                        continue;
                    }
                }
                final Index<K, V> down;
                if ((down = head.down) == null) {
                    break Block_6;
                }
                head = (HeadIndex<K, V>)down;
                index = down.right;
            }
        }
        return head.node;
    }
    
    private Node<K, V> findNode(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        final Comparator<? super K> comparator = this.comparator;
    Label_0017:
        while (true) {
            Node<K, V> predecessor = this.findPredecessor(o, comparator);
            Node<K, V> next = predecessor.next;
            while (next != null) {
                final Node<K, V> next2 = next.next;
                if (next == predecessor.next) {
                    final Object value;
                    if ((value = next.value) == null) {
                        next.helpDelete(predecessor, next2);
                    }
                    else if (predecessor.value != null) {
                        if (value != next) {
                            final int cpr;
                            if ((cpr = cpr(comparator, o, next.key)) == 0) {
                                return next;
                            }
                            if (cpr < 0) {
                                break;
                            }
                            predecessor = next;
                            next = next2;
                            continue;
                        }
                    }
                }
                continue Label_0017;
            }
            break;
        }
        return null;
    }
    
    private V doGet(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        final Comparator<? super K> comparator = this.comparator;
    Label_0017:
        while (true) {
            Node<K, V> predecessor = this.findPredecessor(o, comparator);
            Node<K, V> next = predecessor.next;
            while (next != null) {
                final Node<K, V> next2 = next.next;
                if (next == predecessor.next) {
                    final Object value;
                    if ((value = next.value) == null) {
                        next.helpDelete(predecessor, next2);
                    }
                    else if (predecessor.value != null) {
                        if (value != next) {
                            final int cpr;
                            if ((cpr = cpr(comparator, o, next.key)) == 0) {
                                return (V)value;
                            }
                            if (cpr < 0) {
                                break;
                            }
                            predecessor = next;
                            next = next2;
                            continue;
                        }
                    }
                }
                continue Label_0017;
            }
            break;
        }
        return null;
    }
    
    private V doPut(final K k, final V v, final boolean b) {
        if (k == null) {
            throw new NullPointerException();
        }
        final Comparator<? super K> comparator = this.comparator;
    Label_0183_Outer:
        while (true) {
            Node<K, V> predecessor = this.findPredecessor(k, comparator);
            Node<K, V> next = predecessor.next;
            Node node;
            while (true) {
                while (next != null) {
                    final Node<K, V> next2 = next.next;
                    if (next == predecessor.next) {
                        final Object value;
                        if ((value = next.value) == null) {
                            next.helpDelete(predecessor, next2);
                        }
                        else if (predecessor.value != null) {
                            if (value != next) {
                                final int cpr;
                                if ((cpr = cpr(comparator, k, next.key)) > 0) {
                                    predecessor = next;
                                    next = next2;
                                    continue Label_0183_Outer;
                                }
                                if (cpr != 0) {
                                    break;
                                }
                                if (b || next.casValue(value, v)) {
                                    return (V)value;
                                }
                            }
                        }
                    }
                    continue Label_0183_Outer;
                }
                node = new Node<K, V>(k, v, (Node<Object, Object>)next);
                if (!predecessor.casNext(next, (Node<K, V>)node)) {
                    continue;
                }
                break;
            }
            int nextSecondarySeed = ThreadLocalRandom.nextSecondarySeed();
            if ((nextSecondarySeed & 0x80000001) == 0x0) {
                int n = 1;
                while (((nextSecondarySeed >>>= 1) & 0x1) != 0x0) {
                    ++n;
                }
                Index<K, V> index = null;
                HeadIndex<K, V> headIndex = this.head;
                final int level;
                if (n <= (level = headIndex.level)) {
                    for (int i = 1; i <= n; ++i) {
                        index = new Index<K, V>((Node<Object, Object>)node, (Index<Object, Object>)index, null);
                    }
                }
                else {
                    n = level + 1;
                    final Index[] array = new Index[n + 1];
                    for (int j = 1; j <= n; ++j) {
                        index = (Index<K, V>)(array[j] = new Index((Node<K, V>)node, (Index<K, V>)index, null));
                    }
                    while (true) {
                        headIndex = this.head;
                        final int level2 = headIndex.level;
                        if (n <= level2) {
                            break;
                        }
                        HeadIndex<Object, Object> headIndex2 = (HeadIndex<Object, Object>)headIndex;
                        final Node<K, V> node2 = (Node<K, V>)headIndex.node;
                        for (int l = level2 + 1; l <= n; ++l) {
                            headIndex2 = new HeadIndex<Object, Object>((Node<Object, Object>)node2, headIndex2, array[l], l);
                        }
                        if (this.casHead(headIndex, (HeadIndex<K, V>)headIndex2)) {
                            headIndex = (HeadIndex<K, V>)headIndex2;
                            index = (Index<K, V>)array[n = level2];
                            break;
                        }
                    }
                }
                int n2 = n;
            Label_0437:
                while (true) {
                    int level3 = headIndex.level;
                    Index<K, V> down = headIndex;
                    Index<K, V> index2 = down.right;
                    Index<K, V> down2 = index;
                    while (down != null) {
                        if (down2 == null) {
                            break;
                        }
                        if (index2 != null) {
                            final Node<K, V> node3 = index2.node;
                            final int cpr2 = cpr(comparator, k, node3.key);
                            if (node3.value == null) {
                                if (!down.unlink(index2)) {
                                    continue Label_0641;
                                }
                                index2 = down.right;
                                continue Label_0183_Outer;
                            }
                            else if (cpr2 > 0) {
                                down = index2;
                                index2 = index2.right;
                                continue Label_0183_Outer;
                            }
                        }
                        if (level3 == n2) {
                            if (!down.link(index2, down2)) {
                                continue Label_0641;
                            }
                            if (down2.node.value == null) {
                                this.findNode(k);
                                break;
                            }
                            if (--n2 == 0) {
                                break;
                            }
                        }
                        if (--level3 >= n2 && level3 < n) {
                            down2 = down2.down;
                        }
                        down = down.down;
                        index2 = down.right;
                        continue Label_0183_Outer;
                        Label_0641: {
                            continue Label_0437;
                        }
                    }
                    break;
                }
            }
            return null;
        }
    }
    
    final V doRemove(final Object o, final Object o2) {
        if (o == null) {
            throw new NullPointerException();
        }
        final Comparator<? super K> comparator = this.comparator;
    Label_0017:
        while (true) {
            Node<K, V> predecessor = this.findPredecessor(o, comparator);
            Node<K, V> next = predecessor.next;
            while (next != null) {
                final Node<K, V> next2 = next.next;
                if (next == predecessor.next) {
                    final Object value;
                    if ((value = next.value) == null) {
                        next.helpDelete(predecessor, next2);
                    }
                    else if (predecessor.value != null) {
                        if (value != next) {
                            final int cpr;
                            if ((cpr = cpr(comparator, o, next.key)) < 0) {
                                break;
                            }
                            if (cpr > 0) {
                                predecessor = next;
                                next = next2;
                                continue;
                            }
                            if (o2 != null && !o2.equals(value)) {
                                break;
                            }
                            if (next.casValue(value, null)) {
                                if (!next.appendMarker(next2) || !predecessor.casNext(next, next2)) {
                                    this.findNode(o);
                                }
                                else {
                                    this.findPredecessor(o, comparator);
                                    if (this.head.right == null) {
                                        this.tryReduceLevel();
                                    }
                                }
                                return (V)value;
                            }
                        }
                    }
                }
                continue Label_0017;
            }
            break;
        }
        return null;
    }
    
    private void tryReduceLevel() {
        final HeadIndex<K, V> head = this.head;
        final HeadIndex headIndex;
        final HeadIndex headIndex2;
        if (head.level > 3 && (headIndex = (HeadIndex)head.down) != null && (headIndex2 = (HeadIndex)headIndex.down) != null && headIndex2.right == null && headIndex.right == null && head.right == null && this.casHead(head, headIndex) && head.right != null) {
            this.casHead(headIndex, head);
        }
    }
    
    final Node<K, V> findFirst() {
        Node<K, V> node;
        Node<K, V> next;
        while ((next = (node = this.head.node).next) != null) {
            if (next.value != null) {
                return next;
            }
            next.helpDelete(node, next.next);
        }
        return null;
    }
    
    private Map.Entry<K, V> doRemoveFirstEntry() {
        Node<K, V> node;
        Node<K, V> next;
        while ((next = (node = this.head.node).next) != null) {
            final Node<K, V> next2 = next.next;
            if (next != node.next) {
                continue;
            }
            final Object value = next.value;
            if (value == null) {
                next.helpDelete(node, next2);
            }
            else {
                if (!next.casValue(value, null)) {
                    continue;
                }
                if (!next.appendMarker(next2) || !node.casNext(next, next2)) {
                    this.findFirst();
                }
                this.clearIndexToFirst();
                return new SimpleImmutableEntry<K, V>(next.key, value);
            }
        }
        return null;
    }
    
    private void clearIndexToFirst() {
    Block_3:
        while (true) {
            Index<K, V> index = this.head;
            while (true) {
                final Index<K, V> right = index.right;
                if (right != null && right.indexesDeletedNode() && !index.unlink(right)) {
                    break;
                }
                if ((index = index.down) == null) {
                    break Block_3;
                }
            }
        }
        if (this.head.right == null) {
            this.tryReduceLevel();
        }
    }
    
    private Map.Entry<K, V> doRemoveLastEntry() {
        while (true) {
            Node<K, V> predecessorOfLast = this.findPredecessorOfLast();
            Node<K, V> next = predecessorOfLast.next;
            if (next == null) {
                if (predecessorOfLast.isBaseHeader()) {
                    return null;
                }
                continue;
            }
            else {
                while (true) {
                    final Node<K, V> next2 = (Node<K, V>)next.next;
                    if (next != predecessorOfLast.next) {
                        break;
                    }
                    final Object value = next.value;
                    if (value == null) {
                        next.helpDelete(predecessorOfLast, (Node<K, V>)next2);
                        break;
                    }
                    if (predecessorOfLast.value == null) {
                        break;
                    }
                    if (value == next) {
                        break;
                    }
                    if (next2 != null) {
                        predecessorOfLast = next;
                        next = (Node<K, V>)next2;
                    }
                    else {
                        if (!next.casValue(value, null)) {
                            break;
                        }
                        final K key = next.key;
                        if (!next.appendMarker((Node<K, V>)next2) || !predecessorOfLast.casNext(next, (Node<K, V>)next2)) {
                            this.findNode(key);
                        }
                        else {
                            this.findPredecessor(key, this.comparator);
                            if (this.head.right == null) {
                                this.tryReduceLevel();
                            }
                        }
                        return new SimpleImmutableEntry<K, V>(key, value);
                    }
                }
            }
        }
    }
    
    final Node<K, V> findLast() {
        HeadIndex<K, V> headIndex = this.head;
        Node<K, V> node;
        while (true) {
            final Index<K, V> right;
            if ((right = headIndex.right) != null) {
                if (right.indexesDeletedNode()) {
                    headIndex.unlink(right);
                    headIndex = this.head;
                }
                else {
                    headIndex = (HeadIndex<K, V>)right;
                }
            }
            else {
                final Index<K, V> down;
                if ((down = headIndex.down) == null) {
                    node = headIndex.node;
                    Object next = node.next;
                    while (next != null) {
                        final Node<K, V> next2 = ((Node)next).next;
                        if (next == node.next) {
                            final Object value = ((Node)next).value;
                            if (value == null) {
                                ((Node<K, V>)next).helpDelete(node, (Node<K, V>)next2);
                            }
                            else if (node.value != null) {
                                if (value != next) {
                                    node = (Node<K, V>)next;
                                    next = next2;
                                    continue;
                                }
                            }
                        }
                        headIndex = this.head;
                        continue Label_0165;
                    }
                    break;
                }
                headIndex = (HeadIndex<K, V>)down;
            }
            Label_0165:;
        }
        return node.isBaseHeader() ? null : node;
    }
    
    private Node<K, V> findPredecessorOfLast() {
        HeadIndex<K, V> head = null;
    Label_0059:
        while (true) {
            head = this.head;
            while (true) {
                final Index<K, V> right;
                if ((right = head.right) != null) {
                    if (right.indexesDeletedNode()) {
                        head.unlink(right);
                        break;
                    }
                    if (right.node.next != null) {
                        head = (HeadIndex<K, V>)right;
                        continue;
                    }
                }
                final Index<K, V> down;
                if ((down = head.down) == null) {
                    break Label_0059;
                }
                head = (HeadIndex<K, V>)down;
            }
        }
        return head.node;
    }
    
    final Node<K, V> findNear(final K k, final int n, final Comparator<? super K> comparator) {
        if (k == null) {
            throw new NullPointerException();
        }
    Label_0012:
        while (true) {
            Node<K, V> predecessor = this.findPredecessor(k, comparator);
            Object next = predecessor.next;
            while (next != null) {
                final Node<K, V> next2 = ((Node)next).next;
                if (next == predecessor.next) {
                    final Object value;
                    if ((value = ((Node)next).value) == null) {
                        ((Node<K, V>)next).helpDelete(predecessor, (Node<K, V>)next2);
                    }
                    else if (predecessor.value != null) {
                        if (value != next) {
                            final int cpr = cpr(comparator, k, ((Node)next).key);
                            if ((cpr == 0 && (n & 0x1) != 0x0) || (cpr < 0 && (n & 0x2) == 0x0)) {
                                return (Node<K, V>)next;
                            }
                            if (cpr <= 0 && (n & 0x2) != 0x0) {
                                return predecessor.isBaseHeader() ? null : predecessor;
                            }
                            predecessor = (Node<K, V>)next;
                            next = next2;
                            continue;
                        }
                    }
                }
                continue Label_0012;
            }
            return ((n & 0x2) == 0x0 || predecessor.isBaseHeader()) ? null : predecessor;
        }
    }
    
    final SimpleImmutableEntry<K, V> getNear(final K k, final int n) {
        final Comparator<? super K> comparator = this.comparator;
        while (true) {
            final Node<K, V> near = this.findNear(k, n, comparator);
            if (near == null) {
                return null;
            }
            final SimpleImmutableEntry<K, V> snapshot = near.createSnapshot();
            if (snapshot != null) {
                return snapshot;
            }
        }
    }
    
    public ConcurrentSkipListMap() {
        this.comparator = null;
        this.initialize();
    }
    
    public ConcurrentSkipListMap(final Comparator<? super K> comparator) {
        this.comparator = comparator;
        this.initialize();
    }
    
    public ConcurrentSkipListMap(final Map<? extends K, ? extends V> map) {
        this.comparator = null;
        this.initialize();
        this.putAll(map);
    }
    
    public ConcurrentSkipListMap(final SortedMap<K, ? extends V> sortedMap) {
        this.comparator = sortedMap.comparator();
        this.initialize();
        this.buildFromSorted(sortedMap);
    }
    
    public ConcurrentSkipListMap<K, V> clone() {
        try {
            final ConcurrentSkipListMap concurrentSkipListMap = (ConcurrentSkipListMap)super.clone();
            concurrentSkipListMap.initialize();
            concurrentSkipListMap.buildFromSorted(this);
            return concurrentSkipListMap;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }
    
    private void buildFromSorted(final SortedMap<K, ? extends V> sortedMap) {
        if (sortedMap == null) {
            throw new NullPointerException();
        }
        HeadIndex<K, V> head = this.head;
        Node<K, V> node = head.node;
        final ArrayList<HeadIndex<K, V>> list = new ArrayList<HeadIndex<K, V>>();
        for (int i = 0; i <= head.level; ++i) {
            list.add(null);
        }
        Object down = head;
        for (int j = head.level; j > 0; --j) {
            list.set(j, (HeadIndex<K, V>)down);
            down = ((Index)down).down;
        }
        for (final Map.Entry<K, ? extends V> entry : sortedMap.entrySet()) {
            int nextInt = ThreadLocalRandom.current().nextInt();
            int n = 0;
            if ((nextInt & 0x80000001) == 0x0) {
                do {
                    ++n;
                } while (((nextInt >>>= 1) & 0x1) != 0x0);
                if (n > head.level) {
                    n = head.level + 1;
                }
            }
            final K key = entry.getKey();
            final V value = (V)entry.getValue();
            if (key == null || value == null) {
                throw new NullPointerException();
            }
            final Node next = new Node<K, V>(key, value, null);
            node.next = (Node<K, V>)next;
            node = (Node<K, V>)next;
            if (n <= 0) {
                continue;
            }
            Index<K, V> right = null;
            for (int k = 1; k <= n; ++k) {
                right = new Index<K, V>((Node<Object, Object>)next, (Index<Object, Object>)right, null);
                if (k > head.level) {
                    head = (HeadIndex<K, V>)new HeadIndex<Object, Object>((Node<Object, Object>)head.node, (Index<Object, Object>)head, (Index<Object, Object>)right, k);
                }
                if (k < list.size()) {
                    list.set(k, (HeadIndex<K, V>)(list.get(k).right = (Index<K, V>)right));
                }
                else {
                    list.add((HeadIndex<K, V>)right);
                }
            }
        }
        this.head = head;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (Object o = this.findFirst(); o != null; o = ((Node)o).next) {
            final Object validValue = ((Node<K, Object>)o).getValidValue();
            if (validValue != null) {
                objectOutputStream.writeObject(((Node)o).key);
                objectOutputStream.writeObject(validValue);
            }
        }
        objectOutputStream.writeObject(null);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.initialize();
        HeadIndex<K, V> head = this.head;
        Node<K, V> node = head.node;
        final ArrayList<HeadIndex<K, V>> list = new ArrayList<HeadIndex<K, V>>();
        for (int i = 0; i <= head.level; ++i) {
            list.add(null);
        }
        Object down = head;
        for (int j = head.level; j > 0; --j) {
            list.set(j, (HeadIndex<K, V>)down);
            down = ((Index)down).down;
        }
        while (true) {
            final Object object = objectInputStream.readObject();
            if (object == null) {
                this.head = head;
                return;
            }
            final Object object2 = objectInputStream.readObject();
            if (object2 == null) {
                throw new NullPointerException();
            }
            final Object o = object;
            final Object o2 = object2;
            int nextInt = ThreadLocalRandom.current().nextInt();
            int n = 0;
            if ((nextInt & 0x80000001) == 0x0) {
                do {
                    ++n;
                } while (((nextInt >>>= 1) & 0x1) != 0x0);
                if (n > head.level) {
                    n = head.level + 1;
                }
            }
            final Node next = new Node<K, V>(o, o2, null);
            node.next = (Node<K, V>)next;
            node = (Node<K, V>)next;
            if (n <= 0) {
                continue;
            }
            Object right = null;
            for (int k = 1; k <= n; ++k) {
                right = new Index<K, V>((Node<Object, Object>)next, (Index<Object, Object>)right, null);
                if (k > head.level) {
                    head = (HeadIndex<K, V>)new HeadIndex<Object, Object>((Node<Object, Object>)head.node, (Index<Object, Object>)head, (Index<Object, Object>)right, k);
                }
                if (k < list.size()) {
                    list.set(k, (HeadIndex<K, V>)(list.get(k).right = (Index<K, V>)right));
                }
                else {
                    list.add((HeadIndex<K, V>)right);
                }
            }
        }
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.doGet(o) != null;
    }
    
    @Override
    public V get(final Object o) {
        return this.doGet(o);
    }
    
    @Override
    public V getOrDefault(final Object o, final V v) {
        final V doGet;
        return ((doGet = this.doGet(o)) == null) ? v : doGet;
    }
    
    @Override
    public V put(final K k, final V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return this.doPut(k, v, false);
    }
    
    @Override
    public V remove(final Object o) {
        return this.doRemove(o, null);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        for (Object o2 = this.findFirst(); o2 != null; o2 = ((Node)o2).next) {
            final Object validValue = ((Node<K, Object>)o2).getValidValue();
            if (validValue != null && o.equals(validValue)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int size() {
        long n = 0L;
        for (Node<K, V> node = this.findFirst(); node != null; node = node.next) {
            if (node.getValidValue() != null) {
                ++n;
            }
        }
        return (n >= 2147483647L) ? Integer.MAX_VALUE : ((int)n);
    }
    
    @Override
    public boolean isEmpty() {
        return this.findFirst() == null;
    }
    
    @Override
    public void clear() {
        while (true) {
            final HeadIndex<K, V> head = this.head;
            final HeadIndex headIndex = (HeadIndex)head.down;
            if (headIndex != null) {
                this.casHead(head, headIndex);
            }
            else {
                final Node<K, V> node;
                final Node<K, V> next;
                if ((node = head.node) == null || (next = node.next) == null) {
                    break;
                }
                final Node<K, V> next2 = next.next;
                if (next != node.next) {
                    continue;
                }
                final Object value = next.value;
                if (value == null) {
                    next.helpDelete(node, next2);
                }
                else {
                    if (!next.casValue(value, null) || !next.appendMarker(next2)) {
                        continue;
                    }
                    node.casNext(next, next2);
                }
            }
        }
    }
    
    @Override
    public V computeIfAbsent(final K k, final Function<? super K, ? extends V> function) {
        if (k == null || function == null) {
            throw new NullPointerException();
        }
        V doGet;
        final V apply;
        if ((doGet = this.doGet(k)) == null && (apply = (V)function.apply(k)) != null) {
            final Object doPut;
            doGet = (V)(((doPut = this.doPut(k, apply, true)) == null) ? apply : doPut);
        }
        return doGet;
    }
    
    @Override
    public V computeIfPresent(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (k == null || biFunction == null) {
            throw new NullPointerException();
        }
        Node<K, V> node;
        while ((node = this.findNode(k)) != null) {
            final Object value;
            if ((value = node.value) != null) {
                final Object o = value;
                final V apply = (V)biFunction.apply(k, (Object)o);
                if (apply != null) {
                    if (node.casValue(o, apply)) {
                        return apply;
                    }
                    continue;
                }
                else {
                    if (this.doRemove(k, o) != null) {
                        break;
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    @Override
    public V compute(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (k == null || biFunction == null) {
            throw new NullPointerException();
        }
        while (true) {
            final Node<K, V> node;
            if ((node = this.findNode(k)) == null) {
                final V apply;
                if ((apply = (V)biFunction.apply(k, (Object)null)) == null) {
                    break;
                }
                if (this.doPut(k, apply, true) == null) {
                    return apply;
                }
                continue;
            }
            else {
                final Object value;
                if ((value = node.value) == null) {
                    continue;
                }
                final Object o = value;
                final V apply2;
                if ((apply2 = (V)biFunction.apply(k, (Object)o)) != null) {
                    if (node.casValue(o, apply2)) {
                        return apply2;
                    }
                    continue;
                }
                else {
                    if (this.doRemove(k, o) != null) {
                        break;
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    @Override
    public V merge(final K k, final V v, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
        if (k == null || v == null || biFunction == null) {
            throw new NullPointerException();
        }
        while (true) {
            final Node<K, V> node;
            if ((node = this.findNode(k)) == null) {
                if (this.doPut(k, v, true) == null) {
                    return v;
                }
                continue;
            }
            else {
                final Object value;
                if ((value = node.value) == null) {
                    continue;
                }
                final Object o = value;
                final V apply;
                if ((apply = (V)biFunction.apply(o, v)) != null) {
                    if (node.casValue(o, apply)) {
                        return apply;
                    }
                    continue;
                }
                else {
                    if (this.doRemove(k, o) != null) {
                        return null;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public NavigableSet<K> keySet() {
        final KeySet<K> keySet = this.keySet;
        return (keySet != null) ? keySet : (this.keySet = new KeySet<K>(this));
    }
    
    @Override
    public NavigableSet<K> navigableKeySet() {
        final KeySet<K> keySet = this.keySet;
        return (keySet != null) ? keySet : (this.keySet = new KeySet<K>(this));
    }
    
    @Override
    public Collection<V> values() {
        final Values<V> values = this.values;
        return (values != null) ? values : (this.values = new Values<V>(this));
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        final EntrySet<K, V> entrySet = this.entrySet;
        return (Set<Map.Entry<K, V>>)((entrySet != null) ? entrySet : (this.entrySet = new EntrySet<K, V>(this)));
    }
    
    @Override
    public ConcurrentNavigableMap<K, V> descendingMap() {
        final ConcurrentNavigableMap<K, V> descendingMap = this.descendingMap;
        return (descendingMap != null) ? descendingMap : (this.descendingMap = new SubMap<K, V>(this, null, false, null, false, true));
    }
    
    @Override
    public NavigableSet<K> descendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        final Map map = (Map)o;
        try {
            for (final Map.Entry<K, V> entry : this.entrySet()) {
                if (!entry.getValue().equals(map.get(entry.getKey()))) {
                    return false;
                }
            }
            for (final Map.Entry<K, Object> entry2 : map.entrySet()) {
                final K key = entry2.getKey();
                final Object value = entry2.getValue();
                if (key == null || value == null || !value.equals(this.get(key))) {
                    return false;
                }
            }
            return true;
        }
        catch (ClassCastException ex) {
            return false;
        }
        catch (NullPointerException ex2) {
            return false;
        }
    }
    
    @Override
    public V putIfAbsent(final K k, final V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return this.doPut(k, v, true);
    }
    
    @Override
    public boolean remove(final Object o, final Object o2) {
        if (o == null) {
            throw new NullPointerException();
        }
        return o2 != null && this.doRemove(o, o2) != null;
    }
    
    @Override
    public boolean replace(final K k, final V v, final V v2) {
        if (k == null || v == null || v2 == null) {
            throw new NullPointerException();
        }
        Node<K, V> node;
        while ((node = this.findNode(k)) != null) {
            final Object value;
            if ((value = node.value) != null) {
                if (!v.equals(value)) {
                    return false;
                }
                if (node.casValue(value, v2)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    @Override
    public V replace(final K k, final V v) {
        if (k == null || v == null) {
            throw new NullPointerException();
        }
        Node<K, V> node;
        while ((node = this.findNode(k)) != null) {
            final Object value;
            if ((value = node.value) != null && node.casValue(value, v)) {
                return (V)value;
            }
        }
        return null;
    }
    
    @Override
    public Comparator<? super K> comparator() {
        return this.comparator;
    }
    
    @Override
    public K firstKey() {
        final Node<K, V> first = this.findFirst();
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.key;
    }
    
    @Override
    public K lastKey() {
        final Node<K, V> last = this.findLast();
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.key;
    }
    
    @Override
    public ConcurrentNavigableMap<K, V> subMap(final K k, final boolean b, final K i, final boolean b2) {
        if (k == null || i == null) {
            throw new NullPointerException();
        }
        return new SubMap<K, V>(this, k, b, i, b2, false);
    }
    
    @Override
    public ConcurrentNavigableMap<K, V> headMap(final K k, final boolean b) {
        if (k == null) {
            throw new NullPointerException();
        }
        return new SubMap<K, V>(this, null, false, k, b, false);
    }
    
    @Override
    public ConcurrentNavigableMap<K, V> tailMap(final K k, final boolean b) {
        if (k == null) {
            throw new NullPointerException();
        }
        return new SubMap<K, V>(this, k, b, null, false, false);
    }
    
    @Override
    public ConcurrentNavigableMap<K, V> subMap(final K k, final K i) {
        return this.subMap(k, true, i, false);
    }
    
    @Override
    public ConcurrentNavigableMap<K, V> headMap(final K k) {
        return this.headMap(k, false);
    }
    
    @Override
    public ConcurrentNavigableMap<K, V> tailMap(final K k) {
        return this.tailMap(k, true);
    }
    
    @Override
    public Map.Entry<K, V> lowerEntry(final K k) {
        return this.getNear(k, 2);
    }
    
    @Override
    public K lowerKey(final K k) {
        final Node<K, V> near = this.findNear(k, 2, this.comparator);
        return (near == null) ? null : near.key;
    }
    
    @Override
    public Map.Entry<K, V> floorEntry(final K k) {
        return this.getNear(k, 3);
    }
    
    @Override
    public K floorKey(final K k) {
        final Node<K, V> near = this.findNear(k, 3, this.comparator);
        return (near == null) ? null : near.key;
    }
    
    @Override
    public Map.Entry<K, V> ceilingEntry(final K k) {
        return this.getNear(k, 1);
    }
    
    @Override
    public K ceilingKey(final K k) {
        final Node<K, V> near = this.findNear(k, 1, this.comparator);
        return (near == null) ? null : near.key;
    }
    
    @Override
    public Map.Entry<K, V> higherEntry(final K k) {
        return this.getNear(k, 0);
    }
    
    @Override
    public K higherKey(final K k) {
        final Node<K, V> near = this.findNear(k, 0, this.comparator);
        return (near == null) ? null : near.key;
    }
    
    @Override
    public Map.Entry<K, V> firstEntry() {
        while (true) {
            final Node<K, V> first = this.findFirst();
            if (first == null) {
                return null;
            }
            final SimpleImmutableEntry<K, V> snapshot = first.createSnapshot();
            if (snapshot != null) {
                return snapshot;
            }
        }
    }
    
    @Override
    public Map.Entry<K, V> lastEntry() {
        while (true) {
            final Node<K, V> last = this.findLast();
            if (last == null) {
                return null;
            }
            final SimpleImmutableEntry<K, V> snapshot = last.createSnapshot();
            if (snapshot != null) {
                return snapshot;
            }
        }
    }
    
    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        return this.doRemoveFirstEntry();
    }
    
    @Override
    public Map.Entry<K, V> pollLastEntry() {
        return this.doRemoveLastEntry();
    }
    
    Iterator<K> keyIterator() {
        return new KeyIterator();
    }
    
    Iterator<V> valueIterator() {
        return new ValueIterator();
    }
    
    Iterator<Map.Entry<K, V>> entryIterator() {
        return new EntryIterator();
    }
    
    static final <E> List<E> toList(final Collection<E> collection) {
        final ArrayList<E> list = new ArrayList<E>();
        final Iterator<E> iterator = collection.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
    
    @Override
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer == null) {
            throw new NullPointerException();
        }
        for (Object o = this.findFirst(); o != null; o = ((Node)o).next) {
            final Object validValue;
            if ((validValue = ((Node<K, Object>)o).getValidValue()) != null) {
                biConsumer.accept((Object)((Node)o).key, (Object)validValue);
            }
        }
    }
    
    @Override
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction == null) {
            throw new NullPointerException();
        }
        for (Node<K, V> node = this.findFirst(); node != null; node = node.next) {
            V validValue;
            while ((validValue = node.getValidValue()) != null) {
                final V apply = (V)biFunction.apply(node.key, validValue);
                if (apply == null) {
                    throw new NullPointerException();
                }
                if (node.casValue(validValue, apply)) {
                    break;
                }
            }
        }
    }
    
    final KeySpliterator<K, V> keySpliterator() {
        final Comparator<? super K> comparator = this.comparator;
        HeadIndex<K, V> head;
        Node<K, V> next;
        while (true) {
            final Node<K, V> node = (head = this.head).node;
            if ((next = node.next) == null || next.value != null) {
                break;
            }
            next.helpDelete(node, next.next);
        }
        return new KeySpliterator<K, V>((Comparator<? super Object>)comparator, (Index<Object, Object>)head, (Node<Object, Object>)next, null, (next == null) ? 0 : Integer.MAX_VALUE);
    }
    
    final ValueSpliterator<K, V> valueSpliterator() {
        final Comparator<? super K> comparator = this.comparator;
        HeadIndex<K, V> head;
        Node<K, V> next;
        while (true) {
            final Node<K, V> node = (head = this.head).node;
            if ((next = node.next) == null || next.value != null) {
                break;
            }
            next.helpDelete(node, next.next);
        }
        return new ValueSpliterator<K, V>((Comparator<? super Object>)comparator, (Index<Object, Object>)head, (Node<Object, Object>)next, null, (next == null) ? 0 : Integer.MAX_VALUE);
    }
    
    final EntrySpliterator<K, V> entrySpliterator() {
        final Comparator<? super K> comparator = this.comparator;
        HeadIndex<K, V> head;
        Node<K, V> next;
        while (true) {
            final Node<K, V> node = (head = this.head).node;
            if ((next = node.next) == null || next.value != null) {
                break;
            }
            next.helpDelete(node, next.next);
        }
        return new EntrySpliterator<K, V>((Comparator<? super Object>)comparator, (Index<Object, Object>)head, (Node<Object, Object>)next, null, (next == null) ? 0 : Integer.MAX_VALUE);
    }
    
    static {
        BASE_HEADER = new Object();
        try {
            UNSAFE = Unsafe.getUnsafe();
            headOffset = ConcurrentSkipListMap.UNSAFE.objectFieldOffset(ConcurrentSkipListMap.class.getDeclaredField("head"));
            SECONDARY = ConcurrentSkipListMap.UNSAFE.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomSecondarySeed"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    abstract static class CSLMSpliterator<K, V>
    {
        final Comparator<? super K> comparator;
        final K fence;
        Index<K, V> row;
        Node<K, V> current;
        int est;
        
        CSLMSpliterator(final Comparator<? super K> comparator, final Index<K, V> row, final Node<K, V> current, final K fence, final int est) {
            this.comparator = comparator;
            this.row = row;
            this.current = current;
            this.fence = fence;
            this.est = est;
        }
        
        public final long estimateSize() {
            return this.est;
        }
    }
    
    static class Index<K, V>
    {
        final Node<K, V> node;
        final Index<K, V> down;
        volatile Index<K, V> right;
        private static final Unsafe UNSAFE;
        private static final long rightOffset;
        
        Index(final Node<K, V> node, final Index<K, V> down, final Index<K, V> right) {
            this.node = node;
            this.down = down;
            this.right = right;
        }
        
        final boolean casRight(final Index<K, V> index, final Index<K, V> index2) {
            return Index.UNSAFE.compareAndSwapObject(this, Index.rightOffset, index, index2);
        }
        
        final boolean indexesDeletedNode() {
            return this.node.value == null;
        }
        
        final boolean link(final Index<K, V> right, final Index<K, V> index) {
            final Node<K, V> node = this.node;
            index.right = right;
            return node.value != null && this.casRight(right, index);
        }
        
        final boolean unlink(final Index<K, V> index) {
            return this.node.value != null && this.casRight(index, index.right);
        }
        
        static {
            try {
                UNSAFE = Unsafe.getUnsafe();
                rightOffset = Index.UNSAFE.objectFieldOffset(Index.class.getDeclaredField("right"));
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }
    
    static final class Node<K, V>
    {
        final K key;
        volatile Object value;
        volatile Node<K, V> next;
        private static final Unsafe UNSAFE;
        private static final long valueOffset;
        private static final long nextOffset;
        
        Node(final K key, final Object value, final Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        Node(final Node<K, V> next) {
            this.key = null;
            this.value = this;
            this.next = next;
        }
        
        boolean casValue(final Object o, final Object o2) {
            return Node.UNSAFE.compareAndSwapObject(this, Node.valueOffset, o, o2);
        }
        
        boolean casNext(final Node<K, V> node, final Node<K, V> node2) {
            return Node.UNSAFE.compareAndSwapObject(this, Node.nextOffset, node, node2);
        }
        
        boolean isMarker() {
            return this.value == this;
        }
        
        boolean isBaseHeader() {
            return this.value == ConcurrentSkipListMap.BASE_HEADER;
        }
        
        boolean appendMarker(final Node<K, V> node) {
            return this.casNext(node, new Node<K, V>(node));
        }
        
        void helpDelete(final Node<K, V> node, final Node<K, V> node2) {
            if (node2 == this.next && this == node.next) {
                if (node2 == null || node2.value != node2) {
                    this.casNext(node2, new Node<K, V>(node2));
                }
                else {
                    node.casNext(this, node2.next);
                }
            }
        }
        
        V getValidValue() {
            final Object value = this.value;
            if (value == this || value == ConcurrentSkipListMap.BASE_HEADER) {
                return null;
            }
            return (V)value;
        }
        
        SimpleImmutableEntry<K, V> createSnapshot() {
            final Object value = this.value;
            if (value == null || value == this || value == ConcurrentSkipListMap.BASE_HEADER) {
                return null;
            }
            return new SimpleImmutableEntry<K, V>(this.key, value);
        }
        
        static {
            try {
                UNSAFE = Unsafe.getUnsafe();
                final Class<Node> clazz = Node.class;
                valueOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("value"));
                nextOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }
    
    final class EntryIterator extends Iter<Map.Entry<K, V>>
    {
        @Override
        public Map.Entry<K, V> next() {
            final Node<K, V> next = this.next;
            final V nextValue = this.nextValue;
            this.advance();
            return new SimpleImmutableEntry<K, V>(next.key, nextValue);
        }
    }
    
    abstract class Iter<T> implements Iterator<T>
    {
        Node<K, V> lastReturned;
        Node<K, V> next;
        V nextValue;
        
        Iter() {
            while (true) {
                final Node<K, V> first = ConcurrentSkipListMap.this.findFirst();
                this.next = first;
                if (first == null) {
                    break;
                }
                final Object value = this.next.value;
                if (value != null && value != this.next) {
                    this.nextValue = (V)value;
                    break;
                }
            }
        }
        
        @Override
        public final boolean hasNext() {
            return this.next != null;
        }
        
        final void advance() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.next;
            while (true) {
                final Node<K, V> next = this.next.next;
                this.next = next;
                if (next == null) {
                    break;
                }
                final Object value = this.next.value;
                if (value != null && value != this.next) {
                    this.nextValue = (V)value;
                    break;
                }
            }
        }
        
        @Override
        public void remove() {
            final Node<K, V> lastReturned = this.lastReturned;
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            ConcurrentSkipListMap.this.remove(lastReturned.key);
            this.lastReturned = null;
        }
    }
    
    static final class EntrySet<K1, V1> extends AbstractSet<Map.Entry<K1, V1>>
    {
        final ConcurrentNavigableMap<K1, V1> m;
        
        EntrySet(final ConcurrentNavigableMap<K1, V1> m) {
            this.m = m;
        }
        
        @Override
        public Iterator<Map.Entry<K1, V1>> iterator() {
            if (this.m instanceof ConcurrentSkipListMap) {
                return ((ConcurrentSkipListMap)this.m).entryIterator();
            }
            return ((SubMap)this.m).entryIterator();
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object value = this.m.get(entry.getKey());
            return value != null && value.equals(entry.getValue());
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            return this.m.remove(entry.getKey(), entry.getValue());
        }
        
        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }
        
        @Override
        public int size() {
            return this.m.size();
        }
        
        @Override
        public void clear() {
            this.m.clear();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Set)) {
                return false;
            }
            final Collection collection = (Collection)o;
            try {
                return this.containsAll(collection) && collection.containsAll(this);
            }
            catch (ClassCastException ex) {
                return false;
            }
            catch (NullPointerException ex2) {
                return false;
            }
        }
        
        @Override
        public Object[] toArray() {
            return ConcurrentSkipListMap.toList((Collection<Object>)this).toArray();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            return ConcurrentSkipListMap.toList((Collection<Object>)this).toArray(array);
        }
        
        @Override
        public Spliterator<Map.Entry<K1, V1>> spliterator() {
            if (this.m instanceof ConcurrentSkipListMap) {
                return (Spliterator<Map.Entry<K1, V1>>)((ConcurrentSkipListMap)this.m).entrySpliterator();
            }
            return (Spliterator<Map.Entry<K1, V1>>)((SubMap)this.m).entryIterator();
        }
    }
    
    static final class SubMap<K, V> extends AbstractMap<K, V> implements ConcurrentNavigableMap<K, V>, Cloneable, Serializable
    {
        private static final long serialVersionUID = -7647078645895051609L;
        private final ConcurrentSkipListMap<K, V> m;
        private final K lo;
        private final K hi;
        private final boolean loInclusive;
        private final boolean hiInclusive;
        private final boolean isDescending;
        private transient KeySet<K> keySetView;
        private transient Set<Map.Entry<K, V>> entrySetView;
        private transient Collection<V> valuesView;
        
        SubMap(final ConcurrentSkipListMap<K, V> m, final K lo, final boolean loInclusive, final K hi, final boolean hiInclusive, final boolean isDescending) {
            final Comparator<? super K> comparator = m.comparator;
            if (lo != null && hi != null && ConcurrentSkipListMap.cpr(comparator, lo, hi) > 0) {
                throw new IllegalArgumentException("inconsistent range");
            }
            this.m = m;
            this.lo = lo;
            this.hi = hi;
            this.loInclusive = loInclusive;
            this.hiInclusive = hiInclusive;
            this.isDescending = isDescending;
        }
        
        boolean tooLow(final Object o, final Comparator<? super K> comparator) {
            final int cpr;
            return this.lo != null && ((cpr = ConcurrentSkipListMap.cpr(comparator, o, this.lo)) < 0 || (cpr == 0 && !this.loInclusive));
        }
        
        boolean tooHigh(final Object o, final Comparator<? super K> comparator) {
            final int cpr;
            return this.hi != null && ((cpr = ConcurrentSkipListMap.cpr(comparator, o, this.hi)) > 0 || (cpr == 0 && !this.hiInclusive));
        }
        
        boolean inBounds(final Object o, final Comparator<? super K> comparator) {
            return !this.tooLow(o, comparator) && !this.tooHigh(o, comparator);
        }
        
        void checkKeyBounds(final K k, final Comparator<? super K> comparator) {
            if (k == null) {
                throw new NullPointerException();
            }
            if (!this.inBounds(k, comparator)) {
                throw new IllegalArgumentException("key out of range");
            }
        }
        
        boolean isBeforeEnd(final Node<K, V> node, final Comparator<? super K> comparator) {
            if (node == null) {
                return false;
            }
            if (this.hi == null) {
                return true;
            }
            final K key = node.key;
            if (key == null) {
                return true;
            }
            final int cpr = ConcurrentSkipListMap.cpr(comparator, key, this.hi);
            return cpr <= 0 && (cpr != 0 || this.hiInclusive);
        }
        
        Node<K, V> loNode(final Comparator<? super K> comparator) {
            if (this.lo == null) {
                return this.m.findFirst();
            }
            if (this.loInclusive) {
                return this.m.findNear(this.lo, 1, comparator);
            }
            return this.m.findNear(this.lo, 0, comparator);
        }
        
        Node<K, V> hiNode(final Comparator<? super K> comparator) {
            if (this.hi == null) {
                return this.m.findLast();
            }
            if (this.hiInclusive) {
                return this.m.findNear(this.hi, 3, comparator);
            }
            return this.m.findNear(this.hi, 2, comparator);
        }
        
        K lowestKey() {
            final Comparator<? super K> comparator = this.m.comparator;
            final Node<K, V> loNode = this.loNode(comparator);
            if (this.isBeforeEnd(loNode, comparator)) {
                return loNode.key;
            }
            throw new NoSuchElementException();
        }
        
        K highestKey() {
            final Comparator<? super K> comparator = this.m.comparator;
            final Node<K, V> hiNode = this.hiNode(comparator);
            if (hiNode != null) {
                final K key = hiNode.key;
                if (this.inBounds(key, comparator)) {
                    return key;
                }
            }
            throw new NoSuchElementException();
        }
        
        Map.Entry<K, V> lowestEntry() {
            final Comparator<? super K> comparator = this.m.comparator;
            while (true) {
                final Node<K, V> loNode = this.loNode(comparator);
                if (!this.isBeforeEnd(loNode, comparator)) {
                    return null;
                }
                final SimpleImmutableEntry<K, V> snapshot = loNode.createSnapshot();
                if (snapshot != null) {
                    return snapshot;
                }
            }
        }
        
        Map.Entry<K, V> highestEntry() {
            final Comparator<? super K> comparator = this.m.comparator;
            while (true) {
                final Node<K, V> hiNode = this.hiNode(comparator);
                if (hiNode == null || !this.inBounds(hiNode.key, comparator)) {
                    return null;
                }
                final SimpleImmutableEntry<K, V> snapshot = hiNode.createSnapshot();
                if (snapshot != null) {
                    return snapshot;
                }
            }
        }
        
        Map.Entry<K, V> removeLowest() {
            final Comparator<? super K> comparator = this.m.comparator;
            while (true) {
                final Node<K, V> loNode = this.loNode(comparator);
                if (loNode == null) {
                    return null;
                }
                final K key = loNode.key;
                if (!this.inBounds(key, comparator)) {
                    return null;
                }
                final V doRemove = this.m.doRemove(key, null);
                if (doRemove != null) {
                    return new SimpleImmutableEntry<K, V>(key, doRemove);
                }
            }
        }
        
        Map.Entry<K, V> removeHighest() {
            final Comparator<? super K> comparator = this.m.comparator;
            while (true) {
                final Node<K, V> hiNode = this.hiNode(comparator);
                if (hiNode == null) {
                    return null;
                }
                final K key = hiNode.key;
                if (!this.inBounds(key, comparator)) {
                    return null;
                }
                final V doRemove = this.m.doRemove(key, null);
                if (doRemove != null) {
                    return new SimpleImmutableEntry<K, V>(key, doRemove);
                }
            }
        }
        
        Map.Entry<K, V> getNearEntry(final K k, int n) {
            final Comparator<? super K> comparator = this.m.comparator;
            if (this.isDescending) {
                if ((n & 0x2) == 0x0) {
                    n |= 0x2;
                }
                else {
                    n &= 0xFFFFFFFD;
                }
            }
            if (this.tooLow(k, comparator)) {
                return ((n & 0x2) != 0x0) ? null : this.lowestEntry();
            }
            if (this.tooHigh(k, comparator)) {
                return ((n & 0x2) != 0x0) ? this.highestEntry() : null;
            }
            while (true) {
                final Node<K, V> near = this.m.findNear(k, n, comparator);
                if (near == null || !this.inBounds(near.key, comparator)) {
                    return null;
                }
                final K key = near.key;
                final V validValue = near.getValidValue();
                if (validValue != null) {
                    return new SimpleImmutableEntry<K, V>(key, validValue);
                }
            }
        }
        
        K getNearKey(final K k, int n) {
            final Comparator<? super K> comparator = this.m.comparator;
            if (this.isDescending) {
                if ((n & 0x2) == 0x0) {
                    n |= 0x2;
                }
                else {
                    n &= 0xFFFFFFFD;
                }
            }
            if (this.tooLow(k, comparator)) {
                if ((n & 0x2) == 0x0) {
                    final Node<K, V> loNode = this.loNode(comparator);
                    if (this.isBeforeEnd(loNode, comparator)) {
                        return loNode.key;
                    }
                }
                return null;
            }
            if (this.tooHigh(k, comparator)) {
                if ((n & 0x2) != 0x0) {
                    final Node<K, V> hiNode = this.hiNode(comparator);
                    if (hiNode != null) {
                        final K key = hiNode.key;
                        if (this.inBounds(key, comparator)) {
                            return key;
                        }
                    }
                }
                return null;
            }
            while (true) {
                final Node<K, V> near = this.m.findNear(k, n, comparator);
                if (near == null || !this.inBounds(near.key, comparator)) {
                    return null;
                }
                final K key2 = near.key;
                if (near.getValidValue() != null) {
                    return key2;
                }
            }
        }
        
        @Override
        public boolean containsKey(final Object o) {
            if (o == null) {
                throw new NullPointerException();
            }
            return this.inBounds(o, this.m.comparator) && this.m.containsKey(o);
        }
        
        @Override
        public V get(final Object o) {
            if (o == null) {
                throw new NullPointerException();
            }
            return this.inBounds(o, this.m.comparator) ? this.m.get(o) : null;
        }
        
        @Override
        public V put(final K k, final V v) {
            this.checkKeyBounds(k, this.m.comparator);
            return this.m.put(k, v);
        }
        
        @Override
        public V remove(final Object o) {
            return this.inBounds(o, this.m.comparator) ? this.m.remove(o) : null;
        }
        
        @Override
        public int size() {
            final Comparator<? super K> comparator = this.m.comparator;
            long n = 0L;
            for (Node<K, V> node = this.loNode(comparator); this.isBeforeEnd(node, comparator); node = node.next) {
                if (node.getValidValue() != null) {
                    ++n;
                }
            }
            return (n >= 2147483647L) ? Integer.MAX_VALUE : ((int)n);
        }
        
        @Override
        public boolean isEmpty() {
            final Comparator<? super K> comparator = this.m.comparator;
            return !this.isBeforeEnd(this.loNode(comparator), comparator);
        }
        
        @Override
        public boolean containsValue(final Object o) {
            if (o == null) {
                throw new NullPointerException();
            }
            final Comparator<? super K> comparator = this.m.comparator;
            for (Node<K, V> node = this.loNode(comparator); this.isBeforeEnd(node, comparator); node = node.next) {
                final V validValue = node.getValidValue();
                if (validValue != null && o.equals(validValue)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public void clear() {
            final Comparator<? super K> comparator = this.m.comparator;
            for (Node<K, V> node = this.loNode(comparator); this.isBeforeEnd(node, comparator); node = node.next) {
                if (node.getValidValue() != null) {
                    this.m.remove(node.key);
                }
            }
        }
        
        @Override
        public V putIfAbsent(final K k, final V v) {
            this.checkKeyBounds(k, this.m.comparator);
            return this.m.putIfAbsent(k, v);
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            return this.inBounds(o, this.m.comparator) && this.m.remove(o, o2);
        }
        
        @Override
        public boolean replace(final K k, final V v, final V v2) {
            this.checkKeyBounds(k, this.m.comparator);
            return this.m.replace(k, v, v2);
        }
        
        @Override
        public V replace(final K k, final V v) {
            this.checkKeyBounds(k, this.m.comparator);
            return this.m.replace(k, v);
        }
        
        @Override
        public Comparator<? super K> comparator() {
            final Comparator<? super K> comparator = this.m.comparator();
            if (this.isDescending) {
                return Collections.reverseOrder(comparator);
            }
            return comparator;
        }
        
        SubMap<K, V> newSubMap(K lo, boolean loInclusive, K hi, boolean hiInclusive) {
            final Comparator<? super K> comparator = this.m.comparator;
            if (this.isDescending) {
                final K k = lo;
                lo = hi;
                hi = k;
                final boolean b = loInclusive;
                loInclusive = hiInclusive;
                hiInclusive = b;
            }
            if (this.lo != null) {
                if (lo == null) {
                    lo = this.lo;
                    loInclusive = this.loInclusive;
                }
                else {
                    final int cpr = ConcurrentSkipListMap.cpr(comparator, lo, this.lo);
                    if (cpr < 0 || (cpr == 0 && !this.loInclusive && loInclusive)) {
                        throw new IllegalArgumentException("key out of range");
                    }
                }
            }
            if (this.hi != null) {
                if (hi == null) {
                    hi = this.hi;
                    hiInclusive = this.hiInclusive;
                }
                else {
                    final int cpr2 = ConcurrentSkipListMap.cpr(comparator, hi, this.hi);
                    if (cpr2 > 0 || (cpr2 == 0 && !this.hiInclusive && hiInclusive)) {
                        throw new IllegalArgumentException("key out of range");
                    }
                }
            }
            return new SubMap<K, V>(this.m, lo, loInclusive, hi, hiInclusive, this.isDescending);
        }
        
        @Override
        public SubMap<K, V> subMap(final K k, final boolean b, final K i, final boolean b2) {
            if (k == null || i == null) {
                throw new NullPointerException();
            }
            return this.newSubMap(k, b, i, b2);
        }
        
        @Override
        public SubMap<K, V> headMap(final K k, final boolean b) {
            if (k == null) {
                throw new NullPointerException();
            }
            return this.newSubMap(null, false, k, b);
        }
        
        @Override
        public SubMap<K, V> tailMap(final K k, final boolean b) {
            if (k == null) {
                throw new NullPointerException();
            }
            return this.newSubMap(k, b, null, false);
        }
        
        @Override
        public SubMap<K, V> subMap(final K k, final K i) {
            return this.subMap(k, true, i, false);
        }
        
        @Override
        public SubMap<K, V> headMap(final K k) {
            return this.headMap(k, false);
        }
        
        @Override
        public SubMap<K, V> tailMap(final K k) {
            return this.tailMap(k, true);
        }
        
        @Override
        public SubMap<K, V> descendingMap() {
            return new SubMap<K, V>(this.m, this.lo, this.loInclusive, this.hi, this.hiInclusive, !this.isDescending);
        }
        
        @Override
        public Map.Entry<K, V> ceilingEntry(final K k) {
            return this.getNearEntry(k, 1);
        }
        
        @Override
        public K ceilingKey(final K k) {
            return this.getNearKey(k, 1);
        }
        
        @Override
        public Map.Entry<K, V> lowerEntry(final K k) {
            return this.getNearEntry(k, 2);
        }
        
        @Override
        public K lowerKey(final K k) {
            return this.getNearKey(k, 2);
        }
        
        @Override
        public Map.Entry<K, V> floorEntry(final K k) {
            return this.getNearEntry(k, 3);
        }
        
        @Override
        public K floorKey(final K k) {
            return this.getNearKey(k, 3);
        }
        
        @Override
        public Map.Entry<K, V> higherEntry(final K k) {
            return this.getNearEntry(k, 0);
        }
        
        @Override
        public K higherKey(final K k) {
            return this.getNearKey(k, 0);
        }
        
        @Override
        public K firstKey() {
            return this.isDescending ? this.highestKey() : this.lowestKey();
        }
        
        @Override
        public K lastKey() {
            return this.isDescending ? this.lowestKey() : this.highestKey();
        }
        
        @Override
        public Map.Entry<K, V> firstEntry() {
            return this.isDescending ? this.highestEntry() : this.lowestEntry();
        }
        
        @Override
        public Map.Entry<K, V> lastEntry() {
            return this.isDescending ? this.lowestEntry() : this.highestEntry();
        }
        
        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            return this.isDescending ? this.removeHighest() : this.removeLowest();
        }
        
        @Override
        public Map.Entry<K, V> pollLastEntry() {
            return this.isDescending ? this.removeLowest() : this.removeHighest();
        }
        
        @Override
        public NavigableSet<K> keySet() {
            final KeySet<K> keySetView = this.keySetView;
            return (keySetView != null) ? keySetView : (this.keySetView = new KeySet<K>(this));
        }
        
        @Override
        public NavigableSet<K> navigableKeySet() {
            final KeySet<K> keySetView = this.keySetView;
            return (keySetView != null) ? keySetView : (this.keySetView = new KeySet<K>(this));
        }
        
        @Override
        public Collection<V> values() {
            final Collection<V> valuesView = this.valuesView;
            return (valuesView != null) ? valuesView : (this.valuesView = new Values<V>(this));
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            final Set<Map.Entry<K, V>> entrySetView = this.entrySetView;
            return (entrySetView != null) ? entrySetView : (this.entrySetView = (Set<Map.Entry<K, V>>)new EntrySet((ConcurrentNavigableMap<Object, Object>)this));
        }
        
        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.descendingMap().navigableKeySet();
        }
        
        Iterator<K> keyIterator() {
            return new SubMapKeyIterator();
        }
        
        Iterator<V> valueIterator() {
            return new SubMapValueIterator();
        }
        
        Iterator<Map.Entry<K, V>> entryIterator() {
            return new SubMapEntryIterator();
        }
        
        final class SubMapEntryIterator extends SubMapIter<Map.Entry<K, V>>
        {
            @Override
            public Map.Entry<K, V> next() {
                final Node<K, V> next = this.next;
                final V nextValue = this.nextValue;
                this.advance();
                return new SimpleImmutableEntry<K, V>(next.key, nextValue);
            }
            
            @Override
            public int characteristics() {
                return 1;
            }
        }
        
        abstract class SubMapIter<T> implements Iterator<T>, Spliterator<T>
        {
            Node<K, V> lastReturned;
            Node<K, V> next;
            V nextValue;
            
            SubMapIter() {
                final Comparator<? super K> comparator = (Comparator<? super K>)SubMap.this.m.comparator;
                while (true) {
                    this.next = (SubMap.this.isDescending ? SubMap.this.hiNode(comparator) : SubMap.this.loNode(comparator));
                    if (this.next == null) {
                        break;
                    }
                    final Object value = this.next.value;
                    if (value == null || value == this.next) {
                        continue;
                    }
                    if (!SubMap.this.inBounds(this.next.key, comparator)) {
                        this.next = null;
                        break;
                    }
                    this.nextValue = (V)value;
                    break;
                }
            }
            
            @Override
            public final boolean hasNext() {
                return this.next != null;
            }
            
            final void advance() {
                if (this.next == null) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = this.next;
                if (SubMap.this.isDescending) {
                    this.descend();
                }
                else {
                    this.ascend();
                }
            }
            
            private void ascend() {
                final Comparator<? super K> comparator = (Comparator<? super K>)SubMap.this.m.comparator;
                while (true) {
                    this.next = this.next.next;
                    if (this.next == null) {
                        break;
                    }
                    final Object value = this.next.value;
                    if (value == null || value == this.next) {
                        continue;
                    }
                    if (SubMap.this.tooHigh(this.next.key, comparator)) {
                        this.next = null;
                        break;
                    }
                    this.nextValue = (V)value;
                    break;
                }
            }
            
            private void descend() {
                final Comparator<? super K> comparator = (Comparator<? super K>)SubMap.this.m.comparator;
                while (true) {
                    this.next = (Node<K, V>)SubMap.this.m.findNear(this.lastReturned.key, 2, comparator);
                    if (this.next == null) {
                        break;
                    }
                    final Object value = this.next.value;
                    if (value == null || value == this.next) {
                        continue;
                    }
                    if (SubMap.this.tooLow(this.next.key, comparator)) {
                        this.next = null;
                        break;
                    }
                    this.nextValue = (V)value;
                    break;
                }
            }
            
            @Override
            public void remove() {
                final Node<K, V> lastReturned = this.lastReturned;
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                SubMap.this.m.remove(lastReturned.key);
                this.lastReturned = null;
            }
            
            @Override
            public Spliterator<T> trySplit() {
                return null;
            }
            
            @Override
            public boolean tryAdvance(final Consumer<? super T> consumer) {
                if (this.hasNext()) {
                    consumer.accept(this.next());
                    return true;
                }
                return false;
            }
            
            @Override
            public void forEachRemaining(final Consumer<? super T> consumer) {
                while (this.hasNext()) {
                    consumer.accept(this.next());
                }
            }
            
            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }
        }
        
        final class SubMapKeyIterator extends SubMapIter<K>
        {
            @Override
            public K next() {
                final Node<K, V> next = this.next;
                this.advance();
                return next.key;
            }
            
            @Override
            public int characteristics() {
                return 21;
            }
            
            @Override
            public final Comparator<? super K> getComparator() {
                return SubMap.this.comparator();
            }
        }
        
        final class SubMapValueIterator extends SubMapIter<V>
        {
            @Override
            public V next() {
                final V nextValue = this.nextValue;
                this.advance();
                return nextValue;
            }
            
            @Override
            public int characteristics() {
                return 0;
            }
        }
    }
    
    static final class KeySet<E> extends AbstractSet<E> implements NavigableSet<E>
    {
        final ConcurrentNavigableMap<E, ?> m;
        
        KeySet(final ConcurrentNavigableMap<E, ?> m) {
            this.m = m;
        }
        
        @Override
        public int size() {
            return this.m.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.m.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.m.remove(o) != null;
        }
        
        @Override
        public void clear() {
            this.m.clear();
        }
        
        @Override
        public E lower(final E e) {
            return this.m.lowerKey(e);
        }
        
        @Override
        public E floor(final E e) {
            return this.m.floorKey(e);
        }
        
        @Override
        public E ceiling(final E e) {
            return this.m.ceilingKey(e);
        }
        
        @Override
        public E higher(final E e) {
            return this.m.higherKey(e);
        }
        
        @Override
        public Comparator<? super E> comparator() {
            return this.m.comparator();
        }
        
        @Override
        public E first() {
            return this.m.firstKey();
        }
        
        @Override
        public E last() {
            return this.m.lastKey();
        }
        
        @Override
        public E pollFirst() {
            final Map.Entry<E, ?> pollFirstEntry = this.m.pollFirstEntry();
            return (pollFirstEntry == null) ? null : pollFirstEntry.getKey();
        }
        
        @Override
        public E pollLast() {
            final Map.Entry<E, ?> pollLastEntry = this.m.pollLastEntry();
            return (pollLastEntry == null) ? null : pollLastEntry.getKey();
        }
        
        @Override
        public Iterator<E> iterator() {
            if (this.m instanceof ConcurrentSkipListMap) {
                return ((ConcurrentSkipListMap)this.m).keyIterator();
            }
            return ((SubMap)this.m).keyIterator();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Set)) {
                return false;
            }
            final Collection collection = (Collection)o;
            try {
                return this.containsAll(collection) && collection.containsAll(this);
            }
            catch (ClassCastException ex) {
                return false;
            }
            catch (NullPointerException ex2) {
                return false;
            }
        }
        
        @Override
        public Object[] toArray() {
            return ConcurrentSkipListMap.toList((Collection<Object>)this).toArray();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            return ConcurrentSkipListMap.toList((Collection<Object>)this).toArray(array);
        }
        
        @Override
        public Iterator<E> descendingIterator() {
            return this.descendingSet().iterator();
        }
        
        @Override
        public NavigableSet<E> subSet(final E e, final boolean b, final E e2, final boolean b2) {
            return new KeySet((ConcurrentNavigableMap<Object, ?>)this.m.subMap(e, b, e2, b2));
        }
        
        @Override
        public NavigableSet<E> headSet(final E e, final boolean b) {
            return new KeySet((ConcurrentNavigableMap<Object, ?>)this.m.headMap(e, b));
        }
        
        @Override
        public NavigableSet<E> tailSet(final E e, final boolean b) {
            return new KeySet((ConcurrentNavigableMap<Object, ?>)this.m.tailMap(e, b));
        }
        
        @Override
        public NavigableSet<E> subSet(final E e, final E e2) {
            return this.subSet(e, true, e2, false);
        }
        
        @Override
        public NavigableSet<E> headSet(final E e) {
            return this.headSet(e, false);
        }
        
        @Override
        public NavigableSet<E> tailSet(final E e) {
            return this.tailSet(e, true);
        }
        
        @Override
        public NavigableSet<E> descendingSet() {
            return new KeySet((ConcurrentNavigableMap<Object, ?>)this.m.descendingMap());
        }
        
        @Override
        public Spliterator<E> spliterator() {
            if (this.m instanceof ConcurrentSkipListMap) {
                return (Spliterator<E>)((ConcurrentSkipListMap)this.m).keySpliterator();
            }
            return (Spliterator<E>)((SubMap)this.m).keyIterator();
        }
    }
    
    static final class KeySpliterator<K, V> extends CSLMSpliterator<K, V> implements Spliterator<K>
    {
        KeySpliterator(final Comparator<? super K> comparator, final Index<K, V> index, final Node<K, V> node, final K k, final int n) {
            super(comparator, index, node, k, n);
        }
        
        @Override
        public Spliterator<K> trySplit() {
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            final Node<K, V> current;
            final K key;
            if ((current = this.current) != null && (key = current.key) != null) {
                Index<K, V> down2;
                for (Index<K, V> row = this.row; row != null; row = down2) {
                    final Index<K, V> right;
                    final Node<K, V> node;
                    final Node<K, V> next;
                    final K key2;
                    if ((right = row.right) != null && (node = right.node) != null && (next = node.next) != null && next.value != null && (key2 = next.key) != null && ConcurrentSkipListMap.cpr(comparator, key2, key) > 0 && (fence == null || ConcurrentSkipListMap.cpr(comparator, key2, fence) < 0)) {
                        this.current = next;
                        final Index<K, V> down = row.down;
                        this.row = ((right.right != null) ? right : right.down);
                        this.est -= this.est >>> 2;
                        return new KeySpliterator<K, Object>((Comparator<? super Object>)comparator, (Index<Object, Object>)down, (Node<Object, Object>)current, key2, this.est);
                    }
                    down2 = row.down;
                    this.row = down2;
                }
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            Node<K, V> node = this.current;
            this.current = null;
            K key;
            while (node != null && ((key = node.key) == null || fence == null || ConcurrentSkipListMap.cpr(comparator, fence, key) > 0)) {
                final Object value;
                if ((value = node.value) != null && value != node) {
                    consumer.accept(key);
                }
                node = node.next;
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            Node<K, V> current;
            for (current = this.current; current != null; current = current.next) {
                final K key;
                if ((key = current.key) != null && fence != null && ConcurrentSkipListMap.cpr(comparator, fence, key) <= 0) {
                    current = null;
                    break;
                }
                final Object value;
                if ((value = current.value) != null && value != current) {
                    this.current = current.next;
                    consumer.accept(key);
                    return true;
                }
            }
            this.current = current;
            return false;
        }
        
        @Override
        public int characteristics() {
            return 4373;
        }
        
        @Override
        public final Comparator<? super K> getComparator() {
            return this.comparator;
        }
    }
    
    static final class Values<E> extends AbstractCollection<E>
    {
        final ConcurrentNavigableMap<?, E> m;
        
        Values(final ConcurrentNavigableMap<?, E> m) {
            this.m = m;
        }
        
        @Override
        public Iterator<E> iterator() {
            if (this.m instanceof ConcurrentSkipListMap) {
                return ((ConcurrentSkipListMap)this.m).valueIterator();
            }
            return ((SubMap)this.m).valueIterator();
        }
        
        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }
        
        @Override
        public int size() {
            return this.m.size();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.m.containsValue(o);
        }
        
        @Override
        public void clear() {
            this.m.clear();
        }
        
        @Override
        public Object[] toArray() {
            return ConcurrentSkipListMap.toList((Collection<Object>)this).toArray();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            return ConcurrentSkipListMap.toList((Collection<Object>)this).toArray(array);
        }
        
        @Override
        public Spliterator<E> spliterator() {
            if (this.m instanceof ConcurrentSkipListMap) {
                return (Spliterator<E>)((ConcurrentSkipListMap)this.m).valueSpliterator();
            }
            return (Spliterator<E>)((SubMap)this.m).valueIterator();
        }
    }
    
    static final class ValueSpliterator<K, V> extends CSLMSpliterator<K, V> implements Spliterator<V>
    {
        ValueSpliterator(final Comparator<? super K> comparator, final Index<K, V> index, final Node<K, V> node, final K k, final int n) {
            super(comparator, index, node, k, n);
        }
        
        @Override
        public Spliterator<V> trySplit() {
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            final Node<K, V> current;
            final K key;
            if ((current = this.current) != null && (key = current.key) != null) {
                Index<K, V> down2;
                for (Index<K, V> row = this.row; row != null; row = down2) {
                    final Index<K, V> right;
                    final Node<K, V> node;
                    final Node<K, V> next;
                    final K key2;
                    if ((right = row.right) != null && (node = right.node) != null && (next = node.next) != null && next.value != null && (key2 = next.key) != null && ConcurrentSkipListMap.cpr(comparator, key2, key) > 0 && (fence == null || ConcurrentSkipListMap.cpr(comparator, key2, fence) < 0)) {
                        this.current = next;
                        final Index<K, V> down = row.down;
                        this.row = ((right.right != null) ? right : right.down);
                        this.est -= this.est >>> 2;
                        return new ValueSpliterator<Object, V>((Comparator<? super Object>)comparator, (Index<Object, Object>)down, (Node<Object, Object>)current, key2, this.est);
                    }
                    down2 = row.down;
                    this.row = down2;
                }
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            Node<K, V> node = this.current;
            this.current = null;
            K key;
            while (node != null && ((key = node.key) == null || fence == null || ConcurrentSkipListMap.cpr(comparator, fence, key) > 0)) {
                final Object value;
                if ((value = node.value) != null && value != node) {
                    consumer.accept((Object)value);
                }
                node = node.next;
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            Node<K, V> current;
            for (current = this.current; current != null; current = current.next) {
                final K key;
                if ((key = current.key) != null && fence != null && ConcurrentSkipListMap.cpr(comparator, fence, key) <= 0) {
                    current = null;
                    break;
                }
                final Object value;
                if ((value = current.value) != null && value != current) {
                    this.current = current.next;
                    consumer.accept((Object)value);
                    return true;
                }
            }
            this.current = current;
            return false;
        }
        
        @Override
        public int characteristics() {
            return 4368;
        }
    }
    
    static final class EntrySpliterator<K, V> extends CSLMSpliterator<K, V> implements Spliterator<Map.Entry<K, V>>
    {
        EntrySpliterator(final Comparator<? super K> comparator, final Index<K, V> index, final Node<K, V> node, final K k, final int n) {
            super(comparator, index, node, k, n);
        }
        
        @Override
        public Spliterator<Map.Entry<K, V>> trySplit() {
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            final Node<K, V> current;
            final K key;
            if ((current = this.current) != null && (key = current.key) != null) {
                Index<K, V> down2;
                for (Index<K, V> row = this.row; row != null; row = down2) {
                    final Index<K, V> right;
                    final Node<K, V> node;
                    final Node<K, V> next;
                    final K key2;
                    if ((right = row.right) != null && (node = right.node) != null && (next = node.next) != null && next.value != null && (key2 = next.key) != null && ConcurrentSkipListMap.cpr(comparator, key2, key) > 0 && (fence == null || ConcurrentSkipListMap.cpr(comparator, key2, fence) < 0)) {
                        this.current = next;
                        final Index<K, V> down = row.down;
                        this.row = ((right.right != null) ? right : right.down);
                        this.est -= this.est >>> 2;
                        return (Spliterator<Map.Entry<K, V>>)new EntrySpliterator<Object, Object>((Comparator<? super Object>)comparator, (Index<Object, Object>)down, (Node<Object, Object>)current, key2, this.est);
                    }
                    down2 = row.down;
                    this.row = down2;
                }
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            Node<K, V> node = this.current;
            this.current = null;
            K key;
            while (node != null && ((key = node.key) == null || fence == null || ConcurrentSkipListMap.cpr(comparator, fence, key) > 0)) {
                final Object value;
                if ((value = node.value) != null && value != node) {
                    consumer.accept((Object)new SimpleImmutableEntry(key, value));
                }
                node = node.next;
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Comparator<? super K> comparator = this.comparator;
            final K fence = this.fence;
            Node<K, V> current;
            for (current = this.current; current != null; current = current.next) {
                final K key;
                if ((key = current.key) != null && fence != null && ConcurrentSkipListMap.cpr(comparator, fence, key) <= 0) {
                    current = null;
                    break;
                }
                final Object value;
                if ((value = current.value) != null && value != current) {
                    this.current = current.next;
                    consumer.accept((Object)new SimpleImmutableEntry(key, value));
                    return true;
                }
            }
            this.current = current;
            return false;
        }
        
        @Override
        public int characteristics() {
            return 4373;
        }
        
        @Override
        public final Comparator<Map.Entry<K, V>> getComparator() {
            if (this.comparator != null) {
                return Map.Entry.comparingByKey(this.comparator);
            }
            return (Comparator<Map.Entry<K, V>>)((entry, entry2) -> entry.getKey().compareTo(entry2.getKey()));
        }
    }
    
    static final class HeadIndex<K, V> extends Index<K, V>
    {
        final int level;
        
        HeadIndex(final Node<K, V> node, final Index<K, V> index, final Index<K, V> index2, final int level) {
            super(node, index, index2);
            this.level = level;
        }
    }
    
    final class KeyIterator extends Iter<K>
    {
        @Override
        public K next() {
            final Node<K, V> next = this.next;
            this.advance();
            return next.key;
        }
    }
    
    final class ValueIterator extends Iter<V>
    {
        @Override
        public V next() {
            final V nextValue = this.nextValue;
            this.advance();
            return nextValue;
        }
    }
}
