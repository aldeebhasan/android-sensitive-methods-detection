package java.net;

import sun.net.*;
import sun.security.util.*;
import java.nio.channels.*;
import java.security.*;
import java.io.*;

public class Socket implements Closeable
{
    private boolean created;
    private boolean bound;
    private boolean connected;
    private boolean closed;
    private Object closeLock;
    private boolean shutIn;
    private boolean shutOut;
    SocketImpl impl;
    private boolean oldImpl;
    private static SocketImplFactory factory;
    
    public Socket() {
        this.created = false;
        this.bound = false;
        this.connected = false;
        this.closed = false;
        this.closeLock = new Object();
        this.shutIn = false;
        this.shutOut = false;
        this.oldImpl = false;
        this.setImpl();
    }
    
    public Socket(final Proxy proxy) {
        this.created = false;
        this.bound = false;
        this.connected = false;
        this.closed = false;
        this.closeLock = new Object();
        this.shutIn = false;
        this.shutOut = false;
        this.oldImpl = false;
        if (proxy == null) {
            throw new IllegalArgumentException("Invalid Proxy");
        }
        final Proxy proxy2 = (proxy == Proxy.NO_PROXY) ? Proxy.NO_PROXY : ApplicationProxy.create(proxy);
        final Proxy.Type type = proxy2.type();
        if (type == Proxy.Type.SOCKS || type == Proxy.Type.HTTP) {
            final SecurityManager securityManager = System.getSecurityManager();
            InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy2.address();
            if (inetSocketAddress.getAddress() != null) {
                this.checkAddress(inetSocketAddress.getAddress(), "Socket");
            }
            if (securityManager != null) {
                if (inetSocketAddress.isUnresolved()) {
                    inetSocketAddress = new InetSocketAddress(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
                }
                if (inetSocketAddress.isUnresolved()) {
                    securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
                }
                else {
                    securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
                }
            }
            (this.impl = ((type == Proxy.Type.SOCKS) ? new SocksSocketImpl(proxy2) : new HttpConnectSocketImpl(proxy2))).setSocket(this);
        }
        else {
            if (proxy2 != Proxy.NO_PROXY) {
                throw new IllegalArgumentException("Invalid Proxy");
            }
            if (Socket.factory == null) {
                (this.impl = new PlainSocketImpl(false)).setSocket(this);
            }
            else {
                this.setImpl();
            }
        }
    }
    
    protected Socket(final SocketImpl socketImpl) throws SocketException {
        this(checkPermission(socketImpl), socketImpl);
    }
    
    private Socket(final Void void1, final SocketImpl impl) {
        this.created = false;
        this.bound = false;
        this.connected = false;
        this.closed = false;
        this.closeLock = new Object();
        this.shutIn = false;
        this.shutOut = false;
        this.oldImpl = false;
        if (impl != null) {
            this.impl = impl;
            this.checkOldImpl();
            impl.setSocket(this);
        }
    }
    
    private static Void checkPermission(final SocketImpl socketImpl) {
        if (socketImpl == null) {
            return null;
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.SET_SOCKETIMPL_PERMISSION);
        }
        return null;
    }
    
    public Socket(final String s, final int n) throws UnknownHostException, IOException {
        this((s != null) ? new InetSocketAddress(s, n) : new InetSocketAddress(InetAddress.getByName(null), n), null, true);
    }
    
    public Socket(final InetAddress inetAddress, final int n) throws IOException {
        this((inetAddress != null) ? new InetSocketAddress(inetAddress, n) : null, null, true);
    }
    
    public Socket(final String s, final int n, final InetAddress inetAddress, final int n2) throws IOException {
        this((s != null) ? new InetSocketAddress(s, n) : new InetSocketAddress(InetAddress.getByName(null), n), new InetSocketAddress(inetAddress, n2), true);
    }
    
    public Socket(final InetAddress inetAddress, final int n, final InetAddress inetAddress2, final int n2) throws IOException {
        this((inetAddress != null) ? new InetSocketAddress(inetAddress, n) : null, new InetSocketAddress(inetAddress2, n2), true);
    }
    
    public Socket(final String s, final int n, final boolean b) throws IOException {
        this((s != null) ? new InetSocketAddress(s, n) : new InetSocketAddress(InetAddress.getByName(null), n), null, b);
    }
    
    public Socket(final InetAddress inetAddress, final int n, final boolean b) throws IOException {
        this((inetAddress != null) ? new InetSocketAddress(inetAddress, n) : null, new InetSocketAddress(0), b);
    }
    
    private Socket(final SocketAddress socketAddress, final SocketAddress socketAddress2, final boolean b) throws IOException {
        this.created = false;
        this.bound = false;
        this.connected = false;
        this.closed = false;
        this.closeLock = new Object();
        this.shutIn = false;
        this.shutOut = false;
        this.oldImpl = false;
        this.setImpl();
        if (socketAddress == null) {
            throw new NullPointerException();
        }
        try {
            this.createImpl(b);
            if (socketAddress2 != null) {
                this.bind(socketAddress2);
            }
            this.connect(socketAddress);
        }
        catch (IOException | IllegalArgumentException | SecurityException ex4) {
            final SecurityException ex2;
            final SecurityException ex = ex2;
            try {
                this.close();
            }
            catch (IOException ex3) {
                ex.addSuppressed(ex3);
            }
            throw ex;
        }
    }
    
    void createImpl(final boolean b) throws SocketException {
        if (this.impl == null) {
            this.setImpl();
        }
        try {
            this.impl.create(b);
            this.created = true;
        }
        catch (IOException ex) {
            throw new SocketException(ex.getMessage());
        }
    }
    
    private void checkOldImpl() {
        if (this.impl == null) {
            return;
        }
        this.oldImpl = AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                Serializable s = Socket.this.impl.getClass();
                while (true) {
                    try {
                        ((Class)s).getDeclaredMethod("connect", SocketAddress.class, Integer.TYPE);
                        return Boolean.FALSE;
                    }
                    catch (NoSuchMethodException ex) {
                        s = ((Class<? extends SocketImpl>)s).getSuperclass();
                        if (s.equals(SocketImpl.class)) {
                            return Boolean.TRUE;
                        }
                        continue;
                    }
                    break;
                }
            }
        });
    }
    
    void setImpl() {
        if (Socket.factory != null) {
            this.impl = Socket.factory.createSocketImpl();
            this.checkOldImpl();
        }
        else {
            this.impl = new SocksSocketImpl();
        }
        if (this.impl != null) {
            this.impl.setSocket(this);
        }
    }
    
    SocketImpl getImpl() throws SocketException {
        if (!this.created) {
            this.createImpl(true);
        }
        return this.impl;
    }
    
    public void connect(final SocketAddress socketAddress) throws IOException {
        this.connect(socketAddress, 0);
    }
    
    public void connect(final SocketAddress socketAddress, final int n) throws IOException {
        if (socketAddress == null) {
            throw new IllegalArgumentException("connect: The address can't be null");
        }
        if (n < 0) {
            throw new IllegalArgumentException("connect: timeout can't be negative");
        }
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.oldImpl && this.isConnected()) {
            throw new SocketException("already connected");
        }
        if (!(socketAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Unsupported address type");
        }
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        final InetAddress address = inetSocketAddress.getAddress();
        final int port = inetSocketAddress.getPort();
        this.checkAddress(address, "connect");
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            if (inetSocketAddress.isUnresolved()) {
                securityManager.checkConnect(inetSocketAddress.getHostName(), port);
            }
            else {
                securityManager.checkConnect(address.getHostAddress(), port);
            }
        }
        if (!this.created) {
            this.createImpl(true);
        }
        if (!this.oldImpl) {
            this.impl.connect(inetSocketAddress, n);
        }
        else {
            if (n != 0) {
                throw new UnsupportedOperationException("SocketImpl.connect(addr, timeout)");
            }
            if (inetSocketAddress.isUnresolved()) {
                this.impl.connect(address.getHostName(), port);
            }
            else {
                this.impl.connect(address, port);
            }
        }
        this.connected = true;
        this.bound = true;
    }
    
    public void bind(final SocketAddress socketAddress) throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.oldImpl && this.isBound()) {
            throw new SocketException("Already bound");
        }
        if (socketAddress != null && !(socketAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Unsupported address type");
        }
        InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        if (inetSocketAddress != null && inetSocketAddress.isUnresolved()) {
            throw new SocketException("Unresolved address");
        }
        if (inetSocketAddress == null) {
            inetSocketAddress = new InetSocketAddress(0);
        }
        final InetAddress address = inetSocketAddress.getAddress();
        final int port = inetSocketAddress.getPort();
        this.checkAddress(address, "bind");
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkListen(port);
        }
        this.getImpl().bind(address, port);
        this.bound = true;
    }
    
    private void checkAddress(final InetAddress inetAddress, final String s) {
        if (inetAddress == null) {
            return;
        }
        if (!(inetAddress instanceof Inet4Address) && !(inetAddress instanceof Inet6Address)) {
            throw new IllegalArgumentException(s + ": invalid address type");
        }
    }
    
    final void postAccept() {
        this.connected = true;
        this.created = true;
        this.bound = true;
    }
    
    void setCreated() {
        this.created = true;
    }
    
    void setBound() {
        this.bound = true;
    }
    
    void setConnected() {
        this.connected = true;
    }
    
    public InetAddress getInetAddress() {
        if (!this.isConnected()) {
            return null;
        }
        try {
            return this.getImpl().getInetAddress();
        }
        catch (SocketException ex) {
            return null;
        }
    }
    
    public InetAddress getLocalAddress() {
        if (!this.isBound()) {
            return InetAddress.anyLocalAddress();
        }
        InetAddress inetAddress;
        try {
            inetAddress = (InetAddress)this.getImpl().getOption(15);
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkConnect(inetAddress.getHostAddress(), -1);
            }
            if (inetAddress.isAnyLocalAddress()) {
                inetAddress = InetAddress.anyLocalAddress();
            }
        }
        catch (SecurityException ex) {
            inetAddress = InetAddress.getLoopbackAddress();
        }
        catch (Exception ex2) {
            inetAddress = InetAddress.anyLocalAddress();
        }
        return inetAddress;
    }
    
    public int getPort() {
        if (!this.isConnected()) {
            return 0;
        }
        try {
            return this.getImpl().getPort();
        }
        catch (SocketException ex) {
            return -1;
        }
    }
    
    public int getLocalPort() {
        if (!this.isBound()) {
            return -1;
        }
        try {
            return this.getImpl().getLocalPort();
        }
        catch (SocketException ex) {
            return -1;
        }
    }
    
    public SocketAddress getRemoteSocketAddress() {
        if (!this.isConnected()) {
            return null;
        }
        return new InetSocketAddress(this.getInetAddress(), this.getPort());
    }
    
    public SocketAddress getLocalSocketAddress() {
        if (!this.isBound()) {
            return null;
        }
        return new InetSocketAddress(this.getLocalAddress(), this.getLocalPort());
    }
    
    public SocketChannel getChannel() {
        return null;
    }
    
    public InputStream getInputStream() throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.isConnected()) {
            throw new SocketException("Socket is not connected");
        }
        if (this.isInputShutdown()) {
            throw new SocketException("Socket input is shutdown");
        }
        InputStream inputStream;
        try {
            inputStream = AccessController.doPrivileged((PrivilegedExceptionAction<InputStream>)new PrivilegedExceptionAction<InputStream>() {
                @Override
                public InputStream run() throws IOException {
                    return Socket.this.impl.getInputStream();
                }
            });
        }
        catch (PrivilegedActionException ex) {
            throw (IOException)ex.getException();
        }
        return inputStream;
    }
    
    public OutputStream getOutputStream() throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.isConnected()) {
            throw new SocketException("Socket is not connected");
        }
        if (this.isOutputShutdown()) {
            throw new SocketException("Socket output is shutdown");
        }
        OutputStream outputStream;
        try {
            outputStream = AccessController.doPrivileged((PrivilegedExceptionAction<OutputStream>)new PrivilegedExceptionAction<OutputStream>() {
                @Override
                public OutputStream run() throws IOException {
                    return Socket.this.impl.getOutputStream();
                }
            });
        }
        catch (PrivilegedActionException ex) {
            throw (IOException)ex.getException();
        }
        return outputStream;
    }
    
    public void setTcpNoDelay(final boolean b) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(1, b);
    }
    
    public boolean getTcpNoDelay() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return (boolean)this.getImpl().getOption(1);
    }
    
    public void setSoLinger(final boolean b, int n) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!b) {
            this.getImpl().setOption(128, new Boolean(b));
        }
        else {
            if (n < 0) {
                throw new IllegalArgumentException("invalid value for SO_LINGER");
            }
            if (n > 65535) {
                n = 65535;
            }
            this.getImpl().setOption(128, new Integer(n));
        }
    }
    
    public int getSoLinger() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        final Object option = this.getImpl().getOption(128);
        if (option instanceof Integer) {
            return (int)option;
        }
        return -1;
    }
    
    public void sendUrgentData(final int n) throws IOException {
        if (!this.getImpl().supportsUrgentData()) {
            throw new SocketException("Urgent data not supported");
        }
        this.getImpl().sendUrgentData(n);
    }
    
    public void setOOBInline(final boolean b) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(4099, b);
    }
    
    public boolean getOOBInline() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return (boolean)this.getImpl().getOption(4099);
    }
    
    public synchronized void setSoTimeout(final int n) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (n < 0) {
            throw new IllegalArgumentException("timeout can't be negative");
        }
        this.getImpl().setOption(4102, new Integer(n));
    }
    
    public synchronized int getSoTimeout() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
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
    
    public void setKeepAlive(final boolean b) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(8, b);
    }
    
    public boolean getKeepAlive() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return (boolean)this.getImpl().getOption(8);
    }
    
    public void setTrafficClass(final int n) throws SocketException {
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
    
    public int getTrafficClass() throws SocketException {
        return (int)this.getImpl().getOption(3);
    }
    
    public void setReuseAddress(final boolean b) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(4, b);
    }
    
    public boolean getReuseAddress() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        return (boolean)this.getImpl().getOption(4);
    }
    
    @Override
    public synchronized void close() throws IOException {
        synchronized (this.closeLock) {
            if (this.isClosed()) {
                return;
            }
            if (this.created) {
                this.impl.close();
            }
            this.closed = true;
        }
    }
    
    public void shutdownInput() throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.isConnected()) {
            throw new SocketException("Socket is not connected");
        }
        if (this.isInputShutdown()) {
            throw new SocketException("Socket input is already shutdown");
        }
        this.getImpl().shutdownInput();
        this.shutIn = true;
    }
    
    public void shutdownOutput() throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.isConnected()) {
            throw new SocketException("Socket is not connected");
        }
        if (this.isOutputShutdown()) {
            throw new SocketException("Socket output is already shutdown");
        }
        this.getImpl().shutdownOutput();
        this.shutOut = true;
    }
    
    @Override
    public String toString() {
        try {
            if (this.isConnected()) {
                return "Socket[addr=" + this.getImpl().getInetAddress() + ",port=" + this.getImpl().getPort() + ",localport=" + this.getImpl().getLocalPort() + "]";
            }
        }
        catch (SocketException ex) {}
        return "Socket[unconnected]";
    }
    
    public boolean isConnected() {
        return this.connected || this.oldImpl;
    }
    
    public boolean isBound() {
        return this.bound || this.oldImpl;
    }
    
    public boolean isClosed() {
        synchronized (this.closeLock) {
            return this.closed;
        }
    }
    
    public boolean isInputShutdown() {
        return this.shutIn;
    }
    
    public boolean isOutputShutdown() {
        return this.shutOut;
    }
    
    public static synchronized void setSocketImplFactory(final SocketImplFactory factory) throws IOException {
        if (Socket.factory != null) {
            throw new SocketException("factory already defined");
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        Socket.factory = factory;
    }
    
    public void setPerformancePreferences(final int n, final int n2, final int n3) {
    }
    
    static {
        Socket.factory = null;
    }
}
