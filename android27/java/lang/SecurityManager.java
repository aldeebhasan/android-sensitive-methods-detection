package java.lang;

import sun.security.util.*;
import java.io.*;
import java.net.*;
import java.util.*;
import sun.reflect.*;
import java.security.*;

public class SecurityManager
{
    @Deprecated
    protected boolean inCheck;
    private boolean initialized;
    private static ThreadGroup rootGroup;
    private static boolean packageAccessValid;
    private static String[] packageAccess;
    private static final Object packageAccessLock;
    private static boolean packageDefinitionValid;
    private static String[] packageDefinition;
    private static final Object packageDefinitionLock;
    
    private boolean hasAllPermission() {
        try {
            this.checkPermission(SecurityConstants.ALL_PERMISSION);
            return true;
        }
        catch (SecurityException ex) {
            return false;
        }
    }
    
    @Deprecated
    public boolean getInCheck() {
        return this.inCheck;
    }
    
    public SecurityManager() {
        this.initialized = false;
        synchronized (SecurityManager.class) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(new RuntimePermission("createSecurityManager"));
            }
            this.initialized = true;
        }
    }
    
    protected native Class[] getClassContext();
    
    @Deprecated
    protected ClassLoader currentClassLoader() {
        ClassLoader currentClassLoader0 = this.currentClassLoader0();
        if (currentClassLoader0 != null && this.hasAllPermission()) {
            currentClassLoader0 = null;
        }
        return currentClassLoader0;
    }
    
    private native ClassLoader currentClassLoader0();
    
    @Deprecated
    protected Class<?> currentLoadedClass() {
        Class<?> currentLoadedClass0 = this.currentLoadedClass0();
        if (currentLoadedClass0 != null && this.hasAllPermission()) {
            currentLoadedClass0 = null;
        }
        return currentLoadedClass0;
    }
    
    @Deprecated
    protected native int classDepth(final String p0);
    
    @Deprecated
    protected int classLoaderDepth() {
        int classLoaderDepth0 = this.classLoaderDepth0();
        if (classLoaderDepth0 != -1) {
            if (this.hasAllPermission()) {
                classLoaderDepth0 = -1;
            }
            else {
                --classLoaderDepth0;
            }
        }
        return classLoaderDepth0;
    }
    
    private native int classLoaderDepth0();
    
    @Deprecated
    protected boolean inClass(final String s) {
        return this.classDepth(s) >= 0;
    }
    
    @Deprecated
    protected boolean inClassLoader() {
        return this.currentClassLoader() != null;
    }
    
    public Object getSecurityContext() {
        return AccessController.getContext();
    }
    
    public void checkPermission(final Permission permission) {
        AccessController.checkPermission(permission);
    }
    
    public void checkPermission(final Permission permission, final Object o) {
        if (o instanceof AccessControlContext) {
            ((AccessControlContext)o).checkPermission(permission);
            return;
        }
        throw new SecurityException();
    }
    
    public void checkCreateClassLoader() {
        this.checkPermission(SecurityConstants.CREATE_CLASSLOADER_PERMISSION);
    }
    
    private static ThreadGroup getRootGroup() {
        ThreadGroup threadGroup;
        for (threadGroup = Thread.currentThread().getThreadGroup(); threadGroup.getParent() != null; threadGroup = threadGroup.getParent()) {}
        return threadGroup;
    }
    
    public void checkAccess(final Thread thread) {
        if (thread == null) {
            throw new NullPointerException("thread can't be null");
        }
        if (thread.getThreadGroup() == SecurityManager.rootGroup) {
            this.checkPermission(SecurityConstants.MODIFY_THREAD_PERMISSION);
        }
    }
    
    public void checkAccess(final ThreadGroup threadGroup) {
        if (threadGroup == null) {
            throw new NullPointerException("thread group can't be null");
        }
        if (threadGroup == SecurityManager.rootGroup) {
            this.checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
        }
    }
    
    public void checkExit(final int n) {
        this.checkPermission(new RuntimePermission("exitVM." + n));
    }
    
    public void checkExec(final String s) {
        if (new File(s).isAbsolute()) {
            this.checkPermission(new FilePermission(s, "execute"));
        }
        else {
            this.checkPermission(new FilePermission("<<ALL FILES>>", "execute"));
        }
    }
    
    public void checkLink(final String s) {
        if (s == null) {
            throw new NullPointerException("library can't be null");
        }
        this.checkPermission(new RuntimePermission("loadLibrary." + s));
    }
    
    public void checkRead(final FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) {
            throw new NullPointerException("file descriptor can't be null");
        }
        this.checkPermission(new RuntimePermission("readFileDescriptor"));
    }
    
    public void checkRead(final String s) {
        this.checkPermission(new FilePermission(s, "read"));
    }
    
    public void checkRead(final String s, final Object o) {
        this.checkPermission(new FilePermission(s, "read"), o);
    }
    
    public void checkWrite(final FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) {
            throw new NullPointerException("file descriptor can't be null");
        }
        this.checkPermission(new RuntimePermission("writeFileDescriptor"));
    }
    
    public void checkWrite(final String s) {
        this.checkPermission(new FilePermission(s, "write"));
    }
    
    public void checkDelete(final String s) {
        this.checkPermission(new FilePermission(s, "delete"));
    }
    
    public void checkConnect(String string, final int n) {
        if (string == null) {
            throw new NullPointerException("host can't be null");
        }
        if (!string.startsWith("[") && string.indexOf(58) != -1) {
            string = "[" + string + "]";
        }
        if (n == -1) {
            this.checkPermission(new SocketPermission(string, "resolve"));
        }
        else {
            this.checkPermission(new SocketPermission(string + ":" + n, "connect"));
        }
    }
    
    public void checkConnect(String string, final int n, final Object o) {
        if (string == null) {
            throw new NullPointerException("host can't be null");
        }
        if (!string.startsWith("[") && string.indexOf(58) != -1) {
            string = "[" + string + "]";
        }
        if (n == -1) {
            this.checkPermission(new SocketPermission(string, "resolve"), o);
        }
        else {
            this.checkPermission(new SocketPermission(string + ":" + n, "connect"), o);
        }
    }
    
    public void checkListen(final int n) {
        this.checkPermission(new SocketPermission("localhost:" + n, "listen"));
    }
    
    public void checkAccept(String string, final int n) {
        if (string == null) {
            throw new NullPointerException("host can't be null");
        }
        if (!string.startsWith("[") && string.indexOf(58) != -1) {
            string = "[" + string + "]";
        }
        this.checkPermission(new SocketPermission(string + ":" + n, "accept"));
    }
    
    public void checkMulticast(final InetAddress inetAddress) {
        String s = inetAddress.getHostAddress();
        if (!s.startsWith("[") && s.indexOf(58) != -1) {
            s = "[" + s + "]";
        }
        this.checkPermission(new SocketPermission(s, "connect,accept"));
    }
    
    @Deprecated
    public void checkMulticast(final InetAddress inetAddress, final byte b) {
        String s = inetAddress.getHostAddress();
        if (!s.startsWith("[") && s.indexOf(58) != -1) {
            s = "[" + s + "]";
        }
        this.checkPermission(new SocketPermission(s, "connect,accept"));
    }
    
    public void checkPropertiesAccess() {
        this.checkPermission(new PropertyPermission("*", "read,write"));
    }
    
    public void checkPropertyAccess(final String s) {
        this.checkPermission(new PropertyPermission(s, "read"));
    }
    
    @Deprecated
    public boolean checkTopLevelWindow(final Object o) {
        if (o == null) {
            throw new NullPointerException("window can't be null");
        }
        Permission permission = SecurityConstants.AWT.TOPLEVEL_WINDOW_PERMISSION;
        if (permission == null) {
            permission = SecurityConstants.ALL_PERMISSION;
        }
        try {
            this.checkPermission(permission);
            return true;
        }
        catch (SecurityException ex) {
            return false;
        }
    }
    
    public void checkPrintJobAccess() {
        this.checkPermission(new RuntimePermission("queuePrintJob"));
    }
    
    @Deprecated
    public void checkSystemClipboardAccess() {
        Permission permission = SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION;
        if (permission == null) {
            permission = SecurityConstants.ALL_PERMISSION;
        }
        this.checkPermission(permission);
    }
    
    @Deprecated
    public void checkAwtEventQueueAccess() {
        Permission permission = SecurityConstants.AWT.CHECK_AWT_EVENTQUEUE_PERMISSION;
        if (permission == null) {
            permission = SecurityConstants.ALL_PERMISSION;
        }
        this.checkPermission(permission);
    }
    
    private static String[] getPackages(final String s) {
        String[] array = null;
        if (s != null && !s.equals("")) {
            final StringTokenizer stringTokenizer = new StringTokenizer(s, ",");
            final int countTokens = stringTokenizer.countTokens();
            if (countTokens > 0) {
                array = new String[countTokens];
                int n = 0;
                while (stringTokenizer.hasMoreElements()) {
                    array[n++] = stringTokenizer.nextToken().trim();
                }
            }
        }
        if (array == null) {
            array = new String[0];
        }
        return array;
    }
    
    public void checkPackageAccess(final String s) {
        if (s == null) {
            throw new NullPointerException("package name can't be null");
        }
        final String[] packageAccess;
        synchronized (SecurityManager.packageAccessLock) {
            if (!SecurityManager.packageAccessValid) {
                SecurityManager.packageAccess = getPackages(AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
                    @Override
                    public String run() {
                        return Security.getProperty("package.access");
                    }
                }));
                SecurityManager.packageAccessValid = true;
            }
            packageAccess = SecurityManager.packageAccess;
        }
        for (int i = 0; i < packageAccess.length; ++i) {
            if (s.startsWith(packageAccess[i]) || packageAccess[i].equals(s + ".")) {
                this.checkPermission(new RuntimePermission("accessClassInPackage." + s));
                break;
            }
        }
    }
    
    public void checkPackageDefinition(final String s) {
        if (s == null) {
            throw new NullPointerException("package name can't be null");
        }
        final String[] packageDefinition;
        synchronized (SecurityManager.packageDefinitionLock) {
            if (!SecurityManager.packageDefinitionValid) {
                SecurityManager.packageDefinition = getPackages(AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
                    @Override
                    public String run() {
                        return Security.getProperty("package.definition");
                    }
                }));
                SecurityManager.packageDefinitionValid = true;
            }
            packageDefinition = SecurityManager.packageDefinition;
        }
        for (int i = 0; i < packageDefinition.length; ++i) {
            if (s.startsWith(packageDefinition[i]) || packageDefinition[i].equals(s + ".")) {
                this.checkPermission(new RuntimePermission("defineClassInPackage." + s));
                break;
            }
        }
    }
    
    public void checkSetFactory() {
        this.checkPermission(new RuntimePermission("setFactory"));
    }
    
    @Deprecated
    @CallerSensitive
    public void checkMemberAccess(final Class<?> clazz, final int n) {
        if (clazz == null) {
            throw new NullPointerException("class can't be null");
        }
        if (n != 0) {
            final Class[] classContext = this.getClassContext();
            if (classContext.length < 4 || classContext[3].getClassLoader() != clazz.getClassLoader()) {
                this.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
            }
        }
    }
    
    public void checkSecurityAccess(final String s) {
        this.checkPermission(new SecurityPermission(s));
    }
    
    private native Class<?> currentLoadedClass0();
    
    public ThreadGroup getThreadGroup() {
        return Thread.currentThread().getThreadGroup();
    }
    
    static {
        SecurityManager.rootGroup = getRootGroup();
        SecurityManager.packageAccessValid = false;
        packageAccessLock = new Object();
        SecurityManager.packageDefinitionValid = false;
        packageDefinitionLock = new Object();
    }
}
