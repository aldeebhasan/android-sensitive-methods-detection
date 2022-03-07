package java.io;

public abstract class FilterReader extends Reader
{
    protected Reader in;
    
    protected FilterReader(final Reader in) {
        super(in);
        this.in = in;
    }
    
    @Override
    public int read() throws IOException {
        return this.in.read();
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) throws IOException {
        return this.in.read(array, n, n2);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return this.in.skip(n);
    }
    
    @Override
    public boolean ready() throws IOException {
        return this.in.ready();
    }
    
    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }
    
    @Override
    public void mark(final int n) throws IOException {
        this.in.mark(n);
    }
    
    @Override
    public void reset() throws IOException {
        this.in.reset();
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
}
