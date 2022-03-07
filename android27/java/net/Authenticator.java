package java.net;

import java.security.*;

public abstract class Authenticator
{
    private static Authenticator theAuthenticator;
    private String requestingHost;
    private InetAddress requestingSite;
    private int requestingPort;
    private String requestingProtocol;
    private String requestingPrompt;
    private String requestingScheme;
    private URL requestingURL;
    private RequestorType requestingAuthType;
    
    private void reset() {
        this.requestingHost = null;
        this.requestingSite = null;
        this.requestingPort = -1;
        this.requestingProtocol = null;
        this.requestingPrompt = null;
        this.requestingScheme = null;
        this.requestingURL = null;
        this.requestingAuthType = RequestorType.SERVER;
    }
    
    public static synchronized void setDefault(final Authenticator theAuthenticator) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new NetPermission("setDefaultAuthenticator"));
        }
        Authenticator.theAuthenticator = theAuthenticator;
    }
    
    public static PasswordAuthentication requestPasswordAuthentication(final InetAddress requestingSite, final int requestingPort, final String requestingProtocol, final String requestingPrompt, final String requestingScheme) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new NetPermission("requestPasswordAuthentication"));
        }
        final Authenticator theAuthenticator = Authenticator.theAuthenticator;
        if (theAuthenticator == null) {
            return null;
        }
        synchronized (theAuthenticator) {
            theAuthenticator.reset();
            theAuthenticator.requestingSite = requestingSite;
            theAuthenticator.requestingPort = requestingPort;
            theAuthenticator.requestingProtocol = requestingProtocol;
            theAuthenticator.requestingPrompt = requestingPrompt;
            theAuthenticator.requestingScheme = requestingScheme;
            return theAuthenticator.getPasswordAuthentication();
        }
    }
    
    public static PasswordAuthentication requestPasswordAuthentication(final String requestingHost, final InetAddress requestingSite, final int requestingPort, final String requestingProtocol, final String requestingPrompt, final String requestingScheme) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new NetPermission("requestPasswordAuthentication"));
        }
        final Authenticator theAuthenticator = Authenticator.theAuthenticator;
        if (theAuthenticator == null) {
            return null;
        }
        synchronized (theAuthenticator) {
            theAuthenticator.reset();
            theAuthenticator.requestingHost = requestingHost;
            theAuthenticator.requestingSite = requestingSite;
            theAuthenticator.requestingPort = requestingPort;
            theAuthenticator.requestingProtocol = requestingProtocol;
            theAuthenticator.requestingPrompt = requestingPrompt;
            theAuthenticator.requestingScheme = requestingScheme;
            return theAuthenticator.getPasswordAuthentication();
        }
    }
    
    public static PasswordAuthentication requestPasswordAuthentication(final String requestingHost, final InetAddress requestingSite, final int requestingPort, final String requestingProtocol, final String requestingPrompt, final String requestingScheme, final URL requestingURL, final RequestorType requestingAuthType) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new NetPermission("requestPasswordAuthentication"));
        }
        final Authenticator theAuthenticator = Authenticator.theAuthenticator;
        if (theAuthenticator == null) {
            return null;
        }
        synchronized (theAuthenticator) {
            theAuthenticator.reset();
            theAuthenticator.requestingHost = requestingHost;
            theAuthenticator.requestingSite = requestingSite;
            theAuthenticator.requestingPort = requestingPort;
            theAuthenticator.requestingProtocol = requestingProtocol;
            theAuthenticator.requestingPrompt = requestingPrompt;
            theAuthenticator.requestingScheme = requestingScheme;
            theAuthenticator.requestingURL = requestingURL;
            theAuthenticator.requestingAuthType = requestingAuthType;
            return theAuthenticator.getPasswordAuthentication();
        }
    }
    
    protected final String getRequestingHost() {
        return this.requestingHost;
    }
    
    protected final InetAddress getRequestingSite() {
        return this.requestingSite;
    }
    
    protected final int getRequestingPort() {
        return this.requestingPort;
    }
    
    protected final String getRequestingProtocol() {
        return this.requestingProtocol;
    }
    
    protected final String getRequestingPrompt() {
        return this.requestingPrompt;
    }
    
    protected final String getRequestingScheme() {
        return this.requestingScheme;
    }
    
    protected PasswordAuthentication getPasswordAuthentication() {
        return null;
    }
    
    protected URL getRequestingURL() {
        return this.requestingURL;
    }
    
    protected RequestorType getRequestorType() {
        return this.requestingAuthType;
    }
    
    public enum RequestorType
    {
        PROXY, 
        SERVER;
    }
}
