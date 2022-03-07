package java.net;

import java.security.*;
import java.util.*;
import java.io.*;

public abstract class HttpURLConnection extends URLConnection
{
    protected String method;
    protected int chunkLength;
    protected int fixedContentLength;
    protected long fixedContentLengthLong;
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    protected int responseCode;
    protected String responseMessage;
    private static boolean followRedirects;
    protected boolean instanceFollowRedirects;
    private static final String[] methods;
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_ACCEPTED = 202;
    public static final int HTTP_NOT_AUTHORITATIVE = 203;
    public static final int HTTP_NO_CONTENT = 204;
    public static final int HTTP_RESET = 205;
    public static final int HTTP_PARTIAL = 206;
    public static final int HTTP_MULT_CHOICE = 300;
    public static final int HTTP_MOVED_PERM = 301;
    public static final int HTTP_MOVED_TEMP = 302;
    public static final int HTTP_SEE_OTHER = 303;
    public static final int HTTP_NOT_MODIFIED = 304;
    public static final int HTTP_USE_PROXY = 305;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_PAYMENT_REQUIRED = 402;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_BAD_METHOD = 405;
    public static final int HTTP_NOT_ACCEPTABLE = 406;
    public static final int HTTP_PROXY_AUTH = 407;
    public static final int HTTP_CLIENT_TIMEOUT = 408;
    public static final int HTTP_CONFLICT = 409;
    public static final int HTTP_GONE = 410;
    public static final int HTTP_LENGTH_REQUIRED = 411;
    public static final int HTTP_PRECON_FAILED = 412;
    public static final int HTTP_ENTITY_TOO_LARGE = 413;
    public static final int HTTP_REQ_TOO_LONG = 414;
    public static final int HTTP_UNSUPPORTED_TYPE = 415;
    @Deprecated
    public static final int HTTP_SERVER_ERROR = 500;
    public static final int HTTP_INTERNAL_ERROR = 500;
    public static final int HTTP_NOT_IMPLEMENTED = 501;
    public static final int HTTP_BAD_GATEWAY = 502;
    public static final int HTTP_UNAVAILABLE = 503;
    public static final int HTTP_GATEWAY_TIMEOUT = 504;
    public static final int HTTP_VERSION = 505;
    
    @Override
    public String getHeaderFieldKey(final int n) {
        return null;
    }
    
    public void setFixedLengthStreamingMode(final int fixedContentLength) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        if (this.chunkLength != -1) {
            throw new IllegalStateException("Chunked encoding streaming mode set");
        }
        if (fixedContentLength < 0) {
            throw new IllegalArgumentException("invalid content length");
        }
        this.fixedContentLength = fixedContentLength;
    }
    
    public void setFixedLengthStreamingMode(final long fixedContentLengthLong) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        if (this.chunkLength != -1) {
            throw new IllegalStateException("Chunked encoding streaming mode set");
        }
        if (fixedContentLengthLong < 0L) {
            throw new IllegalArgumentException("invalid content length");
        }
        this.fixedContentLengthLong = fixedContentLengthLong;
    }
    
    public void setChunkedStreamingMode(final int n) {
        if (this.connected) {
            throw new IllegalStateException("Can't set streaming mode: already connected");
        }
        if (this.fixedContentLength != -1 || this.fixedContentLengthLong != -1L) {
            throw new IllegalStateException("Fixed length streaming mode set");
        }
        this.chunkLength = ((n <= 0) ? 4096 : n);
    }
    
    @Override
    public String getHeaderField(final int n) {
        return null;
    }
    
    protected HttpURLConnection(final URL url) {
        super(url);
        this.method = "GET";
        this.chunkLength = -1;
        this.fixedContentLength = -1;
        this.fixedContentLengthLong = -1L;
        this.responseCode = -1;
        this.responseMessage = null;
        this.instanceFollowRedirects = HttpURLConnection.followRedirects;
    }
    
    public static void setFollowRedirects(final boolean followRedirects) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        HttpURLConnection.followRedirects = followRedirects;
    }
    
    public static boolean getFollowRedirects() {
        return HttpURLConnection.followRedirects;
    }
    
    public void setInstanceFollowRedirects(final boolean instanceFollowRedirects) {
        this.instanceFollowRedirects = instanceFollowRedirects;
    }
    
    public boolean getInstanceFollowRedirects() {
        return this.instanceFollowRedirects;
    }
    
    public void setRequestMethod(final String method) throws ProtocolException {
        if (this.connected) {
            throw new ProtocolException("Can't reset method: already connected");
        }
        for (int i = 0; i < HttpURLConnection.methods.length; ++i) {
            if (HttpURLConnection.methods[i].equals(method)) {
                if (method.equals("TRACE")) {
                    final SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkPermission(new NetPermission("allowHttpTrace"));
                    }
                }
                this.method = method;
                return;
            }
        }
        throw new ProtocolException("Invalid HTTP method: " + method);
    }
    
    public String getRequestMethod() {
        return this.method;
    }
    
    public int getResponseCode() throws IOException {
        if (this.responseCode != -1) {
            return this.responseCode;
        }
        Exception ex = null;
        try {
            this.getInputStream();
        }
        catch (Exception ex2) {
            ex = ex2;
        }
        final String headerField = this.getHeaderField(0);
        if (headerField != null) {
            if (headerField.startsWith("HTTP/1.")) {
                final int index = headerField.indexOf(32);
                if (index > 0) {
                    int n = headerField.indexOf(32, index + 1);
                    if (n > 0 && n < headerField.length()) {
                        this.responseMessage = headerField.substring(n + 1);
                    }
                    if (n < 0) {
                        n = headerField.length();
                    }
                    try {
                        return this.responseCode = Integer.parseInt(headerField.substring(index + 1, n));
                    }
                    catch (NumberFormatException ex3) {}
                }
            }
            return -1;
        }
        if (ex == null) {
            return -1;
        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
        }
        throw (IOException)ex;
    }
    
    public String getResponseMessage() throws IOException {
        this.getResponseCode();
        return this.responseMessage;
    }
    
    @Override
    public long getHeaderFieldDate(final String s, final long n) {
        String s2 = this.getHeaderField(s);
        try {
            if (s2.indexOf("GMT") == -1) {
                s2 += " GMT";
            }
            return Date.parse(s2);
        }
        catch (Exception ex) {
            return n;
        }
    }
    
    public abstract void disconnect();
    
    public abstract boolean usingProxy();
    
    @Override
    public Permission getPermission() throws IOException {
        final int port = this.url.getPort();
        return new SocketPermission(this.url.getHost() + ":" + ((port < 0) ? 80 : port), "connect");
    }
    
    public InputStream getErrorStream() {
        return null;
    }
    
    static {
        HttpURLConnection.followRedirects = true;
        methods = new String[] { "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE" };
    }
}
