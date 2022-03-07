package java.lang.reflect;

import sun.reflect.generics.repository.*;
import java.lang.annotation.*;
import sun.reflect.generics.factory.*;
import sun.reflect.generics.scope.*;
import sun.reflect.*;
import java.util.*;
import sun.misc.*;
import sun.reflect.annotation.*;

public final class Field extends AccessibleObject implements Member
{
    private Class<?> clazz;
    private int slot;
    private String name;
    private Class<?> type;
    private int modifiers;
    private transient String signature;
    private transient FieldRepository genericInfo;
    private byte[] annotations;
    private FieldAccessor fieldAccessor;
    private FieldAccessor overrideFieldAccessor;
    private Field root;
    private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
    
    private String getGenericSignature() {
        return this.signature;
    }
    
    private GenericsFactory getFactory() {
        final Class<?> declaringClass = this.getDeclaringClass();
        return CoreReflectionFactory.make(declaringClass, ClassScope.make(declaringClass));
    }
    
    private FieldRepository getGenericInfo() {
        if (this.genericInfo == null) {
            this.genericInfo = FieldRepository.make(this.getGenericSignature(), this.getFactory());
        }
        return this.genericInfo;
    }
    
    Field(final Class<?> clazz, final String name, final Class<?> type, final int modifiers, final int slot, final String signature, final byte[] annotations) {
        this.clazz = clazz;
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations;
    }
    
    Field copy() {
        if (this.root != null) {
            throw new IllegalArgumentException("Can not copy a non-root Field");
        }
        final Field field = new Field(this.clazz, this.name, this.type, this.modifiers, this.slot, this.signature, this.annotations);
        field.root = this;
        field.fieldAccessor = this.fieldAccessor;
        field.overrideFieldAccessor = this.overrideFieldAccessor;
        return field;
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
    
    public boolean isEnumConstant() {
        return (this.getModifiers() & 0x4000) != 0x0;
    }
    
    @Override
    public boolean isSynthetic() {
        return Modifier.isSynthetic(this.getModifiers());
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public Type getGenericType() {
        if (this.getGenericSignature() != null) {
            return this.getGenericInfo().getGenericType();
        }
        return this.getType();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o != null && o instanceof Field) {
            final Field field = (Field)o;
            return this.getDeclaringClass() == field.getDeclaringClass() && this.getName() == field.getName() && this.getType() == field.getType();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode() ^ this.getName().hashCode();
    }
    
    @Override
    public String toString() {
        final int modifiers = this.getModifiers();
        return ((modifiers == 0) ? "" : (Modifier.toString(modifiers) + " ")) + this.getType().getTypeName() + " " + this.getDeclaringClass().getTypeName() + "." + this.getName();
    }
    
    public String toGenericString() {
        final int modifiers = this.getModifiers();
        return ((modifiers == 0) ? "" : (Modifier.toString(modifiers) + " ")) + this.getGenericType().getTypeName() + " " + this.getDeclaringClass().getTypeName() + "." + this.getName();
    }
    
    @CallerSensitive
    public Object get(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).get(o);
    }
    
    @CallerSensitive
    public boolean getBoolean(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).getBoolean(o);
    }
    
    @CallerSensitive
    public byte getByte(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).getByte(o);
    }
    
    @CallerSensitive
    public char getChar(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).getChar(o);
    }
    
    @CallerSensitive
    public short getShort(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).getShort(o);
    }
    
    @CallerSensitive
    public int getInt(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).getInt(o);
    }
    
    @CallerSensitive
    public long getLong(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).getLong(o);
    }
    
    @CallerSensitive
    public float getFloat(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).getFloat(o);
    }
    
    @CallerSensitive
    public double getDouble(final Object o) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        return this.getFieldAccessor(o).getDouble(o);
    }
    
    @CallerSensitive
    public void set(final Object o, final Object o2) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).set(o, o2);
    }
    
    @CallerSensitive
    public void setBoolean(final Object o, final boolean b) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).setBoolean(o, b);
    }
    
    @CallerSensitive
    public void setByte(final Object o, final byte b) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).setByte(o, b);
    }
    
    @CallerSensitive
    public void setChar(final Object o, final char c) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).setChar(o, c);
    }
    
    @CallerSensitive
    public void setShort(final Object o, final short n) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).setShort(o, n);
    }
    
    @CallerSensitive
    public void setInt(final Object o, final int n) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).setInt(o, n);
    }
    
    @CallerSensitive
    public void setLong(final Object o, final long n) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).setLong(o, n);
    }
    
    @CallerSensitive
    public void setFloat(final Object o, final float n) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).setFloat(o, n);
    }
    
    @CallerSensitive
    public void setDouble(final Object o, final double n) throws IllegalArgumentException, IllegalAccessException {
        if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
            this.checkAccess(Reflection.getCallerClass(), this.clazz, o, this.modifiers);
        }
        this.getFieldAccessor(o).setDouble(o, n);
    }
    
    private FieldAccessor getFieldAccessor(final Object o) throws IllegalAccessException {
        final boolean override = this.override;
        final FieldAccessor fieldAccessor = override ? this.overrideFieldAccessor : this.fieldAccessor;
        return (fieldAccessor != null) ? fieldAccessor : this.acquireFieldAccessor(override);
    }
    
    private FieldAccessor acquireFieldAccessor(final boolean b) {
        FieldAccessor fieldAccessor = null;
        if (this.root != null) {
            fieldAccessor = this.root.getFieldAccessor(b);
        }
        if (fieldAccessor != null) {
            if (b) {
                this.overrideFieldAccessor = fieldAccessor;
            }
            else {
                this.fieldAccessor = fieldAccessor;
            }
        }
        else {
            fieldAccessor = Field.reflectionFactory.newFieldAccessor(this, b);
            this.setFieldAccessor(fieldAccessor, b);
        }
        return fieldAccessor;
    }
    
    private FieldAccessor getFieldAccessor(final boolean b) {
        return b ? this.overrideFieldAccessor : this.fieldAccessor;
    }
    
    private void setFieldAccessor(final FieldAccessor fieldAccessor, final boolean b) {
        if (b) {
            this.overrideFieldAccessor = fieldAccessor;
        }
        else {
            this.fieldAccessor = fieldAccessor;
        }
        if (this.root != null) {
            this.root.setFieldAccessor(fieldAccessor, b);
        }
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
        return AnnotationParser.toArray(this.declaredAnnotations());
    }
    
    private synchronized Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
        if (this.declaredAnnotations == null) {
            final Field root = this.root;
            if (root != null) {
                this.declaredAnnotations = root.declaredAnnotations();
            }
            else {
                this.declaredAnnotations = AnnotationParser.parseAnnotations(this.annotations, SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this.getDeclaringClass());
            }
        }
        return this.declaredAnnotations;
    }
    
    private native byte[] getTypeAnnotationBytes0();
    
    public AnnotatedType getAnnotatedType() {
        return TypeAnnotationParser.buildAnnotatedType(this.getTypeAnnotationBytes0(), SharedSecrets.getJavaLangAccess().getConstantPool(this.getDeclaringClass()), this, this.getDeclaringClass(), this.getGenericType(), TypeAnnotation.TypeAnnotationTarget.FIELD);
    }
}
