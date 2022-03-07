package java.util.zip;

import java.io.*;

public class GZIPInputStream extends InflaterInputStream
{
    protected CRC32 crc;
    protected boolean eos;
    private boolean closed;
    public static final int GZIP_MAGIC = 35615;
    private static final int FTEXT = 1;
    private static final int FHCRC = 2;
    private static final int FEXTRA = 4;
    private static final int FNAME = 8;
    private static final int FCOMMENT = 16;
    private byte[] tmpbuf;
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
    
    public GZIPInputStream(final InputStream inputStream, final int n) throws IOException {
        super(inputStream, new Inflater(true), n);
        this.crc = new CRC32();
        this.closed = false;
        this.tmpbuf = new byte[128];
        this.usesDefaultInflater = true;
        this.readHeader(inputStream);
    }
    
    public GZIPInputStream(final InputStream inputStream) throws IOException {
        this(inputStream, 512);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        this.ensureOpen();
        if (this.eos) {
            return -1;
        }
        final int read = super.read(array, n, n2);
        if (read == -1) {
            if (!this.readTrailer()) {
                return this.read(array, n, n2);
            }
            this.eos = true;
        }
        else {
            this.crc.update(array, n, read);
        }
        return read;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.eos = true;
            this.closed = true;
        }
    }
    
    private int readHeader(final InputStream inputStream) throws IOException {
        final CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, this.crc);
        this.crc.reset();
        if (this.readUShort(checkedInputStream) != 35615) {
            throw new ZipException("Not in GZIP format");
        }
        if (this.readUByte(checkedInputStream) != 8) {
            throw new ZipException("Unsupported compression method");
        }
        final int uByte = this.readUByte(checkedInputStream);
        this.skipBytes(checkedInputStream, 6);
        int n = 10;
        if ((uByte & 0x4) == 0x4) {
            final int uShort = this.readUShort(checkedInputStream);
            this.skipBytes(checkedInputStream, uShort);
            n += uShort + 2;
        }
        if ((uByte & 0x8) == 0x8) {
            do {
                ++n;
            } while (this.readUByte(checkedInputStream) != 0);
        }
        if ((uByte & 0x10) == 0x10) {
            do {
                ++n;
            } while (this.readUByte(checkedInputStream) != 0);
        }
        if ((uByte & 0x2) == 0x2) {
            if (this.readUShort(checkedInputStream) != ((int)this.crc.getValue() & 0xFFFF)) {
                throw new ZipException("Corrupt GZIP header");
            }
            n += 2;
        }
        this.crc.reset();
        return n;
    }
    
    private boolean readTrailer() throws IOException {
        InputStream in = this.in;
        final int remaining = this.inf.getRemaining();
        if (remaining > 0) {
            in = new SequenceInputStream(new ByteArrayInputStream(this.buf, this.len - remaining, remaining), new FilterInputStream(in) {
                @Override
                public void close() throws IOException {
                }
            });
        }
        if (this.readUInt(in) != this.crc.getValue() || this.readUInt(in) != (this.inf.getBytesWritten() & 0xFFFFFFFFL)) {
            throw new ZipException("Corrupt GZIP trailer");
        }
        if (this.in.available() > 0 || remaining > 26) {
            final int n = 8;
            int n2;
            try {
                n2 = n + this.readHeader(in);
            }
            catch (IOException ex) {
                return true;
            }
            this.inf.reset();
            if (remaining > n2) {
                this.inf.setInput(this.buf, this.len - remaining + n2, remaining - n2);
            }
            return false;
        }
        return true;
    }
    
    private long readUInt(final InputStream inputStream) throws IOException {
        return this.readUShort(inputStream) << 16 | this.readUShort(inputStream);
    }
    
    private int readUShort(final InputStream inputStream) throws IOException {
        return this.readUByte(inputStream) << 8 | this.readUByte(inputStream);
    }
    
    private int readUByte(final InputStream inputStream) throws IOException {
        final int read = inputStream.read();
        if (read == -1) {
            throw new EOFException();
        }
        if (read < -1 || read > 255) {
            throw new IOException(this.in.getClass().getName() + ".read() returned value out of range -1..255: " + read);
        }
        return read;
    }
    
    private void skipBytes(final InputStream inputStream, int i) throws IOException {
        while (i > 0) {
            final int read = inputStream.read(this.tmpbuf, 0, (i < this.tmpbuf.length) ? i : this.tmpbuf.length);
            if (read == -1) {
                throw new EOFException();
            }
            i -= read;
        }
    }
}
