package java.security.cert;

import java.security.*;
import java.io.*;

public class CertPathValidatorException extends GeneralSecurityException
{
    private static final long serialVersionUID = -3083180014971893139L;
    private int index;
    private CertPath certPath;
    private Reason reason;
    
    public CertPathValidatorException() {
        this(null, null);
    }
    
    public CertPathValidatorException(final String s) {
        this(s, null);
    }
    
    public CertPathValidatorException(final Throwable t) {
        this((t == null) ? null : t.toString(), t);
    }
    
    public CertPathValidatorException(final String s, final Throwable t) {
        this(s, t, null, -1);
    }
    
    public CertPathValidatorException(final String s, final Throwable t, final CertPath certPath, final int n) {
        this(s, t, certPath, n, BasicReason.UNSPECIFIED);
    }
    
    public CertPathValidatorException(final String s, final Throwable t, final CertPath certPath, final int index, final Reason reason) {
        super(s, t);
        this.index = -1;
        this.reason = BasicReason.UNSPECIFIED;
        if (certPath == null && index != -1) {
            throw new IllegalArgumentException();
        }
        if (index < -1 || (certPath != null && index >= certPath.getCertificates().size())) {
            throw new IndexOutOfBoundsException();
        }
        if (reason == null) {
            throw new NullPointerException("reason can't be null");
        }
        this.certPath = certPath;
        this.index = index;
        this.reason = reason;
    }
    
    public CertPath getCertPath() {
        return this.certPath;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public Reason getReason() {
        return this.reason;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        if (this.reason == null) {
            this.reason = BasicReason.UNSPECIFIED;
        }
        if (this.certPath == null && this.index != -1) {
            throw new InvalidObjectException("certpath is null and index != -1");
        }
        if (this.index < -1 || (this.certPath != null && this.index >= this.certPath.getCertificates().size())) {
            throw new InvalidObjectException("index out of range");
        }
    }
    
    public enum BasicReason implements Reason
    {
        UNSPECIFIED, 
        EXPIRED, 
        NOT_YET_VALID, 
        REVOKED, 
        UNDETERMINED_REVOCATION_STATUS, 
        INVALID_SIGNATURE, 
        ALGORITHM_CONSTRAINED;
    }
    
    public interface Reason extends Serializable
    {
    }
}
