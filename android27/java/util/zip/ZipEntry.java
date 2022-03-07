package java.util.zip;

import java.nio.file.attribute.*;
import java.util.*;
import java.util.concurrent.*;

public class ZipEntry implements ZipConstants, Cloneable
{
    String name;
    long xdostime;
    FileTime mtime;
    FileTime atime;
    FileTime ctime;
    long crc;
    long size;
    long csize;
    int method;
    int flag;
    byte[] extra;
    String comment;
    public static final int STORED = 0;
    public static final int DEFLATED = 8;
    static final long DOSTIME_BEFORE_1980 = 2162688L;
    private static final long UPPER_DOSTIME_BOUND = 4036608000000L;
    
    public ZipEntry(final String name) {
        this.xdostime = -1L;
        this.crc = -1L;
        this.size = -1L;
        this.csize = -1L;
        this.method = -1;
        this.flag = 0;
        Objects.requireNonNull(name, "name");
        if (name.length() > 65535) {
            throw new IllegalArgumentException("entry name too long");
        }
        this.name = name;
    }
    
    public ZipEntry(final ZipEntry zipEntry) {
        this.xdostime = -1L;
        this.crc = -1L;
        this.size = -1L;
        this.csize = -1L;
        this.method = -1;
        this.flag = 0;
        Objects.requireNonNull(zipEntry, "entry");
        this.name = zipEntry.name;
        this.xdostime = zipEntry.xdostime;
        this.mtime = zipEntry.mtime;
        this.atime = zipEntry.atime;
        this.ctime = zipEntry.ctime;
        this.crc = zipEntry.crc;
        this.size = zipEntry.size;
        this.csize = zipEntry.csize;
        this.method = zipEntry.method;
        this.flag = zipEntry.flag;
        this.extra = zipEntry.extra;
        this.comment = zipEntry.comment;
    }
    
    ZipEntry() {
        this.xdostime = -1L;
        this.crc = -1L;
        this.size = -1L;
        this.csize = -1L;
        this.method = -1;
        this.flag = 0;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setTime(final long n) {
        this.xdostime = ZipUtils.javaToExtendedDosTime(n);
        if (this.xdostime != 2162688L && n <= 4036608000000L) {
            this.mtime = null;
        }
        else {
            this.mtime = FileTime.from(n, TimeUnit.MILLISECONDS);
        }
    }
    
    public long getTime() {
        if (this.mtime != null) {
            return this.mtime.toMillis();
        }
        return (this.xdostime != -1L) ? ZipUtils.extendedDosToJavaTime(this.xdostime) : -1L;
    }
    
    public ZipEntry setLastModifiedTime(final FileTime fileTime) {
        this.mtime = Objects.requireNonNull(fileTime, "lastModifiedTime");
        this.xdostime = ZipUtils.javaToExtendedDosTime(fileTime.to(TimeUnit.MILLISECONDS));
        return this;
    }
    
    public FileTime getLastModifiedTime() {
        if (this.mtime != null) {
            return this.mtime;
        }
        if (this.xdostime == -1L) {
            return null;
        }
        return FileTime.from(this.getTime(), TimeUnit.MILLISECONDS);
    }
    
    public ZipEntry setLastAccessTime(final FileTime fileTime) {
        this.atime = Objects.requireNonNull(fileTime, "lastAccessTime");
        return this;
    }
    
    public FileTime getLastAccessTime() {
        return this.atime;
    }
    
    public ZipEntry setCreationTime(final FileTime fileTime) {
        this.ctime = Objects.requireNonNull(fileTime, "creationTime");
        return this;
    }
    
    public FileTime getCreationTime() {
        return this.ctime;
    }
    
    public void setSize(final long size) {
        if (size < 0L) {
            throw new IllegalArgumentException("invalid entry size");
        }
        this.size = size;
    }
    
    public long getSize() {
        return this.size;
    }
    
    public long getCompressedSize() {
        return this.csize;
    }
    
    public void setCompressedSize(final long csize) {
        this.csize = csize;
    }
    
    public void setCrc(final long crc) {
        if (crc < 0L || crc > 4294967295L) {
            throw new IllegalArgumentException("invalid entry crc-32");
        }
        this.crc = crc;
    }
    
    public long getCrc() {
        return this.crc;
    }
    
    public void setMethod(final int method) {
        if (method != 0 && method != 8) {
            throw new IllegalArgumentException("invalid compression method");
        }
        this.method = method;
    }
    
    public int getMethod() {
        return this.method;
    }
    
    public void setExtra(final byte[] array) {
        this.setExtra0(array, false);
    }
    
    void setExtra0(final byte[] extra, final boolean b) {
        if (extra != null) {
            if (extra.length > 65535) {
                throw new IllegalArgumentException("invalid extra field length");
            }
            int get17;
            for (int n = 0, length = extra.length; n + 4 < length; n += get17) {
                final int get16 = ZipUtils.get16(extra, n);
                get17 = ZipUtils.get16(extra, n + 2);
                n += 4;
                if (n + get17 > length) {
                    break;
                }
                switch (get16) {
                    case 1: {
                        if (b && get17 >= 16) {
                            this.size = ZipUtils.get64(extra, n);
                            this.csize = ZipUtils.get64(extra, n + 8);
                            break;
                        }
                        break;
                    }
                    case 10: {
                        if (get17 < 32) {
                            break;
                        }
                        final int n2 = n + 4;
                        if (ZipUtils.get16(extra, n2) != 1) {
                            break;
                        }
                        if (ZipUtils.get16(extra, n2 + 2) != 24) {
                            break;
                        }
                        this.mtime = ZipUtils.winTimeToFileTime(ZipUtils.get64(extra, n2 + 4));
                        this.atime = ZipUtils.winTimeToFileTime(ZipUtils.get64(extra, n2 + 12));
                        this.ctime = ZipUtils.winTimeToFileTime(ZipUtils.get64(extra, n2 + 20));
                        break;
                    }
                    case 21589: {
                        final int unsignedInt = Byte.toUnsignedInt(extra[n]);
                        int n3 = 1;
                        if ((unsignedInt & 0x1) != 0x0 && n3 + 4 <= get17) {
                            this.mtime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(extra, n + n3));
                            n3 += 4;
                        }
                        if ((unsignedInt & 0x2) != 0x0 && n3 + 4 <= get17) {
                            this.atime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(extra, n + n3));
                            n3 += 4;
                        }
                        if ((unsignedInt & 0x4) != 0x0 && n3 + 4 <= get17) {
                            this.ctime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(extra, n + n3));
                            n3 += 4;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        this.extra = extra;
    }
    
    public byte[] getExtra() {
        return this.extra;
    }
    
    public void setComment(final String comment) {
        this.comment = comment;
    }
    
    public String getComment() {
        return this.comment;
    }
    
    public boolean isDirectory() {
        return this.name.endsWith("/");
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    public Object clone() {
        try {
            final ZipEntry zipEntry = (ZipEntry)super.clone();
            zipEntry.extra = (byte[])((this.extra == null) ? null : ((byte[])this.extra.clone()));
            return zipEntry;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
}
