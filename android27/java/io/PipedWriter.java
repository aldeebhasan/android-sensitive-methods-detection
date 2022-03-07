package java.io;

public class PipedWriter extends Writer
{
    private PipedReader sink;
    private boolean closed;
    
    public PipedWriter(final PipedReader pipedReader) throws IOException {
        this.closed = false;
        this.connect(pipedReader);
    }
    
    public PipedWriter() {
        this.closed = false;
    }
    
    public synchronized void connect(final PipedReader sink) throws IOException {
        if (sink == null) {
            throw new NullPointerException();
        }
        if (this.sink != null || sink.connected) {
            throw new IOException("Already connected");
        }
        if (sink.closedByReader || this.closed) {
            throw new IOException("Pipe closed");
        }
        this.sink = sink;
        sink.in = -1;
        sink.out = 0;
        sink.connected = true;
    }
    
    @Override
    public void write(final int n) throws IOException {
        if (this.sink == null) {
            throw new IOException("Pipe not connected");
        }
        this.sink.receive(n);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) throws IOException {
        if (this.sink == null) {
            throw new IOException("Pipe not connected");
        }
        if ((n | n2 | n + n2 | array.length - (n + n2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.sink.receive(array, n, n2);
    }
    
    @Override
    public synchronized void flush() throws IOException {
        if (this.sink != null) {
            if (this.sink.closedByReader || this.closed) {
                throw new IOException("Pipe closed");
            }
            synchronized (this.sink) {
                this.sink.notifyAll();
            }
        }
    }
    
    @Override
    public void close() throws IOException {
        this.closed = true;
        if (this.sink != null) {
            this.sink.receivedLast();
        }
    }
}
