package java.util.zip;

import java.nio.charset.*;
import java.io.*;

public class ZipInputStream extends InflaterInputStream implements ZipConstants
{
    private ZipEntry entry;
    private int flag;
    private CRC32 crc;
    private long remaining;
    private byte[] tmpbuf;
    private static final int STORED = 0;
    private static final int DEFLATED = 8;
    private boolean closed;
    private boolean entryEOF;
    private ZipCoder zc;
    private byte[] b;
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
    
    public ZipInputStream(final InputStream inputStream) {
        this(inputStream, StandardCharsets.UTF_8);
    }
    
    public ZipInputStream(final InputStream inputStream, final Charset charset) {
        super(new PushbackInputStream(inputStream, 512), new Inflater(true), 512);
        this.crc = new CRC32();
        this.tmpbuf = new byte[512];
        this.closed = false;
        this.entryEOF = false;
        this.b = new byte[256];
        this.usesDefaultInflater = true;
        if (inputStream == null) {
            throw new NullPointerException("in is null");
        }
        if (charset == null) {
            throw new NullPointerException("charset is null");
        }
        this.zc = ZipCoder.get(charset);
    }
    
    public ZipEntry getNextEntry() throws IOException {
        this.ensureOpen();
        if (this.entry != null) {
            this.closeEntry();
        }
        this.crc.reset();
        this.inf.reset();
        if ((this.entry = this.readLOC()) == null) {
            return null;
        }
        if (this.entry.method == 0) {
            this.remaining = this.entry.size;
        }
        this.entryEOF = false;
        return this.entry;
    }
    
    public void closeEntry() throws IOException {
        this.ensureOpen();
        while (this.read(this.tmpbuf, 0, this.tmpbuf.length) != -1) {}
        this.entryEOF = true;
    }
    
    @Override
    public int available() throws IOException {
        this.ensureOpen();
        if (this.entryEOF) {
            return 0;
        }
        return 1;
    }
    
    @Override
    public int read(final byte[] array, final int n, int n2) throws IOException {
        this.ensureOpen();
        if (n < 0 || n2 < 0 || n > array.length - n2) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        if (this.entry == null) {
            return -1;
        }
        switch (this.entry.method) {
            case 8: {
                n2 = super.read(array, n, n2);
                if (n2 == -1) {
                    this.readEnd(this.entry);
                    this.entryEOF = true;
                    this.entry = null;
                }
                else {
                    this.crc.update(array, n, n2);
                }
                return n2;
            }
            case 0: {
                if (this.remaining <= 0L) {
                    this.entryEOF = true;
                    this.entry = null;
                    return -1;
                }
                if (n2 > this.remaining) {
                    n2 = (int)this.remaining;
                }
                n2 = this.in.read(array, n, n2);
                if (n2 == -1) {
                    throw new ZipException("unexpected EOF");
                }
                this.crc.update(array, n, n2);
                this.remaining -= n2;
                if (this.remaining == 0L && this.entry.crc != this.crc.getValue()) {
                    throw new ZipException("invalid entry CRC (expected 0x" + Long.toHexString(this.entry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
                }
                return n2;
            }
            default: {
                throw new ZipException("invalid compression method");
            }
        }
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
            if (length > this.tmpbuf.length) {
                length = this.tmpbuf.length;
            }
            read = this.read(this.tmpbuf, 0, length);
            if (read == -1) {
                this.entryEOF = true;
                break;
            }
        }
        return i;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.closed = true;
        }
    }
    
    private ZipEntry readLOC() throws IOException {
        try {
            this.readFully(this.tmpbuf, 0, 30);
        }
        catch (EOFException ex) {
            return null;
        }
        if (ZipUtils.get32(this.tmpbuf, 0) != 67324752L) {
            return null;
        }
        this.flag = ZipUtils.get16(this.tmpbuf, 6);
        final int i = ZipUtils.get16(this.tmpbuf, 26);
        int length = this.b.length;
        if (i > length) {
            do {
                length *= 2;
            } while (i > length);
            this.b = new byte[length];
        }
        this.readFully(this.b, 0, i);
        final ZipEntry zipEntry = this.createZipEntry(((this.flag & 0x800) != 0x0) ? this.zc.toStringUTF8(this.b, i) : this.zc.toString(this.b, i));
        if ((this.flag & 0x1) == 0x1) {
            throw new ZipException("encrypted ZIP entry not supported");
        }
        zipEntry.method = ZipUtils.get16(this.tmpbuf, 8);
        zipEntry.xdostime = ZipUtils.get32(this.tmpbuf, 10);
        if ((this.flag & 0x8) == 0x8) {
            if (zipEntry.method != 8) {
                throw new ZipException("only DEFLATED entries can have EXT descriptor");
            }
        }
        else {
            zipEntry.crc = ZipUtils.get32(this.tmpbuf, 14);
            zipEntry.csize = ZipUtils.get32(this.tmpbuf, 18);
            zipEntry.size = ZipUtils.get32(this.tmpbuf, 22);
        }
        final int get16 = ZipUtils.get16(this.tmpbuf, 28);
        if (get16 > 0) {
            final byte[] array = new byte[get16];
            this.readFully(array, 0, get16);
            zipEntry.setExtra0(array, zipEntry.csize == 4294967295L || zipEntry.size == 4294967295L);
        }
        return zipEntry;
    }
    
    protected ZipEntry createZipEntry(final String s) {
        return new ZipEntry(s);
    }
    
    private void readEnd(final ZipEntry zipEntry) throws IOException {
        final int remaining = this.inf.getRemaining();
        if (remaining > 0) {
            ((PushbackInputStream)this.in).unread(this.buf, this.len - remaining, remaining);
        }
        if ((this.flag & 0x8) == 0x8) {
            if (this.inf.getBytesWritten() > 4294967295L || this.inf.getBytesRead() > 4294967295L) {
                this.readFully(this.tmpbuf, 0, 24);
                final long get32 = ZipUtils.get32(this.tmpbuf, 0);
                if (get32 != 134695760L) {
                    zipEntry.crc = get32;
                    zipEntry.csize = ZipUtils.get64(this.tmpbuf, 4);
                    zipEntry.size = ZipUtils.get64(this.tmpbuf, 12);
                    ((PushbackInputStream)this.in).unread(this.tmpbuf, 19, 4);
                }
                else {
                    zipEntry.crc = ZipUtils.get32(this.tmpbuf, 4);
                    zipEntry.csize = ZipUtils.get64(this.tmpbuf, 8);
                    zipEntry.size = ZipUtils.get64(this.tmpbuf, 16);
                }
            }
            else {
                this.readFully(this.tmpbuf, 0, 16);
                final long get33 = ZipUtils.get32(this.tmpbuf, 0);
                if (get33 != 134695760L) {
                    zipEntry.crc = get33;
                    zipEntry.csize = ZipUtils.get32(this.tmpbuf, 4);
                    zipEntry.size = ZipUtils.get32(this.tmpbuf, 8);
                    ((PushbackInputStream)this.in).unread(this.tmpbuf, 11, 4);
                }
                else {
                    zipEntry.crc = ZipUtils.get32(this.tmpbuf, 4);
                    zipEntry.csize = ZipUtils.get32(this.tmpbuf, 8);
                    zipEntry.size = ZipUtils.get32(this.tmpbuf, 12);
                }
            }
        }
        if (zipEntry.size != this.inf.getBytesWritten()) {
            throw new ZipException("invalid entry size (expected " + zipEntry.size + " but got " + this.inf.getBytesWritten() + " bytes)");
        }
        if (zipEntry.csize != this.inf.getBytesRead()) {
            throw new ZipException("invalid entry compressed size (expected " + zipEntry.csize + " but got " + this.inf.getBytesRead() + " bytes)");
        }
        if (zipEntry.crc != this.crc.getValue()) {
            throw new ZipException("invalid entry CRC (expected 0x" + Long.toHexString(zipEntry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
        }
    }
    
    private void readFully(final byte[] array, int n, int i) throws IOException {
        while (i > 0) {
            final int read = this.in.read(array, n, i);
            if (read == -1) {
                throw new EOFException();
            }
            n += read;
            i -= read;
        }
    }
}
