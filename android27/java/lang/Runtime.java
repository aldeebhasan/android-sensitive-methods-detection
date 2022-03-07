package java.lang;

import java.security.*;
import java.util.*;
import sun.reflect.*;
import java.io.*;

public class Runtime
{
    private static Runtime currentRuntime;
    
    public static Runtime getRuntime() {
        return Runtime.currentRuntime;
    }
    
    public void exit(final int n) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkExit(n);
        }
        Shutdown.exit(n);
    }
    
    public void addShutdownHook(final Thread thread) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("shutdownHooks"));
        }
        ApplicationShutdownHooks.add(thread);
    }
    
    public boolean removeShutdownHook(final Thread thread) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("shutdownHooks"));
        }
        return ApplicationShutdownHooks.remove(thread);
    }
    
    public void halt(final int n) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkExit(n);
        }
        Shutdown.beforeHalt();
        Shutdown.halt(n);
    }
    
    @Deprecated
    public static void runFinalizersOnExit(final boolean runFinalizersOnExit) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkExit(0);
            }
            catch (SecurityException ex) {
                throw new SecurityException("runFinalizersOnExit");
            }
        }
        Shutdown.setRunFinalizersOnExit(runFinalizersOnExit);
    }
    
    public Process exec(final String s) throws IOException {
        return this.exec(s, null, null);
    }
    
    public Process exec(final String s, final String[] array) throws IOException {
        return this.exec(s, array, null);
    }
    
    public Process exec(final String s, final String[] array, final File file) throws IOException {
        if (s.length() == 0) {
            throw new IllegalArgumentException("Empty command");
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(s);
        final String[] array2 = new String[stringTokenizer.countTokens()];
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            array2[n] = stringTokenizer.nextToken();
            ++n;
        }
        return this.exec(array2, array, file);
    }
    
    public Process exec(final String[] array) throws IOException {
        return this.exec(array, null, null);
    }
    
    public Process exec(final String[] array, final String[] array2) throws IOException {
        return this.exec(array, array2, null);
    }
    
    public Process exec(final String[] array, final String[] array2, final File file) throws IOException {
        return new ProcessBuilder(array).environment(array2).directory(file).start();
    }
    
    public native int availableProcessors();
    
    public native long freeMemory();
    
    public native long totalMemory();
    
    public native long maxMemory();
    
    public native void gc();
    
    private static native void runFinalization0();
    
    public void runFinalization() {
        runFinalization0();
    }
    
    public native void traceInstructions(final boolean p0);
    
    public native void traceMethodCalls(final boolean p0);
    
    @CallerSensitive
    public void load(final String s) {
        this.load0(Reflection.getCallerClass(), s);
    }
    
    synchronized void load0(final Class<?> clazz, final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkLink(s);
        }
        if (!new File(s).isAbsolute()) {
            throw new UnsatisfiedLinkError("Expecting an absolute path of the library: " + s);
        }
        ClassLoader.loadLibrary(clazz, s, true);
    }
    
    @CallerSensitive
    public void loadLibrary(final String s) {
        this.loadLibrary0(Reflection.getCallerClass(), s);
    }
    
    synchronized void loadLibrary0(final Class<?> clazz, final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkLink(s);
        }
        if (s.indexOf(File.separatorChar) != -1) {
            throw new UnsatisfiedLinkError("Directory separator should not appear in library name: " + s);
        }
        ClassLoader.loadLibrary(clazz, s, false);
    }
    
    @Deprecated
    public InputStream getLocalizedInputStream(final InputStream inputStream) {
        return inputStream;
    }
    
    @Deprecated
    public OutputStream getLocalizedOutputStream(final OutputStream outputStream) {
        return outputStream;
    }
    
    static {
        Runtime.currentRuntime = new Runtime();
    }
}
