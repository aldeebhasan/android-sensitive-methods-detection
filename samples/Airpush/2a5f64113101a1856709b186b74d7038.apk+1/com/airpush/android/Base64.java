package com.airpush.android;

public class Base64 {
    private static final char[] map1 = new char[64];
    private static final byte[] map2 = new byte[128];
    private static final String systemLineSeparator = System.getProperty("line.separator");

    static {
        int i;
        char c = 'A';
        int i2 = 0;
        while (c <= 'Z') {
            i = i2 + 1;
            map1[i2] = c;
            c = (char) (c + 1);
            i2 = i;
        }
        c = 'a';
        while (c <= 'z') {
            i = i2 + 1;
            map1[i2] = c;
            c = (char) (c + 1);
            i2 = i;
        }
        c = '0';
        while (c <= '9') {
            i = i2 + 1;
            map1[i2] = c;
            c = (char) (c + 1);
            i2 = i;
        }
        i = i2 + 1;
        map1[i2] = '+';
        i2 = i + 1;
        map1[i] = '/';
        for (i = 0; i < map2.length; i++) {
            map2[i] = (byte) -1;
        }
        for (i = 0; i < 64; i++) {
            map2[map1[i]] = (byte) i;
        }
    }

    public static String encodeString(String s) {
        return new String(encode(s.getBytes()));
    }

    public static String encodeLines(byte[] in) {
        return encodeLines(in, 0, in.length, 76, systemLineSeparator);
    }

    public static String encodeLines(byte[] in, int iOff, int iLen, int lineLen, String lineSeparator) {
        int blockLen = (lineLen * 3) / 4;
        if (blockLen <= 0) {
            throw new IllegalArgumentException();
        }
        StringBuilder buf = new StringBuilder((((iLen + 2) / 3) * 4) + (lineSeparator.length() * (((iLen + blockLen) - 1) / blockLen)));
        int ip = 0;
        while (ip < iLen) {
            int l = Math.min(iLen - ip, blockLen);
            buf.append(encode(in, iOff + ip, l));
            buf.append(lineSeparator);
            ip += l;
        }
        return buf.toString();
    }

    public static char[] encode(byte[] in) {
        return encode(in, 0, in.length);
    }

    public static char[] encode(byte[] in, int iLen) {
        return encode(in, 0, iLen);
    }

    public static char[] encode(byte[] in, int iOff, int iLen) {
        int oDataLen = ((iLen * 4) + 2) / 3;
        char[] out = new char[(((iLen + 2) / 3) * 4)];
        int iEnd = iOff + iLen;
        int op = 0;
        iOff = iOff;
        while (iOff < iEnd) {
            int ip;
            int ip2;
            iLen = iOff + 1;
            iOff = in[iOff] & 255;
            if (iLen < iEnd) {
                ip = iLen + 1;
                iLen = in[iLen] & 255;
                ip2 = ip;
            } else {
                ip2 = iLen;
                iLen = 0;
            }
            if (ip2 < iEnd) {
                int i = in[ip2] & 255;
                ip2++;
                ip = i;
            } else {
                ip = 0;
            }
            int o0 = iOff >>> 2;
            iOff = ((iOff & 3) << 4) | (iLen >>> 4);
            iLen = ((iLen & 15) << 2) | (ip >>> 6);
            ip &= 63;
            int i2 = op + 1;
            out[op] = map1[o0];
            o0 = i2 + 1;
            out[i2] = map1[iOff];
            out[o0] = o0 < oDataLen ? map1[iLen] : '=';
            iOff = o0 + 1;
            out[iOff] = iOff < oDataLen ? map1[ip] : 61;
            op = iOff + 1;
            iOff = ip2;
        }
        return out;
    }

    public static String decodeString(String s) {
        return new String(decode(s));
    }

    public static byte[] decodeLines(String s) {
        char[] buf = new char[s.length()];
        int p = 0;
        for (int ip = 0; ip < s.length(); ip++) {
            char c = s.charAt(ip);
            if (!(c == ' ' || c == '\r' || c == '\n' || c == '\t')) {
                int p2 = p + 1;
                buf[p] = c;
                p = p2;
            }
        }
        return decode(buf, 0, p);
    }

    public static byte[] decode(String s) {
        return decode(s.toCharArray());
    }

    public static byte[] decode(char[] in) {
        return decode(in, 0, in.length);
    }

    public static byte[] decode(char[] in, int iOff, int iLen) {
        if (iLen % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (iLen > 0 && in[(iOff + iLen) - 1] == '=') {
            iLen--;
        }
        int oLen = (iLen * 3) / 4;
        byte[] out = new byte[oLen];
        int iEnd = iOff + iLen;
        int op = 0;
        iOff = iOff;
        while (iOff < iEnd) {
            int ip;
            iLen = iOff + 1;
            iOff = in[iOff];
            int ip2 = iLen + 1;
            iLen = in[iLen];
            if (ip2 < iEnd) {
                int ip3 = ip2 + 1;
                ip2 = in[ip2];
                ip = ip3;
            } else {
                ip = ip2;
                ip2 = 65;
            }
            if (ip < iEnd) {
                ip3 = ip + 1;
                char c = in[ip];
                ip = ip3;
                ip3 = c;
            } else {
                ip3 = 65;
            }
            if (iOff > 127 || iLen > 127 || ip2 > 127 || ip3 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            iOff = map2[iOff];
            iLen = map2[iLen];
            ip2 = map2[ip2];
            ip3 = map2[ip3];
            if (iOff < 0 || iLen < 0 || ip2 < 0 || ip3 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            iOff = (iOff << 2) | (iLen >>> 4);
            iLen = ((iLen & 15) << 4) | (ip2 >>> 2);
            ip2 = ((ip2 & 3) << 6) | ip3;
            ip3 = op + 1;
            out[op] = (byte) iOff;
            if (ip3 < oLen) {
                iOff = ip3 + 1;
                out[ip3] = (byte) iLen;
                iLen = iOff;
            } else {
                iLen = ip3;
            }
            if (iLen < oLen) {
                iOff = iLen + 1;
                out[iLen] = (byte) ip2;
                op = iOff;
                iOff = ip;
            } else {
                op = iLen;
                iOff = ip;
            }
        }
        return out;
    }

    private Base64() {
    }
}
