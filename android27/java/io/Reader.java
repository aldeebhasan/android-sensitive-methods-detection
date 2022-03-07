package java.io;

import java.nio.*;

public abstract class Reader implements Readable, Closeable
{
    protected Object lock;
    private static final int maxSkipBufferSize = 8192;
    private char[] skipBuffer;
    
    protected Reader() {
        this.skipBuffer = null;
        this.lock = this;
    }
    
    protected Reader(final Object lock) {
        this.skipBuffer = null;
        if (lock == null) {
            throw new NullPointerException();
        }
        this.lock = lock;
    }
    
    @Override
    public int read(final CharBuffer charBuffer) throws IOException {
        final int remaining = charBuffer.remaining();
        final char[] array = new char[remaining];
        final int read = this.read(array, 0, remaining);
        if (read > 0) {
            charBuffer.put(array, 0, read);
        }
        return read;
    }
    
    public int read() throws IOException {
        final char[] array = { '\0' };
        if (this.read(array, 0, 1) == -1) {
            return -1;
        }
        return array[0];
    }
    
    public int read(final char[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    public abstract int read(final char[] p0, final int p1, final int p2) throws IOException;
    
    public long skip(final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("skip value is negative");
        }
        final int n2 = (int)Math.min(n, 8192L);
        synchronized (this.lock) {
            if (this.skipBuffer == null || this.skipBuffer.length < n2) {
                this.skipBuffer = new char[n2];
            }
            long n3;
            int read;
            for (n3 = n; n3 > 0L; n3 -= read) {
                read = this.read(this.skipBuffer, 0, (int)Math.min(n3, n2));
                if (read == -1) {
                    break;
                }
            }
            return n - n3;
        }
    }
    
    public boolean ready() throws IOException {
        return false;
    }
    
    public boolean markSupported() {
        return false;
    }
    
    public void mark(final int n) throws IOException {
        throw new IOException("mark() not supported");
    }
    
    public void reset() throws IOException {
        throw new IOException("reset() not supported");
    }
    
    @Override
    public abstract void close() throws IOException;
}
