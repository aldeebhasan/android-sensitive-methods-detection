package java.util.concurrent.atomic;

import sun.reflect.*;
import java.util.function.*;
import sun.misc.*;
import sun.reflect.misc.*;
import java.security.*;
import java.lang.reflect.*;
import java.util.*;

public abstract class AtomicReferenceFieldUpdater<T, V>
{
    @CallerSensitive
    public static <U, W> AtomicReferenceFieldUpdater<U, W> newUpdater(final Class<U> clazz, final Class<W> clazz2, final String s) {
        return new AtomicReferenceFieldUpdaterImpl<U, W>(clazz, clazz2, s, Reflection.getCallerClass());
    }
    
    public abstract boolean compareAndSet(final T p0, final V p1, final V p2);
    
    public abstract boolean weakCompareAndSet(final T p0, final V p1, final V p2);
    
    public abstract void set(final T p0, final V p1);
    
    public abstract void lazySet(final T p0, final V p1);
    
    public abstract V get(final T p0);
    
    public V getAndSet(final T t, final V v) {
        V value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, v));
        return value;
    }
    
    public final V getAndUpdate(final T t, final UnaryOperator<V> unaryOperator) {
        V value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, (V)unaryOperator.apply(value)));
        return value;
    }
    
    public final V updateAndGet(final T t, final UnaryOperator<V> unaryOperator) {
        V value;
        Object apply;
        do {
            value = this.get(t);
            apply = unaryOperator.apply(value);
        } while (!this.compareAndSet(t, value, (V)apply));
        return (V)apply;
    }
    
    public final V getAndAccumulate(final T t, final V v, final BinaryOperator<V> binaryOperator) {
        V value;
        do {
            value = this.get(t);
        } while (!this.compareAndSet(t, value, (V)binaryOperator.apply(value, v)));
        return value;
    }
    
    public final V accumulateAndGet(final T t, final V v, final BinaryOperator<V> binaryOperator) {
        V value;
        Object apply;
        do {
            value = this.get(t);
            apply = binaryOperator.apply(value, v);
        } while (!this.compareAndSet(t, value, (V)apply));
        return (V)apply;
    }
    
    private static final class AtomicReferenceFieldUpdaterImpl<T, V> extends AtomicReferenceFieldUpdater<T, V>
    {
        private static final Unsafe U;
        private final long offset;
        private final Class<?> cclass;
        private final Class<T> tclass;
        private final Class<V> vclass;
        
        AtomicReferenceFieldUpdaterImpl(final Class<T> tclass, final Class<V> vclass, final String s, final Class<?> clazz) {
            Field field;
            int modifiers;
            Class<?> type;
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
                type = field.getType();
            }
            catch (PrivilegedActionException ex) {
                throw new RuntimeException(ex.getException());
            }
            catch (Exception ex2) {
                throw new RuntimeException(ex2);
            }
            if (vclass != type) {
                throw new ClassCastException();
            }
            if (vclass.isPrimitive()) {
                throw new IllegalArgumentException("Must be reference type");
            }
            if (!Modifier.isVolatile(modifiers)) {
                throw new IllegalArgumentException("Must be volatile type");
            }
            this.cclass = ((Modifier.isProtected(modifiers) && tclass.isAssignableFrom(clazz) && !isSamePackage(tclass, clazz)) ? clazz : tclass);
            this.tclass = tclass;
            this.vclass = vclass;
            this.offset = AtomicReferenceFieldUpdaterImpl.U.objectFieldOffset(field);
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
        
        private final void valueCheck(final V v) {
            if (v != null && !this.vclass.isInstance(v)) {
                throwCCE();
            }
        }
        
        static void throwCCE() {
            throw new ClassCastException();
        }
        
        @Override
        public final boolean compareAndSet(final T t, final V v, final V v2) {
            this.accessCheck(t);
            this.valueCheck(v2);
            return AtomicReferenceFieldUpdaterImpl.U.compareAndSwapObject(t, this.offset, v, v2);
        }
        
        @Override
        public final boolean weakCompareAndSet(final T t, final V v, final V v2) {
            this.accessCheck(t);
            this.valueCheck(v2);
            return AtomicReferenceFieldUpdaterImpl.U.compareAndSwapObject(t, this.offset, v, v2);
        }
        
        @Override
        public final void set(final T t, final V v) {
            this.accessCheck(t);
            this.valueCheck(v);
            AtomicReferenceFieldUpdaterImpl.U.putObjectVolatile(t, this.offset, v);
        }
        
        @Override
        public final void lazySet(final T t, final V v) {
            this.accessCheck(t);
            this.valueCheck(v);
            AtomicReferenceFieldUpdaterImpl.U.putOrderedObject(t, this.offset, v);
        }
        
        @Override
        public final V get(final T t) {
            this.accessCheck(t);
            return (V)AtomicReferenceFieldUpdaterImpl.U.getObjectVolatile(t, this.offset);
        }
        
        @Override
        public final V getAndSet(final T t, final V v) {
            this.accessCheck(t);
            this.valueCheck(v);
            return (V)AtomicReferenceFieldUpdaterImpl.U.getAndSetObject(t, this.offset, v);
        }
        
        static {
            U = Unsafe.getUnsafe();
        }
    }
}
