package java.lang.invoke;

public abstract class CallSite
{
    MethodHandle target;
    private static final MethodHandle GET_TARGET;
    private static final MethodHandle THROW_UCS;
    private static final long TARGET_OFFSET;
    
    CallSite(final MethodType methodType) {
        this.target = this.makeUninitializedCallSite(methodType);
    }
    
    CallSite(final MethodHandle target) {
        target.type();
        this.target = target;
    }
    
    CallSite(final MethodType methodType, final MethodHandle methodHandle) throws Throwable {
        this(methodType);
        final MethodHandle target = (MethodHandle)methodHandle.invokeWithArguments((ConstantCallSite)this);
        this.checkTargetChange(this.target, target);
        this.target = target;
    }
    
    public MethodType type() {
        return this.target.type();
    }
    
    public abstract MethodHandle getTarget();
    
    public abstract void setTarget(final MethodHandle p0);
    
    void checkTargetChange(final MethodHandle methodHandle, final MethodHandle methodHandle2) {
        final MethodType type = methodHandle.type();
        if (!methodHandle2.type().equals((Object)type)) {
            throw wrongTargetType(methodHandle2, type);
        }
    }
    
    private static WrongMethodTypeException wrongTargetType(final MethodHandle methodHandle, final MethodType methodType) {
        return new WrongMethodTypeException(String.valueOf(methodHandle) + " should be of type " + methodType);
    }
    
    public abstract MethodHandle dynamicInvoker();
    
    MethodHandle makeDynamicInvoker() {
        return MethodHandles.foldArguments(MethodHandles.exactInvoker(this.type()), CallSite.GET_TARGET.bindArgumentL(0, this));
    }
    
    private static Object uninitializedCallSite(final Object... array) {
        throw new IllegalStateException("uninitialized call site");
    }
    
    private MethodHandle makeUninitializedCallSite(final MethodType methodType) {
        final MethodType basicType = methodType.basicType();
        MethodHandle methodHandle = basicType.form().cachedMethodHandle(2);
        if (methodHandle == null) {
            methodHandle = basicType.form().setCachedMethodHandle(2, CallSite.THROW_UCS.asType(basicType));
        }
        return methodHandle.viewAsType(methodType, false);
    }
    
    void setTargetNormal(final MethodHandle methodHandle) {
        MethodHandleNatives.setCallSiteTargetNormal(this, methodHandle);
    }
    
    MethodHandle getTargetVolatile() {
        return (MethodHandle)MethodHandleStatics.UNSAFE.getObjectVolatile(this, CallSite.TARGET_OFFSET);
    }
    
    void setTargetVolatile(final MethodHandle methodHandle) {
        MethodHandleNatives.setCallSiteTargetVolatile(this, methodHandle);
    }
    
    static CallSite makeSite(final MethodHandle methodHandle, final String s, final MethodType methodType, Object maybeReBox, final Class<?> clazz) {
        final MethodHandles.Lookup in = MethodHandles.Lookup.IMPL_LOOKUP.in(clazz);
        CallSite callSite;
        try {
            maybeReBox = maybeReBox(maybeReBox);
            Object o;
            if (maybeReBox == null) {
                o = methodHandle.invoke(in, s, methodType);
            }
            else if (!maybeReBox.getClass().isArray()) {
                o = methodHandle.invoke(in, s, methodType, maybeReBox);
            }
            else {
                final Object[] array = (Object[])maybeReBox;
                maybeReBoxElements(array);
                switch (array.length) {
                    case 0: {
                        o = methodHandle.invoke(in, s, methodType);
                        break;
                    }
                    case 1: {
                        o = methodHandle.invoke(in, s, methodType, array[0]);
                        break;
                    }
                    case 2: {
                        o = methodHandle.invoke(in, s, methodType, array[0], array[1]);
                        break;
                    }
                    case 3: {
                        o = methodHandle.invoke(in, s, methodType, array[0], array[1], array[2]);
                        break;
                    }
                    case 4: {
                        o = methodHandle.invoke(in, s, methodType, array[0], array[1], array[2], array[3]);
                        break;
                    }
                    case 5: {
                        o = methodHandle.invoke(in, s, methodType, array[0], array[1], array[2], array[3], array[4]);
                        break;
                    }
                    case 6: {
                        o = methodHandle.invoke(in, s, methodType, array[0], array[1], array[2], array[3], array[4], array[5]);
                        break;
                    }
                    default: {
                        if (3 + array.length > 254) {
                            throw new BootstrapMethodError("too many bootstrap method arguments");
                        }
                        methodHandle.type();
                        final MethodType genericMethodType = MethodType.genericMethodType(3 + array.length);
                        o = genericMethodType.invokers().spreadInvoker(3).invokeExact(methodHandle.asType(genericMethodType), (Object)in, (Object)s, (Object)methodType, array);
                        break;
                    }
                }
            }
            if (!(o instanceof CallSite)) {
                throw new ClassCastException("bootstrap method failed to produce a CallSite");
            }
            callSite = (CallSite)o;
            if (!callSite.getTarget().type().equals((Object)methodType)) {
                throw wrongTargetType(callSite.getTarget(), methodType);
            }
        }
        catch (Throwable t) {
            BootstrapMethodError bootstrapMethodError;
            if (t instanceof BootstrapMethodError) {
                bootstrapMethodError = (BootstrapMethodError)t;
            }
            else {
                bootstrapMethodError = new BootstrapMethodError("call site initialization exception", t);
            }
            throw bootstrapMethodError;
        }
        return callSite;
    }
    
    private static Object maybeReBox(Object value) {
        if (value instanceof Integer) {
            final int intValue = (int)value;
            if (intValue == (byte)intValue) {
                value = intValue;
            }
        }
        return value;
    }
    
    private static void maybeReBoxElements(final Object[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = maybeReBox(array[i]);
        }
    }
    
    static {
        MethodHandleImpl.initStatics();
        try {
            GET_TARGET = MethodHandles.Lookup.IMPL_LOOKUP.findVirtual(CallSite.class, "getTarget", MethodType.methodType(MethodHandle.class));
            THROW_UCS = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(CallSite.class, "uninitializedCallSite", MethodType.methodType(Object.class, Object[].class));
        }
        catch (ReflectiveOperationException ex) {
            throw MethodHandleStatics.newInternalError(ex);
        }
        try {
            TARGET_OFFSET = MethodHandleStatics.UNSAFE.objectFieldOffset(CallSite.class.getDeclaredField("target"));
        }
        catch (Exception ex2) {
            throw new Error(ex2);
        }
    }
}
