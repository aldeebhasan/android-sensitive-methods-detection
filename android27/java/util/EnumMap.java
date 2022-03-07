package java.util;

import sun.misc.*;
import java.io.*;
import java.lang.reflect.*;

public class EnumMap<K extends Enum<K>, V> extends AbstractMap<K, V> implements Serializable, Cloneable
{
    private final Class<K> keyType;
    private transient K[] keyUniverse;
    private transient Object[] vals;
    private transient int size;
    private static final Object NULL;
    private static final Enum<?>[] ZERO_LENGTH_ENUM_ARRAY;
    private transient Set<Map.Entry<K, V>> entrySet;
    private static final long serialVersionUID = 458661240069192865L;
    
    private Object maskNull(final Object o) {
        return (o == null) ? EnumMap.NULL : o;
    }
    
    private V unmaskNull(final Object o) {
        return (V)((o == EnumMap.NULL) ? null : o);
    }
    
    public EnumMap(final Class<K> keyType) {
        this.size = 0;
        this.keyType = keyType;
        this.keyUniverse = getKeyUniverse(keyType);
        this.vals = new Object[this.keyUniverse.length];
    }
    
    public EnumMap(final EnumMap<K, ? extends V> enumMap) {
        this.size = 0;
        this.keyType = enumMap.keyType;
        this.keyUniverse = enumMap.keyUniverse;
        this.vals = enumMap.vals.clone();
        this.size = enumMap.size;
    }
    
    public EnumMap(final Map<K, ? extends V> map) {
        this.size = 0;
        if (map instanceof EnumMap) {
            final EnumMap enumMap = (EnumMap)map;
            this.keyType = enumMap.keyType;
            this.keyUniverse = enumMap.keyUniverse;
            this.vals = enumMap.vals.clone();
            this.size = enumMap.size;
        }
        else {
            if (map.isEmpty()) {
                throw new IllegalArgumentException("Specified map is empty");
            }
            this.keyType = (Class<K>)map.keySet().iterator().next().getDeclaringClass();
            this.keyUniverse = getKeyUniverse(this.keyType);
            this.vals = new Object[this.keyUniverse.length];
            this.putAll((Map<? extends K, ? extends V>)map);
        }
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean containsValue(Object maskNull) {
        maskNull = this.maskNull(maskNull);
        final Object[] vals = this.vals;
        for (int length = vals.length, i = 0; i < length; ++i) {
            if (maskNull.equals(vals[i])) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.isValidKey(o) && this.vals[((Enum)o).ordinal()] != null;
    }
    
    private boolean containsMapping(final Object o, final Object o2) {
        return this.isValidKey(o) && this.maskNull(o2).equals(this.vals[((Enum)o).ordinal()]);
    }
    
    @Override
    public V get(final Object o) {
        return this.isValidKey(o) ? this.unmaskNull(this.vals[((Enum)o).ordinal()]) : null;
    }
    
    @Override
    public V put(final K k, final V v) {
        this.typeCheck(k);
        final int ordinal = k.ordinal();
        final Object o = this.vals[ordinal];
        this.vals[ordinal] = this.maskNull(v);
        if (o == null) {
            ++this.size;
        }
        return this.unmaskNull(o);
    }
    
    @Override
    public V remove(final Object o) {
        if (!this.isValidKey(o)) {
            return null;
        }
        final int ordinal = ((Enum)o).ordinal();
        final Object o2 = this.vals[ordinal];
        this.vals[ordinal] = null;
        if (o2 != null) {
            --this.size;
        }
        return this.unmaskNull(o2);
    }
    
    private boolean removeMapping(final Object o, final Object o2) {
        if (!this.isValidKey(o)) {
            return false;
        }
        final int ordinal = ((Enum)o).ordinal();
        if (this.maskNull(o2).equals(this.vals[ordinal])) {
            this.vals[ordinal] = null;
            --this.size;
            return true;
        }
        return false;
    }
    
    private boolean isValidKey(final Object o) {
        if (o == null) {
            return false;
        }
        final Class<?> class1 = o.getClass();
        return class1 == this.keyType || class1.getSuperclass() == this.keyType;
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        if (map instanceof EnumMap) {
            final EnumMap<? extends K, ? extends V> enumMap = (EnumMap<? extends K, ? extends V>)map;
            if (enumMap.keyType != this.keyType) {
                if (enumMap.isEmpty()) {
                    return;
                }
                throw new ClassCastException(enumMap.keyType + " != " + this.keyType);
            }
            else {
                for (int i = 0; i < this.keyUniverse.length; ++i) {
                    final Object o = enumMap.vals[i];
                    if (o != null) {
                        if (this.vals[i] == null) {
                            ++this.size;
                        }
                        this.vals[i] = o;
                    }
                }
            }
        }
        else {
            super.putAll(map);
        }
    }
    
    @Override
    public void clear() {
        Arrays.fill(this.vals, null);
        this.size = 0;
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
        if (entrySet != null) {
            return entrySet;
        }
        return this.entrySet = new EntrySet();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof EnumMap) {
            return this.equals((EnumMap<?, ?>)o);
        }
        if (!(o instanceof Map)) {
            return false;
        }
        final Map map = (Map)o;
        if (this.size != map.size()) {
            return false;
        }
        for (int i = 0; i < this.keyUniverse.length; ++i) {
            if (null != this.vals[i]) {
                final Enum enum1 = this.keyUniverse[i];
                final V unmaskNull = this.unmaskNull(this.vals[i]);
                if (null == unmaskNull) {
                    if (null != map.get(enum1) || !map.containsKey(enum1)) {
                        return false;
                    }
                }
                else if (!unmaskNull.equals(map.get(enum1))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean equals(final EnumMap<?, ?> enumMap) {
        if (enumMap.keyType != this.keyType) {
            return this.size == 0 && enumMap.size == 0;
        }
        for (int i = 0; i < this.keyUniverse.length; ++i) {
            final Object o = this.vals[i];
            final Object o2 = enumMap.vals[i];
            if (o2 != o && (o2 == null || !o2.equals(o))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int n = 0;
        for (int i = 0; i < this.keyUniverse.length; ++i) {
            if (null != this.vals[i]) {
                n += this.entryHashCode(i);
            }
        }
        return n;
    }
    
    private int entryHashCode(final int n) {
        return this.keyUniverse[n].hashCode() ^ this.vals[n].hashCode();
    }
    
    public EnumMap<K, V> clone() {
        EnumMap enumMap;
        try {
            enumMap = (EnumMap)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new AssertionError();
        }
        enumMap.vals = enumMap.vals.clone();
        enumMap.entrySet = null;
        return enumMap;
    }
    
    private void typeCheck(final K k) {
        final Class<? extends Enum> class1 = k.getClass();
        if (class1 != this.keyType && class1.getSuperclass() != this.keyType) {
            throw new ClassCastException(class1 + " != " + this.keyType);
        }
    }
    
    private static <K extends Enum<K>> K[] getKeyUniverse(final Class<K> clazz) {
        return SharedSecrets.getJavaLangAccess().getEnumConstantsShared(clazz);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        int i = this.size;
        int n = 0;
        while (i > 0) {
            if (null != this.vals[n]) {
                objectOutputStream.writeObject(this.keyUniverse[n]);
                objectOutputStream.writeObject(this.unmaskNull(this.vals[n]));
                --i;
            }
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyUniverse = getKeyUniverse(this.keyType);
        this.vals = new Object[this.keyUniverse.length];
        for (int int1 = objectInputStream.readInt(), i = 0; i < int1; ++i) {
            this.put((K)objectInputStream.readObject(), objectInputStream.readObject());
        }
    }
    
    static {
        NULL = new Object() {
            @Override
            public int hashCode() {
                return 0;
            }
            
            @Override
            public String toString() {
                return "java.util.EnumMap.NULL";
            }
        };
        ZERO_LENGTH_ENUM_ARRAY = new Enum[0];
    }
    
    private class EntryIterator extends EnumMapIterator<Map.Entry<K, V>>
    {
        private Entry lastReturnedEntry;
        
        @Override
        public Map.Entry<K, V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.lastReturnedEntry = new Entry(this.index++);
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
                return (K)EnumMap.this.keyUniverse[this.index];
            }
            
            @Override
            public V getValue() {
                this.checkIndexForEntryUse();
                return (V)EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
            }
            
            @Override
            public V setValue(final V v) {
                this.checkIndexForEntryUse();
                final Object access$1200 = EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
                EnumMap.this.vals[this.index] = EnumMap.this.maskNull(v);
                return (V)access$1200;
            }
            
            @Override
            public boolean equals(final Object o) {
                if (this.index < 0) {
                    return o == this;
                }
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                final Map.Entry entry = (Map.Entry)o;
                final Object access$1200 = EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
                final Object value = entry.getValue();
                return entry.getKey() == EnumMap.this.keyUniverse[this.index] && (access$1200 == value || (access$1200 != null && access$1200.equals(value)));
            }
            
            @Override
            public int hashCode() {
                if (this.index < 0) {
                    return super.hashCode();
                }
                return EnumMap.this.entryHashCode(this.index);
            }
            
            @Override
            public String toString() {
                if (this.index < 0) {
                    return super.toString();
                }
                return EnumMap.this.keyUniverse[this.index] + "=" + EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
            }
            
            private void checkIndexForEntryUse() {
                if (this.index < 0) {
                    throw new IllegalStateException("Entry was removed");
                }
            }
        }
    }
    
    private abstract class EnumMapIterator<T> implements Iterator<T>
    {
        int index;
        int lastReturnedIndex;
        
        private EnumMapIterator() {
            this.index = 0;
            this.lastReturnedIndex = -1;
        }
        
        @Override
        public boolean hasNext() {
            while (this.index < EnumMap.this.vals.length && EnumMap.this.vals[this.index] == null) {
                ++this.index;
            }
            return this.index != EnumMap.this.vals.length;
        }
        
        @Override
        public void remove() {
            this.checkLastReturnedIndex();
            if (EnumMap.this.vals[this.lastReturnedIndex] != null) {
                EnumMap.this.vals[this.lastReturnedIndex] = null;
                EnumMap.this.size--;
            }
            this.lastReturnedIndex = -1;
        }
        
        private void checkLastReturnedIndex() {
            if (this.lastReturnedIndex < 0) {
                throw new IllegalStateException();
            }
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
            return EnumMap.this.containsMapping(entry.getKey(), entry.getValue());
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            return EnumMap.this.removeMapping(entry.getKey(), entry.getValue());
        }
        
        @Override
        public int size() {
            return EnumMap.this.size;
        }
        
        @Override
        public void clear() {
            EnumMap.this.clear();
        }
        
        @Override
        public Object[] toArray() {
            return this.fillEntryArray(new Object[EnumMap.this.size]);
        }
        
        @Override
        public <T> T[] toArray(T[] array) {
            final int size = this.size();
            if (array.length < size) {
                array = (T[])Array.newInstance(array.getClass().getComponentType(), size);
            }
            if (array.length > size) {
                array[size] = null;
            }
            return (T[])this.fillEntryArray(array);
        }
        
        private Object[] fillEntryArray(final Object[] array) {
            int n = 0;
            for (int i = 0; i < EnumMap.this.vals.length; ++i) {
                if (EnumMap.this.vals[i] != null) {
                    array[n++] = new SimpleEntry(EnumMap.this.keyUniverse[i], EnumMap.this.unmaskNull(EnumMap.this.vals[i]));
                }
            }
            return array;
        }
    }
    
    private class KeyIterator extends EnumMapIterator<K>
    {
        @Override
        public K next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastReturnedIndex = this.index++;
            return (K)EnumMap.this.keyUniverse[this.lastReturnedIndex];
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
            return EnumMap.this.size;
        }
        
        @Override
        public boolean contains(final Object o) {
            return EnumMap.this.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            final int access$200 = EnumMap.this.size;
            EnumMap.this.remove(o);
            return EnumMap.this.size != access$200;
        }
        
        @Override
        public void clear() {
            EnumMap.this.clear();
        }
    }
    
    private class ValueIterator extends EnumMapIterator<V>
    {
        @Override
        public V next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastReturnedIndex = this.index++;
            return (V)EnumMap.this.unmaskNull(EnumMap.this.vals[this.lastReturnedIndex]);
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
            return EnumMap.this.size;
        }
        
        @Override
        public boolean contains(final Object o) {
            return EnumMap.this.containsValue(o);
        }
        
        @Override
        public boolean remove(Object access$500) {
            access$500 = EnumMap.this.maskNull(access$500);
            for (int i = 0; i < EnumMap.this.vals.length; ++i) {
                if (access$500.equals(EnumMap.this.vals[i])) {
                    EnumMap.this.vals[i] = null;
                    EnumMap.this.size--;
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public void clear() {
            EnumMap.this.clear();
        }
    }
}
