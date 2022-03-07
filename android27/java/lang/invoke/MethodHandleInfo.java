package java.lang.invoke;

import java.lang.reflect.*;
import java.util.*;

public interface MethodHandleInfo
{
    public static final int REF_getField = 1;
    public static final int REF_getStatic = 2;
    public static final int REF_putField = 3;
    public static final int REF_putStatic = 4;
    public static final int REF_invokeVirtual = 5;
    public static final int REF_invokeStatic = 6;
    public static final int REF_invokeSpecial = 7;
    public static final int REF_newInvokeSpecial = 8;
    public static final int REF_invokeInterface = 9;
    
    int getReferenceKind();
    
    Class<?> getDeclaringClass();
    
    String getName();
    
    MethodType getMethodType();
    
     <T extends Member> T reflectAs(final Class<T> p0, final MethodHandles.Lookup p1);
    
    int getModifiers();
    
    default boolean isVarArgs() {
        return !MethodHandleNatives.refKindIsField((byte)this.getReferenceKind()) && Modifier.isTransient(this.getModifiers());
    }
    
    default String referenceKindToString(final int n) {
        if (!MethodHandleNatives.refKindIsValid(n)) {
            throw MethodHandleStatics.newIllegalArgumentException("invalid reference kind", n);
        }
        return MethodHandleNatives.refKindName((byte)n);
    }
    
    default String toString(final int n, final Class<?> clazz, final String s, final MethodType methodType) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(methodType);
        return String.format("%s %s.%s:%s", referenceKindToString(n), clazz.getName(), s, methodType);
    }
}
