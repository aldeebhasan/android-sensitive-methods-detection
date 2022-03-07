package java.util;

import java.util.spi.*;
import sun.reflect.*;
import java.util.concurrent.*;
import java.lang.ref.*;
import java.io.*;
import java.net.*;
import java.util.jar.*;
import sun.util.locale.*;
import java.security.*;

public abstract class ResourceBundle
{
    private static final int INITIAL_CACHE_SIZE = 32;
    private static final ResourceBundle NONEXISTENT_BUNDLE;
    private static final ConcurrentMap<CacheKey, BundleReference> cacheList;
    private static final ReferenceQueue<Object> referenceQueue;
    protected ResourceBundle parent;
    private Locale locale;
    private String name;
    private volatile boolean expired;
    private volatile CacheKey cacheKey;
    private volatile Set<String> keySet;
    private static final List<ResourceBundleControlProvider> providers;
    
    public String getBaseBundleName() {
        return this.name;
    }
    
    public ResourceBundle() {
        this.parent = null;
        this.locale = null;
    }
    
    public final String getString(final String s) {
        return (String)this.getObject(s);
    }
    
    public final String[] getStringArray(final String s) {
        return (String[])this.getObject(s);
    }
    
    public final Object getObject(final String s) {
        Object o = this.handleGetObject(s);
        if (o == null) {
            if (this.parent != null) {
                o = this.parent.getObject(s);
            }
            if (o == null) {
                throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + s, this.getClass().getName(), s);
            }
        }
        return o;
    }
    
    public Locale getLocale() {
        return this.locale;
    }
    
    private static ClassLoader getLoader(final Class<?> clazz) {
        ClassLoader access$000 = (clazz == null) ? null : clazz.getClassLoader();
        if (access$000 == null) {
            access$000 = RBClassLoader.INSTANCE;
        }
        return access$000;
    }
    
    protected void setParent(final ResourceBundle parent) {
        assert parent != ResourceBundle.NONEXISTENT_BUNDLE;
        this.parent = parent;
    }
    
    @CallerSensitive
    public static final ResourceBundle getBundle(final String s) {
        return getBundleImpl(s, Locale.getDefault(), getLoader(Reflection.getCallerClass()), getDefaultControl(s));
    }
    
    @CallerSensitive
    public static final ResourceBundle getBundle(final String s, final Control control) {
        return getBundleImpl(s, Locale.getDefault(), getLoader(Reflection.getCallerClass()), control);
    }
    
    @CallerSensitive
    public static final ResourceBundle getBundle(final String s, final Locale locale) {
        return getBundleImpl(s, locale, getLoader(Reflection.getCallerClass()), getDefaultControl(s));
    }
    
    @CallerSensitive
    public static final ResourceBundle getBundle(final String s, final Locale locale, final Control control) {
        return getBundleImpl(s, locale, getLoader(Reflection.getCallerClass()), control);
    }
    
    public static ResourceBundle getBundle(final String s, final Locale locale, final ClassLoader classLoader) {
        if (classLoader == null) {
            throw new NullPointerException();
        }
        return getBundleImpl(s, locale, classLoader, getDefaultControl(s));
    }
    
    public static ResourceBundle getBundle(final String s, final Locale locale, final ClassLoader classLoader, final Control control) {
        if (classLoader == null || control == null) {
            throw new NullPointerException();
        }
        return getBundleImpl(s, locale, classLoader, control);
    }
    
    private static Control getDefaultControl(final String s) {
        if (ResourceBundle.providers != null) {
            final Iterator<ResourceBundleControlProvider> iterator = ResourceBundle.providers.iterator();
            while (iterator.hasNext()) {
                final Control control = iterator.next().getControl(s);
                if (control != null) {
                    return control;
                }
            }
        }
        return Control.INSTANCE;
    }
    
    private static ResourceBundle getBundleImpl(final String s, final Locale locale, final ClassLoader classLoader, final Control control) {
        if (locale == null || control == null) {
            throw new NullPointerException();
        }
        final CacheKey cacheKey = new CacheKey(s, locale, classLoader);
        ResourceBundle bundle = null;
        final BundleReference bundleReference = ResourceBundle.cacheList.get(cacheKey);
        if (bundleReference != null) {
            bundle = bundleReference.get();
        }
        if (isValidBundle(bundle) && hasValidParentChain(bundle)) {
            return bundle;
        }
        final boolean b = control == Control.INSTANCE || control instanceof SingleFormatControl;
        final List<String> formats = control.getFormats(s);
        if (!b && !checkList(formats)) {
            throw new IllegalArgumentException("Invalid Control: getFormats");
        }
        ResourceBundle resourceBundle = null;
        for (Locale fallbackLocale = locale; fallbackLocale != null; fallbackLocale = control.getFallbackLocale(s, fallbackLocale)) {
            final List<Locale> candidateLocales = control.getCandidateLocales(s, fallbackLocale);
            if (!b && !checkList(candidateLocales)) {
                throw new IllegalArgumentException("Invalid Control: getCandidateLocales");
            }
            bundle = findBundle(cacheKey, candidateLocales, formats, 0, control, resourceBundle);
            if (isValidBundle(bundle)) {
                final boolean equals = Locale.ROOT.equals(bundle.locale);
                if (!equals || bundle.locale.equals(locale)) {
                    break;
                }
                if (candidateLocales.size() == 1 && bundle.locale.equals(candidateLocales.get(0))) {
                    break;
                }
                if (equals && resourceBundle == null) {
                    resourceBundle = bundle;
                }
            }
        }
        if (bundle == null) {
            if (resourceBundle == null) {
                throwMissingResourceException(s, locale, cacheKey.getCause());
            }
            bundle = resourceBundle;
        }
        keepAlive(classLoader);
        return bundle;
    }
    
    private static void keepAlive(final ClassLoader classLoader) {
    }
    
    private static boolean checkList(final List<?> list) {
        boolean b = list != null && !list.isEmpty();
        if (b) {
            for (int size = list.size(), n = 0; b && n < size; b = (list.get(n) != null), ++n) {}
        }
        return b;
    }
    
    private static ResourceBundle findBundle(final CacheKey cacheKey, final List<Locale> list, final List<String> list2, final int n, final Control control, final ResourceBundle resourceBundle) {
        final Locale locale = list.get(n);
        ResourceBundle bundle = null;
        if (n != list.size() - 1) {
            bundle = findBundle(cacheKey, list, list2, n + 1, control, resourceBundle);
        }
        else if (resourceBundle != null && Locale.ROOT.equals(locale)) {
            return resourceBundle;
        }
        Reference<?> poll;
        while ((poll = ResourceBundle.referenceQueue.poll()) != null) {
            ResourceBundle.cacheList.remove(((CacheKeyReference)poll).getCacheKey());
        }
        boolean expired = false;
        cacheKey.setLocale(locale);
        final ResourceBundle bundleInCache = findBundleInCache(cacheKey, control);
        if (isValidBundle(bundleInCache)) {
            expired = bundleInCache.expired;
            if (!expired) {
                if (bundleInCache.parent == bundle) {
                    return bundleInCache;
                }
                final BundleReference bundleReference = ResourceBundle.cacheList.get(cacheKey);
                if (bundleReference != null && bundleReference.get() == bundleInCache) {
                    ResourceBundle.cacheList.remove(cacheKey, bundleReference);
                }
            }
        }
        if (bundleInCache != ResourceBundle.NONEXISTENT_BUNDLE) {
            final CacheKey cacheKey2 = (CacheKey)cacheKey.clone();
            try {
                final ResourceBundle loadBundle = loadBundle(cacheKey, list2, control, expired);
                if (loadBundle != null) {
                    if (loadBundle.parent == null) {
                        loadBundle.setParent(bundle);
                    }
                    loadBundle.locale = locale;
                    return putBundleInCache(cacheKey, loadBundle, control);
                }
                putBundleInCache(cacheKey, ResourceBundle.NONEXISTENT_BUNDLE, control);
            }
            finally {
                if (cacheKey2.getCause() instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return bundle;
    }
    
    private static ResourceBundle loadBundle(final CacheKey cacheKey, final List<String> list, final Control control, final boolean b) {
        final Locale locale = cacheKey.getLocale();
        ResourceBundle bundle = null;
        for (int size = list.size(), i = 0; i < size; ++i) {
            final String format = list.get(i);
            try {
                bundle = control.newBundle(cacheKey.getName(), locale, format, cacheKey.getLoader(), b);
            }
            catch (LinkageError linkageError) {
                cacheKey.setCause(linkageError);
            }
            catch (Exception ex) {
                cacheKey.setCause(ex);
            }
            if (bundle != null) {
                cacheKey.setFormat(format);
                bundle.name = cacheKey.getName();
                bundle.locale = locale;
                bundle.expired = false;
                break;
            }
        }
        return bundle;
    }
    
    private static boolean isValidBundle(final ResourceBundle resourceBundle) {
        return resourceBundle != null && resourceBundle != ResourceBundle.NONEXISTENT_BUNDLE;
    }
    
    private static boolean hasValidParentChain(ResourceBundle parent) {
        final long currentTimeMillis = System.currentTimeMillis();
        while (parent != null) {
            if (parent.expired) {
                return false;
            }
            final CacheKey cacheKey = parent.cacheKey;
            if (cacheKey != null) {
                final long access$600 = cacheKey.expirationTime;
                if (access$600 >= 0L && access$600 <= currentTimeMillis) {
                    return false;
                }
            }
            parent = parent.parent;
        }
        return true;
    }
    
    private static void throwMissingResourceException(final String s, final Locale locale, Throwable t) {
        if (t instanceof MissingResourceException) {
            t = null;
        }
        throw new MissingResourceException("Can't find bundle for base name " + s + ", locale " + locale, s + "_" + locale, "", t);
    }
    
    private static ResourceBundle findBundleInCache(final CacheKey cacheKey, final Control control) {
        final BundleReference bundleReference = ResourceBundle.cacheList.get(cacheKey);
        if (bundleReference == null) {
            return null;
        }
        ResourceBundle resourceBundle = bundleReference.get();
        if (resourceBundle == null) {
            return null;
        }
        final ResourceBundle parent = resourceBundle.parent;
        assert parent != ResourceBundle.NONEXISTENT_BUNDLE;
        if (parent != null && parent.expired) {
            assert resourceBundle != ResourceBundle.NONEXISTENT_BUNDLE;
            resourceBundle.expired = true;
            resourceBundle.cacheKey = null;
            ResourceBundle.cacheList.remove(cacheKey, bundleReference);
            resourceBundle = null;
        }
        else {
            final CacheKey cacheKey2 = bundleReference.getCacheKey();
            final long access$600 = cacheKey2.expirationTime;
            if (!resourceBundle.expired && access$600 >= 0L && access$600 <= System.currentTimeMillis()) {
                if (resourceBundle != ResourceBundle.NONEXISTENT_BUNDLE) {
                    synchronized (resourceBundle) {
                        final long access$601 = cacheKey2.expirationTime;
                        if (!resourceBundle.expired && access$601 >= 0L && access$601 <= System.currentTimeMillis()) {
                            try {
                                resourceBundle.expired = control.needsReload(cacheKey2.getName(), cacheKey2.getLocale(), cacheKey2.getFormat(), cacheKey2.getLoader(), resourceBundle, cacheKey2.loadTime);
                            }
                            catch (Exception ex) {
                                cacheKey.setCause(ex);
                            }
                            if (resourceBundle.expired) {
                                resourceBundle.cacheKey = null;
                                ResourceBundle.cacheList.remove(cacheKey, bundleReference);
                            }
                            else {
                                setExpirationTime(cacheKey2, control);
                            }
                        }
                    }
                }
                else {
                    ResourceBundle.cacheList.remove(cacheKey, bundleReference);
                    resourceBundle = null;
                }
            }
        }
        return resourceBundle;
    }
    
    private static ResourceBundle putBundleInCache(final CacheKey cacheKey, ResourceBundle resourceBundle, final Control control) {
        setExpirationTime(cacheKey, control);
        if (cacheKey.expirationTime != -1L) {
            final CacheKey cacheKey2 = (CacheKey)cacheKey.clone();
            final BundleReference bundleReference = new BundleReference(resourceBundle, ResourceBundle.referenceQueue, cacheKey2);
            resourceBundle.cacheKey = cacheKey2;
            final BundleReference bundleReference2 = ResourceBundle.cacheList.putIfAbsent(cacheKey2, bundleReference);
            if (bundleReference2 != null) {
                final ResourceBundle resourceBundle2 = bundleReference2.get();
                if (resourceBundle2 != null && !resourceBundle2.expired) {
                    resourceBundle.cacheKey = null;
                    resourceBundle = resourceBundle2;
                    bundleReference.clear();
                }
                else {
                    ResourceBundle.cacheList.put(cacheKey2, bundleReference);
                }
            }
        }
        return resourceBundle;
    }
    
    private static void setExpirationTime(final CacheKey cacheKey, final Control control) {
        final long timeToLive = control.getTimeToLive(cacheKey.getName(), cacheKey.getLocale());
        if (timeToLive >= 0L) {
            final long currentTimeMillis = System.currentTimeMillis();
            cacheKey.loadTime = currentTimeMillis;
            cacheKey.expirationTime = currentTimeMillis + timeToLive;
        }
        else {
            if (timeToLive < -2L) {
                throw new IllegalArgumentException("Invalid Control: TTL=" + timeToLive);
            }
            cacheKey.expirationTime = timeToLive;
        }
    }
    
    @CallerSensitive
    public static final void clearCache() {
        clearCache(getLoader(Reflection.getCallerClass()));
    }
    
    public static final void clearCache(final ClassLoader classLoader) {
        if (classLoader == null) {
            throw new NullPointerException();
        }
        final Set<CacheKey> keySet = (Set<CacheKey>)ResourceBundle.cacheList.keySet();
        for (final CacheKey cacheKey : keySet) {
            if (cacheKey.getLoader() == classLoader) {
                keySet.remove(cacheKey);
            }
        }
    }
    
    protected abstract Object handleGetObject(final String p0);
    
    public abstract Enumeration<String> getKeys();
    
    public boolean containsKey(final String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        for (ResourceBundle parent = this; parent != null; parent = parent.parent) {
            if (parent.handleKeySet().contains(s)) {
                return true;
            }
        }
        return false;
    }
    
    public Set<String> keySet() {
        final HashSet<Object> set = (HashSet<Object>)new HashSet<String>();
        for (ResourceBundle parent = this; parent != null; parent = parent.parent) {
            set.addAll(parent.handleKeySet());
        }
        return (Set<String>)set;
    }
    
    protected Set<String> handleKeySet() {
        if (this.keySet == null) {
            synchronized (this) {
                if (this.keySet == null) {
                    final HashSet<String> keySet = new HashSet<String>();
                    final Enumeration<String> keys = this.getKeys();
                    while (keys.hasMoreElements()) {
                        final String s = keys.nextElement();
                        if (this.handleGetObject(s) != null) {
                            keySet.add(s);
                        }
                    }
                    this.keySet = keySet;
                }
            }
        }
        return this.keySet;
    }
    
    static {
        NONEXISTENT_BUNDLE = new ResourceBundle() {
            @Override
            public Enumeration<String> getKeys() {
                return null;
            }
            
            @Override
            protected Object handleGetObject(final String s) {
                return null;
            }
            
            @Override
            public String toString() {
                return "NONEXISTENT_BUNDLE";
            }
        };
        cacheList = new ConcurrentHashMap<CacheKey, BundleReference>(32);
        referenceQueue = new ReferenceQueue<Object>();
        List<ResourceBundleControlProvider> providers2 = null;
        for (final ResourceBundleControlProvider resourceBundleControlProvider : ServiceLoader.loadInstalled(ResourceBundleControlProvider.class)) {
            if (providers2 == null) {
                providers2 = new ArrayList<ResourceBundleControlProvider>();
            }
            providers2.add(resourceBundleControlProvider);
        }
        providers = providers2;
    }
    
    private static class BundleReference extends SoftReference<ResourceBundle> implements CacheKeyReference
    {
        private CacheKey cacheKey;
        
        BundleReference(final ResourceBundle resourceBundle, final ReferenceQueue<Object> referenceQueue, final CacheKey cacheKey) {
            super(resourceBundle, referenceQueue);
            this.cacheKey = cacheKey;
        }
        
        @Override
        public CacheKey getCacheKey() {
            return this.cacheKey;
        }
    }
    
    private interface CacheKeyReference
    {
        CacheKey getCacheKey();
    }
    
    private static class CacheKey implements Cloneable
    {
        private String name;
        private Locale locale;
        private LoaderReference loaderRef;
        private String format;
        private volatile long loadTime;
        private volatile long expirationTime;
        private Throwable cause;
        private int hashCodeCache;
        
        CacheKey(final String name, final Locale locale, final ClassLoader classLoader) {
            this.name = name;
            this.locale = locale;
            if (classLoader == null) {
                this.loaderRef = null;
            }
            else {
                this.loaderRef = new LoaderReference(classLoader, ResourceBundle.referenceQueue, this);
            }
            this.calculateHashCode();
        }
        
        String getName() {
            return this.name;
        }
        
        CacheKey setName(final String name) {
            if (!this.name.equals(name)) {
                this.name = name;
                this.calculateHashCode();
            }
            return this;
        }
        
        Locale getLocale() {
            return this.locale;
        }
        
        CacheKey setLocale(final Locale locale) {
            if (!this.locale.equals(locale)) {
                this.locale = locale;
                this.calculateHashCode();
            }
            return this;
        }
        
        ClassLoader getLoader() {
            return (this.loaderRef != null) ? this.loaderRef.get() : null;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            try {
                final CacheKey cacheKey = (CacheKey)o;
                if (this.hashCodeCache != cacheKey.hashCodeCache) {
                    return false;
                }
                if (!this.name.equals(cacheKey.name)) {
                    return false;
                }
                if (!this.locale.equals(cacheKey.locale)) {
                    return false;
                }
                if (this.loaderRef == null) {
                    return cacheKey.loaderRef == null;
                }
                final ClassLoader classLoader = this.loaderRef.get();
                return cacheKey.loaderRef != null && classLoader != null && classLoader == cacheKey.loaderRef.get();
            }
            catch (NullPointerException | ClassCastException ex) {
                return false;
            }
        }
        
        @Override
        public int hashCode() {
            return this.hashCodeCache;
        }
        
        private void calculateHashCode() {
            this.hashCodeCache = this.name.hashCode() << 3;
            this.hashCodeCache ^= this.locale.hashCode();
            final ClassLoader loader = this.getLoader();
            if (loader != null) {
                this.hashCodeCache ^= loader.hashCode();
            }
        }
        
        public Object clone() {
            try {
                final CacheKey cacheKey = (CacheKey)super.clone();
                if (this.loaderRef != null) {
                    cacheKey.loaderRef = new LoaderReference(this.loaderRef.get(), ResourceBundle.referenceQueue, cacheKey);
                }
                cacheKey.cause = null;
                return cacheKey;
            }
            catch (CloneNotSupportedException ex) {
                throw new InternalError(ex);
            }
        }
        
        String getFormat() {
            return this.format;
        }
        
        void setFormat(final String format) {
            this.format = format;
        }
        
        private void setCause(final Throwable t) {
            if (this.cause == null) {
                this.cause = t;
            }
            else if (this.cause instanceof ClassNotFoundException) {
                this.cause = t;
            }
        }
        
        private Throwable getCause() {
            return this.cause;
        }
        
        @Override
        public String toString() {
            String s = this.locale.toString();
            if (s.length() == 0) {
                if (this.locale.getVariant().length() != 0) {
                    s = "__" + this.locale.getVariant();
                }
                else {
                    s = "\"\"";
                }
            }
            return "CacheKey[" + this.name + ", lc=" + s + ", ldr=" + this.getLoader() + "(format=" + this.format + ")]";
        }
    }
    
    private static class LoaderReference extends WeakReference<ClassLoader> implements CacheKeyReference
    {
        private CacheKey cacheKey;
        
        LoaderReference(final ClassLoader classLoader, final ReferenceQueue<Object> referenceQueue, final CacheKey cacheKey) {
            super(classLoader, referenceQueue);
            this.cacheKey = cacheKey;
        }
        
        @Override
        public CacheKey getCacheKey() {
            return this.cacheKey;
        }
    }
    
    public static class Control
    {
        public static final List<String> FORMAT_DEFAULT;
        public static final List<String> FORMAT_CLASS;
        public static final List<String> FORMAT_PROPERTIES;
        public static final long TTL_DONT_CACHE = -1L;
        public static final long TTL_NO_EXPIRATION_CONTROL = -2L;
        private static final Control INSTANCE;
        private static final CandidateListCache CANDIDATES_CACHE;
        
        public static final Control getControl(final List<String> list) {
            if (list.equals(Control.FORMAT_PROPERTIES)) {
                return SingleFormatControl.PROPERTIES_ONLY;
            }
            if (list.equals(Control.FORMAT_CLASS)) {
                return SingleFormatControl.CLASS_ONLY;
            }
            if (list.equals(Control.FORMAT_DEFAULT)) {
                return Control.INSTANCE;
            }
            throw new IllegalArgumentException();
        }
        
        public static final Control getNoFallbackControl(final List<String> list) {
            if (list.equals(Control.FORMAT_DEFAULT)) {
                return NoFallbackControl.NO_FALLBACK;
            }
            if (list.equals(Control.FORMAT_PROPERTIES)) {
                return NoFallbackControl.PROPERTIES_ONLY_NO_FALLBACK;
            }
            if (list.equals(Control.FORMAT_CLASS)) {
                return NoFallbackControl.CLASS_ONLY_NO_FALLBACK;
            }
            throw new IllegalArgumentException();
        }
        
        public List<String> getFormats(final String s) {
            if (s == null) {
                throw new NullPointerException();
            }
            return Control.FORMAT_DEFAULT;
        }
        
        public List<Locale> getCandidateLocales(final String s, final Locale locale) {
            if (s == null) {
                throw new NullPointerException();
            }
            return new ArrayList<Locale>(((LocaleObjectCache<BaseLocale, Collection<? extends Locale>>)Control.CANDIDATES_CACHE).get(locale.getBaseLocale()));
        }
        
        public Locale getFallbackLocale(final String s, final Locale locale) {
            if (s == null) {
                throw new NullPointerException();
            }
            final Locale default1 = Locale.getDefault();
            return locale.equals(default1) ? null : default1;
        }
        
        public ResourceBundle newBundle(final String s, final Locale locale, final String s2, final ClassLoader classLoader, final boolean b) throws IllegalAccessException, InstantiationException, IOException {
            final String bundleName = this.toBundleName(s, locale);
            ResourceBundle resourceBundle = null;
            if (s2.equals("java.class")) {
                try {
                    final Class<?> loadClass = classLoader.loadClass(bundleName);
                    if (!ResourceBundle.class.isAssignableFrom(loadClass)) {
                        throw new ClassCastException(loadClass.getName() + " cannot be cast to ResourceBundle");
                    }
                    resourceBundle = (ResourceBundle)loadClass.newInstance();
                }
                catch (ClassNotFoundException ex2) {}
            }
            else {
                if (!s2.equals("java.properties")) {
                    throw new IllegalArgumentException("unknown format: " + s2);
                }
                final String resourceName0 = this.toResourceName0(bundleName, "properties");
                if (resourceName0 == null) {
                    return resourceBundle;
                }
                InputStream inputStream;
                try {
                    inputStream = AccessController.doPrivileged((PrivilegedExceptionAction<InputStream>)new PrivilegedExceptionAction<InputStream>() {
                        @Override
                        public InputStream run() throws IOException {
                            InputStream inputStream = null;
                            if (b) {
                                final URL resource = classLoader.getResource(resourceName0);
                                if (resource != null) {
                                    final URLConnection openConnection = resource.openConnection();
                                    if (openConnection != null) {
                                        openConnection.setUseCaches(false);
                                        inputStream = openConnection.getInputStream();
                                    }
                                }
                            }
                            else {
                                inputStream = classLoader.getResourceAsStream(resourceName0);
                            }
                            return inputStream;
                        }
                    });
                }
                catch (PrivilegedActionException ex) {
                    throw (IOException)ex.getException();
                }
                if (inputStream != null) {
                    try {
                        resourceBundle = new PropertyResourceBundle(inputStream);
                    }
                    finally {
                        inputStream.close();
                    }
                }
            }
            return resourceBundle;
        }
        
        public long getTimeToLive(final String s, final Locale locale) {
            if (s == null || locale == null) {
                throw new NullPointerException();
            }
            return -2L;
        }
        
        public boolean needsReload(final String s, final Locale locale, String substring, final ClassLoader classLoader, final ResourceBundle resourceBundle, final long n) {
            if (resourceBundle == null) {
                throw new NullPointerException();
            }
            if (substring.equals("java.class") || substring.equals("java.properties")) {
                substring = substring.substring(5);
            }
            boolean b = false;
            try {
                final String resourceName0 = this.toResourceName0(this.toBundleName(s, locale), substring);
                if (resourceName0 == null) {
                    return b;
                }
                final URL resource = classLoader.getResource(resourceName0);
                if (resource != null) {
                    long n2 = 0L;
                    final URLConnection openConnection = resource.openConnection();
                    if (openConnection != null) {
                        openConnection.setUseCaches(false);
                        if (openConnection instanceof JarURLConnection) {
                            final JarEntry jarEntry = ((JarURLConnection)openConnection).getJarEntry();
                            if (jarEntry != null) {
                                n2 = jarEntry.getTime();
                                if (n2 == -1L) {
                                    n2 = 0L;
                                }
                            }
                        }
                        else {
                            n2 = openConnection.getLastModified();
                        }
                    }
                    b = (n2 >= n);
                }
            }
            catch (NullPointerException ex) {
                throw ex;
            }
            catch (Exception ex2) {}
            return b;
        }
        
        public String toBundleName(final String s, final Locale locale) {
            if (locale == Locale.ROOT) {
                return s;
            }
            final String language = locale.getLanguage();
            final String script = locale.getScript();
            final String country = locale.getCountry();
            final String variant = locale.getVariant();
            if (language == "" && country == "" && variant == "") {
                return s;
            }
            final StringBuilder sb = new StringBuilder(s);
            sb.append('_');
            if (script != "") {
                if (variant != "") {
                    sb.append(language).append('_').append(script).append('_').append(country).append('_').append(variant);
                }
                else if (country != "") {
                    sb.append(language).append('_').append(script).append('_').append(country);
                }
                else {
                    sb.append(language).append('_').append(script);
                }
            }
            else if (variant != "") {
                sb.append(language).append('_').append(country).append('_').append(variant);
            }
            else if (country != "") {
                sb.append(language).append('_').append(country);
            }
            else {
                sb.append(language);
            }
            return sb.toString();
        }
        
        public final String toResourceName(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder(s.length() + 1 + s2.length());
            sb.append(s.replace('.', '/')).append('.').append(s2);
            return sb.toString();
        }
        
        private String toResourceName0(final String s, final String s2) {
            if (s.contains("://")) {
                return null;
            }
            return this.toResourceName(s, s2);
        }
        
        static {
            FORMAT_DEFAULT = Collections.unmodifiableList((List<? extends String>)Arrays.asList("java.class", "java.properties"));
            FORMAT_CLASS = Collections.unmodifiableList((List<? extends String>)Arrays.asList("java.class"));
            FORMAT_PROPERTIES = Collections.unmodifiableList((List<? extends String>)Arrays.asList("java.properties"));
            INSTANCE = new Control();
            CANDIDATES_CACHE = new CandidateListCache();
        }
        
        private static class CandidateListCache extends LocaleObjectCache<BaseLocale, List<Locale>>
        {
            @Override
            protected List<Locale> createObject(final BaseLocale baseLocale) {
                final String language = baseLocale.getLanguage();
                String script = baseLocale.getScript();
                String region = baseLocale.getRegion();
                String variant = baseLocale.getVariant();
                boolean b = false;
                boolean b2 = false;
                if (language.equals("no")) {
                    if (region.equals("NO") && variant.equals("NY")) {
                        variant = "";
                        b2 = true;
                    }
                    else {
                        b = true;
                    }
                }
                if (language.equals("nb") || b) {
                    final List<Locale> defaultList = getDefaultList("nb", script, region, variant);
                    final LinkedList<Locale> list = new LinkedList<Locale>();
                    for (final Locale locale : defaultList) {
                        list.add(locale);
                        if (locale.getLanguage().length() == 0) {
                            break;
                        }
                        list.add(Locale.getInstance("no", locale.getScript(), locale.getCountry(), locale.getVariant(), null));
                    }
                    return list;
                }
                if (language.equals("nn") || b2) {
                    final List<Locale> defaultList2 = getDefaultList("nn", script, region, variant);
                    int n = defaultList2.size() - 1;
                    defaultList2.add(n++, Locale.getInstance("no", "NO", "NY"));
                    defaultList2.add(n++, Locale.getInstance("no", "NO", ""));
                    defaultList2.add(n++, Locale.getInstance("no", "", ""));
                    return defaultList2;
                }
                if (language.equals("zh")) {
                    if (script.length() == 0 && region.length() > 0) {
                        final String s = region;
                        switch (s) {
                            case "TW":
                            case "HK":
                            case "MO": {
                                script = "Hant";
                                break;
                            }
                            case "CN":
                            case "SG": {
                                script = "Hans";
                                break;
                            }
                        }
                    }
                    else if (script.length() > 0 && region.length() == 0) {
                        final String s2 = script;
                        switch (s2) {
                            case "Hans": {
                                region = "CN";
                                break;
                            }
                            case "Hant": {
                                region = "TW";
                                break;
                            }
                        }
                    }
                }
                return getDefaultList(language, script, region, variant);
            }
            
            private static List<Locale> getDefaultList(final String s, final String s2, final String s3, final String s4) {
                List<String> list = null;
                if (s4.length() > 0) {
                    list = new LinkedList<String>();
                    for (int i = s4.length(); i != -1; i = s4.lastIndexOf(95, --i)) {
                        list.add(s4.substring(0, i));
                    }
                }
                final LinkedList<Locale> list2 = new LinkedList<Locale>();
                if (list != null) {
                    final Iterator<String> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        list2.add(Locale.getInstance(s, s2, s3, iterator.next(), null));
                    }
                }
                if (s3.length() > 0) {
                    list2.add(Locale.getInstance(s, s2, s3, "", null));
                }
                if (s2.length() > 0) {
                    list2.add(Locale.getInstance(s, s2, "", "", null));
                    if (list != null) {
                        final Iterator<String> iterator2 = list.iterator();
                        while (iterator2.hasNext()) {
                            list2.add(Locale.getInstance(s, "", s3, iterator2.next(), null));
                        }
                    }
                    if (s3.length() > 0) {
                        list2.add(Locale.getInstance(s, "", s3, "", null));
                    }
                }
                if (s.length() > 0) {
                    list2.add(Locale.getInstance(s, "", "", "", null));
                }
                list2.add(Locale.ROOT);
                return list2;
            }
        }
    }
    
    private static final class NoFallbackControl extends SingleFormatControl
    {
        private static final Control NO_FALLBACK;
        private static final Control PROPERTIES_ONLY_NO_FALLBACK;
        private static final Control CLASS_ONLY_NO_FALLBACK;
        
        protected NoFallbackControl(final List<String> list) {
            super(list);
        }
        
        @Override
        public Locale getFallbackLocale(final String s, final Locale locale) {
            if (s == null || locale == null) {
                throw new NullPointerException();
            }
            return null;
        }
        
        static {
            NO_FALLBACK = new NoFallbackControl(NoFallbackControl.FORMAT_DEFAULT);
            PROPERTIES_ONLY_NO_FALLBACK = new NoFallbackControl(NoFallbackControl.FORMAT_PROPERTIES);
            CLASS_ONLY_NO_FALLBACK = new NoFallbackControl(NoFallbackControl.FORMAT_CLASS);
        }
    }
    
    private static class SingleFormatControl extends Control
    {
        private static final Control PROPERTIES_ONLY;
        private static final Control CLASS_ONLY;
        private final List<String> formats;
        
        protected SingleFormatControl(final List<String> formats) {
            this.formats = formats;
        }
        
        @Override
        public List<String> getFormats(final String s) {
            if (s == null) {
                throw new NullPointerException();
            }
            return this.formats;
        }
        
        static {
            PROPERTIES_ONLY = new SingleFormatControl(SingleFormatControl.FORMAT_PROPERTIES);
            CLASS_ONLY = new SingleFormatControl(SingleFormatControl.FORMAT_CLASS);
        }
    }
    
    private static class RBClassLoader extends ClassLoader
    {
        private static final RBClassLoader INSTANCE;
        private static final ClassLoader loader;
        
        @Override
        public Class<?> loadClass(final String s) throws ClassNotFoundException {
            if (RBClassLoader.loader != null) {
                return RBClassLoader.loader.loadClass(s);
            }
            return Class.forName(s);
        }
        
        @Override
        public URL getResource(final String s) {
            if (RBClassLoader.loader != null) {
                return RBClassLoader.loader.getResource(s);
            }
            return ClassLoader.getSystemResource(s);
        }
        
        @Override
        public InputStream getResourceAsStream(final String s) {
            if (RBClassLoader.loader != null) {
                return RBClassLoader.loader.getResourceAsStream(s);
            }
            return ClassLoader.getSystemResourceAsStream(s);
        }
        
        static {
            INSTANCE = AccessController.doPrivileged((PrivilegedAction<RBClassLoader>)new PrivilegedAction<RBClassLoader>() {
                @Override
                public RBClassLoader run() {
                    return new RBClassLoader();
                }
            });
            ClassLoader systemClassLoader;
            ClassLoader parent;
            for (systemClassLoader = ClassLoader.getSystemClassLoader(); (parent = systemClassLoader.getParent()) != null; systemClassLoader = parent) {}
            loader = systemClassLoader;
        }
    }
}
