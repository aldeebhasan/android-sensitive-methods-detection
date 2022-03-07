package android.os;

import java.io.*;

public class Binder implements IBinder
{
    public Binder() {
        throw new RuntimeException("Stub!");
    }
    
    public static final native int getCallingPid();
    
    public static final native int getCallingUid();
    
    public static final UserHandle getCallingUserHandle() {
        throw new RuntimeException("Stub!");
    }
    
    public static final native long clearCallingIdentity();
    
    public static final native void restoreCallingIdentity(final long p0);
    
    public static final native void flushPendingCommands();
    
    public static final native void joinThreadPool();
    
    public void attachInterface(final IInterface owner, final String descriptor) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getInterfaceDescriptor() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean pingBinder() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isBinderAlive() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IInterface queryLocalInterface(final String descriptor) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean onTransact(final int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dump(final FileDescriptor fd, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dumpAsync(final FileDescriptor fd, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    protected void dump(final FileDescriptor fd, final PrintWriter fout, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final boolean transact(final int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void linkToDeath(final DeathRecipient recipient, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean unlinkToDeath(final DeathRecipient recipient, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
