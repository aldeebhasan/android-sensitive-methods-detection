package java.io;

public class FilterInputStream extends InputStream
{
    protected volatile InputStream in;
    
    protected FilterInputStream(final InputStream in) {
        this.in = in;
    }
    
    @Override
    public int read() throws IOException {
        return this.in.read();
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.in.read(array, n, n2);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return this.in.skip(n);
    }
    
    @Override
    public int available() throws IOException {
        return this.in.available();
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
    
    @Override
    public synchronized void mark(final int n) {
        this.in.mark(n);
    }
    
    @Override
    public synchronized void reset() throws IOException {
        this.in.reset();
    }
    
    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }
}
