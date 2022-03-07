package android.text.util;

import android.text.*;
import android.widget.*;
import java.util.regex.*;

public class Linkify
{
    public static final int ALL = 15;
    public static final int EMAIL_ADDRESSES = 2;
    public static final int MAP_ADDRESSES = 8;
    public static final int PHONE_NUMBERS = 4;
    public static final int WEB_URLS = 1;
    public static final MatchFilter sPhoneNumberMatchFilter;
    public static final TransformFilter sPhoneNumberTransformFilter;
    public static final MatchFilter sUrlMatchFilter;
    
    public Linkify() {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean addLinks(final Spannable text, final int mask) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean addLinks(final TextView text, final int mask) {
        throw new RuntimeException("Stub!");
    }
    
    public static final void addLinks(final TextView text, final Pattern pattern, final String scheme) {
        throw new RuntimeException("Stub!");
    }
    
    public static final void addLinks(final TextView text, final Pattern pattern, final String scheme, final MatchFilter matchFilter, final TransformFilter transformFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public static final void addLinks(final TextView text, final Pattern pattern, final String defaultScheme, final String[] schemes, final MatchFilter matchFilter, final TransformFilter transformFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean addLinks(final Spannable text, final Pattern pattern, final String scheme) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean addLinks(final Spannable spannable, final Pattern pattern, final String scheme, final MatchFilter matchFilter, final TransformFilter transformFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean addLinks(final Spannable spannable, final Pattern pattern, final String defaultScheme, final String[] schemes, final MatchFilter matchFilter, final TransformFilter transformFilter) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        sPhoneNumberMatchFilter = null;
        sPhoneNumberTransformFilter = null;
        sUrlMatchFilter = null;
    }
    
    public interface TransformFilter
    {
        String transformUrl(final Matcher p0, final String p1);
    }
    
    public interface MatchFilter
    {
        boolean acceptMatch(final CharSequence p0, final int p1, final int p2);
    }
}
