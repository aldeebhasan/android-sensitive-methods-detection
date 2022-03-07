package java.util.zip;

public class Deflater
{
    private final ZStreamRef zsRef;
    private byte[] buf;
    private int off;
    private int len;
    private int level;
    private int strategy;
    private boolean setParams;
    private boolean finish;
    private boolean finished;
    private long bytesRead;
    private long bytesWritten;
    public static final int DEFLATED = 8;
    public static final int NO_COMPRESSION = 0;
    public static final int BEST_SPEED = 1;
    public static final int BEST_COMPRESSION = 9;
    public static final int DEFAULT_COMPRESSION = -1;
    public static final int FILTERED = 1;
    public static final int HUFFMAN_ONLY = 2;
    public static final int DEFAULT_STRATEGY = 0;
    public static final int NO_FLUSH = 0;
    public static final int SYNC_FLUSH = 2;
    public static final int FULL_FLUSH = 3;
    
    public Deflater(final int level, final boolean b) {
        this.buf = new byte[0];
        this.level = level;
        this.strategy = 0;
        this.zsRef = new ZStreamRef(init(level, 0, b));
    }
    
    public Deflater(final int n) {
        this(n, false);
    }
    
    public Deflater() {
        this(-1, false);
    }
    
    public void setInput(final byte[] buf, final int off, final int len) {
        if (buf == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > buf.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        synchronized (this.zsRef) {
            this.buf = buf;
            this.off = off;
            this.len = len;
        }
    }
    
    public void setInput(final byte[] array) {
        this.setInput(array, 0, array.length);
    }
    
    public void setDictionary(final byte[] array, final int n, final int n2) {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n > array.length - n2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        synchronized (this.zsRef) {
            this.ensureOpen();
            setDictionary(this.zsRef.address(), array, n, n2);
        }
    }
    
    public void setDictionary(final byte[] array) {
        this.setDictionary(array, 0, array.length);
    }
    
    public void setStrategy(final int strategy) {
        switch (strategy) {
            case 0:
            case 1:
            case 2: {
                synchronized (this.zsRef) {
                    if (this.strategy != strategy) {
                        this.strategy = strategy;
                        this.setParams = true;
                    }
                }
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
    
    public void setLevel(final int level) {
        if ((level < 0 || level > 9) && level != -1) {
            throw new IllegalArgumentException("invalid compression level");
        }
        synchronized (this.zsRef) {
            if (this.level != level) {
                this.level = level;
                this.setParams = true;
            }
        }
    }
    
    public boolean needsInput() {
        synchronized (this.zsRef) {
            return this.len <= 0;
        }
    }
    
    public void finish() {
        synchronized (this.zsRef) {
            this.finish = true;
        }
    }
    
    public boolean finished() {
        synchronized (this.zsRef) {
            return this.finished;
        }
    }
    
    public int deflate(final byte[] array, final int n, final int n2) {
        return this.deflate(array, n, n2, 0);
    }
    
    public int deflate(final byte[] array) {
        return this.deflate(array, 0, array.length, 0);
    }
    
    public int deflate(final byte[] array, final int n, final int n2, final int n3) {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n > array.length - n2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        synchronized (this.zsRef) {
            this.ensureOpen();
            if (n3 == 0 || n3 == 2 || n3 == 3) {
                final int len = this.len;
                final int deflateBytes = this.deflateBytes(this.zsRef.address(), array, n, n2, n3);
                this.bytesWritten += deflateBytes;
                this.bytesRead += len - this.len;
                return deflateBytes;
            }
            throw new IllegalArgumentException();
        }
    }
    
    public int getAdler() {
        synchronized (this.zsRef) {
            this.ensureOpen();
            return getAdler(this.zsRef.address());
        }
    }
    
    public int getTotalIn() {
        return (int)this.getBytesRead();
    }
    
    public long getBytesRead() {
        synchronized (this.zsRef) {
            this.ensureOpen();
            return this.bytesRead;
        }
    }
    
    public int getTotalOut() {
        return (int)this.getBytesWritten();
    }
    
    public long getBytesWritten() {
        synchronized (this.zsRef) {
            this.ensureOpen();
            return this.bytesWritten;
        }
    }
    
    public void reset() {
        synchronized (this.zsRef) {
            this.ensureOpen();
            reset(this.zsRef.address());
            this.finish = false;
            this.finished = false;
            final boolean b = false;
            this.len = (b ? 1 : 0);
            this.off = (b ? 1 : 0);
            final long n = 0L;
            this.bytesWritten = n;
            this.bytesRead = n;
        }
    }
    
    public void end() {
        synchronized (this.zsRef) {
            final long address = this.zsRef.address();
            this.zsRef.clear();
            if (address != 0L) {
                end(address);
                this.buf = null;
            }
        }
    }
    
    @Override
    protected void finalize() {
        this.end();
    }
    
    private void ensureOpen() {
        assert Thread.holdsLock(this.zsRef);
        if (this.zsRef.address() == 0L) {
            throw new NullPointerException("Deflater has been closed");
        }
    }
    
    private static native void initIDs();
    
    private static native long init(final int p0, final int p1, final boolean p2);
    
    private static native void setDictionary(final long p0, final byte[] p1, final int p2, final int p3);
    
    private native int deflateBytes(final long p0, final byte[] p1, final int p2, final int p3, final int p4);
    
    private static native int getAdler(final long p0);
    
    private static native void reset(final long p0);
    
    private static native void end(final long p0);
    
    static {
        initIDs();
    }
}
