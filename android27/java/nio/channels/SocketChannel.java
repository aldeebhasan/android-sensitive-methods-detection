package java.nio.channels;

import java.nio.channels.spi.*;
import java.io.*;
import java.net.*;
import java.nio.*;

public abstract class SocketChannel extends AbstractSelectableChannel implements ByteChannel, ScatteringByteChannel, GatheringByteChannel, NetworkChannel
{
    protected SocketChannel(final SelectorProvider selectorProvider) {
        super(selectorProvider);
    }
    
    public static SocketChannel open() throws IOException {
        return SelectorProvider.provider().openSocketChannel();
    }
    
    public static SocketChannel open(final SocketAddress socketAddress) throws IOException {
        final SocketChannel open = open();
        try {
            open.connect(socketAddress);
        }
        catch (Throwable t) {
            try {
                open.close();
            }
            catch (Throwable t2) {
                t.addSuppressed(t2);
            }
            throw t;
        }
        assert open.isConnected();
        return open;
    }
    
    @Override
    public final int validOps() {
        return 13;
    }
    
    @Override
    public abstract SocketChannel bind(final SocketAddress p0) throws IOException;
    
    @Override
    public abstract <T> SocketChannel setOption(final SocketOption<T> p0, final T p1) throws IOException;
    
    public abstract SocketChannel shutdownInput() throws IOException;
    
    public abstract SocketChannel shutdownOutput() throws IOException;
    
    public abstract Socket socket();
    
    public abstract boolean isConnected();
    
    public abstract boolean isConnectionPending();
    
    public abstract boolean connect(final SocketAddress p0) throws IOException;
    
    public abstract boolean finishConnect() throws IOException;
    
    public abstract SocketAddress getRemoteAddress() throws IOException;
    
    @Override
    public abstract int read(final ByteBuffer p0) throws IOException;
    
    @Override
    public abstract long read(final ByteBuffer[] p0, final int p1, final int p2) throws IOException;
    
    @Override
    public final long read(final ByteBuffer[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public abstract int write(final ByteBuffer p0) throws IOException;
    
    @Override
    public abstract long write(final ByteBuffer[] p0, final int p1, final int p2) throws IOException;
    
    @Override
    public final long write(final ByteBuffer[] array) throws IOException {
        return this.write(array, 0, array.length);
    }
    
    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;
}
