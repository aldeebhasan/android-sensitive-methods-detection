package java.io;

public class DataInputStream extends FilterInputStream implements DataInput
{
    private byte[] bytearr;
    private char[] chararr;
    private byte[] readBuffer;
    private char[] lineBuffer;
    
    public DataInputStream(final InputStream inputStream) {
        super(inputStream);
        this.bytearr = new byte[80];
        this.chararr = new char[80];
        this.readBuffer = new byte[8];
    }
    
    @Override
    public final int read(final byte[] array) throws IOException {
        return this.in.read(array, 0, array.length);
    }
    
    @Override
    public final int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.in.read(array, n, n2);
    }
    
    @Override
    public final void readFully(final byte[] array) throws IOException {
        this.readFully(array, 0, array.length);
    }
    
    @Override
    public final void readFully(final byte[] array, final int n, final int n2) throws IOException {
        if (n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        int read;
        for (int i = 0; i < n2; i += read) {
            read = this.in.read(array, n + i, n2 - i);
            if (read < 0) {
                throw new EOFException();
            }
        }
    }
    
    @Override
    public final int skipBytes(final int n) throws IOException {
        int n2;
        int n3;
        for (n2 = 0; n2 < n && (n3 = (int)this.in.skip(n - n2)) > 0; n2 += n3) {}
        return n2;
    }
    
    @Override
    public final boolean readBoolean() throws IOException {
        final int read = this.in.read();
        if (read < 0) {
            throw new EOFException();
        }
        return read != 0;
    }
    
    @Override
    public final byte readByte() throws IOException {
        final int read = this.in.read();
        if (read < 0) {
            throw new EOFException();
        }
        return (byte)read;
    }
    
    @Override
    public final int readUnsignedByte() throws IOException {
        final int read = this.in.read();
        if (read < 0) {
            throw new EOFException();
        }
        return read;
    }
    
    @Override
    public final short readShort() throws IOException {
        final int read = this.in.read();
        final int read2 = this.in.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (short)((read << 8) + (read2 << 0));
    }
    
    @Override
    public final int readUnsignedShort() throws IOException {
        final int read = this.in.read();
        final int read2 = this.in.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (read << 8) + (read2 << 0);
    }
    
    @Override
    public final char readChar() throws IOException {
        final int read = this.in.read();
        final int read2 = this.in.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (char)((read << 8) + (read2 << 0));
    }
    
    @Override
    public final int readInt() throws IOException {
        final int read = this.in.read();
        final int read2 = this.in.read();
        final int read3 = this.in.read();
        final int read4 = this.in.read();
        if ((read | read2 | read3 | read4) < 0) {
            throw new EOFException();
        }
        return (read << 24) + (read2 << 16) + (read3 << 8) + (read4 << 0);
    }
    
    @Override
    public final long readLong() throws IOException {
        this.readFully(this.readBuffer, 0, 8);
        return (this.readBuffer[0] << 56) + ((this.readBuffer[1] & 0xFF) << 48) + ((this.readBuffer[2] & 0xFF) << 40) + ((this.readBuffer[3] & 0xFF) << 32) + ((this.readBuffer[4] & 0xFF) << 24) + ((this.readBuffer[5] & 0xFF) << 16) + ((this.readBuffer[6] & 0xFF) << 8) + ((this.readBuffer[7] & 0xFF) << 0);
    }
    
    @Override
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    @Override
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    @Deprecated
    @Override
    public final String readLine() throws IOException {
        char[] lineBuffer = this.lineBuffer;
        if (lineBuffer == null) {
            final char[] lineBuffer2 = new char[128];
            this.lineBuffer = lineBuffer2;
            lineBuffer = lineBuffer2;
        }
        int length = lineBuffer.length;
        int n = 0;
        int read = 0;
    Label_0183:
        while (true) {
            switch (read = this.in.read()) {
                case -1:
                case 10: {
                    break Label_0183;
                }
                case 13: {
                    final int read2 = this.in.read();
                    if (read2 != 10 && read2 != -1) {
                        if (!(this.in instanceof PushbackInputStream)) {
                            this.in = new PushbackInputStream(this.in);
                        }
                        ((PushbackInputStream)this.in).unread(read2);
                        break Label_0183;
                    }
                    break Label_0183;
                }
                default: {
                    if (--length < 0) {
                        lineBuffer = new char[n + 128];
                        length = lineBuffer.length - n - 1;
                        System.arraycopy(this.lineBuffer, 0, lineBuffer, 0, n);
                        this.lineBuffer = lineBuffer;
                    }
                    lineBuffer[n++] = (char)read;
                    continue;
                }
            }
        }
        if (read == -1 && n == 0) {
            return null;
        }
        return String.copyValueOf(lineBuffer, 0, n);
    }
    
    @Override
    public final String readUTF() throws IOException {
        return readUTF(this);
    }
    
    public static final String readUTF(final DataInput dataInput) throws IOException {
        final int unsignedShort = dataInput.readUnsignedShort();
        char[] chararr;
        byte[] bytearr;
        if (dataInput instanceof DataInputStream) {
            final DataInputStream dataInputStream = (DataInputStream)dataInput;
            if (dataInputStream.bytearr.length < unsignedShort) {
                dataInputStream.bytearr = new byte[unsignedShort * 2];
                dataInputStream.chararr = new char[unsignedShort * 2];
            }
            chararr = dataInputStream.chararr;
            bytearr = dataInputStream.bytearr;
        }
        else {
            bytearr = new byte[unsignedShort];
            chararr = new char[unsignedShort];
        }
        int i = 0;
        int n = 0;
        dataInput.readFully(bytearr, 0, unsignedShort);
        while (i < unsignedShort) {
            final int n2 = bytearr[i] & 0xFF;
            if (n2 > 127) {
                break;
            }
            ++i;
            chararr[n++] = (char)n2;
        }
        while (i < unsignedShort) {
            final int n3 = bytearr[i] & 0xFF;
            switch (n3 >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7: {
                    ++i;
                    chararr[n++] = (char)n3;
                    continue;
                }
                case 12:
                case 13: {
                    i += 2;
                    if (i > unsignedShort) {
                        throw new UTFDataFormatException("malformed input: partial character at end");
                    }
                    final byte b = bytearr[i - 1];
                    if ((b & 0xC0) != 0x80) {
                        throw new UTFDataFormatException("malformed input around byte " + i);
                    }
                    chararr[n++] = (char)((n3 & 0x1F) << 6 | (b & 0x3F));
                    continue;
                }
                case 14: {
                    i += 3;
                    if (i > unsignedShort) {
                        throw new UTFDataFormatException("malformed input: partial character at end");
                    }
                    final byte b2 = bytearr[i - 2];
                    final byte b3 = bytearr[i - 1];
                    if ((b2 & 0xC0) != 0x80 || (b3 & 0xC0) != 0x80) {
                        throw new UTFDataFormatException("malformed input around byte " + (i - 1));
                    }
                    chararr[n++] = (char)((n3 & 0xF) << 12 | (b2 & 0x3F) << 6 | (b3 & 0x3F) << 0);
                    continue;
                }
                default: {
                    throw new UTFDataFormatException("malformed input around byte " + i);
                }
            }
        }
        return new String(chararr, 0, n);
    }
}
