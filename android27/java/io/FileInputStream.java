package java.io;

import java.nio.channels.*;
import sun.nio.ch.*;

public class FileInputStream extends InputStream
{
    private final FileDescriptor fd;
    private final String path;
    private FileChannel channel;
    private final Object closeLock;
    private volatile boolean closed;
    
    public FileInputStream(final String s) throws FileNotFoundException {
        this((s != null) ? new File(s) : null);
    }
    
    public FileInputStream(final File file) throws FileNotFoundException {
        this.channel = null;
        this.closeLock = new Object();
        this.closed = false;
        final String path = (file != null) ? file.getPath() : null;
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(path);
        }
        if (path == null) {
            throw new NullPointerException();
        }
        if (file.isInvalid()) {
            throw new FileNotFoundException("Invalid file path");
        }
        (this.fd = new FileDescriptor()).attach(this);
        this.open(this.path = path);
    }
    
    public FileInputStream(final FileDescriptor fd) {
        this.channel = null;
        this.closeLock = new Object();
        this.closed = false;
        final SecurityManager securityManager = System.getSecurityManager();
        if (fd == null) {
            throw new NullPointerException();
        }
        if (securityManager != null) {
            securityManager.checkRead(fd);
        }
        this.fd = fd;
        this.path = null;
        this.fd.attach(this);
    }
    
    private native void open0(final String p0) throws FileNotFoundException;
    
    private void open(final String s) throws FileNotFoundException {
        this.open0(s);
    }
    
    @Override
    public int read() throws IOException {
        return this.read0();
    }
    
    private native int read0() throws IOException;
    
    private native int readBytes(final byte[] p0, final int p1, final int p2) throws IOException;
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.readBytes(array, 0, array.length);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.readBytes(array, n, n2);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return this.skip0(n);
    }
    
    private native long skip0(final long p0) throws IOException;
    
    @Override
    public int available() throws IOException {
        return this.available0();
    }
    
    private native int available0() throws IOException;
    
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
                FileInputStream.this.close0();
            }
        });
    }
    
    public final FileDescriptor getFD() throws IOException {
        if (this.fd != null) {
            return this.fd;
        }
        throw new IOException();
    }
    
    public FileChannel getChannel() {
        synchronized (this) {
            if (this.channel == null) {
                this.channel = FileChannelImpl.open(this.fd, this.path, true, false, this);
            }
            return this.channel;
        }
    }
    
    private static native void initIDs();
    
    private native void close0() throws IOException;
    
    @Override
    protected void finalize() throws IOException {
        if (this.fd != null && this.fd != FileDescriptor.in) {
            this.close();
        }
    }
    
    static {
        initIDs();
    }
}
