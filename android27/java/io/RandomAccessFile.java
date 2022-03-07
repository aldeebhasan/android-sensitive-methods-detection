package java.io;

import java.nio.channels.*;
import sun.nio.ch.*;

public class RandomAccessFile implements DataOutput, DataInput, Closeable
{
    private FileDescriptor fd;
    private FileChannel channel;
    private boolean rw;
    private final String path;
    private Object closeLock;
    private volatile boolean closed;
    private static final int O_RDONLY = 1;
    private static final int O_RDWR = 2;
    private static final int O_SYNC = 4;
    private static final int O_DSYNC = 8;
    
    public RandomAccessFile(final String s, final String s2) throws FileNotFoundException {
        this((s != null) ? new File(s) : null, s2);
    }
    
    public RandomAccessFile(final File file, final String s) throws FileNotFoundException {
        this.channel = null;
        this.closeLock = new Object();
        this.closed = false;
        final String path = (file != null) ? file.getPath() : null;
        int n = -1;
        if (s.equals("r")) {
            n = 1;
        }
        else if (s.startsWith("rw")) {
            n = 2;
            this.rw = true;
            if (s.length() > 2) {
                if (s.equals("rws")) {
                    n |= 0x4;
                }
                else if (s.equals("rwd")) {
                    n |= 0x8;
                }
                else {
                    n = -1;
                }
            }
        }
        if (n < 0) {
            throw new IllegalArgumentException("Illegal mode \"" + s + "\" must be one of \"r\", \"rw\", \"rws\", or \"rwd\"");
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(path);
            if (this.rw) {
                securityManager.checkWrite(path);
            }
        }
        if (path == null) {
            throw new NullPointerException();
        }
        if (file.isInvalid()) {
            throw new FileNotFoundException("Invalid file path");
        }
        (this.fd = new FileDescriptor()).attach(this);
        this.open(this.path = path, n);
    }
    
    public final FileDescriptor getFD() throws IOException {
        if (this.fd != null) {
            return this.fd;
        }
        throw new IOException();
    }
    
    public final FileChannel getChannel() {
        synchronized (this) {
            if (this.channel == null) {
                this.channel = FileChannelImpl.open(this.fd, this.path, true, this.rw, this);
            }
            return this.channel;
        }
    }
    
    private native void open0(final String p0, final int p1) throws FileNotFoundException;
    
    private void open(final String s, final int n) throws FileNotFoundException {
        this.open0(s, n);
    }
    
    public int read() throws IOException {
        return this.read0();
    }
    
    private native int read0() throws IOException;
    
    private native int readBytes(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.readBytes(array, n, n2);
    }
    
    public int read(final byte[] array) throws IOException {
        return this.readBytes(array, 0, array.length);
    }
    
    @Override
    public final void readFully(final byte[] array) throws IOException {
        this.readFully(array, 0, array.length);
    }
    
    @Override
    public final void readFully(final byte[] array, final int n, final int n2) throws IOException {
        int i = 0;
        do {
            final int read = this.read(array, n + i, n2 - i);
            if (read < 0) {
                throw new EOFException();
            }
            i += read;
        } while (i < n2);
    }
    
    @Override
    public int skipBytes(final int n) throws IOException {
        if (n <= 0) {
            return 0;
        }
        final long filePointer = this.getFilePointer();
        final long length = this.length();
        long n2 = filePointer + n;
        if (n2 > length) {
            n2 = length;
        }
        this.seek(n2);
        return (int)(n2 - filePointer);
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.write0(n);
    }
    
    private native void write0(final int p0) throws IOException;
    
    private native void writeBytes(final byte[] p0, final int p1, final int p2) throws IOException;
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.writeBytes(array, 0, array.length);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.writeBytes(array, n, n2);
    }
    
    public native long getFilePointer() throws IOException;
    
    public void seek(final long n) throws IOException {
        if (n < 0L) {
            throw new IOException("Negative seek offset");
        }
        this.seek0(n);
    }
    
    private native void seek0(final long p0) throws IOException;
    
    public native long length() throws IOException;
    
    public native void setLength(final long p0) throws IOException;
    
    @Override
    public void close() throws IOException {
        synchronized (this.closeLock) {
            if (this.closed) {
                return;
            }
            this.closed = true;
        }
        if (this.channel != null) {
            this.channel.close();
        }
        this.fd.closeAll(new Closeable() {
            @Override
            public void close() throws IOException {
                RandomAccessFile.this.close0();
            }
        });
    }
    
    @Override
    public final boolean readBoolean() throws IOException {
        final int read = this.read();
        if (read < 0) {
            throw new EOFException();
        }
        return read != 0;
    }
    
    @Override
    public final byte readByte() throws IOException {
        final int read = this.read();
        if (read < 0) {
            throw new EOFException();
        }
        return (byte)read;
    }
    
    @Override
    public final int readUnsignedByte() throws IOException {
        final int read = this.read();
        if (read < 0) {
            throw new EOFException();
        }
        return read;
    }
    
    @Override
    public final short readShort() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (short)((read << 8) + (read2 << 0));
    }
    
    @Override
    public final int readUnsignedShort() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (read << 8) + (read2 << 0);
    }
    
    @Override
    public final char readChar() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (char)((read << 8) + (read2 << 0));
    }
    
    @Override
    public final int readInt() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        final int read3 = this.read();
        final int read4 = this.read();
        if ((read | read2 | read3 | read4) < 0) {
            throw new EOFException();
        }
        return (read << 24) + (read2 << 16) + (read3 << 8) + (read4 << 0);
    }
    
    @Override
    public final long readLong() throws IOException {
        return (this.readInt() << 32) + (this.readInt() & 0xFFFFFFFFL);
    }
    
    @Override
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    @Override
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    @Override
    public final String readLine() throws IOException {
        final StringBuffer sb = new StringBuffer();
        int read = -1;
        int i = 0;
        while (i == 0) {
            switch (read = this.read()) {
                case -1:
                case 10: {
                    i = 1;
                    continue;
                }
                case 13: {
                    i = 1;
                    final long filePointer = this.getFilePointer();
                    if (this.read() != 10) {
                        this.seek(filePointer);
                        continue;
                    }
                    continue;
                }
                default: {
                    sb.append((char)read);
                    continue;
                }
            }
        }
        if (read == -1 && sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }
    
    @Override
    public final String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }
    
    @Override
    public final void writeBoolean(final boolean b) throws IOException {
        this.write(b ? 1 : 0);
    }
    
    @Override
    public final void writeByte(final int n) throws IOException {
        this.write(n);
    }
    
    @Override
    public final void writeShort(final int n) throws IOException {
        this.write(n >>> 8 & 0xFF);
        this.write(n >>> 0 & 0xFF);
    }
    
    @Override
    public final void writeChar(final int n) throws IOException {
        this.write(n >>> 8 & 0xFF);
        this.write(n >>> 0 & 0xFF);
    }
    
    @Override
    public final void writeInt(final int n) throws IOException {
        this.write(n >>> 24 & 0xFF);
        this.write(n >>> 16 & 0xFF);
        this.write(n >>> 8 & 0xFF);
        this.write(n >>> 0 & 0xFF);
    }
    
    @Override
    public final void writeLong(final long n) throws IOException {
        this.write((int)(n >>> 56) & 0xFF);
        this.write((int)(n >>> 48) & 0xFF);
        this.write((int)(n >>> 40) & 0xFF);
        this.write((int)(n >>> 32) & 0xFF);
        this.write((int)(n >>> 24) & 0xFF);
        this.write((int)(n >>> 16) & 0xFF);
        this.write((int)(n >>> 8) & 0xFF);
        this.write((int)(n >>> 0) & 0xFF);
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
        final byte[] array = new byte[length];
        s.getBytes(0, length, array, 0);
        this.writeBytes(array, 0, length);
    }
    
    @Override
    public final void writeChars(final String s) throws IOException {
        final int length = s.length();
        final int n = 2 * length;
        final byte[] array = new byte[n];
        final char[] array2 = new char[length];
        s.getChars(0, length, array2, 0);
        int i = 0;
        int n2 = 0;
        while (i < length) {
            array[n2++] = (byte)(array2[i] >>> 8);
            array[n2++] = (byte)(array2[i] >>> 0);
            ++i;
        }
        this.writeBytes(array, 0, n);
    }
    
    @Override
    public final void writeUTF(final String s) throws IOException {
        DataOutputStream.writeUTF(s, this);
    }
    
    private static native void initIDs();
    
    private native void close0() throws IOException;
    
    static {
        initIDs();
    }
}
