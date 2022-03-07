package java.net;

public interface CookiePolicy
{
    public static final CookiePolicy ACCEPT_ALL = new CookiePolicy() {
        @Override
        public boolean shouldAccept(final URI uri, final HttpCookie httpCookie) {
            return true;
        }
    };
    public static final CookiePolicy ACCEPT_NONE = new CookiePolicy() {
        @Override
        public boolean shouldAccept(final URI uri, final HttpCookie httpCookie) {
            return false;
        }
    };
    public static final CookiePolicy ACCEPT_ORIGINAL_SERVER = new CookiePolicy() {
        @Override
        public boolean shouldAccept(final URI uri, final HttpCookie httpCookie) {
            return uri != null && httpCookie != null && HttpCookie.domainMatches(httpCookie.getDomain(), uri.getHost());
        }
    };
    
    boolean shouldAccept(final URI p0, final HttpCookie p1);
}
