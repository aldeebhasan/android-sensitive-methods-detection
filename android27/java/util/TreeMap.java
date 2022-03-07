package java.util;

import java.io.*;
import java.util.function.*;
import java.lang.invoke.*;

public class TreeMap<K, V> extends AbstractMap<K, V> implements NavigableMap<K, V>, Cloneable, Serializable
{
    private final Comparator<? super K> comparator;
    private transient Entry<K, V> root;
    private transient int size;
    private transient int modCount;
    private transient EntrySet entrySet;
    private transient KeySet<K> navigableKeySet;
    private transient NavigableMap<K, V> descendingMap;
    private static final Object UNBOUNDED;
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final long serialVersionUID = 919286545866124006L;
    
    public TreeMap() {
        this.size = 0;
        this.modCount = 0;
        this.comparator = null;
    }
    
    public TreeMap(final Comparator<? super K> comparator) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = comparator;
    }
    
    public TreeMap(final Map<? extends K, ? extends V> map) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = null;
        this.putAll(map);
    }
    
    public TreeMap(final SortedMap<K, ? extends V> sortedMap) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = sortedMap.comparator();
        try {
            this.buildFromSorted(sortedMap.size(), sortedMap.entrySet().iterator(), null, null);
        }
        catch (IOException ex) {}
        catch (ClassNotFoundException ex2) {}
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.getEntry(o) != null;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        for (Entry<Object, Object> entry = this.getFirstEntry(); entry != null; entry = successor(entry)) {
            if (valEquals(o, entry.value)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public V get(final Object o) {
        final Entry<K, V> entry = this.getEntry(o);
        return (entry == null) ? null : entry.value;
    }
    
    @Override
    public Comparator<? super K> comparator() {
        return this.comparator;
    }
    
    @Override
    public K firstKey() {
        return key(this.getFirstEntry());
    }
    
    @Override
    public K lastKey() {
        return key(this.getLastEntry());
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        final int size = map.size();
        if (this.size == 0 && size != 0 && map instanceof SortedMap) {
            final Comparator<? super K> comparator = ((SortedMap<? extends K, ? extends V>)map).comparator();
            if (comparator == this.comparator || (comparator != null && comparator.equals(this.comparator))) {
                ++this.modCount;
                try {
                    this.buildFromSorted(size, map.entrySet().iterator(), null, null);
                }
                catch (IOException ex) {}
                catch (ClassNotFoundException ex2) {}
                return;
            }
        }
        super.putAll(map);
    }
    
    final Entry<K, V> getEntry(final Object o) {
        if (this.comparator != null) {
            return this.getEntryUsingComparator(o);
        }
        if (o == null) {
            throw new NullPointerException();
        }
        final Comparable comparable = (Comparable)o;
        Entry<K, V> entry = this.root;
        while (entry != null) {
            final int compareTo = comparable.compareTo(entry.key);
            if (compareTo < 0) {
                entry = entry.left;
            }
            else {
                if (compareTo <= 0) {
                    return entry;
                }
                entry = entry.right;
            }
        }
        return null;
    }
    
    final Entry<K, V> getEntryUsingComparator(final Object o) {
        final Comparator<? super K> comparator = this.comparator;
        if (comparator != null) {
            Entry<K, V> entry = this.root;
            while (entry != null) {
                final int compare = comparator.compare(o, entry.key);
                if (compare < 0) {
                    entry = entry.left;
                }
                else {
                    if (compare <= 0) {
                        return entry;
                    }
                    entry = entry.right;
                }
            }
        }
        return null;
    }
    
    final Entry<K, V> getCeilingEntry(final K k) {
        Entry<K, V> entry = this.root;
        while (entry != null) {
            final int compare = this.compare(k, entry.key);
            if (compare < 0) {
                if (entry.left == null) {
                    return entry;
                }
                entry = entry.left;
            }
            else {
                if (compare <= 0) {
                    return entry;
                }
                if (entry.right == null) {
                    Entry<K, V> entry2 = entry.parent;
                    for (Entry<K, V> entry3 = entry; entry2 != null && entry3 == entry2.right; entry3 = entry2, entry2 = entry2.parent) {}
                    return entry2;
                }
                entry = entry.right;
            }
        }
        return null;
    }
    
    final Entry<K, V> getFloorEntry(final K k) {
        Entry<K, V> entry = this.root;
        while (entry != null) {
            final int compare = this.compare(k, entry.key);
            if (compare > 0) {
                if (entry.right == null) {
                    return entry;
                }
                entry = entry.right;
            }
            else {
                if (compare >= 0) {
                    return entry;
                }
                if (entry.left == null) {
                    Entry<K, V> entry2 = entry.parent;
                    for (Entry<K, V> entry3 = entry; entry2 != null && entry3 == entry2.left; entry3 = entry2, entry2 = entry2.parent) {}
                    return entry2;
                }
                entry = entry.left;
            }
        }
        return null;
    }
    
    final Entry<K, V> getHigherEntry(final K k) {
        Entry<K, V> entry = this.root;
        while (entry != null) {
            if (this.compare(k, entry.key) < 0) {
                if (entry.left == null) {
                    return entry;
                }
                entry = entry.left;
            }
            else {
                if (entry.right == null) {
                    Entry<K, V> entry2 = entry.parent;
                    for (Entry<K, V> entry3 = entry; entry2 != null && entry3 == entry2.right; entry3 = entry2, entry2 = entry2.parent) {}
                    return entry2;
                }
                entry = entry.right;
            }
        }
        return null;
    }
    
    final Entry<K, V> getLowerEntry(final K k) {
        Entry<K, V> entry = this.root;
        while (entry != null) {
            if (this.compare(k, entry.key) > 0) {
                if (entry.right == null) {
                    return entry;
                }
                entry = entry.right;
            }
            else {
                if (entry.left == null) {
                    Entry<K, V> entry2 = entry.parent;
                    for (Entry<K, V> entry3 = entry; entry2 != null && entry3 == entry2.left; entry3 = entry2, entry2 = entry2.parent) {}
                    return entry2;
                }
                entry = entry.left;
            }
        }
        return null;
    }
    
    @Override
    public V put(final K k, final V v) {
        Object o = this.root;
        if (o == null) {
            this.compare(k, k);
            this.root = new Entry<K, V>(k, v, null);
            this.size = 1;
            ++this.modCount;
            return null;
        }
        final Comparator<? super K> comparator = this.comparator;
        Entry<Object, V> entry;
        int n;
        if (comparator != null) {
            do {
                entry = (Entry<Object, V>)o;
                n = comparator.compare(k, (K)((Entry)o).key);
                if (n < 0) {
                    o = ((Entry)o).left;
                }
                else {
                    if (n <= 0) {
                        return ((Entry<Object, V>)o).setValue(v);
                    }
                    o = ((Entry)o).right;
                }
            } while (o != null);
        }
        else {
            if (k == null) {
                throw new NullPointerException();
            }
            final Comparable comparable = (Comparable)k;
            do {
                entry = (Entry<Object, V>)o;
                n = comparable.compareTo(((Entry)o).key);
                if (n < 0) {
                    o = ((Entry)o).left;
                }
                else {
                    if (n <= 0) {
                        return ((Entry<Object, V>)o).setValue(v);
                    }
                    o = ((Entry)o).right;
                }
            } while (o != null);
        }
        final Entry entry2 = new Entry<K, V>(k, v, entry);
        if (n < 0) {
            entry.left = (Entry<Object, V>)entry2;
        }
        else {
            entry.right = (Entry<Object, V>)entry2;
        }
        this.fixAfterInsertion((Entry<K, V>)entry2);
        ++this.size;
        ++this.modCount;
        return null;
    }
    
    @Override
    public V remove(final Object o) {
        final Entry<K, V> entry = this.getEntry(o);
        if (entry == null) {
            return null;
        }
        final V value = entry.value;
        this.deleteEntry(entry);
        return value;
    }
    
    @Override
    public void clear() {
        ++this.modCount;
        this.size = 0;
        this.root = null;
    }
    
    public Object clone() {
        TreeMap treeMap;
        try {
            treeMap = (TreeMap)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
        treeMap.root = null;
        treeMap.size = 0;
        treeMap.modCount = 0;
        treeMap.entrySet = null;
        treeMap.navigableKeySet = null;
        treeMap.descendingMap = null;
        try {
            treeMap.buildFromSorted(this.size, this.entrySet().iterator(), null, null);
        }
        catch (IOException ex2) {}
        catch (ClassNotFoundException ex3) {}
        return treeMap;
    }
    
    @Override
    public Map.Entry<K, V> firstEntry() {
        return exportEntry(this.getFirstEntry());
    }
    
    @Override
    public Map.Entry<K, V> lastEntry() {
        return exportEntry(this.getLastEntry());
    }
    
    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        final Entry<K, V> firstEntry = this.getFirstEntry();
        final Map.Entry<K, V> exportEntry = exportEntry(firstEntry);
        if (firstEntry != null) {
            this.deleteEntry(firstEntry);
        }
        return exportEntry;
    }
    
    @Override
    public Map.Entry<K, V> pollLastEntry() {
        final Entry<K, V> lastEntry = this.getLastEntry();
        final Map.Entry<K, V> exportEntry = exportEntry(lastEntry);
        if (lastEntry != null) {
            this.deleteEntry(lastEntry);
        }
        return exportEntry;
    }
    
    @Override
    public Map.Entry<K, V> lowerEntry(final K k) {
        return exportEntry((Entry<K, V>)this.getLowerEntry((K)k));
    }
    
    @Override
    public K lowerKey(final K k) {
        return keyOrNull((Entry<K, Object>)this.getLowerEntry((K)k));
    }
    
    @Override
    public Map.Entry<K, V> floorEntry(final K k) {
        return exportEntry((Entry<K, V>)this.getFloorEntry((K)k));
    }
    
    @Override
    public K floorKey(final K k) {
        return keyOrNull((Entry<K, Object>)this.getFloorEntry((K)k));
    }
    
    @Override
    public Map.Entry<K, V> ceilingEntry(final K k) {
        return exportEntry((Entry<K, V>)this.getCeilingEntry((K)k));
    }
    
    @Override
    public K ceilingKey(final K k) {
        return keyOrNull((Entry<K, Object>)this.getCeilingEntry((K)k));
    }
    
    @Override
    public Map.Entry<K, V> higherEntry(final K k) {
        return exportEntry((Entry<K, V>)this.getHigherEntry((K)k));
    }
    
    @Override
    public K higherKey(final K k) {
        return keyOrNull((Entry<K, Object>)this.getHigherEntry((K)k));
    }
    
    @Override
    public Set<K> keySet() {
        return this.navigableKeySet();
    }
    
    @Override
    public NavigableSet<K> navigableKeySet() {
        final KeySet<K> navigableKeySet = this.navigableKeySet;
        return (navigableKeySet != null) ? navigableKeySet : (this.navigableKeySet = new KeySet<K>(this));
    }
    
    @Override
    public NavigableSet<K> descendingKeySet() {
        return this.descendingMap().navigableKeySet();
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
    public Set<Map.Entry<K, V>> entrySet() {
        final EntrySet entrySet = this.entrySet;
        return (entrySet != null) ? entrySet : (this.entrySet = new EntrySet());
    }
    
    @Override
    public NavigableMap<K, V> descendingMap() {
        final NavigableMap<K, V> descendingMap = this.descendingMap;
        return (descendingMap != null) ? descendingMap : (this.descendingMap = new DescendingSubMap<K, V>(this, true, null, true, true, null, true));
    }
    
    @Override
    public NavigableMap<K, V> subMap(final K k, final boolean b, final K i, final boolean b2) {
        return new AscendingSubMap<K, V>(this, false, k, b, false, i, b2);
    }
    
    @Override
    public NavigableMap<K, V> headMap(final K k, final boolean b) {
        return new AscendingSubMap<K, V>(this, true, null, true, false, k, b);
    }
    
    @Override
    public NavigableMap<K, V> tailMap(final K k, final boolean b) {
        return new AscendingSubMap<K, V>(this, false, k, b, true, null, true);
    }
    
    @Override
    public SortedMap<K, V> subMap(final K k, final K i) {
        return this.subMap(k, true, i, false);
    }
    
    @Override
    public SortedMap<K, V> headMap(final K k) {
        return this.headMap(k, false);
    }
    
    @Override
    public SortedMap<K, V> tailMap(final K k) {
        return this.tailMap(k, true);
    }
    
    @Override
    public boolean replace(final K k, final V v, final V value) {
        final Entry<K, V> entry = this.getEntry(k);
        if (entry != null && Objects.equals(v, entry.value)) {
            entry.value = value;
            return true;
        }
        return false;
    }
    
    @Override
    public V replace(final K k, final V value) {
        final Entry<K, V> entry = this.getEntry(k);
        if (entry != null) {
            final V value2 = entry.value;
            entry.value = value;
            return value2;
        }
        return null;
    }
    
    @Override
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        final int modCount = this.modCount;
        for (Entry<Object, Object> entry = this.getFirstEntry(); entry != null; entry = successor(entry)) {
            biConsumer.accept((Object)entry.key, (Object)entry.value);
            if (modCount != this.modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    @Override
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        final int modCount = this.modCount;
        for (Entry<Object, Object> entry = this.getFirstEntry(); entry != null; entry = successor(entry)) {
            entry.value = biFunction.apply((Object)entry.key, (Object)entry.value);
            if (modCount != this.modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    Iterator<K> keyIterator() {
        return new KeyIterator(this.getFirstEntry());
    }
    
    Iterator<K> descendingKeyIterator() {
        return new DescendingKeyIterator(this.getLastEntry());
    }
    
    final int compare(final Object o, final Object o2) {
        return (this.comparator == null) ? ((Comparable)o).compareTo(o2) : this.comparator.compare((Object)o, (Object)o2);
    }
    
    static final boolean valEquals(final Object o, final Object o2) {
        return (o == null) ? (o2 == null) : o.equals(o2);
    }
    
    static <K, V> Map.Entry<K, V> exportEntry(final Entry<K, V> entry) {
        return (entry == null) ? null : new SimpleImmutableEntry<K, V>((Map.Entry<? extends K, ? extends V>)entry);
    }
    
    static <K, V> K keyOrNull(final Entry<K, V> entry) {
        return (entry == null) ? null : entry.key;
    }
    
    static <K> K key(final Entry<K, ?> entry) {
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return entry.key;
    }
    
    final Entry<K, V> getFirstEntry() {
        Entry<K, V> entry = this.root;
        if (entry != null) {
            while (entry.left != null) {
                entry = entry.left;
            }
        }
        return entry;
    }
    
    final Entry<K, V> getLastEntry() {
        Entry<K, V> entry = this.root;
        if (entry != null) {
            while (entry.right != null) {
                entry = entry.right;
            }
        }
        return entry;
    }
    
    static <K, V> Entry<K, V> successor(final Entry<K, V> entry) {
        if (entry == null) {
            return null;
        }
        if (entry.right != null) {
            Entry<K, V> entry2;
            for (entry2 = entry.right; entry2.left != null; entry2 = entry2.left) {}
            return entry2;
        }
        Entry<K, V> entry3 = entry.parent;
        for (Entry<K, V> entry4 = entry; entry3 != null && entry4 == entry3.right; entry4 = entry3, entry3 = entry3.parent) {}
        return entry3;
    }
    
    static <K, V> Entry<K, V> predecessor(final Entry<K, V> entry) {
        if (entry == null) {
            return null;
        }
        if (entry.left != null) {
            Entry<K, V> entry2;
            for (entry2 = entry.left; entry2.right != null; entry2 = entry2.right) {}
            return entry2;
        }
        Entry<K, V> entry3 = entry.parent;
        for (Entry<K, V> entry4 = entry; entry3 != null && entry4 == entry3.left; entry4 = entry3, entry3 = entry3.parent) {}
        return entry3;
    }
    
    private static <K, V> boolean colorOf(final Entry<K, V> entry) {
        return entry == null || entry.color;
    }
    
    private static <K, V> Entry<K, V> parentOf(final Entry<K, V> entry) {
        return (entry == null) ? null : entry.parent;
    }
    
    private static <K, V> void setColor(final Entry<K, V> entry, final boolean color) {
        if (entry != null) {
            entry.color = color;
        }
    }
    
    private static <K, V> Entry<K, V> leftOf(final Entry<K, V> entry) {
        return (entry == null) ? null : entry.left;
    }
    
    private static <K, V> Entry<K, V> rightOf(final Entry<K, V> entry) {
        return (entry == null) ? null : entry.right;
    }
    
    private void rotateLeft(final Entry<K, V> entry) {
        if (entry != null) {
            final Entry<K, V> right = entry.right;
            entry.right = right.left;
            if (right.left != null) {
                right.left.parent = entry;
            }
            right.parent = entry.parent;
            if (entry.parent == null) {
                this.root = right;
            }
            else if (entry.parent.left == entry) {
                entry.parent.left = right;
            }
            else {
                entry.parent.right = right;
            }
            right.left = entry;
            entry.parent = right;
        }
    }
    
    private void rotateRight(final Entry<K, V> entry) {
        if (entry != null) {
            final Entry<K, V> left = entry.left;
            entry.left = left.right;
            if (left.right != null) {
                left.right.parent = entry;
            }
            left.parent = entry.parent;
            if (entry.parent == null) {
                this.root = left;
            }
            else if (entry.parent.right == entry) {
                entry.parent.right = left;
            }
            else {
                entry.parent.left = left;
            }
            left.right = entry;
            entry.parent = left;
        }
    }
    
    private void fixAfterInsertion(Entry<K, V> entry) {
        entry.color = false;
        while (entry != null && entry != this.root && !entry.parent.color) {
            if (parentOf(entry) == leftOf(parentOf(parentOf(entry)))) {
                final Entry<K, V> right = rightOf(parentOf(parentOf(entry)));
                if (!colorOf(right)) {
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)entry), true);
                    setColor((Entry<K, V>)right, true);
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)parentOf(entry)), false);
                    entry = parentOf(parentOf(entry));
                }
                else {
                    if (entry == rightOf(parentOf(entry))) {
                        entry = parentOf(entry);
                        this.rotateLeft(entry);
                    }
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)entry), true);
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)parentOf(entry)), false);
                    this.rotateRight(parentOf(parentOf(entry)));
                }
            }
            else {
                final Entry<K, V> left = leftOf(parentOf(parentOf(entry)));
                if (!colorOf(left)) {
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)entry), true);
                    setColor((Entry<K, V>)left, true);
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)parentOf(entry)), false);
                    entry = parentOf(parentOf(entry));
                }
                else {
                    if (entry == leftOf(parentOf(entry))) {
                        entry = parentOf(entry);
                        this.rotateRight(entry);
                    }
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)entry), true);
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)parentOf(entry)), false);
                    this.rotateLeft(parentOf(parentOf(entry)));
                }
            }
        }
        this.root.color = true;
    }
    
    private void deleteEntry(Entry<K, V> entry) {
        ++this.modCount;
        --this.size;
        if (entry.left != null && entry.right != null) {
            final Entry<K, V> successor = successor(entry);
            entry.key = successor.key;
            entry.value = successor.value;
            entry = successor;
        }
        final Entry<K, V> right = (entry.left != null) ? entry.left : entry.right;
        if (right != null) {
            right.parent = entry.parent;
            if (entry.parent == null) {
                this.root = right;
            }
            else if (entry == entry.parent.left) {
                entry.parent.left = right;
            }
            else {
                entry.parent.right = right;
            }
            final Entry<K, V> entry2 = entry;
            final Entry<K, V> entry3 = entry;
            final Entry<K, V> entry4 = entry;
            final Entry<K, V> left = null;
            entry4.parent = (Entry<K, V>)left;
            entry3.right = (Entry<K, V>)left;
            entry2.left = (Entry<K, V>)left;
            if (entry.color) {
                this.fixAfterDeletion(right);
            }
        }
        else if (entry.parent == null) {
            this.root = null;
        }
        else {
            if (entry.color) {
                this.fixAfterDeletion(entry);
            }
            if (entry.parent != null) {
                if (entry == entry.parent.left) {
                    entry.parent.left = null;
                }
                else if (entry == entry.parent.right) {
                    entry.parent.right = null;
                }
                entry.parent = null;
            }
        }
    }
    
    private void fixAfterDeletion(Entry<K, V> entry) {
        while (entry != this.root && colorOf(entry)) {
            if (entry == leftOf(parentOf(entry))) {
                Entry<K, V> entry2 = rightOf(parentOf(entry));
                if (!colorOf(entry2)) {
                    setColor((Entry<K, V>)entry2, true);
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)entry), false);
                    this.rotateLeft(parentOf(entry));
                    entry2 = (Entry<K, V>)rightOf((Entry<K, V>)parentOf(entry));
                }
                if (colorOf((Entry<Object, Object>)leftOf((Entry<K, V>)entry2)) && colorOf((Entry<Object, Object>)rightOf((Entry<K, V>)entry2))) {
                    setColor((Entry<K, V>)entry2, false);
                    entry = parentOf(entry);
                }
                else {
                    if (colorOf((Entry<Object, Object>)rightOf((Entry<K, V>)entry2))) {
                        setColor((Entry<Object, Object>)leftOf((Entry<K, V>)entry2), true);
                        setColor((Entry<K, V>)entry2, false);
                        this.rotateRight(entry2);
                        entry2 = rightOf(parentOf(entry));
                    }
                    setColor(entry2, colorOf((Entry<Object, Object>)parentOf((Entry<K, V>)entry)));
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)entry), true);
                    setColor((Entry<Object, Object>)rightOf((Entry<K, V>)entry2), true);
                    this.rotateLeft(parentOf(entry));
                    entry = this.root;
                }
            }
            else {
                Entry<K, V> entry3 = leftOf(parentOf(entry));
                if (!colorOf(entry3)) {
                    setColor((Entry<K, V>)entry3, true);
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)entry), false);
                    this.rotateRight(parentOf(entry));
                    entry3 = (Entry<K, V>)leftOf((Entry<K, V>)parentOf(entry));
                }
                if (colorOf((Entry<Object, Object>)rightOf((Entry<K, V>)entry3)) && colorOf((Entry<Object, Object>)leftOf((Entry<K, V>)entry3))) {
                    setColor((Entry<K, V>)entry3, false);
                    entry = parentOf(entry);
                }
                else {
                    if (colorOf((Entry<Object, Object>)leftOf((Entry<K, V>)entry3))) {
                        setColor((Entry<Object, Object>)rightOf((Entry<K, V>)entry3), true);
                        setColor((Entry<K, V>)entry3, false);
                        this.rotateLeft(entry3);
                        entry3 = leftOf(parentOf(entry));
                    }
                    setColor(entry3, colorOf((Entry<Object, Object>)parentOf((Entry<K, V>)entry)));
                    setColor((Entry<Object, Object>)parentOf((Entry<K, V>)entry), true);
                    setColor((Entry<Object, Object>)leftOf((Entry<K, V>)entry3), true);
                    this.rotateRight(parentOf(entry));
                    entry = this.root;
                }
            }
        }
        setColor(entry, true);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        for (final Map.Entry<K, V> entry : this.entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.buildFromSorted(objectInputStream.readInt(), null, objectInputStream, null);
    }
    
    void readTreeSet(final int n, final ObjectInputStream objectInputStream, final V v) throws IOException, ClassNotFoundException {
        this.buildFromSorted(n, null, objectInputStream, v);
    }
    
    void addAllForTreeSet(final SortedSet<? extends K> set, final V v) {
        try {
            this.buildFromSorted(set.size(), set.iterator(), null, v);
        }
        catch (IOException ex) {}
        catch (ClassNotFoundException ex2) {}
    }
    
    private void buildFromSorted(final int size, final Iterator<?> iterator, final ObjectInputStream objectInputStream, final V v) throws IOException, ClassNotFoundException {
        this.size = size;
        this.root = this.buildFromSorted(0, 0, size - 1, computeRedLevel(size), iterator, objectInputStream, v);
    }
    
    private final Entry<K, V> buildFromSorted(final int n, final int n2, final int n3, final int n4, final Iterator<?> iterator, final ObjectInputStream objectInputStream, final V v) throws IOException, ClassNotFoundException {
        if (n3 < n2) {
            return null;
        }
        final int n5 = n2 + n3 >>> 1;
        Entry<K, Object> buildFromSorted = null;
        if (n2 < n5) {
            buildFromSorted = (Entry<K, Object>)this.buildFromSorted(n + 1, n2, n5 - 1, n4, iterator, objectInputStream, (V)v);
        }
        Object o;
        Object value;
        if (iterator != null) {
            if (v == null) {
                final Map.Entry entry = (Map.Entry)iterator.next();
                o = entry.getKey();
                value = entry.getValue();
            }
            else {
                o = iterator.next();
                value = v;
            }
        }
        else {
            o = objectInputStream.readObject();
            value = ((v != null) ? v : objectInputStream.readObject());
        }
        final Entry entry2 = new Entry<K, V>((K)o, (V)value, null);
        if (n == n4) {
            entry2.color = false;
        }
        if (buildFromSorted != null) {
            entry2.left = (Entry<K, V>)buildFromSorted;
            buildFromSorted.parent = (Entry<K, Object>)entry2;
        }
        if (n5 < n3) {
            final Entry<K, V> buildFromSorted2 = this.buildFromSorted(n + 1, n5 + 1, n3, n4, iterator, objectInputStream, (V)v);
            entry2.right = (Entry<K, V>)buildFromSorted2;
            buildFromSorted2.parent = (Entry<K, V>)entry2;
        }
        return (Entry<K, V>)entry2;
    }
    
    private static int computeRedLevel(final int n) {
        int n2 = 0;
        for (int i = n - 1; i >= 0; i = i / 2 - 1) {
            ++n2;
        }
        return n2;
    }
    
    static <K> Spliterator<K> keySpliteratorFor(final NavigableMap<K, ?> navigableMap) {
        if (navigableMap instanceof TreeMap) {
            return (Spliterator<K>)((TreeMap<Object, Object>)navigableMap).keySpliterator();
        }
        if (navigableMap instanceof DescendingSubMap) {
            final DescendingSubMap<K, V> descendingSubMap = (DescendingSubMap<K, V>)navigableMap;
            final TreeMap<K, V> m = descendingSubMap.m;
            if (descendingSubMap == m.descendingMap) {
                return (Spliterator<K>)m.descendingKeySpliterator();
            }
        }
        return (Spliterator<K>)((DescendingSubMap<Object, Object>)navigableMap).keySpliterator();
    }
    
    final Spliterator<K> keySpliterator() {
        return new KeySpliterator<K, Object>(this, null, null, 0, -1, 0);
    }
    
    final Spliterator<K> descendingKeySpliterator() {
        return new DescendingKeySpliterator<K, Object>(this, null, null, 0, -2, 0);
    }
    
    static {
        UNBOUNDED = new Object();
    }
    
    static final class AscendingSubMap<K, V> extends NavigableSubMap<K, V>
    {
        private static final long serialVersionUID = 912986545866124060L;
        
        AscendingSubMap(final TreeMap<K, V> treeMap, final boolean b, final K k, final boolean b2, final boolean b3, final K i, final boolean b4) {
            super(treeMap, b, k, b2, b3, i, b4);
        }
        
        @Override
        public Comparator<? super K> comparator() {
            return this.m.comparator();
        }
        
        @Override
        public NavigableMap<K, V> subMap(final K k, final boolean b, final K i, final boolean b2) {
            if (!this.inRange(k, b)) {
                throw new IllegalArgumentException("fromKey out of range");
            }
            if (!this.inRange(i, b2)) {
                throw new IllegalArgumentException("toKey out of range");
            }
            return new AscendingSubMap((TreeMap<Object, Object>)this.m, false, k, b, false, i, b2);
        }
        
        @Override
        public NavigableMap<K, V> headMap(final K k, final boolean b) {
            if (!this.inRange(k, b)) {
                throw new IllegalArgumentException("toKey out of range");
            }
            return new AscendingSubMap((TreeMap<Object, Object>)this.m, this.fromStart, this.lo, this.loInclusive, false, k, b);
        }
        
        @Override
        public NavigableMap<K, V> tailMap(final K k, final boolean b) {
            if (!this.inRange(k, b)) {
                throw new IllegalArgumentException("fromKey out of range");
            }
            return new AscendingSubMap((TreeMap<Object, Object>)this.m, false, k, b, this.toEnd, this.hi, this.hiInclusive);
        }
        
        @Override
        public NavigableMap<K, V> descendingMap() {
            final NavigableMap<K, V> descendingMapView = this.descendingMapView;
            return (descendingMapView != null) ? descendingMapView : (this.descendingMapView = (NavigableMap<K, V>)new DescendingSubMap<Object, Object>((TreeMap<K, V>)this.m, this.fromStart, (K)this.lo, this.loInclusive, this.toEnd, (K)this.hi, this.hiInclusive));
        }
        
        @Override
        Iterator<K> keyIterator() {
            return (Iterator<K>)new SubMapKeyIterator((TreeMap.Entry<K, V>)this.absLowest(), (TreeMap.Entry<K, V>)this.absHighFence());
        }
        
        @Override
        Spliterator<K> keySpliterator() {
            return (Spliterator<K>)new SubMapKeyIterator((TreeMap.Entry<K, V>)this.absLowest(), (TreeMap.Entry<K, V>)this.absHighFence());
        }
        
        @Override
        Iterator<K> descendingKeyIterator() {
            return (Iterator<K>)new DescendingSubMapKeyIterator((TreeMap.Entry<K, V>)this.absHighest(), (TreeMap.Entry<K, V>)this.absLowFence());
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            final EntrySetView entrySetView = this.entrySetView;
            return (Set<Map.Entry<K, V>>)((entrySetView != null) ? entrySetView : (this.entrySetView = new AscendingEntrySetView()));
        }
        
        @Override
        TreeMap.Entry<K, V> subLowest() {
            return this.absLowest();
        }
        
        @Override
        TreeMap.Entry<K, V> subHighest() {
            return this.absHighest();
        }
        
        @Override
        TreeMap.Entry<K, V> subCeiling(final K k) {
            return this.absCeiling(k);
        }
        
        @Override
        TreeMap.Entry<K, V> subHigher(final K k) {
            return this.absHigher(k);
        }
        
        @Override
        TreeMap.Entry<K, V> subFloor(final K k) {
            return this.absFloor(k);
        }
        
        @Override
        TreeMap.Entry<K, V> subLower(final K k) {
            return this.absLower(k);
        }
        
        final class AscendingEntrySetView extends EntrySetView
        {
            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return (Iterator<Map.Entry<K, V>>)new SubMapEntryIterator((TreeMap.Entry<K, V>)AscendingSubMap.this.absLowest(), (TreeMap.Entry<K, V>)AscendingSubMap.this.absHighFence());
            }
        }
    }
    
    abstract static class NavigableSubMap<K, V> extends AbstractMap<K, V> implements NavigableMap<K, V>, Serializable
    {
        private static final long serialVersionUID = -2102997345730753016L;
        final TreeMap<K, V> m;
        final K lo;
        final K hi;
        final boolean fromStart;
        final boolean toEnd;
        final boolean loInclusive;
        final boolean hiInclusive;
        transient NavigableMap<K, V> descendingMapView;
        transient EntrySetView entrySetView;
        transient KeySet<K> navigableKeySetView;
        
        NavigableSubMap(final TreeMap<K, V> m, final boolean fromStart, final K lo, final boolean loInclusive, final boolean toEnd, final K hi, final boolean hiInclusive) {
            if (!fromStart && !toEnd) {
                if (m.compare(lo, hi) > 0) {
                    throw new IllegalArgumentException("fromKey > toKey");
                }
            }
            else {
                if (!fromStart) {
                    m.compare(lo, lo);
                }
                if (!toEnd) {
                    m.compare(hi, hi);
                }
            }
            this.m = m;
            this.fromStart = fromStart;
            this.lo = lo;
            this.loInclusive = loInclusive;
            this.toEnd = toEnd;
            this.hi = hi;
            this.hiInclusive = hiInclusive;
        }
        
        final boolean tooLow(final Object o) {
            if (!this.fromStart) {
                final int compare = this.m.compare(o, this.lo);
                if (compare < 0 || (compare == 0 && !this.loInclusive)) {
                    return true;
                }
            }
            return false;
        }
        
        final boolean tooHigh(final Object o) {
            if (!this.toEnd) {
                final int compare = this.m.compare(o, this.hi);
                if (compare > 0 || (compare == 0 && !this.hiInclusive)) {
                    return true;
                }
            }
            return false;
        }
        
        final boolean inRange(final Object o) {
            return !this.tooLow(o) && !this.tooHigh(o);
        }
        
        final boolean inClosedRange(final Object o) {
            return (this.fromStart || this.m.compare(o, this.lo) >= 0) && (this.toEnd || this.m.compare(this.hi, o) >= 0);
        }
        
        final boolean inRange(final Object o, final boolean b) {
            return b ? this.inRange(o) : this.inClosedRange(o);
        }
        
        final TreeMap.Entry<K, V> absLowest() {
            final TreeMap.Entry<K, V> entry = this.fromStart ? this.m.getFirstEntry() : (this.loInclusive ? this.m.getCeilingEntry(this.lo) : this.m.getHigherEntry(this.lo));
            return (entry == null || this.tooHigh(entry.key)) ? null : entry;
        }
        
        final TreeMap.Entry<K, V> absHighest() {
            final TreeMap.Entry<K, V> entry = this.toEnd ? this.m.getLastEntry() : (this.hiInclusive ? this.m.getFloorEntry(this.hi) : this.m.getLowerEntry(this.hi));
            return (entry == null || this.tooLow(entry.key)) ? null : entry;
        }
        
        final TreeMap.Entry<K, V> absCeiling(final K k) {
            if (this.tooLow(k)) {
                return this.absLowest();
            }
            final TreeMap.Entry<K, V> ceilingEntry = this.m.getCeilingEntry(k);
            return (ceilingEntry == null || this.tooHigh(ceilingEntry.key)) ? null : ceilingEntry;
        }
        
        final TreeMap.Entry<K, V> absHigher(final K k) {
            if (this.tooLow(k)) {
                return this.absLowest();
            }
            final TreeMap.Entry<K, V> higherEntry = this.m.getHigherEntry(k);
            return (higherEntry == null || this.tooHigh(higherEntry.key)) ? null : higherEntry;
        }
        
        final TreeMap.Entry<K, V> absFloor(final K k) {
            if (this.tooHigh(k)) {
                return this.absHighest();
            }
            final TreeMap.Entry<K, V> floorEntry = this.m.getFloorEntry(k);
            return (floorEntry == null || this.tooLow(floorEntry.key)) ? null : floorEntry;
        }
        
        final TreeMap.Entry<K, V> absLower(final K k) {
            if (this.tooHigh(k)) {
                return this.absHighest();
            }
            final TreeMap.Entry<K, V> lowerEntry = this.m.getLowerEntry(k);
            return (lowerEntry == null || this.tooLow(lowerEntry.key)) ? null : lowerEntry;
        }
        
        final TreeMap.Entry<K, V> absHighFence() {
            return this.toEnd ? null : (this.hiInclusive ? this.m.getHigherEntry(this.hi) : this.m.getCeilingEntry(this.hi));
        }
        
        final TreeMap.Entry<K, V> absLowFence() {
            return this.fromStart ? null : (this.loInclusive ? this.m.getLowerEntry(this.lo) : this.m.getFloorEntry(this.lo));
        }
        
        abstract TreeMap.Entry<K, V> subLowest();
        
        abstract TreeMap.Entry<K, V> subHighest();
        
        abstract TreeMap.Entry<K, V> subCeiling(final K p0);
        
        abstract TreeMap.Entry<K, V> subHigher(final K p0);
        
        abstract TreeMap.Entry<K, V> subFloor(final K p0);
        
        abstract TreeMap.Entry<K, V> subLower(final K p0);
        
        abstract Iterator<K> keyIterator();
        
        abstract Spliterator<K> keySpliterator();
        
        abstract Iterator<K> descendingKeyIterator();
        
        @Override
        public boolean isEmpty() {
            return (this.fromStart && this.toEnd) ? this.m.isEmpty() : this.entrySet().isEmpty();
        }
        
        @Override
        public int size() {
            return (this.fromStart && this.toEnd) ? this.m.size() : this.entrySet().size();
        }
        
        @Override
        public final boolean containsKey(final Object o) {
            return this.inRange(o) && this.m.containsKey(o);
        }
        
        @Override
        public final V put(final K k, final V v) {
            if (!this.inRange(k)) {
                throw new IllegalArgumentException("key out of range");
            }
            return this.m.put(k, v);
        }
        
        @Override
        public final V get(final Object o) {
            return this.inRange(o) ? this.m.get(o) : null;
        }
        
        @Override
        public final V remove(final Object o) {
            return this.inRange(o) ? this.m.remove(o) : null;
        }
        
        @Override
        public final Map.Entry<K, V> ceilingEntry(final K k) {
            return TreeMap.exportEntry((TreeMap.Entry<K, V>)this.subCeiling((K)k));
        }
        
        @Override
        public final K ceilingKey(final K k) {
            return TreeMap.keyOrNull((TreeMap.Entry<K, Object>)this.subCeiling((K)k));
        }
        
        @Override
        public final Map.Entry<K, V> higherEntry(final K k) {
            return TreeMap.exportEntry((TreeMap.Entry<K, V>)this.subHigher((K)k));
        }
        
        @Override
        public final K higherKey(final K k) {
            return TreeMap.keyOrNull((TreeMap.Entry<K, Object>)this.subHigher((K)k));
        }
        
        @Override
        public final Map.Entry<K, V> floorEntry(final K k) {
            return TreeMap.exportEntry((TreeMap.Entry<K, V>)this.subFloor((K)k));
        }
        
        @Override
        public final K floorKey(final K k) {
            return TreeMap.keyOrNull((TreeMap.Entry<K, Object>)this.subFloor((K)k));
        }
        
        @Override
        public final Map.Entry<K, V> lowerEntry(final K k) {
            return TreeMap.exportEntry((TreeMap.Entry<K, V>)this.subLower((K)k));
        }
        
        @Override
        public final K lowerKey(final K k) {
            return TreeMap.keyOrNull((TreeMap.Entry<K, Object>)this.subLower((K)k));
        }
        
        @Override
        public final K firstKey() {
            return TreeMap.key(this.subLowest());
        }
        
        @Override
        public final K lastKey() {
            return TreeMap.key(this.subHighest());
        }
        
        @Override
        public final Map.Entry<K, V> firstEntry() {
            return TreeMap.exportEntry(this.subLowest());
        }
        
        @Override
        public final Map.Entry<K, V> lastEntry() {
            return TreeMap.exportEntry(this.subHighest());
        }
        
        @Override
        public final Map.Entry<K, V> pollFirstEntry() {
            final TreeMap.Entry<K, V> subLowest = this.subLowest();
            final Map.Entry<K, V> exportEntry = TreeMap.exportEntry(subLowest);
            if (subLowest != null) {
                ((TreeMap<Object, Object>)this.m).deleteEntry(subLowest);
            }
            return exportEntry;
        }
        
        @Override
        public final Map.Entry<K, V> pollLastEntry() {
            final TreeMap.Entry<K, V> subHighest = this.subHighest();
            final Map.Entry<K, V> exportEntry = TreeMap.exportEntry(subHighest);
            if (subHighest != null) {
                ((TreeMap<Object, Object>)this.m).deleteEntry(subHighest);
            }
            return exportEntry;
        }
        
        @Override
        public final NavigableSet<K> navigableKeySet() {
            final KeySet<K> navigableKeySetView = this.navigableKeySetView;
            return (navigableKeySetView != null) ? navigableKeySetView : (this.navigableKeySetView = new KeySet<K>(this));
        }
        
        @Override
        public final Set<K> keySet() {
            return this.navigableKeySet();
        }
        
        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.descendingMap().navigableKeySet();
        }
        
        @Override
        public final SortedMap<K, V> subMap(final K k, final K i) {
            return (SortedMap<K, V>)this.subMap(k, true, i, false);
        }
        
        @Override
        public final SortedMap<K, V> headMap(final K k) {
            return (SortedMap<K, V>)this.headMap(k, false);
        }
        
        @Override
        public final SortedMap<K, V> tailMap(final K k) {
            return (SortedMap<K, V>)this.tailMap(k, true);
        }
        
        final class DescendingSubMapEntryIterator extends SubMapIterator<Map.Entry<K, V>>
        {
            DescendingSubMapEntryIterator(final TreeMap.Entry<K, V> entry, final TreeMap.Entry<K, V> entry2) {
                super(entry, entry2);
            }
            
            @Override
            public Map.Entry<K, V> next() {
                return this.prevEntry();
            }
            
            @Override
            public void remove() {
                this.removeDescending();
            }
        }
        
        abstract class SubMapIterator<T> implements Iterator<T>
        {
            TreeMap.Entry<K, V> lastReturned;
            TreeMap.Entry<K, V> next;
            final Object fenceKey;
            int expectedModCount;
            
            SubMapIterator(final TreeMap.Entry<K, V> next, final TreeMap.Entry<K, V> entry) {
                this.expectedModCount = ((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount;
                this.lastReturned = null;
                this.next = next;
                this.fenceKey = ((entry == null) ? TreeMap.UNBOUNDED : entry.key);
            }
            
            @Override
            public final boolean hasNext() {
                return this.next != null && this.next.key != this.fenceKey;
            }
            
            final TreeMap.Entry<K, V> nextEntry() {
                final TreeMap.Entry<K, V> next = this.next;
                if (next == null || next.key == this.fenceKey) {
                    throw new NoSuchElementException();
                }
                if (((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                this.next = TreeMap.successor(next);
                return this.lastReturned = next;
            }
            
            final TreeMap.Entry<K, V> prevEntry() {
                final TreeMap.Entry<K, V> next = this.next;
                if (next == null || next.key == this.fenceKey) {
                    throw new NoSuchElementException();
                }
                if (((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                this.next = TreeMap.predecessor(next);
                return this.lastReturned = next;
            }
            
            final void removeAscending() {
                if (this.lastReturned == null) {
                    throw new IllegalStateException();
                }
                if (((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                if (this.lastReturned.left != null && this.lastReturned.right != null) {
                    this.next = this.lastReturned;
                }
                ((TreeMap<Object, Object>)NavigableSubMap.this.m).deleteEntry(this.lastReturned);
                this.lastReturned = null;
                this.expectedModCount = ((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount;
            }
            
            final void removeDescending() {
                if (this.lastReturned == null) {
                    throw new IllegalStateException();
                }
                if (((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                ((TreeMap<Object, Object>)NavigableSubMap.this.m).deleteEntry(this.lastReturned);
                this.lastReturned = null;
                this.expectedModCount = ((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount;
            }
        }
        
        final class DescendingSubMapKeyIterator extends SubMapIterator<K> implements Spliterator<K>
        {
            DescendingSubMapKeyIterator(final TreeMap.Entry<K, V> entry, final TreeMap.Entry<K, V> entry2) {
                super(entry, entry2);
            }
            
            @Override
            public K next() {
                return this.prevEntry().key;
            }
            
            @Override
            public void remove() {
                this.removeDescending();
            }
            
            @Override
            public Spliterator<K> trySplit() {
                return null;
            }
            
            @Override
            public void forEachRemaining(final Consumer<? super K> consumer) {
                while (this.hasNext()) {
                    consumer.accept(this.next());
                }
            }
            
            @Override
            public boolean tryAdvance(final Consumer<? super K> consumer) {
                if (this.hasNext()) {
                    consumer.accept(this.next());
                    return true;
                }
                return false;
            }
            
            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }
            
            @Override
            public int characteristics() {
                return 17;
            }
        }
        
        abstract class EntrySetView extends AbstractSet<Map.Entry<K, V>>
        {
            private transient int size;
            private transient int sizeModCount;
            
            EntrySetView() {
                this.size = -1;
            }
            
            @Override
            public int size() {
                if (NavigableSubMap.this.fromStart && NavigableSubMap.this.toEnd) {
                    return NavigableSubMap.this.m.size();
                }
                if (this.size == -1 || this.sizeModCount != ((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount) {
                    this.sizeModCount = ((TreeMap<Object, Object>)NavigableSubMap.this.m).modCount;
                    this.size = 0;
                    final Iterator<Map.Entry<K, V>> iterator = this.iterator();
                    while (iterator.hasNext()) {
                        ++this.size;
                        iterator.next();
                    }
                }
                return this.size;
            }
            
            @Override
            public boolean isEmpty() {
                final TreeMap.Entry<K, V> absLowest = NavigableSubMap.this.absLowest();
                return absLowest == null || NavigableSubMap.this.tooHigh(absLowest.key);
            }
            
            @Override
            public boolean contains(final Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                final Map.Entry entry = (Map.Entry)o;
                final Object key = entry.getKey();
                if (!NavigableSubMap.this.inRange(key)) {
                    return false;
                }
                final TreeMap.Entry<K, V> entry2 = NavigableSubMap.this.m.getEntry(key);
                return entry2 != null && TreeMap.valEquals(entry2.getValue(), entry.getValue());
            }
            
            @Override
            public boolean remove(final Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                final Map.Entry entry = (Map.Entry)o;
                final Object key = entry.getKey();
                if (!NavigableSubMap.this.inRange(key)) {
                    return false;
                }
                final TreeMap.Entry<K, V> entry2 = NavigableSubMap.this.m.getEntry(key);
                if (entry2 != null && TreeMap.valEquals(entry2.getValue(), entry.getValue())) {
                    ((TreeMap<Object, Object>)NavigableSubMap.this.m).deleteEntry(entry2);
                    return true;
                }
                return false;
            }
        }
        
        final class SubMapEntryIterator extends SubMapIterator<Map.Entry<K, V>>
        {
            SubMapEntryIterator(final TreeMap.Entry<K, V> entry, final TreeMap.Entry<K, V> entry2) {
                super(entry, entry2);
            }
            
            @Override
            public Map.Entry<K, V> next() {
                return this.nextEntry();
            }
            
            @Override
            public void remove() {
                this.removeAscending();
            }
        }
        
        final class SubMapKeyIterator extends SubMapIterator<K> implements Spliterator<K>
        {
            SubMapKeyIterator(final TreeMap.Entry<K, V> entry, final TreeMap.Entry<K, V> entry2) {
                super(entry, entry2);
            }
            
            @Override
            public K next() {
                return this.nextEntry().key;
            }
            
            @Override
            public void remove() {
                this.removeAscending();
            }
            
            @Override
            public Spliterator<K> trySplit() {
                return null;
            }
            
            @Override
            public void forEachRemaining(final Consumer<? super K> consumer) {
                while (this.hasNext()) {
                    consumer.accept(this.next());
                }
            }
            
            @Override
            public boolean tryAdvance(final Consumer<? super K> consumer) {
                if (this.hasNext()) {
                    consumer.accept(this.next());
                    return true;
                }
                return false;
            }
            
            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }
            
            @Override
            public int characteristics() {
                return 21;
            }
            
            @Override
            public final Comparator<? super K> getComparator() {
                return NavigableSubMap.this.comparator();
            }
        }
    }
    
    static final class Entry<K, V> implements Map.Entry<K, V>
    {
        K key;
        V value;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;
        boolean color;
        
        Entry(final K key, final V value, final Entry<K, V> parent) {
            this.color = true;
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
        
        @Override
        public K getKey() {
            return this.key;
        }
        
        @Override
        public V getValue() {
            return this.value;
        }
        
        @Override
        public V setValue(final V value) {
            final V value2 = this.value;
            this.value = value;
            return value2;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            return TreeMap.valEquals(this.key, entry.getKey()) && TreeMap.valEquals(this.value, entry.getValue());
        }
        
        @Override
        public int hashCode() {
            return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
        }
        
        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
    
    static final class KeySet<E> extends AbstractSet<E> implements NavigableSet<E>
    {
        private final NavigableMap<E, ?> m;
        
        KeySet(final NavigableMap<E, ?> m) {
            this.m = m;
        }
        
        @Override
        public Iterator<E> iterator() {
            if (this.m instanceof TreeMap) {
                return ((TreeMap)this.m).keyIterator();
            }
            return ((NavigableSubMap)this.m).keyIterator();
        }
        
        @Override
        public Iterator<E> descendingIterator() {
            if (this.m instanceof TreeMap) {
                return ((TreeMap)this.m).descendingKeyIterator();
            }
            return ((NavigableSubMap)this.m).descendingKeyIterator();
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
        public E first() {
            return this.m.firstKey();
        }
        
        @Override
        public E last() {
            return this.m.lastKey();
        }
        
        @Override
        public Comparator<? super E> comparator() {
            return this.m.comparator();
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
        public boolean remove(final Object o) {
            final int size = this.size();
            this.m.remove(o);
            return this.size() != size;
        }
        
        @Override
        public NavigableSet<E> subSet(final E e, final boolean b, final E e2, final boolean b2) {
            return new KeySet((NavigableMap<Object, ?>)this.m.subMap(e, b, e2, b2));
        }
        
        @Override
        public NavigableSet<E> headSet(final E e, final boolean b) {
            return new KeySet((NavigableMap<Object, ?>)this.m.headMap(e, b));
        }
        
        @Override
        public NavigableSet<E> tailSet(final E e, final boolean b) {
            return new KeySet((NavigableMap<Object, ?>)this.m.tailMap(e, b));
        }
        
        @Override
        public SortedSet<E> subSet(final E e, final E e2) {
            return this.subSet(e, true, e2, false);
        }
        
        @Override
        public SortedSet<E> headSet(final E e) {
            return this.headSet(e, false);
        }
        
        @Override
        public SortedSet<E> tailSet(final E e) {
            return this.tailSet(e, true);
        }
        
        @Override
        public NavigableSet<E> descendingSet() {
            return new KeySet((NavigableMap<Object, ?>)this.m.descendingMap());
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return TreeMap.keySpliteratorFor(this.m);
        }
    }
    
    static final class DescendingSubMap<K, V> extends NavigableSubMap<K, V>
    {
        private static final long serialVersionUID = 912986545866120460L;
        private final Comparator<? super K> reverseComparator;
        
        DescendingSubMap(final TreeMap<K, V> treeMap, final boolean b, final K k, final boolean b2, final boolean b3, final K i, final boolean b4) {
            super(treeMap, b, k, b2, b3, i, b4);
            this.reverseComparator = Collections.reverseOrder((Comparator<? super K>)((TreeMap<Object, Object>)this.m).comparator);
        }
        
        @Override
        public Comparator<? super K> comparator() {
            return this.reverseComparator;
        }
        
        @Override
        public NavigableMap<K, V> subMap(final K k, final boolean b, final K i, final boolean b2) {
            if (!this.inRange(k, b)) {
                throw new IllegalArgumentException("fromKey out of range");
            }
            if (!this.inRange(i, b2)) {
                throw new IllegalArgumentException("toKey out of range");
            }
            return new DescendingSubMap((TreeMap<Object, Object>)this.m, false, i, b2, false, k, b);
        }
        
        @Override
        public NavigableMap<K, V> headMap(final K k, final boolean b) {
            if (!this.inRange(k, b)) {
                throw new IllegalArgumentException("toKey out of range");
            }
            return new DescendingSubMap((TreeMap<Object, Object>)this.m, false, k, b, this.toEnd, this.hi, this.hiInclusive);
        }
        
        @Override
        public NavigableMap<K, V> tailMap(final K k, final boolean b) {
            if (!this.inRange(k, b)) {
                throw new IllegalArgumentException("fromKey out of range");
            }
            return new DescendingSubMap((TreeMap<Object, Object>)this.m, this.fromStart, this.lo, this.loInclusive, false, k, b);
        }
        
        @Override
        public NavigableMap<K, V> descendingMap() {
            final NavigableMap<K, V> descendingMapView = this.descendingMapView;
            return (descendingMapView != null) ? descendingMapView : (this.descendingMapView = (NavigableMap<K, V>)new AscendingSubMap<Object, Object>((TreeMap<K, V>)this.m, this.fromStart, (K)this.lo, this.loInclusive, this.toEnd, (K)this.hi, this.hiInclusive));
        }
        
        @Override
        Iterator<K> keyIterator() {
            return (Iterator<K>)new DescendingSubMapKeyIterator((TreeMap.Entry<K, V>)this.absHighest(), (TreeMap.Entry<K, V>)this.absLowFence());
        }
        
        @Override
        Spliterator<K> keySpliterator() {
            return (Spliterator<K>)new DescendingSubMapKeyIterator((TreeMap.Entry<K, V>)this.absHighest(), (TreeMap.Entry<K, V>)this.absLowFence());
        }
        
        @Override
        Iterator<K> descendingKeyIterator() {
            return (Iterator<K>)new SubMapKeyIterator((TreeMap.Entry<K, V>)this.absLowest(), (TreeMap.Entry<K, V>)this.absHighFence());
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            final EntrySetView entrySetView = this.entrySetView;
            return (Set<Map.Entry<K, V>>)((entrySetView != null) ? entrySetView : (this.entrySetView = new DescendingEntrySetView()));
        }
        
        @Override
        TreeMap.Entry<K, V> subLowest() {
            return this.absHighest();
        }
        
        @Override
        TreeMap.Entry<K, V> subHighest() {
            return this.absLowest();
        }
        
        @Override
        TreeMap.Entry<K, V> subCeiling(final K k) {
            return this.absFloor(k);
        }
        
        @Override
        TreeMap.Entry<K, V> subHigher(final K k) {
            return this.absLower(k);
        }
        
        @Override
        TreeMap.Entry<K, V> subFloor(final K k) {
            return this.absCeiling(k);
        }
        
        @Override
        TreeMap.Entry<K, V> subLower(final K k) {
            return this.absHigher(k);
        }
        
        final class DescendingEntrySetView extends EntrySetView
        {
            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return (Iterator<Map.Entry<K, V>>)new DescendingSubMapEntryIterator((TreeMap.Entry<K, V>)DescendingSubMap.this.absHighest(), (TreeMap.Entry<K, V>)DescendingSubMap.this.absLowFence());
            }
        }
    }
    
    final class DescendingKeyIterator extends PrivateEntryIterator<K>
    {
        DescendingKeyIterator(final Entry<K, V> entry) {
            super(entry);
        }
        
        @Override
        public K next() {
            return this.prevEntry().key;
        }
        
        @Override
        public void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            if (TreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            TreeMap.this.deleteEntry(this.lastReturned);
            this.lastReturned = null;
            this.expectedModCount = TreeMap.this.modCount;
        }
    }
    
    abstract class PrivateEntryIterator<T> implements Iterator<T>
    {
        Entry<K, V> next;
        Entry<K, V> lastReturned;
        int expectedModCount;
        
        PrivateEntryIterator(final Entry<K, V> next) {
            this.expectedModCount = TreeMap.this.modCount;
            this.lastReturned = null;
            this.next = next;
        }
        
        @Override
        public final boolean hasNext() {
            return this.next != null;
        }
        
        final Entry<K, V> nextEntry() {
            final Entry<K, V> next = this.next;
            if (next == null) {
                throw new NoSuchElementException();
            }
            if (TreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = TreeMap.successor(next);
            return this.lastReturned = next;
        }
        
        final Entry<K, V> prevEntry() {
            final Entry<K, V> next = this.next;
            if (next == null) {
                throw new NoSuchElementException();
            }
            if (TreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = TreeMap.predecessor(next);
            return this.lastReturned = next;
        }
        
        @Override
        public void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            if (TreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (this.lastReturned.left != null && this.lastReturned.right != null) {
                this.next = this.lastReturned;
            }
            TreeMap.this.deleteEntry(this.lastReturned);
            this.expectedModCount = TreeMap.this.modCount;
            this.lastReturned = null;
        }
    }
    
    static final class DescendingKeySpliterator<K, V> extends TreeMapSpliterator<K, V> implements Spliterator<K>
    {
        DescendingKeySpliterator(final TreeMap<K, V> treeMap, final Entry<K, V> entry, final Entry<K, V> entry2, final int n, final int n2, final int n3) {
            super(treeMap, entry, entry2, n, n2, n3);
        }
        
        @Override
        public DescendingKeySpliterator<K, V> trySplit() {
            if (this.est < 0) {
                this.getEstimate();
            }
            final int side = this.side;
            final Entry<K, V> current = this.current;
            final Entry<K, V> fence = this.fence;
            final Entry<K, V> entry = (Entry<K, V>)((current == null || current == fence) ? null : ((side == 0) ? ((TreeMap<Object, Object>)this.tree).root : ((side < 0) ? current.left : ((side > 0 && fence != null) ? fence.right : null))));
            if (entry != null && entry != current && entry != fence && this.tree.compare(current.key, entry.key) > 0) {
                this.side = 1;
                final TreeMap<K, V> tree = this.tree;
                final Entry<K, V> entry2 = current;
                final Entry<K, V> current2 = entry;
                this.current = current2;
                return new DescendingKeySpliterator<K, V>((TreeMap<Object, Object>)tree, (Entry<Object, Object>)entry2, (Entry<Object, Object>)current2, -1, this.est >>>= 1, this.expectedModCount);
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.est < 0) {
                this.getEstimate();
            }
            final Entry<K, V> fence = this.fence;
            Entry<K, V> current;
            if ((current = this.current) != null && current != fence) {
                this.current = fence;
                Entry<K, V> entry;
                do {
                    consumer.accept(current.key);
                    if ((entry = current.left) != null) {
                        Entry<K, V> right;
                        while ((right = entry.right) != null) {
                            entry = right;
                        }
                    }
                    else {
                        while ((entry = current.parent) != null && current == entry.left) {
                            current = entry;
                        }
                    }
                } while ((current = entry) != null && current != fence);
                if (((TreeMap<Object, Object>)this.tree).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.est < 0) {
                this.getEstimate();
            }
            final Entry<K, V> current;
            if ((current = this.current) == null || current == this.fence) {
                return false;
            }
            this.current = (Entry<K, V>)TreeMap.predecessor((Entry<K, V>)current);
            consumer.accept(current.key);
            if (((TreeMap<Object, Object>)this.tree).modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return true;
        }
        
        @Override
        public int characteristics() {
            return ((this.side == 0) ? 64 : 0) | 0x1 | 0x10;
        }
    }
    
    static class TreeMapSpliterator<K, V>
    {
        final TreeMap<K, V> tree;
        Entry<K, V> current;
        Entry<K, V> fence;
        int side;
        int est;
        int expectedModCount;
        
        TreeMapSpliterator(final TreeMap<K, V> tree, final Entry<K, V> current, final Entry<K, V> fence, final int side, final int est, final int expectedModCount) {
            this.tree = tree;
            this.current = current;
            this.fence = fence;
            this.side = side;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }
        
        final int getEstimate() {
            int est;
            if ((est = this.est) < 0) {
                final TreeMap<K, V> tree;
                if ((tree = this.tree) != null) {
                    this.current = ((est == -1) ? tree.getFirstEntry() : tree.getLastEntry());
                    final int access$400 = ((TreeMap<Object, Object>)tree).size;
                    this.est = access$400;
                    est = access$400;
                    this.expectedModCount = ((TreeMap<Object, Object>)tree).modCount;
                }
                else {
                    final boolean est2 = false;
                    this.est = (est2 ? 1 : 0);
                    est = (est2 ? 1 : 0);
                }
            }
            return est;
        }
        
        public final long estimateSize() {
            return this.getEstimate();
        }
    }
    
    final class EntryIterator extends PrivateEntryIterator<Map.Entry<K, V>>
    {
        EntryIterator(final Entry<K, V> entry) {
            super(entry);
        }
        
        @Override
        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }
    
    class EntrySet extends AbstractSet<Map.Entry<K, V>>
    {
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(TreeMap.this.getFirstEntry());
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object value = entry.getValue();
            final Entry<K, V> entry2 = TreeMap.this.getEntry(entry.getKey());
            return entry2 != null && TreeMap.valEquals(entry2.getValue(), value);
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object value = entry.getValue();
            final Entry<K, V> entry2 = TreeMap.this.getEntry(entry.getKey());
            if (entry2 != null && TreeMap.valEquals(entry2.getValue(), value)) {
                TreeMap.this.deleteEntry(entry2);
                return true;
            }
            return false;
        }
        
        @Override
        public int size() {
            return TreeMap.this.size();
        }
        
        @Override
        public void clear() {
            TreeMap.this.clear();
        }
        
        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator<K, V>(TreeMap.this, null, null, 0, -1, 0);
        }
    }
    
    static final class EntrySpliterator<K, V> extends TreeMapSpliterator<K, V> implements Spliterator<Map.Entry<K, V>>
    {
        EntrySpliterator(final TreeMap<K, V> treeMap, final Entry<K, V> entry, final Entry<K, V> entry2, final int n, final int n2, final int n3) {
            super(treeMap, entry, entry2, n, n2, n3);
        }
        
        @Override
        public EntrySpliterator<K, V> trySplit() {
            if (this.est < 0) {
                this.getEstimate();
            }
            final int side = this.side;
            final Entry<K, V> current = this.current;
            final Entry<K, V> fence = this.fence;
            final Entry<K, V> entry = (Entry<K, V>)((current == null || current == fence) ? null : ((side == 0) ? ((TreeMap<Object, Object>)this.tree).root : ((side > 0) ? current.right : ((side < 0 && fence != null) ? fence.left : null))));
            if (entry != null && entry != current && entry != fence && this.tree.compare(current.key, entry.key) < 0) {
                this.side = 1;
                final TreeMap<K, V> tree = this.tree;
                final Entry<K, V> entry2 = current;
                final Entry<K, V> current2 = entry;
                this.current = current2;
                return new EntrySpliterator<K, V>((TreeMap<Object, Object>)tree, (Entry<Object, Object>)entry2, (Entry<Object, Object>)current2, -1, this.est >>>= 1, this.expectedModCount);
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.est < 0) {
                this.getEstimate();
            }
            final Entry<K, V> fence = this.fence;
            Entry<K, V> current;
            if ((current = this.current) != null && current != fence) {
                this.current = fence;
                Entry<K, V> entry;
                do {
                    consumer.accept(current);
                    if ((entry = current.right) != null) {
                        Entry<K, V> left;
                        while ((left = entry.left) != null) {
                            entry = left;
                        }
                    }
                    else {
                        while ((entry = current.parent) != null && current == entry.right) {
                            current = entry;
                        }
                    }
                } while ((current = entry) != null && current != fence);
                if (((TreeMap<Object, Object>)this.tree).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.est < 0) {
                this.getEstimate();
            }
            final Entry<K, V> current;
            if ((current = this.current) == null || current == this.fence) {
                return false;
            }
            this.current = (Entry<K, V>)TreeMap.successor((Entry<K, V>)current);
            consumer.accept(current);
            if (((TreeMap<Object, Object>)this.tree).modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return true;
        }
        
        @Override
        public int characteristics() {
            return ((this.side == 0) ? 64 : 0) | 0x1 | 0x4 | 0x10;
        }
        
        @Override
        public Comparator<Map.Entry<K, V>> getComparator() {
            if (((TreeMap<Object, Object>)this.tree).comparator != null) {
                return Map.Entry.comparingByKey((Comparator<? super K>)((TreeMap<Object, Object>)this.tree).comparator);
            }
            return (Comparator<Map.Entry<K, V>>)((entry, entry2) -> entry.getKey().compareTo(entry2.getKey()));
        }
    }
    
    final class KeyIterator extends PrivateEntryIterator<K>
    {
        KeyIterator(final Entry<K, V> entry) {
            super(entry);
        }
        
        @Override
        public K next() {
            return this.nextEntry().key;
        }
    }
    
    static final class KeySpliterator<K, V> extends TreeMapSpliterator<K, V> implements Spliterator<K>
    {
        KeySpliterator(final TreeMap<K, V> treeMap, final Entry<K, V> entry, final Entry<K, V> entry2, final int n, final int n2, final int n3) {
            super(treeMap, entry, entry2, n, n2, n3);
        }
        
        @Override
        public KeySpliterator<K, V> trySplit() {
            if (this.est < 0) {
                this.getEstimate();
            }
            final int side = this.side;
            final Entry<K, V> current = this.current;
            final Entry<K, V> fence = this.fence;
            final Entry<K, V> entry = (Entry<K, V>)((current == null || current == fence) ? null : ((side == 0) ? ((TreeMap<Object, Object>)this.tree).root : ((side > 0) ? current.right : ((side < 0 && fence != null) ? fence.left : null))));
            if (entry != null && entry != current && entry != fence && this.tree.compare(current.key, entry.key) < 0) {
                this.side = 1;
                final TreeMap<K, V> tree = this.tree;
                final Entry<K, V> entry2 = current;
                final Entry<K, V> current2 = entry;
                this.current = current2;
                return new KeySpliterator<K, V>((TreeMap<Object, Object>)tree, (Entry<Object, Object>)entry2, (Entry<Object, Object>)current2, -1, this.est >>>= 1, this.expectedModCount);
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.est < 0) {
                this.getEstimate();
            }
            final Entry<K, V> fence = this.fence;
            Entry<K, V> current;
            if ((current = this.current) != null && current != fence) {
                this.current = fence;
                Entry<K, V> entry;
                do {
                    consumer.accept(current.key);
                    if ((entry = current.right) != null) {
                        Entry<K, V> left;
                        while ((left = entry.left) != null) {
                            entry = left;
                        }
                    }
                    else {
                        while ((entry = current.parent) != null && current == entry.right) {
                            current = entry;
                        }
                    }
                } while ((current = entry) != null && current != fence);
                if (((TreeMap<Object, Object>)this.tree).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.est < 0) {
                this.getEstimate();
            }
            final Entry<K, V> current;
            if ((current = this.current) == null || current == this.fence) {
                return false;
            }
            this.current = (Entry<K, V>)TreeMap.successor((Entry<K, V>)current);
            consumer.accept(current.key);
            if (((TreeMap<Object, Object>)this.tree).modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return true;
        }
        
        @Override
        public int characteristics() {
            return ((this.side == 0) ? 64 : 0) | 0x1 | 0x4 | 0x10;
        }
        
        @Override
        public final Comparator<? super K> getComparator() {
            return ((TreeMap<Object, Object>)this.tree).comparator;
        }
    }
    
    private class SubMap extends AbstractMap<K, V> implements SortedMap<K, V>, Serializable
    {
        private static final long serialVersionUID = -6520786458950516097L;
        private boolean fromStart;
        private boolean toEnd;
        private K fromKey;
        private K toKey;
        
        private SubMap() {
            this.fromStart = false;
            this.toEnd = false;
        }
        
        private Object readResolve() {
            return new AscendingSubMap(TreeMap.this, this.fromStart, this.fromKey, true, this.toEnd, this.toKey, false);
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            throw new InternalError();
        }
        
        @Override
        public K lastKey() {
            throw new InternalError();
        }
        
        @Override
        public K firstKey() {
            throw new InternalError();
        }
        
        @Override
        public SortedMap<K, V> subMap(final K k, final K i) {
            throw new InternalError();
        }
        
        @Override
        public SortedMap<K, V> headMap(final K k) {
            throw new InternalError();
        }
        
        @Override
        public SortedMap<K, V> tailMap(final K k) {
            throw new InternalError();
        }
        
        @Override
        public Comparator<? super K> comparator() {
            throw new InternalError();
        }
    }
    
    final class ValueIterator extends PrivateEntryIterator<V>
    {
        ValueIterator(final Entry<K, V> entry) {
            super(entry);
        }
        
        @Override
        public V next() {
            return this.nextEntry().value;
        }
    }
    
    static final class ValueSpliterator<K, V> extends TreeMapSpliterator<K, V> implements Spliterator<V>
    {
        ValueSpliterator(final TreeMap<K, V> treeMap, final Entry<K, V> entry, final Entry<K, V> entry2, final int n, final int n2, final int n3) {
            super(treeMap, entry, entry2, n, n2, n3);
        }
        
        @Override
        public ValueSpliterator<K, V> trySplit() {
            if (this.est < 0) {
                this.getEstimate();
            }
            final int side = this.side;
            final Entry<K, V> current = this.current;
            final Entry<K, V> fence = this.fence;
            final Entry<K, V> entry = (Entry<K, V>)((current == null || current == fence) ? null : ((side == 0) ? ((TreeMap<Object, Object>)this.tree).root : ((side > 0) ? current.right : ((side < 0 && fence != null) ? fence.left : null))));
            if (entry != null && entry != current && entry != fence && this.tree.compare(current.key, entry.key) < 0) {
                this.side = 1;
                final TreeMap<K, V> tree = this.tree;
                final Entry<K, V> entry2 = current;
                final Entry<K, V> current2 = entry;
                this.current = current2;
                return new ValueSpliterator<K, V>((TreeMap<Object, Object>)tree, (Entry<Object, Object>)entry2, (Entry<Object, Object>)current2, -1, this.est >>>= 1, this.expectedModCount);
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.est < 0) {
                this.getEstimate();
            }
            final Entry<K, V> fence = this.fence;
            Entry<K, V> current;
            if ((current = this.current) != null && current != fence) {
                this.current = fence;
                Entry<K, V> entry;
                do {
                    consumer.accept(current.value);
                    if ((entry = current.right) != null) {
                        Entry<K, V> left;
                        while ((left = entry.left) != null) {
                            entry = left;
                        }
                    }
                    else {
                        while ((entry = current.parent) != null && current == entry.right) {
                            current = entry;
                        }
                    }
                } while ((current = entry) != null && current != fence);
                if (((TreeMap<Object, Object>)this.tree).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            if (this.est < 0) {
                this.getEstimate();
            }
            final Entry<K, V> current;
            if ((current = this.current) == null || current == this.fence) {
                return false;
            }
            this.current = (Entry<K, V>)TreeMap.successor((Entry<K, V>)current);
            consumer.accept(current.value);
            if (((TreeMap<Object, Object>)this.tree).modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return true;
        }
        
        @Override
        public int characteristics() {
            return ((this.side == 0) ? 64 : 0) | 0x10;
        }
    }
    
    class Values extends AbstractCollection<V>
    {
        @Override
        public Iterator<V> iterator() {
            return new ValueIterator(TreeMap.this.getFirstEntry());
        }
        
        @Override
        public int size() {
            return TreeMap.this.size();
        }
        
        @Override
        public boolean contains(final Object o) {
            return TreeMap.this.containsValue(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            for (Map.Entry<K, V> entry = (Map.Entry<K, V>)TreeMap.this.getFirstEntry(); entry != null; entry = (Map.Entry<K, V>)TreeMap.successor((Entry<K, Object>)entry)) {
                if (TreeMap.valEquals(((Entry<K, Object>)entry).getValue(), o)) {
                    TreeMap.this.deleteEntry((Entry)entry);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public void clear() {
            TreeMap.this.clear();
        }
        
        @Override
        public Spliterator<V> spliterator() {
            return new ValueSpliterator<Object, V>(TreeMap.this, null, null, 0, -1, 0);
        }
    }
}
