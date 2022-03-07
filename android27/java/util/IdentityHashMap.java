package java.util;

import java.io.*;
import sun.misc.*;
import java.lang.reflect.*;
import java.util.function.*;

public class IdentityHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Serializable, Cloneable
{
    private static final int DEFAULT_CAPACITY = 32;
    private static final int MINIMUM_CAPACITY = 4;
    private static final int MAXIMUM_CAPACITY = 536870912;
    transient Object[] table;
    int size;
    transient int modCount;
    static final Object NULL_KEY;
    private transient Set<Entry<K, V>> entrySet;
    private static final long serialVersionUID = 8188218128353913216L;
    
    private static Object maskNull(final Object o) {
        return (o == null) ? IdentityHashMap.NULL_KEY : o;
    }
    
    static final Object unmaskNull(final Object o) {
        return (o == IdentityHashMap.NULL_KEY) ? null : o;
    }
    
    public IdentityHashMap() {
        this.init(32);
    }
    
    public IdentityHashMap(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("expectedMaxSize is negative: " + n);
        }
        this.init(capacity(n));
    }
    
    private static int capacity(final int n) {
        return (n > 178956970) ? 536870912 : ((n <= 2) ? 4 : Integer.highestOneBit(n + (n << 1)));
    }
    
    private void init(final int n) {
        this.table = new Object[2 * n];
    }
    
    public IdentityHashMap(final Map<? extends K, ? extends V> map) {
        this((int)((1 + map.size()) * 1.1));
        this.putAll(map);
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    private static int hash(final Object o, final int n) {
        final int identityHashCode = System.identityHashCode(o);
        return (identityHashCode << 1) - (identityHashCode << 8) & n - 1;
    }
    
    private static int nextKeyIndex(final int n, final int n2) {
        return (n + 2 < n2) ? (n + 2) : 0;
    }
    
    @Override
    public V get(final Object o) {
        final Object maskNull = maskNull(o);
        final Object[] table = this.table;
        final int length = table.length;
        int n = hash(maskNull, length);
        while (true) {
            final Object o2 = table[n];
            if (o2 == maskNull) {
                return (V)table[n + 1];
            }
            if (o2 == null) {
                return null;
            }
            n = nextKeyIndex(n, length);
        }
    }
    
    @Override
    public boolean containsKey(final Object o) {
        final Object maskNull = maskNull(o);
        final Object[] table = this.table;
        final int length = table.length;
        int n = hash(maskNull, length);
        while (true) {
            final Object o2 = table[n];
            if (o2 == maskNull) {
                return true;
            }
            if (o2 == null) {
                return false;
            }
            n = nextKeyIndex(n, length);
        }
    }
    
    @Override
    public boolean containsValue(final Object o) {
        final Object[] table = this.table;
        for (int i = 1; i < table.length; i += 2) {
            if (table[i] == o && table[i - 1] != null) {
                return true;
            }
        }
        return false;
    }
    
    private boolean containsMapping(final Object o, final Object o2) {
        final Object maskNull = maskNull(o);
        final Object[] table = this.table;
        final int length = table.length;
        int n = hash(maskNull, length);
        while (true) {
            final Object o3 = table[n];
            if (o3 == maskNull) {
                return table[n + 1] == o2;
            }
            if (o3 == null) {
                return false;
            }
            n = nextKeyIndex(n, length);
        }
    }
    
    @Override
    public V put(final K k, final V v) {
        final Object maskNull = maskNull(k);
        while (true) {
            Object[] table;
            int length;
            int n;
            Object o;
            Object o2;
            for (table = this.table, length = table.length, n = hash(maskNull, length); (o = table[n]) != null; n = nextKeyIndex(n, length)) {
                if (o == maskNull) {
                    o2 = table[n + 1];
                    table[n + 1] = v;
                    return (V)o2;
                }
            }
            final int size = this.size + 1;
            if (size + (size << 1) > length && this.resize(length)) {
                continue;
            }
            ++this.modCount;
            table[n] = maskNull;
            table[n + 1] = v;
            this.size = size;
            return null;
        }
    }
    
    private boolean resize(final int n) {
        final int n2 = n * 2;
        final Object[] table = this.table;
        final int length = table.length;
        if (length == 1073741824) {
            if (this.size == 536870911) {
                throw new IllegalStateException("Capacity exhausted.");
            }
            return false;
        }
        else {
            if (length >= n2) {
                return false;
            }
            final Object[] table2 = new Object[n2];
            for (int i = 0; i < length; i += 2) {
                final Object o = table[i];
                if (o != null) {
                    final Object o2 = table[i + 1];
                    table[i + 1] = (table[i] = null);
                    int n3;
                    for (n3 = hash(o, n2); table2[n3] != null; n3 = nextKeyIndex(n3, n2)) {}
                    table2[n3] = o;
                    table2[n3 + 1] = o2;
                }
            }
            this.table = table2;
            return true;
        }
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        final int size = map.size();
        if (size == 0) {
            return;
        }
        if (size > this.size) {
            this.resize(capacity(size));
        }
        for (final Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), (V)entry.getValue());
        }
    }
    
    @Override
    public V remove(final Object o) {
        final Object maskNull = maskNull(o);
        final Object[] table = this.table;
        final int length = table.length;
        int n = hash(maskNull, length);
        while (true) {
            final Object o2 = table[n];
            if (o2 == maskNull) {
                ++this.modCount;
                --this.size;
                final Object o3 = table[n + 1];
                table[n] = (table[n + 1] = null);
                this.closeDeletion(n);
                return (V)o3;
            }
            if (o2 == null) {
                return null;
            }
            n = nextKeyIndex(n, length);
        }
    }
    
    private boolean removeMapping(final Object o, final Object o2) {
        final Object maskNull = maskNull(o);
        final Object[] table = this.table;
        final int length = table.length;
        int n = hash(maskNull, length);
        while (true) {
            final Object o3 = table[n];
            if (o3 == maskNull) {
                if (table[n + 1] != o2) {
                    return false;
                }
                ++this.modCount;
                --this.size;
                table[n + 1] = (table[n] = null);
                this.closeDeletion(n);
                return true;
            }
            else {
                if (o3 == null) {
                    return false;
                }
                n = nextKeyIndex(n, length);
            }
        }
    }
    
    private void closeDeletion(int n) {
        final Object[] table = this.table;
        Object o;
        for (int length = table.length, n2 = nextKeyIndex(n, length); (o = table[n2]) != null; n2 = nextKeyIndex(n2, length)) {
            final int hash = hash(o, length);
            if ((n2 < hash && (hash <= n || n <= n2)) || (hash <= n && n <= n2)) {
                table[n] = o;
                table[n + 1] = table[n2 + 1];
                table[n2 + 1] = (table[n2] = null);
                n = n2;
            }
        }
    }
    
    @Override
    public void clear() {
        ++this.modCount;
        final Object[] table = this.table;
        for (int i = 0; i < table.length; ++i) {
            table[i] = null;
        }
        this.size = 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IdentityHashMap)) {
            return o instanceof Map && this.entrySet().equals(((Map)o).entrySet());
        }
        final IdentityHashMap identityHashMap = (IdentityHashMap)o;
        if (identityHashMap.size() != this.size) {
            return false;
        }
        final Object[] table = identityHashMap.table;
        for (int i = 0; i < table.length; i += 2) {
            final Object o2 = table[i];
            if (o2 != null && !this.containsMapping(o2, table[i + 1])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int n = 0;
        final Object[] table = this.table;
        for (int i = 0; i < table.length; i += 2) {
            final Object o = table[i];
            if (o != null) {
                n += (System.identityHashCode(unmaskNull(o)) ^ System.identityHashCode(table[i + 1]));
            }
        }
        return n;
    }
    
    public Object clone() {
        try {
            final IdentityHashMap identityHashMap = (IdentityHashMap)super.clone();
            identityHashMap.entrySet = null;
            identityHashMap.table = this.table.clone();
            return identityHashMap;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
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
        final Set<Entry<K, V>> entrySet = this.entrySet;
        if (entrySet != null) {
            return entrySet;
        }
        return this.entrySet = new EntrySet();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        final Object[] table = this.table;
        for (int i = 0; i < table.length; i += 2) {
            final Object o = table[i];
            if (o != null) {
                objectOutputStream.writeObject(unmaskNull(o));
                objectOutputStream.writeObject(table[i + 1]);
            }
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int int1 = objectInputStream.readInt();
        if (int1 < 0) {
            throw new StreamCorruptedException("Illegal mappings count: " + int1);
        }
        final int capacity = capacity(int1);
        SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Object[].class, capacity);
        this.init(capacity);
        for (int i = 0; i < int1; ++i) {
            this.putForCreate(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }
    
    private void putForCreate(final K k, final V v) throws StreamCorruptedException {
        final Object maskNull = maskNull(k);
        Object[] table;
        int length;
        int n;
        Object o;
        for (table = this.table, length = table.length, n = hash(maskNull, length); (o = table[n]) != null; n = nextKeyIndex(n, length)) {
            if (o == maskNull) {
                throw new StreamCorruptedException();
            }
        }
        table[n] = maskNull;
        table[n + 1] = v;
    }
    
    @Override
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        final int modCount = this.modCount;
        final Object[] table = this.table;
        for (int i = 0; i < table.length; i += 2) {
            final Object o = table[i];
            if (o != null) {
                biConsumer.accept((Object)unmaskNull(o), (Object)table[i + 1]);
            }
            if (this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    @Override
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        final int modCount = this.modCount;
        final Object[] table = this.table;
        for (int i = 0; i < table.length; i += 2) {
            final Object o = table[i];
            if (o != null) {
                table[i + 1] = biFunction.apply((Object)unmaskNull(o), (Object)table[i + 1]);
            }
            if (this.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    static {
        NULL_KEY = new Object();
    }
    
    private class EntryIterator extends IdentityHashMapIterator<Map.Entry<K, V>>
    {
        private Entry lastReturnedEntry;
        
        @Override
        public Map.Entry<K, V> next() {
            return this.lastReturnedEntry = new Entry(this.nextIndex());
        }
        
        @Override
        public void remove() {
            this.lastReturnedIndex = ((null == this.lastReturnedEntry) ? -1 : this.lastReturnedEntry.index);
            super.remove();
            this.lastReturnedEntry.index = this.lastReturnedIndex;
            this.lastReturnedEntry = null;
        }
        
        private class Entry implements Map.Entry<K, V>
        {
            private int index;
            
            private Entry(final int index) {
                this.index = index;
            }
            
            @Override
            public K getKey() {
                this.checkIndexForEntryUse();
                return (K)IdentityHashMap.unmaskNull(EntryIterator.this.traversalTable[this.index]);
            }
            
            @Override
            public V getValue() {
                this.checkIndexForEntryUse();
                return (V)EntryIterator.this.traversalTable[this.index + 1];
            }
            
            @Override
            public V setValue(final V v) {
                this.checkIndexForEntryUse();
                final Object o = EntryIterator.this.traversalTable[this.index + 1];
                EntryIterator.this.traversalTable[this.index + 1] = v;
                if (EntryIterator.this.traversalTable != IdentityHashMap.this.table) {
                    IdentityHashMap.this.put(EntryIterator.this.traversalTable[this.index], v);
                }
                return (V)o;
            }
            
            @Override
            public boolean equals(final Object o) {
                if (this.index < 0) {
                    return super.equals(o);
                }
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                final Map.Entry entry = (Map.Entry)o;
                return entry.getKey() == IdentityHashMap.unmaskNull(EntryIterator.this.traversalTable[this.index]) && entry.getValue() == EntryIterator.this.traversalTable[this.index + 1];
            }
            
            @Override
            public int hashCode() {
                if (EntryIterator.this.lastReturnedIndex < 0) {
                    return super.hashCode();
                }
                return System.identityHashCode(IdentityHashMap.unmaskNull(EntryIterator.this.traversalTable[this.index])) ^ System.identityHashCode(EntryIterator.this.traversalTable[this.index + 1]);
            }
            
            @Override
            public String toString() {
                if (this.index < 0) {
                    return super.toString();
                }
                return IdentityHashMap.unmaskNull(EntryIterator.this.traversalTable[this.index]) + "=" + EntryIterator.this.traversalTable[this.index + 1];
            }
            
            private void checkIndexForEntryUse() {
                if (this.index < 0) {
                    throw new IllegalStateException("Entry was removed");
                }
            }
        }
    }
    
    private abstract class IdentityHashMapIterator<T> implements Iterator<T>
    {
        int index;
        int expectedModCount;
        int lastReturnedIndex;
        boolean indexValid;
        Object[] traversalTable;
        
        private IdentityHashMapIterator() {
            this.index = ((IdentityHashMap.this.size != 0) ? 0 : IdentityHashMap.this.table.length);
            this.expectedModCount = IdentityHashMap.this.modCount;
            this.lastReturnedIndex = -1;
            this.traversalTable = IdentityHashMap.this.table;
        }
        
        @Override
        public boolean hasNext() {
            final Object[] traversalTable = this.traversalTable;
            for (int i = this.index; i < traversalTable.length; i += 2) {
                if (traversalTable[i] != null) {
                    this.index = i;
                    return this.indexValid = true;
                }
            }
            this.index = traversalTable.length;
            return false;
        }
        
        protected int nextIndex() {
            if (IdentityHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!this.indexValid && !this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.indexValid = false;
            this.lastReturnedIndex = this.index;
            this.index += 2;
            return this.lastReturnedIndex;
        }
        
        @Override
        public void remove() {
            if (this.lastReturnedIndex == -1) {
                throw new IllegalStateException();
            }
            if (IdentityHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.expectedModCount = ++IdentityHashMap.this.modCount;
            final int lastReturnedIndex = this.lastReturnedIndex;
            this.lastReturnedIndex = -1;
            this.index = lastReturnedIndex;
            this.indexValid = false;
            final Object[] traversalTable = this.traversalTable;
            final int length = traversalTable.length;
            int n = lastReturnedIndex;
            final Object o = traversalTable[n];
            traversalTable[n + 1] = (traversalTable[n] = null);
            if (traversalTable != IdentityHashMap.this.table) {
                IdentityHashMap.this.remove(o);
                this.expectedModCount = IdentityHashMap.this.modCount;
                return;
            }
            final IdentityHashMap this$0 = IdentityHashMap.this;
            --this$0.size;
            Object o2;
            for (int n2 = nextKeyIndex(n, length); (o2 = traversalTable[n2]) != null; n2 = nextKeyIndex(n2, length)) {
                final int access$100 = hash(o2, length);
                if ((n2 < access$100 && (access$100 <= n || n <= n2)) || (access$100 <= n && n <= n2)) {
                    if (n2 < lastReturnedIndex && n >= lastReturnedIndex && this.traversalTable == IdentityHashMap.this.table) {
                        final int n3 = length - lastReturnedIndex;
                        final Object[] traversalTable2 = new Object[n3];
                        System.arraycopy(traversalTable, lastReturnedIndex, traversalTable2, 0, n3);
                        this.traversalTable = traversalTable2;
                        this.index = 0;
                    }
                    traversalTable[n] = o2;
                    traversalTable[n + 1] = traversalTable[n2 + 1];
                    traversalTable[n2 + 1] = (traversalTable[n2] = null);
                    n = n2;
                }
            }
        }
    }
    
    private class EntrySet extends AbstractSet<Entry<K, V>>
    {
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry entry = (Entry)o;
            return IdentityHashMap.this.containsMapping(entry.getKey(), entry.getValue());
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry entry = (Entry)o;
            return IdentityHashMap.this.removeMapping(entry.getKey(), entry.getValue());
        }
        
        @Override
        public int size() {
            return IdentityHashMap.this.size;
        }
        
        @Override
        public void clear() {
            IdentityHashMap.this.clear();
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            Objects.requireNonNull(collection);
            boolean b = false;
            final Iterator<Entry<K, V>> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (collection.contains(iterator.next())) {
                    iterator.remove();
                    b = true;
                }
            }
            return b;
        }
        
        @Override
        public Object[] toArray() {
            return this.toArray(new Object[0]);
        }
        
        @Override
        public <T> T[] toArray(T[] array) {
            final int modCount = IdentityHashMap.this.modCount;
            final int size = this.size();
            if (array.length < size) {
                array = (T[])Array.newInstance(array.getClass().getComponentType(), size);
            }
            final Object[] table = IdentityHashMap.this.table;
            int n = 0;
            for (int i = 0; i < table.length; i += 2) {
                final Object o;
                if ((o = table[i]) != null) {
                    if (n >= size) {
                        throw new ConcurrentModificationException();
                    }
                    array[n++] = (T)new SimpleEntry(IdentityHashMap.unmaskNull(o), table[i + 1]);
                }
            }
            if (n < size || modCount != IdentityHashMap.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (n < array.length) {
                array[n] = null;
            }
            return array;
        }
        
        @Override
        public Spliterator<Entry<K, V>> spliterator() {
            return new EntrySpliterator<K, V>(IdentityHashMap.this, 0, -1, 0, 0);
        }
    }
    
    static final class EntrySpliterator<K, V> extends IdentityHashMapSpliterator<K, V> implements Spliterator<Entry<K, V>>
    {
        EntrySpliterator(final IdentityHashMap<K, V> identityHashMap, final int n, final int n2, final int n3, final int n4) {
            super(identityHashMap, n, n2, n3, n4);
        }
        
        @Override
        public EntrySpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1 & 0xFFFFFFFE;
            EntrySpliterator<K, V> entrySpliterator;
            if (index >= n) {
                entrySpliterator = null;
            }
            else {
                final IdentityHashMap<K, V> map;
                final int n2;
                final int index2;
                entrySpliterator = new EntrySpliterator<K, V>((IdentityHashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
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
            final IdentityHashMap<K, V> map;
            final Object[] table;
            int i;
            final int fence;
            if ((map = this.map) != null && (table = map.table) != null && (i = this.index) >= 0 && (this.index = (fence = this.getFence())) <= table.length) {
                while (i < fence) {
                    final Object o = table[i];
                    if (o != null) {
                        consumer.accept((Object)new SimpleImmutableEntry(IdentityHashMap.unmaskNull(o), table[i + 1]));
                    }
                    i += 2;
                }
                if (map.modCount == this.expectedModCount) {
                    return;
                }
            }
            throw new ConcurrentModificationException();
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super Entry<K, V>> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Object[] table = this.map.table;
            while (this.index < this.getFence()) {
                final Object o = table[this.index];
                final Object o2 = table[this.index + 1];
                this.index += 2;
                if (o != null) {
                    consumer.accept((Object)new SimpleImmutableEntry(IdentityHashMap.unmaskNull(o), o2));
                    if (this.map.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int characteristics() {
            return ((this.fence < 0 || this.est == this.map.size) ? 64 : 0) | 0x1;
        }
    }
    
    static class IdentityHashMapSpliterator<K, V>
    {
        final IdentityHashMap<K, V> map;
        int index;
        int fence;
        int est;
        int expectedModCount;
        
        IdentityHashMapSpliterator(final IdentityHashMap<K, V> map, final int index, final int fence, final int est, final int expectedModCount) {
            this.map = map;
            this.index = index;
            this.fence = fence;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }
        
        final int getFence() {
            int fence;
            if ((fence = this.fence) < 0) {
                this.est = this.map.size;
                this.expectedModCount = this.map.modCount;
                final int length = this.map.table.length;
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
    
    private class KeyIterator extends IdentityHashMapIterator<K>
    {
        @Override
        public K next() {
            return (K)IdentityHashMap.unmaskNull(this.traversalTable[this.nextIndex()]);
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
            return IdentityHashMap.this.size;
        }
        
        @Override
        public boolean contains(final Object o) {
            return IdentityHashMap.this.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            final int size = IdentityHashMap.this.size;
            IdentityHashMap.this.remove(o);
            return IdentityHashMap.this.size != size;
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            Objects.requireNonNull(collection);
            boolean b = false;
            final Iterator<K> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (collection.contains(iterator.next())) {
                    iterator.remove();
                    b = true;
                }
            }
            return b;
        }
        
        @Override
        public void clear() {
            IdentityHashMap.this.clear();
        }
        
        @Override
        public int hashCode() {
            int n = 0;
            final Iterator<Object> iterator = this.iterator();
            while (iterator.hasNext()) {
                n += System.identityHashCode(iterator.next());
            }
            return n;
        }
        
        @Override
        public Object[] toArray() {
            return this.toArray(new Object[0]);
        }
        
        @Override
        public <T> T[] toArray(T[] array) {
            final int modCount = IdentityHashMap.this.modCount;
            final int size = this.size();
            if (array.length < size) {
                array = (T[])Array.newInstance(array.getClass().getComponentType(), size);
            }
            final Object[] table = IdentityHashMap.this.table;
            int n = 0;
            for (int i = 0; i < table.length; i += 2) {
                final Object o;
                if ((o = table[i]) != null) {
                    if (n >= size) {
                        throw new ConcurrentModificationException();
                    }
                    array[n++] = (T)IdentityHashMap.unmaskNull(o);
                }
            }
            if (n < size || modCount != IdentityHashMap.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (n < array.length) {
                array[n] = null;
            }
            return array;
        }
        
        @Override
        public Spliterator<K> spliterator() {
            return new KeySpliterator<K, Object>(IdentityHashMap.this, 0, -1, 0, 0);
        }
    }
    
    static final class KeySpliterator<K, V> extends IdentityHashMapSpliterator<K, V> implements Spliterator<K>
    {
        KeySpliterator(final IdentityHashMap<K, V> identityHashMap, final int n, final int n2, final int n3, final int n4) {
            super(identityHashMap, n, n2, n3, n4);
        }
        
        @Override
        public KeySpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1 & 0xFFFFFFFE;
            KeySpliterator<K, V> keySpliterator;
            if (index >= n) {
                keySpliterator = null;
            }
            else {
                final IdentityHashMap<K, V> map;
                final int n2;
                final int index2;
                keySpliterator = new KeySpliterator<K, V>((IdentityHashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
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
            final IdentityHashMap<K, V> map;
            final Object[] table;
            int i;
            final int fence;
            if ((map = this.map) != null && (table = map.table) != null && (i = this.index) >= 0 && (this.index = (fence = this.getFence())) <= table.length) {
                while (i < fence) {
                    final Object o;
                    if ((o = table[i]) != null) {
                        consumer.accept((Object)IdentityHashMap.unmaskNull(o));
                    }
                    i += 2;
                }
                if (map.modCount == this.expectedModCount) {
                    return;
                }
            }
            throw new ConcurrentModificationException();
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super K> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Object[] table = this.map.table;
            while (this.index < this.getFence()) {
                final Object o = table[this.index];
                this.index += 2;
                if (o != null) {
                    consumer.accept((Object)IdentityHashMap.unmaskNull(o));
                    if (this.map.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int characteristics() {
            return ((this.fence < 0 || this.est == this.map.size) ? 64 : 0) | 0x1;
        }
    }
    
    private class ValueIterator extends IdentityHashMapIterator<V>
    {
        @Override
        public V next() {
            return (V)this.traversalTable[this.nextIndex() + 1];
        }
    }
    
    static final class ValueSpliterator<K, V> extends IdentityHashMapSpliterator<K, V> implements Spliterator<V>
    {
        ValueSpliterator(final IdentityHashMap<K, V> identityHashMap, final int n, final int n2, final int n3, final int n4) {
            super(identityHashMap, n, n2, n3, n4);
        }
        
        @Override
        public ValueSpliterator<K, V> trySplit() {
            final int fence = this.getFence();
            final int index = this.index;
            final int n = index + fence >>> 1 & 0xFFFFFFFE;
            ValueSpliterator<K, V> valueSpliterator;
            if (index >= n) {
                valueSpliterator = null;
            }
            else {
                final IdentityHashMap<K, V> map;
                final int n2;
                final int index2;
                valueSpliterator = new ValueSpliterator<K, V>((IdentityHashMap<Object, Object>)map, n2, index2, this.est >>>= 1, this.expectedModCount);
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
            final IdentityHashMap<K, V> map;
            final Object[] table;
            int i;
            final int fence;
            if ((map = this.map) != null && (table = map.table) != null && (i = this.index) >= 0 && (this.index = (fence = this.getFence())) <= table.length) {
                while (i < fence) {
                    if (table[i] != null) {
                        consumer.accept((Object)table[i + 1]);
                    }
                    i += 2;
                }
                if (map.modCount == this.expectedModCount) {
                    return;
                }
            }
            throw new ConcurrentModificationException();
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super V> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Object[] table = this.map.table;
            while (this.index < this.getFence()) {
                final Object o = table[this.index];
                final Object o2 = table[this.index + 1];
                this.index += 2;
                if (o != null) {
                    consumer.accept((Object)o2);
                    if (this.map.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int characteristics() {
            return (this.fence < 0 || this.est == this.map.size) ? 64 : 0;
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
            return IdentityHashMap.this.size;
        }
        
        @Override
        public boolean contains(final Object o) {
            return IdentityHashMap.this.containsValue(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            final Iterator<V> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() == o) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public void clear() {
            IdentityHashMap.this.clear();
        }
        
        @Override
        public Object[] toArray() {
            return this.toArray(new Object[0]);
        }
        
        @Override
        public <T> T[] toArray(T[] array) {
            final int modCount = IdentityHashMap.this.modCount;
            final int size = this.size();
            if (array.length < size) {
                array = (T[])Array.newInstance(array.getClass().getComponentType(), size);
            }
            final Object[] table = IdentityHashMap.this.table;
            int n = 0;
            for (int i = 0; i < table.length; i += 2) {
                if (table[i] != null) {
                    if (n >= size) {
                        throw new ConcurrentModificationException();
                    }
                    array[n++] = (T)table[i + 1];
                }
            }
            if (n < size || modCount != IdentityHashMap.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (n < array.length) {
                array[n] = null;
            }
            return array;
        }
        
        @Override
        public Spliterator<V> spliterator() {
            return new ValueSpliterator<Object, V>(IdentityHashMap.this, 0, -1, 0, 0);
        }
    }
}
