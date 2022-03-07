package java.util;

import java.io.*;

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
        return AbstractMap.access$000(this.key, entry.getKey()) && AbstractMap.access$000(this.value, entry.getValue());
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
