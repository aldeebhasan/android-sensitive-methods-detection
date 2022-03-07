package java.security;

import java.nio.*;
import java.io.*;
import sun.security.util.*;
import javax.crypto.*;

public abstract class MessageDigest extends MessageDigestSpi
{
    private static final Debug pdebug;
    private static final boolean skipDebug;
    private String algorithm;
    private static final int INITIAL = 0;
    private static final int IN_PROGRESS = 1;
    private int state;
    private Provider provider;
    
    protected MessageDigest(final String algorithm) {
        this.state = 0;
        this.algorithm = algorithm;
    }
    
    public static MessageDigest getInstance(final String s) throws NoSuchAlgorithmException {
        try {
            final Object[] impl = Security.getImpl(s, "MessageDigest", (String)null);
            MessageDigest messageDigest;
            if (impl[0] instanceof MessageDigest) {
                messageDigest = (MessageDigest)impl[0];
            }
            else {
                messageDigest = new Delegate((MessageDigestSpi)impl[0], s);
            }
            messageDigest.provider = (Provider)impl[1];
            if (!MessageDigest.skipDebug && MessageDigest.pdebug != null) {
                MessageDigest.pdebug.println("MessageDigest." + s + " algorithm from: " + messageDigest.provider.getName());
            }
            return messageDigest;
        }
        catch (NoSuchProviderException ex) {
            throw new NoSuchAlgorithmException(s + " not found");
        }
    }
    
    public static MessageDigest getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (s2 == null || s2.length() == 0) {
            throw new IllegalArgumentException("missing provider");
        }
        final Object[] impl = Security.getImpl(s, "MessageDigest", s2);
        if (impl[0] instanceof MessageDigest) {
            final MessageDigest messageDigest = (MessageDigest)impl[0];
            messageDigest.provider = (Provider)impl[1];
            return messageDigest;
        }
        final Delegate delegate = new Delegate((MessageDigestSpi)impl[0], s);
        delegate.provider = (Provider)impl[1];
        return delegate;
    }
    
    public static MessageDigest getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("missing provider");
        }
        final Object[] impl = Security.getImpl(s, "MessageDigest", provider);
        if (impl[0] instanceof MessageDigest) {
            final MessageDigest messageDigest = (MessageDigest)impl[0];
            messageDigest.provider = (Provider)impl[1];
            return messageDigest;
        }
        final Delegate delegate = new Delegate((MessageDigestSpi)impl[0], s);
        delegate.provider = (Provider)impl[1];
        return delegate;
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public void update(final byte b) {
        this.engineUpdate(b);
        this.state = 1;
    }
    
    public void update(final byte[] array, final int n, final int n2) {
        if (array == null) {
            throw new IllegalArgumentException("No input buffer given");
        }
        if (array.length - n < n2) {
            throw new IllegalArgumentException("Input buffer too short");
        }
        this.engineUpdate(array, n, n2);
        this.state = 1;
    }
    
    public void update(final byte[] array) {
        this.engineUpdate(array, 0, array.length);
        this.state = 1;
    }
    
    public final void update(final ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        this.engineUpdate(byteBuffer);
        this.state = 1;
    }
    
    public byte[] digest() {
        final byte[] engineDigest = this.engineDigest();
        this.state = 0;
        return engineDigest;
    }
    
    public int digest(final byte[] array, final int n, final int n2) throws DigestException {
        if (array == null) {
            throw new IllegalArgumentException("No output buffer given");
        }
        if (array.length - n < n2) {
            throw new IllegalArgumentException("Output buffer too small for specified offset and length");
        }
        final int engineDigest = this.engineDigest(array, n, n2);
        this.state = 0;
        return engineDigest;
    }
    
    public byte[] digest(final byte[] array) {
        this.update(array);
        return this.digest();
    }
    
    @Override
    public String toString() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(byteArrayOutputStream);
        printStream.print(this.algorithm + " Message Digest from " + this.provider.getName() + ", ");
        switch (this.state) {
            case 0: {
                printStream.print("<initialized>");
                break;
            }
            case 1: {
                printStream.print("<in progress>");
                break;
            }
        }
        printStream.println();
        return byteArrayOutputStream.toString();
    }
    
    public static boolean isEqual(final byte[] array, final byte[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        final int length2 = array2.length;
        if (length2 == 0) {
            return length == 0;
        }
        int n = 0x0 | length - length2;
        for (int i = 0; i < length; ++i) {
            n |= (array[i] ^ array2[(i - length2 >>> 31) * i]);
        }
        return n == 0;
    }
    
    public void reset() {
        this.engineReset();
        this.state = 0;
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public final int getDigestLength() {
        final int engineGetDigestLength = this.engineGetDigestLength();
        if (engineGetDigestLength == 0) {
            try {
                return ((MessageDigest)this.clone()).digest().length;
            }
            catch (CloneNotSupportedException ex) {
                return engineGetDigestLength;
            }
        }
        return engineGetDigestLength;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }
    
    static {
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("messagedigest"));
    }
    
    static class Delegate extends MessageDigest implements MessageDigestSpi2
    {
        private MessageDigestSpi digestSpi;
        
        public Delegate(final MessageDigestSpi digestSpi, final String s) {
            super(s);
            this.digestSpi = digestSpi;
        }
        
        @Override
        public Object clone() throws CloneNotSupportedException {
            if (this.digestSpi instanceof Cloneable) {
                final Delegate delegate = new Delegate((MessageDigestSpi)this.digestSpi.clone(), this.algorithm);
                delegate.provider = this.provider;
                delegate.state = this.state;
                return delegate;
            }
            throw new CloneNotSupportedException();
        }
        
        @Override
        protected int engineGetDigestLength() {
            return this.digestSpi.engineGetDigestLength();
        }
        
        @Override
        protected void engineUpdate(final byte b) {
            this.digestSpi.engineUpdate(b);
        }
        
        @Override
        protected void engineUpdate(final byte[] array, final int n, final int n2) {
            this.digestSpi.engineUpdate(array, n, n2);
        }
        
        @Override
        protected void engineUpdate(final ByteBuffer byteBuffer) {
            this.digestSpi.engineUpdate(byteBuffer);
        }
        
        @Override
        public void engineUpdate(final SecretKey secretKey) throws InvalidKeyException {
            if (this.digestSpi instanceof MessageDigestSpi2) {
                ((MessageDigestSpi2)this.digestSpi).engineUpdate(secretKey);
                return;
            }
            throw new UnsupportedOperationException("Digest does not support update of SecretKey object");
        }
        
        @Override
        protected byte[] engineDigest() {
            return this.digestSpi.engineDigest();
        }
        
        @Override
        protected int engineDigest(final byte[] array, final int n, final int n2) throws DigestException {
            return this.digestSpi.engineDigest(array, n, n2);
        }
        
        @Override
        protected void engineReset() {
            this.digestSpi.engineReset();
        }
    }
}
