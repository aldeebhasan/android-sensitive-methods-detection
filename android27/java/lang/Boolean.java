package java.lang;

import java.io.*;

public final class Boolean implements Serializable, Comparable<Boolean>
{
    public static final Boolean TRUE;
    public static final Boolean FALSE;
    public static final Class<Boolean> TYPE;
    private final boolean value;
    private static final long serialVersionUID = -3665804199014368530L;
    
    public Boolean(final boolean value) {
        this.value = value;
    }
    
    public Boolean(final String s) {
        this(parseBoolean(s));
    }
    
    public static boolean parseBoolean(final String s) {
        return s != null && s.equalsIgnoreCase("true");
    }
    
    public boolean booleanValue() {
        return this.value;
    }
    
    public static Boolean valueOf(final boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }
    
    public static Boolean valueOf(final String s) {
        return parseBoolean(s) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    public static String toString(final boolean b) {
        return b ? "true" : "false";
    }
    
    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }
    
    @Override
    public int hashCode() {
        return hashCode(this.value);
    }
    
    public static int hashCode(final boolean b) {
        return b ? 1231 : 1237;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Boolean && this.value == (boolean)o;
    }
    
    public static boolean getBoolean(final String s) {
        boolean boolean1 = false;
        try {
            boolean1 = parseBoolean(System.getProperty(s));
        }
        catch (IllegalArgumentException ex) {}
        catch (NullPointerException ex2) {}
        return boolean1;
    }
    
    @Override
    public int compareTo(final Boolean b) {
        return compare(this.value, b.value);
    }
    
    public static int compare(final boolean b, final boolean b2) {
        return (b == b2) ? 0 : (b ? 1 : -1);
    }
    
    public static boolean logicalAnd(final boolean b, final boolean b2) {
        return b && b2;
    }
    
    public static boolean logicalOr(final boolean b, final boolean b2) {
        return b || b2;
    }
    
    public static boolean logicalXor(final boolean b, final boolean b2) {
        return b ^ b2;
    }
    
    static {
        TRUE = new Boolean(true);
        FALSE = new Boolean(false);
        TYPE = Class.getPrimitiveClass("boolean");
    }
}
