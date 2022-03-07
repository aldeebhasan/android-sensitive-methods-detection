package java.lang.reflect;

import java.lang.annotation.*;
import sun.reflect.annotation.*;
import java.util.*;

public final class Parameter implements AnnotatedElement
{
    private final String name;
    private final int modifiers;
    private final Executable executable;
    private final int index;
    private transient volatile Type parameterTypeCache;
    private transient volatile Class<?> parameterClassCache;
    private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
    
    Parameter(final String name, final int modifiers, final Executable executable, final int index) {
        this.parameterTypeCache = null;
        this.parameterClassCache = null;
        this.name = name;
        this.modifiers = modifiers;
        this.executable = executable;
        this.index = index;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Parameter) {
            final Parameter parameter = (Parameter)o;
            return parameter.executable.equals(this.executable) && parameter.index == this.index;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.executable.hashCode() ^ this.index;
    }
    
    public boolean isNamePresent() {
        return this.executable.hasRealParameterData() && this.name != null;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final String typeName = this.getParameterizedType().getTypeName();
        sb.append(Modifier.toString(this.getModifiers()));
        if (0 != this.modifiers) {
            sb.append(' ');
        }
        if (this.isVarArgs()) {
            sb.append(typeName.replaceFirst("\\[\\]$", "..."));
        }
        else {
            sb.append(typeName);
        }
        sb.append(' ');
        sb.append(this.getName());
        return sb.toString();
    }
    
    public Executable getDeclaringExecutable() {
        return this.executable;
    }
    
    public int getModifiers() {
        return this.modifiers;
    }
    
    public String getName() {
        if (this.name == null || this.name.equals("")) {
            return "arg" + this.index;
        }
        return this.name;
    }
    
    String getRealName() {
        return this.name;
    }
    
    public Type getParameterizedType() {
        Type parameterTypeCache = this.parameterTypeCache;
        if (null == parameterTypeCache) {
            parameterTypeCache = this.executable.getAllGenericParameterTypes()[this.index];
            this.parameterTypeCache = parameterTypeCache;
        }
        return parameterTypeCache;
    }
    
    public Class<?> getType() {
        Class<?> parameterClassCache = this.parameterClassCache;
        if (null == parameterClassCache) {
            parameterClassCache = this.executable.getParameterTypes()[this.index];
            this.parameterClassCache = parameterClassCache;
        }
        return parameterClassCache;
    }
    
    public AnnotatedType getAnnotatedType() {
        return this.executable.getAnnotatedParameterTypes()[this.index];
    }
    
    public boolean isImplicit() {
        return Modifier.isMandated(this.getModifiers());
    }
    
    public boolean isSynthetic() {
        return Modifier.isSynthetic(this.getModifiers());
    }
    
    public boolean isVarArgs() {
        return this.executable.isVarArgs() && this.index == this.executable.getParameterCount() - 1;
    }
    
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
        return this.executable.getParameterAnnotations()[this.index];
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
    public Annotation[] getAnnotations() {
        return this.getDeclaredAnnotations();
    }
    
    private synchronized Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
        if (null == this.declaredAnnotations) {
            this.declaredAnnotations = new HashMap<Class<? extends Annotation>, Annotation>();
            final Annotation[] declaredAnnotations = this.getDeclaredAnnotations();
            for (int i = 0; i < declaredAnnotations.length; ++i) {
                this.declaredAnnotations.put(declaredAnnotations[i].annotationType(), declaredAnnotations[i]);
            }
        }
        return this.declaredAnnotations;
    }
}
