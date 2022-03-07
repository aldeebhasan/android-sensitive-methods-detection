package java.io;

import sun.nio.cs.*;
import java.nio.charset.*;

public class OutputStreamWriter extends Writer
{
    private final StreamEncoder se;
    
    public OutputStreamWriter(final OutputStream outputStream, final String s) throws UnsupportedEncodingException {
        super(outputStream);
        if (s == null) {
            throw new NullPointerException("charsetName");
        }
        this.se = StreamEncoder.forOutputStreamWriter(outputStream, this, s);
    }
    
    public OutputStreamWriter(final OutputStream outputStream) {
        super(outputStream);
        try {
            this.se = StreamEncoder.forOutputStreamWriter(outputStream, this, (String)null);
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error(ex);
        }
    }
    
    public OutputStreamWriter(final OutputStream outputStream, final Charset charset) {
        super(outputStream);
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.se = StreamEncoder.forOutputStreamWriter(outputStream, this, charset);
    }
    
    public OutputStreamWriter(final OutputStream outputStream, final CharsetEncoder charsetEncoder) {
        super(outputStream);
        if (charsetEncoder == null) {
            throw new NullPointerException("charset encoder");
        }
        this.se = StreamEncoder.forOutputStreamWriter(outputStream, this, charsetEncoder);
    }
    
    public String getEncoding() {
        return this.se.getEncoding();
    }
    
    void flushBuffer() throws IOException {
        this.se.flushBuffer();
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.se.write(n);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) throws IOException {
        this.se.write(array, n, n2);
    }
    
    @Override
    public void write(final String s, final int n, final int n2) throws IOException {
        this.se.write(s, n, n2);
    }
    
    @Override
    public void flush() throws IOException {
        this.se.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.se.close();
    }
}
