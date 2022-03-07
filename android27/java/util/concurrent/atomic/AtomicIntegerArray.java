package java.util.concurrent.atomic;

import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class AtomicIntegerArray implements Serializable
{
    private static final long serialVersionUID = 2862133569453604235L;
    private static final Unsafe unsafe;
    private static final int base;
    private static final int shift;
    private final int[] array;
    
    private long checkedByteOffset(final int n) {
        if (n < 0 || n >= this.array.length) {
            throw new IndexOutOfBoundsException("index " + n);
        }
        return byteOffset(n);
    }
    
    private static long byteOffset(final int n) {
        return (n << AtomicIntegerArray.shift) + AtomicIntegerArray.base;
    }
    
    public AtomicIntegerArray(final int n) {
        this.array = new int[n];
    }
    
    public AtomicIntegerArray(final int[] array) {
        this.array = array.clone();
    }
    
    public final int length() {
        return this.array.length;
    }
    
    public final int get(final int n) {
        return this.getRaw(this.checkedByteOffset(n));
    }
    
    private int getRaw(final long n) {
        return AtomicIntegerArray.unsafe.getIntVolatile(this.array, n);
    }
    
    public final void set(final int n, final int n2) {
        AtomicIntegerArray.unsafe.putIntVolatile(this.array, this.checkedByteOffset(n), n2);
    }
    
    public final void lazySet(final int n, final int n2) {
        AtomicIntegerArray.unsafe.putOrderedInt(this.array, this.checkedByteOffset(n), n2);
    }
    
    public final int getAndSet(final int n, final int n2) {
        return AtomicIntegerArray.unsafe.getAndSetInt(this.array, this.checkedByteOffset(n), n2);
    }
    
    public final boolean compareAndSet(final int n, final int n2, final int n3) {
        return this.compareAndSetRaw(this.checkedByteOffset(n), n2, n3);
    }
    
    private boolean compareAndSetRaw(final long n, final int n2, final int n3) {
        return AtomicIntegerArray.unsafe.compareAndSwapInt(this.array, n, n2, n3);
    }
    
    public final boolean weakCompareAndSet(final int n, final int n2, final int n3) {
        return this.compareAndSet(n, n2, n3);
    }
    
    public final int getAndIncrement(final int n) {
        return this.getAndAdd(n, 1);
    }
    
    public final int getAndDecrement(final int n) {
        return this.getAndAdd(n, -1);
    }
    
    public final int getAndAdd(final int n, final int n2) {
        return AtomicIntegerArray.unsafe.getAndAddInt(this.array, this.checkedByteOffset(n), n2);
    }
    
    public final int incrementAndGet(final int n) {
        return this.getAndAdd(n, 1) + 1;
    }
    
    public final int decrementAndGet(final int n) {
        return this.getAndAdd(n, -1) - 1;
    }
    
    public final int addAndGet(final int n, final int n2) {
        return this.getAndAdd(n, n2) + n2;
    }
    
    public final int getAndUpdate(final int n, final IntUnaryOperator intUnaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        int raw;
        do {
            raw = this.getRaw(checkedByteOffset);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, intUnaryOperator.applyAsInt(raw)));
        return raw;
    }
    
    public final int updateAndGet(final int n, final IntUnaryOperator intUnaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        int raw;
        int applyAsInt;
        do {
            raw = this.getRaw(checkedByteOffset);
            applyAsInt = intUnaryOperator.applyAsInt(raw);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, applyAsInt));
        return applyAsInt;
    }
    
    public final int getAndAccumulate(final int n, final int n2, final IntBinaryOperator intBinaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        int raw;
        do {
            raw = this.getRaw(checkedByteOffset);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, intBinaryOperator.applyAsInt(raw, n2)));
        return raw;
    }
    
    public final int accumulateAndGet(final int n, final int n2, final IntBinaryOperator intBinaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        int raw;
        int applyAsInt;
        do {
            raw = this.getRaw(checkedByteOffset);
            applyAsInt = intBinaryOperator.applyAsInt(raw, n2);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, applyAsInt));
        return applyAsInt;
    }
    
    @Override
    public String toString() {
        final int n = this.array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(this.getRaw(byteOffset(n2)));
            if (n2 == n) {
                break;
            }
            sb.append(',').append(' ');
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    static {
        unsafe = Unsafe.getUnsafe();
        base = AtomicIntegerArray.unsafe.arrayBaseOffset(int[].class);
        final int arrayIndexScale = AtomicIntegerArray.unsafe.arrayIndexScale(int[].class);
        if ((arrayIndexScale & arrayIndexScale - 1) != 0x0) {
            throw new Error("data type scale not a power of two");
        }
        shift = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
    }
}
