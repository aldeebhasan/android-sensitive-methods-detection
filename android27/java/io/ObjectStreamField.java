package java.io;

import java.lang.reflect.*;
import sun.reflect.misc.*;
import sun.reflect.*;

public class ObjectStreamField implements Comparable<Object>
{
    private final String name;
    private final String signature;
    private final Class<?> type;
    private final boolean unshared;
    private final Field field;
    private int offset;
    
    public ObjectStreamField(final String s, final Class<?> clazz) {
        this(s, clazz, false);
    }
    
    public ObjectStreamField(final String name, final Class<?> type, final boolean unshared) {
        this.offset = 0;
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.type = type;
        this.unshared = unshared;
        this.signature = getClassSignature(type).intern();
        this.field = null;
    }
    
    ObjectStreamField(final String name, final String s, final boolean unshared) {
        this.offset = 0;
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.signature = s.intern();
        this.unshared = unshared;
        this.field = null;
        switch (s.charAt(0)) {
            case 'Z': {
                this.type = Boolean.TYPE;
                break;
            }
            case 'B': {
                this.type = Byte.TYPE;
                break;
            }
            case 'C': {
                this.type = Character.TYPE;
                break;
            }
            case 'S': {
                this.type = Short.TYPE;
                break;
            }
            case 'I': {
                this.type = Integer.TYPE;
                break;
            }
            case 'J': {
                this.type = Long.TYPE;
                break;
            }
            case 'F': {
                this.type = Float.TYPE;
                break;
            }
            case 'D': {
                this.type = Double.TYPE;
                break;
            }
            case 'L':
            case '[': {
                this.type = Object.class;
                break;
            }
            default: {
                throw new IllegalArgumentException("illegal signature");
            }
        }
    }
    
    ObjectStreamField(final Field field, final boolean unshared, final boolean b) {
        this.offset = 0;
        this.field = field;
        this.unshared = unshared;
        this.name = field.getName();
        final Class<?> type = field.getType();
        this.type = ((b || type.isPrimitive()) ? type : Object.class);
        this.signature = getClassSignature(type).intern();
    }
    
    public String getName() {
        return this.name;
    }
    
    @CallerSensitive
    public Class<?> getType() {
        if (System.getSecurityManager() != null && ReflectUtil.needsPackageAccessCheck(Reflection.getCallerClass().getClassLoader(), this.type.getClassLoader())) {
            ReflectUtil.checkPackageAccess(this.type);
        }
        return this.type;
    }
    
    public char getTypeCode() {
        return this.signature.charAt(0);
    }
    
    public String getTypeString() {
        return this.isPrimitive() ? null : this.signature;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    protected void setOffset(final int offset) {
        this.offset = offset;
    }
    
    public boolean isPrimitive() {
        final char char1 = this.signature.charAt(0);
        return char1 != 'L' && char1 != '[';
    }
    
    public boolean isUnshared() {
        return this.unshared;
    }
    
    @Override
    public int compareTo(final Object o) {
        final ObjectStreamField objectStreamField = (ObjectStreamField)o;
        final boolean primitive = this.isPrimitive();
        if (primitive != objectStreamField.isPrimitive()) {
            return primitive ? -1 : 1;
        }
        return this.name.compareTo(objectStreamField.name);
    }
    
    @Override
    public String toString() {
        return this.signature + ' ' + this.name;
    }
    
    Field getField() {
        return this.field;
    }
    
    String getSignature() {
        return this.signature;
    }
    
    private static String getClassSignature(Class<?> componentType) {
        final StringBuilder sb = new StringBuilder();
        while (componentType.isArray()) {
            sb.append('[');
            componentType = componentType.getComponentType();
        }
        if (componentType.isPrimitive()) {
            if (componentType == Integer.TYPE) {
                sb.append('I');
            }
            else if (componentType == Byte.TYPE) {
                sb.append('B');
            }
            else if (componentType == Long.TYPE) {
                sb.append('J');
            }
            else if (componentType == Float.TYPE) {
                sb.append('F');
            }
            else if (componentType == Double.TYPE) {
                sb.append('D');
            }
            else if (componentType == Short.TYPE) {
                sb.append('S');
            }
            else if (componentType == Character.TYPE) {
                sb.append('C');
            }
            else if (componentType == Boolean.TYPE) {
                sb.append('Z');
            }
            else {
                if (componentType != Void.TYPE) {
                    throw new InternalError();
                }
                sb.append('V');
            }
        }
        else {
            sb.append('L' + componentType.getName().replace('.', '/') + ';');
        }
        return sb.toString();
    }
}
