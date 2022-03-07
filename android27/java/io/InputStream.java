package java.io;

public abstract class InputStream implements Closeable
{
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;
    
    public abstract int read() throws IOException;
    
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n2 > array.length - n) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        final int read = this.read();
        if (read == -1) {
            return -1;
        }
        array[n] = (byte)read;
        int i = 1;
        try {
            while (i < n2) {
                final int read2 = this.read();
                if (read2 == -1) {
                    break;
                }
                array[n + i] = (byte)read2;
                ++i;
            }
        }
        catch (IOException ex) {}
        return i;
    }
    
    public long skip(final long n) throws IOException {
        long n2 = n;
        if (n <= 0L) {
            return 0L;
        }
        final int n3 = (int)Math.min(2048L, n2);
        final byte[] array = new byte[n3];
        while (n2 > 0L) {
            final int read = this.read(array, 0, (int)Math.min(n3, n2));
            if (read < 0) {
                break;
            }
            n2 -= read;
        }
        return n - n2;
    }
    
    public int available() throws IOException {
        return 0;
    }
    
    @Override
    public void close() throws IOException {
    }
    
    public synchronized void mark(final int n) {
    }
    
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }
    
    public boolean markSupported() {
        return false;
    }
}
