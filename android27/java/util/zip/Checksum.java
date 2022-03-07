package java.util.zip;

public interface Checksum
{
    void update(final int p0);
    
    void update(final byte[] p0, final int p1, final int p2);
    
    long getValue();
    
    void reset();
}
