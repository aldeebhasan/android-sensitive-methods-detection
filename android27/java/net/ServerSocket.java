package java.net;

import java.security.*;
import java.io.*;
import java.nio.channels.*;

public class ServerSocket implements Closeable
{
    private boolean created;
    private boolean bound;
    private boolean closed;
    private Object closeLock;
    private SocketImpl impl;
    private boolean oldImpl;
    private static SocketImplFactory factory;
    
    ServerSocket(final SocketImpl impl) {
        this.created = false;
        this.bound = false;
        this.closed = false;
        this.closeLock = new Object();
        this.oldImpl = false;
        (this.impl = impl).setServerSocket(this);
    }
    
    public ServerSocket() throws IOException {
        this.created = false;
        this.bound = false;
        this.closed = false;
        this.closeLock = new Object();
        this.oldImpl = false;
        this.setImpl();
    }
    
    public ServerSocket(final int n) throws IOException {
        this(n, 50, null);
    }
    
    public ServerSocket(final int n, final int n2) throws IOException {
        this(n, n2, null);
    }
    
    public ServerSocket(final int n, int n2, final InetAddress inetAddress) throws IOException {
        this.created = false;
        this.bound = false;
        this.closed = false;
        this.closeLock = new Object();
        this.oldImpl = false;
        this.setImpl();
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException("Port value out of range: " + n);
        }
        if (n2 < 1) {
            n2 = 50;
        }
        try {
            this.bind(new InetSocketAddress(inetAddress, n), n2);
        }
        catch (SecurityException ex) {
            this.close();
            throw ex;
        }
        catch (IOException ex2) {
            this.close();
            throw ex2;
        }
    }
    
    SocketImpl getImpl() throws SocketException {
        if (!this.created) {
            this.createImpl();
        }
        return this.impl;
    }
    
    private void checkOldImpl() {
        if (this.impl == null) {
            return;
        }
        try {
            AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction<Void>() {
                @Override
                public Void run() throws NoSuchMethodException {
                    ServerSocket.this.impl.getClass().getDeclaredMethod("connect", SocketAddress.class, Integer.TYPE);
                    return null;
                }
            });
        }
        catch (PrivilegedActionException ex) {
            this.oldImpl = true;
        }
    }
    
    private void setImpl() {
        if (ServerSocket.factory != null) {
            this.impl = ServerSocket.factory.createSocketImpl();
            this.checkOldImpl();
        }
        else {
            this.impl = new SocksSocketImpl();
        }
        if (this.impl != null) {
            this.impl.setServerSocket(this);
        }
    }
    
    void createImpl() throws SocketException {
        if (this.impl == null) {
            this.setImpl();
        }
        try {
            this.impl.create(true);
            this.created = true;
        }
        catch (IOException ex) {
            throw new SocketException(ex.getMessage());
        }
    }
    
    public void bind(final SocketAddress socketAddress) throws IOException {
        this.bind(socketAddress, 50);
    }
    
    public void bind(SocketAddress socketAddress, int n) throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.oldImpl && this.isBound()) {
            throw new SocketException("Already bound");
        }
        if (socketAddress == null) {
            socketAddress = new InetSocketAddress(0);
        }
        if (!(socketAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Unsupported address type");
        }
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
        if (inetSocketAddress.isUnresolved()) {
            throw new SocketException("Unresolved address");
        }
        if (n < 1) {
            n = 50;
        }
        try {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkListen(inetSocketAddress.getPort());
            }
            this.getImpl().bind(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
            this.getImpl().listen(n);
            this.bound = true;
        }
        catch (SecurityException ex) {
            this.bound = false;
            throw ex;
        }
        catch (IOException ex2) {
            this.bound = false;
            throw ex2;
        }
    }
    
    public InetAddress getInetAddress() {
        if (!this.isBound()) {
            return null;
        }
        try {
            final InetAddress inetAddress = this.getImpl().getInetAddress();
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkConnect(inetAddress.getHostAddress(), -1);
            }
            return inetAddress;
        }
        catch (SecurityException ex) {
            return InetAddress.getLoopbackAddress();
        }
        catch (SocketException ex2) {
            return null;
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
    
    public SocketAddress getLocalSocketAddress() {
        if (!this.isBound()) {
            return null;
        }
        return new InetSocketAddress(this.getInetAddress(), this.getLocalPort());
    }
    
    public Socket accept() throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.isBound()) {
            throw new SocketException("Socket is not bound yet");
        }
        final Socket socket = new Socket((SocketImpl)null);
        this.implAccept(socket);
        return socket;
    }
    
    protected final void implAccept(final Socket socket) throws IOException {
        SocketImpl impl = null;
        try {
            if (socket.impl == null) {
                socket.setImpl();
            }
            else {
                socket.impl.reset();
            }
            impl = socket.impl;
            socket.impl = null;
            impl.address = new InetAddress();
            impl.fd = new FileDescriptor();
            this.getImpl().accept(impl);
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkAccept(impl.getInetAddress().getHostAddress(), impl.getPort());
            }
        }
        catch (IOException ex) {
            if (impl != null) {
                impl.reset();
            }
            socket.impl = impl;
            throw ex;
        }
        catch (SecurityException ex2) {
            if (impl != null) {
                impl.reset();
            }
            socket.impl = impl;
            throw ex2;
        }
        socket.impl = impl;
        socket.postAccept();
    }
    
    @Override
    public void close() throws IOException {
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
    
    public ServerSocketChannel getChannel() {
        return null;
    }
    
    public boolean isBound() {
        return this.bound || this.oldImpl;
    }
    
    public boolean isClosed() {
        synchronized (this.closeLock) {
            return this.closed;
        }
    }
    
    public synchronized void setSoTimeout(final int n) throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        this.getImpl().setOption(4102, new Integer(n));
    }
    
    public synchronized int getSoTimeout() throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        final Object option = this.getImpl().getOption(4102);
        if (option instanceof Integer) {
            return (int)option;
        }
        return 0;
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
    public String toString() {
        if (!this.isBound()) {
            return "ServerSocket[unbound]";
        }
        InetAddress inetAddress;
        if (System.getSecurityManager() != null) {
            inetAddress = InetAddress.getLoopbackAddress();
        }
        else {
            inetAddress = this.impl.getInetAddress();
        }
        return "ServerSocket[addr=" + inetAddress + ",localport=" + this.impl.getLocalPort() + "]";
    }
    
    void setBound() {
        this.bound = true;
    }
    
    void setCreated() {
        this.created = true;
    }
    
    public static synchronized void setSocketFactory(final SocketImplFactory factory) throws IOException {
        if (ServerSocket.factory != null) {
            throw new SocketException("factory already defined");
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        ServerSocket.factory = factory;
    }
    
    public synchronized void setReceiveBufferSize(final int n) throws SocketException {
        if (n <= 0) {
            throw new IllegalArgumentException("negative receive size");
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
    
    public void setPerformancePreferences(final int n, final int n2, final int n3) {
    }
    
    static {
        ServerSocket.factory = null;
    }
}
