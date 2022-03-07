package java.util;

import java.nio.*;
import java.io.*;

public static class Encoder
{
    private final byte[] newline;
    private final int linemax;
    private final boolean isURL;
    private final boolean doPadding;
    private static final char[] toBase64;
    private static final char[] toBase64URL;
    private static final int MIMELINEMAX = 76;
    private static final byte[] CRLF;
    static final Encoder RFC4648;
    static final Encoder RFC4648_URLSAFE;
    static final Encoder RFC2045;
    
    private Encoder(final boolean isURL, final byte[] newline, final int linemax, final boolean doPadding) {
        this.isURL = isURL;
        this.newline = newline;
        this.linemax = linemax;
        this.doPadding = doPadding;
    }
    
    private final int outLength(final int n) {
        int n2;
        if (this.doPadding) {
            n2 = 4 * ((n + 2) / 3);
        }
        else {
            final int n3 = n % 3;
            n2 = 4 * (n / 3) + ((n3 == 0) ? 0 : (n3 + 1));
        }
        if (this.linemax > 0) {
            n2 += (n2 - 1) / this.linemax * this.newline.length;
        }
        return n2;
    }
    
    public byte[] encode(final byte[] array) {
        final byte[] array2 = new byte[this.outLength(array.length)];
        final int encode0 = this.encode0(array, 0, array.length, array2);
        if (encode0 != array2.length) {
            return Arrays.copyOf(array2, encode0);
        }
        return array2;
    }
    
    public int encode(final byte[] array, final byte[] array2) {
        if (array2.length < this.outLength(array.length)) {
            throw new IllegalArgumentException("Output byte array is too small for encoding all input bytes");
        }
        return this.encode0(array, 0, array.length, array2);
    }
    
    public String encodeToString(final byte[] array) {
        final byte[] encode = this.encode(array);
        return new String(encode, 0, 0, encode.length);
    }
    
    public ByteBuffer encode(final ByteBuffer byteBuffer) {
        byte[] copy = new byte[this.outLength(byteBuffer.remaining())];
        int n;
        if (byteBuffer.hasArray()) {
            n = this.encode0(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.arrayOffset() + byteBuffer.limit(), copy);
            byteBuffer.position(byteBuffer.limit());
        }
        else {
            final byte[] array = new byte[byteBuffer.remaining()];
            byteBuffer.get(array);
            n = this.encode0(array, 0, array.length, copy);
        }
        if (n != copy.length) {
            copy = Arrays.copyOf(copy, n);
        }
        return ByteBuffer.wrap(copy);
    }
    
    public OutputStream wrap(final OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return new EncOutputStream(outputStream, this.isURL ? Encoder.toBase64URL : Encoder.toBase64, this.newline, this.linemax, this.doPadding);
    }
    
    public Encoder withoutPadding() {
        if (!this.doPadding) {
            return this;
        }
        return new Encoder(this.isURL, this.newline, this.linemax, false);
    }
    
    private int encode0(final byte[] array, final int n, final int n2, final byte[] array2) {
        final char[] array3 = this.isURL ? Encoder.toBase64URL : Encoder.toBase64;
        int i = n;
        int n3 = (n2 - n) / 3 * 3;
        final int n4 = n + n3;
        if (this.linemax > 0 && n3 > this.linemax / 4 * 3) {
            n3 = this.linemax / 4 * 3;
        }
        int n5 = 0;
        while (i < n4) {
            final int min = Math.min(i + n3, n4);
            int n7;
            for (int j = i, n6 = n5; j < min; n7 = ((array[j++] & 0xFF) << 16 | (array[j++] & 0xFF) << 8 | (array[j++] & 0xFF)), array2[n6++] = (byte)array3[n7 >>> 18 & 0x3F], array2[n6++] = (byte)array3[n7 >>> 12 & 0x3F], array2[n6++] = (byte)array3[n7 >>> 6 & 0x3F], array2[n6++] = (byte)array3[n7 & 0x3F]) {}
            final int n8 = (min - i) / 3 * 4;
            n5 += n8;
            i = min;
            if (n8 == this.linemax && i < n2) {
                final byte[] newline = this.newline;
                for (int length = newline.length, k = 0; k < length; ++k) {
                    array2[n5++] = newline[k];
                }
            }
        }
        if (i < n2) {
            final int n9 = array[i++] & 0xFF;
            array2[n5++] = (byte)array3[n9 >> 2];
            if (i == n2) {
                array2[n5++] = (byte)array3[n9 << 4 & 0x3F];
                if (this.doPadding) {
                    array2[n5++] = 61;
                    array2[n5++] = 61;
                }
            }
            else {
                final int n10 = array[i++] & 0xFF;
                array2[n5++] = (byte)array3[(n9 << 4 & 0x3F) | n10 >> 4];
                array2[n5++] = (byte)array3[n10 << 2 & 0x3F];
                if (this.doPadding) {
                    array2[n5++] = 61;
                }
            }
        }
        return n5;
    }
    
    static {
        toBase64 = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
        toBase64URL = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' };
        CRLF = new byte[] { 13, 10 };
        RFC4648 = new Encoder(false, null, -1, true);
        RFC4648_URLSAFE = new Encoder(true, null, -1, true);
        RFC2045 = new Encoder(false, Encoder.CRLF, 76, true);
    }
}
