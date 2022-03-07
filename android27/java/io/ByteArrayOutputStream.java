package java.io;

import java.util.*;

public class ByteArrayOutputStream extends OutputStream
{
    protected byte[] buf;
    protected int count;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    
    public ByteArrayOutputStream() {
        this(32);
    }
    
    public ByteArrayOutputStream(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative initial size: " + n);
        }
        this.buf = new byte[n];
    }
    
    private void ensureCapacity(final int n) {
        if (n - this.buf.length > 0) {
            this.grow(n);
        }
    }
    
    private void grow(final int n) {
        int hugeCapacity = this.buf.length << 1;
        if (hugeCapacity - n < 0) {
            hugeCapacity = n;
        }
        if (hugeCapacity - 2147483639 > 0) {
            hugeCapacity = hugeCapacity(n);
        }
        this.buf = Arrays.copyOf(this.buf, hugeCapacity);
    }
    
    private static int hugeCapacity(final int n) {
        if (n < 0) {
            throw new OutOfMemoryError();
        }
        return (n > 2147483639) ? Integer.MAX_VALUE : 2147483639;
    }
    
    @Override
    public synchronized void write(final int n) {
        this.ensureCapacity(this.count + 1);
        this.buf[this.count] = (byte)n;
        ++this.count;
    }
    
    @Override
    public synchronized void write(final byte[] array, final int n, final int n2) {
        if (n < 0 || n > array.length || n2 < 0 || n + n2 - array.length > 0) {
            throw new IndexOutOfBoundsException();
        }
        this.ensureCapacity(this.count + n2);
        System.arraycopy(array, n, this.buf, this.count, n2);
        this.count += n2;
    }
    
    public synchronized void writeTo(final OutputStream outputStream) throws IOException {
        outputStream.write(this.buf, 0, this.count);
    }
    
    public synchronized void reset() {
        this.count = 0;
    }
    
    public synchronized byte[] toByteArray() {
        return Arrays.copyOf(this.buf, this.count);
    }
    
    public synchronized int size() {
        return this.count;
    }
    
    @Override
    public synchronized String toString() {
        return new String(this.buf, 0, this.count);
    }
    
    public synchronized String toString(final String s) throws UnsupportedEncodingException {
        return new String(this.buf, 0, this.count, s);
    }
    
    @Deprecated
    public synchronized String toString(final int n) {
        return new String(this.buf, n, 0, this.count);
    }
    
    @Override
    public void close() throws IOException {
    }
}
