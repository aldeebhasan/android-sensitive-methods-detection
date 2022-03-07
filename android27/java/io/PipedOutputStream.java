package java.io;

public class PipedOutputStream extends OutputStream
{
    private PipedInputStream sink;
    
    public PipedOutputStream(final PipedInputStream pipedInputStream) throws IOException {
        this.connect(pipedInputStream);
    }
    
    public PipedOutputStream() {
    }
    
    public synchronized void connect(final PipedInputStream sink) throws IOException {
        if (sink == null) {
            throw new NullPointerException();
        }
        if (this.sink != null || sink.connected) {
            throw new IOException("Already connected");
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
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (this.sink == null) {
            throw new IOException("Pipe not connected");
        }
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        this.sink.receive(array, n, n2);
    }
    
    @Override
    public synchronized void flush() throws IOException {
        if (this.sink != null) {
            synchronized (this.sink) {
                this.sink.notifyAll();
            }
        }
    }
    
    @Override
    public void close() throws IOException {
        if (this.sink != null) {
            this.sink.receivedLast();
        }
    }
}
