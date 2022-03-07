package java.util.concurrent.atomic;

import sun.misc.*;
import java.util.*;
import java.util.function.*;
import java.lang.reflect.*;
import java.io.*;

public class AtomicReferenceArray<E> implements Serializable
{
    private static final long serialVersionUID = -6209656149925076980L;
    private static final Unsafe unsafe;
    private static final int base;
    private static final int shift;
    private static final long arrayFieldOffset;
    private final Object[] array;
    
    private long checkedByteOffset(final int n) {
        if (n < 0 || n >= this.array.length) {
            throw new IndexOutOfBoundsException("index " + n);
        }
        return byteOffset(n);
    }
    
    private static long byteOffset(final int n) {
        return (n << AtomicReferenceArray.shift) + AtomicReferenceArray.base;
    }
    
    public AtomicReferenceArray(final int n) {
        this.array = new Object[n];
    }
    
    public AtomicReferenceArray(final E[] array) {
        this.array = Arrays.copyOf(array, array.length, (Class<? extends Object[]>)Object[].class);
    }
    
    public final int length() {
        return this.array.length;
    }
    
    public final E get(final int n) {
        return this.getRaw(this.checkedByteOffset(n));
    }
    
    private E getRaw(final long n) {
        return (E)AtomicReferenceArray.unsafe.getObjectVolatile(this.array, n);
    }
    
    public final void set(final int n, final E e) {
        AtomicReferenceArray.unsafe.putObjectVolatile(this.array, this.checkedByteOffset(n), e);
    }
    
    public final void lazySet(final int n, final E e) {
        AtomicReferenceArray.unsafe.putOrderedObject(this.array, this.checkedByteOffset(n), e);
    }
    
    public final E getAndSet(final int n, final E e) {
        return (E)AtomicReferenceArray.unsafe.getAndSetObject(this.array, this.checkedByteOffset(n), e);
    }
    
    public final boolean compareAndSet(final int n, final E e, final E e2) {
        return this.compareAndSetRaw(this.checkedByteOffset(n), e, e2);
    }
    
    private boolean compareAndSetRaw(final long n, final E e, final E e2) {
        return AtomicReferenceArray.unsafe.compareAndSwapObject(this.array, n, e, e2);
    }
    
    public final boolean weakCompareAndSet(final int n, final E e, final E e2) {
        return this.compareAndSet(n, e, e2);
    }
    
    public final E getAndUpdate(final int n, final UnaryOperator<E> unaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        E raw;
        do {
            raw = this.getRaw(checkedByteOffset);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, (E)unaryOperator.apply(raw)));
        return raw;
    }
    
    public final E updateAndGet(final int n, final UnaryOperator<E> unaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        E raw;
        Object apply;
        do {
            raw = this.getRaw(checkedByteOffset);
            apply = unaryOperator.apply(raw);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, (E)apply));
        return (E)apply;
    }
    
    public final E getAndAccumulate(final int n, final E e, final BinaryOperator<E> binaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        E raw;
        do {
            raw = this.getRaw(checkedByteOffset);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, (E)binaryOperator.apply(raw, e)));
        return raw;
    }
    
    public final E accumulateAndGet(final int n, final E e, final BinaryOperator<E> binaryOperator) {
        final long checkedByteOffset = this.checkedByteOffset(n);
        E raw;
        Object apply;
        do {
            raw = this.getRaw(checkedByteOffset);
            apply = binaryOperator.apply(raw, e);
        } while (!this.compareAndSetRaw(checkedByteOffset, raw, (E)apply));
        return (E)apply;
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
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException, InvalidObjectException {
        Object o = objectInputStream.readFields().get("array", null);
        if (o == null || !((U[])o).getClass().isArray()) {
            throw new InvalidObjectException("Not array type");
        }
        if (((U[])o).getClass() != Object[].class) {
            o = Arrays.copyOf((Object[])o, Array.getLength(o), (Class<? extends Object[]>)Object[].class);
        }
        AtomicReferenceArray.unsafe.putObjectVolatile(this, AtomicReferenceArray.arrayFieldOffset, o);
    }
    
    static {
        try {
            unsafe = Unsafe.getUnsafe();
            arrayFieldOffset = AtomicReferenceArray.unsafe.objectFieldOffset(AtomicReferenceArray.class.getDeclaredField("array"));
            base = AtomicReferenceArray.unsafe.arrayBaseOffset(Object[].class);
            final int arrayIndexScale = AtomicReferenceArray.unsafe.arrayIndexScale(Object[].class);
            if ((arrayIndexScale & arrayIndexScale - 1) != 0x0) {
                throw new Error("data type scale not a power of two");
            }
            shift = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
