package java.util;

import java.nio.charset.*;
import java.nio.*;
import java.io.*;

public static class Decoder
{
    private final boolean isURL;
    private final boolean isMIME;
    private static final int[] fromBase64;
    private static final int[] fromBase64URL;
    static final Decoder RFC4648;
    static final Decoder RFC4648_URLSAFE;
    static final Decoder RFC2045;
    
    private Decoder(final boolean isURL, final boolean isMIME) {
        this.isURL = isURL;
        this.isMIME = isMIME;
    }
    
    public byte[] decode(final byte[] array) {
        byte[] copy = new byte[this.outLength(array, 0, array.length)];
        final int decode0 = this.decode0(array, 0, array.length, copy);
        if (decode0 != copy.length) {
            copy = Arrays.copyOf(copy, decode0);
        }
        return copy;
    }
    
    public byte[] decode(final String s) {
        return this.decode(s.getBytes(StandardCharsets.ISO_8859_1));
    }
    
    public int decode(final byte[] array, final byte[] array2) {
        if (array2.length < this.outLength(array, 0, array.length)) {
            throw new IllegalArgumentException("Output byte array is too small for decoding all input bytes");
        }
        return this.decode0(array, 0, array.length, array2);
    }
    
    public ByteBuffer decode(final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        try {
            byte[] array;
            int n;
            int length;
            if (byteBuffer.hasArray()) {
                array = byteBuffer.array();
                n = byteBuffer.arrayOffset() + byteBuffer.position();
                length = byteBuffer.arrayOffset() + byteBuffer.limit();
                byteBuffer.position(byteBuffer.limit());
            }
            else {
                array = new byte[byteBuffer.remaining()];
                byteBuffer.get(array);
                n = 0;
                length = array.length;
            }
            final byte[] array2 = new byte[this.outLength(array, n, length)];
            return ByteBuffer.wrap(array2, 0, this.decode0(array, n, length, array2));
        }
        catch (IllegalArgumentException ex) {
            byteBuffer.position(position);
            throw ex;
        }
    }
    
    public InputStream wrap(final InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return new DecInputStream(inputStream, this.isURL ? Decoder.fromBase64URL : Decoder.fromBase64, this.isMIME);
    }
    
    private int outLength(final byte[] array, int i, final int n) {
        final int[] array2 = this.isURL ? Decoder.fromBase64URL : Decoder.fromBase64;
        int n2 = 0;
        int n3 = n - i;
        if (n3 == 0) {
            return 0;
        }
        if (n3 >= 2) {
            if (this.isMIME) {
                int n4 = 0;
                while (i < n) {
                    final int n5 = array[i++] & 0xFF;
                    if (n5 == 61) {
                        n3 -= n - i + 1;
                        break;
                    }
                    if (array2[n5] != -1) {
                        continue;
                    }
                    ++n4;
                }
                n3 -= n4;
            }
            else if (array[n - 1] == 61) {
                ++n2;
                if (array[n - 2] == 61) {
                    ++n2;
                }
            }
            if (n2 == 0 && (n3 & 0x3) != 0x0) {
                n2 = 4 - (n3 & 0x3);
            }
            return 3 * ((n3 + 3) / 4) - n2;
        }
        if (this.isMIME && array2[0] == -1) {
            return 0;
        }
        throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
    }
    
    private int decode0(final byte[] array, int i, final int n, final byte[] array2) {
        final int[] array3 = this.isURL ? Decoder.fromBase64URL : Decoder.fromBase64;
        int n2 = 0;
        int n3 = 0;
        int n4 = 18;
        while (i < n) {
            final int n5;
            if ((n5 = array3[array[i++] & 0xFF]) < 0) {
                if (n5 == -2) {
                    if ((n4 == 6 && (i == n || array[i++] != 61)) || n4 == 18) {
                        throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
                    }
                    break;
                }
                else {
                    if (this.isMIME) {
                        continue;
                    }
                    throw new IllegalArgumentException("Illegal base64 character " + Integer.toString(array[i - 1], 16));
                }
            }
            else {
                n3 |= n5 << n4;
                n4 -= 6;
                if (n4 >= 0) {
                    continue;
                }
                array2[n2++] = (byte)(n3 >> 16);
                array2[n2++] = (byte)(n3 >> 8);
                array2[n2++] = (byte)n3;
                n4 = 18;
                n3 = 0;
            }
        }
        if (n4 == 6) {
            array2[n2++] = (byte)(n3 >> 16);
        }
        else if (n4 == 0) {
            array2[n2++] = (byte)(n3 >> 16);
            array2[n2++] = (byte)(n3 >> 8);
        }
        else if (n4 == 12) {
            throw new IllegalArgumentException("Last unit does not have enough valid bits");
        }
        while (i < n) {
            if (this.isMIME && array3[array[i++]] < 0) {
                continue;
            }
            throw new IllegalArgumentException("Input byte array has incorrect ending byte at " + i);
        }
        return n2;
    }
    
    static {
        Arrays.fill(fromBase64 = new int[256], -1);
        for (int i = 0; i < Encoder.toBase64.length; ++i) {
            Decoder.fromBase64[Encoder.toBase64[i]] = i;
        }
        Decoder.fromBase64[61] = -2;
        Arrays.fill(fromBase64URL = new int[256], -1);
        for (int j = 0; j < Encoder.toBase64URL.length; ++j) {
            Decoder.fromBase64URL[Encoder.toBase64URL[j]] = j;
        }
        Decoder.fromBase64URL[61] = -2;
        RFC4648 = new Decoder(false, false);
        RFC4648_URLSAFE = new Decoder(true, false);
        RFC2045 = new Decoder(false, true);
    }
}
