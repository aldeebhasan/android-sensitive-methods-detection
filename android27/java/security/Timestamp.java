package java.security;

import java.util.*;
import java.security.cert.*;
import java.io.*;

public final class Timestamp implements Serializable
{
    private static final long serialVersionUID = -5502683707821851294L;
    private Date timestamp;
    private CertPath signerCertPath;
    private transient int myhash;
    
    public Timestamp(final Date date, final CertPath signerCertPath) {
        this.myhash = -1;
        if (date == null || signerCertPath == null) {
            throw new NullPointerException();
        }
        this.timestamp = new Date(date.getTime());
        this.signerCertPath = signerCertPath;
    }
    
    public Date getTimestamp() {
        return new Date(this.timestamp.getTime());
    }
    
    public CertPath getSignerCertPath() {
        return this.signerCertPath;
    }
    
    @Override
    public int hashCode() {
        if (this.myhash == -1) {
            this.myhash = this.timestamp.hashCode() + this.signerCertPath.hashCode();
        }
        return this.myhash;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof Timestamp)) {
            return false;
        }
        final Timestamp timestamp = (Timestamp)o;
        return this == timestamp || (this.timestamp.equals(timestamp.getTimestamp()) && this.signerCertPath.equals(timestamp.getSignerCertPath()));
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("(");
        sb.append("timestamp: " + this.timestamp);
        final List<? extends Certificate> certificates = this.signerCertPath.getCertificates();
        if (!certificates.isEmpty()) {
            sb.append("TSA: " + certificates.get(0));
        }
        else {
            sb.append("TSA: <empty>");
        }
        sb.append(")");
        return sb.toString();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.myhash = -1;
        this.timestamp = new Date(this.timestamp.getTime());
    }
}
