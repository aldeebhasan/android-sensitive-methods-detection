package java.net;

import sun.net.www.protocol.file.*;
import java.nio.*;
import java.util.jar.*;
import java.util.*;
import java.security.*;
import java.io.*;
import sun.net.www.*;
import sun.misc.*;

public class URLClassLoader extends SecureClassLoader implements Closeable
{
    private final URLClassPath ucp;
    private final AccessControlContext acc;
    private WeakHashMap<Closeable, Void> closeables;
    
    public URLClassLoader(final URL[] array, final ClassLoader classLoader) {
        super(classLoader);
        this.closeables = new WeakHashMap<Closeable, Void>();
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.acc = AccessController.getContext();
        this.ucp = new URLClassPath(array, this.acc);
    }
    
    URLClassLoader(final URL[] array, final ClassLoader classLoader, final AccessControlContext acc) {
        super(classLoader);
        this.closeables = new WeakHashMap<Closeable, Void>();
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.acc = acc;
        this.ucp = new URLClassPath(array, acc);
    }
    
    public URLClassLoader(final URL[] array) {
        this.closeables = new WeakHashMap<Closeable, Void>();
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.acc = AccessController.getContext();
        this.ucp = new URLClassPath(array, this.acc);
    }
    
    URLClassLoader(final URL[] array, final AccessControlContext acc) {
        this.closeables = new WeakHashMap<Closeable, Void>();
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.acc = acc;
        this.ucp = new URLClassPath(array, acc);
    }
    
    public URLClassLoader(final URL[] array, final ClassLoader classLoader, final URLStreamHandlerFactory urlStreamHandlerFactory) {
        super(classLoader);
        this.closeables = new WeakHashMap<Closeable, Void>();
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.acc = AccessController.getContext();
        this.ucp = new URLClassPath(array, urlStreamHandlerFactory, this.acc);
    }
    
    @Override
    public InputStream getResourceAsStream(final String s) {
        final URL resource = this.getResource(s);
        try {
            if (resource == null) {
                return null;
            }
            final URLConnection openConnection = resource.openConnection();
            final InputStream inputStream = openConnection.getInputStream();
            if (openConnection instanceof JarURLConnection) {
                final JarFile jarFile = ((JarURLConnection)openConnection).getJarFile();
                synchronized (this.closeables) {
                    if (!this.closeables.containsKey(jarFile)) {
                        this.closeables.put(jarFile, null);
                    }
                }
            }
            else if (openConnection instanceof FileURLConnection) {
                synchronized (this.closeables) {
                    this.closeables.put(inputStream, null);
                }
            }
            return inputStream;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    @Override
    public void close() throws IOException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("closeClassLoader"));
        }
        final List<IOException> closeLoaders = this.ucp.closeLoaders();
        synchronized (this.closeables) {
            for (final Closeable closeable : this.closeables.keySet()) {
                try {
                    closeable.close();
                }
                catch (IOException ex) {
                    closeLoaders.add(ex);
                }
            }
            this.closeables.clear();
        }
        if (closeLoaders.isEmpty()) {
            return;
        }
        final IOException ex2 = closeLoaders.remove(0);
        final Iterator<IOException> iterator2 = closeLoaders.iterator();
        while (iterator2.hasNext()) {
            ex2.addSuppressed(iterator2.next());
        }
        throw ex2;
    }
    
    protected void addURL(final URL url) {
        this.ucp.addURL(url);
    }
    
    public URL[] getURLs() {
        return this.ucp.getURLs();
    }
    
    @Override
    protected Class<?> findClass(final String s) throws ClassNotFoundException {
        Class<?> clazz;
        try {
            clazz = AccessController.doPrivileged((PrivilegedExceptionAction<Class<?>>)new PrivilegedExceptionAction<Class<?>>() {
                @Override
                public Class<?> run() throws ClassNotFoundException {
                    final Resource resource = URLClassLoader.this.ucp.getResource(s.replace('.', '/').concat(".class"), false);
                    if (resource != null) {
                        try {
                            return URLClassLoader.this.defineClass(s, resource);
                        }
                        catch (IOException ex) {
                            throw new ClassNotFoundException(s, ex);
                        }
                    }
                    return null;
                }
            }, this.acc);
        }
        catch (PrivilegedActionException ex) {
            throw (ClassNotFoundException)ex.getException();
        }
        if (clazz == null) {
            throw new ClassNotFoundException(s);
        }
        return clazz;
    }
    
    private Package getAndVerifyPackage(final String s, final Manifest manifest, final URL url) {
        final Package package1 = this.getPackage(s);
        if (package1 != null) {
            if (package1.isSealed()) {
                if (!package1.isSealed(url)) {
                    throw new SecurityException("sealing violation: package " + s + " is sealed");
                }
            }
            else if (manifest != null && this.isSealed(s, manifest)) {
                throw new SecurityException("sealing violation: can't seal package " + s + ": already loaded");
            }
        }
        return package1;
    }
    
    private void definePackageInternal(final String s, final Manifest manifest, final URL url) {
        if (this.getAndVerifyPackage(s, manifest, url) == null) {
            try {
                if (manifest != null) {
                    this.definePackage(s, manifest, url);
                }
                else {
                    this.definePackage(s, null, null, null, null, null, null, null);
                }
            }
            catch (IllegalArgumentException ex) {
                if (this.getAndVerifyPackage(s, manifest, url) == null) {
                    throw new AssertionError((Object)("Cannot find package " + s));
                }
            }
        }
    }
    
    private Class<?> defineClass(final String s, final Resource resource) throws IOException {
        final long nanoTime = System.nanoTime();
        final int lastIndex = s.lastIndexOf(46);
        final URL codeSourceURL = resource.getCodeSourceURL();
        if (lastIndex != -1) {
            this.definePackageInternal(s.substring(0, lastIndex), resource.getManifest(), codeSourceURL);
        }
        final ByteBuffer byteBuffer = resource.getByteBuffer();
        if (byteBuffer != null) {
            final CodeSource codeSource = new CodeSource(codeSourceURL, resource.getCodeSigners());
            PerfCounter.getReadClassBytesTime().addElapsedTimeFrom(nanoTime);
            return this.defineClass(s, byteBuffer, codeSource);
        }
        final byte[] bytes = resource.getBytes();
        final CodeSource codeSource2 = new CodeSource(codeSourceURL, resource.getCodeSigners());
        PerfCounter.getReadClassBytesTime().addElapsedTimeFrom(nanoTime);
        return this.defineClass(s, bytes, 0, bytes.length, codeSource2);
    }
    
    protected Package definePackage(final String s, final Manifest manifest, final URL url) throws IllegalArgumentException {
        String s2 = null;
        String s3 = null;
        String s4 = null;
        String s5 = null;
        String s6 = null;
        String s7 = null;
        String s8 = null;
        URL url2 = null;
        final Attributes trustedAttributes = SharedSecrets.javaUtilJarAccess().getTrustedAttributes(manifest, s.replace('.', '/').concat("/"));
        if (trustedAttributes != null) {
            s2 = trustedAttributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
            s3 = trustedAttributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
            s4 = trustedAttributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
            s5 = trustedAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            s6 = trustedAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            s7 = trustedAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            s8 = trustedAttributes.getValue(Attributes.Name.SEALED);
        }
        final Attributes mainAttributes = manifest.getMainAttributes();
        if (mainAttributes != null) {
            if (s2 == null) {
                s2 = mainAttributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
            }
            if (s3 == null) {
                s3 = mainAttributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
            }
            if (s4 == null) {
                s4 = mainAttributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
            }
            if (s5 == null) {
                s5 = mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            }
            if (s6 == null) {
                s6 = mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            }
            if (s7 == null) {
                s7 = mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            }
            if (s8 == null) {
                s8 = mainAttributes.getValue(Attributes.Name.SEALED);
            }
        }
        if ("true".equalsIgnoreCase(s8)) {
            url2 = url;
        }
        return this.definePackage(s, s2, s3, s4, s5, s6, s7, url2);
    }
    
    private boolean isSealed(final String s, final Manifest manifest) {
        final Attributes trustedAttributes = SharedSecrets.javaUtilJarAccess().getTrustedAttributes(manifest, s.replace('.', '/').concat("/"));
        String s2 = null;
        if (trustedAttributes != null) {
            s2 = trustedAttributes.getValue(Attributes.Name.SEALED);
        }
        final Attributes mainAttributes;
        if (s2 == null && (mainAttributes = manifest.getMainAttributes()) != null) {
            s2 = mainAttributes.getValue(Attributes.Name.SEALED);
        }
        return "true".equalsIgnoreCase(s2);
    }
    
    public URL findResource(final String s) {
        final URL url = AccessController.doPrivileged((PrivilegedAction<URL>)new PrivilegedAction<URL>() {
            @Override
            public URL run() {
                return URLClassLoader.this.ucp.findResource(s, true);
            }
        }, this.acc);
        return (url != null) ? this.ucp.checkURL(url) : null;
    }
    
    public Enumeration<URL> findResources(final String s) throws IOException {
        return new Enumeration<URL>() {
            private URL url = null;
            final /* synthetic */ Enumeration val$e = URLClassLoader.this.ucp.findResources(s, true);
            
            private boolean next() {
                if (this.url != null) {
                    return true;
                }
                do {
                    final URL url = AccessController.doPrivileged((PrivilegedAction<URL>)new PrivilegedAction<URL>() {
                        @Override
                        public URL run() {
                            if (!Enumeration.this.val$e.hasMoreElements()) {
                                return null;
                            }
                            return Enumeration.this.val$e.nextElement();
                        }
                    }, URLClassLoader.this.acc);
                    if (url == null) {
                        break;
                    }
                    this.url = URLClassLoader.this.ucp.checkURL(url);
                } while (this.url == null);
                return this.url != null;
            }
            
            @Override
            public URL nextElement() {
                if (!this.next()) {
                    throw new NoSuchElementException();
                }
                final URL url = this.url;
                this.url = null;
                return url;
            }
            
            @Override
            public boolean hasMoreElements() {
                return this.next();
            }
        };
    }
    
    @Override
    protected PermissionCollection getPermissions(final CodeSource codeSource) {
        final PermissionCollection permissions = super.getPermissions(codeSource);
        final URL location = codeSource.getLocation();
        URLConnection openConnection;
        Permission permission;
        try {
            openConnection = location.openConnection();
            permission = openConnection.getPermission();
        }
        catch (IOException ex) {
            permission = null;
            openConnection = null;
        }
        if (permission instanceof FilePermission) {
            final String name = permission.getName();
            if (name.endsWith(File.separator)) {
                permission = new FilePermission(name + "-", "read");
            }
        }
        else if (permission == null && location.getProtocol().equals("file")) {
            String s = ParseUtil.decode(location.getFile().replace('/', File.separatorChar));
            if (s.endsWith(File.separator)) {
                s += "-";
            }
            permission = new FilePermission(s, "read");
        }
        else {
            URL jarFileURL = location;
            if (openConnection instanceof JarURLConnection) {
                jarFileURL = ((JarURLConnection)openConnection).getJarFileURL();
            }
            final String host = jarFileURL.getHost();
            if (host != null && host.length() > 0) {
                permission = new SocketPermission(host, "connect,accept");
            }
        }
        if (permission != null) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                    @Override
                    public Void run() throws SecurityException {
                        securityManager.checkPermission(permission);
                        return null;
                    }
                }, this.acc);
            }
            permissions.add(permission);
        }
        return permissions;
    }
    
    public static URLClassLoader newInstance(final URL[] array, final ClassLoader classLoader) {
        return AccessController.doPrivileged((PrivilegedAction<URLClassLoader>)new PrivilegedAction<URLClassLoader>() {
            final /* synthetic */ AccessControlContext val$acc = AccessController.getContext();
            
            @Override
            public URLClassLoader run() {
                return new FactoryURLClassLoader(array, classLoader, this.val$acc);
            }
        });
    }
    
    public static URLClassLoader newInstance(final URL[] array) {
        return AccessController.doPrivileged((PrivilegedAction<URLClassLoader>)new PrivilegedAction<URLClassLoader>() {
            final /* synthetic */ AccessControlContext val$acc = AccessController.getContext();
            
            @Override
            public URLClassLoader run() {
                return new FactoryURLClassLoader(array, this.val$acc);
            }
        });
    }
    
    static {
        SharedSecrets.setJavaNetAccess(new JavaNetAccess() {
            @Override
            public URLClassPath getURLClassPath(final URLClassLoader urlClassLoader) {
                return urlClassLoader.ucp;
            }
            
            @Override
            public String getOriginalHostName(final InetAddress inetAddress) {
                return inetAddress.holder.getOriginalHostName();
            }
        });
        ClassLoader.registerAsParallelCapable();
    }
}
