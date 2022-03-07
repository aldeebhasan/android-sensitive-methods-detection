package java.util.jar;

import sun.security.util.*;
import java.io.*;
import java.util.zip.*;

public class JarInputStream extends ZipInputStream
{
    private Manifest man;
    private JarEntry first;
    private JarVerifier jv;
    private ManifestEntryVerifier mev;
    private final boolean doVerify;
    private boolean tryManifest;
    
    public JarInputStream(final InputStream inputStream) throws IOException {
        this(inputStream, true);
    }
    
    public JarInputStream(final InputStream inputStream, final boolean doVerify) throws IOException {
        super(inputStream);
        this.doVerify = doVerify;
        JarEntry jarEntry = (JarEntry)super.getNextEntry();
        if (jarEntry != null && jarEntry.getName().equalsIgnoreCase("META-INF/")) {
            jarEntry = (JarEntry)super.getNextEntry();
        }
        this.first = this.checkManifest(jarEntry);
    }
    
    private JarEntry checkManifest(final JarEntry jarEntry) throws IOException {
        if (jarEntry != null && "META-INF/MANIFEST.MF".equalsIgnoreCase(jarEntry.getName())) {
            this.man = new Manifest();
            final byte[] bytes = this.getBytes(new BufferedInputStream(this));
            this.man.read(new ByteArrayInputStream(bytes));
            this.closeEntry();
            if (this.doVerify) {
                this.jv = new JarVerifier(bytes);
                this.mev = new ManifestEntryVerifier(this.man);
            }
            return (JarEntry)super.getNextEntry();
        }
        return jarEntry;
    }
    
    private byte[] getBytes(final InputStream inputStream) throws IOException {
        final byte[] array = new byte[8192];
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        int read;
        while ((read = inputStream.read(array, 0, array.length)) != -1) {
            byteArrayOutputStream.write(array, 0, read);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public Manifest getManifest() {
        return this.man;
    }
    
    @Override
    public ZipEntry getNextEntry() throws IOException {
        JarEntry jarEntry;
        if (this.first == null) {
            jarEntry = (JarEntry)super.getNextEntry();
            if (this.tryManifest) {
                jarEntry = this.checkManifest(jarEntry);
                this.tryManifest = false;
            }
        }
        else {
            jarEntry = this.first;
            if (this.first.getName().equalsIgnoreCase("META-INF/INDEX.LIST")) {
                this.tryManifest = true;
            }
            this.first = null;
        }
        if (this.jv != null && jarEntry != null) {
            if (this.jv.nothingToVerify()) {
                this.jv = null;
                this.mev = null;
            }
            else {
                this.jv.beginEntry(jarEntry, this.mev);
            }
        }
        return jarEntry;
    }
    
    public JarEntry getNextJarEntry() throws IOException {
        return (JarEntry)this.getNextEntry();
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        int read;
        if (this.first == null) {
            read = super.read(array, n, n2);
        }
        else {
            read = -1;
        }
        if (this.jv != null) {
            this.jv.update(read, array, n, n2, this.mev);
        }
        return read;
    }
    
    @Override
    protected ZipEntry createZipEntry(final String s) {
        final JarEntry jarEntry = new JarEntry(s);
        if (this.man != null) {
            jarEntry.attr = this.man.getAttributes(s);
        }
        return jarEntry;
    }
}
