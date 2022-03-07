package java.io;

@Deprecated
public class StringBufferInputStream extends InputStream
{
    protected String buffer;
    protected int pos;
    protected int count;
    
    public StringBufferInputStream(final String buffer) {
        this.buffer = buffer;
        this.count = buffer.length();
    }
    
    @Override
    public synchronized int read() {
        return (this.pos < this.count) ? (this.buffer.charAt(this.pos++) & '\u00ff') : -1;
    }
    
    @Override
    public synchronized int read(final byte[] array, int n, int n2) {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (this.pos >= this.count) {
            return -1;
        }
        final int n3 = this.count - this.pos;
        if (n2 > n3) {
            n2 = n3;
        }
        if (n2 <= 0) {
            return 0;
        }
        final String buffer = this.buffer;
        while (--n2 >= 0) {
            array[n++] = (byte)buffer.charAt(this.pos++);
        }
        return n2;
    }
    
    @Override
    public synchronized long skip(long n) {
        if (n < 0L) {
            return 0L;
        }
        if (n > this.count - this.pos) {
            n = this.count - this.pos;
        }
        this.pos += (int)n;
        return n;
    }
    
    @Override
    public synchronized int available() {
        return this.count - this.pos;
    }
    
    @Override
    public synchronized void reset() {
        this.pos = 0;
    }
}
