package java.util.concurrent;

import sun.misc.*;
import java.util.concurrent.locks.*;
import java.io.*;
import java.util.*;
import java.util.function.*;

public class LinkedTransferQueue<E> extends AbstractQueue<E> implements TransferQueue<E>, Serializable
{
    private static final long serialVersionUID = -3223113410248163686L;
    private static final boolean MP;
    private static final int FRONT_SPINS = 128;
    private static final int CHAINED_SPINS = 64;
    static final int SWEEP_THRESHOLD = 32;
    transient volatile Node head;
    private transient volatile Node tail;
    private transient volatile int sweepVotes;
    private static final int NOW = 0;
    private static final int ASYNC = 1;
    private static final int SYNC = 2;
    private static final int TIMED = 3;
    private static final Unsafe UNSAFE;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long sweepVotesOffset;
    
    private boolean casTail(final Node node, final Node node2) {
        return LinkedTransferQueue.UNSAFE.compareAndSwapObject(this, LinkedTransferQueue.tailOffset, node, node2);
    }
    
    private boolean casHead(final Node node, final Node node2) {
        return LinkedTransferQueue.UNSAFE.compareAndSwapObject(this, LinkedTransferQueue.headOffset, node, node2);
    }
    
    private boolean casSweepVotes(final int n, final int n2) {
        return LinkedTransferQueue.UNSAFE.compareAndSwapInt(this, LinkedTransferQueue.sweepVotesOffset, n, n2);
    }
    
    static <E> E cast(final Object o) {
        return (E)o;
    }
    
    private E xfer(final E e, final boolean b, final int n, final long n2) {
        if (b && e == null) {
            throw new NullPointerException();
        }
        Node node = null;
        while (true) {
            Node head;
            Node next3;
            for (Node node2 = head = this.head; head != null; head = ((head != next3) ? next3 : (node2 = this.head))) {
                final boolean isData = head.isData;
                final Object item = head.item;
                if (item != head && item != null == isData) {
                    if (isData == b) {
                        break;
                    }
                    if (head.casItem(item, e)) {
                        Node next = head;
                        while (next != node2) {
                            final Node next2 = next.next;
                            if (this.head == node2 && this.casHead(node2, (next2 == null) ? next : next2)) {
                                node2.forgetNext();
                                break;
                            }
                            if ((node2 = this.head) == null || (next = node2.next) == null) {
                                break;
                            }
                            if (!next.isMatched()) {
                                break;
                            }
                        }
                        LockSupport.unpark(head.waiter);
                        return cast(item);
                    }
                }
                next3 = head.next;
            }
            if (n == 0) {
                break;
            }
            if (node == null) {
                node = new Node(e, b);
            }
            final Node tryAppend = this.tryAppend(node, b);
            if (tryAppend == null) {
                continue;
            }
            if (n != 1) {
                return this.awaitMatch(node, tryAppend, e, n == 3, n2);
            }
            break;
        }
        return e;
    }
    
    private Node tryAppend(Node node, final boolean b) {
        Node node2;
        Node tail = node2 = this.tail;
        while (true) {
            if (node2 == null && (node2 = this.head) == null) {
                if (this.casHead(null, node)) {
                    return node;
                }
                continue;
            }
            else {
                if (node2.cannotPrecede(b)) {
                    return null;
                }
                final Node next;
                if ((next = node2.next) != null) {
                    final Node tail2;
                    node2 = ((node2 != tail && tail != (tail2 = this.tail)) ? (tail = tail2) : ((node2 != next) ? next : null));
                }
                else {
                    if (node2.casNext(null, node)) {
                        if (node2 != tail) {
                            while ((this.tail != tail || !this.casTail(tail, node)) && (tail = this.tail) != null && (node = tail.next) != null && (node = node.next) != null && node != tail) {}
                        }
                        return node2;
                    }
                    node2 = node2.next;
                }
            }
        }
    }
    
    private E awaitMatch(final Node node, final Node node2, final E e, final boolean b, long n) {
        final long n2 = b ? (System.nanoTime() + n) : 0L;
        final Thread currentThread = Thread.currentThread();
        int spins = -1;
        ThreadLocalRandom current = null;
        while (true) {
            final Object item = node.item;
            if (item != e) {
                node.forgetContents();
                return cast(item);
            }
            if ((currentThread.isInterrupted() || (b && n <= 0L)) && node.casItem(e, node)) {
                this.unsplice(node2, node);
                return e;
            }
            if (spins < 0) {
                if ((spins = spinsFor(node2, node.isData)) <= 0) {
                    continue;
                }
                current = ThreadLocalRandom.current();
            }
            else if (spins > 0) {
                --spins;
                if (current.nextInt(64) != 0) {
                    continue;
                }
                Thread.yield();
            }
            else if (node.waiter == null) {
                node.waiter = currentThread;
            }
            else if (b) {
                n = n2 - System.nanoTime();
                if (n <= 0L) {
                    continue;
                }
                LockSupport.parkNanos(this, n);
            }
            else {
                LockSupport.park(this);
            }
        }
    }
    
    private static int spinsFor(final Node node, final boolean b) {
        if (LinkedTransferQueue.MP && node != null) {
            if (node.isData != b) {
                return 192;
            }
            if (node.isMatched()) {
                return 128;
            }
            if (node.waiter == null) {
                return 64;
            }
        }
        return 0;
    }
    
    final Node succ(final Node node) {
        final Node next = node.next;
        return (node == next) ? this.head : next;
    }
    
    private Node firstOfMode(final boolean b) {
        for (Node node = this.head; node != null; node = this.succ(node)) {
            if (!node.isMatched()) {
                return (node.isData == b) ? node : null;
            }
        }
        return null;
    }
    
    final Node firstDataNode() {
        for (Node node = this.head; node != null; node = this.head) {
            final Object item = node.item;
            if (node.isData) {
                if (item != null && item != node) {
                    return node;
                }
            }
            else if (item == null) {
                break;
            }
            if (node == (node = node.next)) {}
        }
        return null;
    }
    
    private E firstDataItem() {
        for (Node node = this.head; node != null; node = this.succ(node)) {
            final Object item = node.item;
            if (node.isData) {
                if (item != null && item != node) {
                    return cast(item);
                }
            }
            else if (item == null) {
                return null;
            }
        }
        return null;
    }
    
    private int countOfMode(final boolean b) {
        int n = 0;
        Node node = this.head;
        while (node != null) {
            if (!node.isMatched()) {
                if (node.isData != b) {
                    return 0;
                }
                if (++n == Integer.MAX_VALUE) {
                    break;
                }
            }
            final Node next = node.next;
            if (next != node) {
                node = next;
            }
            else {
                n = 0;
                node = this.head;
            }
        }
        return n;
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return new LTQSpliterator<E>(this);
    }
    
    final void unsplice(final Node node, final Node node2) {
        node2.forgetContents();
        Label_0190: {
            if (node != null && node != node2 && node.next == node2) {
                final Node next = node2.next;
                if (next == null || (next != node2 && node.casNext(node2, next) && node.isMatched())) {
                    while (true) {
                        final Node head = this.head;
                        if (head == node || head == node2 || head == null) {
                            return;
                        }
                        if (!head.isMatched()) {
                            if (node.next == node || node2.next == node2) {
                                break;
                            }
                            while (true) {
                                final int sweepVotes = this.sweepVotes;
                                if (sweepVotes < 32) {
                                    if (this.casSweepVotes(sweepVotes, sweepVotes + 1)) {
                                        break Label_0190;
                                    }
                                    continue;
                                }
                                else {
                                    if (this.casSweepVotes(sweepVotes, 0)) {
                                        this.sweep();
                                        break Label_0190;
                                    }
                                    continue;
                                }
                            }
                        }
                        else {
                            final Node next2 = head.next;
                            if (next2 == null) {
                                return;
                            }
                            if (next2 == head || !this.casHead(head, next2)) {
                                continue;
                            }
                            head.forgetNext();
                        }
                    }
                }
            }
        }
    }
    
    private void sweep() {
        Node node = this.head;
        Node next;
        while (node != null && (next = node.next) != null) {
            if (!next.isMatched()) {
                node = next;
            }
            else {
                final Node next2;
                if ((next2 = next.next) == null) {
                    break;
                }
                if (next == next2) {
                    node = this.head;
                }
                else {
                    node.casNext(next, next2);
                }
            }
        }
    }
    
    private boolean findAndRemove(final Object o) {
        if (o != null) {
            Node node = null;
            for (Node node2 = this.head; node2 != null; node2 = this.head) {
                final Object item = node2.item;
                if (node2.isData) {
                    if (item != null && item != node2 && o.equals(item) && node2.tryMatchData()) {
                        this.unsplice(node, node2);
                        return true;
                    }
                }
                else if (item == null) {
                    break;
                }
                node = node2;
                if ((node2 = node2.next) == node) {
                    node = null;
                }
            }
        }
        return false;
    }
    
    public LinkedTransferQueue() {
    }
    
    public LinkedTransferQueue(final Collection<? extends E> collection) {
        this();
        this.addAll(collection);
    }
    
    @Override
    public void put(final E e) {
        this.xfer(e, true, 1, 0L);
    }
    
    @Override
    public boolean offer(final E e, final long n, final TimeUnit timeUnit) {
        this.xfer(e, true, 1, 0L);
        return true;
    }
    
    @Override
    public boolean offer(final E e) {
        this.xfer(e, true, 1, 0L);
        return true;
    }
    
    @Override
    public boolean add(final E e) {
        this.xfer(e, true, 1, 0L);
        return true;
    }
    
    @Override
    public boolean tryTransfer(final E e) {
        return this.xfer(e, true, 0, 0L) == null;
    }
    
    @Override
    public void transfer(final E e) throws InterruptedException {
        if (this.xfer(e, true, 2, 0L) != null) {
            Thread.interrupted();
            throw new InterruptedException();
        }
    }
    
    @Override
    public boolean tryTransfer(final E e, final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (this.xfer(e, true, 3, timeUnit.toNanos(n)) == null) {
            return true;
        }
        if (!Thread.interrupted()) {
            return false;
        }
        throw new InterruptedException();
    }
    
    @Override
    public E take() throws InterruptedException {
        final Object xfer = this.xfer(null, false, 2, 0L);
        if (xfer != null) {
            return (E)xfer;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }
    
    @Override
    public E poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        final Object xfer = this.xfer(null, false, 3, timeUnit.toNanos(n));
        if (xfer != null || !Thread.interrupted()) {
            return (E)xfer;
        }
        throw new InterruptedException();
    }
    
    @Override
    public E poll() {
        return this.xfer(null, false, 0, 0L);
    }
    
    @Override
    public int drainTo(final Collection<? super E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        int n = 0;
        Object poll;
        while ((poll = this.poll()) != null) {
            collection.add((Object)poll);
            ++n;
        }
        return n;
    }
    
    @Override
    public int drainTo(final Collection<? super E> collection, final int n) {
        if (collection == null) {
            throw new NullPointerException();
        }
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        int n2;
        Object poll;
        for (n2 = 0; n2 < n && (poll = this.poll()) != null; ++n2) {
            collection.add((Object)poll);
        }
        return n2;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    @Override
    public E peek() {
        return this.firstDataItem();
    }
    
    @Override
    public boolean isEmpty() {
        for (Node node = this.head; node != null; node = this.succ(node)) {
            if (!node.isMatched()) {
                return !node.isData;
            }
        }
        return true;
    }
    
    @Override
    public boolean hasWaitingConsumer() {
        return this.firstOfMode(false) != null;
    }
    
    @Override
    public int size() {
        return this.countOfMode(true);
    }
    
    @Override
    public int getWaitingConsumerCount() {
        return this.countOfMode(false);
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.findAndRemove(o);
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        for (Node node = this.head; node != null; node = this.succ(node)) {
            final Object item = node.item;
            if (node.isData) {
                if (item != null && item != node && o.equals(item)) {
                    return true;
                }
            }
            else if (item == null) {
                break;
            }
        }
        return false;
    }
    
    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        final Iterator<Object> iterator = this.iterator();
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
        objectOutputStream.writeObject(null);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        while (true) {
            final Object object = objectInputStream.readObject();
            if (object == null) {
                break;
            }
            this.offer((E)object);
        }
    }
    
    static {
        MP = (Runtime.getRuntime().availableProcessors() > 1);
        try {
            UNSAFE = Unsafe.getUnsafe();
            final Class<LinkedTransferQueue> clazz = LinkedTransferQueue.class;
            headOffset = LinkedTransferQueue.UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
            tailOffset = LinkedTransferQueue.UNSAFE.objectFieldOffset(clazz.getDeclaredField("tail"));
            sweepVotesOffset = LinkedTransferQueue.UNSAFE.objectFieldOffset(clazz.getDeclaredField("sweepVotes"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    final class Itr implements Iterator<E>
    {
        private Node nextNode;
        private E nextItem;
        private Node lastRet;
        private Node lastPred;
        
        private void advance(final Node lastRet) {
            final Node lastRet2;
            if ((lastRet2 = this.lastRet) != null && !lastRet2.isMatched()) {
                this.lastPred = lastRet2;
            }
            else {
                final Node lastPred;
                if ((lastPred = this.lastPred) == null || lastPred.isMatched()) {
                    this.lastPred = null;
                }
                else {
                    Node next;
                    Node next2;
                    while ((next = lastPred.next) != null && next != lastPred && next.isMatched() && (next2 = next.next) != null && next2 != next) {
                        lastPred.casNext(next, next2);
                    }
                }
            }
            this.lastRet = lastRet;
            Node node = lastRet;
            while (true) {
                final Node nextNode = (node == null) ? LinkedTransferQueue.this.head : node.next;
                if (nextNode == null) {
                    break;
                }
                if (nextNode == node) {
                    node = null;
                }
                else {
                    final Object item = nextNode.item;
                    if (nextNode.isData) {
                        if (item != null && item != nextNode) {
                            this.nextItem = LinkedTransferQueue.cast(item);
                            this.nextNode = nextNode;
                            return;
                        }
                    }
                    else if (item == null) {
                        break;
                    }
                    if (node == null) {
                        node = nextNode;
                    }
                    else {
                        final Node next3;
                        if ((next3 = nextNode.next) == null) {
                            break;
                        }
                        if (nextNode == next3) {
                            node = null;
                        }
                        else {
                            node.casNext(nextNode, next3);
                        }
                    }
                }
            }
            this.nextNode = null;
            this.nextItem = null;
        }
        
        Itr() {
            this.advance(null);
        }
        
        @Override
        public final boolean hasNext() {
            return this.nextNode != null;
        }
        
        @Override
        public final E next() {
            final Node nextNode = this.nextNode;
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            final E nextItem = this.nextItem;
            this.advance(nextNode);
            return nextItem;
        }
        
        @Override
        public final void remove() {
            final Node lastRet = this.lastRet;
            if (lastRet == null) {
                throw new IllegalStateException();
            }
            this.lastRet = null;
            if (lastRet.tryMatchData()) {
                LinkedTransferQueue.this.unsplice(this.lastPred, lastRet);
            }
        }
    }
    
    static final class Node
    {
        final boolean isData;
        volatile Object item;
        volatile Node next;
        volatile Thread waiter;
        private static final long serialVersionUID = -3375979862319811754L;
        private static final Unsafe UNSAFE;
        private static final long itemOffset;
        private static final long nextOffset;
        private static final long waiterOffset;
        
        final boolean casNext(final Node node, final Node node2) {
            return Node.UNSAFE.compareAndSwapObject(this, Node.nextOffset, node, node2);
        }
        
        final boolean casItem(final Object o, final Object o2) {
            return Node.UNSAFE.compareAndSwapObject(this, Node.itemOffset, o, o2);
        }
        
        Node(final Object o, final boolean isData) {
            Node.UNSAFE.putObject(this, Node.itemOffset, o);
            this.isData = isData;
        }
        
        final void forgetNext() {
            Node.UNSAFE.putObject(this, Node.nextOffset, this);
        }
        
        final void forgetContents() {
            Node.UNSAFE.putObject(this, Node.itemOffset, this);
            Node.UNSAFE.putObject(this, Node.waiterOffset, null);
        }
        
        final boolean isMatched() {
            final Object item = this.item;
            return item == this || item == null == this.isData;
        }
        
        final boolean isUnmatchedRequest() {
            return !this.isData && this.item == null;
        }
        
        final boolean cannotPrecede(final boolean b) {
            final boolean isData = this.isData;
            final Object item;
            return isData != b && (item = this.item) != this && item != null == isData;
        }
        
        final boolean tryMatchData() {
            final Object item = this.item;
            if (item != null && item != this && this.casItem(item, null)) {
                LockSupport.unpark(this.waiter);
                return true;
            }
            return false;
        }
        
        static {
            try {
                UNSAFE = Unsafe.getUnsafe();
                final Class<Node> clazz = Node.class;
                itemOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("item"));
                nextOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
                waiterOffset = Node.UNSAFE.objectFieldOffset(clazz.getDeclaredField("waiter"));
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }
    
    static final class LTQSpliterator<E> implements Spliterator<E>
    {
        static final int MAX_BATCH = 33554432;
        final LinkedTransferQueue<E> queue;
        Node current;
        int batch;
        boolean exhausted;
        
        LTQSpliterator(final LinkedTransferQueue<E> queue) {
            this.queue = queue;
        }
        
        @Override
        public Spliterator<E> trySplit() {
            final LinkedTransferQueue<E> queue = this.queue;
            final int batch = this.batch;
            final int n = (batch <= 0) ? 1 : ((batch >= 33554432) ? 33554432 : (batch + 1));
            Node current;
            if (!this.exhausted && ((current = this.current) != null || (current = queue.firstDataNode()) != null) && current.next != null) {
                final Object[] array = new Object[n];
                int batch2 = 0;
                do {
                    final Object item = current.item;
                    if (item != current && (array[batch2] = item) != null) {
                        ++batch2;
                    }
                    if (current == (current = current.next)) {
                        current = queue.firstDataNode();
                    }
                } while (current != null && batch2 < n && current.isData);
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
            final LinkedTransferQueue<E> queue = this.queue;
            Node node;
            if (!this.exhausted && ((node = this.current) != null || (node = queue.firstDataNode()) != null)) {
                this.exhausted = true;
                do {
                    final Object item = node.item;
                    if (item != null && item != node) {
                        consumer.accept((Object)item);
                    }
                    if (node == (node = node.next)) {
                        node = queue.firstDataNode();
                    }
                } while (node != null && node.isData);
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            final LinkedTransferQueue<E> queue = this.queue;
            Node current;
            if (!this.exhausted && ((current = this.current) != null || (current = queue.firstDataNode()) != null)) {
                Object item;
                do {
                    if ((item = current.item) == current) {
                        item = null;
                    }
                    if (current == (current = current.next)) {
                        current = queue.firstDataNode();
                    }
                } while (item == null && current != null && current.isData);
                if ((this.current = current) == null) {
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
        public long estimateSize() {
            return Long.MAX_VALUE;
        }
        
        @Override
        public int characteristics() {
            return 4368;
        }
    }
}
