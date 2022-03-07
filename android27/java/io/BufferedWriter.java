package java.io;

import sun.security.action.*;
import java.security.*;

public class BufferedWriter extends Writer
{
    private Writer out;
    private char[] cb;
    private int nChars;
    private int nextChar;
    private static int defaultCharBufferSize;
    private String lineSeparator;
    
    public BufferedWriter(final Writer writer) {
        this(writer, BufferedWriter.defaultCharBufferSize);
    }
    
    public BufferedWriter(final Writer out, final int nChars) {
        super(out);
        if (nChars <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        this.out = out;
        this.cb = new char[nChars];
        this.nChars = nChars;
        this.nextChar = 0;
        this.lineSeparator = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("line.separator"));
    }
    
    private void ensureOpen() throws IOException {
        if (this.out == null) {
            throw new IOException("Stream closed");
        }
    }
    
    void flushBuffer() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (this.nextChar == 0) {
                return;
            }
            this.out.write(this.cb, 0, this.nextChar);
            this.nextChar = 0;
        }
    }
    
    @Override
    public void write(final int n) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (this.nextChar >= this.nChars) {
                this.flushBuffer();
            }
            this.cb[this.nextChar++] = (char)n;
        }
    }
    
    private int min(final int n, final int n2) {
        if (n < n2) {
            return n;
        }
        return n2;
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return;
            }
            if (n2 >= this.nChars) {
                this.flushBuffer();
                this.out.write(array, n, n2);
                return;
            }
            int i = n;
            final int n3 = n + n2;
            while (i < n3) {
                final int min = this.min(this.nChars - this.nextChar, n3 - i);
                System.arraycopy(array, i, this.cb, this.nextChar, min);
                i += min;
                this.nextChar += min;
                if (this.nextChar >= this.nChars) {
                    this.flushBuffer();
                }
            }
        }
    }
    
    @Override
    public void write(final String s, final int n, final int n2) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            int i = n;
            final int n3 = n + n2;
            while (i < n3) {
                final int min = this.min(this.nChars - this.nextChar, n3 - i);
                s.getChars(i, i + min, this.cb, this.nextChar);
                i += min;
                this.nextChar += min;
                if (this.nextChar >= this.nChars) {
                    this.flushBuffer();
                }
            }
        }
    }
    
    public void newLine() throws IOException {
        this.write(this.lineSeparator);
    }
    
    @Override
    public void flush() throws IOException {
        synchronized (this.lock) {
            this.flushBuffer();
            this.out.flush();
        }
    }
    
    @Override
    public void close() throws IOException {
        synchronized (this.lock) {
            if (this.out == null) {
                return;
            }
            try (final Writer out = this.out) {
                this.flushBuffer();
            }
            finally {
                this.out = null;
                this.cb = null;
            }
        }
    }
    
    static {
        BufferedWriter.defaultCharBufferSize = 8192;
    }
}
