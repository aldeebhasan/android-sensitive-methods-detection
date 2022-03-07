package android.icu.text;

import java.util.*;

public abstract class IDNA
{
    public static final int CHECK_BIDI = 4;
    public static final int CHECK_CONTEXTJ = 8;
    public static final int CHECK_CONTEXTO = 64;
    public static final int DEFAULT = 0;
    public static final int NONTRANSITIONAL_TO_ASCII = 16;
    public static final int NONTRANSITIONAL_TO_UNICODE = 32;
    public static final int USE_STD3_RULES = 2;
    
    IDNA() {
        throw new RuntimeException("Stub!");
    }
    
    public static IDNA getUTS46Instance(final int options) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract StringBuilder labelToASCII(final CharSequence p0, final StringBuilder p1, final Info p2);
    
    public abstract StringBuilder labelToUnicode(final CharSequence p0, final StringBuilder p1, final Info p2);
    
    public abstract StringBuilder nameToASCII(final CharSequence p0, final StringBuilder p1, final Info p2);
    
    public abstract StringBuilder nameToUnicode(final CharSequence p0, final StringBuilder p1, final Info p2);
    
    public static final class Info
    {
        public Info() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean hasErrors() {
            throw new RuntimeException("Stub!");
        }
        
        public Set<Error> getErrors() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isTransitionalDifferent() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public enum Error
    {
        BIDI, 
        CONTEXTJ, 
        CONTEXTO_DIGITS, 
        CONTEXTO_PUNCTUATION, 
        DISALLOWED, 
        DOMAIN_NAME_TOO_LONG, 
        EMPTY_LABEL, 
        HYPHEN_3_4, 
        INVALID_ACE_LABEL, 
        LABEL_HAS_DOT, 
        LABEL_TOO_LONG, 
        LEADING_COMBINING_MARK, 
        LEADING_HYPHEN, 
        PUNYCODE, 
        TRAILING_HYPHEN;
    }
}
