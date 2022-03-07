package java.util.zip;

import java.io.*;

public class InflaterInputStream extends FilterInputStream
{
    protected Inflater inf;
    protected byte[] buf;
    protected int len;
    private boolean closed;
    private boolean reachEOF;
    boolean usesDefaultInflater;
    private byte[] singleByteBuf;
    private byte[] b;
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
    
    public InflaterInputStream(final InputStream inputStream, final Inflater inf, final int n) {
        super(inputStream);
        this.closed = false;
        this.reachEOF = false;
        this.usesDefaultInflater = false;
        this.singleByteBuf = new byte[1];
        this.b = new byte[512];
        if (inputStream == null || inf == null) {
            throw new NullPointerException();
        }
        if (n <= 0) {
            throw new IllegalArgumentException("buffer size <= 0");
        }
        this.inf = inf;
        this.buf = new byte[n];
    }
    
    public InflaterInputStream(final InputStream inputStream, final Inflater inflater) {
        this(inputStream, inflater, 512);
    }
    
    public InflaterInputStream(final InputStream inputStream) {
        this(inputStream, new Inflater());
        this.usesDefaultInflater = true;
    }
    
    @Override
    public int read() throws IOException {
        this.ensureOpen();
        return (this.read(this.singleByteBuf, 0, 1) == -1) ? -1 : Byte.toUnsignedInt(this.singleByteBuf[0]);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        this.ensureOpen();
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n2 > array.length - n) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        try {
            int inflate;
            while ((inflate = this.inf.inflate(array, n, n2)) == 0) {
                if (this.inf.finished() || this.inf.needsDictionary()) {
                    this.reachEOF = true;
                    return -1;
                }
                if (!this.inf.needsInput()) {
                    continue;
                }
                this.fill();
            }
            return inflate;
        }
        catch (DataFormatException ex) {
            final String message = ex.getMessage();
            throw new ZipException((message != null) ? message : "Invalid ZLIB data format");
        }
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
    public long skip(final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("negative skip length");
        }
        this.ensureOpen();
        int n2;
        int i;
        int read;
        for (n2 = (int)Math.min(n, 2147483647L), i = 0; i < n2; i += read) {
            int length = n2 - i;
            if (length > this.b.length) {
                length = this.b.length;
            }
            read = this.read(this.b, 0, length);
            if (read == -1) {
                this.reachEOF = true;
                break;
            }
        }
        return i;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            if (this.usesDefaultInflater) {
                this.inf.end();
            }
            this.in.close();
            this.closed = true;
        }
    }
    
    protected void fill() throws IOException {
        this.ensureOpen();
        this.len = this.in.read(this.buf, 0, this.buf.length);
        if (this.len == -1) {
            throw new EOFException("Unexpected end of ZLIB input stream");
        }
        this.inf.setInput(this.buf, 0, this.len);
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
}
