package java.net;

import java.security.*;
import java.io.*;
import java.nio.channels.*;

public class DatagramSocket implements Closeable
{
    private boolean created;
    private boolean bound;
    private boolean closed;
    private Object closeLock;
    DatagramSocketImpl impl;
    boolean oldImpl;
    private boolean explicitFilter;
    private int bytesLeftToFilter;
    static final int ST_NOT_CONNECTED = 0;
    static final int ST_CONNECTED = 1;
    static final int ST_CONNECTED_NO_IMPL = 2;
    int connectState;
    InetAddress connectedAddress;
    int connectedPort;
    static Class<?> implClass;
    static DatagramSocketImplFactory factory;
    
    private synchronized void connectInternal(final InetAddress connectedAddress, final int connectedPort) throws SocketException {
        if (connectedPort < 0 || connectedPort > 65535) {
            throw new IllegalArgumentException("connect: " + connectedPort);
        }
        if (connectedAddress == null) {
            throw new IllegalArgumentException("connect: null address");
        }
        this.checkAddress(connectedAddress, "connect");
        if (this.isClosed()) {
            return;
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            if (connectedAddress.isMulticastAddress()) {
                securityManager.checkMulticast(connectedAddress);
            }
            else {
                securityManager.checkConnect(connectedAddress.getHostAddress(), connectedPort);
                securityManager.checkAccept(connectedAddress.getHostAddress(), connectedPort);
            }
        }
        if (!this.isBound()) {
            this.bind(new InetSocketAddress(0));
        }
        if (this.oldImpl || (this.impl instanceof AbstractPlainDatagramSocketImpl && ((AbstractPlainDatagramSocketImpl)this.impl).nativeConnectDisabled())) {
            this.connectState = 2;
        }
        else {
            try {
                this.getImpl().connect(connectedAddress, connectedPort);
                this.connectState = 1;
                final int dataAvailable = this.getImpl().dataAvailable();
                if (dataAvailable == -1) {
                    throw new SocketException();
                }
                this.explicitFilter = (dataAvailable > 0);
                if (this.explicitFilter) {
                    this.bytesLeftToFilter = this.getReceiveBufferSize();
                }
            }
            catch (SocketException ex) {
                this.connectState = 2;
            }
        }
        this.connectedAddress = connectedAddress;
        this.connectedPort = connectedPort;
    }
    
    public DatagramSocket() throws SocketException {
        this(new InetSocketAddress(0));
    }
    
    protected DatagramSocket(final DatagramSocketImpl impl) {
        this.created = false;
        this.bound = false;
        this.closed = false;
        this.closeLock = new Object();
        this.oldImpl = false;
        this.explicitFilter = false;
        this.connectState = 0;
        this.connectedAddress = null;
        this.connectedPort = -1;
        if (impl == null) {
            throw new NullPointerException();
        }
        this.impl = impl;
        this.checkOldImpl();
    }
    
    public DatagramSocket(final SocketAddress socketAddress) throws SocketException {
        this.created = false;
        this.bound = false;
        this.closed = false;
        this.closeLock = new Object();
        this.oldImpl = false;
        this.explicitFilter = false;
        this.connectState = 0;
        this.connectedAddress = null;
        this.connectedPort = -1;
        this.createImpl();
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
    
    public DatagramSocket(final int n) throws SocketException {
        this(n, null);
    }
    
    public DatagramSocket(final int n, final InetAddress inetAddress) throws SocketException {
        this(new InetSocketAddress(inetAddress, n));
    }
    
    private void checkOldImpl() {
        if (this.impl == null) {
            return;
        }
        try {
            AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction<Void>() {
                @Override
                public Void run() throws NoSuchMethodException {
                    DatagramSocket.this.impl.getClass().getDeclaredMethod("peekData", DatagramPacket.class);
                    return null;
                }
            });
        }
        catch (PrivilegedActionException ex) {
            this.oldImpl = true;
        }
    }
    
    void createImpl() throws SocketException {
        if (this.impl == null) {
            if (DatagramSocket.factory != null) {
                this.impl = DatagramSocket.factory.createDatagramSocketImpl();
                this.checkOldImpl();
            }
            else {
                this.impl = DefaultDatagramSocketImplFactory.createDatagramSocketImpl(this instanceof MulticastSocket);
                this.checkOldImpl();
            }
        }
        this.impl.create();
        this.impl.setDatagramSocket(this);
        this.created = true;
    }
    
    DatagramSocketImpl getImpl() throws SocketException {
        if (!this.created) {
            this.createImpl();
        }
        return this.impl;
    }
    
    public synchronized void bind(SocketAddress socketAddress) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (this.isBound()) {
            throw new SocketException("already bound");
        }
        if (socketAddress == null) {
            socketAddress = new InetSocketAddress(0);
        }
        if (!(socketAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Unsupported address type!");
        }
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        if (inetSocketAddress.isUnresolved()) {
            throw new SocketException("Unresolved address");
        }
        final InetAddress address = inetSocketAddress.getAddress();
        final int port = inetSocketAddress.getPort();
        this.checkAddress(address, "bind");
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkListen(port);
        }
        try {
            this.getImpl().bind(port, address);
        }
        catch (SocketException ex) {
            this.getImpl().close();
            throw ex;
        }
        this.bound = true;
    }
    
    void checkAddress(final InetAddress inetAddress, final String s) {
        if (inetAddress == null) {
            return;
        }
        if (!(inetAddress instanceof Inet4Address) && !(inetAddress instanceof Inet6Address)) {
            throw new IllegalArgumentException(s + ": invalid address type");
        }
    }
    
    public void connect(final InetAddress inetAddress, final int n) {
        try {
            this.connectInternal(inetAddress, n);
        }
        catch (SocketException ex) {
            throw new Error("connect failed", ex);
        }
    }
    
    public void connect(final SocketAddress socketAddress) throws SocketException {
        if (socketAddress == null) {
            throw new IllegalArgumentException("Address can't be null");
        }
        if (!(socketAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Unsupported address type");
        }
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        if (inetSocketAddress.isUnresolved()) {
            throw new SocketException("Unresolved address");
        }
        this.connectInternal(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
    }
    
    public void disconnect() {
        synchronized (this) {
            if (this.isClosed()) {
                return;
            }
            if (this.connectState == 1) {
                this.impl.disconnect();
            }
            this.connectedAddress = null;
            this.connectedPort = -1;
            this.connectState = 0;
            this.explicitFilter = false;
        }
    }
    
    public boolean isBound() {
        return this.bound;
    }
    
    public boolean isConnected() {
        return this.connectState != 0;
    }
    
    public InetAddress getInetAddress() {
        return this.connectedAddress;
    }
    
    public int getPort() {
        return this.connectedPort;
    }
    
    public SocketAddress getRemoteSocketAddress() {
        if (!this.isConnected()) {
            return null;
        }
        return new InetSocketAddress(this.getInetAddress(), this.getPort());
    }
    
    public SocketAddress getLocalSocketAddress() {
        if (this.isClosed()) {
            return null;
        }
        if (!this.isBound()) {
            return null;
        }
        return new InetSocketAddress(this.getLocalAddress(), this.getLocalPort());
    }
    
    public void send(final DatagramPacket datagramPacket) throws IOException {
        synchronized (datagramPacket) {
            if (this.isClosed()) {
                throw new SocketException("Socket is closed");
            }
            this.checkAddress(datagramPacket.getAddress(), "send");
            if (this.connectState == 0) {
                final SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    if (datagramPacket.getAddress().isMulticastAddress()) {
                        securityManager.checkMulticast(datagramPacket.getAddress());
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
                    throw new IllegalArgumentException("connected address and packet address differ");
                }
            }
            if (!this.isBound()) {
                this.bind(new InetSocketAddress(0));
            }
            this.getImpl().send(datagramPacket);
        }
    }
    
    public synchronized void receive(final DatagramPacket datagramPacket) throws IOException {
        synchronized (datagramPacket) {
            if (!this.isBound()) {
                this.bind(new InetSocketAddress(0));
            }
            if (this.connectState == 0) {
                final SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    while (true) {
                        int n;
                        String s;
                        if (!this.oldImpl) {
                            final DatagramPacket datagramPacket2 = new DatagramPacket(new byte[1], 1);
                            n = this.getImpl().peekData(datagramPacket2);
                            s = datagramPacket2.getAddress().getHostAddress();
                        }
                        else {
                            final InetAddress inetAddress = new InetAddress();
                            n = this.getImpl().peek(inetAddress);
                            s = inetAddress.getHostAddress();
                        }
                        try {
                            securityManager.checkAccept(s, n);
                        }
                        catch (SecurityException ex) {
                            this.getImpl().receive(new DatagramPacket(new byte[1], 1));
                            continue;
                        }
                        break;
                    }
                }
            }
            DatagramPacket datagramPacket3 = null;
            if (this.connectState == 2 || this.explicitFilter) {
                int i = 0;
                while (i == 0) {
                    int n2;
                    InetAddress address;
                    if (!this.oldImpl) {
                        final DatagramPacket datagramPacket4 = new DatagramPacket(new byte[1], 1);
                        n2 = this.getImpl().peekData(datagramPacket4);
                        address = datagramPacket4.getAddress();
                    }
                    else {
                        address = new InetAddress();
                        n2 = this.getImpl().peek(address);
                    }
                    if (!this.connectedAddress.equals(address) || this.connectedPort != n2) {
                        datagramPacket3 = new DatagramPacket(new byte[1024], 1024);
                        this.getImpl().receive(datagramPacket3);
                        if (!this.explicitFilter || !this.checkFiltering(datagramPacket3)) {
                            continue;
                        }
                        i = 1;
                    }
                    else {
                        i = 1;
                    }
                }
            }
            this.getImpl().receive(datagramPacket);
            if (this.explicitFilter && datagramPacket3 == null) {
                this.checkFiltering(datagramPacket);
            }
        }
    }
    
    private boolean checkFiltering(final DatagramPacket datagramPacket) throws SocketException {
        this.bytesLeftToFilter -= datagramPacket.getLength();
        if (this.bytesLeftToFilter <= 0 || this.getImpl().dataAvailable() <= 0) {
            this.explicitFilter = false;
            return true;
        }
        return false;
    }
    
    public InetAddress getLocalAddress() {
        if (this.isClosed()) {
            return null;
        }
        InetAddress inetAddress;
        try {
            inetAddress = (InetAddress)this.getImpl().getOption(15);
            if (inetAddress.isAnyLocalAddress()) {
                inetAddress = InetAddress.anyLocalAddress();
            }
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkConnect(inetAddress.getHostAddress(), -1);
            }
        }
        catch (Exception ex) {
            inetAddress = InetAddress.anyLocalAddress();
        }
        return inetAddress;
    }
    
    public int getLocalPort() {
        if (this.isClosed()) {
            return -1;
        }
        try {
            return this.getImpl().getLocalPort();
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    public synchronized void setSoTimeout(final int n) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(4102, new Integer(n));
    }
    
    public synchronized int getSoTimeout() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (this.getImpl() == null) {
            return 0;
        }
        final Object option = this.getImpl().getOption(4102);
        if (option instanceof Integer) {
            return (int)option;
        }
        return 0;
    }
    
    public synchronized void setSendBufferSize(final int n) throws SocketException {
        if (n <= 0) {
            throw new IllegalArgumentException("negative send size");
        }
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(4097, new Integer(n));
    }
    
    public synchronized int getSendBufferSize() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        int intValue = 0;
        final Object option = this.getImpl().getOption(4097);
        if (option instanceof Integer) {
            intValue = (int)option;
        }
        return intValue;
    }
    
    public synchronized void setReceiveBufferSize(final int n) throws SocketException {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid receive size");
        }
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(4098, new Integer(n));
    }
    
    public synchronized int getReceiveBufferSize() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        int intValue = 0;
        final Object option = this.getImpl().getOption(4098);
        if (option instanceof Integer) {
            intValue = (int)option;
        }
        return intValue;
    }
    
    public synchronized void setReuseAddress(final boolean b) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (this.oldImpl) {
            this.getImpl().setOption(4, new Integer(b ? -1 : 0));
        }
        else {
            this.getImpl().setOption(4, b);
        }
    }
    
    public synchronized boolean getReuseAddress() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return (boolean)this.getImpl().getOption(4);
    }
    
    public synchronized void setBroadcast(final boolean b) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(32, b);
    }
    
    public synchronized boolean getBroadcast() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return (boolean)this.getImpl().getOption(32);
    }
    
    public synchronized void setTrafficClass(final int n) throws SocketException {
        if (n < 0 || n > 255) {
            throw new IllegalArgumentException("tc is not in range 0 -- 255");
        }
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        try {
            this.getImpl().setOption(3, n);
        }
        catch (SocketException ex) {
            if (!this.isConnected()) {
                throw ex;
            }
        }
    }
    
    public synchronized int getTrafficClass() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return (int)this.getImpl().getOption(3);
    }
    
    @Override
    public void close() {
        synchronized (this.closeLock) {
            if (this.isClosed()) {
                return;
            }
            this.impl.close();
            this.closed = true;
        }
    }
    
    public boolean isClosed() {
        synchronized (this.closeLock) {
            return this.closed;
        }
    }
    
    public DatagramChannel getChannel() {
        return null;
    }
    
    public static synchronized void setDatagramSocketImplFactory(final DatagramSocketImplFactory factory) throws IOException {
        if (DatagramSocket.factory != null) {
            throw new SocketException("factory already defined");
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        DatagramSocket.factory = factory;
    }
    
    static {
        DatagramSocket.implClass = null;
    }
}
