package java.io;

public abstract class Writer implements Appendable, Closeable, Flushable
{
    private char[] writeBuffer;
    private static final int WRITE_BUFFER_SIZE = 1024;
    protected Object lock;
    
    protected Writer() {
        this.lock = this;
    }
    
    protected Writer(final Object lock) {
        if (lock == null) {
            throw new NullPointerException();
        }
        this.lock = lock;
    }
    
    public void write(final int n) throws IOException {
        synchronized (this.lock) {
            if (this.writeBuffer == null) {
                this.writeBuffer = new char[1024];
            }
            this.writeBuffer[0] = (char)n;
            this.write(this.writeBuffer, 0, 1);
        }
    }
    
    public void write(final char[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    public abstract void write(final char[] p0, final int p1, final int p2) throws IOException;
    
    public void write(final String s) throws IOException {
        this.write(s, 0, s.length());
    }
    
    public void write(final String s, final int n, final int n2) throws IOException {
        synchronized (this.lock) {
            char[] writeBuffer;
            if (n2 <= 1024) {
                if (this.writeBuffer == null) {
                    this.writeBuffer = new char[1024];
                }
                writeBuffer = this.writeBuffer;
            }
            else {
                writeBuffer = new char[n2];
            }
            s.getChars(n, n + n2, writeBuffer, 0);
            this.write(writeBuffer, 0, n2);
        }
    }
    
    @Override
    public Writer append(final CharSequence charSequence) throws IOException {
        if (charSequence == null) {
            this.write("null");
        }
        else {
            this.write(charSequence.toString());
        }
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence, final int n, final int n2) throws IOException {
        this.write(((charSequence == null) ? "null" : charSequence).subSequence(n, n2).toString());
        return this;
    }
    
    @Override
    public Writer append(final char c) throws IOException {
        this.write(c);
        return this;
    }
    
    @Override
    public abstract void flush() throws IOException;
    
    @Override
    public abstract void close() throws IOException;
}
