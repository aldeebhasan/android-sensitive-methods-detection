package java.lang;

import java.lang.ref.*;
import sun.security.util.*;
import sun.misc.*;
import sun.reflect.generics.repository.*;
import java.io.*;
import java.net.*;
import java.security.*;
import sun.reflect.misc.*;
import sun.reflect.generics.factory.*;
import sun.reflect.generics.scope.*;
import sun.reflect.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import sun.reflect.annotation.*;
import java.util.*;

public final class Class<T> implements Serializable, GenericDeclaration, Type, AnnotatedElement
{
    private static final int ANNOTATION = 8192;
    private static final int ENUM = 16384;
    private static final int SYNTHETIC = 4096;
    private transient volatile Constructor<T> cachedConstructor;
    private transient volatile Class<?> newInstanceCallerCache;
    private transient String name;
    private final ClassLoader classLoader;
    private static ProtectionDomain allPermDomain;
    private static boolean useCaches;
    private transient volatile SoftReference<ReflectionData<T>> reflectionData;
    private transient volatile int classRedefinedCount;
    private transient volatile ClassRepository genericInfo;
    private static final long serialVersionUID = 3206093459760846163L;
    private static final ObjectStreamField[] serialPersistentFields;
    private static ReflectionFactory reflectionFactory;
    private static boolean initted;
    private transient volatile T[] enumConstants;
    private transient volatile Map<String, T> enumConstantDirectory;
    private transient volatile AnnotationData annotationData;
    private transient volatile AnnotationType annotationType;
    transient ClassValue.ClassValueMap classValueMap;
    
    private static native void registerNatives();
    
    private Class(final ClassLoader classLoader) {
        this.classRedefinedCount = 0;
        this.enumConstants = null;
        this.enumConstantDirectory = null;
        this.classLoader = classLoader;
    }
    
    @Override
    public String toString() {
        return (this.isInterface() ? "interface " : (this.isPrimitive() ? "" : "class ")) + this.getName();
    }
    
    public String toGenericString() {
        if (this.isPrimitive()) {
            return this.toString();
        }
        final StringBuilder sb = new StringBuilder();
        final int n = this.getModifiers() & Modifier.classModifiers();
        if (n != 0) {
            sb.append(Modifier.toString(n));
            sb.append(' ');
        }
        if (this.isAnnotation()) {
            sb.append('@');
        }
        if (this.isInterface()) {
            sb.append("interface");
        }
        else if (this.isEnum()) {
            sb.append("enum");
        }
        else {
            sb.append("class");
        }
        sb.append(' ');
        sb.append(this.getName());
        final TypeVariable<Class<T>>[] typeParameters = this.getTypeParameters();
        if (typeParameters.length > 0) {
            int n2 = 1;
            sb.append('<');
            for (final TypeVariable<Class<T>> typeVariable : typeParameters) {
                if (n2 == 0) {
                    sb.append(',');
                }
                sb.append(typeVariable.getTypeName());
                n2 = 0;
            }
            sb.append('>');
        }
        return sb.toString();
    }
    
    @CallerSensitive
    public static Class<?> forName(final String s) throws ClassNotFoundException {
        final Class<?> callerClass = Reflection.getCallerClass();
        return forName0(s, true, ClassLoader.getClassLoader(callerClass), callerClass);
    }
    
    @CallerSensitive
    public static Class<?> forName(final String s, final boolean b, final ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> callerClass = null;
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            callerClass = Reflection.getCallerClass();
            if (VM.isSystemDomainLoader(classLoader) && !VM.isSystemDomainLoader(ClassLoader.getClassLoader(callerClass))) {
                securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
            }
        }
        return forName0(s, b, classLoader, callerClass);
    }
    
    private static native Class<?> forName0(final String p0, final boolean p1, final ClassLoader p2, final Class<?> p3) throws ClassNotFoundException;
    
    @CallerSensitive
    public T newInstance() throws InstantiationException, IllegalAccessException {
        if (System.getSecurityManager() != null) {
            this.checkMemberAccess(0, Reflection.getCallerClass(), false);
        }
        if (this.cachedConstructor == null) {
            if (this == Class.class) {
                throw new IllegalAccessException("Can not call newInstance() on the Class for java.lang.Class");
            }
            try {
                final Constructor<T> constructor0 = this.getConstructor0(new Class[0], 1);
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        constructor0.setAccessible(true);
                        return null;
                    }
                });
                this.cachedConstructor = constructor0;
            }
            catch (NoSuchMethodException ex) {
                throw (InstantiationException)new InstantiationException(this.getName()).initCause(ex);
            }
        }
        final Constructor<T> cachedConstructor = this.cachedConstructor;
        final int modifiers = cachedConstructor.getModifiers();
        if (!Reflection.quickCheckMemberAccess(this, modifiers)) {
            final Class<?> callerClass = Reflection.getCallerClass();
            if (this.newInstanceCallerCache != callerClass) {
                Reflection.ensureMemberAccess(callerClass, this, null, modifiers);
                this.newInstanceCallerCache = callerClass;
            }
        }
        try {
            return cachedConstructor.newInstance((Object[])null);
        }
        catch (InvocationTargetException ex2) {
            Unsafe.getUnsafe().throwException(ex2.getTargetException());
            return null;
        }
    }
    
    public native boolean isInstance(final Object p0);
    
    public native boolean isAssignableFrom(final Class<?> p0);
    
    public native boolean isInterface();
    
    public native boolean isArray();
    
    public native boolean isPrimitive();
    
    public boolean isAnnotation() {
        return (this.getModifiers() & 0x2000) != 0x0;
    }
    
    public boolean isSynthetic() {
        return (this.getModifiers() & 0x1000) != 0x0;
    }
    
    public String getName() {
        String name = this.name;
        if (name == null) {
            name = (this.name = this.getName0());
        }
        return name;
    }
    
    private native String getName0();
    
    @CallerSensitive
    public ClassLoader getClassLoader() {
        final ClassLoader classLoader0 = this.getClassLoader0();
        if (classLoader0 == null) {
            return null;
        }
        if (System.getSecurityManager() != null) {
            ClassLoader.checkClassLoaderPermission(classLoader0, Reflection.getCallerClass());
        }
        return classLoader0;
    }
    
    ClassLoader getClassLoader0() {
        return this.classLoader;
    }
    
    @Override
    public TypeVariable<Class<T>>[] getTypeParameters() {
        final ClassRepository genericInfo = this.getGenericInfo();
        if (genericInfo != null) {
            return (TypeVariable<Class<T>>[])genericInfo.getTypeParameters();
        }
        return (TypeVariable<Class<T>>[])new TypeVariable[0];
    }
    
    public native Class<? super T> getSuperclass();
    
    public Type getGenericSuperclass() {
        final ClassRepository genericInfo = this.getGenericInfo();
        if (genericInfo == null) {
            return this.getSuperclass();
        }
        if (this.isInterface()) {
            return null;
        }
        return genericInfo.getSuperclass();
    }
    
    public Package getPackage() {
        return Package.getPackage(this);
    }
    
    public Class<?>[] getInterfaces() {
        final ReflectionData<T> reflectionData = this.reflectionData();
        if (reflectionData == null) {
            return this.getInterfaces0();
        }
        Class<?>[] interfaces = reflectionData.interfaces;
        if (interfaces == null) {
            interfaces = this.getInterfaces0();
            reflectionData.interfaces = interfaces;
        }
        return (Class<?>[])interfaces.clone();
    }
    
    private native Class<?>[] getInterfaces0();
    
    public Type[] getGenericInterfaces() {
        final ClassRepository genericInfo = this.getGenericInfo();
        return (genericInfo == null) ? this.getInterfaces() : genericInfo.getSuperInterfaces();
    }
    
    public native Class<?> getComponentType();
    
    public native int getModifiers();
    
    public native Object[] getSigners();
    
    native void setSigners(final Object[] p0);
    
    @CallerSensitive
    public Method getEnclosingMethod() throws SecurityException {
        final EnclosingMethodInfo enclosingMethodInfo = this.getEnclosingMethodInfo();
        if (enclosingMethodInfo == null) {
            return null;
        }
        if (!enclosingMethodInfo.isMethod()) {
            return null;
        }
        final MethodRepository make = MethodRepository.make(enclosingMethodInfo.getDescriptor(), this.getFactory());
        final Class<?> class1 = toClass(make.getReturnType());
        final Type[] parameterTypes = make.getParameterTypes();
        final Class[] array = new Class[parameterTypes.length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = toClass(parameterTypes[i]);
        }
        final Class<?> enclosingClass = enclosingMethodInfo.getEnclosingClass();
        enclosingClass.checkMemberAccess(1, Reflection.getCallerClass(), true);
        for (final Method method : enclosingClass.getDeclaredMethods()) {
            if (method.getName().equals(enclosingMethodInfo.getName())) {
                final Class<?>[] parameterTypes2 = method.getParameterTypes();
                if (parameterTypes2.length == array.length) {
                    boolean b = true;
                    for (int k = 0; k < parameterTypes2.length; ++k) {
                        if (!parameterTypes2[k].equals(array[k])) {
                            b = false;
                            break;
                        }
                    }
                    if (b && method.getReturnType().equals(class1)) {
                        return method;
                    }
                }
            }
        }
        throw new InternalError("Enclosing method not found");
    }
    
    private native Object[] getEnclosingMethod0();
    
    private EnclosingMethodInfo getEnclosingMethodInfo() {
        final Object[] enclosingMethod0 = this.getEnclosingMethod0();
        if (enclosingMethod0 == null) {
            return null;
        }
        return new EnclosingMethodInfo(enclosingMethod0);
    }
    
    private static Class<?> toClass(final Type type) {
        if (type instanceof GenericArrayType) {
            return Array.newInstance(toClass(((GenericArrayType)type).getGenericComponentType()), 0).getClass();
        }
        return (Class<?>)type;
    }
    
    @CallerSensitive
    public Constructor<?> getEnclosingConstructor() throws SecurityException {
        final EnclosingMethodInfo enclosingMethodInfo = this.getEnclosingMethodInfo();
        if (enclosingMethodInfo == null) {
            return null;
        }
        if (!enclosingMethodInfo.isConstructor()) {
            return null;
        }
        final Type[] parameterTypes = ConstructorRepository.make(enclosingMethodInfo.getDescriptor(), this.getFactory()).getParameterTypes();
        final Class[] array = new Class[parameterTypes.length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = toClass(parameterTypes[i]);
        }
        final Class<?> enclosingClass = enclosingMethodInfo.getEnclosingClass();
        enclosingClass.checkMemberAccess(1, Reflection.getCallerClass(), true);
        for (final Constructor constructor : enclosingClass.getDeclaredConstructors()) {
            final Class[] parameterTypes2 = constructor.getParameterTypes();
            if (parameterTypes2.length == array.length) {
                boolean b = true;
                for (int k = 0; k < parameterTypes2.length; ++k) {
                    if (!parameterTypes2[k].equals(array[k])) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    return (Constructor<?>)constructor;
                }
            }
        }
        throw new InternalError("Enclosing constructor not found");
    }
    
    @CallerSensitive
    public Class<?> getDeclaringClass() throws SecurityException {
        final Class<?> declaringClass0 = this.getDeclaringClass0();
        if (declaringClass0 != null) {
            declaringClass0.checkPackageAccess(ClassLoader.getClassLoader(Reflection.getCallerClass()), true);
        }
        return declaringClass0;
    }
    
    private native Class<?> getDeclaringClass0();
    
    @CallerSensitive
    public Class<?> getEnclosingClass() throws SecurityException {
        final EnclosingMethodInfo enclosingMethodInfo = this.getEnclosingMethodInfo();
        Class<?> declaringClass;
        if (enclosingMethodInfo == null) {
            declaringClass = this.getDeclaringClass();
        }
        else {
            final Class<?> enclosingClass = enclosingMethodInfo.getEnclosingClass();
            if (enclosingClass == this || enclosingClass == null) {
                throw new InternalError("Malformed enclosing method information");
            }
            declaringClass = enclosingClass;
        }
        if (declaringClass != null) {
            declaringClass.checkPackageAccess(ClassLoader.getClassLoader(Reflection.getCallerClass()), true);
        }
        return declaringClass;
    }
    
    public String getSimpleName() {
        if (this.isArray()) {
            return this.getComponentType().getSimpleName() + "[]";
        }
        final String simpleBinaryName = this.getSimpleBinaryName();
        if (simpleBinaryName == null) {
            final String name = this.getName();
            return name.substring(name.lastIndexOf(".") + 1);
        }
        final int length = simpleBinaryName.length();
        if (length < 1 || simpleBinaryName.charAt(0) != '$') {
            throw new InternalError("Malformed class name");
        }
        int n;
        for (n = 1; n < length && isAsciiDigit(simpleBinaryName.charAt(n)); ++n) {}
        return simpleBinaryName.substring(n);
    }
    
    @Override
    public String getTypeName() {
        if (this.isArray()) {
            try {
                Class<?> componentType = this;
                int n = 0;
                while (componentType.isArray()) {
                    ++n;
                    componentType = componentType.getComponentType();
                }
                final StringBuilder sb = new StringBuilder();
                sb.append(componentType.getName());
                for (int i = 0; i < n; ++i) {
                    sb.append("[]");
                }
                return sb.toString();
            }
            catch (Throwable t) {}
        }
        return this.getName();
    }
    
    private static boolean isAsciiDigit(final char c) {
        return '0' <= c && c <= '9';
    }
    
    public String getCanonicalName() {
        if (this.isArray()) {
            final String canonicalName = this.getComponentType().getCanonicalName();
            if (canonicalName != null) {
                return canonicalName + "[]";
            }
            return null;
        }
        else {
            if (this.isLocalOrAnonymousClass()) {
                return null;
            }
            final Class<?> enclosingClass = this.getEnclosingClass();
            if (enclosingClass == null) {
                return this.getName();
            }
            final String canonicalName2 = enclosingClass.getCanonicalName();
            if (canonicalName2 == null) {
                return null;
            }
            return canonicalName2 + "." + this.getSimpleName();
        }
    }
    
    public boolean isAnonymousClass() {
        return "".equals(this.getSimpleName());
    }
    
    public boolean isLocalClass() {
        return this.isLocalOrAnonymousClass() && !this.isAnonymousClass();
    }
    
    public boolean isMemberClass() {
        return this.getSimpleBinaryName() != null && !this.isLocalOrAnonymousClass();
    }
    
    private String getSimpleBinaryName() {
        final Class<?> enclosingClass = this.getEnclosingClass();
        if (enclosingClass == null) {
            return null;
        }
        try {
            return this.getName().substring(enclosingClass.getName().length());
        }
        catch (IndexOutOfBoundsException ex) {
            throw new InternalError("Malformed class name", ex);
        }
    }
    
    private boolean isLocalOrAnonymousClass() {
        return this.getEnclosingMethodInfo() != null;
    }
    
    @CallerSensitive
    public Class<?>[] getClasses() {
        this.checkMemberAccess(0, Reflection.getCallerClass(), false);
        return (Class<?>[])AccessController.doPrivileged((PrivilegedAction<Class[]>)new PrivilegedAction<Class<?>[]>() {
            @Override
            public Class<?>[] run() {
                final ArrayList<Class<?>> list = new ArrayList<Class<?>>();
                for (Class<? super T> clazz = (Class<? super T>)Class.this; clazz != null; clazz = clazz.getSuperclass()) {
                    final Class<?>[] declaredClasses = clazz.getDeclaredClasses();
                    for (int i = 0; i < declaredClasses.length; ++i) {
                        if (Modifier.isPublic(declaredClasses[i].getModifiers())) {
                            list.add(declaredClasses[i]);
                        }
                    }
                }
                return list.toArray(new Class[0]);
            }
        });
    }
    
    @CallerSensitive
    public Field[] getFields() throws SecurityException {
        this.checkMemberAccess(0, Reflection.getCallerClass(), true);
        return copyFields(this.privateGetPublicFields(null));
    }
    
    @CallerSensitive
    public Method[] getMethods() throws SecurityException {
        this.checkMemberAccess(0, Reflection.getCallerClass(), true);
        return copyMethods(this.privateGetPublicMethods());
    }
    
    @CallerSensitive
    public Constructor<?>[] getConstructors() throws SecurityException {
        this.checkMemberAccess(0, Reflection.getCallerClass(), true);
        return copyConstructors(this.privateGetDeclaredConstructors(true));
    }
    
    @CallerSensitive
    public Field getField(final String s) throws NoSuchFieldException, SecurityException {
        this.checkMemberAccess(0, Reflection.getCallerClass(), true);
        final Field field0 = this.getField0(s);
        if (field0 == null) {
            throw new NoSuchFieldException(s);
        }
        return field0;
    }
    
    @CallerSensitive
    public Method getMethod(final String s, final Class<?>... array) throws NoSuchMethodException, SecurityException {
        this.checkMemberAccess(0, Reflection.getCallerClass(), true);
        final Method method0 = this.getMethod0(s, array, true);
        if (method0 == null) {
            throw new NoSuchMethodException(this.getName() + "." + s + argumentTypesToString(array));
        }
        return method0;
    }
    
    @CallerSensitive
    public Constructor<T> getConstructor(final Class<?>... array) throws NoSuchMethodException, SecurityException {
        this.checkMemberAccess(0, Reflection.getCallerClass(), true);
        return this.getConstructor0(array, 0);
    }
    
    @CallerSensitive
    public Class<?>[] getDeclaredClasses() throws SecurityException {
        this.checkMemberAccess(1, Reflection.getCallerClass(), false);
        return this.getDeclaredClasses0();
    }
    
    @CallerSensitive
    public Field[] getDeclaredFields() throws SecurityException {
        this.checkMemberAccess(1, Reflection.getCallerClass(), true);
        return copyFields(this.privateGetDeclaredFields(false));
    }
    
    @CallerSensitive
    public Method[] getDeclaredMethods() throws SecurityException {
        this.checkMemberAccess(1, Reflection.getCallerClass(), true);
        return copyMethods(this.privateGetDeclaredMethods(false));
    }
    
    @CallerSensitive
    public Constructor<?>[] getDeclaredConstructors() throws SecurityException {
        this.checkMemberAccess(1, Reflection.getCallerClass(), true);
        return copyConstructors(this.privateGetDeclaredConstructors(false));
    }
    
    @CallerSensitive
    public Field getDeclaredField(final String s) throws NoSuchFieldException, SecurityException {
        this.checkMemberAccess(1, Reflection.getCallerClass(), true);
        final Field searchFields = searchFields(this.privateGetDeclaredFields(false), s);
        if (searchFields == null) {
            throw new NoSuchFieldException(s);
        }
        return searchFields;
    }
    
    @CallerSensitive
    public Method getDeclaredMethod(final String s, final Class<?>... array) throws NoSuchMethodException, SecurityException {
        this.checkMemberAccess(1, Reflection.getCallerClass(), true);
        final Method searchMethods = searchMethods(this.privateGetDeclaredMethods(false), s, array);
        if (searchMethods == null) {
            throw new NoSuchMethodException(this.getName() + "." + s + argumentTypesToString(array));
        }
        return searchMethods;
    }
    
    @CallerSensitive
    public Constructor<T> getDeclaredConstructor(final Class<?>... array) throws NoSuchMethodException, SecurityException {
        this.checkMemberAccess(1, Reflection.getCallerClass(), true);
        return this.getConstructor0(array, 1);
    }
    
    public InputStream getResourceAsStream(String resolveName) {
        resolveName = this.resolveName(resolveName);
        final ClassLoader classLoader0 = this.getClassLoader0();
        if (classLoader0 == null) {
            return ClassLoader.getSystemResourceAsStream(resolveName);
        }
        return classLoader0.getResourceAsStream(resolveName);
    }
    
    public URL getResource(String resolveName) {
        resolveName = this.resolveName(resolveName);
        final ClassLoader classLoader0 = this.getClassLoader0();
        if (classLoader0 == null) {
            return ClassLoader.getSystemResource(resolveName);
        }
        return classLoader0.getResource(resolveName);
    }
    
    public ProtectionDomain getProtectionDomain() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.GET_PD_PERMISSION);
        }
        ProtectionDomain protectionDomain = this.getProtectionDomain0();
        if (protectionDomain == null) {
            if (Class.allPermDomain == null) {
                final Permissions permissions = new Permissions();
                permissions.add(SecurityConstants.ALL_PERMISSION);
                Class.allPermDomain = new ProtectionDomain(null, permissions);
            }
            protectionDomain = Class.allPermDomain;
        }
        return protectionDomain;
    }
    
    private native ProtectionDomain getProtectionDomain0();
    
    static native Class<?> getPrimitiveClass(final String p0);
    
    private void checkMemberAccess(final int n, final Class<?> clazz, final boolean b) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            final ClassLoader classLoader = ClassLoader.getClassLoader(clazz);
            final ClassLoader classLoader2 = this.getClassLoader0();
            if (n != 0 && classLoader != classLoader2) {
                securityManager.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
            }
            this.checkPackageAccess(classLoader, b);
        }
    }
    
    private void checkPackageAccess(final ClassLoader classLoader, final boolean b) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            if (ReflectUtil.needsPackageAccessCheck(classLoader, this.getClassLoader0())) {
                final String name = this.getName();
                final int lastIndex = name.lastIndexOf(46);
                if (lastIndex != -1) {
                    final String substring = name.substring(0, lastIndex);
                    if (!Proxy.isProxyClass(this) || ReflectUtil.isNonPublicProxyClass(this)) {
                        securityManager.checkPackageAccess(substring);
                    }
                }
            }
            if (b && Proxy.isProxyClass(this)) {
                ReflectUtil.checkProxyPackageAccess(classLoader, this.getInterfaces());
            }
        }
    }
    
    private String resolveName(String s) {
        if (s == null) {
            return s;
        }
        if (!s.startsWith("/")) {
            Class<?> componentType;
            for (componentType = this; componentType.isArray(); componentType = componentType.getComponentType()) {}
            final String name = componentType.getName();
            final int lastIndex = name.lastIndexOf(46);
            if (lastIndex != -1) {
                s = name.substring(0, lastIndex).replace('.', '/') + "/" + s;
            }
        }
        else {
            s = s.substring(1);
        }
        return s;
    }
    
    private ReflectionData<T> reflectionData() {
        final SoftReference<ReflectionData<T>> reflectionData = this.reflectionData;
        final int classRedefinedCount = this.classRedefinedCount;
        final ReflectionData<T> reflectionData2;
        if (Class.useCaches && reflectionData != null && (reflectionData2 = reflectionData.get()) != null && reflectionData2.redefinedCount == classRedefinedCount) {
            return reflectionData2;
        }
        return this.newReflectionData(reflectionData, classRedefinedCount);
    }
    
    private ReflectionData<T> newReflectionData(SoftReference<ReflectionData<T>> reflectionData, int classRedefinedCount) {
        if (!Class.useCaches) {
            return null;
        }
        while (true) {
            final ReflectionData<T> reflectionData2 = new ReflectionData<T>(classRedefinedCount);
            if (Atomic.casReflectionData(this, reflectionData, new SoftReference<ReflectionData<T>>(reflectionData2))) {
                return reflectionData2;
            }
            reflectionData = this.reflectionData;
            classRedefinedCount = this.classRedefinedCount;
            final ReflectionData<T> reflectionData3;
            if (reflectionData != null && (reflectionData3 = reflectionData.get()) != null && reflectionData3.redefinedCount == classRedefinedCount) {
                return reflectionData3;
            }
        }
    }
    
    private native String getGenericSignature0();
    
    private GenericsFactory getFactory() {
        return CoreReflectionFactory.make(this, ClassScope.make(this));
    }
    
    private ClassRepository getGenericInfo() {
        ClassRepository genericInfo = this.genericInfo;
        if (genericInfo == null) {
            final String genericSignature0 = this.getGenericSignature0();
            if (genericSignature0 == null) {
                genericInfo = ClassRepository.NONE;
            }
            else {
                genericInfo = ClassRepository.make(genericSignature0, this.getFactory());
            }
            this.genericInfo = genericInfo;
        }
        return (genericInfo != ClassRepository.NONE) ? genericInfo : null;
    }
    
    native byte[] getRawAnnotations();
    
    native byte[] getRawTypeAnnotations();
    
    static byte[] getExecutableTypeAnnotationBytes(final Executable executable) {
        return getReflectionFactory().getExecutableTypeAnnotationBytes(executable);
    }
    
    native ConstantPool getConstantPool();
    
    private Field[] privateGetDeclaredFields(final boolean b) {
        checkInitted();
        final ReflectionData<T> reflectionData = this.reflectionData();
        if (reflectionData != null) {
            final Field[] array = b ? reflectionData.declaredPublicFields : reflectionData.declaredFields;
            if (array != null) {
                return array;
            }
        }
        final Field[] filterFields = Reflection.filterFields(this, this.getDeclaredFields0(b));
        if (reflectionData != null) {
            if (b) {
                reflectionData.declaredPublicFields = filterFields;
            }
            else {
                reflectionData.declaredFields = filterFields;
            }
        }
        return filterFields;
    }
    
    private Field[] privateGetPublicFields(Set<Class<?>> set) {
        checkInitted();
        final ReflectionData<T> reflectionData = this.reflectionData();
        if (reflectionData != null) {
            final Field[] publicFields = reflectionData.publicFields;
            if (publicFields != null) {
                return publicFields;
            }
        }
        final ArrayList<Field> list = new ArrayList<Field>();
        if (set == null) {
            set = new HashSet<Class<?>>();
        }
        addAll(list, this.privateGetDeclaredFields(true));
        for (final Class<?> clazz : this.getInterfaces()) {
            if (!set.contains(clazz)) {
                set.add(clazz);
                addAll(list, clazz.privateGetPublicFields(set));
            }
        }
        if (!this.isInterface()) {
            final Class<? super T> superclass = this.getSuperclass();
            if (superclass != null) {
                addAll(list, superclass.privateGetPublicFields(set));
            }
        }
        final Field[] publicFields2 = new Field[list.size()];
        list.toArray(publicFields2);
        if (reflectionData != null) {
            reflectionData.publicFields = publicFields2;
        }
        return publicFields2;
    }
    
    private static void addAll(final Collection<Field> collection, final Field[] array) {
        for (int i = 0; i < array.length; ++i) {
            collection.add(array[i]);
        }
    }
    
    private Constructor<T>[] privateGetDeclaredConstructors(final boolean b) {
        checkInitted();
        final ReflectionData<T> reflectionData = this.reflectionData();
        if (reflectionData != null) {
            final Constructor<T>[] array = b ? reflectionData.publicConstructors : reflectionData.declaredConstructors;
            if (array != null) {
                return array;
            }
        }
        Constructor<T>[] declaredConstructors0;
        if (this.isInterface()) {
            declaredConstructors0 = (Constructor<T>[])new Constructor[0];
        }
        else {
            declaredConstructors0 = (Constructor<T>[])this.getDeclaredConstructors0(b);
        }
        if (reflectionData != null) {
            if (b) {
                reflectionData.publicConstructors = (Constructor<T>[])declaredConstructors0;
            }
            else {
                reflectionData.declaredConstructors = (Constructor<T>[])declaredConstructors0;
            }
        }
        return (Constructor<T>[])declaredConstructors0;
    }
    
    private Method[] privateGetDeclaredMethods(final boolean b) {
        checkInitted();
        final ReflectionData<T> reflectionData = this.reflectionData();
        if (reflectionData != null) {
            final Method[] array = b ? reflectionData.declaredPublicMethods : reflectionData.declaredMethods;
            if (array != null) {
                return array;
            }
        }
        final Method[] filterMethods = Reflection.filterMethods(this, this.getDeclaredMethods0(b));
        if (reflectionData != null) {
            if (b) {
                reflectionData.declaredPublicMethods = filterMethods;
            }
            else {
                reflectionData.declaredMethods = filterMethods;
            }
        }
        return filterMethods;
    }
    
    private Method[] privateGetPublicMethods() {
        checkInitted();
        final ReflectionData<T> reflectionData = this.reflectionData();
        if (reflectionData != null) {
            final Method[] publicMethods = reflectionData.publicMethods;
            if (publicMethods != null) {
                return publicMethods;
            }
        }
        final MethodArray methodArray = new MethodArray();
        methodArray.addAll(this.privateGetDeclaredMethods(true));
        MethodArray methodArray2 = new MethodArray();
        final Class<?>[] interfaces = this.getInterfaces();
        for (int length = interfaces.length, i = 0; i < length; ++i) {
            methodArray2.addInterfaceMethods(interfaces[i].privateGetPublicMethods());
        }
        if (!this.isInterface()) {
            final Class<? super T> superclass = this.getSuperclass();
            if (superclass != null) {
                final MethodArray methodArray3 = new MethodArray();
                methodArray3.addAll(superclass.privateGetPublicMethods());
                for (int j = 0; j < methodArray3.length(); ++j) {
                    final Method value = methodArray3.get(j);
                    if (value != null && !Modifier.isAbstract(value.getModifiers()) && !value.isDefault()) {
                        methodArray2.removeByNameAndDescriptor(value);
                    }
                }
                methodArray3.addAll(methodArray2);
                methodArray2 = methodArray3;
            }
        }
        for (int k = 0; k < methodArray.length(); ++k) {
            methodArray2.removeByNameAndDescriptor(methodArray.get(k));
        }
        methodArray.addAllIfNotPresent(methodArray2);
        methodArray.removeLessSpecifics();
        methodArray.compactAndTrim();
        final Method[] array = methodArray.getArray();
        if (reflectionData != null) {
            reflectionData.publicMethods = array;
        }
        return array;
    }
    
    private static Field searchFields(final Field[] array, final String s) {
        final String intern = s.intern();
        for (int i = 0; i < array.length; ++i) {
            if (array[i].getName() == intern) {
                return getReflectionFactory().copyField(array[i]);
            }
        }
        return null;
    }
    
    private Field getField0(final String s) throws NoSuchFieldException {
        final Field searchFields;
        if ((searchFields = searchFields(this.privateGetDeclaredFields(true), s)) != null) {
            return searchFields;
        }
        final Class<?>[] interfaces = this.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            final Field field0;
            if ((field0 = interfaces[i].getField0(s)) != null) {
                return field0;
            }
        }
        if (!this.isInterface()) {
            final Class<? super T> superclass = this.getSuperclass();
            final Field field2;
            if (superclass != null && (field2 = superclass.getField0(s)) != null) {
                return field2;
            }
        }
        return null;
    }
    
    private static Method searchMethods(final Method[] array, final String s, final Class<?>[] array2) {
        Method method = null;
        final String intern = s.intern();
        for (int i = 0; i < array.length; ++i) {
            final Method method2 = array[i];
            if (method2.getName() == intern && arrayContentsEq(array2, method2.getParameterTypes()) && (method == null || method.getReturnType().isAssignableFrom(method2.getReturnType()))) {
                method = method2;
            }
        }
        return (method == null) ? method : getReflectionFactory().copyMethod(method);
    }
    
    private Method getMethod0(final String s, final Class<?>[] array, final boolean b) {
        final MethodArray methodArray = new MethodArray(2);
        final Method privateGetMethodRecursive = this.privateGetMethodRecursive(s, array, b, methodArray);
        if (privateGetMethodRecursive != null) {
            return privateGetMethodRecursive;
        }
        methodArray.removeLessSpecifics();
        return methodArray.getFirst();
    }
    
    private Method privateGetMethodRecursive(final String s, final Class<?>[] array, final boolean b, final MethodArray methodArray) {
        final Method searchMethods;
        if ((searchMethods = searchMethods(this.privateGetDeclaredMethods(true), s, array)) != null && (b || !Modifier.isStatic(searchMethods.getModifiers()))) {
            return searchMethods;
        }
        if (!this.isInterface()) {
            final Class<? super T> superclass = this.getSuperclass();
            final Method method0;
            if (superclass != null && (method0 = superclass.getMethod0(s, array, true)) != null) {
                return method0;
            }
        }
        final Class<?>[] interfaces = this.getInterfaces();
        for (int length = interfaces.length, i = 0; i < length; ++i) {
            final Method method2;
            if ((method2 = interfaces[i].getMethod0(s, array, false)) != null) {
                methodArray.add(method2);
            }
        }
        return null;
    }
    
    private Constructor<T> getConstructor0(final Class<?>[] array, final int n) throws NoSuchMethodException {
        for (final Constructor<T> constructor : this.privateGetDeclaredConstructors(n == 0)) {
            if (arrayContentsEq(array, constructor.getParameterTypes())) {
                return getReflectionFactory().copyConstructor(constructor);
            }
        }
        throw new NoSuchMethodException(this.getName() + ".<init>" + argumentTypesToString(array));
    }
    
    private static boolean arrayContentsEq(final Object[] array, final Object[] array2) {
        if (array == null) {
            return array2 == null || array2.length == 0;
        }
        if (array2 == null) {
            return array.length == 0;
        }
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    private static Field[] copyFields(final Field[] array) {
        final Field[] array2 = new Field[array.length];
        final ReflectionFactory reflectionFactory = getReflectionFactory();
        for (int i = 0; i < array.length; ++i) {
            array2[i] = reflectionFactory.copyField(array[i]);
        }
        return array2;
    }
    
    private static Method[] copyMethods(final Method[] array) {
        final Method[] array2 = new Method[array.length];
        final ReflectionFactory reflectionFactory = getReflectionFactory();
        for (int i = 0; i < array.length; ++i) {
            array2[i] = reflectionFactory.copyMethod(array[i]);
        }
        return array2;
    }
    
    private static <U> Constructor<U>[] copyConstructors(final Constructor<U>[] array) {
        final Constructor[] array2 = array.clone();
        final ReflectionFactory reflectionFactory = getReflectionFactory();
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = reflectionFactory.copyConstructor((Constructor<U>)array2[i]);
        }
        return (Constructor<U>[])array2;
    }
    
    private native Field[] getDeclaredFields0(final boolean p0);
    
    private native Method[] getDeclaredMethods0(final boolean p0);
    
    private native Constructor<T>[] getDeclaredConstructors0(final boolean p0);
    
    private native Class<?>[] getDeclaredClasses0();
    
    private static String argumentTypesToString(final Class<?>[] array) {
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                final Class<?> clazz = array[i];
                sb.append((clazz == null) ? "null" : clazz.getName());
            }
        }
        sb.append(")");
        return sb.toString();
    }
    
    public boolean desiredAssertionStatus() {
        final ClassLoader classLoader = this.getClassLoader();
        if (classLoader == null) {
            return desiredAssertionStatus0(this);
        }
        synchronized (classLoader.assertionLock) {
            if (classLoader.classAssertionStatus != null) {
                return classLoader.desiredAssertionStatus(this.getName());
            }
        }
        return desiredAssertionStatus0(this);
    }
    
    private static native boolean desiredAssertionStatus0(final Class<?> p0);
    
    public boolean isEnum() {
        return (this.getModifiers() & 0x4000) != 0x0 && this.getSuperclass() == Enum.class;
    }
    
    private static ReflectionFactory getReflectionFactory() {
        if (Class.reflectionFactory == null) {
            Class.reflectionFactory = AccessController.doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction());
        }
        return Class.reflectionFactory;
    }
    
    private static void checkInitted() {
        if (Class.initted) {
            return;
        }
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                if (System.out == null) {
                    return null;
                }
                final String property = System.getProperty("sun.reflect.noCaches");
                if (property != null && property.equals("true")) {
                    Class.useCaches = false;
                }
                Class.initted = true;
                return null;
            }
        });
    }
    
    public T[] getEnumConstants() {
        final Object[] enumConstantsShared = this.getEnumConstantsShared();
        return (T[])((enumConstantsShared != null) ? ((Object[])enumConstantsShared.clone()) : null);
    }
    
    T[] getEnumConstantsShared() {
        if (this.enumConstants == null) {
            if (!this.isEnum()) {
                return null;
            }
            try {
                final Method method = this.getMethod("values", (Class<?>[])new Class[0]);
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        method.setAccessible(true);
                        return null;
                    }
                });
                this.enumConstants = (Object[])method.invoke(null, new Object[0]);
            }
            catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
                return null;
            }
        }
        return (T[])this.enumConstants;
    }
    
    Map<String, T> enumConstantDirectory() {
        if (this.enumConstantDirectory == null) {
            final Object[] enumConstantsShared = this.getEnumConstantsShared();
            if (enumConstantsShared == null) {
                throw new IllegalArgumentException(this.getName() + " is not an enum type");
            }
            final HashMap enumConstantDirectory = new HashMap<String, Enum>(2 * enumConstantsShared.length);
            for (final Object o : enumConstantsShared) {
                enumConstantDirectory.put(((Enum)o).name(), (Enum)o);
            }
            this.enumConstantDirectory = (Map<String, T>)enumConstantDirectory;
        }
        return this.enumConstantDirectory;
    }
    
    public T cast(final Object o) {
        if (o != null && !this.isInstance(o)) {
            throw new ClassCastException(this.cannotCastMsg(o));
        }
        return (T)o;
    }
    
    private String cannotCastMsg(final Object o) {
        return "Cannot cast " + o.getClass().getName() + " to " + this.getName();
    }
    
    public <U> Class<? extends U> asSubclass(final Class<U> clazz) {
        if (clazz.isAssignableFrom(this)) {
            return (Class<? extends U>)this;
        }
        throw new ClassCastException(this.toString());
    }
    
    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
        Objects.requireNonNull(clazz);
        return (A)this.annotationData().annotations.get(clazz);
    }
    
    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
        return super.isAnnotationPresent(clazz);
    }
    
    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> clazz) {
        Objects.requireNonNull(clazz);
        return AnnotationSupport.getAssociatedAnnotations(this.annotationData().declaredAnnotations, this, clazz);
    }
    
    @Override
    public Annotation[] getAnnotations() {
        return AnnotationParser.toArray(this.annotationData().annotations);
    }
    
    @Override
    public <A extends Annotation> A getDeclaredAnnotation(final Class<A> clazz) {
        Objects.requireNonNull(clazz);
        return (A)this.annotationData().declaredAnnotations.get(clazz);
    }
    
    @Override
    public <A extends Annotation> A[] getDeclaredAnnotationsByType(final Class<A> clazz) {
        Objects.requireNonNull(clazz);
        return AnnotationSupport.getDirectlyAndIndirectlyPresent(this.annotationData().declaredAnnotations, clazz);
    }
    
    @Override
    public Annotation[] getDeclaredAnnotations() {
        return AnnotationParser.toArray(this.annotationData().declaredAnnotations);
    }
    
    private AnnotationData annotationData() {
        while (true) {
            final AnnotationData annotationData = this.annotationData;
            final int classRedefinedCount = this.classRedefinedCount;
            if (annotationData != null && annotationData.redefinedCount == classRedefinedCount) {
                return annotationData;
            }
            final AnnotationData annotationData2 = this.createAnnotationData(classRedefinedCount);
            if (Atomic.casAnnotationData(this, annotationData, annotationData2)) {
                return annotationData2;
            }
        }
    }
    
    private AnnotationData createAnnotationData(final int n) {
        final Map<Class<? extends Annotation>, Annotation> annotations = AnnotationParser.parseAnnotations(this.getRawAnnotations(), this.getConstantPool(), this);
        final Class<? super T> superclass = this.getSuperclass();
        Map<Class<? extends Annotation>, Annotation> map = null;
        if (superclass != null) {
            final Map<Class<? extends Annotation>, Annotation> annotations2 = superclass.annotationData().annotations;
            for (final Map.Entry<Class<? extends Annotation>, Annotation> entry : annotations2.entrySet()) {
                final Class<? extends Annotation> clazz = entry.getKey();
                if (AnnotationType.getInstance(clazz).isInherited()) {
                    if (map == null) {
                        map = new LinkedHashMap<Class<? extends Annotation>, Annotation>((Math.max(annotations.size(), Math.min(12, annotations.size() + annotations2.size())) * 4 + 2) / 3);
                    }
                    map.put(clazz, entry.getValue());
                }
            }
        }
        if (map == null) {
            map = annotations;
        }
        else {
            map.putAll(annotations);
        }
        return new AnnotationData(map, annotations, n);
    }
    
    boolean casAnnotationType(final AnnotationType annotationType, final AnnotationType annotationType2) {
        return Atomic.casAnnotationType(this, annotationType, annotationType2);
    }
    
    AnnotationType getAnnotationType() {
        return this.annotationType;
    }
    
    Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap() {
        return this.annotationData().declaredAnnotations;
    }
    
    public AnnotatedType getAnnotatedSuperclass() {
        if (this == Object.class || this.isInterface() || this.isArray() || this.isPrimitive() || this == Void.TYPE) {
            return null;
        }
        return TypeAnnotationParser.buildAnnotatedSuperclass(this.getRawTypeAnnotations(), this.getConstantPool(), this);
    }
    
    public AnnotatedType[] getAnnotatedInterfaces() {
        return TypeAnnotationParser.buildAnnotatedInterfaces(this.getRawTypeAnnotations(), this.getConstantPool(), this);
    }
    
    static {
        registerNatives();
        Class.useCaches = true;
        serialPersistentFields = new ObjectStreamField[0];
        Class.initted = false;
    }
    
    private static class AnnotationData
    {
        final Map<Class<? extends Annotation>, Annotation> annotations;
        final Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
        final int redefinedCount;
        
        AnnotationData(final Map<Class<? extends Annotation>, Annotation> annotations, final Map<Class<? extends Annotation>, Annotation> declaredAnnotations, final int redefinedCount) {
            this.annotations = annotations;
            this.declaredAnnotations = declaredAnnotations;
            this.redefinedCount = redefinedCount;
        }
    }
    
    private static class Atomic
    {
        private static final Unsafe unsafe;
        private static final long reflectionDataOffset;
        private static final long annotationTypeOffset;
        private static final long annotationDataOffset;
        
        private static long objectFieldOffset(final Field[] array, final String s) {
            final Field access$200 = searchFields(array, s);
            if (access$200 == null) {
                throw new Error("No " + s + " field found in java.lang.Class");
            }
            return Atomic.unsafe.objectFieldOffset(access$200);
        }
        
        static <T> boolean casReflectionData(final Class<?> clazz, final SoftReference<ReflectionData<T>> softReference, final SoftReference<ReflectionData<T>> softReference2) {
            return Atomic.unsafe.compareAndSwapObject(clazz, Atomic.reflectionDataOffset, softReference, softReference2);
        }
        
        static <T> boolean casAnnotationType(final Class<?> clazz, final AnnotationType annotationType, final AnnotationType annotationType2) {
            return Atomic.unsafe.compareAndSwapObject(clazz, Atomic.annotationTypeOffset, annotationType, annotationType2);
        }
        
        static <T> boolean casAnnotationData(final Class<?> clazz, final AnnotationData annotationData, final AnnotationData annotationData2) {
            return Atomic.unsafe.compareAndSwapObject(clazz, Atomic.annotationDataOffset, annotationData, annotationData2);
        }
        
        static {
            unsafe = Unsafe.getUnsafe();
            final Field[] access$100 = ((Class<Object>)Class.class).getDeclaredFields0(false);
            reflectionDataOffset = objectFieldOffset(access$100, "reflectionData");
            annotationTypeOffset = objectFieldOffset(access$100, "annotationType");
            annotationDataOffset = objectFieldOffset(access$100, "annotationData");
        }
    }
    
    private static class ReflectionData<T>
    {
        volatile Field[] declaredFields;
        volatile Field[] publicFields;
        volatile Method[] declaredMethods;
        volatile Method[] publicMethods;
        volatile Constructor<T>[] declaredConstructors;
        volatile Constructor<T>[] publicConstructors;
        volatile Field[] declaredPublicFields;
        volatile Method[] declaredPublicMethods;
        volatile Class<?>[] interfaces;
        final int redefinedCount;
        
        ReflectionData(final int redefinedCount) {
            this.redefinedCount = redefinedCount;
        }
    }
    
    private static final class EnclosingMethodInfo
    {
        private Class<?> enclosingClass;
        private String name;
        private String descriptor;
        
        private EnclosingMethodInfo(final Object[] array) {
            if (array.length != 3) {
                throw new InternalError("Malformed enclosing method information");
            }
            try {
                this.enclosingClass = (Class<?>)array[0];
                assert this.enclosingClass != null;
                this.name = (String)array[1];
                this.descriptor = (String)array[2];
                assert this.name == this.descriptor;
            }
            catch (ClassCastException ex) {
                throw new InternalError("Invalid type in enclosing method information", ex);
            }
        }
        
        boolean isPartial() {
            return this.enclosingClass == null || this.name == null || this.descriptor == null;
        }
        
        boolean isConstructor() {
            return !this.isPartial() && "<init>".equals(this.name);
        }
        
        boolean isMethod() {
            return !this.isPartial() && !this.isConstructor() && !"<clinit>".equals(this.name);
        }
        
        Class<?> getEnclosingClass() {
            return this.enclosingClass;
        }
        
        String getName() {
            return this.name;
        }
        
        String getDescriptor() {
            return this.descriptor;
        }
    }
    
    static class MethodArray
    {
        private Method[] methods;
        private int length;
        private int defaults;
        
        MethodArray() {
            this(20);
        }
        
        MethodArray(final int n) {
            if (n < 2) {
                throw new IllegalArgumentException("Size should be 2 or more");
            }
            this.methods = new Method[n];
            this.length = 0;
            this.defaults = 0;
        }
        
        boolean hasDefaults() {
            return this.defaults != 0;
        }
        
        void add(final Method method) {
            if (this.length == this.methods.length) {
                this.methods = Arrays.copyOf(this.methods, 2 * this.methods.length);
            }
            this.methods[this.length++] = method;
            if (method != null && method.isDefault()) {
                ++this.defaults;
            }
        }
        
        void addAll(final Method[] array) {
            for (int i = 0; i < array.length; ++i) {
                this.add(array[i]);
            }
        }
        
        void addAll(final MethodArray methodArray) {
            for (int i = 0; i < methodArray.length(); ++i) {
                this.add(methodArray.get(i));
            }
        }
        
        void addIfNotPresent(final Method method) {
            for (int i = 0; i < this.length; ++i) {
                final Method method2 = this.methods[i];
                if (method2 == method || (method2 != null && method2.equals(method))) {
                    return;
                }
            }
            this.add(method);
        }
        
        void addAllIfNotPresent(final MethodArray methodArray) {
            for (int i = 0; i < methodArray.length(); ++i) {
                final Method value = methodArray.get(i);
                if (value != null) {
                    this.addIfNotPresent(value);
                }
            }
        }
        
        void addInterfaceMethods(final Method[] array) {
            for (final Method method : array) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    this.add(method);
                }
            }
        }
        
        int length() {
            return this.length;
        }
        
        Method get(final int n) {
            return this.methods[n];
        }
        
        Method getFirst() {
            for (final Method method : this.methods) {
                if (method != null) {
                    return method;
                }
            }
            return null;
        }
        
        void removeByNameAndDescriptor(final Method method) {
            for (int i = 0; i < this.length; ++i) {
                final Method method2 = this.methods[i];
                if (method2 != null && this.matchesNameAndDescriptor(method2, method)) {
                    this.remove(i);
                }
            }
        }
        
        private void remove(final int n) {
            if (this.methods[n] != null && this.methods[n].isDefault()) {
                --this.defaults;
            }
            this.methods[n] = null;
        }
        
        private boolean matchesNameAndDescriptor(final Method method, final Method method2) {
            return method.getReturnType() == method2.getReturnType() && method.getName() == method2.getName() && arrayContentsEq(method.getParameterTypes(), method2.getParameterTypes());
        }
        
        void compactAndTrim() {
            int n = 0;
            for (int i = 0; i < this.length; ++i) {
                final Method method = this.methods[i];
                if (method != null) {
                    if (i != n) {
                        this.methods[n] = method;
                    }
                    ++n;
                }
            }
            if (n != this.methods.length) {
                this.methods = Arrays.copyOf(this.methods, n);
            }
        }
        
        void removeLessSpecifics() {
            if (!this.hasDefaults()) {
                return;
            }
            for (int i = 0; i < this.length; ++i) {
                final Method value = this.get(i);
                if (value != null) {
                    if (value.isDefault()) {
                        for (int j = 0; j < this.length; ++j) {
                            if (i != j) {
                                final Method value2 = this.get(j);
                                if (value2 != null) {
                                    if (this.matchesNameAndDescriptor(value, value2)) {
                                        if (hasMoreSpecificClass(value, value2)) {
                                            this.remove(j);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        Method[] getArray() {
            return this.methods;
        }
        
        static boolean hasMoreSpecificClass(final Method method, final Method method2) {
            final Class<?> declaringClass = method.getDeclaringClass();
            final Class<?> declaringClass2 = method2.getDeclaringClass();
            return declaringClass != declaringClass2 && declaringClass2.isAssignableFrom(declaringClass);
        }
    }
}
