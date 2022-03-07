package java.util;

import java.nio.charset.*;
import java.nio.*;
import java.io.*;

public class Base64
{
    public static Encoder getEncoder() {
        return Encoder.RFC4648;
    }
    
    public static Encoder getUrlEncoder() {
        return Encoder.RFC4648_URLSAFE;
    }
    
    public static Encoder getMimeEncoder() {
        return Encoder.RFC2045;
    }
    
    public static Encoder getMimeEncoder(final int n, final byte[] array) {
        Objects.requireNonNull(array);
        final int[] access$000 = Decoder.fromBase64;
        for (final byte b : array) {
            if (access$000[b & 0xFF] != -1) {
                throw new IllegalArgumentException("Illegal base64 line separator character 0x" + Integer.toString(b, 16));
            }
        }
        if (n <= 0) {
            return Encoder.RFC4648;
        }
        return new Encoder(false, array, n >> 2 << 2, true);
    }
    
    public static Decoder getDecoder() {
        return Decoder.RFC4648;
    }
    
    public static Decoder getUrlDecoder() {
        return Decoder.RFC4648_URLSAFE;
    }
    
    public static Decoder getMimeDecoder() {
        return Decoder.RFC2045;
    }
    
    private static class DecInputStream extends InputStream
    {
        private final InputStream is;
        private final boolean isMIME;
        private final int[] base64;
        private int bits;
        private int nextin;
        private int nextout;
        private boolean eof;
        private boolean closed;
        private byte[] sbBuf;
        
        DecInputStream(final InputStream is, final int[] base64, final boolean isMIME) {
            this.bits = 0;
            this.nextin = 18;
            this.nextout = -8;
            this.eof = false;
            this.closed = false;
            this.sbBuf = new byte[1];
            this.is = is;
            this.base64 = base64;
            this.isMIME = isMIME;
        }
        
        @Override
        public int read() throws IOException {
            return (this.read(this.sbBuf, 0, 1) == -1) ? -1 : (this.sbBuf[0] & 0xFF);
        }
        
        @Override
        public int read(final byte[] array, int n, int i) throws IOException {
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            if (this.eof && this.nextout < 0) {
                return -1;
            }
            if (n < 0 || i < 0 || i > array.length - n) {
                throw new IndexOutOfBoundsException();
            }
            final int n2 = n;
            Label_0118: {
                if (this.nextout >= 0) {
                    while (i != 0) {
                        array[n++] = (byte)(this.bits >> this.nextout);
                        --i;
                        this.nextout -= 8;
                        if (this.nextout < 0) {
                            this.bits = 0;
                            break Label_0118;
                        }
                    }
                    return n - n2;
                }
            }
            while (i > 0) {
                final int read = this.is.read();
                if (read == -1) {
                    this.eof = true;
                    if (this.nextin != 18) {
                        if (this.nextin == 12) {
                            throw new IOException("Base64 stream has one un-decoded dangling byte.");
                        }
                        array[n++] = (byte)(this.bits >> 16);
                        --i;
                        if (this.nextin == 0) {
                            if (i == 0) {
                                this.bits >>= 8;
                                this.nextout = 0;
                            }
                            else {
                                array[n++] = (byte)(this.bits >> 8);
                            }
                        }
                    }
                    if (n == n2) {
                        return -1;
                    }
                    return n - n2;
                }
                else if (read == 61) {
                    if (this.nextin == 18 || this.nextin == 12 || (this.nextin == 6 && this.is.read() != 61)) {
                        throw new IOException("Illegal base64 ending sequence:" + this.nextin);
                    }
                    array[n++] = (byte)(this.bits >> 16);
                    --i;
                    if (this.nextin == 0) {
                        if (i == 0) {
                            this.bits >>= 8;
                            this.nextout = 0;
                        }
                        else {
                            array[n++] = (byte)(this.bits >> 8);
                        }
                    }
                    this.eof = true;
                    break;
                }
                else {
                    final int n3;
                    if ((n3 = this.base64[read]) == -1) {
                        if (this.isMIME) {
                            continue;
                        }
                        throw new IOException("Illegal base64 character " + Integer.toString(n3, 16));
                    }
                    else {
                        this.bits |= n3 << this.nextin;
                        if (this.nextin == 0) {
                            this.nextin = 18;
                            this.nextout = 16;
                            while (this.nextout >= 0) {
                                array[n++] = (byte)(this.bits >> this.nextout);
                                --i;
                                this.nextout -= 8;
                                if (i == 0 && this.nextout >= 0) {
                                    return n - n2;
                                }
                            }
                            this.bits = 0;
                        }
                        else {
                            this.nextin -= 6;
                        }
                    }
                }
            }
            return n - n2;
        }
        
        @Override
        public int available() throws IOException {
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            return this.is.available();
        }
        
        @Override
        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                this.is.close();
            }
        }
    }
    
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
    
    private static class EncOutputStream extends FilterOutputStream
    {
        private int leftover;
        private int b0;
        private int b1;
        private int b2;
        private boolean closed;
        private final char[] base64;
        private final byte[] newline;
        private final int linemax;
        private final boolean doPadding;
        private int linepos;
        
        EncOutputStream(final OutputStream outputStream, final char[] base64, final byte[] newline, final int linemax, final boolean doPadding) {
            super(outputStream);
            this.leftover = 0;
            this.closed = false;
            this.linepos = 0;
            this.base64 = base64;
            this.newline = newline;
            this.linemax = linemax;
            this.doPadding = doPadding;
        }
        
        @Override
        public void write(final int n) throws IOException {
            this.write(new byte[] { (byte)(n & 0xFF) }, 0, 1);
        }
        
        private void checkNewline() throws IOException {
            if (this.linepos == this.linemax) {
                this.out.write(this.newline);
                this.linepos = 0;
            }
        }
        
        @Override
        public void write(final byte[] array, int n, int n2) throws IOException {
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            if (n < 0 || n2 < 0 || n2 > array.length - n) {
                throw new ArrayIndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return;
            }
            if (this.leftover != 0) {
                if (this.leftover == 1) {
                    this.b1 = (array[n++] & 0xFF);
                    if (--n2 == 0) {
                        ++this.leftover;
                        return;
                    }
                }
                this.b2 = (array[n++] & 0xFF);
                --n2;
                this.checkNewline();
                this.out.write(this.base64[this.b0 >> 2]);
                this.out.write(this.base64[(this.b0 << 4 & 0x3F) | this.b1 >> 4]);
                this.out.write(this.base64[(this.b1 << 2 & 0x3F) | this.b2 >> 6]);
                this.out.write(this.base64[this.b2 & 0x3F]);
                this.linepos += 4;
            }
            int n3 = n2 / 3;
            this.leftover = n2 - n3 * 3;
            while (n3-- > 0) {
                this.checkNewline();
                final int n4 = (array[n++] & 0xFF) << 16 | (array[n++] & 0xFF) << 8 | (array[n++] & 0xFF);
                this.out.write(this.base64[n4 >>> 18 & 0x3F]);
                this.out.write(this.base64[n4 >>> 12 & 0x3F]);
                this.out.write(this.base64[n4 >>> 6 & 0x3F]);
                this.out.write(this.base64[n4 & 0x3F]);
                this.linepos += 4;
            }
            if (this.leftover == 1) {
                this.b0 = (array[n++] & 0xFF);
            }
            else if (this.leftover == 2) {
                this.b0 = (array[n++] & 0xFF);
                this.b1 = (array[n++] & 0xFF);
            }
        }
        
        @Override
        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                if (this.leftover == 1) {
                    this.checkNewline();
                    this.out.write(this.base64[this.b0 >> 2]);
                    this.out.write(this.base64[this.b0 << 4 & 0x3F]);
                    if (this.doPadding) {
                        this.out.write(61);
                        this.out.write(61);
                    }
                }
                else if (this.leftover == 2) {
                    this.checkNewline();
                    this.out.write(this.base64[this.b0 >> 2]);
                    this.out.write(this.base64[(this.b0 << 4 & 0x3F) | this.b1 >> 4]);
                    this.out.write(this.base64[this.b1 << 2 & 0x3F]);
                    if (this.doPadding) {
                        this.out.write(61);
                    }
                }
                this.leftover = 0;
                this.out.close();
            }
        }
    }
}
