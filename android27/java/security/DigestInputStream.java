package java.security;

import java.io.*;

public class DigestInputStream extends FilterInputStream
{
    private boolean on;
    protected MessageDigest digest;
    
    public DigestInputStream(final InputStream inputStream, final MessageDigest messageDigest) {
        super(inputStream);
        this.on = true;
        this.setMessageDigest(messageDigest);
    }
    
    public MessageDigest getMessageDigest() {
        return this.digest;
    }
    
    public void setMessageDigest(final MessageDigest digest) {
        this.digest = digest;
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.in.read();
        if (this.on && read != -1) {
            this.digest.update((byte)read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int read = this.in.read(array, n, n2);
        if (this.on && read != -1) {
            this.digest.update(array, n, read);
        }
        return read;
    }
    
    public void on(final boolean on) {
        this.on = on;
    }
    
    @Override
    public String toString() {
        return "[Digest Input Stream] " + this.digest.toString();
    }
}
