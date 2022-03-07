package java.security;

import sun.reflect.*;
import sun.security.util.*;

public final class AccessController
{
    @CallerSensitive
    public static native <T> T doPrivileged(final PrivilegedAction<T> p0);
    
    @CallerSensitive
    public static <T> T doPrivilegedWithCombiner(final PrivilegedAction<T> privilegedAction) {
        final AccessControlContext stackAccessControlContext = getStackAccessControlContext();
        if (stackAccessControlContext == null) {
            return (T)doPrivileged((PrivilegedAction<Object>)privilegedAction);
        }
        return doPrivileged(privilegedAction, preserveCombiner(stackAccessControlContext.getAssignedCombiner(), Reflection.getCallerClass()));
    }
    
    @CallerSensitive
    public static native <T> T doPrivileged(final PrivilegedAction<T> p0, final AccessControlContext p1);
    
    @CallerSensitive
    public static <T> T doPrivileged(final PrivilegedAction<T> privilegedAction, final AccessControlContext accessControlContext, final Permission... array) {
        final AccessControlContext context = getContext();
        if (array == null) {
            throw new NullPointerException("null permissions parameter");
        }
        return doPrivileged(privilegedAction, createWrapper((accessControlContext == null) ? null : accessControlContext.getCombiner(), Reflection.getCallerClass(), context, accessControlContext, array));
    }
    
    @CallerSensitive
    public static <T> T doPrivilegedWithCombiner(final PrivilegedAction<T> privilegedAction, final AccessControlContext accessControlContext, final Permission... array) {
        final AccessControlContext context = getContext();
        DomainCombiner domainCombiner = context.getCombiner();
        if (domainCombiner == null && accessControlContext != null) {
            domainCombiner = accessControlContext.getCombiner();
        }
        if (array == null) {
            throw new NullPointerException("null permissions parameter");
        }
        return doPrivileged(privilegedAction, createWrapper(domainCombiner, Reflection.getCallerClass(), context, accessControlContext, array));
    }
    
    @CallerSensitive
    public static native <T> T doPrivileged(final PrivilegedExceptionAction<T> p0) throws PrivilegedActionException;
    
    @CallerSensitive
    public static <T> T doPrivilegedWithCombiner(final PrivilegedExceptionAction<T> privilegedExceptionAction) throws PrivilegedActionException {
        final AccessControlContext stackAccessControlContext = getStackAccessControlContext();
        if (stackAccessControlContext == null) {
            return (T)doPrivileged((PrivilegedExceptionAction<Object>)privilegedExceptionAction);
        }
        return doPrivileged(privilegedExceptionAction, preserveCombiner(stackAccessControlContext.getAssignedCombiner(), Reflection.getCallerClass()));
    }
    
    private static AccessControlContext preserveCombiner(final DomainCombiner domainCombiner, final Class<?> clazz) {
        return createWrapper(domainCombiner, clazz, null, null, null);
    }
    
    private static AccessControlContext createWrapper(final DomainCombiner domainCombiner, final Class<?> clazz, final AccessControlContext accessControlContext, final AccessControlContext accessControlContext2, final Permission[] array) {
        final ProtectionDomain callerPD = getCallerPD(clazz);
        if (accessControlContext2 != null && !accessControlContext2.isAuthorized() && System.getSecurityManager() != null && !callerPD.impliesCreateAccessControlContext()) {
            return new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, null) });
        }
        return new AccessControlContext(callerPD, domainCombiner, accessControlContext, accessControlContext2, array);
    }
    
    private static ProtectionDomain getCallerPD(final Class<?> clazz) {
        return doPrivileged((PrivilegedAction<ProtectionDomain>)new PrivilegedAction<ProtectionDomain>() {
            @Override
            public ProtectionDomain run() {
                return clazz.getProtectionDomain();
            }
        });
    }
    
    @CallerSensitive
    public static native <T> T doPrivileged(final PrivilegedExceptionAction<T> p0, final AccessControlContext p1) throws PrivilegedActionException;
    
    @CallerSensitive
    public static <T> T doPrivileged(final PrivilegedExceptionAction<T> privilegedExceptionAction, final AccessControlContext accessControlContext, final Permission... array) throws PrivilegedActionException {
        final AccessControlContext context = getContext();
        if (array == null) {
            throw new NullPointerException("null permissions parameter");
        }
        return doPrivileged(privilegedExceptionAction, createWrapper((accessControlContext == null) ? null : accessControlContext.getCombiner(), Reflection.getCallerClass(), context, accessControlContext, array));
    }
    
    @CallerSensitive
    public static <T> T doPrivilegedWithCombiner(final PrivilegedExceptionAction<T> privilegedExceptionAction, final AccessControlContext accessControlContext, final Permission... array) throws PrivilegedActionException {
        final AccessControlContext context = getContext();
        DomainCombiner domainCombiner = context.getCombiner();
        if (domainCombiner == null && accessControlContext != null) {
            domainCombiner = accessControlContext.getCombiner();
        }
        if (array == null) {
            throw new NullPointerException("null permissions parameter");
        }
        return doPrivileged(privilegedExceptionAction, createWrapper(domainCombiner, Reflection.getCallerClass(), context, accessControlContext, array));
    }
    
    private static native AccessControlContext getStackAccessControlContext();
    
    static native AccessControlContext getInheritedAccessControlContext();
    
    public static AccessControlContext getContext() {
        final AccessControlContext stackAccessControlContext = getStackAccessControlContext();
        if (stackAccessControlContext == null) {
            return new AccessControlContext(null, true);
        }
        return stackAccessControlContext.optimize();
    }
    
    public static void checkPermission(final Permission permission) throws AccessControlException {
        if (permission == null) {
            throw new NullPointerException("permission can't be null");
        }
        final AccessControlContext stackAccessControlContext = getStackAccessControlContext();
        if (stackAccessControlContext == null) {
            final Debug debug = AccessControlContext.getDebug();
            boolean b = false;
            if (debug != null) {
                b = (!Debug.isOn("codebase=") & (!Debug.isOn("permission=") || Debug.isOn("permission=" + permission.getClass().getCanonicalName())));
            }
            if (b && Debug.isOn("stack")) {
                Thread.dumpStack();
            }
            if (b && Debug.isOn("domain")) {
                debug.println("domain (context is null)");
            }
            if (b) {
                debug.println("access allowed " + permission);
            }
            return;
        }
        stackAccessControlContext.optimize().checkPermission(permission);
    }
}
