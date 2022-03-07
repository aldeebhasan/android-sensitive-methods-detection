package java.util.concurrent.locks;

import sun.misc.*;
import java.util.concurrent.*;

public class LockSupport
{
    private static final Unsafe UNSAFE;
    private static final long parkBlockerOffset;
    private static final long SEED;
    private static final long PROBE;
    private static final long SECONDARY;
    
    private static void setBlocker(final Thread thread, final Object o) {
        LockSupport.UNSAFE.putObject(thread, LockSupport.parkBlockerOffset, o);
    }
    
    public static void unpark(final Thread thread) {
        if (thread != null) {
            LockSupport.UNSAFE.unpark(thread);
        }
    }
    
    public static void park(final Object o) {
        final Thread currentThread = Thread.currentThread();
        setBlocker(currentThread, o);
        LockSupport.UNSAFE.park(false, 0L);
        setBlocker(currentThread, null);
    }
    
    public static void parkNanos(final Object o, final long n) {
        if (n > 0L) {
            final Thread currentThread = Thread.currentThread();
            setBlocker(currentThread, o);
            LockSupport.UNSAFE.park(false, n);
            setBlocker(currentThread, null);
        }
    }
    
    public static void parkUntil(final Object o, final long n) {
        final Thread currentThread = Thread.currentThread();
        setBlocker(currentThread, o);
        LockSupport.UNSAFE.park(true, n);
        setBlocker(currentThread, null);
    }
    
    public static Object getBlocker(final Thread thread) {
        if (thread == null) {
            throw new NullPointerException();
        }
        return LockSupport.UNSAFE.getObjectVolatile(thread, LockSupport.parkBlockerOffset);
    }
    
    public static void park() {
        LockSupport.UNSAFE.park(false, 0L);
    }
    
    public static void parkNanos(final long n) {
        if (n > 0L) {
            LockSupport.UNSAFE.park(false, n);
        }
    }
    
    public static void parkUntil(final long n) {
        LockSupport.UNSAFE.park(true, n);
    }
    
    static final int nextSecondarySeed() {
        final Thread currentThread = Thread.currentThread();
        final int int1;
        int nextInt;
        if ((int1 = LockSupport.UNSAFE.getInt(currentThread, LockSupport.SECONDARY)) != 0) {
            final int n = int1 ^ int1 << 13;
            final int n2 = n ^ n >>> 17;
            nextInt = (n2 ^ n2 << 5);
        }
        else if ((nextInt = ThreadLocalRandom.current().nextInt()) == 0) {
            nextInt = 1;
        }
        LockSupport.UNSAFE.putInt(currentThread, LockSupport.SECONDARY, nextInt);
        return nextInt;
    }
    
    static {
        try {
            UNSAFE = Unsafe.getUnsafe();
            final Class<Thread> clazz = Thread.class;
            parkBlockerOffset = LockSupport.UNSAFE.objectFieldOffset(clazz.getDeclaredField("parkBlocker"));
            SEED = LockSupport.UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomSeed"));
            PROBE = LockSupport.UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomProbe"));
            SECONDARY = LockSupport.UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocalRandomSecondarySeed"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
