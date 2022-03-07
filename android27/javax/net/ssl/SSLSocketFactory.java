package javax.net.ssl;

import javax.net.*;
import java.security.*;
import java.net.*;
import java.io.*;
import sun.security.action.*;
import java.util.*;

public abstract class SSLSocketFactory extends SocketFactory
{
    private static SSLSocketFactory theFactory;
    private static boolean propertyChecked;
    static final boolean DEBUG;
    
    private static void log(final String s) {
        if (SSLSocketFactory.DEBUG) {
            System.out.println(s);
        }
    }
    
    public static synchronized SocketFactory getDefault() {
        if (SSLSocketFactory.theFactory != null) {
            return SSLSocketFactory.theFactory;
        }
        if (!SSLSocketFactory.propertyChecked) {
            SSLSocketFactory.propertyChecked = true;
            final String securityProperty = getSecurityProperty("ssl.SocketFactory.provider");
            if (securityProperty != null) {
                log("setting up default SSLSocketFactory");
                try {
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(securityProperty);
                    }
                    catch (ClassNotFoundException ex3) {
                        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                        if (systemClassLoader != null) {
                            clazz = systemClassLoader.loadClass(securityProperty);
                        }
                    }
                    log("class " + securityProperty + " is loaded");
                    final SSLSocketFactory theFactory = (SSLSocketFactory)clazz.newInstance();
                    log("instantiated an instance of class " + securityProperty);
                    return SSLSocketFactory.theFactory = theFactory;
                }
                catch (Exception ex) {
                    log("SSLSocketFactory instantiation failed: " + ex.toString());
                    return SSLSocketFactory.theFactory = new DefaultSSLSocketFactory(ex);
                }
            }
        }
        try {
            return SSLContext.getDefault().getSocketFactory();
        }
        catch (NoSuchAlgorithmException ex2) {
            return new DefaultSSLSocketFactory(ex2);
        }
    }
    
    static String getSecurityProperty(final String s) {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                String s = Security.getProperty(s);
                if (s != null) {
                    s = s.trim();
                    if (s.length() == 0) {
                        s = null;
                    }
                }
                return s;
            }
        });
    }
    
    public abstract String[] getDefaultCipherSuites();
    
    public abstract String[] getSupportedCipherSuites();
    
    public abstract Socket createSocket(final Socket p0, final String p1, final int p2, final boolean p3) throws IOException;
    
    public Socket createSocket(final Socket socket, final InputStream inputStream, final boolean b) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    static {
        final String lowerCase = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("javax.net.debug", "")).toLowerCase(Locale.ENGLISH);
        DEBUG = (lowerCase.contains("all") || lowerCase.contains("ssl"));
    }
}
