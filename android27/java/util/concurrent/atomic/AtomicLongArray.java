package java.util.concurrent.atomic;

import java.io.*;
import sun.misc.*;
import java.util.function.*;

public class AtomicLongArray implements Serializable
{
    private static final long serialVersionUID = -2308431214976778248L;
    private static final Unsafe unsafe;
    private static final int base;
    private static final int shift;
    private final long[] array;
    
    private long checkedByteOffset(final int n) {
        if (n < 0 || n >= this.array.length) {
            throw new IndexOutOfBoundsException("index " + n);
        }
        return byteOffset(n);
    }
    
    private static long byteOffset(final int n) {
        return (n << AtomicLongArray.shift) + AtomicLongArray.base;
    }
    
    public AtomicLongArray(final int n) {
        this.array = new long[n];
    }
    
    public AtomicLongArray(final long[] array) {
        this.array = array.clone();
    }
    
    public final int length() {
        return this.array.length;
    }
    
    public final long get(final int n) {
        return this.getRaw(this.checkedByteOffset(n));
    }
    
    private long getRaw(final long n) {
        return AtomicLongArray.unsafe.getLongVolatile(this.array, n);
    }
    
    public final void set(final int n, final long n2) {
        AtomicLongArray.unsafe.putLongVolatile(this.array, this.checkedByteOffset(n), n2);
    }
    
    public final void lazySet(final int n, final long n2) {
        AtomicLongArray.unsafe.putOrderedLong(this.array, this.checkedByteOffset(n), n2);
    }
    
    public final long getAndSet(final int n, final long n2) {
        return AtomicLongArray.unsafe.getAndSetLong(this.array, this.checkedByteOffset(n), n2);
    }
    
    public final boolean compareAndSet(final int n, final long n2, final long n3) {
        return this.compareAndSetRaw(this.checkedByteOffset(n), n2, n3);
    }
    
    private boolean compareAndSetRaw(final long n, final long n2, final long n3) {
        return AtomicLongArray.unsafe.compareAndSwapLong(this.array, n, n2, n3);
    }
    
    public final boolean weakCompareAndSet(final int n, final long n2, final long n3) {
        return this.compareAndSet(n, n2, n3);
    }
    
    public final long getAndIncrement(final int n) {
        return this.getAndAdd(n, 1L);
    }
    
    public final long getAndDecrement(final int n) {
        return this.getAndAdd(n, -1L);
    }
    
    public final long getAndAdd(final int n, final long n2) {
        return AtomicLongArray.unsafe.getAndAddLong(this.array, this.checkedByteOffset(n), n2);
    }
    
    public final long incrementAndGet(final int n) {
        return this.getAndAdd(n, 1L) + 1L;
    }
    
    public final long decrementAndGet(final int n) {
        return this.getAndAdd(n, -1L) - 1L;
    }
    
    public long addAndGet(final int n, final long n2) {
        return this.getAndAdd(n, n2) + n2;
    }
    
    public final long getAndUpdate(final int n, final LongUnaryOperator longUnaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        long raw;
        do {
            raw = this.getRaw(checkedByteOffset);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, longUnaryOperator.applyAsLong(raw)));
        return raw;
    }
    
    public final long updateAndGet(final int n, final LongUnaryOperator longUnaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        long raw;
        long applyAsLong;
        do {
            raw = this.getRaw(checkedByteOffset);
            applyAsLong = longUnaryOperator.applyAsLong(raw);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, applyAsLong));
        return applyAsLong;
    }
    
    public final long getAndAccumulate(final int n, final long n2, final LongBinaryOperator longBinaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        long raw;
        do {
            raw = this.getRaw(checkedByteOffset);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, longBinaryOperator.applyAsLong(raw, n2)));
        return raw;
    }
    
    public final long accumulateAndGet(final int n, final long n2, final LongBinaryOperator longBinaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        long raw;
        long applyAsLong;
        do {
            raw = this.getRaw(checkedByteOffset);
            applyAsLong = longBinaryOperator.applyAsLong(raw, n2);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, applyAsLong));
        return applyAsLong;
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
        base = AtomicLongArray.unsafe.arrayBaseOffset(long[].class);
        final int arrayIndexScale = AtomicLongArray.unsafe.arrayIndexScale(long[].class);
        if ((arrayIndexScale & arrayIndexScale - 1) != 0x0) {
            throw new Error("data type scale not a power of two");
        }
        shift = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
    }
}
