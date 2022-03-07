package java.util;

import java.lang.reflect.*;
import java.io.*;
import java.util.function.*;

public class LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, Serializable
{
    transient int size;
    transient Node<E> first;
    transient Node<E> last;
    private static final long serialVersionUID = 876323262645176354L;
    
    public LinkedList() {
        this.size = 0;
    }
    
    public LinkedList(final Collection<? extends E> collection) {
        this();
        this.addAll(collection);
    }
    
    private void linkFirst(final E e) {
        final Node<E> first = this.first;
        final Node prev = new Node<E>(null, e, (Node<Object>)first);
        this.first = (Node<E>)prev;
        if (first == null) {
            this.last = (Node<E>)prev;
        }
        else {
            first.prev = (Node<E>)prev;
        }
        ++this.size;
        ++this.modCount;
    }
    
    void linkLast(final E e) {
        final Node<E> last = this.last;
        final Node next = new Node<E>((Node<Object>)last, e, null);
        this.last = (Node<E>)next;
        if (last == null) {
            this.first = (Node<E>)next;
        }
        else {
            last.next = (Node<E>)next;
        }
        ++this.size;
        ++this.modCount;
    }
    
    void linkBefore(final E e, final Node<E> node) {
        final Node<E> prev = node.prev;
        final Node next = new Node<E>(prev, e, node);
        node.prev = (Node<E>)next;
        if (prev == null) {
            this.first = (Node<E>)next;
        }
        else {
            prev.next = (Node<E>)next;
        }
        ++this.size;
        ++this.modCount;
    }
    
    private E unlinkFirst(final Node<E> node) {
        final E item = node.item;
        final Node<E> next = node.next;
        node.item = null;
        node.next = null;
        this.first = next;
        if (next == null) {
            this.last = null;
        }
        else {
            next.prev = null;
        }
        --this.size;
        ++this.modCount;
        return item;
    }
    
    private E unlinkLast(final Node<E> node) {
        final E item = node.item;
        final Node<E> prev = node.prev;
        node.item = null;
        node.prev = null;
        this.last = prev;
        if (prev == null) {
            this.first = null;
        }
        else {
            prev.next = null;
        }
        --this.size;
        ++this.modCount;
        return item;
    }
    
    E unlink(final Node<E> node) {
        final E item = node.item;
        final Node<E> next = node.next;
        final Node<E> prev = node.prev;
        if (prev == null) {
            this.first = next;
        }
        else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            this.last = prev;
        }
        else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        --this.size;
        ++this.modCount;
        return item;
    }
    
    @Override
    public E getFirst() {
        final Node<E> first = this.first;
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.item;
    }
    
    @Override
    public E getLast() {
        final Node<E> last = this.last;
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.item;
    }
    
    @Override
    public E removeFirst() {
        final Node<E> first = this.first;
        if (first == null) {
            throw new NoSuchElementException();
        }
        return this.unlinkFirst(first);
    }
    
    @Override
    public E removeLast() {
        final Node<E> last = this.last;
        if (last == null) {
            throw new NoSuchElementException();
        }
        return this.unlinkLast(last);
    }
    
    @Override
    public void addFirst(final E e) {
        this.linkFirst(e);
    }
    
    @Override
    public void addLast(final E e) {
        this.linkLast(e);
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.indexOf(o) != -1;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean add(final E e) {
        this.linkLast(e);
        return true;
    }
    
    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            for (Node<E> node = this.first; node != null; node = node.next) {
                if (node.item == null) {
                    this.unlink(node);
                    return true;
                }
            }
        }
        else {
            for (Node<E> node2 = this.first; node2 != null; node2 = node2.next) {
                if (o.equals(node2.item)) {
                    this.unlink(node2);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        return this.addAll(this.size, collection);
    }
    
    @Override
    public boolean addAll(final int n, final Collection<? extends E> collection) {
        this.checkPositionIndex(n);
        final Object[] array = collection.toArray();
        final int length = array.length;
        if (length == 0) {
            return false;
        }
        Node<E> node;
        Node<E> node2;
        if (n == this.size) {
            node = null;
            node2 = this.last;
        }
        else {
            node = this.node(n);
            node2 = node.prev;
        }
        final Object[] array2 = array;
        for (int length2 = array2.length, i = 0; i < length2; ++i) {
            final Node node3 = new Node<E>((Node<Object>)node2, array2[i], null);
            if (node2 == null) {
                this.first = (Node<E>)node3;
            }
            else {
                node2.next = (Node<E>)node3;
            }
            node2 = (Node<E>)node3;
        }
        if (node == null) {
            this.last = node2;
        }
        else {
            node2.next = node;
            node.prev = node2;
        }
        this.size += length;
        ++this.modCount;
        return true;
    }
    
    @Override
    public void clear() {
        Node<E> next;
        for (Node<E> first = this.first; first != null; first = next) {
            next = first.next;
            first.item = null;
            first.next = null;
            first.prev = null;
        }
        final Node<E> node = null;
        this.last = node;
        this.first = node;
        this.size = 0;
        ++this.modCount;
    }
    
    @Override
    public E get(final int n) {
        this.checkElementIndex(n);
        return this.node(n).item;
    }
    
    @Override
    public E set(final int n, final E item) {
        this.checkElementIndex(n);
        final Node<E> node = this.node(n);
        final E item2 = node.item;
        node.item = item;
        return item2;
    }
    
    @Override
    public void add(final int n, final E e) {
        this.checkPositionIndex(n);
        if (n == this.size) {
            this.linkLast(e);
        }
        else {
            this.linkBefore(e, this.node(n));
        }
    }
    
    @Override
    public E remove(final int n) {
        this.checkElementIndex(n);
        return this.unlink(this.node(n));
    }
    
    private boolean isElementIndex(final int n) {
        return n >= 0 && n < this.size;
    }
    
    private boolean isPositionIndex(final int n) {
        return n >= 0 && n <= this.size;
    }
    
    private String outOfBoundsMsg(final int n) {
        return "Index: " + n + ", Size: " + this.size;
    }
    
    private void checkElementIndex(final int n) {
        if (!this.isElementIndex(n)) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }
    }
    
    private void checkPositionIndex(final int n) {
        if (!this.isPositionIndex(n)) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }
    }
    
    Node<E> node(final int n) {
        if (n < this.size >> 1) {
            Node<E> node = this.first;
            for (int i = 0; i < n; ++i) {
                node = node.next;
            }
            return node;
        }
        Node<E> node2 = this.last;
        for (int j = this.size - 1; j > n; --j) {
            node2 = node2.prev;
        }
        return node2;
    }
    
    @Override
    public int indexOf(final Object o) {
        int n = 0;
        if (o == null) {
            for (Node<E> node = this.first; node != null; node = node.next) {
                if (node.item == null) {
                    return n;
                }
                ++n;
            }
        }
        else {
            for (Node<E> node2 = this.first; node2 != null; node2 = node2.next) {
                if (o.equals(node2.item)) {
                    return n;
                }
                ++n;
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        int size = this.size;
        if (o == null) {
            for (Node<E> node = this.last; node != null; node = node.prev) {
                --size;
                if (node.item == null) {
                    return size;
                }
            }
        }
        else {
            for (Node<E> node2 = this.last; node2 != null; node2 = node2.prev) {
                --size;
                if (o.equals(node2.item)) {
                    return size;
                }
            }
        }
        return -1;
    }
    
    @Override
    public E peek() {
        final Node<E> first = this.first;
        return (first == null) ? null : first.item;
    }
    
    @Override
    public E element() {
        return this.getFirst();
    }
    
    @Override
    public E poll() {
        final Node<E> first = this.first;
        return (E)((first == null) ? null : this.unlinkFirst((Node<Object>)first));
    }
    
    @Override
    public E remove() {
        return this.removeFirst();
    }
    
    @Override
    public boolean offer(final E e) {
        return this.add(e);
    }
    
    @Override
    public boolean offerFirst(final E e) {
        this.addFirst(e);
        return true;
    }
    
    @Override
    public boolean offerLast(final E e) {
        this.addLast(e);
        return true;
    }
    
    @Override
    public E peekFirst() {
        final Node<E> first = this.first;
        return (first == null) ? null : first.item;
    }
    
    @Override
    public E peekLast() {
        final Node<E> last = this.last;
        return (last == null) ? null : last.item;
    }
    
    @Override
    public E pollFirst() {
        final Node<E> first = this.first;
        return (E)((first == null) ? null : this.unlinkFirst((Node<Object>)first));
    }
    
    @Override
    public E pollLast() {
        final Node<E> last = this.last;
        return (E)((last == null) ? null : this.unlinkLast((Node<Object>)last));
    }
    
    @Override
    public void push(final E e) {
        this.addFirst(e);
    }
    
    @Override
    public E pop() {
        return this.removeFirst();
    }
    
    @Override
    public boolean removeFirstOccurrence(final Object o) {
        return this.remove(o);
    }
    
    @Override
    public boolean removeLastOccurrence(final Object o) {
        if (o == null) {
            for (Node<E> node = this.last; node != null; node = node.prev) {
                if (node.item == null) {
                    this.unlink(node);
                    return true;
                }
            }
        }
        else {
            for (Node<E> node2 = this.last; node2 != null; node2 = node2.prev) {
                if (o.equals(node2.item)) {
                    this.unlink(node2);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public ListIterator<E> listIterator(final int n) {
        this.checkPositionIndex(n);
        return new ListItr(n);
    }
    
    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }
    
    private LinkedList<E> superClone() {
        try {
            return (LinkedList)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    public Object clone() {
        final LinkedList<E> superClone;
        final LinkedList<E> list2;
        final LinkedList<E> list = list2 = (superClone = this.superClone());
        final Node<E> node = null;
        list2.last = node;
        superClone.first = node;
        list.size = 0;
        list.modCount = 0;
        for (Node<E> node2 = this.first; node2 != null; node2 = node2.next) {
            list.add((Object)node2.item);
        }
        return list;
    }
    
    @Override
    public Object[] toArray() {
        final Object[] array = new Object[this.size];
        int n = 0;
        for (Node<E> node = this.first; node != null; node = node.next) {
            array[n++] = node.item;
        }
        return array;
    }
    
    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < this.size) {
            array = (T[])Array.newInstance(array.getClass().getComponentType(), this.size);
        }
        int n = 0;
        final T[] array2 = array;
        for (Node<E> node = this.first; node != null; node = node.next) {
            array2[n++] = (T)node.item;
        }
        if (array.length > this.size) {
            array[this.size] = null;
        }
        return array;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        for (Node<E> node = this.first; node != null; node = node.next) {
            objectOutputStream.writeObject(node.item);
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        for (int int1 = objectInputStream.readInt(), i = 0; i < int1; ++i) {
            this.linkLast(objectInputStream.readObject());
        }
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new LLSpliterator<E>(this, -1, 0);
    }
    
    private class DescendingIterator implements Iterator<E>
    {
        private final ListItr itr;
        
        private DescendingIterator() {
            this.itr = new ListItr(LinkedList.this.size());
        }
        
        @Override
        public boolean hasNext() {
            return this.itr.hasPrevious();
        }
        
        @Override
        public E next() {
            return this.itr.previous();
        }
        
        @Override
        public void remove() {
            this.itr.remove();
        }
    }
    
    private class ListItr implements ListIterator<E>
    {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;
        private int expectedModCount;
        
        ListItr(final int nextIndex) {
            this.expectedModCount = LinkedList.this.modCount;
            this.next = ((nextIndex == LinkedList.this.size) ? null : LinkedList.this.node(nextIndex));
            this.nextIndex = nextIndex;
        }
        
        @Override
        public boolean hasNext() {
            return this.nextIndex < LinkedList.this.size;
        }
        
        @Override
        public E next() {
            this.checkForComodification();
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.next;
            this.next = this.next.next;
            ++this.nextIndex;
            return this.lastReturned.item;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.nextIndex > 0;
        }
        
        @Override
        public E previous() {
            this.checkForComodification();
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            final Node<E> node = (this.next == null) ? LinkedList.this.last : this.next.prev;
            this.next = node;
            this.lastReturned = node;
            --this.nextIndex;
            return this.lastReturned.item;
        }
        
        @Override
        public int nextIndex() {
            return this.nextIndex;
        }
        
        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }
        
        @Override
        public void remove() {
            this.checkForComodification();
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            final Node<E> next = this.lastReturned.next;
            LinkedList.this.unlink(this.lastReturned);
            if (this.next == this.lastReturned) {
                this.next = next;
            }
            else {
                --this.nextIndex;
            }
            this.lastReturned = null;
            ++this.expectedModCount;
        }
        
        @Override
        public void set(final E item) {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            this.lastReturned.item = item;
        }
        
        @Override
        public void add(final E e) {
            this.checkForComodification();
            this.lastReturned = null;
            if (this.next == null) {
                LinkedList.this.linkLast(e);
            }
            else {
                LinkedList.this.linkBefore(e, this.next);
            }
            ++this.nextIndex;
            ++this.expectedModCount;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            while (LinkedList.this.modCount == this.expectedModCount && this.nextIndex < LinkedList.this.size) {
                consumer.accept(this.next.item);
                this.lastReturned = this.next;
                this.next = this.next.next;
                ++this.nextIndex;
            }
            this.checkForComodification();
        }
        
        final void checkForComodification() {
            if (LinkedList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    private static class Node<E>
    {
        E item;
        Node<E> next;
        Node<E> prev;
        
        Node(final Node<E> prev, final E item, final Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
    
    static final class LLSpliterator<E> implements Spliterator<E>
    {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        final LinkedList<E> list;
        Node<E> current;
        int est;
        int expectedModCount;
        int batch;
        
        LLSpliterator(final LinkedList<E> list, final int est, final int expectedModCount) {
            this.list = list;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }
        
        final int getEst() {
            int est;
            if ((est = this.est) < 0) {
                final LinkedList<E> list;
                if ((list = this.list) == null) {
                    final boolean est2 = false;
                    this.est = (est2 ? 1 : 0);
                    est = (est2 ? 1 : 0);
                }
                else {
                    this.expectedModCount = list.modCount;
                    this.current = list.first;
                    final int size = list.size;
                    this.est = size;
                    est = size;
                }
            }
            return est;
        }
        
        @Override
        public long estimateSize() {
            return this.getEst();
        }
        
        @Override
        public Spliterator<E> trySplit() {
            final int est = this.getEst();
            Node<E> current;
            if (est > 1 && (current = this.current) != null) {
                int n = this.batch + 1024;
                if (n > est) {
                    n = est;
                }
                if (n > 33554432) {
                    n = 33554432;
                }
                final Object[] array = new Object[n];
                int batch = 0;
                do {
                    array[batch++] = current.item;
                } while ((current = current.next) != null && batch < n);
                this.current = current;
                this.batch = batch;
                this.est = est - batch;
                return (Spliterator<E>)Spliterators.spliterator(array, 0, batch, 16);
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            int est;
            Node<E> node;
            if ((est = this.getEst()) > 0 && (node = this.current) != null) {
                this.current = null;
                this.est = 0;
                do {
                    final E item = node.item;
                    node = node.next;
                    consumer.accept(item);
                } while (node != null && --est > 0);
            }
            if (this.list.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final Node<E> current;
            if (this.getEst() <= 0 || (current = this.current) == null) {
                return false;
            }
            --this.est;
            final E item = current.item;
            this.current = current.next;
            consumer.accept(item);
            if (this.list.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return true;
        }
        
        @Override
        public int characteristics() {
            return 16464;
        }
    }
}
