package java.util.zip;

import java.nio.charset.*;
import java.util.stream.*;
import sun.misc.*;
import java.util.*;
import java.io.*;

public class ZipFile implements ZipConstants, Closeable
{
    private long jzfile;
    private final String name;
    private final int total;
    private final boolean locsig;
    private volatile boolean closeRequested;
    private static final int STORED = 0;
    private static final int DEFLATED = 8;
    public static final int OPEN_READ = 1;
    public static final int OPEN_DELETE = 4;
    private static final boolean usemmap;
    private static final boolean ensuretrailingslash;
    private ZipCoder zc;
    private final Map<InputStream, Inflater> streams;
    private Deque<Inflater> inflaterCache;
    private static final int JZENTRY_NAME = 0;
    private static final int JZENTRY_EXTRA = 1;
    private static final int JZENTRY_COMMENT = 2;
    
    private static native void initIDs();
    
    public ZipFile(final String s) throws IOException {
        this(new File(s), 1);
    }
    
    public ZipFile(final File file, final int n) throws IOException {
        this(file, n, StandardCharsets.UTF_8);
    }
    
    public ZipFile(final File file) throws ZipException, IOException {
        this(file, 1);
    }
    
    public ZipFile(final File file, final int n, final Charset charset) throws IOException {
        this.closeRequested = false;
        this.streams = new WeakHashMap<InputStream, Inflater>();
        this.inflaterCache = new ArrayDeque<Inflater>();
        if ((n & 0x1) == 0x0 || (n & 0xFFFFFFFA) != 0x0) {
            throw new IllegalArgumentException("Illegal mode: 0x" + Integer.toHexString(n));
        }
        final String path = file.getPath();
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(path);
            if ((n & 0x4) != 0x0) {
                securityManager.checkDelete(path);
            }
        }
        if (charset == null) {
            throw new NullPointerException("charset is null");
        }
        this.zc = ZipCoder.get(charset);
        final long nanoTime = System.nanoTime();
        this.jzfile = open(path, n, file.lastModified(), ZipFile.usemmap);
        PerfCounter.getZipFileOpenTime().addElapsedTimeFrom(nanoTime);
        PerfCounter.getZipFileCount().increment();
        this.name = path;
        this.total = getTotal(this.jzfile);
        this.locsig = startsWithLOC(this.jzfile);
    }
    
    public ZipFile(final String s, final Charset charset) throws IOException {
        this(new File(s), 1, charset);
    }
    
    public ZipFile(final File file, final Charset charset) throws IOException {
        this(file, 1, charset);
    }
    
    public String getComment() {
        synchronized (this) {
            this.ensureOpen();
            final byte[] commentBytes = getCommentBytes(this.jzfile);
            if (commentBytes == null) {
                return null;
            }
            return this.zc.toString(commentBytes, commentBytes.length);
        }
    }
    
    public ZipEntry getEntry(final String s) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        synchronized (this) {
            this.ensureOpen();
            final long entry = getEntry(this.jzfile, this.zc.getBytes(s), true);
            if (entry != 0L) {
                final ZipEntry zipEntry = ZipFile.ensuretrailingslash ? this.getZipEntry(null, entry) : this.getZipEntry(s, entry);
                freeEntry(this.jzfile, entry);
                return zipEntry;
            }
        }
        return null;
    }
    
    private static native long getEntry(final long p0, final byte[] p1, final boolean p2);
    
    private static native void freeEntry(final long p0, final long p1);
    
    public InputStream getInputStream(final ZipEntry zipEntry) throws IOException {
        if (zipEntry == null) {
            throw new NullPointerException("entry");
        }
        synchronized (this) {
            this.ensureOpen();
            long n;
            if (!this.zc.isUTF8() && (zipEntry.flag & 0x800) != 0x0) {
                n = getEntry(this.jzfile, this.zc.getBytesUTF8(zipEntry.name), false);
            }
            else {
                n = getEntry(this.jzfile, this.zc.getBytes(zipEntry.name), false);
            }
            if (n == 0L) {
                return null;
            }
            final ZipFileInputStream zipFileInputStream = new ZipFileInputStream(n);
            switch (getEntryMethod(n)) {
                case 0: {
                    synchronized (this.streams) {
                        this.streams.put(zipFileInputStream, null);
                    }
                    return zipFileInputStream;
                }
                case 8: {
                    long n2 = getEntrySize(n) + 2L;
                    if (n2 > 65536L) {
                        n2 = 8192L;
                    }
                    if (n2 <= 0L) {
                        n2 = 4096L;
                    }
                    final Inflater inflater = this.getInflater();
                    final ZipFileInflaterInputStream zipFileInflaterInputStream = new ZipFileInflaterInputStream(zipFileInputStream, inflater, (int)n2);
                    synchronized (this.streams) {
                        this.streams.put(zipFileInflaterInputStream, inflater);
                    }
                    return zipFileInflaterInputStream;
                }
                default: {
                    throw new ZipException("invalid compression method");
                }
            }
        }
    }
    
    private Inflater getInflater() {
        synchronized (this.inflaterCache) {
            Inflater inflater;
            while (null != (inflater = this.inflaterCache.poll())) {
                if (!inflater.ended()) {
                    return inflater;
                }
            }
        }
        return new Inflater(true);
    }
    
    private void releaseInflater(final Inflater inflater) {
        if (!inflater.ended()) {
            inflater.reset();
            synchronized (this.inflaterCache) {
                this.inflaterCache.add(inflater);
            }
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public Enumeration<? extends ZipEntry> entries() {
        return new ZipEntryIterator();
    }
    
    public Stream<? extends ZipEntry> stream() {
        return StreamSupport.stream(Spliterators.spliterator((Iterator<? extends ZipEntry>)new ZipEntryIterator(), (long)this.size(), 1297), false);
    }
    
    private ZipEntry getZipEntry(final String name, final long n) {
        final ZipEntry zipEntry = new ZipEntry();
        zipEntry.flag = getEntryFlag(n);
        if (name != null) {
            zipEntry.name = name;
        }
        else {
            final byte[] entryBytes = getEntryBytes(n, 0);
            if (entryBytes == null) {
                zipEntry.name = "";
            }
            else if (!this.zc.isUTF8() && (zipEntry.flag & 0x800) != 0x0) {
                zipEntry.name = this.zc.toStringUTF8(entryBytes, entryBytes.length);
            }
            else {
                zipEntry.name = this.zc.toString(entryBytes, entryBytes.length);
            }
        }
        zipEntry.xdostime = getEntryTime(n);
        zipEntry.crc = getEntryCrc(n);
        zipEntry.size = getEntrySize(n);
        zipEntry.csize = getEntryCSize(n);
        zipEntry.method = getEntryMethod(n);
        zipEntry.setExtra0(getEntryBytes(n, 1), false);
        final byte[] entryBytes2 = getEntryBytes(n, 2);
        if (entryBytes2 == null) {
            zipEntry.comment = null;
        }
        else if (!this.zc.isUTF8() && (zipEntry.flag & 0x800) != 0x0) {
            zipEntry.comment = this.zc.toStringUTF8(entryBytes2, entryBytes2.length);
        }
        else {
            zipEntry.comment = this.zc.toString(entryBytes2, entryBytes2.length);
        }
        return zipEntry;
    }
    
    private static native long getNextEntry(final long p0, final int p1);
    
    public int size() {
        this.ensureOpen();
        return this.total;
    }
    
    @Override
    public void close() throws IOException {
        if (this.closeRequested) {
            return;
        }
        this.closeRequested = true;
        synchronized (this) {
            synchronized (this.streams) {
                if (!this.streams.isEmpty()) {
                    final HashMap<InputStream, Object> hashMap = new HashMap<InputStream, Object>(this.streams);
                    this.streams.clear();
                    for (final Map.Entry<InputStream, V> entry : hashMap.entrySet()) {
                        entry.getKey().close();
                        final Inflater inflater = (Inflater)entry.getValue();
                        if (inflater != null) {
                            inflater.end();
                        }
                    }
                }
            }
            synchronized (this.inflaterCache) {
                Inflater inflater2;
                while (null != (inflater2 = this.inflaterCache.poll())) {
                    inflater2.end();
                }
            }
            if (this.jzfile != 0L) {
                final long jzfile = this.jzfile;
                this.jzfile = 0L;
                close(jzfile);
            }
        }
    }
    
    @Override
    protected void finalize() throws IOException {
        this.close();
    }
    
    private static native void close(final long p0);
    
    private void ensureOpen() {
        if (this.closeRequested) {
            throw new IllegalStateException("zip file closed");
        }
        if (this.jzfile == 0L) {
            throw new IllegalStateException("The object is not initialized.");
        }
    }
    
    private void ensureOpenOrZipException() throws IOException {
        if (this.closeRequested) {
            throw new ZipException("ZipFile closed");
        }
    }
    
    private boolean startsWithLocHeader() {
        return this.locsig;
    }
    
    private static native long open(final String p0, final int p1, final long p2, final boolean p3) throws IOException;
    
    private static native int getTotal(final long p0);
    
    private static native boolean startsWithLOC(final long p0);
    
    private static native int read(final long p0, final long p1, final long p2, final byte[] p3, final int p4, final int p5);
    
    private static native long getEntryTime(final long p0);
    
    private static native long getEntryCrc(final long p0);
    
    private static native long getEntryCSize(final long p0);
    
    private static native long getEntrySize(final long p0);
    
    private static native int getEntryMethod(final long p0);
    
    private static native int getEntryFlag(final long p0);
    
    private static native byte[] getCommentBytes(final long p0);
    
    private static native byte[] getEntryBytes(final long p0, final int p1);
    
    private static native String getZipMessage(final long p0);
    
    static {
        initIDs();
        final String savedProperty = VM.getSavedProperty("sun.zip.disableMemoryMapping");
        usemmap = (savedProperty == null || (savedProperty.length() != 0 && !savedProperty.equalsIgnoreCase("true")));
        final String savedProperty2 = VM.getSavedProperty("jdk.util.zip.ensureTrailingSlash");
        ensuretrailingslash = (savedProperty2 == null || !savedProperty2.equalsIgnoreCase("false"));
        SharedSecrets.setJavaUtilZipFileAccess(new JavaUtilZipFileAccess() {
            @Override
            public boolean startsWithLocHeader(final ZipFile zipFile) {
                return zipFile.startsWithLocHeader();
            }
        });
    }
    
    private class ZipEntryIterator implements Enumeration<ZipEntry>, Iterator<ZipEntry>
    {
        private int i;
        
        public ZipEntryIterator() {
            this.i = 0;
            ZipFile.this.ensureOpen();
        }
        
        @Override
        public boolean hasMoreElements() {
            return this.hasNext();
        }
        
        @Override
        public boolean hasNext() {
            synchronized (ZipFile.this) {
                ZipFile.this.ensureOpen();
                return this.i < ZipFile.this.total;
            }
        }
        
        @Override
        public ZipEntry nextElement() {
            return this.next();
        }
        
        @Override
        public ZipEntry next() {
            synchronized (ZipFile.this) {
                ZipFile.this.ensureOpen();
                if (this.i >= ZipFile.this.total) {
                    throw new NoSuchElementException();
                }
                final long access$500 = getNextEntry(ZipFile.this.jzfile, this.i++);
                if (access$500 == 0L) {
                    String access$501;
                    if (ZipFile.this.closeRequested) {
                        access$501 = "ZipFile concurrently closed";
                    }
                    else {
                        access$501 = getZipMessage(ZipFile.this.jzfile);
                    }
                    throw new ZipError("jzentry == 0,\n jzfile = " + ZipFile.this.jzfile + ",\n total = " + ZipFile.this.total + ",\n name = " + ZipFile.this.name + ",\n i = " + this.i + ",\n message = " + access$501);
                }
                final ZipEntry access$502 = ZipFile.this.getZipEntry(null, access$500);
                freeEntry(ZipFile.this.jzfile, access$500);
                return access$502;
            }
        }
    }
    
    private class ZipFileInflaterInputStream extends InflaterInputStream
    {
        private volatile boolean closeRequested;
        private boolean eof;
        private final ZipFileInputStream zfin;
        
        ZipFileInflaterInputStream(final ZipFileInputStream zfin, final Inflater inflater, final int n) {
            super(zfin, inflater, n);
            this.closeRequested = false;
            this.eof = false;
            this.zfin = zfin;
        }
        
        @Override
        public void close() throws IOException {
            if (this.closeRequested) {
                return;
            }
            this.closeRequested = true;
            super.close();
            final Inflater inflater;
            synchronized (ZipFile.this.streams) {
                inflater = ZipFile.this.streams.remove(this);
            }
            if (inflater != null) {
                ZipFile.this.releaseInflater(inflater);
            }
        }
        
        @Override
        protected void fill() throws IOException {
            if (this.eof) {
                throw new EOFException("Unexpected end of ZLIB input stream");
            }
            this.len = this.in.read(this.buf, 0, this.buf.length);
            if (this.len == -1) {
                this.buf[0] = 0;
                this.len = 1;
                this.eof = true;
            }
            this.inf.setInput(this.buf, 0, this.len);
        }
        
        @Override
        public int available() throws IOException {
            if (this.closeRequested) {
                return 0;
            }
            final long n = this.zfin.size() - this.inf.getBytesWritten();
            return (n > 2147483647L) ? Integer.MAX_VALUE : ((int)n);
        }
        
        @Override
        protected void finalize() throws Throwable {
            this.close();
        }
    }
    
    private class ZipFileInputStream extends InputStream
    {
        private volatile boolean zfisCloseRequested;
        protected long jzentry;
        private long pos;
        protected long rem;
        protected long size;
        
        ZipFileInputStream(final long jzentry) {
            this.zfisCloseRequested = false;
            this.pos = 0L;
            this.rem = getEntryCSize(jzentry);
            this.size = getEntrySize(jzentry);
            this.jzentry = jzentry;
        }
        
        @Override
        public int read(final byte[] array, final int n, int access$1400) throws IOException {
            synchronized (ZipFile.this) {
                final long rem = this.rem;
                final long pos = this.pos;
                if (rem == 0L) {
                    return -1;
                }
                if (access$1400 <= 0) {
                    return 0;
                }
                if (access$1400 > rem) {
                    access$1400 = (int)rem;
                }
                ZipFile.this.ensureOpenOrZipException();
                access$1400 = read(ZipFile.this.jzfile, this.jzentry, pos, array, n, access$1400);
                if (access$1400 > 0) {
                    this.pos = pos + access$1400;
                    this.rem = rem - access$1400;
                }
            }
            if (this.rem == 0L) {
                this.close();
            }
            return access$1400;
        }
        
        @Override
        public int read() throws IOException {
            final byte[] array = { 0 };
            if (this.read(array, 0, 1) == 1) {
                return array[0] & 0xFF;
            }
            return -1;
        }
        
        @Override
        public long skip(long rem) {
            if (rem > this.rem) {
                rem = this.rem;
            }
            this.pos += rem;
            this.rem -= rem;
            if (this.rem == 0L) {
                this.close();
            }
            return rem;
        }
        
        @Override
        public int available() {
            return (this.rem > 2147483647L) ? Integer.MAX_VALUE : ((int)this.rem);
        }
        
        public long size() {
            return this.size;
        }
        
        @Override
        public void close() {
            if (this.zfisCloseRequested) {
                return;
            }
            this.zfisCloseRequested = true;
            this.rem = 0L;
            synchronized (ZipFile.this) {
                if (this.jzentry != 0L && ZipFile.this.jzfile != 0L) {
                    freeEntry(ZipFile.this.jzfile, this.jzentry);
                    this.jzentry = 0L;
                }
            }
            synchronized (ZipFile.this.streams) {
                ZipFile.this.streams.remove(this);
            }
        }
        
        @Override
        protected void finalize() {
            this.close();
        }
    }
}
