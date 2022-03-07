package java.security;

import java.net.*;
import sun.misc.*;
import java.io.*;
import java.security.cert.*;
import java.util.*;

public class CodeSource implements Serializable
{
    private static final long serialVersionUID = 4977541819976013951L;
    private URL location;
    private transient CodeSigner[] signers;
    private transient Certificate[] certs;
    private transient SocketPermission sp;
    private transient CertificateFactory factory;
    
    public CodeSource(final URL location, final Certificate[] array) {
        this.signers = null;
        this.certs = null;
        this.factory = null;
        this.location = location;
        if (array != null) {
            this.certs = array.clone();
        }
    }
    
    public CodeSource(final URL location, final CodeSigner[] array) {
        this.signers = null;
        this.certs = null;
        this.factory = null;
        this.location = location;
        if (array != null) {
            this.signers = array.clone();
        }
    }
    
    @Override
    public int hashCode() {
        if (this.location != null) {
            return this.location.hashCode();
        }
        return 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CodeSource)) {
            return false;
        }
        final CodeSource codeSource = (CodeSource)o;
        if (this.location == null) {
            if (codeSource.location != null) {
                return false;
            }
        }
        else if (!this.location.equals(codeSource.location)) {
            return false;
        }
        return this.matchCerts(codeSource, true);
    }
    
    public final URL getLocation() {
        return this.location;
    }
    
    public final Certificate[] getCertificates() {
        if (this.certs != null) {
            return this.certs.clone();
        }
        if (this.signers != null) {
            final ArrayList list = new ArrayList();
            for (int i = 0; i < this.signers.length; ++i) {
                list.addAll(this.signers[i].getSignerCertPath().getCertificates());
            }
            this.certs = list.toArray(new Certificate[list.size()]);
            return this.certs.clone();
        }
        return null;
    }
    
    public final CodeSigner[] getCodeSigners() {
        if (this.signers != null) {
            return this.signers.clone();
        }
        if (this.certs != null) {
            this.signers = this.convertCertArrayToSignerArray(this.certs);
            return this.signers.clone();
        }
        return null;
    }
    
    public boolean implies(final CodeSource codeSource) {
        return codeSource != null && this.matchCerts(codeSource, false) && this.matchLocation(codeSource);
    }
    
    private boolean matchCerts(final CodeSource codeSource, final boolean b) {
        if (this.certs == null && this.signers == null) {
            return !b || (codeSource.certs == null && codeSource.signers == null);
        }
        if (this.signers != null && codeSource.signers != null) {
            if (b && this.signers.length != codeSource.signers.length) {
                return false;
            }
            for (int i = 0; i < this.signers.length; ++i) {
                boolean b2 = false;
                for (int j = 0; j < codeSource.signers.length; ++j) {
                    if (this.signers[i].equals(codeSource.signers[j])) {
                        b2 = true;
                        break;
                    }
                }
                if (!b2) {
                    return false;
                }
            }
            return true;
        }
        else {
            if (this.certs == null || codeSource.certs == null) {
                return false;
            }
            if (b && this.certs.length != codeSource.certs.length) {
                return false;
            }
            for (int k = 0; k < this.certs.length; ++k) {
                boolean b3 = false;
                for (int l = 0; l < codeSource.certs.length; ++l) {
                    if (this.certs[k].equals(codeSource.certs[l])) {
                        b3 = true;
                        break;
                    }
                }
                if (!b3) {
                    return false;
                }
            }
            return true;
        }
    }
    
    private boolean matchLocation(final CodeSource codeSource) {
        if (this.location == null) {
            return true;
        }
        if (codeSource == null || codeSource.location == null) {
            return false;
        }
        if (this.location.equals(codeSource.location)) {
            return true;
        }
        if (!this.location.getProtocol().equalsIgnoreCase(codeSource.location.getProtocol())) {
            return false;
        }
        final int port = this.location.getPort();
        if (port != -1) {
            final int port2 = codeSource.location.getPort();
            if (port != ((port2 != -1) ? port2 : codeSource.location.getDefaultPort())) {
                return false;
            }
        }
        if (this.location.getFile().endsWith("/-")) {
            if (!codeSource.location.getFile().startsWith(this.location.getFile().substring(0, this.location.getFile().length() - 1))) {
                return false;
            }
        }
        else if (this.location.getFile().endsWith("/*")) {
            final int lastIndex = codeSource.location.getFile().lastIndexOf(47);
            if (lastIndex == -1) {
                return false;
            }
            if (!codeSource.location.getFile().substring(0, lastIndex + 1).equals(this.location.getFile().substring(0, this.location.getFile().length() - 1))) {
                return false;
            }
        }
        else if (!codeSource.location.getFile().equals(this.location.getFile()) && !codeSource.location.getFile().equals(this.location.getFile() + "/")) {
            return false;
        }
        if (this.location.getRef() != null && !this.location.getRef().equals(codeSource.location.getRef())) {
            return false;
        }
        final String host = this.location.getHost();
        final String host2 = codeSource.location.getHost();
        if (host != null) {
            if ("".equals(host) || "localhost".equals(host)) {
                if ("".equals(host2)) {
                    return true;
                }
                if ("localhost".equals(host2)) {
                    return true;
                }
            }
            if (!host.equals(host2)) {
                if (host2 == null) {
                    return false;
                }
                if (this.sp == null) {
                    this.sp = new SocketPermission(host, "resolve");
                }
                if (codeSource.sp == null) {
                    codeSource.sp = new SocketPermission(host2, "resolve");
                }
                if (!this.sp.implies(codeSource.sp)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.location);
        if (this.certs != null && this.certs.length > 0) {
            for (int i = 0; i < this.certs.length; ++i) {
                sb.append(" " + this.certs[i]);
            }
        }
        else if (this.signers != null && this.signers.length > 0) {
            for (int j = 0; j < this.signers.length; ++j) {
                sb.append(" " + this.signers[j]);
            }
        }
        else {
            sb.append(" <no signer certificates>");
        }
        sb.append(")");
        return sb.toString();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        if (this.certs == null || this.certs.length == 0) {
            objectOutputStream.writeInt(0);
        }
        else {
            objectOutputStream.writeInt(this.certs.length);
            for (int i = 0; i < this.certs.length; ++i) {
                final Certificate certificate = this.certs[i];
                try {
                    objectOutputStream.writeUTF(certificate.getType());
                    final byte[] encoded = certificate.getEncoded();
                    objectOutputStream.writeInt(encoded.length);
                    objectOutputStream.write(encoded);
                }
                catch (CertificateEncodingException ex) {
                    throw new IOException(ex.getMessage());
                }
            }
        }
        if (this.signers != null && this.signers.length > 0) {
            objectOutputStream.writeObject(this.signers);
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Hashtable<String, CertificateFactory> hashtable = null;
        ArrayList list = null;
        objectInputStream.defaultReadObject();
        final int int1 = objectInputStream.readInt();
        if (int1 > 0) {
            hashtable = new Hashtable<String, CertificateFactory>(3);
            list = new ArrayList<Certificate>((int1 > 20) ? 20 : int1);
        }
        else if (int1 < 0) {
            throw new IOException("size cannot be negative");
        }
        for (int i = 0; i < int1; ++i) {
            final String utf = objectInputStream.readUTF();
            CertificateFactory instance;
            if (hashtable.containsKey(utf)) {
                instance = hashtable.get(utf);
            }
            else {
                try {
                    instance = CertificateFactory.getInstance(utf);
                }
                catch (CertificateException ex2) {
                    throw new ClassNotFoundException("Certificate factory for " + utf + " not found");
                }
                hashtable.put(utf, instance);
            }
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(IOUtils.readExactlyNBytes(objectInputStream, objectInputStream.readInt()));
            try {
                list.add(instance.generateCertificate(byteArrayInputStream));
            }
            catch (CertificateException ex) {
                throw new IOException(ex.getMessage());
            }
            byteArrayInputStream.close();
        }
        if (list != null) {
            this.certs = list.toArray(new Certificate[int1]);
        }
        try {
            this.signers = ((CodeSigner[])objectInputStream.readObject()).clone();
        }
        catch (IOException ex3) {}
    }
    
    private CodeSigner[] convertCertArrayToSignerArray(final Certificate[] array) {
        if (array == null) {
            return null;
        }
        try {
            if (this.factory == null) {
                this.factory = CertificateFactory.getInstance("X.509");
            }
            int i = 0;
            final ArrayList<CodeSigner> list = new ArrayList<CodeSigner>();
            while (i < array.length) {
                final ArrayList<Certificate> list2 = new ArrayList<Certificate>();
                list2.add(array[i++]);
                int n;
                for (n = i; n < array.length && array[n] instanceof X509Certificate && ((X509Certificate)array[n]).getBasicConstraints() != -1; ++n) {
                    list2.add(array[n]);
                }
                i = n;
                list.add(new CodeSigner(this.factory.generateCertPath(list2), null));
            }
            if (list.isEmpty()) {
                return null;
            }
            return list.toArray(new CodeSigner[list.size()]);
        }
        catch (CertificateException ex) {
            return null;
        }
    }
}
