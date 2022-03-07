package java.util.concurrent.atomic;

import sun.misc.*;

public class AtomicMarkableReference<V>
{
    private volatile Pair<V> pair;
    private static final Unsafe UNSAFE;
    private static final long pairOffset;
    
    public AtomicMarkableReference(final V v, final boolean b) {
        this.pair = Pair.of(v, b);
    }
    
    public V getReference() {
        return this.pair.reference;
    }
    
    public boolean isMarked() {
        return this.pair.mark;
    }
    
    public V get(final boolean[] array) {
        final Pair<V> pair = this.pair;
        array[0] = pair.mark;
        return pair.reference;
    }
    
    public boolean weakCompareAndSet(final V v, final V v2, final boolean b, final boolean b2) {
        return this.compareAndSet(v, v2, b, b2);
    }
    
    public boolean compareAndSet(final V v, final V v2, final boolean b, final boolean b2) {
        final Pair<V> pair = this.pair;
        return v == pair.reference && b == pair.mark && ((v2 == pair.reference && b2 == pair.mark) || this.casPair(pair, Pair.of(v2, b2)));
    }
    
    public void set(final V v, final boolean b) {
        final Pair<V> pair = this.pair;
        if (v != pair.reference || b != pair.mark) {
            this.pair = Pair.of(v, b);
        }
    }
    
    public boolean attemptMark(final V v, final boolean b) {
        final Pair<V> pair = this.pair;
        return v == pair.reference && (b == pair.mark || this.casPair(pair, Pair.of(v, b)));
    }
    
    private boolean casPair(final Pair<V> pair, final Pair<V> pair2) {
        return AtomicMarkableReference.UNSAFE.compareAndSwapObject(this, AtomicMarkableReference.pairOffset, pair, pair2);
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
        UNSAFE = Unsafe.getUnsafe();
        pairOffset = objectFieldOffset(AtomicMarkableReference.UNSAFE, "pair", AtomicMarkableReference.class);
    }
    
    private static class Pair<T>
    {
        final T reference;
        final boolean mark;
        
        private Pair(final T reference, final boolean mark) {
            this.reference = reference;
            this.mark = mark;
        }
        
        static <T> Pair<T> of(final T t, final boolean b) {
            return new Pair<T>(t, b);
        }
    }
}
