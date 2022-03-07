package java.io;

public class PushbackReader extends FilterReader
{
    private char[] buf;
    private int pos;
    
    public PushbackReader(final Reader reader, final int pos) {
        super(reader);
        if (pos <= 0) {
            throw new IllegalArgumentException("size <= 0");
        }
        this.buf = new char[pos];
        this.pos = pos;
    }
    
    public PushbackReader(final Reader reader) {
        this(reader, 1);
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
            if (this.pos < this.buf.length) {
                return this.buf[this.pos++];
            }
            return super.read();
        }
    }
    
    @Override
    public int read(final char[] array, int n, int read) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            try {
                if (read <= 0) {
                    if (read < 0) {
                        throw new IndexOutOfBoundsException();
                    }
                    if (n < 0 || n > array.length) {
                        throw new IndexOutOfBoundsException();
                    }
                    return 0;
                }
                else {
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
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                throw new IndexOutOfBoundsException();
            }
        }
    }
    
    public void unread(final int n) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (this.pos == 0) {
                throw new IOException("Pushback buffer overflow");
            }
            this.buf[--this.pos] = (char)n;
        }
    }
    
    public void unread(final char[] array, final int n, final int n2) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (n2 > this.pos) {
                throw new IOException("Pushback buffer overflow");
            }
            this.pos -= n2;
            System.arraycopy(array, n, this.buf, this.pos, n2);
        }
    }
    
    public void unread(final char[] array) throws IOException {
        this.unread(array, 0, array.length);
    }
    
    @Override
    public boolean ready() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            return this.pos < this.buf.length || super.ready();
        }
    }
    
    @Override
    public void mark(final int n) throws IOException {
        throw new IOException("mark/reset not supported");
    }
    
    @Override
    public void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        this.buf = null;
    }
    
    @Override
    public long skip(long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("skip value is negative");
        }
        synchronized (this.lock) {
            this.ensureOpen();
            final int n2 = this.buf.length - this.pos;
            if (n2 > 0) {
                if (n <= n2) {
                    this.pos += (int)n;
                    return n;
                }
                this.pos = this.buf.length;
                n -= n2;
            }
            return n2 + super.skip(n);
        }
    }
}
