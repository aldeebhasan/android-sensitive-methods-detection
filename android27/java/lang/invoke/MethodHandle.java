package java.lang.invoke;

import java.util.*;
import java.lang.annotation.*;

public abstract class MethodHandle
{
    private final MethodType type;
    final LambdaForm form;
    MethodHandle asTypeCache;
    byte customizationCount;
    private static final long FORM_OFFSET;
    
    public MethodType type() {
        return this.type;
    }
    
    MethodHandle(final MethodType type, final LambdaForm lambdaForm) {
        type.getClass();
        lambdaForm.getClass();
        this.type = type;
        (this.form = lambdaForm.uncustomize()).prepare();
    }
    
    @PolymorphicSignature
    public final native Object invokeExact(final Object... p0) throws Throwable;
    
    @PolymorphicSignature
    public final native Object invoke(final Object... p0) throws Throwable;
    
    @PolymorphicSignature
    final native Object invokeBasic(final Object... p0) throws Throwable;
    
    @PolymorphicSignature
    static native Object linkToVirtual(final Object... p0) throws Throwable;
    
    @PolymorphicSignature
    static native Object linkToStatic(final Object... p0) throws Throwable;
    
    @PolymorphicSignature
    static native Object linkToSpecial(final Object... p0) throws Throwable;
    
    @PolymorphicSignature
    static native Object linkToInterface(final Object... p0) throws Throwable;
    
    public Object invokeWithArguments(final Object... array) throws Throwable {
        final MethodType genericMethodType = MethodType.genericMethodType((array == null) ? 0 : array.length);
        return genericMethodType.invokers().spreadInvoker(0).invokeExact(this.asType(genericMethodType), array);
    }
    
    public Object invokeWithArguments(final List<?> list) throws Throwable {
        return this.invokeWithArguments(list.toArray());
    }
    
    public MethodHandle asType(final MethodType methodType) {
        if (methodType == this.type) {
            return this;
        }
        final MethodHandle typeCached = this.asTypeCached(methodType);
        if (typeCached != null) {
            return typeCached;
        }
        return this.asTypeUncached(methodType);
    }
    
    private MethodHandle asTypeCached(final MethodType methodType) {
        final MethodHandle asTypeCache = this.asTypeCache;
        if (asTypeCache != null && methodType == asTypeCache.type) {
            return asTypeCache;
        }
        return null;
    }
    
    MethodHandle asTypeUncached(final MethodType methodType) {
        if (!this.type.isConvertibleTo(methodType)) {
            throw new WrongMethodTypeException("cannot convert " + this + " to " + methodType);
        }
        return this.asTypeCache = MethodHandleImpl.makePairwiseConvert(this, methodType, true);
    }
    
    public MethodHandle asSpreader(final Class<?> clazz, final int n) {
        final MethodType spreaderChecks = this.asSpreaderChecks(clazz, n);
        final int parameterCount = this.type().parameterCount();
        final int n2 = parameterCount - n;
        final BoundMethodHandle rebind = this.asType(spreaderChecks).rebind();
        return rebind.copyWith(spreaderChecks.replaceParameterTypes(n2, parameterCount, clazz), rebind.editor().spreadArgumentsForm(1 + n2, clazz, n));
    }
    
    private MethodType asSpreaderChecks(final Class<?> clazz, final int n) {
        this.spreadArrayChecks(clazz, n);
        final int parameterCount = this.type().parameterCount();
        if (parameterCount < n || n < 0) {
            throw MethodHandleStatics.newIllegalArgumentException("bad spread array length");
        }
        final Class<?> componentType = clazz.getComponentType();
        final MethodType type = this.type();
        boolean b = true;
        boolean b2 = false;
        for (int i = parameterCount - n; i < parameterCount; ++i) {
            final Class<?> parameterType = type.parameterType(i);
            if (parameterType != componentType) {
                b = false;
                if (!MethodType.canConvert(componentType, parameterType)) {
                    b2 = true;
                    break;
                }
            }
        }
        if (b) {
            return type;
        }
        final MethodType spreaderType = type.asSpreaderType(clazz, n);
        if (!b2) {
            return spreaderType;
        }
        this.asType(spreaderType);
        throw MethodHandleStatics.newInternalError("should not return", null);
    }
    
    private void spreadArrayChecks(final Class<?> clazz, final int n) {
        final Class componentType = clazz.getComponentType();
        if (componentType == null) {
            throw MethodHandleStatics.newIllegalArgumentException("not an array type", clazz);
        }
        if ((n & 0x7F) != n) {
            if ((n & 0xFF) != n) {
                throw MethodHandleStatics.newIllegalArgumentException("array length is not legal", n);
            }
            assert n >= 128;
            if (componentType == Long.TYPE || componentType == Double.TYPE) {
                throw MethodHandleStatics.newIllegalArgumentException("array length is not legal for long[] or double[]", n);
            }
        }
    }
    
    public MethodHandle asCollector(final Class<?> clazz, final int n) {
        this.asCollectorChecks(clazz, n);
        final int n2 = this.type().parameterCount() - 1;
        final BoundMethodHandle rebind = this.rebind();
        final MethodType collectorType = this.type().asCollectorType(clazz, n);
        final MethodHandle varargsArray = MethodHandleImpl.varargsArray(clazz, n);
        final LambdaForm collectArgumentArrayForm = rebind.editor().collectArgumentArrayForm(1 + n2, varargsArray);
        if (collectArgumentArrayForm != null) {
            return rebind.copyWith(collectorType, collectArgumentArrayForm);
        }
        return rebind.copyWithExtendL(collectorType, rebind.editor().collectArgumentsForm(1 + n2, varargsArray.type().basicType()), varargsArray);
    }
    
    boolean asCollectorChecks(final Class<?> clazz, final int n) {
        this.spreadArrayChecks(clazz, n);
        final int parameterCount = this.type().parameterCount();
        if (parameterCount != 0) {
            final Class<?> parameterType = this.type().parameterType(parameterCount - 1);
            if (parameterType == clazz) {
                return true;
            }
            if (parameterType.isAssignableFrom(clazz)) {
                return false;
            }
        }
        throw MethodHandleStatics.newIllegalArgumentException("array type not assignable to trailing argument", this, clazz);
    }
    
    public MethodHandle asVarargsCollector(final Class<?> clazz) {
        clazz.getClass();
        final boolean collectorChecks = this.asCollectorChecks(clazz, 0);
        if (this.isVarargsCollector() && collectorChecks) {
            return this;
        }
        return MethodHandleImpl.makeVarargsCollector(this, clazz);
    }
    
    public boolean isVarargsCollector() {
        return false;
    }
    
    public MethodHandle asFixedArity() {
        assert !this.isVarargsCollector();
        return this;
    }
    
    public MethodHandle bindTo(Object cast) {
        cast = this.type.leadingReferenceParameter().cast(cast);
        return this.bindArgumentL(0, cast);
    }
    
    @Override
    public String toString() {
        if (MethodHandleStatics.DEBUG_METHOD_HANDLE_NAMES) {
            return "MethodHandle" + this.debugString();
        }
        return this.standardString();
    }
    
    String standardString() {
        return "MethodHandle" + this.type;
    }
    
    String debugString() {
        return this.type + " : " + this.internalForm() + this.internalProperties();
    }
    
    BoundMethodHandle bindArgumentL(final int n, final Object o) {
        return this.rebind().bindArgumentL(n, o);
    }
    
    MethodHandle setVarargs(final MemberName memberName) throws IllegalAccessException {
        if (!memberName.isVarargs()) {
            return this;
        }
        final Class<?> lastParameterType = this.type().lastParameterType();
        if (lastParameterType.isArray()) {
            return MethodHandleImpl.makeVarargsCollector(this, lastParameterType);
        }
        throw memberName.makeAccessException("cannot make variable arity", null);
    }
    
    MethodHandle viewAsType(final MethodType methodType, final boolean b) {
        assert this.viewAsTypeChecks(methodType, b);
        final BoundMethodHandle rebind = this.rebind();
        assert !(rebind instanceof DirectMethodHandle);
        return rebind.copyWith(methodType, rebind.form);
    }
    
    boolean viewAsTypeChecks(final MethodType methodType, final boolean b) {
        if (b) {
            assert this.type().isViewableAs(methodType, true) : Arrays.asList(this, methodType);
        }
        else {
            assert this.type().basicType().isViewableAs(methodType.basicType(), true) : Arrays.asList(this, methodType);
        }
        return true;
    }
    
    LambdaForm internalForm() {
        return this.form;
    }
    
    MemberName internalMemberName() {
        return null;
    }
    
    Class<?> internalCallerClass() {
        return null;
    }
    
    MethodHandleImpl.Intrinsic intrinsicName() {
        return MethodHandleImpl.Intrinsic.NONE;
    }
    
    MethodHandle withInternalMemberName(final MemberName memberName, final boolean b) {
        if (memberName != null) {
            return MethodHandleImpl.makeWrappedMember(this, memberName, b);
        }
        if (this.internalMemberName() == null) {
            return this;
        }
        final BoundMethodHandle rebind = this.rebind();
        assert rebind.internalMemberName() == null;
        return rebind;
    }
    
    boolean isInvokeSpecial() {
        return false;
    }
    
    Object internalValues() {
        return null;
    }
    
    Object internalProperties() {
        return "";
    }
    
    abstract MethodHandle copyWith(final MethodType p0, final LambdaForm p1);
    
    abstract BoundMethodHandle rebind();
    
    void updateForm(final LambdaForm lambdaForm) {
        assert lambdaForm.customized == this;
        if (this.form == lambdaForm) {
            return;
        }
        lambdaForm.prepare();
        MethodHandleStatics.UNSAFE.putObject(this, MethodHandle.FORM_OFFSET, lambdaForm);
        MethodHandleStatics.UNSAFE.fullFence();
    }
    
    void customize() {
        if (this.form.customized == null) {
            this.updateForm(this.form.customize(this));
        }
        else {
            assert this.form.customized == this;
        }
    }
    
    static {
        MethodHandleImpl.initStatics();
        try {
            FORM_OFFSET = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodHandle.class.getDeclaredField("form"));
        }
        catch (ReflectiveOperationException ex) {
            throw MethodHandleStatics.newInternalError(ex);
        }
    }
    
    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface PolymorphicSignature {
    }
}
