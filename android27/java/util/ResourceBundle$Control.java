package java.util;

import java.io.*;
import java.security.*;
import java.net.*;
import java.util.jar.*;
import sun.util.locale.*;

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
