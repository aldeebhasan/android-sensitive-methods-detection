package java.net;

import java.io.*;
import sun.nio.cs.*;
import java.nio.*;
import java.text.*;
import java.nio.charset.*;

public final class URI implements Comparable<URI>, Serializable
{
    static final long serialVersionUID = -6052424284110960213L;
    private transient String scheme;
    private transient String fragment;
    private transient String authority;
    private transient String userInfo;
    private transient String host;
    private transient int port;
    private transient String path;
    private transient String query;
    private transient volatile String schemeSpecificPart;
    private transient volatile int hash;
    private transient volatile String decodedUserInfo;
    private transient volatile String decodedAuthority;
    private transient volatile String decodedPath;
    private transient volatile String decodedQuery;
    private transient volatile String decodedFragment;
    private transient volatile String decodedSchemeSpecificPart;
    private volatile String string;
    private static final long L_DIGIT;
    private static final long H_DIGIT = 0L;
    private static final long L_UPALPHA = 0L;
    private static final long H_UPALPHA;
    private static final long L_LOWALPHA = 0L;
    private static final long H_LOWALPHA;
    private static final long L_ALPHA = 0L;
    private static final long H_ALPHA;
    private static final long L_ALPHANUM;
    private static final long H_ALPHANUM;
    private static final long L_HEX;
    private static final long H_HEX;
    private static final long L_MARK;
    private static final long H_MARK;
    private static final long L_UNRESERVED;
    private static final long H_UNRESERVED;
    private static final long L_RESERVED;
    private static final long H_RESERVED;
    private static final long L_ESCAPED = 1L;
    private static final long H_ESCAPED = 0L;
    private static final long L_URIC;
    private static final long H_URIC;
    private static final long L_PCHAR;
    private static final long H_PCHAR;
    private static final long L_PATH;
    private static final long H_PATH;
    private static final long L_DASH;
    private static final long H_DASH;
    private static final long L_DOT;
    private static final long H_DOT;
    private static final long L_USERINFO;
    private static final long H_USERINFO;
    private static final long L_REG_NAME;
    private static final long H_REG_NAME;
    private static final long L_SERVER;
    private static final long H_SERVER;
    private static final long L_SERVER_PERCENT;
    private static final long H_SERVER_PERCENT;
    private static final long L_LEFT_BRACKET;
    private static final long H_LEFT_BRACKET;
    private static final long L_SCHEME;
    private static final long H_SCHEME;
    private static final long L_URIC_NO_SLASH;
    private static final long H_URIC_NO_SLASH;
    private static final char[] hexDigits;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    private URI() {
        this.port = -1;
        this.decodedUserInfo = null;
        this.decodedAuthority = null;
        this.decodedPath = null;
        this.decodedQuery = null;
        this.decodedFragment = null;
        this.decodedSchemeSpecificPart = null;
    }
    
    public URI(final String s) throws URISyntaxException {
        this.port = -1;
        this.decodedUserInfo = null;
        this.decodedAuthority = null;
        this.decodedPath = null;
        this.decodedQuery = null;
        this.decodedFragment = null;
        this.decodedSchemeSpecificPart = null;
        new Parser(s).parse(false);
    }
    
    public URI(final String s, final String s2, final String s3, final int n, final String s4, final String s5, final String s6) throws URISyntaxException {
        this.port = -1;
        this.decodedUserInfo = null;
        this.decodedAuthority = null;
        this.decodedPath = null;
        this.decodedQuery = null;
        this.decodedFragment = null;
        this.decodedSchemeSpecificPart = null;
        final String string = this.toString(s, null, null, s2, s3, n, s4, s5, s6);
        checkPath(string, s, s4);
        new Parser(string).parse(true);
    }
    
    public URI(final String s, final String s2, final String s3, final String s4, final String s5) throws URISyntaxException {
        this.port = -1;
        this.decodedUserInfo = null;
        this.decodedAuthority = null;
        this.decodedPath = null;
        this.decodedQuery = null;
        this.decodedFragment = null;
        this.decodedSchemeSpecificPart = null;
        final String string = this.toString(s, null, s2, null, null, -1, s3, s4, s5);
        checkPath(string, s, s3);
        new Parser(string).parse(false);
    }
    
    public URI(final String s, final String s2, final String s3, final String s4) throws URISyntaxException {
        this(s, null, s2, -1, s3, null, s4);
    }
    
    public URI(final String s, final String s2, final String s3) throws URISyntaxException {
        this.port = -1;
        this.decodedUserInfo = null;
        this.decodedAuthority = null;
        this.decodedPath = null;
        this.decodedQuery = null;
        this.decodedFragment = null;
        this.decodedSchemeSpecificPart = null;
        new Parser(this.toString(s, s2, null, null, null, -1, null, null, s3)).parse(false);
    }
    
    public static URI create(final String s) {
        try {
            return new URI(s);
        }
        catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
    
    public URI parseServerAuthority() throws URISyntaxException {
        if (this.host != null || this.authority == null) {
            return this;
        }
        this.defineString();
        new Parser(this.string).parse(true);
        return this;
    }
    
    public URI normalize() {
        return normalize(this);
    }
    
    public URI resolve(final URI uri) {
        return resolve(this, uri);
    }
    
    public URI resolve(final String s) {
        return this.resolve(create(s));
    }
    
    public URI relativize(final URI uri) {
        return relativize(this, uri);
    }
    
    public URL toURL() throws MalformedURLException {
        if (!this.isAbsolute()) {
            throw new IllegalArgumentException("URI is not absolute");
        }
        return new URL(this.toString());
    }
    
    public String getScheme() {
        return this.scheme;
    }
    
    public boolean isAbsolute() {
        return this.scheme != null;
    }
    
    public boolean isOpaque() {
        return this.path == null;
    }
    
    public String getRawSchemeSpecificPart() {
        this.defineSchemeSpecificPart();
        return this.schemeSpecificPart;
    }
    
    public String getSchemeSpecificPart() {
        if (this.decodedSchemeSpecificPart == null) {
            this.decodedSchemeSpecificPart = decode(this.getRawSchemeSpecificPart());
        }
        return this.decodedSchemeSpecificPart;
    }
    
    public String getRawAuthority() {
        return this.authority;
    }
    
    public String getAuthority() {
        if (this.decodedAuthority == null) {
            this.decodedAuthority = decode(this.authority);
        }
        return this.decodedAuthority;
    }
    
    public String getRawUserInfo() {
        return this.userInfo;
    }
    
    public String getUserInfo() {
        if (this.decodedUserInfo == null && this.userInfo != null) {
            this.decodedUserInfo = decode(this.userInfo);
        }
        return this.decodedUserInfo;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getRawPath() {
        return this.path;
    }
    
    public String getPath() {
        if (this.decodedPath == null && this.path != null) {
            this.decodedPath = decode(this.path);
        }
        return this.decodedPath;
    }
    
    public String getRawQuery() {
        return this.query;
    }
    
    public String getQuery() {
        if (this.decodedQuery == null && this.query != null) {
            this.decodedQuery = decode(this.query);
        }
        return this.decodedQuery;
    }
    
    public String getRawFragment() {
        return this.fragment;
    }
    
    public String getFragment() {
        if (this.decodedFragment == null && this.fragment != null) {
            this.decodedFragment = decode(this.fragment);
        }
        return this.decodedFragment;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof URI)) {
            return false;
        }
        final URI uri = (URI)o;
        if (this.isOpaque() != uri.isOpaque()) {
            return false;
        }
        if (!equalIgnoringCase(this.scheme, uri.scheme)) {
            return false;
        }
        if (!equal(this.fragment, uri.fragment)) {
            return false;
        }
        if (this.isOpaque()) {
            return equal(this.schemeSpecificPart, uri.schemeSpecificPart);
        }
        if (!equal(this.path, uri.path)) {
            return false;
        }
        if (!equal(this.query, uri.query)) {
            return false;
        }
        if (this.authority == uri.authority) {
            return true;
        }
        if (this.host != null) {
            if (!equal(this.userInfo, uri.userInfo)) {
                return false;
            }
            if (!equalIgnoringCase(this.host, uri.host)) {
                return false;
            }
            if (this.port != uri.port) {
                return false;
            }
        }
        else if (this.authority != null) {
            if (!equal(this.authority, uri.authority)) {
                return false;
            }
        }
        else if (this.authority != uri.authority) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        if (this.hash != 0) {
            return this.hash;
        }
        final int hash = hash(hashIgnoringCase(0, this.scheme), this.fragment);
        int hash2;
        if (this.isOpaque()) {
            hash2 = hash(hash, this.schemeSpecificPart);
        }
        else {
            final int hash3 = hash(hash(hash, this.path), this.query);
            if (this.host != null) {
                hash2 = hashIgnoringCase(hash(hash3, this.userInfo), this.host) + 1949 * this.port;
            }
            else {
                hash2 = hash(hash3, this.authority);
            }
        }
        return this.hash = hash2;
    }
    
    @Override
    public int compareTo(final URI uri) {
        final int compareIgnoringCase;
        if ((compareIgnoringCase = compareIgnoringCase(this.scheme, uri.scheme)) != 0) {
            return compareIgnoringCase;
        }
        if (this.isOpaque()) {
            if (!uri.isOpaque()) {
                return 1;
            }
            final int compare;
            if ((compare = compare(this.schemeSpecificPart, uri.schemeSpecificPart)) != 0) {
                return compare;
            }
            return compare(this.fragment, uri.fragment);
        }
        else {
            if (uri.isOpaque()) {
                return -1;
            }
            if (this.host != null && uri.host != null) {
                final int compare2;
                if ((compare2 = compare(this.userInfo, uri.userInfo)) != 0) {
                    return compare2;
                }
                final int compareIgnoringCase2;
                if ((compareIgnoringCase2 = compareIgnoringCase(this.host, uri.host)) != 0) {
                    return compareIgnoringCase2;
                }
                final int n;
                if ((n = this.port - uri.port) != 0) {
                    return n;
                }
            }
            else {
                final int compare3;
                if ((compare3 = compare(this.authority, uri.authority)) != 0) {
                    return compare3;
                }
            }
            final int compare4;
            if ((compare4 = compare(this.path, uri.path)) != 0) {
                return compare4;
            }
            final int compare5;
            if ((compare5 = compare(this.query, uri.query)) != 0) {
                return compare5;
            }
            return compare(this.fragment, uri.fragment);
        }
    }
    
    @Override
    public String toString() {
        this.defineString();
        return this.string;
    }
    
    public String toASCIIString() {
        this.defineString();
        return encode(this.string);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.defineString();
        objectOutputStream.defaultWriteObject();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        this.port = -1;
        objectInputStream.defaultReadObject();
        try {
            new Parser(this.string).parse(false);
        }
        catch (URISyntaxException ex2) {
            final InvalidObjectException ex = new InvalidObjectException("Invalid URI");
            ex.initCause(ex2);
            throw ex;
        }
    }
    
    private static int toLower(final char c) {
        if (c >= 'A' && c <= 'Z') {
            return c + ' ';
        }
        return c;
    }
    
    private static int toUpper(final char c) {
        if (c >= 'a' && c <= 'z') {
            return c - ' ';
        }
        return c;
    }
    
    private static boolean equal(final String s, final String s2) {
        if (s == s2) {
            return true;
        }
        if (s == null || s2 == null) {
            return false;
        }
        if (s.length() != s2.length()) {
            return false;
        }
        if (s.indexOf(37) < 0) {
            return s.equals(s2);
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            final char char2 = s2.charAt(i);
            if (char1 != '%') {
                if (char1 != char2) {
                    return false;
                }
            }
            else {
                if (char2 != '%') {
                    return false;
                }
                ++i;
                if (toLower(s.charAt(i)) != toLower(s2.charAt(i))) {
                    return false;
                }
                ++i;
                if (toLower(s.charAt(i)) != toLower(s2.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static boolean equalIgnoringCase(final String s, final String s2) {
        if (s == s2) {
            return true;
        }
        if (s == null || s2 == null) {
            return false;
        }
        final int length = s.length();
        if (s2.length() != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (toLower(s.charAt(i)) != toLower(s2.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    private static int hash(final int n, final String s) {
        if (s == null) {
            return n;
        }
        return (s.indexOf(37) < 0) ? (n * 127 + s.hashCode()) : normalizedHash(n, s);
    }
    
    private static int normalizedHash(final int n, final String s) {
        int n2 = 0;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            n2 = 31 * n2 + char1;
            if (char1 == '%') {
                for (int j = i + 1; j < i + 3; ++j) {
                    n2 = 31 * n2 + toUpper(s.charAt(j));
                }
                i += 2;
            }
        }
        return n * 127 + n2;
    }
    
    private static int hashIgnoringCase(final int n, final String s) {
        if (s == null) {
            return n;
        }
        int n2 = n;
        for (int length = s.length(), i = 0; i < length; ++i) {
            n2 = 31 * n2 + toLower(s.charAt(i));
        }
        return n2;
    }
    
    private static int compare(final String s, final String s2) {
        if (s == s2) {
            return 0;
        }
        if (s == null) {
            return -1;
        }
        if (s2 != null) {
            return s.compareTo(s2);
        }
        return 1;
    }
    
    private static int compareIgnoringCase(final String s, final String s2) {
        if (s == s2) {
            return 0;
        }
        if (s == null) {
            return -1;
        }
        if (s2 != null) {
            final int length = s.length();
            final int length2 = s2.length();
            for (int n = (length < length2) ? length : length2, i = 0; i < n; ++i) {
                final int n2 = toLower(s.charAt(i)) - toLower(s2.charAt(i));
                if (n2 != 0) {
                    return n2;
                }
            }
            return length - length2;
        }
        return 1;
    }
    
    private static void checkPath(final String s, final String s2, final String s3) throws URISyntaxException {
        if (s2 != null && s3 != null && s3.length() > 0 && s3.charAt(0) != '/') {
            throw new URISyntaxException(s, "Relative path in absolute URI");
        }
    }
    
    private void appendAuthority(final StringBuffer sb, final String s, final String s2, final String s3, final int n) {
        if (s3 != null) {
            sb.append("//");
            if (s2 != null) {
                sb.append(quote(s2, URI.L_USERINFO, URI.H_USERINFO));
                sb.append('@');
            }
            final boolean b = s3.indexOf(58) >= 0 && !s3.startsWith("[") && !s3.endsWith("]");
            if (b) {
                sb.append('[');
            }
            sb.append(s3);
            if (b) {
                sb.append(']');
            }
            if (n != -1) {
                sb.append(':');
                sb.append(n);
            }
        }
        else if (s != null) {
            sb.append("//");
            if (s.startsWith("[")) {
                final int index = s.indexOf("]");
                String substring = s;
                String substring2 = "";
                if (index != -1 && s.indexOf(":") != -1) {
                    if (index == s.length()) {
                        substring2 = s;
                        substring = "";
                    }
                    else {
                        substring2 = s.substring(0, index + 1);
                        substring = s.substring(index + 1);
                    }
                }
                sb.append(substring2);
                sb.append(quote(substring, URI.L_REG_NAME | URI.L_SERVER, URI.H_REG_NAME | URI.H_SERVER));
            }
            else {
                sb.append(quote(s, URI.L_REG_NAME | URI.L_SERVER, URI.H_REG_NAME | URI.H_SERVER));
            }
        }
    }
    
    private void appendSchemeSpecificPart(final StringBuffer sb, final String s, final String s2, final String s3, final String s4, final int n, final String s5, final String s6) {
        if (s != null) {
            if (s.startsWith("//[")) {
                final int index = s.indexOf("]");
                if (index != -1 && s.indexOf(":") != -1) {
                    String substring;
                    String substring2;
                    if (index == s.length()) {
                        substring = s;
                        substring2 = "";
                    }
                    else {
                        substring = s.substring(0, index + 1);
                        substring2 = s.substring(index + 1);
                    }
                    sb.append(substring);
                    sb.append(quote(substring2, URI.L_URIC, URI.H_URIC));
                }
            }
            else {
                sb.append(quote(s, URI.L_URIC, URI.H_URIC));
            }
        }
        else {
            this.appendAuthority(sb, s2, s3, s4, n);
            if (s5 != null) {
                sb.append(quote(s5, URI.L_PATH, URI.H_PATH));
            }
            if (s6 != null) {
                sb.append('?');
                sb.append(quote(s6, URI.L_URIC, URI.H_URIC));
            }
        }
    }
    
    private void appendFragment(final StringBuffer sb, final String s) {
        if (s != null) {
            sb.append('#');
            sb.append(quote(s, URI.L_URIC, URI.H_URIC));
        }
    }
    
    private String toString(final String s, final String s2, final String s3, final String s4, final String s5, final int n, final String s6, final String s7, final String s8) {
        final StringBuffer sb = new StringBuffer();
        if (s != null) {
            sb.append(s);
            sb.append(':');
        }
        this.appendSchemeSpecificPart(sb, s2, s3, s4, s5, n, s6, s7);
        this.appendFragment(sb, s8);
        return sb.toString();
    }
    
    private void defineSchemeSpecificPart() {
        if (this.schemeSpecificPart != null) {
            return;
        }
        final StringBuffer sb = new StringBuffer();
        this.appendSchemeSpecificPart(sb, null, this.getAuthority(), this.getUserInfo(), this.host, this.port, this.getPath(), this.getQuery());
        this.schemeSpecificPart = sb.toString();
    }
    
    private void defineString() {
        if (this.string != null) {
            return;
        }
        final StringBuffer sb = new StringBuffer();
        if (this.scheme != null) {
            sb.append(this.scheme);
            sb.append(':');
        }
        if (this.isOpaque()) {
            sb.append(this.schemeSpecificPart);
        }
        else {
            if (this.host != null) {
                sb.append("//");
                if (this.userInfo != null) {
                    sb.append(this.userInfo);
                    sb.append('@');
                }
                final boolean b = this.host.indexOf(58) >= 0 && !this.host.startsWith("[") && !this.host.endsWith("]");
                if (b) {
                    sb.append('[');
                }
                sb.append(this.host);
                if (b) {
                    sb.append(']');
                }
                if (this.port != -1) {
                    sb.append(':');
                    sb.append(this.port);
                }
            }
            else if (this.authority != null) {
                sb.append("//");
                sb.append(this.authority);
            }
            if (this.path != null) {
                sb.append(this.path);
            }
            if (this.query != null) {
                sb.append('?');
                sb.append(this.query);
            }
        }
        if (this.fragment != null) {
            sb.append('#');
            sb.append(this.fragment);
        }
        this.string = sb.toString();
    }
    
    private static String resolvePath(final String s, final String s2, final boolean b) {
        final int lastIndex = s.lastIndexOf(47);
        final int length = s2.length();
        String s3 = "";
        if (length == 0) {
            if (lastIndex >= 0) {
                s3 = s.substring(0, lastIndex + 1);
            }
        }
        else {
            final StringBuffer sb = new StringBuffer(s.length() + length);
            if (lastIndex >= 0) {
                sb.append(s.substring(0, lastIndex + 1));
            }
            sb.append(s2);
            s3 = sb.toString();
        }
        return normalize(s3);
    }
    
    private static URI resolve(final URI uri, final URI uri2) {
        if (uri2.isOpaque() || uri.isOpaque()) {
            return uri2;
        }
        if (uri2.scheme == null && uri2.authority == null && uri2.path.equals("") && uri2.fragment != null && uri2.query == null) {
            if (uri.fragment != null && uri2.fragment.equals(uri.fragment)) {
                return uri;
            }
            final URI uri3 = new URI();
            uri3.scheme = uri.scheme;
            uri3.authority = uri.authority;
            uri3.userInfo = uri.userInfo;
            uri3.host = uri.host;
            uri3.port = uri.port;
            uri3.path = uri.path;
            uri3.fragment = uri2.fragment;
            uri3.query = uri.query;
            return uri3;
        }
        else {
            if (uri2.scheme != null) {
                return uri2;
            }
            final URI uri4 = new URI();
            uri4.scheme = uri.scheme;
            uri4.query = uri2.query;
            uri4.fragment = uri2.fragment;
            if (uri2.authority == null) {
                uri4.authority = uri.authority;
                uri4.host = uri.host;
                uri4.userInfo = uri.userInfo;
                uri4.port = uri.port;
                final String s = (uri2.path == null) ? "" : uri2.path;
                if (s.length() > 0 && s.charAt(0) == '/') {
                    uri4.path = uri2.path;
                }
                else {
                    uri4.path = resolvePath(uri.path, s, uri.isAbsolute());
                }
            }
            else {
                uri4.authority = uri2.authority;
                uri4.host = uri2.host;
                uri4.userInfo = uri2.userInfo;
                uri4.host = uri2.host;
                uri4.port = uri2.port;
                uri4.path = uri2.path;
            }
            return uri4;
        }
    }
    
    private static URI normalize(final URI uri) {
        if (uri.isOpaque() || uri.path == null || uri.path.length() == 0) {
            return uri;
        }
        final String normalize = normalize(uri.path);
        if (normalize == uri.path) {
            return uri;
        }
        final URI uri2 = new URI();
        uri2.scheme = uri.scheme;
        uri2.fragment = uri.fragment;
        uri2.authority = uri.authority;
        uri2.userInfo = uri.userInfo;
        uri2.host = uri.host;
        uri2.port = uri.port;
        uri2.path = normalize;
        uri2.query = uri.query;
        return uri2;
    }
    
    private static URI relativize(final URI uri, final URI uri2) {
        if (uri2.isOpaque() || uri.isOpaque()) {
            return uri2;
        }
        if (!equalIgnoringCase(uri.scheme, uri2.scheme) || !equal(uri.authority, uri2.authority)) {
            return uri2;
        }
        String s = normalize(uri.path);
        final String normalize = normalize(uri2.path);
        if (!s.equals(normalize)) {
            if (!s.endsWith("/")) {
                s += "/";
            }
            if (!normalize.startsWith(s)) {
                return uri2;
            }
        }
        final URI uri3 = new URI();
        uri3.path = normalize.substring(s.length());
        uri3.query = uri2.query;
        uri3.fragment = uri2.fragment;
        return uri3;
    }
    
    private static int needsNormalization(final String s) {
        boolean b = true;
        int n = 0;
        int n2;
        int i;
        for (n2 = s.length() - 1, i = 0; i <= n2 && s.charAt(i) == '/'; ++i) {}
        if (i > 1) {
            b = false;
        }
        while (i <= n2) {
            if (s.charAt(i) == '.' && (i == n2 || s.charAt(i + 1) == '/' || (s.charAt(i + 1) == '.' && (i + 1 == n2 || s.charAt(i + 2) == '/')))) {
                b = false;
            }
            ++n;
            while (i <= n2) {
                if (s.charAt(i++) != '/') {
                    continue;
                }
                while (i <= n2) {
                    if (s.charAt(i) != '/') {
                        break;
                    }
                    b = false;
                    ++i;
                }
                break;
            }
        }
        return b ? -1 : n;
    }
    
    private static void split(final char[] array, final int[] array2) {
        final int n = array.length - 1;
        int i = 0;
        int n2 = 0;
        while (i <= n) {
            if (array[i] != '/') {
                break;
            }
            array[i] = '\0';
            ++i;
        }
        while (i <= n) {
            array2[n2++] = i++;
            while (i <= n) {
                if (array[i++] != '/') {
                    continue;
                }
                array[i - 1] = '\0';
                while (i <= n) {
                    if (array[i] != '/') {
                        break;
                    }
                    array[i++] = '\0';
                }
                break;
            }
        }
        if (n2 != array2.length) {
            throw new InternalError();
        }
    }
    
    private static int join(final char[] array, final int[] array2) {
        final int length = array2.length;
        final int n = array.length - 1;
        int n2 = 0;
        if (array[n2] == '\0') {
            array[n2++] = '/';
        }
        for (final int n3 : array2) {
            if (n3 != -1) {
                if (n2 == n3) {
                    while (n2 <= n && array[n2] != '\0') {
                        ++n2;
                    }
                    if (n2 <= n) {
                        array[n2++] = '/';
                    }
                }
                else {
                    if (n2 >= n3) {
                        throw new InternalError();
                    }
                    while (n3 <= n && array[n3] != '\0') {
                        array[n2++] = array[n3++];
                    }
                    if (n3 <= n) {
                        array[n2++] = '/';
                    }
                }
            }
        }
        return n2;
    }
    
    private static void removeDots(final char[] array, final int[] array2) {
        final int length = array2.length;
        final int n = array.length - 1;
        for (int i = 0; i < length; ++i) {
            int n2 = 0;
            do {
                final int n3 = array2[i];
                if (array[n3] == '.') {
                    if (n3 == n) {
                        n2 = 1;
                        break;
                    }
                    if (array[n3 + 1] == '\0') {
                        n2 = 1;
                        break;
                    }
                    if (array[n3 + 1] == '.' && (n3 + 1 == n || array[n3 + 2] == '\0')) {
                        n2 = 2;
                        break;
                    }
                    continue;
                }
            } while (++i < length);
            if (i > length) {
                break;
            }
            if (n2 == 0) {
                break;
            }
            if (n2 == 1) {
                array2[i] = -1;
            }
            else {
                int n4;
                for (n4 = i - 1; n4 >= 0 && array2[n4] == -1; --n4) {}
                if (n4 >= 0) {
                    final int n5 = array2[n4];
                    if (array[n5] != '.' || array[n5 + 1] != '.' || array[n5 + 2] != '\0') {
                        array2[n4] = (array2[i] = -1);
                    }
                }
            }
        }
    }
    
    private static void maybeAddLeadingDot(final char[] array, final int[] array2) {
        if (array[0] == '\0') {
            return;
        }
        int length;
        int n;
        for (length = array2.length, n = 0; n < length && array2[n] < 0; ++n) {}
        if (n >= length || n == 0) {
            return;
        }
        int n2;
        for (n2 = array2[n]; n2 < array.length && array[n2] != ':' && array[n2] != '\0'; ++n2) {}
        if (n2 >= array.length || array[n2] == '\0') {
            return;
        }
        array[0] = '.';
        array[1] = '\0';
        array2[0] = 0;
    }
    
    private static String normalize(final String s) {
        final int needsNormalization = needsNormalization(s);
        if (needsNormalization < 0) {
            return s;
        }
        final char[] charArray = s.toCharArray();
        final int[] array = new int[needsNormalization];
        split(charArray, array);
        removeDots(charArray, array);
        maybeAddLeadingDot(charArray, array);
        final String s2 = new String(charArray, 0, join(charArray, array));
        if (s2.equals(s)) {
            return s;
        }
        return s2;
    }
    
    private static long lowMask(final String s) {
        final int length = s.length();
        long n = 0L;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 < '@') {
                n |= 1L << char1;
            }
        }
        return n;
    }
    
    private static long highMask(final String s) {
        final int length = s.length();
        long n = 0L;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 >= '@' && char1 < '\u0080') {
                n |= 1L << char1 - '@';
            }
        }
        return n;
    }
    
    private static long lowMask(final char c, final char c2) {
        long n = 0L;
        final int max = Math.max(Math.min(c, 63), 0);
        for (int max2 = Math.max(Math.min(c2, 63), 0), i = max; i <= max2; ++i) {
            n |= 1L << i;
        }
        return n;
    }
    
    private static long highMask(final char c, final char c2) {
        long n = 0L;
        final int n2 = Math.max(Math.min(c, 127), 64) - 64;
        for (int n3 = Math.max(Math.min(c2, 127), 64) - 64, i = n2; i <= n3; ++i) {
            n |= 1L << i;
        }
        return n;
    }
    
    private static boolean match(final char c, final long n, final long n2) {
        if (c == '\0') {
            return false;
        }
        if (c < '@') {
            return (1L << c & n) != 0x0L;
        }
        return c < '\u0080' && (1L << c - '@' & n2) != 0x0L;
    }
    
    private static void appendEscape(final StringBuffer sb, final byte b) {
        sb.append('%');
        sb.append(URI.hexDigits[b >> 4 & 0xF]);
        sb.append(URI.hexDigits[b >> 0 & 0xF]);
    }
    
    private static void appendEncoded(final StringBuffer sb, final char c) {
        ByteBuffer encode = null;
        try {
            encode = ThreadLocalCoders.encoderFor("UTF-8").encode(CharBuffer.wrap("" + c));
        }
        catch (CharacterCodingException ex) {
            assert false;
        }
        while (encode.hasRemaining()) {
            final int n = encode.get() & 0xFF;
            if (n >= 128) {
                appendEscape(sb, (byte)n);
            }
            else {
                sb.append((char)n);
            }
        }
    }
    
    private static String quote(final String s, final long n, final long n2) {
        s.length();
        StringBuffer sb = null;
        final boolean b = (n & 0x1L) != 0x0L;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 < '\u0080') {
                if (!match(char1, n, n2)) {
                    if (sb == null) {
                        sb = new StringBuffer();
                        sb.append(s.substring(0, i));
                    }
                    appendEscape(sb, (byte)char1);
                }
                else if (sb != null) {
                    sb.append(char1);
                }
            }
            else if (b && (Character.isSpaceChar(char1) || Character.isISOControl(char1))) {
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append(s.substring(0, i));
                }
                appendEncoded(sb, char1);
            }
            else if (sb != null) {
                sb.append(char1);
            }
        }
        return (sb == null) ? s : sb.toString();
    }
    
    private static String encode(final String s) {
        final int length = s.length();
        if (length == 0) {
            return s;
        }
        int n = 0;
        while (s.charAt(n) < '\u0080') {
            if (++n >= length) {
                return s;
            }
        }
        final String normalize = Normalizer.normalize(s, Normalizer.Form.NFC);
        ByteBuffer encode = null;
        try {
            encode = ThreadLocalCoders.encoderFor("UTF-8").encode(CharBuffer.wrap(normalize));
        }
        catch (CharacterCodingException ex) {
            assert false;
        }
        final StringBuffer sb = new StringBuffer();
        while (encode.hasRemaining()) {
            final int n2 = encode.get() & 0xFF;
            if (n2 >= 128) {
                appendEscape(sb, (byte)n2);
            }
            else {
                sb.append((char)n2);
            }
        }
        return sb.toString();
    }
    
    private static int decode(final char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + '\n';
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A' + '\n';
        }
        assert false;
        return -1;
    }
    
    private static byte decode(final char c, final char c2) {
        return (byte)((decode(c) & 0xF) << 4 | (decode(c2) & 0xF) << 0);
    }
    
    private static String decode(final String s) {
        if (s == null) {
            return s;
        }
        final int length = s.length();
        if (length == 0) {
            return s;
        }
        if (s.indexOf(37) < 0) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(length);
        final ByteBuffer allocate = ByteBuffer.allocate(length);
        final CharBuffer allocate2 = CharBuffer.allocate(length);
        final CharsetDecoder onUnmappableCharacter = ThreadLocalCoders.decoderFor("UTF-8").onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        char c = s.charAt(0);
        int n = 0;
        int i = 0;
    Label_0080:
        while (i < length) {
            assert c == s.charAt(i);
            if (c == '[') {
                n = 1;
            }
            else if (n != 0 && c == ']') {
                n = 0;
            }
            if (c == '%' && n == 0) {
                allocate.clear();
                while (URI.$assertionsDisabled || length - i >= 2) {
                    allocate.put(decode(s.charAt(++i), s.charAt(++i)));
                    if (++i < length) {
                        c = s.charAt(i);
                        if (c == '%') {
                            continue;
                        }
                    }
                    allocate.flip();
                    allocate2.clear();
                    onUnmappableCharacter.reset();
                    final CoderResult decode = onUnmappableCharacter.decode(allocate, allocate2, true);
                    assert decode.isUnderflow();
                    final CoderResult flush = onUnmappableCharacter.flush(allocate2);
                    assert flush.isUnderflow();
                    sb.append(allocate2.flip().toString());
                    continue Label_0080;
                }
                throw new AssertionError();
            }
            sb.append(c);
            if (++i >= length) {
                break;
            }
            c = s.charAt(i);
        }
        return sb.toString();
    }
    
    static {
        L_DIGIT = lowMask('0', '9');
        H_UPALPHA = highMask('A', 'Z');
        H_LOWALPHA = highMask('a', 'z');
        H_ALPHA = (URI.H_LOWALPHA | URI.H_UPALPHA);
        L_ALPHANUM = (URI.L_DIGIT | 0x0L);
        H_ALPHANUM = (0x0L | URI.H_ALPHA);
        L_HEX = URI.L_DIGIT;
        H_HEX = (highMask('A', 'F') | highMask('a', 'f'));
        L_MARK = lowMask("-_.!~*'()");
        H_MARK = highMask("-_.!~*'()");
        L_UNRESERVED = (URI.L_ALPHANUM | URI.L_MARK);
        H_UNRESERVED = (URI.H_ALPHANUM | URI.H_MARK);
        L_RESERVED = lowMask(";/?:@&=+$,[]");
        H_RESERVED = highMask(";/?:@&=+$,[]");
        L_URIC = (URI.L_RESERVED | URI.L_UNRESERVED | 0x1L);
        H_URIC = (URI.H_RESERVED | URI.H_UNRESERVED | 0x0L);
        L_PCHAR = (URI.L_UNRESERVED | 0x1L | lowMask(":@&=+$,"));
        H_PCHAR = (URI.H_UNRESERVED | 0x0L | highMask(":@&=+$,"));
        L_PATH = (URI.L_PCHAR | lowMask(";/"));
        H_PATH = (URI.H_PCHAR | highMask(";/"));
        L_DASH = lowMask("-");
        H_DASH = highMask("-");
        L_DOT = lowMask(".");
        H_DOT = highMask(".");
        L_USERINFO = (URI.L_UNRESERVED | 0x1L | lowMask(";:&=+$,"));
        H_USERINFO = (URI.H_UNRESERVED | 0x0L | highMask(";:&=+$,"));
        L_REG_NAME = (URI.L_UNRESERVED | 0x1L | lowMask("$,;:@&=+"));
        H_REG_NAME = (URI.H_UNRESERVED | 0x0L | highMask("$,;:@&=+"));
        L_SERVER = (URI.L_USERINFO | URI.L_ALPHANUM | URI.L_DASH | lowMask(".:@[]"));
        H_SERVER = (URI.H_USERINFO | URI.H_ALPHANUM | URI.H_DASH | highMask(".:@[]"));
        L_SERVER_PERCENT = (URI.L_SERVER | lowMask("%"));
        H_SERVER_PERCENT = (URI.H_SERVER | highMask("%"));
        L_LEFT_BRACKET = lowMask("[");
        H_LEFT_BRACKET = highMask("[");
        L_SCHEME = (0x0L | URI.L_DIGIT | lowMask("+-."));
        H_SCHEME = (URI.H_ALPHA | 0x0L | highMask("+-."));
        L_URIC_NO_SLASH = (URI.L_UNRESERVED | 0x1L | lowMask(";?:@&=+$,"));
        H_URIC_NO_SLASH = (URI.H_UNRESERVED | 0x0L | highMask(";?:@&=+$,"));
        hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    }
    
    private class Parser
    {
        private String input;
        private boolean requireServerAuthority;
        private int ipv6byteCount;
        
        Parser(final String input) {
            this.requireServerAuthority = false;
            this.ipv6byteCount = 0;
            URI.this.string = (this.input = input);
        }
        
        private void fail(final String s) throws URISyntaxException {
            throw new URISyntaxException(this.input, s);
        }
        
        private void fail(final String s, final int n) throws URISyntaxException {
            throw new URISyntaxException(this.input, s, n);
        }
        
        private void failExpecting(final String s, final int n) throws URISyntaxException {
            this.fail("Expected " + s, n);
        }
        
        private void failExpecting(final String s, final String s2, final int n) throws URISyntaxException {
            this.fail("Expected " + s + " following " + s2, n);
        }
        
        private String substring(final int n, final int n2) {
            return this.input.substring(n, n2);
        }
        
        private char charAt(final int n) {
            return this.input.charAt(n);
        }
        
        private boolean at(final int n, final int n2, final char c) {
            return n < n2 && this.charAt(n) == c;
        }
        
        private boolean at(final int n, final int n2, final String s) {
            int n3 = n;
            final int length = s.length();
            if (length > n2 - n3) {
                return false;
            }
            int n4;
            for (n4 = 0; n4 < length && this.charAt(n3++) == s.charAt(n4); ++n4) {}
            return n4 == length;
        }
        
        private int scan(final int n, final int n2, final char c) {
            if (n < n2 && this.charAt(n) == c) {
                return n + 1;
            }
            return n;
        }
        
        private int scan(final int n, final int n2, final String s, final String s2) {
            int i;
            for (i = n; i < n2; ++i) {
                final char char1 = this.charAt(i);
                if (s.indexOf(char1) >= 0) {
                    return -1;
                }
                if (s2.indexOf(char1) >= 0) {
                    break;
                }
            }
            return i;
        }
        
        private int scanEscape(final int n, final int n2, final char c) throws URISyntaxException {
            if (c == '%') {
                if (n + 3 <= n2 && match(this.charAt(n + 1), URI.L_HEX, URI.H_HEX) && match(this.charAt(n + 2), URI.L_HEX, URI.H_HEX)) {
                    return n + 3;
                }
                this.fail("Malformed escape pair", n);
            }
            else if (c > '\u0080' && !Character.isSpaceChar(c) && !Character.isISOControl(c)) {
                return n + 1;
            }
            return n;
        }
        
        private int scan(final int n, final int n2, final long n3, final long n4) throws URISyntaxException {
            int i = n;
            while (i < n2) {
                final char char1 = this.charAt(i);
                if (match(char1, n3, n4)) {
                    ++i;
                }
                else {
                    if ((n3 & 0x1L) == 0x0L) {
                        break;
                    }
                    final int scanEscape = this.scanEscape(i, n2, char1);
                    if (scanEscape <= i) {
                        break;
                    }
                    i = scanEscape;
                }
            }
            return i;
        }
        
        private void checkChars(final int n, final int n2, final long n3, final long n4, final String s) throws URISyntaxException {
            final int scan = this.scan(n, n2, n3, n4);
            if (scan < n2) {
                this.fail("Illegal character in " + s, scan);
            }
        }
        
        private void checkChar(final int n, final long n2, final long n3, final String s) throws URISyntaxException {
            this.checkChars(n, n + 1, n2, n3, s);
        }
        
        void parse(final boolean requireServerAuthority) throws URISyntaxException {
            this.requireServerAuthority = requireServerAuthority;
            final int length = this.input.length();
            int scan = this.scan(0, length, "/?#", ":");
            int n;
            int n2;
            if (scan >= 0 && this.at(scan, length, ':')) {
                if (scan == 0) {
                    this.failExpecting("scheme name", 0);
                }
                this.checkChar(0, 0L, URI.H_ALPHA, "scheme name");
                this.checkChars(1, scan, URI.L_SCHEME, URI.H_SCHEME, "scheme name");
                URI.this.scheme = this.substring(0, scan);
                n = ++scan;
                if (this.at(scan, length, '/')) {
                    n2 = this.parseHierarchical(scan, length);
                }
                else {
                    final int scan2 = this.scan(scan, length, "", "#");
                    if (scan2 <= scan) {
                        this.failExpecting("scheme-specific part", scan);
                    }
                    this.checkChars(scan, scan2, URI.L_URIC, URI.H_URIC, "opaque part");
                    n2 = scan2;
                }
            }
            else {
                n = 0;
                n2 = this.parseHierarchical(0, length);
            }
            URI.this.schemeSpecificPart = this.substring(n, n2);
            if (this.at(n2, length, '#')) {
                this.checkChars(n2 + 1, length, URI.L_URIC, URI.H_URIC, "fragment");
                URI.this.fragment = this.substring(n2 + 1, length);
                n2 = length;
            }
            if (n2 < length) {
                this.fail("end of URI", n2);
            }
        }
        
        private int parseHierarchical(final int n, final int n2) throws URISyntaxException {
            int authority = n;
            if (this.at(authority, n2, '/') && this.at(authority + 1, n2, '/')) {
                authority += 2;
                final int scan = this.scan(authority, n2, "", "/?#");
                if (scan > authority) {
                    authority = this.parseAuthority(authority, scan);
                }
                else if (scan >= n2) {
                    this.failExpecting("authority", authority);
                }
            }
            final int scan2 = this.scan(authority, n2, "", "?#");
            this.checkChars(authority, scan2, URI.L_PATH, URI.H_PATH, "path");
            URI.this.path = this.substring(authority, scan2);
            int n3 = scan2;
            if (this.at(n3, n2, '?')) {
                ++n3;
                final int scan3 = this.scan(n3, n2, "", "#");
                this.checkChars(n3, scan3, URI.L_URIC, URI.H_URIC, "query");
                URI.this.query = this.substring(n3, scan3);
                n3 = scan3;
            }
            return n3;
        }
        
        private int parseAuthority(final int n, final int n2) throws URISyntaxException {
            int server = n;
            URISyntaxException ex = null;
            boolean b;
            if (this.scan(n, n2, "", "]") > n) {
                b = (this.scan(n, n2, URI.L_SERVER_PERCENT, URI.H_SERVER_PERCENT) == n2);
            }
            else {
                b = (this.scan(n, n2, URI.L_SERVER, URI.H_SERVER) == n2);
            }
            final boolean b2 = this.scan(n, n2, URI.L_REG_NAME, URI.H_REG_NAME) == n2;
            if (b2 && !b) {
                URI.this.authority = this.substring(n, n2);
                return n2;
            }
            if (b) {
                try {
                    server = this.parseServer(n, n2);
                    if (server < n2) {
                        this.failExpecting("end of authority", server);
                    }
                    URI.this.authority = this.substring(n, n2);
                }
                catch (URISyntaxException ex2) {
                    URI.this.userInfo = null;
                    URI.this.host = null;
                    URI.this.port = -1;
                    if (this.requireServerAuthority) {
                        throw ex2;
                    }
                    ex = ex2;
                    server = n;
                }
            }
            if (server < n2) {
                if (b2) {
                    URI.this.authority = this.substring(n, n2);
                }
                else {
                    if (ex != null) {
                        throw ex;
                    }
                    this.fail("Illegal character in authority", server);
                }
            }
            return n2;
        }
        
        private int parseServer(final int n, final int n2) throws URISyntaxException {
            int n3 = n;
            final int scan = this.scan(n3, n2, "/?#", "@");
            if (scan >= n3 && this.at(scan, n2, '@')) {
                this.checkChars(n3, scan, URI.L_USERINFO, URI.H_USERINFO, "user info");
                URI.this.userInfo = this.substring(n3, scan);
                n3 = scan + 1;
            }
            if (this.at(n3, n2, '[')) {
                ++n3;
                final int scan2 = this.scan(n3, n2, "/?#", "]");
                if (scan2 > n3 && this.at(scan2, n2, ']')) {
                    final int scan3 = this.scan(n3, scan2, "", "%");
                    if (scan3 > n3) {
                        this.parseIPv6Reference(n3, scan3);
                        if (scan3 + 1 == scan2) {
                            this.fail("scope id expected");
                        }
                        this.checkChars(scan3 + 1, scan2, URI.L_ALPHANUM, URI.H_ALPHANUM, "scope id");
                    }
                    else {
                        this.parseIPv6Reference(n3, scan2);
                    }
                    URI.this.host = this.substring(n3 - 1, scan2 + 1);
                    n3 = scan2 + 1;
                }
                else {
                    this.failExpecting("closing bracket for IPv6 address", scan2);
                }
            }
            else {
                int n4 = this.parseIPv4Address(n3, n2);
                if (n4 <= n3) {
                    n4 = this.parseHostname(n3, n2);
                }
                n3 = n4;
            }
            if (this.at(n3, n2, ':')) {
                ++n3;
                final int scan4 = this.scan(n3, n2, "", "/");
                if (scan4 > n3) {
                    this.checkChars(n3, scan4, URI.L_DIGIT, 0L, "port number");
                    try {
                        URI.this.port = Integer.parseInt(this.substring(n3, scan4));
                    }
                    catch (NumberFormatException ex) {
                        this.fail("Malformed port number", n3);
                    }
                    n3 = scan4;
                }
            }
            if (n3 < n2) {
                this.failExpecting("port number", n3);
            }
            return n3;
        }
        
        private int scanByte(final int n, final int n2) throws URISyntaxException {
            final int scan = this.scan(n, n2, URI.L_DIGIT, 0L);
            if (scan <= n) {
                return scan;
            }
            if (Integer.parseInt(this.substring(n, scan)) > 255) {
                return n;
            }
            return scan;
        }
        
        private int scanIPv4Address(final int n, final int n2, final boolean b) throws URISyntaxException {
            final int scan = this.scan(n, n2, URI.L_DIGIT | URI.L_DOT, 0x0L | URI.H_DOT);
            if (scan <= n || (b && scan != n2)) {
                return -1;
            }
            int n3;
            if ((n3 = this.scanByte(n, scan)) > n) {
                final int n4 = n3;
                if ((n3 = this.scan(n4, scan, '.')) > n4) {
                    final int n5 = n3;
                    if ((n3 = this.scanByte(n5, scan)) > n5) {
                        final int n6 = n3;
                        if ((n3 = this.scan(n6, scan, '.')) > n6) {
                            final int n7 = n3;
                            if ((n3 = this.scanByte(n7, scan)) > n7) {
                                final int n8 = n3;
                                if ((n3 = this.scan(n8, scan, '.')) > n8) {
                                    final int n9 = n3;
                                    if ((n3 = this.scanByte(n9, scan)) > n9) {
                                        if (n3 >= scan) {
                                            return n3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.fail("Malformed IPv4 address", n3);
            return -1;
        }
        
        private int takeIPv4Address(final int n, final int n2, final String s) throws URISyntaxException {
            final int scanIPv4Address = this.scanIPv4Address(n, n2, true);
            if (scanIPv4Address <= n) {
                this.failExpecting(s, n);
            }
            return scanIPv4Address;
        }
        
        private int parseIPv4Address(final int n, final int n2) {
            int scanIPv4Address;
            try {
                scanIPv4Address = this.scanIPv4Address(n, n2, false);
            }
            catch (URISyntaxException ex) {
                return -1;
            }
            catch (NumberFormatException ex2) {
                return -1;
            }
            if (scanIPv4Address > n && scanIPv4Address < n2 && this.charAt(scanIPv4Address) != ':') {
                scanIPv4Address = -1;
            }
            if (scanIPv4Address > n) {
                URI.this.host = this.substring(n, scanIPv4Address);
            }
            return scanIPv4Address;
        }
        
        private int parseHostname(final int n, final int n2) throws URISyntaxException {
            int i = n;
            int n3 = -1;
            do {
                final int scan = this.scan(i, n2, URI.L_ALPHANUM, URI.H_ALPHANUM);
                if (scan <= i) {
                    break;
                }
                if (scan > (n3 = i)) {
                    i = scan;
                    final int scan2 = this.scan(i, n2, URI.L_ALPHANUM | URI.L_DASH, URI.H_ALPHANUM | URI.H_DASH);
                    if (scan2 > i) {
                        if (this.charAt(scan2 - 1) == '-') {
                            this.fail("Illegal character in hostname", scan2 - 1);
                        }
                        i = scan2;
                    }
                }
                final int scan3 = this.scan(i, n2, '.');
                if (scan3 <= i) {
                    break;
                }
                i = scan3;
            } while (i < n2);
            if (i < n2 && !this.at(i, n2, ':')) {
                this.fail("Illegal character in hostname", i);
            }
            if (n3 < 0) {
                this.failExpecting("hostname", n);
            }
            if (n3 > n && !match(this.charAt(n3), 0L, URI.H_ALPHA)) {
                this.fail("Illegal character in hostname", n3);
            }
            URI.this.host = this.substring(n, i);
            return i;
        }
        
        private int parseIPv6Reference(final int n, final int n2) throws URISyntaxException {
            int n3 = n;
            boolean b = false;
            final int scanHexSeq = this.scanHexSeq(n3, n2);
            if (scanHexSeq > n3) {
                n3 = scanHexSeq;
                if (this.at(n3, n2, "::")) {
                    b = true;
                    n3 = this.scanHexPost(n3 + 2, n2);
                }
                else if (this.at(n3, n2, ':')) {
                    n3 = this.takeIPv4Address(n3 + 1, n2, "IPv4 address");
                    this.ipv6byteCount += 4;
                }
            }
            else if (this.at(n3, n2, "::")) {
                b = true;
                n3 = this.scanHexPost(n3 + 2, n2);
            }
            if (n3 < n2) {
                this.fail("Malformed IPv6 address", n);
            }
            if (this.ipv6byteCount > 16) {
                this.fail("IPv6 address too long", n);
            }
            if (!b && this.ipv6byteCount < 16) {
                this.fail("IPv6 address too short", n);
            }
            if (b && this.ipv6byteCount == 16) {
                this.fail("Malformed IPv6 address", n);
            }
            return n3;
        }
        
        private int scanHexPost(final int n, final int n2) throws URISyntaxException {
            if (n == n2) {
                return n;
            }
            final int scanHexSeq = this.scanHexSeq(n, n2);
            int n3;
            if (scanHexSeq > n) {
                n3 = scanHexSeq;
                if (this.at(n3, n2, ':')) {
                    ++n3;
                    n3 = this.takeIPv4Address(n3, n2, "hex digits or IPv4 address");
                    this.ipv6byteCount += 4;
                }
            }
            else {
                n3 = this.takeIPv4Address(n, n2, "hex digits or IPv4 address");
                this.ipv6byteCount += 4;
            }
            return n3;
        }
        
        private int scanHexSeq(final int n, final int n2) throws URISyntaxException {
            final int scan = this.scan(n, n2, URI.L_HEX, URI.H_HEX);
            if (scan <= n) {
                return -1;
            }
            if (this.at(scan, n2, '.')) {
                return -1;
            }
            if (scan > n + 4) {
                this.fail("IPv6 hexadecimal digit sequence too long", n);
            }
            this.ipv6byteCount += 2;
            int i;
            int scan2;
            for (i = scan; i < n2; i = scan2) {
                if (!this.at(i, n2, ':')) {
                    break;
                }
                if (this.at(i + 1, n2, ':')) {
                    break;
                }
                ++i;
                scan2 = this.scan(i, n2, URI.L_HEX, URI.H_HEX);
                if (scan2 <= i) {
                    this.failExpecting("digits for an IPv6 address", i);
                }
                if (this.at(scan2, n2, '.')) {
                    --i;
                    break;
                }
                if (scan2 > i + 4) {
                    this.fail("IPv6 hexadecimal digit sequence too long", i);
                }
                this.ipv6byteCount += 2;
            }
            return i;
        }
    }
}
