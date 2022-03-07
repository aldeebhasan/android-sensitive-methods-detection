package java.util.concurrent.atomic;

import sun.reflect.*;
import java.util.function.*;
import java.util.*;
import sun.misc.*;
import sun.reflect.misc.*;
import java.security.*;
import java.lang.reflect.*;

public abstract class AtomicLongFieldUpdater<T>
{
    @CallerSensitive
    public static <U> AtomicLongFieldUpdater<U> newUpdater(final Class<U> clazz, final String s) {
        final Class<?> callerClass = Reflection.getCallerClass();
        if (AtomicLong.VM_SUPPORTS_LONG_CAS) {
            return new CASUpdater<U>((Class<Object>)clazz, s, callerClass);
        }
        return new LockedUpdater<U>((Class<Object>)clazz, s, callerClass);
    }
    
    public abstract boolean compareAndSet(final T p0, final long p1, final long p2);
    
    public abstract boolean weakCompareAndSet(final T p0, final long p1, final long p2);
    
    public abstract void set(final T p0, final long p1);
    
    public abstract void lazySet(final T p0, final long p1);
    
    public abstract long get(final T p0);
    
    public long getAndSet(final T t, final long n) {
        long value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, n));
        return value;
    }
    
    public long getAndIncrement(final T t) {
        long value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, value + 1L));
        return value;
    }
    
    public long getAndDecrement(final T t) {
        long value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, value - 1L));
        return value;
    }
    
    public long getAndAdd(final T t, final long n) {
        long value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, value + n));
        return value;
    }
    
    public long incrementAndGet(final T t) {
        long value;
        long n;
        do {
            value = this.get(t);
            n = value + 1L;
        } while (!this.compareAndSet(t, value, n));
        return n;
    }
    
    public long decrementAndGet(final T t) {
        long value;
        long n;
        do {
            value = this.get(t);
            n = value - 1L;
        } while (!this.compareAndSet(t, value, n));
        return n;
    }
    
    public long addAndGet(final T t, final long n) {
        long value;
        long n2;
        do {
            value = this.get(t);
            n2 = value + n;
        } while (!this.compareAndSet(t, value, n2));
        return n2;
    }
    
    public final long getAndUpdate(final T t, final LongUnaryOperator longUnaryOperator) {
        long value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, longUnaryOperator.applyAsLong(value)));
        return value;
    }
    
    public final long updateAndGet(final T t, final LongUnaryOperator longUnaryOperator) {
        long value;
        long applyAsLong;
        do {
            value = this.get(t);
            applyAsLong = longUnaryOperator.applyAsLong(value);
        } while (!this.compareAndSet(t, value, applyAsLong));
        return applyAsLong;
    }
    
    public final long getAndAccumulate(final T t, final long n, final LongBinaryOperator longBinaryOperator) {
        long value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, longBinaryOperator.applyAsLong(value, n)));
        return value;
    }
    
    public final long accumulateAndGet(final T t, final long n, final LongBinaryOperator longBinaryOperator) {
        long value;
        long applyAsLong;
        do {
            value = this.get(t);
            applyAsLong = longBinaryOperator.applyAsLong(value, n);
        } while (!this.compareAndSet(t, value, applyAsLong));
        return applyAsLong;
    }
    
    static boolean isAncestor(final ClassLoader classLoader, final ClassLoader classLoader2) {
        ClassLoader parent = classLoader;
        do {
            parent = parent.getParent();
            if (classLoader2 == parent) {
                return true;
            }
        } while (parent != null);
        return false;
    }
    
    private static boolean isSamePackage(final Class<?> clazz, final Class<?> clazz2) {
        return clazz.getClassLoader() == clazz2.getClassLoader() && Objects.equals(getPackageName(clazz), getPackageName(clazz2));
    }
    
    private static String getPackageName(final Class<?> clazz) {
        final String name = clazz.getName();
        final int lastIndex = name.lastIndexOf(46);
        return (lastIndex != -1) ? name.substring(0, lastIndex) : "";
    }
    
    private static final class CASUpdater<T> extends AtomicLongFieldUpdater<T>
    {
        private static final Unsafe U;
        private final long offset;
        private final Class<?> cclass;
        private final Class<T> tclass;
        
        CASUpdater(final Class<T> tclass, final String s, final Class<?> clazz) {
            Field field;
            int modifiers;
            try {
                field = AccessController.doPrivileged((PrivilegedExceptionAction<Field>)new PrivilegedExceptionAction<Field>() {
                    @Override
                    public Field run() throws NoSuchFieldException {
                        return tclass.getDeclaredField(s);
                    }
                });
                modifiers = field.getModifiers();
                ReflectUtil.ensureMemberAccess(clazz, tclass, null, modifiers);
                final ClassLoader classLoader = tclass.getClassLoader();
                final ClassLoader classLoader2 = clazz.getClassLoader();
                if (classLoader2 != null && classLoader2 != classLoader && (classLoader == null || !AtomicLongFieldUpdater.isAncestor(classLoader, classLoader2))) {
                    ReflectUtil.checkPackageAccess(tclass);
                }
            }
            catch (PrivilegedActionException ex) {
                throw new RuntimeException(ex.getException());
            }
            catch (Exception ex2) {
                throw new RuntimeException(ex2);
            }
            if (field.getType() != Long.TYPE) {
                throw new IllegalArgumentException("Must be long type");
            }
            if (!Modifier.isVolatile(modifiers)) {
                throw new IllegalArgumentException("Must be volatile type");
            }
            this.cclass = ((Modifier.isProtected(modifiers) && tclass.isAssignableFrom(clazz) && !isSamePackage(tclass, clazz)) ? clazz : tclass);
            this.tclass = tclass;
            this.offset = CASUpdater.U.objectFieldOffset(field);
        }
        
        private final void accessCheck(final T t) {
            if (!this.cclass.isInstance(t)) {
                this.throwAccessCheckException(t);
            }
        }
        
        private final void throwAccessCheckException(final T t) {
            if (this.cclass == this.tclass) {
                throw new ClassCastException();
            }
            throw new RuntimeException(new IllegalAccessException("Class " + this.cclass.getName() + " can not access a protected member of class " + this.tclass.getName() + " using an instance of " + t.getClass().getName()));
        }
        
        @Override
        public final boolean compareAndSet(final T t, final long n, final long n2) {
            this.accessCheck(t);
            return CASUpdater.U.compareAndSwapLong(t, this.offset, n, n2);
        }
        
        @Override
        public final boolean weakCompareAndSet(final T t, final long n, final long n2) {
            this.accessCheck(t);
            return CASUpdater.U.compareAndSwapLong(t, this.offset, n, n2);
        }
        
        @Override
        public final void set(final T t, final long n) {
            this.accessCheck(t);
            CASUpdater.U.putLongVolatile(t, this.offset, n);
        }
        
        @Override
        public final void lazySet(final T t, final long n) {
            this.accessCheck(t);
            CASUpdater.U.putOrderedLong(t, this.offset, n);
        }
        
        @Override
        public final long get(final T t) {
            this.accessCheck(t);
            return CASUpdater.U.getLongVolatile(t, this.offset);
        }
        
        @Override
        public final long getAndSet(final T t, final long n) {
            this.accessCheck(t);
            return CASUpdater.U.getAndSetLong(t, this.offset, n);
        }
        
        @Override
        public final long getAndAdd(final T t, final long n) {
            this.accessCheck(t);
            return CASUpdater.U.getAndAddLong(t, this.offset, n);
        }
        
        @Override
        public final long getAndIncrement(final T t) {
            return this.getAndAdd(t, 1L);
        }
        
        @Override
        public final long getAndDecrement(final T t) {
            return this.getAndAdd(t, -1L);
        }
        
        @Override
        public final long incrementAndGet(final T t) {
            return this.getAndAdd(t, 1L) + 1L;
        }
        
        @Override
        public final long decrementAndGet(final T t) {
            return this.getAndAdd(t, -1L) - 1L;
        }
        
        @Override
        public final long addAndGet(final T t, final long n) {
            return this.getAndAdd(t, n) + n;
        }
        
        static {
            U = Unsafe.getUnsafe();
        }
    }
    
    private static final class LockedUpdater<T> extends AtomicLongFieldUpdater<T>
    {
        private static final Unsafe U;
        private final long offset;
        private final Class<?> cclass;
        private final Class<T> tclass;
        
        LockedUpdater(final Class<T> tclass, final String s, final Class<?> clazz) {
            Field field;
            int modifiers;
            try {
                field = AccessController.doPrivileged((PrivilegedExceptionAction<Field>)new PrivilegedExceptionAction<Field>() {
                    @Override
                    public Field run() throws NoSuchFieldException {
                        return tclass.getDeclaredField(s);
                    }
                });
                modifiers = field.getModifiers();
                ReflectUtil.ensureMemberAccess(clazz, tclass, null, modifiers);
                final ClassLoader classLoader = tclass.getClassLoader();
                final ClassLoader classLoader2 = clazz.getClassLoader();
                if (classLoader2 != null && classLoader2 != classLoader && (classLoader == null || !AtomicLongFieldUpdater.isAncestor(classLoader, classLoader2))) {
                    ReflectUtil.checkPackageAccess(tclass);
                }
            }
            catch (PrivilegedActionException ex) {
                throw new RuntimeException(ex.getException());
            }
            catch (Exception ex2) {
                throw new RuntimeException(ex2);
            }
            if (field.getType() != Long.TYPE) {
                throw new IllegalArgumentException("Must be long type");
            }
            if (!Modifier.isVolatile(modifiers)) {
                throw new IllegalArgumentException("Must be volatile type");
            }
            this.cclass = ((Modifier.isProtected(modifiers) && tclass.isAssignableFrom(clazz) && !isSamePackage(tclass, clazz)) ? clazz : tclass);
            this.tclass = tclass;
            this.offset = LockedUpdater.U.objectFieldOffset(field);
        }
        
        private final void accessCheck(final T t) {
            if (!this.cclass.isInstance(t)) {
                throw this.accessCheckException(t);
            }
        }
        
        private final RuntimeException accessCheckException(final T t) {
            if (this.cclass == this.tclass) {
                return new ClassCastException();
            }
            return new RuntimeException(new IllegalAccessException("Class " + this.cclass.getName() + " can not access a protected member of class " + this.tclass.getName() + " using an instance of " + t.getClass().getName()));
        }
        
        @Override
        public final boolean compareAndSet(final T t, final long n, final long n2) {
            this.accessCheck(t);
            synchronized (this) {
                if (LockedUpdater.U.getLong(t, this.offset) != n) {
                    return false;
                }
                LockedUpdater.U.putLong(t, this.offset, n2);
                return true;
            }
        }
        
        @Override
        public final boolean weakCompareAndSet(final T t, final long n, final long n2) {
            return this.compareAndSet(t, n, n2);
        }
        
        @Override
        public final void set(final T t, final long n) {
            this.accessCheck(t);
            synchronized (this) {
                LockedUpdater.U.putLong(t, this.offset, n);
            }
        }
        
        @Override
        public final void lazySet(final T t, final long n) {
            this.set(t, n);
        }
        
        @Override
        public final long get(final T t) {
            this.accessCheck(t);
            synchronized (this) {
                return LockedUpdater.U.getLong(t, this.offset);
            }
        }
        
        static {
            U = Unsafe.getUnsafe();
        }
    }
}
