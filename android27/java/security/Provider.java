package java.security;

import sun.security.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.io.*;
import java.util.*;
import java.lang.ref.*;
import java.lang.reflect.*;

public abstract class Provider extends Properties
{
    static final long serialVersionUID = -4298000515446427739L;
    private static final Debug debug;
    private String name;
    private String info;
    private double version;
    private transient Set<Map.Entry<Object, Object>> entrySet;
    private transient int entrySetCallCount;
    private transient boolean initialized;
    private transient boolean legacyChanged;
    private transient volatile boolean servicesChanged;
    private transient Map<String, String> legacyStrings;
    private transient Map<ServiceKey, Service> serviceMap;
    private transient Set<Service> prngServices;
    private transient Map<ServiceKey, Service> legacyMap;
    private transient Set<Service> serviceSet;
    private static final String ALIAS_PREFIX = "Alg.Alias.";
    private static final String ALIAS_PREFIX_LOWER = "alg.alias.";
    private static final int ALIAS_LENGTH;
    private static volatile ServiceKey previousKey;
    private static final Map<String, EngineDescription> knownEngines;
    
    protected Provider(final String name, final double version, final String info) {
        this.entrySet = null;
        this.entrySetCallCount = 0;
        this.name = name;
        this.version = version;
        this.info = info;
        this.serviceMap = new ConcurrentHashMap<ServiceKey, Service>();
        this.putId();
        this.initialized = true;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getVersion() {
        return this.version;
    }
    
    public String getInfo() {
        return this.info;
    }
    
    @Override
    public String toString() {
        return this.name + " version " + this.version;
    }
    
    @Override
    public synchronized void clear() {
        this.check("clearProviderProperties." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Remove " + this.name + " provider properties");
        }
        this.implClear();
    }
    
    @Override
    public synchronized void load(final InputStream inputStream) throws IOException {
        this.check("putProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Load " + this.name + " provider properties");
        }
        final Properties properties = new Properties();
        properties.load(inputStream);
        this.implPutAll(properties);
    }
    
    @Override
    public synchronized void putAll(final Map<?, ?> map) {
        this.check("putProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Put all " + this.name + " provider properties");
        }
        this.implPutAll(map);
    }
    
    @Override
    public synchronized Set<Map.Entry<Object, Object>> entrySet() {
        this.checkInitialized();
        if (this.entrySet == null) {
            if (this.entrySetCallCount++ != 0) {
                return super.entrySet();
            }
            this.entrySet = Collections.unmodifiableMap((Map<?, ?>)this).entrySet();
        }
        if (this.entrySetCallCount != 2) {
            throw new RuntimeException("Internal error.");
        }
        return this.entrySet;
    }
    
    @Override
    public Set<Object> keySet() {
        this.checkInitialized();
        return Collections.unmodifiableSet(super.keySet());
    }
    
    @Override
    public Collection<Object> values() {
        this.checkInitialized();
        return Collections.unmodifiableCollection(super.values());
    }
    
    @Override
    public synchronized Object put(final Object o, final Object o2) {
        this.check("putProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Set " + this.name + " provider property [" + o + "/" + o2 + "]");
        }
        return this.implPut(o, o2);
    }
    
    @Override
    public synchronized Object putIfAbsent(final Object o, final Object o2) {
        this.check("putProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Set " + this.name + " provider property [" + o + "/" + o2 + "]");
        }
        return this.implPutIfAbsent(o, o2);
    }
    
    @Override
    public synchronized Object remove(final Object o) {
        this.check("removeProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Remove " + this.name + " provider property " + o);
        }
        return this.implRemove(o);
    }
    
    @Override
    public synchronized boolean remove(final Object o, final Object o2) {
        this.check("removeProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Remove " + this.name + " provider property " + o);
        }
        return this.implRemove(o, o2);
    }
    
    @Override
    public synchronized boolean replace(final Object o, final Object o2, final Object o3) {
        this.check("putProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Replace " + this.name + " provider property " + o);
        }
        return this.implReplace(o, o2, o3);
    }
    
    @Override
    public synchronized Object replace(final Object o, final Object o2) {
        this.check("putProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Replace " + this.name + " provider property " + o);
        }
        return this.implReplace(o, o2);
    }
    
    @Override
    public synchronized void replaceAll(final BiFunction<? super Object, ? super Object, ?> biFunction) {
        this.check("putProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("ReplaceAll " + this.name + " provider property ");
        }
        this.implReplaceAll(biFunction);
    }
    
    @Override
    public synchronized Object compute(final Object o, final BiFunction<? super Object, ? super Object, ?> biFunction) {
        this.check("putProviderProperty." + this.name);
        this.check("removeProviderProperty" + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Compute " + this.name + " provider property " + o);
        }
        return this.implCompute(o, biFunction);
    }
    
    @Override
    public synchronized Object computeIfAbsent(final Object o, final Function<? super Object, ?> function) {
        this.check("putProviderProperty." + this.name);
        this.check("removeProviderProperty" + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("ComputeIfAbsent " + this.name + " provider property " + o);
        }
        return this.implComputeIfAbsent(o, function);
    }
    
    @Override
    public synchronized Object computeIfPresent(final Object o, final BiFunction<? super Object, ? super Object, ?> biFunction) {
        this.check("putProviderProperty." + this.name);
        this.check("removeProviderProperty" + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("ComputeIfPresent " + this.name + " provider property " + o);
        }
        return this.implComputeIfPresent(o, biFunction);
    }
    
    @Override
    public synchronized Object merge(final Object o, final Object o2, final BiFunction<? super Object, ? super Object, ?> biFunction) {
        this.check("putProviderProperty." + this.name);
        this.check("removeProviderProperty" + this.name);
        if (Provider.debug != null) {
            Provider.debug.println("Merge " + this.name + " provider property " + o);
        }
        return this.implMerge(o, o2, biFunction);
    }
    
    @Override
    public Object get(final Object o) {
        this.checkInitialized();
        return super.get(o);
    }
    
    @Override
    public synchronized Object getOrDefault(final Object o, final Object o2) {
        this.checkInitialized();
        return super.getOrDefault(o, o2);
    }
    
    @Override
    public synchronized void forEach(final BiConsumer<? super Object, ? super Object> biConsumer) {
        this.checkInitialized();
        super.forEach(biConsumer);
    }
    
    @Override
    public Enumeration<Object> keys() {
        this.checkInitialized();
        return super.keys();
    }
    
    @Override
    public Enumeration<Object> elements() {
        this.checkInitialized();
        return super.elements();
    }
    
    @Override
    public String getProperty(final String s) {
        this.checkInitialized();
        return super.getProperty(s);
    }
    
    private void checkInitialized() {
        if (!this.initialized) {
            throw new IllegalStateException();
        }
    }
    
    private void check(final String s) {
        this.checkInitialized();
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(s);
        }
    }
    
    private void putId() {
        super.put("Provider.id name", String.valueOf(this.name));
        super.put("Provider.id version", String.valueOf(this.version));
        super.put("Provider.id info", String.valueOf(this.info));
        super.put("Provider.id className", this.getClass().getName());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for (final Map.Entry<Object, Object> entry : super.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue());
        }
        this.defaults = null;
        objectInputStream.defaultReadObject();
        this.serviceMap = new ConcurrentHashMap<ServiceKey, Service>();
        this.implClear();
        this.initialized = true;
        this.putAll(hashMap);
    }
    
    private boolean checkLegacy(final Object o) {
        if (((String)o).startsWith("Provider.")) {
            return false;
        }
        this.legacyChanged = true;
        if (this.legacyStrings == null) {
            this.legacyStrings = new LinkedHashMap<String, String>();
        }
        return true;
    }
    
    private void implPutAll(final Map<?, ?> map) {
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.implPut(entry.getKey(), entry.getValue());
        }
    }
    
    private Object implRemove(final Object o) {
        if (o instanceof String) {
            if (!this.checkLegacy(o)) {
                return null;
            }
            this.legacyStrings.remove(o);
        }
        return super.remove(o);
    }
    
    private boolean implRemove(final Object o, final Object o2) {
        if (o instanceof String && o2 instanceof String) {
            if (!this.checkLegacy(o)) {
                return false;
            }
            this.legacyStrings.remove(o, o2);
        }
        return super.remove(o, o2);
    }
    
    private boolean implReplace(final Object o, final Object o2, final Object o3) {
        if (o instanceof String && o2 instanceof String && o3 instanceof String) {
            if (!this.checkLegacy(o)) {
                return false;
            }
            this.legacyStrings.replace((String)o, (String)o2, (String)o3);
        }
        return super.replace(o, o2, o3);
    }
    
    private Object implReplace(final Object o, final Object o2) {
        if (o instanceof String && o2 instanceof String) {
            if (!this.checkLegacy(o)) {
                return null;
            }
            this.legacyStrings.replace((String)o, (String)o2);
        }
        return super.replace(o, o2);
    }
    
    private void implReplaceAll(final BiFunction<? super Object, ? super Object, ?> biFunction) {
        this.legacyChanged = true;
        if (this.legacyStrings == null) {
            this.legacyStrings = new LinkedHashMap<String, String>();
        }
        else {
            this.legacyStrings.replaceAll((BiFunction<? super String, ? super String, ? extends String>)biFunction);
        }
        super.replaceAll(biFunction);
    }
    
    private Object implMerge(final Object o, final Object o2, final BiFunction<? super Object, ? super Object, ?> biFunction) {
        if (o instanceof String && o2 instanceof String) {
            if (!this.checkLegacy(o)) {
                return null;
            }
            this.legacyStrings.merge((String)o, (String)o2, (BiFunction<? super String, ? super String, ? extends String>)biFunction);
        }
        return super.merge(o, o2, biFunction);
    }
    
    private Object implCompute(final Object o, final BiFunction<? super Object, ? super Object, ?> biFunction) {
        if (o instanceof String) {
            if (!this.checkLegacy(o)) {
                return null;
            }
            this.legacyStrings.compute((String)o, (BiFunction<? super String, ? super String, ? extends String>)biFunction);
        }
        return super.compute(o, biFunction);
    }
    
    private Object implComputeIfAbsent(final Object o, final Function<? super Object, ?> function) {
        if (o instanceof String) {
            if (!this.checkLegacy(o)) {
                return null;
            }
            this.legacyStrings.computeIfAbsent((String)o, (Function<? super String, ? extends String>)function);
        }
        return super.computeIfAbsent(o, function);
    }
    
    private Object implComputeIfPresent(final Object o, final BiFunction<? super Object, ? super Object, ?> biFunction) {
        if (o instanceof String) {
            if (!this.checkLegacy(o)) {
                return null;
            }
            this.legacyStrings.computeIfPresent((String)o, (BiFunction<? super String, ? super String, ? extends String>)biFunction);
        }
        return super.computeIfPresent(o, biFunction);
    }
    
    private Object implPut(final Object o, final Object o2) {
        if (o instanceof String && o2 instanceof String) {
            if (!this.checkLegacy(o)) {
                return null;
            }
            this.legacyStrings.put((String)o, (String)o2);
        }
        return super.put(o, o2);
    }
    
    private Object implPutIfAbsent(final Object o, final Object o2) {
        if (o instanceof String && o2 instanceof String) {
            if (!this.checkLegacy(o)) {
                return null;
            }
            this.legacyStrings.putIfAbsent((String)o, (String)o2);
        }
        return super.putIfAbsent(o, o2);
    }
    
    private void implClear() {
        if (this.legacyStrings != null) {
            this.legacyStrings.clear();
        }
        if (this.legacyMap != null) {
            this.legacyMap.clear();
        }
        this.serviceMap.clear();
        this.legacyChanged = false;
        this.servicesChanged = false;
        this.serviceSet = null;
        this.prngServices = null;
        super.clear();
        this.putId();
    }
    
    private void ensureLegacyParsed() {
        if (!this.legacyChanged || this.legacyStrings == null) {
            return;
        }
        this.serviceSet = null;
        if (this.legacyMap == null) {
            this.legacyMap = new ConcurrentHashMap<ServiceKey, Service>();
        }
        else {
            this.legacyMap.clear();
        }
        for (final Map.Entry<String, String> entry : this.legacyStrings.entrySet()) {
            this.parseLegacyPut(entry.getKey(), entry.getValue());
        }
        this.removeInvalidServices(this.legacyMap);
        this.legacyChanged = false;
    }
    
    private void removeInvalidServices(final Map<ServiceKey, Service> map) {
        final Iterator<Map.Entry<ServiceKey, Service>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getValue().isValid()) {
                iterator.remove();
            }
        }
    }
    
    private static String[] getTypeAndAlgorithm(final String s) {
        final int index = s.indexOf(46);
        if (index < 1) {
            if (Provider.debug != null) {
                Provider.debug.println("Ignoring invalid entry in provider: " + s);
            }
            return null;
        }
        return new String[] { s.substring(0, index), s.substring(index + 1) };
    }
    
    private void parseLegacyPut(final String s, final String s2) {
        if (s.toLowerCase(Locale.ENGLISH).startsWith("alg.alias.")) {
            final String[] typeAndAlgorithm = getTypeAndAlgorithm(s.substring(Provider.ALIAS_LENGTH));
            if (typeAndAlgorithm == null) {
                return;
            }
            final String engineName = getEngineName(typeAndAlgorithm[0]);
            final String intern = typeAndAlgorithm[1].intern();
            final ServiceKey serviceKey = new ServiceKey(engineName, s2, true);
            Service service = this.legacyMap.get(serviceKey);
            if (service == null) {
                service = new Service(this);
                service.type = engineName;
                service.algorithm = s2;
                this.legacyMap.put(serviceKey, service);
            }
            this.legacyMap.put(new ServiceKey(engineName, intern, true), service);
            service.addAlias(intern);
        }
        else {
            final String[] typeAndAlgorithm2 = getTypeAndAlgorithm(s);
            if (typeAndAlgorithm2 == null) {
                return;
            }
            final int index = typeAndAlgorithm2[1].indexOf(32);
            if (index == -1) {
                final String engineName2 = getEngineName(typeAndAlgorithm2[0]);
                final String intern2 = typeAndAlgorithm2[1].intern();
                final ServiceKey serviceKey2 = new ServiceKey(engineName2, intern2, true);
                Service service2 = this.legacyMap.get(serviceKey2);
                if (service2 == null) {
                    service2 = new Service(this);
                    service2.type = engineName2;
                    service2.algorithm = intern2;
                    this.legacyMap.put(serviceKey2, service2);
                }
                service2.className = s2;
                if (engineName2.equals("SecureRandom")) {
                    this.updateSecureRandomEntries(true, service2);
                }
            }
            else {
                final String engineName3 = getEngineName(typeAndAlgorithm2[0]);
                final String s3 = typeAndAlgorithm2[1];
                final String intern3 = s3.substring(0, index).intern();
                String s4;
                for (s4 = s3.substring(index + 1); s4.startsWith(" "); s4 = s4.substring(1)) {}
                final String intern4 = s4.intern();
                final ServiceKey serviceKey3 = new ServiceKey(engineName3, intern3, true);
                Service service3 = this.legacyMap.get(serviceKey3);
                if (service3 == null) {
                    service3 = new Service(this);
                    service3.type = engineName3;
                    service3.algorithm = intern3;
                    this.legacyMap.put(serviceKey3, service3);
                }
                service3.addAttribute(intern4, s2);
            }
        }
    }
    
    public Service getService(final String s, final String s2) {
        this.checkInitialized();
        ServiceKey previousKey = Provider.previousKey;
        if (!previousKey.matches(s, s2)) {
            previousKey = (Provider.previousKey = new ServiceKey(s, s2, false));
        }
        if (!this.serviceMap.isEmpty()) {
            final Service service = this.serviceMap.get(previousKey);
            if (service != null) {
                return service;
            }
        }
        synchronized (this) {
            this.ensureLegacyParsed();
            if (this.legacyMap != null && !this.legacyMap.isEmpty()) {
                return this.legacyMap.get(previousKey);
            }
        }
        return null;
    }
    
    public synchronized Set<Service> getServices() {
        this.checkInitialized();
        if (this.legacyChanged || this.servicesChanged) {
            this.serviceSet = null;
        }
        if (this.serviceSet == null) {
            this.ensureLegacyParsed();
            final LinkedHashSet<Service> set = new LinkedHashSet<Service>();
            if (!this.serviceMap.isEmpty()) {
                set.addAll((Collection<?>)this.serviceMap.values());
            }
            if (this.legacyMap != null && !this.legacyMap.isEmpty()) {
                set.addAll((Collection<?>)this.legacyMap.values());
            }
            this.serviceSet = (Set<Service>)Collections.unmodifiableSet((Set<?>)set);
            this.servicesChanged = false;
        }
        return this.serviceSet;
    }
    
    protected void putService(final Service service) {
        this.check("putProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println(this.name + ".putService(): " + service);
        }
        if (service == null) {
            throw new NullPointerException();
        }
        if (service.getProvider() != this) {
            throw new IllegalArgumentException("service.getProvider() must match this Provider object");
        }
        final String type = service.getType();
        final ServiceKey serviceKey = new ServiceKey(type, service.getAlgorithm(), true);
        this.implRemoveService(this.serviceMap.get(serviceKey));
        this.serviceMap.put(serviceKey, service);
        final Iterator<String> iterator = service.getAliases().iterator();
        while (iterator.hasNext()) {
            this.serviceMap.put(new ServiceKey(type, (String)iterator.next(), true), service);
        }
        this.servicesChanged = true;
        synchronized (this) {
            this.putPropertyStrings(service);
            if (type.equals("SecureRandom")) {
                this.updateSecureRandomEntries(true, service);
            }
        }
    }
    
    private void updateSecureRandomEntries(final boolean b, final Service service) {
        Objects.requireNonNull(service);
        if (b) {
            if (this.prngServices == null) {
                this.prngServices = new LinkedHashSet<Service>();
            }
            this.prngServices.add(service);
        }
        else {
            this.prngServices.remove(service);
        }
        if (Provider.debug != null) {
            Provider.debug.println((b ? "Add" : "Remove") + " SecureRandom algo " + service.getAlgorithm());
        }
    }
    
    synchronized Service getDefaultSecureRandomService() {
        this.checkInitialized();
        if (this.legacyChanged) {
            this.prngServices = null;
            this.ensureLegacyParsed();
        }
        if (this.prngServices != null && !this.prngServices.isEmpty()) {
            return this.prngServices.iterator().next();
        }
        return null;
    }
    
    private void putPropertyStrings(final Service service) {
        final String type = service.getType();
        final String algorithm = service.getAlgorithm();
        super.put(type + "." + algorithm, service.getClassName());
        final Iterator<String> iterator = service.getAliases().iterator();
        while (iterator.hasNext()) {
            super.put("Alg.Alias." + type + "." + iterator.next(), algorithm);
        }
        for (final Map.Entry<K, Object> entry : service.attributes.entrySet()) {
            super.put(type + "." + algorithm + " " + entry.getKey(), entry.getValue());
        }
    }
    
    private void removePropertyStrings(final Service service) {
        final String type = service.getType();
        final String algorithm = service.getAlgorithm();
        super.remove(type + "." + algorithm);
        final Iterator<String> iterator = service.getAliases().iterator();
        while (iterator.hasNext()) {
            super.remove("Alg.Alias." + type + "." + iterator.next());
        }
        final Iterator<Map.Entry<Object, V>> iterator2 = service.attributes.entrySet().iterator();
        while (iterator2.hasNext()) {
            super.remove(type + "." + algorithm + " " + iterator2.next().getKey());
        }
    }
    
    protected void removeService(final Service service) {
        this.check("removeProviderProperty." + this.name);
        if (Provider.debug != null) {
            Provider.debug.println(this.name + ".removeService(): " + service);
        }
        if (service == null) {
            throw new NullPointerException();
        }
        this.implRemoveService(service);
    }
    
    private void implRemoveService(final Service service) {
        if (service == null || this.serviceMap.isEmpty()) {
            return;
        }
        final String type = service.getType();
        final ServiceKey serviceKey = new ServiceKey(type, service.getAlgorithm(), false);
        if (service != this.serviceMap.get(serviceKey)) {
            return;
        }
        this.servicesChanged = true;
        this.serviceMap.remove(serviceKey);
        final Iterator<String> iterator = service.getAliases().iterator();
        while (iterator.hasNext()) {
            this.serviceMap.remove(new ServiceKey(type, (String)iterator.next(), false));
        }
        synchronized (this) {
            this.removePropertyStrings(service);
            if (type.equals("SecureRandom")) {
                this.updateSecureRandomEntries(false, service);
            }
        }
    }
    
    private static void addEngine(final String s, final boolean b, final String s2) {
        final EngineDescription engineDescription = new EngineDescription(s, b, s2);
        Provider.knownEngines.put(s.toLowerCase(Locale.ENGLISH), engineDescription);
        Provider.knownEngines.put(s, engineDescription);
    }
    
    private static String getEngineName(final String s) {
        EngineDescription engineDescription = Provider.knownEngines.get(s);
        if (engineDescription == null) {
            engineDescription = Provider.knownEngines.get(s.toLowerCase(Locale.ENGLISH));
        }
        return (engineDescription == null) ? s : engineDescription.name;
    }
    
    static {
        debug = Debug.getInstance("provider", "Provider");
        ALIAS_LENGTH = "Alg.Alias.".length();
        Provider.previousKey = new ServiceKey("", "", false);
        knownEngines = new HashMap<String, EngineDescription>();
        addEngine("AlgorithmParameterGenerator", false, null);
        addEngine("AlgorithmParameters", false, null);
        addEngine("KeyFactory", false, null);
        addEngine("KeyPairGenerator", false, null);
        addEngine("KeyStore", false, null);
        addEngine("MessageDigest", false, null);
        addEngine("SecureRandom", false, null);
        addEngine("Signature", true, null);
        addEngine("CertificateFactory", false, null);
        addEngine("CertPathBuilder", false, null);
        addEngine("CertPathValidator", false, null);
        addEngine("CertStore", false, "java.security.cert.CertStoreParameters");
        addEngine("Cipher", true, null);
        addEngine("ExemptionMechanism", false, null);
        addEngine("Mac", true, null);
        addEngine("KeyAgreement", true, null);
        addEngine("KeyGenerator", false, null);
        addEngine("SecretKeyFactory", false, null);
        addEngine("KeyManagerFactory", false, null);
        addEngine("SSLContext", false, null);
        addEngine("TrustManagerFactory", false, null);
        addEngine("GssApiMechanism", false, null);
        addEngine("SaslClientFactory", false, null);
        addEngine("SaslServerFactory", false, null);
        addEngine("Policy", false, "java.security.Policy$Parameters");
        addEngine("Configuration", false, "javax.security.auth.login.Configuration$Parameters");
        addEngine("XMLSignatureFactory", false, null);
        addEngine("KeyInfoFactory", false, null);
        addEngine("TransformService", false, null);
        addEngine("TerminalFactory", false, "java.lang.Object");
    }
    
    private static class EngineDescription
    {
        final String name;
        final boolean supportsParameter;
        final String constructorParameterClassName;
        private volatile Class<?> constructorParameterClass;
        
        EngineDescription(final String name, final boolean supportsParameter, final String constructorParameterClassName) {
            this.name = name;
            this.supportsParameter = supportsParameter;
            this.constructorParameterClassName = constructorParameterClassName;
        }
        
        Class<?> getConstructorParameterClass() throws ClassNotFoundException {
            Class<?> constructorParameterClass = this.constructorParameterClass;
            if (constructorParameterClass == null) {
                constructorParameterClass = Class.forName(this.constructorParameterClassName);
                this.constructorParameterClass = constructorParameterClass;
            }
            return constructorParameterClass;
        }
    }
    
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
            this.type = getEngineName(s);
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
                final EngineDescription engineDescription = Provider.knownEngines.get(this.type);
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
            final EngineDescription engineDescription = Provider.knownEngines.get(this.type);
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
    
    private static class UString
    {
        final String string;
        final String lowerString;
        
        UString(final String string) {
            this.string = string;
            this.lowerString = string.toLowerCase(Locale.ENGLISH);
        }
        
        @Override
        public int hashCode() {
            return this.lowerString.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof UString && this.lowerString.equals(((UString)o).lowerString));
        }
        
        @Override
        public String toString() {
            return this.string;
        }
    }
    
    private static class ServiceKey
    {
        private final String type;
        private final String algorithm;
        private final String originalAlgorithm;
        
        private ServiceKey(final String type, String upperCase, final boolean b) {
            this.type = type;
            this.originalAlgorithm = upperCase;
            upperCase = upperCase.toUpperCase(Locale.ENGLISH);
            this.algorithm = (b ? upperCase.intern() : upperCase);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.type, this.algorithm);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ServiceKey)) {
                return false;
            }
            final ServiceKey serviceKey = (ServiceKey)o;
            return this.type.equals(serviceKey.type) && this.algorithm.equals(serviceKey.algorithm);
        }
        
        boolean matches(final String s, final String s2) {
            return this.type == s && this.originalAlgorithm == s2;
        }
    }
}
