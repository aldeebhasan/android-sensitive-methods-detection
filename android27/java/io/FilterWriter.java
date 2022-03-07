package java.io;

public abstract class FilterWriter extends Writer
{
    protected Writer out;
    
    protected FilterWriter(final Writer out) {
        super(out);
        this.out = out;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.out.write(n);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
    }
    
    @Override
    public void write(final String s, final int n, final int n2) throws IOException {
        this.out.write(s, n, n2);
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
}
