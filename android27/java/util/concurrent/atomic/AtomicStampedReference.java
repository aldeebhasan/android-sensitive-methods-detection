package java.util.concurrent.atomic;

import sun.misc.*;

public class AtomicStampedReference<V>
{
    private volatile Pair<V> pair;
    private static final Unsafe UNSAFE;
    private static final long pairOffset;
    
    public AtomicStampedReference(final V v, final int n) {
        this.pair = Pair.of(v, n);
    }
    
    public V getReference() {
        return this.pair.reference;
    }
    
    public int getStamp() {
        return this.pair.stamp;
    }
    
    public V get(final int[] array) {
        final Pair<V> pair = this.pair;
        array[0] = pair.stamp;
        return pair.reference;
    }
    
    public boolean weakCompareAndSet(final V v, final V v2, final int n, final int n2) {
        return this.compareAndSet(v, v2, n, n2);
    }
    
    public boolean compareAndSet(final V v, final V v2, final int n, final int n2) {
        final Pair<V> pair = this.pair;
        return v == pair.reference && n == pair.stamp && ((v2 == pair.reference && n2 == pair.stamp) || this.casPair(pair, Pair.of(v2, n2)));
    }
    
    public void set(final V v, final int n) {
        final Pair<V> pair = this.pair;
        if (v != pair.reference || n != pair.stamp) {
            this.pair = Pair.of(v, n);
        }
    }
    
    public boolean attemptStamp(final V v, final int n) {
        final Pair<V> pair = this.pair;
        return v == pair.reference && (n == pair.stamp || this.casPair(pair, Pair.of(v, n)));
    }
    
    private boolean casPair(final Pair<V> pair, final Pair<V> pair2) {
        return AtomicStampedReference.UNSAFE.compareAndSwapObject(this, AtomicStampedReference.pairOffset, pair, pair2);
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
        pairOffset = objectFieldOffset(AtomicStampedReference.UNSAFE, "pair", AtomicStampedReference.class);
    }
    
    private static class Pair<T>
    {
        final T reference;
        final int stamp;
        
        private Pair(final T reference, final int stamp) {
            this.reference = reference;
            this.stamp = stamp;
        }
        
        static <T> Pair<T> of(final T t, final int n) {
            return new Pair<T>(t, n);
        }
    }
}
