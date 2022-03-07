package java.io;

public class PipedReader extends Reader
{
    boolean closedByWriter;
    boolean closedByReader;
    boolean connected;
    Thread readSide;
    Thread writeSide;
    private static final int DEFAULT_PIPE_SIZE = 1024;
    char[] buffer;
    int in;
    int out;
    
    public PipedReader(final PipedWriter pipedWriter) throws IOException {
        this(pipedWriter, 1024);
    }
    
    public PipedReader(final PipedWriter pipedWriter, final int n) throws IOException {
        this.closedByWriter = false;
        this.closedByReader = false;
        this.connected = false;
        this.in = -1;
        this.out = 0;
        this.initPipe(n);
        this.connect(pipedWriter);
    }
    
    public PipedReader() {
        this.closedByWriter = false;
        this.closedByReader = false;
        this.connected = false;
        this.in = -1;
        this.out = 0;
        this.initPipe(1024);
    }
    
    public PipedReader(final int n) {
        this.closedByWriter = false;
        this.closedByReader = false;
        this.connected = false;
        this.in = -1;
        this.out = 0;
        this.initPipe(n);
    }
    
    private void initPipe(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Pipe size <= 0");
        }
        this.buffer = new char[n];
    }
    
    public void connect(final PipedWriter pipedWriter) throws IOException {
        pipedWriter.connect(this);
    }
    
    synchronized void receive(final int n) throws IOException {
        if (!this.connected) {
            throw new IOException("Pipe not connected");
        }
        if (this.closedByWriter || this.closedByReader) {
            throw new IOException("Pipe closed");
        }
        if (this.readSide != null && !this.readSide.isAlive()) {
            throw new IOException("Read end dead");
        }
        this.writeSide = Thread.currentThread();
        while (this.in == this.out) {
            if (this.readSide != null && !this.readSide.isAlive()) {
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
        if (this.in < 0) {
            this.in = 0;
            this.out = 0;
        }
        this.buffer[this.in++] = (char)n;
        if (this.in >= this.buffer.length) {
            this.in = 0;
        }
    }
    
    synchronized void receive(final char[] array, int n, int n2) throws IOException {
        while (--n2 >= 0) {
            this.receive(array[n++]);
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
        final char c = this.buffer[this.out++];
        if (this.out >= this.buffer.length) {
            this.out = 0;
        }
        if (this.in == this.out) {
            this.in = -1;
        }
        return c;
    }
    
    @Override
    public synchronized int read(final char[] array, final int n, int n2) throws IOException {
        if (!this.connected) {
            throw new IOException("Pipe not connected");
        }
        if (this.closedByReader) {
            throw new IOException("Pipe closed");
        }
        if (this.writeSide != null && !this.writeSide.isAlive() && !this.closedByWriter && this.in < 0) {
            throw new IOException("Write end dead");
        }
        if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        final int read = this.read();
        if (read < 0) {
            return -1;
        }
        array[n] = (char)read;
        int n3 = 1;
        while (this.in >= 0 && --n2 > 0) {
            array[n + n3] = this.buffer[this.out++];
            ++n3;
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
    public synchronized boolean ready() throws IOException {
        if (!this.connected) {
            throw new IOException("Pipe not connected");
        }
        if (this.closedByReader) {
            throw new IOException("Pipe closed");
        }
        if (this.writeSide != null && !this.writeSide.isAlive() && !this.closedByWriter && this.in < 0) {
            throw new IOException("Write end dead");
        }
        return this.in >= 0;
    }
    
    @Override
    public void close() throws IOException {
        this.in = -1;
        this.closedByReader = true;
    }
}
