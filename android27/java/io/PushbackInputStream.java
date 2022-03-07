package java.io;

public class PushbackInputStream extends FilterInputStream
{
    protected byte[] buf;
    protected int pos;
    
    private void ensureOpen() throws IOException {
        if (this.in == null) {
            throw new IOException("Stream closed");
        }
    }
    
    public PushbackInputStream(final InputStream inputStream, final int pos) {
        super(inputStream);
        if (pos <= 0) {
            throw new IllegalArgumentException("size <= 0");
        }
        this.buf = new byte[pos];
        this.pos = pos;
    }
    
    public PushbackInputStream(final InputStream inputStream) {
        this(inputStream, 1);
    }
    
    @Override
    public int read() throws IOException {
        this.ensureOpen();
        if (this.pos < this.buf.length) {
            return this.buf[this.pos++] & 0xFF;
        }
        return super.read();
    }
    
    @Override
    public int read(final byte[] array, int n, int read) throws IOException {
        this.ensureOpen();
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || read < 0 || read > array.length - n) {
            throw new IndexOutOfBoundsException();
        }
        if (read == 0) {
            return 0;
        }
        int n2 = this.buf.length - this.pos;
        if (n2 > 0) {
            if (read < n2) {
                n2 = read;
            }
            System.arraycopy(this.buf, this.pos, array, n, n2);
            this.pos += n2;
            n += n2;
            read -= n2;
        }
        if (read <= 0) {
            return n2;
        }
        read = super.read(array, n, read);
        if (read == -1) {
            return (n2 == 0) ? -1 : n2;
        }
        return n2 + read;
    }
    
    public void unread(final int n) throws IOException {
        this.ensureOpen();
        if (this.pos == 0) {
            throw new IOException("Push back buffer is full");
        }
        this.buf[--this.pos] = (byte)n;
    }
    
    public void unread(final byte[] array, final int n, final int n2) throws IOException {
        this.ensureOpen();
        if (n2 > this.pos) {
            throw new IOException("Push back buffer is full");
        }
        this.pos -= n2;
        System.arraycopy(array, n, this.buf, this.pos, n2);
    }
    
    public void unread(final byte[] array) throws IOException {
        this.unread(array, 0, array.length);
    }
    
    @Override
    public int available() throws IOException {
        this.ensureOpen();
        final int n = this.buf.length - this.pos;
        final int available = super.available();
        return (n > Integer.MAX_VALUE - available) ? Integer.MAX_VALUE : (n + available);
    }
    
    @Override
    public long skip(long n) throws IOException {
        this.ensureOpen();
        if (n <= 0L) {
            return 0L;
        }
        long n2 = this.buf.length - this.pos;
        if (n2 > 0L) {
            if (n < n2) {
                n2 = n;
            }
            this.pos += (int)n2;
            n -= n2;
        }
        if (n > 0L) {
            n2 += super.skip(n);
        }
        return n2;
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public synchronized void mark(final int n) {
    }
    
    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }
    
    @Override
    public synchronized void close() throws IOException {
        if (this.in == null) {
            return;
        }
        this.in.close();
        this.in = null;
        this.buf = null;
    }
}
