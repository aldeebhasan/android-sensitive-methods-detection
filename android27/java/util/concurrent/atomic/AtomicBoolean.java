package java.util.concurrent.atomic;

import java.io.*;
import sun.misc.*;

public class AtomicBoolean implements Serializable
{
    private static final long serialVersionUID = 4654671469794556979L;
    private static final Unsafe unsafe;
    private static final long valueOffset;
    private volatile int value;
    
    public AtomicBoolean(final boolean value) {
        this.value = (value ? 1 : 0);
    }
    
    public AtomicBoolean() {
    }
    
    public final boolean get() {
        return this.value != 0;
    }
    
    public final boolean compareAndSet(final boolean b, final boolean b2) {
        return AtomicBoolean.unsafe.compareAndSwapInt(this, AtomicBoolean.valueOffset, b ? 1 : 0, b2 ? 1 : 0);
    }
    
    public boolean weakCompareAndSet(final boolean b, final boolean b2) {
        return AtomicBoolean.unsafe.compareAndSwapInt(this, AtomicBoolean.valueOffset, b ? 1 : 0, b2 ? 1 : 0);
    }
    
    public final void set(final boolean value) {
        this.value = (value ? 1 : 0);
    }
    
    public final void lazySet(final boolean b) {
        AtomicBoolean.unsafe.putOrderedInt(this, AtomicBoolean.valueOffset, b ? 1 : 0);
    }
    
    public final boolean getAndSet(final boolean b) {
        boolean value;
        do {
            value = this.get();
        } while (!this.compareAndSet(value, b));
        return value;
    }
    
    @Override
    public String toString() {
        return Boolean.toString(this.get());
    }
    
    static {
        unsafe = Unsafe.getUnsafe();
        try {
            valueOffset = AtomicBoolean.unsafe.objectFieldOffset(AtomicBoolean.class.getDeclaredField("value"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
