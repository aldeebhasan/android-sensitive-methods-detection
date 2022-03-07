package java.text;

import java.util.*;
import java.lang.ref.*;
import java.text.spi.*;
import java.util.spi.*;
import sun.util.locale.provider.*;
import java.util.concurrent.*;

public abstract class Collator implements Comparator<Object>, Cloneable
{
    public static final int PRIMARY = 0;
    public static final int SECONDARY = 1;
    public static final int TERTIARY = 2;
    public static final int IDENTICAL = 3;
    public static final int NO_DECOMPOSITION = 0;
    public static final int CANONICAL_DECOMPOSITION = 1;
    public static final int FULL_DECOMPOSITION = 2;
    private int strength;
    private int decmp;
    private static final ConcurrentMap<Locale, SoftReference<Collator>> cache;
    static final int LESS = -1;
    static final int EQUAL = 0;
    static final int GREATER = 1;
    
    public static synchronized Collator getInstance() {
        return getInstance(Locale.getDefault());
    }
    
    public static Collator getInstance(final Locale locale) {
        SoftReference<Collator> softReference = Collator.cache.get(locale);
        Collator collator = (softReference != null) ? softReference.get() : null;
        if (collator == null) {
            collator = LocaleProviderAdapter.getAdapter(CollatorProvider.class, locale).getCollatorProvider().getInstance(locale);
            if (collator == null) {
                collator = LocaleProviderAdapter.forJRE().getCollatorProvider().getInstance(locale);
            }
            while (true) {
                if (softReference != null) {
                    Collator.cache.remove(locale, softReference);
                }
                softReference = Collator.cache.putIfAbsent(locale, new SoftReference<Collator>(collator));
                if (softReference == null) {
                    break;
                }
                final Collator collator2 = softReference.get();
                if (collator2 != null) {
                    collator = collator2;
                    break;
                }
            }
        }
        return (Collator)collator.clone();
    }
    
    public abstract int compare(final String p0, final String p1);
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((String)o, (String)o2);
    }
    
    public abstract CollationKey getCollationKey(final String p0);
    
    public boolean equals(final String s, final String s2) {
        return this.compare(s, s2) == 0;
    }
    
    public synchronized int getStrength() {
        return this.strength;
    }
    
    public synchronized void setStrength(final int strength) {
        if (strength != 0 && strength != 1 && strength != 2 && strength != 3) {
            throw new IllegalArgumentException("Incorrect comparison level.");
        }
        this.strength = strength;
    }
    
    public synchronized int getDecomposition() {
        return this.decmp;
    }
    
    public synchronized void setDecomposition(final int decmp) {
        if (decmp != 0 && decmp != 1 && decmp != 2) {
            throw new IllegalArgumentException("Wrong decomposition mode.");
        }
        this.decmp = decmp;
    }
    
    public static synchronized Locale[] getAvailableLocales() {
        return LocaleServiceProviderPool.getPool(CollatorProvider.class).getAvailableLocales();
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final Collator collator = (Collator)o;
        return this.strength == collator.strength && this.decmp == collator.decmp;
    }
    
    @Override
    public abstract int hashCode();
    
    protected Collator() {
        this.strength = 0;
        this.decmp = 0;
        this.strength = 2;
        this.decmp = 1;
    }
    
    static {
        cache = new ConcurrentHashMap<Locale, SoftReference<Collator>>();
    }
}
