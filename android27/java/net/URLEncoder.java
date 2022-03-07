package java.net;

import java.util.*;
import java.io.*;
import java.nio.charset.*;
import sun.security.action.*;
import java.security.*;

public class URLEncoder
{
    static BitSet dontNeedEncoding;
    static final int caseDiff = 32;
    static String dfltEncName;
    
    @Deprecated
    public static String encode(final String s) {
        String encode = null;
        try {
            encode = encode(s, URLEncoder.dfltEncName);
        }
        catch (UnsupportedEncodingException ex) {}
        return encode;
    }
    
    public static String encode(final String s, final String s2) throws UnsupportedEncodingException {
        boolean b = false;
        final StringBuffer sb = new StringBuffer(s.length());
        final CharArrayWriter charArrayWriter = new CharArrayWriter();
        if (s2 == null) {
            throw new NullPointerException("charsetName");
        }
        Charset forName;
        try {
            forName = Charset.forName(s2);
        }
        catch (IllegalCharsetNameException ex) {
            throw new UnsupportedEncodingException(s2);
        }
        catch (UnsupportedCharsetException ex2) {
            throw new UnsupportedEncodingException(s2);
        }
        int i = 0;
        while (i < s.length()) {
            int n = s.charAt(i);
            if (URLEncoder.dontNeedEncoding.get(n)) {
                if (n == 32) {
                    n = 43;
                    b = true;
                }
                sb.append((char)n);
                ++i;
            }
            else {
                do {
                    charArrayWriter.write(n);
                    if (n >= 55296 && n <= 56319 && i + 1 < s.length()) {
                        final char char1 = s.charAt(i + 1);
                        if (char1 < '\udc00' || char1 > '\udfff') {
                            continue;
                        }
                        charArrayWriter.write(char1);
                        ++i;
                    }
                } while (++i < s.length() && !URLEncoder.dontNeedEncoding.get(n = s.charAt(i)));
                charArrayWriter.flush();
                final byte[] bytes = new String(charArrayWriter.toCharArray()).getBytes(forName);
                for (int j = 0; j < bytes.length; ++j) {
                    sb.append('%');
                    char forDigit = Character.forDigit(bytes[j] >> 4 & 0xF, 16);
                    if (Character.isLetter(forDigit)) {
                        forDigit -= ' ';
                    }
                    sb.append(forDigit);
                    char forDigit2 = Character.forDigit(bytes[j] & 0xF, 16);
                    if (Character.isLetter(forDigit2)) {
                        forDigit2 -= ' ';
                    }
                    sb.append(forDigit2);
                }
                charArrayWriter.reset();
                b = true;
            }
        }
        return b ? sb.toString() : s;
    }
    
    static {
        URLEncoder.dfltEncName = null;
        URLEncoder.dontNeedEncoding = new BitSet(256);
        for (int i = 97; i <= 122; ++i) {
            URLEncoder.dontNeedEncoding.set(i);
        }
        for (int j = 65; j <= 90; ++j) {
            URLEncoder.dontNeedEncoding.set(j);
        }
        for (int k = 48; k <= 57; ++k) {
            URLEncoder.dontNeedEncoding.set(k);
        }
        URLEncoder.dontNeedEncoding.set(32);
        URLEncoder.dontNeedEncoding.set(45);
        URLEncoder.dontNeedEncoding.set(95);
        URLEncoder.dontNeedEncoding.set(46);
        URLEncoder.dontNeedEncoding.set(42);
        URLEncoder.dfltEncName = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("file.encoding"));
    }
}
