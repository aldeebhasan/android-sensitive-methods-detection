package java.net;

import sun.text.normalizer.*;
import java.text.*;
import sun.net.idn.*;
import java.security.*;
import java.io.*;

public final class IDN
{
    public static final int ALLOW_UNASSIGNED = 1;
    public static final int USE_STD3_ASCII_RULES = 2;
    private static final String ACE_PREFIX = "xn--";
    private static final int ACE_PREFIX_LENGTH;
    private static final int MAX_LABEL_LENGTH = 63;
    private static StringPrep namePrep;
    
    public static String toASCII(final String s, final int n) {
        int i = 0;
        final StringBuffer sb = new StringBuffer();
        if (isRootLabel(s)) {
            return ".";
        }
        while (i < s.length()) {
            final int searchDots = searchDots(s, i);
            sb.append(toASCIIInternal(s.substring(i, searchDots), n));
            if (searchDots != s.length()) {
                sb.append('.');
            }
            i = searchDots + 1;
        }
        return sb.toString();
    }
    
    public static String toASCII(final String s) {
        return toASCII(s, 0);
    }
    
    public static String toUnicode(final String s, final int n) {
        int i = 0;
        final StringBuffer sb = new StringBuffer();
        if (isRootLabel(s)) {
            return ".";
        }
        while (i < s.length()) {
            final int searchDots = searchDots(s, i);
            sb.append(toUnicodeInternal(s.substring(i, searchDots), n));
            if (searchDots != s.length()) {
                sb.append('.');
            }
            i = searchDots + 1;
        }
        return sb.toString();
    }
    
    public static String toUnicode(final String s) {
        return toUnicode(s, 0);
    }
    
    private static String toASCIIInternal(final String s, final int n) {
        final boolean allASCII = isAllASCII(s);
        StringBuffer sb;
        if (!allASCII) {
            final UCharacterIterator instance = UCharacterIterator.getInstance(s);
            try {
                sb = IDN.namePrep.prepare(instance, n);
            }
            catch (ParseException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        else {
            sb = new StringBuffer(s);
        }
        if (sb.length() == 0) {
            throw new IllegalArgumentException("Empty label is not a legal name");
        }
        if ((n & 0x2) != 0x0) {
            for (int i = 0; i < sb.length(); ++i) {
                if (isNonLDHAsciiCodePoint(sb.charAt(i))) {
                    throw new IllegalArgumentException("Contains non-LDH ASCII characters");
                }
            }
            if (sb.charAt(0) == '-' || sb.charAt(sb.length() - 1) == '-') {
                throw new IllegalArgumentException("Has leading or trailing hyphen");
            }
        }
        if (!allASCII && !isAllASCII(sb.toString())) {
            if (startsWithACEPrefix(sb)) {
                throw new IllegalArgumentException("The input starts with the ACE Prefix");
            }
            StringBuffer encode;
            try {
                encode = Punycode.encode(sb, null);
            }
            catch (ParseException ex2) {
                throw new IllegalArgumentException(ex2);
            }
            sb = toASCIILower(encode);
            sb.insert(0, "xn--");
        }
        if (sb.length() > 63) {
            throw new IllegalArgumentException("The label in the input is too long");
        }
        return sb.toString();
    }
    
    private static String toUnicodeInternal(final String s, final int n) {
        StringBuffer prepare = null;
        Label_0045: {
            if (!isAllASCII(s)) {
                try {
                    prepare = IDN.namePrep.prepare(UCharacterIterator.getInstance(s), n);
                    break Label_0045;
                }
                catch (Exception ex) {
                    return s;
                }
            }
            prepare = new StringBuffer(s);
        }
        if (startsWithACEPrefix(prepare)) {
            final String substring = prepare.substring(IDN.ACE_PREFIX_LENGTH, prepare.length());
            try {
                final StringBuffer decode = Punycode.decode(new StringBuffer(substring), null);
                if (toASCII(decode.toString(), n).equalsIgnoreCase(prepare.toString())) {
                    return decode.toString();
                }
            }
            catch (Exception ex2) {}
        }
        return s;
    }
    
    private static boolean isNonLDHAsciiCodePoint(final int n) {
        return (0 <= n && n <= 44) || (46 <= n && n <= 47) || (58 <= n && n <= 64) || (91 <= n && n <= 96) || (123 <= n && n <= 127);
    }
    
    private static int searchDots(final String s, final int n) {
        int n2;
        for (n2 = n; n2 < s.length() && !isLabelSeparator(s.charAt(n2)); ++n2) {}
        return n2;
    }
    
    private static boolean isRootLabel(final String s) {
        return s.length() == 1 && isLabelSeparator(s.charAt(0));
    }
    
    private static boolean isLabelSeparator(final char c) {
        return c == '.' || c == '\u3002' || c == '\uff0e' || c == '\uff61';
    }
    
    private static boolean isAllASCII(final String s) {
        boolean b = true;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) > '\u007f') {
                b = false;
                break;
            }
        }
        return b;
    }
    
    private static boolean startsWithACEPrefix(final StringBuffer sb) {
        boolean b = true;
        if (sb.length() < IDN.ACE_PREFIX_LENGTH) {
            return false;
        }
        for (int i = 0; i < IDN.ACE_PREFIX_LENGTH; ++i) {
            if (toASCIILower(sb.charAt(i)) != "xn--".charAt(i)) {
                b = false;
            }
        }
        return b;
    }
    
    private static char toASCIILower(final char c) {
        if ('A' <= c && c <= 'Z') {
            return (char)(c + 'a' - 'A');
        }
        return c;
    }
    
    private static StringBuffer toASCIILower(final StringBuffer sb) {
        final StringBuffer sb2 = new StringBuffer();
        for (int i = 0; i < sb.length(); ++i) {
            sb2.append(toASCIILower(sb.charAt(i)));
        }
        return sb2;
    }
    
    static {
        ACE_PREFIX_LENGTH = "xn--".length();
        IDN.namePrep = null;
        try {
            InputStream resourceAsStream;
            if (System.getSecurityManager() != null) {
                resourceAsStream = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction<InputStream>() {
                    @Override
                    public InputStream run() {
                        return StringPrep.class.getResourceAsStream("uidna.spp");
                    }
                });
            }
            else {
                resourceAsStream = StringPrep.class.getResourceAsStream("uidna.spp");
            }
            IDN.namePrep = new StringPrep(resourceAsStream);
            resourceAsStream.close();
        }
        catch (IOException ex) {
            assert false;
        }
    }
}
