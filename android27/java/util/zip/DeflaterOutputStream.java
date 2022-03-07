package java.util.zip;

import java.io.*;

public class DeflaterOutputStream extends FilterOutputStream
{
    protected Deflater def;
    protected byte[] buf;
    private boolean closed;
    private final boolean syncFlush;
    boolean usesDefaultDeflater;
    
    public DeflaterOutputStream(final OutputStream outputStream, final Deflater def, final int n, final boolean syncFlush) {
        super(outputStream);
        this.closed = false;
        this.usesDefaultDeflater = false;
        if (outputStream == null || def == null) {
            throw new NullPointerException();
        }
        if (n <= 0) {
            throw new IllegalArgumentException("buffer size <= 0");
        }
        this.def = def;
        this.buf = new byte[n];
        this.syncFlush = syncFlush;
    }
    
    public DeflaterOutputStream(final OutputStream outputStream, final Deflater deflater, final int n) {
        this(outputStream, deflater, n, false);
    }
    
    public DeflaterOutputStream(final OutputStream outputStream, final Deflater deflater, final boolean b) {
        this(outputStream, deflater, 512, b);
    }
    
    public DeflaterOutputStream(final OutputStream outputStream, final Deflater deflater) {
        this(outputStream, deflater, 512, false);
    }
    
    public DeflaterOutputStream(final OutputStream outputStream, final boolean b) {
        this(outputStream, new Deflater(), 512, b);
        this.usesDefaultDeflater = true;
    }
    
    public DeflaterOutputStream(final OutputStream outputStream) {
        this(outputStream, false);
        this.usesDefaultDeflater = true;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.write(new byte[] { (byte)(n & 0xFF) }, 0, 1);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (this.def.finished()) {
            throw new IOException("write beyond end of stream");
        }
        if ((n | n2 | n + n2 | array.length - (n + n2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        if (!this.def.finished()) {
            this.def.setInput(array, n, n2);
            while (!this.def.needsInput()) {
                this.deflate();
            }
        }
    }
    
    public void finish() throws IOException {
        if (!this.def.finished()) {
            this.def.finish();
            while (!this.def.finished()) {
                this.deflate();
            }
        }
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.finish();
            if (this.usesDefaultDeflater) {
                this.def.end();
            }
            this.out.close();
            this.closed = true;
        }
    }
    
    protected void deflate() throws IOException {
        final int deflate = this.def.deflate(this.buf, 0, this.buf.length);
        if (deflate > 0) {
            this.out.write(this.buf, 0, deflate);
        }
    }
    
    @Override
    public void flush() throws IOException {
        if (this.syncFlush && !this.def.finished()) {
            int deflate;
            while ((deflate = this.def.deflate(this.buf, 0, this.buf.length, 2)) > 0) {
                this.out.write(this.buf, 0, deflate);
                if (deflate < this.buf.length) {
                    break;
                }
            }
        }
        this.out.flush();
    }
}
