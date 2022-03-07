package java.security;

import java.util.*;
import java.lang.ref.*;
import java.lang.reflect.*;

public static class Service
{
    private String type;
    private String algorithm;
    private String className;
    private final Provider provider;
    private List<String> aliases;
    private Map<UString, String> attributes;
    private volatile Reference<Class<?>> classRef;
    private volatile Boolean hasKeyAttributes;
    private String[] supportedFormats;
    private Class[] supportedClasses;
    private boolean registered;
    private static final Class<?>[] CLASS0;
    
    private Service(final Provider provider) {
        this.provider = provider;
        this.aliases = Collections.emptyList();
        this.attributes = Collections.emptyMap();
    }
    
    private boolean isValid() {
        return this.type != null && this.algorithm != null && this.className != null;
    }
    
    private void addAlias(final String s) {
        if (this.aliases.isEmpty()) {
            this.aliases = new ArrayList<String>(2);
        }
        this.aliases.add(s);
    }
    
    void addAttribute(final String s, final String s2) {
        if (this.attributes.isEmpty()) {
            this.attributes = new HashMap<UString, String>(8);
        }
        this.attributes.put(new UString(s), s2);
    }
    
    public Service(final Provider provider, final String s, final String algorithm, final String className, final List<String> list, final Map<String, String> map) {
        if (provider == null || s == null || algorithm == null || className == null) {
            throw new NullPointerException();
        }
        this.provider = provider;
        this.type = Provider.access$900(s);
        this.algorithm = algorithm;
        this.className = className;
        if (list == null) {
            this.aliases = Collections.emptyList();
        }
        else {
            this.aliases = new ArrayList<String>(list);
        }
        if (map == null) {
            this.attributes = Collections.emptyMap();
        }
        else {
            this.attributes = new HashMap<UString, String>();
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                this.attributes.put(new UString(entry.getKey()), entry.getValue());
            }
        }
    }
    
    public final String getType() {
        return this.type;
    }
    
    public final String getAlgorithm() {
        return this.algorithm;
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final String getClassName() {
        return this.className;
    }
    
    private final List<String> getAliases() {
        return this.aliases;
    }
    
    public final String getAttribute(final String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        return this.attributes.get(new UString(s));
    }
    
    public Object newInstance(final Object o) throws NoSuchAlgorithmException {
        if (!this.registered) {
            if (this.provider.getService(this.type, this.algorithm) != this) {
                throw new NoSuchAlgorithmException("Service not registered with Provider " + this.provider.getName() + ": " + this);
            }
            this.registered = true;
        }
        try {
            final EngineDescription engineDescription = Provider.access$1000().get(this.type);
            if (engineDescription == null) {
                return this.newInstanceGeneric(o);
            }
            if (engineDescription.constructorParameterClassName == null) {
                if (o != null) {
                    throw new InvalidParameterException("constructorParameter not used with " + this.type + " engines");
                }
                return this.getImplClass().getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            else {
                final Class<?> constructorParameterClass = engineDescription.getConstructorParameterClass();
                if (o != null && !constructorParameterClass.isAssignableFrom(o.getClass())) {
                    throw new InvalidParameterException("constructorParameter must be instanceof " + engineDescription.constructorParameterClassName.replace('$', '.') + " for engine type " + this.type);
                }
                return this.getImplClass().getConstructor(constructorParameterClass).newInstance(o);
            }
        }
        catch (NoSuchAlgorithmException ex) {
            throw ex;
        }
        catch (InvocationTargetException ex2) {
            throw new NoSuchAlgorithmException("Error constructing implementation (algorithm: " + this.algorithm + ", provider: " + this.provider.getName() + ", class: " + this.className + ")", ex2.getCause());
        }
        catch (Exception ex3) {
            throw new NoSuchAlgorithmException("Error constructing implementation (algorithm: " + this.algorithm + ", provider: " + this.provider.getName() + ", class: " + this.className + ")", ex3);
        }
    }
    
    private Class<?> getImplClass() throws NoSuchAlgorithmException {
        try {
            final Reference<Class<?>> classRef = this.classRef;
            Class<?> clazz = (classRef == null) ? null : classRef.get();
            if (clazz == null) {
                final ClassLoader classLoader = this.provider.getClass().getClassLoader();
                if (classLoader == null) {
                    clazz = Class.forName(this.className);
                }
                else {
                    clazz = classLoader.loadClass(this.className);
                }
                if (!Modifier.isPublic(clazz.getModifiers())) {
                    throw new NoSuchAlgorithmException("class configured for " + this.type + " (provider: " + this.provider.getName() + ") is not public.");
                }
                this.classRef = new WeakReference<Class<?>>(clazz);
            }
            return clazz;
        }
        catch (ClassNotFoundException ex) {
            throw new NoSuchAlgorithmException("class configured for " + this.type + " (provider: " + this.provider.getName() + ") cannot be found.", ex);
        }
    }
    
    private Object newInstanceGeneric(final Object o) throws Exception {
        final Class<?> implClass = this.getImplClass();
        if (o == null) {
            try {
                return implClass.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (NoSuchMethodException ex) {
                throw new NoSuchAlgorithmException("No public no-arg constructor found in class " + this.className);
            }
        }
        final Class<?> class1 = o.getClass();
        for (final Constructor constructor : implClass.getConstructors()) {
            final Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 1) {
                if (parameterTypes[0].isAssignableFrom(class1)) {
                    return constructor.newInstance(o);
                }
            }
        }
        throw new NoSuchAlgorithmException("No public constructor matching " + class1.getName() + " found in class " + this.className);
    }
    
    public boolean supportsParameter(final Object o) {
        final EngineDescription engineDescription = Provider.access$1000().get(this.type);
        if (engineDescription == null) {
            return true;
        }
        if (!engineDescription.supportsParameter) {
            throw new InvalidParameterException("supportsParameter() not used with " + this.type + " engines");
        }
        if (o != null && !(o instanceof Key)) {
            throw new InvalidParameterException("Parameter must be instanceof Key for engine " + this.type);
        }
        if (!this.hasKeyAttributes()) {
            return true;
        }
        if (o == null) {
            return false;
        }
        final Key key = (Key)o;
        return this.supportsKeyFormat(key) || this.supportsKeyClass(key);
    }
    
    private boolean hasKeyAttributes() {
        Boolean hasKeyAttributes = this.hasKeyAttributes;
        if (hasKeyAttributes == null) {
            synchronized (this) {
                final String attribute = this.getAttribute("SupportedKeyFormats");
                if (attribute != null) {
                    this.supportedFormats = attribute.split("\\|");
                }
                final String attribute2 = this.getAttribute("SupportedKeyClasses");
                if (attribute2 != null) {
                    final String[] split = attribute2.split("\\|");
                    final ArrayList list = new ArrayList<Class<?>>(split.length);
                    final String[] array = split;
                    for (int length = array.length, i = 0; i < length; ++i) {
                        final Class<?> keyClass = this.getKeyClass(array[i]);
                        if (keyClass != null) {
                            list.add(keyClass);
                        }
                    }
                    this.supportedClasses = list.toArray(Service.CLASS0);
                }
                hasKeyAttributes = (this.supportedFormats != null || this.supportedClasses != null);
                this.hasKeyAttributes = hasKeyAttributes;
            }
        }
        return hasKeyAttributes;
    }
    
    private Class<?> getKeyClass(final String s) {
        try {
            return Class.forName(s);
        }
        catch (ClassNotFoundException ex) {
            try {
                final ClassLoader classLoader = this.provider.getClass().getClassLoader();
                if (classLoader != null) {
                    return classLoader.loadClass(s);
                }
            }
            catch (ClassNotFoundException ex2) {}
            return null;
        }
    }
    
    private boolean supportsKeyFormat(final Key key) {
        if (this.supportedFormats == null) {
            return false;
        }
        final String format = key.getFormat();
        if (format == null) {
            return false;
        }
        final String[] supportedFormats = this.supportedFormats;
        for (int length = supportedFormats.length, i = 0; i < length; ++i) {
            if (supportedFormats[i].equals(format)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean supportsKeyClass(final Key key) {
        if (this.supportedClasses == null) {
            return false;
        }
        final Class<? extends Key> class1 = key.getClass();
        final Class[] supportedClasses = this.supportedClasses;
        for (int length = supportedClasses.length, i = 0; i < length; ++i) {
            if (supportedClasses[i].isAssignableFrom(class1)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.provider.getName() + ": " + this.type + "." + this.algorithm + " -> " + this.className + (this.aliases.isEmpty() ? "" : ("\r\n  aliases: " + this.aliases.toString())) + (this.attributes.isEmpty() ? "" : ("\r\n  attributes: " + this.attributes.toString())) + "\r\n";
    }
    
    static {
        CLASS0 = new Class[0];
    }
}
