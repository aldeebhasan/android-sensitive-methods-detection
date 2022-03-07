package javax.net.ssl;

import javax.net.*;
import java.security.*;

public abstract class SSLServerSocketFactory extends ServerSocketFactory
{
    private static SSLServerSocketFactory theFactory;
    private static boolean propertyChecked;
    
    private static void log(final String s) {
        if (SSLSocketFactory.DEBUG) {
            System.out.println(s);
        }
    }
    
    public static synchronized ServerSocketFactory getDefault() {
        if (SSLServerSocketFactory.theFactory != null) {
            return SSLServerSocketFactory.theFactory;
        }
        if (!SSLServerSocketFactory.propertyChecked) {
            SSLServerSocketFactory.propertyChecked = true;
            final String securityProperty = SSLSocketFactory.getSecurityProperty("ssl.ServerSocketFactory.provider");
            if (securityProperty != null) {
                log("setting up default SSLServerSocketFactory");
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
                    final SSLServerSocketFactory theFactory = (SSLServerSocketFactory)clazz.newInstance();
                    log("instantiated an instance of class " + securityProperty);
                    return SSLServerSocketFactory.theFactory = theFactory;
                }
                catch (Exception ex) {
                    log("SSLServerSocketFactory instantiation failed: " + ex);
                    return SSLServerSocketFactory.theFactory = new DefaultSSLServerSocketFactory(ex);
                }
            }
        }
        try {
            return SSLContext.getDefault().getServerSocketFactory();
        }
        catch (NoSuchAlgorithmException ex2) {
            return new DefaultSSLServerSocketFactory(ex2);
        }
    }
    
    public abstract String[] getDefaultCipherSuites();
    
    public abstract String[] getSupportedCipherSuites();
}
