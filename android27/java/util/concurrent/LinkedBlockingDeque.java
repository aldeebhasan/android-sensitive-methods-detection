package java.util.concurrent;

import java.util.concurrent.locks.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.util.function.*;

public class LinkedBlockingDeque<E> extends AbstractQueue<E> implements BlockingDeque<E>, Serializable
{
    private static final long serialVersionUID = -387911632671998426L;
    transient Node<E> first;
    transient Node<E> last;
    private transient int count;
    private final int capacity;
    final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    
    public LinkedBlockingDeque() {
        this(Integer.MAX_VALUE);
    }
    
    public LinkedBlockingDeque(final int capacity) {
        this.lock = new ReentrantLock();
        this.notEmpty = this.lock.newCondition();
        this.notFull = this.lock.newCondition();
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }
    
    public LinkedBlockingDeque(final Collection<? extends E> collection) {
        this(Integer.MAX_VALUE);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (final E next : collection) {
                if (next == null) {
                    throw new NullPointerException();
                }
                if (!this.linkLast(new Node<E>(next))) {
                    throw new IllegalStateException("Deque full");
                }
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    private boolean linkFirst(final Node<E> prev) {
        if (this.count >= this.capacity) {
            return false;
        }
        final Node<E> first = this.first;
        prev.next = first;
        this.first = prev;
        if (this.last == null) {
            this.last = prev;
        }
        else {
            first.prev = prev;
        }
        ++this.count;
        this.notEmpty.signal();
        return true;
    }
    
    private boolean linkLast(final Node<E> next) {
        if (this.count >= this.capacity) {
            return false;
        }
        final Node<E> last = this.last;
        next.prev = last;
        this.last = next;
        if (this.first == null) {
            this.first = next;
        }
        else {
            last.next = next;
        }
        ++this.count;
        this.notEmpty.signal();
        return true;
    }
    
    private E unlinkFirst() {
        final Node<E> first = this.first;
        if (first == null) {
            return null;
        }
        final Node<E> next = first.next;
        final E item = first.item;
        first.item = null;
        first.next = first;
        if ((this.first = next) == null) {
            this.last = null;
        }
        else {
            next.prev = null;
        }
        --this.count;
        this.notFull.signal();
        return item;
    }
    
    private E unlinkLast() {
        final Node<E> last = this.last;
        if (last == null) {
            return null;
        }
        final Node<E> prev = last.prev;
        final E item = last.item;
        last.item = null;
        last.prev = last;
        if ((this.last = prev) == null) {
            this.first = null;
        }
        else {
            prev.next = null;
        }
        --this.count;
        this.notFull.signal();
        return item;
    }
    
    void unlink(final Node<E> node) {
        final Node<E> prev = node.prev;
        final Node<E> next = node.next;
        if (prev == null) {
            this.unlinkFirst();
        }
        else if (next == null) {
            this.unlinkLast();
        }
        else {
            prev.next = next;
            next.prev = prev;
            node.item = null;
            --this.count;
            this.notFull.signal();
        }
    }
    
    @Override
    public void addFirst(final E e) {
        if (!this.offerFirst(e)) {
            throw new IllegalStateException("Deque full");
        }
    }
    
    @Override
    public void addLast(final E e) {
        if (!this.offerLast(e)) {
            throw new IllegalStateException("Deque full");
        }
    }
    
    @Override
    public boolean offerFirst(final E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        final Node<E> node = new Node<E>(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.linkFirst(node);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean offerLast(final E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        final Node<E> node = new Node<E>(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.linkLast(node);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void putFirst(final E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        final Node<E> node = new Node<E>(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            while (!this.linkFirst(node)) {
                this.notFull.await();
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void putLast(final E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        final Node<E> node = new Node<E>(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            while (!this.linkLast(node)) {
                this.notFull.await();
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean offerFirst(final E e, final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        final Node<E> node = new Node<E>(e);
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (!this.linkFirst(node)) {
                if (n2 <= 0L) {
                    return false;
                }
                n2 = this.notFull.awaitNanos(n2);
            }
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean offerLast(final E e, final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        final Node<E> node = new Node<E>(e);
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (!this.linkLast(node)) {
                if (n2 <= 0L) {
                    return false;
                }
                n2 = this.notFull.awaitNanos(n2);
            }
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E removeFirst() {
        final E pollFirst = this.pollFirst();
        if (pollFirst == null) {
            throw new NoSuchElementException();
        }
        return pollFirst;
    }
    
    @Override
    public E removeLast() {
        final E pollLast = this.pollLast();
        if (pollLast == null) {
            throw new NoSuchElementException();
        }
        return pollLast;
    }
    
    @Override
    public E pollFirst() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.unlinkFirst();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E pollLast() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.unlinkLast();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E takeFirst() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            E unlinkFirst;
            while ((unlinkFirst = this.unlinkFirst()) == null) {
                this.notEmpty.await();
            }
            return unlinkFirst;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E takeLast() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            E unlinkLast;
            while ((unlinkLast = this.unlinkLast()) == null) {
                this.notEmpty.await();
            }
            return unlinkLast;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E pollFirst(final long n, final TimeUnit timeUnit) throws InterruptedException {
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            E unlinkFirst;
            while ((unlinkFirst = this.unlinkFirst()) == null) {
                if (n2 <= 0L) {
                    return null;
                }
                n2 = this.notEmpty.awaitNanos(n2);
            }
            return unlinkFirst;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E pollLast(final long n, final TimeUnit timeUnit) throws InterruptedException {
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            E unlinkLast;
            while ((unlinkLast = this.unlinkLast()) == null) {
                if (n2 <= 0L) {
                    return null;
                }
                n2 = this.notEmpty.awaitNanos(n2);
            }
            return unlinkLast;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E getFirst() {
        final E peekFirst = this.peekFirst();
        if (peekFirst == null) {
            throw new NoSuchElementException();
        }
        return peekFirst;
    }
    
    @Override
    public E getLast() {
        final E peekLast = this.peekLast();
        if (peekLast == null) {
            throw new NoSuchElementException();
        }
        return peekLast;
    }
    
    @Override
    public E peekFirst() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (this.first == null) ? null : this.first.item;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E peekLast() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (this.last == null) ? null : this.last.item;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean removeFirstOccurrence(final Object o) {
        if (o == null) {
            return false;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (Node<E> node = this.first; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    this.unlink(node);
                    return true;
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean removeLastOccurrence(final Object o) {
        if (o == null) {
            return false;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (Node<E> node = this.last; node != null; node = node.prev) {
                if (o.equals(node.item)) {
                    this.unlink(node);
                    return true;
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean add(final E e) {
        this.addLast(e);
        return true;
    }
    
    @Override
    public boolean offer(final E e) {
        return this.offerLast(e);
    }
    
    @Override
    public void put(final E e) throws InterruptedException {
        this.putLast(e);
    }
    
    @Override
    public boolean offer(final E e, final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.offerLast(e, n, timeUnit);
    }
    
    @Override
    public E remove() {
        return this.removeFirst();
    }
    
    @Override
    public E poll() {
        return this.pollFirst();
    }
    
    @Override
    public E take() throws InterruptedException {
        return this.takeFirst();
    }
    
    @Override
    public E poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.pollFirst(n, timeUnit);
    }
    
    @Override
    public E element() {
        return this.getFirst();
    }
    
    @Override
    public E peek() {
        return this.peekFirst();
    }
    
    @Override
    public int remainingCapacity() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.capacity - this.count;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public int drainTo(final Collection<? super E> collection) {
        return this.drainTo(collection, Integer.MAX_VALUE);
    }
    
    @Override
    public int drainTo(final Collection<? super E> collection, final int n) {
        if (collection == null) {
            throw new NullPointerException();
        }
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        if (n <= 0) {
            return 0;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int min = Math.min(n, this.count);
            for (int i = 0; i < min; ++i) {
                collection.add((Object)this.first.item);
                this.unlinkFirst();
            }
            return min;
        }
        finally {
            lock.unlock();
        }
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
    public boolean remove(final Object o) {
        return this.removeFirstOccurrence(o);
    }
    
    @Override
    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.count;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (Node<E> node = this.first; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    return true;
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public Object[] toArray() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] array = new Object[this.count];
            int n = 0;
            for (Node<E> node = this.first; node != null; node = node.next) {
                array[n++] = node.item;
            }
            return array;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public <T> T[] toArray(T[] array) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (array.length < this.count) {
                array = (T[])Array.newInstance(array.getClass().getComponentType(), this.count);
            }
            int n = 0;
            for (Node<E> node = this.first; node != null; node = node.next) {
                array[n++] = (T)node.item;
            }
            if (array.length > n) {
                array[n] = null;
            }
            return array;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public String toString() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Node<E> node = this.first;
            if (node == null) {
                return "[]";
            }
            final StringBuilder sb = new StringBuilder();
            sb.append('[');
            while (true) {
                final E item = node.item;
                sb.append((item == this) ? "(this Collection)" : item);
                node = node.next;
                if (node == null) {
                    break;
                }
                sb.append(',').append(' ');
            }
            return sb.append(']').toString();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void clear() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Node<E> next;
            for (Node<E> first = this.first; first != null; first = next) {
                first.item = null;
                next = first.next;
                first.prev = null;
                first.next = null;
            }
            final Node<E> node = null;
            this.last = node;
            this.first = node;
            this.count = 0;
            this.notFull.signalAll();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingItr();
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new LBDSpliterator<E>(this);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            objectOutputStream.defaultWriteObject();
            for (Node<E> node = this.first; node != null; node = node.next) {
                objectOutputStream.writeObject(node.item);
            }
            objectOutputStream.writeObject(null);
        }
        finally {
            lock.unlock();
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.count = 0;
        this.first = null;
        this.last = null;
        while (true) {
            final Object object = objectInputStream.readObject();
            if (object == null) {
                break;
            }
            this.add((E)object);
        }
    }
    
    private abstract class AbstractItr implements Iterator<E>
    {
        Node<E> next;
        E nextItem;
        private Node<E> lastRet;
        
        abstract Node<E> firstNode();
        
        abstract Node<E> nextNode(final Node<E> p0);
        
        AbstractItr() {
            final ReentrantLock lock = LinkedBlockingDeque.this.lock;
            lock.lock();
            try {
                this.next = this.firstNode();
                this.nextItem = ((this.next == null) ? null : this.next.item);
            }
            finally {
                lock.unlock();
            }
        }
        
        private Node<E> succ(Node<E> node) {
            while (true) {
                final Node<E> nextNode = this.nextNode(node);
                if (nextNode == null) {
                    return null;
                }
                if (nextNode.item != null) {
                    return nextNode;
                }
                if (nextNode == node) {
                    return this.firstNode();
                }
                node = nextNode;
            }
        }
        
        void advance() {
            final ReentrantLock lock = LinkedBlockingDeque.this.lock;
            lock.lock();
            try {
                this.next = this.succ(this.next);
                this.nextItem = ((this.next == null) ? null : this.next.item);
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public boolean hasNext() {
            return this.next != null;
        }
        
        @Override
        public E next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            this.lastRet = this.next;
            final E nextItem = this.nextItem;
            this.advance();
            return nextItem;
        }
        
        @Override
        public void remove() {
            final Node<E> lastRet = this.lastRet;
            if (lastRet == null) {
                throw new IllegalStateException();
            }
            this.lastRet = null;
            final ReentrantLock lock = LinkedBlockingDeque.this.lock;
            lock.lock();
            try {
                if (lastRet.item != null) {
                    LinkedBlockingDeque.this.unlink(lastRet);
                }
            }
            finally {
                lock.unlock();
            }
        }
    }
    
    static final class Node<E>
    {
        E item;
        Node<E> prev;
        Node<E> next;
        
        Node(final E item) {
            this.item = item;
        }
    }
    
    private class DescendingItr extends AbstractItr
    {
        @Override
        Node<E> firstNode() {
            return LinkedBlockingDeque.this.last;
        }
        
        @Override
        Node<E> nextNode(final Node<E> node) {
            return node.prev;
        }
    }
    
    private class Itr extends AbstractItr
    {
        @Override
        Node<E> firstNode() {
            return LinkedBlockingDeque.this.first;
        }
        
        @Override
        Node<E> nextNode(final Node<E> node) {
            return node.next;
        }
    }
    
    static final class LBDSpliterator<E> implements Spliterator<E>
    {
        static final int MAX_BATCH = 33554432;
        final LinkedBlockingDeque<E> queue;
        Node<E> current;
        int batch;
        boolean exhausted;
        long est;
        
        LBDSpliterator(final LinkedBlockingDeque<E> queue) {
            this.queue = queue;
            this.est = queue.size();
        }
        
        @Override
        public long estimateSize() {
            return this.est;
        }
        
        @Override
        public Spliterator<E> trySplit() {
            final LinkedBlockingDeque<E> queue = this.queue;
            final int batch = this.batch;
            final int n = (batch <= 0) ? 1 : ((batch >= 33554432) ? 33554432 : (batch + 1));
            Node<E> node;
            if (!this.exhausted && ((node = this.current) != null || (node = queue.first) != null) && node.next != null) {
                final Object[] array = new Object[n];
                final ReentrantLock lock = queue.lock;
                int batch2 = 0;
                Node<E> node2 = this.current;
                lock.lock();
                try {
                    if (node2 != null || (node2 = queue.first) != null) {
                        do {
                            if ((array[batch2] = node2.item) != null) {
                                ++batch2;
                            }
                        } while ((node2 = node2.next) != null && batch2 < n);
                    }
                }
                finally {
                    lock.unlock();
                }
                final Node<E> current = node2;
                this.current = current;
                if (current == null) {
                    this.est = 0L;
                    this.exhausted = true;
                }
                else {
                    final long est = this.est - batch2;
                    this.est = est;
                    if (est < 0L) {
                        this.est = 0L;
                    }
                }
                if (batch2 > 0) {
                    this.batch = batch2;
                    return (Spliterator<E>)Spliterators.spliterator(array, 0, batch2, 4368);
                }
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final LinkedBlockingDeque<E> queue = this.queue;
            final ReentrantLock lock = queue.lock;
            if (!this.exhausted) {
                this.exhausted = true;
                Node<E> node = this.current;
                do {
                    Object item = null;
                    lock.lock();
                    try {
                        if (node == null) {
                            node = queue.first;
                        }
                        while (node != null) {
                            item = node.item;
                            node = node.next;
                            if (item != null) {
                                break;
                            }
                        }
                    }
                    finally {
                        lock.unlock();
                    }
                    if (item != null) {
                        consumer.accept((Object)item);
                    }
                } while (node != null);
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final LinkedBlockingDeque<E> queue = this.queue;
            final ReentrantLock lock = queue.lock;
            if (!this.exhausted) {
                Object item = null;
                lock.lock();
                try {
                    if (this.current == null) {
                        this.current = queue.first;
                    }
                    while (this.current != null) {
                        item = this.current.item;
                        this.current = this.current.next;
                        if (item != null) {
                            break;
                        }
                    }
                }
                finally {
                    lock.unlock();
                }
                if (this.current == null) {
                    this.exhausted = true;
                }
                if (item != null) {
                    consumer.accept((Object)item);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int characteristics() {
            return 4368;
        }
    }
}
