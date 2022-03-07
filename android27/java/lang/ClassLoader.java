package java.lang;

import java.util.concurrent.*;
import java.security.cert.*;
import java.net.*;
import sun.reflect.misc.*;
import java.nio.*;
import sun.reflect.*;
import sun.misc.*;
import java.lang.reflect.*;
import sun.security.util.*;
import java.security.*;
import java.io.*;
import java.util.*;

public abstract class ClassLoader
{
    private final ClassLoader parent;
    private final ConcurrentHashMap<String, Object> parallelLockMap;
    private final Map<String, Certificate[]> package2certs;
    private static final Certificate[] nocerts;
    private final Vector<Class<?>> classes;
    private final ProtectionDomain defaultDomain;
    private final HashMap<String, Package> packages;
    private static ClassLoader scl;
    private static boolean sclSet;
    private static Vector<String> loadedLibraryNames;
    private static Vector<NativeLibrary> systemNativeLibraries;
    private Vector<NativeLibrary> nativeLibraries;
    private static Stack<NativeLibrary> nativeLibraryContext;
    private static String[] usr_paths;
    private static String[] sys_paths;
    final Object assertionLock;
    private boolean defaultAssertionStatus;
    private Map<String, Boolean> packageAssertionStatus;
    Map<String, Boolean> classAssertionStatus;
    
    private static native void registerNatives();
    
    void addClass(final Class<?> clazz) {
        this.classes.addElement(clazz);
    }
    
    private static Void checkCreateClassLoader() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        return null;
    }
    
    private ClassLoader(final Void void1, final ClassLoader parent) {
        this.classes = new Vector<Class<?>>();
        this.defaultDomain = new ProtectionDomain(new CodeSource(null, (Certificate[])null), null, this, null);
        this.packages = new HashMap<String, Package>();
        this.nativeLibraries = new Vector<NativeLibrary>();
        this.defaultAssertionStatus = false;
        this.packageAssertionStatus = null;
        this.classAssertionStatus = null;
        this.parent = parent;
        if (ParallelLoaders.isRegistered(this.getClass())) {
            this.parallelLockMap = new ConcurrentHashMap<String, Object>();
            this.package2certs = new ConcurrentHashMap<String, Certificate[]>();
            this.assertionLock = new Object();
        }
        else {
            this.parallelLockMap = null;
            this.package2certs = new Hashtable<String, Certificate[]>();
            this.assertionLock = this;
        }
    }
    
    protected ClassLoader(final ClassLoader classLoader) {
        this(checkCreateClassLoader(), classLoader);
    }
    
    protected ClassLoader() {
        this(checkCreateClassLoader(), getSystemClassLoader());
    }
    
    public Class<?> loadClass(final String s) throws ClassNotFoundException {
        return this.loadClass(s, false);
    }
    
    protected Class<?> loadClass(final String s, final boolean b) throws ClassNotFoundException {
        synchronized (this.getClassLoadingLock(s)) {
            Class<?> clazz = this.findLoadedClass(s);
            if (clazz == null) {
                final long nanoTime = System.nanoTime();
                try {
                    if (this.parent != null) {
                        clazz = this.parent.loadClass(s, false);
                    }
                    else {
                        clazz = this.findBootstrapClassOrNull(s);
                    }
                }
                catch (ClassNotFoundException ex) {}
                if (clazz == null) {
                    final long nanoTime2 = System.nanoTime();
                    clazz = this.findClass(s);
                    PerfCounter.getParentDelegationTime().addTime(nanoTime2 - nanoTime);
                    PerfCounter.getFindClassTime().addElapsedTimeFrom(nanoTime2);
                    PerfCounter.getFindClasses().increment();
                }
            }
            if (b) {
                this.resolveClass(clazz);
            }
            return clazz;
        }
    }
    
    protected Object getClassLoadingLock(final String s) {
        ClassLoader putIfAbsent = this;
        if (this.parallelLockMap != null) {
            final Object o = new Object();
            putIfAbsent = this.parallelLockMap.putIfAbsent(s, o);
            if (putIfAbsent == null) {
                putIfAbsent = (ClassLoader)o;
            }
        }
        return putIfAbsent;
    }
    
    private Class<?> loadClassInternal(final String s) throws ClassNotFoundException {
        if (this.parallelLockMap == null) {
            synchronized (this) {
                return this.loadClass(s);
            }
        }
        return this.loadClass(s);
    }
    
    private void checkPackageAccess(final Class<?> clazz, final ProtectionDomain protectionDomain) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            if (ReflectUtil.isNonPublicProxyClass(clazz)) {
                final Class[] interfaces = clazz.getInterfaces();
                for (int length = interfaces.length, i = 0; i < length; ++i) {
                    this.checkPackageAccess(interfaces[i], protectionDomain);
                }
                return;
            }
            final String name = clazz.getName();
            final int lastIndex = name.lastIndexOf(46);
            if (lastIndex != -1) {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        securityManager.checkPackageAccess(name.substring(0, lastIndex));
                        return null;
                    }
                }, new AccessControlContext(new ProtectionDomain[] { protectionDomain }));
            }
        }
    }
    
    protected Class<?> findClass(final String s) throws ClassNotFoundException {
        throw new ClassNotFoundException(s);
    }
    
    @Deprecated
    protected final Class<?> defineClass(final byte[] array, final int n, final int n2) throws ClassFormatError {
        return this.defineClass(null, array, n, n2, null);
    }
    
    protected final Class<?> defineClass(final String s, final byte[] array, final int n, final int n2) throws ClassFormatError {
        return this.defineClass(s, array, n, n2, null);
    }
    
    private ProtectionDomain preDefineClass(final String s, ProtectionDomain defaultDomain) {
        if (!this.checkName(s)) {
            throw new NoClassDefFoundError("IllegalName: " + s);
        }
        if (s != null && s.startsWith("java.")) {
            throw new SecurityException("Prohibited package name: " + s.substring(0, s.lastIndexOf(46)));
        }
        if (defaultDomain == null) {
            defaultDomain = this.defaultDomain;
        }
        if (s != null) {
            this.checkCerts(s, defaultDomain.getCodeSource());
        }
        return defaultDomain;
    }
    
    private String defineClassSourceLocation(final ProtectionDomain protectionDomain) {
        final CodeSource codeSource = protectionDomain.getCodeSource();
        String string = null;
        if (codeSource != null && codeSource.getLocation() != null) {
            string = codeSource.getLocation().toString();
        }
        return string;
    }
    
    private void postDefineClass(final Class<?> clazz, final ProtectionDomain protectionDomain) {
        if (protectionDomain.getCodeSource() != null) {
            final Certificate[] certificates = protectionDomain.getCodeSource().getCertificates();
            if (certificates != null) {
                this.setSigners(clazz, certificates);
            }
        }
    }
    
    protected final Class<?> defineClass(final String s, final byte[] array, final int n, final int n2, ProtectionDomain preDefineClass) throws ClassFormatError {
        preDefineClass = this.preDefineClass(s, preDefineClass);
        final Class<?> defineClass1 = this.defineClass1(s, array, n, n2, preDefineClass, this.defineClassSourceLocation(preDefineClass));
        this.postDefineClass(defineClass1, preDefineClass);
        return defineClass1;
    }
    
    protected final Class<?> defineClass(final String s, final ByteBuffer byteBuffer, ProtectionDomain preDefineClass) throws ClassFormatError {
        final int remaining = byteBuffer.remaining();
        if (byteBuffer.isDirect()) {
            preDefineClass = this.preDefineClass(s, preDefineClass);
            final Class<?> defineClass2 = this.defineClass2(s, byteBuffer, byteBuffer.position(), remaining, preDefineClass, this.defineClassSourceLocation(preDefineClass));
            this.postDefineClass(defineClass2, preDefineClass);
            return defineClass2;
        }
        if (byteBuffer.hasArray()) {
            return this.defineClass(s, byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), remaining, preDefineClass);
        }
        final byte[] array = new byte[remaining];
        byteBuffer.get(array);
        return this.defineClass(s, array, 0, remaining, preDefineClass);
    }
    
    private native Class<?> defineClass0(final String p0, final byte[] p1, final int p2, final int p3, final ProtectionDomain p4);
    
    private native Class<?> defineClass1(final String p0, final byte[] p1, final int p2, final int p3, final ProtectionDomain p4, final String p5);
    
    private native Class<?> defineClass2(final String p0, final ByteBuffer p1, final int p2, final int p3, final ProtectionDomain p4, final String p5);
    
    private boolean checkName(final String s) {
        return s == null || s.length() == 0 || (s.indexOf(47) == -1 && (VM.allowArraySyntax() || s.charAt(0) != '['));
    }
    
    private void checkCerts(final String s, final CodeSource codeSource) {
        final int lastIndex = s.lastIndexOf(46);
        final String s2 = (lastIndex == -1) ? "" : s.substring(0, lastIndex);
        Certificate[] certificates = null;
        if (codeSource != null) {
            certificates = codeSource.getCertificates();
        }
        Certificate[] array = null;
        if (this.parallelLockMap == null) {
            synchronized (this) {
                array = this.package2certs.get(s2);
                if (array == null) {
                    this.package2certs.put(s2, (certificates == null) ? ClassLoader.nocerts : certificates);
                }
            }
        }
        else {
            array = ((ConcurrentHashMap)this.package2certs).putIfAbsent(s2, (certificates == null) ? ClassLoader.nocerts : certificates);
        }
        if (array != null && !this.compareCerts(array, certificates)) {
            throw new SecurityException("class \"" + s + "\"'s signer information does not match signer information of other classes in the same package");
        }
    }
    
    private boolean compareCerts(final Certificate[] array, final Certificate[] array2) {
        if (array2 == null || array2.length == 0) {
            return array.length == 0;
        }
        if (array2.length != array.length) {
            return false;
        }
        for (int i = 0; i < array2.length; ++i) {
            boolean b = false;
            for (int j = 0; j < array.length; ++j) {
                if (array2[i].equals(array[j])) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                return false;
            }
        }
        for (int k = 0; k < array.length; ++k) {
            boolean b2 = false;
            for (int l = 0; l < array2.length; ++l) {
                if (array[k].equals(array2[l])) {
                    b2 = true;
                    break;
                }
            }
            if (!b2) {
                return false;
            }
        }
        return true;
    }
    
    protected final void resolveClass(final Class<?> clazz) {
        this.resolveClass0(clazz);
    }
    
    private native void resolveClass0(final Class<?> p0);
    
    protected final Class<?> findSystemClass(final String s) throws ClassNotFoundException {
        final ClassLoader systemClassLoader = getSystemClassLoader();
        if (systemClassLoader != null) {
            return systemClassLoader.loadClass(s);
        }
        if (!this.checkName(s)) {
            throw new ClassNotFoundException(s);
        }
        final Class<?> bootstrapClass = this.findBootstrapClass(s);
        if (bootstrapClass == null) {
            throw new ClassNotFoundException(s);
        }
        return bootstrapClass;
    }
    
    private Class<?> findBootstrapClassOrNull(final String s) {
        if (!this.checkName(s)) {
            return null;
        }
        return this.findBootstrapClass(s);
    }
    
    private native Class<?> findBootstrapClass(final String p0);
    
    protected final Class<?> findLoadedClass(final String s) {
        if (!this.checkName(s)) {
            return null;
        }
        return this.findLoadedClass0(s);
    }
    
    private final native Class<?> findLoadedClass0(final String p0);
    
    protected final void setSigners(final Class<?> clazz, final Object[] signers) {
        clazz.setSigners(signers);
    }
    
    public URL getResource(final String s) {
        URL url;
        if (this.parent != null) {
            url = this.parent.getResource(s);
        }
        else {
            url = getBootstrapResource(s);
        }
        if (url == null) {
            url = this.findResource(s);
        }
        return url;
    }
    
    public Enumeration<URL> getResources(final String s) throws IOException {
        final Enumeration[] array = new Enumeration[2];
        if (this.parent != null) {
            array[0] = this.parent.getResources(s);
        }
        else {
            array[0] = getBootstrapResources(s);
        }
        array[1] = this.findResources(s);
        return new CompoundEnumeration<URL>(array);
    }
    
    protected URL findResource(final String s) {
        return null;
    }
    
    protected Enumeration<URL> findResources(final String s) throws IOException {
        return Collections.emptyEnumeration();
    }
    
    @CallerSensitive
    protected static boolean registerAsParallelCapable() {
        return ParallelLoaders.register(Reflection.getCallerClass().asSubclass(ClassLoader.class));
    }
    
    public static URL getSystemResource(final String s) {
        final ClassLoader systemClassLoader = getSystemClassLoader();
        if (systemClassLoader == null) {
            return getBootstrapResource(s);
        }
        return systemClassLoader.getResource(s);
    }
    
    public static Enumeration<URL> getSystemResources(final String s) throws IOException {
        final ClassLoader systemClassLoader = getSystemClassLoader();
        if (systemClassLoader == null) {
            return getBootstrapResources(s);
        }
        return systemClassLoader.getResources(s);
    }
    
    private static URL getBootstrapResource(final String s) {
        final Resource resource = getBootstrapClassPath().getResource(s);
        return (resource != null) ? resource.getURL() : null;
    }
    
    private static Enumeration<URL> getBootstrapResources(final String s) throws IOException {
        return new Enumeration<URL>() {
            final /* synthetic */ Enumeration val$e = getBootstrapClassPath().getResources(s);
            
            @Override
            public URL nextElement() {
                return this.val$e.nextElement().getURL();
            }
            
            @Override
            public boolean hasMoreElements() {
                return this.val$e.hasMoreElements();
            }
        };
    }
    
    static URLClassPath getBootstrapClassPath() {
        return Launcher.getBootstrapClassPath();
    }
    
    public InputStream getResourceAsStream(final String s) {
        final URL resource = this.getResource(s);
        try {
            return (resource != null) ? resource.openStream() : null;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public static InputStream getSystemResourceAsStream(final String s) {
        final URL systemResource = getSystemResource(s);
        try {
            return (systemResource != null) ? systemResource.openStream() : null;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    @CallerSensitive
    public final ClassLoader getParent() {
        if (this.parent == null) {
            return null;
        }
        if (System.getSecurityManager() != null) {
            checkClassLoaderPermission(this.parent, Reflection.getCallerClass());
        }
        return this.parent;
    }
    
    @CallerSensitive
    public static ClassLoader getSystemClassLoader() {
        initSystemClassLoader();
        if (ClassLoader.scl == null) {
            return null;
        }
        if (System.getSecurityManager() != null) {
            checkClassLoaderPermission(ClassLoader.scl, Reflection.getCallerClass());
        }
        return ClassLoader.scl;
    }
    
    private static synchronized void initSystemClassLoader() {
        if (!ClassLoader.sclSet) {
            if (ClassLoader.scl != null) {
                throw new IllegalStateException("recursive invocation");
            }
            final Launcher launcher = Launcher.getLauncher();
            if (launcher != null) {
                Throwable t = null;
                ClassLoader.scl = launcher.getClassLoader();
                try {
                    ClassLoader.scl = AccessController.doPrivileged((PrivilegedExceptionAction<ClassLoader>)new SystemClassLoaderAction(ClassLoader.scl));
                }
                catch (PrivilegedActionException ex) {
                    t = ex.getCause();
                    if (t instanceof InvocationTargetException) {
                        t = t.getCause();
                    }
                }
                if (t != null) {
                    if (t instanceof Error) {
                        throw (Error)t;
                    }
                    throw new Error(t);
                }
            }
            ClassLoader.sclSet = true;
        }
    }
    
    boolean isAncestor(final ClassLoader classLoader) {
        ClassLoader parent = this;
        do {
            parent = parent.parent;
            if (classLoader == parent) {
                return true;
            }
        } while (parent != null);
        return false;
    }
    
    private static boolean needsClassLoaderPermissionCheck(final ClassLoader classLoader, final ClassLoader classLoader2) {
        return classLoader != classLoader2 && classLoader != null && !classLoader2.isAncestor(classLoader);
    }
    
    static ClassLoader getClassLoader(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return clazz.getClassLoader0();
    }
    
    static void checkClassLoaderPermission(final ClassLoader classLoader, final Class<?> clazz) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null && needsClassLoaderPermissionCheck(getClassLoader(clazz), classLoader)) {
            securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
        }
    }
    
    protected Package definePackage(final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7, final URL url) throws IllegalArgumentException {
        synchronized (this.packages) {
            if (this.getPackage(s) != null) {
                throw new IllegalArgumentException(s);
            }
            final Package package1 = new Package(s, s2, s3, s4, s5, s6, s7, url, this);
            this.packages.put(s, package1);
            return package1;
        }
    }
    
    protected Package getPackage(final String s) {
        Package package1;
        synchronized (this.packages) {
            package1 = this.packages.get(s);
        }
        if (package1 == null) {
            if (this.parent != null) {
                package1 = this.parent.getPackage(s);
            }
            else {
                package1 = Package.getSystemPackage(s);
            }
            if (package1 != null) {
                synchronized (this.packages) {
                    final Package package2 = this.packages.get(s);
                    if (package2 == null) {
                        this.packages.put(s, package1);
                    }
                    else {
                        package1 = package2;
                    }
                }
            }
        }
        return package1;
    }
    
    protected Package[] getPackages() {
        final HashMap<Object, Package> hashMap;
        synchronized (this.packages) {
            hashMap = new HashMap<Object, Package>(this.packages);
        }
        Package[] array;
        if (this.parent != null) {
            array = this.parent.getPackages();
        }
        else {
            array = Package.getSystemPackages();
        }
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                final String name = array[i].getName();
                if (hashMap.get(name) == null) {
                    hashMap.put(name, array[i]);
                }
            }
        }
        return hashMap.values().toArray(new Package[hashMap.size()]);
    }
    
    protected String findLibrary(final String s) {
        return null;
    }
    
    private static String[] initializePath(final String s) {
        final String property = System.getProperty(s, "");
        final String pathSeparator = File.pathSeparator;
        final int length = property.length();
        int i = property.indexOf(pathSeparator);
        int n = 0;
        while (i >= 0) {
            ++n;
            i = property.indexOf(pathSeparator, i + 1);
        }
        final String[] array = new String[n + 1];
        int n3;
        int n2 = n3 = 0;
        for (int j = property.indexOf(pathSeparator); j >= 0; j = property.indexOf(pathSeparator, n2)) {
            if (j - n2 > 0) {
                array[n3++] = property.substring(n2, j);
            }
            else if (j - n2 == 0) {
                array[n3++] = ".";
            }
            n2 = j + 1;
        }
        array[n3] = property.substring(n2, length);
        return array;
    }
    
    static void loadLibrary(final Class<?> clazz, final String s, final boolean b) {
        final ClassLoader classLoader = (clazz == null) ? null : clazz.getClassLoader();
        if (ClassLoader.sys_paths == null) {
            ClassLoader.usr_paths = initializePath("java.library.path");
            ClassLoader.sys_paths = initializePath("sun.boot.library.path");
        }
        if (!b) {
            if (classLoader != null) {
                final String library = classLoader.findLibrary(s);
                if (library != null) {
                    final File file = new File(library);
                    if (!file.isAbsolute()) {
                        throw new UnsatisfiedLinkError("ClassLoader.findLibrary failed to return an absolute path: " + library);
                    }
                    if (loadLibrary0(clazz, file)) {
                        return;
                    }
                    throw new UnsatisfiedLinkError("Can't load " + library);
                }
            }
            for (int i = 0; i < ClassLoader.sys_paths.length; ++i) {
                final File file2 = new File(ClassLoader.sys_paths[i], System.mapLibraryName(s));
                if (loadLibrary0(clazz, file2)) {
                    return;
                }
                final File mapAlternativeName = ClassLoaderHelper.mapAlternativeName(file2);
                if (mapAlternativeName != null && loadLibrary0(clazz, mapAlternativeName)) {
                    return;
                }
            }
            if (classLoader != null) {
                for (int j = 0; j < ClassLoader.usr_paths.length; ++j) {
                    final File file3 = new File(ClassLoader.usr_paths[j], System.mapLibraryName(s));
                    if (loadLibrary0(clazz, file3)) {
                        return;
                    }
                    final File mapAlternativeName2 = ClassLoaderHelper.mapAlternativeName(file3);
                    if (mapAlternativeName2 != null && loadLibrary0(clazz, mapAlternativeName2)) {
                        return;
                    }
                }
            }
            throw new UnsatisfiedLinkError("no " + s + " in java.library.path");
        }
        if (loadLibrary0(clazz, new File(s))) {
            return;
        }
        throw new UnsatisfiedLinkError("Can't load library: " + s);
    }
    
    private static native String findBuiltinLib(final String p0);
    
    private static boolean loadLibrary0(final Class<?> clazz, final File file) {
        String s = findBuiltinLib(file.getName());
        final boolean b = s != null;
        if (!b) {
            if (AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    return file.exists() ? Boolean.TRUE : null;
                }
            }) == null) {
                return false;
            }
            try {
                s = file.getCanonicalPath();
            }
            catch (IOException ex) {
                return false;
            }
        }
        final ClassLoader classLoader = (clazz == null) ? null : clazz.getClassLoader();
        final Vector<NativeLibrary> vector = (classLoader != null) ? classLoader.nativeLibraries : ClassLoader.systemNativeLibraries;
        synchronized (vector) {
            for (int size = vector.size(), i = 0; i < size; ++i) {
                if (s.equals(vector.elementAt(i).name)) {
                    return true;
                }
            }
            synchronized (ClassLoader.loadedLibraryNames) {
                if (ClassLoader.loadedLibraryNames.contains(s)) {
                    throw new UnsatisfiedLinkError("Native Library " + s + " already loaded in another classloader");
                }
                final int size2 = ClassLoader.nativeLibraryContext.size();
                int j = 0;
                while (j < size2) {
                    final NativeLibrary nativeLibrary = ClassLoader.nativeLibraryContext.elementAt(j);
                    if (s.equals(nativeLibrary.name)) {
                        if (classLoader == nativeLibrary.fromClass.getClassLoader()) {
                            return true;
                        }
                        throw new UnsatisfiedLinkError("Native Library " + s + " is being loaded in another classloader");
                    }
                    else {
                        ++j;
                    }
                }
                final NativeLibrary nativeLibrary2 = new NativeLibrary(clazz, s, b);
                ClassLoader.nativeLibraryContext.push(nativeLibrary2);
                try {
                    nativeLibrary2.load(s, b);
                }
                finally {
                    ClassLoader.nativeLibraryContext.pop();
                }
                if (nativeLibrary2.loaded) {
                    ClassLoader.loadedLibraryNames.addElement(s);
                    vector.addElement(nativeLibrary2);
                    return true;
                }
                return false;
            }
        }
    }
    
    static long findNative(final ClassLoader classLoader, final String s) {
        final Vector<NativeLibrary> vector = (classLoader != null) ? classLoader.nativeLibraries : ClassLoader.systemNativeLibraries;
        synchronized (vector) {
            for (int size = vector.size(), i = 0; i < size; ++i) {
                final long find = vector.elementAt(i).find(s);
                if (find != 0L) {
                    return find;
                }
            }
        }
        return 0L;
    }
    
    public void setDefaultAssertionStatus(final boolean defaultAssertionStatus) {
        synchronized (this.assertionLock) {
            if (this.classAssertionStatus == null) {
                this.initializeJavaAssertionMaps();
            }
            this.defaultAssertionStatus = defaultAssertionStatus;
        }
    }
    
    public void setPackageAssertionStatus(final String s, final boolean b) {
        synchronized (this.assertionLock) {
            if (this.packageAssertionStatus == null) {
                this.initializeJavaAssertionMaps();
            }
            this.packageAssertionStatus.put(s, b);
        }
    }
    
    public void setClassAssertionStatus(final String s, final boolean b) {
        synchronized (this.assertionLock) {
            if (this.classAssertionStatus == null) {
                this.initializeJavaAssertionMaps();
            }
            this.classAssertionStatus.put(s, b);
        }
    }
    
    public void clearAssertionStatus() {
        synchronized (this.assertionLock) {
            this.classAssertionStatus = new HashMap<String, Boolean>();
            this.packageAssertionStatus = new HashMap<String, Boolean>();
            this.defaultAssertionStatus = false;
        }
    }
    
    boolean desiredAssertionStatus(String substring) {
        synchronized (this.assertionLock) {
            final Boolean b = this.classAssertionStatus.get(substring);
            if (b != null) {
                return b;
            }
            int i = substring.lastIndexOf(".");
            if (i < 0) {
                final Boolean b2 = this.packageAssertionStatus.get(null);
                if (b2 != null) {
                    return b2;
                }
            }
            while (i > 0) {
                substring = substring.substring(0, i);
                final Boolean b3 = this.packageAssertionStatus.get(substring);
                if (b3 != null) {
                    return b3;
                }
                i = substring.lastIndexOf(".", i - 1);
            }
            return this.defaultAssertionStatus;
        }
    }
    
    private void initializeJavaAssertionMaps() {
        this.classAssertionStatus = new HashMap<String, Boolean>();
        this.packageAssertionStatus = new HashMap<String, Boolean>();
        final AssertionStatusDirectives retrieveDirectives = retrieveDirectives();
        for (int i = 0; i < retrieveDirectives.classes.length; ++i) {
            this.classAssertionStatus.put(retrieveDirectives.classes[i], retrieveDirectives.classEnabled[i]);
        }
        for (int j = 0; j < retrieveDirectives.packages.length; ++j) {
            this.packageAssertionStatus.put(retrieveDirectives.packages[j], retrieveDirectives.packageEnabled[j]);
        }
        this.defaultAssertionStatus = retrieveDirectives.deflt;
    }
    
    private static native AssertionStatusDirectives retrieveDirectives();
    
    static {
        registerNatives();
        nocerts = new Certificate[0];
        ClassLoader.loadedLibraryNames = new Vector<String>();
        ClassLoader.systemNativeLibraries = new Vector<NativeLibrary>();
        ClassLoader.nativeLibraryContext = new Stack<NativeLibrary>();
    }
    
    static class NativeLibrary
    {
        long handle;
        private int jniVersion;
        private final Class<?> fromClass;
        String name;
        boolean isBuiltin;
        boolean loaded;
        
        native void load(final String p0, final boolean p1);
        
        native long find(final String p0);
        
        native void unload(final String p0, final boolean p1);
        
        public NativeLibrary(final Class<?> fromClass, final String name, final boolean isBuiltin) {
            this.name = name;
            this.fromClass = fromClass;
            this.isBuiltin = isBuiltin;
        }
        
        @Override
        protected void finalize() {
            synchronized (ClassLoader.loadedLibraryNames) {
                if (this.fromClass.getClassLoader() != null && this.loaded) {
                    for (int size = ClassLoader.loadedLibraryNames.size(), i = 0; i < size; ++i) {
                        if (this.name.equals(ClassLoader.loadedLibraryNames.elementAt(i))) {
                            ClassLoader.loadedLibraryNames.removeElementAt(i);
                            break;
                        }
                    }
                    ClassLoader.nativeLibraryContext.push(this);
                    try {
                        this.unload(this.name, this.isBuiltin);
                    }
                    finally {
                        ClassLoader.nativeLibraryContext.pop();
                    }
                }
            }
        }
        
        static Class<?> getFromClass() {
            return ClassLoader.nativeLibraryContext.peek().fromClass;
        }
    }
    
    private static class ParallelLoaders
    {
        private static final Set<Class<? extends ClassLoader>> loaderTypes;
        
        static boolean register(final Class<? extends ClassLoader> clazz) {
            synchronized (ParallelLoaders.loaderTypes) {
                if (ParallelLoaders.loaderTypes.contains(clazz.getSuperclass())) {
                    ParallelLoaders.loaderTypes.add(clazz);
                    return true;
                }
                return false;
            }
        }
        
        static boolean isRegistered(final Class<? extends ClassLoader> clazz) {
            synchronized (ParallelLoaders.loaderTypes) {
                return ParallelLoaders.loaderTypes.contains(clazz);
            }
        }
        
        static {
            synchronized (loaderTypes = Collections.newSetFromMap(new WeakHashMap<Class<? extends ClassLoader>, Boolean>())) {
                ParallelLoaders.loaderTypes.add(ClassLoader.class);
            }
        }
    }
}
