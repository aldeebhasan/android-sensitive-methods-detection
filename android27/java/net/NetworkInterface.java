package java.net;

import java.util.*;
import java.security.*;

public final class NetworkInterface
{
    private String name;
    private String displayName;
    private int index;
    private InetAddress[] addrs;
    private InterfaceAddress[] bindings;
    private NetworkInterface[] childs;
    private NetworkInterface parent;
    private boolean virtual;
    private static final NetworkInterface defaultInterface;
    private static final int defaultIndex;
    
    NetworkInterface() {
        this.parent = null;
        this.virtual = false;
    }
    
    NetworkInterface(final String name, final int index, final InetAddress[] addrs) {
        this.parent = null;
        this.virtual = false;
        this.name = name;
        this.index = index;
        this.addrs = addrs;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Enumeration<InetAddress> getInetAddresses() {
        class checkedAddresses implements Enumeration<InetAddress>
        {
            private int i;
            private int count;
            private InetAddress[] local_addrs;
            
            checkedAddresses() {
                this.i = 0;
                this.count = 0;
                this.local_addrs = new InetAddress[NetworkInterface.this.addrs.length];
                boolean b = true;
                final SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    try {
                        securityManager.checkPermission(new NetPermission("getNetworkInformation"));
                    }
                    catch (SecurityException ex) {
                        b = false;
                    }
                }
                for (int i = 0; i < NetworkInterface.this.addrs.length; ++i) {
                    try {
                        if (securityManager != null && !b) {
                            securityManager.checkConnect(NetworkInterface.this.addrs[i].getHostAddress(), -1);
                        }
                        this.local_addrs[this.count++] = NetworkInterface.this.addrs[i];
                    }
                    catch (SecurityException ex2) {}
                }
            }
            
            @Override
            public InetAddress nextElement() {
                if (this.i < this.count) {
                    return this.local_addrs[this.i++];
                }
                throw new NoSuchElementException();
            }
            
            @Override
            public boolean hasMoreElements() {
                return this.i < this.count;
            }
        }
        return new checkedAddresses();
    }
    
    public List<InterfaceAddress> getInterfaceAddresses() {
        final ArrayList<InterfaceAddress> list = new ArrayList<InterfaceAddress>(1);
        final SecurityManager securityManager = System.getSecurityManager();
        for (int i = 0; i < this.bindings.length; ++i) {
            try {
                if (securityManager != null) {
                    securityManager.checkConnect(this.bindings[i].getAddress().getHostAddress(), -1);
                }
                list.add(this.bindings[i]);
            }
            catch (SecurityException ex) {}
        }
        return list;
    }
    
    public Enumeration<NetworkInterface> getSubInterfaces() {
        class subIFs implements Enumeration<NetworkInterface>
        {
            private int i;
            
            subIFs() {
                this.i = 0;
            }
            
            @Override
            public NetworkInterface nextElement() {
                if (this.i < NetworkInterface.this.childs.length) {
                    return NetworkInterface.this.childs[this.i++];
                }
                throw new NoSuchElementException();
            }
            
            @Override
            public boolean hasMoreElements() {
                return this.i < NetworkInterface.this.childs.length;
            }
        }
        return new subIFs();
    }
    
    public NetworkInterface getParent() {
        return this.parent;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getDisplayName() {
        return "".equals(this.displayName) ? null : this.displayName;
    }
    
    public static NetworkInterface getByName(final String s) throws SocketException {
        if (s == null) {
            throw new NullPointerException();
        }
        return getByName0(s);
    }
    
    public static NetworkInterface getByIndex(final int n) throws SocketException {
        if (n < 0) {
            throw new IllegalArgumentException("Interface index can't be negative");
        }
        return getByIndex0(n);
    }
    
    public static NetworkInterface getByInetAddress(final InetAddress inetAddress) throws SocketException {
        if (inetAddress == null) {
            throw new NullPointerException();
        }
        if (inetAddress instanceof Inet4Address) {
            final Inet4Address inet4Address = (Inet4Address)inetAddress;
            if (inet4Address.holder.family != 1) {
                throw new IllegalArgumentException("invalid family type: " + inet4Address.holder.family);
            }
        }
        else {
            if (!(inetAddress instanceof Inet6Address)) {
                throw new IllegalArgumentException("invalid address type: " + inetAddress);
            }
            final Inet6Address inet6Address = (Inet6Address)inetAddress;
            if (inet6Address.holder.family != 2) {
                throw new IllegalArgumentException("invalid family type: " + inet6Address.holder.family);
            }
        }
        return getByInetAddress0(inetAddress);
    }
    
    public static Enumeration<NetworkInterface> getNetworkInterfaces() throws SocketException {
        final NetworkInterface[] all = getAll();
        if (all == null) {
            return null;
        }
        return new Enumeration<NetworkInterface>() {
            private int i = 0;
            
            @Override
            public NetworkInterface nextElement() {
                if (all != null && this.i < all.length) {
                    return all[this.i++];
                }
                throw new NoSuchElementException();
            }
            
            @Override
            public boolean hasMoreElements() {
                return all != null && this.i < all.length;
            }
        };
    }
    
    private static native NetworkInterface[] getAll() throws SocketException;
    
    private static native NetworkInterface getByName0(final String p0) throws SocketException;
    
    private static native NetworkInterface getByIndex0(final int p0) throws SocketException;
    
    private static native NetworkInterface getByInetAddress0(final InetAddress p0) throws SocketException;
    
    public boolean isUp() throws SocketException {
        return isUp0(this.name, this.index);
    }
    
    public boolean isLoopback() throws SocketException {
        return isLoopback0(this.name, this.index);
    }
    
    public boolean isPointToPoint() throws SocketException {
        return isP2P0(this.name, this.index);
    }
    
    public boolean supportsMulticast() throws SocketException {
        return supportsMulticast0(this.name, this.index);
    }
    
    public byte[] getHardwareAddress() throws SocketException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkPermission(new NetPermission("getNetworkInformation"));
            }
            catch (SecurityException ex) {
                if (!this.getInetAddresses().hasMoreElements()) {
                    return null;
                }
            }
        }
        for (final InetAddress inetAddress : this.addrs) {
            if (inetAddress instanceof Inet4Address) {
                return getMacAddr0(((Inet4Address)inetAddress).getAddress(), this.name, this.index);
            }
        }
        return getMacAddr0(null, this.name, this.index);
    }
    
    public int getMTU() throws SocketException {
        return getMTU0(this.name, this.index);
    }
    
    public boolean isVirtual() {
        return this.virtual;
    }
    
    private static native boolean isUp0(final String p0, final int p1) throws SocketException;
    
    private static native boolean isLoopback0(final String p0, final int p1) throws SocketException;
    
    private static native boolean supportsMulticast0(final String p0, final int p1) throws SocketException;
    
    private static native boolean isP2P0(final String p0, final int p1) throws SocketException;
    
    private static native byte[] getMacAddr0(final byte[] p0, final String p1, final int p2) throws SocketException;
    
    private static native int getMTU0(final String p0, final int p1) throws SocketException;
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NetworkInterface)) {
            return false;
        }
        final NetworkInterface networkInterface = (NetworkInterface)o;
        if (this.name != null) {
            if (!this.name.equals(networkInterface.name)) {
                return false;
            }
        }
        else if (networkInterface.name != null) {
            return false;
        }
        if (this.addrs == null) {
            return networkInterface.addrs == null;
        }
        if (networkInterface.addrs == null) {
            return false;
        }
        if (this.addrs.length != networkInterface.addrs.length) {
            return false;
        }
        final InetAddress[] addrs = networkInterface.addrs;
        for (int length = addrs.length, i = 0; i < length; ++i) {
            boolean b = false;
            for (int j = 0; j < length; ++j) {
                if (this.addrs[i].equals(addrs[j])) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return (this.name == null) ? 0 : this.name.hashCode();
    }
    
    @Override
    public String toString() {
        String s = "name:" + ((this.name == null) ? "null" : this.name);
        if (this.displayName != null) {
            s = s + " (" + this.displayName + ")";
        }
        return s;
    }
    
    private static native void init();
    
    static NetworkInterface getDefault() {
        return NetworkInterface.defaultInterface;
    }
    
    static {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                System.loadLibrary("net");
                return null;
            }
        });
        init();
        defaultInterface = DefaultInterface.getDefault();
        if (NetworkInterface.defaultInterface != null) {
            defaultIndex = NetworkInterface.defaultInterface.getIndex();
        }
        else {
            defaultIndex = 0;
        }
    }
}
