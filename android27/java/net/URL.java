package java.net;

import sun.net.util.*;
import sun.net.www.protocol.jar.*;
import sun.security.util.*;
import sun.net.*;
import sun.security.action.*;
import java.security.*;
import java.util.*;
import java.io.*;

public final class URL implements Serializable
{
    static final String BUILTIN_HANDLERS_PREFIX = "sun.net.www.protocol";
    static final long serialVersionUID = -7627629688361524110L;
    private static final String protocolPathProp = "java.protocol.handler.pkgs";
    private String protocol;
    private String host;
    private int port;
    private String file;
    private transient String query;
    private String authority;
    private transient String path;
    private transient String userInfo;
    private String ref;
    transient InetAddress hostAddress;
    transient URLStreamHandler handler;
    private int hashCode;
    private transient UrlDeserializedState tempState;
    static URLStreamHandlerFactory factory;
    static Hashtable<String, URLStreamHandler> handlers;
    private static Object streamHandlerLock;
    private static final ObjectStreamField[] serialPersistentFields;
    
    public URL(final String s, final String s2, final int n, final String s3) throws MalformedURLException {
        this(s, s2, n, s3, null);
    }
    
    public URL(final String s, final String s2, final String s3) throws MalformedURLException {
        this(s, s2, -1, s3);
    }
    
    public URL(String lowerCase, String string, final int port, final String s, URLStreamHandler urlStreamHandler) throws MalformedURLException {
        this.port = -1;
        this.hashCode = -1;
        if (urlStreamHandler != null) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                this.checkSpecifyHandler(securityManager);
            }
        }
        lowerCase = lowerCase.toLowerCase();
        this.protocol = lowerCase;
        if (string != null) {
            if (string.indexOf(58) >= 0 && !string.startsWith("[")) {
                string = "[" + string + "]";
            }
            this.host = string;
            if (port < -1) {
                throw new MalformedURLException("Invalid port number :" + port);
            }
            this.authority = (((this.port = port) == -1) ? string : (string + ":" + port));
        }
        final Parts parts = new Parts(s);
        this.path = parts.getPath();
        this.query = parts.getQuery();
        if (this.query != null) {
            this.file = this.path + "?" + this.query;
        }
        else {
            this.file = this.path;
        }
        this.ref = parts.getRef();
        if (urlStreamHandler == null && (urlStreamHandler = getURLStreamHandler(lowerCase)) == null) {
            throw new MalformedURLException("unknown protocol: " + lowerCase);
        }
        this.handler = urlStreamHandler;
        if (string != null && this.isBuiltinStreamHandler(urlStreamHandler)) {
            final String checkExternalForm = IPAddressUtil.checkExternalForm(this);
            if (checkExternalForm != null) {
                throw new MalformedURLException(checkExternalForm);
            }
        }
        if ("jar".equalsIgnoreCase(lowerCase) && urlStreamHandler instanceof Handler) {
            final String checkNestedProtocol = ((Handler)urlStreamHandler).checkNestedProtocol(s);
            if (checkNestedProtocol != null) {
                throw new MalformedURLException(checkNestedProtocol);
            }
        }
    }
    
    public URL(final String s) throws MalformedURLException {
        this(null, s);
    }
    
    public URL(final URL url, final String s) throws MalformedURLException {
        this(url, s, null);
    }
    
    public URL(final URL url, final String s, URLStreamHandler handler) throws MalformedURLException {
        this.port = -1;
        this.hashCode = -1;
        int n = 0;
        String protocol = null;
        boolean b = false;
        boolean b2 = false;
        if (handler != null) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                this.checkSpecifyHandler(securityManager);
            }
        }
        try {
            int length;
            for (length = s.length(); length > 0 && s.charAt(length - 1) <= ' '; --length) {}
            while (n < length && s.charAt(n) <= ' ') {
                ++n;
            }
            if (s.regionMatches(true, n, "url:", 0, 4)) {
                n += 4;
            }
            if (n < s.length() && s.charAt(n) == '#') {
                b = true;
            }
            int n2 = n;
            char char1;
            while (!b && n2 < length && (char1 = s.charAt(n2)) != '/') {
                if (char1 == ':') {
                    final String lowerCase = s.substring(n, n2).toLowerCase();
                    if (this.isValidProtocol(lowerCase)) {
                        protocol = lowerCase;
                        n = n2 + 1;
                        break;
                    }
                    break;
                }
                else {
                    ++n2;
                }
            }
            this.protocol = protocol;
            if (url != null && (protocol == null || protocol.equalsIgnoreCase(url.protocol))) {
                if (handler == null) {
                    handler = url.handler;
                }
                if (url.path != null && url.path.startsWith("/")) {
                    protocol = null;
                }
                if (protocol == null) {
                    this.protocol = url.protocol;
                    this.authority = url.authority;
                    this.userInfo = url.userInfo;
                    this.host = url.host;
                    this.port = url.port;
                    this.file = url.file;
                    this.path = url.path;
                    b2 = true;
                }
            }
            if (this.protocol == null) {
                throw new MalformedURLException("no protocol: " + s);
            }
            if (handler == null && (handler = getURLStreamHandler(this.protocol)) == null) {
                throw new MalformedURLException("unknown protocol: " + this.protocol);
            }
            this.handler = handler;
            final int index = s.indexOf(35, n);
            if (index >= 0) {
                this.ref = s.substring(index + 1, length);
                length = index;
            }
            if (b2 && n == length) {
                this.query = url.query;
                if (this.ref == null) {
                    this.ref = url.ref;
                }
            }
            handler.parseURL(this, s, n, length);
        }
        catch (MalformedURLException ex) {
            throw ex;
        }
        catch (Exception ex3) {
            final MalformedURLException ex2 = new MalformedURLException(ex3.getMessage());
            ex2.initCause(ex3);
            throw ex2;
        }
    }
    
    private boolean isValidProtocol(final String s) {
        final int length = s.length();
        if (length < 1) {
            return false;
        }
        if (!Character.isLetter(s.charAt(0))) {
            return false;
        }
        for (int i = 1; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (!Character.isLetterOrDigit(char1) && char1 != '.' && char1 != '+' && char1 != '-') {
                return false;
            }
        }
        return true;
    }
    
    private void checkSpecifyHandler(final SecurityManager securityManager) {
        securityManager.checkPermission(SecurityConstants.SPECIFY_HANDLER_PERMISSION);
    }
    
    void set(final String protocol, final String host, final int port, final String s, final String ref) {
        synchronized (this) {
            this.protocol = protocol;
            this.host = host;
            this.authority = ((port == -1) ? host : (host + ":" + port));
            this.port = port;
            this.file = s;
            this.ref = ref;
            this.hashCode = -1;
            this.hostAddress = null;
            final int lastIndex = s.lastIndexOf(63);
            if (lastIndex != -1) {
                this.query = s.substring(lastIndex + 1);
                this.path = s.substring(0, lastIndex);
            }
            else {
                this.path = s;
            }
        }
    }
    
    void set(final String protocol, final String host, final int port, final String authority, final String userInfo, final String path, final String query, final String ref) {
        synchronized (this) {
            this.protocol = protocol;
            this.host = host;
            this.port = port;
            this.file = ((query == null) ? path : (path + "?" + query));
            this.userInfo = userInfo;
            this.path = path;
            this.ref = ref;
            this.hashCode = -1;
            this.hostAddress = null;
            this.query = query;
            this.authority = authority;
        }
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public String getUserInfo() {
        return this.userInfo;
    }
    
    public String getAuthority() {
        return this.authority;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public int getDefaultPort() {
        return this.handler.getDefaultPort();
    }
    
    public String getProtocol() {
        return this.protocol;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public String getFile() {
        return this.file;
    }
    
    public String getRef() {
        return this.ref;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof URL && this.handler.equals(this, (URL)o);
    }
    
    @Override
    public synchronized int hashCode() {
        if (this.hashCode != -1) {
            return this.hashCode;
        }
        return this.hashCode = this.handler.hashCode(this);
    }
    
    public boolean sameFile(final URL url) {
        return this.handler.sameFile(this, url);
    }
    
    @Override
    public String toString() {
        return this.toExternalForm();
    }
    
    public String toExternalForm() {
        return this.handler.toExternalForm(this);
    }
    
    public URI toURI() throws URISyntaxException {
        final URI uri = new URI(this.toString());
        if (this.authority != null && this.isBuiltinStreamHandler(this.handler)) {
            final String checkAuthority = IPAddressUtil.checkAuthority(this);
            if (checkAuthority != null) {
                throw new URISyntaxException(this.authority, checkAuthority);
            }
        }
        return uri;
    }
    
    public URLConnection openConnection() throws IOException {
        return this.handler.openConnection(this);
    }
    
    public URLConnection openConnection(final Proxy proxy) throws IOException {
        if (proxy == null) {
            throw new IllegalArgumentException("proxy can not be null");
        }
        final Proxy proxy2 = (proxy == Proxy.NO_PROXY) ? Proxy.NO_PROXY : ApplicationProxy.create(proxy);
        final SecurityManager securityManager = System.getSecurityManager();
        if (proxy2.type() != Proxy.Type.DIRECT && securityManager != null) {
            final InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy2.address();
            if (inetSocketAddress.isUnresolved()) {
                securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
            }
            else {
                securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
            }
        }
        return this.handler.openConnection(this, proxy2);
    }
    
    public final InputStream openStream() throws IOException {
        return this.openConnection().getInputStream();
    }
    
    public final Object getContent() throws IOException {
        return this.openConnection().getContent();
    }
    
    public final Object getContent(final Class[] array) throws IOException {
        return this.openConnection().getContent(array);
    }
    
    public static void setURLStreamHandlerFactory(final URLStreamHandlerFactory factory) {
        synchronized (URL.streamHandlerLock) {
            if (URL.factory != null) {
                throw new Error("factory already defined");
            }
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkSetFactory();
            }
            URL.handlers.clear();
            URL.factory = factory;
        }
    }
    
    static URLStreamHandler getURLStreamHandler(final String s) {
        URLStreamHandler urlStreamHandler = URL.handlers.get(s);
        if (urlStreamHandler == null) {
            boolean b = false;
            if (URL.factory != null) {
                urlStreamHandler = URL.factory.createURLStreamHandler(s);
                b = true;
            }
            if (urlStreamHandler == null) {
                String string = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("java.protocol.handler.pkgs", ""));
                if (string != "") {
                    string += "|";
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(string + "sun.net.www.protocol", "|");
                while (urlStreamHandler == null && stringTokenizer.hasMoreTokens()) {
                    final String trim = stringTokenizer.nextToken().trim();
                    try {
                        final String string2 = trim + "." + s + ".Handler";
                        Class<?> clazz = null;
                        try {
                            clazz = Class.forName(string2);
                        }
                        catch (ClassNotFoundException ex) {
                            final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                            if (systemClassLoader != null) {
                                clazz = systemClassLoader.loadClass(string2);
                            }
                        }
                        if (clazz == null) {
                            continue;
                        }
                        urlStreamHandler = (URLStreamHandler)clazz.newInstance();
                    }
                    catch (Exception ex2) {}
                }
            }
            synchronized (URL.streamHandlerLock) {
                URLStreamHandler urlStreamHandler2 = URL.handlers.get(s);
                if (urlStreamHandler2 != null) {
                    return urlStreamHandler2;
                }
                if (!b && URL.factory != null) {
                    urlStreamHandler2 = URL.factory.createURLStreamHandler(s);
                }
                if (urlStreamHandler2 != null) {
                    urlStreamHandler = urlStreamHandler2;
                }
                if (urlStreamHandler != null) {
                    URL.handlers.put(s, urlStreamHandler);
                }
            }
        }
        return urlStreamHandler;
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }
    
    private synchronized void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final String s = (String)fields.get("protocol", null);
        if (getURLStreamHandler(s) == null) {
            throw new IOException("unknown protocol: " + s);
        }
        String s2 = (String)fields.get("host", null);
        final int value = fields.get("port", -1);
        String s3 = (String)fields.get("authority", null);
        final String s4 = (String)fields.get("file", null);
        final String s5 = (String)fields.get("ref", null);
        final int value2 = fields.get("hashCode", -1);
        if (s3 == null && ((s2 != null && s2.length() > 0) || value != -1)) {
            if (s2 == null) {
                s2 = "";
            }
            s3 = ((value == -1) ? s2 : (s2 + ":" + value));
        }
        this.tempState = new UrlDeserializedState(s, s2, value, s3, s4, s5, value2);
    }
    
    private Object readResolve() throws ObjectStreamException {
        final URLStreamHandler urlStreamHandler = getURLStreamHandler(this.tempState.getProtocol());
        URL url;
        if (this.isBuiltinStreamHandler(urlStreamHandler.getClass().getName())) {
            url = this.fabricateNewURL();
        }
        else {
            url = this.setDeserializedFields(urlStreamHandler);
        }
        return url;
    }
    
    private URL setDeserializedFields(final URLStreamHandler handler) {
        String userInfo = null;
        final String protocol = this.tempState.getProtocol();
        String host = this.tempState.getHost();
        final int port = this.tempState.getPort();
        String authority = this.tempState.getAuthority();
        final String file = this.tempState.getFile();
        final String ref = this.tempState.getRef();
        final int hashCode = this.tempState.getHashCode();
        if (authority == null && ((host != null && host.length() > 0) || port != -1)) {
            if (host == null) {
                host = "";
            }
            authority = ((port == -1) ? host : (host + ":" + port));
            final int lastIndex = host.lastIndexOf(64);
            if (lastIndex != -1) {
                userInfo = host.substring(0, lastIndex);
                host = host.substring(lastIndex + 1);
            }
        }
        else if (authority != null) {
            final int index = authority.indexOf(64);
            if (index != -1) {
                userInfo = authority.substring(0, index);
            }
        }
        String substring = null;
        String substring2 = null;
        if (file != null) {
            final int lastIndex2 = file.lastIndexOf(63);
            if (lastIndex2 != -1) {
                substring2 = file.substring(lastIndex2 + 1);
                substring = file.substring(0, lastIndex2);
            }
            else {
                substring = file;
            }
        }
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.file = file;
        this.authority = authority;
        this.ref = ref;
        this.hashCode = hashCode;
        this.handler = handler;
        this.query = substring2;
        this.path = substring;
        this.userInfo = userInfo;
        return this;
    }
    
    private URL fabricateNewURL() throws InvalidObjectException {
        final String reconstituteUrlString = this.tempState.reconstituteUrlString();
        URL url;
        try {
            url = new URL(reconstituteUrlString);
        }
        catch (MalformedURLException ex2) {
            this.resetState();
            final InvalidObjectException ex = new InvalidObjectException("Malformed URL: " + reconstituteUrlString);
            ex.initCause(ex2);
            throw ex;
        }
        url.setSerializedHashCode(this.tempState.getHashCode());
        this.resetState();
        return url;
    }
    
    boolean isBuiltinStreamHandler(final URLStreamHandler urlStreamHandler) {
        final Class<? extends URLStreamHandler> class1 = urlStreamHandler.getClass();
        return this.isBuiltinStreamHandler(class1.getName()) || class1.getClassLoader() == null;
    }
    
    private boolean isBuiltinStreamHandler(final String s) {
        return s.startsWith("sun.net.www.protocol");
    }
    
    private void resetState() {
        this.protocol = null;
        this.host = null;
        this.port = -1;
        this.file = null;
        this.authority = null;
        this.ref = null;
        this.hashCode = -1;
        this.handler = null;
        this.query = null;
        this.path = null;
        this.userInfo = null;
        this.tempState = null;
    }
    
    private void setSerializedHashCode(final int hashCode) {
        this.hashCode = hashCode;
    }
    
    static {
        URL.handlers = new Hashtable<String, URLStreamHandler>();
        URL.streamHandlerLock = new Object();
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("protocol", String.class), new ObjectStreamField("host", String.class), new ObjectStreamField("port", Integer.TYPE), new ObjectStreamField("authority", String.class), new ObjectStreamField("file", String.class), new ObjectStreamField("ref", String.class), new ObjectStreamField("hashCode", Integer.TYPE) };
    }
}
