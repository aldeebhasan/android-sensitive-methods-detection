package java.net;

import sun.misc.*;
import java.io.*;
import java.util.*;

public final class Inet6Address extends InetAddress
{
    static final int INADDRSZ = 16;
    private transient int cached_scope_id;
    private final transient Inet6AddressHolder holder6;
    private static final long serialVersionUID = 6880410070516793377L;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long FIELDS_OFFSET;
    private static final Unsafe UNSAFE;
    private static final int INT16SZ = 2;
    
    Inet6Address() {
        this.holder.init(null, 2);
        this.holder6 = new Inet6AddressHolder();
    }
    
    Inet6Address(final String s, final byte[] array, final int n) {
        this.holder.init(s, 2);
        (this.holder6 = new Inet6AddressHolder()).init(array, n);
    }
    
    Inet6Address(final String s, final byte[] array) {
        this.holder6 = new Inet6AddressHolder();
        try {
            this.initif(s, array, null);
        }
        catch (UnknownHostException ex) {}
    }
    
    Inet6Address(final String s, final byte[] array, final NetworkInterface networkInterface) throws UnknownHostException {
        this.holder6 = new Inet6AddressHolder();
        this.initif(s, array, networkInterface);
    }
    
    Inet6Address(final String s, final byte[] array, final String s2) throws UnknownHostException {
        this.holder6 = new Inet6AddressHolder();
        this.initstr(s, array, s2);
    }
    
    public static Inet6Address getByAddress(String substring, final byte[] array, final NetworkInterface networkInterface) throws UnknownHostException {
        if (substring != null && substring.length() > 0 && substring.charAt(0) == '[' && substring.charAt(substring.length() - 1) == ']') {
            substring = substring.substring(1, substring.length() - 1);
        }
        if (array != null && array.length == 16) {
            return new Inet6Address(substring, array, networkInterface);
        }
        throw new UnknownHostException("addr is of illegal length");
    }
    
    public static Inet6Address getByAddress(String substring, final byte[] array, final int n) throws UnknownHostException {
        if (substring != null && substring.length() > 0 && substring.charAt(0) == '[' && substring.charAt(substring.length() - 1) == ']') {
            substring = substring.substring(1, substring.length() - 1);
        }
        if (array != null && array.length == 16) {
            return new Inet6Address(substring, array, n);
        }
        throw new UnknownHostException("addr is of illegal length");
    }
    
    private void initstr(final String s, final byte[] array, final String s2) throws UnknownHostException {
        try {
            final NetworkInterface byName = NetworkInterface.getByName(s2);
            if (byName == null) {
                throw new UnknownHostException("no such interface " + s2);
            }
            this.initif(s, array, byName);
        }
        catch (SocketException ex) {
            throw new UnknownHostException("SocketException thrown" + s2);
        }
    }
    
    private void initif(final String s, final byte[] array, final NetworkInterface networkInterface) throws UnknownHostException {
        int n = -1;
        this.holder6.init(array, networkInterface);
        if (array.length == 16) {
            n = 2;
        }
        this.holder.init(s, n);
    }
    
    private static boolean isDifferentLocalAddressType(final byte[] array, final byte[] array2) {
        return (!isLinkLocalAddress(array) || isLinkLocalAddress(array2)) && (!isSiteLocalAddress(array) || isSiteLocalAddress(array2));
    }
    
    private static int deriveNumericScope(final byte[] array, final NetworkInterface networkInterface) throws UnknownHostException {
        final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        while (inetAddresses.hasMoreElements()) {
            final InetAddress inetAddress = inetAddresses.nextElement();
            if (!(inetAddress instanceof Inet6Address)) {
                continue;
            }
            final Inet6Address inet6Address = (Inet6Address)inetAddress;
            if (!isDifferentLocalAddressType(array, inet6Address.getAddress())) {
                continue;
            }
            return inet6Address.getScopeId();
        }
        throw new UnknownHostException("no scope_id found");
    }
    
    private int deriveNumericScope(final String s) throws UnknownHostException {
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        }
        catch (SocketException ex) {
            throw new UnknownHostException("could not enumerate local network interfaces");
        }
        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.getName().equals(s)) {
                return deriveNumericScope(this.holder6.ipaddress, networkInterface);
            }
        }
        throw new UnknownHostException("No matching address found for interface : " + s);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        NetworkInterface byName = null;
        if (this.getClass().getClassLoader() != null) {
            throw new SecurityException("invalid address type");
        }
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final byte[] array = (byte[])fields.get("ipaddress", null);
        int n = fields.get("scope_id", -1);
        boolean value = fields.get("scope_id_set", false);
        boolean value2 = fields.get("scope_ifname_set", false);
        final String s = (String)fields.get("ifname", null);
        if (s != null && !"".equals(s)) {
            try {
                byName = NetworkInterface.getByName(s);
                if (byName == null) {
                    value = false;
                    value2 = false;
                    n = 0;
                }
                else {
                    value2 = true;
                    try {
                        n = deriveNumericScope(array, byName);
                    }
                    catch (UnknownHostException ex) {}
                }
            }
            catch (SocketException ex2) {}
        }
        final byte[] array2 = array.clone();
        if (array2.length != 16) {
            throw new InvalidObjectException("invalid address length: " + array2.length);
        }
        if (this.holder.getFamily() != 2) {
            throw new InvalidObjectException("invalid address family type");
        }
        Inet6Address.UNSAFE.putObject(this, Inet6Address.FIELDS_OFFSET, new Inet6AddressHolder(array2, n, value, byName, value2));
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        Object name = null;
        if (this.holder6.scope_ifname != null) {
            name = this.holder6.scope_ifname.getName();
            this.holder6.scope_ifname_set = true;
        }
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("ipaddress", this.holder6.ipaddress);
        putFields.put("scope_id", this.holder6.scope_id);
        putFields.put("scope_id_set", this.holder6.scope_id_set);
        putFields.put("scope_ifname_set", this.holder6.scope_ifname_set);
        putFields.put("ifname", name);
        objectOutputStream.writeFields();
    }
    
    @Override
    public boolean isMulticastAddress() {
        return this.holder6.isMulticastAddress();
    }
    
    @Override
    public boolean isAnyLocalAddress() {
        return this.holder6.isAnyLocalAddress();
    }
    
    @Override
    public boolean isLoopbackAddress() {
        return this.holder6.isLoopbackAddress();
    }
    
    @Override
    public boolean isLinkLocalAddress() {
        return this.holder6.isLinkLocalAddress();
    }
    
    static boolean isLinkLocalAddress(final byte[] array) {
        return (array[0] & 0xFF) == 0xFE && (array[1] & 0xC0) == 0x80;
    }
    
    @Override
    public boolean isSiteLocalAddress() {
        return this.holder6.isSiteLocalAddress();
    }
    
    static boolean isSiteLocalAddress(final byte[] array) {
        return (array[0] & 0xFF) == 0xFE && (array[1] & 0xC0) == 0xC0;
    }
    
    @Override
    public boolean isMCGlobal() {
        return this.holder6.isMCGlobal();
    }
    
    @Override
    public boolean isMCNodeLocal() {
        return this.holder6.isMCNodeLocal();
    }
    
    @Override
    public boolean isMCLinkLocal() {
        return this.holder6.isMCLinkLocal();
    }
    
    @Override
    public boolean isMCSiteLocal() {
        return this.holder6.isMCSiteLocal();
    }
    
    @Override
    public boolean isMCOrgLocal() {
        return this.holder6.isMCOrgLocal();
    }
    
    @Override
    public byte[] getAddress() {
        return this.holder6.ipaddress.clone();
    }
    
    public int getScopeId() {
        return this.holder6.scope_id;
    }
    
    public NetworkInterface getScopedInterface() {
        return this.holder6.scope_ifname;
    }
    
    @Override
    public String getHostAddress() {
        return this.holder6.getHostAddress();
    }
    
    @Override
    public int hashCode() {
        return this.holder6.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof Inet6Address && this.holder6.equals(((Inet6Address)o).holder6);
    }
    
    public boolean isIPv4CompatibleAddress() {
        return this.holder6.isIPv4CompatibleAddress();
    }
    
    static String numericToTextFormat(final byte[] array) {
        final StringBuilder sb = new StringBuilder(39);
        for (int i = 0; i < 8; ++i) {
            sb.append(Integer.toHexString((array[i << 1] << 8 & 0xFF00) | (array[(i << 1) + 1] & 0xFF)));
            if (i < 7) {
                sb.append(":");
            }
        }
        return sb.toString();
    }
    
    private static native void init();
    
    static {
        init();
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("ipaddress", byte[].class), new ObjectStreamField("scope_id", Integer.TYPE), new ObjectStreamField("scope_id_set", Boolean.TYPE), new ObjectStreamField("scope_ifname_set", Boolean.TYPE), new ObjectStreamField("ifname", String.class) };
        try {
            final Unsafe unsafe = Unsafe.getUnsafe();
            FIELDS_OFFSET = unsafe.objectFieldOffset(Inet6Address.class.getDeclaredField("holder6"));
            UNSAFE = unsafe;
        }
        catch (ReflectiveOperationException ex) {
            throw new Error(ex);
        }
    }
    
    private class Inet6AddressHolder
    {
        byte[] ipaddress;
        int scope_id;
        boolean scope_id_set;
        NetworkInterface scope_ifname;
        boolean scope_ifname_set;
        
        private Inet6AddressHolder() {
            this.ipaddress = new byte[16];
        }
        
        private Inet6AddressHolder(final byte[] ipaddress, final int scope_id, final boolean scope_id_set, final NetworkInterface scope_ifname, final boolean scope_ifname_set) {
            this.ipaddress = ipaddress;
            this.scope_id = scope_id;
            this.scope_id_set = scope_id_set;
            this.scope_ifname_set = scope_ifname_set;
            this.scope_ifname = scope_ifname;
        }
        
        void setAddr(final byte[] array) {
            if (array.length == 16) {
                System.arraycopy(array, 0, this.ipaddress, 0, 16);
            }
        }
        
        void init(final byte[] addr, final int scope_id) {
            this.setAddr(addr);
            if (scope_id >= 0) {
                this.scope_id = scope_id;
                this.scope_id_set = true;
            }
        }
        
        void init(final byte[] addr, final NetworkInterface scope_ifname) throws UnknownHostException {
            this.setAddr(addr);
            if (scope_ifname != null) {
                this.scope_id = deriveNumericScope(this.ipaddress, scope_ifname);
                this.scope_id_set = true;
                this.scope_ifname = scope_ifname;
                this.scope_ifname_set = true;
            }
        }
        
        String getHostAddress() {
            String s = Inet6Address.numericToTextFormat(this.ipaddress);
            if (this.scope_ifname != null) {
                s = s + "%" + this.scope_ifname.getName();
            }
            else if (this.scope_id_set) {
                s = s + "%" + this.scope_id;
            }
            return s;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Inet6AddressHolder && Arrays.equals(this.ipaddress, ((Inet6AddressHolder)o).ipaddress);
        }
        
        @Override
        public int hashCode() {
            if (this.ipaddress != null) {
                int n = 0;
                int i = 0;
                while (i < 16) {
                    int n2 = 0;
                    int n3 = 0;
                    while (n2 < 4 && i < 16) {
                        n3 = (n3 << 8) + this.ipaddress[i];
                        ++n2;
                        ++i;
                    }
                    n += n3;
                }
                return n;
            }
            return 0;
        }
        
        boolean isIPv4CompatibleAddress() {
            return this.ipaddress[0] == 0 && this.ipaddress[1] == 0 && this.ipaddress[2] == 0 && this.ipaddress[3] == 0 && this.ipaddress[4] == 0 && this.ipaddress[5] == 0 && this.ipaddress[6] == 0 && this.ipaddress[7] == 0 && this.ipaddress[8] == 0 && this.ipaddress[9] == 0 && this.ipaddress[10] == 0 && this.ipaddress[11] == 0;
        }
        
        boolean isMulticastAddress() {
            return (this.ipaddress[0] & 0xFF) == 0xFF;
        }
        
        boolean isAnyLocalAddress() {
            byte b = 0;
            for (int i = 0; i < 16; ++i) {
                b |= this.ipaddress[i];
            }
            return b == 0;
        }
        
        boolean isLoopbackAddress() {
            byte b = 0;
            for (int i = 0; i < 15; ++i) {
                b |= this.ipaddress[i];
            }
            return b == 0 && this.ipaddress[15] == 1;
        }
        
        boolean isLinkLocalAddress() {
            return (this.ipaddress[0] & 0xFF) == 0xFE && (this.ipaddress[1] & 0xC0) == 0x80;
        }
        
        boolean isSiteLocalAddress() {
            return (this.ipaddress[0] & 0xFF) == 0xFE && (this.ipaddress[1] & 0xC0) == 0xC0;
        }
        
        boolean isMCGlobal() {
            return (this.ipaddress[0] & 0xFF) == 0xFF && (this.ipaddress[1] & 0xF) == 0xE;
        }
        
        boolean isMCNodeLocal() {
            return (this.ipaddress[0] & 0xFF) == 0xFF && (this.ipaddress[1] & 0xF) == 0x1;
        }
        
        boolean isMCLinkLocal() {
            return (this.ipaddress[0] & 0xFF) == 0xFF && (this.ipaddress[1] & 0xF) == 0x2;
        }
        
        boolean isMCSiteLocal() {
            return (this.ipaddress[0] & 0xFF) == 0xFF && (this.ipaddress[1] & 0xF) == 0x5;
        }
        
        boolean isMCOrgLocal() {
            return (this.ipaddress[0] & 0xFF) == 0xFF && (this.ipaddress[1] & 0xF) == 0x8;
        }
    }
}
