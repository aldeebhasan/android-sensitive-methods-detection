package java.net;

import java.text.*;
import java.util.*;
import sun.misc.*;

public final class HttpCookie implements Cloneable
{
    private final String name;
    private String value;
    private String comment;
    private String commentURL;
    private boolean toDiscard;
    private String domain;
    private long maxAge;
    private String path;
    private String portlist;
    private boolean secure;
    private boolean httpOnly;
    private int version;
    private final String header;
    private final long whenCreated;
    private static final long MAX_AGE_UNSPECIFIED = -1L;
    private static final String[] COOKIE_DATE_FORMATS;
    private static final String SET_COOKIE = "set-cookie:";
    private static final String SET_COOKIE2 = "set-cookie2:";
    private static final String tspecials = ",; ";
    static final Map<String, CookieAttributeAssignor> assignors;
    static final TimeZone GMT;
    
    public HttpCookie(final String s, final String s2) {
        this(s, s2, null);
    }
    
    private HttpCookie(String trim, final String value, final String header) {
        this.maxAge = -1L;
        this.version = 1;
        trim = trim.trim();
        if (trim.length() == 0 || !isToken(trim) || trim.charAt(0) == '$') {
            throw new IllegalArgumentException("Illegal cookie name");
        }
        this.name = trim;
        this.value = value;
        this.toDiscard = false;
        this.secure = false;
        this.whenCreated = System.currentTimeMillis();
        this.portlist = null;
        this.header = header;
    }
    
    public static List<HttpCookie> parse(final String s) {
        return parse(s, false);
    }
    
    private static List<HttpCookie> parse(String s, final boolean b) {
        final int guessCookieVersion = guessCookieVersion(s);
        if (startsWithIgnoreCase(s, "set-cookie2:")) {
            s = s.substring("set-cookie2:".length());
        }
        else if (startsWithIgnoreCase(s, "set-cookie:")) {
            s = s.substring("set-cookie:".length());
        }
        final ArrayList<HttpCookie> list = new ArrayList<HttpCookie>();
        if (guessCookieVersion == 0) {
            final HttpCookie internal = parseInternal(s, b);
            internal.setVersion(0);
            list.add(internal);
        }
        else {
            final Iterator<String> iterator = splitMultiCookies(s).iterator();
            while (iterator.hasNext()) {
                final HttpCookie internal2 = parseInternal(iterator.next(), b);
                internal2.setVersion(1);
                list.add(internal2);
            }
        }
        return list;
    }
    
    public boolean hasExpired() {
        return this.maxAge == 0L || (this.maxAge != -1L && (System.currentTimeMillis() - this.whenCreated) / 1000L > this.maxAge);
    }
    
    public void setComment(final String comment) {
        this.comment = comment;
    }
    
    public String getComment() {
        return this.comment;
    }
    
    public void setCommentURL(final String commentURL) {
        this.commentURL = commentURL;
    }
    
    public String getCommentURL() {
        return this.commentURL;
    }
    
    public void setDiscard(final boolean toDiscard) {
        this.toDiscard = toDiscard;
    }
    
    public boolean getDiscard() {
        return this.toDiscard;
    }
    
    public void setPortlist(final String portlist) {
        this.portlist = portlist;
    }
    
    public String getPortlist() {
        return this.portlist;
    }
    
    public void setDomain(final String domain) {
        if (domain != null) {
            this.domain = domain.toLowerCase();
        }
        else {
            this.domain = domain;
        }
    }
    
    public String getDomain() {
        return this.domain;
    }
    
    public void setMaxAge(final long maxAge) {
        this.maxAge = maxAge;
    }
    
    public long getMaxAge() {
        return this.maxAge;
    }
    
    public void setPath(final String path) {
        this.path = path;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public void setSecure(final boolean secure) {
        this.secure = secure;
    }
    
    public boolean getSecure() {
        return this.secure;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(final int version) {
        if (version != 0 && version != 1) {
            throw new IllegalArgumentException("cookie version should be 0 or 1");
        }
        this.version = version;
    }
    
    public boolean isHttpOnly() {
        return this.httpOnly;
    }
    
    public void setHttpOnly(final boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
    
    public static boolean domainMatches(final String s, final String s2) {
        if (s == null || s2 == null) {
            return false;
        }
        final boolean equalsIgnoreCase = ".local".equalsIgnoreCase(s);
        int n = s.indexOf(46);
        if (n == 0) {
            n = s.indexOf(46, 1);
        }
        if (!equalsIgnoreCase && (n == -1 || n == s.length() - 1)) {
            return false;
        }
        if (s2.indexOf(46) == -1 && (equalsIgnoreCase || s.equalsIgnoreCase(s2 + ".local"))) {
            return true;
        }
        final int n2 = s2.length() - s.length();
        if (n2 == 0) {
            return s2.equalsIgnoreCase(s);
        }
        if (n2 > 0) {
            final String substring = s2.substring(0, n2);
            final String substring2 = s2.substring(n2);
            return substring.indexOf(46) == -1 && substring2.equalsIgnoreCase(s);
        }
        return n2 == -1 && s.charAt(0) == '.' && s2.equalsIgnoreCase(s.substring(1));
    }
    
    @Override
    public String toString() {
        if (this.getVersion() > 0) {
            return this.toRFC2965HeaderString();
        }
        return this.toNetscapeHeaderString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HttpCookie)) {
            return false;
        }
        final HttpCookie httpCookie = (HttpCookie)o;
        return equalsIgnoreCase(this.getName(), httpCookie.getName()) && equalsIgnoreCase(this.getDomain(), httpCookie.getDomain()) && Objects.equals(this.getPath(), httpCookie.getPath());
    }
    
    @Override
    public int hashCode() {
        return this.name.toLowerCase().hashCode() + ((this.domain != null) ? this.domain.toLowerCase().hashCode() : 0) + ((this.path != null) ? this.path.hashCode() : 0);
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    private static boolean isToken(final String s) {
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 < ' ' || char1 >= '\u007f' || ",; ".indexOf(char1) != -1) {
                return false;
            }
        }
        return true;
    }
    
    private static HttpCookie parseInternal(final String s, final boolean b) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, ";");
        HttpCookie httpCookie;
        try {
            final String nextToken = stringTokenizer.nextToken();
            final int index = nextToken.indexOf(61);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid cookie name-value pair");
            }
            final String trim = nextToken.substring(0, index).trim();
            final String trim2 = nextToken.substring(index + 1).trim();
            if (b) {
                httpCookie = new HttpCookie(trim, stripOffSurroundingQuote(trim2), s);
            }
            else {
                httpCookie = new HttpCookie(trim, stripOffSurroundingQuote(trim2));
            }
        }
        catch (NoSuchElementException ex) {
            throw new IllegalArgumentException("Empty cookie header string");
        }
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken2 = stringTokenizer.nextToken();
            final int index2 = nextToken2.indexOf(61);
            String s2;
            String trim3;
            if (index2 != -1) {
                s2 = nextToken2.substring(0, index2).trim();
                trim3 = nextToken2.substring(index2 + 1).trim();
            }
            else {
                s2 = nextToken2.trim();
                trim3 = null;
            }
            assignAttribute(httpCookie, s2, trim3);
        }
        return httpCookie;
    }
    
    private static void assignAttribute(final HttpCookie httpCookie, final String s, String stripOffSurroundingQuote) {
        stripOffSurroundingQuote = stripOffSurroundingQuote(stripOffSurroundingQuote);
        final CookieAttributeAssignor cookieAttributeAssignor = HttpCookie.assignors.get(s.toLowerCase());
        if (cookieAttributeAssignor != null) {
            cookieAttributeAssignor.assign(httpCookie, s, stripOffSurroundingQuote);
        }
    }
    
    private String header() {
        return this.header;
    }
    
    private String toNetscapeHeaderString() {
        return this.getName() + "=" + this.getValue();
    }
    
    private String toRFC2965HeaderString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append("=\"").append(this.getValue()).append('\"');
        if (this.getPath() != null) {
            sb.append(";$Path=\"").append(this.getPath()).append('\"');
        }
        if (this.getDomain() != null) {
            sb.append(";$Domain=\"").append(this.getDomain()).append('\"');
        }
        if (this.getPortlist() != null) {
            sb.append(";$Port=\"").append(this.getPortlist()).append('\"');
        }
        return sb.toString();
    }
    
    private long expiryDate2DeltaSeconds(final String s) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(HttpCookie.GMT);
        int i = 0;
        while (i < HttpCookie.COOKIE_DATE_FORMATS.length) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HttpCookie.COOKIE_DATE_FORMATS[i], Locale.US);
            gregorianCalendar.set(1970, 0, 1, 0, 0, 0);
            simpleDateFormat.setTimeZone(HttpCookie.GMT);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.set2DigitYearStart(gregorianCalendar.getTime());
            try {
                gregorianCalendar.setTime(simpleDateFormat.parse(s));
                if (!HttpCookie.COOKIE_DATE_FORMATS[i].contains("yyyy")) {
                    int n = gregorianCalendar.get(1) % 100;
                    if (n < 70) {
                        n += 2000;
                    }
                    else {
                        n += 1900;
                    }
                    gregorianCalendar.set(1, n);
                }
                return (gregorianCalendar.getTimeInMillis() - this.whenCreated) / 1000L;
            }
            catch (Exception ex) {
                ++i;
                continue;
            }
            break;
        }
        return 0L;
    }
    
    private static int guessCookieVersion(String lowerCase) {
        int n = 0;
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.indexOf("expires=") != -1) {
            n = 0;
        }
        else if (lowerCase.indexOf("version=") != -1) {
            n = 1;
        }
        else if (lowerCase.indexOf("max-age") != -1) {
            n = 1;
        }
        else if (startsWithIgnoreCase(lowerCase, "set-cookie2:")) {
            n = 1;
        }
        return n;
    }
    
    private static String stripOffSurroundingQuote(final String s) {
        if (s != null && s.length() > 2 && s.charAt(0) == '\"' && s.charAt(s.length() - 1) == '\"') {
            return s.substring(1, s.length() - 1);
        }
        if (s != null && s.length() > 2 && s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'') {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
    
    private static boolean equalsIgnoreCase(final String s, final String s2) {
        return s == s2 || (s != null && s2 != null && s.equalsIgnoreCase(s2));
    }
    
    private static boolean startsWithIgnoreCase(final String s, final String s2) {
        return s != null && s2 != null && (s.length() >= s2.length() && s2.equalsIgnoreCase(s.substring(0, s2.length())));
    }
    
    private static List<String> splitMultiCookies(final String s) {
        final ArrayList<String> list = new ArrayList<String>();
        int n = 0;
        int i = 0;
        int n2 = 0;
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if (char1 == '\"') {
                ++n;
            }
            if (char1 == ',' && n % 2 == 0) {
                list.add(s.substring(n2, i));
                n2 = i + 1;
            }
            ++i;
        }
        list.add(s.substring(n2));
        return list;
    }
    
    static {
        COOKIE_DATE_FORMATS = new String[] { "EEE',' dd-MMM-yyyy HH:mm:ss 'GMT'", "EEE',' dd MMM yyyy HH:mm:ss 'GMT'", "EEE MMM dd yyyy HH:mm:ss 'GMT'Z", "EEE',' dd-MMM-yy HH:mm:ss 'GMT'", "EEE',' dd MMM yy HH:mm:ss 'GMT'", "EEE MMM dd yy HH:mm:ss 'GMT'Z" };
        (assignors = new HashMap<String, CookieAttributeAssignor>()).put("comment", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String comment) {
                if (httpCookie.getComment() == null) {
                    httpCookie.setComment(comment);
                }
            }
        });
        HttpCookie.assignors.put("commenturl", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String commentURL) {
                if (httpCookie.getCommentURL() == null) {
                    httpCookie.setCommentURL(commentURL);
                }
            }
        });
        HttpCookie.assignors.put("discard", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String s2) {
                httpCookie.setDiscard(true);
            }
        });
        HttpCookie.assignors.put("domain", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String domain) {
                if (httpCookie.getDomain() == null) {
                    httpCookie.setDomain(domain);
                }
            }
        });
        HttpCookie.assignors.put("max-age", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String s2) {
                try {
                    final long long1 = Long.parseLong(s2);
                    if (httpCookie.getMaxAge() == -1L) {
                        httpCookie.setMaxAge(long1);
                    }
                }
                catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Illegal cookie max-age attribute");
                }
            }
        });
        HttpCookie.assignors.put("path", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String path) {
                if (httpCookie.getPath() == null) {
                    httpCookie.setPath(path);
                }
            }
        });
        HttpCookie.assignors.put("port", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String s2) {
                if (httpCookie.getPortlist() == null) {
                    httpCookie.setPortlist((s2 == null) ? "" : s2);
                }
            }
        });
        HttpCookie.assignors.put("secure", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String s2) {
                httpCookie.setSecure(true);
            }
        });
        HttpCookie.assignors.put("httponly", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String s2) {
                httpCookie.setHttpOnly(true);
            }
        });
        HttpCookie.assignors.put("version", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String s2) {
                try {
                    httpCookie.setVersion(Integer.parseInt(s2));
                }
                catch (NumberFormatException ex) {}
            }
        });
        HttpCookie.assignors.put("expires", new CookieAttributeAssignor() {
            @Override
            public void assign(final HttpCookie httpCookie, final String s, final String s2) {
                if (httpCookie.getMaxAge() == -1L) {
                    httpCookie.setMaxAge(httpCookie.expiryDate2DeltaSeconds(s2));
                }
            }
        });
        SharedSecrets.setJavaNetHttpCookieAccess(new JavaNetHttpCookieAccess() {
            @Override
            public List<HttpCookie> parse(final String s) {
                return parse(s, true);
            }
            
            @Override
            public String header(final HttpCookie httpCookie) {
                return httpCookie.header;
            }
        });
        GMT = TimeZone.getTimeZone("GMT");
    }
    
    interface CookieAttributeAssignor
    {
        void assign(final HttpCookie p0, final String p1, final String p2);
    }
}
