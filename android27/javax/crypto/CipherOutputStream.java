package javax.crypto;

import java.io.*;

public class CipherOutputStream extends FilterOutputStream
{
    private Cipher cipher;
    private OutputStream output;
    private byte[] ibuffer;
    private byte[] obuffer;
    private boolean closed;
    
    public CipherOutputStream(final OutputStream output, final Cipher cipher) {
        super(output);
        this.ibuffer = new byte[1];
        this.closed = false;
        this.output = output;
        this.cipher = cipher;
    }
    
    protected CipherOutputStream(final OutputStream output) {
        super(output);
        this.ibuffer = new byte[1];
        this.closed = false;
        this.output = output;
        this.cipher = new NullCipher();
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.ibuffer[0] = (byte)n;
        this.obuffer = this.cipher.update(this.ibuffer, 0, 1);
        if (this.obuffer != null) {
            this.output.write(this.obuffer);
            this.obuffer = null;
        }
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.obuffer = this.cipher.update(array, n, n2);
        if (this.obuffer != null) {
            this.output.write(this.obuffer);
            this.obuffer = null;
        }
    }
    
    @Override
    public void flush() throws IOException {
        if (this.obuffer != null) {
            this.output.write(this.obuffer);
            this.obuffer = null;
        }
        this.output.flush();
    }
    
    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        try {
            this.obuffer = this.cipher.doFinal();
        }
        catch (IllegalBlockSizeException | BadPaddingException ex) {
            this.obuffer = null;
        }
        try {
            this.flush();
        }
        catch (IOException ex2) {}
        this.out.close();
    }
}
