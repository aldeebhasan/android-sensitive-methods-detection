package java.nio.channels;

import java.nio.channels.spi.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.concurrent.*;

public abstract class AsynchronousSocketChannel implements AsynchronousByteChannel, NetworkChannel
{
    private final AsynchronousChannelProvider provider;
    
    protected AsynchronousSocketChannel(final AsynchronousChannelProvider provider) {
        this.provider = provider;
    }
    
    public final AsynchronousChannelProvider provider() {
        return this.provider;
    }
    
    public static AsynchronousSocketChannel open(final AsynchronousChannelGroup asynchronousChannelGroup) throws IOException {
        return ((asynchronousChannelGroup == null) ? AsynchronousChannelProvider.provider() : asynchronousChannelGroup.provider()).openAsynchronousSocketChannel(asynchronousChannelGroup);
    }
    
    public static AsynchronousSocketChannel open() throws IOException {
        return open(null);
    }
    
    @Override
    public abstract AsynchronousSocketChannel bind(final SocketAddress p0) throws IOException;
    
    @Override
    public abstract <T> AsynchronousSocketChannel setOption(final SocketOption<T> p0, final T p1) throws IOException;
    
    public abstract AsynchronousSocketChannel shutdownInput() throws IOException;
    
    public abstract AsynchronousSocketChannel shutdownOutput() throws IOException;
    
    public abstract SocketAddress getRemoteAddress() throws IOException;
    
    public abstract <A> void connect(final SocketAddress p0, final A p1, final CompletionHandler<Void, ? super A> p2);
    
    public abstract Future<Void> connect(final SocketAddress p0);
    
    public abstract <A> void read(final ByteBuffer p0, final long p1, final TimeUnit p2, final A p3, final CompletionHandler<Integer, ? super A> p4);
    
    @Override
    public final <A> void read(final ByteBuffer byteBuffer, final A a, final CompletionHandler<Integer, ? super A> completionHandler) {
        this.read(byteBuffer, 0L, TimeUnit.MILLISECONDS, a, completionHandler);
    }
    
    @Override
    public abstract Future<Integer> read(final ByteBuffer p0);
    
    public abstract <A> void read(final ByteBuffer[] p0, final int p1, final int p2, final long p3, final TimeUnit p4, final A p5, final CompletionHandler<Long, ? super A> p6);
    
    public abstract <A> void write(final ByteBuffer p0, final long p1, final TimeUnit p2, final A p3, final CompletionHandler<Integer, ? super A> p4);
    
    @Override
    public final <A> void write(final ByteBuffer byteBuffer, final A a, final CompletionHandler<Integer, ? super A> completionHandler) {
        this.write(byteBuffer, 0L, TimeUnit.MILLISECONDS, a, completionHandler);
    }
    
    @Override
    public abstract Future<Integer> write(final ByteBuffer p0);
    
    public abstract <A> void write(final ByteBuffer[] p0, final int p1, final int p2, final long p3, final TimeUnit p4, final A p5, final CompletionHandler<Long, ? super A> p6);
    
    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;
}
