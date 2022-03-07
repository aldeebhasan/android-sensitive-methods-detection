package java.io;

import java.nio.channels.*;
import sun.nio.ch.*;

public class FileOutputStream extends OutputStream
{
    private final FileDescriptor fd;
    private final boolean append;
    private FileChannel channel;
    private final String path;
    private final Object closeLock;
    private volatile boolean closed;
    
    public FileOutputStream(final String s) throws FileNotFoundException {
        this((s != null) ? new File(s) : null, false);
    }
    
    public FileOutputStream(final String s, final boolean b) throws FileNotFoundException {
        this((s != null) ? new File(s) : null, b);
    }
    
    public FileOutputStream(final File file) throws FileNotFoundException {
        this(file, false);
    }
    
    public FileOutputStream(final File file, final boolean append) throws FileNotFoundException {
        this.closeLock = new Object();
        this.closed = false;
        final String path = (file != null) ? file.getPath() : null;
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(path);
        }
        if (path == null) {
            throw new NullPointerException();
        }
        if (file.isInvalid()) {
            throw new FileNotFoundException("Invalid file path");
        }
        (this.fd = new FileDescriptor()).attach(this);
        this.append = append;
        this.open(this.path = path, append);
    }
    
    public FileOutputStream(final FileDescriptor fd) {
        this.closeLock = new Object();
        this.closed = false;
        final SecurityManager securityManager = System.getSecurityManager();
        if (fd == null) {
            throw new NullPointerException();
        }
        if (securityManager != null) {
            securityManager.checkWrite(fd);
        }
        this.fd = fd;
        this.append = false;
        this.path = null;
        this.fd.attach(this);
    }
    
    private native void open0(final String p0, final boolean p1) throws FileNotFoundException;
    
    private void open(final String s, final boolean b) throws FileNotFoundException {
        this.open0(s, b);
    }
    
    private native void write(final int p0, final boolean p1) throws IOException;
    
    @Override
    public void write(final int n) throws IOException {
        this.write(n, this.append);
    }
    
    private native void writeBytes(final byte[] p0, final int p1, final int p2, final boolean p3) throws IOException;
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.writeBytes(array, 0, array.length, this.append);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.writeBytes(array, n, n2, this.append);
    }
    
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
                FileOutputStream.this.close0();
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
                this.channel = FileChannelImpl.open(this.fd, this.path, false, true, this.append, this);
            }
            return this.channel;
        }
    }
    
    @Override
    protected void finalize() throws IOException {
        if (this.fd != null) {
            if (this.fd == FileDescriptor.out || this.fd == FileDescriptor.err) {
                this.flush();
            }
            else {
                this.close();
            }
        }
    }
    
    private native void close0() throws IOException;
    
    private static native void initIDs();
    
    static {
        initIDs();
    }
}
