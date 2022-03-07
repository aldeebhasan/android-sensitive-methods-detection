package java.security;

import java.util.*;
import sun.security.util.*;
import java.nio.*;

public class SecureClassLoader extends ClassLoader
{
    private final boolean initialized;
    private final HashMap<CodeSource, ProtectionDomain> pdcache;
    private static final Debug debug;
    
    protected SecureClassLoader(final ClassLoader classLoader) {
        super(classLoader);
        this.pdcache = new HashMap<CodeSource, ProtectionDomain>(11);
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.initialized = true;
    }
    
    protected SecureClassLoader() {
        this.pdcache = new HashMap<CodeSource, ProtectionDomain>(11);
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkCreateClassLoader();
        }
        this.initialized = true;
    }
    
    protected final Class<?> defineClass(final String s, final byte[] array, final int n, final int n2, final CodeSource codeSource) {
        return this.defineClass(s, array, n, n2, this.getProtectionDomain(codeSource));
    }
    
    protected final Class<?> defineClass(final String s, final ByteBuffer byteBuffer, final CodeSource codeSource) {
        return this.defineClass(s, byteBuffer, this.getProtectionDomain(codeSource));
    }
    
    protected PermissionCollection getPermissions(final CodeSource codeSource) {
        this.check();
        return new Permissions();
    }
    
    private ProtectionDomain getProtectionDomain(final CodeSource codeSource) {
        if (codeSource == null) {
            return null;
        }
        ProtectionDomain protectionDomain = null;
        synchronized (this.pdcache) {
            protectionDomain = this.pdcache.get(codeSource);
            if (protectionDomain == null) {
                protectionDomain = new ProtectionDomain(codeSource, this.getPermissions(codeSource), this, null);
                this.pdcache.put(codeSource, protectionDomain);
                if (SecureClassLoader.debug != null) {
                    SecureClassLoader.debug.println(" getPermissions " + protectionDomain);
                    SecureClassLoader.debug.println("");
                }
            }
        }
        return protectionDomain;
    }
    
    private void check() {
        if (!this.initialized) {
            throw new SecurityException("ClassLoader object not initialized");
        }
    }
    
    static {
        debug = Debug.getInstance("scl");
        ClassLoader.registerAsParallelCapable();
    }
}
