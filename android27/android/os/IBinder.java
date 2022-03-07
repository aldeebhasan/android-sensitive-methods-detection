package android.os;

import java.io.*;

public interface IBinder
{
    public static final int DUMP_TRANSACTION = 1598311760;
    public static final int FIRST_CALL_TRANSACTION = 1;
    public static final int FLAG_ONEWAY = 1;
    public static final int INTERFACE_TRANSACTION = 1598968902;
    public static final int LAST_CALL_TRANSACTION = 16777215;
    public static final int LIKE_TRANSACTION = 1598835019;
    public static final int PING_TRANSACTION = 1599098439;
    public static final int TWEET_TRANSACTION = 1599362900;
    
    String getInterfaceDescriptor() throws RemoteException;
    
    boolean pingBinder();
    
    boolean isBinderAlive();
    
    IInterface queryLocalInterface(final String p0);
    
    void dump(final FileDescriptor p0, final String[] p1) throws RemoteException;
    
    void dumpAsync(final FileDescriptor p0, final String[] p1) throws RemoteException;
    
    boolean transact(final int p0, final Parcel p1, final Parcel p2, final int p3) throws RemoteException;
    
    void linkToDeath(final DeathRecipient p0, final int p1) throws RemoteException;
    
    boolean unlinkToDeath(final DeathRecipient p0, final int p1);
    
    public interface DeathRecipient
    {
        void binderDied();
    }
}
