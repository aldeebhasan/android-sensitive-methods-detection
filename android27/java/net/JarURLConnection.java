package java.net;

import sun.net.www.*;
import java.io.*;
import java.util.jar.*;
import java.security.cert.*;

public abstract class JarURLConnection extends URLConnection
{
    private URL jarFileURL;
    private String entryName;
    protected URLConnection jarFileURLConnection;
    
    protected JarURLConnection(final URL url) throws MalformedURLException {
        super(url);
        this.parseSpecs(url);
    }
    
    private void parseSpecs(final URL url) throws MalformedURLException {
        final String file = url.getFile();
        int index = file.indexOf("!/");
        if (index == -1) {
            throw new MalformedURLException("no !/ found in url spec:" + file);
        }
        this.jarFileURL = new URL(file.substring(0, index++));
        this.entryName = null;
        if (++index != file.length()) {
            this.entryName = file.substring(index, file.length());
            this.entryName = ParseUtil.decode(this.entryName);
        }
    }
    
    public URL getJarFileURL() {
        return this.jarFileURL;
    }
    
    public String getEntryName() {
        return this.entryName;
    }
    
    public abstract JarFile getJarFile() throws IOException;
    
    public Manifest getManifest() throws IOException {
        return this.getJarFile().getManifest();
    }
    
    public JarEntry getJarEntry() throws IOException {
        return this.getJarFile().getJarEntry(this.entryName);
    }
    
    public Attributes getAttributes() throws IOException {
        final JarEntry jarEntry = this.getJarEntry();
        return (jarEntry != null) ? jarEntry.getAttributes() : null;
    }
    
    public Attributes getMainAttributes() throws IOException {
        final Manifest manifest = this.getManifest();
        return (manifest != null) ? manifest.getMainAttributes() : null;
    }
    
    public Certificate[] getCertificates() throws IOException {
        final JarEntry jarEntry = this.getJarEntry();
        return (Certificate[])((jarEntry != null) ? jarEntry.getCertificates() : null);
    }
}
