package java.net;

import java.io.*;
import sun.net.util.*;

public abstract class URLStreamHandler
{
    protected abstract URLConnection openConnection(final URL p0) throws IOException;
    
    protected URLConnection openConnection(final URL url, final Proxy proxy) throws IOException {
        throw new UnsupportedOperationException("Method not implemented.");
    }
    
    protected void parseURL(final URL url, String substring, int n, int n2) {
        final String protocol = url.getProtocol();
        String authority = url.getAuthority();
        String s = url.getUserInfo();
        String s2 = url.getHost();
        int n3 = url.getPort();
        String s3 = url.getPath();
        String s4 = url.getQuery();
        final String ref = url.getRef();
        boolean b = false;
        boolean b2 = false;
        if (n < n2) {
            final int index = substring.indexOf(63);
            b2 = (index == n);
            if (index != -1 && index < n2) {
                s4 = substring.substring(index + 1, n2);
                if (n2 > index) {
                    n2 = index;
                }
                substring = substring.substring(0, index);
            }
        }
        if ((n > n2 - 4 || substring.charAt(n) != '/' || substring.charAt(n + 1) != '/' || substring.charAt(n + 2) != '/' || substring.charAt(n + 3) != '/') && n <= n2 - 2 && substring.charAt(n) == '/' && substring.charAt(n + 1) == '/') {
            n += 2;
            int n4 = substring.indexOf(47, n);
            if (n4 < 0 || n4 > n2) {
                n4 = substring.indexOf(63, n);
                if (n4 < 0 || n4 > n2) {
                    n4 = n2;
                }
            }
            authority = (s2 = substring.substring(n, n4));
            final int index2 = authority.indexOf(64);
            if (index2 != -1) {
                if (index2 != authority.lastIndexOf(64)) {
                    s = null;
                    s2 = null;
                }
                else {
                    s = authority.substring(0, index2);
                    s2 = authority.substring(index2 + 1);
                }
            }
            else {
                s = null;
            }
            if (s2 != null) {
                if (s2.length() > 0 && s2.charAt(0) == '[') {
                    int index3;
                    if ((index3 = s2.indexOf(93)) <= 2) {
                        throw new IllegalArgumentException("Invalid authority field: " + authority);
                    }
                    final String s5 = s2;
                    s2 = s5.substring(0, index3 + 1);
                    if (!IPAddressUtil.isIPv6LiteralAddress(s2.substring(1, index3))) {
                        throw new IllegalArgumentException("Invalid host: " + s2);
                    }
                    n3 = -1;
                    if (s5.length() > index3 + 1) {
                        if (s5.charAt(index3 + 1) != ':') {
                            throw new IllegalArgumentException("Invalid authority field: " + authority);
                        }
                        ++index3;
                        if (s5.length() > index3 + 1) {
                            n3 = Integer.parseInt(s5.substring(index3 + 1));
                        }
                    }
                }
                else {
                    final int index4 = s2.indexOf(58);
                    n3 = -1;
                    if (index4 >= 0) {
                        if (s2.length() > index4 + 1) {
                            n3 = Integer.parseInt(s2.substring(index4 + 1));
                        }
                        s2 = s2.substring(0, index4);
                    }
                }
            }
            else {
                s2 = "";
            }
            if (n3 < -1) {
                throw new IllegalArgumentException("Invalid port number :" + n3);
            }
            n = n4;
            if (authority != null && authority.length() > 0) {
                s3 = "";
            }
        }
        if (s2 == null) {
            s2 = "";
        }
        if (n < n2) {
            if (substring.charAt(n) == '/') {
                s3 = substring.substring(n, n2);
            }
            else if (s3 != null && s3.length() > 0) {
                b = true;
                final int lastIndex = s3.lastIndexOf(47);
                String s6 = "";
                if (lastIndex == -1 && authority != null) {
                    s6 = "/";
                }
                s3 = s3.substring(0, lastIndex + 1) + s6 + substring.substring(n, n2);
            }
            else {
                s3 = ((authority != null) ? "/" : "") + substring.substring(n, n2);
            }
        }
        else if (b2 && s3 != null) {
            int lastIndex2 = s3.lastIndexOf(47);
            if (lastIndex2 < 0) {
                lastIndex2 = 0;
            }
            s3 = s3.substring(0, lastIndex2) + "/";
        }
        if (s3 == null) {
            s3 = "";
        }
        if (b) {
            int index5;
            while ((index5 = s3.indexOf("/./")) >= 0) {
                s3 = s3.substring(0, index5) + s3.substring(index5 + 2);
            }
            int n5 = 0;
            int index6;
            while ((index6 = s3.indexOf("/../", n5)) >= 0) {
                if (index6 > 0 && (n2 = s3.lastIndexOf(47, index6 - 1)) >= 0 && s3.indexOf("/../", n2) != 0) {
                    s3 = s3.substring(0, n2) + s3.substring(index6 + 3);
                    n5 = 0;
                }
                else {
                    n5 = index6 + 3;
                }
            }
            while (s3.endsWith("/..") && (n2 = s3.lastIndexOf(47, s3.indexOf("/..") - 1)) >= 0) {
                s3 = s3.substring(0, n2 + 1);
            }
            if (s3.startsWith("./") && s3.length() > 2) {
                s3 = s3.substring(2);
            }
            if (s3.endsWith("/.")) {
                s3 = s3.substring(0, s3.length() - 1);
            }
        }
        this.setURL(url, protocol, s2, n3, authority, s, s3, s4, ref);
    }
    
    protected int getDefaultPort() {
        return -1;
    }
    
    protected boolean equals(final URL url, final URL url2) {
        final String ref = url.getRef();
        final String ref2 = url2.getRef();
        return (ref == ref2 || (ref != null && ref.equals(ref2))) && this.sameFile(url, url2);
    }
    
    protected int hashCode(final URL url) {
        int n = 0;
        final String protocol = url.getProtocol();
        if (protocol != null) {
            n += protocol.hashCode();
        }
        final InetAddress hostAddress = this.getHostAddress(url);
        if (hostAddress != null) {
            n += hostAddress.hashCode();
        }
        else {
            final String host = url.getHost();
            if (host != null) {
                n += host.toLowerCase().hashCode();
            }
        }
        final String file = url.getFile();
        if (file != null) {
            n += file.hashCode();
        }
        int n2;
        if (url.getPort() == -1) {
            n2 = n + this.getDefaultPort();
        }
        else {
            n2 = n + url.getPort();
        }
        final String ref = url.getRef();
        if (ref != null) {
            n2 += ref.hashCode();
        }
        return n2;
    }
    
    protected boolean sameFile(final URL url, final URL url2) {
        return (url.getProtocol() == url2.getProtocol() || (url.getProtocol() != null && url.getProtocol().equalsIgnoreCase(url2.getProtocol()))) && (url.getFile() == url2.getFile() || (url.getFile() != null && url.getFile().equals(url2.getFile()))) && ((url.getPort() != -1) ? url.getPort() : url.handler.getDefaultPort()) == ((url2.getPort() != -1) ? url2.getPort() : url2.handler.getDefaultPort()) && this.hostsEqual(url, url2);
    }
    
    protected synchronized InetAddress getHostAddress(final URL url) {
        if (url.hostAddress != null) {
            return url.hostAddress;
        }
        final String host = url.getHost();
        if (host == null || host.equals("")) {
            return null;
        }
        try {
            url.hostAddress = InetAddress.getByName(host);
        }
        catch (UnknownHostException ex) {
            return null;
        }
        catch (SecurityException ex2) {
            return null;
        }
        return url.hostAddress;
    }
    
    protected boolean hostsEqual(final URL url, final URL url2) {
        final InetAddress hostAddress = this.getHostAddress(url);
        final InetAddress hostAddress2 = this.getHostAddress(url2);
        if (hostAddress != null && hostAddress2 != null) {
            return hostAddress.equals(hostAddress2);
        }
        if (url.getHost() != null && url2.getHost() != null) {
            return url.getHost().equalsIgnoreCase(url2.getHost());
        }
        return url.getHost() == null && url2.getHost() == null;
    }
    
    protected String toExternalForm(final URL url) {
        int n = url.getProtocol().length() + 1;
        if (url.getAuthority() != null && url.getAuthority().length() > 0) {
            n += 2 + url.getAuthority().length();
        }
        if (url.getPath() != null) {
            n += url.getPath().length();
        }
        if (url.getQuery() != null) {
            n += 1 + url.getQuery().length();
        }
        if (url.getRef() != null) {
            n += 1 + url.getRef().length();
        }
        final StringBuffer sb = new StringBuffer(n);
        sb.append(url.getProtocol());
        sb.append(":");
        if (url.getAuthority() != null && url.getAuthority().length() > 0) {
            sb.append("//");
            sb.append(url.getAuthority());
        }
        if (url.getPath() != null) {
            sb.append(url.getPath());
        }
        if (url.getQuery() != null) {
            sb.append('?');
            sb.append(url.getQuery());
        }
        if (url.getRef() != null) {
            sb.append("#");
            sb.append(url.getRef());
        }
        return sb.toString();
    }
    
    protected void setURL(final URL url, final String s, final String s2, final int n, final String s3, final String s4, final String s5, final String s6, final String s7) {
        if (this != url.handler) {
            throw new SecurityException("handler for url different from this handler");
        }
        if (s2 != null && url.isBuiltinStreamHandler(this)) {
            final String checkHostString = IPAddressUtil.checkHostString(s2);
            if (checkHostString != null) {
                throw new IllegalArgumentException(checkHostString);
            }
        }
        url.set(url.getProtocol(), s2, n, s3, s4, s5, s6, s7);
    }
    
    @Deprecated
    protected void setURL(final URL url, final String s, String substring, final int n, final String s2, final String s3) {
        String s4 = null;
        String substring2 = null;
        if (substring != null && substring.length() != 0) {
            s4 = ((n == -1) ? substring : (substring + ":" + n));
            final int lastIndex = substring.lastIndexOf(64);
            if (lastIndex != -1) {
                substring2 = substring.substring(0, lastIndex);
                substring = substring.substring(lastIndex + 1);
            }
        }
        String substring3 = null;
        String substring4 = null;
        if (s2 != null) {
            final int lastIndex2 = s2.lastIndexOf(63);
            if (lastIndex2 != -1) {
                substring4 = s2.substring(lastIndex2 + 1);
                substring3 = s2.substring(0, lastIndex2);
            }
            else {
                substring3 = s2;
            }
        }
        this.setURL(url, s, substring, n, s4, substring2, substring3, substring4, s3);
    }
}
