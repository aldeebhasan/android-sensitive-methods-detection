package java.io;

public abstract static class GetField
{
    public abstract ObjectStreamClass getObjectStreamClass();
    
    public abstract boolean defaulted(final String p0) throws IOException;
    
    public abstract boolean get(final String p0, final boolean p1) throws IOException;
    
    public abstract byte get(final String p0, final byte p1) throws IOException;
    
    public abstract char get(final String p0, final char p1) throws IOException;
    
    public abstract short get(final String p0, final short p1) throws IOException;
    
    public abstract int get(final String p0, final int p1) throws IOException;
    
    public abstract long get(final String p0, final long p1) throws IOException;
    
    public abstract float get(final String p0, final float p1) throws IOException;
    
    public abstract double get(final String p0, final double p1) throws IOException;
    
    public abstract Object get(final String p0, final Object p1) throws IOException;
}
