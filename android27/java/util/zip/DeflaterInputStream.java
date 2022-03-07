package java.util.zip;

import java.io.*;

public class DeflaterInputStream extends FilterInputStream
{
    protected final Deflater def;
    protected final byte[] buf;
    private byte[] rbuf;
    private boolean usesDefaultDeflater;
    private boolean reachEOF;
    
    private void ensureOpen() throws IOException {
        if (this.in == null) {
            throw new IOException("Stream closed");
        }
    }
    
    public DeflaterInputStream(final InputStream inputStream) {
        this(inputStream, new Deflater());
        this.usesDefaultDeflater = true;
    }
    
    public DeflaterInputStream(final InputStream inputStream, final Deflater deflater) {
        this(inputStream, deflater, 512);
    }
    
    public DeflaterInputStream(final InputStream inputStream, final Deflater def, final int n) {
        super(inputStream);
        this.rbuf = new byte[1];
        this.usesDefaultDeflater = false;
        this.reachEOF = false;
        if (inputStream == null) {
            throw new NullPointerException("Null input");
        }
        if (def == null) {
            throw new NullPointerException("Null deflater");
        }
        if (n < 1) {
            throw new IllegalArgumentException("Buffer size < 1");
        }
        this.def = def;
        this.buf = new byte[n];
    }
    
    @Override
    public void close() throws IOException {
        if (this.in != null) {
            try {
                if (this.usesDefaultDeflater) {
                    this.def.end();
                }
                this.in.close();
            }
            finally {
                this.in = null;
            }
        }
    }
    
    @Override
    public int read() throws IOException {
        if (this.read(this.rbuf, 0, 1) <= 0) {
            return -1;
        }
        return this.rbuf[0] & 0xFF;
    }
    
    @Override
    public int read(final byte[] array, int n, int n2) throws IOException {
        this.ensureOpen();
        if (array == null) {
            throw new NullPointerException("Null buffer for read");
        }
        if (n < 0 || n2 < 0 || n2 > array.length - n) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        int n3 = 0;
        while (n2 > 0 && !this.def.finished()) {
            if (this.def.needsInput()) {
                final int read = this.in.read(this.buf, 0, this.buf.length);
                if (read < 0) {
                    this.def.finish();
                }
                else if (read > 0) {
                    this.def.setInput(this.buf, 0, read);
                }
            }
            final int deflate = this.def.deflate(array, n, n2);
            n3 += deflate;
            n += deflate;
            n2 -= deflate;
        }
        if (n3 == 0 && this.def.finished()) {
            this.reachEOF = true;
            n3 = -1;
        }
        return n3;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("negative skip length");
        }
        this.ensureOpen();
        if (this.rbuf.length < 512) {
            this.rbuf = new byte[512];
        }
        int i = (int)Math.min(n, 2147483647L);
        long n2 = 0L;
        while (i > 0) {
            final int read = this.read(this.rbuf, 0, (i <= this.rbuf.length) ? i : this.rbuf.length);
            if (read < 0) {
                break;
            }
            n2 += read;
            i -= read;
        }
        return n2;
    }
    
    @Override
    public int available() throws IOException {
        this.ensureOpen();
        if (this.reachEOF) {
            return 0;
        }
        return 1;
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public void mark(final int n) {
    }
    
    @Override
    public void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }
}
