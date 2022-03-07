package java.util;

import java.lang.ref.*;
import java.util.function.*;

public class WeakHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>
{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;
    private final ReferenceQueue<Object> queue;
    int modCount;
    private static final Object NULL_KEY;
    private transient Set<Map.Entry<K, V>> entrySet;
    
    private Entry<K, V>[] newTable(final int n) {
        return (Entry<K, V>[])new Entry[n];
    }
    
    public WeakHashMap(int n, final float loadFactor) {
        this.queue = new ReferenceQueue<Object>();
        if (n < 0) {
            throw new IllegalArgumentException("Illegal Initial Capacity: " + n);
        }
        if (n > 1073741824) {
            n = 1073741824;
        }
        if (loadFactor <= 0.0f || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal Load factor: " + loadFactor);
        }
        int i;
        for (i = 1; i < n; i <<= 1) {}
        this.table = this.newTable(i);
        this.loadFactor = loadFactor;
        this.threshold = (int)(i * loadFactor);
    }
    
    public WeakHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public WeakHashMap() {
        this(16, 0.75f);
    }
    
    public WeakHashMap(final Map<? extends K, ? extends V> map) {
        this(Math.max((int)(map.size() / 0.75f) + 1, 16), 0.75f);
        this.putAll(map);
    }
    
    private static Object maskNull(final Object o) {
        return (o == null) ? WeakHashMap.NULL_KEY : o;
    }
    
    static Object unmaskNull(final Object o) {
        return (o == WeakHashMap.NULL_KEY) ? null : o;
    }
    
    private static boolean eq(final Object o, final Object o2) {
        return o == o2 || o.equals(o2);
    }
    
    final int hash(final Object o) {
        final int hashCode = o.hashCode();
        final int n = hashCode ^ (hashCode >>> 20 ^ hashCode >>> 12);
        return n ^ n >>> 7 ^ n >>> 4;
    }
    
    private static int indexFor(final int n, final int n2) {
        return n & n2 - 1;
    }
    
    private void expungeStaleEntries() {
        Reference<?> poll;
        while ((poll = this.queue.poll()) != null) {
            synchronized (this.queue) {
                final Entry<?, ?> entry = (Entry<?, ?>)poll;
                final int index = indexFor(entry.hash, this.table.length);
                Entry<K, V> entry3;
                Entry<K, V> next;
                for (Entry<K, V> entry2 = entry3 = this.table[index]; entry3 != null; entry3 = next) {
                    next = entry3.next;
                    if (entry3 == entry) {
                        if (entry2 == entry) {
                            this.table[index] = next;
                        }
                        else {
                            entry2.next = next;
                        }
                        entry.value = null;
                        --this.size;
                        break;
                    }
                    entry2 = entry3;
                }
            }
        }
    }
    
    private Entry<K, V>[] getTable() {
        this.expungeStaleEntries();
        return this.table;
    }
    
    @Override
    public int size() {
        if (this.size == 0) {
            return 0;
        }
        this.expungeStaleEntries();
        return this.size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public V get(final Object o) {
        final Object maskNull = maskNull(o);
        final int hash = this.hash(maskNull);
        final Entry<K, V>[] table = this.getTable();
        for (Entry<?, ?> next = table[indexFor(hash, table.length)]; next != null; next = next.next) {
            if (next.hash == hash && eq(maskNull, next.get())) {
                return (V)next.value;
            }
        }
        return null;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.getEntry(o) != null;
    }
    
    Entry<K, V> getEntry(final Object o) {
        final Object maskNull = maskNull(o);
        final int hash = this.hash(maskNull);
        final Entry<K, V>[] table = this.getTable();
        Entry<?, ?> next;
        for (next = table[indexFor(hash, table.length)]; next != null && (next.hash != hash || !eq(maskNull, next.get())); next = next.next) {}
        return (Entry<K, V>)next;
    }
    
    @Override
    public V put(final K k, final V value) {
        final Object maskNull = maskNull(k);
        final int hash = this.hash(maskNull);
        final Entry<K, V>[] table = this.getTable();
        final int index = indexFor(hash, table.length);
        for (Entry<?, ?> next = table[index]; next != null; next = next.next) {
            if (hash == next.hash && eq(maskNull, next.get())) {
                final Object value2 = next.value;
                if (value != value2) {
                    next.value = value;
                }
                return (V)value2;
            }
        }
        ++this.modCount;
        table[index] = new Entry<K, V>(maskNull, value, this.queue, hash, (Entry<Object, Object>)table[index]);
        if (++this.size >= this.threshold) {
            this.resize(table.length * 2);
        }
        return null;
    }
    
    void resize(final int n) {
        final Entry<K, V>[] table = this.getTable();
        if (table.length == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        final Entry<K, V>[] table2 = this.newTable(n);
        this.transfer(table, table2);
        this.table = table2;
        if (this.size >= this.threshold / 2) {
            this.threshold = (int)(n * this.loadFactor);
        }
        else {
            this.expungeStaleEntries();
            this.transfer(table2, table);
            this.table = table;
        }
    }
    
    private void transfer(final Entry<K, V>[] array, final Entry<K, V>[] array2) {
        for (int i = 0; i < array.length; ++i) {
            Entry<K, V> entry = array[i];
            array[i] = null;
            while (entry != null) {
                final Entry<K, V> next = entry.next;
                if (entry.get() == null) {
                    entry.next = null;
                    entry.value = null;
                    --this.size;
                }
                else {
                    final int index = indexFor(entry.hash, array2.length);
                    entry.next = array2[index];
                    array2[index] = entry;
                }
                entry = next;
            }
        }
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        final int size = map.size();
        if (size == 0) {
            return;
        }
        if (size > this.threshold) {
            int n = (int)(size / this.loadFactor + 1.0f);
            if (n > 1073741824) {
                n = 1073741824;
            }
            int i;
            for (i = this.table.length; i < n; i <<= 1) {}
            if (i > this.table.length) {
                this.resize(i);
            }
        }
        for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), (V)entry.getValue());
        }
    }
    
    @Override
    public V remove(final Object o) {
        final Object maskNull = maskNull(o);
        final int hash = this.hash(maskNull);
        final Entry<K, V>[] table = this.getTable();
        final int index = indexFor(hash, table.length);
        Entry<K, V> entry2;
        Entry<K, V> next;
        for (Entry<K, V> entry = entry2 = table[index]; entry2 != null; entry2 = next) {
            next = entry2.next;
            if (hash == entry2.hash && eq(maskNull, entry2.get())) {
                ++this.modCount;
                --this.size;
                if (entry == entry2) {
                    table[index] = next;
                }
                else {
                    entry.next = next;
                }
                return entry2.value;
            }
            entry = entry2;
        }
        return null;
    }
    
    boolean removeMapping(final Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        final Entry<K, V>[] table = this.getTable();
        final Map.Entry entry = (Map.Entry)o;
        final int hash = this.hash(maskNull(entry.getKey()));
        final int index = indexFor(hash, table.length);
        Entry<K, V> entry3;
        Entry<K, V> next;
        for (Entry<K, V> entry2 = entry3 = table[index]; entry3 != null; entry3 = next) {
            next = entry3.next;
            if (hash == entry3.hash && entry3.equals(entry)) {
                ++this.modCount;
                --this.size;
                if (entry2 == entry3) {
                    table[index] = next;
                }
                else {
                    entry2.next = next;
                }
                return true;
            }
            entry2 = entry3;
        }
        return false;
    }
    
    @Override
    public void clear() {
        while (this.queue.poll() != null) {}
        ++this.modCount;
        Arrays.fill(this.table, null);
        this.size = 0;
        while (this.queue.poll() != null) {}
    }
    
    @Override
    public boolean containsValue(final Object o) {
        if (o == null) {
            return this.containsNullValue();
        }
        final Entry<K, V>[] table = this.getTable();
        int length = table.length;
        while (length-- > 0) {
            for (Entry<K, V> next = table[length]; next != null; next = next.next) {
                if (o.equals(next.value)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean containsNullValue() {
        final Entry<K, V>[] table = this.getTable();
        int length = table.length;
        while (length-- > 0) {
            for (Entry<K, V> next = table[length]; next != null; next = next.next) {
                if (next.value == null) {
                    return true;
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
    public Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> entrySet = this.entrySet;
        return (entrySet != null) ? entrySet : (this.entrySet = new EntrySet());
    }
    
    @Override
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        final int modCount = this.modCount;
        for (Entry<?, ?> next : this.getTable()) {
            while (next != null) {
                final Object value = next.get();
                if (value != null) {
                    biConsumer.accept((Object)unmaskNull(value), (Object)next.value);
                }
                next = next.next;
                if (modCount != this.modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
    
    @Override
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        final int modCount = this.modCount;
        for (Entry<?, ?> next : this.getTable()) {
            while (next != null) {
                final Object value = next.get();
                if (value != null) {
                    next.value = biFunction.apply((Object)unmaskNull(value), (Object)next.value);
                }
                next = next.next;
                if (modCount != this.modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
    
    static {
        NULL_KEY = new Object();
    }
    
    private static class Entry<K, V> extends WeakReference<Object> implements Map.Entry<K, V>
    {
        V value;
        final int hash;
        Entry<K, V> next;
        
        Entry(final Object o, final V value, final ReferenceQueue<Object> referenceQueue, final int hash, final Entry<K, V> next) {
            super(o, referenceQueue);
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
        
        @Override
        public K getKey() {
            return (K)WeakHashMap.unmaskNull(this.get());
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
            final Object key = this.getKey();
            final Object key2 = entry.getKey();
            if (key == key2 || (key != null && key.equals(key2))) {
                final Object value = this.getValue();
                final V value2 = entry.getValue();
                if (value == value2 || (value != null && value.equals(value2))) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.getKey()) ^ Objects.hashCode(this.getValue());
        }
        
        @Override
        public String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }
    
    private class EntryIterator extends HashIterator<Map.Entry<K, V>>
    {
        @Override
        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }
    
    private abstract class HashIterator<T> implements Iterator<T>
    {
        private int index;
        private Entry<K, V> entry;
        private Entry<K, V> lastReturned;
        private int expectedModCount;
        private Object nextKey;
        private Object currentKey;
        
        HashIterator() {
            this.expectedModCount = WeakHashMap.this.modCount;
            this.index = (WeakHashMap.this.isEmpty() ? 0 : WeakHashMap.this.table.length);
        }
        
        @Override
        public boolean hasNext() {
            final Entry<K, V>[] table = WeakHashMap.this.table;
            while (this.nextKey == null) {
                Entry<K, V> entry;
                int index;
                for (entry = this.entry, index = this.index; entry == null && index > 0; entry = table[--index]) {}
                this.entry = entry;
                this.index = index;
                if (entry == null) {
                    this.currentKey = null;
                    return false;
                }
                this.nextKey = entry.get();
                if (this.nextKey != null) {
                    continue;
                }
                this.entry = this.entry.next;
            }
            return true;
        }
        
        protected Entry<K, V> nextEntry() {
            if (WeakHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (this.nextKey == null && !this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.entry;
            this.entry = this.entry.next;
            this.currentKey = this.nextKey;
            this.nextKey = null;
            return this.lastReturned;
        }
        
        @Override
        public void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            if (WeakHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            WeakHashMap.this.remove(this.currentKey);
            this.expectedModCount = WeakHashMap.this.modCount;
            this.lastReturned = null;
            this.currentKey = null;
        }
    }
    
    private class EntrySet extends AbstractSet<Map.Entry<K, V>>
    {
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Entry<K, V> entry2 = WeakHashMap.this.getEntry(entry.getKey());
            return entry2 != null && entry2.equals(entry);
        }
        
        @Override
        public boolean remove(final Object o) {
            return WeakHashMap.this.removeMapping(o);
        }
        
        @Override
        public int size() {
            return WeakHashMap.this.size();
        }
        
        @Override
        public void clear() {
            WeakHashMap.this.clear();
        }
        
        private List<Map.Entry<K, V>> deepCopy() {
            final ArrayList<SimpleEntry> list = (ArrayList<SimpleEntry>)new ArrayList<SimpleEntry<K, V>>(this.size());
            final Iterator<Map.Entry<K, V>> iterator = this.iterator();
            while (iterator.hasNext()) {
                list.add(new SimpleEntry<K, V>(iterator.next()));
            }
            return (List<Map.Entry<K, V>>)list;
        }
        
        @Override
        public Object[] toArray() {
            return this.deepCopy().toArray();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            return this.deepCopy().toArray(array);
        }
        
        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator<K, V>(WeakHashMap.this, 0, -1, 0, 0);
        }
    }
    
    static final class EntrySpliterator<K, V> extends WeakHashMapSpliterator<K, V> implements Spliterator<Map.Entry<K, V>>
    {
        EntrySpliterator(final WeakHashMap<K, V> weakHashMap, final int n, final int n2, final int n3, final int n4) {
            super(weakHashMap, n, n2, n3, n4);
        }
        
        @Override
        public EntrySpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1;
            EntrySpliterator<K, V> entrySpliterator;
            if (index >= n) {
                entrySpliterator = null;
            }
            else {
                final WeakHashMap<K, V> map;
                final int n2;
                final int index2;
                entrySpliterator = new EntrySpliterator<K, V>((WeakHashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
                map = this.map;
                n2 = index;
                index2 = n;
                this.index = index2;
            }
            return entrySpliterator;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final WeakHashMap<K, V> map = this.map;
            final Entry<K, V>[] table = map.table;
            int fence;
            int expectedModCount;
            if ((fence = this.fence) < 0) {
                final int modCount = map.modCount;
                this.expectedModCount = modCount;
                expectedModCount = modCount;
                final int length = table.length;
                this.fence = length;
                fence = length;
            }
            else {
                expectedModCount = this.expectedModCount;
            }
            int index;
            if (table.length >= fence && (index = this.index) >= 0) {
                final int n = index;
                final int index2 = fence;
                this.index = index2;
                if (n < index2 || this.current != null) {
                    Entry<?, ?> entry = this.current;
                    this.current = null;
                    do {
                        if (entry == null) {
                            entry = table[index++];
                        }
                        else {
                            final Object value = entry.get();
                            final Object value2 = entry.value;
                            entry = entry.next;
                            if (value == null) {
                                continue;
                            }
                            consumer.accept((Object)new SimpleImmutableEntry(WeakHashMap.unmaskNull(value), value2));
                        }
                    } while (entry != null || index < fence);
                }
            }
            if (map.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Entry<K, V>[] table = this.map.table;
            final int fence;
            if (table.length >= (fence = this.getFence()) && this.index >= 0) {
                while (this.current != null || this.index < fence) {
                    if (this.current == null) {
                        this.current = table[this.index++];
                    }
                    else {
                        final Object value = this.current.get();
                        final V value2 = this.current.value;
                        this.current = this.current.next;
                        if (value == null) {
                            continue;
                        }
                        consumer.accept((Object)new SimpleImmutableEntry(WeakHashMap.unmaskNull(value), value2));
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
            return 1;
        }
    }
    
    static class WeakHashMapSpliterator<K, V>
    {
        final WeakHashMap<K, V> map;
        Entry<K, V> current;
        int index;
        int fence;
        int est;
        int expectedModCount;
        
        WeakHashMapSpliterator(final WeakHashMap<K, V> map, final int index, final int fence, final int est, final int expectedModCount) {
            this.map = map;
            this.index = index;
            this.fence = fence;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }
        
        final int getFence() {
            int fence;
            if ((fence = this.fence) < 0) {
                final WeakHashMap<K, V> map = this.map;
                this.est = map.size();
                this.expectedModCount = map.modCount;
                final int length = map.table.length;
                this.fence = length;
                fence = length;
            }
            return fence;
        }
        
        public final long estimateSize() {
            this.getFence();
            return this.est;
        }
    }
    
    private class KeyIterator extends HashIterator<K>
    {
        @Override
        public K next() {
            return this.nextEntry().getKey();
        }
    }
    
    private class KeySet extends AbstractSet<K>
    {
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
        
        @Override
        public int size() {
            return WeakHashMap.this.size();
        }
        
        @Override
        public boolean contains(final Object o) {
            return WeakHashMap.this.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            if (WeakHashMap.this.containsKey(o)) {
                WeakHashMap.this.remove(o);
                return true;
            }
            return false;
        }
        
        @Override
        public void clear() {
            WeakHashMap.this.clear();
        }
        
        @Override
        public Spliterator<K> spliterator() {
            return new KeySpliterator<K, Object>(WeakHashMap.this, 0, -1, 0, 0);
        }
    }
    
    static final class KeySpliterator<K, V> extends WeakHashMapSpliterator<K, V> implements Spliterator<K>
    {
        KeySpliterator(final WeakHashMap<K, V> weakHashMap, final int n, final int n2, final int n3, final int n4) {
            super(weakHashMap, n, n2, n3, n4);
        }
        
        @Override
        public KeySpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1;
            KeySpliterator<K, V> keySpliterator;
            if (index >= n) {
                keySpliterator = null;
            }
            else {
                final WeakHashMap<K, V> map;
                final int n2;
                final int index2;
                keySpliterator = new KeySpliterator<K, V>((WeakHashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
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
            final WeakHashMap<K, V> map = this.map;
            final Entry<K, V>[] table = map.table;
            int fence;
            int expectedModCount;
            if ((fence = this.fence) < 0) {
                final int modCount = map.modCount;
                this.expectedModCount = modCount;
                expectedModCount = modCount;
                final int length = table.length;
                this.fence = length;
                fence = length;
            }
            else {
                expectedModCount = this.expectedModCount;
            }
            int index;
            if (table.length >= fence && (index = this.index) >= 0) {
                final int n = index;
                final int index2 = fence;
                this.index = index2;
                if (n < index2 || this.current != null) {
                    Entry<?, ?> entry = this.current;
                    this.current = null;
                    do {
                        if (entry == null) {
                            entry = table[index++];
                        }
                        else {
                            final Object value = entry.get();
                            entry = entry.next;
                            if (value == null) {
                                continue;
                            }
                            consumer.accept((Object)WeakHashMap.unmaskNull(value));
                        }
                    } while (entry != null || index < fence);
                }
            }
            if (map.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Entry<K, V>[] table = this.map.table;
            final int fence;
            if (table.length >= (fence = this.getFence()) && this.index >= 0) {
                while (this.current != null || this.index < fence) {
                    if (this.current == null) {
                        this.current = table[this.index++];
                    }
                    else {
                        final Object value = this.current.get();
                        this.current = this.current.next;
                        if (value == null) {
                            continue;
                        }
                        consumer.accept((Object)WeakHashMap.unmaskNull(value));
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
            return 1;
        }
    }
    
    private class ValueIterator extends HashIterator<V>
    {
        @Override
        public V next() {
            return this.nextEntry().value;
        }
    }
    
    static final class ValueSpliterator<K, V> extends WeakHashMapSpliterator<K, V> implements Spliterator<V>
    {
        ValueSpliterator(final WeakHashMap<K, V> weakHashMap, final int n, final int n2, final int n3, final int n4) {
            super(weakHashMap, n, n2, n3, n4);
        }
        
        @Override
        public ValueSpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1;
            ValueSpliterator<K, V> valueSpliterator;
            if (index >= n) {
                valueSpliterator = null;
            }
            else {
                final WeakHashMap<K, V> map;
                final int n2;
                final int index2;
                valueSpliterator = new ValueSpliterator<K, V>((WeakHashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
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
            final WeakHashMap<K, V> map = this.map;
            final Entry<K, V>[] table = map.table;
            int fence;
            int expectedModCount;
            if ((fence = this.fence) < 0) {
                final int modCount = map.modCount;
                this.expectedModCount = modCount;
                expectedModCount = modCount;
                final int length = table.length;
                this.fence = length;
                fence = length;
            }
            else {
                expectedModCount = this.expectedModCount;
            }
            int index;
            if (table.length >= fence && (index = this.index) >= 0) {
                final int n = index;
                final int index2 = fence;
                this.index = index2;
                if (n < index2 || this.current != null) {
                    Entry<?, ?> entry = this.current;
                    this.current = null;
                    do {
                        if (entry == null) {
                            entry = table[index++];
                        }
                        else {
                            final Object value = entry.get();
                            final Object value2 = entry.value;
                            entry = entry.next;
                            if (value == null) {
                                continue;
                            }
                            consumer.accept((Object)value2);
                        }
                    } while (entry != null || index < fence);
                }
            }
            if (map.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Entry<K, V>[] table = this.map.table;
            final int fence;
            if (table.length >= (fence = this.getFence()) && this.index >= 0) {
                while (this.current != null || this.index < fence) {
                    if (this.current == null) {
                        this.current = table[this.index++];
                    }
                    else {
                        final Object value = this.current.get();
                        final V value2 = this.current.value;
                        this.current = this.current.next;
                        if (value == null) {
                            continue;
                        }
                        consumer.accept(value2);
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
            return 0;
        }
    }
    
    private class Values extends AbstractCollection<V>
    {
        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
        
        @Override
        public int size() {
            return WeakHashMap.this.size();
        }
        
        @Override
        public boolean contains(final Object o) {
            return WeakHashMap.this.containsValue(o);
        }
        
        @Override
        public void clear() {
            WeakHashMap.this.clear();
        }
        
        @Override
        public Spliterator<V> spliterator() {
            return new ValueSpliterator<Object, V>(WeakHashMap.this, 0, -1, 0, 0);
        }
    }
}
