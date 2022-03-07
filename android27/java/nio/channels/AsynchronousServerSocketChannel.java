package java.nio.channels;

import java.nio.channels.spi.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public abstract class AsynchronousServerSocketChannel implements AsynchronousChannel, NetworkChannel
{
    private final AsynchronousChannelProvider provider;
    
    protected AsynchronousServerSocketChannel(final AsynchronousChannelProvider provider) {
        this.provider = provider;
    }
    
    public final AsynchronousChannelProvider provider() {
        return this.provider;
    }
    
    public static AsynchronousServerSocketChannel open(final AsynchronousChannelGroup asynchronousChannelGroup) throws IOException {
        return ((asynchronousChannelGroup == null) ? AsynchronousChannelProvider.provider() : asynchronousChannelGroup.provider()).openAsynchronousServerSocketChannel(asynchronousChannelGroup);
    }
    
    public static AsynchronousServerSocketChannel open() throws IOException {
        return open(null);
    }
    
    @Override
    public final AsynchronousServerSocketChannel bind(final SocketAddress socketAddress) throws IOException {
        return this.bind(socketAddress, 0);
    }
    
    public abstract AsynchronousServerSocketChannel bind(final SocketAddress p0, final int p1) throws IOException;
    
    @Override
    public abstract <T> AsynchronousServerSocketChannel setOption(final SocketOption<T> p0, final T p1) throws IOException;
    
    public abstract <A> void accept(final A p0, final CompletionHandler<AsynchronousSocketChannel, ? super A> p1);
    
    public abstract Future<AsynchronousSocketChannel> accept();
    
    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;
}
