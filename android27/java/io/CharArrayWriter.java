package java.io;

import java.util.*;

public class CharArrayWriter extends Writer
{
    protected char[] buf;
    protected int count;
    
    public CharArrayWriter() {
        this(32);
    }
    
    public CharArrayWriter(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative initial size: " + n);
        }
        this.buf = new char[n];
    }
    
    @Override
    public void write(final int n) {
        synchronized (this.lock) {
            final int count = this.count + 1;
            if (count > this.buf.length) {
                this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, count));
            }
            this.buf[this.count] = (char)n;
            this.count = count;
        }
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
        if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        synchronized (this.lock) {
            final int count = this.count + n2;
            if (count > this.buf.length) {
                this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, count));
            }
            System.arraycopy(array, n, this.buf, this.count, n2);
            this.count = count;
        }
    }
    
    @Override
    public void write(final String s, final int n, final int n2) {
        synchronized (this.lock) {
            final int count = this.count + n2;
            if (count > this.buf.length) {
                this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, count));
            }
            s.getChars(n, n + n2, this.buf, this.count);
            this.count = count;
        }
    }
    
    public void writeTo(final Writer writer) throws IOException {
        synchronized (this.lock) {
            writer.write(this.buf, 0, this.count);
        }
    }
    
    @Override
    public CharArrayWriter append(final CharSequence charSequence) {
        final String s = (charSequence == null) ? "null" : charSequence.toString();
        this.write(s, 0, s.length());
        return this;
    }
    
    @Override
    public CharArrayWriter append(final CharSequence charSequence, final int n, final int n2) {
        final String string = ((charSequence == null) ? "null" : charSequence).subSequence(n, n2).toString();
        this.write(string, 0, string.length());
        return this;
    }
    
    @Override
    public CharArrayWriter append(final char c) {
        this.write(c);
        return this;
    }
    
    public void reset() {
        this.count = 0;
    }
    
    public char[] toCharArray() {
        synchronized (this.lock) {
            return Arrays.copyOf(this.buf, this.count);
        }
    }
    
    public int size() {
        return this.count;
    }
    
    @Override
    public String toString() {
        synchronized (this.lock) {
            return new String(this.buf, 0, this.count);
        }
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void close() {
    }
}
