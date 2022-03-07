package java.io;

public class StringReader extends Reader
{
    private String str;
    private int length;
    private int next;
    private int mark;
    
    public StringReader(final String str) {
        this.next = 0;
        this.mark = 0;
        this.str = str;
        this.length = str.length();
    }
    
    private void ensureOpen() throws IOException {
        if (this.str == null) {
            throw new IOException("Stream closed");
        }
    }
    
    @Override
    public int read() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (this.next >= this.length) {
                return -1;
            }
            return this.str.charAt(this.next++);
        }
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return 0;
            }
            if (this.next >= this.length) {
                return -1;
            }
            final int min = Math.min(this.length - this.next, n2);
            this.str.getChars(this.next, this.next + min, array, n);
            this.next += min;
            return min;
        }
    }
    
    @Override
    public long skip(final long n) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (this.next >= this.length) {
                return 0L;
            }
            final long max = Math.max(-this.next, Math.min(this.length - this.next, n));
            this.next += (int)max;
            return max;
        }
    }
    
    @Override
    public boolean ready() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            return true;
        }
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public void mark(final int n) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException("Read-ahead limit < 0");
        }
        synchronized (this.lock) {
            this.ensureOpen();
            this.mark = this.next;
        }
    }
    
    @Override
    public void reset() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            this.next = this.mark;
        }
    }
    
    @Override
    public void close() {
        this.str = null;
    }
}
