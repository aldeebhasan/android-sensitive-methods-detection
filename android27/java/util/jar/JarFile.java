package java.util.jar;

import java.lang.ref.*;
import java.util.zip.*;
import java.io.*;
import java.util.stream.*;
import sun.security.util.*;
import sun.security.action.*;
import java.net.*;
import java.util.*;
import sun.misc.*;
import java.security.cert.*;
import java.security.*;

public class JarFile extends ZipFile
{
    private SoftReference<Manifest> manRef;
    private JarEntry manEntry;
    private JarVerifier jv;
    private boolean jvInitialized;
    private boolean verify;
    static final ThreadLocal<Boolean> isInitializing;
    private boolean hasClassPathAttribute;
    private volatile boolean hasCheckedSpecialAttributes;
    public static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
    private static final char[] CLASSPATH_CHARS;
    private static final int[] CLASSPATH_LASTOCC;
    private static final int[] CLASSPATH_OPTOSFT;
    private static String javaHome;
    private static volatile String[] jarNames;
    
    public JarFile(final String s) throws IOException {
        this(new File(s), true, 1);
    }
    
    public JarFile(final String s, final boolean b) throws IOException {
        this(new File(s), b, 1);
    }
    
    public JarFile(final File file) throws IOException {
        this(file, true, 1);
    }
    
    public JarFile(final File file, final boolean b) throws IOException {
        this(file, b, 1);
    }
    
    public JarFile(final File file, final boolean verify, final int n) throws IOException {
        super(file, n);
        this.verify = verify;
    }
    
    public Manifest getManifest() throws IOException {
        return this.getManifestFromReference();
    }
    
    private Manifest getManifestFromReference() throws IOException {
        Manifest manifest = (this.manRef != null) ? this.manRef.get() : null;
        if (manifest == null) {
            final JarEntry manEntry = this.getManEntry();
            if (manEntry != null) {
                if (this.verify) {
                    final byte[] bytes = this.getBytes(manEntry);
                    if (!this.jvInitialized) {
                        this.jv = new JarVerifier(bytes);
                    }
                    manifest = new Manifest(this.jv, new ByteArrayInputStream(bytes));
                }
                else {
                    manifest = new Manifest(super.getInputStream(manEntry));
                }
                this.manRef = new SoftReference<Manifest>(manifest);
            }
        }
        return manifest;
    }
    
    private native String[] getMetaInfEntryNames();
    
    public JarEntry getJarEntry(final String s) {
        return (JarEntry)this.getEntry(s);
    }
    
    @Override
    public ZipEntry getEntry(final String s) {
        final ZipEntry entry = super.getEntry(s);
        if (entry != null) {
            return new JarFileEntry(entry);
        }
        return null;
    }
    
    @Override
    public Enumeration<JarEntry> entries() {
        return new JarEntryIterator();
    }
    
    @Override
    public Stream<JarEntry> stream() {
        return StreamSupport.stream(Spliterators.spliterator((Iterator<? extends JarEntry>)new JarEntryIterator(), (long)this.size(), 1297), false);
    }
    
    private void maybeInstantiateVerifier() throws IOException {
        if (this.jv != null) {
            return;
        }
        if (this.verify) {
            final String[] metaInfEntryNames = this.getMetaInfEntryNames();
            if (metaInfEntryNames != null) {
                for (int i = 0; i < metaInfEntryNames.length; ++i) {
                    final String upperCase = metaInfEntryNames[i].toUpperCase(Locale.ENGLISH);
                    if (upperCase.endsWith(".DSA") || upperCase.endsWith(".RSA") || upperCase.endsWith(".EC") || upperCase.endsWith(".SF")) {
                        this.getManifest();
                        return;
                    }
                }
            }
            this.verify = false;
        }
    }
    
    private void initializeVerifier() {
        ManifestEntryVerifier manifestEntryVerifier = null;
        try {
            final String[] metaInfEntryNames = this.getMetaInfEntryNames();
            if (metaInfEntryNames != null) {
                for (int i = 0; i < metaInfEntryNames.length; ++i) {
                    final String upperCase = metaInfEntryNames[i].toUpperCase(Locale.ENGLISH);
                    if ("META-INF/MANIFEST.MF".equals(upperCase) || SignatureFileVerifier.isBlockOrSF(upperCase)) {
                        final JarEntry jarEntry = this.getJarEntry(metaInfEntryNames[i]);
                        if (jarEntry == null) {
                            throw new JarException("corrupted jar file");
                        }
                        if (manifestEntryVerifier == null) {
                            manifestEntryVerifier = new ManifestEntryVerifier(this.getManifestFromReference());
                        }
                        final byte[] bytes = this.getBytes(jarEntry);
                        if (bytes != null && bytes.length > 0) {
                            this.jv.beginEntry(jarEntry, manifestEntryVerifier);
                            this.jv.update(bytes.length, bytes, 0, bytes.length, manifestEntryVerifier);
                            this.jv.update(-1, null, 0, 0, manifestEntryVerifier);
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            this.jv = null;
            this.verify = false;
            if (JarVerifier.debug != null) {
                JarVerifier.debug.println("jarfile parsing error!");
                ex.printStackTrace();
            }
        }
        if (this.jv != null) {
            this.jv.doneWithMeta();
            if (JarVerifier.debug != null) {
                JarVerifier.debug.println("done with meta!");
            }
            if (this.jv.nothingToVerify()) {
                if (JarVerifier.debug != null) {
                    JarVerifier.debug.println("nothing to verify!");
                }
                this.jv = null;
                this.verify = false;
            }
        }
    }
    
    private byte[] getBytes(final ZipEntry zipEntry) throws IOException {
        try (final InputStream inputStream = super.getInputStream(zipEntry)) {
            return IOUtils.readFully(inputStream, (int)zipEntry.getSize(), true);
        }
    }
    
    @Override
    public synchronized InputStream getInputStream(final ZipEntry zipEntry) throws IOException {
        this.maybeInstantiateVerifier();
        if (this.jv == null) {
            return super.getInputStream(zipEntry);
        }
        if (!this.jvInitialized) {
            this.initializeVerifier();
            this.jvInitialized = true;
            if (this.jv == null) {
                return super.getInputStream(zipEntry);
            }
        }
        return new JarVerifier.VerifierStream(this.getManifestFromReference(), (zipEntry instanceof JarFileEntry) ? ((JarEntry)zipEntry) : this.getJarEntry(zipEntry.getName()), super.getInputStream(zipEntry), this.jv);
    }
    
    private JarEntry getManEntry() {
        if (this.manEntry == null) {
            this.manEntry = this.getJarEntry("META-INF/MANIFEST.MF");
            if (this.manEntry == null) {
                final String[] metaInfEntryNames = this.getMetaInfEntryNames();
                if (metaInfEntryNames != null) {
                    for (int i = 0; i < metaInfEntryNames.length; ++i) {
                        if ("META-INF/MANIFEST.MF".equals(metaInfEntryNames[i].toUpperCase(Locale.ENGLISH))) {
                            this.manEntry = this.getJarEntry(metaInfEntryNames[i]);
                            break;
                        }
                    }
                }
            }
        }
        return this.manEntry;
    }
    
    boolean hasClassPathAttribute() throws IOException {
        this.checkForSpecialAttributes();
        return this.hasClassPathAttribute;
    }
    
    private boolean match(final char[] array, final byte[] array2, final int[] array3, final int[] array4) {
        final int length = array.length;
        final int n = array2.length - length;
        int i = 0;
    Label_0014:
        while (i <= n) {
            for (int j = length - 1; j >= 0; --j) {
                final char c = (char)array2[i + j];
                final char c2 = ((c - 'A' | 'Z' - c) >= '\0') ? ((char)(c + ' ')) : c;
                if (c2 != array[j]) {
                    i += Math.max(j + 1 - array3[c2 & '\u007f'], array4[j]);
                    continue Label_0014;
                }
            }
            return true;
        }
        return false;
    }
    
    private void checkForSpecialAttributes() throws IOException {
        if (this.hasCheckedSpecialAttributes) {
            return;
        }
        if (!this.isKnownNotToHaveSpecialAttributes()) {
            final JarEntry manEntry = this.getManEntry();
            if (manEntry != null && this.match(JarFile.CLASSPATH_CHARS, this.getBytes(manEntry), JarFile.CLASSPATH_LASTOCC, JarFile.CLASSPATH_OPTOSFT)) {
                this.hasClassPathAttribute = true;
            }
        }
        this.hasCheckedSpecialAttributes = true;
    }
    
    private boolean isKnownNotToHaveSpecialAttributes() {
        if (JarFile.javaHome == null) {
            JarFile.javaHome = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("java.home"));
        }
        if (JarFile.jarNames == null) {
            final String[] jarNames = new String[11];
            final String separator = File.separator;
            int n = 0;
            jarNames[n++] = separator + "rt.jar";
            jarNames[n++] = separator + "jsse.jar";
            jarNames[n++] = separator + "jce.jar";
            jarNames[n++] = separator + "charsets.jar";
            jarNames[n++] = separator + "dnsns.jar";
            jarNames[n++] = separator + "zipfs.jar";
            jarNames[n++] = separator + "localedata.jar";
            final String s = jarNames[n++] = "cldrdata.jar";
            jarNames[n++] = s + "sunjce_provider.jar";
            jarNames[n++] = s + "sunpkcs11.jar";
            jarNames[n++] = s + "sunec.jar";
            JarFile.jarNames = jarNames;
        }
        final String name = this.getName();
        if (name.startsWith(JarFile.javaHome)) {
            final String[] jarNames2 = JarFile.jarNames;
            for (int i = 0; i < jarNames2.length; ++i) {
                if (name.endsWith(jarNames2[i])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    synchronized void ensureInitialization() {
        try {
            this.maybeInstantiateVerifier();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (this.jv != null && !this.jvInitialized) {
            JarFile.isInitializing.set(Boolean.TRUE);
            try {
                this.initializeVerifier();
                this.jvInitialized = true;
            }
            finally {
                JarFile.isInitializing.set(Boolean.FALSE);
            }
        }
    }
    
    JarEntry newEntry(final ZipEntry zipEntry) {
        return new JarFileEntry(zipEntry);
    }
    
    Enumeration<String> entryNames(final CodeSource[] array) {
        this.ensureInitialization();
        if (this.jv != null) {
            return this.jv.entryNames(this, array);
        }
        boolean b = false;
        for (int i = 0; i < array.length; ++i) {
            if (array[i].getCodeSigners() == null) {
                b = true;
                break;
            }
        }
        if (b) {
            return this.unsignedEntryNames();
        }
        return new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }
            
            @Override
            public String nextElement() {
                throw new NoSuchElementException();
            }
        };
    }
    
    Enumeration<JarEntry> entries2() {
        this.ensureInitialization();
        if (this.jv != null) {
            return this.jv.entries2(this, super.entries());
        }
        return new Enumeration<JarEntry>() {
            ZipEntry entry;
            final /* synthetic */ Enumeration val$enum_ = super.entries();
            
            @Override
            public boolean hasMoreElements() {
                if (this.entry != null) {
                    return true;
                }
                while (this.val$enum_.hasMoreElements()) {
                    final ZipEntry entry = this.val$enum_.nextElement();
                    if (JarVerifier.isSigningRelated(entry.getName())) {
                        continue;
                    }
                    this.entry = entry;
                    return true;
                }
                return false;
            }
            
            @Override
            public JarFileEntry nextElement() {
                if (this.hasMoreElements()) {
                    final ZipEntry entry = this.entry;
                    this.entry = null;
                    return new JarFileEntry(entry);
                }
                throw new NoSuchElementException();
            }
        };
    }
    
    CodeSource[] getCodeSources(final URL url) {
        this.ensureInitialization();
        if (this.jv != null) {
            return this.jv.getCodeSources(this, url);
        }
        if (this.unsignedEntryNames().hasMoreElements()) {
            return new CodeSource[] { JarVerifier.getUnsignedCS(url) };
        }
        return null;
    }
    
    private Enumeration<String> unsignedEntryNames() {
        return new Enumeration<String>() {
            String name;
            final /* synthetic */ Enumeration val$entries = JarFile.this.entries();
            
            @Override
            public boolean hasMoreElements() {
                if (this.name != null) {
                    return true;
                }
                while (this.val$entries.hasMoreElements()) {
                    final ZipEntry zipEntry = this.val$entries.nextElement();
                    final String name = zipEntry.getName();
                    if (!zipEntry.isDirectory()) {
                        if (JarVerifier.isSigningRelated(name)) {
                            continue;
                        }
                        this.name = name;
                        return true;
                    }
                }
                return false;
            }
            
            @Override
            public String nextElement() {
                if (this.hasMoreElements()) {
                    final String name = this.name;
                    this.name = null;
                    return name;
                }
                throw new NoSuchElementException();
            }
        };
    }
    
    CodeSource getCodeSource(final URL url, final String s) {
        this.ensureInitialization();
        if (this.jv == null) {
            return JarVerifier.getUnsignedCS(url);
        }
        if (this.jv.eagerValidation) {
            final JarEntry jarEntry = this.getJarEntry(s);
            CodeSource codeSource;
            if (jarEntry != null) {
                codeSource = this.jv.getCodeSource(url, this, jarEntry);
            }
            else {
                codeSource = this.jv.getCodeSource(url, s);
            }
            return codeSource;
        }
        return this.jv.getCodeSource(url, s);
    }
    
    void setEagerValidation(final boolean eagerValidation) {
        try {
            this.maybeInstantiateVerifier();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (this.jv != null) {
            this.jv.setEagerValidation(eagerValidation);
        }
    }
    
    List<Object> getManifestDigests() {
        this.ensureInitialization();
        if (this.jv != null) {
            return this.jv.getManifestDigests();
        }
        return new ArrayList<Object>();
    }
    
    static boolean isInitializing() {
        final Boolean b = JarFile.isInitializing.get();
        return b != null && b;
    }
    
    static {
        isInitializing = new ThreadLocal<Boolean>();
        SharedSecrets.setJavaUtilJarAccess(new JavaUtilJarAccessImpl());
        CLASSPATH_CHARS = new char[] { 'c', 'l', 'a', 's', 's', '-', 'p', 'a', 't', 'h' };
        CLASSPATH_LASTOCC = new int[128];
        CLASSPATH_OPTOSFT = new int[10];
        JarFile.CLASSPATH_LASTOCC[99] = 1;
        JarFile.CLASSPATH_LASTOCC[108] = 2;
        JarFile.CLASSPATH_LASTOCC[115] = 5;
        JarFile.CLASSPATH_LASTOCC[45] = 6;
        JarFile.CLASSPATH_LASTOCC[112] = 7;
        JarFile.CLASSPATH_LASTOCC[97] = 8;
        JarFile.CLASSPATH_LASTOCC[116] = 9;
        JarFile.CLASSPATH_LASTOCC[104] = 10;
        for (int i = 0; i < 9; ++i) {
            JarFile.CLASSPATH_OPTOSFT[i] = 10;
        }
        JarFile.CLASSPATH_OPTOSFT[9] = 1;
    }
    
    private class JarEntryIterator implements Enumeration<JarEntry>, Iterator<JarEntry>
    {
        final Enumeration<? extends ZipEntry> e;
        
        private JarEntryIterator() {
            this.e = JarFile.this.entries();
        }
        
        @Override
        public boolean hasNext() {
            return this.e.hasMoreElements();
        }
        
        @Override
        public JarEntry next() {
            return new JarFileEntry((ZipEntry)this.e.nextElement());
        }
        
        @Override
        public boolean hasMoreElements() {
            return this.hasNext();
        }
        
        @Override
        public JarEntry nextElement() {
            return this.next();
        }
    }
    
    private class JarFileEntry extends JarEntry
    {
        JarFileEntry(final ZipEntry zipEntry) {
            super(zipEntry);
        }
        
        @Override
        public Attributes getAttributes() throws IOException {
            final Manifest manifest = JarFile.this.getManifest();
            if (manifest != null) {
                return manifest.getAttributes(this.getName());
            }
            return null;
        }
        
        @Override
        public Certificate[] getCertificates() {
            try {
                JarFile.this.maybeInstantiateVerifier();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (this.certs == null && JarFile.this.jv != null) {
                this.certs = JarFile.this.jv.getCerts(JarFile.this, this);
            }
            return (Certificate[])((this.certs == null) ? null : ((Certificate[])this.certs.clone()));
        }
        
        @Override
        public CodeSigner[] getCodeSigners() {
            try {
                JarFile.this.maybeInstantiateVerifier();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (this.signers == null && JarFile.this.jv != null) {
                this.signers = JarFile.this.jv.getCodeSigners(JarFile.this, this);
            }
            return (CodeSigner[])((this.signers == null) ? null : ((CodeSigner[])this.signers.clone()));
        }
    }
}
