package java.util.concurrent;

import java.util.*;
import java.io.*;
import sun.misc.*;
import java.util.concurrent.locks.*;

public class SynchronousQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, Serializable
{
    private static final long serialVersionUID = -3223113410248163686L;
    static final int NCPUS;
    static final int maxTimedSpins;
    static final int maxUntimedSpins;
    static final long spinForTimeoutThreshold = 1000L;
    private transient volatile Transferer<E> transferer;
    private ReentrantLock qlock;
    private WaitQueue waitingProducers;
    private WaitQueue waitingConsumers;
    
    public SynchronousQueue() {
        this(false);
    }
    
    public SynchronousQueue(final boolean b) {
        this.transferer = (Transferer<E>)(b ? new TransferQueue<E>() : new TransferStack<E>());
    }
    
    @Override
    public void put(final E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        if (this.transferer.transfer(e, false, 0L) == null) {
            Thread.interrupted();
            throw new InterruptedException();
        }
    }
    
    @Override
    public boolean offer(final E e, final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        if (this.transferer.transfer(e, true, timeUnit.toNanos(n)) != null) {
            return true;
        }
        if (!Thread.interrupted()) {
            return false;
        }
        throw new InterruptedException();
    }
    
    @Override
    public boolean offer(final E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        return this.transferer.transfer(e, true, 0L) != null;
    }
    
    @Override
    public E take() throws InterruptedException {
        final E transfer = this.transferer.transfer(null, false, 0L);
        if (transfer != null) {
            return transfer;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }
    
    @Override
    public E poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        final E transfer = this.transferer.transfer(null, true, timeUnit.toNanos(n));
        if (transfer != null || !Thread.interrupted()) {
            return transfer;
        }
        throw new InterruptedException();
    }
    
    @Override
    public E poll() {
        return this.transferer.transfer(null, true, 0L);
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public int remainingCapacity() {
        return 0;
    }
    
    @Override
    public void clear() {
    }
    
    @Override
    public boolean contains(final Object o) {
        return false;
    }
    
    @Override
    public boolean remove(final Object o) {
        return false;
    }
    
    @Override
    public boolean containsAll(final Collection<?> collection) {
        return collection.isEmpty();
    }
    
    @Override
    public boolean removeAll(final Collection<?> collection) {
        return false;
    }
    
    @Override
    public boolean retainAll(final Collection<?> collection) {
        return false;
    }
    
    @Override
    public E peek() {
        return null;
    }
    
    @Override
    public Iterator<E> iterator() {
        return Collections.emptyIterator();
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.emptySpliterator();
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
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.transferer instanceof TransferQueue) {
            this.qlock = new ReentrantLock(true);
            this.waitingProducers = new FifoWaitQueue();
            this.waitingConsumers = new FifoWaitQueue();
        }
        else {
            this.qlock = new ReentrantLock();
            this.waitingProducers = new LifoWaitQueue();
            this.waitingConsumers = new LifoWaitQueue();
        }
        objectOutputStream.defaultWriteObject();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.waitingProducers instanceof FifoWaitQueue) {
            this.transferer = new TransferQueue<E>();
        }
        else {
            this.transferer = new TransferStack<E>();
        }
    }
    
    static long objectFieldOffset(final Unsafe unsafe, final String s, final Class<?> clazz) {
        try {
            return unsafe.objectFieldOffset(clazz.getDeclaredField(s));
        }
        catch (NoSuchFieldException ex) {
            final NoSuchFieldError noSuchFieldError = new NoSuchFieldError(s);
            noSuchFieldError.initCause(ex);
            throw noSuchFieldError;
        }
    }
    
    static {
        NCPUS = Runtime.getRuntime().availableProcessors();
        maxTimedSpins = ((SynchronousQueue.NCPUS < 2) ? 0 : 32);
        maxUntimedSpins = SynchronousQueue.maxTimedSpins * 16;
    }
    
    static class FifoWaitQueue extends WaitQueue
    {
        private static final long serialVersionUID = -3623113410248163686L;
    }
    
    static class WaitQueue implements Serializable
    {
    }
    
    static class LifoWaitQueue extends WaitQueue
    {
        private static final long serialVersionUID = -3633113410248163686L;
    }
    
    static final class TransferQueue<E> extends Transferer<E>
    {
        transient volatile QNode head;
        transient volatile QNode tail;
        transient volatile QNode cleanMe;
        private static final Unsafe UNSAFE;
        private static final long headOffset;
        private static final long tailOffset;
        private static final long cleanMeOffset;
        
        TransferQueue() {
            final QNode qNode = new QNode(null, false);
            this.head = qNode;
            this.tail = qNode;
        }
        
        void advanceHead(final QNode next, final QNode qNode) {
            if (next == this.head && TransferQueue.UNSAFE.compareAndSwapObject(this, TransferQueue.headOffset, next, qNode)) {
                next.next = next;
            }
        }
        
        void advanceTail(final QNode qNode, final QNode qNode2) {
            if (this.tail == qNode) {
                TransferQueue.UNSAFE.compareAndSwapObject(this, TransferQueue.tailOffset, qNode, qNode2);
            }
        }
        
        boolean casCleanMe(final QNode qNode, final QNode qNode2) {
            return this.cleanMe == qNode && TransferQueue.UNSAFE.compareAndSwapObject(this, TransferQueue.cleanMeOffset, qNode, qNode2);
        }
        
        @Override
        E transfer(final E e, final boolean b, final long n) {
            QNode item = null;
            final boolean b2 = e != null;
            while (true) {
                final QNode tail = this.tail;
                final QNode head = this.head;
                if (tail != null) {
                    if (head == null) {
                        continue;
                    }
                    if (head == tail || tail.isData == b2) {
                        final QNode next = tail.next;
                        if (tail != this.tail) {
                            continue;
                        }
                        if (next != null) {
                            this.advanceTail(tail, next);
                        }
                        else {
                            if (b && n <= 0L) {
                                return null;
                            }
                            if (item == null) {
                                item = new QNode(e, b2);
                            }
                            if (!tail.casNext(null, item)) {
                                continue;
                            }
                            this.advanceTail(tail, item);
                            final Object awaitFulfill = this.awaitFulfill(item, e, b, n);
                            if (awaitFulfill == item) {
                                this.clean(tail, item);
                                return null;
                            }
                            if (!item.isOffList()) {
                                this.advanceHead(tail, item);
                                if (awaitFulfill != null) {
                                    item.item = item;
                                }
                                item.waiter = null;
                            }
                            return (E)((awaitFulfill != null) ? awaitFulfill : e);
                        }
                    }
                    else {
                        final QNode next2 = head.next;
                        if (tail != this.tail || next2 == null) {
                            continue;
                        }
                        if (head != this.head) {
                            continue;
                        }
                        final Object item2 = next2.item;
                        if (b2 != (item2 != null) && item2 != next2 && next2.casItem(item2, e)) {
                            this.advanceHead(head, next2);
                            LockSupport.unpark(next2.waiter);
                            return (E)((item2 != null) ? item2 : e);
                        }
                        this.advanceHead(head, next2);
                    }
                }
            }
        }
        
        Object awaitFulfill(final QNode qNode, final E e, final boolean b, long n) {
            final long n2 = b ? (System.nanoTime() + n) : 0L;
            final Thread currentThread = Thread.currentThread();
            int n3 = (this.head.next == qNode) ? (b ? SynchronousQueue.maxTimedSpins : SynchronousQueue.maxUntimedSpins) : 0;
            Object item;
            while (true) {
                if (currentThread.isInterrupted()) {
                    qNode.tryCancel(e);
                }
                item = qNode.item;
                if (item != e) {
                    break;
                }
                if (b) {
                    n = n2 - System.nanoTime();
                    if (n <= 0L) {
                        qNode.tryCancel(e);
                        continue;
                    }
                }
                if (n3 > 0) {
                    --n3;
                }
                else if (qNode.waiter == null) {
                    qNode.waiter = currentThread;
                }
                else if (!b) {
                    LockSupport.park(this);
                }
                else {
                    if (n <= 1000L) {
                        continue;
                    }
                    LockSupport.parkNanos(this, n);
                }
            }
            return item;
        }
        
        void clean(final QNode qNode, final QNode qNode2) {
            qNode2.waiter = null;
            while (qNode.next == qNode2) {
                final QNode head = this.head;
                final QNode next = head.next;
                if (next != null && next.isCancelled()) {
                    this.advanceHead(head, next);
                }
                else {
                    final QNode tail = this.tail;
                    if (tail == head) {
                        return;
                    }
                    final QNode next2 = tail.next;
                    if (tail != this.tail) {
                        continue;
                    }
                    if (next2 != null) {
                        this.advanceTail(tail, next2);
                    }
                    else {
                        if (qNode2 != tail) {
                            final QNode next3 = qNode2.next;
                            if (next3 == qNode2 || qNode.casNext(qNode2, next3)) {
                                return;
                            }
                        }
                        final QNode cleanMe = this.cleanMe;
                        if (cleanMe != null) {
                            final QNode next4 = cleanMe.next;
                            final QNode next5;
                            if (next4 == null || next4 == cleanMe || !next4.isCancelled() || (next4 != tail && (next5 = next4.next) != null && next5 != next4 && cleanMe.casNext(next4, next5))) {
                                this.casCleanMe(cleanMe, null);
                            }
                            if (cleanMe == qNode) {
                                return;
                            }
                            continue;
                        }
                        else {
                            if (this.casCleanMe(null, qNode)) {
                                return;
                            }
                            continue;
                        }
                    }
                }
            }
        }
        
        static {
            try {
                UNSAFE = Unsafe.getUnsafe();
                final Class<TransferQueue> clazz = TransferQueue.class;
                headOffset = TransferQueue.UNSAFE.objectFieldOffset(clazz.getDeclaredField("head"));
                tailOffset = TransferQueue.UNSAFE.objectFieldOffset(clazz.getDeclaredField("tail"));
                cleanMeOffset = TransferQueue.UNSAFE.objectFieldOffset(clazz.getDeclaredField("cleanMe"));
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
        
        static final class QNode
        {
            volatile QNode next;
            volatile Object item;
            volatile Thread waiter;
            final boolean isData;
            private static final Unsafe UNSAFE;
            private static final long itemOffset;
            private static final long nextOffset;
            
            QNode(final Object item, final boolean isData) {
                this.item = item;
                this.isData = isData;
            }
            
            boolean casNext(final QNode qNode, final QNode qNode2) {
                return this.next == qNode && QNode.UNSAFE.compareAndSwapObject(this, QNode.nextOffset, qNode, qNode2);
            }
            
            boolean casItem(final Object o, final Object o2) {
                return this.item == o && QNode.UNSAFE.compareAndSwapObject(this, QNode.itemOffset, o, o2);
            }
            
            void tryCancel(final Object o) {
                QNode.UNSAFE.compareAndSwapObject(this, QNode.itemOffset, o, this);
            }
            
            boolean isCancelled() {
                return this.item == this;
            }
            
            boolean isOffList() {
                return this.next == this;
            }
            
            static {
                try {
                    UNSAFE = Unsafe.getUnsafe();
                    final Class<QNode> clazz = QNode.class;
                    itemOffset = QNode.UNSAFE.objectFieldOffset(clazz.getDeclaredField("item"));
                    nextOffset = QNode.UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
                }
                catch (Exception ex) {
                    throw new Error(ex);
                }
            }
        }
    }
    
    abstract static class Transferer<E>
    {
        abstract E transfer(final E p0, final boolean p1, final long p2);
    }
    
    static final class TransferStack<E> extends Transferer<E>
    {
        static final int REQUEST = 0;
        static final int DATA = 1;
        static final int FULFILLING = 2;
        volatile SNode head;
        private static final Unsafe UNSAFE;
        private static final long headOffset;
        
        static boolean isFulfilling(final int n) {
            return (n & 0x2) != 0x0;
        }
        
        boolean casHead(final SNode sNode, final SNode sNode2) {
            return sNode == this.head && TransferStack.UNSAFE.compareAndSwapObject(this, TransferStack.headOffset, sNode, sNode2);
        }
        
        static SNode snode(SNode sNode, final Object o, final SNode next, final int mode) {
            if (sNode == null) {
                sNode = new SNode(o);
            }
            sNode.mode = mode;
            sNode.next = next;
            return sNode;
        }
        
        @Override
        E transfer(final E e, final boolean b, final long n) {
            SNode sNode = null;
            final int n2 = (e != null) ? 1 : 0;
            while (true) {
                final SNode head = this.head;
                if (head == null || head.mode == n2) {
                    if (b && n <= 0L) {
                        if (head == null || !head.isCancelled()) {
                            return null;
                        }
                        this.casHead(head, head.next);
                    }
                    else {
                        if (!this.casHead(head, sNode = snode(sNode, e, head, n2))) {
                            continue;
                        }
                        final SNode awaitFulfill = this.awaitFulfill(sNode, b, n);
                        if (awaitFulfill == sNode) {
                            this.clean(sNode);
                            return null;
                        }
                        final SNode head2;
                        if ((head2 = this.head) != null && head2.next == sNode) {
                            this.casHead(head2, sNode.next);
                        }
                        return (E)((n2 == 0) ? awaitFulfill.item : sNode.item);
                    }
                }
                else if (!isFulfilling(head.mode)) {
                    if (head.isCancelled()) {
                        this.casHead(head, head.next);
                    }
                    else {
                        if (!this.casHead(head, sNode = snode(sNode, e, head, 0x2 | n2))) {
                            continue;
                        }
                        while (true) {
                            final SNode next = sNode.next;
                            if (next == null) {
                                this.casHead(sNode, null);
                                sNode = null;
                                break;
                            }
                            final SNode next2 = next.next;
                            if (next.tryMatch(sNode)) {
                                this.casHead(sNode, next2);
                                return (E)((n2 == 0) ? next.item : sNode.item);
                            }
                            sNode.casNext(next, next2);
                        }
                    }
                }
                else {
                    final SNode next3 = head.next;
                    if (next3 == null) {
                        this.casHead(head, null);
                    }
                    else {
                        final SNode next4 = next3.next;
                        if (next3.tryMatch(head)) {
                            this.casHead(head, next4);
                        }
                        else {
                            head.casNext(next3, next4);
                        }
                    }
                }
            }
        }
        
        SNode awaitFulfill(final SNode sNode, final boolean b, long n) {
            final long n2 = b ? (System.nanoTime() + n) : 0L;
            final Thread currentThread = Thread.currentThread();
            int n3 = this.shouldSpin(sNode) ? (b ? SynchronousQueue.maxTimedSpins : SynchronousQueue.maxUntimedSpins) : 0;
            SNode match;
            while (true) {
                if (currentThread.isInterrupted()) {
                    sNode.tryCancel();
                }
                match = sNode.match;
                if (match != null) {
                    break;
                }
                if (b) {
                    n = n2 - System.nanoTime();
                    if (n <= 0L) {
                        sNode.tryCancel();
                        continue;
                    }
                }
                if (n3 > 0) {
                    n3 = (this.shouldSpin(sNode) ? (n3 - 1) : 0);
                }
                else if (sNode.waiter == null) {
                    sNode.waiter = currentThread;
                }
                else if (!b) {
                    LockSupport.park(this);
                }
                else {
                    if (n <= 1000L) {
                        continue;
                    }
                    LockSupport.parkNanos(this, n);
                }
            }
            return match;
        }
        
        boolean shouldSpin(final SNode sNode) {
            final SNode head = this.head;
            return head == sNode || head == null || isFulfilling(head.mode);
        }
        
        void clean(final SNode sNode) {
            sNode.item = null;
            sNode.waiter = null;
            SNode sNode2 = sNode.next;
            if (sNode2 != null && sNode2.isCancelled()) {
                sNode2 = sNode2.next;
            }
            SNode head;
            while ((head = this.head) != null && head != sNode2 && head.isCancelled()) {
                this.casHead(head, head.next);
            }
            while (head != null && head != sNode2) {
                final SNode next = head.next;
                if (next != null && next.isCancelled()) {
                    head.casNext(next, next.next);
                }
                else {
                    head = next;
                }
            }
        }
        
        static {
            try {
                UNSAFE = Unsafe.getUnsafe();
                headOffset = TransferStack.UNSAFE.objectFieldOffset(TransferStack.class.getDeclaredField("head"));
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }
        
        static final class SNode
        {
            volatile SNode next;
            volatile SNode match;
            volatile Thread waiter;
            Object item;
            int mode;
            private static final Unsafe UNSAFE;
            private static final long matchOffset;
            private static final long nextOffset;
            
            SNode(final Object item) {
                this.item = item;
            }
            
            boolean casNext(final SNode sNode, final SNode sNode2) {
                return sNode == this.next && SNode.UNSAFE.compareAndSwapObject(this, SNode.nextOffset, sNode, sNode2);
            }
            
            boolean tryMatch(final SNode sNode) {
                if (this.match == null && SNode.UNSAFE.compareAndSwapObject(this, SNode.matchOffset, null, sNode)) {
                    final Thread waiter = this.waiter;
                    if (waiter != null) {
                        this.waiter = null;
                        LockSupport.unpark(waiter);
                    }
                    return true;
                }
                return this.match == sNode;
            }
            
            void tryCancel() {
                SNode.UNSAFE.compareAndSwapObject(this, SNode.matchOffset, null, this);
            }
            
            boolean isCancelled() {
                return this.match == this;
            }
            
            static {
                try {
                    UNSAFE = Unsafe.getUnsafe();
                    final Class<SNode> clazz = SNode.class;
                    matchOffset = SNode.UNSAFE.objectFieldOffset(clazz.getDeclaredField("match"));
                    nextOffset = SNode.UNSAFE.objectFieldOffset(clazz.getDeclaredField("next"));
                }
                catch (Exception ex) {
                    throw new Error(ex);
                }
            }
        }
    }
}
