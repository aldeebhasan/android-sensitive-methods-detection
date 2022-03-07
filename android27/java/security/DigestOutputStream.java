package java.security;

import java.io.*;

public class DigestOutputStream extends FilterOutputStream
{
    private boolean on;
    protected MessageDigest digest;
    
    public DigestOutputStream(final OutputStream outputStream, final MessageDigest messageDigest) {
        super(outputStream);
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
    public void write(final int n) throws IOException {
        this.out.write(n);
        if (this.on) {
            this.digest.update((byte)n);
        }
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
        if (this.on) {
            this.digest.update(array, n, n2);
        }
    }
    
    public void on(final boolean on) {
        this.on = on;
    }
    
    @Override
    public String toString() {
        return "[Digest Output Stream] " + this.digest.toString();
    }
}
