package java.lang.reflect;

import sun.reflect.generics.factory.*;
import sun.reflect.generics.scope.*;
import sun.reflect.*;
import java.nio.*;
import sun.misc.*;
import sun.reflect.annotation.*;
import java.lang.annotation.*;
import sun.reflect.generics.repository.*;

public final class Method extends Executable
{
    private Class<?> clazz;
    private int slot;
    private String name;
    private Class<?> returnType;
    private Class<?>[] parameterTypes;
    private Class<?>[] exceptionTypes;
    private int modifiers;
    private transient String signature;
    private transient MethodRepository genericInfo;
    private byte[] annotations;
    private byte[] parameterAnnotations;
    private byte[] annotationDefault;
    private volatile MethodAccessor methodAccessor;
    private Method root;
    
    private String getGenericSignature() {
        return this.signature;
    }
    
    private GenericsFactory getFactory() {
        return CoreReflectionFactory.make(this, MethodScope.make(this));
    }
    
    @Override
    MethodRepository getGenericInfo() {
        if (this.genericInfo == null) {
            this.genericInfo = MethodRepository.make(this.getGenericSignature(), this.getFactory());
        }
        return this.genericInfo;
    }
    
    Method(final Class<?> clazz, final String name, final Class<?>[] parameterTypes, final Class<?> returnType, final Class<?>[] exceptionTypes, final int modifiers, final int slot, final String signature, final byte[] annotations, final byte[] parameterAnnotations, final byte[] annotationDefault) {
        this.clazz = clazz;
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.exceptionTypes = exceptionTypes;
        this.modifiers = modifiers;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations;
        this.parameterAnnotations = parameterAnnotations;
        this.annotationDefault = annotationDefault;
    }
    
    Method copy() {
        if (this.root != null) {
            throw new IllegalArgumentException("Can not copy a non-root Method");
        }
        final Method method = new Method(this.clazz, this.name, this.parameterTypes, this.returnType, this.exceptionTypes, this.modifiers, this.slot, this.signature, this.annotations, this.parameterAnnotations, this.annotationDefault);
        method.root = this;
        method.methodAccessor = this.methodAccessor;
        return method;
    }
    
    @Override
    Executable getRoot() {
        return this.root;
    }
    
    @Override
    boolean hasGenericInformation() {
        return this.getGenericSignature() != null;
    }
    
    @Override
    byte[] getAnnotationBytes() {
        return this.annotations;
    }
    
    @Override
    public Class<?> getDeclaringClass() {
        return this.clazz;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public int getModifiers() {
        return this.modifiers;
    }
    
    @Override
    public TypeVariable<Method>[] getTypeParameters() {
        if (this.getGenericSignature() != null) {
            return (TypeVariable<Method>[])this.getGenericInfo().getTypeParameters();
        }
        return (TypeVariable<Method>[])new TypeVariable[0];
    }
    
    public Class<?> getReturnType() {
        return this.returnType;
    }
    
    public Type getGenericReturnType() {
        if (this.getGenericSignature() != null) {
            return this.getGenericInfo().getReturnType();
        }
        return this.getReturnType();
    }
    
    @Override
    public Class<?>[] getParameterTypes() {
        return (Class<?>[])this.parameterTypes.clone();
    }
    
    @Override
    public int getParameterCount() {
        return this.parameterTypes.length;
    }
    
    @Override
    public Type[] getGenericParameterTypes() {
        return super.getGenericParameterTypes();
    }
    
    @Override
    public Class<?>[] getExceptionTypes() {
        return (Class<?>[])this.exceptionTypes.clone();
    }
    
    @Override
    public Type[] getGenericExceptionTypes() {
        return super.getGenericExceptionTypes();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o != null && o instanceof Method) {
            final Method method = (Method)o;
            if (this.getDeclaringClass() == method.getDeclaringClass() && this.getName() == method.getName()) {
                return this.returnType.equals(method.getReturnType()) && this.equalParamTypes(this.parameterTypes, method.parameterTypes);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode() ^ this.getName().hashCode();
    }
    
    @Override
    public String toString() {
        return this.sharedToString(Modifier.methodModifiers(), this.isDefault(), this.parameterTypes, this.exceptionTypes);
    }
    
    @Override
    void specificToStringHeader(final StringBuilder sb) {
        sb.append(this.getReturnType().getTypeName()).append(' ');
        sb.append(this.getDeclaringClass().getTypeName()).append('.');
        sb.append(this.getName());
    }
    
    @Override
    public String toGenericString() {
        return this.sharedToGenericString(Modifier.methodModifiers(), this.isDefault());
    }
    
    @Override
    void specificToGenericStringHeader(final StringBuilder sb) {
        sb.append(this.getGenericReturnType().getTypeName()).append(' ');
        sb.append(this.getDeclaringClass().getTypeName()).append('.');
        sb.append(this.getName());
    }
    
    @CallerSensitive
    public Object invoke(final Object o, final Object... array) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        MethodAccessor methodAccessor = this.methodAccessor;
        if (methodAccessor == null) {
            methodAccessor = this.acquireMethodAccessor();
        }
        return methodAccessor.invoke(o, array);
    }
    
    public boolean isBridge() {
        return (this.getModifiers() & 0x40) != 0x0;
    }
    
    @Override
    public boolean isVarArgs() {
        return super.isVarArgs();
    }
    
    @Override
    public boolean isSynthetic() {
        return super.isSynthetic();
    }
    
    public boolean isDefault() {
        return (this.getModifiers() & 0x409) == 0x1 && this.getDeclaringClass().isInterface();
    }
    
    private MethodAccessor acquireMethodAccessor() {
        MethodAccessor methodAccessor = null;
        if (this.root != null) {
            methodAccessor = this.root.getMethodAccessor();
        }
        if (methodAccessor != null) {
            this.methodAccessor = methodAccessor;
        }
        else {
            methodAccessor = Method.reflectionFactory.newMethodAccessor(this);
            this.setMethodAccessor(methodAccessor);
        }
        return methodAccessor;
    }
    
    MethodAccessor getMethodAccessor() {
        return this.methodAccessor;
    }
    
    void setMethodAccessor(final MethodAccessor methodAccessor) {
        this.methodAccessor = methodAccessor;
        if (this.root != null) {
            this.root.setMethodAccessor(methodAccessor);
        }
    }
    
    public Object getDefaultValue() {
        if (this.annotationDefault == null) {
            return null;
        }
        final Object memberValue = AnnotationParser.parseMemberValue(AnnotationType.invocationHandlerReturnType(this.getReturnType()), ByteBuffer.wrap(this.annotationDefault), SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this.getDeclaringClass());
        if (memberValue instanceof ExceptionProxy) {
            throw new AnnotationFormatError("Invalid default: " + this);
        }
        return memberValue;
    }
    
    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
        return super.getAnnotation(clazz);
    }
    
    @Override
    public Annotation[] getDeclaredAnnotations() {
        return super.getDeclaredAnnotations();
    }
    
    @Override
    public Annotation[][] getParameterAnnotations() {
        return this.sharedGetParameterAnnotations(this.parameterTypes, this.parameterAnnotations);
    }
    
    @Override
    public AnnotatedType getAnnotatedReturnType() {
        return this.getAnnotatedReturnType0(this.getGenericReturnType());
    }
    
    @Override
    void handleParameterNumberMismatch(final int n, final int n2) {
        throw new AnnotationFormatError("Parameter annotations don't match number of parameters");
    }
}
