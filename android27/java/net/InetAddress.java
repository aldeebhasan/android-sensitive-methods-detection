package java.net;

import sun.misc.*;
import sun.net.spi.nameservice.*;
import sun.net.util.*;
import java.security.*;
import java.io.*;
import sun.security.action.*;
import sun.net.*;
import java.util.*;

public class InetAddress implements Serializable
{
    static final int IPv4 = 1;
    static final int IPv6 = 2;
    static transient boolean preferIPv6Address;
    final transient InetAddressHolder holder;
    private static List<NameService> nameServices;
    private transient String canonicalHostName;
    private static final long serialVersionUID = 3286316764910316507L;
    private static Cache addressCache;
    private static Cache negativeCache;
    private static boolean addressCacheInit;
    static InetAddress[] unknown_array;
    static InetAddressImpl impl;
    private static final HashMap<String, Void> lookupTable;
    private static InetAddress cachedLocalHost;
    private static long cacheTime;
    private static final long maxCacheTime = 5000L;
    private static final Object cacheLock;
    private static final long FIELDS_OFFSET;
    private static final Unsafe UNSAFE;
    private static final ObjectStreamField[] serialPersistentFields;
    
    InetAddressHolder holder() {
        return this.holder;
    }
    
    InetAddress() {
        this.canonicalHostName = null;
        this.holder = new InetAddressHolder();
    }
    
    private Object readResolve() throws ObjectStreamException {
        return new Inet4Address(this.holder().getHostName(), this.holder().getAddress());
    }
    
    public boolean isMulticastAddress() {
        return false;
    }
    
    public boolean isAnyLocalAddress() {
        return false;
    }
    
    public boolean isLoopbackAddress() {
        return false;
    }
    
    public boolean isLinkLocalAddress() {
        return false;
    }
    
    public boolean isSiteLocalAddress() {
        return false;
    }
    
    public boolean isMCGlobal() {
        return false;
    }
    
    public boolean isMCNodeLocal() {
        return false;
    }
    
    public boolean isMCLinkLocal() {
        return false;
    }
    
    public boolean isMCSiteLocal() {
        return false;
    }
    
    public boolean isMCOrgLocal() {
        return false;
    }
    
    public boolean isReachable(final int n) throws IOException {
        return this.isReachable(null, 0, n);
    }
    
    public boolean isReachable(final NetworkInterface networkInterface, final int n, final int n2) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException("ttl can't be negative");
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("timeout can't be negative");
        }
        return InetAddress.impl.isReachable(this, n2, networkInterface, n);
    }
    
    public String getHostName() {
        return this.getHostName(true);
    }
    
    String getHostName(final boolean b) {
        if (this.holder().getHostName() == null) {
            this.holder().hostName = getHostFromNameService(this, b);
        }
        return this.holder().getHostName();
    }
    
    public String getCanonicalHostName() {
        if (this.canonicalHostName == null) {
            this.canonicalHostName = getHostFromNameService(this, true);
        }
        return this.canonicalHostName;
    }
    
    private static String getHostFromNameService(final InetAddress inetAddress, final boolean b) {
        String s = null;
        for (final NameService nameService : InetAddress.nameServices) {
            try {
                s = nameService.getHostByAddr(inetAddress.getAddress());
                if (b) {
                    final SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkConnect(s, -1);
                    }
                }
                final InetAddress[] allByName0 = getAllByName0(s, b);
                boolean equals = false;
                if (allByName0 != null) {
                    for (int n = 0; !equals && n < allByName0.length; equals = inetAddress.equals(allByName0[n]), ++n) {}
                }
                if (!equals) {
                    return inetAddress.getHostAddress();
                }
            }
            catch (SecurityException ex) {
                s = inetAddress.getHostAddress();
            }
            catch (UnknownHostException ex2) {
                s = inetAddress.getHostAddress();
                continue;
            }
            break;
        }
        return s;
    }
    
    public byte[] getAddress() {
        return null;
    }
    
    public String getHostAddress() {
        return null;
    }
    
    @Override
    public int hashCode() {
        return -1;
    }
    
    @Override
    public boolean equals(final Object o) {
        return false;
    }
    
    @Override
    public String toString() {
        final String hostName = this.holder().getHostName();
        return ((hostName != null) ? hostName : "") + "/" + this.getHostAddress();
    }
    
    private static void cacheInitIfNeeded() {
        assert Thread.holdsLock(InetAddress.addressCache);
        if (InetAddress.addressCacheInit) {
            return;
        }
        (InetAddress.unknown_array = new InetAddress[1])[0] = InetAddress.impl.anyLocalAddress();
        InetAddress.addressCache.put(InetAddress.impl.anyLocalAddress().getHostName(), InetAddress.unknown_array);
        InetAddress.addressCacheInit = true;
    }
    
    private static void cacheAddresses(String lowerCase, final InetAddress[] array, final boolean b) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (InetAddress.addressCache) {
            cacheInitIfNeeded();
            if (b) {
                InetAddress.addressCache.put(lowerCase, array);
            }
            else {
                InetAddress.negativeCache.put(lowerCase, array);
            }
        }
    }
    
    private static InetAddress[] getCachedAddresses(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (InetAddress.addressCache) {
            cacheInitIfNeeded();
            CacheEntry cacheEntry = InetAddress.addressCache.get(lowerCase);
            if (cacheEntry == null) {
                cacheEntry = InetAddress.negativeCache.get(lowerCase);
            }
            if (cacheEntry != null) {
                return cacheEntry.addresses;
            }
        }
        return null;
    }
    
    private static NameService createNSProvider(final String s) {
        if (s == null) {
            return null;
        }
        NameService nameService = null;
        if (s.equals("default")) {
            nameService = new NameService() {
                @Override
                public InetAddress[] lookupAllHostAddr(final String s) throws UnknownHostException {
                    return InetAddress.impl.lookupAllHostAddr(s);
                }
                
                @Override
                public String getHostByAddr(final byte[] array) throws UnknownHostException {
                    return InetAddress.impl.getHostByAddr(array);
                }
            };
        }
        else {
            try {
                nameService = AccessController.doPrivileged((PrivilegedExceptionAction<NameService>)new PrivilegedExceptionAction<NameService>() {
                    @Override
                    public NameService run() {
                        for (final NameServiceDescriptor nameServiceDescriptor : ServiceLoader.load(NameServiceDescriptor.class)) {
                            if (s.equalsIgnoreCase(nameServiceDescriptor.getType() + "," + nameServiceDescriptor.getProviderName())) {
                                try {
                                    return nameServiceDescriptor.createNameService();
                                }
                                catch (Exception ex) {
                                    ex.printStackTrace();
                                    System.err.println("Cannot create name service:" + s + ": " + ex);
                                }
                            }
                        }
                        return null;
                    }
                });
            }
            catch (PrivilegedActionException ex) {}
        }
        return nameService;
    }
    
    public static InetAddress getByAddress(String substring, final byte[] array) throws UnknownHostException {
        if (substring != null && substring.length() > 0 && substring.charAt(0) == '[' && substring.charAt(substring.length() - 1) == ']') {
            substring = substring.substring(1, substring.length() - 1);
        }
        if (array != null) {
            if (array.length == 4) {
                return new Inet4Address(substring, array);
            }
            if (array.length == 16) {
                final byte[] convertFromIPv4MappedAddress = IPAddressUtil.convertFromIPv4MappedAddress(array);
                if (convertFromIPv4MappedAddress != null) {
                    return new Inet4Address(substring, convertFromIPv4MappedAddress);
                }
                return new Inet6Address(substring, array);
            }
        }
        throw new UnknownHostException("addr is of illegal length");
    }
    
    public static InetAddress getByName(final String s) throws UnknownHostException {
        return getAllByName(s)[0];
    }
    
    private static InetAddress getByName(final String s, final InetAddress inetAddress) throws UnknownHostException {
        return getAllByName(s, inetAddress)[0];
    }
    
    public static InetAddress[] getAllByName(final String s) throws UnknownHostException {
        return getAllByName(s, null);
    }
    
    private static InetAddress[] getAllByName(String substring, final InetAddress inetAddress) throws UnknownHostException {
        if (substring == null || substring.length() == 0) {
            return new InetAddress[] { InetAddress.impl.loopbackAddress() };
        }
        boolean b = false;
        if (substring.charAt(0) == '[') {
            if (substring.length() <= 2 || substring.charAt(substring.length() - 1) != ']') {
                throw new UnknownHostException(substring + ": invalid IPv6 address");
            }
            substring = substring.substring(1, substring.length() - 1);
            b = true;
        }
        if (Character.digit(substring.charAt(0), 16) != -1 || substring.charAt(0) == ':') {
            int checkNumericZone = -1;
            String substring2 = null;
            byte[] array = IPAddressUtil.textToNumericFormatV4(substring);
            if (array == null) {
                final int index;
                if ((index = substring.indexOf("%")) != -1) {
                    checkNumericZone = checkNumericZone(substring);
                    if (checkNumericZone == -1) {
                        substring2 = substring.substring(index + 1);
                    }
                }
                if ((array = IPAddressUtil.textToNumericFormatV6(substring)) == null && substring.contains(":")) {
                    throw new UnknownHostException(substring + ": invalid IPv6 address");
                }
            }
            else if (b) {
                throw new UnknownHostException("[" + substring + "]");
            }
            final InetAddress[] array2 = { null };
            if (array != null) {
                if (array.length == 4) {
                    array2[0] = new Inet4Address(null, array);
                }
                else if (substring2 != null) {
                    array2[0] = new Inet6Address(null, array, substring2);
                }
                else {
                    array2[0] = new Inet6Address(null, array, checkNumericZone);
                }
                return array2;
            }
        }
        else if (b) {
            throw new UnknownHostException("[" + substring + "]");
        }
        return getAllByName0(substring, inetAddress, true);
    }
    
    public static InetAddress getLoopbackAddress() {
        return InetAddress.impl.loopbackAddress();
    }
    
    private static int checkNumericZone(final String s) throws UnknownHostException {
        final int index = s.indexOf(37);
        final int length = s.length();
        int n = 0;
        if (index == -1) {
            return -1;
        }
        int i = index + 1;
        while (i < length) {
            final char char1 = s.charAt(i);
            if (char1 == ']') {
                if (i == index + 1) {
                    return -1;
                }
                break;
            }
            else {
                final int digit;
                if ((digit = Character.digit(char1, 10)) < 0) {
                    return -1;
                }
                n = n * 10 + digit;
                ++i;
            }
        }
        return n;
    }
    
    private static InetAddress[] getAllByName0(final String s) throws UnknownHostException {
        return getAllByName0(s, true);
    }
    
    static InetAddress[] getAllByName0(final String s, final boolean b) throws UnknownHostException {
        return getAllByName0(s, null, b);
    }
    
    private static InetAddress[] getAllByName0(final String s, final InetAddress inetAddress, final boolean b) throws UnknownHostException {
        if (b) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkConnect(s, -1);
            }
        }
        InetAddress[] array = getCachedAddresses(s);
        if (array == null) {
            array = getAddressesFromNameService(s, inetAddress);
        }
        if (array == InetAddress.unknown_array) {
            throw new UnknownHostException(s);
        }
        return array.clone();
    }
    
    private static InetAddress[] getAddressesFromNameService(final String s, final InetAddress inetAddress) throws UnknownHostException {
        boolean b = false;
        UnknownHostException ex = null;
        InetAddress[] array;
        if ((array = checkLookupTable(s)) == null) {
            try {
                for (final NameService nameService : InetAddress.nameServices) {
                    try {
                        array = nameService.lookupAllHostAddr(s);
                        b = true;
                    }
                    catch (UnknownHostException ex2) {
                        if (!s.equalsIgnoreCase("localhost")) {
                            array = InetAddress.unknown_array;
                            b = false;
                            ex = ex2;
                            continue;
                        }
                        array = new InetAddress[] { InetAddress.impl.loopbackAddress() };
                        b = true;
                    }
                    break;
                }
                if (inetAddress != null && array.length > 1 && !array[0].equals(inetAddress)) {
                    int n;
                    for (n = 1; n < array.length && !array[n].equals(inetAddress); ++n) {}
                    if (n < array.length) {
                        InetAddress inetAddress2 = inetAddress;
                        for (int i = 0; i < n; ++i) {
                            final InetAddress inetAddress3 = array[i];
                            array[i] = inetAddress2;
                            inetAddress2 = inetAddress3;
                        }
                        array[n] = inetAddress2;
                    }
                }
                cacheAddresses(s, array, b);
                if (!b && ex != null) {
                    throw ex;
                }
            }
            finally {
                updateLookupTable(s);
            }
        }
        return array;
    }
    
    private static InetAddress[] checkLookupTable(final String s) {
        synchronized (InetAddress.lookupTable) {
            if (!InetAddress.lookupTable.containsKey(s)) {
                InetAddress.lookupTable.put(s, null);
                return null;
            }
            while (InetAddress.lookupTable.containsKey(s)) {
                try {
                    InetAddress.lookupTable.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        final InetAddress[] cachedAddresses = getCachedAddresses(s);
        if (cachedAddresses == null) {
            synchronized (InetAddress.lookupTable) {
                InetAddress.lookupTable.put(s, null);
                return null;
            }
        }
        return cachedAddresses;
    }
    
    private static void updateLookupTable(final String s) {
        synchronized (InetAddress.lookupTable) {
            InetAddress.lookupTable.remove(s);
            InetAddress.lookupTable.notifyAll();
        }
    }
    
    public static InetAddress getByAddress(final byte[] array) throws UnknownHostException {
        return getByAddress(null, array);
    }
    
    public static InetAddress getLocalHost() throws UnknownHostException {
        final SecurityManager securityManager = System.getSecurityManager();
        try {
            final String localHostName = InetAddress.impl.getLocalHostName();
            if (securityManager != null) {
                securityManager.checkConnect(localHostName, -1);
            }
            if (localHostName.equals("localhost")) {
                return InetAddress.impl.loopbackAddress();
            }
            InetAddress cachedLocalHost = null;
            synchronized (InetAddress.cacheLock) {
                final long currentTimeMillis = System.currentTimeMillis();
                if (InetAddress.cachedLocalHost != null) {
                    if (currentTimeMillis - InetAddress.cacheTime < 5000L) {
                        cachedLocalHost = InetAddress.cachedLocalHost;
                    }
                    else {
                        InetAddress.cachedLocalHost = null;
                    }
                }
                if (cachedLocalHost == null) {
                    InetAddress[] addressesFromNameService;
                    try {
                        addressesFromNameService = getAddressesFromNameService(localHostName, null);
                    }
                    catch (UnknownHostException ex2) {
                        final UnknownHostException ex = new UnknownHostException(localHostName + ": " + ex2.getMessage());
                        ex.initCause(ex2);
                        throw ex;
                    }
                    InetAddress.cachedLocalHost = addressesFromNameService[0];
                    InetAddress.cacheTime = currentTimeMillis;
                    cachedLocalHost = addressesFromNameService[0];
                }
            }
            return cachedLocalHost;
        }
        catch (SecurityException ex3) {
            return InetAddress.impl.loopbackAddress();
        }
    }
    
    private static native void init();
    
    static InetAddress anyLocalAddress() {
        return InetAddress.impl.anyLocalAddress();
    }
    
    static InetAddressImpl loadImpl(final String s) {
        Object o = null;
        final String s2 = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("impl.prefix", ""));
        try {
            o = Class.forName("java.net." + s2 + s).newInstance();
        }
        catch (ClassNotFoundException ex) {
            System.err.println("Class not found: java.net." + s2 + s + ":\ncheck impl.prefix property in your properties file.");
        }
        catch (InstantiationException ex2) {
            System.err.println("Could not instantiate: java.net." + s2 + s + ":\ncheck impl.prefix property in your properties file.");
        }
        catch (IllegalAccessException ex3) {
            System.err.println("Cannot access class: java.net." + s2 + s + ":\ncheck impl.prefix property in your properties file.");
        }
        if (o == null) {
            try {
                o = Class.forName(s).newInstance();
            }
            catch (Exception ex4) {
                throw new Error("System property impl.prefix incorrect");
            }
        }
        return (InetAddressImpl)o;
    }
    
    private void readObjectNoData(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        if (this.getClass().getClassLoader() != null) {
            throw new SecurityException("invalid address type");
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        if (this.getClass().getClassLoader() != null) {
            throw new SecurityException("invalid address type");
        }
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final String s = (String)fields.get("hostName", null);
        final int value = fields.get("address", 0);
        final int value2 = fields.get("family", 0);
        if (value2 != 1 && value2 != 2) {
            throw new InvalidObjectException("invalid address family type: " + value2);
        }
        InetAddress.UNSAFE.putObject(this, InetAddress.FIELDS_OFFSET, new InetAddressHolder(s, value, value2));
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.getClass().getClassLoader() != null) {
            throw new SecurityException("invalid address type");
        }
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("hostName", this.holder().getHostName());
        putFields.put("address", this.holder().getAddress());
        putFields.put("family", this.holder().getFamily());
        objectOutputStream.writeFields();
    }
    
    static {
        InetAddress.preferIPv6Address = false;
        InetAddress.nameServices = null;
        InetAddress.preferIPv6Address = AccessController.doPrivileged((PrivilegedAction<Boolean>)new GetBooleanAction("java.net.preferIPv6Addresses"));
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                System.loadLibrary("net");
                return null;
            }
        });
        init();
        InetAddress.addressCache = new Cache(Cache.Type.Positive);
        InetAddress.negativeCache = new Cache(Cache.Type.Negative);
        InetAddress.addressCacheInit = false;
        lookupTable = new HashMap<String, Void>();
        InetAddress.impl = InetAddressImplFactory.create();
        final String s = "sun.net.spi.nameservice.provider.";
        int n = 1;
        InetAddress.nameServices = new ArrayList<NameService>();
        for (String s2 = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction(s + n)); s2 != null; s2 = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction(s + n))) {
            final NameService nsProvider = createNSProvider(s2);
            if (nsProvider != null) {
                InetAddress.nameServices.add(nsProvider);
            }
            ++n;
        }
        if (InetAddress.nameServices.size() == 0) {
            InetAddress.nameServices.add(createNSProvider("default"));
        }
        InetAddress.cachedLocalHost = null;
        InetAddress.cacheTime = 0L;
        cacheLock = new Object();
        try {
            final Unsafe unsafe = Unsafe.getUnsafe();
            FIELDS_OFFSET = unsafe.objectFieldOffset(InetAddress.class.getDeclaredField("holder"));
            UNSAFE = unsafe;
        }
        catch (ReflectiveOperationException ex) {
            throw new Error(ex);
        }
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("hostName", String.class), new ObjectStreamField("address", Integer.TYPE), new ObjectStreamField("family", Integer.TYPE) };
    }
    
    static final class Cache
    {
        private LinkedHashMap<String, CacheEntry> cache;
        private Type type;
        
        public Cache(final Type type) {
            this.type = type;
            this.cache = new LinkedHashMap<String, CacheEntry>();
        }
        
        private int getPolicy() {
            if (this.type == Type.Positive) {
                return InetAddressCachePolicy.get();
            }
            return InetAddressCachePolicy.getNegative();
        }
        
        public Cache put(final String s, final InetAddress[] array) {
            final int policy = this.getPolicy();
            if (policy == 0) {
                return this;
            }
            if (policy != -1) {
                final LinkedList<String> list = new LinkedList<String>();
                final long currentTimeMillis = System.currentTimeMillis();
                for (final String s2 : this.cache.keySet()) {
                    final CacheEntry cacheEntry = this.cache.get(s2);
                    if (cacheEntry.expiration < 0L || cacheEntry.expiration >= currentTimeMillis) {
                        break;
                    }
                    list.add(s2);
                }
                final Iterator<Object> iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    this.cache.remove(iterator2.next());
                }
            }
            long n;
            if (policy == -1) {
                n = -1L;
            }
            else {
                n = System.currentTimeMillis() + policy * 1000;
            }
            this.cache.put(s, new CacheEntry(array, n));
            return this;
        }
        
        public CacheEntry get(final String s) {
            final int policy = this.getPolicy();
            if (policy == 0) {
                return null;
            }
            CacheEntry cacheEntry = this.cache.get(s);
            if (cacheEntry != null && policy != -1 && cacheEntry.expiration >= 0L && cacheEntry.expiration < System.currentTimeMillis()) {
                this.cache.remove(s);
                cacheEntry = null;
            }
            return cacheEntry;
        }
        
        enum Type
        {
            Positive, 
            Negative;
        }
    }
    
    static final class CacheEntry
    {
        InetAddress[] addresses;
        long expiration;
        
        CacheEntry(final InetAddress[] addresses, final long expiration) {
            this.addresses = addresses;
            this.expiration = expiration;
        }
    }
    
    static class InetAddressHolder
    {
        String originalHostName;
        String hostName;
        int address;
        int family;
        
        InetAddressHolder() {
        }
        
        InetAddressHolder(final String s, final int address, final int family) {
            this.originalHostName = s;
            this.hostName = s;
            this.address = address;
            this.family = family;
        }
        
        void init(final String s, final int family) {
            this.originalHostName = s;
            this.hostName = s;
            if (family != -1) {
                this.family = family;
            }
        }
        
        String getHostName() {
            return this.hostName;
        }
        
        String getOriginalHostName() {
            return this.originalHostName;
        }
        
        int getAddress() {
            return this.address;
        }
        
        int getFamily() {
            return this.family;
        }
    }
}
