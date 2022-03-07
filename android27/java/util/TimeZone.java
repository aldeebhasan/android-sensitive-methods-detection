package java.util;

import java.io.*;
import sun.util.locale.provider.*;
import java.time.*;
import sun.util.calendar.*;
import sun.security.action.*;
import java.security.*;

public abstract class TimeZone implements Serializable, Cloneable
{
    public static final int SHORT = 0;
    public static final int LONG = 1;
    private static final int ONE_MINUTE = 60000;
    private static final int ONE_HOUR = 3600000;
    private static final int ONE_DAY = 86400000;
    static final long serialVersionUID = 3581463369166924961L;
    static final TimeZone NO_TIMEZONE;
    private String ID;
    private static volatile TimeZone defaultTimeZone;
    static final String GMT_ID = "GMT";
    private static final int GMT_ID_LENGTH = 3;
    private static volatile TimeZone mainAppContextDefault;
    
    public abstract int getOffset(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    public int getOffset(final long n) {
        if (this.inDaylightTime(new Date(n))) {
            return this.getRawOffset() + this.getDSTSavings();
        }
        return this.getRawOffset();
    }
    
    int getOffsets(final long n, final int[] array) {
        final int rawOffset = this.getRawOffset();
        int dstSavings = 0;
        if (this.inDaylightTime(new Date(n))) {
            dstSavings = this.getDSTSavings();
        }
        if (array != null) {
            array[0] = rawOffset;
            array[1] = dstSavings;
        }
        return rawOffset + dstSavings;
    }
    
    public abstract void setRawOffset(final int p0);
    
    public abstract int getRawOffset();
    
    public String getID() {
        return this.ID;
    }
    
    public void setID(final String id) {
        if (id == null) {
            throw new NullPointerException();
        }
        this.ID = id;
    }
    
    public final String getDisplayName() {
        return this.getDisplayName(false, 1, Locale.getDefault(Locale.Category.DISPLAY));
    }
    
    public final String getDisplayName(final Locale locale) {
        return this.getDisplayName(false, 1, locale);
    }
    
    public final String getDisplayName(final boolean b, final int n) {
        return this.getDisplayName(b, n, Locale.getDefault(Locale.Category.DISPLAY));
    }
    
    public String getDisplayName(final boolean b, final int n, final Locale locale) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("Illegal style: " + n);
        }
        final String id = this.getID();
        final String retrieveDisplayName = TimeZoneNameUtility.retrieveDisplayName(id, b, n, locale);
        if (retrieveDisplayName != null) {
            return retrieveDisplayName;
        }
        if (id.startsWith("GMT") && id.length() > 3) {
            final char char1 = id.charAt(3);
            if (char1 == '+' || char1 == '-') {
                return id;
            }
        }
        int rawOffset = this.getRawOffset();
        if (b) {
            rawOffset += this.getDSTSavings();
        }
        return ZoneInfoFile.toCustomID(rawOffset);
    }
    
    private static String[] getDisplayNames(final String s, final Locale locale) {
        return TimeZoneNameUtility.retrieveDisplayNames(s, locale);
    }
    
    public int getDSTSavings() {
        if (this.useDaylightTime()) {
            return 3600000;
        }
        return 0;
    }
    
    public abstract boolean useDaylightTime();
    
    public boolean observesDaylightTime() {
        return this.useDaylightTime() || this.inDaylightTime(new Date());
    }
    
    public abstract boolean inDaylightTime(final Date p0);
    
    public static synchronized TimeZone getTimeZone(final String s) {
        return getTimeZone(s, true);
    }
    
    public static TimeZone getTimeZone(final ZoneId zoneId) {
        String s = zoneId.getId();
        final char char1 = s.charAt(0);
        if (char1 == '+' || char1 == '-') {
            s = "GMT" + s;
        }
        else if (char1 == 'Z' && s.length() == 1) {
            s = "UTC";
        }
        return getTimeZone(s, true);
    }
    
    public ZoneId toZoneId() {
        final String id = this.getID();
        if (ZoneInfoFile.useOldMapping() && id.length() == 3) {
            if ("EST".equals(id)) {
                return ZoneId.of("America/New_York");
            }
            if ("MST".equals(id)) {
                return ZoneId.of("America/Denver");
            }
            if ("HST".equals(id)) {
                return ZoneId.of("America/Honolulu");
            }
        }
        return ZoneId.of(id, ZoneId.SHORT_IDS);
    }
    
    private static TimeZone getTimeZone(final String s, final boolean b) {
        TimeZone timeZone = ZoneInfo.getTimeZone(s);
        if (timeZone == null) {
            timeZone = parseCustomTimeZone(s);
            if (timeZone == null && b) {
                timeZone = new ZoneInfo("GMT", 0);
            }
        }
        return timeZone;
    }
    
    public static synchronized String[] getAvailableIDs(final int n) {
        return ZoneInfo.getAvailableIDs(n);
    }
    
    public static synchronized String[] getAvailableIDs() {
        return ZoneInfo.getAvailableIDs();
    }
    
    private static native String getSystemTimeZoneID(final String p0);
    
    private static native String getSystemGMTOffsetID();
    
    public static TimeZone getDefault() {
        return (TimeZone)getDefaultRef().clone();
    }
    
    static TimeZone getDefaultRef() {
        TimeZone timeZone = TimeZone.defaultTimeZone;
        if (timeZone == null) {
            timeZone = setDefaultZone();
            assert timeZone != null;
        }
        return timeZone;
    }
    
    private static synchronized TimeZone setDefaultZone() {
        String systemTimeZoneID = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("user.timezone"));
        if (systemTimeZoneID == null || systemTimeZoneID.isEmpty()) {
            final String s = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("java.home"));
            try {
                systemTimeZoneID = getSystemTimeZoneID(s);
                if (systemTimeZoneID == null) {
                    systemTimeZoneID = "GMT";
                }
            }
            catch (NullPointerException ex) {
                systemTimeZoneID = "GMT";
            }
        }
        TimeZone defaultTimeZone = getTimeZone(systemTimeZoneID, false);
        if (defaultTimeZone == null) {
            final String systemGMTOffsetID = getSystemGMTOffsetID();
            if (systemGMTOffsetID != null) {
                systemTimeZoneID = systemGMTOffsetID;
            }
            defaultTimeZone = getTimeZone(systemTimeZoneID, true);
        }
        assert defaultTimeZone != null;
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                System.setProperty("user.timezone", systemTimeZoneID);
                return null;
            }
        });
        return TimeZone.defaultTimeZone = defaultTimeZone;
    }
    
    public static void setDefault(final TimeZone defaultTimeZone) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new PropertyPermission("user.timezone", "write"));
        }
        TimeZone.defaultTimeZone = defaultTimeZone;
    }
    
    public boolean hasSameRules(final TimeZone timeZone) {
        return timeZone != null && this.getRawOffset() == timeZone.getRawOffset() && this.useDaylightTime() == timeZone.useDaylightTime();
    }
    
    public Object clone() {
        try {
            final TimeZone timeZone = (TimeZone)super.clone();
            timeZone.ID = this.ID;
            return timeZone;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    private static final TimeZone parseCustomTimeZone(final String s) {
        final int length;
        if ((length = s.length()) < 5 || s.indexOf("GMT") != 0) {
            return null;
        }
        final ZoneInfo zoneInfo = ZoneInfoFile.getZoneInfo(s);
        if (zoneInfo != null) {
            return zoneInfo;
        }
        int i = 3;
        boolean b = false;
        final char char1 = s.charAt(i++);
        if (char1 == '-') {
            b = true;
        }
        else if (char1 != '+') {
            return null;
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        while (i < length) {
            final char char2 = s.charAt(i++);
            if (char2 == ':') {
                if (n3 > 0) {
                    return null;
                }
                if (n4 > 2) {
                    return null;
                }
                n = n2;
                ++n3;
                n2 = 0;
                n4 = 0;
            }
            else {
                if (char2 < '0' || char2 > '9') {
                    return null;
                }
                n2 = n2 * 10 + (char2 - '0');
                ++n4;
            }
        }
        if (i != length) {
            return null;
        }
        if (n3 == 0) {
            if (n4 <= 2) {
                n = n2;
                n2 = 0;
            }
            else {
                n = n2 / 100;
                n2 %= 100;
            }
        }
        else if (n4 != 2) {
            return null;
        }
        if (n > 23 || n2 > 59) {
            return null;
        }
        final int n5 = (n * 60 + n2) * 60 * 1000;
        ZoneInfo zoneInfo2;
        if (n5 == 0) {
            zoneInfo2 = ZoneInfoFile.getZoneInfo("GMT");
            if (b) {
                zoneInfo2.setID("GMT-00:00");
            }
            else {
                zoneInfo2.setID("GMT+00:00");
            }
        }
        else {
            zoneInfo2 = ZoneInfoFile.getCustomTimeZone(s, b ? (-n5) : n5);
        }
        return zoneInfo2;
    }
    
    static {
        NO_TIMEZONE = null;
    }
}
