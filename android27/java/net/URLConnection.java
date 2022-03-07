package java.net;

import sun.net.www.*;
import sun.security.util.*;
import java.io.*;
import java.util.*;
import sun.security.action.*;
import java.security.*;

public abstract class URLConnection
{
    protected URL url;
    protected boolean doInput;
    protected boolean doOutput;
    private static boolean defaultAllowUserInteraction;
    protected boolean allowUserInteraction;
    private static boolean defaultUseCaches;
    protected boolean useCaches;
    protected long ifModifiedSince;
    protected boolean connected;
    private int connectTimeout;
    private int readTimeout;
    private MessageHeader requests;
    private static volatile FileNameMap fileNameMap;
    static ContentHandlerFactory factory;
    private static Hashtable<String, ContentHandler> handlers;
    private static final String contentClassPrefix = "sun.net.www.content";
    private static final String contentPathProp = "java.content.handler.pkgs";
    
    public static FileNameMap getFileNameMap() {
        FileNameMap fileNameMap = URLConnection.fileNameMap;
        if (fileNameMap == null) {
            fileNameMap = (URLConnection.fileNameMap = new FileNameMap() {
                private FileNameMap internalMap = MimeTable.loadTable();
                
                @Override
                public String getContentTypeFor(final String s) {
                    return this.internalMap.getContentTypeFor(s);
                }
            });
        }
        return fileNameMap;
    }
    
    public static void setFileNameMap(final FileNameMap fileNameMap) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        URLConnection.fileNameMap = fileNameMap;
    }
    
    public abstract void connect() throws IOException;
    
    public void setConnectTimeout(final int connectTimeout) {
        if (connectTimeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.connectTimeout = connectTimeout;
    }
    
    public int getConnectTimeout() {
        return this.connectTimeout;
    }
    
    public void setReadTimeout(final int readTimeout) {
        if (readTimeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.readTimeout = readTimeout;
    }
    
    public int getReadTimeout() {
        return this.readTimeout;
    }
    
    protected URLConnection(final URL url) {
        this.doInput = true;
        this.doOutput = false;
        this.allowUserInteraction = URLConnection.defaultAllowUserInteraction;
        this.useCaches = URLConnection.defaultUseCaches;
        this.ifModifiedSince = 0L;
        this.connected = false;
        this.url = url;
    }
    
    public URL getURL() {
        return this.url;
    }
    
    public int getContentLength() {
        final long contentLengthLong = this.getContentLengthLong();
        if (contentLengthLong > 2147483647L) {
            return -1;
        }
        return (int)contentLengthLong;
    }
    
    public long getContentLengthLong() {
        return this.getHeaderFieldLong("content-length", -1L);
    }
    
    public String getContentType() {
        return this.getHeaderField("content-type");
    }
    
    public String getContentEncoding() {
        return this.getHeaderField("content-encoding");
    }
    
    public long getExpiration() {
        return this.getHeaderFieldDate("expires", 0L);
    }
    
    public long getDate() {
        return this.getHeaderFieldDate("date", 0L);
    }
    
    public long getLastModified() {
        return this.getHeaderFieldDate("last-modified", 0L);
    }
    
    public String getHeaderField(final String s) {
        return null;
    }
    
    public Map<String, List<String>> getHeaderFields() {
        return Collections.emptyMap();
    }
    
    public int getHeaderFieldInt(final String s, final int n) {
        final String headerField = this.getHeaderField(s);
        try {
            return Integer.parseInt(headerField);
        }
        catch (Exception ex) {
            return n;
        }
    }
    
    public long getHeaderFieldLong(final String s, final long n) {
        final String headerField = this.getHeaderField(s);
        try {
            return Long.parseLong(headerField);
        }
        catch (Exception ex) {
            return n;
        }
    }
    
    public long getHeaderFieldDate(final String s, final long n) {
        final String headerField = this.getHeaderField(s);
        try {
            return Date.parse(headerField);
        }
        catch (Exception ex) {
            return n;
        }
    }
    
    public String getHeaderFieldKey(final int n) {
        return null;
    }
    
    public String getHeaderField(final int n) {
        return null;
    }
    
    public Object getContent() throws IOException {
        this.getInputStream();
        return this.getContentHandler().getContent(this);
    }
    
    public Object getContent(final Class[] array) throws IOException {
        this.getInputStream();
        return this.getContentHandler().getContent(this, array);
    }
    
    public Permission getPermission() throws IOException {
        return SecurityConstants.ALL_PERMISSION;
    }
    
    public InputStream getInputStream() throws IOException {
        throw new UnknownServiceException("protocol doesn't support input");
    }
    
    public OutputStream getOutputStream() throws IOException {
        throw new UnknownServiceException("protocol doesn't support output");
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + ":" + this.url;
    }
    
    public void setDoInput(final boolean doInput) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        this.doInput = doInput;
    }
    
    public boolean getDoInput() {
        return this.doInput;
    }
    
    public void setDoOutput(final boolean doOutput) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        this.doOutput = doOutput;
    }
    
    public boolean getDoOutput() {
        return this.doOutput;
    }
    
    public void setAllowUserInteraction(final boolean allowUserInteraction) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        this.allowUserInteraction = allowUserInteraction;
    }
    
    public boolean getAllowUserInteraction() {
        return this.allowUserInteraction;
    }
    
    public static void setDefaultAllowUserInteraction(final boolean defaultAllowUserInteraction) {
        URLConnection.defaultAllowUserInteraction = defaultAllowUserInteraction;
    }
    
    public static boolean getDefaultAllowUserInteraction() {
        return URLConnection.defaultAllowUserInteraction;
    }
    
    public void setUseCaches(final boolean useCaches) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        this.useCaches = useCaches;
    }
    
    public boolean getUseCaches() {
        return this.useCaches;
    }
    
    public void setIfModifiedSince(final long ifModifiedSince) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        this.ifModifiedSince = ifModifiedSince;
    }
    
    public long getIfModifiedSince() {
        return this.ifModifiedSince;
    }
    
    public boolean getDefaultUseCaches() {
        return URLConnection.defaultUseCaches;
    }
    
    public void setDefaultUseCaches(final boolean defaultUseCaches) {
        URLConnection.defaultUseCaches = defaultUseCaches;
    }
    
    public void setRequestProperty(final String s, final String s2) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        if (s == null) {
            throw new NullPointerException("key is null");
        }
        if (this.requests == null) {
            this.requests = new MessageHeader();
        }
        this.requests.set(s, s2);
    }
    
    public void addRequestProperty(final String s, final String s2) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        if (s == null) {
            throw new NullPointerException("key is null");
        }
        if (this.requests == null) {
            this.requests = new MessageHeader();
        }
        this.requests.add(s, s2);
    }
    
    public String getRequestProperty(final String s) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        if (this.requests == null) {
            return null;
        }
        return this.requests.findValue(s);
    }
    
    public Map<String, List<String>> getRequestProperties() {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        if (this.requests == null) {
            return Collections.emptyMap();
        }
        return this.requests.getHeaders(null);
    }
    
    @Deprecated
    public static void setDefaultRequestProperty(final String s, final String s2) {
    }
    
    @Deprecated
    public static String getDefaultRequestProperty(final String s) {
        return null;
    }
    
    public static synchronized void setContentHandlerFactory(final ContentHandlerFactory factory) {
        if (URLConnection.factory != null) {
            throw new Error("factory already defined");
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        URLConnection.factory = factory;
    }
    
    synchronized ContentHandler getContentHandler() throws UnknownServiceException {
        final String stripOffParameters = this.stripOffParameters(this.getContentType());
        ContentHandler contentHandler = null;
        if (stripOffParameters == null) {
            throw new UnknownServiceException("no content-type");
        }
        try {
            contentHandler = URLConnection.handlers.get(stripOffParameters);
            if (contentHandler != null) {
                return contentHandler;
            }
        }
        catch (Exception ex2) {}
        if (URLConnection.factory != null) {
            contentHandler = URLConnection.factory.createContentHandler(stripOffParameters);
        }
        if (contentHandler == null) {
            try {
                contentHandler = this.lookupContentHandlerClassFor(stripOffParameters);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                contentHandler = UnknownContentHandler.INSTANCE;
            }
            URLConnection.handlers.put(stripOffParameters, contentHandler);
        }
        return contentHandler;
    }
    
    private String stripOffParameters(final String s) {
        if (s == null) {
            return null;
        }
        final int index = s.indexOf(59);
        if (index > 0) {
            return s.substring(0, index);
        }
        return s;
    }
    
    private ContentHandler lookupContentHandlerClassFor(final String s) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        final String typeToPackageName = this.typeToPackageName(s);
        final StringTokenizer stringTokenizer = new StringTokenizer(this.getContentHandlerPkgPrefixes(), "|");
        while (stringTokenizer.hasMoreTokens()) {
            final String trim = stringTokenizer.nextToken().trim();
            try {
                final String string = trim + "." + typeToPackageName;
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(string);
                }
                catch (ClassNotFoundException ex) {
                    final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                    if (systemClassLoader != null) {
                        clazz = systemClassLoader.loadClass(string);
                    }
                }
                if (clazz != null) {
                    return (ContentHandler)clazz.newInstance();
                }
                continue;
            }
            catch (Exception ex2) {}
        }
        return UnknownContentHandler.INSTANCE;
    }
    
    private String typeToPackageName(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        final int length = lowerCase.length();
        final char[] array = new char[length];
        lowerCase.getChars(0, length, array, 0);
        for (int i = 0; i < length; ++i) {
            final char c = array[i];
            if (c == '/') {
                array[i] = '.';
            }
            else if (('A' > c || c > 'Z') && ('a' > c || c > 'z') && ('0' > c || c > '9')) {
                array[i] = '_';
            }
        }
        return new String(array);
    }
    
    private String getContentHandlerPkgPrefixes() {
        String string = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("java.content.handler.pkgs", ""));
        if (string != "") {
            string += "|";
        }
        return string + "sun.net.www.content";
    }
    
    public static String guessContentTypeFromName(final String s) {
        return getFileNameMap().getContentTypeFor(s);
    }
    
    public static String guessContentTypeFromStream(final InputStream inputStream) throws IOException {
        if (!inputStream.markSupported()) {
            return null;
        }
        inputStream.mark(16);
        final int read = inputStream.read();
        final int read2 = inputStream.read();
        final int read3 = inputStream.read();
        final int read4 = inputStream.read();
        final int read5 = inputStream.read();
        final int read6 = inputStream.read();
        final int read7 = inputStream.read();
        final int read8 = inputStream.read();
        final int read9 = inputStream.read();
        final int read10 = inputStream.read();
        final int read11 = inputStream.read();
        final int read12 = inputStream.read();
        final int read13 = inputStream.read();
        final int read14 = inputStream.read();
        final int read15 = inputStream.read();
        final int read16 = inputStream.read();
        inputStream.reset();
        if (read == 202 && read2 == 254 && read3 == 186 && read4 == 190) {
            return "application/java-vm";
        }
        if (read == 172 && read2 == 237) {
            return "application/x-java-serialized-object";
        }
        if (read == 60) {
            if (read2 == 33 || (read2 == 104 && ((read3 == 116 && read4 == 109 && read5 == 108) || (read3 == 101 && read4 == 97 && read5 == 100))) || (read2 == 98 && read3 == 111 && read4 == 100 && read5 == 121) || (read2 == 72 && ((read3 == 84 && read4 == 77 && read5 == 76) || (read3 == 69 && read4 == 65 && read5 == 68))) || (read2 == 66 && read3 == 79 && read4 == 68 && read5 == 89)) {
                return "text/html";
            }
            if (read2 == 63 && read3 == 120 && read4 == 109 && read5 == 108 && read6 == 32) {
                return "application/xml";
            }
        }
        if (read == 239 && read2 == 187 && read3 == 191 && read4 == 60 && read5 == 63 && read6 == 120) {
            return "application/xml";
        }
        if (read == 254 && read2 == 255 && read3 == 0 && read4 == 60 && read5 == 0 && read6 == 63 && read7 == 0 && read8 == 120) {
            return "application/xml";
        }
        if (read == 255 && read2 == 254 && read3 == 60 && read4 == 0 && read5 == 63 && read6 == 0 && read7 == 120 && read8 == 0) {
            return "application/xml";
        }
        if (read == 0 && read2 == 0 && read3 == 254 && read4 == 255 && read5 == 0 && read6 == 0 && read7 == 0 && read8 == 60 && read9 == 0 && read10 == 0 && read11 == 0 && read12 == 63 && read13 == 0 && read14 == 0 && read15 == 0 && read16 == 120) {
            return "application/xml";
        }
        if (read == 255 && read2 == 254 && read3 == 0 && read4 == 0 && read5 == 60 && read6 == 0 && read7 == 0 && read8 == 0 && read9 == 63 && read10 == 0 && read11 == 0 && read12 == 0 && read13 == 120 && read14 == 0 && read15 == 0 && read16 == 0) {
            return "application/xml";
        }
        if (read == 71 && read2 == 73 && read3 == 70 && read4 == 56) {
            return "image/gif";
        }
        if (read == 35 && read2 == 100 && read3 == 101 && read4 == 102) {
            return "image/x-bitmap";
        }
        if (read == 33 && read2 == 32 && read3 == 88 && read4 == 80 && read5 == 77 && read6 == 50) {
            return "image/x-pixmap";
        }
        if (read == 137 && read2 == 80 && read3 == 78 && read4 == 71 && read5 == 13 && read6 == 10 && read7 == 26 && read8 == 10) {
            return "image/png";
        }
        if (read == 255 && read2 == 216 && read3 == 255) {
            if (read4 == 224 || read4 == 238) {
                return "image/jpeg";
            }
            if (read4 == 225 && read7 == 69 && read8 == 120 && read9 == 105 && read10 == 102 && read11 == 0) {
                return "image/jpeg";
            }
        }
        if (read == 208 && read2 == 207 && read3 == 17 && read4 == 224 && read5 == 161 && read6 == 177 && read7 == 26 && read8 == 225 && checkfpx(inputStream)) {
            return "image/vnd.fpx";
        }
        if (read == 46 && read2 == 115 && read3 == 110 && read4 == 100) {
            return "audio/basic";
        }
        if (read == 100 && read2 == 110 && read3 == 115 && read4 == 46) {
            return "audio/basic";
        }
        if (read == 82 && read2 == 73 && read3 == 70 && read4 == 70) {
            return "audio/x-wav";
        }
        return null;
    }
    
    private static boolean checkfpx(final InputStream inputStream) throws IOException {
        inputStream.mark(256);
        final long n = 28L;
        final long skipForward;
        if ((skipForward = skipForward(inputStream, n)) < n) {
            inputStream.reset();
            return false;
        }
        final int[] array = new int[16];
        if (readBytes(array, 2, inputStream) < 0) {
            inputStream.reset();
            return false;
        }
        final int n2 = array[0];
        final long n3 = skipForward + 2L;
        if (readBytes(array, 2, inputStream) < 0) {
            inputStream.reset();
            return false;
        }
        int n4;
        if (n2 == 254) {
            n4 = array[0] + (array[1] << 8);
        }
        else {
            n4 = (array[0] << 8) + array[1];
        }
        final long n5 = 48L - (n3 + 2L);
        final long skipForward2;
        if ((skipForward2 = skipForward(inputStream, n5)) < n5) {
            inputStream.reset();
            return false;
        }
        if (readBytes(array, 4, inputStream) < 0) {
            inputStream.reset();
            return false;
        }
        int n6;
        if (n2 == 254) {
            n6 = array[0] + (array[1] << 8) + (array[2] << 16) + (array[3] << 24);
        }
        else {
            n6 = (array[0] << 24) + (array[1] << 16) + (array[2] << 8) + array[3];
        }
        inputStream.reset();
        final long n7 = 512L + (1 << n4) * n6 + 80L;
        if (n7 < 0L) {
            return false;
        }
        inputStream.mark((int)n7 + 48);
        if (skipForward(inputStream, n7) < n7) {
            inputStream.reset();
            return false;
        }
        if (readBytes(array, 16, inputStream) < 0) {
            inputStream.reset();
            return false;
        }
        if (n2 == 254 && array[0] == 0 && array[2] == 97 && array[3] == 86 && array[4] == 84 && array[5] == 193 && array[6] == 206 && array[7] == 17 && array[8] == 133 && array[9] == 83 && array[10] == 0 && array[11] == 170 && array[12] == 0 && array[13] == 161 && array[14] == 249 && array[15] == 91) {
            inputStream.reset();
            return true;
        }
        if (array[3] == 0 && array[1] == 97 && array[0] == 86 && array[5] == 84 && array[4] == 193 && array[7] == 206 && array[6] == 17 && array[8] == 133 && array[9] == 83 && array[10] == 0 && array[11] == 170 && array[12] == 0 && array[13] == 161 && array[14] == 249 && array[15] == 91) {
            inputStream.reset();
            return true;
        }
        inputStream.reset();
        return false;
    }
    
    private static int readBytes(final int[] array, final int n, final InputStream inputStream) throws IOException {
        final byte[] array2 = new byte[n];
        if (inputStream.read(array2, 0, n) < n) {
            return -1;
        }
        for (int i = 0; i < n; ++i) {
            array[i] = (array2[i] & 0xFF);
        }
        return 0;
    }
    
    private static long skipForward(final InputStream inputStream, final long n) throws IOException {
        long n2;
        long skip;
        for (n2 = 0L; n2 != n; n2 += skip) {
            skip = inputStream.skip(n - n2);
            if (skip <= 0L) {
                if (inputStream.read() == -1) {
                    return n2;
                }
                ++n2;
            }
        }
        return n2;
    }
    
    static {
        URLConnection.defaultAllowUserInteraction = false;
        URLConnection.defaultUseCaches = true;
        URLConnection.handlers = new Hashtable<String, ContentHandler>();
    }
}
