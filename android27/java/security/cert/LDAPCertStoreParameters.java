package java.security.cert;

public class LDAPCertStoreParameters implements CertStoreParameters
{
    private static final int LDAP_DEFAULT_PORT = 389;
    private int port;
    private String serverName;
    
    public LDAPCertStoreParameters(final String serverName, final int port) {
        if (serverName == null) {
            throw new NullPointerException();
        }
        this.serverName = serverName;
        this.port = port;
    }
    
    public LDAPCertStoreParameters(final String s) {
        this(s, 389);
    }
    
    public LDAPCertStoreParameters() {
        this("localhost", 389);
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public int getPort() {
        return this.port;
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString(), ex);
        }
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("LDAPCertStoreParameters: [\n");
        sb.append("  serverName: " + this.serverName + "\n");
        sb.append("  port: " + this.port + "\n");
        sb.append("]");
        return sb.toString();
    }
}
