package java.util.concurrent.atomic;

import sun.reflect.*;
import java.util.function.*;
import sun.misc.*;
import sun.reflect.misc.*;
import java.security.*;
import java.lang.reflect.*;
import java.util.*;

public abstract class AtomicIntegerFieldUpdater<T>
{
    @CallerSensitive
    public static <U> AtomicIntegerFieldUpdater<U> newUpdater(final Class<U> clazz, final String s) {
        return new AtomicIntegerFieldUpdaterImpl<U>(clazz, s, Reflection.getCallerClass());
    }
    
    public abstract boolean compareAndSet(final T p0, final int p1, final int p2);
    
    public abstract boolean weakCompareAndSet(final T p0, final int p1, final int p2);
    
    public abstract void set(final T p0, final int p1);
    
    public abstract void lazySet(final T p0, final int p1);
    
    public abstract int get(final T p0);
    
    public int getAndSet(final T t, final int n) {
        int value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, n));
        return value;
    }
    
    public int getAndIncrement(final T t) {
        int value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, value + 1));
        return value;
    }
    
    public int getAndDecrement(final T t) {
        int value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, value - 1));
        return value;
    }
    
    public int getAndAdd(final T t, final int n) {
        int value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, value + n));
        return value;
    }
    
    public int incrementAndGet(final T t) {
        int value;
        int n;
        do {
            value = this.get(t);
            n = value + 1;
        } while (!this.compareAndSet(t, value, n));
        return n;
    }
    
    public int decrementAndGet(final T t) {
        int value;
        int n;
        do {
            value = this.get(t);
            n = value - 1;
        } while (!this.compareAndSet(t, value, n));
        return n;
    }
    
    public int addAndGet(final T t, final int n) {
        int value;
        int n2;
        do {
            value = this.get(t);
            n2 = value + n;
        } while (!this.compareAndSet(t, value, n2));
        return n2;
    }
    
    public final int getAndUpdate(final T t, final IntUnaryOperator intUnaryOperator) {
        int value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, intUnaryOperator.applyAsInt(value)));
        return value;
    }
    
    public final int updateAndGet(final T t, final IntUnaryOperator intUnaryOperator) {
        int value;
        int applyAsInt;
        do {
            value = this.get(t);
            applyAsInt = intUnaryOperator.applyAsInt(value);
        } while (!this.compareAndSet(t, value, applyAsInt));
        return applyAsInt;
    }
    
    public final int getAndAccumulate(final T t, final int n, final IntBinaryOperator intBinaryOperator) {
        int value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, intBinaryOperator.applyAsInt(value, n)));
        return value;
    }
    
    public final int accumulateAndGet(final T t, final int n, final IntBinaryOperator intBinaryOperator) {
        int value;
        int applyAsInt;
        do {
            value = this.get(t);
            applyAsInt = intBinaryOperator.applyAsInt(value, n);
        } while (!this.compareAndSet(t, value, applyAsInt));
        return applyAsInt;
    }
    
    private static final class AtomicIntegerFieldUpdaterImpl<T> extends AtomicIntegerFieldUpdater<T>
    {
        private static final Unsafe U;
        private final long offset;
        private final Class<?> cclass;
        private final Class<T> tclass;
        
        AtomicIntegerFieldUpdaterImpl(final Class<T> tclass, final String s, final Class<?> clazz) {
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
                if (classLoader2 != null && classLoader2 != classLoader && (classLoader == null || !isAncestor(classLoader, classLoader2))) {
                    ReflectUtil.checkPackageAccess(tclass);
                }
            }
            catch (PrivilegedActionException ex) {
                throw new RuntimeException(ex.getException());
            }
            catch (Exception ex2) {
                throw new RuntimeException(ex2);
            }
            if (field.getType() != Integer.TYPE) {
                throw new IllegalArgumentException("Must be integer type");
            }
            if (!Modifier.isVolatile(modifiers)) {
                throw new IllegalArgumentException("Must be volatile type");
            }
            this.cclass = ((Modifier.isProtected(modifiers) && tclass.isAssignableFrom(clazz) && !isSamePackage(tclass, clazz)) ? clazz : tclass);
            this.tclass = tclass;
            this.offset = AtomicIntegerFieldUpdaterImpl.U.objectFieldOffset(field);
        }
        
        private static boolean isAncestor(final ClassLoader classLoader, final ClassLoader classLoader2) {
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
        public final boolean compareAndSet(final T t, final int n, final int n2) {
            this.accessCheck(t);
            return AtomicIntegerFieldUpdaterImpl.U.compareAndSwapInt(t, this.offset, n, n2);
        }
        
        @Override
        public final boolean weakCompareAndSet(final T t, final int n, final int n2) {
            this.accessCheck(t);
            return AtomicIntegerFieldUpdaterImpl.U.compareAndSwapInt(t, this.offset, n, n2);
        }
        
        @Override
        public final void set(final T t, final int n) {
            this.accessCheck(t);
            AtomicIntegerFieldUpdaterImpl.U.putIntVolatile(t, this.offset, n);
        }
        
        @Override
        public final void lazySet(final T t, final int n) {
            this.accessCheck(t);
            AtomicIntegerFieldUpdaterImpl.U.putOrderedInt(t, this.offset, n);
        }
        
        @Override
        public final int get(final T t) {
            this.accessCheck(t);
            return AtomicIntegerFieldUpdaterImpl.U.getIntVolatile(t, this.offset);
        }
        
        @Override
        public final int getAndSet(final T t, final int n) {
            this.accessCheck(t);
            return AtomicIntegerFieldUpdaterImpl.U.getAndSetInt(t, this.offset, n);
        }
        
        @Override
        public final int getAndAdd(final T t, final int n) {
            this.accessCheck(t);
            return AtomicIntegerFieldUpdaterImpl.U.getAndAddInt(t, this.offset, n);
        }
        
        @Override
        public final int getAndIncrement(final T t) {
            return this.getAndAdd(t, 1);
        }
        
        @Override
        public final int getAndDecrement(final T t) {
            return this.getAndAdd(t, -1);
        }
        
        @Override
        public final int incrementAndGet(final T t) {
            return this.getAndAdd(t, 1) + 1;
        }
        
        @Override
        public final int decrementAndGet(final T t) {
            return this.getAndAdd(t, -1) - 1;
        }
        
        @Override
        public final int addAndGet(final T t, final int n) {
            return this.getAndAdd(t, n) + n;
        }
        
        static {
            U = Unsafe.getUnsafe();
        }
    }
}
