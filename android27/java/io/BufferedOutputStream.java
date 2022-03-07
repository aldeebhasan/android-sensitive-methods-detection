package java.io;

public class BufferedOutputStream extends FilterOutputStream
{
    protected byte[] buf;
    protected int count;
    
    public BufferedOutputStream(final OutputStream outputStream) {
        this(outputStream, 8192);
    }
    
    public BufferedOutputStream(final OutputStream outputStream, final int n) {
        super(outputStream);
        if (n <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        this.buf = new byte[n];
    }
    
    private void flushBuffer() throws IOException {
        if (this.count > 0) {
            this.out.write(this.buf, 0, this.count);
            this.count = 0;
        }
    }
    
    @Override
    public synchronized void write(final int n) throws IOException {
        if (this.count >= this.buf.length) {
            this.flushBuffer();
        }
        this.buf[this.count++] = (byte)n;
    }
    
    @Override
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        if (n2 >= this.buf.length) {
            this.flushBuffer();
            this.out.write(array, n, n2);
            return;
        }
        if (n2 > this.buf.length - this.count) {
            this.flushBuffer();
        }
        System.arraycopy(array, n, this.buf, this.count, n2);
        this.count += n2;
    }
    
    @Override
    public synchronized void flush() throws IOException {
        this.flushBuffer();
        this.out.flush();
    }
}
