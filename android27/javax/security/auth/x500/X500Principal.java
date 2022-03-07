package javax.security.auth.x500;

import java.security.*;
import sun.security.x509.*;
import java.util.*;
import sun.security.util.*;
import java.io.*;

public final class X500Principal implements Principal, Serializable
{
    private static final long serialVersionUID = -500463348111345721L;
    public static final String RFC1779 = "RFC1779";
    public static final String RFC2253 = "RFC2253";
    public static final String CANONICAL = "CANONICAL";
    private transient X500Name thisX500Name;
    
    X500Principal(final X500Name thisX500Name) {
        this.thisX500Name = thisX500Name;
    }
    
    public X500Principal(final String s) {
        this(s, Collections.emptyMap());
    }
    
    public X500Principal(final String s, final Map<String, String> map) {
        if (s == null) {
            throw new NullPointerException(ResourcesMgr.getString("provided.null.name"));
        }
        if (map == null) {
            throw new NullPointerException(ResourcesMgr.getString("provided.null.keyword.map"));
        }
        try {
            this.thisX500Name = new X500Name(s, map);
        }
        catch (Exception ex2) {
            final IllegalArgumentException ex = new IllegalArgumentException("improperly specified input name: " + s);
            ex.initCause(ex2);
            throw ex;
        }
    }
    
    public X500Principal(final byte[] array) {
        try {
            this.thisX500Name = new X500Name(array);
        }
        catch (Exception ex2) {
            final IllegalArgumentException ex = new IllegalArgumentException("improperly specified input name");
            ex.initCause(ex2);
            throw ex;
        }
    }
    
    public X500Principal(final InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException("provided null input stream");
        }
        try {
            if (inputStream.markSupported()) {
                inputStream.mark(inputStream.available() + 1);
            }
            this.thisX500Name = new X500Name(new DerValue(inputStream).data);
        }
        catch (Exception ex2) {
            if (inputStream.markSupported()) {
                try {
                    inputStream.reset();
                }
                catch (IOException ex4) {
                    final IllegalArgumentException ex = new IllegalArgumentException("improperly specified input stream and unable to reset input stream");
                    ex.initCause(ex2);
                    throw ex;
                }
            }
            final IllegalArgumentException ex3 = new IllegalArgumentException("improperly specified input stream");
            ex3.initCause(ex2);
            throw ex3;
        }
    }
    
    @Override
    public String getName() {
        return this.getName("RFC2253");
    }
    
    public String getName(final String s) {
        if (s != null) {
            if (s.equalsIgnoreCase("RFC1779")) {
                return this.thisX500Name.getRFC1779Name();
            }
            if (s.equalsIgnoreCase("RFC2253")) {
                return this.thisX500Name.getRFC2253Name();
            }
            if (s.equalsIgnoreCase("CANONICAL")) {
                return this.thisX500Name.getRFC2253CanonicalName();
            }
        }
        throw new IllegalArgumentException("invalid format specified");
    }
    
    public String getName(final String s, final Map<String, String> map) {
        if (map == null) {
            throw new NullPointerException(ResourcesMgr.getString("provided.null.OID.map"));
        }
        if (s != null) {
            if (s.equalsIgnoreCase("RFC1779")) {
                return this.thisX500Name.getRFC1779Name(map);
            }
            if (s.equalsIgnoreCase("RFC2253")) {
                return this.thisX500Name.getRFC2253Name(map);
            }
        }
        throw new IllegalArgumentException("invalid format specified");
    }
    
    public byte[] getEncoded() {
        try {
            return this.thisX500Name.getEncoded();
        }
        catch (IOException ex) {
            throw new RuntimeException("unable to get encoding", ex);
        }
    }
    
    @Override
    public String toString() {
        return this.thisX500Name.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof X500Principal && this.thisX500Name.equals(((X500Principal)o).thisX500Name));
    }
    
    @Override
    public int hashCode() {
        return this.thisX500Name.hashCode();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.thisX500Name.getEncodedInternal());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, NotActiveException, ClassNotFoundException {
        this.thisX500Name = new X500Name((byte[])objectInputStream.readObject());
    }
}
