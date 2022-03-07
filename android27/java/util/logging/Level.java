package java.util.logging;

import java.io.*;
import java.util.*;

public class Level implements Serializable
{
    private static final String defaultBundle = "sun.util.logging.resources.logging";
    private final String name;
    private final int value;
    private final String resourceBundleName;
    private transient String localizedLevelName;
    private transient Locale cachedLocale;
    public static final Level OFF;
    public static final Level SEVERE;
    public static final Level WARNING;
    public static final Level INFO;
    public static final Level CONFIG;
    public static final Level FINE;
    public static final Level FINER;
    public static final Level FINEST;
    public static final Level ALL;
    private static final long serialVersionUID = -8176160795706313070L;
    
    protected Level(final String s, final int n) {
        this(s, n, null);
    }
    
    protected Level(final String s, final int n, final String s2) {
        this(s, n, s2, true);
    }
    
    private Level(final String name, final int value, final String resourceBundleName, final boolean b) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.value = value;
        this.resourceBundleName = resourceBundleName;
        this.localizedLevelName = ((resourceBundleName == null) ? name : null);
        this.cachedLocale = null;
        if (b) {
            KnownLevel.add(this);
        }
    }
    
    public String getResourceBundleName() {
        return this.resourceBundleName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getLocalizedName() {
        return this.getLocalizedLevelName();
    }
    
    final String getLevelName() {
        return this.name;
    }
    
    private String computeLocalizedLevelName(final Locale locale) {
        if (!"sun.util.logging.resources.logging".equals(this.resourceBundleName)) {
            return ResourceBundle.getBundle(this.resourceBundleName, locale, ClassLoader.getSystemClassLoader()).getString(this.name);
        }
        final ResourceBundle bundle = ResourceBundle.getBundle("sun.util.logging.resources.logging", locale);
        final String string = bundle.getString(this.name);
        final Locale locale2 = bundle.getLocale();
        final Locale locale3 = (Locale.ROOT.equals(locale2) || this.name.equals(string.toUpperCase(Locale.ROOT))) ? Locale.ROOT : locale2;
        return Locale.ROOT.equals(locale3) ? this.name : string.toUpperCase(locale3);
    }
    
    final String getCachedLocalizedLevelName() {
        if (this.localizedLevelName != null && this.cachedLocale != null && this.cachedLocale.equals(Locale.getDefault())) {
            return this.localizedLevelName;
        }
        if (this.resourceBundleName == null) {
            return this.name;
        }
        return null;
    }
    
    final synchronized String getLocalizedLevelName() {
        final String cachedLocalizedLevelName = this.getCachedLocalizedLevelName();
        if (cachedLocalizedLevelName != null) {
            return cachedLocalizedLevelName;
        }
        final Locale default1 = Locale.getDefault();
        try {
            this.localizedLevelName = this.computeLocalizedLevelName(default1);
        }
        catch (Exception ex) {
            this.localizedLevelName = this.name;
        }
        this.cachedLocale = default1;
        return this.localizedLevelName;
    }
    
    static Level findLevel(final String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        final KnownLevel byName = KnownLevel.findByName(s);
        if (byName != null) {
            return byName.mirroredLevel;
        }
        try {
            final int int1 = Integer.parseInt(s);
            KnownLevel knownLevel = KnownLevel.findByValue(int1);
            if (knownLevel == null) {
                final Level level = new Level(s, int1);
                knownLevel = KnownLevel.findByValue(int1);
            }
            return knownLevel.mirroredLevel;
        }
        catch (NumberFormatException ex) {
            final KnownLevel byLocalizedLevelName = KnownLevel.findByLocalizedLevelName(s);
            if (byLocalizedLevelName != null) {
                return byLocalizedLevelName.mirroredLevel;
            }
            return null;
        }
    }
    
    @Override
    public final String toString() {
        return this.name;
    }
    
    public final int intValue() {
        return this.value;
    }
    
    private Object readResolve() {
        final KnownLevel matches = KnownLevel.matches(this);
        if (matches != null) {
            return matches.levelObject;
        }
        return new Level(this.name, this.value, this.resourceBundleName);
    }
    
    public static synchronized Level parse(final String s) throws IllegalArgumentException {
        s.length();
        final KnownLevel byName = KnownLevel.findByName(s);
        if (byName != null) {
            return byName.levelObject;
        }
        try {
            final int int1 = Integer.parseInt(s);
            KnownLevel knownLevel = KnownLevel.findByValue(int1);
            if (knownLevel == null) {
                final Level level = new Level(s, int1);
                knownLevel = KnownLevel.findByValue(int1);
            }
            return knownLevel.levelObject;
        }
        catch (NumberFormatException ex) {
            final KnownLevel byLocalizedLevelName = KnownLevel.findByLocalizedLevelName(s);
            if (byLocalizedLevelName != null) {
                return byLocalizedLevelName.levelObject;
            }
            throw new IllegalArgumentException("Bad level \"" + s + "\"");
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        try {
            return ((Level)o).value == this.value;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    static {
        OFF = new Level("OFF", Integer.MAX_VALUE, "sun.util.logging.resources.logging");
        SEVERE = new Level("SEVERE", 1000, "sun.util.logging.resources.logging");
        WARNING = new Level("WARNING", 900, "sun.util.logging.resources.logging");
        INFO = new Level("INFO", 800, "sun.util.logging.resources.logging");
        CONFIG = new Level("CONFIG", 700, "sun.util.logging.resources.logging");
        FINE = new Level("FINE", 500, "sun.util.logging.resources.logging");
        FINER = new Level("FINER", 400, "sun.util.logging.resources.logging");
        FINEST = new Level("FINEST", 300, "sun.util.logging.resources.logging");
        ALL = new Level("ALL", Integer.MIN_VALUE, "sun.util.logging.resources.logging");
    }
    
    static final class KnownLevel
    {
        private static Map<String, List<KnownLevel>> nameToLevels;
        private static Map<Integer, List<KnownLevel>> intToLevels;
        final Level levelObject;
        final Level mirroredLevel;
        
        KnownLevel(final Level level) {
            this.levelObject = level;
            if (level.getClass() == Level.class) {
                this.mirroredLevel = level;
            }
            else {
                this.mirroredLevel = new Level(level.name, level.value, level.resourceBundleName, false, null);
            }
        }
        
        static synchronized void add(final Level level) {
            final KnownLevel knownLevel = new KnownLevel(level);
            List<KnownLevel> list = KnownLevel.nameToLevels.get(level.name);
            if (list == null) {
                list = new ArrayList<KnownLevel>();
                KnownLevel.nameToLevels.put(level.name, list);
            }
            list.add(knownLevel);
            List<KnownLevel> list2 = KnownLevel.intToLevels.get(level.value);
            if (list2 == null) {
                list2 = new ArrayList<KnownLevel>();
                KnownLevel.intToLevels.put(level.value, list2);
            }
            list2.add(knownLevel);
        }
        
        static synchronized KnownLevel findByName(final String s) {
            final List<KnownLevel> list = KnownLevel.nameToLevels.get(s);
            if (list != null) {
                return list.get(0);
            }
            return null;
        }
        
        static synchronized KnownLevel findByValue(final int n) {
            final List<KnownLevel> list = KnownLevel.intToLevels.get(n);
            if (list != null) {
                return list.get(0);
            }
            return null;
        }
        
        static synchronized KnownLevel findByLocalizedLevelName(final String s) {
            final Iterator<List<KnownLevel>> iterator = KnownLevel.nameToLevels.values().iterator();
            while (iterator.hasNext()) {
                for (final KnownLevel knownLevel : iterator.next()) {
                    if (s.equals(knownLevel.levelObject.getLocalizedLevelName())) {
                        return knownLevel;
                    }
                }
            }
            return null;
        }
        
        static synchronized KnownLevel matches(final Level level) {
            final List<KnownLevel> list = KnownLevel.nameToLevels.get(level.name);
            if (list != null) {
                for (final KnownLevel knownLevel : list) {
                    final Level mirroredLevel = knownLevel.mirroredLevel;
                    final Class<? extends Level> class1 = knownLevel.levelObject.getClass();
                    if (level.value == mirroredLevel.value && (level.resourceBundleName == mirroredLevel.resourceBundleName || (level.resourceBundleName != null && level.resourceBundleName.equals(mirroredLevel.resourceBundleName))) && class1 == level.getClass()) {
                        return knownLevel;
                    }
                }
            }
            return null;
        }
        
        static {
            KnownLevel.nameToLevels = new HashMap<String, List<KnownLevel>>();
            KnownLevel.intToLevels = new HashMap<Integer, List<KnownLevel>>();
        }
    }
}
