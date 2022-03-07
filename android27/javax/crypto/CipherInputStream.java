package javax.crypto;

import java.io.*;

public class CipherInputStream extends FilterInputStream
{
    private Cipher cipher;
    private InputStream input;
    private byte[] ibuffer;
    private boolean done;
    private byte[] obuffer;
    private int ostart;
    private int ofinish;
    private boolean closed;
    
    private int getMoreData() throws IOException {
        if (this.done) {
            return -1;
        }
        final int read = this.input.read(this.ibuffer);
        if (read != -1) {
            try {
                this.obuffer = this.cipher.update(this.ibuffer, 0, read);
            }
            catch (IllegalStateException ex) {
                this.obuffer = null;
                throw ex;
            }
            this.ostart = 0;
            if (this.obuffer == null) {
                this.ofinish = 0;
            }
            else {
                this.ofinish = this.obuffer.length;
            }
            return this.ofinish;
        }
        this.done = true;
        try {
            this.obuffer = this.cipher.doFinal();
        }
        catch (IllegalBlockSizeException | BadPaddingException ex4) {
            final BadPaddingException ex3;
            final BadPaddingException ex2 = ex3;
            this.obuffer = null;
            throw new IOException(ex2);
        }
        if (this.obuffer == null) {
            return -1;
        }
        this.ostart = 0;
        return this.ofinish = this.obuffer.length;
    }
    
    public CipherInputStream(final InputStream input, final Cipher cipher) {
        super(input);
        this.ibuffer = new byte[512];
        this.done = false;
        this.ostart = 0;
        this.ofinish = 0;
        this.closed = false;
        this.input = input;
        this.cipher = cipher;
    }
    
    protected CipherInputStream(final InputStream input) {
        super(input);
        this.ibuffer = new byte[512];
        this.done = false;
        this.ostart = 0;
        this.ofinish = 0;
        this.closed = false;
        this.input = input;
        this.cipher = new NullCipher();
    }
    
    @Override
    public int read() throws IOException {
        if (this.ostart >= this.ofinish) {
            int i;
            for (i = 0; i == 0; i = this.getMoreData()) {}
            if (i == -1) {
                return -1;
            }
        }
        return this.obuffer[this.ostart++] & 0xFF;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.ostart >= this.ofinish) {
            int i;
            for (i = 0; i == 0; i = this.getMoreData()) {}
            if (i == -1) {
                return -1;
            }
        }
        if (n2 <= 0) {
            return 0;
        }
        int n3 = this.ofinish - this.ostart;
        if (n2 < n3) {
            n3 = n2;
        }
        if (array != null) {
            System.arraycopy(this.obuffer, this.ostart, array, n, n3);
        }
        this.ostart += n3;
        return n3;
    }
    
    @Override
    public long skip(long n) throws IOException {
        final int n2 = this.ofinish - this.ostart;
        if (n > n2) {
            n = n2;
        }
        if (n < 0L) {
            return 0L;
        }
        this.ostart += (int)n;
        return n;
    }
    
    @Override
    public int available() throws IOException {
        return this.ofinish - this.ostart;
    }
    
    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.input.close();
        if (!this.done) {
            try {
                this.cipher.doFinal();
            }
            catch (BadPaddingException ex) {}
            catch (IllegalBlockSizeException ex2) {}
        }
        this.ostart = 0;
        this.ofinish = 0;
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
}
