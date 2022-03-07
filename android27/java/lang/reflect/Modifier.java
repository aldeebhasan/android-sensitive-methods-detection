package java.lang.reflect;

import java.security.*;
import sun.reflect.*;
import java.io.*;

public class Modifier
{
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int STATIC = 8;
    public static final int FINAL = 16;
    public static final int SYNCHRONIZED = 32;
    public static final int VOLATILE = 64;
    public static final int TRANSIENT = 128;
    public static final int NATIVE = 256;
    public static final int INTERFACE = 512;
    public static final int ABSTRACT = 1024;
    public static final int STRICT = 2048;
    static final int BRIDGE = 64;
    static final int VARARGS = 128;
    static final int SYNTHETIC = 4096;
    static final int ANNOTATION = 8192;
    static final int ENUM = 16384;
    static final int MANDATED = 32768;
    private static final int CLASS_MODIFIERS = 3103;
    private static final int INTERFACE_MODIFIERS = 3087;
    private static final int CONSTRUCTOR_MODIFIERS = 7;
    private static final int METHOD_MODIFIERS = 3391;
    private static final int FIELD_MODIFIERS = 223;
    private static final int PARAMETER_MODIFIERS = 16;
    static final int ACCESS_MODIFIERS = 7;
    
    public static boolean isPublic(final int n) {
        return (n & 0x1) != 0x0;
    }
    
    public static boolean isPrivate(final int n) {
        return (n & 0x2) != 0x0;
    }
    
    public static boolean isProtected(final int n) {
        return (n & 0x4) != 0x0;
    }
    
    public static boolean isStatic(final int n) {
        return (n & 0x8) != 0x0;
    }
    
    public static boolean isFinal(final int n) {
        return (n & 0x10) != 0x0;
    }
    
    public static boolean isSynchronized(final int n) {
        return (n & 0x20) != 0x0;
    }
    
    public static boolean isVolatile(final int n) {
        return (n & 0x40) != 0x0;
    }
    
    public static boolean isTransient(final int n) {
        return (n & 0x80) != 0x0;
    }
    
    public static boolean isNative(final int n) {
        return (n & 0x100) != 0x0;
    }
    
    public static boolean isInterface(final int n) {
        return (n & 0x200) != 0x0;
    }
    
    public static boolean isAbstract(final int n) {
        return (n & 0x400) != 0x0;
    }
    
    public static boolean isStrict(final int n) {
        return (n & 0x800) != 0x0;
    }
    
    public static String toString(final int n) {
        final StringBuilder sb = new StringBuilder();
        if ((n & 0x1) != 0x0) {
            sb.append("public ");
        }
        if ((n & 0x4) != 0x0) {
            sb.append("protected ");
        }
        if ((n & 0x2) != 0x0) {
            sb.append("private ");
        }
        if ((n & 0x400) != 0x0) {
            sb.append("abstract ");
        }
        if ((n & 0x8) != 0x0) {
            sb.append("static ");
        }
        if ((n & 0x10) != 0x0) {
            sb.append("final ");
        }
        if ((n & 0x80) != 0x0) {
            sb.append("transient ");
        }
        if ((n & 0x40) != 0x0) {
            sb.append("volatile ");
        }
        if ((n & 0x20) != 0x0) {
            sb.append("synchronized ");
        }
        if ((n & 0x100) != 0x0) {
            sb.append("native ");
        }
        if ((n & 0x800) != 0x0) {
            sb.append("strictfp ");
        }
        if ((n & 0x200) != 0x0) {
            sb.append("interface ");
        }
        final int length;
        if ((length = sb.length()) > 0) {
            return sb.toString().substring(0, length - 1);
        }
        return "";
    }
    
    static boolean isSynthetic(final int n) {
        return (n & 0x1000) != 0x0;
    }
    
    static boolean isMandated(final int n) {
        return (n & 0x8000) != 0x0;
    }
    
    public static int classModifiers() {
        return 3103;
    }
    
    public static int interfaceModifiers() {
        return 3087;
    }
    
    public static int constructorModifiers() {
        return 7;
    }
    
    public static int methodModifiers() {
        return 3391;
    }
    
    public static int fieldModifiers() {
        return 223;
    }
    
    public static int parameterModifiers() {
        return 16;
    }
    
    static {
        AccessController.doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction()).setLangReflectAccess(new ReflectAccess());
    }
}
