package java.lang.reflect;

public final class Array
{
    public static Object newInstance(final Class<?> clazz, final int n) throws NegativeArraySizeException {
        return newArray(clazz, n);
    }
    
    public static Object newInstance(final Class<?> clazz, final int... array) throws IllegalArgumentException, NegativeArraySizeException {
        return multiNewArray(clazz, array);
    }
    
    public static native int getLength(final Object p0) throws IllegalArgumentException;
    
    public static native Object get(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native boolean getBoolean(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native byte getByte(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native char getChar(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native short getShort(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native int getInt(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native long getLong(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native float getFloat(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native double getDouble(final Object p0, final int p1) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void set(final Object p0, final int p1, final Object p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void setBoolean(final Object p0, final int p1, final boolean p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void setByte(final Object p0, final int p1, final byte p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void setChar(final Object p0, final int p1, final char p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void setShort(final Object p0, final int p1, final short p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void setInt(final Object p0, final int p1, final int p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void setLong(final Object p0, final int p1, final long p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void setFloat(final Object p0, final int p1, final float p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    public static native void setDouble(final Object p0, final int p1, final double p2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
    private static native Object newArray(final Class<?> p0, final int p1) throws NegativeArraySizeException;
    
    private static native Object multiNewArray(final Class<?> p0, final int[] p1) throws IllegalArgumentException, NegativeArraySizeException;
}
