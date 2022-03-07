package java.text;

import java.lang.ref.*;
import java.util.*;
import java.text.spi.*;
import java.util.spi.*;
import sun.util.locale.provider.*;

public abstract class BreakIterator implements Cloneable
{
    public static final int DONE = -1;
    private static final int CHARACTER_INDEX = 0;
    private static final int WORD_INDEX = 1;
    private static final int LINE_INDEX = 2;
    private static final int SENTENCE_INDEX = 3;
    private static final SoftReference<BreakIteratorCache>[] iterCache;
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    public abstract int first();
    
    public abstract int last();
    
    public abstract int next(final int p0);
    
    public abstract int next();
    
    public abstract int previous();
    
    public abstract int following(final int p0);
    
    public int preceding(final int n) {
        int n2;
        for (n2 = this.following(n); n2 >= n && n2 != -1; n2 = this.previous()) {}
        return n2;
    }
    
    public boolean isBoundary(final int n) {
        if (n == 0) {
            return true;
        }
        final int following = this.following(n - 1);
        if (following == -1) {
            throw new IllegalArgumentException();
        }
        return following == n;
    }
    
    public abstract int current();
    
    public abstract CharacterIterator getText();
    
    public void setText(final String s) {
        this.setText(new StringCharacterIterator(s));
    }
    
    public abstract void setText(final CharacterIterator p0);
    
    public static BreakIterator getWordInstance() {
        return getWordInstance(Locale.getDefault());
    }
    
    public static BreakIterator getWordInstance(final Locale locale) {
        return getBreakInstance(locale, 1);
    }
    
    public static BreakIterator getLineInstance() {
        return getLineInstance(Locale.getDefault());
    }
    
    public static BreakIterator getLineInstance(final Locale locale) {
        return getBreakInstance(locale, 2);
    }
    
    public static BreakIterator getCharacterInstance() {
        return getCharacterInstance(Locale.getDefault());
    }
    
    public static BreakIterator getCharacterInstance(final Locale locale) {
        return getBreakInstance(locale, 0);
    }
    
    public static BreakIterator getSentenceInstance() {
        return getSentenceInstance(Locale.getDefault());
    }
    
    public static BreakIterator getSentenceInstance(final Locale locale) {
        return getBreakInstance(locale, 3);
    }
    
    private static BreakIterator getBreakInstance(final Locale locale, final int n) {
        if (BreakIterator.iterCache[n] != null) {
            final BreakIteratorCache breakIteratorCache = BreakIterator.iterCache[n].get();
            if (breakIteratorCache != null && breakIteratorCache.getLocale().equals(locale)) {
                return breakIteratorCache.createBreakInstance();
            }
        }
        final BreakIterator breakInstance = createBreakInstance(locale, n);
        BreakIterator.iterCache[n] = new SoftReference<BreakIteratorCache>(new BreakIteratorCache(locale, breakInstance));
        return breakInstance;
    }
    
    private static BreakIterator createBreakInstance(final Locale locale, final int n) {
        BreakIterator breakIterator = createBreakInstance(LocaleProviderAdapter.getAdapter(BreakIteratorProvider.class, locale), locale, n);
        if (breakIterator == null) {
            breakIterator = createBreakInstance(LocaleProviderAdapter.forJRE(), locale, n);
        }
        return breakIterator;
    }
    
    private static BreakIterator createBreakInstance(final LocaleProviderAdapter localeProviderAdapter, final Locale locale, final int n) {
        final BreakIteratorProvider breakIteratorProvider = localeProviderAdapter.getBreakIteratorProvider();
        BreakIterator breakIterator = null;
        switch (n) {
            case 0: {
                breakIterator = breakIteratorProvider.getCharacterInstance(locale);
                break;
            }
            case 1: {
                breakIterator = breakIteratorProvider.getWordInstance(locale);
                break;
            }
            case 2: {
                breakIterator = breakIteratorProvider.getLineInstance(locale);
                break;
            }
            case 3: {
                breakIterator = breakIteratorProvider.getSentenceInstance(locale);
                break;
            }
        }
        return breakIterator;
    }
    
    public static synchronized Locale[] getAvailableLocales() {
        return LocaleServiceProviderPool.getPool(BreakIteratorProvider.class).getAvailableLocales();
    }
    
    static {
        iterCache = new SoftReference[4];
    }
    
    private static final class BreakIteratorCache
    {
        private BreakIterator iter;
        private Locale locale;
        
        BreakIteratorCache(final Locale locale, final BreakIterator breakIterator) {
            this.locale = locale;
            this.iter = (BreakIterator)breakIterator.clone();
        }
        
        Locale getLocale() {
            return this.locale;
        }
        
        BreakIterator createBreakInstance() {
            return (BreakIterator)this.iter.clone();
        }
    }
}
