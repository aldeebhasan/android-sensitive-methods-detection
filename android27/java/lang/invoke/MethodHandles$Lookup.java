package java.lang.invoke;

import java.util.concurrent.*;
import sun.invoke.util.*;
import sun.reflect.misc.*;
import sun.security.util.*;
import java.security.*;
import java.lang.reflect.*;
import sun.misc.*;

public static final class Lookup
{
    private final Class<?> lookupClass;
    private final int allowedModes;
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int PACKAGE = 8;
    private static final int ALL_MODES = 15;
    private static final int TRUSTED = -1;
    static final Lookup PUBLIC_LOOKUP;
    static final Lookup IMPL_LOOKUP;
    private static final boolean ALLOW_NESTMATE_ACCESS = false;
    static ConcurrentHashMap<MemberName, DirectMethodHandle> LOOKASIDE_TABLE;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    private static int fixmods(int n) {
        n &= 0x7;
        return (n != 0) ? n : 8;
    }
    
    public Class<?> lookupClass() {
        return this.lookupClass;
    }
    
    private Class<?> lookupClassOrNull() {
        return (this.allowedModes == -1) ? null : this.lookupClass;
    }
    
    public int lookupModes() {
        return this.allowedModes & 0xF;
    }
    
    Lookup(final Class<?> clazz) {
        this(clazz, 15);
        checkUnprivilegedlookupClass(clazz, 15);
    }
    
    private Lookup(final Class<?> lookupClass, final int allowedModes) {
        this.lookupClass = lookupClass;
        this.allowedModes = allowedModes;
    }
    
    public Lookup in(final Class<?> clazz) {
        clazz.getClass();
        if (this.allowedModes == -1) {
            return new Lookup(clazz, 15);
        }
        if (clazz == this.lookupClass) {
            return this;
        }
        int n = this.allowedModes & 0xB;
        if ((n & 0x8) != 0x0 && !VerifyAccess.isSamePackage(this.lookupClass, clazz)) {
            n &= 0xFFFFFFF5;
        }
        if ((n & 0x2) != 0x0 && !VerifyAccess.isSamePackageMember(this.lookupClass, clazz)) {
            n &= 0xFFFFFFFD;
        }
        if ((n & 0x1) != 0x0 && !VerifyAccess.isClassAccessible(clazz, this.lookupClass, this.allowedModes)) {
            n = 0;
        }
        checkUnprivilegedlookupClass(clazz, n);
        return new Lookup(clazz, n);
    }
    
    private static void checkUnprivilegedlookupClass(final Class<?> clazz, final int n) {
        final String name = clazz.getName();
        if (name.startsWith("java.lang.invoke.")) {
            throw MethodHandleStatics.newIllegalArgumentException("illegal lookupClass: " + clazz);
        }
        if (n == 15 && clazz.getClassLoader() == null && (name.startsWith("java.") || (name.startsWith("sun.") && !name.startsWith("sun.invoke.") && !name.equals("sun.reflect.ReflectionFactory")))) {
            throw MethodHandleStatics.newIllegalArgumentException("illegal lookupClass: " + clazz);
        }
    }
    
    @Override
    public String toString() {
        final String name = this.lookupClass.getName();
        switch (this.allowedModes) {
            case 0: {
                return name + "/noaccess";
            }
            case 1: {
                return name + "/public";
            }
            case 9: {
                return name + "/package";
            }
            case 11: {
                return name + "/private";
            }
            case 15: {
                return name;
            }
            case -1: {
                return "/trusted";
            }
            default: {
                final String string = name + "/" + Integer.toHexString(this.allowedModes);
                assert false : string;
                return string;
            }
        }
    }
    
    public MethodHandle findStatic(final Class<?> clazz, final String s, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        final MemberName resolveOrFail = this.resolveOrFail((byte)6, clazz, s, methodType);
        return this.getDirectMethod((byte)6, clazz, resolveOrFail, this.findBoundCallerClass(resolveOrFail));
    }
    
    public MethodHandle findVirtual(final Class<?> clazz, final String s, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        if (clazz == MethodHandle.class) {
            final MethodHandle virtualForMH = this.findVirtualForMH(s, methodType);
            if (virtualForMH != null) {
                return virtualForMH;
            }
        }
        final int n = clazz.isInterface() ? 9 : 5;
        final MemberName resolveOrFail = this.resolveOrFail((byte)n, clazz, s, methodType);
        return this.getDirectMethod((byte)n, clazz, resolveOrFail, this.findBoundCallerClass(resolveOrFail));
    }
    
    private MethodHandle findVirtualForMH(final String s, final MethodType methodType) {
        if ("invoke".equals(s)) {
            return MethodHandles.invoker(methodType);
        }
        if ("invokeExact".equals(s)) {
            return MethodHandles.exactInvoker(methodType);
        }
        assert !MemberName.isMethodHandleInvokeName(s);
        return null;
    }
    
    public MethodHandle findConstructor(final Class<?> clazz, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        if (clazz.isArray()) {
            throw new NoSuchMethodException("no constructor for array class: " + clazz.getName());
        }
        return this.getDirectConstructor(clazz, this.resolveOrFail((byte)8, clazz, "<init>", methodType));
    }
    
    public MethodHandle findSpecial(final Class<?> clazz, final String s, final MethodType methodType, final Class<?> clazz2) throws NoSuchMethodException, IllegalAccessException {
        this.checkSpecialCaller(clazz2);
        final Lookup in = this.in(clazz2);
        final MemberName resolveOrFail = in.resolveOrFail((byte)7, clazz, s, methodType);
        return in.getDirectMethod((byte)7, clazz, resolveOrFail, this.findBoundCallerClass(resolveOrFail));
    }
    
    public MethodHandle findGetter(final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
        return this.getDirectField((byte)1, clazz, this.resolveOrFail((byte)1, clazz, s, clazz2));
    }
    
    public MethodHandle findSetter(final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
        return this.getDirectField((byte)3, clazz, this.resolveOrFail((byte)3, clazz, s, clazz2));
    }
    
    public MethodHandle findStaticGetter(final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
        return this.getDirectField((byte)2, clazz, this.resolveOrFail((byte)2, clazz, s, clazz2));
    }
    
    public MethodHandle findStaticSetter(final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
        return this.getDirectField((byte)4, clazz, this.resolveOrFail((byte)4, clazz, s, clazz2));
    }
    
    public MethodHandle bind(final Object o, final String s, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        final Class<?> class1 = o.getClass();
        final MemberName resolveOrFail = this.resolveOrFail((byte)7, class1, s, methodType);
        return this.getDirectMethodNoRestrict((byte)7, class1, resolveOrFail, this.findBoundCallerClass(resolveOrFail)).bindArgumentL(0, o).setVarargs(resolveOrFail);
    }
    
    public MethodHandle unreflect(final Method method) throws IllegalAccessException {
        if (method.getDeclaringClass() == MethodHandle.class) {
            final MethodHandle unreflectForMH = this.unreflectForMH(method);
            if (unreflectForMH != null) {
                return unreflectForMH;
            }
        }
        final MemberName memberName = new MemberName(method);
        byte referenceKind = memberName.getReferenceKind();
        if (referenceKind == 7) {
            referenceKind = 5;
        }
        assert memberName.isMethod();
        return (method.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectMethodNoSecurityManager(referenceKind, memberName.getDeclaringClass(), memberName, this.findBoundCallerClass(memberName));
    }
    
    private MethodHandle unreflectForMH(final Method method) {
        if (MemberName.isMethodHandleInvokeName(method.getName())) {
            return MethodHandleImpl.fakeMethodHandleInvoke(new MemberName(method));
        }
        return null;
    }
    
    public MethodHandle unreflectSpecial(final Method method, final Class<?> clazz) throws IllegalAccessException {
        this.checkSpecialCaller(clazz);
        final Lookup in = this.in(clazz);
        final MemberName memberName = new MemberName(method, true);
        assert memberName.isMethod();
        return in.getDirectMethodNoSecurityManager((byte)7, memberName.getDeclaringClass(), memberName, this.findBoundCallerClass(memberName));
    }
    
    public MethodHandle unreflectConstructor(final Constructor<?> constructor) throws IllegalAccessException {
        final MemberName memberName = new MemberName(constructor);
        assert memberName.isConstructor();
        return (constructor.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectConstructorNoSecurityManager(memberName.getDeclaringClass(), memberName);
    }
    
    public MethodHandle unreflectGetter(final Field field) throws IllegalAccessException {
        return this.unreflectField(field, false);
    }
    
    private MethodHandle unreflectField(final Field field, final boolean b) throws IllegalAccessException {
        final MemberName memberName = new MemberName(field, b);
        if (!Lookup.$assertionsDisabled) {
            if (b) {
                if (MethodHandleNatives.refKindIsSetter(memberName.getReferenceKind())) {
                    return (field.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectFieldNoSecurityManager(memberName.getReferenceKind(), field.getDeclaringClass(), memberName);
                }
            }
            else if (MethodHandleNatives.refKindIsGetter(memberName.getReferenceKind())) {
                return (field.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectFieldNoSecurityManager(memberName.getReferenceKind(), field.getDeclaringClass(), memberName);
            }
            throw new AssertionError();
        }
        return (field.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectFieldNoSecurityManager(memberName.getReferenceKind(), field.getDeclaringClass(), memberName);
    }
    
    public MethodHandle unreflectSetter(final Field field) throws IllegalAccessException {
        return this.unreflectField(field, true);
    }
    
    public MethodHandleInfo revealDirect(final MethodHandle methodHandle) {
        final MemberName internalMemberName = methodHandle.internalMemberName();
        if (internalMemberName == null || (!internalMemberName.isResolved() && !internalMemberName.isMethodHandleInvoke())) {
            throw MethodHandleStatics.newIllegalArgumentException("not a direct method handle");
        }
        final Class<?> declaringClass = internalMemberName.getDeclaringClass();
        byte referenceKind = internalMemberName.getReferenceKind();
        assert MethodHandleNatives.refKindIsValid(referenceKind);
        if (referenceKind == 7 && !methodHandle.isInvokeSpecial()) {
            referenceKind = 5;
        }
        if (referenceKind == 5 && declaringClass.isInterface()) {
            referenceKind = 9;
        }
        try {
            this.checkAccess(referenceKind, declaringClass, internalMemberName);
            this.checkSecurityManager(declaringClass, internalMemberName);
        }
        catch (IllegalAccessException ex) {
            throw new IllegalArgumentException(ex);
        }
        if (this.allowedModes != -1 && internalMemberName.isCallerSensitive()) {
            final Class<?> internalCallerClass = methodHandle.internalCallerClass();
            if (!this.hasPrivateAccess() || internalCallerClass != this.lookupClass()) {
                throw new IllegalArgumentException("method handle is caller sensitive: " + internalCallerClass);
            }
        }
        return new InfoFromMemberName(this, internalMemberName, referenceKind);
    }
    
    MemberName resolveOrFail(final byte b, final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
        this.checkSymbolicClass(clazz);
        s.getClass();
        clazz2.getClass();
        return MethodHandles.access$000().resolveOrFail(b, new MemberName(clazz, s, clazz2, b), this.lookupClassOrNull(), NoSuchFieldException.class);
    }
    
    MemberName resolveOrFail(final byte b, final Class<?> clazz, final String s, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        this.checkSymbolicClass(clazz);
        s.getClass();
        methodType.getClass();
        this.checkMethodName(b, s);
        return MethodHandles.access$000().resolveOrFail(b, new MemberName(clazz, s, methodType, b), this.lookupClassOrNull(), NoSuchMethodException.class);
    }
    
    MemberName resolveOrFail(final byte b, final MemberName memberName) throws ReflectiveOperationException {
        this.checkSymbolicClass(memberName.getDeclaringClass());
        memberName.getName().getClass();
        memberName.getType().getClass();
        return MethodHandles.access$000().resolveOrFail(b, memberName, this.lookupClassOrNull(), ReflectiveOperationException.class);
    }
    
    void checkSymbolicClass(final Class<?> clazz) throws IllegalAccessException {
        clazz.getClass();
        final Class<?> lookupClassOrNull = this.lookupClassOrNull();
        if (lookupClassOrNull != null && !VerifyAccess.isClassAccessible(clazz, lookupClassOrNull, this.allowedModes)) {
            throw new MemberName(clazz).makeAccessException("symbolic reference class is not public", this);
        }
    }
    
    void checkMethodName(final byte b, final String s) throws NoSuchMethodException {
        if (s.startsWith("<") && b != 8) {
            throw new NoSuchMethodException("illegal method name: " + s);
        }
    }
    
    Class<?> findBoundCallerClass(final MemberName memberName) throws IllegalAccessException {
        Class<?> lookupClass = null;
        if (MethodHandleNatives.isCallerSensitive(memberName)) {
            if (!this.hasPrivateAccess()) {
                throw new IllegalAccessException("Attempt to lookup caller-sensitive method using restricted lookup object");
            }
            lookupClass = this.lookupClass;
        }
        return lookupClass;
    }
    
    private boolean hasPrivateAccess() {
        return (this.allowedModes & 0x2) != 0x0;
    }
    
    void checkSecurityManager(final Class<?> clazz, final MemberName memberName) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return;
        }
        if (this.allowedModes == -1) {
            return;
        }
        final boolean hasPrivateAccess = this.hasPrivateAccess();
        if (!hasPrivateAccess || !VerifyAccess.classLoaderIsAncestor(this.lookupClass, clazz)) {
            ReflectUtil.checkPackageAccess(clazz);
        }
        if (memberName.isPublic()) {
            return;
        }
        if (!hasPrivateAccess) {
            securityManager.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
        }
        final Class<?> declaringClass = memberName.getDeclaringClass();
        if (!hasPrivateAccess && declaringClass != clazz) {
            ReflectUtil.checkPackageAccess(declaringClass);
        }
    }
    
    void checkMethod(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
        final boolean b2 = b == 6;
        String s;
        if (memberName.isConstructor()) {
            s = "expected a method, not a constructor";
        }
        else if (!memberName.isMethod()) {
            s = "expected a method";
        }
        else {
            if (b2 == memberName.isStatic()) {
                this.checkAccess(b, clazz, memberName);
                return;
            }
            s = (b2 ? "expected a static method" : "expected a non-static method");
        }
        throw memberName.makeAccessException(s, this);
    }
    
    void checkField(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
        final boolean b2 = !MethodHandleNatives.refKindHasReceiver(b);
        if (b2 != memberName.isStatic()) {
            throw memberName.makeAccessException(b2 ? "expected a static field" : "expected a non-static field", this);
        }
        this.checkAccess(b, clazz, memberName);
    }
    
    void checkAccess(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
        assert memberName.referenceKindIsConsistentWith(b) && MethodHandleNatives.refKindIsValid(b) && MethodHandleNatives.refKindIsField(b) == memberName.isField();
        final int allowedModes = this.allowedModes;
        if (allowedModes == -1) {
            return;
        }
        int modifiers = memberName.getModifiers();
        if (Modifier.isProtected(modifiers) && b == 5 && memberName.getDeclaringClass() == Object.class && memberName.getName().equals("clone") && clazz.isArray()) {
            modifiers ^= 0x5;
        }
        if (Modifier.isProtected(modifiers) && b == 8) {
            modifiers ^= 0x4;
        }
        if (Modifier.isFinal(modifiers) && MethodHandleNatives.refKindIsSetter(b)) {
            throw memberName.makeAccessException("unexpected set of a final field", this);
        }
        if (Modifier.isPublic(modifiers) && Modifier.isPublic(clazz.getModifiers()) && allowedModes != 0) {
            return;
        }
        final int fixmods = fixmods(modifiers);
        if ((fixmods & allowedModes) != 0x0) {
            if (VerifyAccess.isMemberAccessible(clazz, memberName.getDeclaringClass(), modifiers, this.lookupClass(), allowedModes)) {
                return;
            }
        }
        else if ((fixmods & 0x4) != 0x0 && (allowedModes & 0x8) != 0x0 && VerifyAccess.isSamePackage(memberName.getDeclaringClass(), this.lookupClass())) {
            return;
        }
        throw memberName.makeAccessException(this.accessFailedMessage(clazz, memberName), this);
    }
    
    String accessFailedMessage(final Class<?> clazz, final MemberName memberName) {
        final Class<?> declaringClass = memberName.getDeclaringClass();
        final int modifiers = memberName.getModifiers();
        boolean b = Modifier.isPublic(declaringClass.getModifiers()) && (declaringClass == clazz || Modifier.isPublic(clazz.getModifiers()));
        if (!b && (this.allowedModes & 0x8) != 0x0) {
            b = (VerifyAccess.isClassAccessible(declaringClass, this.lookupClass(), 15) && (declaringClass == clazz || VerifyAccess.isClassAccessible(clazz, this.lookupClass(), 15)));
        }
        if (!b) {
            return "class is not public";
        }
        if (Modifier.isPublic(modifiers)) {
            return "access to public member failed";
        }
        if (Modifier.isPrivate(modifiers)) {
            return "member is private";
        }
        if (Modifier.isProtected(modifiers)) {
            return "member is protected";
        }
        return "member is private to package";
    }
    
    private void checkSpecialCaller(final Class<?> clazz) throws IllegalAccessException {
        if (this.allowedModes == -1) {
            return;
        }
        if (!this.hasPrivateAccess() || clazz != this.lookupClass()) {
            throw new MemberName(clazz).makeAccessException("no private access for invokespecial", this);
        }
    }
    
    private boolean restrictProtectedReceiver(final MemberName memberName) {
        return memberName.isProtected() && !memberName.isStatic() && this.allowedModes != -1 && memberName.getDeclaringClass() != this.lookupClass() && !VerifyAccess.isSamePackage(memberName.getDeclaringClass(), this.lookupClass());
    }
    
    private MethodHandle restrictReceiver(final MemberName memberName, final DirectMethodHandle directMethodHandle, final Class<?> clazz) throws IllegalAccessException {
        assert !memberName.isStatic();
        if (!memberName.getDeclaringClass().isAssignableFrom(clazz)) {
            throw memberName.makeAccessException("caller class must be a subclass below the method", clazz);
        }
        final MethodType type = directMethodHandle.type();
        if (type.parameterType(0) == clazz) {
            return directMethodHandle;
        }
        final MethodType changeParameterType = type.changeParameterType(0, clazz);
        assert !directMethodHandle.isVarargsCollector();
        assert directMethodHandle.viewAsTypeChecks(changeParameterType, true);
        return directMethodHandle.copyWith(changeParameterType, directMethodHandle.form);
    }
    
    private MethodHandle getDirectMethod(final byte b, final Class<?> clazz, final MemberName memberName, final Class<?> clazz2) throws IllegalAccessException {
        return this.getDirectMethodCommon(b, clazz, memberName, true, true, clazz2);
    }
    
    private MethodHandle getDirectMethodNoRestrict(final byte b, final Class<?> clazz, final MemberName memberName, final Class<?> clazz2) throws IllegalAccessException {
        return this.getDirectMethodCommon(b, clazz, memberName, true, false, clazz2);
    }
    
    private MethodHandle getDirectMethodNoSecurityManager(final byte b, final Class<?> clazz, final MemberName memberName, final Class<?> clazz2) throws IllegalAccessException {
        return this.getDirectMethodCommon(b, clazz, memberName, false, true, clazz2);
    }
    
    private MethodHandle getDirectMethodCommon(final byte b, Class<?> clazz, MemberName varargs, final boolean b2, final boolean b3, final Class<?> clazz2) throws IllegalAccessException {
        this.checkMethod(b, clazz, varargs);
        if (b2) {
            this.checkSecurityManager(clazz, varargs);
        }
        assert !varargs.isMethodHandleInvoke();
        if (b == 7 && clazz != this.lookupClass() && !clazz.isInterface() && clazz != this.lookupClass().getSuperclass() && clazz.isAssignableFrom(this.lookupClass())) {
            assert !varargs.getName().equals("<init>");
            Class<?> clazz3 = this.lookupClass();
            MemberName resolveOrNull;
            do {
                clazz3 = clazz3.getSuperclass();
                resolveOrNull = MethodHandles.access$000().resolveOrNull(b, new MemberName(clazz3, varargs.getName(), varargs.getMethodType(), (byte)7), this.lookupClassOrNull());
            } while (resolveOrNull == null && clazz != clazz3);
            if (resolveOrNull == null) {
                throw new InternalError(varargs.toString());
            }
            varargs = resolveOrNull;
            clazz = clazz3;
            this.checkMethod(b, clazz, varargs);
        }
        MethodHandle methodHandle;
        final DirectMethodHandle directMethodHandle = (DirectMethodHandle)(methodHandle = DirectMethodHandle.make(b, clazz, varargs));
        if (b3 && (b == 7 || (MethodHandleNatives.refKindHasReceiver(b) && this.restrictProtectedReceiver(varargs)))) {
            methodHandle = this.restrictReceiver(varargs, directMethodHandle, this.lookupClass());
        }
        return this.maybeBindCaller(varargs, methodHandle, clazz2).setVarargs(varargs);
    }
    
    private MethodHandle maybeBindCaller(final MemberName memberName, final MethodHandle methodHandle, final Class<?> clazz) throws IllegalAccessException {
        if (this.allowedModes == -1 || !MethodHandleNatives.isCallerSensitive(memberName)) {
            return methodHandle;
        }
        Class<?> lookupClass = this.lookupClass;
        if (!this.hasPrivateAccess()) {
            lookupClass = clazz;
        }
        return MethodHandleImpl.bindCaller(methodHandle, lookupClass);
    }
    
    private MethodHandle getDirectField(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
        return this.getDirectFieldCommon(b, clazz, memberName, true);
    }
    
    private MethodHandle getDirectFieldNoSecurityManager(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
        return this.getDirectFieldCommon(b, clazz, memberName, false);
    }
    
    private MethodHandle getDirectFieldCommon(final byte b, final Class<?> clazz, final MemberName memberName, final boolean b2) throws IllegalAccessException {
        this.checkField(b, clazz, memberName);
        if (b2) {
            this.checkSecurityManager(clazz, memberName);
        }
        final DirectMethodHandle make = DirectMethodHandle.make(clazz, memberName);
        if (MethodHandleNatives.refKindHasReceiver(b) && this.restrictProtectedReceiver(memberName)) {
            return this.restrictReceiver(memberName, make, this.lookupClass());
        }
        return make;
    }
    
    private MethodHandle getDirectConstructor(final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
        return this.getDirectConstructorCommon(clazz, memberName, true);
    }
    
    private MethodHandle getDirectConstructorNoSecurityManager(final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
        return this.getDirectConstructorCommon(clazz, memberName, false);
    }
    
    private MethodHandle getDirectConstructorCommon(final Class<?> clazz, final MemberName varargs, final boolean b) throws IllegalAccessException {
        assert varargs.isConstructor();
        this.checkAccess((byte)8, clazz, varargs);
        if (b) {
            this.checkSecurityManager(clazz, varargs);
        }
        assert !MethodHandleNatives.isCallerSensitive(varargs);
        return DirectMethodHandle.make(varargs).setVarargs(varargs);
    }
    
    MethodHandle linkMethodHandleConstant(final byte b, final Class<?> clazz, final String s, final Object o) throws ReflectiveOperationException {
        if (!(o instanceof Class) && !(o instanceof MethodType)) {
            throw new InternalError("unresolved MemberName");
        }
        final MemberName memberName = new MemberName(b, clazz, s, o);
        final DirectMethodHandle directMethodHandle = Lookup.LOOKASIDE_TABLE.get(memberName);
        if (directMethodHandle != null) {
            this.checkSymbolicClass(clazz);
            return directMethodHandle;
        }
        if (clazz == MethodHandle.class && b == 5) {
            final MethodHandle virtualForMH = this.findVirtualForMH(memberName.getName(), memberName.getMethodType());
            if (virtualForMH != null) {
                return virtualForMH;
            }
        }
        final MemberName resolveOrFail = this.resolveOrFail(b, memberName);
        final MethodHandle directMethodForConstant = this.getDirectMethodForConstant(b, clazz, resolveOrFail);
        if (directMethodForConstant instanceof DirectMethodHandle && this.canBeCached(b, clazz, resolveOrFail)) {
            MemberName memberName2 = directMethodForConstant.internalMemberName();
            if (memberName2 != null) {
                memberName2 = memberName2.asNormalOriginal();
            }
            if (memberName.equals(memberName2)) {
                Lookup.LOOKASIDE_TABLE.put(memberName2, (DirectMethodHandle)directMethodForConstant);
            }
        }
        return directMethodForConstant;
    }
    
    private boolean canBeCached(final byte b, final Class<?> clazz, final MemberName memberName) {
        if (b == 7) {
            return false;
        }
        if (!Modifier.isPublic(clazz.getModifiers()) || !Modifier.isPublic(memberName.getDeclaringClass().getModifiers()) || !memberName.isPublic() || memberName.isCallerSensitive()) {
            return false;
        }
        final ClassLoader classLoader = clazz.getClassLoader();
        if (!VM.isSystemDomainLoader(classLoader)) {
            ClassLoader classLoader2 = ClassLoader.getSystemClassLoader();
            boolean b2 = false;
            while (classLoader2 != null) {
                if (classLoader == classLoader2) {
                    b2 = true;
                    break;
                }
                classLoader2 = classLoader2.getParent();
            }
            if (!b2) {
                return false;
            }
        }
        try {
            this.checkSecurityManager(clazz, MethodHandles.publicLookup().resolveOrFail(b, new MemberName(b, clazz, memberName.getName(), memberName.getType())));
        }
        catch (ReflectiveOperationException | SecurityException ex) {
            return false;
        }
        return true;
    }
    
    private MethodHandle getDirectMethodForConstant(final byte b, final Class<?> clazz, final MemberName memberName) throws ReflectiveOperationException {
        if (MethodHandleNatives.refKindIsField(b)) {
            return this.getDirectFieldNoSecurityManager(b, clazz, memberName);
        }
        if (MethodHandleNatives.refKindIsMethod(b)) {
            return this.getDirectMethodNoSecurityManager(b, clazz, memberName, this.lookupClass);
        }
        if (b == 8) {
            return this.getDirectConstructorNoSecurityManager(clazz, memberName);
        }
        throw MethodHandleStatics.newIllegalArgumentException("bad MethodHandle constant #" + memberName);
    }
    
    static {
        MethodHandles.access$000().getClass();
        PUBLIC_LOOKUP = new Lookup(Object.class, 1);
        IMPL_LOOKUP = new Lookup(Object.class, -1);
        Lookup.LOOKASIDE_TABLE = new ConcurrentHashMap<MemberName, DirectMethodHandle>();
    }
}
