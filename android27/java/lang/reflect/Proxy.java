package java.lang.reflect;

import java.io.*;
import sun.reflect.*;
import sun.security.util.*;
import sun.reflect.misc.*;
import java.security.*;
import java.util.function.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import sun.misc.*;
import java.lang.ref.*;

public class Proxy implements Serializable
{
    private static final long serialVersionUID = -2222568056686623797L;
    private static final Class<?>[] constructorParams;
    private static final WeakCache<ClassLoader, Class<?>[], Class<?>> proxyClassCache;
    protected InvocationHandler h;
    private static final Object key0;
    
    private Proxy() {
    }
    
    protected Proxy(final InvocationHandler h) {
        Objects.requireNonNull(h);
        this.h = h;
    }
    
    @CallerSensitive
    public static Class<?> getProxyClass(final ClassLoader classLoader, final Class<?>... array) throws IllegalArgumentException {
        final Class[] array2 = array.clone();
        if (System.getSecurityManager() != null) {
            checkProxyAccess(Reflection.getCallerClass(), classLoader, (Class<?>[])array2);
        }
        return getProxyClass0(classLoader, (Class<?>[])array2);
    }
    
    private static void checkProxyAccess(final Class<?> clazz, final ClassLoader classLoader, final Class<?>... array) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            final ClassLoader classLoader2 = clazz.getClassLoader();
            if (VM.isSystemDomainLoader(classLoader) && !VM.isSystemDomainLoader(classLoader2)) {
                securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
            }
            ReflectUtil.checkProxyPackageAccess(classLoader2, array);
        }
    }
    
    private static Class<?> getProxyClass0(final ClassLoader classLoader, final Class<?>... array) {
        if (array.length > 65535) {
            throw new IllegalArgumentException("interface limit exceeded");
        }
        return Proxy.proxyClassCache.get(classLoader, array);
    }
    
    @CallerSensitive
    public static Object newProxyInstance(final ClassLoader classLoader, final Class<?>[] array, final InvocationHandler invocationHandler) throws IllegalArgumentException {
        Objects.requireNonNull(invocationHandler);
        final Class[] array2 = array.clone();
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            checkProxyAccess(Reflection.getCallerClass(), classLoader, (Class<?>[])array2);
        }
        final Class<?> proxyClass0 = getProxyClass0(classLoader, (Class<?>[])array2);
        try {
            if (securityManager != null) {
                checkNewProxyPermission(Reflection.getCallerClass(), proxyClass0);
            }
            final Constructor<?> constructor = proxyClass0.getConstructor(Proxy.constructorParams);
            if (!Modifier.isPublic(proxyClass0.getModifiers())) {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        constructor.setAccessible(true);
                        return null;
                    }
                });
            }
            return constructor.newInstance(invocationHandler);
        }
        catch (IllegalAccessException | InstantiationException ex5) {
            final InstantiationException ex2;
            final InstantiationException ex = ex2;
            throw new InternalError(ex.toString(), ex);
        }
        catch (InvocationTargetException ex3) {
            final Throwable cause = ex3.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw new InternalError(cause.toString(), cause);
        }
        catch (NoSuchMethodException ex4) {
            throw new InternalError(ex4.toString(), ex4);
        }
    }
    
    private static void checkNewProxyPermission(final Class<?> clazz, final Class<?> clazz2) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null && ReflectUtil.isNonPublicProxyClass(clazz2)) {
            final ClassLoader classLoader = clazz.getClassLoader();
            final ClassLoader classLoader2 = clazz2.getClassLoader();
            final int lastIndex = clazz2.getName().lastIndexOf(46);
            final String s = (lastIndex == -1) ? "" : clazz2.getName().substring(0, lastIndex);
            final int lastIndex2 = clazz.getName().lastIndexOf(46);
            final String s2 = (lastIndex2 == -1) ? "" : clazz.getName().substring(0, lastIndex2);
            if (classLoader2 != classLoader || !s.equals(s2)) {
                securityManager.checkPermission(new ReflectPermission("newProxyInPackage." + s));
            }
        }
    }
    
    public static boolean isProxyClass(final Class<?> clazz) {
        return Proxy.class.isAssignableFrom(clazz) && Proxy.proxyClassCache.containsValue(clazz);
    }
    
    @CallerSensitive
    public static InvocationHandler getInvocationHandler(final Object o) throws IllegalArgumentException {
        if (!isProxyClass(o.getClass())) {
            throw new IllegalArgumentException("not a proxy instance");
        }
        final InvocationHandler h = ((Proxy)o).h;
        if (System.getSecurityManager() != null) {
            final Class<? extends InvocationHandler> class1 = h.getClass();
            if (ReflectUtil.needsPackageAccessCheck(Reflection.getCallerClass().getClassLoader(), class1.getClassLoader())) {
                ReflectUtil.checkPackageAccess(class1);
            }
        }
        return h;
    }
    
    private static native Class<?> defineClass0(final ClassLoader p0, final String p1, final byte[] p2, final int p3, final int p4);
    
    static {
        constructorParams = new Class[] { InvocationHandler.class };
        proxyClassCache = new WeakCache<ClassLoader, Class<?>[], Class<?>>(new KeyFactory(), new ProxyClassFactory());
        key0 = new Object();
    }
    
    private static final class Key1 extends WeakReference<Class<?>>
    {
        private final int hash;
        
        Key1(final Class<?> clazz) {
            super(clazz);
            this.hash = clazz.hashCode();
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            final Class clazz;
            return this == o || (o != null && o.getClass() == Key1.class && (clazz = ((Reference<Class>)this).get()) != null && clazz == ((Reference<Class>)o).get());
        }
    }
    
    private static final class Key2 extends WeakReference<Class<?>>
    {
        private final int hash;
        private final WeakReference<Class<?>> ref2;
        
        Key2(final Class<?> clazz, final Class<?> clazz2) {
            super(clazz);
            this.hash = 31 * clazz.hashCode() + clazz2.hashCode();
            this.ref2 = new WeakReference<Class<?>>(clazz2);
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            final Class clazz;
            final Class clazz2;
            return this == o || (o != null && o.getClass() == Key2.class && (clazz = ((Reference<Class>)this).get()) != null && clazz == ((Reference<Class>)o).get() && (clazz2 = this.ref2.get()) != null && clazz2 == ((Key2)o).ref2.get());
        }
    }
    
    private static final class KeyFactory implements BiFunction<ClassLoader, Class<?>[], Object>
    {
        @Override
        public Object apply(final ClassLoader classLoader, final Class<?>[] array) {
            switch (array.length) {
                case 1: {
                    return new Key1(array[0]);
                }
                case 2: {
                    return new Key2(array[0], array[1]);
                }
                case 0: {
                    return Proxy.key0;
                }
                default: {
                    return new KeyX(array);
                }
            }
        }
    }
    
    private static final class KeyX
    {
        private final int hash;
        private final WeakReference<Class<?>>[] refs;
        
        KeyX(final Class<?>[] array) {
            this.hash = Arrays.hashCode(array);
            this.refs = (WeakReference<Class<?>>[])new WeakReference[array.length];
            for (int i = 0; i < array.length; ++i) {
                this.refs[i] = new WeakReference<Class<?>>(array[i]);
            }
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o != null && o.getClass() == KeyX.class && equals(this.refs, ((KeyX)o).refs));
        }
        
        private static boolean equals(final WeakReference<Class<?>>[] array, final WeakReference<Class<?>>[] array2) {
            if (array.length != array2.length) {
                return false;
            }
            for (int i = 0; i < array.length; ++i) {
                final Class clazz = array[i].get();
                if (clazz == null || clazz != array2[i].get()) {
                    return false;
                }
            }
            return true;
        }
    }
    
    private static final class ProxyClassFactory implements BiFunction<ClassLoader, Class<?>[], Class<?>>
    {
        private static final String proxyClassNamePrefix = "$Proxy";
        private static final AtomicLong nextUniqueNumber;
        
        @Override
        public Class<?> apply(final ClassLoader classLoader, final Class<?>[] array) {
            final IdentityHashMap<Class<?>, Boolean> identityHashMap = new IdentityHashMap<Class<?>, Boolean>(array.length);
            for (final Class<?> clazz : array) {
                Class<?> forName = null;
                try {
                    forName = Class.forName(clazz.getName(), false, classLoader);
                }
                catch (ClassNotFoundException ex) {}
                if (forName != clazz) {
                    throw new IllegalArgumentException(clazz + " is not visible from class loader");
                }
                if (!forName.isInterface()) {
                    throw new IllegalArgumentException(forName.getName() + " is not an interface");
                }
                if (identityHashMap.put(forName, Boolean.TRUE) != null) {
                    throw new IllegalArgumentException("repeated interface: " + forName.getName());
                }
            }
            String s = null;
            int n = 17;
            for (final Class<?> clazz2 : array) {
                if (!Modifier.isPublic(clazz2.getModifiers())) {
                    n = 16;
                    final String name = clazz2.getName();
                    final int lastIndex = name.lastIndexOf(46);
                    final String s2 = (lastIndex == -1) ? "" : name.substring(0, lastIndex + 1);
                    if (s == null) {
                        s = s2;
                    }
                    else if (!s2.equals(s)) {
                        throw new IllegalArgumentException("non-public interfaces from different packages");
                    }
                }
            }
            if (s == null) {
                s = "com.sun.proxy.";
            }
            final String string = s + "$Proxy" + ProxyClassFactory.nextUniqueNumber.getAndIncrement();
            final byte[] generateProxyClass = ProxyGenerator.generateProxyClass(string, array, n);
            try {
                return defineClass0(classLoader, string, generateProxyClass, 0, generateProxyClass.length);
            }
            catch (ClassFormatError classFormatError) {
                throw new IllegalArgumentException(classFormatError.toString());
            }
        }
        
        static {
            nextUniqueNumber = new AtomicLong();
        }
    }
}
