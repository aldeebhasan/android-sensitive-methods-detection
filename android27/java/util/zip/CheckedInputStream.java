package java.util.zip;

import java.io.*;

public class CheckedInputStream extends FilterInputStream
{
    private Checksum cksum;
    
    public CheckedInputStream(final InputStream inputStream, final Checksum cksum) {
        super(inputStream);
        this.cksum = cksum;
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.in.read();
        if (read != -1) {
            this.cksum.update(read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, int read) throws IOException {
        read = this.in.read(array, n, read);
        if (read != -1) {
            this.cksum.update(array, n, read);
        }
        return read;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        final byte[] array = new byte[512];
        long n2;
        long n4;
        for (n2 = 0L; n2 < n; n2 += n4) {
            final long n3 = n - n2;
            n4 = this.read(array, 0, (n3 < array.length) ? ((int)n3) : array.length);
            if (n4 == -1L) {
                return n2;
            }
        }
        return n2;
    }
    
    public Checksum getChecksum() {
        return this.cksum;
    }
}
