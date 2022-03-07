package java.util;

import java.io.*;

public abstract class AbstractMap<K, V> implements Map<K, V>
{
    transient Set<K> keySet;
    transient Collection<V> values;
    
    @Override
    public int size() {
        return this.entrySet().size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        final Iterator<Entry<K, V>> iterator = this.entrySet().iterator();
        if (o == null) {
            while (iterator.hasNext()) {
                if (iterator.next().getValue() == null) {
                    return true;
                }
            }
        }
        else {
            while (iterator.hasNext()) {
                if (o.equals(iterator.next().getValue())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        final Iterator<Entry<K, V>> iterator = this.entrySet().iterator();
        if (o == null) {
            while (iterator.hasNext()) {
                if (iterator.next().getKey() == null) {
                    return true;
                }
            }
        }
        else {
            while (iterator.hasNext()) {
                if (o.equals(iterator.next().getKey())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public V get(final Object o) {
        final Iterator<Entry<K, V>> iterator = this.entrySet().iterator();
        if (o == null) {
            while (iterator.hasNext()) {
                final Entry<K, V> entry = iterator.next();
                if (entry.getKey() == null) {
                    return entry.getValue();
                }
            }
        }
        else {
            while (iterator.hasNext()) {
                final Entry<K, V> entry2 = iterator.next();
                if (o.equals(entry2.getKey())) {
                    return entry2.getValue();
                }
            }
        }
        return null;
    }
    
    @Override
    public V put(final K k, final V v) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public V remove(final Object o) {
        final Iterator<Entry<K, V>> iterator = this.entrySet().iterator();
        Object o2 = null;
        if (o == null) {
            while (o2 == null && iterator.hasNext()) {
                final Entry<K, V> entry = iterator.next();
                if (entry.getKey() == null) {
                    o2 = entry;
                }
            }
        }
        else {
            while (o2 == null && iterator.hasNext()) {
                final Entry<K, V> entry2 = iterator.next();
                if (o.equals(entry2.getKey())) {
                    o2 = entry2;
                }
            }
        }
        Object value = null;
        if (o2 != null) {
            value = ((Entry<K, Object>)o2).getValue();
            iterator.remove();
        }
        return (V)value;
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        for (final Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), (V)entry.getValue());
        }
    }
    
    @Override
    public void clear() {
        this.entrySet().clear();
    }
    
    @Override
    public Set<K> keySet() {
        Set<K> keySet = this.keySet;
        if (keySet == null) {
            keySet = new AbstractSet<K>() {
                @Override
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<Entry<K, V>> i = AbstractMap.this.entrySet().iterator();
                        
                        @Override
                        public boolean hasNext() {
                            return this.i.hasNext();
                        }
                        
                        @Override
                        public K next() {
                            return this.i.next().getKey();
                        }
                        
                        @Override
                        public void remove() {
                            this.i.remove();
                        }
                    };
                }
                
                @Override
                public int size() {
                    return AbstractMap.this.size();
                }
                
                @Override
                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }
                
                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }
                
                @Override
                public boolean contains(final Object o) {
                    return AbstractMap.this.containsKey(o);
                }
            };
            this.keySet = keySet;
        }
        return keySet;
    }
    
    @Override
    public Collection<V> values() {
        Collection<V> values = this.values;
        if (values == null) {
            values = new AbstractCollection<V>() {
                @Override
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        private Iterator<Entry<K, V>> i = AbstractMap.this.entrySet().iterator();
                        
                        @Override
                        public boolean hasNext() {
                            return this.i.hasNext();
                        }
                        
                        @Override
                        public V next() {
                            return this.i.next().getValue();
                        }
                        
                        @Override
                        public void remove() {
                            this.i.remove();
                        }
                    };
                }
                
                @Override
                public int size() {
                    return AbstractMap.this.size();
                }
                
                @Override
                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }
                
                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }
                
                @Override
                public boolean contains(final Object o) {
                    return AbstractMap.this.containsValue(o);
                }
            };
            this.values = values;
        }
        return values;
    }
    
    @Override
    public abstract Set<Entry<K, V>> entrySet();
    
    @Override
    public boolean equals(final Object o) {
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
            for (final Entry<K, V> entry : this.entrySet()) {
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
    public int hashCode() {
        int n = 0;
        final Iterator<Entry<K, V>> iterator = this.entrySet().iterator();
        while (iterator.hasNext()) {
            n += iterator.next().hashCode();
        }
        return n;
    }
    
    @Override
    public String toString() {
        final Iterator<Entry<K, V>> iterator = this.entrySet().iterator();
        if (!iterator.hasNext()) {
            return "{}";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        while (true) {
            final Entry<K, V> entry = iterator.next();
            final K key = entry.getKey();
            final V value = entry.getValue();
            sb.append((key == this) ? "(this Map)" : key);
            sb.append('=');
            sb.append((value == this) ? "(this Map)" : value);
            if (!iterator.hasNext()) {
                break;
            }
            sb.append(',').append(' ');
        }
        return sb.append('}').toString();
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        final AbstractMap abstractMap = (AbstractMap)super.clone();
        abstractMap.keySet = null;
        abstractMap.values = null;
        return abstractMap;
    }
    
    private static boolean eq(final Object o, final Object o2) {
        return (o == null) ? (o2 == null) : o.equals(o2);
    }
    
    public static class SimpleEntry<K, V> implements Entry<K, V>, Serializable
    {
        private static final long serialVersionUID = -8499721149061103585L;
        private final K key;
        private V value;
        
        public SimpleEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
        
        public SimpleEntry(final Entry<? extends K, ? extends V> entry) {
            this.key = (K)entry.getKey();
            this.value = (V)entry.getValue();
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
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry entry = (Entry)o;
            return eq(this.key, entry.getKey()) && eq(this.value, entry.getValue());
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
    
    public static class SimpleImmutableEntry<K, V> implements Entry<K, V>, Serializable
    {
        private static final long serialVersionUID = 7138329143949025153L;
        private final K key;
        private final V value;
        
        public SimpleImmutableEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
        
        public SimpleImmutableEntry(final Entry<? extends K, ? extends V> entry) {
            this.key = (K)entry.getKey();
            this.value = (V)entry.getValue();
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
        public V setValue(final V v) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry entry = (Entry)o;
            return eq(this.key, entry.getKey()) && eq(this.value, entry.getValue());
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
}
