package java.util.concurrent.atomic;

import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class AtomicInteger extends Number implements Serializable
{
    private static final long serialVersionUID = 6214790243416807050L;
    private static final Unsafe unsafe;
    private static final long valueOffset;
    private volatile int value;
    
    public AtomicInteger(final int value) {
        this.value = value;
    }
    
    public AtomicInteger() {
    }
    
    public final int get() {
        return this.value;
    }
    
    public final void set(final int value) {
        this.value = value;
    }
    
    public final void lazySet(final int n) {
        AtomicInteger.unsafe.putOrderedInt(this, AtomicInteger.valueOffset, n);
    }
    
    public final int getAndSet(final int n) {
        return AtomicInteger.unsafe.getAndSetInt(this, AtomicInteger.valueOffset, n);
    }
    
    public final boolean compareAndSet(final int n, final int n2) {
        return AtomicInteger.unsafe.compareAndSwapInt(this, AtomicInteger.valueOffset, n, n2);
    }
    
    public final boolean weakCompareAndSet(final int n, final int n2) {
        return AtomicInteger.unsafe.compareAndSwapInt(this, AtomicInteger.valueOffset, n, n2);
    }
    
    public final int getAndIncrement() {
        return AtomicInteger.unsafe.getAndAddInt(this, AtomicInteger.valueOffset, 1);
    }
    
    public final int getAndDecrement() {
        return AtomicInteger.unsafe.getAndAddInt(this, AtomicInteger.valueOffset, -1);
    }
    
    public final int getAndAdd(final int n) {
        return AtomicInteger.unsafe.getAndAddInt(this, AtomicInteger.valueOffset, n);
    }
    
    public final int incrementAndGet() {
        return AtomicInteger.unsafe.getAndAddInt(this, AtomicInteger.valueOffset, 1) + 1;
    }
    
    public final int decrementAndGet() {
        return AtomicInteger.unsafe.getAndAddInt(this, AtomicInteger.valueOffset, -1) - 1;
    }
    
    public final int addAndGet(final int n) {
        return AtomicInteger.unsafe.getAndAddInt(this, AtomicInteger.valueOffset, n) + n;
    }
    
    public final int getAndUpdate(final IntUnaryOperator intUnaryOperator) {
        int value;
        do {
            value = this.get();
        } while (!this.compareAndSet(value, intUnaryOperator.applyAsInt(value)));
        return value;
    }
    
    public final int updateAndGet(final IntUnaryOperator intUnaryOperator) {
        int value;
        int applyAsInt;
        do {
            value = this.get();
            applyAsInt = intUnaryOperator.applyAsInt(value);
        } while (!this.compareAndSet(value, applyAsInt));
        return applyAsInt;
    }
    
    public final int getAndAccumulate(final int n, final IntBinaryOperator intBinaryOperator) {
        int value;
        do {
            value = this.get();
        } while (!this.compareAndSet(value, intBinaryOperator.applyAsInt(value, n)));
        return value;
    }
    
    public final int accumulateAndGet(final int n, final IntBinaryOperator intBinaryOperator) {
        int value;
        int applyAsInt;
        do {
            value = this.get();
            applyAsInt = intBinaryOperator.applyAsInt(value, n);
        } while (!this.compareAndSet(value, applyAsInt));
        return applyAsInt;
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.get());
    }
    
    @Override
    public int intValue() {
        return this.get();
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
        try {
            valueOffset = AtomicInteger.unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
