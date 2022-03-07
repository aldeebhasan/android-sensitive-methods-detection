package java.nio.channels;

import java.nio.*;
import sun.nio.ch.*;
import java.util.concurrent.*;
import java.nio.charset.*;
import java.io.*;
import sun.nio.cs.*;
import java.nio.channels.spi.*;

public final class Channels
{
    private static void checkNotNull(final Object o, final String s) {
        if (o == null) {
            throw new NullPointerException("\"" + s + "\" is null!");
        }
    }
    
    private static void writeFullyImpl(final WritableByteChannel writableByteChannel, final ByteBuffer byteBuffer) throws IOException {
        while (byteBuffer.remaining() > 0) {
            if (writableByteChannel.write(byteBuffer) <= 0) {
                throw new RuntimeException("no bytes written");
            }
        }
    }
    
    private static void writeFully(final WritableByteChannel writableByteChannel, final ByteBuffer byteBuffer) throws IOException {
        if (writableByteChannel instanceof SelectableChannel) {
            final SelectableChannel selectableChannel = (SelectableChannel)writableByteChannel;
            synchronized (selectableChannel.blockingLock()) {
                if (!selectableChannel.isBlocking()) {
                    throw new IllegalBlockingModeException();
                }
                writeFullyImpl(writableByteChannel, byteBuffer);
            }
        }
        else {
            writeFullyImpl(writableByteChannel, byteBuffer);
        }
    }
    
    public static InputStream newInputStream(final ReadableByteChannel readableByteChannel) {
        checkNotNull(readableByteChannel, "ch");
        return new ChannelInputStream(readableByteChannel);
    }
    
    public static OutputStream newOutputStream(final WritableByteChannel writableByteChannel) {
        checkNotNull(writableByteChannel, "ch");
        return new OutputStream() {
            private ByteBuffer bb = null;
            private byte[] bs = null;
            private byte[] b1 = null;
            
            @Override
            public synchronized void write(final int n) throws IOException {
                if (this.b1 == null) {
                    this.b1 = new byte[1];
                }
                this.b1[0] = (byte)n;
                this.write(this.b1);
            }
            
            @Override
            public synchronized void write(final byte[] bs, final int n, final int n2) throws IOException {
                if (n < 0 || n > bs.length || n2 < 0 || n + n2 > bs.length || n + n2 < 0) {
                    throw new IndexOutOfBoundsException();
                }
                if (n2 == 0) {
                    return;
                }
                final ByteBuffer bb = (this.bs == bs) ? this.bb : ByteBuffer.wrap(bs);
                bb.limit(Math.min(n + n2, bb.capacity()));
                bb.position(n);
                this.bb = bb;
                this.bs = bs;
                writeFully(writableByteChannel, bb);
            }
            
            @Override
            public void close() throws IOException {
                writableByteChannel.close();
            }
        };
    }
    
    public static InputStream newInputStream(final AsynchronousByteChannel asynchronousByteChannel) {
        checkNotNull(asynchronousByteChannel, "ch");
        return new InputStream() {
            private ByteBuffer bb = null;
            private byte[] bs = null;
            private byte[] b1 = null;
            
            @Override
            public synchronized int read() throws IOException {
                if (this.b1 == null) {
                    this.b1 = new byte[1];
                }
                if (this.read(this.b1) == 1) {
                    return this.b1[0] & 0xFF;
                }
                return -1;
            }
            
            @Override
            public synchronized int read(final byte[] bs, final int n, final int n2) throws IOException {
                if (n < 0 || n > bs.length || n2 < 0 || n + n2 > bs.length || n + n2 < 0) {
                    throw new IndexOutOfBoundsException();
                }
                if (n2 == 0) {
                    return 0;
                }
                final ByteBuffer bb = (this.bs == bs) ? this.bb : ByteBuffer.wrap(bs);
                bb.position(n);
                bb.limit(Math.min(n + n2, bb.capacity()));
                this.bb = bb;
                this.bs = bs;
                boolean b = false;
                try {
                    return asynchronousByteChannel.read(bb).get();
                }
                catch (ExecutionException ex) {
                    throw new IOException(ex.getCause());
                }
                catch (InterruptedException ex2) {
                    b = true;
                }
                finally {
                    if (b) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            @Override
            public void close() throws IOException {
                asynchronousByteChannel.close();
            }
        };
    }
    
    public static OutputStream newOutputStream(final AsynchronousByteChannel asynchronousByteChannel) {
        checkNotNull(asynchronousByteChannel, "ch");
        return new OutputStream() {
            private ByteBuffer bb = null;
            private byte[] bs = null;
            private byte[] b1 = null;
            
            @Override
            public synchronized void write(final int n) throws IOException {
                if (this.b1 == null) {
                    this.b1 = new byte[1];
                }
                this.b1[0] = (byte)n;
                this.write(this.b1);
            }
            
            @Override
            public synchronized void write(final byte[] bs, final int n, final int n2) throws IOException {
                if (n < 0 || n > bs.length || n2 < 0 || n + n2 > bs.length || n + n2 < 0) {
                    throw new IndexOutOfBoundsException();
                }
                if (n2 == 0) {
                    return;
                }
                final ByteBuffer bb = (this.bs == bs) ? this.bb : ByteBuffer.wrap(bs);
                bb.limit(Math.min(n + n2, bb.capacity()));
                bb.position(n);
                this.bb = bb;
                this.bs = bs;
                boolean b = false;
                try {
                    while (bb.remaining() > 0) {
                        try {
                            asynchronousByteChannel.write(bb).get();
                            continue;
                        }
                        catch (ExecutionException ex) {
                            throw new IOException(ex.getCause());
                        }
                        catch (InterruptedException ex2) {
                            b = true;
                            continue;
                        }
                        break;
                    }
                }
                finally {
                    if (b) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            @Override
            public void close() throws IOException {
                asynchronousByteChannel.close();
            }
        };
    }
    
    public static ReadableByteChannel newChannel(final InputStream inputStream) {
        checkNotNull(inputStream, "in");
        if (inputStream instanceof FileInputStream && FileInputStream.class.equals(inputStream.getClass())) {
            return ((FileInputStream)inputStream).getChannel();
        }
        return new ReadableByteChannelImpl(inputStream);
    }
    
    public static WritableByteChannel newChannel(final OutputStream outputStream) {
        checkNotNull(outputStream, "out");
        if (outputStream instanceof FileOutputStream && FileOutputStream.class.equals(outputStream.getClass())) {
            return ((FileOutputStream)outputStream).getChannel();
        }
        return new WritableByteChannelImpl(outputStream);
    }
    
    public static Reader newReader(final ReadableByteChannel readableByteChannel, final CharsetDecoder charsetDecoder, final int n) {
        checkNotNull(readableByteChannel, "ch");
        return StreamDecoder.forDecoder(readableByteChannel, charsetDecoder.reset(), n);
    }
    
    public static Reader newReader(final ReadableByteChannel readableByteChannel, final String s) {
        checkNotNull(s, "csName");
        return newReader(readableByteChannel, Charset.forName(s).newDecoder(), -1);
    }
    
    public static Writer newWriter(final WritableByteChannel writableByteChannel, final CharsetEncoder charsetEncoder, final int n) {
        checkNotNull(writableByteChannel, "ch");
        return StreamEncoder.forEncoder(writableByteChannel, charsetEncoder.reset(), n);
    }
    
    public static Writer newWriter(final WritableByteChannel writableByteChannel, final String s) {
        checkNotNull(s, "csName");
        return newWriter(writableByteChannel, Charset.forName(s).newEncoder(), -1);
    }
    
    private static class ReadableByteChannelImpl extends AbstractInterruptibleChannel implements ReadableByteChannel
    {
        InputStream in;
        private static final int TRANSFER_SIZE = 8192;
        private byte[] buf;
        private boolean open;
        private Object readLock;
        
        ReadableByteChannelImpl(final InputStream in) {
            this.buf = new byte[0];
            this.open = true;
            this.readLock = new Object();
            this.in = in;
        }
        
        @Override
        public int read(final ByteBuffer byteBuffer) throws IOException {
            final int remaining = byteBuffer.remaining();
            int i = 0;
            int read = 0;
            synchronized (this.readLock) {
                while (i < remaining) {
                    final int min = Math.min(remaining - i, 8192);
                    if (this.buf.length < min) {
                        this.buf = new byte[min];
                    }
                    if (i > 0 && this.in.available() <= 0) {
                        break;
                    }
                    try {
                        this.begin();
                        read = this.in.read(this.buf, 0, min);
                    }
                    finally {
                        this.end(read > 0);
                    }
                    if (read < 0) {
                        break;
                    }
                    i += read;
                    byteBuffer.put(this.buf, 0, read);
                }
                if (read < 0 && i == 0) {
                    return -1;
                }
                return i;
            }
        }
        
        @Override
        protected void implCloseChannel() throws IOException {
            this.in.close();
            this.open = false;
        }
    }
    
    private static class WritableByteChannelImpl extends AbstractInterruptibleChannel implements WritableByteChannel
    {
        OutputStream out;
        private static final int TRANSFER_SIZE = 8192;
        private byte[] buf;
        private boolean open;
        private Object writeLock;
        
        WritableByteChannelImpl(final OutputStream out) {
            this.buf = new byte[0];
            this.open = true;
            this.writeLock = new Object();
            this.out = out;
        }
        
        @Override
        public int write(final ByteBuffer byteBuffer) throws IOException {
            final int remaining = byteBuffer.remaining();
            int i = 0;
            synchronized (this.writeLock) {
                while (i < remaining) {
                    final int min = Math.min(remaining - i, 8192);
                    if (this.buf.length < min) {
                        this.buf = new byte[min];
                    }
                    byteBuffer.get(this.buf, 0, min);
                    try {
                        this.begin();
                        this.out.write(this.buf, 0, min);
                    }
                    finally {
                        this.end(min > 0);
                    }
                    i += min;
                }
                return i;
            }
        }
        
        @Override
        protected void implCloseChannel() throws IOException {
            this.out.close();
            this.open = false;
        }
    }
}
