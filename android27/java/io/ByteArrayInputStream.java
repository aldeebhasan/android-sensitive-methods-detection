package java.io;

public class ByteArrayInputStream extends InputStream
{
    protected byte[] buf;
    protected int pos;
    protected int mark;
    protected int count;
    
    public ByteArrayInputStream(final byte[] buf) {
        this.mark = 0;
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }
    
    public ByteArrayInputStream(final byte[] buf, final int n, final int n2) {
        this.mark = 0;
        this.buf = buf;
        this.pos = n;
        this.count = Math.min(n + n2, buf.length);
        this.mark = n;
    }
    
    @Override
    public synchronized int read() {
        return (this.pos < this.count) ? (this.buf[this.pos++] & 0xFF) : -1;
    }
    
    @Override
    public synchronized int read(final byte[] array, final int n, int n2) {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n2 > array.length - n) {
            throw new IndexOutOfBoundsException();
        }
        if (this.pos >= this.count) {
            return -1;
        }
        final int n3 = this.count - this.pos;
        if (n2 > n3) {
            n2 = n3;
        }
        if (n2 <= 0) {
            return 0;
        }
        System.arraycopy(this.buf, this.pos, array, n, n2);
        this.pos += n2;
        return n2;
    }
    
    @Override
    public synchronized long skip(final long n) {
        long n2 = this.count - this.pos;
        if (n < n2) {
            n2 = ((n < 0L) ? 0L : n);
        }
        this.pos += (int)n2;
        return n2;
    }
    
    @Override
    public synchronized int available() {
        return this.count - this.pos;
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public void mark(final int n) {
        this.mark = this.pos;
    }
    
    @Override
    public synchronized void reset() {
        this.pos = this.mark;
    }
    
    @Override
    public void close() throws IOException {
    }
}
