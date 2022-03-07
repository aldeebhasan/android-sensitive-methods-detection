package java.lang;

import java.lang.reflect.*;
import sun.reflect.*;
import java.lang.annotation.*;
import sun.net.www.*;
import java.net.*;
import java.security.*;
import java.util.jar.*;
import java.io.*;
import java.util.*;

public class Package implements AnnotatedElement
{
    private static Map<String, Package> pkgs;
    private static Map<String, URL> urls;
    private static Map<String, Manifest> mans;
    private final String pkgName;
    private final String specTitle;
    private final String specVersion;
    private final String specVendor;
    private final String implTitle;
    private final String implVersion;
    private final String implVendor;
    private final URL sealBase;
    private final transient ClassLoader loader;
    private transient Class<?> packageInfo;
    
    public String getName() {
        return this.pkgName;
    }
    
    public String getSpecificationTitle() {
        return this.specTitle;
    }
    
    public String getSpecificationVersion() {
        return this.specVersion;
    }
    
    public String getSpecificationVendor() {
        return this.specVendor;
    }
    
    public String getImplementationTitle() {
        return this.implTitle;
    }
    
    public String getImplementationVersion() {
        return this.implVersion;
    }
    
    public String getImplementationVendor() {
        return this.implVendor;
    }
    
    public boolean isSealed() {
        return this.sealBase != null;
    }
    
    public boolean isSealed(final URL url) {
        return url.equals(this.sealBase);
    }
    
    public boolean isCompatibleWith(final String s) throws NumberFormatException {
        if (this.specVersion == null || this.specVersion.length() < 1) {
            throw new NumberFormatException("Empty version string");
        }
        final String[] split = this.specVersion.split("\\.", -1);
        final int[] array = new int[split.length];
        for (int i = 0; i < split.length; ++i) {
            array[i] = Integer.parseInt(split[i]);
            if (array[i] < 0) {
                throw NumberFormatException.forInputString("" + array[i]);
            }
        }
        final String[] split2 = s.split("\\.", -1);
        final int[] array2 = new int[split2.length];
        for (int j = 0; j < split2.length; ++j) {
            array2[j] = Integer.parseInt(split2[j]);
            if (array2[j] < 0) {
                throw NumberFormatException.forInputString("" + array2[j]);
            }
        }
        for (int max = Math.max(array2.length, array.length), k = 0; k < max; ++k) {
            final int n = (k < array2.length) ? array2[k] : 0;
            final int n2 = (k < array.length) ? array[k] : 0;
            if (n2 < n) {
                return false;
            }
            if (n2 > n) {
                return true;
            }
        }
        return true;
    }
    
    @CallerSensitive
    public static Package getPackage(final String s) {
        final ClassLoader classLoader = ClassLoader.getClassLoader(Reflection.getCallerClass());
        if (classLoader != null) {
            return classLoader.getPackage(s);
        }
        return getSystemPackage(s);
    }
    
    @CallerSensitive
    public static Package[] getPackages() {
        final ClassLoader classLoader = ClassLoader.getClassLoader(Reflection.getCallerClass());
        if (classLoader != null) {
            return classLoader.getPackages();
        }
        return getSystemPackages();
    }
    
    static Package getPackage(final Class<?> clazz) {
        final String name = clazz.getName();
        final int lastIndex = name.lastIndexOf(46);
        if (lastIndex == -1) {
            return null;
        }
        final String substring = name.substring(0, lastIndex);
        final ClassLoader classLoader = clazz.getClassLoader();
        if (classLoader != null) {
            return classLoader.getPackage(substring);
        }
        return getSystemPackage(substring);
    }
    
    @Override
    public int hashCode() {
        return this.pkgName.hashCode();
    }
    
    @Override
    public String toString() {
        final String specTitle = this.specTitle;
        final String specVersion = this.specVersion;
        String string;
        if (specTitle != null && specTitle.length() > 0) {
            string = ", " + specTitle;
        }
        else {
            string = "";
        }
        String string2;
        if (specVersion != null && specVersion.length() > 0) {
            string2 = ", version " + specVersion;
        }
        else {
            string2 = "";
        }
        return "package " + this.pkgName + string + string2;
    }
    
    private Class<?> getPackageInfo() {
        if (this.packageInfo == null) {
            try {
                this.packageInfo = Class.forName(this.pkgName + ".package-info", false, this.loader);
            }
            catch (ClassNotFoundException ex) {
                class PackageInfoProxy
                {
                }
                this.packageInfo = PackageInfoProxy.class;
            }
        }
        return this.packageInfo;
    }
    
    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
        return this.getPackageInfo().getAnnotation(clazz);
    }
    
    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
        return super.isAnnotationPresent(clazz);
    }
    
    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> clazz) {
        return this.getPackageInfo().getAnnotationsByType(clazz);
    }
    
    @Override
    public Annotation[] getAnnotations() {
        return this.getPackageInfo().getAnnotations();
    }
    
    @Override
    public <A extends Annotation> A getDeclaredAnnotation(final Class<A> clazz) {
        return this.getPackageInfo().getDeclaredAnnotation(clazz);
    }
    
    @Override
    public <A extends Annotation> A[] getDeclaredAnnotationsByType(final Class<A> clazz) {
        return this.getPackageInfo().getDeclaredAnnotationsByType(clazz);
    }
    
    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.getPackageInfo().getDeclaredAnnotations();
    }
    
    Package(final String pkgName, final String specTitle, final String specVersion, final String specVendor, final String implTitle, final String implVersion, final String implVendor, final URL sealBase, final ClassLoader loader) {
        this.pkgName = pkgName;
        this.implTitle = implTitle;
        this.implVersion = implVersion;
        this.implVendor = implVendor;
        this.specTitle = specTitle;
        this.specVersion = specVersion;
        this.specVendor = specVendor;
        this.sealBase = sealBase;
        this.loader = loader;
    }
    
    private Package(final String pkgName, final Manifest manifest, final URL url, final ClassLoader loader) {
        final String concat = pkgName.replace('.', '/').concat("/");
        String s = null;
        String specTitle = null;
        String specVersion = null;
        String specVendor = null;
        String implTitle = null;
        String implVersion = null;
        String implVendor = null;
        URL sealBase = null;
        final Attributes attributes = manifest.getAttributes(concat);
        if (attributes != null) {
            specTitle = attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
            specVersion = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
            specVendor = attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
            implTitle = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            implVersion = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            implVendor = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            s = attributes.getValue(Attributes.Name.SEALED);
        }
        final Attributes mainAttributes = manifest.getMainAttributes();
        if (mainAttributes != null) {
            if (specTitle == null) {
                specTitle = mainAttributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
            }
            if (specVersion == null) {
                specVersion = mainAttributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
            }
            if (specVendor == null) {
                specVendor = mainAttributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
            }
            if (implTitle == null) {
                implTitle = mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            }
            if (implVersion == null) {
                implVersion = mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            }
            if (implVendor == null) {
                implVendor = mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            }
            if (s == null) {
                s = mainAttributes.getValue(Attributes.Name.SEALED);
            }
        }
        if ("true".equalsIgnoreCase(s)) {
            sealBase = url;
        }
        this.pkgName = pkgName;
        this.specTitle = specTitle;
        this.specVersion = specVersion;
        this.specVendor = specVendor;
        this.implTitle = implTitle;
        this.implVersion = implVersion;
        this.implVendor = implVendor;
        this.sealBase = sealBase;
        this.loader = loader;
    }
    
    static Package getSystemPackage(String concat) {
        synchronized (Package.pkgs) {
            Package defineSystemPackage = Package.pkgs.get(concat);
            if (defineSystemPackage == null) {
                concat = concat.replace('.', '/').concat("/");
                final String systemPackage0 = getSystemPackage0(concat);
                if (systemPackage0 != null) {
                    defineSystemPackage = defineSystemPackage(concat, systemPackage0);
                }
            }
            return defineSystemPackage;
        }
    }
    
    static Package[] getSystemPackages() {
        final String[] systemPackages0 = getSystemPackages0();
        synchronized (Package.pkgs) {
            for (int i = 0; i < systemPackages0.length; ++i) {
                defineSystemPackage(systemPackages0[i], getSystemPackage0(systemPackages0[i]));
            }
            return Package.pkgs.values().toArray(new Package[Package.pkgs.size()]);
        }
    }
    
    private static Package defineSystemPackage(final String s, final String s2) {
        return AccessController.doPrivileged((PrivilegedAction<Package>)new PrivilegedAction<Package>() {
            @Override
            public Package run() {
                final String val$iname = s;
                URL fileToEncodedURL = Package.urls.get(s2);
                if (fileToEncodedURL == null) {
                    final File file = new File(s2);
                    try {
                        fileToEncodedURL = ParseUtil.fileToEncodedURL(file);
                    }
                    catch (MalformedURLException ex) {}
                    if (fileToEncodedURL != null) {
                        Package.urls.put(s2, fileToEncodedURL);
                        if (file.isFile()) {
                            Package.mans.put(s2, loadManifest(s2));
                        }
                    }
                }
                final String replace = val$iname.substring(0, val$iname.length() - 1).replace('/', '.');
                final Manifest manifest = Package.mans.get(s2);
                Package package1;
                if (manifest != null) {
                    package1 = new Package(replace, manifest, fileToEncodedURL, null, null);
                }
                else {
                    package1 = new Package(replace, null, null, null, null, null, null, null, null);
                }
                Package.pkgs.put(replace, package1);
                return package1;
            }
        });
    }
    
    private static Manifest loadManifest(final String s) {
        try (final FileInputStream fileInputStream = new FileInputStream(s);
             final JarInputStream jarInputStream = new JarInputStream(fileInputStream, false)) {
            return jarInputStream.getManifest();
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    private static native String getSystemPackage0(final String p0);
    
    private static native String[] getSystemPackages0();
    
    static {
        Package.pkgs = new HashMap<String, Package>(31);
        Package.urls = new HashMap<String, URL>(10);
        Package.mans = new HashMap<String, Manifest>(10);
    }
}
