package java.util.concurrent.atomic;

import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class AtomicLong extends Number implements Serializable
{
    private static final long serialVersionUID = 1927816293512124184L;
    private static final Unsafe unsafe;
    private static final long valueOffset;
    static final boolean VM_SUPPORTS_LONG_CAS;
    private volatile long value;
    
    private static native boolean VMSupportsCS8();
    
    public AtomicLong(final long value) {
        this.value = value;
    }
    
    public AtomicLong() {
    }
    
    public final long get() {
        return this.value;
    }
    
    public final void set(final long value) {
        this.value = value;
    }
    
    public final void lazySet(final long n) {
        AtomicLong.unsafe.putOrderedLong(this, AtomicLong.valueOffset, n);
    }
    
    public final long getAndSet(final long n) {
        return AtomicLong.unsafe.getAndSetLong(this, AtomicLong.valueOffset, n);
    }
    
    public final boolean compareAndSet(final long n, final long n2) {
        return AtomicLong.unsafe.compareAndSwapLong(this, AtomicLong.valueOffset, n, n2);
    }
    
    public final boolean weakCompareAndSet(final long n, final long n2) {
        return AtomicLong.unsafe.compareAndSwapLong(this, AtomicLong.valueOffset, n, n2);
    }
    
    public final long getAndIncrement() {
        return AtomicLong.unsafe.getAndAddLong(this, AtomicLong.valueOffset, 1L);
    }
    
    public final long getAndDecrement() {
        return AtomicLong.unsafe.getAndAddLong(this, AtomicLong.valueOffset, -1L);
    }
    
    public final long getAndAdd(final long n) {
        return AtomicLong.unsafe.getAndAddLong(this, AtomicLong.valueOffset, n);
    }
    
    public final long incrementAndGet() {
        return AtomicLong.unsafe.getAndAddLong(this, AtomicLong.valueOffset, 1L) + 1L;
    }
    
    public final long decrementAndGet() {
        return AtomicLong.unsafe.getAndAddLong(this, AtomicLong.valueOffset, -1L) - 1L;
    }
    
    public final long addAndGet(final long n) {
        return AtomicLong.unsafe.getAndAddLong(this, AtomicLong.valueOffset, n) + n;
    }
    
    public final long getAndUpdate(final LongUnaryOperator longUnaryOperator) {
        long value;
        do {
            value = this.get();
        } while (!this.compareAndSet(value, longUnaryOperator.applyAsLong(value)));
        return value;
    }
    
    public final long updateAndGet(final LongUnaryOperator longUnaryOperator) {
        long value;
        long applyAsLong;
        do {
            value = this.get();
            applyAsLong = longUnaryOperator.applyAsLong(value);
        } while (!this.compareAndSet(value, applyAsLong));
        return applyAsLong;
    }
    
    public final long getAndAccumulate(final long n, final LongBinaryOperator longBinaryOperator) {
        long value;
        do {
            value = this.get();
        } while (!this.compareAndSet(value, longBinaryOperator.applyAsLong(value, n)));
        return value;
    }
    
    public final long accumulateAndGet(final long n, final LongBinaryOperator longBinaryOperator) {
        long value;
        long applyAsLong;
        do {
            value = this.get();
            applyAsLong = longBinaryOperator.applyAsLong(value, n);
        } while (!this.compareAndSet(value, applyAsLong));
        return applyAsLong;
    }
    
    @Override
    public String toString() {
        return Long.toString(this.get());
    }
    
    @Override
    public int intValue() {
        return (int)this.get();
    }
    
    @Override
    public long longValue() {
        return this.get();
    }
    
    @Override
    public float floatValue() {
        return this.get();
    }
    
    @Override
    public double doubleValue() {
        return this.get();
    }
    
    static {
        unsafe = Unsafe.getUnsafe();
        VM_SUPPORTS_LONG_CAS = VMSupportsCS8();
        try {
            valueOffset = AtomicLong.unsafe.objectFieldOffset(AtomicLong.class.getDeclaredField("value"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
