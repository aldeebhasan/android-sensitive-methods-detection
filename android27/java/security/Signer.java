package java.security;

@Deprecated
public abstract class Signer extends Identity
{
    private static final long serialVersionUID = -1763464102261361480L;
    private PrivateKey privateKey;
    
    protected Signer() {
    }
    
    public Signer(final String s) {
        super(s);
    }
    
    public Signer(final String s, final IdentityScope identityScope) throws KeyManagementException {
        super(s, identityScope);
    }
    
    public PrivateKey getPrivateKey() {
        check("getSignerPrivateKey");
        return this.privateKey;
    }
    
    public final void setKeyPair(final KeyPair keyPair) throws InvalidParameterException, KeyException {
        check("setSignerKeyPair");
        final PublicKey public1 = keyPair.getPublic();
        final PrivateKey private1 = keyPair.getPrivate();
        if (public1 == null || private1 == null) {
            throw new InvalidParameterException();
        }
        try {
            AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction<Void>() {
                @Override
                public Void run() throws KeyManagementException {
                    Signer.this.setPublicKey(public1);
                    return null;
                }
            });
        }
        catch (PrivilegedActionException ex) {
            throw (KeyManagementException)ex.getException();
        }
        this.privateKey = private1;
    }
    
    @Override
    String printKeys() {
        String s;
        if (this.getPublicKey() != null && this.privateKey != null) {
            s = "\tpublic and private keys initialized";
        }
        else {
            s = "\tno keys";
        }
        return s;
    }
    
    @Override
    public String toString() {
        return "[Signer]" + super.toString();
    }
    
    private static void check(final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(s);
        }
    }
}
