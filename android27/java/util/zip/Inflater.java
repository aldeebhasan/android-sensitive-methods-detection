package java.util.zip;

public class Inflater
{
    private final ZStreamRef zsRef;
    private byte[] buf;
    private int off;
    private int len;
    private boolean finished;
    private boolean needDict;
    private long bytesRead;
    private long bytesWritten;
    private static final byte[] defaultBuf;
    
    public Inflater(final boolean b) {
        this.buf = Inflater.defaultBuf;
        this.zsRef = new ZStreamRef(init(b));
    }
    
    public Inflater() {
        this(false);
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
            this.needDict = false;
        }
    }
    
    public void setDictionary(final byte[] array) {
        this.setDictionary(array, 0, array.length);
    }
    
    public int getRemaining() {
        synchronized (this.zsRef) {
            return this.len;
        }
    }
    
    public boolean needsInput() {
        synchronized (this.zsRef) {
            return this.len <= 0;
        }
    }
    
    public boolean needsDictionary() {
        synchronized (this.zsRef) {
            return this.needDict;
        }
    }
    
    public boolean finished() {
        synchronized (this.zsRef) {
            return this.finished;
        }
    }
    
    public int inflate(final byte[] array, final int n, final int n2) throws DataFormatException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n > array.length - n2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        synchronized (this.zsRef) {
            this.ensureOpen();
            final int len = this.len;
            final int inflateBytes = this.inflateBytes(this.zsRef.address(), array, n, n2);
            this.bytesWritten += inflateBytes;
            this.bytesRead += len - this.len;
            return inflateBytes;
        }
    }
    
    public int inflate(final byte[] array) throws DataFormatException {
        return this.inflate(array, 0, array.length);
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
            this.buf = Inflater.defaultBuf;
            this.finished = false;
            this.needDict = false;
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
            throw new NullPointerException("Inflater has been closed");
        }
    }
    
    boolean ended() {
        synchronized (this.zsRef) {
            return this.zsRef.address() == 0L;
        }
    }
    
    private static native void initIDs();
    
    private static native long init(final boolean p0);
    
    private static native void setDictionary(final long p0, final byte[] p1, final int p2, final int p3);
    
    private native int inflateBytes(final long p0, final byte[] p1, final int p2, final int p3) throws DataFormatException;
    
    private static native int getAdler(final long p0);
    
    private static native void reset(final long p0);
    
    private static native void end(final long p0);
    
    static {
        defaultBuf = new byte[0];
        initIDs();
    }
}
