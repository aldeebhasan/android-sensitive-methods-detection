package java.io;

public abstract static class PutField
{
    public abstract void put(final String p0, final boolean p1);
    
    public abstract void put(final String p0, final byte p1);
    
    public abstract void put(final String p0, final char p1);
    
    public abstract void put(final String p0, final short p1);
    
    public abstract void put(final String p0, final int p1);
    
    public abstract void put(final String p0, final long p1);
    
    public abstract void put(final String p0, final float p1);
    
    public abstract void put(final String p0, final double p1);
    
    public abstract void put(final String p0, final Object p1);
    
    @Deprecated
    public abstract void write(final ObjectOutput p0) throws IOException;
}
