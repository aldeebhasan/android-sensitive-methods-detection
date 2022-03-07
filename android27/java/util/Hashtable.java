package java.util;

import java.util.function.*;
import java.io.*;
import sun.misc.*;

public class Hashtable<K, V> extends Dictionary<K, V> implements Map<K, V>, Cloneable, Serializable
{
    private transient Entry<?, ?>[] table;
    private transient int count;
    private int threshold;
    private float loadFactor;
    private transient int modCount;
    private static final long serialVersionUID = 1421746759512286392L;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private transient volatile Set<K> keySet;
    private transient volatile Set<Map.Entry<K, V>> entrySet;
    private transient volatile Collection<V> values;
    private static final int KEYS = 0;
    private static final int VALUES = 1;
    private static final int ENTRIES = 2;
    
    public Hashtable(int n, final float loadFactor) {
        this.modCount = 0;
        if (n < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + n);
        }
        if (loadFactor <= 0.0f || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal Load: " + loadFactor);
        }
        if (n == 0) {
            n = 1;
        }
        this.loadFactor = loadFactor;
        this.table = (Entry<?, ?>[])new Entry[n];
        this.threshold = (int)Math.min(n * loadFactor, 2.14748365E9f);
    }
    
    public Hashtable(final int n) {
        this(n, 0.75f);
    }
    
    public Hashtable() {
        this(11, 0.75f);
    }
    
    public Hashtable(final Map<? extends K, ? extends V> map) {
        this(Math.max(2 * map.size(), 11), 0.75f);
        this.putAll(map);
    }
    
    @Override
    public synchronized int size() {
        return this.count;
    }
    
    @Override
    public synchronized boolean isEmpty() {
        return this.count == 0;
    }
    
    @Override
    public synchronized Enumeration<K> keys() {
        return this.getEnumeration(0);
    }
    
    @Override
    public synchronized Enumeration<V> elements() {
        return this.getEnumeration(1);
    }
    
    public synchronized boolean contains(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        final Entry<?, ?>[] table = this.table;
        int length = table.length;
        while (length-- > 0) {
            for (Entry<?, ?> next = table[length]; next != null; next = next.next) {
                if (next.value.equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return this.contains(o);
    }
    
    @Override
    public synchronized boolean containsKey(final Object o) {
        final Entry<?, ?>[] table = this.table;
        final int hashCode = o.hashCode();
        for (Entry<?, ?> next = table[(hashCode & Integer.MAX_VALUE) % table.length]; next != null; next = next.next) {
            if (next.hash == hashCode && next.key.equals(o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public synchronized V get(final Object o) {
        final Entry<?, ?>[] table = this.table;
        final int hashCode = o.hashCode();
        for (Entry<?, ?> next = table[(hashCode & Integer.MAX_VALUE) % table.length]; next != null; next = next.next) {
            if (next.hash == hashCode && next.key.equals(o)) {
                return (V)next.value;
            }
        }
        return null;
    }
    
    protected void rehash() {
        final int length = this.table.length;
        final Entry<?, ?>[] table = this.table;
        int n = (length << 1) + 1;
        if (n - 2147483639 > 0) {
            if (length == 2147483639) {
                return;
            }
            n = 2147483639;
        }
        final Entry[] table2 = new Entry[n];
        ++this.modCount;
        this.threshold = (int)Math.min(n * this.loadFactor, 2.14748365E9f);
        this.table = (Entry<?, ?>[])table2;
        int n2 = length;
        while (n2-- > 0) {
            Entry<?, ?> entry;
            int n3;
            for (Entry<?, ?> next = table[n2]; next != null; next = next.next, n3 = (entry.hash & Integer.MAX_VALUE) % n, entry.next = (Entry<?, ?>)table2[n3], table2[n3] = entry) {
                entry = next;
            }
        }
    }
    
    private void addEntry(int hashCode, final K k, final V v, int n) {
        ++this.modCount;
        Entry<?, ?>[] array = this.table;
        if (this.count >= this.threshold) {
            this.rehash();
            array = this.table;
            hashCode = k.hashCode();
            n = (hashCode & Integer.MAX_VALUE) % array.length;
        }
        array[n] = new Entry<Object, Object>(hashCode, k, v, (Entry<Object, Object>)array[n]);
        ++this.count;
    }
    
    @Override
    public synchronized V put(final K k, final V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        final Entry<?, ?>[] table = this.table;
        final int hashCode = k.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % table.length;
        for (Entry<?, ?> next = table[n]; next != null; next = next.next) {
            if (next.hash == hashCode && next.key.equals(k)) {
                final Object value2 = next.value;
                next.value = value;
                return (V)value2;
            }
        }
        this.addEntry(hashCode, k, value, n);
        return null;
    }
    
    @Override
    public synchronized V remove(final Object o) {
        final Entry<?, ?>[] table = this.table;
        final int hashCode = o.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % table.length;
        Entry<?, ?> next = table[n];
        Entry<?, ?> entry = null;
        while (next != null) {
            if (next.hash == hashCode && next.key.equals(o)) {
                ++this.modCount;
                if (entry != null) {
                    entry.next = next.next;
                }
                else {
                    table[n] = next.next;
                }
                --this.count;
                final Object value = next.value;
                next.value = null;
                return (V)value;
            }
            entry = next;
            next = next.next;
        }
        return null;
    }
    
    @Override
    public synchronized void putAll(final Map<? extends K, ? extends V> map) {
        for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), (V)entry.getValue());
        }
    }
    
    @Override
    public synchronized void clear() {
        final Entry<?, ?>[] table = this.table;
        ++this.modCount;
        int length = table.length;
        while (--length >= 0) {
            table[length] = null;
        }
        this.count = 0;
    }
    
    public synchronized Object clone() {
        try {
            final Hashtable hashtable = (Hashtable)super.clone();
            hashtable.table = (Entry<?, ?>[])new Entry[this.table.length];
            int length = this.table.length;
            while (length-- > 0) {
                hashtable.table[length] = (Entry<?, ?>)((this.table[length] != null) ? ((Entry)this.table[length].clone()) : null);
            }
            hashtable.keySet = null;
            hashtable.entrySet = null;
            hashtable.values = null;
            hashtable.modCount = 0;
            return hashtable;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    @Override
    public synchronized String toString() {
        final int n = this.size() - 1;
        if (n == -1) {
            return "{}";
        }
        final StringBuilder sb = new StringBuilder();
        final Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
        sb.append('{');
        int n2 = 0;
        while (true) {
            final Map.Entry<K, V> entry = iterator.next();
            final K key = entry.getKey();
            final V value = entry.getValue();
            sb.append((key == this) ? "(this Map)" : key.toString());
            sb.append('=');
            sb.append((value == this) ? "(this Map)" : value.toString());
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append('}').toString();
    }
    
    private <T> Enumeration<T> getEnumeration(final int n) {
        if (this.count == 0) {
            return Collections.emptyEnumeration();
        }
        return new Enumerator<T>(n, false);
    }
    
    private <T> Iterator<T> getIterator(final int n) {
        if (this.count == 0) {
            return Collections.emptyIterator();
        }
        return new Enumerator<T>(n, true);
    }
    
    @Override
    public Set<K> keySet() {
        if (this.keySet == null) {
            this.keySet = Collections.synchronizedSet((Set<K>)new KeySet(), this);
        }
        return this.keySet;
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.entrySet == null) {
            this.entrySet = Collections.synchronizedSet((Set<Map.Entry<K, V>>)new EntrySet(), this);
        }
        return this.entrySet;
    }
    
    @Override
    public Collection<V> values() {
        if (this.values == null) {
            this.values = Collections.synchronizedCollection((Collection<V>)new ValueCollection(), this);
        }
        return this.values;
    }
    
    @Override
    public synchronized boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        final Map map = (Map)o;
        if (map.size() != this.size()) {
            return false;
        }
        try {
            for (final Map.Entry<K, V> entry : this.entrySet()) {
                final K key = entry.getKey();
                final V value = entry.getValue();
                if (value == null) {
                    if (map.get(key) != null || !map.containsKey(key)) {
                        return false;
                    }
                    continue;
                }
                else {
                    if (!value.equals(map.get(key))) {
                        return false;
                    }
                    continue;
                }
            }
        }
        catch (ClassCastException ex) {
            return false;
        }
        catch (NullPointerException ex2) {
            return false;
        }
        return true;
    }
    
    @Override
    public synchronized int hashCode() {
        int n = 0;
        if (this.count == 0 || this.loadFactor < 0.0f) {
            return n;
        }
        this.loadFactor = -this.loadFactor;
        for (Entry<?, ?> next : this.table) {
            while (next != null) {
                n += next.hashCode();
                next = next.next;
            }
        }
        this.loadFactor = -this.loadFactor;
        return n;
    }
    
    @Override
    public synchronized V getOrDefault(final Object o, final V v) {
        final V value = this.get(o);
        return (null == value) ? v : value;
    }
    
    @Override
    public synchronized void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        final int modCount = this.modCount;
        for (Entry<?, ?> next : this.table) {
            while (next != null) {
                biConsumer.accept((Object)next.key, (Object)next.value);
                next = next.next;
                if (modCount != this.modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
    
    @Override
    public synchronized void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        final int modCount = this.modCount;
        for (Entry<?, ?> next : this.table) {
            while (next != null) {
                next.value = Objects.requireNonNull((V)biFunction.apply((Object)next.key, (Object)next.value));
                next = next.next;
                if (modCount != this.modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
    
    @Override
    public synchronized V putIfAbsent(final K k, final V value) {
        Objects.requireNonNull(value);
        final Entry<?, ?>[] table = this.table;
        final int hashCode = k.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % table.length;
        for (Entry<?, ?> next = table[n]; next != null; next = next.next) {
            if (next.hash == hashCode && next.key.equals(k)) {
                final Object value2 = next.value;
                if (value2 == null) {
                    next.value = value;
                }
                return (V)value2;
            }
        }
        this.addEntry(hashCode, k, value, n);
        return null;
    }
    
    @Override
    public synchronized boolean remove(final Object o, final Object o2) {
        Objects.requireNonNull(o2);
        final Entry<?, ?>[] table = this.table;
        final int hashCode = o.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % table.length;
        Entry<?, ?> next = table[n];
        Entry<?, ?> entry = null;
        while (next != null) {
            if (next.hash == hashCode && next.key.equals(o) && next.value.equals(o2)) {
                ++this.modCount;
                if (entry != null) {
                    entry.next = next.next;
                }
                else {
                    table[n] = next.next;
                }
                --this.count;
                next.value = null;
                return true;
            }
            entry = next;
            next = next.next;
        }
        return false;
    }
    
    @Override
    public synchronized boolean replace(final K k, final V v, final V value) {
        Objects.requireNonNull(v);
        Objects.requireNonNull(value);
        final Entry<?, ?>[] table = this.table;
        final int hashCode = k.hashCode();
        Entry<?, ?> next = table[(hashCode & Integer.MAX_VALUE) % table.length];
        while (next != null) {
            if (next.hash == hashCode && next.key.equals(k)) {
                if (next.value.equals(v)) {
                    next.value = value;
                    return true;
                }
                return false;
            }
            else {
                next = next.next;
            }
        }
        return false;
    }
    
    @Override
    public synchronized V replace(final K k, final V value) {
        Objects.requireNonNull(value);
        final Entry<?, ?>[] table = this.table;
        final int hashCode = k.hashCode();
        for (Entry<?, ?> next = table[(hashCode & Integer.MAX_VALUE) % table.length]; next != null; next = next.next) {
            if (next.hash == hashCode && next.key.equals(k)) {
                final Object value2 = next.value;
                next.value = value;
                return (V)value2;
            }
        }
        return null;
    }
    
    @Override
    public synchronized V computeIfAbsent(final K k, final Function<? super K, ? extends V> function) {
        Objects.requireNonNull(function);
        final Entry<?, ?>[] table = this.table;
        final int hashCode = k.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % table.length;
        for (Entry<?, ?> next = table[n]; next != null; next = next.next) {
            if (next.hash == hashCode && next.key.equals(k)) {
                return (V)next.value;
            }
        }
        final V apply = (V)function.apply(k);
        if (apply != null) {
            this.addEntry(hashCode, k, apply, n);
        }
        return apply;
    }
    
    @Override
    public synchronized V computeIfPresent(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        final Entry<?, ?>[] table = this.table;
        final int hashCode = k.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % table.length;
        Entry<?, ?> next = table[n];
        Entry<?, ?> entry = null;
        while (next != null) {
            if (next.hash == hashCode && next.key.equals(k)) {
                final V apply = (V)biFunction.apply(k, (Object)next.value);
                if (apply == null) {
                    ++this.modCount;
                    if (entry != null) {
                        entry.next = next.next;
                    }
                    else {
                        table[n] = next.next;
                    }
                    --this.count;
                }
                else {
                    next.value = apply;
                }
                return apply;
            }
            entry = next;
            next = next.next;
        }
        return null;
    }
    
    @Override
    public synchronized V compute(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        final Entry<?, ?>[] table = this.table;
        final int hashCode = k.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % table.length;
        Entry<?, ?> next = table[n];
        Entry<?, ?> entry = null;
        while (next != null) {
            if (next.hash == hashCode && Objects.equals(next.key, k)) {
                final V apply = (V)biFunction.apply(k, (Object)next.value);
                if (apply == null) {
                    ++this.modCount;
                    if (entry != null) {
                        entry.next = next.next;
                    }
                    else {
                        table[n] = next.next;
                    }
                    --this.count;
                }
                else {
                    next.value = apply;
                }
                return apply;
            }
            entry = next;
            next = next.next;
        }
        final V apply2 = (V)biFunction.apply(k, (Object)null);
        if (apply2 != null) {
            this.addEntry(hashCode, k, apply2, n);
        }
        return apply2;
    }
    
    @Override
    public synchronized V merge(final K k, final V v, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        final Entry<?, ?>[] table = this.table;
        final int hashCode = k.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % table.length;
        Entry<?, ?> next = table[n];
        Entry<?, ?> entry = null;
        while (next != null) {
            if (next.hash == hashCode && next.key.equals(k)) {
                final V apply = (V)biFunction.apply(next.value, v);
                if (apply == null) {
                    ++this.modCount;
                    if (entry != null) {
                        entry.next = next.next;
                    }
                    else {
                        table[n] = next.next;
                    }
                    --this.count;
                }
                else {
                    next.value = apply;
                }
                return apply;
            }
            entry = next;
            next = next.next;
        }
        if (v != null) {
            this.addEntry(hashCode, k, v, n);
        }
        return v;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        Entry next = null;
        synchronized (this) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeInt(this.table.length);
            objectOutputStream.writeInt(this.count);
            for (int i = 0; i < this.table.length; ++i) {
                for (Entry<?, ?> next2 = this.table[i]; next2 != null; next2 = next2.next) {
                    next = new Entry<Object, Object>(0, next2.key, next2.value, (Entry<Object, Object>)next);
                }
            }
        }
        while (next != null) {
            objectOutputStream.writeObject(next.key);
            objectOutputStream.writeObject(next.value);
            next = next.next;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.loadFactor <= 0.0f || Float.isNaN(this.loadFactor)) {
            throw new StreamCorruptedException("Illegal Load: " + this.loadFactor);
        }
        final int int1 = objectInputStream.readInt();
        int i = objectInputStream.readInt();
        if (i < 0) {
            throw new StreamCorruptedException("Illegal # of Elements: " + i);
        }
        final int max = Math.max(int1, (int)(i / this.loadFactor) + 1);
        int n = (int)((i + i / 20) / this.loadFactor) + 3;
        if (n > i && (n & 0x1) == 0x0) {
            --n;
        }
        int min = Math.min(n, max);
        if (min < 0) {
            min = max;
        }
        SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Map.Entry[].class, min);
        this.table = (Entry<?, ?>[])new Entry[min];
        this.threshold = (int)Math.min(min * this.loadFactor, 2.14748365E9f);
        this.count = 0;
        while (i > 0) {
            this.reconstitutionPut(this.table, objectInputStream.readObject(), objectInputStream.readObject());
            --i;
        }
    }
    
    private void reconstitutionPut(final Entry<?, ?>[] array, final K k, final V v) throws StreamCorruptedException {
        if (v == null) {
            throw new StreamCorruptedException();
        }
        final int hashCode = k.hashCode();
        final int n = (hashCode & Integer.MAX_VALUE) % array.length;
        for (Entry<?, ?> next = array[n]; next != null; next = next.next) {
            if (next.hash == hashCode && next.key.equals(k)) {
                throw new StreamCorruptedException();
            }
        }
        array[n] = new Entry<Object, Object>(hashCode, k, v, (Entry<Object, Object>)array[n]);
        ++this.count;
    }
    
    private static class Entry<K, V> implements Map.Entry<K, V>
    {
        final int hash;
        final K key;
        V value;
        Entry<K, V> next;
        
        protected Entry(final int hash, final K key, final V value, final Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        @Override
        protected Object clone() {
            return new Entry(this.hash, this.key, this.value, (this.next == null) ? null : ((Entry)this.next.clone()));
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
            if (value == null) {
                throw new NullPointerException();
            }
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
            if (this.key == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            }
            else if (!this.key.equals(entry.getKey())) {
                return false;
            }
            if ((this.value != null) ? this.value.equals(entry.getValue()) : (entry.getValue() == null)) {
                return true;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.hash ^ Objects.hashCode(this.value);
        }
        
        @Override
        public String toString() {
            return this.key.toString() + "=" + this.value.toString();
        }
    }
    
    private class EntrySet extends AbstractSet<Map.Entry<K, V>>
    {
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return (Iterator<Map.Entry<K, V>>)Hashtable.this.getIterator(2);
        }
        
        @Override
        public boolean add(final Map.Entry<K, V> entry) {
            return super.add(entry);
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object key = entry.getKey();
            final Entry[] access$400 = Hashtable.this.table;
            final int hashCode = key.hashCode();
            for (Entry next = access$400[(hashCode & Integer.MAX_VALUE) % access$400.length]; next != null; next = next.next) {
                if (next.hash == hashCode && next.equals(entry)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object key = entry.getKey();
            final Entry[] access$400 = Hashtable.this.table;
            final int hashCode = key.hashCode();
            final int n = (hashCode & Integer.MAX_VALUE) % access$400.length;
            Entry next = access$400[n];
            Entry entry2 = null;
            while (next != null) {
                if (next.hash == hashCode && next.equals(entry)) {
                    Hashtable.this.modCount++;
                    if (entry2 != null) {
                        entry2.next = next.next;
                    }
                    else {
                        access$400[n] = next.next;
                    }
                    Hashtable.this.count--;
                    next.value = null;
                    return true;
                }
                entry2 = next;
                next = next.next;
            }
            return false;
        }
        
        @Override
        public int size() {
            return Hashtable.this.count;
        }
        
        @Override
        public void clear() {
            Hashtable.this.clear();
        }
    }
    
    private class Enumerator<T> implements Enumeration<T>, Iterator<T>
    {
        Entry<?, ?>[] table;
        int index;
        Entry<?, ?> entry;
        Entry<?, ?> lastReturned;
        int type;
        boolean iterator;
        protected int expectedModCount;
        
        Enumerator(final int type, final boolean iterator) {
            this.table = Hashtable.this.table;
            this.index = this.table.length;
            this.expectedModCount = Hashtable.this.modCount;
            this.type = type;
            this.iterator = iterator;
        }
        
        @Override
        public boolean hasMoreElements() {
            Entry<?, ?> entry = this.entry;
            int index = this.index;
            for (Entry<?, ?>[] table = this.table; entry == null && index > 0; entry = table[--index]) {}
            this.entry = entry;
            this.index = index;
            return entry != null;
        }
        
        @Override
        public T nextElement() {
            Entry<?, ?> entry = this.entry;
            int index = this.index;
            for (Entry<?, ?>[] table = this.table; entry == null && index > 0; entry = table[--index]) {}
            this.entry = entry;
            this.index = index;
            if (entry != null) {
                final Entry<?, ?> entry2 = this.entry;
                this.lastReturned = entry2;
                final Entry<?, ?> entry3 = entry2;
                this.entry = entry3.next;
                return (T)((this.type == 0) ? entry3.key : ((this.type == 1) ? entry3.value : entry3));
            }
            throw new NoSuchElementException("Hashtable Enumerator");
        }
        
        @Override
        public boolean hasNext() {
            return this.hasMoreElements();
        }
        
        @Override
        public T next() {
            if (Hashtable.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return this.nextElement();
        }
        
        @Override
        public void remove() {
            if (!this.iterator) {
                throw new UnsupportedOperationException();
            }
            if (this.lastReturned == null) {
                throw new IllegalStateException("Hashtable Enumerator");
            }
            if (Hashtable.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            synchronized (Hashtable.this) {
                final Entry[] access$400 = Hashtable.this.table;
                final int n = (this.lastReturned.hash & Integer.MAX_VALUE) % access$400.length;
                Entry<?, ?> next = (Entry<?, ?>)access$400[n];
                Entry<?, ?> entry = null;
                while (next != null) {
                    if (next == this.lastReturned) {
                        Hashtable.this.modCount++;
                        ++this.expectedModCount;
                        if (entry == null) {
                            access$400[n] = next.next;
                        }
                        else {
                            entry.next = next.next;
                        }
                        Hashtable.this.count--;
                        this.lastReturned = null;
                        return;
                    }
                    entry = next;
                    next = next.next;
                }
                throw new ConcurrentModificationException();
            }
        }
    }
    
    private class KeySet extends AbstractSet<K>
    {
        @Override
        public Iterator<K> iterator() {
            return (Iterator<K>)Hashtable.this.getIterator(0);
        }
        
        @Override
        public int size() {
            return Hashtable.this.count;
        }
        
        @Override
        public boolean contains(final Object o) {
            return Hashtable.this.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return Hashtable.this.remove(o) != null;
        }
        
        @Override
        public void clear() {
            Hashtable.this.clear();
        }
    }
    
    private class ValueCollection extends AbstractCollection<V>
    {
        @Override
        public Iterator<V> iterator() {
            return (Iterator<V>)Hashtable.this.getIterator(1);
        }
        
        @Override
        public int size() {
            return Hashtable.this.count;
        }
        
        @Override
        public boolean contains(final Object o) {
            return Hashtable.this.containsValue(o);
        }
        
        @Override
        public void clear() {
            Hashtable.this.clear();
        }
    }
}
