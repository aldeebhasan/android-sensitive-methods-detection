package java.util.prefs;

import java.security.*;
import java.util.*;
import java.io.*;

public abstract class Preferences
{
    private static final PreferencesFactory factory;
    public static final int MAX_KEY_LENGTH = 80;
    public static final int MAX_VALUE_LENGTH = 8192;
    public static final int MAX_NAME_LENGTH = 80;
    private static Permission prefsPerm;
    
    private static PreferencesFactory factory() {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getProperty("java.util.prefs.PreferencesFactory");
            }
        });
        if (s != null) {
            try {
                return (PreferencesFactory)Class.forName(s, false, ClassLoader.getSystemClassLoader()).newInstance();
            }
            catch (Exception ex2) {
                try {
                    final SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkPermission(new AllPermission());
                    }
                    return (PreferencesFactory)Class.forName(s, false, Thread.currentThread().getContextClassLoader()).newInstance();
                }
                catch (Exception ex) {
                    throw new InternalError("Can't instantiate Preferences factory " + s, ex);
                }
            }
        }
        return AccessController.doPrivileged((PrivilegedAction<PreferencesFactory>)new PrivilegedAction<PreferencesFactory>() {
            @Override
            public PreferencesFactory run() {
                return factory1();
            }
        });
    }
    
    private static PreferencesFactory factory1() {
        final Iterator<PreferencesFactory> iterator = ServiceLoader.load(PreferencesFactory.class, ClassLoader.getSystemClassLoader()).iterator();
        while (iterator.hasNext()) {
            try {
                return iterator.next();
            }
            catch (ServiceConfigurationError serviceConfigurationError) {
                if (serviceConfigurationError.getCause() instanceof SecurityException) {
                    continue;
                }
                throw serviceConfigurationError;
            }
            break;
        }
        final String property = System.getProperty("os.name");
        String s;
        if (property.startsWith("Windows")) {
            s = "java.util.prefs.WindowsPreferencesFactory";
        }
        else if (property.contains("OS X")) {
            s = "java.util.prefs.MacOSXPreferencesFactory";
        }
        else {
            s = "java.util.prefs.FileSystemPreferencesFactory";
        }
        try {
            return (PreferencesFactory)Class.forName(s, false, Preferences.class.getClassLoader()).newInstance();
        }
        catch (Exception ex) {
            throw new InternalError("Can't instantiate platform default Preferences factory " + s, ex);
        }
    }
    
    public static Preferences userNodeForPackage(final Class<?> clazz) {
        return userRoot().node(nodeName(clazz));
    }
    
    public static Preferences systemNodeForPackage(final Class<?> clazz) {
        return systemRoot().node(nodeName(clazz));
    }
    
    private static String nodeName(final Class<?> clazz) {
        if (clazz.isArray()) {
            throw new IllegalArgumentException("Arrays have no associated preferences node.");
        }
        final String name = clazz.getName();
        final int lastIndex = name.lastIndexOf(46);
        if (lastIndex < 0) {
            return "/<unnamed>";
        }
        return "/" + name.substring(0, lastIndex).replace('.', '/');
    }
    
    public static Preferences userRoot() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(Preferences.prefsPerm);
        }
        return Preferences.factory.userRoot();
    }
    
    public static Preferences systemRoot() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(Preferences.prefsPerm);
        }
        return Preferences.factory.systemRoot();
    }
    
    public abstract void put(final String p0, final String p1);
    
    public abstract String get(final String p0, final String p1);
    
    public abstract void remove(final String p0);
    
    public abstract void clear() throws BackingStoreException;
    
    public abstract void putInt(final String p0, final int p1);
    
    public abstract int getInt(final String p0, final int p1);
    
    public abstract void putLong(final String p0, final long p1);
    
    public abstract long getLong(final String p0, final long p1);
    
    public abstract void putBoolean(final String p0, final boolean p1);
    
    public abstract boolean getBoolean(final String p0, final boolean p1);
    
    public abstract void putFloat(final String p0, final float p1);
    
    public abstract float getFloat(final String p0, final float p1);
    
    public abstract void putDouble(final String p0, final double p1);
    
    public abstract double getDouble(final String p0, final double p1);
    
    public abstract void putByteArray(final String p0, final byte[] p1);
    
    public abstract byte[] getByteArray(final String p0, final byte[] p1);
    
    public abstract String[] keys() throws BackingStoreException;
    
    public abstract String[] childrenNames() throws BackingStoreException;
    
    public abstract Preferences parent();
    
    public abstract Preferences node(final String p0);
    
    public abstract boolean nodeExists(final String p0) throws BackingStoreException;
    
    public abstract void removeNode() throws BackingStoreException;
    
    public abstract String name();
    
    public abstract String absolutePath();
    
    public abstract boolean isUserNode();
    
    @Override
    public abstract String toString();
    
    public abstract void flush() throws BackingStoreException;
    
    public abstract void sync() throws BackingStoreException;
    
    public abstract void addPreferenceChangeListener(final PreferenceChangeListener p0);
    
    public abstract void removePreferenceChangeListener(final PreferenceChangeListener p0);
    
    public abstract void addNodeChangeListener(final NodeChangeListener p0);
    
    public abstract void removeNodeChangeListener(final NodeChangeListener p0);
    
    public abstract void exportNode(final OutputStream p0) throws IOException, BackingStoreException;
    
    public abstract void exportSubtree(final OutputStream p0) throws IOException, BackingStoreException;
    
    public static void importPreferences(final InputStream inputStream) throws IOException, InvalidPreferencesFormatException {
        XmlSupport.importPreferences(inputStream);
    }
    
    static {
        factory = factory();
        Preferences.prefsPerm = new RuntimePermission("preferences");
    }
}
