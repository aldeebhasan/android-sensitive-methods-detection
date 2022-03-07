package java.io;

import java.util.concurrent.atomic.*;

public class BufferedInputStream extends FilterInputStream
{
    private static int DEFAULT_BUFFER_SIZE;
    private static int MAX_BUFFER_SIZE;
    protected volatile byte[] buf;
    private static final AtomicReferenceFieldUpdater<BufferedInputStream, byte[]> bufUpdater;
    protected int count;
    protected int pos;
    protected int markpos;
    protected int marklimit;
    
    private InputStream getInIfOpen() throws IOException {
        final InputStream in = this.in;
        if (in == null) {
            throw new IOException("Stream closed");
        }
        return in;
    }
    
    private byte[] getBufIfOpen() throws IOException {
        final byte[] buf = this.buf;
        if (buf == null) {
            throw new IOException("Stream closed");
        }
        return buf;
    }
    
    public BufferedInputStream(final InputStream inputStream) {
        this(inputStream, BufferedInputStream.DEFAULT_BUFFER_SIZE);
    }
    
    public BufferedInputStream(final InputStream inputStream, final int n) {
        super(inputStream);
        this.markpos = -1;
        if (n <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        this.buf = new byte[n];
    }
    
    private void fill() throws IOException {
        byte[] bufIfOpen = this.getBufIfOpen();
        if (this.markpos < 0) {
            this.pos = 0;
        }
        else if (this.pos >= bufIfOpen.length) {
            if (this.markpos > 0) {
                final int pos = this.pos - this.markpos;
                System.arraycopy(bufIfOpen, this.markpos, bufIfOpen, 0, pos);
                this.pos = pos;
                this.markpos = 0;
            }
            else if (bufIfOpen.length >= this.marklimit) {
                this.markpos = -1;
                this.pos = 0;
            }
            else {
                if (bufIfOpen.length >= BufferedInputStream.MAX_BUFFER_SIZE) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                int marklimit = (this.pos <= BufferedInputStream.MAX_BUFFER_SIZE - this.pos) ? (this.pos * 2) : BufferedInputStream.MAX_BUFFER_SIZE;
                if (marklimit > this.marklimit) {
                    marklimit = this.marklimit;
                }
                final byte[] array = new byte[marklimit];
                System.arraycopy(bufIfOpen, 0, array, 0, this.pos);
                if (!BufferedInputStream.bufUpdater.compareAndSet(this, bufIfOpen, array)) {
                    throw new IOException("Stream closed");
                }
                bufIfOpen = array;
            }
        }
        this.count = this.pos;
        final int read = this.getInIfOpen().read(bufIfOpen, this.pos, bufIfOpen.length - this.pos);
        if (read > 0) {
            this.count = read + this.pos;
        }
    }
    
    @Override
    public synchronized int read() throws IOException {
        if (this.pos >= this.count) {
            this.fill();
            if (this.pos >= this.count) {
                return -1;
            }
        }
        return this.getBufIfOpen()[this.pos++] & 0xFF;
    }
    
    private int read1(final byte[] array, final int n, final int n2) throws IOException {
        int n3 = this.count - this.pos;
        if (n3 <= 0) {
            if (n2 >= this.getBufIfOpen().length && this.markpos < 0) {
                return this.getInIfOpen().read(array, n, n2);
            }
            this.fill();
            n3 = this.count - this.pos;
            if (n3 <= 0) {
                return -1;
            }
        }
        final int n4 = (n3 < n2) ? n3 : n2;
        System.arraycopy(this.getBufIfOpen(), this.pos, array, n, n4);
        this.pos += n4;
        return n4;
    }
    
    @Override
    public synchronized int read(final byte[] array, final int n, final int n2) throws IOException {
        this.getBufIfOpen();
        if ((n | n2 | n + n2 | array.length - (n + n2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        int n3 = 0;
        while (true) {
            final int read1 = this.read1(array, n + n3, n2 - n3);
            if (read1 <= 0) {
                return (n3 == 0) ? read1 : n3;
            }
            n3 += read1;
            if (n3 >= n2) {
                return n3;
            }
            final InputStream in = this.in;
            if (in != null && in.available() <= 0) {
                return n3;
            }
        }
    }
    
    @Override
    public synchronized long skip(final long n) throws IOException {
        this.getBufIfOpen();
        if (n <= 0L) {
            return 0L;
        }
        long n2 = this.count - this.pos;
        if (n2 <= 0L) {
            if (this.markpos < 0) {
                return this.getInIfOpen().skip(n);
            }
            this.fill();
            n2 = this.count - this.pos;
            if (n2 <= 0L) {
                return 0L;
            }
        }
        final long n3 = (n2 < n) ? n2 : n;
        this.pos += (int)n3;
        return n3;
    }
    
    @Override
    public synchronized int available() throws IOException {
        final int n = this.count - this.pos;
        final int available = this.getInIfOpen().available();
        return (n > Integer.MAX_VALUE - available) ? Integer.MAX_VALUE : (n + available);
    }
    
    @Override
    public synchronized void mark(final int marklimit) {
        this.marklimit = marklimit;
        this.markpos = this.pos;
    }
    
    @Override
    public synchronized void reset() throws IOException {
        this.getBufIfOpen();
        if (this.markpos < 0) {
            throw new IOException("Resetting to invalid mark");
        }
        this.pos = this.markpos;
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public void close() throws IOException {
        byte[] buf;
        while ((buf = this.buf) != null) {
            if (BufferedInputStream.bufUpdater.compareAndSet(this, buf, null)) {
                final InputStream in = this.in;
                this.in = null;
                if (in != null) {
                    in.close();
                }
            }
        }
    }
    
    static {
        BufferedInputStream.DEFAULT_BUFFER_SIZE = 8192;
        BufferedInputStream.MAX_BUFFER_SIZE = 2147483639;
        bufUpdater = AtomicReferenceFieldUpdater.newUpdater(BufferedInputStream.class, byte[].class, "buf");
    }
}
