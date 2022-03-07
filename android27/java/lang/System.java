package java.lang;

import java.nio.channels.*;
import java.nio.channels.spi.*;
import sun.security.util.*;
import java.io.*;
import sun.misc.*;
import sun.reflect.*;
import sun.reflect.annotation.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import sun.nio.ch.*;
import java.security.*;
import java.util.*;

public final class System
{
    public static final InputStream in;
    public static final PrintStream out;
    public static final PrintStream err;
    private static volatile SecurityManager security;
    private static volatile Console cons;
    private static Properties props;
    private static String lineSeparator;
    
    private static native void registerNatives();
    
    public static void setIn(final InputStream in0) {
        checkIO();
        setIn0(in0);
    }
    
    public static void setOut(final PrintStream out0) {
        checkIO();
        setOut0(out0);
    }
    
    public static void setErr(final PrintStream err0) {
        checkIO();
        setErr0(err0);
    }
    
    public static Console console() {
        if (System.cons == null) {
            synchronized (System.class) {
                System.cons = SharedSecrets.getJavaIOAccess().console();
            }
        }
        return System.cons;
    }
    
    public static Channel inheritedChannel() throws IOException {
        return SelectorProvider.provider().inheritedChannel();
    }
    
    private static void checkIO() {
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("setIO"));
        }
    }
    
    private static native void setIn0(final InputStream p0);
    
    private static native void setOut0(final PrintStream p0);
    
    private static native void setErr0(final PrintStream p0);
    
    public static void setSecurityManager(final SecurityManager securityManager0) {
        try {
            securityManager0.checkPackageAccess("java.lang");
        }
        catch (Exception ex) {}
        setSecurityManager0(securityManager0);
    }
    
    private static synchronized void setSecurityManager0(final SecurityManager security) {
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("setSecurityManager"));
        }
        if (security != null && security.getClass().getClassLoader() != null) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    security.getClass().getProtectionDomain().implies(SecurityConstants.ALL_PERMISSION);
                    return null;
                }
            });
        }
        System.security = security;
    }
    
    public static SecurityManager getSecurityManager() {
        return System.security;
    }
    
    public static native long currentTimeMillis();
    
    public static native long nanoTime();
    
    public static native void arraycopy(final Object p0, final int p1, final Object p2, final int p3, final int p4);
    
    public static native int identityHashCode(final Object p0);
    
    private static native Properties initProperties(final Properties p0);
    
    public static Properties getProperties() {
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertiesAccess();
        }
        return System.props;
    }
    
    public static String lineSeparator() {
        return System.lineSeparator;
    }
    
    public static void setProperties(Properties props) {
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertiesAccess();
        }
        if (props == null) {
            props = new Properties();
            initProperties(props);
        }
        System.props = props;
    }
    
    public static String getProperty(final String s) {
        checkKey(s);
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertyAccess(s);
        }
        return System.props.getProperty(s);
    }
    
    public static String getProperty(final String s, final String s2) {
        checkKey(s);
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertyAccess(s);
        }
        return System.props.getProperty(s, s2);
    }
    
    public static String setProperty(final String s, final String s2) {
        checkKey(s);
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new PropertyPermission(s, "write"));
        }
        return (String)System.props.setProperty(s, s2);
    }
    
    public static String clearProperty(final String s) {
        checkKey(s);
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new PropertyPermission(s, "write"));
        }
        return ((Hashtable<K, String>)System.props).remove(s);
    }
    
    private static void checkKey(final String s) {
        if (s == null) {
            throw new NullPointerException("key can't be null");
        }
        if (s.equals("")) {
            throw new IllegalArgumentException("key can't be empty");
        }
    }
    
    public static String getenv(final String s) {
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getenv." + s));
        }
        return ProcessEnvironment.getenv(s);
    }
    
    public static Map<String, String> getenv() {
        final SecurityManager securityManager = getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getenv.*"));
        }
        return ProcessEnvironment.getenv();
    }
    
    public static void exit(final int n) {
        Runtime.getRuntime().exit(n);
    }
    
    public static void gc() {
        Runtime.getRuntime().gc();
    }
    
    public static void runFinalization() {
        Runtime.getRuntime().runFinalization();
    }
    
    @Deprecated
    public static void runFinalizersOnExit(final boolean b) {
        Runtime.runFinalizersOnExit(b);
    }
    
    @CallerSensitive
    public static void load(final String s) {
        Runtime.getRuntime().load0(Reflection.getCallerClass(), s);
    }
    
    @CallerSensitive
    public static void loadLibrary(final String s) {
        Runtime.getRuntime().loadLibrary0(Reflection.getCallerClass(), s);
    }
    
    public static native String mapLibraryName(final String p0);
    
    private static PrintStream newPrintStream(final FileOutputStream fileOutputStream, final String s) {
        if (s != null) {
            try {
                return new PrintStream(new BufferedOutputStream(fileOutputStream, 128), true, s);
            }
            catch (UnsupportedEncodingException ex) {}
        }
        return new PrintStream(new BufferedOutputStream(fileOutputStream, 128), true);
    }
    
    private static void initializeSystemClass() {
        initProperties(System.props = new Properties());
        VM.saveAndRemoveProperties(System.props);
        System.lineSeparator = System.props.getProperty("line.separator");
        Version.init();
        final FileInputStream fileInputStream = new FileInputStream(FileDescriptor.in);
        final FileOutputStream fileOutputStream = new FileOutputStream(FileDescriptor.out);
        final FileOutputStream fileOutputStream2 = new FileOutputStream(FileDescriptor.err);
        setIn0(new BufferedInputStream(fileInputStream));
        setOut0(newPrintStream(fileOutputStream, System.props.getProperty("sun.stdout.encoding")));
        setErr0(newPrintStream(fileOutputStream2, System.props.getProperty("sun.stderr.encoding")));
        loadLibrary("zip");
        Terminator.setup();
        VM.initializeOSEnvironment();
        final Thread currentThread = Thread.currentThread();
        currentThread.getThreadGroup().add(currentThread);
        setJavaLangAccess();
        VM.booted();
    }
    
    private static void setJavaLangAccess() {
        SharedSecrets.setJavaLangAccess(new JavaLangAccess() {
            @Override
            public ConstantPool getConstantPool(final Class<?> clazz) {
                return clazz.getConstantPool();
            }
            
            @Override
            public boolean casAnnotationType(final Class<?> clazz, final AnnotationType annotationType, final AnnotationType annotationType2) {
                return clazz.casAnnotationType(annotationType, annotationType2);
            }
            
            @Override
            public AnnotationType getAnnotationType(final Class<?> clazz) {
                return clazz.getAnnotationType();
            }
            
            @Override
            public Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap(final Class<?> clazz) {
                return (Map<Class<? extends Annotation>, Annotation>)clazz.getDeclaredAnnotationMap();
            }
            
            @Override
            public byte[] getRawClassAnnotations(final Class<?> clazz) {
                return clazz.getRawAnnotations();
            }
            
            @Override
            public byte[] getRawClassTypeAnnotations(final Class<?> clazz) {
                return clazz.getRawTypeAnnotations();
            }
            
            @Override
            public byte[] getRawExecutableTypeAnnotations(final Executable executable) {
                return Class.getExecutableTypeAnnotationBytes(executable);
            }
            
            @Override
            public <E extends Enum<E>> E[] getEnumConstantsShared(final Class<E> clazz) {
                return clazz.getEnumConstantsShared();
            }
            
            @Override
            public void blockedOn(final Thread thread, final Interruptible interruptible) {
                thread.blockedOn(interruptible);
            }
            
            @Override
            public void registerShutdownHook(final int n, final boolean b, final Runnable runnable) {
                Shutdown.add(n, b, runnable);
            }
            
            @Override
            public int getStackTraceDepth(final Throwable t) {
                return t.getStackTraceDepth();
            }
            
            @Override
            public StackTraceElement getStackTraceElement(final Throwable t, final int n) {
                return t.getStackTraceElement(n);
            }
            
            @Override
            public String newStringUnsafe(final char[] array) {
                return new String(array, true);
            }
            
            @Override
            public Thread newThreadWithAcc(final Runnable runnable, final AccessControlContext accessControlContext) {
                return new Thread(runnable, accessControlContext);
            }
            
            @Override
            public void invokeFinalize(final Object o) throws Throwable {
                o.finalize();
            }
        });
    }
    
    static {
        registerNatives();
        in = null;
        out = null;
        err = null;
        System.security = null;
        System.cons = null;
    }
}
