package java.io;

public class StringWriter extends Writer
{
    private StringBuffer buf;
    
    public StringWriter() {
        this.buf = new StringBuffer();
        this.lock = this.buf;
    }
    
    public StringWriter(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative buffer size");
        }
        this.buf = new StringBuffer(n);
        this.lock = this.buf;
    }
    
    @Override
    public void write(final int n) {
        this.buf.append((char)n);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
        if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        this.buf.append(array, n, n2);
    }
    
    @Override
    public void write(final String s) {
        this.buf.append(s);
    }
    
    @Override
    public void write(final String s, final int n, final int n2) {
        this.buf.append(s.substring(n, n + n2));
    }
    
    @Override
    public StringWriter append(final CharSequence charSequence) {
        if (charSequence == null) {
            this.write("null");
        }
        else {
            this.write(charSequence.toString());
        }
        return this;
    }
    
    @Override
    public StringWriter append(final CharSequence charSequence, final int n, final int n2) {
        this.write(((charSequence == null) ? "null" : charSequence).subSequence(n, n2).toString());
        return this;
    }
    
    @Override
    public StringWriter append(final char c) {
        this.write(c);
        return this;
    }
    
    @Override
    public String toString() {
        return this.buf.toString();
    }
    
    public StringBuffer getBuffer() {
        return this.buf;
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void close() throws IOException {
    }
}
