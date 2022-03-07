package java.io;

import java.util.*;
import sun.misc.*;

public final class FileDescriptor
{
    private int fd;
    private long handle;
    private Closeable parent;
    private List<Closeable> otherParents;
    private boolean closed;
    public static final FileDescriptor in;
    public static final FileDescriptor out;
    public static final FileDescriptor err;
    
    public FileDescriptor() {
        this.fd = -1;
        this.handle = -1L;
    }
    
    public boolean valid() {
        return this.handle != -1L || this.fd != -1;
    }
    
    public native void sync() throws SyncFailedException;
    
    private static native void initIDs();
    
    private static native long set(final int p0);
    
    private static FileDescriptor standardStream(final int n) {
        final FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.handle = set(n);
        return fileDescriptor;
    }
    
    synchronized void attach(final Closeable parent) {
        if (this.parent == null) {
            this.parent = parent;
        }
        else if (this.otherParents == null) {
            (this.otherParents = new ArrayList<Closeable>()).add(this.parent);
            this.otherParents.add(parent);
        }
        else {
            this.otherParents.add(parent);
        }
    }
    
    synchronized void closeAll(final Closeable closeable) throws IOException {
        if (!this.closed) {
            this.closed = true;
            IOException ex = null;
            try {
                Throwable t = null;
                try {
                    if (this.otherParents != null) {
                        for (final Closeable closeable2 : this.otherParents) {
                            try {
                                closeable2.close();
                            }
                            catch (IOException ex2) {
                                if (ex == null) {
                                    ex = ex2;
                                }
                                else {
                                    ex.addSuppressed(ex2);
                                }
                            }
                        }
                    }
                }
                catch (Throwable t2) {
                    t = t2;
                    throw t2;
                }
                finally {
                    if (closeable != null) {
                        if (t != null) {
                            try {
                                closeable.close();
                            }
                            catch (Throwable t3) {
                                t.addSuppressed(t3);
                            }
                        }
                        else {
                            closeable.close();
                        }
                    }
                }
            }
            catch (IOException ex3) {
                if (ex != null) {
                    ex3.addSuppressed(ex);
                }
                ex = ex3;
            }
            finally {
                if (ex != null) {
                    throw ex;
                }
            }
        }
    }
    
    static {
        initIDs();
        SharedSecrets.setJavaIOFileDescriptorAccess(new JavaIOFileDescriptorAccess() {
            @Override
            public void set(final FileDescriptor fileDescriptor, final int n) {
                fileDescriptor.fd = n;
            }
            
            @Override
            public int get(final FileDescriptor fileDescriptor) {
                return fileDescriptor.fd;
            }
            
            @Override
            public void setHandle(final FileDescriptor fileDescriptor, final long n) {
                fileDescriptor.handle = n;
            }
            
            @Override
            public long getHandle(final FileDescriptor fileDescriptor) {
                return fileDescriptor.handle;
            }
        });
        in = standardStream(0);
        out = standardStream(1);
        err = standardStream(2);
    }
}
