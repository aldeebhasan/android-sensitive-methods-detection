package java.util;

import java.io.*;

public class TreeSet<E> extends AbstractSet<E> implements NavigableSet<E>, Cloneable, Serializable
{
    private transient NavigableMap<E, Object> m;
    private static final Object PRESENT;
    private static final long serialVersionUID = -2479143000061671589L;
    
    TreeSet(final NavigableMap<E, Object> m) {
        this.m = m;
    }
    
    public TreeSet() {
        this((NavigableMap)new TreeMap());
    }
    
    public TreeSet(final Comparator<? super E> comparator) {
        this((NavigableMap)new TreeMap(comparator));
    }
    
    public TreeSet(final Collection<? extends E> collection) {
        this();
        this.addAll(collection);
    }
    
    public TreeSet(final SortedSet<E> set) {
        this(set.comparator());
        this.addAll((Collection<? extends E>)set);
    }
    
    @Override
    public Iterator<E> iterator() {
        return this.m.navigableKeySet().iterator();
    }
    
    @Override
    public Iterator<E> descendingIterator() {
        return this.m.descendingKeySet().iterator();
    }
    
    @Override
    public NavigableSet<E> descendingSet() {
        return new TreeSet((NavigableMap<Object, Object>)this.m.descendingMap());
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
    public boolean add(final E e) {
        return this.m.put(e, TreeSet.PRESENT) == null;
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.m.remove(o) == TreeSet.PRESENT;
    }
    
    @Override
    public void clear() {
        this.m.clear();
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        if (this.m.size() == 0 && collection.size() > 0 && collection instanceof SortedSet && this.m instanceof TreeMap) {
            final SortedSet<? extends E> set = (SortedSet<? extends E>)collection;
            final TreeMap treeMap = (TreeMap)this.m;
            final Comparator<? super E> comparator = set.comparator();
            final Comparator<? super Object> comparator2 = treeMap.comparator();
            if (comparator == comparator2 || (comparator != null && comparator.equals(comparator2))) {
                treeMap.addAllForTreeSet(set, TreeSet.PRESENT);
                return true;
            }
        }
        return super.addAll(collection);
    }
    
    @Override
    public NavigableSet<E> subSet(final E e, final boolean b, final E e2, final boolean b2) {
        return new TreeSet((NavigableMap<Object, Object>)this.m.subMap(e, b, e2, b2));
    }
    
    @Override
    public NavigableSet<E> headSet(final E e, final boolean b) {
        return new TreeSet((NavigableMap<Object, Object>)this.m.headMap(e, b));
    }
    
    @Override
    public NavigableSet<E> tailSet(final E e, final boolean b) {
        return new TreeSet((NavigableMap<Object, Object>)this.m.tailMap(e, b));
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
    public E pollFirst() {
        final Map.Entry<E, Object> pollFirstEntry = this.m.pollFirstEntry();
        return (pollFirstEntry == null) ? null : pollFirstEntry.getKey();
    }
    
    @Override
    public E pollLast() {
        final Map.Entry<E, Object> pollLastEntry = this.m.pollLastEntry();
        return (pollLastEntry == null) ? null : pollLastEntry.getKey();
    }
    
    public Object clone() {
        TreeSet set;
        try {
            set = (TreeSet)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
        set.m = new TreeMap<E, Object>(this.m);
        return set;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.m.comparator());
        objectOutputStream.writeInt(this.m.size());
        final Iterator<Object> iterator = this.m.keySet().iterator();
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        ((TreeMap<K, Object>)(this.m = new TreeMap<E, Object>((Comparator<? super E>)objectInputStream.readObject()))).readTreeSet(objectInputStream.readInt(), objectInputStream, TreeSet.PRESENT);
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return TreeMap.keySpliteratorFor(this.m);
    }
    
    static {
        PRESENT = new Object();
    }
}
