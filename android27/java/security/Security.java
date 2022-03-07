package java.security;

import sun.security.util.*;
import java.io.*;
import java.net.*;
import sun.security.jca.*;
import jdk.internal.event.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

public final class Security
{
    private static final Debug sdebug;
    private static Properties props;
    private static final Map<String, Class<?>> spiMap;
    
    private static void initialize() {
        Security.props = new Properties();
        boolean b = false;
        boolean b2 = false;
        final File securityPropFile = securityPropFile("java.security");
        if (securityPropFile.exists()) {
            InputStream inputStream = null;
            try {
                inputStream = new BufferedInputStream(new FileInputStream(securityPropFile));
                Security.props.load(inputStream);
                b = true;
                if (Security.sdebug != null) {
                    Security.sdebug.println("reading security properties file: " + securityPropFile);
                }
            }
            catch (IOException ex) {
                if (Security.sdebug != null) {
                    Security.sdebug.println("unable to load security properties from " + securityPropFile);
                    ex.printStackTrace();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (IOException ex3) {
                        if (Security.sdebug != null) {
                            Security.sdebug.println("unable to close input stream");
                        }
                    }
                }
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (IOException ex4) {
                        if (Security.sdebug != null) {
                            Security.sdebug.println("unable to close input stream");
                        }
                    }
                }
            }
        }
        if ("true".equalsIgnoreCase(Security.props.getProperty("security.overridePropertiesFile"))) {
            String s = System.getProperty("java.security.properties");
            if (s != null && s.startsWith("=")) {
                b2 = true;
                s = s.substring(1);
            }
            if (b2) {
                Security.props = new Properties();
                if (Security.sdebug != null) {
                    Security.sdebug.println("overriding other security properties files!");
                }
            }
            if (s != null) {
                BufferedInputStream bufferedInputStream = null;
                try {
                    s = PropertyExpander.expand(s);
                    final File file = new File(s);
                    URL url;
                    if (file.exists()) {
                        url = new URL("file:" + file.getCanonicalPath());
                    }
                    else {
                        url = new URL(s);
                    }
                    bufferedInputStream = new BufferedInputStream(url.openStream());
                    Security.props.load(bufferedInputStream);
                    b = true;
                    if (Security.sdebug != null) {
                        Security.sdebug.println("reading security properties file: " + url);
                        if (b2) {
                            Security.sdebug.println("overriding other security properties files!");
                        }
                    }
                }
                catch (Exception ex2) {
                    if (Security.sdebug != null) {
                        Security.sdebug.println("unable to load security properties from " + s);
                        ex2.printStackTrace();
                    }
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        }
                        catch (IOException ex5) {
                            if (Security.sdebug != null) {
                                Security.sdebug.println("unable to close input stream");
                            }
                        }
                    }
                }
                finally {
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        }
                        catch (IOException ex6) {
                            if (Security.sdebug != null) {
                                Security.sdebug.println("unable to close input stream");
                            }
                        }
                    }
                }
            }
        }
        if (!b) {
            initializeStatic();
            if (Security.sdebug != null) {
                Security.sdebug.println("unable to load security properties -- using defaults");
            }
        }
    }
    
    private static void initializeStatic() {
        ((Hashtable<String, String>)Security.props).put("security.provider.1", "sun.security.provider.Sun");
        ((Hashtable<String, String>)Security.props).put("security.provider.2", "sun.security.rsa.SunRsaSign");
        ((Hashtable<String, String>)Security.props).put("security.provider.3", "com.sun.net.ssl.internal.ssl.Provider");
        ((Hashtable<String, String>)Security.props).put("security.provider.4", "com.sun.crypto.provider.SunJCE");
        ((Hashtable<String, String>)Security.props).put("security.provider.5", "sun.security.jgss.SunProvider");
        ((Hashtable<String, String>)Security.props).put("security.provider.6", "com.sun.security.sasl.Provider");
    }
    
    private static File securityPropFile(final String s) {
        final String separator = File.separator;
        return new File(System.getProperty("java.home") + separator + "lib" + separator + "security" + separator + s);
    }
    
    private static ProviderProperty getProviderProperty(final String s) {
        final ProviderProperty providerProperty = null;
        final List<Provider> providers = Providers.getProviderList().providers();
        for (int i = 0; i < providers.size(); ++i) {
            final Provider provider = providers.get(i);
            String className = provider.getProperty(s);
            if (className == null) {
                final Enumeration<Object> keys = provider.keys();
                while (keys.hasMoreElements() && className == null) {
                    final String s2 = keys.nextElement();
                    if (s.equalsIgnoreCase(s2)) {
                        className = provider.getProperty(s2);
                        break;
                    }
                }
            }
            if (className != null) {
                final ProviderProperty providerProperty2 = new ProviderProperty();
                providerProperty2.className = className;
                providerProperty2.provider = provider;
                return providerProperty2;
            }
        }
        return providerProperty;
    }
    
    private static String getProviderProperty(final String s, final Provider provider) {
        String s2 = provider.getProperty(s);
        if (s2 == null) {
            final Enumeration<Object> keys = provider.keys();
            while (keys.hasMoreElements() && s2 == null) {
                final String s3 = keys.nextElement();
                if (s.equalsIgnoreCase(s3)) {
                    s2 = provider.getProperty(s3);
                    break;
                }
            }
        }
        return s2;
    }
    
    @Deprecated
    public static String getAlgorithmProperty(final String s, final String s2) {
        final ProviderProperty providerProperty = getProviderProperty("Alg." + s2 + "." + s);
        if (providerProperty != null) {
            return providerProperty.className;
        }
        return null;
    }
    
    public static synchronized int insertProviderAt(final Provider provider, final int n) {
        final String name = provider.getName();
        checkInsertProvider(name);
        final ProviderList fullProviderList = Providers.getFullProviderList();
        final ProviderList insert = ProviderList.insertAt(fullProviderList, provider, n - 1);
        if (fullProviderList == insert) {
            return -1;
        }
        Providers.setProviderList(insert);
        return insert.getIndex(name) + 1;
    }
    
    public static int addProvider(final Provider provider) {
        return insertProviderAt(provider, 0);
    }
    
    public static synchronized void removeProvider(final String s) {
        check("removeProvider." + s);
        Providers.setProviderList(ProviderList.remove(Providers.getFullProviderList(), s));
    }
    
    public static Provider[] getProviders() {
        return Providers.getFullProviderList().toArray();
    }
    
    public static Provider getProvider(final String s) {
        return Providers.getProviderList().getProvider(s);
    }
    
    public static Provider[] getProviders(final String s) {
        final int index = s.indexOf(58);
        String substring;
        String substring2;
        if (index == -1) {
            substring = s;
            substring2 = "";
        }
        else {
            substring = s.substring(0, index);
            substring2 = s.substring(index + 1);
        }
        final Hashtable<String, String> hashtable = new Hashtable<String, String>(1);
        hashtable.put(substring, substring2);
        return getProviders(hashtable);
    }
    
    public static Provider[] getProviders(final Map<String, String> map) {
        final Provider[] providers = getProviders();
        final Set<String> keySet = map.keySet();
        HashSet<Provider> set = new LinkedHashSet<Provider>(5);
        if (keySet == null || providers == null) {
            return providers;
        }
        int n = 1;
        for (final String s : keySet) {
            final LinkedHashSet<Provider> allQualifyingCandidates = getAllQualifyingCandidates(s, map.get(s), providers);
            if (n != 0) {
                set = allQualifyingCandidates;
                n = 0;
            }
            if (allQualifyingCandidates == null || allQualifyingCandidates.isEmpty()) {
                set = null;
                break;
            }
            final Iterator<Provider> iterator2 = set.iterator();
            while (iterator2.hasNext()) {
                if (!allQualifyingCandidates.contains(iterator2.next())) {
                    iterator2.remove();
                }
            }
        }
        if (set == null || set.isEmpty()) {
            return null;
        }
        final Object[] array = set.toArray();
        final Provider[] array2 = new Provider[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = (Provider)array[i];
        }
        return array2;
    }
    
    private static Class<?> getSpiClass(final String s) {
        final Class<?> clazz = Security.spiMap.get(s);
        if (clazz != null) {
            return clazz;
        }
        try {
            final Class<?> forName = Class.forName("java.security." + s + "Spi");
            Security.spiMap.put(s, forName);
            return forName;
        }
        catch (ClassNotFoundException ex) {
            throw new AssertionError("Spi class not found", ex);
        }
    }
    
    static Object[] getImpl(final String s, final String s2, final String s3) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (s3 == null) {
            return GetInstance.getInstance(s2, getSpiClass(s2), s).toArray();
        }
        return GetInstance.getInstance(s2, getSpiClass(s2), s, s3).toArray();
    }
    
    static Object[] getImpl(final String s, final String s2, final String s3, final Object o) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (s3 == null) {
            return GetInstance.getInstance(s2, getSpiClass(s2), s, o).toArray();
        }
        return GetInstance.getInstance(s2, getSpiClass(s2), s, o, s3).toArray();
    }
    
    static Object[] getImpl(final String s, final String s2, final Provider provider) throws NoSuchAlgorithmException {
        return GetInstance.getInstance(s2, getSpiClass(s2), s, provider).toArray();
    }
    
    static Object[] getImpl(final String s, final String s2, final Provider provider, final Object o) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return GetInstance.getInstance(s2, getSpiClass(s2), s, o, provider).toArray();
    }
    
    public static String getProperty(final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new SecurityPermission("getProperty." + s));
        }
        String s2 = Security.props.getProperty(s);
        if (s2 != null) {
            s2 = s2.trim();
        }
        return s2;
    }
    
    public static void setProperty(final String s, final String s2) {
        check("setProperty." + s);
        ((Hashtable<String, String>)Security.props).put(s, s2);
        invalidateSMCache(s);
        if (EventHelper.isLoggingSecurity()) {
            EventHelper.logSecurityPropertyEvent(s, s2);
        }
    }
    
    private static void invalidateSMCache(final String s) {
        final boolean equals = s.equals("package.access");
        final boolean equals2 = s.equals("package.definition");
        if (equals || equals2) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    try {
                        final Class<?> forName = Class.forName("java.lang.SecurityManager", false, null);
                        Field field;
                        boolean accessible;
                        if (equals) {
                            field = forName.getDeclaredField("packageAccessValid");
                            accessible = field.isAccessible();
                            field.setAccessible(true);
                        }
                        else {
                            field = forName.getDeclaredField("packageDefinitionValid");
                            accessible = field.isAccessible();
                            field.setAccessible(true);
                        }
                        field.setBoolean(field, false);
                        field.setAccessible(accessible);
                    }
                    catch (Exception ex) {}
                    return null;
                }
            });
        }
    }
    
    private static void check(final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(s);
        }
    }
    
    private static void checkInsertProvider(final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkSecurityAccess("insertProvider");
            }
            catch (SecurityException ex) {
                try {
                    securityManager.checkSecurityAccess("insertProvider." + s);
                }
                catch (SecurityException ex2) {
                    ex.addSuppressed(ex2);
                    throw ex;
                }
            }
        }
    }
    
    private static LinkedHashSet<Provider> getAllQualifyingCandidates(final String s, final String s2, final Provider[] array) {
        final String[] filterComponents = getFilterComponents(s, s2);
        return getProvidersNotUsingCache(filterComponents[0], filterComponents[1], filterComponents[2], s2, array);
    }
    
    private static LinkedHashSet<Provider> getProvidersNotUsingCache(final String s, final String s2, final String s3, final String s4, final Provider[] array) {
        final LinkedHashSet<Provider> set = new LinkedHashSet<Provider>(5);
        for (int i = 0; i < array.length; ++i) {
            if (isCriterionSatisfied(array[i], s, s2, s3, s4)) {
                set.add(array[i]);
            }
        }
        return set;
    }
    
    private static boolean isCriterionSatisfied(final Provider provider, final String s, final String s2, final String s3, final String s4) {
        String s5 = s + '.' + s2;
        if (s3 != null) {
            s5 = s5 + ' ' + s3;
        }
        String s6 = getProviderProperty(s5, provider);
        if (s6 == null) {
            final String providerProperty = getProviderProperty("Alg.Alias." + s + "." + s2, provider);
            if (providerProperty != null) {
                String s7 = s + "." + providerProperty;
                if (s3 != null) {
                    s7 = s7 + ' ' + s3;
                }
                s6 = getProviderProperty(s7, provider);
            }
            if (s6 == null) {
                return false;
            }
        }
        if (s3 == null) {
            return true;
        }
        if (isStandardAttr(s3)) {
            return isConstraintSatisfied(s3, s4, s6);
        }
        return s4.equalsIgnoreCase(s6);
    }
    
    private static boolean isStandardAttr(final String s) {
        return s.equalsIgnoreCase("KeySize") || s.equalsIgnoreCase("ImplementedIn");
    }
    
    private static boolean isConstraintSatisfied(final String s, final String s2, final String s3) {
        if (s.equalsIgnoreCase("KeySize")) {
            return Integer.parseInt(s2) <= Integer.parseInt(s3);
        }
        return s.equalsIgnoreCase("ImplementedIn") && s2.equalsIgnoreCase(s3);
    }
    
    static String[] getFilterComponents(final String s, final String s2) {
        final int index = s.indexOf(46);
        if (index < 0) {
            throw new InvalidParameterException("Invalid filter");
        }
        final String substring = s.substring(0, index);
        String trim = null;
        String s3;
        if (s2.length() == 0) {
            s3 = s.substring(index + 1).trim();
            if (s3.length() == 0) {
                throw new InvalidParameterException("Invalid filter");
            }
        }
        else {
            final int index2 = s.indexOf(32);
            if (index2 == -1) {
                throw new InvalidParameterException("Invalid filter");
            }
            trim = s.substring(index2 + 1).trim();
            if (trim.length() == 0) {
                throw new InvalidParameterException("Invalid filter");
            }
            if (index2 < index || index == index2 - 1) {
                throw new InvalidParameterException("Invalid filter");
            }
            s3 = s.substring(index + 1, index2);
        }
        return new String[] { substring, s3, trim };
    }
    
    public static Set<String> getAlgorithms(final String s) {
        if (s == null || s.length() == 0 || s.endsWith(".")) {
            return Collections.emptySet();
        }
        final HashSet<String> set = new HashSet<String>();
        final Provider[] providers = getProviders();
        for (int i = 0; i < providers.length; ++i) {
            final Enumeration<Object> keys = providers[i].keys();
            while (keys.hasMoreElements()) {
                final String upperCase = keys.nextElement().toUpperCase(Locale.ENGLISH);
                if (upperCase.startsWith(s.toUpperCase(Locale.ENGLISH)) && upperCase.indexOf(" ") < 0) {
                    set.add(upperCase.substring(s.length() + 1));
                }
            }
        }
        return (Set<String>)Collections.unmodifiableSet((Set<?>)set);
    }
    
    static {
        sdebug = Debug.getInstance("properties");
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                initialize();
                return null;
            }
        });
        spiMap = new ConcurrentHashMap<String, Class<?>>();
    }
    
    private static class ProviderProperty
    {
        String className;
        Provider provider;
    }
}
