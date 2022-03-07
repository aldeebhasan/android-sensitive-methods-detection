package java.net;

import java.io.*;
import java.util.*;
import java.security.*;

public class MulticastSocket extends DatagramSocket
{
    private boolean interfaceSet;
    private Object ttlLock;
    private Object infLock;
    private InetAddress infAddress;
    private static final NetworkInterface defNetIntf;
    
    public MulticastSocket() throws IOException {
        this(new InetSocketAddress(0));
    }
    
    public MulticastSocket(final int n) throws IOException {
        this(new InetSocketAddress(n));
    }
    
    public MulticastSocket(final SocketAddress socketAddress) throws IOException {
        super((SocketAddress)null);
        this.ttlLock = new Object();
        this.infLock = new Object();
        this.infAddress = null;
        this.setReuseAddress(true);
        if (socketAddress != null) {
            try {
                this.bind(socketAddress);
            }
            finally {
                if (!this.isBound()) {
                    this.close();
                }
            }
        }
    }
    
    @Deprecated
    public void setTTL(final byte ttl) throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setTTL(ttl);
    }
    
    public void setTimeToLive(final int timeToLive) throws IOException {
        if (timeToLive < 0 || timeToLive > 255) {
            throw new IllegalArgumentException("ttl out of range");
        }
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setTimeToLive(timeToLive);
    }
    
    @Deprecated
    public byte getTTL() throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return this.getImpl().getTTL();
    }
    
    public int getTimeToLive() throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return this.getImpl().getTimeToLive();
    }
    
    public void joinGroup(final InetAddress inetAddress) throws IOException {
        synchronized (this.infLock) {
            if (!this.interfaceSet && MulticastSocket.defNetIntf != null) {
                if (inetAddress == null) {
                    throw new NullPointerException("Multicast address is null");
                }
                this.joinGroup(new InetSocketAddress(inetAddress, 0), MulticastSocket.defNetIntf);
                return;
            }
        }
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.checkAddress(inetAddress, "joinGroup");
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkMulticast(inetAddress);
        }
        if (!inetAddress.isMulticastAddress()) {
            throw new SocketException("Not a multicast address");
        }
        final NetworkInterface default1 = NetworkInterface.getDefault();
        if (!this.interfaceSet && default1 != null) {
            this.setNetworkInterface(default1);
        }
        this.getImpl().join(inetAddress);
    }
    
    public void leaveGroup(final InetAddress inetAddress) throws IOException {
        synchronized (this.infLock) {
            if (!this.interfaceSet && MulticastSocket.defNetIntf != null) {
                if (inetAddress == null) {
                    throw new NullPointerException("Multicast address is null");
                }
                this.leaveGroup(new InetSocketAddress(inetAddress, 0), MulticastSocket.defNetIntf);
                return;
            }
        }
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.checkAddress(inetAddress, "leaveGroup");
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkMulticast(inetAddress);
        }
        if (!inetAddress.isMulticastAddress()) {
            throw new SocketException("Not a multicast address");
        }
        this.getImpl().leave(inetAddress);
    }
    
    public void joinGroup(final SocketAddress socketAddress, final NetworkInterface networkInterface) throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (socketAddress == null || !(socketAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Unsupported address type");
        }
        if (this.oldImpl) {
            throw new UnsupportedOperationException();
        }
        this.checkAddress(((InetSocketAddress)socketAddress).getAddress(), "joinGroup");
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkMulticast(((InetSocketAddress)socketAddress).getAddress());
        }
        if (!((InetSocketAddress)socketAddress).getAddress().isMulticastAddress()) {
            throw new SocketException("Not a multicast address");
        }
        this.getImpl().joinGroup(socketAddress, networkInterface);
    }
    
    public void leaveGroup(final SocketAddress socketAddress, final NetworkInterface networkInterface) throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (socketAddress == null || !(socketAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Unsupported address type");
        }
        if (this.oldImpl) {
            throw new UnsupportedOperationException();
        }
        this.checkAddress(((InetSocketAddress)socketAddress).getAddress(), "leaveGroup");
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkMulticast(((InetSocketAddress)socketAddress).getAddress());
        }
        if (!((InetSocketAddress)socketAddress).getAddress().isMulticastAddress()) {
            throw new SocketException("Not a multicast address");
        }
        this.getImpl().leaveGroup(socketAddress, networkInterface);
    }
    
    public void setInterface(final InetAddress infAddress) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.checkAddress(infAddress, "setInterface");
        synchronized (this.infLock) {
            this.getImpl().setOption(16, infAddress);
            this.infAddress = infAddress;
            this.interfaceSet = true;
        }
    }
    
    public InetAddress getInterface() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        synchronized (this.infLock) {
            final InetAddress inetAddress = (InetAddress)this.getImpl().getOption(16);
            if (this.infAddress == null) {
                return inetAddress;
            }
            if (inetAddress.equals(this.infAddress)) {
                return inetAddress;
            }
            try {
                final Enumeration<InetAddress> inetAddresses = NetworkInterface.getByInetAddress(inetAddress).getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    if (inetAddresses.nextElement().equals(this.infAddress)) {
                        return this.infAddress;
                    }
                }
                this.infAddress = null;
                return inetAddress;
            }
            catch (Exception ex) {
                return inetAddress;
            }
        }
    }
    
    public void setNetworkInterface(final NetworkInterface networkInterface) throws SocketException {
        synchronized (this.infLock) {
            this.getImpl().setOption(31, networkInterface);
            this.infAddress = null;
            this.interfaceSet = true;
        }
    }
    
    public NetworkInterface getNetworkInterface() throws SocketException {
        final NetworkInterface networkInterface = (NetworkInterface)this.getImpl().getOption(31);
        if (networkInterface.getIndex() == 0 || networkInterface.getIndex() == -1) {
            final InetAddress[] array = { InetAddress.anyLocalAddress() };
            return new NetworkInterface(array[0].getHostName(), 0, array);
        }
        return networkInterface;
    }
    
    public void setLoopbackMode(final boolean b) throws SocketException {
        this.getImpl().setOption(18, b);
    }
    
    public boolean getLoopbackMode() throws SocketException {
        return (boolean)this.getImpl().getOption(18);
    }
    
    @Deprecated
    public void send(final DatagramPacket datagramPacket, final byte ttl) throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.checkAddress(datagramPacket.getAddress(), "send");
        synchronized (this.ttlLock) {
            synchronized (datagramPacket) {
                if (this.connectState == 0) {
                    final SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        if (datagramPacket.getAddress().isMulticastAddress()) {
                            securityManager.checkMulticast(datagramPacket.getAddress(), ttl);
                        }
                        else {
                            securityManager.checkConnect(datagramPacket.getAddress().getHostAddress(), datagramPacket.getPort());
                        }
                    }
                }
                else {
                    final InetAddress address = datagramPacket.getAddress();
                    if (address == null) {
                        datagramPacket.setAddress(this.connectedAddress);
                        datagramPacket.setPort(this.connectedPort);
                    }
                    else if (!address.equals(this.connectedAddress) || datagramPacket.getPort() != this.connectedPort) {
                        throw new SecurityException("connected address and packet address differ");
                    }
                }
                final byte ttl2 = this.getTTL();
                try {
                    if (ttl != ttl2) {
                        this.getImpl().setTTL(ttl);
                    }
                    this.getImpl().send(datagramPacket);
                }
                finally {
                    if (ttl != ttl2) {
                        this.getImpl().setTTL(ttl2);
                    }
                }
            }
        }
    }
    
    static {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getProperty("jdk.net.defaultMulticastInterface");
            }
        });
        NetworkInterface byName = null;
        if (s != null) {
            try {
                byName = NetworkInterface.getByName(s);
                if (byName == null) {
                    System.err.println("WARNING: cannot find network interface " + s);
                }
                else {
                    System.err.println("INFO: network interface set to " + s);
                }
            }
            catch (SocketException ex) {
                System.err.println("ERROR: failed to find network interface " + s);
            }
        }
        defNetIntf = byName;
    }
}
