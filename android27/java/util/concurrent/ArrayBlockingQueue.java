package java.util.concurrent;

import java.util.concurrent.locks.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.lang.ref.*;

public class ArrayBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, Serializable
{
    private static final long serialVersionUID = -817911632652898426L;
    final Object[] items;
    int takeIndex;
    int putIndex;
    int count;
    final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    transient Itrs itrs;
    
    final int dec(final int n) {
        return ((n == 0) ? this.items.length : n) - 1;
    }
    
    final E itemAt(final int n) {
        return (E)this.items[n];
    }
    
    private static void checkNotNull(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }
    
    private void enqueue(final E e) {
        final Object[] items = this.items;
        items[this.putIndex] = e;
        if (++this.putIndex == items.length) {
            this.putIndex = 0;
        }
        ++this.count;
        this.notEmpty.signal();
    }
    
    private E dequeue() {
        final Object[] items = this.items;
        final Object o = items[this.takeIndex];
        items[this.takeIndex] = null;
        if (++this.takeIndex == items.length) {
            this.takeIndex = 0;
        }
        --this.count;
        if (this.itrs != null) {
            this.itrs.elementDequeued();
        }
        this.notFull.signal();
        return (E)o;
    }
    
    void removeAt(final int n) {
        final Object[] items = this.items;
        if (n == this.takeIndex) {
            items[this.takeIndex] = null;
            if (++this.takeIndex == items.length) {
                this.takeIndex = 0;
            }
            --this.count;
            if (this.itrs != null) {
                this.itrs.elementDequeued();
            }
        }
        else {
            final int putIndex = this.putIndex;
            int putIndex2 = n;
            while (true) {
                int n2 = putIndex2 + 1;
                if (n2 == items.length) {
                    n2 = 0;
                }
                if (n2 == putIndex) {
                    break;
                }
                items[putIndex2] = items[n2];
                putIndex2 = n2;
            }
            items[putIndex2] = null;
            this.putIndex = putIndex2;
            --this.count;
            if (this.itrs != null) {
                this.itrs.removedAt(n);
            }
        }
        this.notFull.signal();
    }
    
    public ArrayBlockingQueue(final int n) {
        this(n, false);
    }
    
    public ArrayBlockingQueue(final int n, final boolean b) {
        this.itrs = null;
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.items = new Object[n];
        this.lock = new ReentrantLock(b);
        this.notEmpty = this.lock.newCondition();
        this.notFull = this.lock.newCondition();
    }
    
    public ArrayBlockingQueue(final int n, final boolean b, final Collection<? extends E> collection) {
        this(n, b);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int count = 0;
            try {
                for (final E next : collection) {
                    checkNotNull(next);
                    this.items[count++] = next;
                }
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                throw new IllegalArgumentException();
            }
            this.count = count;
            this.putIndex = ((count == n) ? 0 : count);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean add(final E e) {
        return super.add(e);
    }
    
    @Override
    public boolean offer(final E e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (this.count == this.items.length) {
                return false;
            }
            this.enqueue(e);
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void put(final E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (this.count == this.items.length) {
                this.notFull.await();
            }
            this.enqueue(e);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean offer(final E e, final long n, final TimeUnit timeUnit) throws InterruptedException {
        checkNotNull(e);
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (this.count == this.items.length) {
                if (n2 <= 0L) {
                    return false;
                }
                n2 = this.notFull.awaitNanos(n2);
            }
            this.enqueue(e);
            return true;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (this.count == 0) ? null : this.dequeue();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (this.count == 0) {
                this.notEmpty.await();
            }
            return this.dequeue();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        long n2 = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (this.count == 0) {
                if (n2 <= 0L) {
                    return null;
                }
                n2 = this.notEmpty.awaitNanos(n2);
            }
            return this.dequeue();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public E peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.itemAt(this.takeIndex);
        }
        finally {
            lock.unlock();
        }
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
    public int remainingCapacity() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.items.length - this.count;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            return false;
        }
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (this.count > 0) {
                final int putIndex = this.putIndex;
                int takeIndex = this.takeIndex;
                while (!o.equals(items[takeIndex])) {
                    if (++takeIndex == items.length) {
                        takeIndex = 0;
                    }
                    if (takeIndex == putIndex) {
                        return false;
                    }
                }
                this.removeAt(takeIndex);
                return true;
            }
            return false;
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
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (this.count > 0) {
                final int putIndex = this.putIndex;
                int takeIndex = this.takeIndex;
                while (!o.equals(items[takeIndex])) {
                    if (++takeIndex == items.length) {
                        takeIndex = 0;
                    }
                    if (takeIndex == putIndex) {
                        return false;
                    }
                }
                return true;
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
        Object[] array;
        try {
            final int count = this.count;
            array = new Object[count];
            final int n = this.items.length - this.takeIndex;
            if (count <= n) {
                System.arraycopy(this.items, this.takeIndex, array, 0, count);
            }
            else {
                System.arraycopy(this.items, this.takeIndex, array, 0, n);
                System.arraycopy(this.items, 0, array, n, count - n);
            }
        }
        finally {
            lock.unlock();
        }
        return array;
    }
    
    @Override
    public <T> T[] toArray(T[] array) {
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int count = this.count;
            final int length = array.length;
            if (length < count) {
                array = (T[])Array.newInstance(array.getClass().getComponentType(), count);
            }
            final int n = items.length - this.takeIndex;
            if (count <= n) {
                System.arraycopy(items, this.takeIndex, array, 0, count);
            }
            else {
                System.arraycopy(items, this.takeIndex, array, 0, n);
                System.arraycopy(items, 0, array, n, count - n);
            }
            if (length > count) {
                array[count] = null;
            }
        }
        finally {
            lock.unlock();
        }
        return array;
    }
    
    @Override
    public String toString() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int count = this.count;
            if (count == 0) {
                return "[]";
            }
            final Object[] items = this.items;
            final StringBuilder sb = new StringBuilder();
            sb.append('[');
            int takeIndex = this.takeIndex;
            while (true) {
                final Object o = items[takeIndex];
                sb.append((o == this) ? "(this Collection)" : o);
                if (--count == 0) {
                    break;
                }
                sb.append(',').append(' ');
                if (++takeIndex != items.length) {
                    continue;
                }
                takeIndex = 0;
            }
            return sb.append(']').toString();
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void clear() {
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int count = this.count;
            if (count > 0) {
                final int putIndex = this.putIndex;
                int i = this.takeIndex;
                do {
                    items[i] = null;
                    if (++i == items.length) {
                        i = 0;
                    }
                } while (i != putIndex);
                this.takeIndex = putIndex;
                this.count = 0;
                if (this.itrs != null) {
                    this.itrs.queueIsEmpty();
                }
                while (count > 0 && lock.hasWaiters(this.notFull)) {
                    this.notFull.signal();
                    --count;
                }
            }
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
        checkNotNull(collection);
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        if (n <= 0) {
            return 0;
        }
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int min = Math.min(n, this.count);
            int takeIndex = this.takeIndex;
            int i = 0;
            try {
                while (i < min) {
                    collection.add((Object)items[takeIndex]);
                    items[takeIndex] = null;
                    if (++takeIndex == items.length) {
                        takeIndex = 0;
                    }
                    ++i;
                }
                return min;
            }
            finally {
                if (i > 0) {
                    this.count -= i;
                    this.takeIndex = takeIndex;
                    if (this.itrs != null) {
                        if (this.count == 0) {
                            this.itrs.queueIsEmpty();
                        }
                        else if (i > takeIndex) {
                            this.itrs.takeIndexWrapped();
                        }
                    }
                    while (i > 0 && lock.hasWaiters(this.notFull)) {
                        this.notFull.signal();
                        --i;
                    }
                }
            }
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
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator((Collection<? extends E>)this, 4368);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.items.length == 0 || this.takeIndex < 0 || this.takeIndex >= this.items.length || this.putIndex < 0 || this.putIndex >= this.items.length || this.count < 0 || this.count > this.items.length || Math.floorMod(this.putIndex - this.takeIndex, this.items.length) != Math.floorMod(this.count, this.items.length)) {
            throw new InvalidObjectException("invariants violated");
        }
    }
    
    private class Itr implements Iterator<E>
    {
        private int cursor;
        private E nextItem;
        private int nextIndex;
        private E lastItem;
        private int lastRet;
        private int prevTakeIndex;
        private int prevCycles;
        private static final int NONE = -1;
        private static final int REMOVED = -2;
        private static final int DETACHED = -3;
        
        Itr() {
            this.lastRet = -1;
            final ReentrantLock lock = ArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                if (ArrayBlockingQueue.this.count == 0) {
                    this.cursor = -1;
                    this.nextIndex = -1;
                    this.prevTakeIndex = -3;
                }
                else {
                    final int takeIndex = ArrayBlockingQueue.this.takeIndex;
                    this.prevTakeIndex = takeIndex;
                    final int nextIndex = takeIndex;
                    this.nextIndex = nextIndex;
                    this.nextItem = ArrayBlockingQueue.this.itemAt(nextIndex);
                    this.cursor = this.incCursor(takeIndex);
                    if (ArrayBlockingQueue.this.itrs == null) {
                        ArrayBlockingQueue.this.itrs = new Itrs(this);
                    }
                    else {
                        ArrayBlockingQueue.this.itrs.register(this);
                        ArrayBlockingQueue.this.itrs.doSomeSweeping(false);
                    }
                    this.prevCycles = ArrayBlockingQueue.this.itrs.cycles;
                }
            }
            finally {
                lock.unlock();
            }
        }
        
        boolean isDetached() {
            return this.prevTakeIndex < 0;
        }
        
        private int incCursor(int n) {
            if (++n == ArrayBlockingQueue.this.items.length) {
                n = 0;
            }
            if (n == ArrayBlockingQueue.this.putIndex) {
                n = -1;
            }
            return n;
        }
        
        private boolean invalidated(final int n, final int n2, final long n3, final int n4) {
            if (n < 0) {
                return false;
            }
            int n5 = n - n2;
            if (n5 < 0) {
                n5 += n4;
            }
            return n3 > n5;
        }
        
        private void incorporateDequeues() {
            final int cycles = ArrayBlockingQueue.this.itrs.cycles;
            final int takeIndex = ArrayBlockingQueue.this.takeIndex;
            final int prevCycles = this.prevCycles;
            final int prevTakeIndex = this.prevTakeIndex;
            if (cycles != prevCycles || takeIndex != prevTakeIndex) {
                final int length = ArrayBlockingQueue.this.items.length;
                final long n = (cycles - prevCycles) * length + (takeIndex - prevTakeIndex);
                if (this.invalidated(this.lastRet, prevTakeIndex, n, length)) {
                    this.lastRet = -2;
                }
                if (this.invalidated(this.nextIndex, prevTakeIndex, n, length)) {
                    this.nextIndex = -2;
                }
                if (this.invalidated(this.cursor, prevTakeIndex, n, length)) {
                    this.cursor = takeIndex;
                }
                if (this.cursor < 0 && this.nextIndex < 0 && this.lastRet < 0) {
                    this.detach();
                }
                else {
                    this.prevCycles = cycles;
                    this.prevTakeIndex = takeIndex;
                }
            }
        }
        
        private void detach() {
            if (this.prevTakeIndex >= 0) {
                this.prevTakeIndex = -3;
                ArrayBlockingQueue.this.itrs.doSomeSweeping(true);
            }
        }
        
        @Override
        public boolean hasNext() {
            if (this.nextItem != null) {
                return true;
            }
            this.noNext();
            return false;
        }
        
        private void noNext() {
            final ReentrantLock lock = ArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                if (!this.isDetached()) {
                    this.incorporateDequeues();
                    if (this.lastRet >= 0) {
                        this.lastItem = ArrayBlockingQueue.this.itemAt(this.lastRet);
                        this.detach();
                    }
                }
            }
            finally {
                lock.unlock();
            }
        }
        
        @Override
        public E next() {
            final E nextItem = this.nextItem;
            if (nextItem == null) {
                throw new NoSuchElementException();
            }
            final ReentrantLock lock = ArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                if (!this.isDetached()) {
                    this.incorporateDequeues();
                }
                this.lastRet = this.nextIndex;
                final int cursor = this.cursor;
                if (cursor >= 0) {
                    final ArrayBlockingQueue this$0 = ArrayBlockingQueue.this;
                    final int nextIndex = cursor;
                    this.nextIndex = nextIndex;
                    this.nextItem = this$0.itemAt(nextIndex);
                    this.cursor = this.incCursor(cursor);
                }
                else {
                    this.nextIndex = -1;
                    this.nextItem = null;
                }
            }
            finally {
                lock.unlock();
            }
            return nextItem;
        }
        
        @Override
        public void remove() {
            final ReentrantLock lock = ArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                if (!this.isDetached()) {
                    this.incorporateDequeues();
                }
                final int lastRet = this.lastRet;
                this.lastRet = -1;
                if (lastRet >= 0) {
                    if (!this.isDetached()) {
                        ArrayBlockingQueue.this.removeAt(lastRet);
                    }
                    else {
                        final E lastItem = this.lastItem;
                        this.lastItem = null;
                        if (ArrayBlockingQueue.this.itemAt(lastRet) == lastItem) {
                            ArrayBlockingQueue.this.removeAt(lastRet);
                        }
                    }
                }
                else if (lastRet == -1) {
                    throw new IllegalStateException();
                }
                if (this.cursor < 0 && this.nextIndex < 0) {
                    this.detach();
                }
            }
            finally {
                lock.unlock();
            }
        }
        
        void shutdown() {
            this.cursor = -1;
            if (this.nextIndex >= 0) {
                this.nextIndex = -2;
            }
            if (this.lastRet >= 0) {
                this.lastRet = -2;
                this.lastItem = null;
            }
            this.prevTakeIndex = -3;
        }
        
        private int distance(final int n, final int n2, final int n3) {
            int n4 = n - n2;
            if (n4 < 0) {
                n4 += n3;
            }
            return n4;
        }
        
        boolean removedAt(final int n) {
            if (this.isDetached()) {
                return true;
            }
            final int cycles = ArrayBlockingQueue.this.itrs.cycles;
            final int takeIndex = ArrayBlockingQueue.this.takeIndex;
            final int prevCycles = this.prevCycles;
            final int prevTakeIndex = this.prevTakeIndex;
            final int length = ArrayBlockingQueue.this.items.length;
            int n2 = cycles - prevCycles;
            if (n < takeIndex) {
                ++n2;
            }
            final int n3 = n2 * length + (n - prevTakeIndex);
            int cursor = this.cursor;
            if (cursor >= 0) {
                final int distance = this.distance(cursor, prevTakeIndex, length);
                if (distance == n3) {
                    if (cursor == ArrayBlockingQueue.this.putIndex) {
                        cursor = (this.cursor = -1);
                    }
                }
                else if (distance > n3) {
                    cursor = (this.cursor = ArrayBlockingQueue.this.dec(cursor));
                }
            }
            int lastRet = this.lastRet;
            if (lastRet >= 0) {
                final int distance2 = this.distance(lastRet, prevTakeIndex, length);
                if (distance2 == n3) {
                    lastRet = (this.lastRet = -2);
                }
                else if (distance2 > n3) {
                    lastRet = (this.lastRet = ArrayBlockingQueue.this.dec(lastRet));
                }
            }
            final int nextIndex = this.nextIndex;
            if (nextIndex >= 0) {
                final int distance3 = this.distance(nextIndex, prevTakeIndex, length);
                if (distance3 == n3) {
                    this.nextIndex = -2;
                }
                else if (distance3 > n3) {
                    this.nextIndex = ArrayBlockingQueue.this.dec(nextIndex);
                }
            }
            else if (cursor < 0 && nextIndex < 0 && lastRet < 0) {
                this.prevTakeIndex = -3;
                return true;
            }
            return false;
        }
        
        boolean takeIndexWrapped() {
            if (this.isDetached()) {
                return true;
            }
            if (ArrayBlockingQueue.this.itrs.cycles - this.prevCycles > 1) {
                this.shutdown();
                return true;
            }
            return false;
        }
    }
    
    class Itrs
    {
        int cycles;
        private Node head;
        private Node sweeper;
        private static final int SHORT_SWEEP_PROBES = 4;
        private static final int LONG_SWEEP_PROBES = 16;
        
        Itrs(final Itr itr) {
            this.cycles = 0;
            this.sweeper = null;
            this.register(itr);
        }
        
        void doSomeSweeping(final boolean b) {
            int i = b ? 16 : 4;
            final Node sweeper = this.sweeper;
            Node node;
            Node node2;
            int n;
            if (sweeper == null) {
                node = null;
                node2 = this.head;
                n = 1;
            }
            else {
                node = sweeper;
                node2 = node.next;
                n = 0;
            }
            while (i > 0) {
                if (node2 == null) {
                    if (n != 0) {
                        break;
                    }
                    node = null;
                    node2 = this.head;
                    n = 1;
                }
                final Itr itr = node2.get();
                final Node next = node2.next;
                if (itr == null || itr.isDetached()) {
                    i = 16;
                    node2.clear();
                    node2.next = null;
                    if (node == null) {
                        if ((this.head = next) == null) {
                            ArrayBlockingQueue.this.itrs = null;
                            return;
                        }
                    }
                    else {
                        node.next = next;
                    }
                }
                else {
                    node = node2;
                }
                node2 = next;
                --i;
            }
            this.sweeper = ((node2 == null) ? null : node);
        }
        
        void register(final Itr itr) {
            this.head = new Node(itr, this.head);
        }
        
        void takeIndexWrapped() {
            ++this.cycles;
            Node node = null;
            Node next;
            for (Node head = this.head; head != null; head = next) {
                final Itr itr = head.get();
                next = head.next;
                if (itr == null || itr.takeIndexWrapped()) {
                    head.clear();
                    head.next = null;
                    if (node == null) {
                        this.head = next;
                    }
                    else {
                        node.next = next;
                    }
                }
                else {
                    node = head;
                }
            }
            if (this.head == null) {
                ArrayBlockingQueue.this.itrs = null;
            }
        }
        
        void removedAt(final int n) {
            Node node = null;
            Node next;
            for (Node head = this.head; head != null; head = next) {
                final Itr itr = head.get();
                next = head.next;
                if (itr == null || itr.removedAt(n)) {
                    head.clear();
                    head.next = null;
                    if (node == null) {
                        this.head = next;
                    }
                    else {
                        node.next = next;
                    }
                }
                else {
                    node = head;
                }
            }
            if (this.head == null) {
                ArrayBlockingQueue.this.itrs = null;
            }
        }
        
        void queueIsEmpty() {
            for (Node node = this.head; node != null; node = node.next) {
                final Itr itr = node.get();
                if (itr != null) {
                    node.clear();
                    itr.shutdown();
                }
            }
            this.head = null;
            ArrayBlockingQueue.this.itrs = null;
        }
        
        void elementDequeued() {
            if (ArrayBlockingQueue.this.count == 0) {
                this.queueIsEmpty();
            }
            else if (ArrayBlockingQueue.this.takeIndex == 0) {
                this.takeIndexWrapped();
            }
        }
        
        private class Node extends WeakReference<Itr>
        {
            Node next;
            
            Node(final Itr itr, final Node next) {
                super(itr);
                this.next = next;
            }
        }
    }
}
