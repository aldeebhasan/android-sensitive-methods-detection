package java.lang.reflect;

import java.lang.annotation.*;
import sun.reflect.*;
import java.security.*;

public class AccessibleObject implements AnnotatedElement
{
    private static final Permission ACCESS_PERMISSION;
    boolean override;
    static final ReflectionFactory reflectionFactory;
    volatile Object securityCheckCache;
    
    public static void setAccessible(final AccessibleObject[] array, final boolean b) throws SecurityException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AccessibleObject.ACCESS_PERMISSION);
        }
        for (int i = 0; i < array.length; ++i) {
            setAccessible0(array[i], b);
        }
    }
    
    public void setAccessible(final boolean b) throws SecurityException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AccessibleObject.ACCESS_PERMISSION);
        }
        setAccessible0(this, b);
    }
    
    private static void setAccessible0(final AccessibleObject accessibleObject, final boolean override) throws SecurityException {
        if (accessibleObject instanceof Constructor && override && ((Constructor)accessibleObject).getDeclaringClass() == Class.class) {
            throw new SecurityException("Cannot make a java.lang.Class constructor accessible");
        }
        accessibleObject.override = override;
    }
    
    public boolean isAccessible() {
        return this.override;
    }
    
    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
        throw new AssertionError((Object)"All subclasses should override this method");
    }
    
    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
        return super.isAnnotationPresent(clazz);
    }
    
    @Override
    public <T extends Annotation> T[] getAnnotationsByType(final Class<T> clazz) {
        throw new AssertionError((Object)"All subclasses should override this method");
    }
    
    @Override
    public Annotation[] getAnnotations() {
        return this.getDeclaredAnnotations();
    }
    
    @Override
    public <T extends Annotation> T getDeclaredAnnotation(final Class<T> clazz) {
        return (T)this.getAnnotation((Class<Annotation>)clazz);
    }
    
    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(final Class<T> clazz) {
        return (T[])this.getAnnotationsByType((Class<Annotation>)clazz);
    }
    
    @Override
    public Annotation[] getDeclaredAnnotations() {
        throw new AssertionError((Object)"All subclasses should override this method");
    }
    
    void checkAccess(final Class<?> clazz, final Class<?> clazz2, final Object o, final int n) throws IllegalAccessException {
        if (clazz == clazz2) {
            return;
        }
        final Object securityCheckCache = this.securityCheckCache;
        Class<?> class1 = clazz2;
        if (o != null && Modifier.isProtected(n) && (class1 = o.getClass()) != clazz2) {
            if (securityCheckCache instanceof Class[]) {
                final Class[] array = (Class[])securityCheckCache;
                if (array[1] == class1 && array[0] == clazz) {
                    return;
                }
            }
        }
        else if (securityCheckCache == clazz) {
            return;
        }
        this.slowCheckMemberAccess(clazz, clazz2, o, n, class1);
    }
    
    void slowCheckMemberAccess(final Class<?> clazz, final Class<?> clazz2, final Object o, final int n, final Class<?> clazz3) throws IllegalAccessException {
        Reflection.ensureMemberAccess(clazz, clazz2, o, n);
        Class<?> securityCheckCache;
        if (clazz3 == clazz2) {
            securityCheckCache = clazz;
        }
        else {
            final Class[] array = (Object)(securityCheckCache = (Class<?>)(Object)new Class[2]);
            array[0] = clazz;
            array[1] = clazz3;
        }
        this.securityCheckCache = securityCheckCache;
    }
    
    static {
        ACCESS_PERMISSION = new ReflectPermission("suppressAccessChecks");
        reflectionFactory = AccessController.doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction());
    }
}
