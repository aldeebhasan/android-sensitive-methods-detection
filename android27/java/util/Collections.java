package java.util;

import java.lang.reflect.*;
import java.util.function.*;
import sun.misc.*;
import java.io.*;
import java.util.stream.*;

public class Collections
{
    private static final int BINARYSEARCH_THRESHOLD = 5000;
    private static final int REVERSE_THRESHOLD = 18;
    private static final int SHUFFLE_THRESHOLD = 5;
    private static final int FILL_THRESHOLD = 25;
    private static final int ROTATE_THRESHOLD = 100;
    private static final int COPY_THRESHOLD = 10;
    private static final int REPLACEALL_THRESHOLD = 11;
    private static final int INDEXOFSUBLIST_THRESHOLD = 35;
    private static Random r;
    public static final Set EMPTY_SET;
    public static final List EMPTY_LIST;
    public static final Map EMPTY_MAP;
    
    public static <T extends Comparable<? super T>> void sort(final List<T> list) {
        list.sort(null);
    }
    
    public static <T> void sort(final List<T> list, final Comparator<? super T> comparator) {
        list.sort(comparator);
    }
    
    public static <T> int binarySearch(final List<? extends Comparable<? super T>> list, final T t) {
        if (list instanceof RandomAccess || list.size() < 5000) {
            return indexedBinarySearch((List<? extends Comparable<? super Object>>)list, (Object)t);
        }
        return iteratorBinarySearch((List<? extends Comparable<? super Object>>)list, (Object)t);
    }
    
    private static <T> int indexedBinarySearch(final List<? extends Comparable<? super T>> list, final T t) {
        int i = 0;
        int n = list.size() - 1;
        while (i <= n) {
            final int n2 = i + n >>> 1;
            final int compareTo = ((Comparable)list.get(n2)).compareTo(t);
            if (compareTo < 0) {
                i = n2 + 1;
            }
            else {
                if (compareTo <= 0) {
                    return n2;
                }
                n = n2 - 1;
            }
        }
        return -(i + 1);
    }
    
    private static <T> int iteratorBinarySearch(final List<? extends Comparable<? super T>> list, final T t) {
        int i = 0;
        int n = list.size() - 1;
        final ListIterator<? extends Comparable<? super T>> listIterator = list.listIterator();
        while (i <= n) {
            final int n2 = i + n >>> 1;
            final int compareTo = get((ListIterator<? extends Comparable<T>>)listIterator, n2).compareTo(t);
            if (compareTo < 0) {
                i = n2 + 1;
            }
            else {
                if (compareTo <= 0) {
                    return n2;
                }
                n = n2 - 1;
            }
        }
        return -(i + 1);
    }
    
    private static <T> T get(final ListIterator<? extends T> listIterator, final int n) {
        int nextIndex = listIterator.nextIndex();
        T t;
        if (nextIndex <= n) {
            do {
                t = (T)listIterator.next();
            } while (nextIndex++ < n);
        }
        else {
            do {
                t = (T)listIterator.previous();
            } while (--nextIndex > n);
        }
        return t;
    }
    
    public static <T> int binarySearch(final List<? extends T> list, final T t, final Comparator<? super T> comparator) {
        if (comparator == null) {
            return binarySearch((List<? extends Comparable<? super T>>)list, t);
        }
        if (list instanceof RandomAccess || list.size() < 5000) {
            return indexedBinarySearch(list, (Object)t, (Comparator<? super Object>)comparator);
        }
        return iteratorBinarySearch(list, (Object)t, (Comparator<? super Object>)comparator);
    }
    
    private static <T> int indexedBinarySearch(final List<? extends T> list, final T t, final Comparator<? super T> comparator) {
        int i = 0;
        int n = list.size() - 1;
        while (i <= n) {
            final int n2 = i + n >>> 1;
            final int compare = comparator.compare((T)list.get(n2), t);
            if (compare < 0) {
                i = n2 + 1;
            }
            else {
                if (compare <= 0) {
                    return n2;
                }
                n = n2 - 1;
            }
        }
        return -(i + 1);
    }
    
    private static <T> int iteratorBinarySearch(final List<? extends T> list, final T t, final Comparator<? super T> comparator) {
        int i = 0;
        int n = list.size() - 1;
        final ListIterator<? extends T> listIterator = list.listIterator();
        while (i <= n) {
            final int n2 = i + n >>> 1;
            final int compare = comparator.compare(get(listIterator, n2), (Object)t);
            if (compare < 0) {
                i = n2 + 1;
            }
            else {
                if (compare <= 0) {
                    return n2;
                }
                n = n2 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static void reverse(final List<?> list) {
        final int size = list.size();
        if (size < 18 || list instanceof RandomAccess) {
            for (int i = 0, n = size >> 1, n2 = size - 1; i < n; ++i, --n2) {
                swap(list, i, n2);
            }
        }
        else {
            final ListIterator<?> listIterator = list.listIterator();
            final ListIterator<?> listIterator2 = list.listIterator(size);
            for (int j = 0; j < list.size() >> 1; ++j) {
                final Object next = listIterator.next();
                listIterator.set(listIterator2.previous());
                listIterator2.set(next);
            }
        }
    }
    
    public static void shuffle(final List<?> list) {
        Random r = Collections.r;
        if (r == null) {
            r = (Collections.r = new Random());
        }
        shuffle(list, r);
    }
    
    public static void shuffle(final List<?> list, final Random random) {
        final int size = list.size();
        if (size < 5 || list instanceof RandomAccess) {
            for (int i = size; i > 1; --i) {
                swap(list, i - 1, random.nextInt(i));
            }
        }
        else {
            final Object[] array = list.toArray();
            for (int j = size; j > 1; --j) {
                swap(array, j - 1, random.nextInt(j));
            }
            final ListIterator<?> listIterator = list.listIterator();
            for (int k = 0; k < array.length; ++k) {
                listIterator.next();
                listIterator.set(array[k]);
            }
        }
    }
    
    public static void swap(final List<?> list, final int n, final int n2) {
        list.set(n, list.set(n2, list.get(n)));
    }
    
    private static void swap(final Object[] array, final int n, final int n2) {
        final Object o = array[n];
        array[n] = array[n2];
        array[n2] = o;
    }
    
    public static <T> void fill(final List<? super T> list, final T t) {
        final int size = list.size();
        if (size < 25 || list instanceof RandomAccess) {
            for (int i = 0; i < size; ++i) {
                list.set(i, t);
            }
        }
        else {
            final ListIterator<T> listIterator = list.listIterator();
            for (int j = 0; j < size; ++j) {
                listIterator.next();
                listIterator.set(t);
            }
        }
    }
    
    public static <T> void copy(final List<? super T> list, final List<? extends T> list2) {
        final int size = list2.size();
        if (size > list.size()) {
            throw new IndexOutOfBoundsException("Source does not fit in dest");
        }
        if (size < 10 || (list2 instanceof RandomAccess && list instanceof RandomAccess)) {
            for (int i = 0; i < size; ++i) {
                list.set(i, (Object)list2.get(i));
            }
        }
        else {
            final ListIterator<? super T> listIterator = list.listIterator();
            final ListIterator<? extends T> listIterator2 = list2.listIterator();
            for (int j = 0; j < size; ++j) {
                listIterator.next();
                listIterator.set(listIterator2.next());
            }
        }
    }
    
    public static <T extends java.lang.Object> T min(final Collection<? extends T> collection) {
        final Iterator<? extends T> iterator = collection.iterator();
        Object next = iterator.next();
        while (iterator.hasNext()) {
            final T next2 = (T)iterator.next();
            if (((Comparable<Object>)next2).compareTo(next) < 0) {
                next = next2;
            }
        }
        return (T)next;
    }
    
    public static <T> T min(final Collection<? extends T> collection, final Comparator<? super T> comparator) {
        if (comparator == null) {
            return min(collection);
        }
        final Iterator<? extends T> iterator = collection.iterator();
        T next = (T)iterator.next();
        while (iterator.hasNext()) {
            final T next2 = (T)iterator.next();
            if (comparator.compare(next2, next) < 0) {
                next = next2;
            }
        }
        return next;
    }
    
    public static <T extends java.lang.Object> T max(final Collection<? extends T> collection) {
        final Iterator<? extends T> iterator = collection.iterator();
        Object next = iterator.next();
        while (iterator.hasNext()) {
            final T next2 = (T)iterator.next();
            if (((Comparable<Object>)next2).compareTo(next) > 0) {
                next = next2;
            }
        }
        return (T)next;
    }
    
    public static <T> T max(final Collection<? extends T> collection, final Comparator<? super T> comparator) {
        if (comparator == null) {
            return max(collection);
        }
        final Iterator<? extends T> iterator = collection.iterator();
        T next = (T)iterator.next();
        while (iterator.hasNext()) {
            final T next2 = (T)iterator.next();
            if (comparator.compare(next2, next) > 0) {
                next = next2;
            }
        }
        return next;
    }
    
    public static void rotate(final List<?> list, final int n) {
        if (list instanceof RandomAccess || list.size() < 100) {
            rotate1(list, n);
        }
        else {
            rotate2(list, n);
        }
    }
    
    private static <T> void rotate1(final List<T> list, int n) {
        final int size = list.size();
        if (size == 0) {
            return;
        }
        n %= size;
        if (n < 0) {
            n += size;
        }
        if (n == 0) {
            return;
        }
        int n2 = 0;
        int i = 0;
        while (i != size) {
            T t = list.get(n2);
            int j = n2;
            do {
                j += n;
                if (j >= size) {
                    j -= size;
                }
                t = list.set(j, t);
                ++i;
            } while (j != n2);
            ++n2;
        }
    }
    
    private static void rotate2(final List<?> list, final int n) {
        final int size = list.size();
        if (size == 0) {
            return;
        }
        int n2 = -n % size;
        if (n2 < 0) {
            n2 += size;
        }
        if (n2 == 0) {
            return;
        }
        reverse(list.subList(0, n2));
        reverse(list.subList(n2, size));
        reverse(list);
    }
    
    public static <T> boolean replaceAll(final List<T> list, final T t, final T t2) {
        boolean b = false;
        final int size = list.size();
        if (size < 11 || list instanceof RandomAccess) {
            if (t == null) {
                for (int i = 0; i < size; ++i) {
                    if (list.get(i) == null) {
                        list.set(i, t2);
                        b = true;
                    }
                }
            }
            else {
                for (int j = 0; j < size; ++j) {
                    if (t.equals(list.get(j))) {
                        list.set(j, t2);
                        b = true;
                    }
                }
            }
        }
        else {
            final ListIterator<T> listIterator = list.listIterator();
            if (t == null) {
                for (int k = 0; k < size; ++k) {
                    if (listIterator.next() == null) {
                        listIterator.set(t2);
                        b = true;
                    }
                }
            }
            else {
                for (int l = 0; l < size; ++l) {
                    if (t.equals(listIterator.next())) {
                        listIterator.set(t2);
                        b = true;
                    }
                }
            }
        }
        return b;
    }
    
    public static int indexOfSubList(final List<?> list, final List<?> list2) {
        final int size = list.size();
        final int size2 = list2.size();
        final int n = size - size2;
        if (size < 35 || (list instanceof RandomAccess && list2 instanceof RandomAccess)) {
            int i = 0;
        Label_0042:
            while (i <= n) {
                for (int j = 0, n2 = i; j < size2; ++j, ++n2) {
                    if (!eq(list2.get(j), list.get(n2))) {
                        ++i;
                        continue Label_0042;
                    }
                }
                return i;
            }
        }
        else {
            final ListIterator<?> listIterator = list.listIterator();
            int k = 0;
        Label_0119:
            while (k <= n) {
                final ListIterator<?> listIterator2 = list2.listIterator();
                for (int l = 0; l < size2; ++l) {
                    if (!eq(listIterator2.next(), listIterator.next())) {
                        for (int n3 = 0; n3 < l; ++n3) {
                            listIterator.previous();
                        }
                        ++k;
                        continue Label_0119;
                    }
                }
                return k;
            }
        }
        return -1;
    }
    
    public static int lastIndexOfSubList(final List<?> list, final List<?> list2) {
        final int size = list.size();
        final int size2 = list2.size();
        final int n = size - size2;
        if (size < 35 || list instanceof RandomAccess) {
            int i = n;
        Label_0036:
            while (i >= 0) {
                for (int j = 0, n2 = i; j < size2; ++j, ++n2) {
                    if (!eq(list2.get(j), list.get(n2))) {
                        --i;
                        continue Label_0036;
                    }
                }
                return i;
            }
        }
        else {
            if (n < 0) {
                return -1;
            }
            final ListIterator<?> listIterator = list.listIterator(n);
            int k = n;
        Label_0121:
            while (k >= 0) {
                final ListIterator<?> listIterator2 = list2.listIterator();
                for (int l = 0; l < size2; ++l) {
                    if (!eq(listIterator2.next(), listIterator.next())) {
                        if (k != 0) {
                            for (int n3 = 0; n3 <= l + 1; ++n3) {
                                listIterator.previous();
                            }
                        }
                        --k;
                        continue Label_0121;
                    }
                }
                return k;
            }
        }
        return -1;
    }
    
    public static <T> Collection<T> unmodifiableCollection(final Collection<? extends T> collection) {
        return new UnmodifiableCollection<T>(collection);
    }
    
    public static <T> Set<T> unmodifiableSet(final Set<? extends T> set) {
        return new UnmodifiableSet<T>(set);
    }
    
    public static <T> SortedSet<T> unmodifiableSortedSet(final SortedSet<T> set) {
        return new UnmodifiableSortedSet<T>(set);
    }
    
    public static <T> NavigableSet<T> unmodifiableNavigableSet(final NavigableSet<T> set) {
        return new UnmodifiableNavigableSet<T>(set);
    }
    
    public static <T> List<T> unmodifiableList(final List<? extends T> list) {
        return (list instanceof RandomAccess) ? new UnmodifiableRandomAccessList<T>(list) : new UnmodifiableList<T>(list);
    }
    
    public static <K, V> Map<K, V> unmodifiableMap(final Map<? extends K, ? extends V> map) {
        return new UnmodifiableMap<K, V>(map);
    }
    
    public static <K, V> SortedMap<K, V> unmodifiableSortedMap(final SortedMap<K, ? extends V> sortedMap) {
        return new UnmodifiableSortedMap<K, V>(sortedMap);
    }
    
    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(final NavigableMap<K, ? extends V> navigableMap) {
        return new UnmodifiableNavigableMap<K, V>(navigableMap);
    }
    
    public static <T> Collection<T> synchronizedCollection(final Collection<T> collection) {
        return new SynchronizedCollection<T>(collection);
    }
    
    static <T> Collection<T> synchronizedCollection(final Collection<T> collection, final Object o) {
        return new SynchronizedCollection<T>(collection, o);
    }
    
    public static <T> Set<T> synchronizedSet(final Set<T> set) {
        return new SynchronizedSet<T>(set);
    }
    
    static <T> Set<T> synchronizedSet(final Set<T> set, final Object o) {
        return new SynchronizedSet<T>(set, o);
    }
    
    public static <T> SortedSet<T> synchronizedSortedSet(final SortedSet<T> set) {
        return new SynchronizedSortedSet<T>(set);
    }
    
    public static <T> NavigableSet<T> synchronizedNavigableSet(final NavigableSet<T> set) {
        return new SynchronizedNavigableSet<T>(set);
    }
    
    public static <T> List<T> synchronizedList(final List<T> list) {
        return (list instanceof RandomAccess) ? new SynchronizedRandomAccessList<T>(list) : new SynchronizedList<T>(list);
    }
    
    static <T> List<T> synchronizedList(final List<T> list, final Object o) {
        return (list instanceof RandomAccess) ? new SynchronizedRandomAccessList<T>(list, o) : new SynchronizedList<T>(list, o);
    }
    
    public static <K, V> Map<K, V> synchronizedMap(final Map<K, V> map) {
        return new SynchronizedMap<K, V>(map);
    }
    
    public static <K, V> SortedMap<K, V> synchronizedSortedMap(final SortedMap<K, V> sortedMap) {
        return new SynchronizedSortedMap<K, V>(sortedMap);
    }
    
    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(final NavigableMap<K, V> navigableMap) {
        return new SynchronizedNavigableMap<K, V>(navigableMap);
    }
    
    public static <E> Collection<E> checkedCollection(final Collection<E> collection, final Class<E> clazz) {
        return new CheckedCollection<E>(collection, clazz);
    }
    
    static <T> T[] zeroLengthArray(final Class<T> clazz) {
        return (T[])Array.newInstance(clazz, 0);
    }
    
    public static <E> Queue<E> checkedQueue(final Queue<E> queue, final Class<E> clazz) {
        return new CheckedQueue<E>(queue, clazz);
    }
    
    public static <E> Set<E> checkedSet(final Set<E> set, final Class<E> clazz) {
        return new CheckedSet<E>(set, clazz);
    }
    
    public static <E> SortedSet<E> checkedSortedSet(final SortedSet<E> set, final Class<E> clazz) {
        return new CheckedSortedSet<E>(set, clazz);
    }
    
    public static <E> NavigableSet<E> checkedNavigableSet(final NavigableSet<E> set, final Class<E> clazz) {
        return new CheckedNavigableSet<E>(set, clazz);
    }
    
    public static <E> List<E> checkedList(final List<E> list, final Class<E> clazz) {
        return (list instanceof RandomAccess) ? new CheckedRandomAccessList<E>(list, clazz) : new CheckedList<E>(list, clazz);
    }
    
    public static <K, V> Map<K, V> checkedMap(final Map<K, V> map, final Class<K> clazz, final Class<V> clazz2) {
        return new CheckedMap<K, V>(map, clazz, clazz2);
    }
    
    public static <K, V> SortedMap<K, V> checkedSortedMap(final SortedMap<K, V> sortedMap, final Class<K> clazz, final Class<V> clazz2) {
        return new CheckedSortedMap<K, V>(sortedMap, clazz, clazz2);
    }
    
    public static <K, V> NavigableMap<K, V> checkedNavigableMap(final NavigableMap<K, V> navigableMap, final Class<K> clazz, final Class<V> clazz2) {
        return new CheckedNavigableMap<K, V>(navigableMap, clazz, clazz2);
    }
    
    public static <T> Iterator<T> emptyIterator() {
        return (Iterator<T>)EmptyIterator.EMPTY_ITERATOR;
    }
    
    public static <T> ListIterator<T> emptyListIterator() {
        return (ListIterator<T>)EmptyListIterator.EMPTY_ITERATOR;
    }
    
    public static <T> Enumeration<T> emptyEnumeration() {
        return (Enumeration<T>)EmptyEnumeration.EMPTY_ENUMERATION;
    }
    
    public static final <T> Set<T> emptySet() {
        return (Set<T>)Collections.EMPTY_SET;
    }
    
    public static <E> SortedSet<E> emptySortedSet() {
        return (SortedSet<E>)UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
    }
    
    public static <E> NavigableSet<E> emptyNavigableSet() {
        return (NavigableSet<E>)UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
    }
    
    public static final <T> List<T> emptyList() {
        return (List<T>)Collections.EMPTY_LIST;
    }
    
    public static final <K, V> Map<K, V> emptyMap() {
        return (Map<K, V>)Collections.EMPTY_MAP;
    }
    
    public static final <K, V> SortedMap<K, V> emptySortedMap() {
        return (SortedMap<K, V>)UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
    }
    
    public static final <K, V> NavigableMap<K, V> emptyNavigableMap() {
        return (NavigableMap<K, V>)UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
    }
    
    public static <T> Set<T> singleton(final T t) {
        return new SingletonSet<T>(t);
    }
    
    static <E> Iterator<E> singletonIterator(final E e) {
        return new Iterator<E>() {
            private boolean hasNext = true;
            
            @Override
            public boolean hasNext() {
                return this.hasNext;
            }
            
            @Override
            public E next() {
                if (this.hasNext) {
                    this.hasNext = false;
                    return e;
                }
                throw new NoSuchElementException();
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void forEachRemaining(final Consumer<? super E> consumer) {
                Objects.requireNonNull(consumer);
                if (this.hasNext) {
                    consumer.accept((Object)e);
                    this.hasNext = false;
                }
            }
        };
    }
    
    static <T> Spliterator<T> singletonSpliterator(final T t) {
        return new Spliterator<T>() {
            long est = 1L;
            
            @Override
            public Spliterator<T> trySplit() {
                return null;
            }
            
            @Override
            public boolean tryAdvance(final Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (this.est > 0L) {
                    --this.est;
                    consumer.accept((Object)t);
                    return true;
                }
                return false;
            }
            
            @Override
            public void forEachRemaining(final Consumer<? super T> consumer) {
                this.tryAdvance(consumer);
            }
            
            @Override
            public long estimateSize() {
                return this.est;
            }
            
            @Override
            public int characteristics() {
                return ((t != null) ? 256 : 0) | 0x40 | 0x4000 | 0x400 | 0x1 | 0x10;
            }
        };
    }
    
    public static <T> List<T> singletonList(final T t) {
        return new SingletonList<T>(t);
    }
    
    public static <K, V> Map<K, V> singletonMap(final K k, final V v) {
        return new SingletonMap<K, V>(k, v);
    }
    
    public static <T> List<T> nCopies(final int n, final T t) {
        if (n < 0) {
            throw new IllegalArgumentException("List length = " + n);
        }
        return new CopiesList<T>(n, t);
    }
    
    public static <T> Comparator<T> reverseOrder() {
        return (Comparator<T>)ReverseComparator.REVERSE_ORDER;
    }
    
    public static <T> Comparator<T> reverseOrder(final Comparator<T> comparator) {
        if (comparator == null) {
            return reverseOrder();
        }
        if (comparator instanceof ReverseComparator2) {
            return (Comparator<T>)((ReverseComparator2)comparator).cmp;
        }
        return new ReverseComparator2<T>(comparator);
    }
    
    public static <T> Enumeration<T> enumeration(final Collection<T> collection) {
        return new Enumeration<T>() {
            private final Iterator<T> i = collection.iterator();
            
            @Override
            public boolean hasMoreElements() {
                return this.i.hasNext();
            }
            
            @Override
            public T nextElement() {
                return this.i.next();
            }
        };
    }
    
    public static <T> ArrayList<T> list(final Enumeration<T> enumeration) {
        final ArrayList<T> list = new ArrayList<T>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }
    
    static boolean eq(final Object o, final Object o2) {
        return (o == null) ? (o2 == null) : o.equals(o2);
    }
    
    public static int frequency(final Collection<?> collection, final Object o) {
        int n = 0;
        if (o == null) {
            final Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() == null) {
                    ++n;
                }
            }
        }
        else {
            final Iterator<?> iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                if (o.equals(iterator2.next())) {
                    ++n;
                }
            }
        }
        return n;
    }
    
    public static boolean disjoint(final Collection<?> collection, final Collection<?> collection2) {
        Collection collection3 = collection2;
        Collection collection4 = collection;
        if (collection instanceof Set) {
            collection4 = collection2;
            collection3 = collection;
        }
        else if (!(collection2 instanceof Set)) {
            final int size = collection.size();
            final int size2 = collection2.size();
            if (size == 0 || size2 == 0) {
                return true;
            }
            if (size > size2) {
                collection4 = collection2;
                collection3 = collection;
            }
        }
        final Iterator<?> iterator = collection4.iterator();
        while (iterator.hasNext()) {
            if (collection3.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @SafeVarargs
    public static <T> boolean addAll(final Collection<? super T> collection, final T... array) {
        boolean b = false;
        for (int length = array.length, i = 0; i < length; ++i) {
            b |= collection.add(array[i]);
        }
        return b;
    }
    
    public static <E> Set<E> newSetFromMap(final Map<E, Boolean> map) {
        return new SetFromMap<E>(map);
    }
    
    public static <T> Queue<T> asLifoQueue(final Deque<T> deque) {
        return new AsLIFOQueue<T>(deque);
    }
    
    static {
        EMPTY_SET = new EmptySet();
        EMPTY_LIST = new EmptyList();
        EMPTY_MAP = new EmptyMap();
    }
    
    static class AsLIFOQueue<E> extends AbstractQueue<E> implements Queue<E>, Serializable
    {
        private static final long serialVersionUID = 1802017725587941708L;
        private final Deque<E> q;
        
        AsLIFOQueue(final Deque<E> q) {
            this.q = q;
        }
        
        @Override
        public boolean add(final E e) {
            this.q.addFirst(e);
            return true;
        }
        
        @Override
        public boolean offer(final E e) {
            return this.q.offerFirst(e);
        }
        
        @Override
        public E poll() {
            return this.q.pollFirst();
        }
        
        @Override
        public E remove() {
            return this.q.removeFirst();
        }
        
        @Override
        public E peek() {
            return this.q.peekFirst();
        }
        
        @Override
        public E element() {
            return this.q.getFirst();
        }
        
        @Override
        public void clear() {
            this.q.clear();
        }
        
        @Override
        public int size() {
            return this.q.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.q.isEmpty();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.q.contains(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.q.remove(o);
        }
        
        @Override
        public Iterator<E> iterator() {
            return this.q.iterator();
        }
        
        @Override
        public Object[] toArray() {
            return this.q.toArray();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            return this.q.toArray(array);
        }
        
        @Override
        public String toString() {
            return this.q.toString();
        }
        
        @Override
        public boolean containsAll(final Collection<?> collection) {
            return this.q.containsAll(collection);
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            return this.q.removeAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection<?> collection) {
            return this.q.retainAll(collection);
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            this.q.forEach(consumer);
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            return this.q.removeIf(predicate);
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return this.q.spliterator();
        }
        
        @Override
        public Stream<E> stream() {
            return this.q.stream();
        }
        
        @Override
        public Stream<E> parallelStream() {
            return this.q.parallelStream();
        }
    }
    
    static class CheckedCollection<E> implements Collection<E>, Serializable
    {
        private static final long serialVersionUID = 1578914078182001775L;
        final Collection<E> c;
        final Class<E> type;
        private E[] zeroLengthElementArray;
        
        E typeCheck(final Object o) {
            if (o != null && !this.type.isInstance(o)) {
                throw new ClassCastException(this.badElementMsg(o));
            }
            return (E)o;
        }
        
        private String badElementMsg(final Object o) {
            return "Attempt to insert " + o.getClass() + " element into collection with element type " + this.type;
        }
        
        CheckedCollection(final Collection<E> collection, final Class<E> clazz) {
            this.c = Objects.requireNonNull(collection, "c");
            this.type = Objects.requireNonNull(clazz, "type");
        }
        
        @Override
        public int size() {
            return this.c.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.c.isEmpty();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.c.contains(o);
        }
        
        @Override
        public Object[] toArray() {
            return this.c.toArray();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            return this.c.toArray(array);
        }
        
        @Override
        public String toString() {
            return this.c.toString();
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.c.remove(o);
        }
        
        @Override
        public void clear() {
            this.c.clear();
        }
        
        @Override
        public boolean containsAll(final Collection<?> collection) {
            return this.c.containsAll(collection);
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            return this.c.removeAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection<?> collection) {
            return this.c.retainAll(collection);
        }
        
        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                final /* synthetic */ Iterator val$it = CheckedCollection.this.c.iterator();
                
                @Override
                public boolean hasNext() {
                    return this.val$it.hasNext();
                }
                
                @Override
                public E next() {
                    return this.val$it.next();
                }
                
                @Override
                public void remove() {
                    this.val$it.remove();
                }
            };
        }
        
        @Override
        public boolean add(final E e) {
            return this.c.add(this.typeCheck(e));
        }
        
        private E[] zeroLengthElementArray() {
            return (E[])((this.zeroLengthElementArray != null) ? this.zeroLengthElementArray : (this.zeroLengthElementArray = Collections.zeroLengthArray(this.type)));
        }
        
        Collection<E> checkedCopyOf(final Collection<? extends E> collection) {
            Object[] array;
            try {
                final Object[] zeroLengthElementArray = this.zeroLengthElementArray();
                array = collection.toArray(zeroLengthElementArray);
                if (array.getClass() != zeroLengthElementArray.getClass()) {
                    array = Arrays.copyOf(array, array.length, zeroLengthElementArray.getClass());
                }
            }
            catch (ArrayStoreException ex) {
                final Object[] array2;
                array = (array2 = collection.toArray().clone());
                for (int length = array2.length, i = 0; i < length; ++i) {
                    this.typeCheck(array2[i]);
                }
            }
            return (Collection<E>)Arrays.asList(array);
        }
        
        @Override
        public boolean addAll(final Collection<? extends E> collection) {
            return this.c.addAll(this.checkedCopyOf(collection));
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            this.c.forEach(consumer);
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            return this.c.removeIf(predicate);
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return this.c.spliterator();
        }
        
        @Override
        public Stream<E> stream() {
            return this.c.stream();
        }
        
        @Override
        public Stream<E> parallelStream() {
            return this.c.parallelStream();
        }
    }
    
    static class CheckedList<E> extends CheckedCollection<E> implements List<E>
    {
        private static final long serialVersionUID = 65247728283967356L;
        final List<E> list;
        
        CheckedList(final List<E> list, final Class<E> clazz) {
            super(list, clazz);
            this.list = list;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || this.list.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.list.hashCode();
        }
        
        @Override
        public E get(final int n) {
            return this.list.get(n);
        }
        
        @Override
        public E remove(final int n) {
            return this.list.remove(n);
        }
        
        @Override
        public int indexOf(final Object o) {
            return this.list.indexOf(o);
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            return this.list.lastIndexOf(o);
        }
        
        @Override
        public E set(final int n, final E e) {
            return this.list.set(n, this.typeCheck(e));
        }
        
        @Override
        public void add(final int n, final E e) {
            this.list.add(n, this.typeCheck(e));
        }
        
        @Override
        public boolean addAll(final int n, final Collection<? extends E> collection) {
            return this.list.addAll(n, this.checkedCopyOf(collection));
        }
        
        @Override
        public ListIterator<E> listIterator() {
            return this.listIterator(0);
        }
        
        @Override
        public ListIterator<E> listIterator(final int n) {
            return new ListIterator<E>() {
                final /* synthetic */ ListIterator val$i = CheckedList.this.list.listIterator(n);
                
                @Override
                public boolean hasNext() {
                    return this.val$i.hasNext();
                }
                
                @Override
                public E next() {
                    return this.val$i.next();
                }
                
                @Override
                public boolean hasPrevious() {
                    return this.val$i.hasPrevious();
                }
                
                @Override
                public E previous() {
                    return this.val$i.previous();
                }
                
                @Override
                public int nextIndex() {
                    return this.val$i.nextIndex();
                }
                
                @Override
                public int previousIndex() {
                    return this.val$i.previousIndex();
                }
                
                @Override
                public void remove() {
                    this.val$i.remove();
                }
                
                @Override
                public void set(final E e) {
                    this.val$i.set(CheckedList.this.typeCheck(e));
                }
                
                @Override
                public void add(final E e) {
                    this.val$i.add(CheckedList.this.typeCheck(e));
                }
                
                @Override
                public void forEachRemaining(final Consumer<? super E> consumer) {
                    this.val$i.forEachRemaining(consumer);
                }
            };
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            return new CheckedList((List<Object>)this.list.subList(n, n2), (Class<Object>)this.type);
        }
        
        @Override
        public void replaceAll(final UnaryOperator<E> unaryOperator) {
            Objects.requireNonNull(unaryOperator);
            this.list.replaceAll(e -> this.typeCheck(unaryOperator.apply(e)));
        }
        
        @Override
        public void sort(final Comparator<? super E> comparator) {
            this.list.sort(comparator);
        }
    }
    
    private static class CheckedMap<K, V> implements Map<K, V>, Serializable
    {
        private static final long serialVersionUID = 5742860141034234728L;
        private final Map<K, V> m;
        final Class<K> keyType;
        final Class<V> valueType;
        private transient Set<Entry<K, V>> entrySet;
        
        private void typeCheck(final Object o, final Object o2) {
            if (o != null && !this.keyType.isInstance(o)) {
                throw new ClassCastException(this.badKeyMsg(o));
            }
            if (o2 != null && !this.valueType.isInstance(o2)) {
                throw new ClassCastException(this.badValueMsg(o2));
            }
        }
        
        private BiFunction<? super K, ? super V, ? extends V> typeCheck(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Objects.requireNonNull(biFunction);
            final Object o3;
            return (BiFunction<? super K, ? super V, ? extends V>)((o, o2) -> {
                biFunction.apply((Object)o, (Object)o2);
                this.typeCheck(o, o3);
                return o3;
            });
        }
        
        private String badKeyMsg(final Object o) {
            return "Attempt to insert " + o.getClass() + " key into map with key type " + this.keyType;
        }
        
        private String badValueMsg(final Object o) {
            return "Attempt to insert " + o.getClass() + " value into map with value type " + this.valueType;
        }
        
        CheckedMap(final Map<K, V> map, final Class<K> clazz, final Class<V> clazz2) {
            this.m = Objects.requireNonNull(map);
            this.keyType = Objects.requireNonNull(clazz);
            this.valueType = Objects.requireNonNull(clazz2);
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
        public boolean containsKey(final Object o) {
            return this.m.containsKey(o);
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return this.m.containsValue(o);
        }
        
        @Override
        public V get(final Object o) {
            return this.m.get(o);
        }
        
        @Override
        public V remove(final Object o) {
            return this.m.remove(o);
        }
        
        @Override
        public void clear() {
            this.m.clear();
        }
        
        @Override
        public Set<K> keySet() {
            return this.m.keySet();
        }
        
        @Override
        public Collection<V> values() {
            return this.m.values();
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || this.m.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.m.hashCode();
        }
        
        @Override
        public String toString() {
            return this.m.toString();
        }
        
        @Override
        public V put(final K k, final V v) {
            this.typeCheck(k, v);
            return this.m.put(k, v);
        }
        
        @Override
        public void putAll(final Map<? extends K, ? extends V> map) {
            final Object[] array = map.entrySet().toArray();
            final ArrayList list = new ArrayList<Object>(array.length);
            final Object[] array2 = array;
            for (int length = array2.length, i = 0; i < length; ++i) {
                final Entry<Object, V> entry = (Entry<Object, V>)array2[i];
                final Object key = entry.getKey();
                final V value = entry.getValue();
                this.typeCheck(key, value);
                list.add(new AbstractMap.SimpleImmutableEntry<Object, Object>(key, value));
            }
            for (final AbstractMap.SimpleImmutableEntry<Object, Object> simpleImmutableEntry : list) {
                this.m.put(simpleImmutableEntry.getKey(), simpleImmutableEntry.getValue());
            }
        }
        
        @Override
        public Set<Entry<K, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = new CheckedEntrySet<K, V>(this.m.entrySet(), this.valueType);
            }
            return this.entrySet;
        }
        
        @Override
        public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
            this.m.forEach(biConsumer);
        }
        
        @Override
        public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            this.m.replaceAll(this.typeCheck(biFunction));
        }
        
        @Override
        public V putIfAbsent(final K k, final V v) {
            this.typeCheck(k, v);
            return this.m.putIfAbsent(k, v);
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            return this.m.remove(o, o2);
        }
        
        @Override
        public boolean replace(final K k, final V v, final V v2) {
            this.typeCheck(k, v2);
            return this.m.replace(k, v, v2);
        }
        
        @Override
        public V replace(final K k, final V v) {
            this.typeCheck(k, v);
            return this.m.replace(k, v);
        }
        
        @Override
        public V computeIfAbsent(final K k, final Function<? super K, ? extends V> function) {
            Objects.requireNonNull(function);
            final Object o2;
            return this.m.computeIfAbsent(k, o -> {
                function.apply((Object)o);
                this.typeCheck(o, o2);
                return o2;
            });
        }
        
        @Override
        public V computeIfPresent(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            return this.m.computeIfPresent(k, this.typeCheck(biFunction));
        }
        
        @Override
        public V compute(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            return this.m.compute(k, this.typeCheck(biFunction));
        }
        
        @Override
        public V merge(final K k, final V v, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Objects.requireNonNull(biFunction);
            final Object o3;
            return this.m.merge(k, v, (o, o2) -> {
                biFunction.apply((Object)o, (Object)o2);
                this.typeCheck(null, o3);
                return o3;
            });
        }
        
        static class CheckedEntrySet<K, V> implements Set<Entry<K, V>>
        {
            private final Set<Entry<K, V>> s;
            private final Class<V> valueType;
            
            CheckedEntrySet(final Set<Entry<K, V>> s, final Class<V> valueType) {
                this.s = s;
                this.valueType = valueType;
            }
            
            @Override
            public int size() {
                return this.s.size();
            }
            
            @Override
            public boolean isEmpty() {
                return this.s.isEmpty();
            }
            
            @Override
            public String toString() {
                return this.s.toString();
            }
            
            @Override
            public int hashCode() {
                return this.s.hashCode();
            }
            
            @Override
            public void clear() {
                this.s.clear();
            }
            
            @Override
            public boolean add(final Entry<K, V> entry) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean addAll(final Collection<? extends Entry<K, V>> collection) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<Entry<K, V>>() {
                    final /* synthetic */ Iterator val$i = CheckedEntrySet.this.s.iterator();
                    final /* synthetic */ Class val$valueType = CheckedEntrySet.this.valueType;
                    
                    @Override
                    public boolean hasNext() {
                        return this.val$i.hasNext();
                    }
                    
                    @Override
                    public void remove() {
                        this.val$i.remove();
                    }
                    
                    @Override
                    public Entry<K, V> next() {
                        return (Entry<K, V>)CheckedEntrySet.checkedEntry(this.val$i.next(), (Class<Object>)this.val$valueType);
                    }
                };
            }
            
            @Override
            public Object[] toArray() {
                final Object[] array = this.s.toArray();
                final Object[] array2 = CheckedEntry.class.isInstance(array.getClass().getComponentType()) ? array : new Object[array.length];
                for (int i = 0; i < array.length; ++i) {
                    array2[i] = checkedEntry((Entry<Object, Object>)array[i], this.valueType);
                }
                return array2;
            }
            
            @Override
            public <T> T[] toArray(final T[] array) {
                final T[] array2 = this.s.toArray((array.length == 0) ? array : Arrays.copyOf(array, 0));
                for (int i = 0; i < array2.length; ++i) {
                    array2[i] = (T)checkedEntry((Entry<Object, Object>)array2[i], this.valueType);
                }
                if (array2.length > array.length) {
                    return array2;
                }
                System.arraycopy(array2, 0, array, 0, array2.length);
                if (array.length > array2.length) {
                    array[array2.length] = null;
                }
                return array;
            }
            
            @Override
            public boolean contains(final Object o) {
                if (!(o instanceof Entry)) {
                    return false;
                }
                final Entry entry = (Entry)o;
                return this.s.contains((entry instanceof CheckedEntry) ? entry : checkedEntry((Entry<Object, Object>)entry, this.valueType));
            }
            
            @Override
            public boolean containsAll(final Collection<?> collection) {
                final Iterator<?> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    if (!this.contains(iterator.next())) {
                        return false;
                    }
                }
                return true;
            }
            
            @Override
            public boolean remove(final Object o) {
                return o instanceof Entry && this.s.remove(new AbstractMap.SimpleImmutableEntry((Entry<?, ?>)o));
            }
            
            @Override
            public boolean removeAll(final Collection<?> collection) {
                return this.batchRemove(collection, false);
            }
            
            @Override
            public boolean retainAll(final Collection<?> collection) {
                return this.batchRemove(collection, true);
            }
            
            private boolean batchRemove(final Collection<?> collection, final boolean b) {
                Objects.requireNonNull(collection);
                boolean b2 = false;
                final Iterator<Entry<K, V>> iterator = this.iterator();
                while (iterator.hasNext()) {
                    if (collection.contains(iterator.next()) != b) {
                        iterator.remove();
                        b2 = true;
                    }
                }
                return b2;
            }
            
            @Override
            public boolean equals(final Object o) {
                if (o == this) {
                    return true;
                }
                if (!(o instanceof Set)) {
                    return false;
                }
                final Set set = (Set)o;
                return set.size() == this.s.size() && this.containsAll(set);
            }
            
            static <K, V, T> CheckedEntry<K, V, T> checkedEntry(final Entry<K, V> entry, final Class<T> clazz) {
                return new CheckedEntry<K, V, T>(entry, clazz);
            }
            
            private static class CheckedEntry<K, V, T> implements Entry<K, V>
            {
                private final Entry<K, V> e;
                private final Class<T> valueType;
                
                CheckedEntry(final Entry<K, V> entry, final Class<T> clazz) {
                    this.e = Objects.requireNonNull(entry);
                    this.valueType = Objects.requireNonNull(clazz);
                }
                
                @Override
                public K getKey() {
                    return this.e.getKey();
                }
                
                @Override
                public V getValue() {
                    return this.e.getValue();
                }
                
                @Override
                public int hashCode() {
                    return this.e.hashCode();
                }
                
                @Override
                public String toString() {
                    return this.e.toString();
                }
                
                @Override
                public V setValue(final V value) {
                    if (value != null && !this.valueType.isInstance(value)) {
                        throw new ClassCastException(this.badValueMsg(value));
                    }
                    return this.e.setValue(value);
                }
                
                private String badValueMsg(final Object o) {
                    return "Attempt to insert " + o.getClass() + " value into map with value type " + this.valueType;
                }
                
                @Override
                public boolean equals(final Object o) {
                    return o == this || (o instanceof Entry && this.e.equals(new AbstractMap.SimpleImmutableEntry((Entry<?, ?>)o)));
                }
            }
        }
    }
    
    static class CheckedNavigableMap<K, V> extends CheckedSortedMap<K, V> implements NavigableMap<K, V>, Serializable
    {
        private static final long serialVersionUID = -4852462692372534096L;
        private final NavigableMap<K, V> nm;
        
        CheckedNavigableMap(final NavigableMap<K, V> nm, final Class<K> clazz, final Class<V> clazz2) {
            super(nm, clazz, clazz2);
            this.nm = nm;
        }
        
        @Override
        public Comparator<? super K> comparator() {
            return this.nm.comparator();
        }
        
        @Override
        public K firstKey() {
            return this.nm.firstKey();
        }
        
        @Override
        public K lastKey() {
            return this.nm.lastKey();
        }
        
        @Override
        public Map.Entry<K, V> lowerEntry(final K k) {
            final Map.Entry<K, V> lowerEntry = this.nm.lowerEntry(k);
            return (null != lowerEntry) ? new CheckedEntrySet.CheckedEntry<K, V, Object>((Map.Entry<Object, Object>)lowerEntry, (Class<Object>)this.valueType) : null;
        }
        
        @Override
        public K lowerKey(final K k) {
            return this.nm.lowerKey(k);
        }
        
        @Override
        public Map.Entry<K, V> floorEntry(final K k) {
            final Map.Entry<K, V> floorEntry = this.nm.floorEntry(k);
            return (null != floorEntry) ? new CheckedEntrySet.CheckedEntry<K, V, Object>((Map.Entry<Object, Object>)floorEntry, (Class<Object>)this.valueType) : null;
        }
        
        @Override
        public K floorKey(final K k) {
            return this.nm.floorKey(k);
        }
        
        @Override
        public Map.Entry<K, V> ceilingEntry(final K k) {
            final Map.Entry<K, V> ceilingEntry = this.nm.ceilingEntry(k);
            return (null != ceilingEntry) ? new CheckedEntrySet.CheckedEntry<K, V, Object>((Map.Entry<Object, Object>)ceilingEntry, (Class<Object>)this.valueType) : null;
        }
        
        @Override
        public K ceilingKey(final K k) {
            return this.nm.ceilingKey(k);
        }
        
        @Override
        public Map.Entry<K, V> higherEntry(final K k) {
            final Map.Entry<K, V> higherEntry = this.nm.higherEntry(k);
            return (null != higherEntry) ? new CheckedEntrySet.CheckedEntry<K, V, Object>((Map.Entry<Object, Object>)higherEntry, (Class<Object>)this.valueType) : null;
        }
        
        @Override
        public K higherKey(final K k) {
            return this.nm.higherKey(k);
        }
        
        @Override
        public Map.Entry<K, V> firstEntry() {
            final Map.Entry<K, V> firstEntry = this.nm.firstEntry();
            return (null != firstEntry) ? new CheckedEntrySet.CheckedEntry<K, V, Object>((Map.Entry<Object, Object>)firstEntry, (Class<Object>)this.valueType) : null;
        }
        
        @Override
        public Map.Entry<K, V> lastEntry() {
            final Map.Entry<K, V> lastEntry = this.nm.lastEntry();
            return (null != lastEntry) ? new CheckedEntrySet.CheckedEntry<K, V, Object>((Map.Entry<Object, Object>)lastEntry, (Class<Object>)this.valueType) : null;
        }
        
        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            final Map.Entry<K, V> pollFirstEntry = this.nm.pollFirstEntry();
            return (null == pollFirstEntry) ? null : new CheckedEntrySet.CheckedEntry<K, V, Object>((Map.Entry<Object, Object>)pollFirstEntry, (Class<Object>)this.valueType);
        }
        
        @Override
        public Map.Entry<K, V> pollLastEntry() {
            final Map.Entry<K, V> pollLastEntry = this.nm.pollLastEntry();
            return (null == pollLastEntry) ? null : new CheckedEntrySet.CheckedEntry<K, V, Object>((Map.Entry<Object, Object>)pollLastEntry, (Class<Object>)this.valueType);
        }
        
        @Override
        public NavigableMap<K, V> descendingMap() {
            return Collections.checkedNavigableMap(this.nm.descendingMap(), this.keyType, this.valueType);
        }
        
        @Override
        public NavigableSet<K> keySet() {
            return this.navigableKeySet();
        }
        
        @Override
        public NavigableSet<K> navigableKeySet() {
            return Collections.checkedNavigableSet(this.nm.navigableKeySet(), this.keyType);
        }
        
        @Override
        public NavigableSet<K> descendingKeySet() {
            return Collections.checkedNavigableSet(this.nm.descendingKeySet(), this.keyType);
        }
        
        @Override
        public NavigableMap<K, V> subMap(final K k, final K i) {
            return Collections.checkedNavigableMap(this.nm.subMap(k, true, i, false), this.keyType, this.valueType);
        }
        
        @Override
        public NavigableMap<K, V> headMap(final K k) {
            return Collections.checkedNavigableMap(this.nm.headMap(k, false), this.keyType, this.valueType);
        }
        
        @Override
        public NavigableMap<K, V> tailMap(final K k) {
            return Collections.checkedNavigableMap(this.nm.tailMap(k, true), this.keyType, this.valueType);
        }
        
        @Override
        public NavigableMap<K, V> subMap(final K k, final boolean b, final K i, final boolean b2) {
            return Collections.checkedNavigableMap(this.nm.subMap(k, b, i, b2), this.keyType, this.valueType);
        }
        
        @Override
        public NavigableMap<K, V> headMap(final K k, final boolean b) {
            return Collections.checkedNavigableMap(this.nm.headMap(k, b), this.keyType, this.valueType);
        }
        
        @Override
        public NavigableMap<K, V> tailMap(final K k, final boolean b) {
            return Collections.checkedNavigableMap(this.nm.tailMap(k, b), this.keyType, this.valueType);
        }
    }
    
    static class CheckedSortedMap<K, V> extends CheckedMap<K, V> implements SortedMap<K, V>, Serializable
    {
        private static final long serialVersionUID = 1599671320688067438L;
        private final SortedMap<K, V> sm;
        
        CheckedSortedMap(final SortedMap<K, V> sm, final Class<K> clazz, final Class<V> clazz2) {
            super(sm, clazz, clazz2);
            this.sm = sm;
        }
        
        @Override
        public Comparator<? super K> comparator() {
            return this.sm.comparator();
        }
        
        @Override
        public K firstKey() {
            return this.sm.firstKey();
        }
        
        @Override
        public K lastKey() {
            return this.sm.lastKey();
        }
        
        @Override
        public SortedMap<K, V> subMap(final K k, final K i) {
            return Collections.checkedSortedMap(this.sm.subMap(k, i), this.keyType, this.valueType);
        }
        
        @Override
        public SortedMap<K, V> headMap(final K k) {
            return Collections.checkedSortedMap(this.sm.headMap(k), this.keyType, this.valueType);
        }
        
        @Override
        public SortedMap<K, V> tailMap(final K k) {
            return Collections.checkedSortedMap(this.sm.tailMap(k), this.keyType, this.valueType);
        }
    }
    
    static class CheckedNavigableSet<E> extends CheckedSortedSet<E> implements NavigableSet<E>, Serializable
    {
        private static final long serialVersionUID = -5429120189805438922L;
        private final NavigableSet<E> ns;
        
        CheckedNavigableSet(final NavigableSet<E> ns, final Class<E> clazz) {
            super(ns, clazz);
            this.ns = ns;
        }
        
        @Override
        public E lower(final E e) {
            return this.ns.lower(e);
        }
        
        @Override
        public E floor(final E e) {
            return this.ns.floor(e);
        }
        
        @Override
        public E ceiling(final E e) {
            return this.ns.ceiling(e);
        }
        
        @Override
        public E higher(final E e) {
            return this.ns.higher(e);
        }
        
        @Override
        public E pollFirst() {
            return this.ns.pollFirst();
        }
        
        @Override
        public E pollLast() {
            return this.ns.pollLast();
        }
        
        @Override
        public NavigableSet<E> descendingSet() {
            return Collections.checkedNavigableSet(this.ns.descendingSet(), this.type);
        }
        
        @Override
        public Iterator<E> descendingIterator() {
            return Collections.checkedNavigableSet(this.ns.descendingSet(), this.type).iterator();
        }
        
        @Override
        public NavigableSet<E> subSet(final E e, final E e2) {
            return Collections.checkedNavigableSet(this.ns.subSet(e, true, e2, false), this.type);
        }
        
        @Override
        public NavigableSet<E> headSet(final E e) {
            return Collections.checkedNavigableSet(this.ns.headSet(e, false), this.type);
        }
        
        @Override
        public NavigableSet<E> tailSet(final E e) {
            return Collections.checkedNavigableSet(this.ns.tailSet(e, true), this.type);
        }
        
        @Override
        public NavigableSet<E> subSet(final E e, final boolean b, final E e2, final boolean b2) {
            return Collections.checkedNavigableSet(this.ns.subSet(e, b, e2, b2), this.type);
        }
        
        @Override
        public NavigableSet<E> headSet(final E e, final boolean b) {
            return Collections.checkedNavigableSet(this.ns.headSet(e, b), this.type);
        }
        
        @Override
        public NavigableSet<E> tailSet(final E e, final boolean b) {
            return Collections.checkedNavigableSet(this.ns.tailSet(e, b), this.type);
        }
    }
    
    static class CheckedSortedSet<E> extends CheckedSet<E> implements SortedSet<E>, Serializable
    {
        private static final long serialVersionUID = 1599911165492914959L;
        private final SortedSet<E> ss;
        
        CheckedSortedSet(final SortedSet<E> ss, final Class<E> clazz) {
            super(ss, clazz);
            this.ss = ss;
        }
        
        @Override
        public Comparator<? super E> comparator() {
            return this.ss.comparator();
        }
        
        @Override
        public E first() {
            return this.ss.first();
        }
        
        @Override
        public E last() {
            return this.ss.last();
        }
        
        @Override
        public SortedSet<E> subSet(final E e, final E e2) {
            return Collections.checkedSortedSet(this.ss.subSet(e, e2), this.type);
        }
        
        @Override
        public SortedSet<E> headSet(final E e) {
            return Collections.checkedSortedSet(this.ss.headSet(e), this.type);
        }
        
        @Override
        public SortedSet<E> tailSet(final E e) {
            return Collections.checkedSortedSet(this.ss.tailSet(e), this.type);
        }
    }
    
    static class CheckedSet<E> extends CheckedCollection<E> implements Set<E>, Serializable
    {
        private static final long serialVersionUID = 4694047833775013803L;
        
        CheckedSet(final Set<E> set, final Class<E> clazz) {
            super(set, clazz);
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || this.c.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.c.hashCode();
        }
    }
    
    static class CheckedQueue<E> extends CheckedCollection<E> implements Queue<E>, Serializable
    {
        private static final long serialVersionUID = 1433151992604707767L;
        final Queue<E> queue;
        
        CheckedQueue(final Queue<E> queue, final Class<E> clazz) {
            super(queue, clazz);
            this.queue = queue;
        }
        
        @Override
        public E element() {
            return this.queue.element();
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || this.c.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.c.hashCode();
        }
        
        @Override
        public E peek() {
            return this.queue.peek();
        }
        
        @Override
        public E poll() {
            return this.queue.poll();
        }
        
        @Override
        public E remove() {
            return this.queue.remove();
        }
        
        @Override
        public boolean offer(final E e) {
            return this.queue.offer(this.typeCheck(e));
        }
    }
    
    static class CheckedRandomAccessList<E> extends CheckedList<E> implements RandomAccess
    {
        private static final long serialVersionUID = 1638200125423088369L;
        
        CheckedRandomAccessList(final List<E> list, final Class<E> clazz) {
            super(list, clazz);
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            return new CheckedRandomAccessList((List<Object>)this.list.subList(n, n2), (Class<Object>)this.type);
        }
    }
    
    private static class CopiesList<E> extends AbstractList<E> implements RandomAccess, Serializable
    {
        private static final long serialVersionUID = 2739099268398711800L;
        final int n;
        final E element;
        
        CopiesList(final int n, final E element) {
            assert n >= 0;
            this.n = n;
            this.element = element;
        }
        
        @Override
        public int size() {
            return this.n;
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.n != 0 && Collections.eq(o, this.element);
        }
        
        @Override
        public int indexOf(final Object o) {
            return this.contains(o) ? 0 : -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            return this.contains(o) ? (this.n - 1) : -1;
        }
        
        @Override
        public E get(final int n) {
            if (n < 0 || n >= this.n) {
                throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + this.n);
            }
            return this.element;
        }
        
        @Override
        public Object[] toArray() {
            final Object[] array = new Object[this.n];
            if (this.element != null) {
                Arrays.fill(array, 0, this.n, this.element);
            }
            return array;
        }
        
        @Override
        public <T> T[] toArray(T[] array) {
            final int n = this.n;
            if (array.length < n) {
                array = (T[])Array.newInstance(array.getClass().getComponentType(), n);
                if (this.element != null) {
                    Arrays.fill(array, 0, n, this.element);
                }
            }
            else {
                Arrays.fill(array, 0, n, this.element);
                if (array.length > n) {
                    array[n] = null;
                }
            }
            return array;
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            if (n < 0) {
                throw new IndexOutOfBoundsException("fromIndex = " + n);
            }
            if (n2 > this.n) {
                throw new IndexOutOfBoundsException("toIndex = " + n2);
            }
            if (n > n2) {
                throw new IllegalArgumentException("fromIndex(" + n + ") > toIndex(" + n2 + ")");
            }
            return new CopiesList(n2 - n, this.element);
        }
        
        @Override
        public int hashCode() {
            if (this.n == 0) {
                return 1;
            }
            int n = 31;
            int n2 = 1;
            for (int i = Integer.numberOfLeadingZeros(this.n) + 1; i < 32; ++i) {
                n2 *= n + 1;
                n *= n;
                if (this.n << i < 0) {
                    n *= 31;
                    n2 = n2 * 31 + 1;
                }
            }
            return n + n2 * ((this.element == null) ? 0 : this.element.hashCode());
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof CopiesList) {
                final CopiesList list = (CopiesList)o;
                return this.n == list.n && (this.n == 0 || Collections.eq(this.element, list.element));
            }
            if (!(o instanceof List)) {
                return false;
            }
            int n = this.n;
            final E element = this.element;
            final Iterator<Object> iterator = (Iterator<Object>)((List)o).iterator();
            if (element == null) {
                while (iterator.hasNext() && n-- > 0) {
                    if (iterator.next() != null) {
                        return false;
                    }
                }
            }
            else {
                while (iterator.hasNext() && n-- > 0) {
                    if (!element.equals(iterator.next())) {
                        return false;
                    }
                }
            }
            return n == 0 && !iterator.hasNext();
        }
        
        @Override
        public Stream<E> stream() {
            return IntStream.range(0, this.n).mapToObj(p0 -> this.element);
        }
        
        @Override
        public Stream<E> parallelStream() {
            return IntStream.range(0, this.n).parallel().mapToObj(p0 -> this.element);
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return this.stream().spliterator();
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            SharedSecrets.getJavaOISAccess().checkArray(objectInputStream, Object[].class, this.n);
        }
    }
    
    private static class EmptyEnumeration<E> implements Enumeration<E>
    {
        static final EmptyEnumeration<Object> EMPTY_ENUMERATION;
        
        @Override
        public boolean hasMoreElements() {
            return false;
        }
        
        @Override
        public E nextElement() {
            throw new NoSuchElementException();
        }
        
        static {
            EMPTY_ENUMERATION = new EmptyEnumeration<Object>();
        }
    }
    
    private static class EmptyIterator<E> implements Iterator<E>
    {
        static final EmptyIterator<Object> EMPTY_ITERATOR;
        
        @Override
        public boolean hasNext() {
            return false;
        }
        
        @Override
        public E next() {
            throw new NoSuchElementException();
        }
        
        @Override
        public void remove() {
            throw new IllegalStateException();
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
        }
        
        static {
            EMPTY_ITERATOR = new EmptyIterator<Object>();
        }
    }
    
    private static class EmptyList<E> extends AbstractList<E> implements RandomAccess, Serializable
    {
        private static final long serialVersionUID = 8842843931221139166L;
        
        @Override
        public Iterator<E> iterator() {
            return Collections.emptyIterator();
        }
        
        @Override
        public ListIterator<E> listIterator() {
            return Collections.emptyListIterator();
        }
        
        @Override
        public int size() {
            return 0;
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public boolean contains(final Object o) {
            return false;
        }
        
        @Override
        public boolean containsAll(final Collection<?> collection) {
            return collection.isEmpty();
        }
        
        @Override
        public Object[] toArray() {
            return new Object[0];
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            if (array.length > 0) {
                array[0] = null;
            }
            return array;
        }
        
        @Override
        public E get(final int n) {
            throw new IndexOutOfBoundsException("Index: " + n);
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof List && ((List)o).isEmpty();
        }
        
        @Override
        public int hashCode() {
            return 1;
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }
        
        @Override
        public void replaceAll(final UnaryOperator<E> unaryOperator) {
            Objects.requireNonNull(unaryOperator);
        }
        
        @Override
        public void sort(final Comparator<? super E> comparator) {
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.emptySpliterator();
        }
        
        private Object readResolve() {
            return Collections.EMPTY_LIST;
        }
    }
    
    private static class EmptyListIterator<E> extends EmptyIterator<E> implements ListIterator<E>
    {
        static final EmptyListIterator<Object> EMPTY_ITERATOR;
        
        @Override
        public boolean hasPrevious() {
            return false;
        }
        
        @Override
        public E previous() {
            throw new NoSuchElementException();
        }
        
        @Override
        public int nextIndex() {
            return 0;
        }
        
        @Override
        public int previousIndex() {
            return -1;
        }
        
        @Override
        public void set(final E e) {
            throw new IllegalStateException();
        }
        
        @Override
        public void add(final E e) {
            throw new UnsupportedOperationException();
        }
        
        static {
            EMPTY_ITERATOR = new EmptyListIterator<Object>();
        }
    }
    
    private static class EmptyMap<K, V> extends AbstractMap<K, V> implements Serializable
    {
        private static final long serialVersionUID = 6428348081105594320L;
        
        @Override
        public int size() {
            return 0;
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return false;
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return false;
        }
        
        @Override
        public V get(final Object o) {
            return null;
        }
        
        @Override
        public Set<K> keySet() {
            return Collections.emptySet();
        }
        
        @Override
        public Collection<V> values() {
            return (Collection<V>)Collections.emptySet();
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return Collections.emptySet();
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Map && ((Map)o).isEmpty();
        }
        
        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public V getOrDefault(final Object o, final V v) {
            return v;
        }
        
        @Override
        public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
            Objects.requireNonNull(biConsumer);
        }
        
        @Override
        public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Objects.requireNonNull(biFunction);
        }
        
        @Override
        public V putIfAbsent(final K k, final V v) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean replace(final K k, final V v, final V v2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V replace(final K k, final V v) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V computeIfAbsent(final K k, final Function<? super K, ? extends V> function) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V computeIfPresent(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V compute(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V merge(final K k, final V v, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        private Object readResolve() {
            return Collections.EMPTY_MAP;
        }
    }
    
    private static class EmptySet<E> extends AbstractSet<E> implements Serializable
    {
        private static final long serialVersionUID = 1582296315990362920L;
        
        @Override
        public Iterator<E> iterator() {
            return Collections.emptyIterator();
        }
        
        @Override
        public int size() {
            return 0;
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public boolean contains(final Object o) {
            return false;
        }
        
        @Override
        public boolean containsAll(final Collection<?> collection) {
            return collection.isEmpty();
        }
        
        @Override
        public Object[] toArray() {
            return new Object[0];
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            if (array.length > 0) {
                array[0] = null;
            }
            return array;
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.emptySpliterator();
        }
        
        private Object readResolve() {
            return Collections.EMPTY_SET;
        }
    }
    
    private static class ReverseComparator implements Comparator<Comparable<Object>>, Serializable
    {
        private static final long serialVersionUID = 7207038068494060240L;
        static final ReverseComparator REVERSE_ORDER;
        
        @Override
        public int compare(final Comparable<Object> comparable, final Comparable<Object> comparable2) {
            return comparable2.compareTo(comparable);
        }
        
        private Object readResolve() {
            return Collections.reverseOrder();
        }
        
        @Override
        public Comparator<Comparable<Object>> reversed() {
            return Comparator.naturalOrder();
        }
        
        static {
            REVERSE_ORDER = new ReverseComparator();
        }
    }
    
    private static class ReverseComparator2<T> implements Comparator<T>, Serializable
    {
        private static final long serialVersionUID = 4374092139857L;
        final Comparator<T> cmp;
        
        ReverseComparator2(final Comparator<T> cmp) {
            assert cmp != null;
            this.cmp = cmp;
        }
        
        @Override
        public int compare(final T t, final T t2) {
            return this.cmp.compare(t2, t);
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || (o instanceof ReverseComparator2 && this.cmp.equals(((ReverseComparator2)o).cmp));
        }
        
        @Override
        public int hashCode() {
            return this.cmp.hashCode() ^ Integer.MIN_VALUE;
        }
        
        @Override
        public Comparator<T> reversed() {
            return this.cmp;
        }
    }
    
    private static class SetFromMap<E> extends AbstractSet<E> implements Set<E>, Serializable
    {
        private final Map<E, Boolean> m;
        private transient Set<E> s;
        private static final long serialVersionUID = 2454657854757543876L;
        
        SetFromMap(final Map<E, Boolean> m) {
            if (!m.isEmpty()) {
                throw new IllegalArgumentException("Map is non-empty");
            }
            this.m = m;
            this.s = m.keySet();
        }
        
        @Override
        public void clear() {
            this.m.clear();
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
        public boolean add(final E e) {
            return this.m.put(e, Boolean.TRUE) == null;
        }
        
        @Override
        public Iterator<E> iterator() {
            return this.s.iterator();
        }
        
        @Override
        public Object[] toArray() {
            return this.s.toArray();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            return this.s.toArray(array);
        }
        
        @Override
        public String toString() {
            return this.s.toString();
        }
        
        @Override
        public int hashCode() {
            return this.s.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || this.s.equals(o);
        }
        
        @Override
        public boolean containsAll(final Collection<?> collection) {
            return this.s.containsAll(collection);
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            return this.s.removeAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection<?> collection) {
            return this.s.retainAll(collection);
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            this.s.forEach(consumer);
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            return this.s.removeIf(predicate);
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return this.s.spliterator();
        }
        
        @Override
        public Stream<E> stream() {
            return this.s.stream();
        }
        
        @Override
        public Stream<E> parallelStream() {
            return this.s.parallelStream();
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.s = this.m.keySet();
        }
    }
    
    private static class SingletonList<E> extends AbstractList<E> implements RandomAccess, Serializable
    {
        private static final long serialVersionUID = 3093736618740652951L;
        private final E element;
        
        SingletonList(final E element) {
            this.element = element;
        }
        
        @Override
        public Iterator<E> iterator() {
            return Collections.singletonIterator(this.element);
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        @Override
        public boolean contains(final Object o) {
            return Collections.eq(o, this.element);
        }
        
        @Override
        public E get(final int n) {
            if (n != 0) {
                throw new IndexOutOfBoundsException("Index: " + n + ", Size: 1");
            }
            return this.element;
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            consumer.accept(this.element);
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void replaceAll(final UnaryOperator<E> unaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void sort(final Comparator<? super E> comparator) {
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return Collections.singletonSpliterator(this.element);
        }
    }
    
    private static class SingletonMap<K, V> extends AbstractMap<K, V> implements Serializable
    {
        private static final long serialVersionUID = -6979724477215052911L;
        private final K k;
        private final V v;
        private transient Set<K> keySet;
        private transient Set<Map.Entry<K, V>> entrySet;
        private transient Collection<V> values;
        
        SingletonMap(final K k, final V v) {
            this.k = k;
            this.v = v;
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return Collections.eq(o, this.k);
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return Collections.eq(o, this.v);
        }
        
        @Override
        public V get(final Object o) {
            return Collections.eq(o, this.k) ? this.v : null;
        }
        
        @Override
        public Set<K> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.singleton(this.k);
            }
            return this.keySet;
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = (Set<Map.Entry<K, V>>)Collections.singleton(new SimpleImmutableEntry<K, V>(this.k, this.v));
            }
            return this.entrySet;
        }
        
        @Override
        public Collection<V> values() {
            if (this.values == null) {
                this.values = Collections.singleton(this.v);
            }
            return this.values;
        }
        
        @Override
        public V getOrDefault(final Object o, final V v) {
            return Collections.eq(o, this.k) ? this.v : v;
        }
        
        @Override
        public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
            biConsumer.accept(this.k, this.v);
        }
        
        @Override
        public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V putIfAbsent(final K k, final V v) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean replace(final K k, final V v, final V v2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V replace(final K k, final V v) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V computeIfAbsent(final K k, final Function<? super K, ? extends V> function) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V computeIfPresent(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V compute(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V merge(final K k, final V v, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
    }
    
    private static class SingletonSet<E> extends AbstractSet<E> implements Serializable
    {
        private static final long serialVersionUID = 3193687207550431679L;
        private final E element;
        
        SingletonSet(final E element) {
            this.element = element;
        }
        
        @Override
        public Iterator<E> iterator() {
            return Collections.singletonIterator(this.element);
        }
        
        @Override
        public int size() {
            return 1;
        }
        
        @Override
        public boolean contains(final Object o) {
            return Collections.eq(o, this.element);
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            consumer.accept(this.element);
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return Collections.singletonSpliterator(this.element);
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            throw new UnsupportedOperationException();
        }
    }
    
    static class SynchronizedCollection<E> implements Collection<E>, Serializable
    {
        private static final long serialVersionUID = 3053995032091335093L;
        final Collection<E> c;
        final Object mutex;
        
        SynchronizedCollection(final Collection<E> collection) {
            this.c = Objects.requireNonNull(collection);
            this.mutex = this;
        }
        
        SynchronizedCollection(final Collection<E> collection, final Object o) {
            this.c = Objects.requireNonNull(collection);
            this.mutex = Objects.requireNonNull(o);
        }
        
        @Override
        public int size() {
            synchronized (this.mutex) {
                return this.c.size();
            }
        }
        
        @Override
        public boolean isEmpty() {
            synchronized (this.mutex) {
                return this.c.isEmpty();
            }
        }
        
        @Override
        public boolean contains(final Object o) {
            synchronized (this.mutex) {
                return this.c.contains(o);
            }
        }
        
        @Override
        public Object[] toArray() {
            synchronized (this.mutex) {
                return this.c.toArray();
            }
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            synchronized (this.mutex) {
                return this.c.toArray(array);
            }
        }
        
        @Override
        public Iterator<E> iterator() {
            return this.c.iterator();
        }
        
        @Override
        public boolean add(final E e) {
            synchronized (this.mutex) {
                return this.c.add(e);
            }
        }
        
        @Override
        public boolean remove(final Object o) {
            synchronized (this.mutex) {
                return this.c.remove(o);
            }
        }
        
        @Override
        public boolean containsAll(final Collection<?> collection) {
            synchronized (this.mutex) {
                return this.c.containsAll(collection);
            }
        }
        
        @Override
        public boolean addAll(final Collection<? extends E> collection) {
            synchronized (this.mutex) {
                return this.c.addAll(collection);
            }
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            synchronized (this.mutex) {
                return this.c.removeAll(collection);
            }
        }
        
        @Override
        public boolean retainAll(final Collection<?> collection) {
            synchronized (this.mutex) {
                return this.c.retainAll(collection);
            }
        }
        
        @Override
        public void clear() {
            synchronized (this.mutex) {
                this.c.clear();
            }
        }
        
        @Override
        public String toString() {
            synchronized (this.mutex) {
                return this.c.toString();
            }
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            synchronized (this.mutex) {
                this.c.forEach(consumer);
            }
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            synchronized (this.mutex) {
                return this.c.removeIf(predicate);
            }
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return this.c.spliterator();
        }
        
        @Override
        public Stream<E> stream() {
            return this.c.stream();
        }
        
        @Override
        public Stream<E> parallelStream() {
            return this.c.parallelStream();
        }
        
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.mutex) {
                objectOutputStream.defaultWriteObject();
            }
        }
    }
    
    static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E>
    {
        private static final long serialVersionUID = -7754090372962971524L;
        final List<E> list;
        
        SynchronizedList(final List<E> list) {
            super(list);
            this.list = list;
        }
        
        SynchronizedList(final List<E> list, final Object o) {
            super(list, o);
            this.list = list;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            synchronized (this.mutex) {
                return this.list.equals(o);
            }
        }
        
        @Override
        public int hashCode() {
            synchronized (this.mutex) {
                return this.list.hashCode();
            }
        }
        
        @Override
        public E get(final int n) {
            synchronized (this.mutex) {
                return this.list.get(n);
            }
        }
        
        @Override
        public E set(final int n, final E e) {
            synchronized (this.mutex) {
                return this.list.set(n, e);
            }
        }
        
        @Override
        public void add(final int n, final E e) {
            synchronized (this.mutex) {
                this.list.add(n, e);
            }
        }
        
        @Override
        public E remove(final int n) {
            synchronized (this.mutex) {
                return this.list.remove(n);
            }
        }
        
        @Override
        public int indexOf(final Object o) {
            synchronized (this.mutex) {
                return this.list.indexOf(o);
            }
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            synchronized (this.mutex) {
                return this.list.lastIndexOf(o);
            }
        }
        
        @Override
        public boolean addAll(final int n, final Collection<? extends E> collection) {
            synchronized (this.mutex) {
                return this.list.addAll(n, collection);
            }
        }
        
        @Override
        public ListIterator<E> listIterator() {
            return this.list.listIterator();
        }
        
        @Override
        public ListIterator<E> listIterator(final int n) {
            return this.list.listIterator(n);
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            synchronized (this.mutex) {
                return new SynchronizedList((List<Object>)this.list.subList(n, n2), this.mutex);
            }
        }
        
        @Override
        public void replaceAll(final UnaryOperator<E> unaryOperator) {
            synchronized (this.mutex) {
                this.list.replaceAll(unaryOperator);
            }
        }
        
        @Override
        public void sort(final Comparator<? super E> comparator) {
            synchronized (this.mutex) {
                this.list.sort(comparator);
            }
        }
        
        private Object readResolve() {
            return (this.list instanceof RandomAccess) ? new SynchronizedRandomAccessList<E>(this.list) : this;
        }
    }
    
    static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess
    {
        private static final long serialVersionUID = 1530674583602358482L;
        
        SynchronizedRandomAccessList(final List<E> list) {
            super(list);
        }
        
        SynchronizedRandomAccessList(final List<E> list, final Object o) {
            super(list, o);
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            synchronized (this.mutex) {
                return new SynchronizedRandomAccessList((List<Object>)this.list.subList(n, n2), this.mutex);
            }
        }
        
        private Object writeReplace() {
            return new SynchronizedList((List<Object>)this.list);
        }
    }
    
    private static class SynchronizedMap<K, V> implements Map<K, V>, Serializable
    {
        private static final long serialVersionUID = 1978198479659022715L;
        private final Map<K, V> m;
        final Object mutex;
        private transient Set<K> keySet;
        private transient Set<Entry<K, V>> entrySet;
        private transient Collection<V> values;
        
        SynchronizedMap(final Map<K, V> map) {
            this.m = Objects.requireNonNull(map);
            this.mutex = this;
        }
        
        SynchronizedMap(final Map<K, V> m, final Object mutex) {
            this.m = m;
            this.mutex = mutex;
        }
        
        @Override
        public int size() {
            synchronized (this.mutex) {
                return this.m.size();
            }
        }
        
        @Override
        public boolean isEmpty() {
            synchronized (this.mutex) {
                return this.m.isEmpty();
            }
        }
        
        @Override
        public boolean containsKey(final Object o) {
            synchronized (this.mutex) {
                return this.m.containsKey(o);
            }
        }
        
        @Override
        public boolean containsValue(final Object o) {
            synchronized (this.mutex) {
                return this.m.containsValue(o);
            }
        }
        
        @Override
        public V get(final Object o) {
            synchronized (this.mutex) {
                return this.m.get(o);
            }
        }
        
        @Override
        public V put(final K k, final V v) {
            synchronized (this.mutex) {
                return this.m.put(k, v);
            }
        }
        
        @Override
        public V remove(final Object o) {
            synchronized (this.mutex) {
                return this.m.remove(o);
            }
        }
        
        @Override
        public void putAll(final Map<? extends K, ? extends V> map) {
            synchronized (this.mutex) {
                this.m.putAll(map);
            }
        }
        
        @Override
        public void clear() {
            synchronized (this.mutex) {
                this.m.clear();
            }
        }
        
        @Override
        public Set<K> keySet() {
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = new SynchronizedSet<K>(this.m.keySet(), this.mutex);
                }
                return this.keySet;
            }
        }
        
        @Override
        public Set<Entry<K, V>> entrySet() {
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = new SynchronizedSet<Entry<K, V>>(this.m.entrySet(), this.mutex);
                }
                return this.entrySet;
            }
        }
        
        @Override
        public Collection<V> values() {
            synchronized (this.mutex) {
                if (this.values == null) {
                    this.values = new SynchronizedCollection<V>(this.m.values(), this.mutex);
                }
                return this.values;
            }
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            synchronized (this.mutex) {
                return this.m.equals(o);
            }
        }
        
        @Override
        public int hashCode() {
            synchronized (this.mutex) {
                return this.m.hashCode();
            }
        }
        
        @Override
        public String toString() {
            synchronized (this.mutex) {
                return this.m.toString();
            }
        }
        
        @Override
        public V getOrDefault(final Object o, final V v) {
            synchronized (this.mutex) {
                return this.m.getOrDefault(o, v);
            }
        }
        
        @Override
        public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
            synchronized (this.mutex) {
                this.m.forEach(biConsumer);
            }
        }
        
        @Override
        public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            synchronized (this.mutex) {
                this.m.replaceAll(biFunction);
            }
        }
        
        @Override
        public V putIfAbsent(final K k, final V v) {
            synchronized (this.mutex) {
                return this.m.putIfAbsent(k, v);
            }
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            synchronized (this.mutex) {
                return this.m.remove(o, o2);
            }
        }
        
        @Override
        public boolean replace(final K k, final V v, final V v2) {
            synchronized (this.mutex) {
                return this.m.replace(k, v, v2);
            }
        }
        
        @Override
        public V replace(final K k, final V v) {
            synchronized (this.mutex) {
                return this.m.replace(k, v);
            }
        }
        
        @Override
        public V computeIfAbsent(final K k, final Function<? super K, ? extends V> function) {
            synchronized (this.mutex) {
                return this.m.computeIfAbsent(k, function);
            }
        }
        
        @Override
        public V computeIfPresent(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            synchronized (this.mutex) {
                return this.m.computeIfPresent(k, biFunction);
            }
        }
        
        @Override
        public V compute(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            synchronized (this.mutex) {
                return this.m.compute(k, biFunction);
            }
        }
        
        @Override
        public V merge(final K k, final V v, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
            synchronized (this.mutex) {
                return this.m.merge(k, v, biFunction);
            }
        }
        
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.mutex) {
                objectOutputStream.defaultWriteObject();
            }
        }
    }
    
    static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E>
    {
        private static final long serialVersionUID = 487447009682186044L;
        
        SynchronizedSet(final Set<E> set) {
            super(set);
        }
        
        SynchronizedSet(final Set<E> set, final Object o) {
            super(set, o);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            synchronized (this.mutex) {
                return this.c.equals(o);
            }
        }
        
        @Override
        public int hashCode() {
            synchronized (this.mutex) {
                return this.c.hashCode();
            }
        }
    }
    
    static class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V>
    {
        private static final long serialVersionUID = 699392247599746807L;
        private final NavigableMap<K, V> nm;
        
        SynchronizedNavigableMap(final NavigableMap<K, V> nm) {
            super(nm);
            this.nm = nm;
        }
        
        SynchronizedNavigableMap(final NavigableMap<K, V> nm, final Object o) {
            super(nm, o);
            this.nm = nm;
        }
        
        @Override
        public Map.Entry<K, V> lowerEntry(final K k) {
            synchronized (this.mutex) {
                return this.nm.lowerEntry(k);
            }
        }
        
        @Override
        public K lowerKey(final K k) {
            synchronized (this.mutex) {
                return this.nm.lowerKey(k);
            }
        }
        
        @Override
        public Map.Entry<K, V> floorEntry(final K k) {
            synchronized (this.mutex) {
                return this.nm.floorEntry(k);
            }
        }
        
        @Override
        public K floorKey(final K k) {
            synchronized (this.mutex) {
                return this.nm.floorKey(k);
            }
        }
        
        @Override
        public Map.Entry<K, V> ceilingEntry(final K k) {
            synchronized (this.mutex) {
                return this.nm.ceilingEntry(k);
            }
        }
        
        @Override
        public K ceilingKey(final K k) {
            synchronized (this.mutex) {
                return this.nm.ceilingKey(k);
            }
        }
        
        @Override
        public Map.Entry<K, V> higherEntry(final K k) {
            synchronized (this.mutex) {
                return this.nm.higherEntry(k);
            }
        }
        
        @Override
        public K higherKey(final K k) {
            synchronized (this.mutex) {
                return this.nm.higherKey(k);
            }
        }
        
        @Override
        public Map.Entry<K, V> firstEntry() {
            synchronized (this.mutex) {
                return this.nm.firstEntry();
            }
        }
        
        @Override
        public Map.Entry<K, V> lastEntry() {
            synchronized (this.mutex) {
                return this.nm.lastEntry();
            }
        }
        
        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            synchronized (this.mutex) {
                return this.nm.pollFirstEntry();
            }
        }
        
        @Override
        public Map.Entry<K, V> pollLastEntry() {
            synchronized (this.mutex) {
                return this.nm.pollLastEntry();
            }
        }
        
        @Override
        public NavigableMap<K, V> descendingMap() {
            synchronized (this.mutex) {
                return new SynchronizedNavigableMap((NavigableMap<Object, Object>)this.nm.descendingMap(), this.mutex);
            }
        }
        
        @Override
        public NavigableSet<K> keySet() {
            return this.navigableKeySet();
        }
        
        @Override
        public NavigableSet<K> navigableKeySet() {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet<K>(this.nm.navigableKeySet(), this.mutex);
            }
        }
        
        @Override
        public NavigableSet<K> descendingKeySet() {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet<K>(this.nm.descendingKeySet(), this.mutex);
            }
        }
        
        @Override
        public SortedMap<K, V> subMap(final K k, final K i) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableMap((NavigableMap<Object, Object>)this.nm.subMap(k, true, i, false), this.mutex);
            }
        }
        
        @Override
        public SortedMap<K, V> headMap(final K k) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableMap((NavigableMap<Object, Object>)this.nm.headMap(k, false), this.mutex);
            }
        }
        
        @Override
        public SortedMap<K, V> tailMap(final K k) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableMap((NavigableMap<Object, Object>)this.nm.tailMap(k, true), this.mutex);
            }
        }
        
        @Override
        public NavigableMap<K, V> subMap(final K k, final boolean b, final K i, final boolean b2) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableMap((NavigableMap<Object, Object>)this.nm.subMap(k, b, i, b2), this.mutex);
            }
        }
        
        @Override
        public NavigableMap<K, V> headMap(final K k, final boolean b) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableMap((NavigableMap<Object, Object>)this.nm.headMap(k, b), this.mutex);
            }
        }
        
        @Override
        public NavigableMap<K, V> tailMap(final K k, final boolean b) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableMap((NavigableMap<Object, Object>)this.nm.tailMap(k, b), this.mutex);
            }
        }
    }
    
    static class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E>
    {
        private static final long serialVersionUID = -5505529816273629798L;
        private final NavigableSet<E> ns;
        
        SynchronizedNavigableSet(final NavigableSet<E> ns) {
            super(ns);
            this.ns = ns;
        }
        
        SynchronizedNavigableSet(final NavigableSet<E> ns, final Object o) {
            super(ns, o);
            this.ns = ns;
        }
        
        @Override
        public E lower(final E e) {
            synchronized (this.mutex) {
                return this.ns.lower(e);
            }
        }
        
        @Override
        public E floor(final E e) {
            synchronized (this.mutex) {
                return this.ns.floor(e);
            }
        }
        
        @Override
        public E ceiling(final E e) {
            synchronized (this.mutex) {
                return this.ns.ceiling(e);
            }
        }
        
        @Override
        public E higher(final E e) {
            synchronized (this.mutex) {
                return this.ns.higher(e);
            }
        }
        
        @Override
        public E pollFirst() {
            synchronized (this.mutex) {
                return this.ns.pollFirst();
            }
        }
        
        @Override
        public E pollLast() {
            synchronized (this.mutex) {
                return this.ns.pollLast();
            }
        }
        
        @Override
        public NavigableSet<E> descendingSet() {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet((NavigableSet<Object>)this.ns.descendingSet(), this.mutex);
            }
        }
        
        @Override
        public Iterator<E> descendingIterator() {
            synchronized (this.mutex) {
                return this.descendingSet().iterator();
            }
        }
        
        @Override
        public NavigableSet<E> subSet(final E e, final E e2) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet((NavigableSet<Object>)this.ns.subSet(e, true, e2, false), this.mutex);
            }
        }
        
        @Override
        public NavigableSet<E> headSet(final E e) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet((NavigableSet<Object>)this.ns.headSet(e, false), this.mutex);
            }
        }
        
        @Override
        public NavigableSet<E> tailSet(final E e) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet((NavigableSet<Object>)this.ns.tailSet(e, true), this.mutex);
            }
        }
        
        @Override
        public NavigableSet<E> subSet(final E e, final boolean b, final E e2, final boolean b2) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet((NavigableSet<Object>)this.ns.subSet(e, b, e2, b2), this.mutex);
            }
        }
        
        @Override
        public NavigableSet<E> headSet(final E e, final boolean b) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet((NavigableSet<Object>)this.ns.headSet(e, b), this.mutex);
            }
        }
        
        @Override
        public NavigableSet<E> tailSet(final E e, final boolean b) {
            synchronized (this.mutex) {
                return new SynchronizedNavigableSet((NavigableSet<Object>)this.ns.tailSet(e, b), this.mutex);
            }
        }
    }
    
    static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E>
    {
        private static final long serialVersionUID = 8695801310862127406L;
        private final SortedSet<E> ss;
        
        SynchronizedSortedSet(final SortedSet<E> ss) {
            super(ss);
            this.ss = ss;
        }
        
        SynchronizedSortedSet(final SortedSet<E> ss, final Object o) {
            super(ss, o);
            this.ss = ss;
        }
        
        @Override
        public Comparator<? super E> comparator() {
            synchronized (this.mutex) {
                return this.ss.comparator();
            }
        }
        
        @Override
        public SortedSet<E> subSet(final E e, final E e2) {
            synchronized (this.mutex) {
                return new SynchronizedSortedSet((SortedSet<Object>)this.ss.subSet(e, e2), this.mutex);
            }
        }
        
        @Override
        public SortedSet<E> headSet(final E e) {
            synchronized (this.mutex) {
                return new SynchronizedSortedSet((SortedSet<Object>)this.ss.headSet(e), this.mutex);
            }
        }
        
        @Override
        public SortedSet<E> tailSet(final E e) {
            synchronized (this.mutex) {
                return new SynchronizedSortedSet((SortedSet<Object>)this.ss.tailSet(e), this.mutex);
            }
        }
        
        @Override
        public E first() {
            synchronized (this.mutex) {
                return this.ss.first();
            }
        }
        
        @Override
        public E last() {
            synchronized (this.mutex) {
                return this.ss.last();
            }
        }
    }
    
    static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V>
    {
        private static final long serialVersionUID = -8798146769416483793L;
        private final SortedMap<K, V> sm;
        
        SynchronizedSortedMap(final SortedMap<K, V> sm) {
            super(sm);
            this.sm = sm;
        }
        
        SynchronizedSortedMap(final SortedMap<K, V> sm, final Object o) {
            super(sm, o);
            this.sm = sm;
        }
        
        @Override
        public Comparator<? super K> comparator() {
            synchronized (this.mutex) {
                return this.sm.comparator();
            }
        }
        
        @Override
        public SortedMap<K, V> subMap(final K k, final K i) {
            synchronized (this.mutex) {
                return new SynchronizedSortedMap((SortedMap<Object, Object>)this.sm.subMap(k, i), this.mutex);
            }
        }
        
        @Override
        public SortedMap<K, V> headMap(final K k) {
            synchronized (this.mutex) {
                return new SynchronizedSortedMap((SortedMap<Object, Object>)this.sm.headMap(k), this.mutex);
            }
        }
        
        @Override
        public SortedMap<K, V> tailMap(final K k) {
            synchronized (this.mutex) {
                return new SynchronizedSortedMap((SortedMap<Object, Object>)this.sm.tailMap(k), this.mutex);
            }
        }
        
        @Override
        public K firstKey() {
            synchronized (this.mutex) {
                return this.sm.firstKey();
            }
        }
        
        @Override
        public K lastKey() {
            synchronized (this.mutex) {
                return this.sm.lastKey();
            }
        }
    }
    
    static class UnmodifiableCollection<E> implements Collection<E>, Serializable
    {
        private static final long serialVersionUID = 1820017752578914078L;
        final Collection<? extends E> c;
        
        UnmodifiableCollection(final Collection<? extends E> c) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.c = c;
        }
        
        @Override
        public int size() {
            return this.c.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.c.isEmpty();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.c.contains(o);
        }
        
        @Override
        public Object[] toArray() {
            return this.c.toArray();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            return this.c.toArray(array);
        }
        
        @Override
        public String toString() {
            return this.c.toString();
        }
        
        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private final Iterator<? extends E> i = UnmodifiableCollection.this.c.iterator();
                
                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }
                
                @Override
                public E next() {
                    return (E)this.i.next();
                }
                
                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public void forEachRemaining(final Consumer<? super E> consumer) {
                    this.i.forEachRemaining(consumer);
                }
            };
        }
        
        @Override
        public boolean add(final E e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean containsAll(final Collection<?> collection) {
            return this.c.containsAll(collection);
        }
        
        @Override
        public boolean addAll(final Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection<?> collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            this.c.forEach(consumer);
        }
        
        @Override
        public boolean removeIf(final Predicate<? super E> predicate) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return (Spliterator<E>)this.c.spliterator();
        }
        
        @Override
        public Stream<E> stream() {
            return (Stream<E>)this.c.stream();
        }
        
        @Override
        public Stream<E> parallelStream() {
            return (Stream<E>)this.c.parallelStream();
        }
    }
    
    static class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E>
    {
        private static final long serialVersionUID = -283967356065247728L;
        final List<? extends E> list;
        
        UnmodifiableList(final List<? extends E> list) {
            super(list);
            this.list = list;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || this.list.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.list.hashCode();
        }
        
        @Override
        public E get(final int n) {
            return (E)this.list.get(n);
        }
        
        @Override
        public E set(final int n, final E e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void add(final int n, final E e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public E remove(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int indexOf(final Object o) {
            return this.list.indexOf(o);
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            return this.list.lastIndexOf(o);
        }
        
        @Override
        public boolean addAll(final int n, final Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void replaceAll(final UnaryOperator<E> unaryOperator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void sort(final Comparator<? super E> comparator) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ListIterator<E> listIterator() {
            return this.listIterator(0);
        }
        
        @Override
        public ListIterator<E> listIterator(final int n) {
            return new ListIterator<E>() {
                private final ListIterator<? extends E> i = UnmodifiableList.this.list.listIterator(n);
                
                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }
                
                @Override
                public E next() {
                    return (E)this.i.next();
                }
                
                @Override
                public boolean hasPrevious() {
                    return this.i.hasPrevious();
                }
                
                @Override
                public E previous() {
                    return (E)this.i.previous();
                }
                
                @Override
                public int nextIndex() {
                    return this.i.nextIndex();
                }
                
                @Override
                public int previousIndex() {
                    return this.i.previousIndex();
                }
                
                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public void set(final E e) {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public void add(final E e) {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public void forEachRemaining(final Consumer<? super E> consumer) {
                    this.i.forEachRemaining(consumer);
                }
            };
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            return new UnmodifiableList(this.list.subList(n, n2));
        }
        
        private Object readResolve() {
            return (this.list instanceof RandomAccess) ? new UnmodifiableRandomAccessList<E>(this.list) : this;
        }
    }
    
    static class UnmodifiableRandomAccessList<E> extends UnmodifiableList<E> implements RandomAccess
    {
        private static final long serialVersionUID = -2542308836966382001L;
        
        UnmodifiableRandomAccessList(final List<? extends E> list) {
            super(list);
        }
        
        @Override
        public List<E> subList(final int n, final int n2) {
            return new UnmodifiableRandomAccessList(this.list.subList(n, n2));
        }
        
        private Object writeReplace() {
            return new UnmodifiableList(this.list);
        }
    }
    
    private static class UnmodifiableMap<K, V> implements Map<K, V>, Serializable
    {
        private static final long serialVersionUID = -1034234728574286014L;
        private final Map<? extends K, ? extends V> m;
        private transient Set<K> keySet;
        private transient Set<Entry<K, V>> entrySet;
        private transient Collection<V> values;
        
        UnmodifiableMap(final Map<? extends K, ? extends V> m) {
            if (m == null) {
                throw new NullPointerException();
            }
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
        public boolean containsKey(final Object o) {
            return this.m.containsKey(o);
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return this.m.containsValue(o);
        }
        
        @Override
        public V get(final Object o) {
            return (V)this.m.get(o);
        }
        
        @Override
        public V put(final K k, final V v) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putAll(final Map<? extends K, ? extends V> map) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Set<K> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.m.keySet());
            }
            return this.keySet;
        }
        
        @Override
        public Set<Entry<K, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = (Set<Entry<K, V>>)new UnmodifiableEntrySet(this.m.entrySet());
            }
            return this.entrySet;
        }
        
        @Override
        public Collection<V> values() {
            if (this.values == null) {
                this.values = Collections.unmodifiableCollection(this.m.values());
            }
            return this.values;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || this.m.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.m.hashCode();
        }
        
        @Override
        public String toString() {
            return this.m.toString();
        }
        
        @Override
        public V getOrDefault(final Object o, final V v) {
            return (V)this.m.getOrDefault(o, (V)v);
        }
        
        @Override
        public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
            this.m.forEach(biConsumer);
        }
        
        @Override
        public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V putIfAbsent(final K k, final V v) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(final Object o, final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean replace(final K k, final V v, final V v2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V replace(final K k, final V v) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V computeIfAbsent(final K k, final Function<? super K, ? extends V> function) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V computeIfPresent(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V compute(final K k, final BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public V merge(final K k, final V v, final BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }
        
        static class UnmodifiableEntrySet<K, V> extends UnmodifiableSet<Entry<K, V>>
        {
            private static final long serialVersionUID = 7854390611657943733L;
            
            UnmodifiableEntrySet(final Set<? extends Entry<? extends K, ? extends V>> set) {
                super(set);
            }
            
            static <K, V> Consumer<Entry<K, V>> entryConsumer(final Consumer<? super Entry<K, V>> consumer) {
                return entry -> consumer.accept((Object)new UnmodifiableEntry(entry));
            }
            
            @Override
            public void forEach(final Consumer<? super Entry<K, V>> consumer) {
                Objects.requireNonNull(consumer);
                this.c.forEach((Consumer<? super Object>)entryConsumer((Consumer<? super Entry<Object, Object>>)consumer));
            }
            
            @Override
            public Spliterator<Entry<K, V>> spliterator() {
                return new UnmodifiableEntrySetSpliterator<K, V>((Spliterator<Entry<K, V>>)this.c.spliterator());
            }
            
            @Override
            public Stream<Entry<K, V>> stream() {
                return StreamSupport.stream(this.spliterator(), false);
            }
            
            @Override
            public Stream<Entry<K, V>> parallelStream() {
                return StreamSupport.stream(this.spliterator(), true);
            }
            
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<Entry<K, V>>() {
                    private final Iterator<? extends Entry<? extends K, ? extends V>> i = UnmodifiableEntrySet.this.c.iterator();
                    
                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                    
                    @Override
                    public Entry<K, V> next() {
                        return new UnmodifiableEntry<K, V>((Entry<? extends K, ? extends V>)this.i.next());
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            
            @Override
            public Object[] toArray() {
                final Object[] array = this.c.toArray();
                for (int i = 0; i < array.length; ++i) {
                    array[i] = new UnmodifiableEntry((Entry<?, ?>)array[i]);
                }
                return array;
            }
            
            @Override
            public <T> T[] toArray(final T[] array) {
                final T[] array2 = this.c.toArray((array.length == 0) ? array : Arrays.copyOf(array, 0));
                for (int i = 0; i < array2.length; ++i) {
                    array2[i] = (T)new UnmodifiableEntry((Entry<?, ?>)array2[i]);
                }
                if (array2.length > array.length) {
                    return array2;
                }
                System.arraycopy(array2, 0, array, 0, array2.length);
                if (array.length > array2.length) {
                    array[array2.length] = null;
                }
                return array;
            }
            
            @Override
            public boolean contains(final Object o) {
                return o instanceof Entry && this.c.contains(new UnmodifiableEntry((Entry<?, ?>)o));
            }
            
            @Override
            public boolean containsAll(final Collection<?> collection) {
                final Iterator<?> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    if (!this.contains(iterator.next())) {
                        return false;
                    }
                }
                return true;
            }
            
            @Override
            public boolean equals(final Object o) {
                if (o == this) {
                    return true;
                }
                if (!(o instanceof Set)) {
                    return false;
                }
                final Set set = (Set)o;
                return set.size() == this.c.size() && this.containsAll(set);
            }
            
            private static class UnmodifiableEntry<K, V> implements Entry<K, V>
            {
                private Entry<? extends K, ? extends V> e;
                
                UnmodifiableEntry(final Entry<? extends K, ? extends V> entry) {
                    this.e = Objects.requireNonNull(entry);
                }
                
                @Override
                public K getKey() {
                    return (K)this.e.getKey();
                }
                
                @Override
                public V getValue() {
                    return (V)this.e.getValue();
                }
                
                @Override
                public V setValue(final V v) {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public int hashCode() {
                    return this.e.hashCode();
                }
                
                @Override
                public boolean equals(final Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (!(o instanceof Entry)) {
                        return false;
                    }
                    final Entry entry = (Entry)o;
                    return Collections.eq(this.e.getKey(), entry.getKey()) && Collections.eq(this.e.getValue(), entry.getValue());
                }
                
                @Override
                public String toString() {
                    return this.e.toString();
                }
            }
            
            static final class UnmodifiableEntrySetSpliterator<K, V> implements Spliterator<Entry<K, V>>
            {
                final Spliterator<Entry<K, V>> s;
                
                UnmodifiableEntrySetSpliterator(final Spliterator<Entry<K, V>> s) {
                    this.s = s;
                }
                
                @Override
                public boolean tryAdvance(final Consumer<? super Entry<K, V>> consumer) {
                    Objects.requireNonNull(consumer);
                    return this.s.tryAdvance((Consumer<? super Entry<K, V>>)UnmodifiableEntrySet.entryConsumer((Consumer<? super Entry<Object, Object>>)consumer));
                }
                
                @Override
                public void forEachRemaining(final Consumer<? super Entry<K, V>> consumer) {
                    Objects.requireNonNull(consumer);
                    this.s.forEachRemaining((Consumer<? super Entry<K, V>>)UnmodifiableEntrySet.entryConsumer((Consumer<? super Entry<Object, Object>>)consumer));
                }
                
                @Override
                public Spliterator<Entry<K, V>> trySplit() {
                    final Spliterator<Entry<K, V>> trySplit = this.s.trySplit();
                    return (Spliterator<Entry<K, V>>)((trySplit == null) ? null : new UnmodifiableEntrySetSpliterator<Object, Object>((Spliterator<Entry<Object, Object>>)trySplit));
                }
                
                @Override
                public long estimateSize() {
                    return this.s.estimateSize();
                }
                
                @Override
                public long getExactSizeIfKnown() {
                    return this.s.getExactSizeIfKnown();
                }
                
                @Override
                public int characteristics() {
                    return this.s.characteristics();
                }
                
                @Override
                public boolean hasCharacteristics(final int n) {
                    return this.s.hasCharacteristics(n);
                }
                
                @Override
                public Comparator<? super Entry<K, V>> getComparator() {
                    return this.s.getComparator();
                }
            }
        }
    }
    
    static class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E>, Serializable
    {
        private static final long serialVersionUID = -9215047833775013803L;
        
        UnmodifiableSet(final Set<? extends E> set) {
            super(set);
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || this.c.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.c.hashCode();
        }
    }
    
    static class UnmodifiableNavigableMap<K, V> extends UnmodifiableSortedMap<K, V> implements NavigableMap<K, V>, Serializable
    {
        private static final long serialVersionUID = -4858195264774772197L;
        private static final EmptyNavigableMap<?, ?> EMPTY_NAVIGABLE_MAP;
        private final NavigableMap<K, ? extends V> nm;
        
        UnmodifiableNavigableMap(final NavigableMap<K, ? extends V> nm) {
            super(nm);
            this.nm = nm;
        }
        
        @Override
        public K lowerKey(final K k) {
            return this.nm.lowerKey(k);
        }
        
        @Override
        public K floorKey(final K k) {
            return this.nm.floorKey(k);
        }
        
        @Override
        public K ceilingKey(final K k) {
            return this.nm.ceilingKey(k);
        }
        
        @Override
        public K higherKey(final K k) {
            return this.nm.higherKey(k);
        }
        
        @Override
        public Map.Entry<K, V> lowerEntry(final K k) {
            final Map.Entry<K, ? extends V> lowerEntry = this.nm.lowerEntry(k);
            return (null != lowerEntry) ? new UnmodifiableEntrySet.UnmodifiableEntry<K, V>(lowerEntry) : null;
        }
        
        @Override
        public Map.Entry<K, V> floorEntry(final K k) {
            final Map.Entry<K, ? extends V> floorEntry = this.nm.floorEntry(k);
            return (null != floorEntry) ? new UnmodifiableEntrySet.UnmodifiableEntry<K, V>(floorEntry) : null;
        }
        
        @Override
        public Map.Entry<K, V> ceilingEntry(final K k) {
            final Map.Entry<K, ? extends V> ceilingEntry = this.nm.ceilingEntry(k);
            return (null != ceilingEntry) ? new UnmodifiableEntrySet.UnmodifiableEntry<K, V>(ceilingEntry) : null;
        }
        
        @Override
        public Map.Entry<K, V> higherEntry(final K k) {
            final Map.Entry<K, ? extends V> higherEntry = this.nm.higherEntry(k);
            return (null != higherEntry) ? new UnmodifiableEntrySet.UnmodifiableEntry<K, V>(higherEntry) : null;
        }
        
        @Override
        public Map.Entry<K, V> firstEntry() {
            final Map.Entry<K, ? extends V> firstEntry = this.nm.firstEntry();
            return (null != firstEntry) ? new UnmodifiableEntrySet.UnmodifiableEntry<K, V>(firstEntry) : null;
        }
        
        @Override
        public Map.Entry<K, V> lastEntry() {
            final Map.Entry<K, ? extends V> lastEntry = this.nm.lastEntry();
            return (null != lastEntry) ? new UnmodifiableEntrySet.UnmodifiableEntry<K, V>(lastEntry) : null;
        }
        
        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Map.Entry<K, V> pollLastEntry() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public NavigableMap<K, V> descendingMap() {
            return Collections.unmodifiableNavigableMap(this.nm.descendingMap());
        }
        
        @Override
        public NavigableSet<K> navigableKeySet() {
            return Collections.unmodifiableNavigableSet(this.nm.navigableKeySet());
        }
        
        @Override
        public NavigableSet<K> descendingKeySet() {
            return Collections.unmodifiableNavigableSet(this.nm.descendingKeySet());
        }
        
        @Override
        public NavigableMap<K, V> subMap(final K k, final boolean b, final K i, final boolean b2) {
            return Collections.unmodifiableNavigableMap(this.nm.subMap(k, b, i, b2));
        }
        
        @Override
        public NavigableMap<K, V> headMap(final K k, final boolean b) {
            return Collections.unmodifiableNavigableMap(this.nm.headMap(k, b));
        }
        
        @Override
        public NavigableMap<K, V> tailMap(final K k, final boolean b) {
            return Collections.unmodifiableNavigableMap(this.nm.tailMap(k, b));
        }
        
        static {
            EMPTY_NAVIGABLE_MAP = new EmptyNavigableMap<Object, Object>();
        }
        
        private static class EmptyNavigableMap<K, V> extends UnmodifiableNavigableMap<K, V> implements Serializable
        {
            private static final long serialVersionUID = -2239321462712562324L;
            
            EmptyNavigableMap() {
                super(new TreeMap<Object, Object>());
            }
            
            @Override
            public NavigableSet<K> navigableKeySet() {
                return Collections.emptyNavigableSet();
            }
            
            private Object readResolve() {
                return UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
            }
        }
    }
    
    static class UnmodifiableSortedMap<K, V> extends UnmodifiableMap<K, V> implements SortedMap<K, V>, Serializable
    {
        private static final long serialVersionUID = -8806743815996713206L;
        private final SortedMap<K, ? extends V> sm;
        
        UnmodifiableSortedMap(final SortedMap<K, ? extends V> sm) {
            super(sm);
            this.sm = sm;
        }
        
        @Override
        public Comparator<? super K> comparator() {
            return this.sm.comparator();
        }
        
        @Override
        public SortedMap<K, V> subMap(final K k, final K i) {
            return new UnmodifiableSortedMap((SortedMap<Object, ?>)this.sm.subMap(k, i));
        }
        
        @Override
        public SortedMap<K, V> headMap(final K k) {
            return new UnmodifiableSortedMap((SortedMap<Object, ?>)this.sm.headMap(k));
        }
        
        @Override
        public SortedMap<K, V> tailMap(final K k) {
            return new UnmodifiableSortedMap((SortedMap<Object, ?>)this.sm.tailMap(k));
        }
        
        @Override
        public K firstKey() {
            return this.sm.firstKey();
        }
        
        @Override
        public K lastKey() {
            return this.sm.lastKey();
        }
    }
    
    static class UnmodifiableNavigableSet<E> extends UnmodifiableSortedSet<E> implements NavigableSet<E>, Serializable
    {
        private static final long serialVersionUID = -6027448201786391929L;
        private static final NavigableSet<?> EMPTY_NAVIGABLE_SET;
        private final NavigableSet<E> ns;
        
        UnmodifiableNavigableSet(final NavigableSet<E> ns) {
            super(ns);
            this.ns = ns;
        }
        
        @Override
        public E lower(final E e) {
            return this.ns.lower(e);
        }
        
        @Override
        public E floor(final E e) {
            return this.ns.floor(e);
        }
        
        @Override
        public E ceiling(final E e) {
            return this.ns.ceiling(e);
        }
        
        @Override
        public E higher(final E e) {
            return this.ns.higher(e);
        }
        
        @Override
        public E pollFirst() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public E pollLast() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public NavigableSet<E> descendingSet() {
            return new UnmodifiableNavigableSet((NavigableSet<Object>)this.ns.descendingSet());
        }
        
        @Override
        public Iterator<E> descendingIterator() {
            return this.descendingSet().iterator();
        }
        
        @Override
        public NavigableSet<E> subSet(final E e, final boolean b, final E e2, final boolean b2) {
            return new UnmodifiableNavigableSet((NavigableSet<Object>)this.ns.subSet(e, b, e2, b2));
        }
        
        @Override
        public NavigableSet<E> headSet(final E e, final boolean b) {
            return new UnmodifiableNavigableSet((NavigableSet<Object>)this.ns.headSet(e, b));
        }
        
        @Override
        public NavigableSet<E> tailSet(final E e, final boolean b) {
            return new UnmodifiableNavigableSet((NavigableSet<Object>)this.ns.tailSet(e, b));
        }
        
        static {
            EMPTY_NAVIGABLE_SET = new EmptyNavigableSet<Object>();
        }
        
        private static class EmptyNavigableSet<E> extends UnmodifiableNavigableSet<E> implements Serializable
        {
            private static final long serialVersionUID = -6291252904449939134L;
            
            public EmptyNavigableSet() {
                super(new TreeSet<Object>());
            }
            
            private Object readResolve() {
                return UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
            }
        }
    }
    
    static class UnmodifiableSortedSet<E> extends UnmodifiableSet<E> implements SortedSet<E>, Serializable
    {
        private static final long serialVersionUID = -4929149591599911165L;
        private final SortedSet<E> ss;
        
        UnmodifiableSortedSet(final SortedSet<E> ss) {
            super(ss);
            this.ss = ss;
        }
        
        @Override
        public Comparator<? super E> comparator() {
            return this.ss.comparator();
        }
        
        @Override
        public SortedSet<E> subSet(final E e, final E e2) {
            return new UnmodifiableSortedSet((SortedSet<Object>)this.ss.subSet(e, e2));
        }
        
        @Override
        public SortedSet<E> headSet(final E e) {
            return new UnmodifiableSortedSet((SortedSet<Object>)this.ss.headSet(e));
        }
        
        @Override
        public SortedSet<E> tailSet(final E e) {
            return new UnmodifiableSortedSet((SortedSet<Object>)this.ss.tailSet(e));
        }
        
        @Override
        public E first() {
            return this.ss.first();
        }
        
        @Override
        public E last() {
            return this.ss.last();
        }
    }
}
