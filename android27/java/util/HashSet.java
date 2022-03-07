package java.util;

import java.io.*;
import sun.misc.*;

public class HashSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable
{
    static final long serialVersionUID = -5024744406713321676L;
    private transient HashMap<E, Object> map;
    private static final Object PRESENT;
    
    public HashSet() {
        this.map = new HashMap<E, Object>();
    }
    
    public HashSet(final Collection<? extends E> collection) {
        this.map = new HashMap<E, Object>(Math.max((int)(collection.size() / 0.75f) + 1, 16));
        this.addAll(collection);
    }
    
    public HashSet(final int n, final float n2) {
        this.map = new HashMap<E, Object>(n, n2);
    }
    
    public HashSet(final int n) {
        this.map = new HashMap<E, Object>(n);
    }
    
    HashSet(final int n, final float n2, final boolean b) {
        this.map = new LinkedHashMap<E, Object>(n, n2);
    }
    
    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }
    
    @Override
    public int size() {
        return this.map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public boolean add(final E e) {
        return this.map.put(e, HashSet.PRESENT) == null;
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.map.remove(o) == HashSet.PRESENT;
    }
    
    @Override
    public void clear() {
        this.map.clear();
    }
    
    public Object clone() {
        try {
            final HashSet set = (HashSet)super.clone();
            set.map = (HashMap<E, Object>)this.map.clone();
            return set;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.map.capacity());
        objectOutputStream.writeFloat(this.map.loadFactor());
        objectOutputStream.writeInt(this.map.size());
        final Iterator<E> iterator = this.map.keySet().iterator();
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int int1 = objectInputStream.readInt();
        if (int1 < 0) {
            throw new InvalidObjectException("Illegal capacity: " + int1);
        }
        final float float1 = objectInputStream.readFloat();
        if (float1 <= 0.0f || Float.isNaN(float1)) {
            throw new InvalidObjectException("Illegal load factor: " + float1);
        }
        final int int2 = objectInputStream.readInt();
        if (int2 < 0) {
            throw new InvalidObjectException("Illegal size: " + int2);
        }
        final int n = (int)Math.min(int2 * Math.min(1.0f / float1, 4.0f), 1.07374182E9f);
        SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Map.Entry[].class, HashMap.tableSizeFor(n));
        this.map = ((this instanceof LinkedHashSet) ? new LinkedHashMap<E, Object>(n, float1) : new HashMap<E, Object>(n, float1));
        for (int i = 0; i < int2; ++i) {
            this.map.put((E)objectInputStream.readObject(), HashSet.PRESENT);
        }
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new HashMap.KeySpliterator<E, Object>(this.map, 0, -1, 0, 0);
    }
    
    static {
        PRESENT = new Object();
    }
}
