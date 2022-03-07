package java.io;

public class DataOutputStream extends FilterOutputStream implements DataOutput
{
    protected int written;
    private byte[] bytearr;
    private byte[] writeBuffer;
    
    public DataOutputStream(final OutputStream outputStream) {
        super(outputStream);
        this.bytearr = null;
        this.writeBuffer = new byte[8];
    }
    
    private void incCount(final int n) {
        int written = this.written + n;
        if (written < 0) {
            written = Integer.MAX_VALUE;
        }
        this.written = written;
    }
    
    @Override
    public synchronized void write(final int n) throws IOException {
        this.out.write(n);
        this.incCount(1);
    }
    
    @Override
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
        this.incCount(n2);
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public final void writeBoolean(final boolean b) throws IOException {
        this.out.write(b ? 1 : 0);
        this.incCount(1);
    }
    
    @Override
    public final void writeByte(final int n) throws IOException {
        this.out.write(n);
        this.incCount(1);
    }
    
    @Override
    public final void writeShort(final int n) throws IOException {
        this.out.write(n >>> 8 & 0xFF);
        this.out.write(n >>> 0 & 0xFF);
        this.incCount(2);
    }
    
    @Override
    public final void writeChar(final int n) throws IOException {
        this.out.write(n >>> 8 & 0xFF);
        this.out.write(n >>> 0 & 0xFF);
        this.incCount(2);
    }
    
    @Override
    public final void writeInt(final int n) throws IOException {
        this.out.write(n >>> 24 & 0xFF);
        this.out.write(n >>> 16 & 0xFF);
        this.out.write(n >>> 8 & 0xFF);
        this.out.write(n >>> 0 & 0xFF);
        this.incCount(4);
    }
    
    @Override
    public final void writeLong(final long n) throws IOException {
        this.writeBuffer[0] = (byte)(n >>> 56);
        this.writeBuffer[1] = (byte)(n >>> 48);
        this.writeBuffer[2] = (byte)(n >>> 40);
        this.writeBuffer[3] = (byte)(n >>> 32);
        this.writeBuffer[4] = (byte)(n >>> 24);
        this.writeBuffer[5] = (byte)(n >>> 16);
        this.writeBuffer[6] = (byte)(n >>> 8);
        this.writeBuffer[7] = (byte)(n >>> 0);
        this.out.write(this.writeBuffer, 0, 8);
        this.incCount(8);
    }
    
    @Override
    public final void writeFloat(final float n) throws IOException {
        this.writeInt(Float.floatToIntBits(n));
    }
    
    @Override
    public final void writeDouble(final double n) throws IOException {
        this.writeLong(Double.doubleToLongBits(n));
    }
    
    @Override
    public final void writeBytes(final String s) throws IOException {
        final int length = s.length();
        for (int i = 0; i < length; ++i) {
            this.out.write((byte)s.charAt(i));
        }
        this.incCount(length);
    }
    
    @Override
    public final void writeChars(final String s) throws IOException {
        final int length = s.length();
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            this.out.write(char1 >>> 8 & '\u00ff');
            this.out.write(char1 >>> 0 & '\u00ff');
        }
        this.incCount(length * 2);
    }
    
    @Override
    public final void writeUTF(final String s) throws IOException {
        writeUTF(s, this);
    }
    
    static int writeUTF(final String s, final DataOutput dataOutput) throws IOException {
        final int length = s.length();
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 >= '\u0001' && char1 <= '\u007f') {
                ++n;
            }
            else if (char1 > '\u07ff') {
                n += 3;
            }
            else {
                n += 2;
            }
        }
        if (n > 65535) {
            throw new UTFDataFormatException("encoded string too long: " + n + " bytes");
        }
        byte[] bytearr;
        if (dataOutput instanceof DataOutputStream) {
            final DataOutputStream dataOutputStream = (DataOutputStream)dataOutput;
            if (dataOutputStream.bytearr == null || dataOutputStream.bytearr.length < n + 2) {
                dataOutputStream.bytearr = new byte[n * 2 + 2];
            }
            bytearr = dataOutputStream.bytearr;
        }
        else {
            bytearr = new byte[n + 2];
        }
        bytearr[n2++] = (byte)(n >>> 8 & 0xFF);
        bytearr[n2++] = (byte)(n >>> 0 & 0xFF);
        int j;
        for (j = 0; j < length; ++j) {
            final char char2 = s.charAt(j);
            if (char2 < '\u0001') {
                break;
            }
            if (char2 > '\u007f') {
                break;
            }
            bytearr[n2++] = (byte)char2;
        }
        while (j < length) {
            final char char3 = s.charAt(j);
            if (char3 >= '\u0001' && char3 <= '\u007f') {
                bytearr[n2++] = (byte)char3;
            }
            else if (char3 > '\u07ff') {
                bytearr[n2++] = (byte)('\u00e0' | (char3 >> 12 & '\u000f'));
                bytearr[n2++] = (byte)('\u0080' | (char3 >> 6 & '?'));
                bytearr[n2++] = (byte)('\u0080' | (char3 >> 0 & '?'));
            }
            else {
                bytearr[n2++] = (byte)('\u00c0' | (char3 >> 6 & '\u001f'));
                bytearr[n2++] = (byte)('\u0080' | (char3 >> 0 & '?'));
            }
            ++j;
        }
        dataOutput.write(bytearr, 0, n + 2);
        return n + 2;
    }
    
    public final int size() {
        return this.written;
    }
}
