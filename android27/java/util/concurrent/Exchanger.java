package java.util.concurrent;

import sun.misc.*;

public class Exchanger<V>
{
    private static final int ASHIFT = 7;
    private static final int MMASK = 255;
    private static final int SEQ = 256;
    private static final int NCPU;
    static final int FULL;
    private static final int SPINS = 1024;
    private static final Object NULL_ITEM;
    private static final Object TIMED_OUT;
    private final Participant participant;
    private volatile Node[] arena;
    private volatile Node slot;
    private volatile int bound;
    private static final Unsafe U;
    private static final long BOUND;
    private static final long SLOT;
    private static final long MATCH;
    private static final long BLOCKER;
    private static final int ABASE;
    
    private final Object arenaExchange(final Object o, final boolean b, long n) {
        final Node[] arena = this.arena;
        final Node node = this.participant.get();
        int index = node.index;
        while (true) {
            final long n2;
            final Node node2 = (Node)Exchanger.U.getObjectVolatile(arena, n2 = (index << 7) + Exchanger.ABASE);
            if (node2 != null && Exchanger.U.compareAndSwapObject(arena, n2, node2, null)) {
                final Object item = node2.item;
                node2.match = o;
                final Thread parked = node2.parked;
                if (parked != null) {
                    Exchanger.U.unpark(parked);
                }
                return item;
            }
            final int bound;
            final int n3;
            if (index <= (n3 = ((bound = this.bound) & 0xFF)) && node2 == null) {
                node.item = o;
                if (Exchanger.U.compareAndSwapObject(arena, n2, null, node)) {
                    final long n4 = (b && n3 == 0) ? (System.nanoTime() + n) : 0L;
                    final Thread currentThread = Thread.currentThread();
                    int hash = node.hash;
                    int n5 = 1024;
                    while (true) {
                        final Object match = node.match;
                        if (match != null) {
                            Exchanger.U.putOrderedObject(node, Exchanger.MATCH, null);
                            node.item = null;
                            node.hash = hash;
                            return match;
                        }
                        if (n5 > 0) {
                            final int n6 = hash ^ hash << 1;
                            final int n7 = n6 ^ n6 >>> 3;
                            hash = (n7 ^ n7 << 10);
                            if (hash == 0) {
                                hash = (0x400 | (int)currentThread.getId());
                            }
                            else {
                                if (hash >= 0 || (--n5 & 0x1FF) != 0x0) {
                                    continue;
                                }
                                Thread.yield();
                            }
                        }
                        else if (Exchanger.U.getObjectVolatile(arena, n2) != node) {
                            n5 = 1024;
                        }
                        else if (!currentThread.isInterrupted() && n3 == 0 && (!b || (n = n4 - System.nanoTime()) > 0L)) {
                            Exchanger.U.putObject(currentThread, Exchanger.BLOCKER, this);
                            node.parked = currentThread;
                            if (Exchanger.U.getObjectVolatile(arena, n2) == node) {
                                Exchanger.U.park(false, n);
                            }
                            node.parked = null;
                            Exchanger.U.putObject(currentThread, Exchanger.BLOCKER, null);
                        }
                        else {
                            if (Exchanger.U.getObjectVolatile(arena, n2) != node || !Exchanger.U.compareAndSwapObject(arena, n2, node, null)) {
                                continue;
                            }
                            if (n3 != 0) {
                                Exchanger.U.compareAndSwapInt(this, Exchanger.BOUND, bound, bound + 256 - 1);
                            }
                            node.item = null;
                            node.hash = hash;
                            final Node node3 = node;
                            final int index2 = node3.index >>> 1;
                            node3.index = index2;
                            index = index2;
                            if (Thread.interrupted()) {
                                return null;
                            }
                            if (b && n3 == 0 && n <= 0L) {
                                return Exchanger.TIMED_OUT;
                            }
                            break;
                        }
                    }
                }
                else {
                    node.item = null;
                }
            }
            else {
                if (node.bound != bound) {
                    node.bound = bound;
                    node.collides = 0;
                    index = ((index != n3 || n3 == 0) ? n3 : (n3 - 1));
                }
                else {
                    final int collides;
                    if ((collides = node.collides) < n3 || n3 == Exchanger.FULL || !Exchanger.U.compareAndSwapInt(this, Exchanger.BOUND, bound, bound + 256 + 1)) {
                        node.collides = collides + 1;
                        index = ((index == 0) ? n3 : (index - 1));
                    }
                    else {
                        index = n3 + 1;
                    }
                }
                node.index = index;
            }
        }
    }
    
    private final Object slotExchange(final Object o, final boolean b, long n) {
        final Node node = this.participant.get();
        final Thread currentThread = Thread.currentThread();
        if (currentThread.isInterrupted()) {
            return null;
        }
        while (true) {
            final Node slot;
            if ((slot = this.slot) != null) {
                if (Exchanger.U.compareAndSwapObject(this, Exchanger.SLOT, slot, null)) {
                    final Object item = slot.item;
                    slot.match = o;
                    final Thread parked = slot.parked;
                    if (parked != null) {
                        Exchanger.U.unpark(parked);
                    }
                    return item;
                }
                if (Exchanger.NCPU <= 1 || this.bound != 0 || !Exchanger.U.compareAndSwapInt(this, Exchanger.BOUND, 0, 256)) {
                    continue;
                }
                this.arena = new Node[Exchanger.FULL + 2 << 7];
            }
            else {
                if (this.arena != null) {
                    return null;
                }
                node.item = o;
                if (Exchanger.U.compareAndSwapObject(this, Exchanger.SLOT, null, node)) {
                    int hash = node.hash;
                    final long n2 = b ? (System.nanoTime() + n) : 0L;
                    int n3 = (Exchanger.NCPU > 1) ? 1024 : 1;
                    Object match;
                    while ((match = node.match) == null) {
                        if (n3 > 0) {
                            final int n4 = hash ^ hash << 1;
                            final int n5 = n4 ^ n4 >>> 3;
                            hash = (n5 ^ n5 << 10);
                            if (hash == 0) {
                                hash = (0x400 | (int)currentThread.getId());
                            }
                            else {
                                if (hash >= 0 || (--n3 & 0x1FF) != 0x0) {
                                    continue;
                                }
                                Thread.yield();
                            }
                        }
                        else if (this.slot != node) {
                            n3 = 1024;
                        }
                        else if (!currentThread.isInterrupted() && this.arena == null && (!b || (n = n2 - System.nanoTime()) > 0L)) {
                            Exchanger.U.putObject(currentThread, Exchanger.BLOCKER, this);
                            node.parked = currentThread;
                            if (this.slot == node) {
                                Exchanger.U.park(false, n);
                            }
                            node.parked = null;
                            Exchanger.U.putObject(currentThread, Exchanger.BLOCKER, null);
                        }
                        else {
                            if (Exchanger.U.compareAndSwapObject(this, Exchanger.SLOT, node, null)) {
                                match = ((b && n <= 0L && !currentThread.isInterrupted()) ? Exchanger.TIMED_OUT : null);
                                break;
                            }
                            continue;
                        }
                    }
                    Exchanger.U.putOrderedObject(node, Exchanger.MATCH, null);
                    node.item = null;
                    node.hash = hash;
                    return match;
                }
                node.item = null;
            }
        }
    }
    
    public Exchanger() {
        this.participant = new Participant();
    }
    
    public V exchange(final V v) throws InterruptedException {
        final Object o = (v == null) ? Exchanger.NULL_ITEM : v;
        Object o2;
        if ((this.arena != null || (o2 = this.slotExchange(o, false, 0L)) == null) && (Thread.interrupted() || (o2 = this.arenaExchange(o, false, 0L)) == null)) {
            throw new InterruptedException();
        }
        return (V)((o2 == Exchanger.NULL_ITEM) ? null : o2);
    }
    
    public V exchange(final V v, final long n, final TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        final Object o = (v == null) ? Exchanger.NULL_ITEM : v;
        final long nanos = timeUnit.toNanos(n);
        Object o2;
        if ((this.arena != null || (o2 = this.slotExchange(o, true, nanos)) == null) && (Thread.interrupted() || (o2 = this.arenaExchange(o, true, nanos)) == null)) {
            throw new InterruptedException();
        }
        if (o2 == Exchanger.TIMED_OUT) {
            throw new TimeoutException();
        }
        return (V)((o2 == Exchanger.NULL_ITEM) ? null : o2);
    }
    
    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        FULL = ((Exchanger.NCPU >= 510) ? 255 : (Exchanger.NCPU >>> 1));
        NULL_ITEM = new Object();
        TIMED_OUT = new Object();
        int arrayIndexScale;
        try {
            U = Unsafe.getUnsafe();
            final Class<Exchanger> clazz = Exchanger.class;
            final Class<Node> clazz2 = Node.class;
            final Class<Node[]> clazz3 = Node[].class;
            final Class<Thread> clazz4 = Thread.class;
            BOUND = Exchanger.U.objectFieldOffset(clazz.getDeclaredField("bound"));
            SLOT = Exchanger.U.objectFieldOffset(clazz.getDeclaredField("slot"));
            MATCH = Exchanger.U.objectFieldOffset(clazz2.getDeclaredField("match"));
            BLOCKER = Exchanger.U.objectFieldOffset(clazz4.getDeclaredField("parkBlocker"));
            arrayIndexScale = Exchanger.U.arrayIndexScale(clazz3);
            ABASE = Exchanger.U.arrayBaseOffset(clazz3) + 128;
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
        if ((arrayIndexScale & arrayIndexScale - 1) != 0x0 || arrayIndexScale > 128) {
            throw new Error("Unsupported array scale");
        }
    }
    
    @Contended
    static final class Node
    {
        int index;
        int bound;
        int collides;
        int hash;
        Object item;
        volatile Object match;
        volatile Thread parked;
    }
    
    static final class Participant extends ThreadLocal<Node>
    {
        public Node initialValue() {
            return new Node();
        }
    }
}
