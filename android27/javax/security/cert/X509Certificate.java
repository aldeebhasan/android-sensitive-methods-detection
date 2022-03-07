package javax.security.cert;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.math.*;
import java.security.*;

public abstract class X509Certificate extends Certificate
{
    private static final String X509_PROVIDER = "cert.provider.x509v1";
    private static String X509Provider;
    
    public static final X509Certificate getInstance(final InputStream inputStream) throws CertificateException {
        return getInst(inputStream);
    }
    
    public static final X509Certificate getInstance(final byte[] array) throws CertificateException {
        return getInst(array);
    }
    
    private static final X509Certificate getInst(final Object o) throws CertificateException {
        String x509Provider = X509Certificate.X509Provider;
        if (x509Provider == null || x509Provider.length() == 0) {
            x509Provider = "com.sun.security.cert.internal.x509.X509V1CertImpl";
        }
        try {
            Class[] array;
            if (o instanceof InputStream) {
                array = new Class[] { InputStream.class };
            }
            else {
                if (!(o instanceof byte[])) {
                    throw new CertificateException("Unsupported argument type");
                }
                array = new Class[] { o.getClass() };
            }
            return (X509Certificate)Class.forName(x509Provider).getConstructor((Class<?>[])array).newInstance(o);
        }
        catch (ClassNotFoundException ex) {
            throw new CertificateException("Could not find class: " + ex);
        }
        catch (IllegalAccessException ex2) {
            throw new CertificateException("Could not access class: " + ex2);
        }
        catch (InstantiationException ex3) {
            throw new CertificateException("Problems instantiating: " + ex3);
        }
        catch (InvocationTargetException ex4) {
            throw new CertificateException("InvocationTargetException: " + ex4.getTargetException());
        }
        catch (NoSuchMethodException ex5) {
            throw new CertificateException("Could not find class method: " + ex5.getMessage());
        }
    }
    
    public abstract void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException;
    
    public abstract void checkValidity(final Date p0) throws CertificateExpiredException, CertificateNotYetValidException;
    
    public abstract int getVersion();
    
    public abstract BigInteger getSerialNumber();
    
    public abstract Principal getIssuerDN();
    
    public abstract Principal getSubjectDN();
    
    public abstract Date getNotBefore();
    
    public abstract Date getNotAfter();
    
    public abstract String getSigAlgName();
    
    public abstract String getSigAlgOID();
    
    public abstract byte[] getSigAlgParams();
    
    static {
        X509Certificate.X509Provider = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("cert.provider.x509v1");
            }
        });
    }
}
