package java.util.zip;

import java.io.*;

public class GZIPOutputStream extends DeflaterOutputStream
{
    protected CRC32 crc;
    private static final int GZIP_MAGIC = 35615;
    private static final int TRAILER_SIZE = 8;
    
    public GZIPOutputStream(final OutputStream outputStream, final int n) throws IOException {
        this(outputStream, n, false);
    }
    
    public GZIPOutputStream(final OutputStream outputStream, final int n, final boolean b) throws IOException {
        super(outputStream, new Deflater(-1, true), n, b);
        this.crc = new CRC32();
        this.usesDefaultDeflater = true;
        this.writeHeader();
        this.crc.reset();
    }
    
    public GZIPOutputStream(final OutputStream outputStream) throws IOException {
        this(outputStream, 512, false);
    }
    
    public GZIPOutputStream(final OutputStream outputStream, final boolean b) throws IOException {
        this(outputStream, 512, b);
    }
    
    @Override
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        super.write(array, n, n2);
        this.crc.update(array, n, n2);
    }
    
    @Override
    public void finish() throws IOException {
        if (!this.def.finished()) {
            this.def.finish();
            while (!this.def.finished()) {
                final int deflate = this.def.deflate(this.buf, 0, this.buf.length);
                if (this.def.finished() && deflate <= this.buf.length - 8) {
                    this.writeTrailer(this.buf, deflate);
                    this.out.write(this.buf, 0, deflate + 8);
                    return;
                }
                if (deflate <= 0) {
                    continue;
                }
                this.out.write(this.buf, 0, deflate);
            }
            final byte[] array = new byte[8];
            this.writeTrailer(array, 0);
            this.out.write(array);
        }
    }
    
    private void writeHeader() throws IOException {
        this.out.write(new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 });
    }
    
    private void writeTrailer(final byte[] array, final int n) throws IOException {
        this.writeInt((int)this.crc.getValue(), array, n);
        this.writeInt(this.def.getTotalIn(), array, n + 4);
    }
    
    private void writeInt(final int n, final byte[] array, final int n2) throws IOException {
        this.writeShort(n & 0xFFFF, array, n2);
        this.writeShort(n >> 16 & 0xFFFF, array, n2 + 2);
    }
    
    private void writeShort(final int n, final byte[] array, final int n2) throws IOException {
        array[n2] = (byte)(n & 0xFF);
        array[n2 + 1] = (byte)(n >> 8 & 0xFF);
    }
}
