package java.io;

import sun.nio.cs.*;
import java.nio.charset.*;

public class InputStreamReader extends Reader
{
    private final StreamDecoder sd;
    
    public InputStreamReader(final InputStream inputStream) {
        super(inputStream);
        try {
            this.sd = StreamDecoder.forInputStreamReader(inputStream, this, (String)null);
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error(ex);
        }
    }
    
    public InputStreamReader(final InputStream inputStream, final String s) throws UnsupportedEncodingException {
        super(inputStream);
        if (s == null) {
            throw new NullPointerException("charsetName");
        }
        this.sd = StreamDecoder.forInputStreamReader(inputStream, this, s);
    }
    
    public InputStreamReader(final InputStream inputStream, final Charset charset) {
        super(inputStream);
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.sd = StreamDecoder.forInputStreamReader(inputStream, this, charset);
    }
    
    public InputStreamReader(final InputStream inputStream, final CharsetDecoder charsetDecoder) {
        super(inputStream);
        if (charsetDecoder == null) {
            throw new NullPointerException("charset decoder");
        }
        this.sd = StreamDecoder.forInputStreamReader(inputStream, this, charsetDecoder);
    }
    
    public String getEncoding() {
        return this.sd.getEncoding();
    }
    
    @Override
    public int read() throws IOException {
        return this.sd.read();
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) throws IOException {
        return this.sd.read(array, n, n2);
    }
    
    @Override
    public boolean ready() throws IOException {
        return this.sd.ready();
    }
    
    @Override
    public void close() throws IOException {
        this.sd.close();
    }
}
