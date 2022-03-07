package java.nio.channels;

import java.nio.channels.spi.*;
import java.io.*;
import java.net.*;

public abstract class ServerSocketChannel extends AbstractSelectableChannel implements NetworkChannel
{
    protected ServerSocketChannel(final SelectorProvider selectorProvider) {
        super(selectorProvider);
    }
    
    public static ServerSocketChannel open() throws IOException {
        return SelectorProvider.provider().openServerSocketChannel();
    }
    
    @Override
    public final int validOps() {
        return 16;
    }
    
    @Override
    public final ServerSocketChannel bind(final SocketAddress socketAddress) throws IOException {
        return this.bind(socketAddress, 0);
    }
    
    public abstract ServerSocketChannel bind(final SocketAddress p0, final int p1) throws IOException;
    
    @Override
    public abstract <T> ServerSocketChannel setOption(final SocketOption<T> p0, final T p1) throws IOException;
    
    public abstract ServerSocket socket();
    
    public abstract SocketChannel accept() throws IOException;
    
    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;
}
