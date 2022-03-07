package java.nio.channels;

import java.nio.channels.spi.*;
import java.io.*;
import java.net.*;
import java.nio.*;

public abstract class DatagramChannel extends AbstractSelectableChannel implements ByteChannel, ScatteringByteChannel, GatheringByteChannel, MulticastChannel
{
    protected DatagramChannel(final SelectorProvider selectorProvider) {
        super(selectorProvider);
    }
    
    public static DatagramChannel open() throws IOException {
        return SelectorProvider.provider().openDatagramChannel();
    }
    
    public static DatagramChannel open(final ProtocolFamily protocolFamily) throws IOException {
        return SelectorProvider.provider().openDatagramChannel(protocolFamily);
    }
    
    @Override
    public final int validOps() {
        return 5;
    }
    
    @Override
    public abstract DatagramChannel bind(final SocketAddress p0) throws IOException;
    
    @Override
    public abstract <T> DatagramChannel setOption(final SocketOption<T> p0, final T p1) throws IOException;
    
    public abstract DatagramSocket socket();
    
    public abstract boolean isConnected();
    
    public abstract DatagramChannel connect(final SocketAddress p0) throws IOException;
    
    public abstract DatagramChannel disconnect() throws IOException;
    
    public abstract SocketAddress getRemoteAddress() throws IOException;
    
    public abstract SocketAddress receive(final ByteBuffer p0) throws IOException;
    
    public abstract int send(final ByteBuffer p0, final SocketAddress p1) throws IOException;
    
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
