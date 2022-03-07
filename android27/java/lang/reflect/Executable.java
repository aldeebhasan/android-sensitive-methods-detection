package java.lang.reflect;

import java.lang.annotation.*;
import sun.reflect.generics.repository.*;
import sun.misc.*;
import java.util.*;
import sun.reflect.annotation.*;

public abstract class Executable extends AccessibleObject implements Member, GenericDeclaration
{
    private transient volatile boolean hasRealParameterData;
    private transient volatile Parameter[] parameters;
    private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
    
    abstract byte[] getAnnotationBytes();
    
    abstract Executable getRoot();
    
    abstract boolean hasGenericInformation();
    
    abstract ConstructorRepository getGenericInfo();
    
    boolean equalParamTypes(final Class<?>[] array, final Class<?>[] array2) {
        if (array.length == array2.length) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] != array2[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    Annotation[][] parseParameterAnnotations(final byte[] array) {
        return AnnotationParser.parseParameterAnnotations(array, SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this.getDeclaringClass());
    }
    
    void separateWithCommas(final Class<?>[] array, final StringBuilder sb) {
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i].getTypeName());
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
    }
    
    void printModifiersIfNonzero(final StringBuilder sb, final int n, final boolean b) {
        final int n2 = this.getModifiers() & n;
        if (n2 != 0 && !b) {
            sb.append(Modifier.toString(n2)).append(' ');
        }
        else {
            final int n3 = n2 & 0x7;
            if (n3 != 0) {
                sb.append(Modifier.toString(n3)).append(' ');
            }
            if (b) {
                sb.append("default ");
            }
            final int n4 = n2 & 0xFFFFFFF8;
            if (n4 != 0) {
                sb.append(Modifier.toString(n4)).append(' ');
            }
        }
    }
    
    String sharedToString(final int n, final boolean b, final Class<?>[] array, final Class<?>[] array2) {
        try {
            final StringBuilder sb = new StringBuilder();
            this.printModifiersIfNonzero(sb, n, b);
            this.specificToStringHeader(sb);
            sb.append('(');
            this.separateWithCommas(array, sb);
            sb.append(')');
            if (array2.length > 0) {
                sb.append(" throws ");
                this.separateWithCommas(array2, sb);
            }
            return sb.toString();
        }
        catch (Exception ex) {
            return "<" + ex + ">";
        }
    }
    
    abstract void specificToStringHeader(final StringBuilder p0);
    
    String sharedToGenericString(final int n, final boolean b) {
        try {
            final StringBuilder sb = new StringBuilder();
            this.printModifiersIfNonzero(sb, n, b);
            final TypeVariable<?>[] typeParameters = this.getTypeParameters();
            if (typeParameters.length > 0) {
                int n2 = 1;
                sb.append('<');
                for (final TypeVariable<?> typeVariable : typeParameters) {
                    if (n2 == 0) {
                        sb.append(',');
                    }
                    sb.append(typeVariable.toString());
                    n2 = 0;
                }
                sb.append("> ");
            }
            this.specificToGenericStringHeader(sb);
            sb.append('(');
            final Type[] genericParameterTypes = this.getGenericParameterTypes();
            for (int j = 0; j < genericParameterTypes.length; ++j) {
                String s = genericParameterTypes[j].getTypeName();
                if (this.isVarArgs() && j == genericParameterTypes.length - 1) {
                    s = s.replaceFirst("\\[\\]$", "...");
                }
                sb.append(s);
                if (j < genericParameterTypes.length - 1) {
                    sb.append(',');
                }
            }
            sb.append(')');
            final Type[] genericExceptionTypes = this.getGenericExceptionTypes();
            if (genericExceptionTypes.length > 0) {
                sb.append(" throws ");
                for (int k = 0; k < genericExceptionTypes.length; ++k) {
                    sb.append((genericExceptionTypes[k] instanceof Class) ? ((Class)genericExceptionTypes[k]).getName() : genericExceptionTypes[k].toString());
                    if (k < genericExceptionTypes.length - 1) {
                        sb.append(',');
                    }
                }
            }
            return sb.toString();
        }
        catch (Exception ex) {
            return "<" + ex + ">";
        }
    }
    
    abstract void specificToGenericStringHeader(final StringBuilder p0);
    
    @Override
    public abstract Class<?> getDeclaringClass();
    
    @Override
    public abstract String getName();
    
    @Override
    public abstract int getModifiers();
    
    @Override
    public abstract TypeVariable<?>[] getTypeParameters();
    
    public abstract Class<?>[] getParameterTypes();
    
    public int getParameterCount() {
        throw new AbstractMethodError();
    }
    
    public Type[] getGenericParameterTypes() {
        if (this.hasGenericInformation()) {
            return this.getGenericInfo().getParameterTypes();
        }
        return this.getParameterTypes();
    }
    
    Type[] getAllGenericParameterTypes() {
        if (!this.hasGenericInformation()) {
            return this.getParameterTypes();
        }
        final boolean hasRealParameterData = this.hasRealParameterData();
        final Type[] genericParameterTypes = this.getGenericParameterTypes();
        final Class<?>[] parameterTypes = this.getParameterTypes();
        final Type[] array = new Type[parameterTypes.length];
        final Parameter[] parameters = this.getParameters();
        int n = 0;
        if (hasRealParameterData) {
            for (int i = 0; i < array.length; ++i) {
                final Parameter parameter = parameters[i];
                if (parameter.isSynthetic() || parameter.isImplicit()) {
                    array[i] = parameterTypes[i];
                }
                else {
                    array[i] = genericParameterTypes[n];
                    ++n;
                }
            }
            return array;
        }
        return (((Class<?>[])genericParameterTypes).length == parameterTypes.length) ? genericParameterTypes : parameterTypes;
    }
    
    public Parameter[] getParameters() {
        return this.privateGetParameters().clone();
    }
    
    private Parameter[] synthesizeAllParams() {
        final int parameterCount = this.getParameterCount();
        final Parameter[] array = new Parameter[parameterCount];
        for (int i = 0; i < parameterCount; ++i) {
            array[i] = new Parameter("arg" + i, 0, this, i);
        }
        return array;
    }
    
    private void verifyParameters(final Parameter[] array) {
        if (this.getParameterTypes().length != array.length) {
            throw new MalformedParametersException("Wrong number of parameters in MethodParameters attribute");
        }
        for (final Parameter parameter : array) {
            final String realName = parameter.getRealName();
            final int modifiers = parameter.getModifiers();
            if (realName != null && (realName.isEmpty() || realName.indexOf(46) != -1 || realName.indexOf(59) != -1 || realName.indexOf(91) != -1 || realName.indexOf(47) != -1)) {
                throw new MalformedParametersException("Invalid parameter name \"" + realName + "\"");
            }
            if (modifiers != (modifiers & 0x9010)) {
                throw new MalformedParametersException("Invalid parameter modifiers");
            }
        }
    }
    
    private Parameter[] privateGetParameters() {
        Parameter[] parameters = this.parameters;
        if (parameters == null) {
            try {
                parameters = this.getParameters0();
            }
            catch (IllegalArgumentException ex) {
                throw new MalformedParametersException("Invalid constant pool index");
            }
            if (parameters == null) {
                this.hasRealParameterData = false;
                parameters = this.synthesizeAllParams();
            }
            else {
                this.hasRealParameterData = true;
                this.verifyParameters(parameters);
            }
            this.parameters = parameters;
        }
        return parameters;
    }
    
    boolean hasRealParameterData() {
        if (this.parameters == null) {
            this.privateGetParameters();
        }
        return this.hasRealParameterData;
    }
    
    private native Parameter[] getParameters0();
    
    native byte[] getTypeAnnotationBytes0();
    
    byte[] getTypeAnnotationBytes() {
        return this.getTypeAnnotationBytes0();
    }
    
    public abstract Class<?>[] getExceptionTypes();
    
    public Type[] getGenericExceptionTypes() {
        final Type[] exceptionTypes;
        if (this.hasGenericInformation() && (exceptionTypes = this.getGenericInfo().getExceptionTypes()).length > 0) {
            return exceptionTypes;
        }
        return this.getExceptionTypes();
    }
    
    public abstract String toGenericString();
    
    public boolean isVarArgs() {
        return (this.getModifiers() & 0x80) != 0x0;
    }
    
    @Override
    public boolean isSynthetic() {
        return Modifier.isSynthetic(this.getModifiers());
    }
    
    public abstract Annotation[][] getParameterAnnotations();
    
    Annotation[][] sharedGetParameterAnnotations(final Class<?>[] array, final byte[] array2) {
        final int length = array.length;
        if (array2 == null) {
            return new Annotation[length][0];
        }
        final Annotation[][] parameterAnnotations = this.parseParameterAnnotations(array2);
        if (parameterAnnotations.length != length) {
            this.handleParameterNumberMismatch(parameterAnnotations.length, length);
        }
        return parameterAnnotations;
    }
    
    abstract void handleParameterNumberMismatch(final int p0, final int p1);
    
    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
        Objects.requireNonNull(clazz);
        return clazz.cast(this.declaredAnnotations().get(clazz));
    }
    
    @Override
    public <T extends Annotation> T[] getAnnotationsByType(final Class<T> clazz) {
        Objects.requireNonNull(clazz);
        return AnnotationSupport.getDirectlyAndIndirectlyPresent(this.declaredAnnotations(), clazz);
    }
    
    @Override
    public Annotation[] getDeclaredAnnotations() {
        return AnnotationParser.toArray(this.declaredAnnotations());
    }
    
    private synchronized Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
        if (this.declaredAnnotations == null) {
            final Executable root = this.getRoot();
            if (root != null) {
                this.declaredAnnotations = root.declaredAnnotations();
            }
            else {
                this.declaredAnnotations = AnnotationParser.parseAnnotations(this.getAnnotationBytes(), SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this.getDeclaringClass());
            }
        }
        return this.declaredAnnotations;
    }
    
    public abstract AnnotatedType getAnnotatedReturnType();
    
    AnnotatedType getAnnotatedReturnType0(final Type type) {
        return TypeAnnotationParser.buildAnnotatedType(this.getTypeAnnotationBytes0(), SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this, this.getDeclaringClass(), type, TypeAnnotation.TypeAnnotationTarget.METHOD_RETURN);
    }
    
    public AnnotatedType getAnnotatedReceiverType() {
        if (Modifier.isStatic(this.getModifiers())) {
            return null;
        }
        return TypeAnnotationParser.buildAnnotatedType(this.getTypeAnnotationBytes0(), SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this, this.getDeclaringClass(), this.getDeclaringClass(), TypeAnnotation.TypeAnnotationTarget.METHOD_RECEIVER);
    }
    
    public AnnotatedType[] getAnnotatedParameterTypes() {
        return TypeAnnotationParser.buildAnnotatedTypes(this.getTypeAnnotationBytes0(), SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this, this.getDeclaringClass(), this.getAllGenericParameterTypes(), TypeAnnotation.TypeAnnotationTarget.METHOD_FORMAL_PARAMETER);
    }
    
    public AnnotatedType[] getAnnotatedExceptionTypes() {
        return TypeAnnotationParser.buildAnnotatedTypes(this.getTypeAnnotationBytes0(), SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this, this.getDeclaringClass(), this.getGenericExceptionTypes(), TypeAnnotation.TypeAnnotationTarget.THROWS);
    }
}
