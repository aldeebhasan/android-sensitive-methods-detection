package java.security.cert;

import javax.security.auth.x500.*;
import sun.security.x509.*;
import java.util.*;
import sun.security.util.*;
import sun.misc.*;
import java.io.*;

public class CertificateRevokedException extends CertificateException
{
    private static final long serialVersionUID = 7839996631571608627L;
    private Date revocationDate;
    private final CRLReason reason;
    private final X500Principal authority;
    private transient Map<String, Extension> extensions;
    
    public CertificateRevokedException(final Date date, final CRLReason reason, final X500Principal authority, final Map<String, Extension> map) {
        if (date == null || reason == null || authority == null || map == null) {
            throw new NullPointerException();
        }
        this.revocationDate = new Date(date.getTime());
        this.reason = reason;
        this.authority = authority;
        (this.extensions = Collections.checkedMap(new HashMap<String, Extension>(), String.class, Extension.class)).putAll(map);
    }
    
    public Date getRevocationDate() {
        return (Date)this.revocationDate.clone();
    }
    
    public CRLReason getRevocationReason() {
        return this.reason;
    }
    
    public X500Principal getAuthorityName() {
        return this.authority;
    }
    
    public Date getInvalidityDate() {
        final Extension extension = this.getExtensions().get("2.5.29.24");
        if (extension == null) {
            return null;
        }
        try {
            return new Date(InvalidityDateExtension.toImpl(extension).get("DATE").getTime());
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public Map<String, Extension> getExtensions() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends Extension>)this.extensions);
    }
    
    @Override
    public String getMessage() {
        return "Certificate has been revoked, reason: " + this.reason + ", revocation date: " + this.revocationDate + ", authority: " + this.authority + ", extension OIDs: " + this.extensions.keySet();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.extensions.size());
        final Iterator<Map.Entry<String, Extension>> iterator = this.extensions.entrySet().iterator();
        while (iterator.hasNext()) {
            final Extension extension = iterator.next().getValue();
            objectOutputStream.writeObject(extension.getId());
            objectOutputStream.writeBoolean(extension.isCritical());
            final byte[] value = extension.getValue();
            objectOutputStream.writeInt(value.length);
            objectOutputStream.write(value);
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.revocationDate = new Date(this.revocationDate.getTime());
        final int int1 = objectInputStream.readInt();
        if (int1 == 0) {
            this.extensions = Collections.emptyMap();
        }
        else {
            if (int1 < 0) {
                throw new IOException("size cannot be negative");
            }
            this.extensions = new HashMap<String, Extension>((int1 > 20) ? 20 : int1);
        }
        for (int i = 0; i < int1; ++i) {
            final String s = (String)objectInputStream.readObject();
            this.extensions.put(s, sun.security.x509.Extension.newExtension(new ObjectIdentifier(s), objectInputStream.readBoolean(), IOUtils.readExactlyNBytes(objectInputStream, objectInputStream.readInt())));
        }
    }
}
