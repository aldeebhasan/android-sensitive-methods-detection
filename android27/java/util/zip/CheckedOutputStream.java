package java.util.zip;

import java.io.*;

public class CheckedOutputStream extends FilterOutputStream
{
    private Checksum cksum;
    
    public CheckedOutputStream(final OutputStream outputStream, final Checksum cksum) {
        super(outputStream);
        this.cksum = cksum;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.out.write(n);
        this.cksum.update(n);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
        this.cksum.update(array, n, n2);
    }
    
    public Checksum getChecksum() {
        return this.cksum;
    }
}
