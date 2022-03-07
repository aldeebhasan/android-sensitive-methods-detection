package java.util.zip;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import sun.security.action.*;
import java.security.*;

public class ZipOutputStream extends DeflaterOutputStream implements ZipConstants
{
    private static final boolean inhibitZip64;
    private XEntry current;
    private Vector<XEntry> xentries;
    private HashSet<String> names;
    private CRC32 crc;
    private long written;
    private long locoff;
    private byte[] comment;
    private int method;
    private boolean finished;
    private boolean closed;
    private final ZipCoder zc;
    public static final int STORED = 0;
    public static final int DEFLATED = 8;
    
    private static int version(final ZipEntry zipEntry) throws ZipException {
        switch (zipEntry.method) {
            case 8: {
                return 20;
            }
            case 0: {
                return 10;
            }
            default: {
                throw new ZipException("unsupported compression method");
            }
        }
    }
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
    
    public ZipOutputStream(final OutputStream outputStream) {
        this(outputStream, StandardCharsets.UTF_8);
    }
    
    public ZipOutputStream(final OutputStream outputStream, final Charset charset) {
        super(outputStream, new Deflater(-1, true));
        this.xentries = new Vector<XEntry>();
        this.names = new HashSet<String>();
        this.crc = new CRC32();
        this.written = 0L;
        this.locoff = 0L;
        this.method = 8;
        this.closed = false;
        if (charset == null) {
            throw new NullPointerException("charset is null");
        }
        this.zc = ZipCoder.get(charset);
        this.usesDefaultDeflater = true;
    }
    
    public void setComment(final String s) {
        if (s != null) {
            this.comment = this.zc.getBytes(s);
            if (this.comment.length > 65535) {
                throw new IllegalArgumentException("ZIP file comment too long.");
            }
        }
    }
    
    public void setMethod(final int method) {
        if (method != 8 && method != 0) {
            throw new IllegalArgumentException("invalid compression method");
        }
        this.method = method;
    }
    
    public void setLevel(final int level) {
        this.def.setLevel(level);
    }
    
    public void putNextEntry(final ZipEntry zipEntry) throws IOException {
        this.ensureOpen();
        if (this.current != null) {
            this.closeEntry();
        }
        if (zipEntry.xdostime == -1L) {
            zipEntry.setTime(System.currentTimeMillis());
        }
        if (zipEntry.method == -1) {
            zipEntry.method = this.method;
        }
        zipEntry.flag = 0;
        switch (zipEntry.method) {
            case 8: {
                if (zipEntry.size == -1L || zipEntry.csize == -1L || zipEntry.crc == -1L) {
                    zipEntry.flag = 8;
                    break;
                }
                break;
            }
            case 0: {
                if (zipEntry.size == -1L) {
                    zipEntry.size = zipEntry.csize;
                }
                else if (zipEntry.csize == -1L) {
                    zipEntry.csize = zipEntry.size;
                }
                else if (zipEntry.size != zipEntry.csize) {
                    throw new ZipException("STORED entry where compressed != uncompressed size");
                }
                if (zipEntry.size == -1L || zipEntry.crc == -1L) {
                    throw new ZipException("STORED entry missing size, compressed size, or crc-32");
                }
                break;
            }
            default: {
                throw new ZipException("unsupported compression method");
            }
        }
        if (!this.names.add(zipEntry.name)) {
            throw new ZipException("duplicate entry: " + zipEntry.name);
        }
        if (this.zc.isUTF8()) {
            zipEntry.flag |= 0x800;
        }
        this.current = new XEntry(zipEntry, this.written);
        this.xentries.add(this.current);
        this.writeLOC(this.current);
    }
    
    public void closeEntry() throws IOException {
        this.ensureOpen();
        if (this.current != null) {
            final ZipEntry entry = this.current.entry;
            switch (entry.method) {
                case 8: {
                    this.def.finish();
                    while (!this.def.finished()) {
                        this.deflate();
                    }
                    if ((entry.flag & 0x8) == 0x0) {
                        if (entry.size != this.def.getBytesRead()) {
                            throw new ZipException("invalid entry size (expected " + entry.size + " but got " + this.def.getBytesRead() + " bytes)");
                        }
                        if (entry.csize != this.def.getBytesWritten()) {
                            throw new ZipException("invalid entry compressed size (expected " + entry.csize + " but got " + this.def.getBytesWritten() + " bytes)");
                        }
                        if (entry.crc != this.crc.getValue()) {
                            throw new ZipException("invalid entry CRC-32 (expected 0x" + Long.toHexString(entry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
                        }
                    }
                    else {
                        entry.size = this.def.getBytesRead();
                        entry.csize = this.def.getBytesWritten();
                        entry.crc = this.crc.getValue();
                        this.writeEXT(entry);
                    }
                    this.def.reset();
                    this.written += entry.csize;
                    break;
                }
                case 0: {
                    if (entry.size != this.written - this.locoff) {
                        throw new ZipException("invalid entry size (expected " + entry.size + " but got " + (this.written - this.locoff) + " bytes)");
                    }
                    if (entry.crc != this.crc.getValue()) {
                        throw new ZipException("invalid entry crc-32 (expected 0x" + Long.toHexString(entry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
                    }
                    break;
                }
                default: {
                    throw new ZipException("invalid compression method");
                }
            }
            this.crc.reset();
            this.current = null;
        }
    }
    
    @Override
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        this.ensureOpen();
        if (n < 0 || n2 < 0 || n > array.length - n2) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        if (this.current == null) {
            throw new ZipException("no current ZIP entry");
        }
        final ZipEntry entry = this.current.entry;
        switch (entry.method) {
            case 8: {
                super.write(array, n, n2);
                break;
            }
            case 0: {
                this.written += n2;
                if (this.written - this.locoff > entry.size) {
                    throw new ZipException("attempt to write past end of STORED entry");
                }
                this.out.write(array, n, n2);
                break;
            }
            default: {
                throw new ZipException("invalid compression method");
            }
        }
        this.crc.update(array, n, n2);
    }
    
    @Override
    public void finish() throws IOException {
        this.ensureOpen();
        if (this.finished) {
            return;
        }
        if (this.current != null) {
            this.closeEntry();
        }
        final long written = this.written;
        final Iterator<XEntry> iterator = this.xentries.iterator();
        while (iterator.hasNext()) {
            this.writeCEN(iterator.next());
        }
        this.writeEND(written, this.written - written);
        this.finished = true;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.closed = true;
        }
    }
    
    private void writeLOC(final XEntry xEntry) throws IOException {
        final ZipEntry entry = xEntry.entry;
        final int flag = entry.flag;
        boolean b = false;
        int extraLen = this.getExtraLen(entry.extra);
        this.writeInt(67324752L);
        if ((flag & 0x8) == 0x8) {
            this.writeShort(version(entry));
            this.writeShort(flag);
            this.writeShort(entry.method);
            this.writeInt(entry.xdostime);
            this.writeInt(0L);
            this.writeInt(0L);
            this.writeInt(0L);
        }
        else {
            if (entry.csize >= 4294967295L || entry.size >= 4294967295L) {
                b = true;
                this.writeShort(45);
            }
            else {
                this.writeShort(version(entry));
            }
            this.writeShort(flag);
            this.writeShort(entry.method);
            this.writeInt(entry.xdostime);
            this.writeInt(entry.crc);
            if (b) {
                this.writeInt(4294967295L);
                this.writeInt(4294967295L);
                extraLen += 20;
            }
            else {
                this.writeInt(entry.csize);
                this.writeInt(entry.size);
            }
        }
        final byte[] bytes = this.zc.getBytes(entry.name);
        this.writeShort(bytes.length);
        int n = 0;
        int n2 = 0;
        if (entry.mtime != null) {
            n += 4;
            n2 |= 0x1;
        }
        if (entry.atime != null) {
            n += 4;
            n2 |= 0x2;
        }
        if (entry.ctime != null) {
            n += 4;
            n2 |= 0x4;
        }
        if (n2 != 0) {
            extraLen += n + 5;
        }
        this.writeShort(extraLen);
        this.writeBytes(bytes, 0, bytes.length);
        if (b) {
            this.writeShort(1);
            this.writeShort(16);
            this.writeLong(entry.size);
            this.writeLong(entry.csize);
        }
        if (n2 != 0) {
            this.writeShort(21589);
            this.writeShort(n + 1);
            this.writeByte(n2);
            if (entry.mtime != null) {
                this.writeInt(ZipUtils.fileTimeToUnixTime(entry.mtime));
            }
            if (entry.atime != null) {
                this.writeInt(ZipUtils.fileTimeToUnixTime(entry.atime));
            }
            if (entry.ctime != null) {
                this.writeInt(ZipUtils.fileTimeToUnixTime(entry.ctime));
            }
        }
        this.writeExtra(entry.extra);
        this.locoff = this.written;
    }
    
    private void writeEXT(final ZipEntry zipEntry) throws IOException {
        this.writeInt(134695760L);
        this.writeInt(zipEntry.crc);
        if (zipEntry.csize >= 4294967295L || zipEntry.size >= 4294967295L) {
            this.writeLong(zipEntry.csize);
            this.writeLong(zipEntry.size);
        }
        else {
            this.writeInt(zipEntry.csize);
            this.writeInt(zipEntry.size);
        }
    }
    
    private void writeCEN(final XEntry xEntry) throws IOException {
        final ZipEntry entry = xEntry.entry;
        final int flag = entry.flag;
        final int version = version(entry);
        long csize = entry.csize;
        long size = entry.size;
        long offset = xEntry.offset;
        int n = 0;
        boolean b = false;
        if (entry.csize >= 4294967295L) {
            csize = 4294967295L;
            n += 8;
            b = true;
        }
        if (entry.size >= 4294967295L) {
            size = 4294967295L;
            n += 8;
            b = true;
        }
        if (xEntry.offset >= 4294967295L) {
            offset = 4294967295L;
            n += 8;
            b = true;
        }
        this.writeInt(33639248L);
        if (b) {
            this.writeShort(45);
            this.writeShort(45);
        }
        else {
            this.writeShort(version);
            this.writeShort(version);
        }
        this.writeShort(flag);
        this.writeShort(entry.method);
        this.writeInt(entry.xdostime);
        this.writeInt(entry.crc);
        this.writeInt(csize);
        this.writeInt(size);
        final byte[] bytes = this.zc.getBytes(entry.name);
        this.writeShort(bytes.length);
        int extraLen = this.getExtraLen(entry.extra);
        if (b) {
            extraLen += n + 4;
        }
        int n2 = 0;
        if (entry.mtime != null) {
            extraLen += 4;
            n2 |= 0x1;
        }
        if (entry.atime != null) {
            n2 |= 0x2;
        }
        if (entry.ctime != null) {
            n2 |= 0x4;
        }
        if (n2 != 0) {
            extraLen += 5;
        }
        this.writeShort(extraLen);
        byte[] bytes2;
        if (entry.comment != null) {
            bytes2 = this.zc.getBytes(entry.comment);
            this.writeShort(Math.min(bytes2.length, 65535));
        }
        else {
            bytes2 = null;
            this.writeShort(0);
        }
        this.writeShort(0);
        this.writeShort(0);
        this.writeInt(0L);
        this.writeInt(offset);
        this.writeBytes(bytes, 0, bytes.length);
        if (b) {
            this.writeShort(1);
            this.writeShort(n);
            if (size == 4294967295L) {
                this.writeLong(entry.size);
            }
            if (csize == 4294967295L) {
                this.writeLong(entry.csize);
            }
            if (offset == 4294967295L) {
                this.writeLong(xEntry.offset);
            }
        }
        if (n2 != 0) {
            this.writeShort(21589);
            if (entry.mtime != null) {
                this.writeShort(5);
                this.writeByte(n2);
                this.writeInt(ZipUtils.fileTimeToUnixTime(entry.mtime));
            }
            else {
                this.writeShort(1);
                this.writeByte(n2);
            }
        }
        this.writeExtra(entry.extra);
        if (bytes2 != null) {
            this.writeBytes(bytes2, 0, Math.min(bytes2.length, 65535));
        }
    }
    
    private void writeEND(final long n, final long n2) throws IOException {
        boolean b = false;
        long n3 = n2;
        long n4 = n;
        if (n3 >= 4294967295L) {
            n3 = 4294967295L;
            b = true;
        }
        if (n4 >= 4294967295L) {
            n4 = 4294967295L;
            b = true;
        }
        int size = this.xentries.size();
        if (size >= 65535) {
            b |= !ZipOutputStream.inhibitZip64;
            if (b) {
                size = 65535;
            }
        }
        if (b) {
            final long written = this.written;
            this.writeInt(101075792L);
            this.writeLong(44L);
            this.writeShort(45);
            this.writeShort(45);
            this.writeInt(0L);
            this.writeInt(0L);
            this.writeLong(this.xentries.size());
            this.writeLong(this.xentries.size());
            this.writeLong(n2);
            this.writeLong(n);
            this.writeInt(117853008L);
            this.writeInt(0L);
            this.writeLong(written);
            this.writeInt(1L);
        }
        this.writeInt(101010256L);
        this.writeShort(0);
        this.writeShort(0);
        this.writeShort(size);
        this.writeShort(size);
        this.writeInt(n3);
        this.writeInt(n4);
        if (this.comment != null) {
            this.writeShort(this.comment.length);
            this.writeBytes(this.comment, 0, this.comment.length);
        }
        else {
            this.writeShort(0);
        }
    }
    
    private int getExtraLen(final byte[] array) {
        if (array == null) {
            return 0;
        }
        int n = 0;
        final int length = array.length;
        int get17;
        for (int n2 = 0; n2 + 4 <= length; n2 += get17 + 4) {
            final int get16 = ZipUtils.get16(array, n2);
            get17 = ZipUtils.get16(array, n2 + 2);
            if (get17 < 0) {
                break;
            }
            if (n2 + 4 + get17 > length) {
                break;
            }
            if (get16 == 21589 || get16 == 1) {
                n += get17 + 4;
            }
        }
        return length - n;
    }
    
    private void writeExtra(final byte[] array) throws IOException {
        if (array != null) {
            int length;
            int n;
            int get17;
            for (length = array.length, n = 0; n + 4 <= length; n += get17 + 4) {
                final int get16 = ZipUtils.get16(array, n);
                get17 = ZipUtils.get16(array, n + 2);
                if (get17 < 0 || n + 4 + get17 > length) {
                    this.writeBytes(array, n, length - n);
                    return;
                }
                if (get16 != 21589 && get16 != 1) {
                    this.writeBytes(array, n, get17 + 4);
                }
            }
            if (n < length) {
                this.writeBytes(array, n, length - n);
            }
        }
    }
    
    private void writeByte(final int n) throws IOException {
        this.out.write(n & 0xFF);
        ++this.written;
    }
    
    private void writeShort(final int n) throws IOException {
        final OutputStream out = this.out;
        out.write(n >>> 0 & 0xFF);
        out.write(n >>> 8 & 0xFF);
        this.written += 2L;
    }
    
    private void writeInt(final long n) throws IOException {
        final OutputStream out = this.out;
        out.write((int)(n >>> 0 & 0xFFL));
        out.write((int)(n >>> 8 & 0xFFL));
        out.write((int)(n >>> 16 & 0xFFL));
        out.write((int)(n >>> 24 & 0xFFL));
        this.written += 4L;
    }
    
    private void writeLong(final long n) throws IOException {
        final OutputStream out = this.out;
        out.write((int)(n >>> 0 & 0xFFL));
        out.write((int)(n >>> 8 & 0xFFL));
        out.write((int)(n >>> 16 & 0xFFL));
        out.write((int)(n >>> 24 & 0xFFL));
        out.write((int)(n >>> 32 & 0xFFL));
        out.write((int)(n >>> 40 & 0xFFL));
        out.write((int)(n >>> 48 & 0xFFL));
        out.write((int)(n >>> 56 & 0xFFL));
        this.written += 8L;
    }
    
    private void writeBytes(final byte[] array, final int n, final int n2) throws IOException {
        super.out.write(array, n, n2);
        this.written += n2;
    }
    
    static {
        inhibitZip64 = Boolean.parseBoolean(AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("jdk.util.zip.inhibitZip64", "false")));
    }
    
    private static class XEntry
    {
        final ZipEntry entry;
        final long offset;
        
        public XEntry(final ZipEntry entry, final long offset) {
            this.entry = entry;
            this.offset = offset;
        }
    }
}
