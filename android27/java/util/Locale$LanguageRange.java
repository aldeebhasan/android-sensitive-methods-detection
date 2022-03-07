package java.util;

import sun.util.locale.*;

public static final class LanguageRange
{
    public static final double MAX_WEIGHT = 1.0;
    public static final double MIN_WEIGHT = 0.0;
    private final String range;
    private final double weight;
    private volatile int hash;
    
    public LanguageRange(final String s) {
        this(s, 1.0);
    }
    
    public LanguageRange(String lowerCase, final double weight) {
        this.hash = 0;
        if (lowerCase == null) {
            throw new NullPointerException();
        }
        if (weight < 0.0 || weight > 1.0) {
            throw new IllegalArgumentException("weight=" + weight);
        }
        lowerCase = lowerCase.toLowerCase();
        boolean b = false;
        final String[] split = lowerCase.split("-");
        if (isSubtagIllFormed(split[0], true) || lowerCase.endsWith("-")) {
            b = true;
        }
        else {
            for (int i = 1; i < split.length; ++i) {
                if (isSubtagIllFormed(split[i], false)) {
                    b = true;
                    break;
                }
            }
        }
        if (b) {
            throw new IllegalArgumentException("range=" + lowerCase);
        }
        this.range = lowerCase;
        this.weight = weight;
    }
    
    private static boolean isSubtagIllFormed(final String s, final boolean b) {
        if (s.equals("") || s.length() > 8) {
            return true;
        }
        if (s.equals("*")) {
            return false;
        }
        final char[] charArray = s.toCharArray();
        if (b) {
            for (final char c : charArray) {
                if (c < 'a' || c > 'z') {
                    return true;
                }
            }
        }
        else {
            for (final char c2 : charArray) {
                if (c2 < '0' || (c2 > '9' && c2 < 'a') || c2 > 'z') {
                    return true;
                }
            }
        }
        return false;
    }
    
    public String getRange() {
        return this.range;
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public static List<LanguageRange> parse(final String s) {
        return LocaleMatcher.parse(s);
    }
    
    public static List<LanguageRange> parse(final String s, final Map<String, List<String>> map) {
        return mapEquivalents(parse(s), map);
    }
    
    public static List<LanguageRange> mapEquivalents(final List<LanguageRange> list, final Map<String, List<String>> map) {
        return LocaleMatcher.mapEquivalents(list, map);
    }
    
    @Override
    public int hashCode() {
        if (this.hash == 0) {
            final int n = 37 * 17 + this.range.hashCode();
            final long doubleToLongBits = Double.doubleToLongBits(this.weight);
            this.hash = 37 * n + (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
        }
        return this.hash;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LanguageRange)) {
            return false;
        }
        final LanguageRange languageRange = (LanguageRange)o;
        return this.hash == languageRange.hash && this.range.equals(languageRange.range) && this.weight == languageRange.weight;
    }
}
