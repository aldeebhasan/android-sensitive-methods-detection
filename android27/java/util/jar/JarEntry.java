package java.util.jar;

import java.util.zip.*;
import java.security.cert.*;
import java.security.*;
import java.io.*;

public class JarEntry extends ZipEntry
{
    Attributes attr;
    Certificate[] certs;
    CodeSigner[] signers;
    
    public JarEntry(final String s) {
        super(s);
    }
    
    public JarEntry(final ZipEntry zipEntry) {
        super(zipEntry);
    }
    
    public JarEntry(final JarEntry jarEntry) {
        this((ZipEntry)jarEntry);
        this.attr = jarEntry.attr;
        this.certs = jarEntry.certs;
        this.signers = jarEntry.signers;
    }
    
    public Attributes getAttributes() throws IOException {
        return this.attr;
    }
    
    public Certificate[] getCertificates() {
        return (Certificate[])((this.certs == null) ? null : ((Certificate[])this.certs.clone()));
    }
    
    public CodeSigner[] getCodeSigners() {
        return (CodeSigner[])((this.signers == null) ? null : ((CodeSigner[])this.signers.clone()));
    }
}
