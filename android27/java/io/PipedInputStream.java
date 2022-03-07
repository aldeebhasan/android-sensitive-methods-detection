package java.io;

public class PipedInputStream extends InputStream
{
    boolean closedByWriter;
    volatile boolean closedByReader;
    boolean connected;
    Thread readSide;
    Thread writeSide;
    private static final int DEFAULT_PIPE_SIZE = 1024;
    protected static final int PIPE_SIZE = 1024;
    protected byte[] buffer;
    protected int in;
    protected int out;
    
    public PipedInputStream(final PipedOutputStream pipedOutputStream) throws IOException {
        this(pipedOutputStream, 1024);
    }
    
    public PipedInputStream(final PipedOutputStream pipedOutputStream, final int n) throws IOException {
        this.closedByWriter = false;
        this.closedByReader = false;
        this.connected = false;
        this.in = -1;
        this.out = 0;
        this.initPipe(n);
        this.connect(pipedOutputStream);
    }
    
    public PipedInputStream() {
        this.closedByWriter = false;
        this.closedByReader = false;
        this.connected = false;
        this.in = -1;
        this.out = 0;
        this.initPipe(1024);
    }
    
    public PipedInputStream(final int n) {
        this.closedByWriter = false;
        this.closedByReader = false;
        this.connected = false;
        this.in = -1;
        this.out = 0;
        this.initPipe(n);
    }
    
    private void initPipe(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Pipe Size <= 0");
        }
        this.buffer = new byte[n];
    }
    
    public void connect(final PipedOutputStream pipedOutputStream) throws IOException {
        pipedOutputStream.connect(this);
    }
    
    protected synchronized void receive(final int n) throws IOException {
        this.checkStateForReceive();
        this.writeSide = Thread.currentThread();
        if (this.in == this.out) {
            this.awaitSpace();
        }
        if (this.in < 0) {
            this.in = 0;
            this.out = 0;
        }
        this.buffer[this.in++] = (byte)(n & 0xFF);
        if (this.in >= this.buffer.length) {
            this.in = 0;
        }
    }
    
    synchronized void receive(final byte[] array, int n, final int n2) throws IOException {
        this.checkStateForReceive();
        this.writeSide = Thread.currentThread();
        int i = n2;
        while (i > 0) {
            if (this.in == this.out) {
                this.awaitSpace();
            }
            int n3 = 0;
            if (this.out < this.in) {
                n3 = this.buffer.length - this.in;
            }
            else if (this.in < this.out) {
                if (this.in == -1) {
                    final boolean b = false;
                    this.out = (b ? 1 : 0);
                    this.in = (b ? 1 : 0);
                    n3 = this.buffer.length - this.in;
                }
                else {
                    n3 = this.out - this.in;
                }
            }
            if (n3 > i) {
                n3 = i;
            }
            assert n3 > 0;
            System.arraycopy(array, n, this.buffer, this.in, n3);
            i -= n3;
            n += n3;
            this.in += n3;
            if (this.in < this.buffer.length) {
                continue;
            }
            this.in = 0;
        }
    }
    
    private void checkStateForReceive() throws IOException {
        if (!this.connected) {
            throw new IOException("Pipe not connected");
        }
        if (this.closedByWriter || this.closedByReader) {
            throw new IOException("Pipe closed");
        }
        if (this.readSide != null && !this.readSide.isAlive()) {
            throw new IOException("Read end dead");
        }
    }
    
    private void awaitSpace() throws IOException {
        while (this.in == this.out) {
            this.checkStateForReceive();
            this.notifyAll();
            try {
                this.wait(1000L);
                continue;
            }
            catch (InterruptedException ex) {
                throw new InterruptedIOException();
            }
            break;
        }
    }
    
    synchronized void receivedLast() {
        this.closedByWriter = true;
        this.notifyAll();
    }
    
    @Override
    public synchronized int read() throws IOException {
        if (!this.connected) {
            throw new IOException("Pipe not connected");
        }
        if (this.closedByReader) {
            throw new IOException("Pipe closed");
        }
        if (this.writeSide != null && !this.writeSide.isAlive() && !this.closedByWriter && this.in < 0) {
            throw new IOException("Write end dead");
        }
        this.readSide = Thread.currentThread();
        int n = 2;
        while (this.in < 0) {
            if (this.closedByWriter) {
                return -1;
            }
            if (this.writeSide != null && !this.writeSide.isAlive() && --n < 0) {
                throw new IOException("Pipe broken");
            }
            this.notifyAll();
            try {
                this.wait(1000L);
                continue;
            }
            catch (InterruptedException ex) {
                throw new InterruptedIOException();
            }
            break;
        }
        final int n2 = this.buffer[this.out++] & 0xFF;
        if (this.out >= this.buffer.length) {
            this.out = 0;
        }
        if (this.in == this.out) {
            this.in = -1;
        }
        return n2;
    }
    
    @Override
    public synchronized int read(final byte[] array, final int n, int n2) throws IOException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n2 > array.length - n) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        final int read = this.read();
        if (read < 0) {
            return -1;
        }
        array[n] = (byte)read;
        int n3 = 1;
        while (this.in >= 0 && n2 > 1) {
            int min;
            if (this.in > this.out) {
                min = Math.min(this.buffer.length - this.out, this.in - this.out);
            }
            else {
                min = this.buffer.length - this.out;
            }
            if (min > n2 - 1) {
                min = n2 - 1;
            }
            System.arraycopy(this.buffer, this.out, array, n + n3, min);
            this.out += min;
            n3 += min;
            n2 -= min;
            if (this.out >= this.buffer.length) {
                this.out = 0;
            }
            if (this.in == this.out) {
                this.in = -1;
            }
        }
        return n3;
    }
    
    @Override
    public synchronized int available() throws IOException {
        if (this.in < 0) {
            return 0;
        }
        if (this.in == this.out) {
            return this.buffer.length;
        }
        if (this.in > this.out) {
            return this.in - this.out;
        }
        return this.in + this.buffer.length - this.out;
    }
    
    @Override
    public void close() throws IOException {
        this.closedByReader = true;
        synchronized (this) {
            this.in = -1;
        }
    }
}
