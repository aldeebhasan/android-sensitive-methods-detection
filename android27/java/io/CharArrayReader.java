package java.io;

public class CharArrayReader extends Reader
{
    protected char[] buf;
    protected int pos;
    protected int markedPos;
    protected int count;
    
    public CharArrayReader(final char[] buf) {
        this.markedPos = 0;
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }
    
    public CharArrayReader(final char[] buf, final int n, final int n2) {
        this.markedPos = 0;
        if (n < 0 || n > buf.length || n2 < 0 || n + n2 < 0) {
            throw new IllegalArgumentException();
        }
        this.buf = buf;
        this.pos = n;
        this.count = Math.min(n + n2, buf.length);
        this.markedPos = n;
    }
    
    private void ensureOpen() throws IOException {
        if (this.buf == null) {
            throw new IOException("Stream closed");
        }
    }
    
    @Override
    public int read() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (this.pos >= this.count) {
                return -1;
            }
            return this.buf[this.pos++];
        }
    }
    
    @Override
    public int read(final char[] array, final int n, int n2) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return 0;
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
    }
    
    @Override
    public long skip(long n) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            final long n2 = this.count - this.pos;
            if (n > n2) {
                n = n2;
            }
            if (n < 0L) {
                return 0L;
            }
            this.pos += (int)n;
            return n;
        }
    }
    
    @Override
    public boolean ready() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            return this.count - this.pos > 0;
        }
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public void mark(final int n) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            this.markedPos = this.pos;
        }
    }
    
    @Override
    public void reset() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            this.pos = this.markedPos;
        }
    }
    
    @Override
    public void close() {
        this.buf = null;
    }
}
