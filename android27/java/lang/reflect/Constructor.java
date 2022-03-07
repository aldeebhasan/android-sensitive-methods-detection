package java.lang.reflect;

import sun.reflect.generics.repository.*;
import sun.reflect.generics.factory.*;
import sun.reflect.generics.scope.*;
import sun.reflect.*;
import java.lang.annotation.*;
import sun.misc.*;
import sun.reflect.annotation.*;

public final class Constructor<T> extends Executable
{
    private Class<T> clazz;
    private int slot;
    private Class<?>[] parameterTypes;
    private Class<?>[] exceptionTypes;
    private int modifiers;
    private transient String signature;
    private transient ConstructorRepository genericInfo;
    private byte[] annotations;
    private byte[] parameterAnnotations;
    private volatile ConstructorAccessor constructorAccessor;
    private Constructor<T> root;
    
    private GenericsFactory getFactory() {
        return CoreReflectionFactory.make(this, ConstructorScope.make(this));
    }
    
    @Override
    ConstructorRepository getGenericInfo() {
        if (this.genericInfo == null) {
            this.genericInfo = ConstructorRepository.make(this.getSignature(), this.getFactory());
        }
        return this.genericInfo;
    }
    
    @Override
    Executable getRoot() {
        return this.root;
    }
    
    Constructor(final Class<T> clazz, final Class<?>[] parameterTypes, final Class<?>[] exceptionTypes, final int modifiers, final int slot, final String signature, final byte[] annotations, final byte[] parameterAnnotations) {
        this.clazz = clazz;
        this.parameterTypes = parameterTypes;
        this.exceptionTypes = exceptionTypes;
        this.modifiers = modifiers;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations;
        this.parameterAnnotations = parameterAnnotations;
    }
    
    Constructor<T> copy() {
        if (this.root != null) {
            throw new IllegalArgumentException("Can not copy a non-root Constructor");
        }
        final Constructor constructor = new Constructor(this.clazz, this.parameterTypes, this.exceptionTypes, this.modifiers, this.slot, this.signature, this.annotations, this.parameterAnnotations);
        constructor.root = this;
        constructor.constructorAccessor = this.constructorAccessor;
        return constructor;
    }
    
    @Override
    boolean hasGenericInformation() {
        return this.getSignature() != null;
    }
    
    @Override
    byte[] getAnnotationBytes() {
        return this.annotations;
    }
    
    @Override
    public Class<T> getDeclaringClass() {
        return this.clazz;
    }
    
    @Override
    public String getName() {
        return this.getDeclaringClass().getName();
    }
    
    @Override
    public int getModifiers() {
        return this.modifiers;
    }
    
    @Override
    public TypeVariable<Constructor<T>>[] getTypeParameters() {
        if (this.getSignature() != null) {
            return (TypeVariable<Constructor<T>>[])this.getGenericInfo().getTypeParameters();
        }
        return (TypeVariable<Constructor<T>>[])new TypeVariable[0];
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
        if (o != null && o instanceof Constructor) {
            final Constructor constructor = (Constructor)o;
            if (this.getDeclaringClass() == constructor.getDeclaringClass()) {
                return this.equalParamTypes(this.parameterTypes, constructor.parameterTypes);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode();
    }
    
    @Override
    public String toString() {
        return this.sharedToString(Modifier.constructorModifiers(), false, this.parameterTypes, this.exceptionTypes);
    }
    
    @Override
    void specificToStringHeader(final StringBuilder sb) {
        sb.append(this.getDeclaringClass().getTypeName());
    }
    
    @Override
    public String toGenericString() {
        return this.sharedToGenericString(Modifier.constructorModifiers(), false);
    }
    
    @Override
    void specificToGenericStringHeader(final StringBuilder sb) {
        this.specificToStringHeader(sb);
    }
    
    @CallerSensitive
    public T newInstance(final Object... array) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, null, this.modifiers);
        }
        if ((this.clazz.getModifiers() & 0x4000) != 0x0) {
            throw new IllegalArgumentException("Cannot reflectively create enum objects");
        }
        ConstructorAccessor constructorAccessor = this.constructorAccessor;
        if (constructorAccessor == null) {
            constructorAccessor = this.acquireConstructorAccessor();
        }
        return (T)constructorAccessor.newInstance(array);
    }
    
    @Override
    public boolean isVarArgs() {
        return super.isVarArgs();
    }
    
    @Override
    public boolean isSynthetic() {
        return super.isSynthetic();
    }
    
    private ConstructorAccessor acquireConstructorAccessor() {
        ConstructorAccessor constructorAccessor = null;
        if (this.root != null) {
            constructorAccessor = this.root.getConstructorAccessor();
        }
        if (constructorAccessor != null) {
            this.constructorAccessor = constructorAccessor;
        }
        else {
            constructorAccessor = Constructor.reflectionFactory.newConstructorAccessor(this);
            this.setConstructorAccessor(constructorAccessor);
        }
        return constructorAccessor;
    }
    
    ConstructorAccessor getConstructorAccessor() {
        return this.constructorAccessor;
    }
    
    void setConstructorAccessor(final ConstructorAccessor constructorAccessor) {
        this.constructorAccessor = constructorAccessor;
        if (this.root != null) {
            this.root.setConstructorAccessor(constructorAccessor);
        }
    }
    
    int getSlot() {
        return this.slot;
    }
    
    String getSignature() {
        return this.signature;
    }
    
    byte[] getRawAnnotations() {
        return this.annotations;
    }
    
    byte[] getRawParameterAnnotations() {
        return this.parameterAnnotations;
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
    void handleParameterNumberMismatch(final int n, final int n2) {
        final Class<T> declaringClass = this.getDeclaringClass();
        if (declaringClass.isEnum() || declaringClass.isAnonymousClass() || declaringClass.isLocalClass()) {
            return;
        }
        if (!declaringClass.isMemberClass() || (declaringClass.isMemberClass() && (declaringClass.getModifiers() & 0x8) == 0x0 && n + 1 != n2)) {
            throw new AnnotationFormatError("Parameter annotations don't match number of parameters");
        }
    }
    
    @Override
    public AnnotatedType getAnnotatedReturnType() {
        return this.getAnnotatedReturnType0(this.getDeclaringClass());
    }
    
    @Override
    public AnnotatedType getAnnotatedReceiverType() {
        if (this.getDeclaringClass().getEnclosingClass() == null) {
            return super.getAnnotatedReceiverType();
        }
        return TypeAnnotationParser.buildAnnotatedType(this.getTypeAnnotationBytes0(), SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this, this.getDeclaringClass(), this.getDeclaringClass().getEnclosingClass(), TypeAnnotation.TypeAnnotationTarget.METHOD_RECEIVER);
    }
}
