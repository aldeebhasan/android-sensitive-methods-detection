package java.net;

import java.io.*;

public class URLDecoder
{
    static String dfltEncName;
    
    @Deprecated
    public static String decode(final String s) {
        String decode = null;
        try {
            decode = decode(s, URLDecoder.dfltEncName);
        }
        catch (UnsupportedEncodingException ex) {}
        return decode;
    }
    
    public static String decode(final String s, final String s2) throws UnsupportedEncodingException {
        boolean b = false;
        final int length = s.length();
        final StringBuffer sb = new StringBuffer((length > 500) ? (length / 2) : length);
        int i = 0;
        if (s2.length() == 0) {
            throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
        }
        byte[] array = null;
        while (i < length) {
            char c = s.charAt(i);
            switch (c) {
                case 43: {
                    sb.append(' ');
                    ++i;
                    b = true;
                    continue;
                }
                case 37: {
                    try {
                        if (array == null) {
                            array = new byte[(length - i) / 3];
                        }
                        int n = 0;
                        while (i + 2 < length && c == '%') {
                            final int int1 = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                            if (int1 < 0) {
                                throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                            }
                            array[n++] = (byte)int1;
                            i += 3;
                            if (i >= length) {
                                continue;
                            }
                            c = s.charAt(i);
                        }
                        if (i < length && c == '%') {
                            throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
                        }
                        sb.append(new String(array, 0, n, s2));
                    }
                    catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + ex.getMessage());
                    }
                    b = true;
                    continue;
                }
                default: {
                    sb.append(c);
                    ++i;
                    continue;
                }
            }
        }
        return b ? sb.toString() : s;
    }
    
    static {
        URLDecoder.dfltEncName = URLEncoder.dfltEncName;
    }
}
