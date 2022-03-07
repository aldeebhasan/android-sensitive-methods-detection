package java.util.concurrent.atomic;

import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class AtomicReference<V> implements Serializable
{
    private static final long serialVersionUID = -1848883965231344442L;
    private static final Unsafe unsafe;
    private static final long valueOffset;
    private volatile V value;
    
    public AtomicReference(final V value) {
        this.value = value;
    }
    
    public AtomicReference() {
    }
    
    public final V get() {
        return this.value;
    }
    
    public final void set(final V value) {
        this.value = value;
    }
    
    public final void lazySet(final V v) {
        AtomicReference.unsafe.putOrderedObject(this, AtomicReference.valueOffset, v);
    }
    
    public final boolean compareAndSet(final V v, final V v2) {
        return AtomicReference.unsafe.compareAndSwapObject(this, AtomicReference.valueOffset, v, v2);
    }
    
    public final boolean weakCompareAndSet(final V v, final V v2) {
        return AtomicReference.unsafe.compareAndSwapObject(this, AtomicReference.valueOffset, v, v2);
    }
    
    public final V getAndSet(final V v) {
        return (V)AtomicReference.unsafe.getAndSetObject(this, AtomicReference.valueOffset, v);
    }
    
    public final V getAndUpdate(final UnaryOperator<V> unaryOperator) {
        V value;
        do {
            value = this.get();
        } while (!this.compareAndSet(value, (V)unaryOperator.apply(value)));
        return value;
    }
    
    public final V updateAndGet(final UnaryOperator<V> unaryOperator) {
        V value;
        Object apply;
        do {
            value = this.get();
            apply = unaryOperator.apply(value);
        } while (!this.compareAndSet(value, (V)apply));
        return (V)apply;
    }
    
    public final V getAndAccumulate(final V v, final BinaryOperator<V> binaryOperator) {
        V value;
        do {
            value = this.get();
        } while (!this.compareAndSet(value, (V)binaryOperator.apply(value, v)));
        return value;
    }
    
    public final V accumulateAndGet(final V v, final BinaryOperator<V> binaryOperator) {
        V value;
        Object apply;
        do {
            value = this.get();
            apply = binaryOperator.apply(value, v);
        } while (!this.compareAndSet(value, (V)apply));
        return (V)apply;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.get());
    }
    
    static {
        unsafe = Unsafe.getUnsafe();
        try {
            valueOffset = AtomicReference.unsafe.objectFieldOffset(AtomicReference.class.getDeclaredField("value"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
