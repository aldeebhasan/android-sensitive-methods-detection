package java.io;

public class FilterOutputStream extends OutputStream
{
    protected OutputStream out;
    
    public FilterOutputStream(final OutputStream out) {
        this.out = out;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.out.write(n);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if ((n | n2 | array.length - (n2 + n) | n + n2) < 0) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < n2; ++i) {
            this.write(array[n + i]);
        }
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public void close() throws IOException {
        try (final OutputStream out = this.out) {
            this.flush();
        }
    }
}
