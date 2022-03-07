package java.util.concurrent;

import sun.misc.*;
import java.io.*;
import java.util.*;
import java.util.function.*;

public class ConcurrentLinkedDeque<E> extends AbstractCollection<E> implements Deque<E>, Serializable
{
    private static final long serialVersionUID = 876323262645176354L;
    private transient volatile Node<E> head;
    private transient volatile Node<E> tail;
    private static final Node<Object> PREV_TERMINATOR;
    private static final Node<Object> NEXT_TERMINATOR;
    private static final int HOPS = 2;
    private static final Unsafe UNSAFE;
    private static final long headOffset;
    private static final long tailOffset;
    
    Node<E> prevTerminator() {
        return (Node<E>)ConcurrentLinkedDeque.PREV_TERMINATOR;
    }
    
    Node<E> nextTerminator() {
        return (Node<E>)ConcurrentLinkedDeque.NEXT_TERMINATOR;
    }
    
    private void linkFirst(final E e) {
        checkNotNull(e);
        final Node<E> node = new Node<E>(e);
        Node<E> head2 = null;
        Node<E> head = null;
    Block_5:
        while (true) {
            head = (head2 = this.head);
            while (true) {
                final Node<E> prev;
                final Node<E> prev2;
                if ((prev = head2.prev) != null && (prev2 = (head2 = prev).prev) != null) {
                    head2 = ((head != (head = this.head)) ? head : prev2);
                }
                else {
                    if (head2.next == head2) {
                        break;
                    }
                    node.lazySetNext(head2);
                    if (head2.casPrev(null, node)) {
                        break Block_5;
                    }
                    continue;
                }
            }
        }
        if (head2 != head) {
            this.casHead(head, node);
        }
    }
    
    private void linkLast(final E e) {
        checkNotNull(e);
        final Node<E> node = new Node<E>(e);
        Node<E> tail2 = null;
        Node<E> tail = null;
    Block_5:
        while (true) {
            tail = (tail2 = this.tail);
            while (true) {
                final Node<E> next;
                final Node<E> next2;
                if ((next = tail2.next) != null && (next2 = (tail2 = next).next) != null) {
                    tail2 = ((tail != (tail = this.tail)) ? tail : next2);
                }
                else {
                    if (tail2.prev == tail2) {
                        break;
                    }
                    node.lazySetPrev(tail2);
                    if (tail2.casNext(null, node)) {
                        break Block_5;
                    }
                    continue;
                }
            }
        }
        if (tail2 != tail) {
            this.casTail(tail, node);
        }
    }
    
    void unlink(final Node<E> node) {
        final Node<E> prev = node.prev;
        final Node<E> next = node.next;
        if (prev == null) {
            this.unlinkFirst(node, next);
        }
        else {
            if (next != null) {
                int n = 1;
                Node<E> node2 = prev;
                while (true) {
                    while (node2.item == null) {
                        final Node<E> prev2 = node2.prev;
                        if (prev2 == null) {
                            if (node2.next == node2) {
                                return;
                            }
                            final Node<E> node3 = node2;
                            final boolean b = true;
                            Node<E> node4 = next;
                            while (true) {
                                while (node4.item == null) {
                                    final Node<E> next2 = node4.next;
                                    if (next2 == null) {
                                        if (node4.prev == node4) {
                                            return;
                                        }
                                        final Node<E> node5 = node4;
                                        final boolean b2 = true;
                                        if (n < 2 && (b | b2)) {
                                            return;
                                        }
                                        this.skipDeletedSuccessors(node3);
                                        this.skipDeletedPredecessors(node5);
                                        if ((b | b2) && node3.next == node5 && node5.prev == node3) {
                                            if (b) {
                                                if (node3.prev != null) {
                                                    return;
                                                }
                                            }
                                            else if (node3.item == null) {
                                                return;
                                            }
                                            if (b2) {
                                                if (node5.next != null) {
                                                    return;
                                                }
                                            }
                                            else if (node5.item == null) {
                                                return;
                                            }
                                            this.updateHead();
                                            this.updateTail();
                                            node.lazySetPrev(b ? this.prevTerminator() : node);
                                            node.lazySetNext(b2 ? this.nextTerminator() : node);
                                        }
                                        return;
                                    }
                                    else {
                                        if (node4 == next2) {
                                            return;
                                        }
                                        node4 = next2;
                                        ++n;
                                    }
                                }
                                final Node<E> node5 = node4;
                                final boolean b2 = false;
                                continue;
                            }
                        }
                        else {
                            if (node2 == prev2) {
                                return;
                            }
                            node2 = prev2;
                            ++n;
                        }
                    }
                    final Node<E> node3 = node2;
                    final boolean b = false;
                    continue;
                }
            }
            this.unlinkLast(node, prev);
        }
    }
    
    private void unlinkFirst(final Node<E> node, final Node<E> node2) {
        Node<E> node3 = null;
        Node<E> node4;
        Node<E> next;
        for (node4 = node2; node4.item == null && (next = node4.next) != null; node4 = next) {
            if (node4 == next) {
                return;
            }
            node3 = node4;
        }
        if (node3 != null && node4.prev != node4 && node.casNext(node2, node4)) {
            this.skipDeletedPredecessors(node4);
            if (node.prev == null && (node4.next == null || node4.item != null) && node4.prev == node) {
                this.updateHead();
                this.updateTail();
                node3.lazySetNext(node3);
                node3.lazySetPrev(this.prevTerminator());
            }
        }
    }
    
    private void unlinkLast(final Node<E> node, final Node<E> node2) {
        Node<E> node3 = null;
        Node<E> node4;
        Node<E> prev;
        for (node4 = node2; node4.item == null && (prev = node4.prev) != null; node4 = prev) {
            if (node4 == prev) {
                return;
            }
            node3 = node4;
        }
        if (node3 != null && node4.next != node4 && node.casPrev(node2, node4)) {
            this.skipDeletedSuccessors(node4);
            if (node.next == null && (node4.prev == null || node4.item != null) && node4.next == node) {
                this.updateHead();
                this.updateTail();
                node3.lazySetPrev(node3);
                node3.lazySetNext(this.nextTerminator());
            }
        }
    }
    
    private final void updateHead() {
        Node<E> head;
        Node<E> prev;
    Label_0000:
        while ((head = this.head).item == null && (prev = head.prev) != null) {
            Node<E> prev2;
            Node<E> prev3;
            while ((prev2 = prev.prev) != null && (prev3 = (prev = prev2).prev) != null) {
                if (head != this.head) {
                    continue Label_0000;
                }
                prev = prev3;
            }
            if (this.casHead(head, prev)) {
                return;
            }
        }
    }
    
    private final void updateTail() {
        Node<E> tail;
        Node<E> next;
    Label_0000:
        while ((tail = this.tail).item == null && (next = tail.next) != null) {
            Node<E> next2;
            Node<E> next3;
            while ((next2 = next.next) != null && (next3 = (next = next2).next) != null) {
                if (tail != this.tail) {
                    continue Label_0000;
                }
                next = next3;
            }
            if (this.casTail(tail, next)) {
                return;
            }
        }
    }
    
    private void skipDeletedPredecessors(final Node<E> node) {
    Label_0069:
        do {
            Node<E> prev;
            final Node<E> node2 = prev = node.prev;
            while (true) {
                while (prev.item == null) {
                    final Node<E> prev2 = prev.prev;
                    if (prev2 == null) {
                        if (prev.next == prev) {
                            continue Label_0069;
                        }
                        if (node2 == prev || node.casPrev(node2, prev)) {
                            return;
                        }
                        continue Label_0069;
                    }
                    else {
                        if (prev == prev2) {
                            continue Label_0069;
                        }
                        prev = prev2;
                    }
                }
                continue;
            }
        } while (node.item != null || node.next == null);
    }
    
    private void skipDeletedSuccessors(final Node<E> node) {
    Label_0069:
        do {
            Node<E> next;
            final Node<E> node2 = next = node.next;
            while (true) {
                while (next.item == null) {
                    final Node<E> next2 = next.next;
                    if (next2 == null) {
                        if (next.prev == next) {
                            continue Label_0069;
                        }
                        if (node2 == next || node.casNext(node2, next)) {
                            return;
                        }
                        continue Label_0069;
                    }
                    else {
                        if (next == next2) {
                            continue Label_0069;
                        }
                        next = next2;
                    }
                }
                continue;
            }
        } while (node.item != null || node.prev == null);
    }
    
    final Node<E> succ(final Node<E> node) {
        final Node<E> next = node.next;
        return (node == next) ? this.first() : next;
    }
    
    final Node<E> pred(final Node<E> node) {
        final Node<E> prev = node.prev;
        return (node == prev) ? this.last() : prev;
    }
    
    Node<E> first() {
        Node<E> head;
        Node<E> head2;
        do {
            Node<E> prev;
            Node<E> prev2;
            for (head2 = (head = this.head); (prev = head.prev) != null && (prev2 = (head = prev).prev) != null; head = ((head2 != (head2 = this.head)) ? head2 : prev2)) {}
        } while (head != head2 && !this.casHead(head2, head));
        return head;
    }
    
    Node<E> last() {
        Node<E> tail;
        Node<E> tail2;
        do {
            Node<E> next;
            Node<E> next2;
            for (tail2 = (tail = this.tail); (next = tail.next) != null && (next2 = (tail = next).next) != null; tail = ((tail2 != (tail2 = this.tail)) ? tail2 : next2)) {}
        } while (tail != tail2 && !this.casTail(tail2, tail));
        return tail;
    }
    
    private static void checkNotNull(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }
    
    private E screenNullResult(final E e) {
        if (e == null) {
            throw new NoSuchElementException();
        }
        return e;
    }
    
    private ArrayList<E> toArrayList() {
        final ArrayList<E> list = new ArrayList<E>();
        for (Node<E> node = this.first(); node != null; node = this.succ(node)) {
            final E item = node.item;
            if (item != null) {
                list.add(item);
            }
        }
        return list;
    }
    
    public ConcurrentLinkedDeque() {
        final Node<E> node = new Node<E>(null);
        this.tail = node;
        this.head = node;
    }
    
    public ConcurrentLinkedDeque(final Collection<? extends E> collection) {
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
                node3.lazySetPrev(node2);
                node2 = (Node<E>)node3;
            }
        }
        this.initHeadTail(node, node2);
    }
    
    private void initHeadTail(Node<E> head, Node<E> tail) {
        if (head == tail) {
            if (head == null) {
                tail = (head = new Node<E>(null));
            }
            else {
                final Node<E> node = new Node<E>(null);
                tail.lazySetNext(node);
                node.lazySetPrev(tail);
                tail = node;
            }
        }
        this.head = head;
        this.tail = tail;
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
    public boolean offerFirst(final E e) {
        this.linkFirst(e);
        return true;
    }
    
    @Override
    public boolean offerLast(final E e) {
        this.linkLast(e);
        return true;
    }
    
    @Override
    public E peekFirst() {
        for (Node<E> node = this.first(); node != null; node = this.succ(node)) {
            final E item = node.item;
            if (item != null) {
                return item;
            }
        }
        return null;
    }
    
    @Override
    public E peekLast() {
        for (Node<E> node = this.last(); node != null; node = this.pred(node)) {
            final E item = node.item;
            if (item != null) {
                return item;
            }
        }
        return null;
    }
    
    @Override
    public E getFirst() {
        return this.screenNullResult(this.peekFirst());
    }
    
    @Override
    public E getLast() {
        return this.screenNullResult(this.peekLast());
    }
    
    @Override
    public E pollFirst() {
        for (Object o = this.first(); o != null; o = this.succ((Node<Object>)o)) {
            final E item = ((Node)o).item;
            if (item != null && ((Node<Object>)o).casItem(item, null)) {
                this.unlink((Node<E>)o);
                return (E)item;
            }
        }
        return null;
    }
    
    @Override
    public E pollLast() {
        for (Object o = this.last(); o != null; o = this.pred((Node<Object>)o)) {
            final E item = ((Node)o).item;
            if (item != null && ((Node<Object>)o).casItem(item, null)) {
                this.unlink((Node<E>)o);
                return (E)item;
            }
        }
        return null;
    }
    
    @Override
    public E removeFirst() {
        return this.screenNullResult(this.pollFirst());
    }
    
    @Override
    public E removeLast() {
        return this.screenNullResult(this.pollLast());
    }
    
    @Override
    public boolean offer(final E e) {
        return this.offerLast(e);
    }
    
    @Override
    public boolean add(final E e) {
        return this.offerLast(e);
    }
    
    @Override
    public E poll() {
        return this.pollFirst();
    }
    
    @Override
    public E peek() {
        return this.peekFirst();
    }
    
    @Override
    public E remove() {
        return this.removeFirst();
    }
    
    @Override
    public E pop() {
        return this.removeFirst();
    }
    
    @Override
    public E element() {
        return this.getFirst();
    }
    
    @Override
    public void push(final E e) {
        this.addFirst(e);
    }
    
    @Override
    public boolean removeFirstOccurrence(final Object o) {
        checkNotNull(o);
        for (Node<E> node = this.first(); node != null; node = this.succ(node)) {
            final E item = node.item;
            if (item != null && o.equals(item) && node.casItem(item, null)) {
                this.unlink(node);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean removeLastOccurrence(final Object o) {
        checkNotNull(o);
        for (Node<E> node = this.last(); node != null; node = this.pred(node)) {
            final E item = node.item;
            if (item != null && o.equals(item) && node.casItem(item, null)) {
                this.unlink(node);
                return true;
            }
        }
        return false;
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
    public boolean isEmpty() {
        return this.peekFirst() == null;
    }
    
    @Override
    public int size() {
        int n = 0;
        for (Node<E> node = this.first(); node != null && (node.item == null || ++n != Integer.MAX_VALUE); node = this.succ(node)) {}
        return n;
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.removeFirstOccurrence(o);
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
                node3.lazySetPrev(node2);
                node2 = (Node<E>)node3;
            }
        }
        if (node == null) {
            return false;
        }
        Node<E> tail = null;
    Block_9:
        while (true) {
            Node<E> tail2;
            tail = (tail2 = this.tail);
            while (true) {
                final Node<E> next2;
                final Node<E> next3;
                if ((next2 = tail2.next) != null && (next3 = (tail2 = next2).next) != null) {
                    tail2 = ((tail != (tail = this.tail)) ? tail : next3);
                }
                else {
                    if (tail2.prev == tail2) {
                        break;
                    }
                    node.lazySetPrev(tail2);
                    if (tail2.casNext(null, node)) {
                        break Block_9;
                    }
                    continue;
                }
            }
        }
        if (!this.casTail(tail, node2)) {
            final Node<E> tail3 = this.tail;
            if (node2.next == null) {
                this.casTail(tail3, node2);
            }
        }
        return true;
    }
    
    @Override
    public void clear() {
        while (this.pollFirst() != null) {}
    }
    
    @Override
    public Object[] toArray() {
        return this.toArrayList().toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        return this.toArrayList().toArray(array);
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
        return new CLDSpliterator<E>(this);
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
        Node<E> node = null;
        Node<E> node2 = null;
        Object object;
        while ((object = objectInputStream.readObject()) != null) {
            final Node node3 = new Node<E>(object);
            if (node == null) {
                node2 = (node = (Node<E>)node3);
            }
            else {
                node2.lazySetNext((Node<E>)node3);
                node3.lazySetPrev(node2);
                node2 = (Node<E>)node3;
            }
        }
        this.initHeadTail(node, node2);
    }
    
    private boolean casHead(final Node<E> node, final Node<E> node2) {
        return ConcurrentLinkedDeque.UNSAFE.compareAndSwapObject(this, ConcurrentLinkedDeque.headOffset, node, node2);
    }
    
    private boolean casTail(final Node<E> node, final Node<E> node2) {
        return ConcurrentLinkedDeque.UNSAFE.compareAndSwapObject(this, ConcurrentLinkedDeque.tailOffset, node, node2);
    }
    
    static {
        PREV_TERMINATOR = new Node<Object>();
        ConcurrentLinkedDeque.PREV_TERMINATOR.next = ConcurrentLinkedDeque.PREV_TERMINATOR;
        NEXT_TERMINATOR = new Node<Object>();
        ConcurrentLinkedDeque.NEXT_TERMINATOR.prev = ConcurrentLinkedDeque.NEXT_TERMINATOR;
        try {
            UNSAFE = Unsafe.getUnsafe();
            final Class<ConcurrentLinkedDeque> clazz = ConcurrentLinkedDeque.class;
            headOffset = ConcurrentLinkedDeque.UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
            tailOffset = ConcurrentLinkedDeque.UNSAFE.objectFieldOffset(clazz.getDeclaredField("tail"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    private abstract class AbstractItr implements Iterator<E>
    {
        private Node<E> nextNode;
        private E nextItem;
        private Node<E> lastRet;
        
        abstract Node<E> startNode();
        
        abstract Node<E> nextNode(final Node<E> p0);
        
        AbstractItr() {
            this.advance();
        }
        
        private void advance() {
            this.lastRet = this.nextNode;
            for (Node<E> nextNode = (this.nextNode == null) ? this.startNode() : this.nextNode(this.nextNode); nextNode != null; nextNode = this.nextNode(nextNode)) {
                final E item = nextNode.item;
                if (item != null) {
                    this.nextNode = nextNode;
                    this.nextItem = item;
                    return;
                }
            }
            this.nextNode = null;
            this.nextItem = null;
        }
        
        @Override
        public boolean hasNext() {
            return this.nextItem != null;
        }
        
        @Override
        public E next() {
            final E nextItem = this.nextItem;
            if (nextItem == null) {
                throw new NoSuchElementException();
            }
            this.advance();
            return nextItem;
        }
        
        @Override
        public void remove() {
            final Node<E> lastRet = this.lastRet;
            if (lastRet == null) {
                throw new IllegalStateException();
            }
            lastRet.item = null;
            ConcurrentLinkedDeque.this.unlink(lastRet);
            this.lastRet = null;
        }
    }
    
    static final class Node<E>
    {
        volatile Node<E> prev;
        volatile E item;
        volatile Node<E> next;
        private static final Unsafe UNSAFE;
        private static final long prevOffset;
        private static final long itemOffset;
        private static final long nextOffset;
        
        Node() {
        }
        
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
        
        void lazySetPrev(final Node<E> node) {
            Node.UNSAFE.putOrderedObject(this, Node.prevOffset, node);
        }
        
        boolean casPrev(final Node<E> node, final Node<E> node2) {
            return Node.UNSAFE.compareAndSwapObject(this, Node.prevOffset, node, node2);
        }
        
        static {
            try {
                UNSAFE = Unsafe.getUnsafe();
                final Class<Node> clazz = Node.class;
                prevOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("prev"));
                itemOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("item"));
                nextOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }
    
    static final class CLDSpliterator<E> implements Spliterator<E>
    {
        static final int MAX_BATCH = 33554432;
        final ConcurrentLinkedDeque<E> queue;
        Node<E> current;
        int batch;
        boolean exhausted;
        
        CLDSpliterator(final ConcurrentLinkedDeque<E> queue) {
            this.queue = queue;
        }
        
        @Override
        public Spliterator<E> trySplit() {
            final ConcurrentLinkedDeque<E> queue = this.queue;
            final int batch = this.batch;
            final int n = (batch <= 0) ? 1 : ((batch >= 33554432) ? 33554432 : (batch + 1));
            Node<E> current;
            if (!this.exhausted && ((current = this.current) != null || (current = queue.first()) != null)) {
                if (current.item == null && current == (current = current.next)) {
                    current = (this.current = queue.first());
                }
                if (current != null && current.next != null) {
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
            }
            return null;
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final ConcurrentLinkedDeque<E> queue = this.queue;
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
            final ConcurrentLinkedDeque<E> queue = this.queue;
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
    
    private class DescendingItr extends AbstractItr
    {
        @Override
        Node<E> startNode() {
            return ConcurrentLinkedDeque.this.last();
        }
        
        @Override
        Node<E> nextNode(final Node<E> node) {
            return ConcurrentLinkedDeque.this.pred(node);
        }
    }
    
    private class Itr extends AbstractItr
    {
        @Override
        Node<E> startNode() {
            return ConcurrentLinkedDeque.this.first();
        }
        
        @Override
        Node<E> nextNode(final Node<E> node) {
            return ConcurrentLinkedDeque.this.succ(node);
        }
    }
}
