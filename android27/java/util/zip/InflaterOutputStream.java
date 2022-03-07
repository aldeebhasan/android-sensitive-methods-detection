package java.util.zip;

import java.io.*;

public class InflaterOutputStream extends FilterOutputStream
{
    protected final Inflater inf;
    protected final byte[] buf;
    private final byte[] wbuf;
    private boolean usesDefaultInflater;
    private boolean closed;
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
    
    public InflaterOutputStream(final OutputStream outputStream) {
        this(outputStream, new Inflater());
        this.usesDefaultInflater = true;
    }
    
    public InflaterOutputStream(final OutputStream outputStream, final Inflater inflater) {
        this(outputStream, inflater, 512);
    }
    
    public InflaterOutputStream(final OutputStream outputStream, final Inflater inf, final int n) {
        super(outputStream);
        this.wbuf = new byte[1];
        this.usesDefaultInflater = false;
        this.closed = false;
        if (outputStream == null) {
            throw new NullPointerException("Null output");
        }
        if (inf == null) {
            throw new NullPointerException("Null inflater");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("Buffer size < 1");
        }
        this.inf = inf;
        this.buf = new byte[n];
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            try {
                this.finish();
            }
            finally {
                this.out.close();
                this.closed = true;
            }
        }
    }
    
    @Override
    public void flush() throws IOException {
        this.ensureOpen();
        if (!this.inf.finished()) {
            try {
                while (!this.inf.finished() && !this.inf.needsInput()) {
                    final int inflate = this.inf.inflate(this.buf, 0, this.buf.length);
                    if (inflate < 1) {
                        break;
                    }
                    this.out.write(this.buf, 0, inflate);
                }
                super.flush();
            }
            catch (DataFormatException ex) {
                String message = ex.getMessage();
                if (message == null) {
                    message = "Invalid ZLIB data format";
                }
                throw new ZipException(message);
            }
        }
    }
    
    public void finish() throws IOException {
        this.ensureOpen();
        this.flush();
        if (this.usesDefaultInflater) {
            this.inf.end();
        }
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.wbuf[0] = (byte)n;
        this.write(this.wbuf, 0, 1);
    }
    
    @Override
    public void write(final byte[] array, int n, int n2) throws IOException {
        this.ensureOpen();
        if (array == null) {
            throw new NullPointerException("Null buffer for read");
        }
        if (n < 0 || n2 < 0 || n2 > array.length - n) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        Label_0047: {
            break Label_0047;
            try {
                while (true) {
                    if (this.inf.needsInput()) {
                        if (n2 < 1) {
                            break;
                        }
                        final int n3 = (n2 < 512) ? n2 : 512;
                        this.inf.setInput(array, n, n3);
                        n += n3;
                        n2 -= n3;
                    }
                    int i;
                    do {
                        i = this.inf.inflate(this.buf, 0, this.buf.length);
                        if (i > 0) {
                            this.out.write(this.buf, 0, i);
                        }
                    } while (i > 0);
                    if (this.inf.finished()) {
                        break;
                    }
                    if (this.inf.needsDictionary()) {
                        throw new ZipException("ZLIB dictionary missing");
                    }
                }
            }
            catch (DataFormatException ex) {
                String message = ex.getMessage();
                if (message == null) {
                    message = "Invalid ZLIB data format";
                }
                throw new ZipException(message);
            }
        }
    }
}
