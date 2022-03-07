package java.nio.charset;

import java.nio.charset.spi.*;
import sun.security.action.*;
import java.security.*;
import sun.misc.*;
import java.util.*;
import java.nio.*;
import sun.nio.cs.*;

public abstract class Charset implements Comparable<Charset>
{
    private static volatile String bugLevel;
    private static CharsetProvider standardProvider;
    private static volatile Object[] cache1;
    private static volatile Object[] cache2;
    private static ThreadLocal<ThreadLocal<?>> gate;
    private static volatile Charset defaultCharset;
    private final String name;
    private final String[] aliases;
    private Set<String> aliasSet;
    
    static boolean atBugLevel(final String s) {
        String bugLevel = Charset.bugLevel;
        if (bugLevel == null) {
            if (!VM.isBooted()) {
                return false;
            }
            bugLevel = (Charset.bugLevel = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("sun.nio.cs.bugLevel", "")));
        }
        return bugLevel.equals(s);
    }
    
    private static void checkName(final String s) {
        final int length = s.length();
        if (!atBugLevel("1.4") && length == 0) {
            throw new IllegalCharsetNameException(s);
        }
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 < 'A' || char1 > 'Z') {
                if (char1 < 'a' || char1 > 'z') {
                    if (char1 < '0' || char1 > '9') {
                        if (char1 != '-' || i == 0) {
                            if (char1 != '+' || i == 0) {
                                if (char1 != ':' || i == 0) {
                                    if (char1 != '_' || i == 0) {
                                        if (char1 != '.' || i == 0) {
                                            throw new IllegalCharsetNameException(s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void cache(final String s, final Charset charset) {
        Charset.cache2 = Charset.cache1;
        Charset.cache1 = new Object[] { s, charset };
    }
    
    private static Iterator<CharsetProvider> providers() {
        return new Iterator<CharsetProvider>() {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            ServiceLoader<CharsetProvider> sl = ServiceLoader.load(CharsetProvider.class, this.cl);
            Iterator<CharsetProvider> i = this.sl.iterator();
            CharsetProvider next = null;
            
            private boolean getNext() {
                while (this.next == null) {
                    try {
                        if (!this.i.hasNext()) {
                            return false;
                        }
                        this.next = this.i.next();
                        continue;
                    }
                    catch (ServiceConfigurationError serviceConfigurationError) {
                        if (serviceConfigurationError.getCause() instanceof SecurityException) {
                            continue;
                        }
                        throw serviceConfigurationError;
                    }
                    break;
                }
                return true;
            }
            
            @Override
            public boolean hasNext() {
                return this.getNext();
            }
            
            @Override
            public CharsetProvider next() {
                if (!this.getNext()) {
                    throw new NoSuchElementException();
                }
                final CharsetProvider next = this.next;
                this.next = null;
                return next;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    private static Charset lookupViaProviders(final String s) {
        if (!VM.isBooted()) {
            return null;
        }
        if (Charset.gate.get() != null) {
            return null;
        }
        try {
            Charset.gate.set(Charset.gate);
            return AccessController.doPrivileged((PrivilegedAction<Charset>)new PrivilegedAction<Charset>() {
                @Override
                public Charset run() {
                    final Iterator access$000 = providers();
                    while (access$000.hasNext()) {
                        final Charset charsetForName = access$000.next().charsetForName(s);
                        if (charsetForName != null) {
                            return charsetForName;
                        }
                    }
                    return null;
                }
            });
        }
        finally {
            Charset.gate.set(null);
        }
    }
    
    private static Charset lookupExtendedCharset(final String s) {
        final CharsetProvider extendedProvider = ExtendedProviderHolder.extendedProvider;
        return (extendedProvider != null) ? extendedProvider.charsetForName(s) : null;
    }
    
    private static Charset lookup(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("Null charset name");
        }
        final Object[] cache1;
        if ((cache1 = Charset.cache1) != null && s.equals(cache1[0])) {
            return (Charset)cache1[1];
        }
        return lookup2(s);
    }
    
    private static Charset lookup2(final String s) {
        final Object[] cache2;
        if ((cache2 = Charset.cache2) != null && s.equals(cache2[0])) {
            Charset.cache2 = Charset.cache1;
            Charset.cache1 = cache2;
            return (Charset)cache2[1];
        }
        Charset charset;
        if ((charset = Charset.standardProvider.charsetForName(s)) != null || (charset = lookupExtendedCharset(s)) != null || (charset = lookupViaProviders(s)) != null) {
            cache(s, charset);
            return charset;
        }
        checkName(s);
        return null;
    }
    
    public static boolean isSupported(final String s) {
        return lookup(s) != null;
    }
    
    public static Charset forName(final String s) {
        final Charset lookup = lookup(s);
        if (lookup != null) {
            return lookup;
        }
        throw new UnsupportedCharsetException(s);
    }
    
    private static void put(final Iterator<Charset> iterator, final Map<String, Charset> map) {
        while (iterator.hasNext()) {
            final Charset charset = iterator.next();
            if (!map.containsKey(charset.name())) {
                map.put(charset.name(), charset);
            }
        }
    }
    
    public static SortedMap<String, Charset> availableCharsets() {
        return AccessController.doPrivileged((PrivilegedAction<SortedMap<String, Charset>>)new PrivilegedAction<SortedMap<String, Charset>>() {
            @Override
            public SortedMap<String, Charset> run() {
                final TreeMap<String, Charset> treeMap = new TreeMap<String, Charset>(ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
                put(Charset.standardProvider.charsets(), treeMap);
                final CharsetProvider extendedProvider = ExtendedProviderHolder.extendedProvider;
                if (extendedProvider != null) {
                    put(extendedProvider.charsets(), treeMap);
                }
                final Iterator access$000 = providers();
                while (access$000.hasNext()) {
                    put(access$000.next().charsets(), treeMap);
                }
                return (SortedMap<String, Charset>)Collections.unmodifiableSortedMap((SortedMap<String, ?>)treeMap);
            }
        });
    }
    
    public static Charset defaultCharset() {
        if (Charset.defaultCharset == null) {
            synchronized (Charset.class) {
                final Charset lookup = lookup(AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("file.encoding")));
                if (lookup != null) {
                    Charset.defaultCharset = lookup;
                }
                else {
                    Charset.defaultCharset = forName("UTF-8");
                }
            }
        }
        return Charset.defaultCharset;
    }
    
    protected Charset(final String name, final String[] array) {
        this.aliasSet = null;
        checkName(name);
        final String[] aliases = (array == null) ? new String[0] : array;
        for (int i = 0; i < aliases.length; ++i) {
            checkName(aliases[i]);
        }
        this.name = name;
        this.aliases = aliases;
    }
    
    public final String name() {
        return this.name;
    }
    
    public final Set<String> aliases() {
        if (this.aliasSet != null) {
            return this.aliasSet;
        }
        final int length = this.aliases.length;
        final HashSet set = new HashSet<String>(length);
        for (int i = 0; i < length; ++i) {
            set.add(this.aliases[i]);
        }
        return this.aliasSet = Collections.unmodifiableSet((Set<? extends String>)set);
    }
    
    public String displayName() {
        return this.name;
    }
    
    public final boolean isRegistered() {
        return !this.name.startsWith("X-") && !this.name.startsWith("x-");
    }
    
    public String displayName(final Locale locale) {
        return this.name;
    }
    
    public abstract boolean contains(final Charset p0);
    
    public abstract CharsetDecoder newDecoder();
    
    public abstract CharsetEncoder newEncoder();
    
    public boolean canEncode() {
        return true;
    }
    
    public final CharBuffer decode(final ByteBuffer byteBuffer) {
        try {
            return ThreadLocalCoders.decoderFor(this).onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).decode(byteBuffer);
        }
        catch (CharacterCodingException ex) {
            throw new Error(ex);
        }
    }
    
    public final ByteBuffer encode(final CharBuffer charBuffer) {
        try {
            return ThreadLocalCoders.encoderFor(this).onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).encode(charBuffer);
        }
        catch (CharacterCodingException ex) {
            throw new Error(ex);
        }
    }
    
    public final ByteBuffer encode(final String s) {
        return this.encode(CharBuffer.wrap(s));
    }
    
    @Override
    public final int compareTo(final Charset charset) {
        return this.name().compareToIgnoreCase(charset.name());
    }
    
    @Override
    public final int hashCode() {
        return this.name().hashCode();
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o instanceof Charset && (this == o || this.name.equals(((Charset)o).name()));
    }
    
    @Override
    public final String toString() {
        return this.name();
    }
    
    static {
        Charset.bugLevel = null;
        Charset.standardProvider = new StandardCharsets();
        Charset.cache1 = null;
        Charset.cache2 = null;
        Charset.gate = new ThreadLocal<ThreadLocal<?>>();
    }
    
    private static class ExtendedProviderHolder
    {
        static final CharsetProvider extendedProvider;
        
        private static CharsetProvider extendedProvider() {
            return AccessController.doPrivileged((PrivilegedAction<CharsetProvider>)new PrivilegedAction<CharsetProvider>() {
                @Override
                public CharsetProvider run() {
                    try {
                        return (CharsetProvider)Class.forName("sun.nio.cs.ext.ExtendedCharsets").newInstance();
                    }
                    catch (ClassNotFoundException ex) {}
                    catch (InstantiationException | IllegalAccessException ex2) {
                        final Object o;
                        throw new Error((Throwable)o);
                    }
                    return null;
                }
            });
        }
        
        static {
            extendedProvider = extendedProvider();
        }
    }
}
