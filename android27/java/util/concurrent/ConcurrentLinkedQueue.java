package java.util.concurrent;

import sun.misc.*;
import java.io.*;
import java.util.function.*;
import java.util.*;

public class ConcurrentLinkedQueue<E> extends AbstractQueue<E> implements Queue<E>, Serializable
{
    private static final long serialVersionUID = 196745693267521676L;
    private transient volatile Node<E> head;
    private transient volatile Node<E> tail;
    private static final Unsafe UNSAFE;
    private static final long headOffset;
    private static final long tailOffset;
    
    public ConcurrentLinkedQueue() {
        final Node<E> node = new Node<E>(null);
        this.tail = node;
        this.head = node;
    }
    
    public ConcurrentLinkedQueue(final Collection<? extends E> collection) {
        Node<E> head = null;
        Node<E> tail = null;
        for (final E next : collection) {
            checkNotNull(next);
            final Node node = new Node<E>(next);
            if (head == null) {
                tail = (head = (Node<E>)node);
            }
            else {
                tail.lazySetNext((Node<E>)node);
                tail = (Node<E>)node;
            }
        }
        if (head == null) {
            tail = (head = new Node<E>(null));
        }
        this.head = head;
        this.tail = tail;
    }
    
    @Override
    public boolean add(final E e) {
        return this.offer(e);
    }
    
    final void updateHead(final Node<E> node, final Node<E> node2) {
        if (node != node2 && this.casHead(node, node2)) {
            node.lazySetNext(node);
        }
    }
    
    final Node<E> succ(final Node<E> node) {
        final Node<E> next = node.next;
        return (node == next) ? this.head : next;
    }
    
    @Override
    public boolean offer(final E e) {
        checkNotNull(e);
        final Node<E> node = new Node<E>(e);
        Node<E> tail;
        Node<E> node2 = tail = this.tail;
        while (true) {
            final Node<E> next = tail.next;
            if (next == null) {
                if (tail.casNext(null, node)) {
                    break;
                }
                continue;
            }
            else if (tail == next) {
                tail = ((node2 != (node2 = this.tail)) ? node2 : this.head);
            }
            else {
                tail = ((tail != node2 && node2 != (node2 = this.tail)) ? node2 : next);
            }
        }
        if (tail != node2) {
            this.casTail(node2, node);
        }
        return true;
    }
    
    @Override
    public E poll() {
        while (true) {
            Object head;
            final Node<E> node = (Node<E>)(head = this.head);
            while (true) {
                final E item = ((Node)head).item;
                if (item != null && ((Node<Object>)head).casItem(item, null)) {
                    if (head != node) {
                        final Node<E> next;
                        this.updateHead(node, (Node<E>)(((next = (Node<E>)((Node)head).next) != null) ? next : head));
                    }
                    return (E)item;
                }
                final Object next2;
                if ((next2 = ((Node)head).next) == null) {
                    this.updateHead(node, (Node<E>)head);
                    return null;
                }
                if (head == next2) {
                    break;
                }
                head = next2;
            }
        }
    }
    
    @Override
    public E peek() {
        Node<E> head = null;
        Node<E> node = null;
        E item = null;
    Label_0027:
        while (true) {
            node = (head = this.head);
            while (true) {
                item = head.item;
                final Node<E> next;
                if (item != null || (next = head.next) == null) {
                    break Label_0027;
                }
                if (head == next) {
                    break;
                }
                head = next;
            }
        }
        this.updateHead(node, head);
        return item;
    }
    
    Node<E> first() {
        Node<E> head = null;
        Node<E> node = null;
        boolean b = false;
    Label_0035:
        while (true) {
            node = (head = this.head);
            while (true) {
                b = (head.item != null);
                final Node<E> next;
                if (b || (next = head.next) == null) {
                    break Label_0035;
                }
                if (head == next) {
                    break;
                }
                head = next;
            }
        }
        this.updateHead(node, head);
        return b ? head : null;
    }
    
    @Override
    public boolean isEmpty() {
        return this.first() == null;
    }
    
    @Override
    public int size() {
        int n = 0;
        for (Node<E> node = this.first(); node != null && (node.item == null || ++n != Integer.MAX_VALUE); node = this.succ(node)) {}
        return n;
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        for (Node<E> node = this.first(); node != null; node = this.succ(node)) {
            final E item = node.item;
            if (item != null && o.equals(item)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean remove(final Object o) {
        if (o != null) {
            Node<E> node = null;
            Node<E> node2 = null;
            for (Node<E> first = this.first(); first != null; first = node2) {
                boolean casItem = false;
                final E item = first.item;
                Label_0091: {
                    if (item != null) {
                        if (!o.equals(item)) {
                            node2 = this.succ(first);
                            break Label_0091;
                        }
                        casItem = first.casItem(item, null);
                    }
                    node2 = this.succ(first);
                    if (node != null && node2 != null) {
                        node.casNext(first, node2);
                    }
                    if (casItem) {
                        return true;
                    }
                }
                node = first;
            }
        }
        return false;
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        Node<E> node = null;
        Node<E> node2 = null;
        for (final E next : collection) {
            checkNotNull(next);
            final Node node3 = new Node<E>(next);
            if (node == null) {
                node2 = (node = (Node<E>)node3);
            }
            else {
                node2.lazySetNext((Node<E>)node3);
                node2 = (Node<E>)node3;
            }
        }
        if (node == null) {
            return false;
        }
        Node<E> tail;
        Node<E> node4 = tail = this.tail;
        while (true) {
            final Node<E> next2 = tail.next;
            if (next2 == null) {
                if (tail.casNext(null, node)) {
                    break;
                }
                continue;
            }
            else if (tail == next2) {
                tail = ((node4 != (node4 = this.tail)) ? node4 : this.head);
            }
            else {
                tail = ((tail != node4 && node4 != (node4 = this.tail)) ? node4 : next2);
            }
        }
        if (!this.casTail(node4, node2)) {
            final Node<E> tail2 = this.tail;
            if (node2.next == null) {
                this.casTail(tail2, node2);
            }
        }
        return true;
    }
    
    @Override
    public Object[] toArray() {
        final ArrayList<E> list = new ArrayList<E>();
        for (Node<E> node = this.first(); node != null; node = this.succ(node)) {
            final E item = node.item;
            if (item != null) {
                list.add(item);
            }
        }
        return list.toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        int n;
        Node<E> node;
        E item;
        for (n = 0, node = this.first(); node != null && n < array.length; node = this.succ(node)) {
            item = node.item;
            if (item != null) {
                array[n++] = (T)item;
            }
        }
        if (node == null) {
            if (n < array.length) {
                array[n] = null;
            }
            return array;
        }
        final ArrayList<E> list = new ArrayList<E>();
        for (Node<E> node2 = this.first(); node2 != null; node2 = this.succ(node2)) {
            final E item2 = node2.item;
            if (item2 != null) {
                list.add(item2);
            }
        }
        return list.toArray(array);
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (Node<E> node = this.first(); node != null; node = this.succ(node)) {
            final E item = node.item;
            if (item != null) {
                objectOutputStream.writeObject(item);
            }
        }
        objectOutputStream.writeObject(null);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Node<E> head = null;
        Node<E> tail = null;
        Object object;
        while ((object = objectInputStream.readObject()) != null) {
            final Node node = new Node<E>(object);
            if (head == null) {
                tail = (head = (Node<E>)node);
            }
            else {
                tail.lazySetNext((Node<E>)node);
                tail = (Node<E>)node;
            }
        }
        if (head == null) {
            tail = (head = new Node<E>(null));
        }
        this.head = head;
        this.tail = tail;
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new CLQSpliterator<E>(this);
    }
    
    private static void checkNotNull(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }
    
    private boolean casTail(final Node<E> node, final Node<E> node2) {
        return ConcurrentLinkedQueue.UNSAFE.compareAndSwapObject(this, ConcurrentLinkedQueue.tailOffset, node, node2);
    }
    
    private boolean casHead(final Node<E> node, final Node<E> node2) {
        return ConcurrentLinkedQueue.UNSAFE.compareAndSwapObject(this, ConcurrentLinkedQueue.headOffset, node, node2);
    }
    
    static {
        try {
            UNSAFE = Unsafe.getUnsafe();
            final Class<ConcurrentLinkedQueue> clazz = ConcurrentLinkedQueue.class;
            headOffset = ConcurrentLinkedQueue.UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
            tailOffset = ConcurrentLinkedQueue.UNSAFE.objectFieldOffset(clazz.getDeclaredField("tail"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class CLQSpliterator<E> implements Spliterator<E>
    {
        static final int MAX_BATCH = 33554432;
        final ConcurrentLinkedQueue<E> queue;
        Node<E> current;
        int batch;
        boolean exhausted;
        
        CLQSpliterator(final ConcurrentLinkedQueue<E> queue) {
            this.queue = queue;
        }
        
        @Override
        public Spliterator<E> trySplit() {
            final ConcurrentLinkedQueue<E> queue = this.queue;
            final int batch = this.batch;
            final int n = (batch <= 0) ? 1 : ((batch >= 33554432) ? 33554432 : (batch + 1));
            Node<E> current;
            if (!this.exhausted && ((current = this.current) != null || (current = queue.first()) != null) && current.next != null) {
                final Object[] array = new Object[n];
                int batch2 = 0;
                do {
                    if ((array[batch2] = current.item) != null) {
                        ++batch2;
                    }
                    if (current == (current = current.next)) {
                        current = queue.first();
                    }
                } while (current != null && batch2 < n);
                if ((this.current = current) == null) {
                    this.exhausted = true;
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
            final ConcurrentLinkedQueue<E> queue = this.queue;
            Node<E> node;
            if (!this.exhausted && ((node = this.current) != null || (node = queue.first()) != null)) {
                this.exhausted = true;
                do {
                    final E item = node.item;
                    if (node == (node = node.next)) {
                        node = queue.first();
                    }
                    if (item != null) {
                        consumer.accept(item);
                    }
                } while (node != null);
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final ConcurrentLinkedQueue<E> queue = this.queue;
            Node<E> current;
            if (!this.exhausted && ((current = this.current) != null || (current = queue.first()) != null)) {
                E item;
                do {
                    item = current.item;
                    if (current == (current = current.next)) {
                        current = queue.first();
                    }
                } while (item == null && current != null);
                if ((this.current = current) == null) {
                    this.exhausted = true;
                }
                if (item != null) {
                    consumer.accept(item);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }
        
        @Override
        public int characteristics() {
            return 4368;
        }
    }
    
    private static class Node<E>
    {
        volatile E item;
        volatile Node<E> next;
        private static final Unsafe UNSAFE;
        private static final long itemOffset;
        private static final long nextOffset;
        
        Node(final E e) {
            Node.UNSAFE.putObject(this, Node.itemOffset, e);
        }
        
        boolean casItem(final E e, final E e2) {
            return Node.UNSAFE.compareAndSwapObject(this, Node.itemOffset, e, e2);
        }
        
        void lazySetNext(final Node<E> node) {
            Node.UNSAFE.putOrderedObject(this, Node.nextOffset, node);
        }
        
        boolean casNext(final Node<E> node, final Node<E> node2) {
            return Node.UNSAFE.compareAndSwapObject(this, Node.nextOffset, node, node2);
        }
        
        static {
            try {
                UNSAFE = Unsafe.getUnsafe();
                final Class<Node> clazz = Node.class;
                itemOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("item"));
                nextOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }
    
    private class Itr implements Iterator<E>
    {
        private Node<E> nextNode;
        private E nextItem;
        private Node<E> lastRet;
        
        Itr() {
            this.advance();
        }
        
        private E advance() {
            this.lastRet = this.nextNode;
            final E nextItem = this.nextItem;
            Node<E> nextNode;
            Node<E> nextNode2;
            if (this.nextNode == null) {
                nextNode = ConcurrentLinkedQueue.this.first();
                nextNode2 = null;
            }
            else {
                nextNode2 = this.nextNode;
                nextNode = ConcurrentLinkedQueue.this.succ(this.nextNode);
            }
            while (nextNode != null) {
                final E item = nextNode.item;
                if (item != null) {
                    this.nextNode = nextNode;
                    this.nextItem = item;
                    return nextItem;
                }
                final Node<E> succ = ConcurrentLinkedQueue.this.succ(nextNode);
                if (nextNode2 != null && succ != null) {
                    nextNode2.casNext(nextNode, succ);
                }
                nextNode = succ;
            }
            this.nextNode = null;
            this.nextItem = null;
            return nextItem;
        }
        
        @Override
        public boolean hasNext() {
            return this.nextNode != null;
        }
        
        @Override
        public E next() {
            if (this.nextNode == null) {
                throw new NoSuchElementException();
            }
            return this.advance();
        }
        
        @Override
        public void remove() {
            final Node<E> lastRet = this.lastRet;
            if (lastRet == null) {
                throw new IllegalStateException();
            }
            lastRet.item = null;
            this.lastRet = null;
        }
    }
}
