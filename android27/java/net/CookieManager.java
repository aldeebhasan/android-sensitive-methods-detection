package java.net;

import java.io.*;
import sun.util.logging.*;
import java.util.*;

public class CookieManager extends CookieHandler
{
    private CookiePolicy policyCallback;
    private CookieStore cookieJar;
    
    public CookieManager() {
        this(null, null);
    }
    
    public CookieManager(final CookieStore cookieJar, final CookiePolicy cookiePolicy) {
        this.cookieJar = null;
        this.policyCallback = ((cookiePolicy == null) ? CookiePolicy.ACCEPT_ORIGINAL_SERVER : cookiePolicy);
        if (cookieJar == null) {
            this.cookieJar = new InMemoryCookieStore();
        }
        else {
            this.cookieJar = cookieJar;
        }
    }
    
    public void setCookiePolicy(final CookiePolicy policyCallback) {
        if (policyCallback != null) {
            this.policyCallback = policyCallback;
        }
    }
    
    public CookieStore getCookieStore() {
        return this.cookieJar;
    }
    
    @Override
    public Map<String, List<String>> get(final URI uri, final Map<String, List<String>> map) throws IOException {
        if (uri == null || map == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        final HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        if (this.cookieJar == null) {
            return (Map<String, List<String>>)Collections.unmodifiableMap((Map<?, ?>)hashMap);
        }
        final boolean equalsIgnoreCase = "https".equalsIgnoreCase(uri.getScheme());
        final ArrayList<HttpCookie> list = new ArrayList<HttpCookie>();
        String path = uri.getPath();
        if (path == null || path.isEmpty()) {
            path = "/";
        }
        for (final HttpCookie httpCookie : this.cookieJar.get(uri)) {
            if (this.pathMatches(path, httpCookie.getPath()) && (equalsIgnoreCase || !httpCookie.getSecure())) {
                if (httpCookie.isHttpOnly()) {
                    final String scheme = uri.getScheme();
                    if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                        continue;
                    }
                }
                final String portlist = httpCookie.getPortlist();
                if (portlist != null && !portlist.isEmpty()) {
                    int port = uri.getPort();
                    if (port == -1) {
                        port = ("https".equals(uri.getScheme()) ? 443 : 80);
                    }
                    if (!isInPortList(portlist, port)) {
                        continue;
                    }
                    list.add(httpCookie);
                }
                else {
                    list.add(httpCookie);
                }
            }
        }
        hashMap.put("Cookie", this.sortByPath(list));
        return (Map<String, List<String>>)Collections.unmodifiableMap((Map<?, ?>)hashMap);
    }
    
    @Override
    public void put(final URI uri, final Map<String, List<String>> map) throws IOException {
        if (uri == null || map == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (this.cookieJar == null) {
            return;
        }
        final PlatformLogger logger = PlatformLogger.getLogger("java.net.CookieManager");
        for (final String s : map.keySet()) {
            if (s != null) {
                if (!s.equalsIgnoreCase("Set-Cookie2") && !s.equalsIgnoreCase("Set-Cookie")) {
                    continue;
                }
                for (final String s2 : map.get(s)) {
                    try {
                        List<HttpCookie> list;
                        try {
                            list = HttpCookie.parse(s2);
                        }
                        catch (IllegalArgumentException ex) {
                            list = Collections.emptyList();
                            if (logger.isLoggable(PlatformLogger.Level.SEVERE)) {
                                logger.severe("Invalid cookie for " + uri + ": " + s2);
                            }
                        }
                        for (final HttpCookie httpCookie : list) {
                            if (httpCookie.getPath() == null) {
                                String path = uri.getPath();
                                if (!path.endsWith("/")) {
                                    final int lastIndex = path.lastIndexOf("/");
                                    if (lastIndex > 0) {
                                        path = path.substring(0, lastIndex + 1);
                                    }
                                    else {
                                        path = "/";
                                    }
                                }
                                httpCookie.setPath(path);
                            }
                            if (httpCookie.getDomain() == null) {
                                String domain = uri.getHost();
                                if (domain != null && !domain.contains(".")) {
                                    domain += ".local";
                                }
                                httpCookie.setDomain(domain);
                            }
                            final String portlist = httpCookie.getPortlist();
                            if (portlist != null) {
                                int port = uri.getPort();
                                if (port == -1) {
                                    port = ("https".equals(uri.getScheme()) ? 443 : 80);
                                }
                                if (portlist.isEmpty()) {
                                    httpCookie.setPortlist("" + port);
                                    if (!this.shouldAcceptInternal(uri, httpCookie)) {
                                        continue;
                                    }
                                    this.cookieJar.add(uri, httpCookie);
                                }
                                else {
                                    if (!isInPortList(portlist, port) || !this.shouldAcceptInternal(uri, httpCookie)) {
                                        continue;
                                    }
                                    this.cookieJar.add(uri, httpCookie);
                                }
                            }
                            else {
                                if (!this.shouldAcceptInternal(uri, httpCookie)) {
                                    continue;
                                }
                                this.cookieJar.add(uri, httpCookie);
                            }
                        }
                    }
                    catch (IllegalArgumentException ex2) {}
                }
            }
        }
    }
    
    private boolean shouldAcceptInternal(final URI uri, final HttpCookie httpCookie) {
        try {
            return this.policyCallback.shouldAccept(uri, httpCookie);
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    private static boolean isInPortList(String substring, final int n) {
        for (int i = substring.indexOf(","); i > 0; i = substring.indexOf(",")) {
            try {
                if (Integer.parseInt(substring.substring(0, i)) == n) {
                    return true;
                }
            }
            catch (NumberFormatException ex) {}
            substring = substring.substring(i + 1);
        }
        if (!substring.isEmpty()) {
            try {
                if (Integer.parseInt(substring) == n) {
                    return true;
                }
            }
            catch (NumberFormatException ex2) {}
        }
        return false;
    }
    
    private boolean pathMatches(final String s, final String s2) {
        return s == s2 || (s != null && s2 != null && s.startsWith(s2));
    }
    
    private List<String> sortByPath(final List<HttpCookie> list) {
        Collections.sort((List<Object>)list, (Comparator<? super Object>)new CookiePathComparator());
        final ArrayList<String> list2 = new ArrayList<String>();
        for (final HttpCookie httpCookie : list) {
            if (list.indexOf(httpCookie) == 0 && httpCookie.getVersion() > 0) {
                list2.add("$Version=\"1\"");
            }
            list2.add(httpCookie.toString());
        }
        return list2;
    }
    
    static class CookiePathComparator implements Comparator<HttpCookie>
    {
        @Override
        public int compare(final HttpCookie httpCookie, final HttpCookie httpCookie2) {
            if (httpCookie == httpCookie2) {
                return 0;
            }
            if (httpCookie == null) {
                return -1;
            }
            if (httpCookie2 == null) {
                return 1;
            }
            if (!httpCookie.getName().equals(httpCookie2.getName())) {
                return 0;
            }
            if (httpCookie.getPath().startsWith(httpCookie2.getPath())) {
                return -1;
            }
            if (httpCookie2.getPath().startsWith(httpCookie.getPath())) {
                return 1;
            }
            return 0;
        }
    }
}
